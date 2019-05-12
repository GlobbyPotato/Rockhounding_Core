package com.globbypotato.rockhounding_core.machines.tileentity;

import java.util.Random;

import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public abstract class TileEntityInv extends TileEntityBase implements ITickable{

	public Random rand = new Random();

	public int INVENTORYSIZE;
	protected MachineStackHandler input;
	protected IItemHandlerModifiable automationInput;
	protected MachineStackHandler output;
	protected IItemHandlerModifiable automationOutput;
	protected MachineStackHandler upgrade;
	protected IItemHandlerModifiable automationUpgrade;
	protected ItemStackHandler template;

	public static final int INPUT_SLOT = 0;
	public static final int CONSUMABLE_SLOT = 2;

	public static final int OUTPUT_SLOT = 0;

	public int cooktime = 0;
	public boolean activation = false;
	public int facing = 0;
	public int recipeIndex = -1;

	public TileEntityInv(int inputSlots, int outputSlots, int templateSlots, int upgradeslots){
		this.INVENTORYSIZE = inputSlots + outputSlots + templateSlots + upgradeslots;
		this.template = new TemplateStackHandler(templateSlots);

		this.input = new MachineStackHandler(inputSlots,this){
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

		this.upgrade = new MachineStackHandler(upgradeslots,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate){
				return super.insertItem(slot, stack, simulate);
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN_OUT);

	}



	//---------- HANDLERS ----------
	public IItemHandler getInput() {
		return this.input;
	}

	public IItemHandler getOutput() {
		return this.output;
	}

	public IItemHandler getTemplate() {
		return this.template;
	}

	public IItemHandler getUpgrade() {
		return this.upgrade;
	}

	public IItemHandler getInventory(){
		return new CombinedInvWrapper(this.input, this.output, this.upgrade);
	}

	public IItemHandler getCombinedHandler(){
		return new CombinedInvWrapper(new IItemHandlerModifiable[]{this.automationInput, this.automationOutput, this.automationUpgrade});
	}



	//---------- CUSTOM ----------
	public boolean isActive(){
		int power = this.world.getRedstonePower(poweredPosition(), poweredFacing());
		if(isComparatorSensible() && power > 0 && this.activation){
			this.activation = false;
		}
		return this.activation || (isComparatorSensible() && power > 0);
	}

	public int getCooktime(){
		return this.cooktime;
	}

	public int getGUIHeight() { 
		return 0; 
	}

	/**
	 * @return override only if there is a fuel slot in the machine
	 */
	public int fuelID(){
		return -1;
	}

	public EnumFacing getFacing(){
		return EnumFacing.getFront(this.facing);
	}

	public EnumFacing isFacingAt(int side){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + side);
	}

	public boolean isCooking() {
		return false;
	}

	public int getRecipeIndex(){
		return this.recipeIndex;
	}



	//---------- REDSTONE SIGNAL ----------
	public boolean isComparatorSensible(){
		return true;
	}

	public EnumFacing poweredFacing(){
		return getFacing();
	}

	public BlockPos poweredPosition(){
		return this.pos.offset(poweredFacing());
	}

	public boolean isPowered(){
		int power = this.world.getRedstonePower(poweredPosition(), poweredFacing());
        return power > 0;
	}



	//---------- I/O ----------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.input.deserializeNBT(compound.getCompoundTag("input"));
		this.output.deserializeNBT(compound.getCompoundTag("output"));
		this.upgrade.deserializeNBT(compound.getCompoundTag("upgrade"));
		this.cooktime = compound.getInteger("Cooktime");
		this.activation = compound.getBoolean("Activation");
		this.facing = compound.getInteger("Facing");
		this.recipeIndex = compound.getInteger("RecipeIndex");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setTag("input", this.input.serializeNBT());
		compound.setTag("output", this.output.serializeNBT());
		compound.setTag("upgrade", this.upgrade.serializeNBT());
		compound.setInteger("Cooktime", getCooktime());
		compound.setBoolean("Activation", this.activation);
		compound.setInteger("Facing", getFacing().ordinal());
		compound.setInteger("RecipeIndex", getRecipeIndex());
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



	//---------- PROCESS ----------
	public void tickOff() {
		if(this.getCooktime() > 0){
			this.cooktime = 0;
			this.markDirtyClient();
		}
	}

}