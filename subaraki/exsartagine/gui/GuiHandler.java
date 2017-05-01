package subaraki.exsartagine.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import subaraki.exsartagine.gui.client.GuiPan;
import subaraki.exsartagine.gui.server.ContainerPan;
import subaraki.exsartagine.tileentity.TileEntityPan;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityPan)
			return new ContainerPan(player.inventory, (TileEntityPan)te);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityPan)
			return new GuiPan(player, (TileEntityPan)te);

		return null;
	}

}
