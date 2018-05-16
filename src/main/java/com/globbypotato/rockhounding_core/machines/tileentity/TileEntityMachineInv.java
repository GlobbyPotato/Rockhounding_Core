package com.globbypotato.rockhounding_core.machines.tileentity;

import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public abstract class TileEntityMachineInv extends TileEntityMachineBase implements ITickable{

	public int SIZE;
	protected MachineStackHandler input;
	protected IItemHandlerModifiable automationInput;
	protected MachineStackHandler output;
	protected IItemHandlerModifiable automationOutput;

	public static final int INPUT_SLOT = 0;
	public static final int OUTPUT_SLOT = 0;
	public static final int CONSUMABLE_SLOT = 2;

	public abstract int getGUIHeight();

	public TileEntityMachineInv(int inputSlots, int outputSlots){
		this.SIZE = inputSlots + outputSlots;

		this.input = new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
				return super.insertItem(slot, stack, simulate);
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN_OUT);

		this.output = new MachineStackHandler(outputSlots,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
				return stack;
			}
			@Override
		    protected void onContentsChanged(int slot){
				this.tile.markDirty();
		    }
		};
		this.automationOutput = new WrappedItemHandler(this.output, WriteMode.IN_OUT){
			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate){
				ItemStack stack = getStackInSlot(slot);
				if(stack!= null) return super.extractItem(slot, amount, simulate);
				return null;
			}
		};

	}

	public boolean isCooking() {
		return false;
	}

	public IItemHandler getOutput() {
		return this.output;
	}

	public IItemHandler getInput() {
		return this.input;
	}

	public IItemHandler getInventory(){
		return new CombinedInvWrapper(this.input, this.output);
	}

	public IItemHandler getCombinedHandler(){
		return new CombinedInvWrapper(new IItemHandlerModifiable[]{this.automationInput, this.automationOutput});
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.input.deserializeNBT(compound.getCompoundTag("input"));
		this.output.deserializeNBT(compound.getCompoundTag("output"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("input", this.input.serializeNBT());
		compound.setTag("output", this.output.serializeNBT());
		return compound;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getCombinedHandler());
		}
		return super.getCapability(capability, facing);
	}
}