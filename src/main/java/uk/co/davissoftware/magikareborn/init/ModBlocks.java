package uk.co.davissoftware.magikareborn.init;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import uk.co.davissoftware.magikareborn.common.util.RegistryHandler;

public class ModBlocks {



    public static void initModels() {
        RenderTypeLookup.setRenderLayer(RegistryHandler.LIGHTSPELL_BLOCK.get(), RenderType.getCutout());
    }
}
