package com.globbypotato.rockhounding_core.compat.jei;

import com.globbypotato.rockhounding_core.CoreItems;
import com.globbypotato.rockhounding_core.handlers.ModConfig;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class RockhoundingPlugin extends BlankModPlugin{

	public static IJeiHelpers jeiHelpers;

	@Override
	public void register(IModRegistry registry) {

		jeiHelpers = registry.getJeiHelpers();

		registry.addDescription(new ItemStack(CoreItems.heat_inductor), "This item must be placed in the fuel slot of any Rockhounding tech machine that is enabled to accept fuel. It will allow to refill the power bar by converting the incoming RF. From the configuration file, it can be set to be moved through machines, or it can be set as a permanent upgrade for a single machine. By default, this item can be crafted only from the Chemistry module, since it uses specific alloys, though, if Chemistry is not installed, it can be still used if a Rockhounding machine can accept it. In this case the player needs to inject a custom recipe of his choise to make it craftable again.");
		registry.addDescription(new ItemStack(CoreItems.fuel_blend), "This is a customizable fuel blend. It can be used only in the Rockhounding machines, if the option is enabled. It will replace the regular fuel and can be customized from the config file with power and quantity.");

		IIngredientBlacklist itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
		if(!ModConfig.enableFuelBlend){
			itemBlacklist.addIngredientToBlacklist(new ItemStack(CoreItems.fuel_blend));
		}

	}
}