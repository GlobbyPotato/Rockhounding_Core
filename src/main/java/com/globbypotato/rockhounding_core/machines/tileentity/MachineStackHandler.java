package com.globbypotato.rockhounding_core.machines.tileentity;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class MachineStackHandler extends ItemStackHandler{
	Random rand = new Random();
	
	TileEntityInv tile;
	public MachineStackHandler(int slots,TileEntityInv tile) {
		super(slots);
		this.tile = tile;
	}

/*
 * ------------------------------------------------------
 * 						ITEMSTACKS
 * ------------------------------------------------------
 */

	/**
	 * Check if can insert the new stack in the slot or add an amount to the existing one 
	 * 
	 * @param stackInSlot
	 * @param recipeOutput
	 * @return
	 */
	public boolean canSetOrStack(ItemStack stackInSlot, ItemStack recipeOutput) {
		return stackInSlot.isEmpty() || canStack(stackInSlot, recipeOutput);
	}

	/**
	 * Check if can add an amount to the existing one 
	 * 
	 * @param stackInSlot
	 * @param recipeOutput
	 * @return
	 */
	public boolean canStack(ItemStack stackInSlot, ItemStack recipeOutput) {
		return !stackInSlot.isEmpty() && stackInSlot.isItemEqual(recipeOutput) && stackInSlot.getCount() <= stackInSlot.getMaxStackSize() - recipeOutput.getCount();
	}

	/**
	 * Insert a new stack or add an amount to the existing one
	 * 
	 * @param slot
	 * @param stackToSet
	 */
	public void setOrStack(int slot, ItemStack stackToSet){
		if(this.getStackInSlot(slot).isEmpty()){
			this.setStackInSlot(slot, stackToSet);
		}else{
			for(int x = 0; x < stackToSet.getCount(); x++){
				incrementSlot(slot);
			}
		}
	}

	/**
	 * Check if increase by 1 the current stack
	 * 
	 * @param stack
	 * @return
	 */
	public boolean canIncrementSlot(ItemStack stack) {
		return !stack.isEmpty() && stack.getCount() < stack.getMaxStackSize();
	}

	/**
	 * Increase by 1 the current slot
	 * 
	 * @param slot
	 */
	public void incrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		if(temp.getCount()+1 <= temp.getMaxStackSize()){
			temp.grow(1);
		}
		this.setStackInSlot(slot, temp);
	}

	/**
	 * Increase the stack in slot by an amount
	 * 
	 * @param slot
	 * @param num
	 */
	public void incrementSlotBy(int slot, int num){
		ItemStack temp = this.getStackInSlot(slot);
		int adding = Math.min(temp.getMaxStackSize() - temp.getCount(), num);
		temp.grow(adding);
		this.setStackInSlot(slot, temp);
	}

	/**
	 * Insert a new stack or increment the existing one
	 * 
	 * @param slot
	 * @param stackToSet
	 */
	public void setOrIncrement(int slot, ItemStack stackToSet){
		if(this.getStackInSlot(slot).isEmpty()){
			this.setStackInSlot(slot, stackToSet);
		}else{
			incrementSlot(slot);
		}
	}

	/**
	 * Decrease by 1 the stack in slot
	 * 
	 * @param slot
	 */
	public void decrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		temp.shrink(1);
		if(temp.getCount() <= 0){
			this.setStackInSlot(slot, ItemStack.EMPTY);
		}else{
			this.setStackInSlot(slot, temp);
		}
	}

	/**
	 * Decrease the stack in slot by a specific amount
	 * 
	 * @param slot
	 * @param num
	 */
	public void decrementSlotBy(int slot, int num){
		ItemStack temp = this.getStackInSlot(slot);
		int taking = Math.min(temp.getCount(), num);
		temp.shrink(taking);
		if(temp.getCount() <= 0){
			this.setStackInSlot(slot, ItemStack.EMPTY);
		}else{
			this.setStackInSlot(slot, temp);
		}
	}

	/**
	 * Damage the stack in the slot
	 * 
	 * @param slot
	 */
	public void damageSlot(int slot) {
		if(!this.getStackInSlot(slot).isEmpty()){
			int damage = this.getStackInSlot(slot).getItemDamage() + 1;
			this.getStackInSlot(slot).setItemDamage(damage);
			if(damage >= this.getStackInSlot(slot).getMaxDamage()){
				this.getStackInSlot(slot).shrink(1);
			}
			if(this.getStackInSlot(slot).getCount() <= 0){
				this.setStackInSlot(slot, ItemStack.EMPTY);
			}
		}
	}

	/**
	  * Damage the stack in the slot by a specific amount
	  * 
	  * @param slot
	  * @param step
	  */
	 public void damageSlot(int slot, int step) {
	 	if(this.getStackInSlot(slot) != ItemStack.EMPTY){
	 		int damage = this.getStackInSlot(slot).getItemDamage() + step;
	 		this.getStackInSlot(slot).setItemDamage(damage);
	 		if(damage >= this.getStackInSlot(slot).getMaxDamage()){
	 			this.getStackInSlot(slot).shrink(1);
	 		}
	 		if(this.getStackInSlot(slot).getCount() <= 0){
	 			this.setStackInSlot(slot, ItemStack.EMPTY);
	 		}
	 	}
	 }



	 /**
	  * attempt to damage a slot with UNBREAKING enchantment
	  * 
	  * @param level
	  * @param slot
	  */
	 public void damageUnbreakingSlot(int level, int slot){
		if(this.rand.nextInt(1 + level) == 0){
			damageSlot(slot);
		}
	 }



	/**
	 * Repair the stack in the slot
	 * 
	 * @param slot
	 */	
	 public void repairMendingSlot(int slot) {
		if(!this.getStackInSlot(slot).isEmpty()){
			int damage = this.getStackInSlot(slot).getItemDamage();
			if(damage > 0){
				this.getStackInSlot(slot).setItemDamage(damage - 1);
			}
		}
	}



