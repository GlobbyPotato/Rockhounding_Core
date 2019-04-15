package com.globbypotato.rockhounding_core;

import com.globbypotato.rockhounding_core.handlers.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Reference.MODID)
public class CoreBlocks {

	@Mod.EventBusSubscriber(modid = Reference.MODID)
	public static class RegistrationHandler {

	}

}