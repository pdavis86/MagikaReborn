package magikareborn.items;

import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import magikareborn.base.BaseItem;
import magikareborn.helpers.ResourceHelper;
import net.minecraft.creativetab.CreativeTabs;
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
        super("MagikaOpusItem", CreativeTabs.MISC);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if (worldIn.isRemote) {
            IOpusCapability opusCapability = playerIn.getCapability(OpusCapabilityStorage.CAPABILITY, null);

            System.out.println("Selected tab on rightclick: " + playerIn.getCapability(OpusCapabilityStorage.CAPABILITY, null).getSelectedTab());

            //todo: get the data if missing then open GUI
            /*if (opusCapability.getSelectedTab() == -1) {

            } else {
                Minecraft.getMinecraft().displayGuiScreen(new SkillTreeGui());
            }*/
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemModelResourceLocation(this));
    }

}
