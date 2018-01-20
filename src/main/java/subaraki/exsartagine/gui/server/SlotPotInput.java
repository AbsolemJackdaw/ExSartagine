package subaraki.exsartagine.gui.server;

import lib.recipes.PotRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotPotInput extends SlotItemHandler {

	public SlotPotInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(!stack.isEmpty())
		{
			if(!PotRecipes.getInstance().getCookingResult(stack).isEmpty())
			{
				return true;
			}
		}
		return false;
	}
}