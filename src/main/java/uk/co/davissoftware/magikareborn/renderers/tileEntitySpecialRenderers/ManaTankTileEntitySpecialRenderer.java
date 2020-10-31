package magikareborn.renderers.tileEntitySpecialRenderers;

import magikareborn.helpers.RenderHelper;
import magikareborn.tileentities.ManaTankTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class ManaTankTileEntitySpecialRenderer extends TileEntitySpecialRenderer<ManaTankTileEntity> {

    @Override
    public void render(ManaTankTileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        FluidTank tank = te.getFluidTank();
        FluidStack liquid = tank.getFluid();

        if (liquid == null) {
            return;
        }

        //System.out.println("Rendering amount: " + liquid.amount);

        float height = (float) liquid.amount / tank.getCapacity();
        float offset = RenderHelper.FLUID_OFFSET;
        RenderHelper.renderFluidCuboid(liquid, te.getPos(), x, y, z, offset, offset, offset, 1d - offset, height - offset, 1d - offset);
    }
}