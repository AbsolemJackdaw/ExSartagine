package subaraki.exsartagine.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryPan extends ItemStackHandler{
 
	public InventoryPan(){
		super(2);
	}
	
	@Override
	public NBTTagCompound serializeNBT() {
		return super.serializeNBT();
	}
}
