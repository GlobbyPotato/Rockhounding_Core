package com.globbypotato.rockhounding_core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BaseBlock extends Block {

	public BaseBlock(String modid, String name, Material material) {
		super(material);
		setRegistryName(new ResourceLocation(modid, name));
		setUnlocalizedName(getRegistryName().toString());
	}

}