package magikareborn.helpers;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class LightHelper {

    //GuiOverlayDebug was very helpful

    public static int getLightLevelAtPos(World world, BlockPos blockPos) {
        int lightFromSky = world.getLightFor(EnumSkyBlock.SKY, blockPos) - world.getSkylightSubtracted();
        int lightFromBlocks = world.getLightFor(EnumSkyBlock.BLOCK, blockPos);

        //System.out.println("sky: " + lightFromSky + ", blocks: " + lightFromBlocks);

        return Math.max(lightFromSky, lightFromBlocks);
    }

}
