package com.globbypotato.rockhounding_core.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumFluidNbt implements IStringSerializable {
	SULF("Sulf"),
	CHLO("Chlo"),
	FLUO("Fluo"),
	NITR("Nitr"),
	PHOS("Phos"),
	CYAN("Cyan"),
	SOLVENT("Solvent"),
	REAGENT("Reagent"),
	SOLUTION("Solution"),
	BYPRODUCT("Byproduct"),
	BATH("Bath"),
	ACID("Acid"),
	FLUID("Fluid"),
	LAVA("Lava"),
	WATER("Water"),
	GAS("Gas"),
	COMBUSTIBLE("Combustible"),
	FLUID_IN("FluidIn"),
	FLUID_OUT("FluidOut"),
	GAS_IN("GasIn"),
	GAS_OUT("GasOut");

	private String nameTag;
	private EnumFluidNbt(String nameTag) {
		this.nameTag = nameTag;
	}

	@Override
	public String getName() {
		return toString().toLowerCase();
	}

	public static int size(){
		return values().length;
	}

	public String nameTag(){
		return this.nameTag;
	}

}