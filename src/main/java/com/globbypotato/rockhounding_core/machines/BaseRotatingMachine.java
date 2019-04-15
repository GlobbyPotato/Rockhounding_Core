package com.globbypotato.rockhounding_core.machines;

import java.util.Random;

import com.globbypotato.rockhounding_core.blocks.BaseMetaBlock;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BaseRotatingMachine extends BaseMetaBlock {

	public BaseRotatingMachine(String modid, String name, Material material, String[]array){
        super(modid, name, material, array);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
		return false;
    }

    @Override
	public boolean isFullCube(IBlockState state) {
		return false;
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
	public boolean hasTileEntity(IBlockState state) {
		return false;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return super.getPickBlock(state, target, world, pos, player);
    }

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		TileEntity te = world.getTileEntity(pos);
		if (te != null && te instanceof TileEntityInv){
			if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)){
				IItemHandler inventory = ((TileEntityInv) te).getInventory();
				int slots = inventory.getSlots();
				for(int i=0;i<slots; i++){
					if(inventory.getStackInSlot(i) != ItemStack.EMPTY){
						world.spawnEntity(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),inventory.getStackInSlot(i)));
					}
				}
			}
		}
		super.breakBlock(world, pos, state);
	}

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items){
        for (int i = 0; i < this.array.length; ++i){
            items.add(new ItemStack(this, 1, i));
        }

    }

}