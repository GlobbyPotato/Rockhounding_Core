package rockhounding.api.machines;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.oredict.OreDictionary;

public class IReciper {

/**
 * ROCKHOUNDING: CHEMISTRY   -   LEACHING VAT
 * The Analyzer extracts extracts a mineral shard from a pool of shards composing a mineral category
 */

	/**
	 * Adds a custom recipe to the Leaching Vat.
	 * The input can be any itemstack. The output will another itemstack.
	 * 
	 * @param inputStack : the input itemstack
	 * @param outputStack : the output itemstack
	 */
	protected static void sendToAnalyzer(ItemStack inputStack, ItemStack outputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		NBTTagList elementList = new NBTTagList();
		NBTTagList probabilityList = new NBTTagList();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
        NBTTagCompound tagElements = new NBTTagCompound();
        outputStack.writeToNBT(tagElements);
        elementList.appendTag(tagElements);
		recipe.setTag("Elements", elementList);
		NBTTagCompound tagQuantity = new NBTTagCompound();
		tagQuantity.setInteger("Probability0", 100);
		probabilityList.appendTag(tagQuantity);
		recipe.setTag("Probabilities", probabilityList);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "addToAnalyzer", recipe);
	}

	/**
	 * Adds a custom recipe to the Leaching Vat.
	 * The input can be any itemstack. The output is an array of extractible elements depending on a each element specific gravity.
	 * 
	 * @param inputStack : the input itemstack
	 * @param elements : the list of elements extractible from the the input itemstack
	 * @param gravity : the list of gravity for each element (multiplied x100, i.e. 3.76 must be written as 376). Alternatively the list of probability of each element to be extracted
	 * @param hasGravity : true if the recipe must be processed considering the specific gravity
	 */
	protected static void sendToAnalyzer(ItemStack inputStack, List<ItemStack> elements, List<Integer> gravity, boolean hasGravity) {
		if(elements.size() == gravity.size()){
			NBTTagCompound input = new NBTTagCompound(); 
			NBTTagCompound recipe = new NBTTagCompound();
			NBTTagList elementList = new NBTTagList();
			NBTTagList probabilityList = new NBTTagList();
			inputStack.writeToNBT(input);
			recipe.setTag("Input", input);
			for(int i = 0; i < elements.size(); i++){
                NBTTagCompound tagElements = new NBTTagCompound();
                elements.get(i).writeToNBT(tagElements);
                elementList.appendTag(tagElements);
			}
			recipe.setTag("Elements", elementList);
			for(int i = 0; i < gravity.size(); i++){
				if(gravity.get(i) > 0){
					NBTTagCompound tagQuantity = new NBTTagCompound();
					tagQuantity.setInteger("Probability" + i, gravity.get(i));
					probabilityList.appendTag(tagQuantity);
				}
			}
			recipe.setTag("Probabilities", probabilityList);
			recipe.setBoolean("Gravity", hasGravity);
			FMLInterModComms.sendMessage("rockhounding_chemistry", "addToAnalyzer", recipe);
		}
	}

	/**
	 * Removes a recipe from the Leaching Vat.
	 * 
	 * @param inputStack : the itemstack no longer allowed to be processed
	 */
	protected static void removeFromAnalyzer(ItemStack inputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "removeFromAnalyzer", recipe);
	}



