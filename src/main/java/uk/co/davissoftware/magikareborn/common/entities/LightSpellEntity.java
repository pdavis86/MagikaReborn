package uk.co.davissoftware.magikareborn.common.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import uk.co.davissoftware.magikareborn.ModRoot;
import uk.co.davissoftware.magikareborn.common.capabilities.IOpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapability;
import uk.co.davissoftware.magikareborn.common.capabilities.OpusCapabilityStorage;
import uk.co.davissoftware.magikareborn.common.helpers.LightHelper;
import uk.co.davissoftware.magikareborn.common.helpers.SoundHelper;
import uk.co.davissoftware.magikareborn.common.util.RegistryHandler;

import javax.annotation.Nonnull;

public class LightSpellEntity extends ProjectileSpellEntityBase {

//    public LightSpellEntity(EntityType<? extends ThrowableEntity> type, LivingEntity throwerIn, World worldIn) {
//        super(type, throwerIn, worldIn);
//    }

    public LightSpellEntity(LivingEntity throwerIn, World worldIn) {
        //todo: replace snowball
        super(EntityType.SNOWBALL, throwerIn, worldIn);
        this.setNoGravity(true);
        this.setShooter(throwerIn);
        this.shoot(throwerIn.rotationPitch, throwerIn.rotationYaw, throwerIn.getEyeHeight());
    }

    private void shoot(float rotationPitchIn, float rotationYawIn, float pitchOffset) {
        float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
        float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
        this.shoot(f, f1, f2, 0.8f, 1f);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {

        if (world.isRemote || !this.isAlive()) {
            return;
        }

        this.remove();

        BlockPos impactPos = new BlockPos(
                (result.getHitVec().x - this.prevPosX) * 0.5 + result.getHitVec().x,
                (result.getHitVec().y - this.prevPosY) * 0.5 + result.getHitVec().y,
                (result.getHitVec().z - this.prevPosZ) * 0.5 + result.getHitVec().z
        );

        BlockState oldBlockState = world.getBlockState(impactPos);

//        ModRoot.LOGGER.warn("Impacted block at " + result.getHitVec());
//        ModRoot.LOGGER.warn("Adjusted to " + impactPos);
//        ModRoot.LOGGER.warn("Impacted with block " + oldBlockState.getBlock().getRegistryName() + ". Solid: " + oldBlockState.isSolid());
//        ModRoot.LOGGER.warn("Spell previously at " + this.prevPosX + " " + this.prevPosY + " " + this.prevPosZ);
//        ModRoot.LOGGER.warn("-");

        if (result.getType() != RayTraceResult.Type.BLOCK || oldBlockState.getBlock() == RegistryHandler.LIGHTSPELL_BLOCK.get()) {

//            ModRoot.LOGGER.warn("Impacted object of type " + result.getType());

            //todo: replace sound
            SoundHelper.playSoundForAll(thrower, SoundEvents.BLOCK_GLASS_BREAK, 1, 1);
            return;
        }

        BlockPos newBlockPos;
        if (oldBlockState.isSolid()) {
            newBlockPos = new BlockPos(this.prevPosX, this.prevPosY, this.prevPosZ);
        } else {
            newBlockPos = impactPos;
            world.destroyBlock(newBlockPos, true);
        }

        world.setBlockState(newBlockPos, RegistryHandler.LIGHTSPELL_BLOCK.get().getDefaultState());

        ModRoot.LOGGER.warn("After placing block");

        IOpusCapability opusCapability = thrower.getCapability(OpusCapabilityStorage.CAPABILITY, null).orElse(null);
        if (opusCapability == null) {
            OpusCapability.logNullWarning(thrower.getScoreboardName());
            return;
        }

        int oldLightValue = LightHelper.getLightLevelAtPos(world, newBlockPos);
        float xp = (float) Math.abs(oldLightValue - 16) / 100;
        opusCapability.addXp(xp);

        ModRoot.LOGGER.warn("Added " + xp);

        //todo: replace sound event
        SoundHelper.playSoundForPlayer(thrower, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
    }

}
