package com.globbypotato.rockhounding_core.machines.tileentity;

import java.util.Random;

import com.globbypotato.rockhounding_core.CoreItems;
import com.globbypotato.rockhounding_core.handlers.ModConfig;
import com.globbypotato.rockhounding_core.utils.FuelUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public abstract class TileEntityMachineEnergy extends TileEntityMachineInv  implements IStorage {

	public Random rand = new Random();

	public int FUEL_SLOT;

	public int cookTime = 0;
	public int recipeIndex = -1;
	public boolean activation;

	public int powerCount = 0;
	public int powerMax = 32000;
	public int redstoneCount = 0;
	public int redstoneMax = 32000;
	public int yeldCount = 0;
	public int yeldMax = 900;
	public int chargeCount = 0;
	public int chargeMax = 1000000;

	public boolean permanentInductor;

	public TileEntityMachineEnergy(int inputSlots, int outputSlots, int fuelSlot) {
		super(inputSlots, outputSlots);
		this.FUEL_SLOT = fuelSlot;
	}

	//---------------- CUSTOM ----------------
	@Override
	public boolean hasRF() { 
		return false; 
	}

	@Override
	public int getGUIHeight() { 
		return 0; 
	}

	@Override
	public boolean canInduct() {
		return hasPermanentInduction() || (!hasPermanentInduction() && (INPUT_SLOTS > 0 && input.getStackInSlot(FUEL_SLOT) != null && input.getStackInSlot(FUEL_SLOT).isItemEqual(new ItemStack(CoreItems.heat_inductor))));
	}

	public boolean hasPermanentInduction() { 
		return ModConfig.allowInductor && permanentInductor; 
	}

	public boolean hasFuelBlend() { 
		return ModConfig.enableFuelBlend; 
	}

	protected void fuelHandler(ItemStack stack) {
		if(stack != null) {
			if(!hasFuelBlend() && FuelUtils.isItemFuel(stack) ){
				if( powerCount <= (powerMax - FuelUtils.getItemBurnTime(stack)) ){
					burnFuel(stack);
				}
			}else if(hasFuelBlend() && ItemStack.areItemsEqual(stack, new ItemStack(CoreItems.fuel_blend))){
				if( powerCount <= (powerMax - ModConfig.fuelBlendPower) ){
					burnBlend(stack);
				}
			}else if(ModConfig.allowInductor && !permanentInductor && ItemStack.areItemsEqual(stack, new ItemStack(CoreItems.heat_inductor))){
					permanentInductor = true;
					input.setStackInSlot(FUEL_SLOT, null);
			}
		}
	}

	protected void burnFuel(ItemStack stack) {
		powerCount += FuelUtils.getItemBurnTime(stack);
		stack.stackSize--;
		if(stack.stackSize <= 0){
			input.setStackInSlot(FUEL_SLOT, stack.getItem().getContainerItem(input.getStackInSlot(FUEL_SLOT)));
		}
	}

	protected void burnBlend(ItemStack stack) {
		powerCount += ModConfig.fuelBlendPower;
		stack.stackSize--;
		if(stack.stackSize <= 0){
			input.setStackInSlot(FUEL_SLOT, null);
		}
	}

	protected boolean hasRedstone(ItemStack insertingStack) {
		return !hasFuelBlend()
			&& insertingStack != null 
			&& (insertingStack.getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK) || insertingStack.getItem() == Items.REDSTONE);
	}

	protected void redstoneHandler(int slot, int cooktime) {
		ItemStack stack = input.getStackInSlot(slot);
		if(stack != null){
			if(stack.getItem() == Items.REDSTONE && redstoneCount <= (redstoneMax - cooktime)){
				burnRedstone(slot, stack, cooktime);
			}else if(stack.getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK) && redstoneCount <= (redstoneMax - (cooktime * 9))){
				burnRedstone(slot, stack, cooktime * 9);
			}
		}
	}

	private void burnRedstone(int slot, ItemStack stack, int charge) {
		redstoneCount += charge; 
		stack.stackSize--;
		if(stack.stackSize <= 0){
			input.setStackInSlot(slot, null);
		}
	}

	public boolean isRedstoneRequired(int amount){
		return hasFuelBlend() ? true : this.getRedstone() >= amount;
	}

	public boolean needsRedstoneRefill(){
		return hasFuelBlend() ? true : isFullRedstone();
	}



	//---------------- I/O ----------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.powerCount = compound.getInteger("PowerCount");
		this.redstoneCount = compound.getInteger("RedstoneCount");
		this.cookTime = compound.getInteger("CookTime");
		this.permanentInductor = compound.getBoolean("Inductor");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("PowerCount", this.powerCount);
		compound.setInteger("RedstoneCount", this.redstoneCount);
		compound.setInteger("CookTime", this.cookTime);
		compound.setBoolean("Inductor", this.permanentInductor);
		return compound;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if ((capability == CapabilityEnergy.ENERGY)) {
			return (T)CapabilityEnergy.ENERGY.cast(this);
		}
		return super.getCapability(capability, facing);
	}

	//---------------- COFH ----------------
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		return calculateEnergy(maxReceive, false);
	}

	//---------------- FORGE ----------------
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return calculateEnergy(maxReceive, false);
	}

	public int calculateEnergy(int maxReceive, boolean simulate){
		int energyReceived = 0;
		if(isRedstoneFilled() || canRefillOnlyPower() ){
	        energyReceived = Math.min(this.getPowerMax() - this.getPower(), Math.min(maxReceive, maxReceive));
	        if (!simulate){
	        	this.powerCount += energyReceived;
	        }
			if(isFullPower()){this.powerCount = this.powerMax;}
		}else if(redstoneIsRefillable()){
	        energyReceived = Math.min(this.getRedstoneMax() - this.getRedstone(), Math.min(maxReceive, maxReceive));
	        if (!simulate){
	        	this.redstoneCount += energyReceived;
	        }
			if(isFullRedstone()){this.redstoneCount = this.redstoneMax;}
		}
        return maxReceive > 0 ? energyReceived : 0;
	}

	public boolean redstoneIsRefillable() {
		return hasRF() && !hasFuelBlend();
	}

	public boolean canRefillOnlyPower() {
		return canInduct() && !hasRF();
	}

	public boolean isRedstoneFilled() {
		return canInduct() && hasRF() && needsRedstoneRefill();
	}



	//---------------- VARIABLES ----------------
	@Override
	public int getPower() { 	  return this.powerCount; }
	@Override
	public int getPowerMax() { 	  return this.powerMax; }
	@Override
	public int getRedstone() { 	  return this.redstoneCount; }
	@Override
	public int getRedstoneMax() { return this.redstoneMax; }
	@Override
	public int getChargeMax() {   return this.chargeMax; }
	@Override
	public int getYeld() { 	  	  return this.yeldCount; }
	@Override
	public int getYeldMax() {     return this.yeldMax; }



	//---------------- MISC ----------------
	public int rfTransfer(){
    	return 40;
    }

}