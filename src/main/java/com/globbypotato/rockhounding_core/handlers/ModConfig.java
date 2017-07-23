package com.globbypotato.rockhounding_core.handlers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	public static final String CATEGORY_TOOLS = "Tools";
	public static final String CATEGORY_FUEL = "Fuel";

	public static boolean allowInductor;

	public static boolean enableRainRefill;

	public static boolean enableFuelBlend;
	public static int fuelBlendPower;
	public static int fuelBlendQuantity;

	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		allowInductor = config.get(									CATEGORY_TOOLS, "PermanentInduction",		true,	"Make the Induction Heating Interface a machine permanent upgrade").getBoolean();

		enableFuelBlend = config.get(								CATEGORY_FUEL, "EnableFuelBlend",			false,	"Replaces regular fueling with a customized fuel").getBoolean();
		fuelBlendPower = config.get(								CATEGORY_FUEL, "FuelBlendPower",			600,	"The burntime of each fuel blend item").getInt();
		fuelBlendQuantity = config.get(								CATEGORY_FUEL, "FuelBlendQuantity",			9,		"The amount of produced fuel blend per craft").getInt();

        if (config.hasChanged()) {
        	config.save();
        }

	}
}