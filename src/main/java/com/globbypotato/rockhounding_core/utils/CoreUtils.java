package com.globbypotato.rockhounding_core.utils;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.oredict.OreDictionary;

public class CoreUtils {
	public static int mendingFactor = 4;

// ITEMS
	/**
	 * Checks if the player is holding the mod wrench in main hand
	 * 
	 * @param player
	 * @return
	 */
	public static boolean hasWrench(EntityPlayer player) {
		return !player.getHeldItemMainhand().isEmpty() 
			&& player.getHeldItemMainhand().isItemEqual(CoreBasics.mod_wrench);
	}

	/**
	 * Checks if the player is holding a certain item in main hand
	 * 
	 * @param player
	 * @return
	 */
	public static boolean hasTool(EntityPlayer player, ItemStack heldItem) {
		return !player.getHeldItemMainhand().isEmpty() 
			&& player.getHeldItemMainhand().isItemEqual(heldItem);
	}

	/**
	 * Checks if the inserting stack matches the given consumable
	 * 
	 * @param consumable
	 * @param insertingStack
	 * @return
	 */
	public static boolean hasConsumable(ItemStack consumable, ItemStack insertingStack) {
		return !insertingStack.isEmpty() 
			&& ItemStack.areItemsEqualIgnoreDurability(consumable, insertingStack)
			&& insertingStack.getItemDamage() < insertingStack.getMaxDamage();
	}

	/**
	 * Checks if the inserting stack matches the given consumable and has at least N uses
	 * 
	 * @param consumable
	 * @param insertingStack
	 * @param step
	 * @return
	 */
	public static boolean hasConsumable(ItemStack consumable, ItemStack insertingStack, int step) {
		return !insertingStack.isEmpty() 
	 		&& ItemStack.areItemsEqualIgnoreDurability(consumable, insertingStack)
	 		&& insertingStack.getItemDamage() <= insertingStack.getMaxDamage() - step;
	}

	/**
	 * Makes an array out of a list of variables
	 * 
	 * @param array
	 * @return
	 */
	public static ArrayList<Integer> intArrayToList(int[] array){
		ArrayList<Integer> temp = new ArrayList<Integer>(array.length);
		for(int i = 0; i < array.length; i++){
			temp.add(array[i]);
		}
		return temp;
	}

	/**
	 * Checks if two itemstacks are matching
	 * 
	 * @param recipeIngredient
	 * @param slotIngredient
	 * @return
	 */
	public static boolean isMatchingIngredient(ItemStack recipeIngredient, ItemStack slotIngredient) {
		if(!recipeIngredient.isEmpty() && !slotIngredient.isEmpty()){
			ArrayList<Integer> inputIDs = intArrayToList(OreDictionary.getOreIDs(slotIngredient));
			ArrayList<Integer> recipeIDs = intArrayToList(OreDictionary.getOreIDs(recipeIngredient));
			if(!inputIDs.isEmpty() && inputIDs.size() > 0 && !recipeIDs.isEmpty() && recipeIDs.size() > 0){
				if(compareDictArrays(inputIDs, recipeIDs)){
					return true;
				}
			}
			return ItemStack.areItemsEqual(recipeIngredient, slotIngredient);
		}
		return false;
	}

	/**
	 * Checks if two itemstacks are matching
	 * 
	 * @param recipeIngredient
	 * @param canOredict
	 * @param slotIngredient
	 * @return
	 */
	public static boolean isMatchingIngredient(ItemStack recipeIngredient, boolean canOredict, ItemStack slotIngredient) {
		if(!recipeIngredient.isEmpty() && !slotIngredient.isEmpty()){
			if(canOredict){
				ArrayList<Integer> inputIDs = intArrayToList(OreDictionary.getOreIDs(slotIngredient));
				ArrayList<Integer> recipeIDs = intArrayToList(OreDictionary.getOreIDs(recipeIngredient));
				if(!inputIDs.isEmpty() && inputIDs.size() > 0 && !recipeIDs.isEmpty() && recipeIDs.size() > 0){
					if(compareDictArrays(inputIDs, recipeIDs)){
						return true;
					}
				}
			}
			return ItemStack.areItemsEqual(recipeIngredient, slotIngredient);
		}
		return false;
	}

