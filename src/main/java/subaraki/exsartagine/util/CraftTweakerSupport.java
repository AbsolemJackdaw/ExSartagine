package subaraki.exsartagine.util;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import lib.recipes.PotRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import subaraki.exsartagine.recipe.PanRecipes;
import subaraki.exsartagine.recipe.SmelterEntries;

@ZenClass("mods."+Reference.MODID+".ExSartagine")
@ZenRegister
public class CraftTweakerSupport {

	@ZenMethod
	public static void addPotRecipe (IIngredient input, IItemStack output) {
		CraftTweakerAPI.apply(new AddPotRecipe(input, output));
	}

	@ZenMethod
	public static void addPanRecipe (IIngredient input, IItemStack output) {
		CraftTweakerAPI.apply(new AddPanRecipe(input, output));;
	}

	@ZenMethod
	public static void addSmelterEntry (IIngredient entry) {
		SmelterEntries.getInstance().addEntry(CraftTweakerMC.getItemStack(entry));
	}

	@ZenMethod
	public static void removeSmelterEntry (IIngredient entry) {
		SmelterEntries.getInstance().removeEntry(CraftTweakerMC.getItemStack(entry));
	}
	
	private static final class AddPotRecipe implements IAction {
		private final ItemStack[] inputs;
		private final ItemStack output;
		
		private AddPotRecipe(IIngredient input, IItemStack output) {
			this.inputs = CraftTweakerMC.getExamples(input);
			this.output = CraftTweakerMC.getItemStack(output);
		}
		
		@Override
		public void apply() {
			for (ItemStack input : inputs) {
				PotRecipes.getInstance().addRecipe(input, output);
			}
		}
		
		@Override
		public String describe() {
			return "Added recipes with output " + output.getItem().getRegistryName() + " to Pot";
		}
	}
	
	private static final class AddPanRecipe implements IAction {
		private final ItemStack[] inputs;
		private final ItemStack output;
		
		private AddPotRecipe(IIngredient input, IItemStack output) {
			this.inputs = CraftTweakerMC.getExamples(input);
			this.output = CraftTweakerMC.getItemStack(output);
		}
		
		@Override
		public void apply() {
			for (ItemStack input : inputs) {
				PanRecipes.getInstance().addRecipe(input, output);
			}
		}
		
		@Override
		public String describe() {
			return "Added recipes with output " + output.getItem().getRegistryName() + " to Pan";
		}
	}
	
	private static final class AddSmelterEntry implements IAction {
		private final ItemStack[] entries;
		
		private AddPotRecipe(IIngredient entry) {
			this.entries = CraftTweakerMC.getExamples(entry);
		}
		
		@Override
		public void apply() {
			for (ItemStack entry : entries) {
				SmelterEntries.getInstance().addEntry(entry);
			}
		}
		
		@Override
		public String describe() {
			return "Added recipes with partial input " + entries[0].getItem().getRegistryName() + " to Smelter";
		}
	}
	
	private static final class RemoveSmelterEntry implements IAction {
		private final ItemStack[] entries;
		
		private AddPotRecipe(IIngredient entry) {
			this.entries = CraftTweakerMC.getExamples(entry);
		}
		
		@Override
		public void apply() {
			for (ItemStack entry : entries) {
				SmelterEntries.getInstance().removeEntry(entry);
			}
		}
		
		@Override
		public String describe() {
			return "Removed recipes with partial input " + entries[0].getItem().getRegistryName() + " to Smelter";
		}
	}
}
