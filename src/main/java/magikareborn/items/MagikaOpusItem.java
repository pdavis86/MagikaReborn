package magikareborn.items;

import magikareborn.ModRoot;
import magikareborn.base.BaseItem;
import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.gui.OpusGuiScreen;
import magikareborn.helpers.ResourceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class MagikaOpusItem extends BaseItem {

    public MagikaOpusItem() {
        super("MagikaOpusItem", ModRoot.MAGIKA_REBORN_CREATIVE_TAB);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResource(this));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {

        IOpusCapability opusCapability = playerIn.getCapability(OpusCapabilityStorage.CAPABILITY, null);
        if (opusCapability == null) {
            OpusCapability.logNullWarning(playerIn.getName());
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        if (worldIn.isRemote) {
            Minecraft.getMinecraft().displayGuiScreen(new OpusGuiScreen());
            playerIn.addStat(StatList.getObjectUseStats(this), 1);

        } else {
            if (opusCapability.getMagicLevel() == 0) {
                opusCapability.setMagicLevel(1);
                //System.out.println("Magic level is now: " + opusCapability.getMagicLevel() + " and manamax is: " + opusCapability.getManaMax());
                opusCapability.sendToPlayer();
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }


}
