package com.visualmetronome;

import com.google.inject.Provides;
import net.runelite.api.events.GameTick;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;

@PluginDescriptor(
        name = "Visual Metronome",
        description = "Shows a visual cue on an overlay every game tick to help timing based activities",
        tags = {"timers", "overlays", "tick", "skilling"}
)
public class VisualMetronomePlugin extends Plugin implements KeyListener
{
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private ConfigManager configManager;

    @Inject
    private VisualMetronomeTileOverlay tileOverlay;

    @Inject
    private VisualMetronomeNumberOverlay numberOverlay;

    @Inject
    private FullResizableVisualMetronomeOverlay overlay;

    @Inject
    private VisualMetronomeConfig config;

    @Inject
    private KeyManager keyManager;

    protected int currentColorIndex = 0;
    protected int currentColorIndex2 = 0;
    protected int currentColorIndex3 = 0;
    protected int tickCounter = 0;
    protected int tickCounter2 = 0;
    protected int tickCounter3 = 0;
    protected Color currentColor = Color.WHITE;
    protected Color currentColor2 = Color.WHITE;
    protected Color currentColor3 = Color.WHITE;
    protected int resetValue = 0;
    protected Dimension DEFAULT_SIZE = new Dimension(25, 25);

    @Provides
    VisualMetronomeConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(VisualMetronomeConfig.class);
    }

    @Subscribe
    public void onGameTick(GameTick tick)
    {
        if (tickCounter % config.tickCount() == 0)
        {
            tickCounter = 0;
            if (currentColorIndex == config.colorCycle())
            {
                currentColorIndex = 0;
            }
            switch (++currentColorIndex)
            {
                case 1:
                    currentColor = config.getTickColor();
                    break;
                case 2:
                    currentColor = config.getTockColor();
                    break;
                case 3:
                    currentColor = config.getTick3Color();
                    break;
                case 4:
                    currentColor = config.getTick4Color();
                    break;
                case 5:
                    currentColor = config.getTick5Color();
                    break;
                case 6:
                    currentColor = config.getTick6Color();
                    break;
                case 7:
                    currentColor = config.getTick7Color();
                    break;
                case 8:
                    currentColor = config.getTick8Color();
                    break;
                case 9:
                    currentColor = config.getTick9Color();
                    break;
                case 10:
                    currentColor = config.getTick10Color();
            }
        }
        
        tickCounter++;
        if (tickCounter2 % config.tickCount() == 0)
        {
            if (currentColorIndex2 == config.tickCount2())
            {
                tickCounter2 = 0;
                currentColorIndex2 = 0;
            }
            switch (++currentColorIndex2)
            {
                case 1:
                    currentColor2 = config.getTickColor();
                    break;
                case 2:
                    currentColor2 = config.getTockColor();
                    break;
                case 3:
                    currentColor2 = config.getTick3Color();
                    break;
                case 4:
                    currentColor2 = config.getTick4Color();
                    break;
                case 5:
                    currentColor2 = config.getTick5Color();
                    break;
                case 6:
                    currentColor2 = config.getTick6Color();
                    break;
                case 7:
                    currentColor2 = config.getTick7Color();
                    break;
                case 8:
                    currentColor2 = config.getTick8Color();
                    break;
                case 9:
                    currentColor2 = config.getTick9Color();
                    break;
                case 10:
                    currentColor2 = config.getTick10Color();
                    break;
            }
        }
        tickCounter2++;
        if (tickCounter3 % config.tickCount() == 0){
            if (currentColorIndex3 == config.tickCount3())
            {
                tickCounter3 = 0;
                currentColorIndex3 = 0;
            }
            switch (++currentColorIndex3)
            {
                case 1:
                    currentColor3 = config.getTickColor();
                    break;
                case 2:
                    currentColor3 = config.getTockColor();
                    break;
                case 3:
                    currentColor3 = config.getTick3Color();
                    break;
                case 4:
                    currentColor3 = config.getTick4Color();
                    break;
                case 5:
                    currentColor3 = config.getTick5Color();
                    break;
                case 6:
                    currentColor3 = config.getTick6Color();
                    break;
                case 7:
                    currentColor3 = config.getTick7Color();
                    break;
                case 8:
                    currentColor3 = config.getTick8Color();
                    break;
                case 9:
                    currentColor3 = config.getTick9Color();
                    break;
                case 10:
                    currentColor3 = config.getTick10Color();
                    break;
            }
        }
        tickCounter3++;
    }
    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("visualmetronome"))
        {
            return;
        }

        if (currentColorIndex > config.colorCycle())
        {
            currentColorIndex = 0;
        }
    
        if (currentColorIndex2 > config.colorCycle())
        {
            currentColorIndex2 = 0;
        }
    
        if (currentColorIndex3 > config.colorCycle())
        {
            currentColorIndex3 = 0;
        }

        if (tickCounter > config.tickCount())
        {
            tickCounter = 0;
        }
        if (tickCounter2 > config.tickCount2())
        {
            tickCounter2 = 0;
        }
        if (tickCounter3 > config.tickCount3())
        {
            tickCounter3 = 0;
        }

        DEFAULT_SIZE = new Dimension(config.boxWidth(), config.boxWidth());
    }

    @Override
    protected void startUp() throws Exception
    {
        DEFAULT_SIZE = new Dimension(config.boxWidth(), config.boxWidth());
        overlay.setPreferredSize(DEFAULT_SIZE);
        overlayManager.add(overlay);
        overlayManager.add(tileOverlay);
        overlayManager.add(numberOverlay);
        keyManager.registerKeyListener(this);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        overlayManager.remove(tileOverlay);
        overlayManager.remove(numberOverlay);
        tickCounter = 0;
        tickCounter2 = 0;
        tickCounter3 = 0;
        currentColorIndex = 0;
        currentColorIndex2 = 0;
        currentColorIndex3 = 0;
        currentColor = config.getTickColor();
        keyManager.unregisterKeyListener(this);
    }

    //hotkey settings
    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (config.tickResetHotkey().matches(e))
        {
            // Reset Cycle 1
            if (config.tickCount() > 1)
            {
                // Prevent out of bounds by setting to 0 if reset start is above tick count
                resetValue = (config.tickResetStartTick() >= config.tickCount()) ? 0 : config.tickResetStartTick();
                tickCounter = resetValue;
                currentColorIndex = 0;
                currentColorIndex2 = 0;
                currentColorIndex3 = 0;
            }
            else
            {
                resetValue = (config.tickResetStartTick() >= config.colorCycle()) ? 0 : config.tickResetStartTick();
                tickCounter = resetValue;
                currentColorIndex = resetValue;
                currentColorIndex2 = resetValue;
                currentColorIndex3 = resetValue;
            }
            tickCounter2 = (config.tickResetStartTick() >= config.tickCount2()) ? 0 : config.tickResetStartTick();
            tickCounter3 = (config.tickResetStartTick() >= config.tickCount3()) ? 0 : config.tickResetStartTick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
    }
}
