package magikareborn.gui;

import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class UiOverlayGui extends Gui {

    public UiOverlayGui(Minecraft mc) {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();

        IOpusCapability opusCapability = mc.player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
        if (opusCapability == null) {
            OpusCapability.logNullWarning(mc.player.getName());
            return;
        }

        if (opusCapability.getMagicLevel() > 0) {
            GL11.glPushMatrix();

            //todo: if spell book is held - ItemStack curItem = mc.player.getHeldItem(EnumHand.MAIN_HAND);

            //todo: show spell cost on right-side end of mana bar
            //Mana bar
            int manaBarLeft = 10;
            int manaBarWidth = 80;
            int manaBarHeight = 8;
            int manaBarTop = height - manaBarLeft - manaBarHeight;
            int manaWidth = (int) Math.floor((opusCapability.getMana() / opusCapability.getManaMax()) * manaBarWidth);
            drawRect(manaBarLeft, manaBarTop, manaBarLeft + manaBarWidth, manaBarTop + manaBarHeight, 0xFF000000); //todo: remove this
            drawRect(manaBarLeft, manaBarTop, manaBarLeft + manaWidth, manaBarTop + manaBarHeight, 0xFF50A0C8);
            //todo: draw textured rectangle over the top

            //Magic XP bar
            int xpBarLeft = 10;
            int xpBarWidth = width - 20;
            int xpBarHeight = 2;
            int xpBarTop = 15;
            int xpWidth = (int) Math.floor((opusCapability.getXp() / opusCapability.getXpMax()) * xpBarWidth);
            drawRect(xpBarLeft, xpBarTop, xpBarLeft + xpBarWidth, xpBarTop + xpBarHeight, 0xFF000000); //todo: remove this
            drawRect(xpBarLeft, xpBarTop, xpBarLeft + xpWidth, xpBarTop + xpBarHeight, 0xFF50A0C8);
            drawCenteredString(mc.fontRenderer, Integer.toString(opusCapability.getMagicLevel()), width / 2, 4, 0xFF50A0C8);

            GL11.glPopMatrix();
        }
    }

}
