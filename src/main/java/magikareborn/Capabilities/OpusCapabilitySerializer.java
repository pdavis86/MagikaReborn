package magikareborn.Capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OpusCapabilitySerializer implements ICapabilitySerializable<NBTTagCompound> {

    private static final String TAGNAME_SELECTED_TAB = "selectedtab";

    private final IOpusCapability _opusCapability;

    public OpusCapabilitySerializer() {
        _opusCapability = new OpusCapability();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == OpusCapabilityStorage.CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == OpusCapabilityStorage.CAPABILITY) {
            return (T) _opusCapability;
        }
        return null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger(TAGNAME_SELECTED_TAB, _opusCapability.getSelectedTab());
        return tagCompound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        _opusCapability.setSelectedTab(nbt.getInteger(TAGNAME_SELECTED_TAB));
    }

}
