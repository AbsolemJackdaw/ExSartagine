package subaraki.exsartagine.block;

import static lib.block.BlockRegistry.registerBlock;

import net.minecraft.block.Block;

public class ExSartagineBlock {

	public static Block pan;
	public static Block smelter;

	public static void load(){
		pan = new BlockPan();
		smelter = new BlockSmelter();
		
		register();
	}
	
	private static void register(){
		registerBlock(pan);
		registerBlock(smelter);
	}
}
