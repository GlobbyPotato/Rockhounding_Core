package com.globbypotato.rockhounding_core.utils;

import com.globbypotato.rockhounding_core.CoreItems;
import com.globbypotato.rockhounding_core.handlers.ModConfig;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;

public class CoreUtils {

	public static ItemStack inductor = new ItemStack(CoreItems.heat_inductor);
	public static ItemStack blend = new ItemStack(CoreItems.fuel_blend);

	public static boolean hasConsumable(ItemStack consumable, ItemStack insertingStack) {
		return insertingStack != null 
			&& ItemStack.areItemsEqualIgnoreDurability(consumable, insertingStack)
			&& insertingStack.getItemDamage() < insertingStack.getMaxDamage();
	}

	public static boolean hasConsumable(ItemStack consumable, ItemStack insertingStack, int step) {
		return insertingStack != null 
			&& ItemStack.areItemsEqualIgnoreDurability(consumable, insertingStack)
			&& insertingStack.getItemDamage() <= insertingStack.getMaxDamage() - step;
	}

	public static boolean isBucketType(ItemStack insertingStack) {
		return insertingStack != null 
			&& (insertingStack.getItem() instanceof ItemBucket || insertingStack.getItem() instanceof UniversalBucket);
	}

	public static ItemStack getFluidBucket(Fluid fluid) {
		return UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluid);
	}

	public static boolean isEmptyBucket(ItemStack insertingStack) {
		return insertingStack.getItem() == Items.BUCKET || (insertingStack.getItem() instanceof UniversalBucket	& FluidUtil.getFluidContained(insertingStack).containsFluid(null));
	}

	public static boolean hasInductor(ItemStack insertingStack) {
		return insertingStack != null && (ItemStack.areItemsEqual(insertingStack, inductor));
	}

	public static boolean hasBlend(ItemStack insertingStack) {
		return insertingStack != null && (ItemStack.areItemsEqual(insertingStack, blend));
	}

	public static boolean isPowerSource(ItemStack insertingStack){
		return (!ModConfig.enableFuelBlend && FuelUtils.isItemFuel(insertingStack)) 
			|| CoreUtils.hasInductor(insertingStack)
			|| (ModConfig.enableFuelBlend && CoreUtils.hasBlend(insertingStack));
	}
}