	/**
	 * Compose an itemstack based on a modded item
	 * 
	 * @param item
	 * @param num
	 * @param meta
	 * @return
	 */
	public static ItemStack getModdedStack(Item item, int num, int meta) { 
		return item != null ? new ItemStack (item, num, meta) : ItemStack.EMPTY;
	}

	/**
	 * Compose an itemstack based on a modded block
	 * 
	 * @param block
	 * @param num
	 * @param meta
	 * @return
	 */
	public static ItemStack getModdedStack(Block block, int num, int meta) { 
		return block != null ? new ItemStack (block, num, meta) : ItemStack.EMPTY;
	}

	/**
	 * Compare two oredict arrays to find a match
	 * 
	 * @param inputOreIDs
	 * @param recipeOreIDs
	 * @return
	 */
	public static boolean compareDictArrays(ArrayList<Integer> inputOreIDs, ArrayList<Integer> recipeOreIDs) {
		for(Integer recipeID: recipeOreIDs){
			String recipeName = OreDictionary.getOreName(recipeID);
			for(Integer inputID: inputOreIDs){
				String inputName = OreDictionary.getOreName(inputID);
				if(inputName.matches(recipeName)){
					return true;
				}
			}
		}
		return false;
	}



	/**
	 * obtain the enchantment level of the consumable, if available
	 * 
	 * @param enchantment
	 * @param consumable
	 * @return
	 */
	public static int getEnchantmentLevel(Enchantment enchantment, ItemStack consumable){
		int j = 0;
		if(!consumable.isEmpty() && consumable.getItemDamage() < consumable.getMaxDamage()){
	        j = EnchantmentHelper.getEnchantmentLevel(enchantment, consumable);
		}
		return j;
	}



	/**
	 * 
	 * Check if the stack in the slot has mending
	 * 
	 * @param slot
	 * @return
	 */
	public static boolean hasMending(ItemStack slot){
		int mendingLevel = getEnchantmentLevel(Enchantments.MENDING, slot);
        return mendingLevel > 0;
	}



// FLUIDS
	/**
	 * Gets a fluidstack witgh a registered fluid
	 * 
	 * @param fluid
	 * @param amount
	 * @return
	 */
	public static FluidStack getFluid(String fluidName, int amount) {
		return fluidExists(fluidName) ? new FluidStack(FluidRegistry.getFluid(fluidName), amount) : null;
	}

	/**
	 * Checks if the given fluid is registered
	 * 
	 * @param fluidname
	 * @return
	 */
	public static boolean fluidExists(String fluidname) {
		return FluidRegistry.isFluidRegistered(fluidname);
	}

	/**
	 * Checks if the inserting stack is any filled bucket
	 * 
	 * @param insertingStack
	 * @return
	 */
	public static boolean isBucketType(ItemStack insertingStack) {
		return !insertingStack.isEmpty() 
			&& (insertingStack.getItem() instanceof ItemBucket || insertingStack.getItem() instanceof UniversalBucket);
	}

	/**
	 * Returns a filled bucket stack related to a given fluid
	 * 
	 * @param fluid
	 * @return
	 */
	public static ItemStack getFluidBucket(Fluid fluid) {
		return FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
	}

	/**
	 * Checks if the inserting stack is an empty bucket 
	 * 
	 * @param insertingStack
	 * @return
	 */
	public static boolean isEmptyBucket(ItemStack insertingStack) {
		return !insertingStack.isEmpty()
			&& (insertingStack.getItem() == Items.BUCKET 
			|| (insertingStack.getItem() instanceof UniversalBucket && FluidUtil.getFluidContained(insertingStack).containsFluid(null)));
	}

}