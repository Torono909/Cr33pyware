package dev.fxcte.creepyware.features.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.fxcte.creepyware.CreepyWare;
import dev.fxcte.creepyware.event.events.ClientEvent;
import dev.fxcte.creepyware.features.command.Command;
import dev.fxcte.creepyware.features.gui.CreepyWareGui;
import dev.fxcte.creepyware.features.modules.Module;
import dev.fxcte.creepyware.features.setting.Setting;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClickGui
        extends Module {
    private static ClickGui INSTANCE = new ClickGui();
    public Setting<String> prefix = this.register(new Setting<String>("Prefix", "."));
    public Setting<Boolean> customFov = this.register(new Setting<Boolean>("CustomFov", false));
    public Setting<Float> fov = this.register(new Setting<Float>("Fov", Float.valueOf(150.0f), Float.valueOf(-180.0f), Float.valueOf(180.0f)));
    public Setting<Integer> red = this.register(new Setting<Integer>("Red", 170, 0, 255));
    public Setting<Integer> green = this.register(new Setting<Integer>("Green", 0, 0, 255));
    public Setting<Integer> blue = this.register(new Setting<Integer>("Blue", 255, 0, 255));
    public Setting<Integer> hoverAlpha = this.register(new Setting<Integer>("Alpha", 180, 0, 255));
    public Setting<Integer> topRed = this.register(new Setting<Integer>("SecondRed", 80, 0, 255));
    public Setting<Integer> topGreen = this.register(new Setting<Integer>("SecondGreen", 0, 0, 255));
    public Setting<Integer> topBlue = this.register(new Setting<Integer>("SecondBlue", 185, 0, 255));
    public Setting<Integer> alpha = this.register(new Setting<Integer>("HoverAlpha", 240, 0, 255));
    public Setting<Integer> fontcolorr = this.register(new Setting<Integer>("FontRed", 210, 0, 255));
    public Setting<Integer> fontcolorg = this.register(new Setting<Integer>("FontGreen", 210, 0, 255));
    public Setting<Integer> fontcolorb = this.register(new Setting<Integer>("FontBlue", 210, 0, 255));
    public Setting<Boolean> rainbow = this.register(new Setting<Boolean>("Rainbow", false));
    public Setting<Boolean> outline = this.register(new Setting<Boolean>("Outline", false));
    public Setting<Boolean> colorSync  = this.register(new Setting<Boolean>("Sync", false));
    public Setting<rainbowMode> rainbowModeHud = this.register(new Setting<Object>("HRainbowMode", rainbowMode.Static, v -> this.rainbow.getValue() && this.colorSync.getValue()));
    public Setting<rainbowModeArray> rainbowModeA = this.register(new Setting<Object>("ARainbowMode", rainbowModeArray.Static, v -> this.rainbow.getValue() && this.colorSync.getValue()));
    public Setting<Integer> rainbowHue = this.register(new Setting<Object>("Delay", Integer.valueOf(240), Integer.valueOf(0), Integer.valueOf(600), v -> this.rainbow.getValue()));
    public Setting<Float> rainbowBrightness = this.register(new Setting<Object>("Brightness ", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> this.rainbow.getValue()));
    public Setting<Float> rainbowSaturation = this.register(new Setting<Object>("Saturation", Float.valueOf(150.0f), Float.valueOf(1.0f), Float.valueOf(255.0f), v -> this.rainbow.getValue()));
    private CreepyWareGui click;

    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Module.Category.CLIENT, true, false, false);
        this.setInstance();
    }

    public static ClickGui getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ClickGui();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onUpdate() {
        if (this.customFov.getValue().booleanValue()) {
            ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, this.fov.getValue().floatValue());
        }
    }

    @SubscribeEvent
    public void onSettingChange(ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                CreepyWare.commandManager.setPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to " + ChatFormatting.DARK_GRAY + CreepyWare.commandManager.getPrefix());
            }
            CreepyWare.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(CreepyWareGui.getClickGui());
    }

    @Override
    public void onLoad() {
        CreepyWare.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        CreepyWare.commandManager.setPrefix(this.prefix.getValue());
    }

    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof CreepyWareGui)) {
            this.disable();
        }
    }

    public enum rainbowModeArray {
        Static,
        Up

    }

    public enum rainbowMode {
        Static,
        Sideway

    }
}
