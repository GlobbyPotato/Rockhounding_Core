package com.globbypotato.rockhounding_core.machines.tileentity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IFluidHandlingTile {
	public boolean interactWithFluidHandler(@Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull World world, @Nonnull BlockPos pos, @Nullable EnumFacing side);
}