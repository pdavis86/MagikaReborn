package magikareborn.init;

import magikareborn.items.MagikaOpusItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    public static final MagikaOpusItem MAGIKA_OPUS_ITEM = new MagikaOpusItem();

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        MAGIKA_OPUS_ITEM.initModel();
    }
}
