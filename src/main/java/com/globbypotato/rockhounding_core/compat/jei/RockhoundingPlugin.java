package com.globbypotato.rockhounding_core.compat.jei;

import com.globbypotato.rockhounding_core.utils.CoreBasics;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class RockhoundingPlugin implements IModPlugin{

	public static IJeiHelpers jeiHelpers;

	@Override
	public void register(IModRegistry registry) {

		jeiHelpers = registry.getJeiHelpers();

		registry.addIngredientInfo(CoreBasics.heat_inductor, ItemStack.class, "This item must be placed in the fuel slot of any Rockhounding tech machine that is enabled to accept fuel. It will allow to refill the power bar by converting the incoming RF. From the configuration file, it can be set to be moved through machines, or it can be set as a permanent upgrade for a single machine. It also has an option to make the fuel slot no longer accept common fuel once a permanent induction has been enabled. The recipe depends on the presence of the Chemistry module in the pack, so two variants are available.");
		registry.addIngredientInfo(CoreBasics.gas_turbine, ItemStack.class, "This item must be placed in the redstone slot of the Rockhounding Power Station. It allows to convert Syngas into RF.");

//		IIngredientBlacklist itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
	}
}