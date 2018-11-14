package magikareborn.blocks;

import magikareborn.base.BaseBlock;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModItems;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SuppressWarnings("deprecation")
public class LightSpellBlock extends BaseBlock {

    public LightSpellBlock() {
        super("LightSpellBlock", Material.CIRCUITS, ModItems.MAGIKA_REBORN_CREATIVE_TAB);
        //setTickRandomly(true);
        setLightLevel(16 / 16f);
        this.setSoundType(SoundType.GLASS);
        //todo: investigate how Blocks.REDSTONE_WIRE can be small
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), ResourceHelper.VARIANT_INVENTORY));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    /*@Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        SoundHelper.playSoundForAll(worldIn, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1f, 1f);
    }*/

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
