package com.globbypotato.rockhounding_core.handlers;

import com.globbypotato.rockhounding_core.CoreItems;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import rockhounding.api.IReciperBase;

@Mod.EventBusSubscriber
public class CoreRecipes {

	@SubscribeEvent
	public static void registerRecipes(final RegistryEvent.Register<IRecipe> event){
		//inductor
		if(IReciperBase.chemistryLoaded()){
			GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID, "heat_inductor"), new ResourceLocation(Reference.MODID, "utils"), CoreBasics.heat_inductor, new Object[] { "III", "HHH", "N N", 'I', "ingotIron", 'H', "itemNichromeHeater", 'N', "nuggetIron"});
		}else{
			GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID, "heat_inductor"), new ResourceLocation(Reference.MODID, "utils"), CoreBasics.heat_inductor, new Object[] { "III", "OBO", "N N", 'I', "ingotIron", 'B', "blockIron", 'N', "nuggetIron", 'O', "obsidian"});
		}
		//fuel blend
		if(ModConfig.enableFuelBlend){
			GameRegistry.addShapedRecipe(new ResourceLocation(Reference.MODID, "fuel_blend"), new ResourceLocation(Reference.MODID, "utils"), new ItemStack(CoreItems.FUEL_BLEND, 9), new Object[] { "RRR", "CGC", "RRR", 'R', "dustRedstone", 'C', new ItemStack(Items.COAL), 'G', "dustGlowstone"});
		}
	}
}