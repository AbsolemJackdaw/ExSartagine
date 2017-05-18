package subaraki.exsartagine.block;

import static lib.block.BlockRegistry.registerBlock;

import net.minecraft.block.Block;

public class ExSartagineBlock {

	public static Block pan;
	public static Block smelter;
	public static Block pot;
	public static Block range;
	public static Block range_extension;
	public static Block range_extension_lit;

	public static void load(){
		pan = new BlockPan();
		smelter = new BlockSmelter();
		pot = new BlockPot();
		range = new BlockRange();
		range_extension = new BlockRangeExtension("range_extended");
		range_extension_lit = new BlockRangeExtension("range_extended_lit");
		register();
	}
	
	private static void register(){
		registerBlock(pan);
		registerBlock(smelter);
		registerBlock(pot);
		registerBlock(range);
		registerBlock(range_extension);
		registerBlock(range_extension_lit);

	}
}
