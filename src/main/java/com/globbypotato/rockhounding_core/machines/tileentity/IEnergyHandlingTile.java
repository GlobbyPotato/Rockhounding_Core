package com.globbypotato.rockhounding_core.machines.tileentity;

import net.minecraftforge.energy.IEnergyStorage;

public interface IEnergyHandlingTile extends IEnergyStorage{

	abstract int getRedstone();
	abstract int getRedstoneMax();
	abstract int getChargeMax();
	abstract int getYeld();
	abstract int getYeldMax();

	abstract boolean hasRF();
	abstract boolean canInduct();

	public default boolean hasRedstone(){
		return getRedstone() > 0;
	}

	public default boolean isFullRedstone(){
		return getRedstone() >= getRedstoneMax();
	}

	public default boolean hasCharge(){
		return getRedstone() > 0;
	}

	public default boolean isFullCharge(){
		return getRedstone() >= getChargeMax();
	}



	//---------------- FORGE ----------------
	@Override
	public default int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public default int getEnergyStored() {
		return this.getRedstone();
	}

	@Override
	public default int getMaxEnergyStored() {
		return this.getRedstoneMax();
	}

	@Override
	public default boolean canExtract() {
		return false;
	}

	@Override
	public default boolean canReceive() {
		return true;
	}

}
