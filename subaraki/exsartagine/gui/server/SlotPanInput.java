package subaraki.exsartagine.gui.server;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotPanInput extends SlotItemHandler {

	public SlotPanInput(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
	}
}
