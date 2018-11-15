package magikareborn.base;

import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import magikareborn.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public abstract class BaseSpell extends BaseItem {

    public BaseSpell(String name) {
        super(name, ModItems.MAGIKA_REBORN_CREATIVE_TAB);
    }

    public abstract int getMinMagicLevel();

    public abstract float getManaCost();

    public boolean canCast(EntityPlayer playerIn) {
        boolean okToCast = false;

        IOpusCapability opusCapability = playerIn.getCapability(OpusCapabilityStorage.CAPABILITY, null);
        World world = playerIn.getEntityWorld();

        if (!opusCapability.isSpellUnlocked()) {
            //todo: localise
            if (!world.isRemote)
                playerIn.sendMessage(new TextComponentString("You do not know how to cast this spell"));

        } else if (opusCapability.getMagicLevel() < getMinMagicLevel()) {
            //todo: localise
            if (!world.isRemote)
                playerIn.sendMessage(new TextComponentString("You lack the experience to cast this spell"));

        } else if (opusCapability.getMana() < getManaCost()) {
            //todo: flash mana bar instead
            if (!world.isRemote)
                playerIn.sendMessage(new TextComponentString("You do not have enough mana to cast this spell"));

        } else {
            okToCast = true;
        }

        if (okToCast && !world.isRemote) {
            opusCapability.subtractMana(getManaCost());
        }

        return okToCast;
    }
}
