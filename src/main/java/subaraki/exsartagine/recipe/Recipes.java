package subaraki.exsartagine.recipe;

import lib.recipes.PotRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import subaraki.exsartagine.item.ExSartagineItems;

public class Recipes {

	public Recipes() {

		FurnaceRecipes.instance().addSmelting(ExSartagineItems.pizza_chicken_raw, new ItemStack(ExSartagineItems.pizza_chicken), 0.6f);
		FurnaceRecipes.instance().addSmelting(ExSartagineItems.pizza_meat_raw, new ItemStack(ExSartagineItems.pizza_meat), 0.6f);
		FurnaceRecipes.instance().addSmelting(ExSartagineItems.pizza_sweet_raw, new ItemStack(ExSartagineItems.pizza_sweet), 0.6f);
		FurnaceRecipes.instance().addSmelting(ExSartagineItems.pizza_fish_raw, new ItemStack(ExSartagineItems.pizza_fish), 0.6f);

		FurnaceRecipes.instance().addSmelting(ExSartagineItems.bread_dough, new ItemStack(ExSartagineItems.bread_fine), 0.6f);
		FurnaceRecipes.instance().addSmelting(ExSartagineItems.bread_meat_raw, new ItemStack(ExSartagineItems.bread_meat), 0.6f);
		FurnaceRecipes.instance().addSmelting(ExSartagineItems.bread_veggie_raw, new ItemStack(ExSartagineItems.bread_veggie), 0.6f);

		PotRecipes.getInstance().addRecipe(new ItemStack(Items.EGG,1), new ItemStack(ExSartagineItems.boiled_egg,1));
		PotRecipes.getInstance().addRecipe(new ItemStack(Items.BEETROOT_SEEDS,1), new ItemStack(ExSartagineItems.boiled_beans,1));
		PotRecipes.getInstance().addRecipe(new ItemStack(Items.POTATO,1), new ItemStack(ExSartagineItems.boiled_potato,1));

		PotRecipes.getInstance().addRecipe(new ItemStack(Blocks.STONE,1), new ItemStack(ExSartagineItems.salt,1));
		
		PotRecipes.getInstance().addRecipe(new ItemStack(ExSartagineItems.spaghetti_raw), new ItemStack(ExSartagineItems.spaghetti_cooked));
		
		PotRecipes.getInstance().addRecipe(new ItemStack(ExSartagineItems.noodles_chicken), new ItemStack(ExSartagineItems.noodles_chicken_cooked));
		PotRecipes.getInstance().addRecipe(new ItemStack(ExSartagineItems.noodles_fish), new ItemStack(ExSartagineItems.noodles_fish_cooked));
		PotRecipes.getInstance().addRecipe(new ItemStack(ExSartagineItems.noodles_meat), new ItemStack(ExSartagineItems.noodles_meat_cooked));
		PotRecipes.getInstance().addRecipe(new ItemStack(ExSartagineItems.noodles_veggie), new ItemStack(ExSartagineItems.noodles_veggie_cooked));

	}
}
