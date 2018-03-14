package subaraki.exsartagine.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemNoodle extends ItemFood {

	public ItemNoodle(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, net.minecraft.world.World worldIn, net.minecraft.entity.EntityLivingBase entityLiving) {
		super.onItemUseFinish(stack, worldIn, entityLiving);
		return new ItemStack(Items.BOWL);
	};
}
