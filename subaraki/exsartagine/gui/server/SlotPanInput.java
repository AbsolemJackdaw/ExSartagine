package subaraki.exsartagine.gui.server;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.oredict.OreDictionary;

public class SlotPanInput extends SlotItemHandler {

	public SlotPanInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(!stack.isEmpty())
		{
			boolean flag = false;
        	int[] ids = OreDictionary.getOreIDs(stack);
        	for(int id: ids)
        		if(OreDictionary.getOreName(id).contains("food")){
        			flag = true;
        			break;
        		}
        	
			if(stack.getItem() instanceof ItemFood || flag)
			{
				ItemFood food = (ItemFood)stack.getItem();
				if(!FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty())
				{
					return true;
				}
			}
		}
		return false;
	}
}