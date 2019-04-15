package rockhounding.api;

import net.minecraftforge.fml.common.Loader;

public class IReciperBase {
	public static String rh_core_id = "rockhounding_core";
	public static String rh_chemistry_id = "rockhounding_chemistry";
	public static String rh_oretiers_id = "rockhounding_oretiers";
	public static String rh_surface_id = "rockhounding_surface";
	public static String rh_rocks_id = "rockhounding_rocks";

	public static String tagInput = "Input";
	public static String tagOutput = "Output";
	public static String tagSolvent = "Solvent";
	public static String tagReagent = "Reagent";
	public static String tagElements = "Elements";
	public static String tagWeights = "Weights";
	public static String tagPattern = "Pattern";
	public static String tagOredict = "Oredict";
	public static String tagDisplay = "Display";
	public static String tagWaste = "Waste";
	public static String tagSlag1 = "Slag1";
	public static String tagSlag2 = "Slag2";
	public static String tagTemperature = "Temperature";
	public static String tagPressure = "Pressure";
	public static String tagByproduct = "Byproduct";

	public static String add_pollutant_fluid_key = "add_pollutant_fluid"; 
	public static String remove_pollutant_fluid_key = "remove_pollutant_fluid"; 
	public static String add_pollutant_gas_key = "add_pollutant_gas"; 
	public static String remove_pollutant_gas_key = "remove_pollutant_gas"; 

	public static String add_toxic_mutation_key = "add_toxic_mutation"; 
	public static String remove_toxic_mutation_input_key = "remove_toxic_mutation_input"; 
	public static String remove_toxic_mutation_output_key = "remove_toxic_mutation_output"; 

	public static String add_slurry_pond_key = "add_slurry_pond"; 
	public static String remove_slurry_pond_solute_key = "remove_slurry_pond_solute"; 
	public static String remove_slurry_pond_bath_key = "remove_slurry_pond_bath"; 
	public static String remove_slurry_pond_slurry_key = "remove_slurry_pond_slurry"; 

	public static String add_transposer_key = "add_transposer"; 
	public static String remove_transposer_input_key = "remove_transposer_input"; 
	public static String remove_transposer_output_key = "remove_transposer_output"; 

	public static String add_seasoning_rack_key = "add_seasoning_rack"; 
	public static String remove_seasoning_rack_input_key = "remove_seasoning_rack_input"; 
	public static String remove_seasoning_rack_output_key = "remove_seasoning_rack_output"; 

	public static String add_retention_vat_key = "add_retention_vat"; 
	public static String remove_retention_vat_input_key = "remove_retention_vat_input"; 

	public static String add_profiling_bench_key = "add_profiling_bench"; 
	public static String remove_profiling_bench_input_key = "remove_profiling_bench_input"; 
	public static String remove_profiling_bench_output_key = "remove_profiling_bench_output"; 
	public static String remove_profiling_bench_oredict_key = "remove_profiling_bench_oredict"; 

	public static String add_mineral_sizer_key = "add_mineral_sizer"; 
	public static String remove_mineral_sizer_input_key = "remove_mineral_sizer_input"; 

	public static String add_metal_alloyer_key = "add_metal_alloyer"; 
	public static String remove_metal_alloyer_output_key = "remove_metal_alloyer_output"; 
	public static String remove_metal_alloyer_oredict_key = "remove_metal_alloyer_oredict"; 

	public static String add_material_cabinet_key = "add_material_cabinet"; 
	public static String remove_material_cabinet_oredict_key = "remove_material_cabinet_oredict"; 

	public static String add_leaching_vat_key = "add_leaching_vat"; 
	public static String remove_leaching_vat_input_key = "remove_leaching_vat_input"; 

	public static String add_lab_oven_key = "add_lab_oven"; 
	public static String remove_lab_oven_solute_key = "remove_lab_oven_solute"; 
	public static String remove_lab_oven_solvent_key = "remove_lab_oven_solvent"; 
	public static String remove_lab_oven_solution_key = "remove_lab_oven_solution"; 
	public static String remove_lab_oven_precipitate_key = "remove_lab_oven_precipitate"; 

