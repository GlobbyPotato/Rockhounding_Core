package com.globbypotato.rockhounding_core.proxy;

import com.globbypotato.rockhounding_core.handlers.ModConfig;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		// Load Config
		ModConfig.loadConfig(e);

	}

	/**
	 * 
	 * @param e
	 */
	public void init(FMLInitializationEvent e){}

	/**
	 * 
	 * @param e
	 */
	public void postInit(FMLPostInitializationEvent e){}

}