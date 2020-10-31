package magikareborn.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class OpusCapabilityStorage implements Capability.IStorage<IOpusCapability> {

    //Unique instance of the capability
    @CapabilityInject(IOpusCapability.class)
    public static Capability<IOpusCapability> CAPABILITY = null;

    //Registration instance of this class
    private static final OpusCapabilityStorage _storage = new OpusCapabilityStorage();

    private OpusCapabilityStorage() {
    }

    public static void register() {
        CapabilityManager.INSTANCE.register(IOpusCapability.class, _storage, OpusCapability::new);
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
