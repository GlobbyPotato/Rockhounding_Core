package com.globbypotato.rockhounding_core;

import com.globbypotato.rockhounding_core.handlers.Reference;
import com.globbypotato.rockhounding_core.handlers.RegistryHandler;
import com.globbypotato.rockhounding_core.items.io.UtilIO;

import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(Reference.MODID)
public class CoreItems {

	// initialize the item
	public static Item MOD_WRENCH;

	@Mod.EventBusSubscriber(modid = Reference.MODID)
	public static class RegistrationHandler {

		// initialize the item
		public static void initItems(){
			MOD_WRENCH = new UtilIO("mod_wrench");
		}

		// register the item
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			initItems();

			final IForgeRegistry<Item> registry = event.getRegistry();
			registry.register(MOD_WRENCH);
		}

		// register the model
		/**
		 * 
		 * @param event
		 */
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event){
			RegistryHandler.registerSingleModel(MOD_WRENCH);
		}

	}


}