package subaraki.exsartagine.recipe;

import lib.recipes.PotRecipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import subaraki.exsartagine.item.ExSartagineItems;

public class Recipes {

	public Recipes() {
		GameRegistry.addRecipe(new ItemStack(ExSartagineItems.pan), new Object[]{
				"xxx","IxS","III",
				'I',Items.IRON_INGOT,
				'S',Items.STICK
		});
		
		GameRegistry.addRecipe(new ItemStack(ExSartagineItems.smelter), new Object[]{
				"CxC","CFx","CCC",
				'C',Items.IRON_INGOT,
				'F', Blocks.FURNACE
		});
		
		GameRegistry.addRecipe(new ItemStack(ExSartagineItems.pot), new Object[]{
				"IxI","CBC","CCC",
				'I',Items.IRON_INGOT,
				'C', Blocks.COBBLESTONE,
				'B', Items.BUCKET
		});
		
		PotRecipes.getInstance().addRecipe(new ItemStack(Items.EGG,1), new ItemStack(ExSartagineItems.boiled_egg,1));
		PotRecipes.getInstance().addRecipe(new ItemStack(Items.BEETROOT_SEEDS,1), new ItemStack(ExSartagineItems.boiled_beans,1));
		PotRecipes.getInstance().addRecipe(new ItemStack(Items.POTATO,1), new ItemStack(ExSartagineItems.boiled_potato,1));
	}
}
