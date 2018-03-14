package subaraki.exsartagine.item;

import static lib.item.ItemRegistry.registerItem;
import static lib.item.ItemRegistry.registerRender;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import subaraki.exsartagine.block.ExSartagineBlock;
import static subaraki.exsartagine.util.Reference.*;

public class ExSartagineItems {

	public static Item pan;
	public static Item smelter;
	public static Item pot;
	public static Item range;

	public static ItemFood boiled_egg;
	public static ItemFood boiled_beans;
	public static ItemFood boiled_potato;

	public static ItemFood pizza_plain;

	public static ItemFood pizza_meat;
	public static ItemFood pizza_chicken;
	public static ItemFood pizza_fish;
	public static ItemFood pizza_sweet;

	public static ItemFood pizza_meat_raw;
	public static ItemFood pizza_chicken_raw;
	public static ItemFood pizza_fish_raw;
	public static ItemFood pizza_sweet_raw;

	public static ItemFood bread_fine;
	public static ItemFood bread_meat;
	public static ItemFood bread_veggie;

	public static ItemFood bread_meat_raw;
	public static ItemFood bread_veggie_raw;

	public static Item dry_strings;

	public static Item spaghetti_raw;
	public static ItemNoodle spaghetti_cooked;
	public static ItemNoodle spaghetti_sauced;
	public static ItemNoodle spaghetti_bolognaise;
	public static ItemNoodle spaghetti_cheese;
	public static ItemNoodle spaghetti_veggie;

	public static Item noodles_chicken;
	public static Item noodles_fish;
	public static Item noodles_meat;
	public static Item noodles_veggie;
	public static ItemNoodle noodles_chicken_cooked;
	public static ItemNoodle noodles_fish_cooked;
	public static ItemNoodle noodles_meat_cooked;
	public static ItemNoodle noodles_veggie_cooked ;

	public static Item pizza_dough;
	public static Item bread_dough;

	public static Item dough;
	public static Item salt;
	public static Item yeast;
	public static Item curd;
	public static Item flour;

