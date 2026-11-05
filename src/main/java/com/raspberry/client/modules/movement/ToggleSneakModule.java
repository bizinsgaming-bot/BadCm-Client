
package com.raspberry.client.modules.movement;

import com.raspberry.client.events.EventTarget;
import com.raspberry.client.events.types.EventUpdate;
import com.raspberry.client.modules.Category;
import com.raspberry.client.modules.Module;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.settings.KeyBinding;

public class ToggleSneakModule extends Module {

    private boolean sneaking = false;
    private boolean wasKeyDown = false;

    public ToggleSneakModule() {
        super("Toggle Sneak", "Toggle crouch instead of hold", Category.MOVEMENT, 0);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (event.isPre() && mc.thePlayer != null) {

            int keyCode = mc.gameSettings.keyBindSneak.getKeyCode();

            boolean isKeyDown = Keyboard.isKeyDown(keyCode);

            if (isKeyDown && !wasKeyDown) {
                sneaking = !sneaking;
            }

            wasKeyDown = isKeyDown;

            KeyBinding.setKeyBindState(keyCode, sneaking);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        sneaking = false;
        wasKeyDown = false;

        if (mc.gameSettings != null) {
            KeyBinding.setKeyBindState(
                mc.gameSettings.keyBindSneak.getKeyCode(),
                false
            );
        }
    }
}
