package magikareborn.gui;

import magikareborn.base.BaseSpell;
import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.containers.SpellAltarContainer;
import magikareborn.helpers.ResourceHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.opengl.GL11;

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

        IOpusCapability opusCapability = mc.player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
        if (opusCapability == null) {
            OpusCapability.logNullWarning(mc.player.getName());
            return;
        }

        SpellAltarContainer spellAltarContainer = ((SpellAltarContainer) this.inventorySlots);
        Item craftableItem = spellAltarContainer.getCraftableItem().getItem();

        if (craftableItem instanceof BaseSpell) {

            BaseSpell craftableSpell = (BaseSpell) craftableItem;

            //todo: Show bar filled of required xp
            int availableXpLevels = mc.player.experienceLevel;
            int requiredXpLevels = craftableSpell.getCraftingXpCost();
            int xpColor = availableXpLevels >= requiredXpLevels ? 0x0000FF00 : 0x00FF0000;
            drawString(mc.fontRenderer, new TextComponentTranslation("message.title.xp").getFormattedText() + ": " + availableXpLevels + "/" + requiredXpLevels, guiLeft + 120, guiTop + 10, xpColor);

            //todo: Show bar filled of required mana
            int availableMana = (int) opusCapability.getMana() + spellAltarContainer.getAvailableMana();
            int requiredMana = (int) craftableSpell.getCraftingManaCost();
            int manaColor = availableMana >= requiredMana ? 0x0000FF00 : 0x00FF0000;
            drawString(mc.fontRenderer, new TextComponentTranslation("message.title.mana").getFormattedText() + ": " + availableMana + "/" + requiredMana, guiLeft + 120, guiTop + 20, manaColor);

//            if (availableXpLevels < requiredXpLevels) {
//                System.out.println("Not enough XP levels"); //todo: remove
//                drawOverOutputSlot();
//
//            } else if (availableMana < requiredMana) {
//                System.out.println("Not enough mana"); //todo: remove
//                drawOverOutputSlot();
//            }

            if (opusCapability.getMagicLevel() < craftableSpell.getMinMagicLevel()) {
                drawString(mc.fontRenderer, new TextComponentTranslation("message.magic_level_too_low").getFormattedText(), guiLeft + 120, guiTop + 30, 0x00FF0000);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);

        GL11.glPushMatrix();
        drawRect(100,27,120,40, 0x00FF0000);
        GL11.glPopMatrix();
    }
}
