package magikareborn.fluids;

//import magikareborn.ModRoot;
//import magikareborn.init.ModMaterials;
import magikareborn.ModRoot;
import magikareborn.textures.ResourceManager;

import net.minecraft.init.SoundEvents;
//import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraft.util.SoundEvent;

//import net.minecraftforge.fluids.Fluid;

public class ManaFluid extends Fluid {

    public static final String FLUIDNAME = "manafluid";

	public ManaFluid()
    {
        super(FLUIDNAME
                , ResourceManager.getFluidResourceLocation("liquid_still")
                , ResourceManager.getFluidResourceLocation("liquid_flowing")
                //not working - , 0xef67f0f5
        );

        setUnlocalizedName(ModRoot.MODID + "." + FLUIDNAME.toLowerCase());

        setDensity(8); //Thickness
		setViscosity(3000); //Speed of movement
		//setLuminosity(9); //Brightness
		//setMaterial(ModMaterials.SLIME);
        //setGaseous(false);
        //setTemperature(300);

        // make opaque if no alpha is set
        /*if(((color >> 24) & 0xFF) == 0) {
            color |= 0xFF << 24;
        }*/
        //this.color = color;
    }

    @Override
    public SoundEvent getEmptySound() {
        return SoundEvents.ITEM_BUCKET_EMPTY;
    }

    @Override
    public SoundEvent getFillSound() {
        return SoundEvents.ITEM_BUCKET_FILL;
    }

    /*@Override
    public int getColor() {
        return color;
    }*/

    /*@Override
    public String getLocalizedName(FluidStack stack) {
        String s = this.getUnlocalizedName();
        return s == null ? "" : I18n.translateToLocal(s + ".name");
    }*/
}
