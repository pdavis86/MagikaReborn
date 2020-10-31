package uk.co.davissoftware.magikareborn.common.capabilities;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class OpusCapabilityStorage implements Capability.IStorage<IOpusCapability> {

    @CapabilityInject(IOpusCapability.class)
    public static Capability<IOpusCapability> CAPABILITY = null;

    private static final OpusCapabilityStorage _storage = new OpusCapabilityStorage();

    public static void register() {
        CapabilityManager.INSTANCE.register(IOpusCapability.class, _storage, OpusCapability::new);
    }

    @Override
    public INBT writeNBT(Capability<IOpusCapability> capability, IOpusCapability instance, Direction side) {
        //NOT USED
        return null;
    }

    @Override
    public void readNBT(Capability<IOpusCapability> capability, IOpusCapability instance, Direction side, INBT nbt) {
        //NOT USED
    }

}
