package magikareborn.init;

import magikareborn.items.DigSpellItem;
import magikareborn.items.LightSpellItem;
import magikareborn.items.MagikaOpusItem;
import magikareborn.items.ManaBucketItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static final MagikaOpusItem MAGIKA_OPUS_ITEM = new MagikaOpusItem();
    public static final LightSpellItem LIGHT_SPELL_ITEM = new LightSpellItem();
    public static final DigSpellItem DIG_SPELL_ITEM = new DigSpellItem();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        MAGIKA_OPUS_ITEM.initModel();
        LIGHT_SPELL_ITEM.initModel();
        DIG_SPELL_ITEM.initModel();
    }

}
