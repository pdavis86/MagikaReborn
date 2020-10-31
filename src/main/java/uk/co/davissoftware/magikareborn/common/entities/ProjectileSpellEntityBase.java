package uk.co.davissoftware.magikareborn.common.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.EndGatewayTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class ProjectileSpellEntityBase extends ThrowableEntity {

    private static final DataParameter<ItemStack> ITEMSTACK_DATA = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.ITEMSTACK);

    LivingEntity thrower;

    private final int _maxTicksToLive;

    protected ProjectileSpellEntityBase(EntityType<? extends ThrowableEntity> type, LivingEntity throwerIn, World worldIn) {
        super(type, throwerIn, worldIn);
        this.thrower = throwerIn;

        //15 seconds * 20 ticks per second (as standard)
        _maxTicksToLive = 15 * 20;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public EntitySize getSize(Pose poseIn) {
        return EntitySize.fixed(0.25F, 0.25F);
    }

    @SuppressWarnings("IfStatementWithIdenticalBranches")
    @Override
    public void tick() {

        if (ticksExisted >= _maxTicksToLive && !world.isRemote) {
            this.remove();
            return;
        }


        //Everything below is a copy of ThrowableEntity.tick() except with one change to remove the slow down of the projectile

        super.tick();
        RayTraceResult raytraceresult = ProjectileHelper.func_234618_a_(this, this::func_230298_a_);
        boolean flag = false;
        if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockRayTraceResult) raytraceresult).getPos();
            BlockState blockstate = this.world.getBlockState(blockpos);
            if (blockstate.isIn(Blocks.NETHER_PORTAL)) {
                this.setPortal(blockpos);
                flag = true;
            } else if (blockstate.isIn(Blocks.END_GATEWAY)) {
                TileEntity tileentity = this.world.getTileEntity(blockpos);
                if (tileentity instanceof EndGatewayTileEntity && EndGatewayTileEntity.func_242690_a(this)) {
                    ((EndGatewayTileEntity) tileentity).teleportEntity(this);
                }
                flag = true;
            }
        }

        if (raytraceresult.getType() != RayTraceResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.onImpact(raytraceresult);
        }

        this.doBlockCollisions();
        Vector3d vector3d = this.getMotion();
        double d2 = this.getPosX() + vector3d.x;
        double d0 = this.getPosY() + vector3d.y;
        double d1 = this.getPosZ() + vector3d.z;
        this.func_234617_x_();
        float f;
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                //float f1 = 0.25F;
                this.world.addParticle(ParticleTypes.BUBBLE, d2 - vector3d.x * 0.25D, d0 - vector3d.y * 0.25D, d1 - vector3d.z * 0.25D, vector3d.x, vector3d.y, vector3d.z);
            }

            f = 1F; //Changed from 0.8F;
        } else {
            f = 1F; //Changed from 0.99F;
        }

        this.setMotion(vector3d.scale(/*(double)*/ f));
        if (!this.hasNoGravity()) {
            Vector3d vector3d1 = this.getMotion();
            this.setMotion(vector3d1.x, vector3d1.y - (double) this.getGravityVelocity(), vector3d1.z);
        }

        this.setPosition(d2, d0, d1);
    }

    protected void registerData() {
        this.getDataManager().register(ITEMSTACK_DATA, ItemStack.EMPTY);
    }

}

