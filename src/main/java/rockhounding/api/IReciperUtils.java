package rockhounding.api;

import java.util.List;

import com.google.common.base.Strings;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class IReciperUtils extends IReciperBase{
	public static boolean isValidStack(ItemStack stack){
		return !stack.isEmpty() && stack.getItem() != null;
	}

	public static boolean isValidFluid(FluidStack fluid) {
		return fluid != null && fluid.getFluid() != null && fluid.amount > 0;
	}

	public static boolean isValidGas(FluidStack gas) {
		return gas != null && gas.getFluid() != null && gas.getFluid().isGaseous() && gas.amount > 0;
	}

	public static boolean isValidConsumable(ItemStack stack){
		return !stack.isEmpty() && stack.getItem() != null && stack.getItem().isDamageable();
	}

	public static boolean isNonNegativeFloat(List<Float> array) {
		int emptyness = 0;
		for(Float num : array){
			if(num < 0){
				emptyness++;
			}
		}
		return emptyness == 0;
	}

	public static boolean isNonNegativeInteger(List<Integer> array) {
		int emptyness = 0;
		for(Integer num : array){
			if(num < 0){
				emptyness++;
			}
		}
		return emptyness == 0;
	}

	public static boolean isValidStackArray(List<ItemStack> array) {
		return !array.isEmpty() && array.size() > 0 && noEmptyStack(array);
	}

				private static boolean noEmptyStack(List<ItemStack> array) {
					int emptyness = 0;
					for(ItemStack stack : array){
						if(stack.isEmpty() || stack.getItem() == null){
							emptyness++;
						}
					}
					return emptyness == 0;
				}

	public static boolean arraysMatch(int a, int b) {
		return a == b;
	}

	public static boolean isValidString(String stack){
		return !Strings.isNullOrEmpty(stack);
	}

	public static ItemStack oredictStack(String input) {
		List<ItemStack> ores = OreDictionary.getOres(input);
		ItemStack entryStack = ItemStack.EMPTY;
		if(!ores.isEmpty() && ores.size() > 0){
			entryStack = ores.get(0);
			entryStack.setCount(1);
		}
		return entryStack;
	}

	public static boolean isValidStringArray(List<String> array) {
		return !array.isEmpty() && array.size() > 0 && noEmptyString(array);
	}

				private static boolean noEmptyString(List<String> array) {
					int emptyness = 0;
					for(String string : array){
						if(Strings.isNullOrEmpty(string)){
							emptyness++;
						}
					}
					return emptyness == 0;
				}

}