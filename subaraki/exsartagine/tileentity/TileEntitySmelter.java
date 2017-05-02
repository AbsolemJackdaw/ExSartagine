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

public class TileEntitySmelter extends TileEntity implements ITickable {

	private boolean isCooking = false;
	private int cookingTime = 0;

	private static final int BONUS = 2;
	private static final int RESULT = 1;
	private static final int ENTRY = 0;

	private ItemStackHandler inventory = new ItemStackHandler(3);

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return (T) inventory;
		}
		return super.getCapability(capability, facing);
	}

	public ItemStackHandler getInventory(){
		return inventory;
	}

	public void setCooking(){
		isCooking = true;
	}

	public void stopCooking(){
		isCooking = false;
		cookingTime = 0;
	}

	public boolean isCooking(){
		return isCooking;
	}

	@Override
	public void update() {

		if(cookingTime == 199)
		{
			if(!world.isRemote)
			{
				if(getOre().getCount() > 0 && 
						(getResult().isEmpty() || getResult().getCount() < getResult().getMaxStackSize()))
				{
					if(world.rand.nextInt(3) == 0){
						if(getBonus().isEmpty())
						{
							ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getOreStackOne()).copy();
							inventory.setStackInSlot(BONUS, itemstack);
						}
						else
							getBonus().grow(1);
					}

					if(getResult().isEmpty())
					{
						ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getOreStackOne()).copy();
						inventory.setStackInSlot(RESULT, itemstack);
						getOre().shrink(1);
					}
					else
					{
						getResult().grow(1);
						getOre().shrink(1);
					}
				}
			}
			cookingTime = 0;
			world.notifyBlockUpdate(getPos(), world.getBlockState(getPos()), ExSartagineBlock.smelter.getDefaultState(), 3);
		}

		if(isCooking)
		{
			if(getOre().getCount() > 0 && 
					(getResult().getItem().equals(FurnaceRecipes.instance().getSmeltingResult(getOreStackOne()).getItem()) || getResult().isEmpty()))
				cookingTime++;
			else
				cookingTime = 0;
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

	private ItemStack getOre(){
		return inventory.getStackInSlot(ENTRY);
	}
	private ItemStack getOreStackOne(){
		ItemStack stack = inventory.getStackInSlot(ENTRY).copy();
		//stack.setCount(1);
		return stack;
	}
	private ItemStack getResult(){
		return inventory.getStackInSlot(RESULT);
	}
	private ItemStack getBonus(){
		return inventory.getStackInSlot(BONUS);
	}

	/**maxes at 200 to restart from 0*/
	public int getCookingProgress(){
		return cookingTime;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("cooktime", cookingTime);
		compound.setBoolean("cooking", isCooking);
		compound.setTag("inv", inventory.serializeNBT());
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("cooktime") && compound.hasKey("cooking")){
			this.isCooking = compound.getBoolean("cooking");
			this.cookingTime = compound.getInteger("cooktime");
		}
		if(compound.hasKey("inv"))
			inventory.deserializeNBT(compound.getCompoundTag("inv"));
	}

	/////////////////3 METHODS ABSOLUTELY NEEDED FOR CLIENT/SERVER SYNCING/////////////////////
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);

		return new SPacketUpdateTileEntity(getPos(), 0, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbt =  super.getUpdateTag();
		writeToNBT(nbt);
		return nbt;
	}

	//calls readFromNbt by default. no need to add anything in here
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
	}
	////////////////////////////////////////////////////////////////////

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
	{
		return oldState.getBlock() != newSate.getBlock();
	}
}
