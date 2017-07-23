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

	public boolean canSetOrStack(ItemStack stackInSlot, ItemStack recipeOutput) {
		return (stackInSlot == null || (stackInSlot != null && stackInSlot.isItemEqual(recipeOutput) && stackInSlot.stackSize <= stackInSlot.getMaxStackSize() - recipeOutput.stackSize));
	}

	public boolean canStack(ItemStack stackInSlot, ItemStack recipeOutput) {
		return (stackInSlot != null && stackInSlot.isItemEqual(recipeOutput) && stackInSlot.stackSize <= stackInSlot.getMaxStackSize() - recipeOutput.stackSize);
	}

	public void setOrStack(int slot, ItemStack stackToSet){
		if(this.getStackInSlot(slot) == null){
			this.setStackInSlot(slot, stackToSet);
		}else{
			for(int x = 0; x < stackToSet.stackSize; x++){
				incrementSlot(slot);
			}
		}
	}

	public void incrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		if(temp.stackSize + 1 <= temp.getMaxStackSize()){
			temp.stackSize++;
		}
		this.setStackInSlot(slot, temp);
	}
	
	public void setOrIncrement(int slot, ItemStack stackToSet){
		if(this.getStackInSlot(slot) == null){
			this.setStackInSlot(slot, stackToSet);
		}else{
			incrementSlot(slot);
		}
	}

	public void decrementSlot(int slot){
		ItemStack temp = this.getStackInSlot(slot);
		temp.stackSize--;
		if(temp.stackSize <= 0){
			this.setStackInSlot(slot, null);
		}else{
			this.setStackInSlot(slot, temp);
		}
	}

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
	
	public boolean canSetOrFill(FluidTank tank, FluidStack tankFluid, FluidStack recipeFluid) {
		return recipeFluid != null
			&& tankFluid == null || (tankFluid != null && tankFluid.isFluidEqual(recipeFluid) && tankFluid.amount <= tank.getCapacity() - recipeFluid.amount);
	}

	public boolean hasEnoughFluid(FluidStack tankFluid, FluidStack recipeFluid){
		return tankFluid != null && recipeFluid != null
			&& tankFluid.isFluidEqual(recipeFluid) 
			&& tankFluid.amount >= recipeFluid.amount;
	}
	
	public void drainOrClean(FluidTank tank, int amount, boolean doClean){
		tank.getFluid().amount -= amount;
		if (doClean && tank.getFluidAmount() <= 0) {
			tank.setFluid(null);
		}
	}

}