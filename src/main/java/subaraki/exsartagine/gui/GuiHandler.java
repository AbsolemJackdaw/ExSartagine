package subaraki.exsartagine.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import subaraki.exsartagine.gui.client.GuiPan;
import subaraki.exsartagine.gui.client.GuiPot;
import subaraki.exsartagine.gui.client.GuiRange;
import subaraki.exsartagine.gui.client.GuiSmelter;
import subaraki.exsartagine.gui.server.ContainerPan;
import subaraki.exsartagine.gui.server.ContainerPot;
import subaraki.exsartagine.gui.server.ContainerRange;
import subaraki.exsartagine.gui.server.ContainerSmelter;
import subaraki.exsartagine.tileentity.TileEntityPan;
import subaraki.exsartagine.tileentity.TileEntityPot;
import subaraki.exsartagine.tileentity.TileEntityRange;
import subaraki.exsartagine.tileentity.TileEntitySmelter;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityPan && ID == 0)
			return new ContainerPan(player.inventory, (TileEntityPan)te);
		if(te instanceof TileEntitySmelter && ID == 1)
			return new ContainerSmelter(player.inventory, (TileEntitySmelter)te);
		if(te instanceof TileEntityPot && ID == 2)
			return new ContainerPot(player.inventory, (TileEntityPot)te);
		if(te instanceof TileEntityRange && ID == 3)
			return new ContainerRange(player.inventory, (TileEntityRange)te);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityPan && ID == 0)
			return new GuiPan(player, (TileEntityPan)te);
		if(te instanceof TileEntitySmelter && ID == 1)
			return new GuiSmelter(player, (TileEntitySmelter)te);
		if(te instanceof TileEntityPot && ID == 2)
			return new GuiPot(player, (TileEntityPot)te);
		if(te instanceof TileEntityRange && ID == 3)
			return new GuiRange(player, (TileEntityRange)te);
		
		return null;
	}
}
