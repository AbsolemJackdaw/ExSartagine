package subaraki.exsartagine.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import subaraki.exsartagine.gui.server.ContainerSmelter;
import subaraki.exsartagine.tileentity.TileEntitySmelter;

public class GuiSmelter extends GuiContainer {

	private static final ResourceLocation GUI_SMELTER = new ResourceLocation("exsartagine","textures/gui/smelter.png");

	private final InventoryPlayer playerInventory;
	private final TileEntitySmelter smelter;

	public GuiSmelter(EntityPlayer player, TileEntitySmelter pan) {
		super(new ContainerSmelter(player.inventory, pan));

		playerInventory = player.inventory;
		this.smelter = pan;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = I18n.format("smelter.gui");
		this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}


	private float fade = 0.2f;
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_SMELTER);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		fade +=0.05f;
		if(smelter.isCooking())
		{
			this.drawTexturedModalRect(i+56, j+53, 176, 28, 16, 16); //furnace lit

			GlStateManager.enableBlend();
			GlStateManager.color(1f, 1f, 1f, (float)(Math.cos(Math.sin(fade))));
			this.drawTexturedModalRect(i+57, j+37, 176, 0, 14, 12); //fire
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.disableBlend();

		}
		else
			this.drawTexturedModalRect(i+56, j+53, 176, 12, 16, 16); //furnace out

		float progress = smelter.getCookingProgress() / 5.52f; //progress max = 200. 200 / 36 = 5.5. 5.5*200 = 36; 36 is texture max
		this.drawTexturedModalRect(i+73, j+17, 176, 44, (int)progress, 15); //Arrow
	}
}
