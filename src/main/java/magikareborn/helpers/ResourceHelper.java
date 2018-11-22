package magikareborn.helpers;

import magikareborn.ModRoot;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class ResourceHelper {

    public static final String RESOURCE_DOMAIN = ModRoot.MODID.toLowerCase();
    public static final String INVENTORY_VARIANT = "inventory";
    public static final String GUIS_TEXTURE_PATH_STEM = "textures/guis/";
    public static final String ENTITIES_TEXTURE_PATH_STEM = "textures/entities/";
    public static final String ITEMS_TEXTURE_PATH_STEM = "textures/items/";
    public static final String GUIS_OPUS_TEXTURE_PATH_STEM = GUIS_TEXTURE_PATH_STEM + "opus/";

    public static ModelResourceLocation getItemInventoryModelResource(Item item) {
        return new ModelResourceLocation(item.getRegistryName(), INVENTORY_VARIANT);
    }

    public static ResourceLocation getGuiResource(String textureFileName) {
        return new ResourceLocation(RESOURCE_DOMAIN, GUIS_TEXTURE_PATH_STEM + textureFileName + ".png");
    }

    public static ResourceLocation getEntityResource(String entityClassSimpleName) {
        return new ResourceLocation(RESOURCE_DOMAIN, ENTITIES_TEXTURE_PATH_STEM + entityClassSimpleName + ".png");
    }

    public static ResourceLocation getTileEntityResource(Class<? extends TileEntity> classType) {
        return new ResourceLocation(RESOURCE_DOMAIN, classType.getSimpleName());
    }

    public static ResourceLocation getItemResource(String name) {
        return new ResourceLocation(RESOURCE_DOMAIN, ITEMS_TEXTURE_PATH_STEM + name.toLowerCase() + ".png");
    }

    public static ResourceLocation getOpusGuiResource(String textureFileName){
        return new ResourceLocation(RESOURCE_DOMAIN, GUIS_OPUS_TEXTURE_PATH_STEM + textureFileName + ".png");
    }
}
