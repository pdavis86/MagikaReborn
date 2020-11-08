package uk.co.davissoftware.magikareborn.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import uk.co.davissoftware.magikareborn.common.capabilities.IOpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapability;
import uk.co.davissoftware.magikareborn.common.entities.LightSpellEntity;
import uk.co.davissoftware.magikareborn.common.helpers.SoundHelper;

public class LightSpellItem extends SpellItemBase {

    public LightSpellItem() {
        super();
    }

//    @SideOnly(Side.CLIENT)
//    public void initModel() {
//        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResource(this));
//    }

    @Override
    public int getMinMagicLevel() {
        return 1;
    }

    @Override
    public int getCraftingXpCost() {
        return 0;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isRemote) {
            if (canCast(playerIn)) {
                //todo: what is a good cooldown time? Should it come from the spell?
                playerIn.getCooldownTracker().setCooldown(this, 10);

                //todo: change sound
                SoundHelper.playSoundForAll(playerIn, SoundEvents.ENTITY_ENDER_PEARL_THROW, 1, 1);
                worldIn.addEntity(new LightSpellEntity(playerIn, worldIn));

                IOpusCapability opusCapability = OpusCapability.getFromPlayer(playerIn);
                if (opusCapability != null) {
                    opusCapability.subtractMana(getCastingManaCost());
                }

                playerIn.addStat(Stats.ITEM_USED.get(this));
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
