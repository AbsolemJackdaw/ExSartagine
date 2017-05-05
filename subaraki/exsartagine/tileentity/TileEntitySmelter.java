package subaraki.exsartagine.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import subaraki.exsartagine.block.BlockSmelter;
import subaraki.exsartagine.block.ExSartagineBlock;

public class TileEntitySmelter extends TileEntityCooker {

	private static final int BONUS = 2;

	public TileEntitySmelter() {
		this.inventory = new ItemStackHandler(3);
	}

	@Override
	public void update() {

		if(cookingTime == 199)
		{
			if(!world.isRemote)
			{
				if(getEntry().getCount() > 0 && 
						(getResult().isEmpty() || getResult().getCount() < getResult().getMaxStackSize()))
				{
					if(world.rand.nextInt(3) == 0){
						if(getBonus().isEmpty())
						{
							ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getEntryStackOne()).copy();
							inventory.setStackInSlot(BONUS, itemstack);
						}
						else
							getBonus().grow(1);
					}

					if(getResult().isEmpty())
					{
						ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getEntryStackOne()).copy();
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
			world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), ExSartagineBlock.smelter.getDefaultState(), 3);
		}

		if(isCooking)
		{
			if(getEntry().getCount() > 0 && 
					(getResult().getItem().equals(FurnaceRecipes.instance().getSmeltingResult(getEntryStackOne()).getItem()) || getResult().isEmpty()))
				cookingTime++;
			else if (cookingTime > 0)
				cookingTime --;
		}
		
		if(!world.isRemote)
		{
			//set lava block rendering
			if(!world.getBlockState(pos).getValue(BlockSmelter.FULL) && (!getResult().isEmpty() || !getBonus().isEmpty()))
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockSmelter.FULL, true), 3);
			//set lava block gone
			if(world.getBlockState(pos).getValue(BlockSmelter.FULL) && (getResult().isEmpty() && getBonus().isEmpty()))
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockSmelter.FULL, false), 3);
	
		}
	}

	private ItemStack getBonus(){
		return inventory.getStackInSlot(BONUS);
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