/**
 * ROCKHOUNDING: CHEMISTRY   -   CHEMICAL EXTRACTOR
 * The Chemical Extractor produces element dusts from the inputted itemstack.
 */

	/**
	 * Adds a custom recipe to the Chemical Extractor.
	 * The input includes an array with the elements composing the itemstack and an array with the percentage of each element
	 * 
	 * @param category : the category to which the input itemstack belongs
	 * @param inputStack : the input itemstack
	 * @param elements : the list of elements composing the itemstack
	 * @param quantities : the list of percentage of each element added in the previous array
	 */
	protected static void sendToExtractor(String category, ItemStack inputStack, List<String> elements, List<Integer> quantities) {
		if(elements.size() == quantities.size()){
			NBTTagCompound input = new NBTTagCompound(); 
			NBTTagCompound recipe = new NBTTagCompound();
			NBTTagList elementList = new NBTTagList();
			NBTTagList quantityList = new NBTTagList();
			recipe.setString("Category", category);
			inputStack.writeToNBT(input);
			recipe.setTag("Input", input);
			for(int i = 0; i < elements.size(); i++){
				if(!elements.get(i).matches("")){
					NBTTagCompound tagElements = new NBTTagCompound();
					tagElements.setString("Element" + i, elements.get(i));
					elementList.appendTag(tagElements);
				}
			}
			recipe.setTag("Elements", elementList);
			for(int i = 0; i < quantities.size(); i++){
				if(quantities.get(i) > 0){
					NBTTagCompound tagQuantity = new NBTTagCompound();
					tagQuantity.setInteger("Quantity" + i, quantities.get(i));
					quantityList.appendTag(tagQuantity);
				}
			}
			recipe.setTag("Quantities", quantityList);
			FMLInterModComms.sendMessage("rockhounding_chemistry", "addToExtractor", recipe);
		}
	}

	/**
	 * Inhibits one or more elements from the Chemical Extractor.
	 * 
	 * @param elements : the list of elements inhibited from extraction
	 */
	protected static void inhibitFromExtractor(List<String> elements) {
		NBTTagList elementList = new NBTTagList();
		NBTTagCompound recipe = new NBTTagCompound();
		for(int i = 0; i < elements.size(); i++){
			if(!elements.get(i).matches("")){
				NBTTagCompound tagElements = new NBTTagCompound();
				tagElements.setString("Element" + i, elements.get(i));
				elementList.appendTag(tagElements);
			}
		}
		recipe.setTag("Elements", elementList);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "inhibitFromExtractor", recipe);
	}



/**
 * ROCKHOUNDING: CHEMISTRY   -   LAB OVEN
 * The Lab Oven produces fluids by the combination of an itemstack and one or two fluids
 */

	/**
	 * Adds a custom fluid recipe to the Lab Oven.
	 * 
	 * @param soluteStack : the input itemstack representing the solid ingredient 
	 * @param isCatalyst : if the solute will be treated as a catalyst
	 * @param solventA : the main fluid solvent. This must be always used
	 * @param solventB : the secondary fluid solvet. It can be null if not used
	 * @param outputFluid : the output fluid
	 */
	protected static void sendToOven(ItemStack soluteStack, boolean isCatalyst, FluidStack solventA, FluidStack solventB, FluidStack outputFluid) {
		NBTTagCompound solute = new NBTTagCompound(); 
		NBTTagCompound solvent1 = new NBTTagCompound();
		NBTTagCompound solvent2 = new NBTTagCompound();
		NBTTagCompound solution = new NBTTagCompound();
		NBTTagCompound recipe = new NBTTagCompound();
		soluteStack.writeToNBT(solute);
		recipe.setTag("Solute", solute);
		recipe.setBoolean("Catalyst", isCatalyst);
		solventA.writeToNBT(solvent1);
		recipe.setTag("Solvent1", solvent1);
		if(solventB != null){
			solventB.writeToNBT(solvent2);
			recipe.setTag("Solvent2", solvent2);
		}
		outputFluid.writeToNBT(solution);
		recipe.setTag("Solution", solution);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "addToOven", recipe);
	}

	/**
	 * Adds a custom fluid recipe to the Lab Oven.
	 * 
	 * @param soluteStack : the input itemstack representing the solid ingredient 
	 * @param isCatalyst : if the solute will be treated as a catalyst
	 * @param solvent : the fluid solvent.
	 * @param outputFluid : the output fluid
	 */
	protected static void sendToOven(ItemStack soluteStack, boolean isCatalyst, FluidStack solvent, FluidStack outputFluid) {
		NBTTagCompound solute = new NBTTagCompound(); 
		NBTTagCompound solvent1 = new NBTTagCompound();
		NBTTagCompound solution = new NBTTagCompound();
		NBTTagCompound recipe = new NBTTagCompound();
		soluteStack.writeToNBT(solute);
		recipe.setTag("Solute", solute);
		recipe.setBoolean("Catalyst", isCatalyst);
		solvent.writeToNBT(solvent1);
		recipe.setTag("Solvent1", solvent1);
		outputFluid.writeToNBT(solution);
		recipe.setTag("Solution", solution);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "addToOven", recipe);
	}

	/**
	 * Removes a fluid recipe from the Lab Oven.
	 * 
	 * @param outputFluid : the output fluid to remove. Amount will be ignored
	 */
	protected static void removeFromOven(FluidStack outputFluid) {
		NBTTagCompound solution = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		outputFluid.writeToNBT(solution);
		recipe.setTag("Solution", solution);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "removeFromOven", recipe);
	}


