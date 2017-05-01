package subaraki.exsartagine.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import subaraki.exsartagine.tileentity.TileEntityPan;

public class GuiPan extends GuiContainer {

	private static final ResourceLocation GUI_PAN = new ResourceLocation("exsartagine","textures/gui/pan.png");

	private final InventoryPlayer playerInventory;
	private final TileEntityPan pan;

	public GuiPan(EntityPlayer player, TileEntityPan pan) {
		super(player.inventoryContainer);

		playerInventory = player.inventory;
		this.pan = pan;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_PAN);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		float progress = pan.getCookingProgress() / 5.6f; //progress max = 125. 125 / 22 = 5.6. 5.6*125 = 22; 22 is texture max
		if(pan.isCooking())
		{
			this.drawTexturedModalRect(i+56, j+53, 176, 28, 16, 16); //furnace lit

			GlStateManager.enableBlend();
			GlStateManager.color(1f, 1f, 1f, (float)Math.cos((progress)));
			this.drawTexturedModalRect(i+57, j+38, 176, 0, 14, 12); //fire
			GlStateManager.disableBlend();

		}
		else
			this.drawTexturedModalRect(i+56, j+53, 176, 12, 16, 16); //furnace out

		this.drawTexturedModalRect(i+80, j+34, 176, 44, (int)progress, 15); //Arrow
	}
}
