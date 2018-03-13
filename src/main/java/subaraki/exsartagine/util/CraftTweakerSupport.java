package subaraki.exsartagine.util;

import lib.recipes.PotRecipes;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods."+Reference.MODID)
public class CraftTweakerSupport {

	@ZenMethod
	public static void addPotRecipe (IIngredient input, IIngredient output) {
		PotRecipes.getInstance().addRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output));
	}
}