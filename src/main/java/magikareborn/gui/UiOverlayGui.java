package magikareborn.gui;

import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
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
        if (opusCapability.getMagicLevel() > 0) {
            GL11.glPushMatrix();

            //todo: if spell book is held - ItemStack curItem = mc.player.getHeldItem(EnumHand.MAIN_HAND);

            //todo: show spell cost on right-side end of mana bar
            //Mana bar
            int barLeft = 10;
            int barWidth = 80;
            int barHeight = 8;
            int barTop = height - barLeft - barHeight;
            int manaWidth = (int)Math.floor((opusCapability.getMana() / opusCapability.getManaMax()) * barWidth) ;
            drawRect(barLeft, barTop, barLeft + barWidth, barTop + barHeight, 0xFF000000); //todo: remove this
            drawRect(barLeft, barTop, barLeft + manaWidth, barTop + barHeight, 0xFF50A0C8);
            //todo: draw textured rectangle over the top

            GL11.glPopMatrix();
        }
    }

}
