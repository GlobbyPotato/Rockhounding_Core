package com.globbypotato.rockhounding_core.machines.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class MachineStackHandler extends ItemStackHandler{

	TileEntityMachineInv tile;
	public MachineStackHandler(int slots,TileEntityMachineInv tile) {
		super(slots);
		this.tile = tile;
	}

	/**
	 * Check if can insert the new stack in the slot or add an amount to the existing one 
	 * 
	 * @param stackInSlot
	 * @param recipeOutput
	 * @return
	 */
	public boolean canSetOrStack(ItemStack stackInSlot, ItemStack recipeOutput) {
		return stackInSlot == null || canStack(stackInSlot, recipeOutput);
	}

	/**
	 * Check if can add an amount to the existing one 
	 * 
	 * @param stackInSlot
	 * @param recipeOutput
	 * @return
	 */
	public boolean canStack(ItemStack stackInSlot, ItemStack recipeOutput) {
		return stackInSlot != null && stackInSlot.isItemEqual(recipeOutput) && stackInSlot.stackSize <= stackInSlot.getMaxStackSize() - recipeOutput.stackSize;
	}

	/**
	 * Insert a new stack or add an amount to the existing one
	 * 
	 * @param slot
	 * @param stackToSet
	 */
	public void setOrStack(int slot, ItemStack stackToSet){
		if(this.getStackInSlot(slot) == null){
			this.setStackInSlot(slot, stackToSet);
		}else{
			for(int x = 0; x < stackToSet.stackSize; x++){
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
		return stack != null && stack.stackSize < stack.getMaxStackSize();
	}

	/**
	 * Increase by 1 the current slot
	 * 
	 * @param slot
	 */
	public void incrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		if(temp.stackSize + 1 <= temp.getMaxStackSize()){
			temp.stackSize++;
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
		int adding = Math.min(temp.getMaxStackSize() - temp.stackSize, num);
		temp.stackSize += adding;
		this.setStackInSlot(slot, temp);
	}

	/**
	 * Insert a new stack or increment the existing one
	 * 
	 * @param slot
	 * @param stackToSet
	 */
	public void setOrIncrement(int slot, ItemStack stackToSet){
		if(this.getStackInSlot(slot) == null){
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
		temp.stackSize--;
		if(temp.stackSize <= 0){
			this.setStackInSlot(slot, null);
		}else{
			this.setStackInSlot(slot, temp);
		}
	}

	/**
	 * Decrease the stack in slot by an amount
	 * 
	 * @param slot
	 * @param num
	 */
	public void decrementSlotBy(int slot, int num){
		ItemStack temp = this.getStackInSlot(slot);
		int taking = Math.min(temp.stackSize, num);
		temp.stackSize -= taking;
		if(temp.stackSize <= 0){
			this.setStackInSlot(slot, null);
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
		if(this.getStackInSlot(slot) != null){
			int damage = this.getStackInSlot(slot).getItemDamage() + 1;
			this.getStackInSlot(slot).setItemDamage(damage);
			if(damage >= this.getStackInSlot(slot).getMaxDamage()){
				this.getStackInSlot(slot).stackSize--;
			}
			if(this.getStackInSlot(slot).stackSize <= 0){
				this.setStackInSlot(slot, null);
			}
		}
	}

	/**
	 * Check if can insert a new fluid or fill the existing one
	 * 
	 * @param tank
	 * @param tankFluid
	 * @param recipeFluid
	 * @return
	 */
	public boolean canSetOrFill(FluidTank tank, FluidStack tankFluid, FluidStack recipeFluid) {
		return recipeFluid != null && (tankFluid == null || canFillTank(tank, tankFluid, recipeFluid));
	}

	/**
	 * Check if can add the specific amount to the existing fluid
	 * 
	 * @param tank
	 * @param tankFluid
	 * @param recipeFluid
	 * @return
	 */
	public boolean canFillTank(FluidTank tank, FluidStack tankFluid, FluidStack recipeFluid){
		return tankFluid != null && tankFluid.isFluidEqual(recipeFluid) && tankFluid.amount <= tank.getCapacity() - recipeFluid.amount;
	}

	/**
	 * Check if there is enough fluid amount than requested
	 * 
	 * @param tankFluid
	 * @param recipeFluid
	 * @return
	 */
	public boolean hasEnoughFluid(FluidStack tankFluid, FluidStack recipeFluid){
		return tankFluid != null && recipeFluid != null
			&& tankFluid.isFluidEqual(recipeFluid) 
			&& tankFluid.amount >= recipeFluid.amount;
	}

	/**
	 * Remove a specific fluid amount and/or clean the tank
	 * 
	 * @param tank
	 * @param amount
	 * @param doClean
	 */
	public void drainOrClean(FluidTank tank, int amount, boolean doClean){
		tank.getFluid().amount -= amount;
		if (doClean && tank.getFluidAmount() <= 0) {
			tank.setFluid(null);
		}
	}

}