package subaraki.exsartagine.util;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import lib.recipes.PotRecipes;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import subaraki.exsartagine.recipe.PanRecipes;
import subaraki.exsartagine.recipe.SmelterEntries;

@ZenRegister
@ZenClass("mods."+Reference.MODID)
public class CraftTweakerSupport {

	@ZenMethod
	public static void addPotRecipe (IIngredient input, IIngredient output) {
		PotRecipes.getInstance().addRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output));
	}

	@ZenMethod
	public static void addSmelterEntry (IIngredient entry) {
		SmelterEntries.getInstance().addEntry(CraftTweakerMC.getItemStack(entry));
	}

	@ZenMethod
	public static void removeSmelterEntry (IIngredient entry) {
		SmelterEntries.getInstance().removeEntry(CraftTweakerMC.getItemStack(entry));
	}

	@ZenMethod
	public static void addPanRecipe (IIngredient input, IIngredient output) {
		PanRecipes.getInstance().addRecipe(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output));
	}
}