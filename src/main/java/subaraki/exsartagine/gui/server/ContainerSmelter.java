package subaraki.exsartagine.gui.server;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import subaraki.exsartagine.tileentity.TileEntitySmelter;

public class ContainerSmelter extends Container{

	private List<ItemStack> ores = new ArrayList<ItemStack>();

	public ContainerSmelter(InventoryPlayer playerInventory, TileEntitySmelter smelter) {

		for(String ore : OreDictionary.getOreNames())
			if(ore.toLowerCase().contains("ore"))
				ores.addAll(OreDictionary.getOres(ore));

		this.addSlotToContainer(new SlotSmelterInput(ores, smelter.getInventory(), 0, 56, 17));
		this.addSlotToContainer(new SlotPanOutput(playerInventory.player, smelter.getInventory(), 1, 116, 35));
		this.addSlotToContainer(new SlotPanOutput(playerInventory.player, smelter.getInventory(), 2, 140, 39));

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 9; ++j)
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));

		for (int k = 0; k < 9; ++k)
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

	/**
	 * Take a stack from the specified inventory slot.
	 */
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 1 || index == 2)
			{
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index != 0)
			{
				if (itemstack1.getItem() instanceof ItemBlock)
				{
					for(ItemStack stack : ores)
						if(OreDictionary.itemMatches(stack, itemstack1, false))
						{
							if (!this.mergeItemStack(itemstack1, 0, 1, false))
							{
								return ItemStack.EMPTY;
							}
							break;
						}
				}
				else if (index >= 3 && index < 30)
				{
					if (!this.mergeItemStack(itemstack1, 30, 39, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 3, 39, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}
}