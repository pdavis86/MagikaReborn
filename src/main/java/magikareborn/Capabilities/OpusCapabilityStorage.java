package magikareborn.Capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class OpusCapabilityStorage implements Capability.IStorage<IOpusCapability> {

    //Unique instance of the capability
    @CapabilityInject(IOpusCapability.class)
    public static Capability<IOpusCapability> CAPABILITY = null;

    //Registration instance of this class
    private static final OpusCapabilityStorage _storage = new OpusCapabilityStorage();

    private OpusCapabilityStorage() {}

    public static void register() {
        CapabilityManager.INSTANCE.register(IOpusCapability.class, _storage, new Callable<IOpusCapability>() {
            @Override
            public IOpusCapability call() throws Exception {
                return new OpusCapability();
            }
        });
    }

    @Override
    public NBTBase writeNBT(Capability<IOpusCapability> capability, IOpusCapability instance, EnumFacing side) {
        //NOT USED
        return null;
    }

    @Override
    public void readNBT(Capability<IOpusCapability> capability, IOpusCapability instance, EnumFacing side, NBTBase nbt) {
        //NOT USED
    }

}
