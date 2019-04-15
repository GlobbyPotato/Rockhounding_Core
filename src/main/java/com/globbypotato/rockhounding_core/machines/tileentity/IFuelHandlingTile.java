package com.globbypotato.rockhounding_core.machines.tileentity;

public interface IFuelHandlingTile {

	abstract int getPower();
	abstract int getPowerMax();

	public default boolean hasPower(){
		return getPower() > 0;
	}

	public default boolean isFullPower(){
		return getPower() >= getPowerMax();
	}

}