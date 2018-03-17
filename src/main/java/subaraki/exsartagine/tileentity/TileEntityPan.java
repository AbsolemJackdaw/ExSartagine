package subaraki.exsartagine.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import subaraki.exsartagine.block.ExSartagineBlock;
import subaraki.exsartagine.gui.server.SlotPanInput;

public class TileEntityPan extends TileEntityCooker{

	public TileEntityPan() {
		initInventory();
	}
	
	@Override
	public void update() {

		if(cookingTime == 125)
		{
			if(!world.isRemote)
			{
				if(getEntry().getCount() > 0 && (getResult().isEmpty() || getResult().getCount() < getResult().getMaxStackSize()))
				{
					if(getResult().isEmpty())
					{
						ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getEntry()).copy();
						
						setResult(itemstack.copy());
						getEntry().shrink(1);
					}
					else
					{
						getResult().grow(1);
						getEntry().shrink(1);
					}
				}
			}
			cookingTime = 0;
			world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), ExSartagineBlock.pan.getDefaultState(), 3);
		}

		if(isCooking)
		{
			if(getEntry().getCount() > 0 && 
					(getResult().getItem().equals(FurnaceRecipes.instance().getSmeltingResult(getEntryStackOne()).getItem()) || getResult().isEmpty()))
				cookingTime++;
			else if (cookingTime > 0)
				cookingTime --;
		}
	}

	@Override
	public boolean isValid(ItemStack stack) {
		return new SlotPanInput(null, 0, 0, 0).isItemValid(stack);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}
}
