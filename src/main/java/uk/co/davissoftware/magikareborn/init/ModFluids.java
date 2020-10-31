package magikareborn.init;

import magikareborn.ModRoot;
import magikareborn.base.ModFluid;
import net.minecraft.util.ResourceLocation;

public class ModFluids {

    public static ModFluid MANA_FLUID = new ModFluid(
            "manafluid",
            new ResourceLocation(ModRoot.MODID, "mana_still"),
            new ResourceLocation(ModRoot.MODID, "mana_flow")
    );

    //todo: look into this again. Looks to be how to simplify the blockstate json

    /*@SideOnly(Side.CLIENT)
    public static void initModels() {

        //System.out.println("Registering fluidStack models");
        //registerFluidModels(MANA_FLUID);
    }*/

    /*public static void registerFluidModels(Fluid fluidStack) {
        if(fluidStack == null) {
            return;
        }

        Block block = fluidStack.getBlock();
        if(block != null) {
            Item item = Item.getItemFromBlock(block);
            FluidStateMapper mapper = new FluidStateMapper(fluidStack);

            // item-model
            if(item != Items.AIR) {
                ModelLoader.registerItemVariants(item);
                ModelLoader.setCustomMeshDefinition(item, mapper);
            }
            // block-model
            ModelLoader.setCustomStateMapper(block, mapper);
        }
    }

    public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

        public final Fluid fluidStack;
        public final ModelResourceLocation location;

        public FluidStateMapper(Fluid fluidStack) {
            this.fluidStack = fluidStack;
            this.location = new ModelResourceLocation(new ResourceLocation(ModRoot.MODID.toLowerCase(), "fluid_block"), fluidStack.getName().toLowerCase());
        }

        @Nonnull
        @Override
        protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
            return location;
        }

        @Nonnull
        @Override
        public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
            return location;
        }
    }*/
}
