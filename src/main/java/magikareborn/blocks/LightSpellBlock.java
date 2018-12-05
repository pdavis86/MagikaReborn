package magikareborn.blocks;

import magikareborn.base.BaseBlock;
import magikareborn.helpers.ResourceHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

//Inspiration from BlockFlowerPot

@SuppressWarnings("deprecation")
public class LightSpellBlock extends BaseBlock {

    private static final AxisAlignedBB _boundingBox = new AxisAlignedBB(0.375D, 0.375D, 0.375D, 0.625D, 0.625D, 0.625D);

    public LightSpellBlock() {
        super("LightSpellBlock", Material.CIRCUITS, null); //ModRoot.MAGIKA_REBORN_CREATIVE_TAB
        //setTickRandomly(true);
        setLightLevel(16 / 16f);
        this.setSoundType(SoundType.GLASS);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getResourceLocation(), ResourceHelper.INVENTORY_VARIANT));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return _boundingBox;
    }

    // used by the renderer to control lighting and visibility of other blocks.
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    // used by the renderer to control lighting and visibility of other blocks
    // also by (eg) wall or fence to control whether the fence joins itself to this block
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    /*@Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }*/

    @Nonnull
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, @Nonnull IBlockAccess worldIn, @Nonnull BlockPos pos) {
        return null;
    }

    /*@Override
    public int tickRate(World par1World) {
        return 20 - 5 * AMCore.config.getGFXLevel();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int meta = par1World.getBlockMetadata(par2, par3, par4);
        int color = ItemDye.field_150922_c[meta];

        AMParticle particle = (AMParticle)AMCore.instance.proxy.particleManager.spawn(par1World, "sparkle", par2 + 0.5 + (rand.nextDouble() * 0.2f - 0.1f), par3 + 0.5, par4 + 0.5 + (rand.nextDouble() * 0.2f - 0.1f));
        if (particle != null){
            particle.setIgnoreMaxAge(false);
            particle.setMaxAge(10 + rand.nextInt(20));
            particle.AddParticleController(new ParticleFloatUpward(particle, 0f, -0.01f, 1, false));
            particle.AddParticleController(new ParticleGrow(particle, -0.005f, 1, false));
            particle.setRGBColorI(color);
        }
    }*/

    //todo: Add ability to dye lights!
    /*@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int face, float interactX, float interactY, float interactZ){

        if (!world.isRemote && player.getCurrentEquippedItem() != null){

            int id = OreDictionary.getOreID(player.getCurrentEquippedItem());
            if (id > -1){
                ArrayList<ItemStack> ores = OreDictionary.getOres(id);
                for (ItemStack stack : ores){
                    if (stack.getItem() == Items.dye){
                        world.setBlockMetadataWithNotify(x, y, z, player.getCurrentEquippedItem().getItemDamage() % 15, 2);
                        break;
                    }
                }
            }
        }

        return super.onBlockActivated(world, x, y, z, player, face, interactX, interactY, interactZ);
    }*/

}
