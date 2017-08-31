package com.globbypotato.rockhounding_core.machines.tileentity;

import com.globbypotato.rockhounding_core.handlers.ModConfig;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.FuelUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public abstract class TileEntityMachineTank extends TileEntityMachineEnergy implements IFluidHandlingTile{

	public FluidTank lavaTank;

	public TileEntityMachineTank(int inputSlots, int outputSlots, int fuelSlot) {
		super(inputSlots, outputSlots, fuelSlot);

		lavaTank = new FluidTank(Fluid.BUCKET_VOLUME){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
		        return fluid.isFluidEqual(lavaStack());
		    }
		};
		lavaTank.setTileEntity(this);
		lavaTank.setCanDrain(false);
	}



	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.lavaTank.readFromNBT(compound.getCompoundTag("LavaTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound lavaTankNBT = new NBTTagCompound();
		this.lavaTank.writeToNBT(lavaTankNBT);
		compound.setTag("LavaTank", lavaTankNBT);

		return compound;
	}



	//---------------- HANDLER ----------------
	@Override
	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean didFill = FluidUtil.interactWithFluidHandler(heldItem, this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side), player);
		this.markDirtyClient();
		return didFill;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) getCombinedTank();
		return super.getCapability(capability, facing);
	}

	protected FluidHandlerConcatenate getCombinedTank(){
		return null;
	}



	//---------------- CUSTOM ----------------
	protected boolean handleBucket(ItemStack insertingStack, Fluid fluid){
		if(insertingStack != null){
			if(CoreUtils.isBucketType(insertingStack)){
				if(insertingStack.getItem() instanceof UniversalBucket){
					if(toFluidStack(fluid) != null){
						return isValidFluidStack(insertingStack, toFluidStack(fluid));
					}
				}else if(insertingStack.getItem() instanceof ItemBucket || insertingStack.getItem() == Items.BUCKET){
					return true;
				}
			}
		}
		return false;
	}

	private boolean isValidFluidStack(ItemStack insertingStack, FluidStack fluidStack) {
		if(insertingStack != null && fluidStack != null){
			return FluidUtil.getFluidContained(insertingStack).containsFluid(fluidStack);
		}
		return false;
	}

	private FluidStack toFluidStack(Fluid fluid) {
		return fluid != null ? new FluidStack(fluid, Fluid.BUCKET_VOLUME) : null;
	}

	protected void emptyContainer(int slot, FluidTank tank){
		if( FluidUtil.getFluidContained(input.getStackInSlot(slot)) != null){
			if(FluidUtil.tryEmptyContainer(input.getStackInSlot(slot), tank, 1000, null, false) != null){
				input.setStackInSlot(slot,FluidUtil.tryEmptyContainer(input.getStackInSlot(slot), tank, Fluid.BUCKET_VOLUME, null, true));
			}
		}
	}

	protected void fillContainer(int slot, FluidTank tank) {
		if (CoreUtils.isBucketType(input.getStackInSlot(slot)) && tank.getFluid() != null && tank.getFluidAmount() >= Fluid.BUCKET_VOLUME) {
			if (FluidUtil.tryFillContainer(input.getStackInSlot(slot), tank, Fluid.BUCKET_VOLUME, null, false) != null) {
				if (input.getStackInSlot(slot).stackSize > 1) {
					ItemStack droppingBeaker = FluidUtil.tryFillContainer(input.getStackInSlot(slot), tank, Fluid.BUCKET_VOLUME, null, true);
					EnumFacing front = EnumFacing.getFront(getBlockMetadata());
					BlockPos frontPos = pos.offset(front.getOpposite());
					EntityItem entityitem = new EntityItem(worldObj, frontPos.getX() + 0.5D, frontPos.getY() + 0.5D, frontPos.getZ() + 0.5D, droppingBeaker);
					worldObj.spawnEntityInWorld(entityitem);
					input.decrementSlot(slot);
				} else {
					input.setStackInSlot(slot, FluidUtil.tryFillContainer(input.getStackInSlot(slot), tank, Fluid.BUCKET_VOLUME, null, true));
				}
			}
		}
	}



	//---------------- FUELING ----------------
	public FluidStack lavaStack() {
		return new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME);
	}

	public int lavaBurntime(){
		return FuelUtils.getItemBurnTime(new ItemStack(Items.LAVA_BUCKET));
	}

	public void lavaHandler(){
		if(!ModConfig.enableFuelBlend){
			if(this.getPower() <= this.getPowerMax() - lavaBurntime()){
				if(lavaTank.getFluidAmount() >= Fluid.BUCKET_VOLUME){
					lavaTank.getFluid().amount -= Fluid.BUCKET_VOLUME;
					this.powerCount += lavaBurntime();
					this.markDirtyClient();
				}
			}
		}
	}

}