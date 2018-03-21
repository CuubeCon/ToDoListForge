package rocks.cubecon.ToDoList;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.lwjgl.input.Keyboard;

/**
 * The Main Class of the Mod where all the KeyBindings and Events are registered
 *
 * @author CubeCon
 */
@Mod(modid = ToDoList.MODID, name = ToDoList.NAME, version = ToDoList.VERSION, clientSideOnly = true)
public class ToDoList
{
	
	public static final String MODID       = "todolist";
	public static final String NAME        = "ToDoList";
	public static final String VERSION     = "1.0";

	public static KeyBinding showHideKey   =  new KeyBinding(I18n.format("todolist.keybinding.showHide"), Keyboard.KEY_P, "ToDoList");
	public static KeyBinding configKey     =  new KeyBinding(I18n.format("todolist.keybinding.config"), Keyboard.KEY_U, "ToDoList");
	public static KeyBinding checkTask1Key =  new KeyBinding(I18n.format("todolist.keybinding.checkTask1"), Keyboard.KEY_NUMPAD1, "ToDoList");
	public static KeyBinding checkTask2Key =  new KeyBinding(I18n.format("todolist.keybinding.checkTask2"), Keyboard.KEY_NUMPAD2, "ToDoList");
	public static KeyBinding checkTask3Key =  new KeyBinding(I18n.format("todolist.keybinding.checkTask3"), Keyboard.KEY_NUMPAD3, "ToDoList");
	public static KeyBinding checkTask4Key =  new KeyBinding(I18n.format("todolist.keybinding.checkTask4"), Keyboard.KEY_NUMPAD4, "ToDoList");


	/**
     * This method will be executed while loading and do register stuff
     *
	 * @param event FML Event
	 */
	@EventHandler
	public void init(FMLInitializationEvent event) 
	{
		ClientRegistry.registerKeyBinding(showHideKey);
		ClientRegistry.registerKeyBinding(configKey);
		ClientRegistry.registerKeyBinding(checkTask1Key);
		ClientRegistry.registerKeyBinding(checkTask2Key);
		ClientRegistry.registerKeyBinding(checkTask3Key);
		ClientRegistry.registerKeyBinding(checkTask4Key);
		MinecraftForge.EVENT_BUS.register(new GuiToDoListOverlay());
	}
}
