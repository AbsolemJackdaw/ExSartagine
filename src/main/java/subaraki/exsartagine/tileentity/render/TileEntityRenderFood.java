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
			ei.hoverStart = 0f;
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

		ItemStack entryToRender = te.getEntry().copy();
		ItemStack resultToRender  = te.getResult().copy();

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y, (float) z ); //translate to correct location

		GlStateManager.translate(0.1, -0.65, 0); //translate to center of the pan

		GlStateManager.translate(0.5, 0.75, 0.5); //set normal and rotate so it rotates in center, 
		GlStateManager.rotate(90, 0, 0, 1);		  //and is rendered flat down on the pan
		GlStateManager.translate(-0.5, -0.75, -0.5);


		if(!entryToRender.isEmpty())
		{
			entryToRender.setCount(1);
			ei.setItem(entryToRender);
			Minecraft.getMinecraft().getRenderManager().renderEntity(ei, 0.5, 0.4, 0.5, 0F, 0, false);
		} 
		else if (!resultToRender.isEmpty())
		{
			resultToRender.setCount(1);
			ei.setItem(resultToRender);
			Minecraft.getMinecraft().getRenderManager().renderEntity(ei, 0.5, 0.4, 0.5, 0F, 0, false);	
		}
		GlStateManager.popMatrix();
	}
}
