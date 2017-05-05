package subaraki.exsartagine.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PotRecipes {

	private static Map<Item, Item> recipes = new HashMap<Item, Item>();

	public static void addRecipe(ItemStack entry, ItemStack output){
		recipes.put(entry.getItem(), output.getItem());
	}

	public static ItemStack getCookingResult(ItemStack stack)
    {
        for (Entry<Item, Item> entry : recipes.entrySet())
        {
            if (stack.getItem() == (Item)entry.getKey())
            {
                return new ItemStack((Item)entry.getValue());
            }
        }
        return ItemStack.EMPTY;
    }
	
	public static ItemStack getResultFor(ItemStack entry){

		for(Item input : recipes.keySet())
		{
			if(entry.getItem() == input)
			{
				return new ItemStack(recipes.get(input));
			}
		}
		return ItemStack.EMPTY;
	}
}
