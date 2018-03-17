package subaraki.exsartagine.recipe;

import java.util.ArrayList;
import java.util.Map.Entry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class SmelterEntries{

	private static final SmelterEntries INSTANCE = new SmelterEntries();
	private ArrayList<SmelterEntry> smelterEntryList = new ArrayList<SmelterEntry>();

	public static SmelterEntries getInstance() {
		return INSTANCE;
	}

	public void addEntry(ItemStack entry){
		SmelterEntry smelterEntry = new SmelterEntry(entry);
		smelterEntryList.add(smelterEntry);
	}

	public void removeEntry(ItemStack entry){
		SmelterEntry smelterEntry = new SmelterEntry(entry);
		for(SmelterEntry se : smelterEntryList)
		{
			if(se.equals(smelterEntry))
			{
				smelterEntryList.remove(se);
				break;
			}
		}
	}

	public ItemStack getResult(ItemStack stack)
	{
		SmelterEntry entry = new SmelterEntry(stack);

		if(smelterEntryList.contains(entry))
		{
			if(!FurnaceRecipes.instance().getSmeltingResult(stack).isEmpty())
				return FurnaceRecipes.instance().getSmeltingResult(stack);
		}

		return ItemStack.EMPTY;
	}

	private boolean areEntriesEqual(SmelterEntry a, SmelterEntry b){

		if(a.meta == b.meta && a.item == b.item)
			return true;

		return false;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	private class SmelterEntry{

		int amount;
		Item item;
		int meta;

		public SmelterEntry(ItemStack stack) {
			amount = stack.getCount();
			item = stack.getItem();
			meta = stack.getMetadata();
		}

		public ItemStack getStack(){
			return new ItemStack(item,amount,meta);
		}

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof SmelterEntry))
				return false;

			if(!areEntriesEqual((SmelterEntry)obj, this))
				return false;

			return true;
		}
	}
}
