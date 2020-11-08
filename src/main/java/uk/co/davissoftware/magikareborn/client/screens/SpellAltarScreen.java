package uk.co.davissoftware.magikareborn.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.containers.SpellAltarContainer;
import uk.co.davissoftware.magikareborn.common.helpers.ResourceHelper;

@OnlyIn(Dist.CLIENT)
public class SpellAltarScreen extends ContainerScreen<SpellAltarContainer> {

    private static final ResourceLocation _background = ResourceHelper.getGuiResource("spellaltar");

    public SpellAltarScreen(SpellAltarContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);

//        this.guiLeft = 0;
//        this.guiTop = 0;
        this.xSize = 178;
        this.ySize = 151;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void func_230450_a_(MatrixStack p_230450_1_, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        drawGuiContainerBackgroundLayer(p_230450_1_, p_230450_2_, p_230450_3_, p_230450_4_);
    }

    private int getWidth() {
        return field_230708_k_;
    }

    private int getHeight(){
        return field_230709_l_;
    }

    @SuppressWarnings({"deprecation", "ConstantConditions", "unused"})
    private void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.getMinecraft().getTextureManager().bindTexture(_background);
        int i = (this.getWidth() - this.xSize) / 2;
        int j = (this.getHeight() - this.ySize) / 2;
        this.func_238474_b_(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

//        IOpusCapability opusCapability = mc.player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
//        if (opusCapability == null) {
//            OpusCapability.logNullWarning(mc.player.getName());
//            return;
//        }
//
//        SpellAltarContainer spellAltarContainer = ((SpellAltarContainer) this.inventorySlots);
//        Item craftableItem = spellAltarContainer.getCraftableItem().getItem();
//
//        if (craftableItem instanceof BaseSpell) {
//
//            BaseSpell craftableSpell = (BaseSpell) craftableItem;
//
//            //todo: Show bar filled of required xp
//            int availableXpLevels = mc.player.experienceLevel;
//            int requiredXpLevels = craftableSpell.getCraftingXpCost();
//            int xpColor = availableXpLevels >= requiredXpLevels ? 0x0000FF00 : 0x00FF0000;
//            drawString(mc.fontRenderer, new TextComponentTranslation("message.title.xp").getFormattedText() + ": " + availableXpLevels + "/" + requiredXpLevels, guiLeft + 120, guiTop + 10, xpColor);
//
//            //todo: Show bar filled of required mana
//            int availableMana = (int) opusCapability.getMana() + spellAltarContainer.getAvailableMana();
//            int requiredMana = (int) craftableSpell.getCraftingManaCost();
//            int manaColor = availableMana >= requiredMana ? 0x0000FF00 : 0x00FF0000;
//            drawString(mc.fontRenderer, new TextComponentTranslation("message.title.mana").getFormattedText() + ": " + availableMana + "/" + requiredMana, guiLeft + 120, guiTop + 20, manaColor);
//
////            if (availableXpLevels < requiredXpLevels) {
////                System.out.println("Not enough XP levels"); //todo: remove
////                drawOverOutputSlot();
////
////            } else if (availableMana < requiredMana) {
////                System.out.println("Not enough mana"); //todo: remove
////                drawOverOutputSlot();
////            }
//
//            if (opusCapability.getMagicLevel() < craftableSpell.getMinMagicLevel()) {
//                drawString(mc.fontRenderer, new TextComponentTranslation("message.magic_level_too_low").getFormattedText(), guiLeft + 120, guiTop + 30, 0x00FF0000);
//            }
//        }
    }
}
