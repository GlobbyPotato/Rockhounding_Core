package com.globbypotato.rockhounding_core.utils;

import com.globbypotato.rockhounding_core.CoreItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;

public class CoreBasics {
	
	public static String rh_chemistry_id = "rockhounding_chemistry";
	public static boolean chemistryLoaded(){return Loader.isModLoaded(rh_chemistry_id);}

	// compose the itemstack
	public static ItemStack mod_wrench = new ItemStack(CoreItems.MOD_WRENCH);
	public static FluidStack waterStack(int amount) {return new FluidStack(FluidRegistry.WATER, amount);}
	public static FluidStack lavaStack(int amount) {return new FluidStack(FluidRegistry.LAVA, amount);}
	public static FluidStack gasStack(int amount) {return new FluidStack(FluidRegistry.getFluid("syngas"), amount);}

}