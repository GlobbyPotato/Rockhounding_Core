package com.globbypotato.rockhounding_core;

import com.globbypotato.rockhounding_core.handlers.Reference;
import com.globbypotato.rockhounding_core.items.BaseUtil;
import com.globbypotato.rockhounding_core.items.FuelBlend;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class CoreItems {

	public static Item heat_inductor;
	public static Item fuel_blend;

	public static void init(){
		heat_inductor = new BaseUtil("heat_inductor").setCreativeTab(Reference.RockhoundingCore);
		fuel_blend = new FuelBlend("fuel_blend");
	}

	public static void initClient(){
		//items
		registerSimpleItemRender(heat_inductor, 0, "heat_inductor");
		registerSimpleItemRender(fuel_blend, 0, "fuel_blend");
	}
	
	//render meta item
	public static void registerMetaItemRender(Item item, int meta, String fileName){
		ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName() + "_" + fileName, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}
	//render simple item
	public static void registerSimpleItemRender(Item item, int meta, String fileName){
		ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}
}