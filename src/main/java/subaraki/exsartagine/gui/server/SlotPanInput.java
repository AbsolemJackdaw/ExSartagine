package subaraki.exsartagine.gui.server;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.oredict.OreDictionary;
import subaraki.exsartagine.recipe.PanRecipes;

public class SlotPanInput extends SlotItemHandler {

	
	public SlotPanInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack entryStack) {
		if(!entryStack.isEmpty())
		{
			//Prioritise PanRecipe inputs
			if(!PanRecipes.getInstance().getCookingResult(entryStack).isEmpty())
			{
				return true;
			}
			//if no pan input was found, check the furnace recipes for a resulting food item from the entry stack
			else
			{
				ItemStack resultingStack = FurnaceRecipes.instance().getSmeltingResult(entryStack);

				if(resultingStack.isEmpty())
					return false;
				
				boolean flag = getOreIDs(resultingStack);
				
				if(resultingStack.getItem() instanceof ItemFood || flag)
				{
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean getOreIDs(ItemStack stack){
		int[] ids = OreDictionary.getOreIDs(stack);
		for(int id: ids)
			if(OreDictionary.getOreName(id).contains("food")){
				return true;
			}
		return false;
	}
}