	public static String add_lab_blender_key = "add_lab_blender"; 
	public static String remove_lab_blender_output_key = "remove_lab_blender_output"; 

	public static String add_heat_exchanger_key = "add_heat_exchanger"; 
	public static String remove_heat_exchanger_input_key = "remove_heat_exchanger_input"; 
	public static String remove_heat_exchanger_output_key = "remove_heat_exchanger_output"; 

	public static String add_reforming_reactor_key = "add_reforming_reactor"; 
	public static String remove_reforming_reactor_solvent_key = "remove_reforming_reactor_solvent"; 
	public static String remove_reforming_reactor_reagent_key = "remove_reforming_reactor_reagent"; 
	public static String remove_reforming_reactor_output_key = "remove_reforming_reactor_output"; 

	public static String add_gas_purifier_key = "add_gas_purifier"; 
	public static String remove_gas_purifier_input_key = "remove_gas_purifier_input"; 
	public static String remove_gas_purifier_output_key = "remove_gas_purifier_output"; 

	public static String add_gas_condenser_key = "add_gas_condenser"; 
	public static String remove_gas_condenser_input_key = "remove_gas_condenser_input"; 
	public static String remove_gas_condenser_output_key = "remove_gas_condenser_output"; 

	public static String add_gasification_plant_key = "add_gasification_plant"; 
	public static String remove_gasification_plant_solvent_key = "remove_gasification_plant_solvent"; 
	public static String remove_gasification_plant_reagent_key = "remove_gasification_plant_reagent"; 
	public static String remove_gasification_plant_output_key = "remove_gasification_plant_output"; 

	public static String add_deposition_chamber_key = "add_deposition_chamber"; 
	public static String remove_deposition_chamber_input_key = "remove_deposition_chamber_input"; 
	public static String remove_deposition_chamber_output_key = "remove_deposition_chamber_output"; 
	public static String remove_deposition_chamber_solvent_key = "remove_deposition_chamber_solvent"; 

	public static String add_chemical_extractor_key = "add_chemical_extractor"; 
	public static String remove_chemical_extractor_input_key = "remove_chemical_extractor_input"; 
	public static String remove_chemical_extractor_oredict_key = "remove_chemical_extractor_oredict"; 
	public static String inhibit_chemical_extractor_key = "inhibit_chemical_extractor";

	public static String add_bloomery_key = "add_bloomery"; 
	public static String remove_bloomery_input_key = "remove_bloomery_input"; 
	public static String remove_bloomery_bloom_key = "remove_bloomery_bloom"; 
	public static String remove_bloomery_output_key = "remove_bloomery_output"; 

	public static String add_coal_refiner_key = "add_coal_refiner"; 
	public static String remove_coal_refiner_input_key = "remove_coal_refiner_input"; 
	public static String remove_coal_refiner_output_key = "remove_coal_refiner_output"; 

	public static String add_drying_pallet_key = "add_drying_pallet"; 
	public static String remove_drying_pallet_input_key = "remove_drying_pallet_input"; 
	public static String remove_drying_pallet_output_key = "remove_drying_pallet_output"; 

	public static String add_rock_vendor_key = "add_rock_vendor"; 
	public static String remove_rock_vendor_input_key = "remove_rock_vendor_input"; 

	public static String add_cutting_station_key = "add_cutting_station";

	public static String add_stone_rammer_key = "add_stone_rammer";

	public static String add_carving_bench_key = "add_carving_bench";

	public static String add_sculpting_bench_key = "add_sculpting_bench";

	public static String add_compost_bin_key = "add_compost_bin";
	public static String remove_compost_bin_key = "remove_compost_bin"; 

	public static String add_wood_incubator_key = "add_wood_incubator";

	public static String add_vivarium_key = "add_vivarium";

	public static boolean chemistryLoaded(){return Loader.isModLoaded(rh_chemistry_id);}
	public static boolean oretiersLoaded(){return Loader.isModLoaded(rh_oretiers_id);}
	public static boolean rocksLoaded(){return Loader.isModLoaded(rh_rocks_id);}
	public static boolean surfaceLoaded(){return Loader.isModLoaded(rh_surface_id);}

}
