package com.globbypotato.rockhounding_core.machines.tileentity;

import com.globbypotato.rockhounding_core.gas.CapabilityGasHandler;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.PipelineBase;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public abstract class TileEntityVessel extends TileEntityInv implements IGasHandlingTile{

	public TileEntityVessel(int inputSlots, int outputSlots,int templateSlots, int upgradeSlots) {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);
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

}