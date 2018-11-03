package magikareborn.helpers;

import magikareborn.ModRoot;
import net.minecraft.util.ResourceLocation;

public class ResourceHelper {

    public static final String VARIANT_INVENTORY = "inventory";

    private static final String _resourceDomain = ModRoot.MODID.toLowerCase();
    //private static final String _blockTexturePathStem = "textures/blocks/";
    private static final String _fluidTexturePathStem = "blocks/fluids/";
    private static final String _guiTexturePathStem = "textures/gui/";

    /*public static String getBlockTexturePath(String textureFileName){
        return String.format("%s/%s", getBlockTexturePathStem(), textureFileName);
    }*/

    public static ResourceLocation getFluidResourceLocation(String textureFileName){
        return new ResourceLocation(_resourceDomain, _fluidTexturePathStem + textureFileName);
    }

    public static ResourceLocation getGuiResourceLocation(String textureFileName){
        //return new ResourceLocation(ModRoot.MODID, "textures/gui/tinychest.png");
        return new ResourceLocation(_resourceDomain, _guiTexturePathStem + textureFileName + ".png");
    }
}
