package com.globbypotato.rockhounding_core.handlers;

import com.globbypotato.rockhounding_core.CoreItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Reference {
	// Create Mod Reference 
	public static final String MODID = "rockhounding_core";
    public static final String NAME = "Rockhounding Mod: Core";
    public static final String VERSION = "${version_mod}";
	public static final String CLIENT_PROXY_CLASS = "com.globbypotato.rockhounding_core.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.globbypotato.rockhounding_core.proxy.CommonProxy";

	//Create new Creative Tab with Icon
	public static CreativeTabs RockhoundingCore = new CreativeTabs("rockhoundingCore") {
		public Item getTabIconItem() { 
			return CoreItems.heat_inductor; 
		}
	};

}