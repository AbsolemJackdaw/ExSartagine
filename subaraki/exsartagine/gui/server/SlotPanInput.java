package subaraki.exsartagine.gui.server;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotPanInput extends SlotItemHandler {

	public SlotPanInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(!stack.isEmpty())
		{
			if(stack.getItem() instanceof ItemFood)
			{
				if(!FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty())
				{
					return true;
				}
			}
		}
		return false;
	}
}