package magikareborn.textures;

import magikareborn.ModRoot;
import net.minecraft.util.ResourceLocation;

public class ResourceManager {

    public static final String VARIANT_INVENTORY = "inventory";

    private static final String _resourceDomain = ModRoot.MODID.toLowerCase();
    //private static final String _blockTexturePathStem = "textures/blocks";
    private static final String _fluidTexturePathStem = "blocks/fluids/";

    /*public static String getBlockTexturePath(String textureFileName){
        return String.format("%s/%s", getBlockTexturePathStem(), textureFileName);
    }*/

    public static ResourceLocation getFluidResourceLocation(String textureFileName){
        return new ResourceLocation(_resourceDomain, _fluidTexturePathStem + textureFileName);
    }
}
