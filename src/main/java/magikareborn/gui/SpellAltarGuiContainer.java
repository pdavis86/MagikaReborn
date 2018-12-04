package magikareborn.gui;

import magikareborn.containers.SpellAltarContainer;
import magikareborn.helpers.ResourceHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class SpellAltarGuiContainer extends GuiContainer {

    public static final Rectangle RecipesArea = new Rectangle(1, 2, 16, 32);
    public static final int GUI_ID = 0;

    private static final int WIDTH = 180;
    private static final int HEIGHT = 152;

    private static final ResourceLocation _background = ResourceHelper.getGuiResource("spellaltar");

    //todo: add tooltips

    public SpellAltarGuiContainer(SpellAltarContainer container) {
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        drawDefaultBackground();

        mc.getTextureManager().bindTexture(_background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        //Show bar filled of required xp
        drawString(mc.fontRenderer, "XP:", guiLeft + 120, guiTop + 10, 0x0000FF00);

        //Show bar filled of required mana by checking inventorySlots.hghghggh
        drawString(mc.fontRenderer, "Mana:", guiLeft + 120, guiTop + 20, 0x000000FF);
    }

}
