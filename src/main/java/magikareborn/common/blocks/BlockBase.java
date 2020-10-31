package uk.co.davissoftware.magikareborn.common.blocks;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import uk.co.davissoftware.magikareborn.common.helpers.BlockHelper;

import java.util.List;

abstract class BlockBase extends Block {

    BlockBase(Properties properties) {
        super(properties);
    }

    @SuppressWarnings({"deprecation", "NullableProblems"})
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = Lists.newArrayList();
        list.add(new ItemStack(BlockHelper.GetBlockItem(state.getBlock())));
        return list;
    }

}
