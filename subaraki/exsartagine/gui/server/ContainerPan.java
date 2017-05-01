package subaraki.exsartagine.gui.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerPan extends Container{
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}