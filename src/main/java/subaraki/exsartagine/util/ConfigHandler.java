package subaraki.exsartagine.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler 
{
	public static ConfigHandler instance = new ConfigHandler();

	public static int percent = 33;

	public void loadConfig(File file)
	{
		Configuration config = new Configuration(file);
		config.load();
		loadSettings(config);
		config.save();
	}

	private void loadSettings(Configuration config)
	{
		config.addCustomCategoryComment("Bonus Slot", "Define how often a bonus ingot will smelt in the Smelter.");
		config.getInt("bonus ingot", "Bonus Slot", 33, 0, 100, "Expressed in percent. [0% - 100%]");
	}
}