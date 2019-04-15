package com.globbypotato.rockhounding_core.machines.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.globbypotato.rockhounding_core.utils.CoreBasics;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.FuelUtils;

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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public abstract class TileEntityPoweredTank extends TileEntityPoweredMachine implements IFluidHandlingTile{

	public FluidTank lavaTank;

	public TileEntityPoweredTank(int inputSlots, int outputSlots,int templateSlots, int upgradeSlots) {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);

		this.lavaTank = new FluidTank(Fluid.BUCKET_VOLUME){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
		        return fluid.isFluidEqual(CoreBasics.lavaStack(Fluid.BUCKET_VOLUME));
		    }
		};
		this.lavaTank.setTileEntity(this);
		this.lavaTank.setCanDrain(false);
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
	public boolean interactWithFluidHandler(@Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull World world, @Nonnull BlockPos pos, @Nullable EnumFacing side){
		boolean didFill = FluidUtil.interactWithFluidHandler(player, hand, world, pos, side);
		this.markDirtyClient();
		return didFill;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) getCombinedTank();
		return super.getCapability(capability, facing);
	}

	/**
	 * List here all the fluid tanks present in the machine
	 * 
	 * @return
	 */
	protected FluidHandlerConcatenate getCombinedTank(){
		return null;
	}



	//---------------- CUSTOM ----------------
	/**
	 * Checks if inserting stack is a specific fluid bucket
	 * 
	 * @param insertingStack
	 * @param fluid
	 * @return
	 */
	protected boolean handleBucket(ItemStack insertingStack, Fluid fluid){
		if(!insertingStack.isEmpty()){
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

	private static boolean isValidFluidStack(ItemStack insertingStack, FluidStack fluidStack) {
		if(!insertingStack.isEmpty() && fluidStack != null){
			return FluidUtil.getFluidContained(insertingStack).containsFluid(fluidStack);
		}
		return false;
	}

	private static FluidStack toFluidStack(Fluid fluid) {
		return fluid != null ? new FluidStack(fluid, Fluid.BUCKET_VOLUME) : null;
	}
	
	/**
	 * If possible transfer the fluid from the bucket in slot to the tank
	 * 
	 * @param slot
	 * @param tank
	 */
	protected void emptyContainer(int slot, FluidTank tank){
		ItemStack bucketSlot = this.input.getStackInSlot(slot);
		FluidStack insertingFluid = FluidUtil.getFluidContained(bucketSlot);
		if(CoreUtils.isBucketType(bucketSlot) && insertingFluid != null){
			if(tank.getFluid() == null || (tank.getFluid() != null && insertingFluid.isFluidEqual(tank.getFluid()))){
				if(!FluidUtil.tryEmptyContainer(bucketSlot, tank, 1000, null, false).getResult().isEmpty()){
					this.input.setStackInSlot(slot, FluidUtil.tryEmptyContainer(bucketSlot, tank, 1000, null, true).getResult());
				}
			}
		}
		this.markDirtyClient();
	}

	/**
	 * If possible transfer the fluid from the tank to the empty bucket in slot
	 * 
	 * @param slot
	 * @param tank
	 */
	protected void fillContainer(int slot, FluidTank tank){
		ItemStack bucketSlot = this.input.getStackInSlot(slot);
		if(CoreUtils.isEmptyBucket(bucketSlot) && tank.getFluid() != null && tank.getFluidAmount() >= 1000){
			if(FluidUtil.tryFillContainer(bucketSlot, tank, 1000, null, false) != null){
				if(bucketSlot.getCount() > 1){
					ItemStack droppingBeaker = FluidUtil.tryFillContainer(bucketSlot, tank, 1000, null, true).getResult();
				    EnumFacing front = EnumFacing.getFront(getBlockMetadata());
					BlockPos frontPos = this.pos.offset(front.getOpposite());
					EntityItem entityitem = new EntityItem(this.world, frontPos.getX() + 0.5D, frontPos.getY() + 0.5D, frontPos.getZ() + 0.5D, droppingBeaker);
					this.world.spawnEntity(entityitem);
					this.input.decrementSlot(slot);
				}else{
					this.input.setStackInSlot(slot, FluidUtil.tryFillContainer(bucketSlot, tank, 1000, null, true).getResult());
				}
			}
		}
		this.markDirtyClient();
	}



	//---------------- FUELING ----------------
	public int lavaBurntime(){
		return FuelUtils.getItemBurnTime(new ItemStack(Items.LAVA_BUCKET));
	}

	/**
	 * Transfer the lava from inner tank to the power bar
	 */
	public void lavaHandler(){
		if(!hasFuelBlend()){
			if(this.lavaTank.getFluidAmount() >= Fluid.BUCKET_VOLUME){
				if(this.getPower() <= this.getPowerMax() - lavaBurntime()){
					this.input.drainOrCleanFluid(this.lavaTank, Fluid.BUCKET_VOLUME, false);
					this.powerCount += lavaBurntime();
					this.markDirtyClient();
				}
			}
		}
	}

}