package com.globbypotato.rockhounding_core.machines.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity {

	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(this.pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = getUpdateTag();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, getBlockMetadata(), tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
	    NBTTagCompound tag = packet.getNbtCompound();
	    handleUpdateTag(tag);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag){
		this.readFromNBT(tag);
	}

	public int getInventoryStackLimit(){
		return 64;
	}
	
	//Courtesy of mcjtylib
	public void markDirtyClient() {
		markDirty();
		if (this.world != null) {
			IBlockState state = this.world.getBlockState(getPos());
			this.world.notifyBlockUpdate(getPos(), state, state, 3);
		}
	}

}