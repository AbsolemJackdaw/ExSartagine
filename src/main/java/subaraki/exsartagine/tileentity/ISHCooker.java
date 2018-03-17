package subaraki.exsartagine.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ISHCooker extends ItemStackHandler{

	private TileEntityCooker tec;
	public ISHCooker(int slots, TileEntityCooker tec) {
		super(slots);
		this.tec = tec;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if(slot == 0 && tec.isValid(stack) || slot > 0)
			return super.insertItem(slot, stack, simulate);
		else
			return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return super.extractItem(slot, amount, simulate);
	}

	@Override
	protected void onContentsChanged(int slot) {
		tec.markDirty();
	}
}
