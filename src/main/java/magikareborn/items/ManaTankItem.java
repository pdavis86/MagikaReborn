package magikareborn.items;

import magikareborn.base.BaseItem;
import magikareborn.fluids.ManaTankFluidHandler;
import magikareborn.helpers.ResourceHelper;
import magikareborn.init.ModFluids;
import magikareborn.init.ModItems;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.DispenseFluidContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManaTankItem extends BaseItem {

    private final int CAPACITY = Fluid.BUCKET_VOLUME * 4;
    private final ItemStack EMPTY_STACK = new ItemStack(this);

    //todo: this is not appearing in the creative menu!

    public ManaTankItem() {
        super("ManaTankItem", ModItems.MAGIKA_REBORN_CREATIVE_TAB);
        setMaxStackSize(1);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DispenseFluidContainer.getInstance());
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, ResourceHelper.getItemInventoryModelResource(this));
    }

    @Override
    public ICapabilityProvider initCapabilities(@Nonnull ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new ManaTankFluidHandler(stack, CAPACITY);
    }

    @Override
    public void getSubItems(@Nullable final CreativeTabs tab, @Nonnull final NonNullList subItems) {
        if (!this.isInCreativeTab(tab)) return;

        //todo: add more subitems

        subItems.add(EMPTY_STACK);

        final ItemStack stack = new ItemStack(this);
        final IFluidHandlerItem fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        if (fluidHandler != null) {
            final FluidStack fluidStack = new FluidStack(ModFluids.MANA_FLUID, CAPACITY);
            final int fluidFillAmount = fluidHandler.fill(fluidStack, true);
            if (fluidFillAmount == fluidStack.amount) {
                final ItemStack filledStack = fluidHandler.getContainer();
                subItems.add(filledStack);
            }
        }
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(@Nonnull final ItemStack stack) {
        String unlocalizedName = this.getUnlocalizedNameInefficiently(stack);
        IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(stack);
        FluidStack fluidStack = fluidHandler.getTankProperties()[0].getContents();

        // If the container is empty, translate the unlocalised name directly
        if (fluidStack == null || fluidStack.amount <= 0) {
            unlocalizedName += ".name";
        } else {
            unlocalizedName += ".filled.name";
        }

        return new TextComponentTranslation(unlocalizedName).getUnformattedText().trim();
    }
}
