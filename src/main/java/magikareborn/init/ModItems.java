package magikareborn.init;

import magikareborn.items.LightSpellItem;
import magikareborn.items.MagikaOpusItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

	//todo: why is this not working?
    public static final CreativeTabs MAGIKA_REBORN_TAB = (new CreativeTabs("tabMagicaReborn") {
        @Override
        public ItemStack getTabIconItem() {
            //todo: change icon
            return new ItemStack(Blocks.DIRT);
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
