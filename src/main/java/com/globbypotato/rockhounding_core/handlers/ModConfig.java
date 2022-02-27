package com.globbypotato.rockhounding_core.handlers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	public static final String CATEGORY_INDUCTION = "Induction";
	public static final String CATEGORY_BLEND = "Blend";

	public static boolean enableRainRefill;

	public static boolean enablePermanentInduction;
	public static int RFToFuelFactor;

	public static boolean enableFuelBlend;
	public static int fuelBlendPower;
	public static boolean enableGatedFuel;

	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		enablePermanentInduction = config.get(			CATEGORY_INDUCTION, "Permanent Induction Enabler",		true,	"Make the Induction Heating Interface a machine permanent upgrade").getBoolean();
		RFToFuelFactor = config.get(					CATEGORY_INDUCTION, "RF To Fuel Factor",				1,		"How many fuel ticks 1RF can produce").getInt();
		enableGatedFuel = config.get(					CATEGORY_INDUCTION, "Gated Fuel Enabler",				false,	"Fuel/blend will be no longer accepted if a permanent induction is activated").getBoolean();

		enableFuelBlend = config.get(					CATEGORY_BLEND, "Fuel Blend Enabler",					false,	"Replaces regular fueling with a customized fuel blend").getBoolean();
		fuelBlendPower = config.get(					CATEGORY_BLEND, "Fuel Blend Power",						800,	"The burn time of each fuel blend item").getInt();

        if (config.hasChanged()) {
        	config.save();
        }

	}
}