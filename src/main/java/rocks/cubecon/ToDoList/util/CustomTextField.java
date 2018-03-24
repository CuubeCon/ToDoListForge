package rocks.cubecon.ToDoList.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import rocks.cubecon.ToDoList.ToDoList;

/**
 * CustomTextField needed because of the custom font to avoid text overflow
 *
 * @author CubeCon
 * @since 1.0.2
 */
public class CustomTextField extends GuiTextField
{
    private int maxLength;

    /**
     * Constructor same as normal, added maxLength
     *
     * @param componentId the id
     * @param fontrendererObj the fontrenderer
     * @param x x position
     * @param y y position
     * @param par5Width width
     * @param par6Height height
     * @param maxLength max length
     */
    public CustomTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height, int maxLength)
    {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
        this.maxLength = maxLength;
    }

    /**
     * Here we check if the text would overflow with custom font.
     * if so, remove the last char and return false
     *
     * @param typedChar the char
     * @param keyCode the keycode
     * @return true if the key is written
     */
    @Override
    public boolean textboxKeyTyped(char typedChar, int keyCode)
    {
        boolean tmp = super.textboxKeyTyped(typedChar, keyCode);
        if(tmp)
        {
            if (maxLength < ToDoList.customFontRenderer.getStringWidth(this.getText()))
                setText(getText().substring(0, getText().length()-1));
            else
                return true;
        }
        return false;
    }
}
