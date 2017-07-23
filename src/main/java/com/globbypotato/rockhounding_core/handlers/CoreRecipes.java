package com.globbypotato.rockhounding_core.handlers;

import com.globbypotato.rockhounding_core.CoreItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CoreRecipes {

	public static void init() {
		//inductor
		GameRegistry.addRecipe(new ShapedOreRecipe(CoreItems.heat_inductor, new Object[] { "III", "HHH", "N N", 'I', "ingotIron", 'H', "itemNichromeHeater", 'N', "nuggetIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(CoreItems.fuel_blend, ModConfig.fuelBlendQuantity), new Object[] { "RRR", "CGC", "RRR", 'R', "dustRedstone", 'C', Items.COAL, 'G', "dustGlowstone"}));
	}
}