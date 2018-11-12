package magikareborn.gui;

import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class UiOverlayGui extends Gui {

    private static IOpusCapability _opusCapability;

    public UiOverlayGui(Minecraft mc) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();

        if (_opusCapability == null) {
            _opusCapability = mc.player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
        }

        if (_opusCapability.getMagicLevel() > 0) {
            GL11.glPushMatrix();

            //todo: if spell book is held - ItemStack curItem = mc.player.getHeldItem(EnumHand.MAIN_HAND);

            //todo: show spell cost on right-side end of mana bar
            //Mana bar
            int manaBarLeft = 10;
            int manaBarWidth = 80;
            int manaBarHeight = 8;
            int manaBarTop = height - manaBarLeft - manaBarHeight;
            int manaWidth = (int) Math.floor((_opusCapability.getMana() / _opusCapability.getManaMax()) * manaBarWidth);
            drawRect(manaBarLeft, manaBarTop, manaBarLeft + manaBarWidth, manaBarTop + manaBarHeight, 0xFF000000); //todo: remove this
            drawRect(manaBarLeft, manaBarTop, manaBarLeft + manaWidth, manaBarTop + manaBarHeight, 0xFF50A0C8);
            //todo: draw textured rectangle over the top

            //Magic XP bar
            int xpBarLeft = 10;
            int xpBarWidth = width - 20;
            int xpBarHeight = 2;
            int xpBarTop = 15;
            int xpWidth = (int) Math.floor((_opusCapability.getMagicXp() / _opusCapability.getMagicXpMax()) * xpBarWidth);
            drawRect(xpBarLeft, xpBarTop, xpBarLeft + xpBarWidth, xpBarTop + xpBarHeight, 0xFF000000); //todo: remove this
            drawRect(xpBarLeft, xpBarTop, xpBarLeft + xpWidth, xpBarTop + xpBarHeight, 0xFF50A0C8);
            drawCenteredString(mc.fontRenderer, Integer.toString(_opusCapability.getMagicLevel()), width / 2, 4, 0xFF50A0C8);

            GL11.glPopMatrix();
        }
    }

}
