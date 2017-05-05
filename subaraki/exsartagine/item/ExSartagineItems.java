package subaraki.exsartagine.item;

import static lib.item.ItemRegistry.registerItem;
import static lib.item.ItemRegistry.registerRender;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import subaraki.exsartagine.block.ExSartagineBlock;
import subaraki.exsartagine.mod.ExSartagine;

public class ExSartagineItems {

	public static Item pan;
	public static Item smelter;
	public static Item pot;

	public static void load(){
		pan = new ItemBlock(ExSartagineBlock.pan).setRegistryName(ExSartagineBlock.pan.getRegistryName());
		smelter = new ItemBlock(ExSartagineBlock.smelter).setRegistryName(ExSartagineBlock.smelter.getRegistryName());
		pot = new ItemBlock(ExSartagineBlock.pot).setRegistryName(ExSartagineBlock.pot.getRegistryName());

		register();
	}

	private static void register() {
		registerItem(pan);	
		registerItem(smelter);	
		registerItem(pot);		
	}
	
	public static void registerRenders(){
		registerRender(pan, "pan", ExSartagine.MODID);
		registerRender(smelter, "smelter", ExSartagine.MODID);
		registerRender(pot, "pot", ExSartagine.MODID);
	}
}
