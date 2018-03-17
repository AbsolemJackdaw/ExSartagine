package subaraki.exsartagine.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import subaraki.exsartagine.block.BlockSmelter;
import subaraki.exsartagine.block.ExSartagineBlock;
import subaraki.exsartagine.gui.server.SlotSmelterInput;
import subaraki.exsartagine.util.ConfigHandler;

public class TileEntitySmelter extends TileEntityCooker {

	private static final int BONUSSLOT = 2;
	private int bonusChance = ConfigHandler.percent; // in percentage

	public TileEntitySmelter() {
		initInventory(3);
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
					if(world.rand.nextInt(100)+1 <= bonusChance){ //+1, so 0% is no chance [1-100]
						if(getBonus().isEmpty())
						{
							ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getEntryStackOne()).copy();
							setResult(BONUSSLOT, itemstack);
						}
						else
							getBonus().grow(1);
					}

					if(getResult().isEmpty())
					{
						ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getEntryStackOne()).copy();
						setResult(itemstack);
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
		return getResult(BONUSSLOT);
	}

	@Override
	public boolean isValid(ItemStack stack) {
		return new SlotSmelterInput(null, 0, 0, 0).isItemValid(stack);
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
