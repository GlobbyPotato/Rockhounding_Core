package rockhounding.api;

import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ExampleRecipes extends IReciper{
	public static void init(){

/**
 * ROCKHOUNDING: CHEMISTRY
 */

	//POLLUTANT FLUIDS
		sendToPollutantFluids(new FluidStack(FluidRegistry.WATER, 1000));
		removeFromPollutantFluids(new FluidStack(FluidRegistry.getFluid("toxic_waste"), 1000));

	//POLLUTANT GASES
		sendToPollutantGases(new FluidStack(FluidRegistry.getFluid("xenon"), 1000));
		removeFromPollutantGases(new FluidStack(FluidRegistry.getFluid("ammonia"), 1000));

	//TOXIC MUTATION
		sendToToxicMutation(new ItemStack(Items.LEATHER), new ItemStack(Items.ROTTEN_FLESH));
		removeFromToxicMutationByInput(new ItemStack(Items.LEATHER));
		removeFromToxicMutationByOutput(new ItemStack(Items.ROTTEN_FLESH));

	//SLURRY POND
		sendToSlurryPond(new ItemStack(Items.MAGMA_CREAM), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.LAVA, 1000));
		removeFromSlurryPondByInput(new ItemStack(Items.MAGMA_CREAM));
		removeFromSlurryPondByBath(new FluidStack(FluidRegistry.WATER, 1000));
		removeFromSlurryPondBySlurry(new FluidStack(FluidRegistry.LAVA, 1000));

	//SEASONING RACK
		sendToSeasoningRack(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER));
		removeFromSeasoningRackByInput(new ItemStack(Items.ROTTEN_FLESH));
		removeFromSeasoningRackByOutput(new ItemStack(Items.LEATHER));

	//RETENTION VAT
		sendToRetentionVat(new FluidStack(FluidRegistry.WATER, 1000), Arrays.asList(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND), new ItemStack(Blocks.CLAY)), Arrays.asList(1.50F, 3.25F, 8.66F));
		removeFromRetentionVatByInput(new FluidStack(FluidRegistry.WATER, 1000));

	//PROFILING BENCH
		sendToProfilingBench(new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Items.GOLD_INGOT, 9), 7);
		sendToProfilingBench("blockGold", new ItemStack(Items.GOLD_INGOT, 9), 7);
		removeFromProfilingBenchByOredict("blockGold");
		removeFromProfilingBenchByInput(new ItemStack(Blocks.GOLD_BLOCK));
		removeFromProfilingBenchByOutput(new ItemStack(Items.GOLD_INGOT));

	//MINERAL SIZER
		sendToMineralSizer(new ItemStack(Blocks.STONE), Arrays.asList(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND), new ItemStack(Blocks.CLAY)), Arrays.asList(4, 6, 14));
		removeFromMineralSizerByInput(new ItemStack(Blocks.STONE));

	//METAL ALLOYER
		sendToMetalAlloyer(Arrays.asList("dustRedstone", "dustGlowstone"), Arrays.asList(70, 40), new ItemStack(Items.GHAST_TEAR, 9));
		removeFromMetalAlloyerByOutput(new ItemStack(Items.GHAST_TEAR));
		removeFromMetalAlloyerByOredict("dustRedstone");

	//MATERIAL CABINET
		sendToMaterialCabinet("Gl", "dustGlowstone", "Glowstone");
		removeFromMaterialCabinetByOredict("dustRedstone");

	//LEACHING VAT
		sendToLeachingVat(new ItemStack(Blocks.NETHERRACK), Arrays.asList(new ItemStack(Blocks.GRAVEL), new ItemStack(Blocks.SAND), new ItemStack(Blocks.CLAY)), Arrays.asList(4.50F, 5.25F, 7.66F), new FluidStack(FluidRegistry.LAVA, 500));
		removeFromLeachingVatByInput(new ItemStack(Blocks.NETHERRACK));

	//LAB OVEN
		sendToLabOven(null, new ItemStack(Items.MAGMA_CREAM), ItemStack.EMPTY, new FluidStack(FluidRegistry.WATER, 1000), null, new FluidStack(FluidRegistry.LAVA, 1000), null);
		removeFromLabOvenBySolute(new ItemStack(Items.MAGMA_CREAM));
		removeFromLabOvenBySolvent(new FluidStack(FluidRegistry.WATER, 1000));
		removeFromLabOvenBySolution(new FluidStack(FluidRegistry.LAVA, 1000));
		removeFromLabOvenByPrecipitate(new ItemStack(Items.NETHER_WART));

	//LAB BLENDER
		sendToLabBlender(Arrays.asList(new ItemStack(Items.REDSTONE, 4), new ItemStack(Items.GLOWSTONE_DUST, 6), new ItemStack(Items.GHAST_TEAR, 8)), new ItemStack(Items.NETHER_STAR, 2));
		removeFromLabBlenderByOutput(new ItemStack(Items.NETHER_STAR));

	//HEAT EXCHANGER
		sendToHeatExchanger(new FluidStack(FluidRegistry.getFluid("compressed_air"), 1000), new FluidStack(FluidRegistry.getFluid("cooled_air"), 1000));
		removeFromHeatExchangerByInput(new FluidStack(FluidRegistry.getFluid("compressed_air"), 1000));
		removeFromHeatExchangerByOutput(new FluidStack(FluidRegistry.getFluid("cooled_air"), 1000));

	//GAS REFORMER
		sendToReformingReactor(new FluidStack(FluidRegistry.getFluid("nitrogen"), 1000), new FluidStack(FluidRegistry.getFluid("syngas"), 1000), new FluidStack(FluidRegistry.getFluid("ammonia"), 1000), new ItemStack(Blocks.IRON_BLOCK));
		removeFromReformingReactorBySolvent(new FluidStack(FluidRegistry.getFluid("nitrogen"), 1000));
		removeFromReformingReactorByReagent(new FluidStack(FluidRegistry.getFluid("syngas"), 1000));
		removeFromReformingReactorByOutput(new FluidStack(FluidRegistry.getFluid("ammonia"), 1000));

	//GAS PURIFIER
		sendToGasPurifier(new FluidStack(FluidRegistry.getFluid("raw_syngas"), 1000), new FluidStack(FluidRegistry.getFluid("syngas"), 1000), new ItemStack(Items.GUNPOWDER), ItemStack.EMPTY);
		removeFromGasPurifierByInput(new FluidStack(FluidRegistry.getFluid("nitrogen"), 1000));
		removeFromGasPurifierByOutput(new FluidStack(FluidRegistry.getFluid("syngas"), 1000));

	//GAS CONDENSER
		sendToGasCondenser(new FluidStack(FluidRegistry.getFluid("oxygen"), 80), new FluidStack(FluidRegistry.getFluid("liquid_oxigen"), 1));

	//GASIFICATION_PLANT
		sendToGasificationPlant(new FluidStack(FluidRegistry.getFluid("coal_slurry"), 1000), new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.getFluid("raw_syngas"), 1000), new ItemStack(Items.GUNPOWDER), ItemStack.EMPTY, 800);
		removeFromGasificationPlantBySolvent(new FluidStack(FluidRegistry.getFluid("coal_slurry"), 1000));
		removeFromGasificationPlantByReagent(new FluidStack(FluidRegistry.WATER, 1000));
		removeFromGasificationPlantByOutput(new FluidStack(FluidRegistry.getFluid("raw_syngas"), 1000));

	//DEPOSITION CHAMBER
		sendToDepositionChamber(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.MAGMA_CREAM), new FluidStack(FluidRegistry.LAVA, 1000), 2000, 2000);
		removeFromDepositionChamberByInput(new ItemStack(Items.SLIME_BALL));
		removeFromDepositionChamberByOutput(new ItemStack(Items.MAGMA_CREAM));
		removeFromDepositionChamberBySolvent(new FluidStack(FluidRegistry.getFluid("syngas"), 1000));

	//CHEMICAL EXTRACTOR
		sendToChemicalExtractor("Oxide", new ItemStack(Items.GOLD_INGOT), Arrays.asList("dustSilver", "dustGold"), Arrays.asList(70, 20));
		removeFromChemicalExtractorByInput(new ItemStack(Items.GOLD_INGOT));
		removeFromChemicalExtractorByOredict("dustGold");
		inhibitFromChemicalExtractor("dustSilicon");

	//TRANSPOSER
		sendToTransposer(new FluidStack(FluidRegistry.getFluid("sulfuricacid"), 1000), new FluidStack(FluidRegistry.getFluid("sulfuric_acid"), 1000));
		removeFromTransposerByInput(new FluidStack(FluidRegistry.getFluid("sulfuricacid"), 1000));
		removeFromTransposerByOutput(new FluidStack(FluidRegistry.getFluid("sulfuric_acid"), 1000));



