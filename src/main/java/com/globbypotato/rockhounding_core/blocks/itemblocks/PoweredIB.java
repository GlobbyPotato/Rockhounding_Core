package com.globbypotato.rockhounding_core.blocks.itemblocks;

import java.util.List;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PoweredIB extends ItemBlock {
	public PoweredIB(Block block) {
        super(block);
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack);
    }

	@Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		setItemNbt(itemstack);
    }

    private static void setItemNbt(ItemStack itemstack) {
    	itemstack.setTagCompound(new NBTTagCompound());
    	itemstack.getTagCompound().setInteger("Fuel", 0);
    	itemstack.getTagCompound().setInteger("Energy", 0);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(itemstack.hasTagCompound()) {
        	int fuel = itemstack.getTagCompound().getInteger("Fuel");
        	int energy = itemstack.getTagCompound().getInteger("Energy");
        	if(fuel > 0){
        		tooltip.add(TextFormatting.DARK_GRAY + "Stored Fuel: " + TextFormatting.YELLOW + fuel + " ticks");
        	}
        	if(energy > 0){
        		tooltip.add(TextFormatting.DARK_GRAY + "Stored Energy: " + TextFormatting.RED + energy + " RF");
        	}

        	for(int x = 0; x < EnumFluidNbt.size(); x++){
        		String getTag = EnumFluidNbt.values()[x].nameTag();
        		if(itemstack.getTagCompound().hasKey(getTag)){
        			FluidStack fluid = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(getTag));
        			if(fluid != null && fluid.amount > 0){
                		tooltip.add(TextFormatting.DARK_GRAY + "Stored Fluid: " + TextFormatting.GOLD + fluid.getLocalizedName() + " (" + fluid.amount + " mB)");
        			}
        		}
        	}
        }else{
        	setItemNbt(itemstack);
        }
    }

}