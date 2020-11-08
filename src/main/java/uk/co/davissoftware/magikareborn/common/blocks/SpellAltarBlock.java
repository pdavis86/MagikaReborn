package uk.co.davissoftware.magikareborn.common.blocks;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import uk.co.davissoftware.magikareborn.common.helpers.BlockHelper;
import uk.co.davissoftware.magikareborn.common.helpers.ToolHelper;
import uk.co.davissoftware.magikareborn.common.tileentities.SpellAltarTileEntity;

import java.util.List;

public class SpellAltarBlock extends BlockBase {

    public static final String NAME = "spellaltarblock";

    public SpellAltarBlock() {
        super(Block.Properties
                .create(Material.WOOD)
                .hardnessAndResistance(1.5f, 8.0f)
                //.slipperiness(0.8F)
                .harvestTool(ToolType.AXE)
                .harvestLevel(ToolHelper.HARVESTLEVEL_WOOD)
                .sound(SoundType.WOOD)
        );
    }

    @SuppressWarnings({"deprecation", "NullableProblems"})
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> list = Lists.newArrayList();
        list.add(new ItemStack(BlockHelper.GetBlockItem(state.getBlock())));
        return list;
    }

    @SuppressWarnings({"deprecation", "NullableProblems"})
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }

        if (player.isSuppressingBounce()) {
            return ActionResultType.PASS;
        }

        if (!(player instanceof ServerPlayerEntity)) {
            return ActionResultType.PASS;
        }

        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (!(tileentity instanceof INamedContainerProvider)) {
            return ActionResultType.PASS;
        }

        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileentity, pos);
        return ActionResultType.SUCCESS;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new SpellAltarTileEntity();
    }
}
