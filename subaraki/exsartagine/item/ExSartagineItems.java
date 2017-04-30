package subaraki.exsartagine.item;

import static lib.item.ItemRegistry.registerItem;
import static lib.item.ItemRegistry.registerRender;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import subaraki.exsartagine.block.ExSartagineBlock;
import subaraki.exsartagine.mod.ExSartagine;

public class ExSartagineItems {

	public static Item pan;
	
	public static void load(){
		pan = new ItemBlock(ExSartagineBlock.pan).setRegistryName(ExSartagineBlock.pan.getRegistryName());
		
		register();
	}

	private static void register() {
		registerItem(pan);		
	}
	
	public static void registerRenders(){
		registerRender(pan, "pan", ExSartagine.MODID);
	}
}
