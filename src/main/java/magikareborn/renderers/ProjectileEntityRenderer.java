package magikareborn.renderers;

import magikareborn.entities.ProjectileEntity;
import magikareborn.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nonnull;

public class ProjectileEntityRenderer extends RenderSnowball<ProjectileEntity> {

    public static final IRenderFactory<ProjectileEntity> FACTORY = new Factory();

    public ProjectileEntityRenderer(RenderManager renderManagerIn, Item itemIn, RenderItem itemRendererIn) {
        super(renderManagerIn, itemIn, itemRendererIn);
    }

    @Nonnull
    @Override
    public ItemStack getStackToRender(ProjectileEntity entityIn) {
        return new ItemStack(item);
    }

    private static class Factory implements IRenderFactory<ProjectileEntity> {

        @Override
        public Render<? super ProjectileEntity> createRenderFor(RenderManager manager) {
            return new ProjectileEntityRenderer(manager, ModItems.LIGHT_SPELL_ITEM, Minecraft.getMinecraft().getRenderItem());
        }
    }

}
