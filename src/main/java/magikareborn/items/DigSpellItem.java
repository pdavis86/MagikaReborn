package magikareborn.items;

import magikareborn.base.BaseSpell;
import magikareborn.capabilities.IOpusCapability;
import magikareborn.capabilities.OpusCapability;
import magikareborn.capabilities.OpusCapabilityStorage;
import magikareborn.helpers.ResourceHelper;
import magikareborn.helpers.SoundHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class DigSpellItem extends BaseSpell {

    public DigSpellItem() {
        super("DigSpellItem");
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResource(this));
    }

    @Override
    public int getMinMagicLevel() {
        return 1;
    }

    @Override
    public int getCraftingXpCost() {
        return 0;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {

        if (!canCast(playerIn) || worldIn.isRemote) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        Minecraft mc = Minecraft.getMinecraft();
        RayTraceResult target;

        if (mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
            target = mc.objectMouseOver;

        } else {

            int partialTicks = 0;
            double playerReach = mc.playerController.getBlockReachDistance();
            Vec3d eyePosition = playerIn.getPositionEyes(partialTicks);
            Vec3d lookVector = playerIn.getLook(partialTicks);
            Vec3d traceEnd = eyePosition.addVector(lookVector.x * playerReach, lookVector.y * playerReach, lookVector.z * playerReach);

            target = worldIn.rayTraceBlocks(eyePosition, traceEnd, true);
        }

        if (target == null) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        BlockPos pos = target.getBlockPos();
        IBlockState mouseoverBlockState = worldIn.getBlockState(pos);
        Block mouseoverBlock = mouseoverBlockState.getBlock();

        if (mouseoverBlock == Blocks.AIR) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        //todo: check if the block can be mined
        int blockHarvestLevel = mouseoverBlock.getHarvestLevel(mouseoverBlockState);
        if (blockHarvestLevel < 0) {
            blockHarvestLevel = 0;
        }

        if (!ForgeEventFactory.doPlayerHarvestCheck(playerIn, mouseoverBlockState, true)) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        EntityPlayerMP playerMp = (EntityPlayerMP) playerIn;
        int blockBreak = ForgeHooks.onBlockBreakEvent(worldIn, playerMp.interactionManager.getGameType(), playerMp, pos);
        if (blockBreak == -1) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        mouseoverBlock.onBlockHarvested(worldIn, pos, mouseoverBlockState, playerIn);

        boolean flag = mouseoverBlock.removedByPlayer(mouseoverBlockState, worldIn, pos, playerIn, true);
        if (flag) {
            mouseoverBlock.onBlockDestroyedByPlayer(worldIn, pos, mouseoverBlockState);
            mouseoverBlock.harvestBlock(worldIn, playerIn, pos, mouseoverBlockState, null, ItemStack.EMPTY);
        }

        SoundHelper.playSoundForAll(playerIn, mouseoverBlock.getSoundType(null,null,null,null).getBreakSound(), 1, 1);

        //todo: what is a good cooldown time? Should it come from the spell?
        playerIn.getCooldownTracker().setCooldown(this, 5);

        IOpusCapability opusCapability = playerIn.getCapability(OpusCapabilityStorage.CAPABILITY, null);
        if (opusCapability == null) {
            OpusCapability.logNullWarning(playerIn.getName());
        } else {
            opusCapability.subtractMana(getCastingManaCost());
            opusCapability.addXp(0.02f * (blockHarvestLevel + 1));
        }

        playerIn.addStat(StatList.getObjectUseStats(this), 1);

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