/**
 * ROCKHOUNDING: CHEMISTRY   -   MINERAL SIZER
 * The Mineral Sizer is meant to be a crusher to obtain a crushed product from an input object.
 * By default the machine has an additional hardcoded function for internal uses in the Rockhounding mod.
 */

	/**
	 * Adds a custom recipe to the Mineral Sizer. No sizing methods applied will be applier
	 * 
	 * @param inputStack : the input itemstack
	 * @param outputStack : the output itemstack
	 */
	protected static void sendToSizer(ItemStack inputStack, ItemStack outputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		NBTTagList elementList = new NBTTagList();
		NBTTagList probabilityList = new NBTTagList();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
        NBTTagCompound tagElements = new NBTTagCompound();
        outputStack.writeToNBT(tagElements);
        elementList.appendTag(tagElements);
		recipe.setTag("Elements", elementList);
		NBTTagCompound tagQuantity = new NBTTagCompound();
		tagQuantity.setInteger("Probability0", 100);
		probabilityList.appendTag(tagQuantity);
		recipe.setTag("Probabilities", probabilityList);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "addToSizer", recipe);
	}

	/**
	 * Adds a custom recipe to the Mineral Sizer using the sizing chance.
	 * 
	 * @param inputStack : the input itemstack
	 * @param elements : the list of elements extractible from the the input itemstack
	 * @param values : the list of probability of each element to be extracted or the comminution
	 * @param hasComminution : determine if the sizing method will be randomized (false) or by comminution (true)
	 */
	protected static void sendToSizer(ItemStack inputStack, List<ItemStack> elements, List<Integer> probability, boolean hasComminution) {
		if(elements.size() == probability.size()){
			NBTTagCompound input = new NBTTagCompound(); 
			NBTTagCompound recipe = new NBTTagCompound();
			NBTTagList elementList = new NBTTagList();
			NBTTagList probabilityList = new NBTTagList();
			inputStack.writeToNBT(input);
			recipe.setTag("Input", input);
			for(int i = 0; i < elements.size(); i++){
                NBTTagCompound tagElements = new NBTTagCompound();
                elements.get(i).writeToNBT(tagElements);
                elementList.appendTag(tagElements);
			}
			recipe.setTag("Elements", elementList);
			for(int i = 0; i < probability.size(); i++){
				if(probability.get(i) > 0){
					NBTTagCompound tagQuantity = new NBTTagCompound();
					tagQuantity.setInteger("Probability" + i, probability.get(i));
					probabilityList.appendTag(tagQuantity);
				}
			}
			recipe.setTag("Probabilities", probabilityList);
			FMLInterModComms.sendMessage("rockhounding_chemistry", "addToSizer", recipe);
		}
	}

	/**
	 * Removes a recipe from the Mineral Sizer.
	 * 
	 * @param inputStack : the object being crushed
	 */
	protected static void removeFromSizer(ItemStack inputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "removeFromSizer", recipe);
	}


/**
 * ROCKHOUNDING: CHEMISTRY   -   SEASONING RACK
 * The Seasoning Rack is a processing machine, returning an itemstack from another at the input.
 * By default the machine has an additional hardcoded salt processing for internal uses in the Rockhounding mod.
 */

	/**
	 * Adds a custom recipe to the Seasoning Rack.
	 * 
	 * @param inputStack : the itemstack being processed
	 * @param outputStack : the resulting itemstack
	 */
	protected static void sendToSeasoner(ItemStack inputStack, ItemStack outputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound output = new NBTTagCompound();		
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "addToSeasoner", recipe);
	}

	/**
	 * Remove an itemstack the list of allowed currency
	 * 
	 * @param inputStack : the itemstack being processed
	 */
	protected static void removeFromSeasoner(ItemStack inputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "removeFromSeasoner", recipe);
	}


