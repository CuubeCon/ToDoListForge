package rocks.cubecon.ToDoList;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;
import rocks.cubecon.ToDoList.util.CustomTextField;

/**
 * GuiScreen where you can write the Tasks
 *
 * @author CubeCon
 */
public class GuiConfig extends GuiScreen
{
	public static String headingText = "ToDoList", task1Text ="", task2Text="", task3Text="", task4Text="";

	private GuiTextField listHeading;
	private GuiTextField task1;
	private GuiTextField task2;
	private GuiTextField task3;
	private GuiTextField task4;
	private GuiButton emptyTask1, emptyTask2, emptyTask3, emptyTask4, finish,cancel;
	private int textPosX;

    /**
     * Inits the Gui and adds Buttons.
     * Called when the GUI is displayed and when the
     * window resizes.
     */
	@Override
	public void initGui()
	{
        Keyboard.enableRepeatEvents(true);
		super.initGui();
		listHeading = new CustomTextField(0, fontRenderer, width/2 - 100, height/2 - 62, 210, 20, 60);
		task1       = new CustomTextField(1, fontRenderer, width/2 - 100, height/2 - 37, 210, 20, 240);
		task2       = new CustomTextField(3, fontRenderer, width/2 - 100, height/2 - 12, 210, 20, 240);
		task3       = new CustomTextField(5, fontRenderer, width/2 - 100, height/2 + 13, 210, 20, 240);
		task4       = new CustomTextField(7, fontRenderer, width/2 - 100, height/2 + 38, 210, 20, 240);
		/* Maxlength, so task needs max two lines */
		listHeading.setMaxStringLength(12);
		task1.setMaxStringLength(48);
		task2.setMaxStringLength(48);
		task3.setMaxStringLength(48);
		task4.setMaxStringLength(48);
		/* Set previous task text */
		listHeading.setText(headingText);
		task1.setText(task1Text);
		task2.setText(task2Text);
		task3.setText(task3Text);
		task4.setText(task4Text);
		emptyTask1 = new GuiButton(2, width/2 +115, height/2 - 37, 20, 20, "X");
		emptyTask2 = new GuiButton(4, width/2 +115, height/2 - 12, 20, 20, "X");
		emptyTask3 = new GuiButton(5, width/2 +115, height/2 + 13, 20, 20, "X");
		emptyTask4 = new GuiButton(8, width/2 +115, height/2 + 38, 20, 20, "X");
		/* if tasktext is empty, disable button to clear textfield */
		if(task1Text.equals(""))
			emptyTask1.enabled = false;
		if(task2Text.equals(""))
			emptyTask2.enabled = false;
		if(task3Text.equals(""))
			emptyTask3.enabled = false;
		if(task4Text.equals(""))
			emptyTask4.enabled = false;
		cancel = new GuiButton(9, width/2 - 140, height/2 + 80 , 100,20, I18n.format("todolist.config.cancel"));
		finish = new GuiButton(10, width/2 + 35, height/2 + 80, 100, 20, I18n.format("todolist.config.finish"));
		buttonList.add(emptyTask1);
        buttonList.add(emptyTask2);
        buttonList.add(emptyTask3);
        buttonList.add(emptyTask4);
        buttonList.add(finish);
        buttonList.add(cancel);
        /* different position for german words, words are longer */
        textPosX = (mc.getLanguageManager().getCurrentLanguage().getLanguageCode().equals("de_de")?157:140);
	}

