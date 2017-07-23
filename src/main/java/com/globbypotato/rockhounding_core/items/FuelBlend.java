package com.globbypotato.rockhounding_core.items;

import java.util.List;

import com.globbypotato.rockhounding_core.handlers.ModConfig;
import com.globbypotato.rockhounding_core.handlers.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FuelBlend extends BaseItem{

	public FuelBlend(String name) {
		super(name);
		if(ModConfig.enableFuelBlend){
			setCreativeTab(Reference.RockhoundingCore);
		}else{
			setCreativeTab(null);
		}
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
       	tooltip.add(TextFormatting.DARK_GRAY + "Burntime: " + TextFormatting.YELLOW + ModConfig.fuelBlendPower + " ticks");
	}

}