package com.globbypotato.rockhounding_core.machines.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

/*
 * Injected by al132. With permission from WillieWillus, originally from ProjectE 
 * https://github.com/sinkillerj/ProjectE/blob/MC1.10.x/src/main/java/moze_intel/projecte/gameObjs/tiles/WrappedItemHandler.java
 */
public class WrappedItemHandler implements IItemHandlerModifiable{

	public final IItemHandlerModifiable compose;
    public final WriteMode mode;

    public WrappedItemHandler(IItemHandlerModifiable compose, WriteMode mode){
        this.compose = compose;
        this.mode = mode;
    }

    @Override
    public int getSlots(){
        return this.compose.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot){
        return this.compose.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
        if (this.mode == WriteMode.IN || this.mode == WriteMode.IN_OUT)
            return this.compose.insertItem(slot, stack, simulate);
		return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate){
        if (this.mode == WriteMode.OUT || this.mode == WriteMode.IN_OUT)
            return this.compose.extractItem(slot, amount, simulate);
		return ItemStack.EMPTY;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack){
        this.compose.setStackInSlot(slot, stack);
    }

    public enum WriteMode{
        IN,
        OUT,
        IN_OUT,
        NONE
    }

	@Override
	public int getSlotLimit(int slot) {
		return 64;
	}
}