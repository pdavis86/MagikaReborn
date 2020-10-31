package uk.co.davissoftware.magikareborn.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.capabilities.IOpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapability;
import uk.co.davissoftware.magikareborn.common.helpers.LevellingHelper;
import uk.co.davissoftware.magikareborn.common.helpers.SoundHelper;

abstract class SpellItemBase extends Item {

    SpellItemBase() {
        super(new Item.Properties()
                .group(ModRoot.CREATIVE_TAB)
                .maxStackSize(1)
        );
    }

    public abstract int getMinMagicLevel();

    public abstract int getCraftingXpCost();

    public float getCraftingManaCost() {
        return LevellingHelper.getManaForCrafting(getMinMagicLevel());
    }

    protected float getCastingManaCost() {
        return LevellingHelper.getManaForCasting(getMinMagicLevel());
    }

    boolean canCast(PlayerEntity playerIn) {

        if (playerIn.abilities.isCreativeMode) {
            return true;
        }

        boolean okToCast = false;

        ModRoot.LOGGER.warn("TEST");

        IOpusCapability opusCapability = OpusCapability.getFromPlayer(playerIn);
        if (opusCapability == null) {
            return false;
        }

        World world = playerIn.getEntityWorld();

        if (!opusCapability.isSpellUnlocked()) {
            if (!world.isRemote) {
                //todo: replace sound event
                SoundHelper.playSoundForPlayer(playerIn, SoundEvents.BLOCK_GLASS_STEP, 1f, 1f);
                playerIn.sendMessage(new TranslationTextComponent("message.spell_not_unlocked"), null);
            }

        } else if (opusCapability.getMagicLevel() < getMinMagicLevel()) {
            if (!world.isRemote) {
                //todo: replace sound event
                SoundHelper.playSoundForPlayer(playerIn, SoundEvents.BLOCK_GLASS_STEP, 1f, 1f);
                playerIn.sendMessage(new TranslationTextComponent("message.magic_level_too_low"), null);
            }

        } else if (opusCapability.getMana() < getCastingManaCost()) {
            if (!world.isRemote) {
                //todo: replace sound event
                SoundHelper.playSoundForPlayer(playerIn, SoundEvents.BLOCK_GLASS_STEP, 1f, 1f);
                //todo: flash mana bar instead
                playerIn.sendMessage(new TranslationTextComponent("You do not have enough mana to cast this spell"), null);
            }

        } else {
            okToCast = true;
        }

        return okToCast;
    }
}
