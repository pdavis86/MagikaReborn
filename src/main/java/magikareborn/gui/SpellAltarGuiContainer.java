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

    //private SpellAltarContainer _container;

    private static final ResourceLocation _background = ResourceHelper.getGuiResource("spellaltar");

    public SpellAltarGuiContainer(SpellAltarContainer container) {
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
        //_container = container;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        drawDefaultBackground();

        mc.getTextureManager().bindTexture(_background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int totalMana = ((SpellAltarContainer)this.inventorySlots).getAvailableMana();

        //Show bar filled of required xp
        drawString(mc.fontRenderer, "XP:", guiLeft + 120, guiTop + 10, 0x0000FF00);

        //Show bar filled of required mana
        int requiredMana = 9000;
        drawString(mc.fontRenderer, "Mana: " + totalMana + "/" + requiredMana, guiLeft + 120, guiTop + 20, 0x000000FF);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
