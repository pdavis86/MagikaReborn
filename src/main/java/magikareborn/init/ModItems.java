package magikareborn.init;

import magikareborn.items.LightSpellItem;
import magikareborn.items.MagikaOpusItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    //todo: why is this not working?
    public static final CreativeTabs MAGIKA_REBORN_CREATIVE_TAB = (new CreativeTabs("tabMagikaReborn") {
        @Override
        public ItemStack getTabIconItem() {
            //todo: change icon
            return new ItemStack(Blocks.DIRT);
        }

        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> items) {

            for (Item item : Item.REGISTRY) {
                if (isInCreativeTab(item, this)) {
                    items.add(new ItemStack(item));
                }
            }
            items.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.MANA_FLUID, Fluid.BUCKET_VOLUME)));
        }

        private boolean isInCreativeTab(Item item, CreativeTabs targetTab) {
            for (CreativeTabs tab : item.getCreativeTabs())
                if (tab == targetTab)
                    return true;
            CreativeTabs creativetabs = item.getCreativeTab();
            return creativetabs != null && (targetTab == CreativeTabs.SEARCH || targetTab == creativetabs);
        }
    });

    public static final MagikaOpusItem MAGIKA_OPUS_ITEM = new MagikaOpusItem();
    public static final LightSpellItem LIGHT_SPELL_ITEM = new LightSpellItem();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        MAGIKA_OPUS_ITEM.initModel();
        LIGHT_SPELL_ITEM.initModel();
    }


}
