package rockhounding.api;

import java.util.List;

import com.google.common.base.Strings;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class IReciper extends IReciperUtils{
	
/**
 * ROCKHOUNDING: CHEMISTRY
 */

//POLLUTANT GASES
		/**
		 * Adds a gas to the list of pollutant gases
		 * @param gas : the pollutant gas
		 */
		public static void sendToPollutantGases(FluidStack gas) {
			if(isValidGas(gas)){
				NBTTagCompound recipe = new NBTTagCompound();

				NBTTagCompound bathNBT = new NBTTagCompound();
				gas.writeToNBT(bathNBT);
				recipe.setTag(tagInput, bathNBT);

				FMLInterModComms.sendMessage(rh_chemistry_id, add_pollutant_gas_key, recipe);
			}
		}

		/**
		 * Removes a gas from the list of pollutant gases
		 * @param gas : the pollutant gas
		 */
		public static void removeFromPollutantGases(FluidStack gas) {
			if(isValidGas(gas)){
				NBTTagCompound recipe = new NBTTagCompound();

				NBTTagCompound bathNBT = new NBTTagCompound(); 
				gas.writeToNBT(bathNBT);
				recipe.setTag(tagInput, bathNBT);

				FMLInterModComms.sendMessage(rh_chemistry_id, remove_pollutant_gas_key, recipe);
			}
		}



//POLLUTANT FLUIDS
		/**
		 * Adds a fluid to the list of pollutant fluids
		 * @param bath : the pollutant fluid
		 */
		public static void sendToPollutantFluids(FluidStack bath) {
			if(isValidFluid(bath)){
				NBTTagCompound recipe = new NBTTagCompound();

				NBTTagCompound bathNBT = new NBTTagCompound();
				bath.writeToNBT(bathNBT);
				recipe.setTag(tagInput, bathNBT);

				FMLInterModComms.sendMessage(rh_chemistry_id, add_pollutant_fluid_key, recipe);
			}
		}

		/**
		 * Removes a fluid from the list of pollutant fluids
		 * @param bath : the pollutant fluid
		 */
		public static void removeFromPollutantFluids(FluidStack bath) {
			if(isValidFluid(bath)){
				NBTTagCompound recipe = new NBTTagCompound();

				NBTTagCompound bathNBT = new NBTTagCompound(); 
				bath.writeToNBT(bathNBT);
				recipe.setTag(tagInput, bathNBT);

				FMLInterModComms.sendMessage(rh_chemistry_id, remove_pollutant_fluid_key, recipe);
			}
		}



//SEASONING RACK
		/**
		 * Adds a recipe to the Toxic Mutation
		 * @param input : the input
		 * @param output : the output
		 */
		public static void sendToToxicMutation(ItemStack input, ItemStack output) {
			if(isValidStack(input) && isValidStack(output)){
				NBTTagCompound recipe = new NBTTagCompound();

				NBTTagCompound inputNBT = new NBTTagCompound(); 
				input.writeToNBT(inputNBT);
				recipe.setTag(tagInput, inputNBT);

				NBTTagCompound outputNBT = new NBTTagCompound(); 
				output.writeToNBT(outputNBT);
				recipe.setTag(tagOutput, outputNBT);

				FMLInterModComms.sendMessage(rh_chemistry_id, add_toxic_mutation_key, recipe);
			}
		}

		/**
		 * Removes a recipe from the Toxic Mutation by input
		 * @param input : the input stack
		 */
		public static void removeFromToxicMutationByInput(ItemStack input) {
			if(isValidStack(input)){
				NBTTagCompound recipe = new NBTTagCompound();

				NBTTagCompound inputNBT = new NBTTagCompound(); 
				input.writeToNBT(inputNBT);
				recipe.setTag(tagInput, inputNBT);

				FMLInterModComms.sendMessage(rh_chemistry_id, remove_toxic_mutation_input_key, recipe);
			}
		}

		/**
		 * Removes a recipe from the Toxic Mutation by output
		 * @param output : the output stack
		 */
		public static void removeFromToxicMutationByOutput(ItemStack output) {
			if(isValidStack(output)){
				NBTTagCompound recipe = new NBTTagCompound();

				NBTTagCompound outputNBT = new NBTTagCompound(); 
				output.writeToNBT(outputNBT);
				recipe.setTag(tagOutput, outputNBT);

				FMLInterModComms.sendMessage(rh_chemistry_id, remove_toxic_mutation_output_key, recipe);
			}
		}




//TRANSPOSER
	/**
	 * Adds a recipe to the Transposer
	 * @param input : the fluid or gas input
	 * @param output : the fluid or gas output
	 */
	public static void sendToTransposer(FluidStack input, FluidStack output) {
		if(isValidFluid(input) && isValidFluid(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound bathNBT = new NBTTagCompound();
			input.writeToNBT(bathNBT);
			recipe.setTag(tagInput, bathNBT);

			NBTTagCompound slurryNBT = new NBTTagCompound();
			output.writeToNBT(slurryNBT);
			recipe.setTag(tagOutput, slurryNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_transposer_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Transposer by input
	 * @param input : the input fluid or gas
	 */
	public static void removeFromTransposerByInput(FluidStack input) {
		if(isValidFluid(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound bathNBT = new NBTTagCompound(); 
			input.writeToNBT(bathNBT);
			recipe.setTag(tagInput, bathNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_transposer_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Transposer by output
	 * @param output : the output fluid or gas
	 */
	public static void removeFromTransposerByOutput(FluidStack output) {
		if(isValidFluid(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound slurryNBT = new NBTTagCompound(); 
			output.writeToNBT(slurryNBT);
			recipe.setTag(tagOutput, slurryNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_transposer_output_key, recipe);
		}
	}



//SLURRY POND
	/**
	 * Adds a recipe to the Slurry Pond
	 * @param solute : the solid ingredient
	 * @param bath : the liquid bath
	 * @param slurry : the liquid product
	 */
	public static void sendToSlurryPond(ItemStack solute, FluidStack bath, FluidStack slurry) {
		if(isValidStack(solute) && isValidFluid(bath) && isValidFluid(slurry)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound soluteNBT = new NBTTagCompound(); 
			solute.writeToNBT(soluteNBT);
			recipe.setTag(tagInput, soluteNBT);

			NBTTagCompound bathNBT = new NBTTagCompound();
			bath.writeToNBT(bathNBT);
			recipe.setTag(tagSolvent, bathNBT);

			NBTTagCompound slurryNBT = new NBTTagCompound();
			slurry.writeToNBT(slurryNBT);
			recipe.setTag(tagOutput, slurryNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_slurry_pond_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Slurry Pond by solute
	 * @param solute : the solid ingredient
	 */
	public static void removeFromSlurryPondByInput(ItemStack solute) {
		if(isValidStack(solute)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound soluteNBT = new NBTTagCompound(); 
			solute.writeToNBT(soluteNBT);
			recipe.setTag(tagInput, soluteNBT);
			
			FMLInterModComms.sendMessage(rh_chemistry_id, remove_slurry_pond_solute_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Slurry Pond by bath
	 * @param bath : the liquid bath
	 */
	public static void removeFromSlurryPondByBath(FluidStack bath) {
		if(isValidFluid(bath)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound bathNBT = new NBTTagCompound(); 
			bath.writeToNBT(bathNBT);
			recipe.setTag(tagSolvent, bathNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_slurry_pond_bath_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Slurry Pond by slurry
	 * @param slurry : the liquid product
	 */
	public static void removeFromSlurryPondBySlurry(FluidStack slurry) {
		if(isValidFluid(slurry)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound slurryNBT = new NBTTagCompound(); 
			slurry.writeToNBT(slurryNBT);
			recipe.setTag(tagOutput, slurryNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_slurry_pond_slurry_key, recipe);
		}
	}



//SEASONING RACK
	/**
	 * Adds a recipe to the Seasoning Rack
	 * @param input : the input
	 * @param output : the output
	 */
	public static void sendToSeasoningRack(ItemStack input, ItemStack output) {
		if(isValidStack(input) && isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_seasoning_rack_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Seasoning Rack by input
	 * @param input : the input stack
	 */
	public static void removeFromSeasoningRackByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_seasoning_rack_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Seasoning Rack by output
	 * @param output : the output stack
	 */
	public static void removeFromSeasoningRackByOutput(ItemStack output) {
		if(isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_seasoning_rack_output_key, recipe);
		}
	}



//RETENTION VAT
	/**
	 * Adds a recipe to the Retention Vat
	 * @param input : the fluid input
	 * @param specimen : the product array
	 * @param gravity : the gravity array (min:0; max:34)
	 */
	public static void sendToRetentionVat(FluidStack input, List<ItemStack> specimen, List<Float> gravity) {
		if(isValidFluid(input) && isValidStackArray(specimen) && isNonNegativeFloat(gravity) && arraysMatch(specimen.size(), gravity.size())){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagList elementsTAG = new NBTTagList();
			for(int i = 0; i < specimen.size(); i++){
                NBTTagCompound elementsNBT = new NBTTagCompound();
                specimen.get(i).writeToNBT(elementsNBT);
                elementsTAG.appendTag(elementsNBT);
			}
			recipe.setTag(tagElements, elementsTAG);

			NBTTagList weightTAG = new NBTTagList();
			for(int i = 0; i < gravity.size(); i++){
				if(gravity.get(i) > 0){
					NBTTagCompound weightNBT = new NBTTagCompound();
					weightNBT.setFloat(tagWeights + i, gravity.get(i));
					weightTAG.appendTag(weightNBT);
				}
			}
			recipe.setTag(tagWeights, weightTAG);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_retention_vat_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Retention Vat by input
	 * @param input : the input fluid
	 */
	public static void removeFromRetentionVatByInput(FluidStack input) {
		if(isValidFluid(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_retention_vat_input_key, recipe);
		}
	}



//PROFILING BENCH
	/**
	 * Adds a recipe to the Profiling Bench
	 * @param input : the input
	 * @param output : the output
	 * @param pattern : the casting pattern
	 */
	public static void sendToProfilingBench(ItemStack input, ItemStack output, int pattern) {
		if(isValidStack(input) && isValidStack(output) && pattern > -1){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			recipe.setInteger(tagPattern, pattern);
			FMLInterModComms.sendMessage(rh_chemistry_id, add_profiling_bench_key, recipe);
		}
	}

	/**
	 * Adds a recipe to the Profiling Bench
	 * @param input : the input oredict
	 * @param output : the output
	 * @param pattern : the casting pattern (0:generic, 1:coil, 2:rod, 3:foil, 4:arm, 5:casing, 6:gear, 7:ingot, 8:gauze, 9:coin, 10:plate)
	 */
	public static void sendToProfilingBench(String input, ItemStack output, int pattern) {
		if(isValidString(input) && isValidStack(output) && pattern > -1){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			oredictStack(input).writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			recipe.setInteger(tagPattern, pattern);
			FMLInterModComms.sendMessage(rh_chemistry_id, add_profiling_bench_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Profiling Bench by input
	 * @param input : the input
	 */
	public static void removeFromProfilingBenchByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, remove_profiling_bench_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Profiling Bench by output
	 * @param output : the output
	 */
	public static void removeFromProfilingBenchByOutput(ItemStack output) {
		if(isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, remove_profiling_bench_output_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Profiling Bench by oredict
	 * @param oredict : the input oredict
	 */
	public static void removeFromProfilingBenchByOredict(String oredict) {
		if(isValidString(oredict)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			recipe.setString(tagOredict, oredict);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, remove_profiling_bench_oredict_key, recipe);
		}
	}



//MINERAL SIZER
	/**
	 * Adds a recipe to the Mineral Sizer
	 * @param input : the fluid input
	 * @param gangue : the product array
	 * @param comminution : the comminution array (min:0; max:15)
	 */
	public static void sendToMineralSizer(ItemStack input, List<ItemStack> gangue, List<Integer> comminution) {
		if(isValidStack(input) && isValidStackArray(gangue) && isNonNegativeInteger(comminution) && arraysMatch(gangue.size(), comminution.size())){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagList elementsTAG = new NBTTagList();
			for(int i = 0; i < gangue.size(); i++){
                NBTTagCompound elementsNBT = new NBTTagCompound();
                gangue.get(i).writeToNBT(elementsNBT);
                elementsTAG.appendTag(elementsNBT);
			}
			recipe.setTag(tagElements, elementsTAG);

			NBTTagList weightTAG = new NBTTagList();
			for(int i = 0; i < comminution.size(); i++){
				if(comminution.get(i) > 0){
					NBTTagCompound weightNBT = new NBTTagCompound();
					weightNBT.setInteger(tagWeights + i, comminution.get(i));
					weightTAG.appendTag(weightNBT);
				}
			}
			recipe.setTag(tagWeights, weightTAG);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_mineral_sizer_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Mineral Sizer by input
	 * @param input : the input stack
	 */
	public static void removeFromMineralSizerByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_mineral_sizer_input_key, recipe);
		}
	}



//METAL ALLOYER
	/**
	 * Adds a recipe to the Metal Alloyer
	 * @param element : input oredict array
	 * @param quantity : quantity array
	 * @param output : the output
	 */
	public static void sendToMetalAlloyer(List<String> element, List<Integer> quantity, ItemStack output) {
		if(isValidStack(output) && isValidStringArray(element) && isNonNegativeInteger(quantity) && arraysMatch(element.size(), quantity.size())){
			NBTTagCompound recipe = new NBTTagCompound();
	
			NBTTagList elementTAG = new NBTTagList();
			for(int i = 0; i < element.size(); i++){
				if(!Strings.isNullOrEmpty(element.get(i))){
					NBTTagCompound elementNBT = new NBTTagCompound();
					elementNBT.setString(tagElements + i, element.get(i));
					elementTAG.appendTag(elementNBT);
				}
			}
			recipe.setTag(tagElements, elementTAG);
	
			NBTTagList weightTAG = new NBTTagList();
			for(int i = 0; i < quantity.size(); i++){
				if(quantity.get(i) > 0){
					NBTTagCompound weightNBT = new NBTTagCompound();
					weightNBT.setInteger(tagWeights + i, quantity.get(i));
					weightTAG.appendTag(weightNBT);
				}
			}
			recipe.setTag(tagWeights, weightTAG);
	
			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, add_metal_alloyer_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Metal Alloyer by output
	 * @param output : the output
	 */
	public static void removeFromMetalAlloyerByOutput(ItemStack output) {
		if(isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, remove_metal_alloyer_output_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Metal Alloyer by oredict
	 * @param oredict : the ingredient oredict
	 */
	public static void removeFromMetalAlloyerByOredict(String oredict) {
		if(isValidString(oredict)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			recipe.setString(tagOredict, oredict);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, remove_metal_alloyer_oredict_key, recipe);
		}
	}



//MATERIAL CABINET
	/**
	 * Adds a recipe to the Material Cabinet
	 * @param id : the symbol of the ingredient. Max 2 chars
	 * @param oredict : the oredict of the ingredient
	 * @param display : the display name of the ingredient
	 */
	public static void sendToMaterialCabinet(String id, String oredict, String display) {
		if(isValidString(id) && id.length() <= 2 && isValidString(oredict) && isValidString(display)){
			NBTTagCompound recipe = new NBTTagCompound();

			recipe.setString(tagPattern, id);
			recipe.setString(tagOredict, oredict);
			recipe.setString(tagDisplay, display);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_material_cabinet_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Material Cabinet by oredict
	 * @param oredict : the ingredient oredict
	 */
	public static void removeFromMaterialCabinetByOredict(String oredict) {
		if(isValidString(oredict)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			recipe.setString(tagOredict, oredict);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, remove_material_cabinet_oredict_key, recipe);
		}
	}



//LEACHING VAT
	/**
	 * Adds a recipe to the Leaching Vat
	 * @param input : the fluid input
	 * @param specimen : the product array
	 * @param gravity : the gravity array (min:0; max:15)
	 */
	public static void sendToLeachingVat(ItemStack input, List<ItemStack> specimen, List<Float> gravity, FluidStack leachate) {
		if(isValidStack(input) && isValidStackArray(specimen) && isNonNegativeFloat(gravity) && arraysMatch(specimen.size(), gravity.size())){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagList elementsTAG = new NBTTagList();
			for(int i = 0; i < specimen.size(); i++){
                NBTTagCompound elementsNBT = new NBTTagCompound();
                specimen.get(i).writeToNBT(elementsNBT);
                elementsTAG.appendTag(elementsNBT);
			}
			recipe.setTag(tagElements, elementsTAG);

			NBTTagList weightTAG = new NBTTagList();
			for(int i = 0; i < gravity.size(); i++){
				if(gravity.get(i) > 0){
					NBTTagCompound weightNBT = new NBTTagCompound();
					weightNBT.setFloat(tagWeights + i, gravity.get(i));
					weightTAG.appendTag(weightNBT);
				}
			}
			recipe.setTag(tagWeights, weightTAG);

			NBTTagCompound leachateNBT = new NBTTagCompound(); 
			leachate.writeToNBT(leachateNBT);
			recipe.setTag(tagSolvent, leachateNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_leaching_vat_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Leaching Vat by input
	 * @param input : the input stack
	 */
	public static void removeFromLeachingVatByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_leaching_vat_input_key, recipe);
		}
	}



//LAB OVEN
	/**
	 * Adds a recipe to the Lab Oven. One between solute and catalyst must be always used
	 * @param display : the display name (optional)
	 * @param solute : the solid ingredient (optional)
	 * @param catalyst : consumable catalyst (optional)
	 * @param solvent : the solvent input
	 * @param reagent : the reagent input (optional)
	 * @param solution : the fluid output
	 * @param byproduct : the fluid byproduct (optional)
	 * @param precipitate : the solid byproduct (optional)
	 */
	public static void sendToLabOven(String display, ItemStack solute, ItemStack catalyst, FluidStack solvent, FluidStack reagent, FluidStack solution, FluidStack byproduct) {
		if((isValidStack(solute) || isValidConsumable(catalyst)) && isValidFluid(solvent) && isValidFluid(solution)){
			NBTTagCompound recipe = new NBTTagCompound();

			if(isValidString(display)){
				recipe.setString(tagDisplay, display);
			}
			
			if(isValidStack(solute)){
				NBTTagCompound inputNBT = new NBTTagCompound(); 
				solute.writeToNBT(inputNBT);
				recipe.setTag(tagInput, inputNBT);
			}
			
			if(isValidConsumable(catalyst)){
				NBTTagCompound catalystNBT = new NBTTagCompound(); 
				catalyst.writeToNBT(catalystNBT);
				recipe.setTag(tagPattern, catalystNBT);
			}

			NBTTagCompound solventNBT = new NBTTagCompound();
			solvent.writeToNBT(solventNBT);
			recipe.setTag(tagSolvent, solventNBT);

			if(isValidFluid(reagent)){
				NBTTagCompound reagentNBT = new NBTTagCompound();
				reagent.writeToNBT(reagentNBT);
				recipe.setTag(tagReagent, reagentNBT);
			}
				
			NBTTagCompound solutionNBT = new NBTTagCompound();
			solution.writeToNBT(solutionNBT);
			recipe.setTag(tagOutput, solutionNBT);

			if(isValidFluid(byproduct)){
				NBTTagCompound byproductNBT = new NBTTagCompound();
				byproduct.writeToNBT(byproductNBT);
				recipe.setTag(tagByproduct, byproductNBT);
			}

			FMLInterModComms.sendMessage(rh_chemistry_id, add_lab_oven_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Lab Oven by solute
	 * @param solute : the sold ingredient
	 */
	public static void removeFromLabOvenBySolute(ItemStack solute) {
		if(isValidStack(solute)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound soluteNBT = new NBTTagCompound(); 
			solute.writeToNBT(soluteNBT);
			recipe.setTag(tagInput, soluteNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_lab_oven_solute_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Lab Oven by solvent
	 * @param solvent : the solvent input
	 */
	public static void removeFromLabOvenBySolvent(FluidStack solvent) {
		if(isValidFluid(solvent)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound solventNBT = new NBTTagCompound(); 
			solvent.writeToNBT(solventNBT);
			recipe.setTag(tagSolvent, solventNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_lab_oven_solvent_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Lab Oven by solution
	 * @param solution : the output solution
	 */
	public static void removeFromLabOvenBySolution(FluidStack solution) {
		if(isValidFluid(solution)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound solutionNBT = new NBTTagCompound(); 
			solution.writeToNBT(solutionNBT);
			recipe.setTag(tagOutput, solutionNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_lab_oven_solution_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Lab Oven by precipitate
	 * @param precipitate : the solid byproduct
	 */
	public static void removeFromLabOvenByPrecipitate(ItemStack precipitate) {
		if(isValidStack(precipitate)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound precipitateNBT = new NBTTagCompound(); 
			precipitate.writeToNBT(precipitateNBT);
			recipe.setTag(tagWaste, precipitateNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_lab_oven_precipitate_key, recipe);
		}
	}



// LAB BLENDER
	/**
	 * Adds a recipe to the Lab Blender
	 * @param stacks : array of ingredients
	 * @param output : the output stack
	 */
	public static void sendToLabBlender(List<ItemStack> stacks, ItemStack output) {
		if(isValidStack(output) && isValidStackArray(stacks)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			NBTTagList elementTAG = new NBTTagList();
			for(int i = 0; i < stacks.size(); i++){
				NBTTagCompound elementNBT = new NBTTagCompound();
				stacks.get(i).writeToNBT(elementNBT);
				elementTAG.appendTag(elementNBT);
			}
			recipe.setTag(tagElements, elementTAG);
	
			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, add_lab_blender_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Lab Blender by output
	 * @param output : the output
	 */
	public static void removeFromLabBlenderByOutput(ItemStack output) {
		if(isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);
	
			FMLInterModComms.sendMessage(rh_chemistry_id, remove_lab_blender_output_key, recipe);
		}
	}



//HEAT EXCHANGER
	/**
	 * Adds a recipe to the Heat Exchanger. Both elements must be gaseous
	 * @param input : the input gas
	 * @param output : the output gas
	 */
	public static void sendToHeatExchanger(FluidStack input, FluidStack output) {
		if(isValidGas(input) && isValidGas(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_heat_exchanger_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Heat Exchanger by input
	 * @param input : the input gas
	 */
	public static void removeFromHeatExchangerByInput(FluidStack input) {
		if(isValidGas(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_heat_exchanger_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Heat Exchanger by output
	 * @param output : the output gas
	 */
	public static void removeFromHeatExchangerByOutput(FluidStack output) {
		if(isValidGas(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_heat_exchanger_output_key, recipe);
		}
	}



//REFORMING REACTOR
	/**
	 * Adds a recipe to the Reforming Reactor
	 * @param solvent : the LEFT input gas
	 * @param reagent : the RIGHT input gas
	 * @param output : the output gas
	 * @param catalyst : the specific catalyst
	 */
	public static void sendToReformingReactor(FluidStack solvent, FluidStack reagent, FluidStack output, ItemStack catalyst) {
		if(isValidGas(solvent) && isValidGas(reagent) && isValidGas(output) && isValidStack(catalyst)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound solventNBT = new NBTTagCompound(); 
			solvent.writeToNBT(solventNBT);
			recipe.setTag(tagSolvent, solventNBT);

			NBTTagCompound reagentNBT = new NBTTagCompound(); 
			reagent.writeToNBT(reagentNBT);
			recipe.setTag(tagReagent, reagentNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			NBTTagCompound catalystNBT = new NBTTagCompound(); 
			catalyst.writeToNBT(catalystNBT);
			recipe.setTag(tagPattern, catalystNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_reforming_reactor_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Heat Exchanger by solvent
	 * @param solvent : the LEFT input gas
	 */
	public static void removeFromReformingReactorBySolvent(FluidStack solvent) {
		if(isValidGas(solvent)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			solvent.writeToNBT(inputNBT);
			recipe.setTag(tagSolvent, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_reforming_reactor_solvent_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Heat Exchanger by reagent
	 * @param reagent : the RIGHT input gas
	 */
	public static void removeFromReformingReactorByReagent(FluidStack reagent) {
		if(isValidGas(reagent)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			reagent.writeToNBT(inputNBT);
			recipe.setTag(tagReagent, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_reforming_reactor_reagent_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Heat Exchanger by output
	 * @param output : the output gas
	 */
	public static void removeFromReformingReactorByOutput(FluidStack output) {
		if(isValidGas(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_reforming_reactor_output_key, recipe);
		}
	}



//GAS CONDENSER
	/**
	 * Note: the Expander Chamber uses the same recipes (reversed)
	 * Adds a recipe to the Gas Condenser
	 * @param input : the input gas
	 * @param output : the output liquid
	 */
	public static void sendToGasCondenser(FluidStack input, FluidStack output) {
		if(isValidGas(input) && isValidFluid(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_gas_condenser_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Gas Condenser by input
	 * @param input : the input gas
	 */
	public static void removeFromGasCondenserByInput(FluidStack input) {
		if(isValidGas(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagOutput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_gas_condenser_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Gas Condenser by output
	 * @param output : the output fluid
	 */
	public static void removeFromGasCondenserByOutput(FluidStack output) {
		if(isValidFluid(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_gas_condenser_output_key, recipe);
		}
	}



//GAS PURIFIER
	/**
	 * Adds a recipe to the Gas Purifier
	 * @param input : the input gas
	 * @param output : the output gas
	 * @param slag1 : the main slag (optional)
	 * @param slag2 : the secondary slag (optional)
	 */
	public static void sendToGasPurifier(FluidStack input, FluidStack output, ItemStack slag1, ItemStack slag2) {
		if(isValidGas(input) && isValidGas(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			if(isValidStack(slag1)){
				NBTTagCompound slag1NBT = new NBTTagCompound(); 
				slag1.writeToNBT(slag1NBT);
				recipe.setTag(tagSlag1, slag1NBT);
			}

			if(isValidStack(slag2)){
				NBTTagCompound slag2NBT = new NBTTagCompound(); 
				slag2.writeToNBT(slag2NBT);
				recipe.setTag(tagSlag2, slag2NBT);
			}

			FMLInterModComms.sendMessage(rh_chemistry_id, add_gas_purifier_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Heat Exchanger by input
	 * @param input : the input gas
	 */
	public static void removeFromGasPurifierByInput(FluidStack input) {
		if(isValidGas(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagOutput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_gas_purifier_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Heat Exchanger by output
	 * @param output : the output gas
	 */
	public static void removeFromGasPurifierByOutput(FluidStack output) {
		if(isValidGas(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_gas_purifier_output_key, recipe);
		}
	}



//GASIFICATION PLANT
	/**
	 * Adds a recipe to the Gasification Plant
	 * @param solvent : the main input fluid
	 * @param reagent : the reagent fluid
	 * @param output : the output gas
	 * @param slag1 : the main slag (optional)
	 * @param slag2 : the secondary slag (optional)
	 * @param temperature : the threashold temperature (min:31K; max:2000K)
	 */
	public static void sendToGasificationPlant(FluidStack solvent, FluidStack reagent, FluidStack output, ItemStack slag1, ItemStack slag2, int temperature) {
		if(isValidFluid(solvent) && isValidFluid(reagent) && isValidGas(output) && temperature > 30 && temperature <= 2000){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound solventNBT = new NBTTagCompound(); 
			solvent.writeToNBT(solventNBT);
			recipe.setTag(tagSolvent, solventNBT);

			NBTTagCompound reagentNBT = new NBTTagCompound(); 
			reagent.writeToNBT(reagentNBT);
			recipe.setTag(tagReagent, reagentNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			if(isValidStack(slag1)){
				NBTTagCompound slag1NBT = new NBTTagCompound(); 
				slag1.writeToNBT(slag1NBT);
				recipe.setTag(tagSlag1, slag1NBT);
			}

			if(isValidStack(slag2)){
				NBTTagCompound slag2NBT = new NBTTagCompound(); 
				slag2.writeToNBT(slag2NBT);
				recipe.setTag(tagSlag2, slag2NBT);
			}

			recipe.setInteger(tagTemperature, temperature);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_gasification_plant_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Gasification Plant by solvent
	 * @param solvent : the solvent fluid
	 */
	public static void removeFromGasificationPlantBySolvent(FluidStack solvent) {
		if(isValidFluid(solvent)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			solvent.writeToNBT(inputNBT);
			recipe.setTag(tagSolvent, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_gasification_plant_solvent_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Gasification Plant by reagent
	 * @param reagent : the reagent fluid
	 */
	public static void removeFromGasificationPlantByReagent(FluidStack reagent) {
		if(isValidFluid(reagent)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			reagent.writeToNBT(inputNBT);
			recipe.setTag(tagReagent, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_gasification_plant_reagent_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Gasification Plant by output
	 * @param output : the output gas
	 */
	public static void removeFromGasificationPlantByOutput(FluidStack output) {
		if(isValidGas(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_gasification_plant_output_key, recipe);
		}
	}



//DEPOSITION CHAMBER
	/**
	 * Adds a recipe to the Deposition Chamber
	 * @param input : the input stack
	 * @param output : the output stack
	 * @param solvent : the solvent gas
	 * @param temperature : the threashold temperature (min:30; max:3000)
	 * @param pressure : the threashold pressure (min:30; max:32000)
	 */
	public static void sendToDepositionChamber(ItemStack input, ItemStack output, FluidStack solvent, int temperature, int pressure) {
		if(isValidStack(input) && isValidStack(output) && isValidGas(solvent) && temperature > 30 && temperature <= 3000 && pressure > 30 && pressure <= 32000){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			NBTTagCompound solventNBT = new NBTTagCompound(); 
			solvent.writeToNBT(solventNBT);
			recipe.setTag(tagSolvent, solventNBT);

			recipe.setInteger(tagTemperature, temperature);
			recipe.setInteger(tagPressure, pressure);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_deposition_chamber_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Deposition Chamber by input
	 * @param input : the input stack
	 */
	public static void removeFromDepositionChamberByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_deposition_chamber_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Deposition Chamber by output
	 * @param output : the output stack
	 */
	public static void removeFromDepositionChamberByOutput(ItemStack output) {
		if(isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_deposition_chamber_output_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Deposition Chamber by solvent
	 * @param solvent : the solvent gas
	 */
	public static void removeFromDepositionChamberBySolvent(FluidStack solvent) {
		if(isValidGas(solvent)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			solvent.writeToNBT(inputNBT);
			recipe.setTag(tagSolvent, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_deposition_chamber_solvent_key, recipe);
		}
	}



//CHEMICAL EXTRACTOR
	/**
	 * Adds a recipe to the Chemical Extractor
	 * @param category : the category of the input
	 * @param input : the input stack
	 * @param element : the elements oredict array
	 * @param quantity : the quantity array
	 */
	public static void sendToChemicalExtractor(String category, ItemStack input, List<String> element, List<Integer> quantity) {
		if(isValidString(category) && isValidStack(input) && isValidStringArray(element) && isNonNegativeInteger(quantity) && arraysMatch(element.size(), quantity.size())){
			NBTTagCompound recipe = new NBTTagCompound();

			recipe.setString(tagDisplay, category);

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagList elementTAG = new NBTTagList();
			for(int i = 0; i < element.size(); i++){
				if(!Strings.isNullOrEmpty(element.get(i))){
					NBTTagCompound elementNBT = new NBTTagCompound();
					elementNBT.setString(tagElements + i, element.get(i));
					elementTAG.appendTag(elementNBT);
				}
			}
			recipe.setTag(tagElements, elementTAG);

			NBTTagList weightTAG = new NBTTagList();
			for(int i = 0; i < quantity.size(); i++){
				if(quantity.get(i) > 0){
					NBTTagCompound weightNBT = new NBTTagCompound();
					weightNBT.setInteger(tagWeights + i, quantity.get(i));
					weightTAG.appendTag(weightNBT);
				}
			}
			recipe.setTag(tagWeights, weightTAG);

			FMLInterModComms.sendMessage(rh_chemistry_id, add_chemical_extractor_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Chemical Extractor by input
	 * @param input : the input stack
	 */
	public static void removeFromChemicalExtractorByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_chemical_extractor_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Chemical Extractor by oredict
	 * @param oredict : the ingredient oredict
	 */
	public static void removeFromChemicalExtractorByOredict(String oredict) {
		if(isValidString(oredict)){
			NBTTagCompound recipe = new NBTTagCompound();
	
			recipe.setString(tagOredict, oredict);

			FMLInterModComms.sendMessage(rh_chemistry_id, remove_chemical_extractor_oredict_key, recipe);
		}
	}

	/**
	 * Inhibits one element from being extracted by the Chemical Extractor
	 * @param string : the inhibited element oredict
	 */
	public static void inhibitFromChemicalExtractor(String string) {
		if(isValidString(string)){
			NBTTagCompound recipe = new NBTTagCompound();
			recipe.setString(tagOredict, string);

			FMLInterModComms.sendMessage(rh_chemistry_id, inhibit_chemical_extractor_key, recipe);
		}
	}



/**
 * ROCKHOUNDING: ORE TIERS
 */

//BLOOMERY
	/**
	 * Adds a recipe to the Bloomery
	 * @param input : the input ore
	 * @param bloom : the molten ore
	 * @param output : the casted metal
	 */
	public static void sendToBloomery(ItemStack input, FluidStack bloom, ItemStack output) {
		if(isValidStack(input) && isValidFluid(bloom) && isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();
			
			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagInput, outputNBT);

			NBTTagCompound solventNBT = new NBTTagCompound(); 
			bloom.writeToNBT(solventNBT);
			recipe.setTag(tagSolvent, solventNBT);

			FMLInterModComms.sendMessage(rh_oretiers_id, add_bloomery_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Bloomery by input
	 * @param input : the input ore
	 */
	public static void removeFromBloomeryByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_oretiers_id, remove_bloomery_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Bloomery by output
	 * @param output : the output ore
	 */
	public static void removeFromBloomeryByOutput(ItemStack output) {
		if(isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_oretiers_id, remove_bloomery_output_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Bloomery by solvent
	 * @param solvent : the solvent ore
	 */
	public static void removeFromBloomeryByBloom(FluidStack solvent) {
		if(isValidFluid(solvent)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound solventNBT = new NBTTagCompound(); 
			solvent.writeToNBT(solventNBT);
			recipe.setTag(tagSolvent, solventNBT);

			FMLInterModComms.sendMessage(rh_oretiers_id, remove_bloomery_bloom_key, recipe);
		}
	}



//COAL REFINER
	/**
	 * Adds a recipe to the Coal Refiner
	 * @param input : the input
	 * @param oredict : if the recipe extends to oredicted inputs
	 * @param output : the output
	 * @param refining : the time required for refining
	 */
	public static void sendToCoalRefiner(ItemStack input, boolean oredict, ItemStack output, int refining) {
		if(isValidStack(input) && isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			recipe.setBoolean(tagOredict, oredict);
			recipe.setInteger(tagWeights, refining);

			FMLInterModComms.sendMessage(rh_oretiers_id, add_coal_refiner_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Coal Refiner by input
	 * @param input : the input stack
	 */
	public static void removeFromCoalRefinerByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_oretiers_id, remove_coal_refiner_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Coal Refiner by output
	 * @param output : the output stack
	 */
	public static void removeFromCoalRefinerByOutput(ItemStack output) {
		if(isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_oretiers_id, remove_coal_refiner_output_key, recipe);
		}
	}



//DRYING PALLET
	/**
	 * Adds a recipe to the Drying Pallet
	 * @param input : the input
	 * @param oredict : if the recipe extends to oredicted inputs
	 * @param output : the output
	 * @param refining : the time required for drying
	 */
	public static void sendToDryingPallet(ItemStack input, boolean oredict, ItemStack output, int refining) {
		if(isValidStack(input) && isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			recipe.setBoolean(tagOredict, oredict);
			recipe.setInteger(tagWeights, refining);

			FMLInterModComms.sendMessage(rh_oretiers_id, add_drying_pallet_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Drying Pallet by input
	 * @param input : the input stack
	 */
	public static void removeFromDryingPalletByInput(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_oretiers_id, remove_drying_pallet_input_key, recipe);
		}
	}

	/**
	 * Removes a recipe from the Drying Pallet by output
	 * @param output : the output stack
	 */
	public static void removeFromDryingPalletByOutput(ItemStack output) {
		if(isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_oretiers_id, remove_drying_pallet_output_key, recipe);
		}
	}



/**
 * ROCKHOUNDING: ROCKS
 */

//ROCK VENDOR
	/**
	 * Adds a recipe to the Rock Vending System
	 * @param input : the input stack
	 * @param stacksize : the returning stacksize
	 */
	public static void sendToRockVendor(ItemStack input, int stacksize) {
		if(isValidStack(input) && stacksize < 64){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			recipe.setInteger(tagPattern, stacksize);

			FMLInterModComms.sendMessage(rh_rocks_id, add_rock_vendor_key, recipe);
		}
	}	

	/**
	 * Removes a recipe from the Rock Vending System by input
	 * @param input : the input stack
	 */
	public static void removeFromRockVendor(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_rocks_id, remove_rock_vendor_input_key, recipe);
		}
	}



// CUTTING STATION
	/**
	 * Adds a recipe to the Cutting Station
	 * @param input : the input stack
	 * @param pattern : the code of the cutting (min:0; max:19)
	 * @param output : the output stack
	 */
	public static void sendToCuttingStation(ItemStack input, int pattern, ItemStack output) {
		if(isValidStack(input) && isValidStack(output) && pattern > -1 && pattern < 20){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			recipe.setInteger(tagPattern, pattern);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_rocks_id, add_cutting_station_key, recipe);
		}
	}



// STONE RAMMER
	/**
	 * Adds a recipe to the Stone Rammer
	 * @param input : the input stack
	 * @param output : the output stack
	 */
	public static void sendToStoneRammer(ItemStack input, ItemStack output) {
		if(isValidStack(input) && isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_rocks_id, add_stone_rammer_key, recipe);
		}
	}



// CARVING BENCH
	/**
	 * Adds a recipe to the Carving Bench
	 * @param input : the input stack
	 * @param pattern : the carving pattern
	 * @param output : the output stack
	 */
	public static void sendToCarvingTable(ItemStack input, int pattern, ItemStack output) {
		if(isValidStack(input) && isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			recipe.setInteger(tagPattern, pattern);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_rocks_id, add_carving_bench_key, recipe);
		}
	}



// SCULPTING BENCH
	/**
	 * Adds a recipe to the Sculpting Bench
	 * @param input : the input stack
	 * @param pattern : the carving pattern
	 * @param output : the output stack
	 */
	public static void sendToSculptingTable(ItemStack input, int pattern, ItemStack output) {
		if(isValidStack(input) && isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			recipe.setInteger(tagPattern, pattern);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_rocks_id, add_sculpting_bench_key, recipe);
		}
	}



/**
 * ROCKHOUNDING: SURFACE
 */

//COMPOST BIN
	/**
	 * Adds a recipe to the Compost Bin
	 * @param input : the adding item
	 * @param type : the type of composting item (-1:custom(see 'value'), 0:seed(1%), 1:plants(3%), 2:saplings(5%), 3:vegetation(10%), 4:food(20%), 5:fossil(50%))
	 * @param value : the composting value only if type = -1
	 */
	public static void sendToCompostBin(ItemStack input, int type, int value) {
		if(isValidStack(input) && type >= -1 && type < 6){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			recipe.setInteger(tagPattern, type);
			
			recipe.setInteger(tagWeights, value);

			FMLInterModComms.sendMessage(rh_surface_id, add_compost_bin_key, recipe);
		}
	}

	/**
	 * Removes a recipe to the Compost Bin
	 * @param input : the removing item
	 */
	public static void removeFromCompostBin(ItemStack input) {
		if(isValidStack(input)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			FMLInterModComms.sendMessage(rh_surface_id, remove_compost_bin_key, recipe);
		}
	}



//WOOD INCUBATOR
	/**
	 * Adds a recipe to the Wood Incubator
	 * @param input : the wood input
	 * @param solute : the doping compound
	 * @param solvent : the aging solvent
	 * @param output : the fossil wood output
	 */
	public static void sendToWoodIncubator(ItemStack input, ItemStack solute, FluidStack solvent, ItemStack output) {
		if(isValidStack(input) && isValidStack(solute) && isValidFluid(solvent) && isValidStack(output)){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound soluteNBT = new NBTTagCompound(); 
			solute.writeToNBT(soluteNBT);
			recipe.setTag(tagPattern, soluteNBT);

			NBTTagCompound solventNBT = new NBTTagCompound(); 
			solvent.writeToNBT(solventNBT);
			recipe.setTag(tagSolvent, solventNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			FMLInterModComms.sendMessage(rh_surface_id, add_wood_incubator_key, recipe);
		}
	}



//VIVARIUM
	/**
	 * Adds a recipe to the Vivarium
	 * @param input : the input stack
	 * @param output : the output stack
	 * @param consumeRate : 1/n chance to produce an output item
	 * @param produceRate : 1/n chance to consume the input
	 */
	public static void sendToVivarium(ItemStack input, ItemStack output, int produceRate, int consumeRate) {
		if(isValidStack(input) && isValidStack(output) && produceRate > 0 && consumeRate > 0){
			NBTTagCompound recipe = new NBTTagCompound();

			NBTTagCompound inputNBT = new NBTTagCompound(); 
			input.writeToNBT(inputNBT);
			recipe.setTag(tagInput, inputNBT);

			NBTTagCompound outputNBT = new NBTTagCompound(); 
			output.writeToNBT(outputNBT);
			recipe.setTag(tagOutput, outputNBT);

			recipe.setInteger(tagWeights, produceRate);
			recipe.setInteger(tagWaste, consumeRate);

			FMLInterModComms.sendMessage(rh_surface_id, add_vivarium_key, recipe);
		}
	}

}