/**
 * ROCKHOUNDING: CHEMISTRY   -   METAL ALLOYER
 * The Metal Alloyer produces alloyed ingots from mixing an array of dusts.
 */

	/**
	 * Adds a custom recipe to the Metal Alloyer.
	 * The input is an array of 1 to 6 strings representing the oredict of the single dust and their quantity in the alloy.
	 * The order is fixed, so an alloy can have the same ingredients. Just one difference in the arrays will make it unique.
	 * 
	 * @param displayName : the name of the alloy that will be shown in the machine selector
	 * @param dusts : the array of the oredicted dusts in their desired order
	 * @param quantities : the quantity of each element of the previous array
	 * @param ingotStack : the outputted alloy result.
	 * @param scrapStack : the randomized optinal waste resulting from the alloy. It can be null if not used
	 */
	protected static void sendToAlloyer(String displayName, List<String> dusts, List<Integer> quantities, ItemStack ingotStack, ItemStack scrapStack) {
		if(dusts.size() == quantities.size()){
			NBTTagCompound recipe = new NBTTagCompound();
			NBTTagCompound ingot = new NBTTagCompound();
			NBTTagCompound scrap = new NBTTagCompound();
			NBTTagList dustList = new NBTTagList();
			NBTTagList quantityList = new NBTTagList();
			recipe.setString("Display", displayName);
			for(int i = 0; i < dusts.size(); i++){
				if(!dusts.get(i).matches("")){
					NBTTagCompound tagDust = new NBTTagCompound();
					tagDust.setString("Dust" + i, dusts.get(i));
					dustList.appendTag(tagDust);
				}
			}
			recipe.setTag("Dusts", dustList);
			for(int i = 0; i < quantities.size(); i++){
				if(quantities.get(i) > 0){
					NBTTagCompound tagQuantity = new NBTTagCompound();
					tagQuantity.setInteger("Quantity" + i, quantities.get(i));
					quantityList.appendTag(tagQuantity);
				}
			}
			recipe.setTag("Quantities", quantityList);
			ingotStack.writeToNBT(ingot);
			recipe.setTag("Ingot", ingot);
			if(scrapStack != null){
				scrapStack.writeToNBT(scrap);
				recipe.setTag("Scrap", scrap);
			}
			FMLInterModComms.sendMessage("rockhounding_chemistry", "addToAlloyer", recipe);
		}
	}
	
	/**
	 * Adds a custom recipe to the Metal Alloyer.
	 * The input is an array of 1 to 6 strings representing the oredict of the single dust and their quantity in the alloy.
	 * The order is fixed, so an alloy can have the same ingredients. Just one difference in the arrays will make it unique.
	 * 
	 * @param displayName : the name of the alloy that will be shown in the machine selector
	 * @param dusts : the array of the oredicted dusts in their desired order
	 * @param quantities : the quantity of each element of the previous array
	 * @param ingotStack : the outputted alloy result.
	 */
	protected static void sendToAlloyer(String displayName, List<String> dusts, List<Integer> quantities, ItemStack ingotStack) {
		if(dusts.size() == quantities.size()){
			NBTTagCompound recipe = new NBTTagCompound();
			NBTTagCompound ingot = new NBTTagCompound();
			NBTTagList dustList = new NBTTagList();
			NBTTagList quantityList = new NBTTagList();
			recipe.setString("Display", displayName);
			for(int i = 0; i < dusts.size(); i++){
				if(!dusts.get(i).matches("")){
					NBTTagCompound tagDust = new NBTTagCompound();
					tagDust.setString("Dust" + i, dusts.get(i));
					dustList.appendTag(tagDust);
				}
			}
			recipe.setTag("Dusts", dustList);
			for(int i = 0; i < quantities.size(); i++){
				if(quantities.get(i) > 0){
					NBTTagCompound tagQuantity = new NBTTagCompound();
					tagQuantity.setInteger("Quantity" + i, quantities.get(i));
					quantityList.appendTag(tagQuantity);
				}
			}
			recipe.setTag("Quantities", quantityList);
			ingotStack.writeToNBT(ingot);
			recipe.setTag("Ingot", ingot);
			FMLInterModComms.sendMessage("rockhounding_chemistry", "addToAlloyer", recipe);
		}
	}

	/**
	 * Removes a recipe from the Metal Alloyer.
	 * 
	 * @param ingotStack : the outputted alloy result.
	 */
	protected static void removeFromAlloyer(ItemStack ingotStack) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		ingotStack.writeToNBT(output);
		recipe.setTag("Ingot", output);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "removeFromAlloyer", recipe);
	}


