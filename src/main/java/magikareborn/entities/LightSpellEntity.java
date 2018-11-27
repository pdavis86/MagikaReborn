package magikareborn.entities;

import magikareborn.ModRoot;
import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.helpers.LightHelper;
import magikareborn.helpers.SoundHelper;
import magikareborn.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

import javax.annotation.Nonnull;

public class LightSpellEntity extends ProjectileSpellEntity {

    //Needed for registration
    @SuppressWarnings("unused")
    public LightSpellEntity(World worldIn) {
        super(worldIn);
    }

    public LightSpellEntity(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);

        this.setNoGravity(true);
        this.shoot(throwerIn, throwerIn.rotationPitch, throwerIn.rotationYaw, throwerIn.getEyeHeight(), 0.8f, 1f);
    }

    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {
        if (!world.isRemote) {

            this.setDead();

            //System.out.println("result.typeOfHit: " + result.typeOfHit);

            if (result.typeOfHit == RayTraceResult.Type.BLOCK && world.getBlockState(result.getBlockPos()).getBlock() != ModBlocks.LIGHT_SPELL_BLOCK) {

                IBlockState oldBlockState = world.getBlockState(result.getBlockPos());

                BlockPos newBlockPos;
                if (oldBlockState.isFullCube()) {
                    newBlockPos = new BlockPos(this.prevPosX, this.prevPosY, this.prevPosZ);
                } else {
                    world.destroyBlock(result.getBlockPos(), true);
                    newBlockPos = result.getBlockPos();
                }

                /*Chunk chunk = world.getChunkFromBlockCoords(newBlockPos);

                System.out.println("sky: " + chunk.getLightFor(EnumSkyBlock.SKY, newBlockPos));
                System.out.println("block: " + chunk.getLightFor(EnumSkyBlock.BLOCK, newBlockPos));
                System.out.println("Light: " + chunk.getLightSubtracted(newBlockPos, 0) + " (" + chunk.getLightFor(EnumSkyBlock.SKY, newBlockPos) + " sky, " + chunk.getLightFor(EnumSkyBlock.BLOCK, newBlockPos) + " block)");

                System.out.println("world.getSkylightSubtracted(): " + world.getSkylightSubtracted());
                System.out.println("world.getLight old: " + world.getLight(result.getBlockPos()));
                System.out.println("world.getLight new: " + world.getLight(newBlockPos));
                System.out.println("world.getLightFor old: " + world.getLightFor(EnumSkyBlock.BLOCK, result.getBlockPos()));
                System.out.println("world.getLightFor new: " + world.getLightFor(EnumSkyBlock.BLOCK, newBlockPos));
                System.out.println("world.getLightFor sky old: " + world.getLightFor(EnumSkyBlock.SKY, result.getBlockPos()));
                System.out.println("world.getLightFor sky new: " + world.getLightFor(EnumSkyBlock.SKY, newBlockPos));
                System.out.println("getLightValue old: " + oldBlockState.getBlock().getLightValue(oldBlockState, world, newBlockPos));
                System.out.println("getLightFromNeighbors old: " + world.getLightFromNeighbors(result.getBlockPos()));
                System.out.println("getLightFromNeighbors new: " + world.getLightFromNeighbors(newBlockPos));
                System.out.println("getLightBrightness old: " + world.getLightBrightness(result.getBlockPos()));
                System.out.println("getLightBrightness new: " + world.getLightBrightness(newBlockPos));
                System.out.println("getCombinedLight old: " + world.getCombinedLight(result.getBlockPos(), 0));
                System.out.println("getCombinedLight new: " + world.getCombinedLight(newBlockPos, 0));
                */

                int oldLightValue = LightHelper.getLightLevelAtPos(world, newBlockPos);
                float xp = (float) Math.abs(oldLightValue - 16) / 100;

                //System.out.println("oldLightValue is: " + oldLightValue + ", XP is: " + xp);

                world.setBlockState(newBlockPos, ModBlocks.LIGHT_SPELL_BLOCK.getDefaultState());

                IOpusCapability opusCapability = thrower.getCapability(OpusCapabilityStorage.CAPABILITY, null);
                if (opusCapability == null) {
                    OpusCapability.logNullWarning(thrower.getName());
                    return;
                }
                opusCapability.addMagicXp(xp);

            } else {
                //todo: replace sound
                SoundHelper.playSoundForAll(thrower, SoundEvents.BLOCK_GLASS_BREAK, 1, 1);
            }
        }

        /*IBlockState collisionBlockState = world.getBlockState(result.getBlockPos());
        int blockX = result.getBlockPos().getX();
        int blockY = result.getBlockPos().getY();
        int blockZ = result.getBlockPos().getZ();

        int blockFace = hmmmmmmmm;

        //if (collisionBlockState.getBlock() == Blocks.AIR) blockFace = -1;
        //if (blockFace != -1){
        switch (blockFace) {
            case 0:
                blocky--;
                break;
            case 1:
                blocky++;
                break;
            case 2:
                blockz--;
                break;
            case 3:
                blockz++;
                break;
            case 4:
                blockx--;
                break;
            case 5:
                blockx++;
                break;
        }
        //}

        BlockPos adjustedBlockPos = new BlockPos(blockX, blockY, blockZ);

        if (world.getBlockState(adjustedBlockPos) != Blocks.AIR) {
            return;
        }

        world.setBlockState(adjustedBlockPos, ModBlocks.LIGHT_SPELL_BLOCK.getDefaultState());*/
    }
}
