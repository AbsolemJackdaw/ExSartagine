package subaraki.exsartagine.tileentity;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityCooker extends TileEntity implements ITickable {

	protected boolean isCooking = false;
	protected int cookingTime = 0;

	protected static final int RESULT = 1;
	protected static final int ENTRY = 0;

	private ISHCooker inventory;
	private RangedWrapper input;
	private RangedWrapper output;

	/**inits inventory with 2 slots. input and output*/
	protected void initInventory(){
		inventory = new ISHCooker(2, this);
		input = new RangedWrapper(inventory, 0, 1);
		output = new RangedWrapper(inventory, 1, 2);
	}
	
	/**i nit inventory with more slots, where 0 is input, and x>0 is output*/
	protected void initInventory(int slots){
		inventory = new ISHCooker(slots, this);
		
		input = new RangedWrapper(inventory, 0, 1);
		output = new RangedWrapper(inventory, 1, slots);
		
	}

	public void setEntry(ItemStack stack){
		getInventory().insertItem(ENTRY, stack, false);
	}

	public void setResult(ItemStack stack){
		setResult(RESULT, stack);
	}
	
	public void setResult(int slot, ItemStack stack){
		getInventory().insertItem(slot, stack, false);
	}
	
	public ItemStack getEntry(){
		return getInventory().getStackInSlot(ENTRY);
	}

	public ItemStack getResult() {
		return this.getResult(RESULT);
	}

	public ItemStack getResult(int slot){
		return getInventory().getStackInSlot(slot);
	}
	
	public ItemStack getEntryStackOne(){
		ItemStack stack = getInventory().getStackInSlot(ENTRY);
		return stack.copy(); 
	}

	public boolean isValid(ItemStack stack){
		return false;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

			if(facing == facing.UP)
				return false;

			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

			if(facing == null)
				return (T) inventory;
			
			if(EnumFacing.DOWN == facing) //to prevent npe. facing can be null
				return (T)output;
			else
				return (T)input;

		}
		return super.getCapability(capability, facing);
	}

	public IItemHandler getInventory(){
		return getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null) ;
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

	@Override
	public void update() {
	}
}
