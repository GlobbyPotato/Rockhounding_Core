package com.globbypotato.rockhounding_core.handlers;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ModConfig {
	public static final String CATEGORY_INDUCTION = "Induction";

	public static boolean enableRainRefill;

	public static boolean enablePermanentInduction;
	public static int RFToFuelFactor;

	public static void loadConfig(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		enablePermanentInduction = config.get(			CATEGORY_INDUCTION, "Permanent Induction Enabler",		true,	"Make the Induction Heating Interface a machine permanent upgrade").getBoolean();
		RFToFuelFactor = config.get(					CATEGORY_INDUCTION, "RF To Fuel Factor",				1,		"How many fuel ticks 1RF can produce").getInt();

        if (config.hasChanged()) {
        	config.save();
        }

	}
}