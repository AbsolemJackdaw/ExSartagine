package subaraki.exsartagine.gui.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import subaraki.exsartagine.gui.server.ContainerRange;
import subaraki.exsartagine.tileentity.TileEntityRange;

public class GuiRange extends GuiContainer {

	private static final ResourceLocation GUI_PAN = new ResourceLocation("exsartagine","textures/gui/range.png");

	private final InventoryPlayer playerInventory;
	private final TileEntityRange range;
	
	public GuiRange(EntityPlayer player, TileEntityRange range) {
		super(new ContainerRange(player.inventory, range));
		this.range = range;
		this.playerInventory = player.inventory;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String guiName = I18n.format("range.gui");
		this.fontRenderer.drawString(guiName, this.xSize / 2 - this.fontRenderer.getStringWidth(guiName) / 2, 5, 4210752);
		this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
	}
	
	private float fade = 0.2f;
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_PAN);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		fade +=0.05f;
		
		if(range.isFueled())
		{
			GlStateManager.enableBlend();
			GlStateManager.color(1f, 1f, 1f, (float)(Math.cos(Math.sin(fade))));
			this.drawTexturedModalRect(i+96, j+21, 176, 28, 77, 13); //fire
			GlStateManager.color(1, 1, 1, 1);
			GlStateManager.disableBlend();
			
			float fuel = (float)range.getFuelTimer();
			float scale = 28f / (float)range.getMaxFuelTimer(); //28 = texture size
			
			float convert = fuel * scale;
			
			this.drawTexturedModalRect(i+114, j+39+28 - (int) convert , 176, 28-(int)convert, 28, (int)convert);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
