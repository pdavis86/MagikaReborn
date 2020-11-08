package uk.co.davissoftware.magikareborn.common.items;

import net.minecraft.item.Item;
import uk.co.davissoftware.magikareborn.ModRoot;

public class ItemBase extends Item {

    public ItemBase(Properties properties) {
        super(properties
                .group(ModRoot.CREATIVE_TAB));
    }
}
