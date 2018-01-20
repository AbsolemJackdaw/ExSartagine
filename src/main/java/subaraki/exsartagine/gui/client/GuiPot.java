package subaraki.exsartagine.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import subaraki.exsartagine.gui.server.ContainerPot;
import subaraki.exsartagine.tileentity.TileEntityPot;

public class GuiPot extends GuiContainer {

	private static final ResourceLocation GUI_POT = new ResourceLocation("exsartagine","textures/gui/pot.png");

	private final InventoryPlayer playerInventory;
	private final TileEntityPot pot;

	public GuiPot(EntityPlayer player, TileEntityPot pot) {
		super(new ContainerPot(player.inventory, pot));

		playerInventory = player.inventory;
		this.pot = pot;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = I18n.format("pot.gui");
		this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}


	private float fade = 0.2f;
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_POT);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		fade +=0.05f;
		if(pot.isCooking())
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

		float progress = pot.getCookingProgress() / 3.55f; //progress max = 125. 125 / 33 = 3.75 3.75*125 = 33; 33 is texture max
		this.drawTexturedModalRect(i+76, j+34, 176, 44, (int)progress, 18); //Arrow

		float waterProgress = (float)pot.getWaterLevel() / 3.5f;
		this.drawTexturedModalRect(i+14, j+15+(54 - (int)waterProgress), 176, 62 + (54 - (int)waterProgress), 5, 54);
	}
}
