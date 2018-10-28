package magikareborn.init;

import magikareborn.fluids.FluidMana;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {

    public static FluidMana fluidMana;

    public static void registerFluids()
    {
        System.out.println("Registering fluids");

        fluidMana = new FluidMana();

        FluidRegistry.registerFluid(fluidMana);
    }
}
