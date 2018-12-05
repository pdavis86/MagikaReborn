package magikareborn.fluids;

import magikareborn.init.ModFluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaTankFluidHandler extends FluidHandlerItemStackSimple {

    /*public ManaTankFluidHandler(@Nonnull ItemStack container, int capacity) {
        super(container, capacity);
    }*/

    // Always make a copy of this if you use it for an assignment
    private static final FluidStack EMPTY = new FluidStack(ModFluids.MANA_FLUID, 0);

    public ManaTankFluidHandler(@Nonnull ItemStack container, int capacity) {
        super(container, capacity);

        // the reading of the NBT and conversion to capability data happens before the constructor
        //Without this, it will lose the fluidStack level after saving and loading the game
        if (getFluidStack() == null) {
            setContainerToEmpty();
        }
    }

    @Override
    protected void setContainerToEmpty() {
        // some code looks at level, some looks at lack of handler (tag)
        setFluidStack(EMPTY.copy());
        if (container.getTagCompound() == null) {
            return;
        }
        container.getTagCompound().removeTag(FLUID_NBT_KEY);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluidStack) {
        return (fluidStack.getFluid() == ModFluids.MANA_FLUID);
    }

    // rename getFluid() method since it is confusing as it returns a fluidStack stack
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    @Nullable
    @Override
    public FluidStack getFluid() {
        return super.getFluid();
    }

    @SuppressWarnings({"WeakerAccess", "deprecation"})
    public FluidStack getFluidStack() {
        return getFluid();
    }

    // rename setFluid() method since it is confusing as it take a fluidStack stack
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    @Override
    protected void setFluid(FluidStack fluid) {
        super.setFluid(fluid);
    }

    @SuppressWarnings({"WeakerAccess", "deprecation"})
    public void setFluidStack(FluidStack parFluidStack) {
        setFluid(parFluidStack);
    }


}
