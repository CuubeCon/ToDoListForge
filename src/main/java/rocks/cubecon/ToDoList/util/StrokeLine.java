package rocks.cubecon.ToDoList.util;

/**
 * StrokeLine Class
 *
 * @author CubeCon
 */
public class StrokeLine {
    private int currentLength;
    private int maxLength;
    private boolean shouldDraw;

    /**
     * Constructor
     *
     * @param currentLength int currentlength of the line
     * @param maxLength int max length of the line
     */
    public StrokeLine(int currentLength, int maxLength)
    {
        this.currentLength = currentLength;
        this.maxLength = maxLength;
        this.shouldDraw = false;
    }

    public int getCurrentLength()
    {
        return this.currentLength;
    }

    public void setCurrentLength(int currentLength)
    {
        this.currentLength = currentLength;
    }

    public int getMaxLength()
    {
        return maxLength;
    }

    public void setMaxLength(int maxLength)
    {
        this.maxLength = maxLength;
    }

    public boolean isShouldDraw()
    {
        return shouldDraw;
    }

    public void setShouldDraw(boolean shouldDraw)
    {
        this.shouldDraw = shouldDraw;
    }
}