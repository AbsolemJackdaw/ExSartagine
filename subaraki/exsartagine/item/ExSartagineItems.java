package subaraki.exsartagine.item;

import static lib.item.ItemRegistry.registerItem;
import static lib.item.ItemRegistry.registerRender;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import subaraki.exsartagine.block.ExSartagineBlock;
import subaraki.exsartagine.mod.ExSartagine;

public class ExSartagineItems {

	public static Item pan;
	public static Item smelter;
	public static Item pot;

	public static ItemFood boiled_egg;
	public static ItemFood boiled_beans;
	public static ItemFood boiled_potato;

	public static void load(){
		pan = new ItemBlock(ExSartagineBlock.pan).setRegistryName(ExSartagineBlock.pan.getRegistryName());
		smelter = new ItemBlock(ExSartagineBlock.smelter).setRegistryName(ExSartagineBlock.smelter.getRegistryName());
		pot = new ItemBlock(ExSartagineBlock.pot).setRegistryName(ExSartagineBlock.pot.getRegistryName());

		boiled_egg = (ItemFood) new ItemFood(4, 0.5f, false).setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(ExSartagine.MODID+".egg.boiled").setRegistryName("egg.boiled");
		boiled_beans = (ItemFood) new ItemFood(3, 0.2f,false).setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(ExSartagine.MODID+".beans.boiled").setRegistryName("beans.boiled");
		boiled_potato = (ItemFood) new ItemFood(6, 0.5f,false).setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(ExSartagine.MODID+".potato.boiled").setRegistryName("potato.boiled");

		register();
	}

	private static void register() {
		registerItem(pan);	
		registerItem(smelter);	
		registerItem(pot);	
		
		registerItem(boiled_egg);
		registerItem(boiled_beans);
		registerItem(boiled_potato);

	}

	public static void registerRenders(){
		registerRender(pan, "pan", ExSartagine.MODID);
		registerRender(smelter, "smelter", ExSartagine.MODID);
		registerRender(pot, "pot", ExSartagine.MODID);
		
		registerRender(boiled_egg, "egg", ExSartagine.MODID);
		registerRender(boiled_beans, "beans", ExSartagine.MODID);
		registerRender(boiled_potato, "potato", ExSartagine.MODID);
	}
}
