package magikareborn.init;

import magikareborn.ModRoot;
import magikareborn.entities.ProjectileEntity;
import magikareborn.helpers.ResourceHelper;
import magikareborn.renderers.ProjectileEntityRenderer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {

    public static void registerEntities() {
        int id = 0;
        EntityRegistry.registerModEntity(
                ResourceHelper.getEntityResourceLocation(ProjectileEntity.class.getSimpleName()), ProjectileEntity.class, "Projectile Entity",
                id++, ModRoot.instance,
                32, 5, true);

        //Any EntityRegistry.addSpawn calls

        //Any LootTableList.register calls
    }

    public static void registerEntityRenderers() {

        //System.out.println("Registering entity renderers");

        RenderingRegistry.registerEntityRenderingHandler(ProjectileEntity.class, ProjectileEntityRenderer.FACTORY);
    }
}
