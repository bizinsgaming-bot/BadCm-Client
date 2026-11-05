package com.raspberry.client.modules.visual;

import com.raspberry.client.events.EventTarget;
import com.raspberry.client.events.types.EventUpdate;
import com.raspberry.client.modules.Category;
import com.raspberry.client.modules.Module;
import com.raspberry.client.settings.types.BooleanSetting;
import com.raspberry.client.settings.types.NumberSetting;
import org.lwjgl.input.Keyboard;

/**
 * Zoom module.
 * Allows zooming in by holding a key (default: C).
 */
public class ZoomModule extends Module {

    private final NumberSetting zoomLevel;
    private final BooleanSetting smoothZoom;

    private float previousFOV = 70.0F;
    private float currentZoom = 1.0F;
    private boolean wasKeyDown = false;

    public ZoomModule() {
        super("Zoom", "Zoom in with a key", Category.VISUAL, Keyboard.KEY_C);

        zoomLevel = new NumberSetting("Zoom Level", "How much to zoom", 4.0, 1.0, 10.0, 0.5);
        addSetting(zoomLevel);

        smoothZoom = new BooleanSetting("Smooth Zoom", "Smooth zoom transition", true);
        addSetting(smoothZoom);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (event.isPre() && mc.thePlayer != null) {
            boolean isKeyDown = Keyboard.isKeyDown(getKeyBind());

            if (isKeyDown && !wasKeyDown) {
                // Save FOV when starting zoom
                previousFOV = mc.gameSettings.fovSetting;
            }

            if (isKeyDown) {
                // Apply zoom
                float targetZoom = previousFOV / ((Number) zoomLevel.getValue()).floatValue();

                if (smoothZoom.isEnabled()) {
                    // Smooth transition
                    currentZoom += (targetZoom - currentZoom) * 0.5F;
                } else {
                    currentZoom = targetZoom;
                }

                mc.gameSettings.fovSetting = currentZoom;
            } else if (wasKeyDown) {
                // Restore FOV
                if (smoothZoom.isEnabled()) {
                    mc.gameSettings.fovSetting += (previousFOV - mc.gameSettings.fovSetting) * 0.5F;
                } else {
                    mc.gameSettings.fovSetting = previousFOV;
                }
            }

            wasKeyDown = isKeyDown;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        // Restore original FOV
        if (mc.gameSettings != null) {
            mc.gameSettings.fovSetting = previousFOV;
        }
    }
}
