package uk.co.davissoftware.magikareborn.common.helpers;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class LightHelper {

    //GuiOverlayDebug was very helpful

    public static int getLightLevelAtPos(World world, BlockPos blockPos) {
        int lightFromSky = world.getLightFor(LightType.SKY, blockPos) - world.getSkylightSubtracted();
        int lightFromBlocks = world.getLightFor(LightType.BLOCK, blockPos);

        //System.out.println("sky: " + lightFromSky + ", blocks: " + lightFromBlocks);

        return Math.max(lightFromSky, lightFromBlocks);
    }
}
