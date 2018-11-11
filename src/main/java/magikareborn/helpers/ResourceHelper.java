package magikareborn.helpers;

import magikareborn.ModRoot;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ResourceHelper {

    public static final String VARIANT_INVENTORY = "inventory";

    private static final String _resourceDomain = ModRoot.MODID.toLowerCase();

    private static final String _guisTexturePathStem = "textures/guis/";
    private static final String _entitiesTexturePathStem = "textures/entities/";

    public static ModelResourceLocation getItemInventoryModelResourceLocation(Item item) {
        return new ModelResourceLocation(item.getRegistryName(), VARIANT_INVENTORY);
    }

    public static ResourceLocation getGuiResourceLocation(String textureFileName) {
        return new ResourceLocation(_resourceDomain, _guisTexturePathStem + textureFileName + ".png");
    }

    public static ResourceLocation getEntityResourceLocation(String entityClassSimpleName) {
        //System.out.println("There needs to be a texture in " + _entitiesTexturePathStem + entityClassSimpleName);
        return new ResourceLocation(_resourceDomain, _entitiesTexturePathStem + entityClassSimpleName + ".png"); //.toLowerCase()
    }
}
