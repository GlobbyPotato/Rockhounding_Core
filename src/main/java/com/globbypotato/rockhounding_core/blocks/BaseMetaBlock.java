package com.globbypotato.rockhounding_core.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

public class BaseMetaBlock extends Block{
	public String[] array;

	protected BaseMetaBlock(String modid, String name, Material material, String[]array) {
		super(material);
		setRegistryName(new ResourceLocation(modid, name));
		setUnlocalizedName(getRegistryName().toString());
		this.array = array;
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Item.getItemFromBlock(this);
	}

	@Override
    public int damageDropped(IBlockState state){
    	return getMetaFromState(state);
    }

	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items){
        for (int i = 0; i < this.array.length; ++i){
            items.add(new ItemStack(this, 1, i));
        }
    }
}