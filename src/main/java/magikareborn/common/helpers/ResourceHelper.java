package uk.co.davissoftware.magikareborn.common.helpers;

import net.minecraft.util.ResourceLocation;
import uk.co.davissoftware.magikareborn.ModRoot;

import java.util.Locale;

public class ResourceHelper {

    private static final String MOD_NAMESPACE = ModRoot.MODID.toLowerCase(Locale.US);

    public static ResourceLocation getResource(String path) {
        return new ResourceLocation(MOD_NAMESPACE, path);
    }
}
