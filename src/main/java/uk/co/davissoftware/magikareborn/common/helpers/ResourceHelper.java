package uk.co.davissoftware.magikareborn.common.helpers;

import net.minecraft.util.ResourceLocation;
import uk.co.davissoftware.magikareborn.ModRoot;

import java.util.Locale;

public class ResourceHelper {

    private static final String MOD_NAMESPACE = ModRoot.MODID.toLowerCase(Locale.US);
    private static final String GUIS_TEXTURE_PATH_STEM = "textures/guis/";
//    private static final String ENTITIES_TEXTURE_PATH_STEM = "textures/entities/";
//    private static final String ITEMS_TEXTURE_PATH_STEM = "textures/items/";
//    private static final String GUIS_OPUS_TEXTURE_PATH_STEM = GUIS_TEXTURE_PATH_STEM + "opus/";

    public static ResourceLocation getResource(String path) {
        return new ResourceLocation(MOD_NAMESPACE, path);
    }

    public static ResourceLocation getGuiResource(String textureFileName) {
        return new ResourceLocation(MOD_NAMESPACE, GUIS_TEXTURE_PATH_STEM + textureFileName + ".png");
    }

//    public static ResourceLocation getEntityResource(String entityClassSimpleName) {
//        return new ResourceLocation(RESOURCE_DOMAIN, ENTITIES_TEXTURE_PATH_STEM + entityClassSimpleName + ".png");
//    }
//
//    public static ResourceLocation getTileEntityResource(Class<? extends TileEntity> classType) {
//        return new ResourceLocation(RESOURCE_DOMAIN, classType.getSimpleName());
//    }
//
//    public static ResourceLocation getItemResource(String name) {
//        return new ResourceLocation(RESOURCE_DOMAIN, ITEMS_TEXTURE_PATH_STEM + name.toLowerCase() + ".png");
//    }
//
//    public static ResourceLocation getOpusGuiResource(String textureFileName) {
//        return new ResourceLocation(RESOURCE_DOMAIN, GUIS_OPUS_TEXTURE_PATH_STEM + textureFileName + ".png");
//    }
}