/**
 * ROCKHOUNDING: CHEMISTRY   -   DEPOSITION CHAMBER
 * The Deposition Chamber enriches an element into another compound
 */

	/**
	 * Adds a custom recipe to the Deposition Chamber.
	 * Max fluid amount can be 10000mB. Temperature can be any integer up to 3000, pressure can be any integer up to 32000.
	 * 
	 * @param inputStack : the input itemstack
	 * @param solvent : the fluid solvent. The amount will count for the recipe
	 * @param outputStack : the output itemstack
	 * @param temperature : the working temperature
	 * @param pressure : the wotking pressure
	 */
	protected static void sendToDeposition(ItemStack inputStack, ItemStack outputStack, FluidStack solventStack, int temperature, int pressure) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound solvent = new NBTTagCompound();
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		solventStack.writeToNBT(solvent);
		recipe.setTag("Solvent", solvent);
		recipe.setInteger("Temperature", temperature > 3000 ? temperature : 3000);
		recipe.setInteger("Pressure", pressure > 32000 ? pressure : 32000);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "addToDeposition", recipe);
	}

	/**
	 * Removes a recipe from the Deposition Chamber.
	 * 
	 * @param outputStack : the outputted result.
	 */
	protected static void removeFromDeposition(ItemStack outputStack) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "removeFromDeposition", recipe);
	}


	
/**
 * ROCKHOUNDING: CHEMISTRY   -   CASTING BENCH
 * The Casting bench produces several metal furnitures for the mod
 */

	/**
	 * Adds a custom recipe to the Casting bench.
	 * Left-click to scroll the available patterns, right-click to add/remove the recipe items 
	 * 
	 * @param input : the OreDictionary String related to the input material
	 * @param outputStack : the output itemstack
	 * @param pattern : the numeric code of the required pattern. 0-Coils, 1-Rods, 2-Foils, 3-Arm, 4-Casing, 5-Customized
	 */
	protected static void sendToCasting(String input, ItemStack outputStack, int pattern) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		recipe.setString("Input", input);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		recipe.setInteger("Pattern", pattern);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "addToCasting", recipe);
	}

	/**
	 * Removes a recipe from the Casting bench.
	 * 
	 * @param outputStack : the output itemstack
	 */
	protected static void removeFromCasting(ItemStack outputStack) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "removeFromCasting", recipe);
	}

	
	
