package com.globbypotato.rockhounding_core.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class FuelUtils {

	/**
	 * Checks if the given stack is burnable
	 * 
	 * @param stack
	 * @return
	 */
	public static boolean isItemFuel(ItemStack stack){
		return FuelUtils.getItemBurnTime(stack) > 0;
	}

	public static int getItemBurnTime(ItemStack stack){
        if(stack.isEmpty()){
            return 0;
        }
		Item item = stack.getItem();
		if (!item.getRegistryName().getResourceDomain().equals("minecraft")){
		    int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);
		    if (burnTime >= 0) return burnTime;
		}
		return item == Item.getItemFromBlock(Blocks.WOODEN_SLAB) ? 150 : (item == Item.getItemFromBlock(Blocks.WOOL) ? 100 : (item == Item.getItemFromBlock(Blocks.CARPET) ? 67 : (item == Item.getItemFromBlock(Blocks.LADDER) ? 300 : (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON) ? 100 : (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD ? 300 : (item == Item.getItemFromBlock(Blocks.COAL_BLOCK) ? 16000 : (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()) ? 200 : (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()) ? 200 : (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()) ? 200 : (item == Items.STICK ? 100 : (item != Items.BOW && item != Items.FISHING_ROD ? (item == Items.SIGN ? 200 : (item == Items.COAL ? 1600 : (item == Items.LAVA_BUCKET ? 20000 : (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL ? (item == Items.BLAZE_ROD ? 2400 : (item instanceof ItemDoor && item != Items.IRON_DOOR ? 200 : (item instanceof ItemBoat ? 400 : 0))) : 100)))) : 300)))))))))));
	}
}