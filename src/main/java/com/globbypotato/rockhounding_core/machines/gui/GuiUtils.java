package com.globbypotato.rockhounding_core.machines.gui;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;

import com.globbypotato.rockhounding_core.handlers.Reference;
import com.globbypotato.rockhounding_core.utils.RenderFluidUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class GuiUtils{
	public static ResourceLocation TEXTURE_GAS = new ResourceLocation(Reference.MODID + ":textures/gas/gas_texture.png");

/*
 * -------- CONSTANTS --------
 */
	public static String gasUnit() {
		return "cu";
	}

	public static String fluidUnit() {
		return "mB";
	}

/*
 * -------- AREA --------
 */

    /**
     * Mark a mouse hovering area
     * 
     * @param posX
     * @param posY
     * @param width
     * @param height
     * @param mouseX
     * @param mouseY
     * @param x
     * @param y
     * @return
     */
    public static boolean hoveringArea(int posX, int posY, int width, int height, int mouseX, int mouseY, int x, int y) {
    	int xStart = posX + x;		int xEnd = posX + width + x;
    	int yStart = posY + y;		int yEnd = posY + height + y;
    	return mouseX >= xStart && mouseX < xEnd && mouseY >= yStart && mouseY < yEnd;
	}

	/**
	 * Rescale a progress bar 
	 * 
	 * @param pixels
	 * @param count
	 * @param max
	 * @return
	 */
	public static int getScaledValue(int pixels, int count, int max) {
        return count > 0 && max > 0 ? count * pixels / max : 0;
	}

/*
 * -------- LABELS --------
 */

	/**
	 * Draw a scaled storage label with unit and a consume amount parameter
	 * 
	 * @param mainColor
	 * @param unit
	 * @param paramColor
	 * @param process
	 * @param count
	 * @param max
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public static List<String> drawStorage(TextFormatting mainColor, String unit, TextFormatting paramColor, int process, int count, int max, int mouseX, int mouseY) {
		String counter = TextFormatting.DARK_GRAY + "Storage: " + mainColor + count + "/" + max + " " + unit;
		String cooking = "";
		if(process > 0){
			cooking = TextFormatting.DARK_GRAY + "Process: " + paramColor + process + " " + unit;
		}
		return Arrays.asList(counter, cooking);
	}

	/**
	 * Draw a simple label
	 * 
	 * @param caption
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public static List<String> drawLabel(String caption, int mouseX, int mouseY) {
		return Arrays.asList(caption);
	}

	/**
	 * Draw a multiline label
	 * 
	 * @param multistring
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public static List<String> drawMultiLabel(String[] multistring, int mouseX, int mouseY) {
		 return Arrays.asList(multistring);
	}

	/**
	 * Draw the label of a fluid dedicated tank content
	 * 
	 * @param tank
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public static List<String> drawGasTankInfo(FluidTank tank, int mouseX, int mouseY) {
		int fluidAmount = 0;
		if(tank.getFluid() != null){
			fluidAmount = tank.getFluidAmount();
		}
		String quantity = translateMC(fluidAmount) + "/" + translateMC(tank.getCapacity()) + " " + gasUnit();
		String liquid = TextFormatting.GRAY + "Empty";
		if(tank.getFluid() != null){
			liquid = TextFormatting.BOLD + tank.getFluid().getLocalizedName();
		}
		return Arrays.asList(liquid, quantity);
	}

	/**
	 * Draw the label of a gas dedicated tank content
	 * 
	 * @param tank
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public static List<String> drawFluidTankInfo(FluidTank tank, int mouseX, int mouseY) {
		int fluidAmount = 0;
		if(tank.getFluid() != null){
			fluidAmount = tank.getFluidAmount();
		}
		String quantity = fluidAmount + "/" + tank.getCapacity() + " mb ";
		String liquid = TextFormatting.GRAY + "Empty";
		if(tank.getFluid() != null){
			liquid = TextFormatting.BOLD + tank.getFluid().getLocalizedName();
		}
		return Arrays.asList(liquid, quantity);
	}

	/**
	 * Draw the label of a fluid tank content with additional info
	 * 
	 * @param tank
	 * @param consumes
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public static List<String> drawTankInfoWithConsume(FluidTank tank, int consumes, int mouseX, int mouseY) {
		int fluidAmount = 0;
		if(tank.getFluid() != null){fluidAmount = tank.getFluidAmount();}
		String quantity = fluidAmount + "/" + tank.getCapacity() + " mb ";
		String liquid = TextFormatting.GRAY + "Empty";
		String cons = "";
		if(tank.getFluid() != null){liquid = TextFormatting.BOLD + tank.getFluid().getLocalizedName();}
		if(consumes > 0){
			cons = TextFormatting.YELLOW + "Consumes " + consumes + "mB";
		}
		return Arrays.asList(liquid, quantity, cons);
	}

	/**
	 * Draw the fuel status
	 * 
	 * @param fuelGated
	 * @param hasFuelBlend
	 * @param canInduct
	 * @param isPermanentInduction
	 * @return
	 */
    public static String[] handleFuelStatus(boolean fuelGated, boolean hasFuelBlend, boolean canInduct, boolean isPermanentInduction) {		
		String fuelCaption = TextFormatting.GRAY + "Fuel:";
		String inductionCaption = TextFormatting.GRAY + "Induction:";

		String fuelStatus = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.GOLD + "Free";
		if(fuelGated){
			fuelStatus = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.GOLD + "Gated";
		}

		String fuelType = TextFormatting.DARK_GRAY + "Type: " + TextFormatting.YELLOW + "Common";
		if(hasFuelBlend){
			fuelType = TextFormatting.DARK_GRAY + "Type: " + TextFormatting.YELLOW + "Blend";
		}

		String indString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.RED + "OFF";
		if(canInduct){
			indString = TextFormatting.DARK_GRAY + "Status: " + TextFormatting.RED + "ON";
		}

		String permaString = TextFormatting.DARK_GRAY + "Type: " + TextFormatting.DARK_RED + "Moveable";
		if(isPermanentInduction){
			permaString = TextFormatting.DARK_GRAY + "Type: " + TextFormatting.DARK_RED + "Permanent";
		}

		return new String[]{fuelCaption, fuelType, fuelStatus, "", inductionCaption, permaString, indString};
	}

    /**
     * Compose a gas tank content label from fluid values
     * 
     * @param tank
     * @return
     */
    public static String composeGasContent(FluidTank tank){
    	return tank.getFluid().getLocalizedName() + " (" + translateMC(tank.getFluidAmount()) + "/" + translateMC(tank.getCapacity()) + " " + gasUnit() + ")";
    }
    
    /**
     * Compose a fluid tank content label from fluid values
     * 
     * @param tank
     * @return
     */
    public static String composeFluidContent(FluidTank tank){
    	return tank.getFluid().getLocalizedName() + " (" + tank.getFluidAmount() + "/" + tank.getCapacity() + " " + fluidUnit() + ")";
    }

/*
 * -------- RENDER --------
 */

    /**
     * Render a fluid filling bar on a gui
     * 
     * @param fluid
     * @param amount
     * @param capacity
     * @param i
     * @param j
     * @param w
     * @param h
     */
	public static void renderFluidBar(FluidStack fluid, int amount, int capacity, int i, int j, int w, int h) {
		if(amount > 5){
			RenderFluidUtils.bindBlockTexture();
			RenderFluidUtils.renderGuiTank(fluid, capacity, amount, i, j, w, h);
		}
	}

    /**
     * Converts millibuckets into gasUnit values
     * 
     * @param amount
     * @return
     */
	public static String translateMC(int amount) {
		double value = amount;
	    DecimalFormat decForm = new DecimalFormat("0,000", new DecimalFormatSymbols());
	    decForm.setRoundingMode(RoundingMode.CEILING);
		return decForm.format(value);		
	}

	/**
	 * Rounds float values to the first decimal
	 * 
	 * @param amount
	 * @return
	 */
	public static String floatTranslate(float amount) {
		double value = amount;
	    DecimalFormat decForm = new DecimalFormat("0,0", new DecimalFormatSymbols());
	    decForm.setRoundingMode(RoundingMode.CEILING);
		return decForm.format(value);		
	}

	/**
	 * Rounds a float to its first decimal
	 * 
	 * @param amount
	 * @return
	 */
	public static float floatRounder(float amount) {
		return Math.round(amount * 10F) / 10F;
	}


}