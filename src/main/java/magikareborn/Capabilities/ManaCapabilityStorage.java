package magikareborn.Capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class ManaCapabilityStorage implements Capability.IStorage<IManaCapability> {

    public static void register() {
        //todo: replace
        CapabilityManager.INSTANCE.register(IManaCapability.class, new ManaCapabilityStorage(), ManaCapability.class);
    }

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IManaCapability> capability, IManaCapability instance, EnumFacing side) {
        return new NBTTagFloat(instance.getMana());
    }

    @Override
    public void readNBT(Capability<IManaCapability> capability, IManaCapability instance, EnumFacing side, NBTBase nbt) {
        instance.setMana(((NBTPrimitive) nbt).getFloat());
    }
}
