package magikareborn.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nonnull;

public class ModCreativeTab extends CreativeTabs {

    public ModCreativeTab() {
        super("tabMagikaReborn");
    }

    @Nonnull
    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.MAGIKA_OPUS_ITEM);
    }

    @Override
    public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> items) {
        items.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.MANA_FLUID, Fluid.BUCKET_VOLUME)));
        super.displayAllRelevantItems(items);
    }

        /*
            for (Item item : Item.REGISTRY) {
                if (isInCreativeTab(item, this)) {
                    items.add(new ItemStack(item));
                }
            }

        }*/

        /*private boolean isInCreativeTab(Item item, CreativeTabs targetTab) {
            for (CreativeTabs tab : item.getCreativeTabs()) {
                if (tab == targetTab) {
                    return true;
                }
            }
            CreativeTabs creativetabs = item.getCreativeTab();
            return creativetabs != null && (targetTab == CreativeTabs.SEARCH || targetTab == creativetabs);
        }*/
}
