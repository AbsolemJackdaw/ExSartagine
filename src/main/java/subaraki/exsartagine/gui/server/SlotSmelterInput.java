package subaraki.exsartagine.gui.server;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.oredict.OreDictionary;

public class SlotSmelterInput extends SlotItemHandler {

	List<ItemStack> ores = new ArrayList<>();

	public SlotSmelterInput(List<ItemStack> ores, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.ores = ores;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(!stack.isEmpty())
		{
			if(stack.getItem() instanceof ItemBlock)
			{
				for(ItemStack ore : ores)
					if(OreDictionary.itemMatches(ore, stack, false))
					{
						return true;
					}
			}
			
			else
			{
				ItemStack result = FurnaceRecipes.instance().getSmeltingResult(getStack());
				if (result.getItem().equals(Items.IRON_NUGGET))
				{
					return true;
				}
			}
		}
		return false;
	}
}