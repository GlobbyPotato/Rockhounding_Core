package com.globbypotato.rockhounding_core.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class BaseArray extends BaseItem {
	public String[] itemArray;

	public BaseArray(String modid, String name, String[] array) {
		super(modid, name);
		setHasSubtypes(true);
		this.itemArray = array;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if( i < 0 || i >= this.itemArray.length){ i = 0; }
		return super.getUnlocalizedName() + "." + this.itemArray[i];
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items){
        if (this.isInCreativeTab(tab)){
	        for (int i = 0; i < this.itemArray.length; ++i){
	            items.add(new ItemStack(this, 1, i));
	        }
        }
    }

}