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
		GameRegistry.addRecipe(new ItemStack(ExSartagineItems.pan), 
				"xxx","IxS","III",
				'I',Items.IRON_INGOT,
				'S',Items.STICK
				);

		GameRegistry.addRecipe(new ItemStack(ExSartagineItems.smelter), 
				"CxC","CFx","CCC",
				'C',Items.IRON_INGOT,
				'F', Blocks.FURNACE
				);

		GameRegistry.addRecipe(new ItemStack(ExSartagineItems.pot), 
				"IxI","CBC","CCC",
				'I',Items.IRON_INGOT,
				'C', Blocks.COBBLESTONE,
				'B', Items.BUCKET
				);

		GameRegistry.addRecipe(new ItemStack(ExSartagineItems.range), 
				"IxI","IMI","CCC",
				'I',Items.IRON_INGOT,
				'C', Blocks.IRON_BLOCK,
				'M', Blocks.MAGMA
				);

		GameRegistry.addRecipe(new ItemStack(ExSartagineItems.flour), 
				"F",
				'F',Items.WHEAT
				);

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.yeast), 
				"cropMushroom",
				"dustSugar"
				));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.yeast), 
				"cropMushroom",
				"cropFruit"
				));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.curd), 
				"itemYeast",
				"dustSalt",
				Items.MILK_BUCKET
				));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ExSartagineItems.dough, 8), 
				"DDD","DWD","DDD",
				'D', "dustFlour",
				'W', Items.WATER_BUCKET
				));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.bread_dough, 1), 
				"foodDough",
				"itemYeast",
				"dustSalt"
				));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_dough, 1), 
				"foodDough",
				"itemYeast",
				"dustSalt",
				"dustSugar"
				));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ExSartagineItems.bread_meat_raw, 6), 
				"DD","DM",
				'D', "foodDoughBread",
				'M', "foodMeatRaw"
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.bread_veggie_raw, 1), 
				"foodDoughBread",
				"cropVegetable",
				"cropVegetable"
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_plain, 1), 
				"foodDoughFlat",
				"cropTomato",
				"cropTomato",
				"itemCheese"
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_plain, 1), 
				"foodDoughFlat",
				"cropTomato",
				"cropTomato",
				"ingredientCheese"
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_plain, 1), 
				"foodDoughFlat",
				Items.BEETROOT,
				Items.BEETROOT,
				Items.BEETROOT,
				"itemCheese"
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_plain, 1), 
				"foodDoughFlat",
				Items.BEETROOT,
				Items.BEETROOT,
				Items.BEETROOT,
				"ingredientCheese"
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_meat_raw), 
				ExSartagineItems.pizza_plain,
				"foodRedMeatRaw"
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_chicken_raw), 
				ExSartagineItems.pizza_plain,
				Items.CHICKEN
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_fish_raw), 
				ExSartagineItems.pizza_plain,
				"foodFishRaw"
				));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ExSartagineItems.pizza_sweet_raw), 
				ExSartagineItems.pizza_plain,
				"cropFruit"
				));
		
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
	}
}
