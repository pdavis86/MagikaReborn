package magikareborn.items;

import magikareborn.base.BaseItem;
import magikareborn.entities.ProjectileEntity;
import magikareborn.helpers.ResourceHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightSpellItem extends BaseItem {

    public LightSpellItem() {
        super("LightSpellItem", CreativeTabs.MISC);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        //todo: what is a good cooldown time? Should it come from the spell?
        playerIn.getCooldownTracker().setCooldown(this, 10);

        if (worldIn.isRemote) {
            //todo: change sound
            worldIn.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_ENDERPEARL_THROW, null, 1, 1, false);
        }
        else {
            //todo: check mana
            //todo: take mana
            worldIn.spawnEntity(new ProjectileEntity(worldIn, playerIn));

            //todo: remove
            /*EntityEnderPearl pearl = new EntityEnderPearl(worldIn, playerIn);
            //pearl.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);
            pearl.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(pearl);*/
        }

        //Entity example = new EntityThrowable(worldIn);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResourceLocation(this));
    }

}
