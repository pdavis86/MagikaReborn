package magikareborn.renderers;

import magikareborn.entities.LightSpellEntity;
import magikareborn.init.ModBlocks;
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

public class LightSpellEntityRenderer extends RenderSnowball<LightSpellEntity> {

    public static final IRenderFactory<LightSpellEntity> FACTORY = new Factory();

    private LightSpellEntityRenderer(RenderManager renderManagerIn, Item itemIn, RenderItem itemRendererIn) {
        super(renderManagerIn, itemIn, itemRendererIn);
    }

    @Nonnull
    @Override
    public ItemStack getStackToRender(LightSpellEntity entityIn) {
        return new ItemStack(item);
    }

    private static class Factory implements IRenderFactory<LightSpellEntity> {

        @Override
        public Render<? super LightSpellEntity> createRenderFor(RenderManager manager) {
            return new LightSpellEntityRenderer(manager, Item.getItemFromBlock(ModBlocks.LIGHT_SPELL_BLOCK), Minecraft.getMinecraft().getRenderItem());
        }
    }

}
