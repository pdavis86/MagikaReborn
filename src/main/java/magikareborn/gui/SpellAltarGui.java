package magikareborn.gui;

import magikareborn.containers.SpellAltarContainer;
import magikareborn.helpers.ResourceHelper;
import magikareborn.tileentities.SpellAltarTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class SpellAltarGui extends GuiContainer {

    public static final Rectangle RecipesArea = new Rectangle(1, 2, 16, 32);
    public static final int GUI_ID = 0;
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation _background = ResourceHelper.getGuiResourceLocation("spellaltar");

    public SpellAltarGui(SpellAltarTileEntity tileEntity, SpellAltarContainer container) {
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        drawDefaultBackground();

        mc.getTextureManager().bindTexture(_background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
