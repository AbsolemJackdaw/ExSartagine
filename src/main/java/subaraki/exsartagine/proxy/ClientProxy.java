package subaraki.exsartagine.proxy;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import subaraki.exsartagine.item.ExSartagineItems;
import subaraki.exsartagine.tileentity.TileEntityPan;
import subaraki.exsartagine.tileentity.TileEntityPot;
import subaraki.exsartagine.tileentity.TileEntityRange;
import subaraki.exsartagine.tileentity.TileEntityRangeExtension;
import subaraki.exsartagine.tileentity.TileEntitySmelter;
import subaraki.exsartagine.tileentity.render.TileEntityRenderFood;

public class ClientProxy extends ServerProxy {

	@Override
	public void registerRenders() {
		ExSartagineItems.registerRenders();

	}

	@Override
	public void registerTileEntityAndRenderer() {
		GameRegistry.registerTileEntity(TileEntityPan.class, "tileentityexsartagine");
		GameRegistry.registerTileEntity(TileEntitySmelter.class, "tileentityexsartaginesmelter");
		GameRegistry.registerTileEntity(TileEntityPot.class, "tileentityexsartaginepot");
		GameRegistry.registerTileEntity(TileEntityRange.class, "tileentityexsartaginerange");
		GameRegistry.registerTileEntity(TileEntityRangeExtension.class, "tileentityexsartaginerangeextension");
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPan.class, new TileEntityRenderFood());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmelter.class, new TileEntityRenderFood());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPot.class, new TileEntityRenderFood());
	}
}