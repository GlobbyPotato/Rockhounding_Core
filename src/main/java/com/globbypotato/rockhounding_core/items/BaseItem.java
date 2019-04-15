package com.globbypotato.rockhounding_core.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItem extends Item {

	public BaseItem(String modid, String name){
		super();
		setRegistryName(new ResourceLocation(modid, name));
		setUnlocalizedName(this.getRegistryName().toString());
	}
	
	@SideOnly(Side.CLIENT)
	public void initModel() {
    	ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
}