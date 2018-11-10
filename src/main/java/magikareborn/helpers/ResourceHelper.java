package magikareborn.helpers;

import magikareborn.ModRoot;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ResourceHelper {

    public static final String VARIANT_INVENTORY = "inventory";

    private static final String _resourceDomain = ModRoot.MODID.toLowerCase();

    private static final String _fluidsTexturePathStem = "blocks/fluids/";
    private static final String _guisTexturePathStem = "textures/guis/";

    public static ModelResourceLocation getItemModelResourceLocation(Item item) {
        return new ModelResourceLocation(item.getRegistryName(), VARIANT_INVENTORY);
    }

    public static ResourceLocation getFluidResourceLocation(String textureFileName) {
        return new ResourceLocation(_resourceDomain, _fluidsTexturePathStem + textureFileName);
    }

    public static ResourceLocation getGuiResourceLocation(String textureFileName) {
        return new ResourceLocation(_resourceDomain, _guisTexturePathStem + textureFileName + ".png");
    }
}
