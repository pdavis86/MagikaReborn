package magikareborn.items;

import magikareborn.base.BaseSpell;
import magikareborn.entities.LightSpellEntity;
import magikareborn.helpers.ResourceHelper;
import magikareborn.helpers.SoundHelper;
import magikareborn.init.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightSpellItem extends BaseSpell {

    public LightSpellItem() {
        super("LightSpellItem", ModItems.MAGIKA_REBORN_CREATIVE_TAB);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResourceLocation(this));
    }

    @Override
    public int getMinMagicLevel() {
        return 1;
    }

    @Override
    public float getManaCost() {
        return 0.2f;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if (!worldIn.isRemote) {
            if (canCast(playerIn)) {
                //todo: what is a good cooldown time? Should it come from the spell?
                playerIn.getCooldownTracker().setCooldown(this, 10);

                //todo: change sound
                SoundHelper.playSoundForAll(playerIn, SoundEvents.ENTITY_ENDERPEARL_THROW, 1, 1);
                worldIn.spawnEntity(new LightSpellEntity(worldIn, playerIn));
            }
        }
        //todo: move to teleport spell
        /*EntityEnderPearl pearl = new EntityEnderPearl(worldIn, playerIn);
        //pearl.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);
        pearl.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
        worldIn.spawnEntity(pearl);*/

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