/**
 * ROCKHOUNDING: CHEMISTRY   -   LAB BLENDER
 * The Lab Blender refines solid composts used by the mod
 */

	/**
	 * Adds a custom recipe to the Lab Blender via oredict.
	 * 
	 * @param oredict : the list of oredicted ingredients
	 * @param quantity : the amount of each ingredient required
	 * @param outputStack : the output itemstack. The stacksize is considered too
	 */
	protected static void sendToBlender(List<String> oredict, List<Integer> quantity, ItemStack outputStack) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		NBTTagList elementList = new NBTTagList();
		int ingrNum = 0;
		for(int i = 0; i < oredict.size(); i++){
			String ingr = oredict.get(i);
			List<ItemStack> ores = OreDictionary.getOres(ingr);
			if(ores.size() > 0){
				ItemStack entryStack = ores.get(0);
				entryStack.stackSize = quantity.get(i);
				NBTTagCompound tagElements = new NBTTagCompound();
				entryStack.writeToNBT(tagElements);
				elementList.appendTag(tagElements);
				ingrNum++;
			}
		}
		recipe.setTag("Elements", elementList);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		if(ingrNum == oredict.size()){
			FMLInterModComms.sendMessage("rockhounding_chemistry", "addToBlender", recipe);
		}
	}

	/**
	 * Adds a custom recipe to the Lab Blender via itemstack.
	 * 
	 * @param inputStack : the list of ingredient itemstacks
	 * @param outputStack : the output itemstack. The stacksize is considered too
	 */
	protected static void sendToBlender(List<ItemStack> inputStack, ItemStack outputStack) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		NBTTagList elementList = new NBTTagList();
		for(int i = 0; i < inputStack.size(); i++){
            NBTTagCompound tagElements = new NBTTagCompound();
            inputStack.get(i).writeToNBT(tagElements);
            elementList.appendTag(tagElements);
		}
		recipe.setTag("Elements", elementList);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "addToBlender", recipe);
	}

	/**
	 * Removes a recipe from the Lab Blender.
	 * 
	 * @param outputStack : the output itemstack
	 */
	protected static void removeFromBlender(ItemStack outputStack) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_chemistry", "removeFromBlender", recipe);
	}

	
	
/**
 * ROCKHOUNDING: SURFACE   -   WOOD INCUBATOR
 * The Wood Incubator transforms an object into another by the effect of a solid and a fluid ingredient.
 */

	/**
	 * Adds a custom recipe to the Wood Incubator.
	 * 
	 * @param inputStack : the object to be transformed
	 * @param soluteStack : the solid ingredient
	 * @param canSoluteOredict : if the solute can include oredicted equivalents
	 * @param solventStack : the fluid ingredient
	 * @param outputStack : the final object
	 */
	protected static void sendToIncubator(ItemStack inputStack, ItemStack soluteStack, boolean canSoluteOredict, FluidStack solventStack, ItemStack outputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound solute = new NBTTagCompound(); 
		NBTTagCompound solvent = new NBTTagCompound();
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		soluteStack.writeToNBT(solute);
		recipe.setTag("Solute", solute);
		recipe.setBoolean("Oredict", canSoluteOredict);
		solventStack.writeToNBT(solvent);
		recipe.setTag("Solvent", solvent);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_surface", "addToIncubator", recipe);
	}

	/**
	 * Removes a recipe from the Wood Incubator.
	 * 
	 * @param outputStack : the final object
	 */
	protected static void removeFromIncubator(ItemStack outputStack) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_surface", "removeFromIncubator", recipe);
	}


/**
 * ROCKHOUNDING: SURFACE   -   COMPOST BIN
 * The Compost Bin produces a compost item used for the Rockhounding mod purposes.
 */

	/**
	 * Adds a custom recipe to the Compost Bin
	 * 
	 * @param inputStack : the object being composted
	 * @param canInputOredict : if the input can include oredicted equivalents
	 * 
	 */
	protected static void sendToCompost(ItemStack inputStack, boolean canInputOredict) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		recipe.setBoolean("Oredict", canInputOredict);
		FMLInterModComms.sendMessage("rockhounding_surface", "addToCompost", recipe);
	}

	/**
	 * description : Removes a recipe from the Compost Bin.
	 * 
	 * @param inputStack : object no longer allowed to be processed
	 */
	protected static void removeFromCompost(ItemStack inputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		FMLInterModComms.sendMessage("rockhounding_surface", "removeFromCompost", recipe);
	}


/**
 * ROCKHOUNDING: ORE TIERS   -   BLOOMERY
 * The Bloomery can separately melt an ore into its molten form and then cast the fluid into an ingot.
 */

	/**
	 * Adds a custom recipe to the Bloomery.
	 * 
	 * @param inputStack : ore to be smelted
	 * @param moltenStack : the fluid obtained melting the ore
	 * @param outputStack : the item obtained casting the fluid
	 */
	protected static void sendToBloomery(ItemStack inputStack, FluidStack moltenStack, ItemStack outputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound molten = new NBTTagCompound();
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		moltenStack.writeToNBT(molten);
		recipe.setTag("Molten", molten);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_oretiers", "addToBloomery", recipe);
	}

	/**
	 * Remove a recipe from the Bloomery casted items
	 * 
	 * @param inputStack : ore to be smelted
	 */
	protected static void removeFromBloomery(ItemStack inputStack) {
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(output);
		recipe.setTag("Input", output);
		FMLInterModComms.sendMessage("rockhounding_oretiers", "removeFromBloomery", recipe);
	}


