package com.globbypotato.rockhounding_core.utils;

import com.globbypotato.rockhounding_core.CoreItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class CoreBasics {
	public static String r_flux = "redstoneflux";
	// compose the itemstack
	public static ItemStack heat_inductor = new ItemStack(CoreItems.HEAT_INDUCTOR);
	public static ItemStack gas_turbine = new ItemStack(CoreItems.GAS_TURBINE);
	public static ItemStack mod_wrench = new ItemStack(CoreItems.MOD_WRENCH);
	public static FluidStack waterStack(int amount) {return new FluidStack(FluidRegistry.WATER, amount);}
	public static FluidStack lavaStack(int amount) {return new FluidStack(FluidRegistry.LAVA, amount);}
	public static FluidStack gasStack(int amount) {return new FluidStack(FluidRegistry.getFluid("syngas"), amount);}

}