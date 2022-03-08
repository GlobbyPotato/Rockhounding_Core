package com.globbypotato.rockhounding_core.utils;

import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityFueledMachine;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityFueledTank;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityFueledVessel;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityPoweredMachine;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityPoweredTank;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityPoweredVessel;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class MachinesUtils {

    public static void restoreMachineNBT(ItemStack stack, TileEntity te, int facing) {
		if(te instanceof TileEntityInv){
			if(facing != -1){
				TileEntityInv tile = (TileEntityInv)te;
				tile.facing = facing;
			}
		}
		if(stack.hasTagCompound()){
			if(te instanceof TileEntityInv){
				TileEntityInv tile = (TileEntityInv)te;
				if(stack.getTagCompound().hasKey("Recipe")){
					int recipe = stack.getTagCompound().getInteger("Recipe");
	    			tile.recipeIndex = recipe;
				}
			}

			if(te instanceof TileEntityFueledMachine){
				TileEntityFueledMachine tile = (TileEntityFueledMachine)te;
				if(stack.getTagCompound().hasKey("Fuel")){
		        	int fuel = stack.getTagCompound().getInteger("Fuel");
	            	tile.powerCount = fuel;
				}
			}

			if(te instanceof TileEntityFueledTank){
				TileEntityFueledTank tile = (TileEntityFueledTank)te;
	    		if(stack.getTagCompound().hasKey(EnumFluidNbt.LAVA.nameTag())){
	    			tile.lavaTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.LAVA.nameTag())));
	    		}
			}

			if(te instanceof TileEntityFueledVessel){
				TileEntityPoweredVessel tile = (TileEntityPoweredVessel)te;
	    		if(stack.getTagCompound().hasKey(EnumFluidNbt.COMBUSTIBLE.nameTag())){
	    			tile.gasTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.COMBUSTIBLE.nameTag())));
	    		}
			}

			if(te instanceof TileEntityPoweredMachine){
				TileEntityPoweredMachine tile = (TileEntityPoweredMachine)te;
				if(stack.getTagCompound().hasKey("Energy")){
					int energy = stack.getTagCompound().getInteger("Energy");
	            	tile.redstoneCount = energy;
				}
			}

			if(te instanceof TileEntityPoweredVessel){
				TileEntityPoweredVessel tile = (TileEntityPoweredVessel)te;
	    		if(stack.getTagCompound().hasKey(EnumFluidNbt.COMBUSTIBLE.nameTag())){
	    			tile.gasTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.COMBUSTIBLE.nameTag())));
	    		}
			}
			
			if(te instanceof TileEntityPoweredTank){
				TileEntityPoweredTank tile = (TileEntityPoweredTank)te;
	    		if(stack.getTagCompound().hasKey(EnumFluidNbt.LAVA.nameTag())){
	    			tile.lavaTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.LAVA.nameTag())));
	    		}
			}


		}
	}

	public static void addMachineNbt(ItemStack itemstack, TileEntity tileentity) {
		if(tileentity != null){
			if(tileentity instanceof TileEntityInv){
				itemstack.setTagCompound(new NBTTagCompound());
				TileEntityInv tile = (TileEntityInv)tileentity;
				if(tile.recipeIndex >= 0){
					itemstack.getTagCompound().setInteger("Recipe", tile.recipeIndex);
				}
			}

			if(tileentity instanceof TileEntityFueledMachine){
				TileEntityFueledMachine tile = (TileEntityFueledMachine)tileentity;
				itemstack.getTagCompound().setInteger("Fuel", tile.powerCount);
			}

			if (tileentity instanceof TileEntityFueledTank){
				TileEntityFueledTank tile = ((TileEntityFueledTank)tileentity);
				NBTTagCompound lava = new NBTTagCompound(); 
				if(tile.lavaTank.getFluid() != null){
					tile.lavaTank.getFluid().writeToNBT(lava);
					itemstack.getTagCompound().setTag(EnumFluidNbt.LAVA.nameTag(), lava);
				}
			}

			if(tileentity instanceof TileEntityFueledVessel){
				TileEntityFueledVessel tile = ((TileEntityFueledVessel)tileentity);
				NBTTagCompound lava = new NBTTagCompound(); 
				if(tile.gasTank.getFluid() != null){
					tile.gasTank.getFluid().writeToNBT(lava);
					itemstack.getTagCompound().setTag(EnumFluidNbt.COMBUSTIBLE.nameTag(), lava);
				}
			}

			if(tileentity instanceof TileEntityPoweredMachine){
				TileEntityPoweredMachine tile = (TileEntityPoweredMachine)tileentity;
				itemstack.getTagCompound().setInteger("Energy", tile.redstoneCount);
			}

			if(tileentity instanceof TileEntityPoweredVessel){
				TileEntityPoweredVessel tile = ((TileEntityPoweredVessel)tileentity);
				NBTTagCompound lava = new NBTTagCompound(); 
				if(tile.gasTank.getFluid() != null){
					tile.gasTank.getFluid().writeToNBT(lava);
					itemstack.getTagCompound().setTag(EnumFluidNbt.COMBUSTIBLE.nameTag(), lava);
				}
			}

			if (tileentity instanceof TileEntityPoweredTank){
				TileEntityPoweredTank tile = ((TileEntityPoweredTank)tileentity);
				NBTTagCompound lava = new NBTTagCompound(); 
				if(tile.lavaTank.getFluid() != null){
					tile.lavaTank.getFluid().writeToNBT(lava);
					itemstack.getTagCompound().setTag(EnumFluidNbt.LAVA.nameTag(), lava);
				}
			}
		}
	}

}