/*
 * ------------------------------------------------------
 * 						FLUIDS
 * ------------------------------------------------------
 */

	/**
	 * Check if can insert a new fluid or fill the existing one
	 * 
	 * @param tank
	 * @param tankFluid
	 * @param insertingFluid
	 * @return
	 */
	public boolean canSetOrFillFluid(FluidTank tank, FluidStack tankFluid, FluidStack insertingFluid) {
		return insertingFluid != null && ((tankFluid == null && insertingFluid.amount <= tank.getCapacity()) || canFillFluid(tank, tankFluid, insertingFluid));
	}

	/**
	 * Check if can add the specific amount to the existing fluid
	 * 
	 * @param tank
	 * @param tankFluid
	 * @param insertingFluid
	 * @return
	 */
	public boolean canFillFluid(FluidTank tank, FluidStack tankFluid, FluidStack insertingFluid){
		return tankFluid != null && tankFluid.isFluidEqual(insertingFluid) && tankFluid.amount + insertingFluid.amount <= tank.getCapacity();
	}

	/**
	 * Check if can insert a new fluid or fill the existing one with a specific amount
	 * 
	 * @param tank
	 * @param tankFluid
	 * @param insertingFluid
	 * @param amount
	 * @return
	 */
	public boolean canSetOrAddFluid(FluidTank tank, FluidStack tankFluid, FluidStack insertingFluid, int amount) {
		return insertingFluid != null && ((tankFluid == null && amount <= tank.getCapacity()) || canAddFluid(tank, tankFluid, insertingFluid, amount));
	}

	/**
	 * Check if can add the specific amount to the existing fluid by custom amount
	 * 
	 * @param tank
	 * @param tankFluid
	 * @param insertingFluid
	 * @param amount 
	 * @return
	 */
	public boolean canAddFluid(FluidTank tank, FluidStack tankFluid, FluidStack insertingFluid, int amount){
		return tankFluid != null && tankFluid.isFluidEqual(insertingFluid) && tankFluid.amount + amount <= tank.getCapacity();
	}

	/**
	 * Check if is possible to drain the requested fluid from the stored one
	 * 
	 * @param tankFluid
	 * @param requestedFluid
	 * @return
	 */
	public boolean canDrainFluid(FluidStack tankFluid, FluidStack requestedFluid){
		return tankFluid != null && requestedFluid != null && tankFluid.isFluidEqual(requestedFluid) && tankFluid.amount >= requestedFluid.amount;
	}

	/**
	 * Check if is possible to drain the requested fluid from the stored one with specific amount, ignoring fluidAmount()
	 * 
	 * @param tankFluid
	 * @param requestedFluid
	 * @return
	 */
	public boolean canDrainFluid(FluidStack tankFluid, FluidStack requestedFluid, int amount){
		return tankFluid != null && requestedFluid != null && tankFluid.isFluidEqual(requestedFluid) && tankFluid.amount >= amount;
	}

	/**
	 * Remove a specific fluid amount. Can clean the tank or leave the fluid reservation
	 * 
	 * @param tank
	 * @param amount
	 * @param doClean
	 */
	public void drainOrCleanFluid(FluidTank tank, int amount, boolean doClean){
		tank.getFluid().amount -= amount;
		if (doClean && tank.getFluidAmount() <= 0) {
			tank.setFluid(null);
		}
	}

	/**
	 * Check if a tank has any fluid inside
	 * 
	 * @param tank
	 * @return
	 */
	public boolean tankHasFluid(FluidTank tank) {
		return tank.getFluid() != null && tank.getFluidAmount() > 0;
	}

	/**
	 * Check if a tank has at least a need amount of any fluid
	 * 
	 * @param tank
	 * @param amount
	 * @return
	 */
	public boolean tankHasFluid(FluidTank tank, int amount) {
		return tank.getFluid() != null && tank.getFluidAmount() >= amount;
	}
	
	/**
	 * if canSetOrFillFluid, do the task
	 * 
	 * @param tank
	 * @param insertingFluid
	 */
	public void setOrFillFluid(FluidTank tank, FluidStack insertingFluid){
		if(tank.getFluid() != null){
			tank.getFluid().amount += insertingFluid.amount;
		}else{
			tank.setFluid(insertingFluid);
		}
	}

	/**
	 * if canSetOrFillFluid, do the task with specific amount
	 * 
	 * @param tank
	 * @param insertingFluid
	 * @param amount
	 */
	public void setOrFillFluid(FluidTank tank, FluidStack insertingFluid, int amount){
		if(tank.getFluid() != null){
			tank.getFluid().amount += amount;
		}else{
			tank.setFluid(new FluidStack(insertingFluid.getFluid(), amount));
		}
	}
}