/**
 * ROCKHOUNDING: ORE TIERS   -   BASIC COAL REFINERY
 * The Basic coal refinery allows to improve coals used by the mod into better coals.
 */
	/**
	 * Adds a custom recipe to the Seasoning Rack.
	 * 
		 * @param inputStack : coal to be improved
		 * @param outputStack : improved coal
	 */
	protected static void sendToRefinery(ItemStack inputStack, ItemStack outputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound output = new NBTTagCompound();		
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_oretiers", "addToRefinery", recipe);
	}

	/**
	 * Removes a recipe from the refiner
	 * 
		 * @param inputStack : coal to be improved
	 */
	protected static void removeFromRefinery(ItemStack inputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		FMLInterModComms.sendMessage("rockhounding_oretiers", "removeFromRefinery", recipe);
	}


/**
 * ROCKHOUNDING: ORE TIERS   -   PEAT DRYING PALLET
 * The Peat drying pallet allows to dry moist peat chunks into the proper coal.
 */
	/**
	 * Adds a custom recipe to the pallet.
	 * 
		 * @param inputStack : item to be improved
		 * @param outputStack : improved item
	 */
	protected static void sendToPallet(ItemStack inputStack, ItemStack outputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound output = new NBTTagCompound();		
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_oretiers", "addToPallet", recipe);
	}

	/**
	 * Removes a recipe from the pallet
	 * 
		 * @param inputStack : item to be improved
	 */
	protected static void removeFromPallet(ItemStack inputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		FMLInterModComms.sendMessage("rockhounding_oretiers", "removeFromPallet", recipe);
	}


/**
 * ROCKHOUNDING: ROCKS   -   ROCKS VENDING SYSTEM
 * The Rocks Vending Systems allows to purchase the rocks provided by the mod
 */

	/**
	 * Add a custom fee item to the Rocks Vending System
	 * 
	 * @param inputStack : the itemstack allowed to be used as currency
	 * @param canInputOredict : if the iput can include oredicted equivalents
	 * @param outputStacksize : how many rocks can be purchased with one item
	 */
	protected static void sendToVendor(ItemStack inputStack, boolean canInputOredict, int outputStacksize) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		recipe.setBoolean("Oredict", canInputOredict);
		recipe.setInteger("Stacksize", outputStacksize);
		FMLInterModComms.sendMessage("rockhounding_rocks", "addToVendor", recipe);
	}

	/**
	 * Remove an itemstack the list of allowed currency
	 * 
	 * @param inputStack : the itemstack to remove from the list of allowed currency
	 */
	protected static void removeFromVendor(ItemStack inputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		FMLInterModComms.sendMessage("rockhounding_rocks", "removeFromVendor", recipe);
	}



/**
 * ROCKHOUNDING: ROCKS   -   ROCKS VENDING SYSTEM
 * The Rocks Vending Systems allows to purchase the rocks provided by the mod
 */

	/**
	 * Add a custom recipe to the Cutting Station
	 * 
	 * @param inputStack : the block that will be cut
	 * @param cutCode : The code of the cut being performed (see gui switches). Zero if no code matches
	 * @param outputStack : the resulting cut block
	 */
	protected static void sendToCutting(ItemStack inputStack, int cutCode, ItemStack outputStack) {
		NBTTagCompound input = new NBTTagCompound(); 
		NBTTagCompound output = new NBTTagCompound(); 
		NBTTagCompound recipe = new NBTTagCompound();
		inputStack.writeToNBT(input);
		recipe.setTag("Input", input);
		recipe.setInteger("Code", cutCode);
		outputStack.writeToNBT(output);
		recipe.setTag("Output", output);
		FMLInterModComms.sendMessage("rockhounding_rocks", "addToCutting", recipe);
	}

}