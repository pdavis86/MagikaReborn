package magikareborn.blocks;

import magikareborn.ModRoot;
import magikareborn.helpers.ResourceHelper;
import magikareborn.helpers.SoundHelper;
import magikareborn.init.ModBlocks;
import magikareborn.init.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.stream.IntStream;

public class ManaFluidBlock extends BlockFluidClassic {

    //todo: tidy this

    public ManaFluidBlock(String name, Fluid fluid, Material material, CreativeTabs creativeTab) {
        super(fluid, material);
        setRegistryName(name.toLowerCase());
        setUnlocalizedName(ModRoot.MODID.toLowerCase() + "." + name.toLowerCase());
        setCreativeTab(creativeTab);
    }

    public Item getNewItem() {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        Item item = Item.getItemFromBlock(this);
        ModelLoader.setCustomModelResourceLocation(item, 0, ResourceHelper.getItemInventoryModelResource(item));
        ModelLoader.registerItemVariants(item);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

        if (worldIn.isRemote) {
            return;
        }

        if (entityIn instanceof EntityCreeper) {

            droppedInMana(worldIn, pos, entityIn);

            EntityVillager villager = new EntityVillager(worldIn);
            villager.setPosition(entityIn.posX, entityIn.posY, entityIn.posZ);
            worldIn.spawnEntity(villager);

            //villager.attackEntityFrom(new EntityDamageSource("creeper conversion", player), 18.0f); //todo: localise
            //villager.tasks.addTask(1, new EntityAIAvoidEntity<EntityPlayer>(villager, EntityPlayer.class, 6.0f, 1.0D, 1.2D));
            //villager.tasks.addTask(0, new EntityAIPanic(villager, 6.0D));

            /*this.tasks.addTask(5, new EntityAIAvoidEntity(this, new Predicate()
            {
                public boolean shallAvoid(Entity entity)
                {
                    return entity instanceof EntityCreeper;
                }
                public boolean apply(Object entityObject)
                {
                    return this.shallAvoid((Entity)entityObject);
                }
            }, 6.0F, 1.0D, 1.2D));*/

        } else if (entityIn instanceof EntityItem) {

            ItemStack itemStack = ((EntityItem) entityIn).getItem();
            Item item = itemStack.getItem();

            if (item == Items.BOOK) {

                droppedInMana(worldIn, pos, entityIn);

                EntityItem opus = new EntityItem(worldIn, entityIn.posX, entityIn.posY, entityIn.posZ, new ItemStack(ModItems.MAGIKA_OPUS_ITEM));
                worldIn.spawnEntity(opus);

                //todo: replace sound event
                SoundHelper.playSoundForAll(worldIn, entityIn.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1f, 1f);

            } else if (item != Item.getItemFromBlock(ModBlocks.MAGICAL_LOG_BLOCK) && item != Item.getItemFromBlock(ModBlocks.MAGICAL_PLANKS_BLOCK)) {
                int[] oreIds = OreDictionary.getOreIDs(itemStack);

                if (IntStream.of(oreIds).anyMatch(x -> x == OreDictionary.getOreID("logWood"))) {
                    //worldIn.getMinecraftServer().sendMessage(new TextComponentString("You tossed in a log"));
                    droppedInMana(worldIn, pos, entityIn);
                    EntityItem log = new EntityItem(worldIn, entityIn.posX, entityIn.posY, entityIn.posZ, new ItemStack(Item.getItemFromBlock(ModBlocks.MAGICAL_LOG_BLOCK), itemStack.getCount()));
                    worldIn.spawnEntity(log);

                }
                else if (IntStream.of(oreIds).anyMatch(x -> x == OreDictionary.getOreID("plankWood"))) {
                    //worldIn.getMinecraftServer().sendMessage(new TextComponentString("You tossed in planks"));
                    droppedInMana(worldIn, pos, entityIn);
                    EntityItem planks = new EntityItem(worldIn, entityIn.posX, entityIn.posY, entityIn.posZ, new ItemStack(Item.getItemFromBlock(ModBlocks.MAGICAL_PLANKS_BLOCK), itemStack.getCount()));
                    worldIn.spawnEntity(planks);
                }
            }
        }
    }

    private void droppedInMana(World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setDead();

        EntityLightningBolt bolt = new EntityLightningBolt(worldIn, entityIn.posX, entityIn.posY, entityIn.posZ, true);
        bolt.setPosition(entityIn.posX, entityIn.posY, entityIn.posZ);
        worldIn.addWeatherEffect(bolt);

        worldIn.setBlockToAir(pos);
    }

}
