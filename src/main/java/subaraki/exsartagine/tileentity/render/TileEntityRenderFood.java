package subaraki.exsartagine.tileentity.render;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import subaraki.exsartagine.tileentity.TileEntityCooker;
import subaraki.exsartagine.tileentity.TileEntitySmelter;

public class TileEntityRenderFood extends TileEntitySpecialRenderer {

	private final RenderItem itemRenderer;
	private final Random random = new Random();

	private EntityItem ei;

	public TileEntityRenderFood() {
		itemRenderer = Minecraft.getMinecraft().getRenderItem();
	}

	@Override
	public void render(TileEntity tileentity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(tileentity, x, y, z, partialTicks, destroyStage, alpha);

		if(ei == null)
		{
			ei = new EntityItem(getWorld(), 0, 0, 0);
			ei.setInfinitePickupDelay();
			ei.setNoDespawn();
		}
		
		TileEntityCooker te = null;
		if (tileentity == null || tileentity instanceof TileEntitySmelter)
			return;

		if (tileentity instanceof TileEntityCooker)
		{
			te = (TileEntityCooker)tileentity;
		}

		if(te == null)
			return;

		ItemStack entryToRender = te.getEntry();
		ItemStack resultToRender  = te.getResult();

		GlStateManager.pushMatrix();

		GlStateManager.translate((float) x - 0F, (float) y - 0.6F, (float) z - 0F);

		if(!entryToRender.isEmpty())
		{
			if(ei.getItem() != entryToRender)
				ei.setItem(entryToRender);
			Minecraft.getMinecraft().getRenderManager().renderEntity(ei, 0.5, 0.4, 0.5, 0F, 0, false);
		}
		else if (!resultToRender.isEmpty())
		{
			if(ei.getItem() != resultToRender)
				ei.setItem(resultToRender);

			Minecraft.getMinecraft().getRenderManager().renderEntity(ei, 0.5, 0.4, 0.5, 0F, 0, false);	
		}

		GlStateManager.popMatrix();
	}
}
