package magikareborn.items;

import magikareborn.base.BaseSpell;
import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.entities.LightSpellEntity;
import magikareborn.helpers.ResourceHelper;
import magikareborn.helpers.SoundHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class LightSpellItem extends BaseSpell {

    public LightSpellItem() {
        super("LightSpellItem");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResource(this));
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        /*ThreadLocalRandom rnd = ThreadLocalRandom.current();
        System.out.println("New light spell");
        System.out.println("Setting rnd value of " + rnd.nextInt(1, 15 + 1));
        System.out.println("Setting rnd value of " + rnd.nextInt(1, 15 + 1));
        System.out.println("Setting rnd value of " + rnd.nextInt(1, 15 + 1));
        System.out.println("Setting rnd value of " + rnd.nextInt(1, 15 + 1));
        System.out.println("Setting rnd value of " + rnd.nextInt(1, 15 + 1));*/
    }

    @Override
    public int getMinMagicLevel() {
        return 1;
    }

    @Override
    public int getCraftingXpCost() {
        return 0;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {

        if (!worldIn.isRemote) {
            if (canCast(playerIn)) {
                //todo: what is a good cooldown time? Should it come from the spell?
                playerIn.getCooldownTracker().setCooldown(this, 10);

                //todo: change sound
                SoundHelper.playSoundForAll(playerIn, SoundEvents.ENTITY_ENDERPEARL_THROW, 1, 1);
                worldIn.spawnEntity(new LightSpellEntity(worldIn, playerIn));

                IOpusCapability opusCapability = playerIn.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                if (opusCapability == null) {
                    OpusCapability.logNullWarning(playerIn.getName());
                } else {
                    opusCapability.subtractMana(getCastingManaCost());
                }

                playerIn.addStat(StatList.getObjectUseStats(this), 1);
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
