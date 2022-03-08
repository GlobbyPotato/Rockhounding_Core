package com.globbypotato.rockhounding_core.machines.tileentity;

import com.globbypotato.rockhounding_core.utils.FuelUtils;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.EnergyStorage;

public abstract class TileEntityFueledMachine extends TileEntityInv  implements IFuelHandlingTile {

    public final EnergyStorage storage = new EnergyStorage(rfTransfer());

    public int powerCount = 0;
	public int powerMax = 64000;

	public TileEntityFueledMachine(int inputSlots, int outputSlots, int templateSlots, int upgradeSlots) {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);
	}



	//---------------- FUEL ----------------
	protected void fuelHandler(ItemStack stack) {
		if(fuelID() > -1 && !stack.isEmpty()) {
			if(isFuel(stack) ){
				burnFuel(stack);
				this.markDirtyClient();
			}
		}
	}

	public boolean isFuel(ItemStack insertingStack){
		return FuelUtils.isItemFuel(insertingStack);
	}

	protected void burnFuel(ItemStack stack) {
		if(fuelID() >-1 && !stack.isEmpty()) {
			if( this.getPower() <= (this.getPowerMax() - FuelUtils.getItemBurnTime(stack)) ){
				this.powerCount += FuelUtils.getItemBurnTime(stack);
				if(stack.getItem().hasContainerItem(stack)){
					if(stack.getCount() > 1){
						ItemStack dropContainer = stack.getItem().getContainerItem(this.input.getStackInSlot(fuelID()));
						BlockPos contPos = this.pos.offset(getFacing().getOpposite());
						EntityItem entityCont = new EntityItem(this.world, contPos.getX(), contPos.getY(), contPos.getZ(), dropContainer);
						if(!this.world.isRemote){
							this.world.spawnEntity(entityCont);
						}
						stack.shrink(1);
					}else{
						this.input.setStackInSlot(fuelID(), stack.getItem().getContainerItem(this.input.getStackInSlot(fuelID())));
					}
				}else{
					stack.shrink(1);
				}
			}
			if(stack.getCount() <= 0){
				this.input.setStackInSlot(fuelID(), ItemStack.EMPTY);
			}
		}
	}



	//---------------- I/O ----------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.powerCount = compound.getInteger("PowerCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("PowerCount", getPower());
		return compound;
	}



	//---------------- VARIABLES ----------------
	@Override
	public int getPower() { 	  return this.powerCount; }
	@Override
	public int getPowerMax() { 	  return this.powerMax; }

}