    /**
     * Draws the screen and all the components in it.
     *
     * @param mouseX mouse x position
     * @param mouseY mouse y position
     * @param partialTicks the partial ticks
     */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawString(fontRenderer, ChatFormatting.WHITE + I18n.format("todolist.config.head"), width/2 - 30, height/2 - 90, 0);
        drawString(fontRenderer, ChatFormatting.WHITE + I18n.format("todolist.config.listHeadingText"), width/2 - textPosX - 5, height/2 - 55, 0);
        listHeading.drawTextBox();
        drawString(fontRenderer, ChatFormatting.WHITE + I18n.format("todolist.config.task1"), width/2 - textPosX, height/2 - 30, 0);
		task1.drawTextBox();
        drawString(fontRenderer, ChatFormatting.WHITE + I18n.format("todolist.config.task2"), width/2 - textPosX, height/2 -  5, 0);
		task2.drawTextBox();
        drawString(fontRenderer, ChatFormatting.WHITE + I18n.format("todolist.config.task3"), width/2 - textPosX, height/2 + 20, 0);
		task3.drawTextBox();
        drawString(fontRenderer, ChatFormatting.WHITE + I18n.format("todolist.config.task4"), width/2 - textPosX, height/2 + 45, 0);
		task4.drawTextBox();
	}

    /**
     * Handles the KeyboardInput and enable/disable emptyTasks button when text is empty or not
     *
     * @param typedChar the typed char
     * @param keyCode the keycode from the typed char
     * @throws IOException java exception
     */
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		super.keyTyped(typedChar, keyCode);
		listHeading.textboxKeyTyped(typedChar, keyCode);
		task1.textboxKeyTyped(typedChar, keyCode);
		task2.textboxKeyTyped(typedChar, keyCode);
		task3.textboxKeyTyped(typedChar, keyCode);
		task4.textboxKeyTyped(typedChar, keyCode);

		if((task1.getText().isEmpty()) && emptyTask1.enabled)
			emptyTask1.enabled = false;
		else if(!task1.getText().isEmpty() && !emptyTask1.enabled)
			emptyTask1.enabled = true;

		if((task2.getText().isEmpty()) && emptyTask2.enabled)
			emptyTask2.enabled = false;
		else if(!task2.getText().isEmpty() && !emptyTask2.enabled)
			emptyTask2.enabled = true;

		if((task3.getText().isEmpty()) && emptyTask3.enabled)
			emptyTask3.enabled = false;
		else if(!task3.getText().isEmpty() && !emptyTask3.enabled)
			emptyTask3.enabled = true;

		if((task4.getText().isEmpty()) && emptyTask4.enabled)
			emptyTask4.enabled = false;
		else if(!task4.getText().isEmpty() && !emptyTask4.enabled)
			emptyTask4.enabled = true;
	}

    /**
     *  Called when the mouse is clicked
     *
     * @param mouseX mouse x postition
     * @param mouseY mouse y position
     * @param mouseButton mousebutton pressed as int
     * @throws IOException java exception
     */
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		listHeading.mouseClicked(mouseX, mouseY, mouseButton);
		task1.mouseClicked(mouseX, mouseY, mouseButton);
		task2.mouseClicked(mouseX, mouseY, mouseButton);
		task3.mouseClicked(mouseX, mouseY, mouseButton);
		task4.mouseClicked(mouseX, mouseY, mouseButton);
	}

    /**
     * Called from the main game loop to update the screen.
     */
	@Override
	public void updateScreen()
	{
        super.updateScreen();
        listHeading.updateCursorCounter();
		task1.updateCursorCounter();
		task2.updateCursorCounter();
		task3.updateCursorCounter();
		task4.updateCursorCounter();
	}

    /**
     * Method for actions when clicked a GuiButton
     *
     * @param button the clicked GuiButton
     * @throws IOException java exception
     */
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);
        /* save texts and close Gui */
        if (button == finish)
        {
            headingText = listHeading.getText();
            task1Text   = task1.getText();
            task2Text   = task2.getText();
            task3Text   = task3.getText();
            task4Text   = task4.getText();
            closeGui();
        }
        else if (button == cancel) /* close Gui without saving possible changes */
        {
        	closeGui();
        }
        else if (button == emptyTask1)
        {
            task1.setText("");
            emptyTask1.enabled = false;
        }
        else if (button == emptyTask2)
        {
            task2.setText("");
            emptyTask2.enabled = false;
        }
        else if (button == emptyTask3)
        {
            task3.setText("");
            emptyTask3.enabled = false;
        }
        else if (button == emptyTask4)
        {
            task4.setText("");
            emptyTask4.enabled = false;
        }
    }

    /**
     *  Close the GUI
     */
    private void closeGui()
    {
        this.mc.displayGuiScreen(null);

        if (this.mc.currentScreen == null)
        {
            this.mc.setIngameFocus();
        }
    }
}