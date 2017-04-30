package subaraki.exsartagine.block;

import static lib.block.BlockRegistry.registerBlock;

import net.minecraft.block.Block;

public class ExSartagineBlock {

	public static Block pan;
	
	public static void load(){
		pan = new BlockPan();
		
		register();
	}
	
	private static void register(){
		registerBlock(pan);
	}
}
