package subaraki.exsartagine.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import subaraki.exsartagine.block.ExSartagineBlock;

public class TileEntityPan extends TileEntityCooker{

	public TileEntityPan() {
		this.inventory = new ItemStackHandler(2);
	}
	
	@Override
	public void update() {

		if(cookingTime == 125)
		{
			if(!world.isRemote)
			{
				if(getEntry().getCount() > 0 && 
						(getResult().isEmpty() || getResult().getCount() < getResult().getMaxStackSize()))
				{
					if(getResult().isEmpty())
					{
						ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getEntryStackOne()).copy();
						//itemstack.setCount(1);
						inventory.setStackInSlot(RESULT, itemstack);

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
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}
}
