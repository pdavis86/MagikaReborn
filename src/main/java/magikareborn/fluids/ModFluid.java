package magikareborn.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fluids.Fluid;

public class ModFluid extends Fluid {

    //http://jabelarminecraft.blogspot.com/p/minecraft-modding-custom-fluids.html

    protected int mapColor = 0xFFFFFFFF;
    protected float overlayAlpha = 0.2F;
    protected SoundEvent emptySound = SoundEvents.ITEM_BUCKET_EMPTY;
    protected SoundEvent fillSound = SoundEvents.ITEM_BUCKET_FILL;
    protected static Material material = Material.WATER;

    public ModFluid(String fluidName, ResourceLocation still, ResourceLocation flowing) {
        super(fluidName, still, flowing);
    }

    public ModFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, int mapColor) {
        this(fluidName, still, flowing);
        setColor(mapColor);
    }

    /*public ModFluid(String fluidName, ResourceLocation still, ResourceLocation flowing, int mapColor, float overlayAlpha) {
        this(fluidName, still, flowing, mapColor);
        setAlpha(overlayAlpha);
    }*/

    public ModFluid setColor(int parColor)    {
        mapColor = parColor;
        return this;
    }

    @Override
    public int getColor()    {
        return mapColor;
    }

    public ModFluid setAlpha(float parOverlayAlpha)    {
        overlayAlpha = parOverlayAlpha;
        return this;
    }

    public float getAlpha()    {
        return overlayAlpha;
    }

    @Override
    public ModFluid setEmptySound(SoundEvent parSound)    {
        emptySound = parSound;
        return this;
    }

    @Override
    public SoundEvent getEmptySound()    {
        return emptySound;
    }

    @Override
    public ModFluid setFillSound(SoundEvent parSound)    {
        fillSound = parSound;
        return this;
    }

    @Override
    public SoundEvent getFillSound()    {
        return fillSound;
    }

    public ModFluid setMaterial(Material parMaterial)    {
        material = parMaterial;
        return this;
    }

    public Material getMaterial()    {
        return material;
    }

    /*@Override
    public boolean doesVaporize(FluidStack fluidStack)    {
        if (block == null)
            return false;

        return block.getDefaultState().getMaterial() == getMaterial();
    }*/
}
