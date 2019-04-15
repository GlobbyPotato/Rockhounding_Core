package com.globbypotato.rockhounding_core.machines.tileentity;

import com.globbypotato.rockhounding_core.gas.CapabilityGasHandler;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.PipelineBase;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public abstract class TileEntityFueledVessel extends TileEntityFueledTank implements IGasHandlingTile{

	public FluidTank gasTank;

	public TileEntityFueledVessel(int inputSlots, int outputSlots, int templateSlots, int upgradeSlots) {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);

		this.gasTank = new FluidTank(Fluid.BUCKET_VOLUME){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
		        return fluid.isFluidEqual(CoreBasics.gasStack(Fluid.BUCKET_VOLUME));
		    }
		};
		this.gasTank.setTileEntity(this);
		this.gasTank.setCanDrain(false);
	}



	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.gasTank.readFromNBT(compound.getCompoundTag("GasTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound lavaTankNBT = new NBTTagCompound();
		this.gasTank.writeToNBT(lavaTankNBT);
		compound.setTag("GasTank", lavaTankNBT);

		return compound;
	}



	//---------------- HANDLER ----------------
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityGasHandler.GAS_HANDLER_CAPABILITY && isGasHandling(facing)) return true;
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityGasHandler.GAS_HANDLER_CAPABILITY && isGasHandling(facing))
			return (T) getCombinedGasTank();
		return super.getCapability(capability, facing);
	}

	private boolean isGasHandling(EnumFacing facing) {
		Block front = this.world.getBlockState(this.pos.offset(getFacing())).getBlock();
		Block back = this.world.getBlockState(this.pos.offset(getFacing().getOpposite())).getBlock();
		return (facing == getFacing() && front instanceof PipelineBase) || (facing == getFacing().getOpposite() && back instanceof PipelineBase);
	}

	/**
	 * List here all the gas tanks present in the machine
	 * 
	 * @return
	 */
	protected GasHandlerConcatenate getCombinedGasTank(){
		return null;
	}


	//---------------- FUELING ----------------
	public int gasBurntime(){
		return 200;
	}

	/**
	 * Transfer the gas from inner tank to the power bar
	 */
	public void gasHandler(){
		if(!hasFuelBlend()){
			if(this.gasTank.getFluidAmount() > 0){
				if(this.getPower() <= this.getPowerMax() - gasBurntime()){
					this.input.drainOrCleanFluid(this.gasTank, 1, false);
					this.powerCount += gasBurntime();
					this.markDirtyClient();
				}
			}
		}
	}

}