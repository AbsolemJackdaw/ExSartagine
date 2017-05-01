package subaraki.exsartagine.proxy;

import net.minecraftforge.fml.common.registry.GameRegistry;
import subaraki.exsartagine.tileentity.TileEntityPan;

public class ServerProxy {

	public void registerRenders(){}
	
	public void registerTileEntityAndRenderer(){
		GameRegistry.registerTileEntity(TileEntityPan.class, "tileentityexsartagine");
	}
}
