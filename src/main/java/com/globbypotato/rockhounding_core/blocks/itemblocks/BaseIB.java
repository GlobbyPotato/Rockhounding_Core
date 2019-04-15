package com.globbypotato.rockhounding_core.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BaseIB extends ItemBlock {
	public BaseIB(Block block) {
        super(block);
	}

	@Override
	public int getMetadata(int meta){
		return meta;
	}
}