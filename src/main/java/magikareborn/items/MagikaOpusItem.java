package magikareborn.items;

import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.base.BaseItem;
import magikareborn.gui.OpusGuiScreen;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagikaOpusItem extends BaseItem {

    public MagikaOpusItem() {
        super("MagikaOpusItem", ModItems.MAGIKA_REBORN_CREATIVE_TAB);
        //todo: add creation instructions to JEI : https://github.com/mezz/JustEnoughItems/wiki/Getting-Started
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        IOpusCapability opusCapability = playerIn.getCapability(OpusCapabilityStorage.CAPABILITY, null);

        if (worldIn.isRemote) {
            if (opusCapability.getMagicLevel() == 0){
                //todo: show achievement
            }
            Minecraft.getMinecraft().displayGuiScreen(new OpusGuiScreen());

        } else {
            if (opusCapability.getMagicLevel() == 0){
                opusCapability.setMagicLevel(1);
                //System.out.println("Magic level is now: " + opusCapability.getMagicLevel() + " and manamax is: " + opusCapability.getManaMax());
                opusCapability.sendToPlayer();
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResource(this));
    }

}
