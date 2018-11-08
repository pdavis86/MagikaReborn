package magikareborn.blocks;

import magikareborn.ModRoot;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ManaFluidBlock extends BlockFluidClassic {

    //todo: tidy this

    public ManaFluidBlock(String name, Fluid fluid, Material material, CreativeTabs creativeTab) {
        super(fluid, material);
        setRegistryName(name.toLowerCase());
        setUnlocalizedName(ModRoot.MODID.toLowerCase() + "." + name.toLowerCase());
        setCreativeTab(creativeTab);
    }

    public Item getNewItem(){
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        Item item = Item.getItemFromBlock(this);
        ModelLoader.setCustomModelResourceLocation(item, 0, ResourceHelper.getItemModelResourceLocation(item));
        ModelLoader.registerItemVariants(item);
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

        if(entityIn.world.isRemote) {
            return;
        }

        //or, in Itickable, List entities = worldObj.getEntitiesWithinAABB(EntityItem.class, scanAbove);

        //todo: this is crap
        /*EntityPlayer player = worldIn.playerEntities.get(0);

        if(entityIn instanceof EntityPlayer) {

            //ItemStack heldItemStack = player.getHeldItem(EnumHand.MAIN_HAND);
            //TextComponentString component = new TextComponentString("You fell in the mana holding " + (heldItemStack != null ? heldItemStack.getDisplayName() : "nothing"));
            //component.getStyle().setColor(TextFormatting.BLUE);
            //player.sendStatusMessage(component, false);

            return;
        }*/

        if(entityIn instanceof EntityCreeper){
            entityIn.setDead();

            EntityLightningBolt bolt = new EntityLightningBolt(worldIn, entityIn.posX, entityIn.posY, entityIn.posZ, true);
            bolt.setPosition(entityIn.posX, entityIn.posY, entityIn.posZ);
            entityIn.world.spawnEntity(bolt);

            worldIn.setBlockToAir(pos);

            EntityVillager villager = new EntityVillager(entityIn.world);
            villager.setPosition(entityIn.posX, entityIn.posY, entityIn.posZ);

            entityIn.world.spawnEntity(villager);
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

        }
        /*else if(entityIn instanceof EntityItem) {

            ItemStack itemStack = ((EntityItem) entityIn).getItem();

            //TextComponentString msg = new TextComponentString("Item stack is: " + itemStack.getDisplayName());
            //player.sendStatusMessage(msg, false);

            if(itemStack.getItem() == Item.getItemFromBlock(Blocks.DIRT)){

                EntityLightningBolt bolt = new EntityLightningBolt(worldIn, entityIn.posX, entityIn.posY, entityIn.posZ, true);
                bolt.setPosition(entityIn.posX, entityIn.posY, entityIn.posZ);
                entityIn.world.spawnEntity(bolt);

                entityIn.setDead();

                //TextComponentString component = new TextComponentString("You dropped: " + entityIn.getName());
                //component.getStyle().setColor(TextFormatting.GREEN);
                //player.sendStatusMessage(component, false);



                player.inventory.addItemStackToInventory(new ItemStack(ModBlocks.MANA_FLUID_BLOCK));
            }
        }*/


    }
}