/**
 * ROCKHOUNDING: ORE TIERS
 */

	//BLOOMERY
		sendToBloomery(new ItemStack(Blocks.MAGMA), new FluidStack(FluidRegistry.LAVA, 250), new ItemStack(Items.MAGMA_CREAM));
		removeFromBloomeryByInput(new ItemStack(Blocks.IRON_ORE));
		removeFromBloomeryByBloom(new FluidStack(FluidRegistry.LAVA, 1000));
		removeFromBloomeryByOutput(new ItemStack(Items.MAGMA_CREAM));

	//COAL REFINER
		sendToCoalRefiner(new ItemStack(Items.COAL, 1, 1), true, new ItemStack(Items.COAL), 2000);
		removeFromCoalRefinerByInput(new ItemStack(Items.COAL, 1, 1));
		removeFromCoalRefinerByOutput(new ItemStack(Items.COAL));

	//DRYING PALLET
		sendToDryingPallet(new ItemStack(Items.ROTTEN_FLESH), true, new ItemStack(Items.LEATHER), 2000);
		removeFromDryingPalletByInput(new ItemStack(Items.ROTTEN_FLESH));
		removeFromDryingPalletByOutput(new ItemStack(Items.LEATHER));



/**
 * ROCKHOUNDING: ROCKS
 */

	//ROCK VENDOR
		sendToRockVendor(new ItemStack(Items.NETHER_STAR), 64);
		removeFromRockVendor(new ItemStack(Items.GOLD_INGOT));

	//CUTTING STATION
		sendToCuttingStation(new ItemStack(Blocks.STONE, 1, 1), 2, new ItemStack(Blocks.STONE, 1, 2));

	//STONE RAMMER
		sendToStoneRammer(new ItemStack(Blocks.STONE, 1, 1), new ItemStack(Blocks.STONE, 1, 2));

	//CARVING TABLE
		sendToCarvingTable(new ItemStack(Blocks.STONE), 3, new ItemStack(Blocks.STONEBRICK));

	//SCULPTING TABLE
		sendToSculptingTable(new ItemStack(Blocks.QUARTZ_BLOCK), 1, new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1));



/**
 * ROCKHOUNDING: SURFACE
 */

	//COMPOST BIN
		sendToCompostBin(new ItemStack(Blocks.BONE_BLOCK), -1, 25);
		removeFromCompostBin(new ItemStack(Blocks.MELON_BLOCK));

	//WOOD INCUBATOR
		sendToWoodIncubator(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.ROTTEN_FLESH), new FluidStack(FluidRegistry.WATER, 500), new ItemStack(Blocks.SLIME_BLOCK));

	//VIVARIUM
		sendToVivarium(new ItemStack(Blocks.WATERLILY), new ItemStack(Items.FISH), 2000, 20);

	}
}