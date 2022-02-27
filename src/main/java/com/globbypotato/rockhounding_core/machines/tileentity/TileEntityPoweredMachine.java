package com.globbypotato.rockhounding_core.machines.tileentity;

import com.globbypotato.rockhounding_core.handlers.ModConfig;
import com.globbypotato.rockhounding_core.utils.CoreBasics;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.FuelUtils;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;

public abstract class TileEntityPoweredMachine extends TileEntityFueledMachine implements IEnergyHandlingTile {

	public int redstoneCount = 0;
	public int redstoneMax = 64000;
	public int yeldCount = 0;
	public int yeldMax = 9000;
	public int chargeCount = 0;
	public int chargeMax = 1000000;
	public int redstoneFactor = 300;

    public final EnergyStorage storage = new EnergyStorage(rfTransfer());

	public boolean permanentInductor;

	public TileEntityPoweredMachine(int inputSlots, int outputSlots, int templateSlots, int upgradeSlots) {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);
	}



	//---------------- HANDLER ----------------
	/**
	 * @return override if the machine uses RF
	 */
	@Override
	public boolean hasRF() { 
		return false; 
	}

	/**
	 * @return override if the RF is not required with blend
	 */
	protected boolean isRFGatedByBlend(){
		return false;
	}

	public int getRFConsume(){
		return this.redstoneFactor;
	}



	//---------------- INDUCTION ----------------
	@Override
	public boolean canInduct() {
		return hasPermanentInduction() || (!hasPermanentInduction() && hasValidInductor());
	}

	private boolean hasValidInductor() {
		return this.input.getSlots() > 0 
			&& fuelID() > -1 
			&& !this.input.getStackInSlot(fuelID()).isEmpty() 
			&& this.input.getStackInSlot(fuelID()).isItemEqual(CoreBasics.heat_inductor);
	}


	public boolean hasPermanentInduction() { 
		return allowPermanentInduction() && isInductionActive(); 
	}

	public boolean isInductionActive(){
		return this.permanentInductor;
	}

	public boolean allowPermanentInduction(){
		return ModConfig.enablePermanentInduction;
	}

	public boolean enableGatedFuel(){
		return ModConfig.enableGatedFuel;
	}



	//---------------- FUEL ----------------
	public void powerHandler(ItemStack stack) {
		if(fuelID() > -1 && !stack.isEmpty()) {
			if(allowPermanentInduction() && !isInductionActive() && ItemStack.areItemsEqual(stack, CoreBasics.heat_inductor)){
				this.permanentInductor = true;
				this.input.setStackInSlot(fuelID(), ItemStack.EMPTY);
			}
		}
	}

	@Override
	public boolean isGatedPowerSource(ItemStack insertingStack){
		return (!hasFuelBlend() && FuelUtils.isItemFuel(insertingStack) && !isFuelGated()) 
			|| CoreUtils.hasInductor(insertingStack)
			|| (hasFuelBlend() && CoreUtils.hasBlend(insertingStack) && !isFuelGated());
	}

	public boolean isFuelGated() {
		return allowPermanentInduction() && isInductionActive() && enableGatedFuel();
	}



	//---------------- REDSTONE ----------------
	public boolean hasRedstone(ItemStack insertingStack) {
		return !hasFuelBlend()
			&& !insertingStack.isEmpty() 
			&& (insertingStack.getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK) || insertingStack.getItem() == Items.REDSTONE || insertingStack.isItemEqual(CoreBasics.gas_turbine));
	}

	protected void redstoneHandler(int slot, int cooktime) {
		ItemStack stack = this.input.getStackInSlot(slot);
		if(!stack.isEmpty()){
			if(stack.getItem() == Items.REDSTONE && this.getRedstone() <= (this.getRedstoneMax() - cooktime)){
				burnRedstone(slot, stack, cooktime);
			}else if(stack.getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK) && this.getRedstone() <= (this.getRedstoneMax() - (cooktime * 9))){
				burnRedstone(slot, stack, cooktime * 9);
			}
		}
	}

	private void burnRedstone(int slot, ItemStack stack, int charge) {
		this.redstoneCount += charge; 
		stack.shrink(1);
		if(stack.getCount() <= 0){
			this.input.setStackInSlot(slot, ItemStack.EMPTY);
		}
	}

	public boolean isRedstoneRequired(int amount){
		return hasFuelBlend() && isRFGatedByBlend() ? true : this.getRedstone() >= amount;
	}

	public boolean isRedstoneRefilled(){
		return hasFuelBlend() && isRFGatedByBlend() ? true : isFullRedstone();
	}

	public boolean redstoneIsRefillable() {
		return hasRF() && (!hasFuelBlend() || (hasFuelBlend() && !isRFGatedByBlend()));
	}

	public boolean canRefillOnlyPower() {
		return canInduct() && !hasRF();
	}

	public boolean isRedstoneFilled() {
		return canInduct() && hasRF();
	}



	//---------------- I/O ----------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.redstoneCount = compound.getInteger("RedstoneCount");
		this.permanentInductor = compound.getBoolean("Inductor");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("RedstoneCount", getRedstone());
		compound.setBoolean("Inductor", isInductionActive());

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



	//---------------- FORGE ----------------
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return this.storage.receiveEnergy(maxReceive, simulate);
	}



	//---------------- VARIABLES ----------------
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

	public int rfTransfer(){
    	return 2000;
    }

	public int getRFToFuel() {
		return getRedstone() * ModConfig.RFToFuelFactor;
	}



	//---------------- PROCESS ----------------
	public void injectEnergy(){
		int energyReceived = 0;
		if(redstoneIsRefillable()){
			if(!isFullRedstone()){
		        energyReceived = Math.min(this.getRedstoneMax() - this.getRedstone(), this.getRedstone());
	        	this.redstoneCount += energyReceived;
	        	this.storage.extractEnergy(energyReceived, false);
				this.markDirtyClient();
			}else{
				this.redstoneCount = this.getRedstoneMax();
				this.markDirtyClient();
			}
		}
	}

	public void injectFuel(){
		int energyReceived = 0;
		if(isRedstoneFilled() || canRefillOnlyPower() ){
	        if(!isFullPower()){
		        energyReceived = Math.min(this.getPowerMax() - this.getPower(), this.getRFToFuel());
		        this.powerCount += energyReceived;
	        	this.storage.extractEnergy(energyReceived, false);
				this.markDirtyClient();
	        }else{
				this.powerCount = this.getPowerMax();
				this.markDirtyClient();
			}
		}
	}

	public void sendEnergy(TileEntity checkTile, EnumFacing facing){
		int possibleEnergy = 0;
		if(checkTile.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite()) && checkTile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).canReceive()){
			possibleEnergy = checkTile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).receiveEnergy(getRedstone(), true);
			if(possibleEnergy > 0){
				checkTile.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite()).receiveEnergy(possibleEnergy, false);
				this.redstoneCount -= possibleEnergy;
			}
		}
	}

}