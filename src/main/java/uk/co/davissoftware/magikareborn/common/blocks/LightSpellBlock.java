package uk.co.davissoftware.magikareborn.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class LightSpellBlock extends BlockBase {

    public static final String NAME = "lightspellblock";

    private static final VoxelShape SHAPE = Block.makeCuboidShape(6, 6, 6, 10, 10, 10);

    public LightSpellBlock() {
        super(Block.Properties
                .create(Material.GLASS)
                .sound(SoundType.GLASS)
                .notSolid()
                .doesNotBlockMovement()
                .noDrops()
                .notSolid()
        );
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 15;
    }

    @SuppressWarnings({"deprecation", "NullableProblems"})
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    //todo: Add ability to dye lights!
}