	public static CreativeTabs foods = new CreativeTabs("exsartaginefoods") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(pizza_plain);
		}
	};

	public static CreativeTabs pots = new CreativeTabs("potsnpans") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(range);
		}
	};
	public static void load(){
		pan = new ItemBlock(ExSartagineBlock.pan).setRegistryName(ExSartagineBlock.pan.getRegistryName()).setCreativeTab(pots);
		smelter = new ItemBlock(ExSartagineBlock.smelter).setRegistryName(ExSartagineBlock.smelter.getRegistryName()).setCreativeTab(pots);
		pot = new ItemBlock(ExSartagineBlock.pot).setRegistryName(ExSartagineBlock.pot.getRegistryName()).setCreativeTab(pots);
		range = new ItemBlock(ExSartagineBlock.range).setRegistryName(ExSartagineBlock.range.getRegistryName()).setCreativeTab(pots);

		boiled_egg = (ItemFood) new ItemFood(4, 0.5f, false).setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".egg.boiled").setRegistryName("egg.boiled");
		boiled_beans = (ItemFood) new ItemFood(3, 0.2f,false).setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".beans.boiled").setRegistryName("beans.boiled");
		boiled_potato = (ItemFood) new ItemFood(6, 0.5f,false).setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".potato.boiled").setRegistryName("potato.boiled");

		flour = new Item().setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".flour").setRegistryName("flour");
		salt = new Item().setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".salt").setRegistryName("salt");
		yeast = new Item().setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".yeast").setRegistryName("yeast");
		curd = new Item(){
			public void addInformation(net.minecraft.item.ItemStack stack, net.minecraft.entity.player.EntityPlayer playerIn, java.util.List<String> tooltip, boolean advanced) {
				String text = ChatFormatting.ITALIC + "Simple Cheese";

				if(!tooltip.contains(text))
					tooltip.add(text);
			};
		}.setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".curd").setRegistryName("curd").setCreativeTab(foods);

		dough = new Item().setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".dough").setRegistryName("dough").setCreativeTab(foods);
		bread_dough = new Item().setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".doughBread").setRegistryName("doughBread").setCreativeTab(foods);

		pizza_dough = new Item().setCreativeTab(CreativeTabs.FOOD).setUnlocalizedName(MODID+".doughPizza").setRegistryName("doughPizza").setCreativeTab(foods);
		pizza_plain = (ItemFood)new ItemFood(6, 0.6f, false).setUnlocalizedName(MODID+".pizzaPlain").setRegistryName("pizzaPlain").setCreativeTab(foods);
		pizza_meat = (ItemFood)new ItemFood(12, 0.9f, false).setUnlocalizedName(MODID+".pizzaMeat").setRegistryName("pizzaMeat").setCreativeTab(foods);
		pizza_chicken = (ItemFood)new ItemFood(10, 0.9f, false).setUnlocalizedName(MODID+".pizzaChicken").setRegistryName("pizzaChicken").setCreativeTab(foods);
		pizza_sweet = (ItemFood)new ItemFood(10, 0.7f, false).setUnlocalizedName(MODID+".pizzaSweet").setRegistryName("pizzaSweet").setCreativeTab(foods);
		pizza_fish = (ItemFood)new ItemFood(9, 1.0f, false).setUnlocalizedName(MODID+".pizzaFish").setRegistryName("pizzaFish").setCreativeTab(foods);

		pizza_meat_raw = (ItemFood)new ItemFood(4, 0.3f, false).setUnlocalizedName(MODID+".pizzaMeatRaw").setRegistryName("pizzaMeatRaw").setCreativeTab(foods);
		pizza_chicken_raw = (ItemFood)new ItemFood(3, 0.3f, false).setUnlocalizedName(MODID+".pizzaChickenRaw").setRegistryName("pizzaChickenRaw").setCreativeTab(foods);
		pizza_sweet_raw = (ItemFood)new ItemFood(3, 0.2f, false).setUnlocalizedName(MODID+".pizzaSweetRaw").setRegistryName("pizzaSweetRaw").setCreativeTab(foods);
		pizza_fish_raw = (ItemFood)new ItemFood(2, 0.4f, false).setUnlocalizedName(MODID+".pizzaFishRaw").setRegistryName("pizzaFishRaw").setCreativeTab(foods);

		bread_fine = (ItemFood)new ItemFood(5, 0.8f, false).setUnlocalizedName(MODID+".breadFine").setRegistryName("breadFine").setCreativeTab(foods);
		bread_meat = (ItemFood)new ItemFood(4, 0.45f, false).setUnlocalizedName(MODID+".breadMeat").setRegistryName("breadMeat").setCreativeTab(foods);
		bread_veggie = (ItemFood)new ItemFood(8, 0.9f, false).setUnlocalizedName(MODID+".breadVeggie").setRegistryName("breadVeggie").setCreativeTab(foods);

		bread_meat_raw = (ItemFood)new ItemFood(2, 0.3f, false).setUnlocalizedName(MODID+".breadMeatRaw").setRegistryName("breadMeatRaw").setCreativeTab(foods);
		bread_veggie_raw = (ItemFood)new ItemFood(3, 0.3f, false).setUnlocalizedName(MODID+".breadVeggieRaw").setRegistryName("breadVeggieRaw").setCreativeTab(foods);

		dry_strings = new Item().setUnlocalizedName(MODID+".dry_noodles").setRegistryName("dry_noodles").setCreativeTab(foods);

		spaghetti_raw = new Item().setUnlocalizedName(MODID+".spaghetti_raw").setRegistryName("spaghetti_raw").setCreativeTab(foods);
		spaghetti_cooked = (ItemNoodle)new ItemNoodle(3, 0.7f, false).setUnlocalizedName(MODID+".spaghetti_cooked").setRegistryName("spaghetti_cooked").setCreativeTab(foods);
		spaghetti_sauced = (ItemNoodle)new ItemNoodle(5, 0.5f, false).setUnlocalizedName(MODID+".spaghetti_sauced").setRegistryName("spaghetti_sauced").setCreativeTab(foods);
		spaghetti_bolognaise = (ItemNoodle)new ItemNoodle(5, 0.7f, false).setUnlocalizedName(MODID+".spaghetti_bolognaise").setRegistryName("spaghetti_bolognaise").setCreativeTab(foods);
		spaghetti_cheese = (ItemNoodle)new ItemNoodle(5, 0.8f, false).setUnlocalizedName(MODID+".spaghetti_cheese").setRegistryName("spaghetti_cheese").setCreativeTab(foods);
		spaghetti_veggie = (ItemNoodle)new ItemNoodle(8, 0.5f, false).setUnlocalizedName(MODID+".spaghetti_veggie").setRegistryName("spaghetti_veggie").setCreativeTab(foods);

		noodles_chicken_cooked = (ItemNoodle)new ItemNoodle(6, 0.7f, false).setUnlocalizedName(MODID+".noodles_chicken_cooked").setRegistryName("noodles_chicken_cooked").setCreativeTab(foods);
		noodles_fish_cooked = (ItemNoodle)new ItemNoodle(6, 0.7f, false).setUnlocalizedName(MODID+".noodles_fish_cooked").setRegistryName("noodles_fish_cooked").setCreativeTab(foods);
		noodles_meat_cooked = (ItemNoodle)new ItemNoodle(5, 0.8f, false).setUnlocalizedName(MODID+".noodles_meat_cooked").setRegistryName("noodles_meat_cooked").setCreativeTab(foods);
		noodles_veggie_cooked = (ItemNoodle)new ItemNoodle(7, 0.7f, false).setUnlocalizedName(MODID+".noodles_veggie_cooked").setRegistryName("noodles_veggie_cooked").setCreativeTab(foods);

		noodles_chicken = new Item().setUnlocalizedName(MODID+".noodles_chicken").setRegistryName("noodles_chicken").setCreativeTab(foods);
		noodles_fish = new Item().setUnlocalizedName(MODID+".noodles_fish").setRegistryName("noodles_fish").setCreativeTab(foods);
		noodles_meat= new Item().setUnlocalizedName(MODID+".noodles_meat").setRegistryName("noodles_meat").setCreativeTab(foods);
		noodles_veggie = new Item().setUnlocalizedName(MODID+".noodles_veggie").setRegistryName("noodles_veggie").setCreativeTab(foods);

		register();

		addToOreDict();
	}

	private static void addToOreDict() {
		OreDictionary.registerOre("egg", boiled_egg);
		OreDictionary.registerOre("ingredientEgg", boiled_egg);
		OreDictionary.registerOre("cropVegetable", boiled_potato);
		OreDictionary.registerOre("cropVegetable", boiled_beans);

		OreDictionary.registerOre("foodFlour", flour);
		OreDictionary.registerOre("dustFlour", flour);
		OreDictionary.registerOre("dustSalt", salt);
		OreDictionary.registerOre("itemCheese", curd);
		OreDictionary.registerOre("ingredientCheese", curd);
		OreDictionary.registerOre("itemYeast", yeast);
		OreDictionary.registerOre("foodDough", dough);
		OreDictionary.registerOre("foodDoughFlat", pizza_dough);
		OreDictionary.registerOre("foodDoughBread", bread_dough); //food registry for pan slot

		OreDictionary.registerOre("itemNoodles", dry_strings);
	}

	private static void register() {
		registerItem(pan);	
		registerItem(smelter);	
		registerItem(pot);	
		registerItem(range);	

		registerItem(boiled_egg);
		registerItem(boiled_beans);
		registerItem(boiled_potato);

		registerItem(salt);
		registerItem(flour);
		registerItem(curd);
		registerItem(yeast);
		registerItem(dough);

		registerItem(pizza_plain);
		registerItem(pizza_chicken);
		registerItem(pizza_chicken_raw);
		registerItem(pizza_fish);
		registerItem(pizza_fish_raw);
		registerItem(pizza_meat);
		registerItem(pizza_meat_raw);
		registerItem(pizza_sweet);
		registerItem(pizza_sweet_raw);
		registerItem(pizza_dough);

		registerItem(bread_dough);
		registerItem(bread_fine);
		registerItem(bread_meat);
		registerItem(bread_meat_raw);
		registerItem(bread_veggie);
		registerItem(bread_veggie_raw);

		registerItem(dry_strings);

		registerItem(noodles_chicken);
		registerItem(noodles_meat);
		registerItem(noodles_fish);
		registerItem(noodles_veggie);

		registerItem(noodles_chicken_cooked);
		registerItem(noodles_meat_cooked);
		registerItem(noodles_fish_cooked);
		registerItem(noodles_veggie_cooked);

		registerItem(spaghetti_raw);
		registerItem(spaghetti_cooked);
		registerItem(spaghetti_sauced);
		registerItem(spaghetti_bolognaise);
		registerItem(spaghetti_cheese);
		registerItem(spaghetti_veggie);
	}

	public static void registerRenders(){
		registerRender(pan, "pan", MODID);
		registerRender(smelter, "smelter", MODID);
		registerRender(pot, "pot", MODID);
		registerRender(range, "range", MODID);

		registerRender(boiled_egg, "egg", MODID);
		registerRender(boiled_beans, "beans", MODID);
		registerRender(boiled_potato, "potato", MODID);

		registerRender(pizza_plain, "pizza_plain", MODID);

		registerRender(pizza_chicken, "pizza_chicken_cooked", MODID);
		registerRender(pizza_sweet, "pizza_sweet_cooked", MODID);
		registerRender(pizza_meat, "pizza_meat_cooked", MODID);
		registerRender(pizza_fish, "pizza_fish_cooked", MODID);

		registerRender(pizza_chicken_raw, "pizza_chicken_raw", MODID);
		registerRender(pizza_sweet_raw, "pizza_sweet_raw", MODID);
		registerRender(pizza_meat_raw, "pizza_meat_raw", MODID);
		registerRender(pizza_fish_raw, "pizza_fish_raw", MODID);

		registerRender(bread_fine, "fine_bread", MODID);
		registerRender(bread_meat, "meat_minibread", MODID);
		registerRender(bread_veggie, "veggie_bread", MODID);

		registerRender(bread_meat_raw, "meat_minibread_raw", MODID);
		registerRender(bread_veggie_raw, "veggie_bread_raw", MODID);

		registerRender(pizza_dough, "dough", MODID);
		registerRender(bread_dough, "dough", MODID);
		registerRender(dough, "dough", MODID);
		registerRender(salt, "salt", MODID);
		registerRender(flour, "flour", MODID);
		registerRender(yeast, "yeast", MODID);
		registerRender(curd, "curd", MODID);

		registerRender(dry_strings, "dry_strings", MODID);

		registerRender(noodles_chicken, "noodles_chicken", MODID);
		registerRender(noodles_fish, "noodles_fish", MODID);
		registerRender(noodles_meat, "noodles_meat", MODID);
		registerRender(noodles_veggie, "noodles_veggie", MODID);

		registerRender(noodles_chicken_cooked, "noodles_chicken_cooked", MODID);
		registerRender(noodles_fish_cooked, "noodles_fish_cooked", MODID);
		registerRender(noodles_meat_cooked, "noodles_meat_cooked", MODID);
		registerRender(noodles_veggie_cooked, "noodles_veggie_cooked", MODID);

		registerRender(spaghetti_raw, "spaghetti_raw", MODID);
		registerRender(spaghetti_cooked, "spaghetti_cooked", MODID);
		registerRender(spaghetti_sauced, "spaghetti_sauced", MODID);
		registerRender(spaghetti_bolognaise, "spaghetti_bolognaise", MODID);
		registerRender(spaghetti_cheese, "spaghetti_cheese", MODID);
		registerRender(spaghetti_veggie, "spaghetti_veggie", MODID);

	}
}
