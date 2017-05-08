package subaraki.exsartagine.tileentity;

import lib.recipes.PotRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import subaraki.exsartagine.block.BlockPot;
import subaraki.exsartagine.block.ExSartagineBlock;

public class TileEntityPot extends TileEntityCooker{

	/**max 192 , value of 3 stacks. one bucket = 192*/
	private int waterLevel = 0;

	public TileEntityPot() {
		this.inventory = new ItemStackHandler(2);
	}

	public int getWaterLevel() {
		return waterLevel;
	}

	public void replenishWater(){
		this.waterLevel = 192;
	}

	@Override
	public void update() {

		if(cookingTime == 125 && waterLevel > 0)
		{
			if(!world.isRemote){

				if(getEntry().getCount() > 0 )
				{
					if(getEntry().getCount() > 0 && 
							(getResult().isEmpty() || getResult().getCount() < getResult().getMaxStackSize()))
					{
						if(getResult().isEmpty())
						{
							ItemStack stack = PotRecipes.getInstance().getCookingResult(getEntryStackOne()).copy();
							getInventory().setStackInSlot(RESULT, stack.copy());
							getEntry().shrink(1);
						}
						else
						{
							getResult().grow(1);
							getEntry().shrink(1);
						}
					}
				}
			}

			cookingTime = 0;
			waterLevel--;
			world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), ExSartagineBlock.pot.getDefaultState(), 3);
		}

		if(isCooking())
		{
			if(!getEntry().isEmpty() && 
					getEntry().getCount() > 0 && 
					getWaterLevel() > 0 &&
					(getResult().getItem().equals(PotRecipes.getInstance().getCookingResult(getEntry()).getItem()) 
							|| getResult().isEmpty())) //or recipe fits
			{
				cookingTime++;
			}
			else if (cookingTime > 0)
				cookingTime--;
		}

		if(!world.isRemote)
		{
			//set water block rendering
			if(!world.getBlockState(pos).getValue(BlockPot.FULL) && waterLevel > 0)
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockPot.FULL, true), 3);
			//set water block gone
			if(world.getBlockState(pos).getValue(BlockPot.FULL) && waterLevel == 0)
				world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockPot.FULL, false), 3);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("water", waterLevel);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.waterLevel = compound.getInteger("water");
	}
}
