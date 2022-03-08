package com.globbypotato.rockhounding_core.compat.jei;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class RockhoundingPlugin implements IModPlugin{

	public static IJeiHelpers jeiHelpers;

	@Override
	public void register(IModRegistry registry) {

		jeiHelpers = registry.getJeiHelpers();

//		IIngredientBlacklist itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
	}
}