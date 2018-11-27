package magikareborn.init;

import magikareborn.items.LightSpellItem;
import magikareborn.items.MagikaOpusItem;
import magikareborn.items.ManaTankItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static final MagikaOpusItem MAGIKA_OPUS_ITEM = new MagikaOpusItem();
    public static final LightSpellItem LIGHT_SPELL_ITEM = new LightSpellItem();
    public static final ManaTankItem MANA_TANK_ITEM = new ManaTankItem();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        MAGIKA_OPUS_ITEM.initModel();
        LIGHT_SPELL_ITEM.initModel();
        MANA_TANK_ITEM.initModel();
    }

}
