package rocks.cubecon.ToDoList;

import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rocks.cubecon.ToDoList.util.StrokeLine;

/**
 * The Class where all the magic happens.
 * It's the heart of the mod, so handle with care :-)
 *
 * @author CubeCon
 */
public class GuiToDoListOverlay extends Gui
{
	private static ResourceLocation backgroundLocation = new ResourceLocation(ToDoList.MODID, "bg.png");
	private static ResourceLocation ascii = new ResourceLocation(ToDoList.MODID, "ascii.png");
	private FontRenderer fr;
	private boolean fadeOut = false;
	private StrokeLine strokeLineTask1 = new StrokeLine(0,0);
    private StrokeLine strokeLineTask2 = new StrokeLine(0,0);
    private StrokeLine strokeLineTask3 = new StrokeLine(0,0);
    private StrokeLine strokeLineTask4 = new StrokeLine(0,0);
	private int imageWidth = 256;
	private int currentImageWidth = -imageWidth;
	private Minecraft mc = Minecraft.getMinecraft();
	private boolean showGui = false;
	private ScaledResolution sr;

    /**
     * Renders the Gui, when showGui = true
     * Called every tick
     *
     * @param event RenderGameOverlayEvent
     */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event)
	{
		if(showGui && event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
		{
		    /* Update Textlength in StrokeLines, in case that Text change when gui is open */
		    updateTextLength();
			mc.getTextureManager().bindTexture(backgroundLocation);
			this.zLevel = 0.0f;
			/* Fade in/out Animation */
			if (!fadeOut && currentImageWidth < 0)
				currentImageWidth = currentImageWidth + 5;
			else if (fadeOut && currentImageWidth > -imageWidth)
				currentImageWidth = currentImageWidth - 5;
			/* Draw background image */
			GlStateManager.scale(0.5, 0.5, 0.5);
			drawTexturedModalRect(currentImageWidth, sr.getScaledHeight() - 74, 0, 0, imageWidth, 254);
			GlStateManager.scale(5, 5, 5);
			this.zLevel = 200.0f;
			/* Draw heading and task texts */
			String tmp;
            tmp = I18n.format("todolist.heading");
            fr.drawString(TextFormatting.ITALIC + tmp, currentImageWidth, (sr.getScaledHeight() - 60) / 5, 3);
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
			GlStateManager.scale(1, 1, 1);
			fr.drawSplitString(GuiConfig.task1Text, currentImageWidth + 5, (sr.getScaledHeight() / 2) - 8,120, 0);
			fr.drawSplitString(GuiConfig.task2Text, currentImageWidth + 5, (sr.getScaledHeight() / 2) + 18,120, 0);
			fr.drawSplitString(GuiConfig.task3Text, currentImageWidth + 5, (sr.getScaledHeight() / 2) + 44, 120, 0);
			fr.drawSplitString(GuiConfig.task4Text, currentImageWidth + 5, (sr.getScaledHeight() / 2) + 70, 120, 0);
			this.zLevel = 300.0f;
			mc.getTextureManager().bindTexture(backgroundLocation);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			/* Draw StrokeLines when needed */
			handleStrokeLine(strokeLineTask1, (sr.getScaledHeight() / 2) - 5, event.getPartialTicks());
            handleStrokeLine(strokeLineTask2, (sr.getScaledHeight() / 2) + 21,event.getPartialTicks());
            handleStrokeLine(strokeLineTask3, (sr.getScaledHeight() / 2) + 47,event.getPartialTicks());
            handleStrokeLine(strokeLineTask4, (sr.getScaledHeight() / 2) + 73,event.getPartialTicks());

			this.zLevel = 0.0f;
			/* Gui is completed out of the screen */
			if (fadeOut && currentImageWidth <= -imageWidth) {
				fadeOut = false;
				showGui = false;
			}
		}
	}

    /**
     * Handle KeyInput.
     * Show/Hide ToDoList and StrokeLines
     *
     * @param event InputEvent
     */
	@SubscribeEvent
	public void onKey(InputEvent event)
	{
		if(ToDoList.showHideKey.isPressed())
		{
			if(!showGui)
			    initGui();
			else
				fadeOut = true;
		}

		if(ToDoList.configKey.isPressed())
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiConfig());
		}

        checkKeyBindingPress(ToDoList.checkTask1Key.isPressed(), strokeLineTask1);
        checkKeyBindingPress(ToDoList.checkTask2Key.isPressed(), strokeLineTask2);
        checkKeyBindingPress(ToDoList.checkTask3Key.isPressed(), strokeLineTask3);
        checkKeyBindingPress(ToDoList.checkTask4Key.isPressed(), strokeLineTask4);
	}

    /**
     * Init the Gui and sets variables.
     * Call when Show Key was pressed
     */
	private void initGui()
	{
        currentImageWidth = -242;
	    sr = new ScaledResolution(mc);
	    /* Used for custom font */
		fr = new FontRenderer(Minecraft.getMinecraft().gameSettings, ascii, mc.renderEngine, false);
		IReloadableResourceManager rrm = (IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager();
		rrm.registerReloadListener(fr);
        updateTextLength();
		showGui = true;
	}

    /**
     * Check if KeyBinding for a StrokeLine is pressed and switch the boolean shouldShow
     *
     * @param isPressed booleal keybinding.isPressed()
     * @param strokeLine one of the four StrokeLines
     */
	private void checkKeyBindingPress(boolean isPressed, StrokeLine strokeLine)
    {
        if (isPressed)
        {
            if (strokeLine.isShouldDraw())
                strokeLine.setShouldDraw(false);
            else if (!strokeLine.isShouldDraw() && strokeLine.getMaxLength() > 1)
                strokeLine.setShouldDraw(true);
        }
    }

    /**
     * Method draw appearend, constant or disappearend StrokeLine
     * Draws two StrokeLines if Text is on two lines ( Length > 120)
     *
     * @param strokeLine one of the four StrokeLines
     * @param posY int for the y-coordinate of the StrokeLine
     */
	private void handleStrokeLine(StrokeLine strokeLine, int posY, float partialTicks)
    {
        /* StrokeLine appeare */
		if (strokeLine.isShouldDraw() && strokeLine.getCurrentLength() <= strokeLine.getMaxLength())
		{
			strokeLine.setCurrentLength(strokeLine.getCurrentLength() + (((strokeLine.getMaxLength() / 100 < 1) ? 1 : strokeLine.getMaxLength() / 100) * (int) partialTicks + 1)); //
			if(strokeLine.getMaxLength() > 120 && strokeLine.getCurrentLength() >= 120)
			{
				drawTexturedModalRect(currentImageWidth + 4, posY, 0, 254, 120, 2);
				drawTexturedModalRect(currentImageWidth + 4, posY + fr.FONT_HEIGHT, 0, 254, strokeLine.getCurrentLength()-120, 2);
			}
			else
			{

				drawTexturedModalRect(currentImageWidth + 4, posY, 0, 254, strokeLine.getCurrentLength(), 2);
			}
		}
		else if (strokeLine.isShouldDraw()) /* Constant StrokeLine */
		{
			if(strokeLine.getMaxLength() > 120)
			{
				drawTexturedModalRect(currentImageWidth + 4, posY, 0, 254, 120, 2);
				drawTexturedModalRect(currentImageWidth + 4, posY + fr.FONT_HEIGHT, 0, 254, strokeLine.getCurrentLength()-120, 2);
			}
			else
			{
				drawTexturedModalRect(currentImageWidth + 4, posY, 0, 254, strokeLine.getCurrentLength(), 2);
			}
		}
		else if (!strokeLine.isShouldDraw() && strokeLine.getCurrentLength() > 0) /* StrokeLine disappear */
		{
			strokeLine.setCurrentLength(strokeLine.getCurrentLength() - (((strokeLine.getMaxLength() / 100 < 1) ? 1 : strokeLine.getMaxLength() / 100)* (int) partialTicks + 1)); // * partialTicks
			if(strokeLine.getMaxLength() > 120 && strokeLine.getCurrentLength() >= 120)
			{
				drawTexturedModalRect(currentImageWidth + 4, posY, 0, 254, 120, 2);
				drawTexturedModalRect(currentImageWidth + 4, posY + fr.FONT_HEIGHT, 0, 254, strokeLine.getCurrentLength()-120, 2);
			}
			else
			{

				drawTexturedModalRect(currentImageWidth + 4, posY, 0, 254, strokeLine.getCurrentLength(), 2);
			}
		}
    }

    /**
     * Update the Tasks Textlength, so the StrokeLine knows his length
     */
    private void updateTextLength()
    {
        strokeLineTask1.setMaxLength(fr.getStringWidth(GuiConfig.task1Text) + 1);
        strokeLineTask2.setMaxLength(fr.getStringWidth(GuiConfig.task2Text) + 1);
        strokeLineTask3.setMaxLength(fr.getStringWidth(GuiConfig.task3Text) + 1);
        strokeLineTask4.setMaxLength(fr.getStringWidth(GuiConfig.task4Text) + 1);
    }
}
