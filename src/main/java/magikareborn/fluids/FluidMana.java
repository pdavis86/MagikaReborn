package magikareborn.fluids;

import magikareborn.ModRoot;
import magikareborn.init.ModMaterials;
import magikareborn.textures.ResourceManager;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

//import net.minecraftforge.fluids.Fluid;

public class FluidMana extends ModFluid {

    public static final String FLUIDNAME = "fluidNameLiquidMana"; //todo: where is this used?

	public FluidMana()
    {
        super(FLUIDNAME
                , new ResourceLocation(ModRoot.MODID, ResourceManager.getBlockTexturePath("liquidManaStill.png"))
                , new ResourceLocation(ModRoot.MODID, ResourceManager.getBlockTexturePath("liquidManaFlowing.png"))
        );

        setDensity(8);
		setViscosity(1);
		setLuminosity(9);
		setMaterial(ModMaterials.SLIME);
        //setGaseous(false);
        //setTemperature(300);
    }

    @Override
    public SoundEvent getEmptySound() {
        return SoundEvents.ITEM_BUCKET_EMPTY;
    }

    @Override
    public SoundEvent getFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL;
    }
}
