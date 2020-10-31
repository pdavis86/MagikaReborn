package uk.co.davissoftware.magikareborn.common.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import uk.co.davissoftware.magikareborn.ModRoot;

public class BlockHelper {

    @SuppressWarnings("ConstantConditions")
    public static Item GetBlockItem(Block block) {
        return new BlockItem(block, new Item.Properties().group(ModRoot.CREATIVE_TAB))
            //.setRegistryName(block.getRegistryName())
            .getItem()
        ;
    }
}
