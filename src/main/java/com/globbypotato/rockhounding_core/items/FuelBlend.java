package com.globbypotato.rockhounding_core.items;

import java.util.List;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_core.handlers.ModConfig;
import com.globbypotato.rockhounding_core.handlers.Reference;
import com.globbypotato.rockhounding_core.items.io.ItemIO;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FuelBlend extends ItemIO{

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
    public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
       	tooltip.add(TextFormatting.DARK_GRAY + "Power: " + TextFormatting.YELLOW + ModConfig.fuelBlendPower + " ticks");
	}

}