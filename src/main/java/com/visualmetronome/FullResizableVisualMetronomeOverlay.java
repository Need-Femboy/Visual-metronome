package com.visualmetronome;

import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.OverlayUtil;

public class FullResizableVisualMetronomeOverlay extends Overlay
{

    private final VisualMetronomeConfig config;
    private final VisualMetronomePlugin plugin;

    private static int TITLE_PADDING = 10;
    private static final int MINIMUM_SIZE = 16; // too small and resizing becomes impossible, requiring a reset

    @Inject
    public FullResizableVisualMetronomeOverlay(VisualMetronomeConfig config, VisualMetronomePlugin plugin)
    {
        super(plugin);
        this.config = config;
        this.plugin = plugin;
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setMinimumSize(MINIMUM_SIZE);
        setResizable(true);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Dimension preferredSize = getPreferredSize();
        if (preferredSize == null)
        {
            // if this happens, reset to default - should be rare, but eg. alt+rightclick will cause this
            preferredSize = plugin.DEFAULT_SIZE;
            setPreferredSize(preferredSize);
        }
    
        int boxWidth = preferredSize.width;
        int boxHeight = preferredSize.height;
        int titlePadding = Math.min(boxWidth, boxHeight) / 2 - 4; // Scales tick number position
    
        if (config.enableMetronome())
        {
            int centerX = 0;
            
            if (config.enableCycle2())
            {
                graphics.setColor(plugin.currentColor2);
                graphics.fillRect(0, 0, boxWidth, boxHeight);
                centerX = boxWidth;
            }
            
            graphics.setColor(plugin.currentColor);
            graphics.fillRect(centerX, 0, boxWidth, boxHeight);
            
            if (config.enableCycle3())
            {
                graphics.setColor(plugin.currentColor3);
                graphics.fillRect(centerX + boxWidth, 0, boxWidth, boxHeight);
            }
        
            if (config.showTick())
            {
                if (config.disableFontScaling())
                {
                    graphics.setColor(config.NumberColor());
                    graphics.drawString(config.tickCount() == 1 ? String.valueOf(plugin.currentColorIndex) : String.valueOf(plugin.tickCounter), centerX + titlePadding, boxHeight - titlePadding);
                
                    if (config.enableCycle2())
                    {
                        graphics.setColor(config.cycle2Color());
                        graphics.drawString(String.valueOf(plugin.tickCounter2), titlePadding, boxHeight - titlePadding);
                    }
                
                    if (config.enableCycle3())
                    {
                        graphics.setColor(config.cycle3Color());
                        graphics.drawString(String.valueOf(plugin.tickCounter3), centerX + boxWidth + titlePadding, boxHeight - titlePadding);
                    }
                }
                else
                {
                    graphics.setFont(
                            config.fontType() == FontTypes.REGULAR
                                    ? new Font(FontManager.getRunescapeFont().getName(), Font.PLAIN, Math.min(boxWidth, boxHeight))
                                    : new Font(config.fontType().toString(), Font.PLAIN, Math.min(boxWidth, boxHeight))
                    );
    
                    if (config.fontType() == FontTypes.REGULAR)
                    {
                        graphics.setFont(new Font(FontManager.getRunescapeFont().getName(), Font.PLAIN, Math.min(preferredSize.width, preferredSize.height))); //scales font size based on the size of the metronome
                    }
                    else
                    {
                        graphics.setFont(new Font(config.fontType().toString(), Font.PLAIN, Math.min(preferredSize.width, Math.min(preferredSize.width, preferredSize.height))));
                    }
                
                    OverlayUtil.renderTextLocation(
                            graphics,
                            new Point(centerX + (boxWidth / 3), boxHeight),
                            config.tickCount() == 1 ? String.valueOf(plugin.currentColorIndex) : String.valueOf(plugin.tickCounter),
                            config.NumberColor()
                    );
                
                    if (config.enableCycle2())
                    {
                        OverlayUtil.renderTextLocation(
                                graphics,
                                new Point(boxWidth / 3, boxHeight),String.valueOf(plugin.tickCounter2),
                                config.cycle2Color()
                        );
                    }
                    
                    if (config.enableCycle3())
                    {
                        OverlayUtil.renderTextLocation(
                                graphics,
                                new Point(centerX + boxWidth + (boxWidth / 3), boxHeight), String.valueOf(plugin.tickCounter3),
                                config.cycle3Color()
                        );
                    }
                }
            }
        }

        
        return preferredSize;
    }
}
