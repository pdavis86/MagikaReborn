package magikareborn.base;

import magikareborn.ModRoot;
import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.helpers.SoundHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public abstract class BaseSpell extends BaseItem {

    public BaseSpell(String name) {
        super(name, ModRoot.MAGIKA_REBORN_CREATIVE_TAB);
        setMaxStackSize(1);
    }

    public abstract int getMinMagicLevel();

    public abstract int getCraftingXpCost();

    public abstract float getCraftingManaCost();

    public abstract float getCastingManaCost();

    protected boolean canCast(EntityPlayer playerIn) {

        if (playerIn.capabilities.isCreativeMode) {
            return true;
        }

        boolean okToCast = false;

        IOpusCapability opusCapability = playerIn.getCapability(OpusCapabilityStorage.CAPABILITY, null);
        if (opusCapability == null) {
            OpusCapability.logNullWarning(playerIn.getName());
            return false;
        }

        World world = playerIn.getEntityWorld();

        if (!opusCapability.isSpellUnlocked()) {
            if (!world.isRemote) {
                //todo: replace sound event
                SoundHelper.playSoundForPlayer(playerIn, SoundEvents.BLOCK_GLASS_STEP, 1f, 1f);
                playerIn.sendMessage(new TextComponentTranslation("message.spell_not_unlocked"));
            }

        } else if (opusCapability.getMagicLevel() < getMinMagicLevel()) {
            if (!world.isRemote) {
                //todo: replace sound event
                SoundHelper.playSoundForPlayer(playerIn, SoundEvents.BLOCK_GLASS_STEP, 1f, 1f);
                playerIn.sendMessage(new TextComponentTranslation("message.magic_level_too_low"));
            }

        } else if (opusCapability.getMana() < getCastingManaCost()) {
            if (!world.isRemote) {
                //todo: replace sound event
                SoundHelper.playSoundForPlayer(playerIn, SoundEvents.BLOCK_GLASS_STEP, 1f, 1f);
                //todo: flash mana bar instead
                playerIn.sendMessage(new TextComponentString("You do not have enough mana to cast this spell"));
            }

        } else {
            okToCast = true;
        }

        if (okToCast && !world.isRemote) {
            opusCapability.subtractMana(getCastingManaCost());
        }

        return okToCast;
    }
}
