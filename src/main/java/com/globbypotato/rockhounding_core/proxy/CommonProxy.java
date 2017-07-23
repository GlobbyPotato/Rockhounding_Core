package com.globbypotato.rockhounding_core.proxy;

import com.globbypotato.rockhounding_core.CoreItems;
import com.globbypotato.rockhounding_core.handlers.CoreRecipes;
import com.globbypotato.rockhounding_core.handlers.ModConfig;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		// Load Config
		ModConfig.loadConfig(e);

		// Register Contents
		CoreItems.init();
	}

	public void init(FMLInitializationEvent e){
		// Register Recipes
		CoreRecipes.init();
	}

	public void postInit(FMLPostInitializationEvent e){}

	public void registerTileEntitySpecialRenderer() {}

	public void registerRenderInformation() {}

}