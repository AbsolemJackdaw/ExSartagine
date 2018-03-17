package subaraki.exsartagine.gui.server;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import subaraki.exsartagine.recipe.SmelterEntries;

public class SlotSmelterInput extends SlotItemHandler {


	public SlotSmelterInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(!stack.isEmpty())
		{
			boolean hasSmelterEntry = !SmelterEntries.getInstance().getResult(stack).isEmpty();

			if(hasSmelterEntry)
			{
				return true;
			}
			else
			{
				Item furnaceResult = FurnaceRecipes.instance().getSmeltingResult(stack).getItem();
				//you can still smelt gold/iron tools and armor into a nugget !
				if(furnaceResult.equals(Items.GOLD_NUGGET) || furnaceResult.equals(Items.IRON_NUGGET))
					return true; 
			}
		}
		return false;
	}
}