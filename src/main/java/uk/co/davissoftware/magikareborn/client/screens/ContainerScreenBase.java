package uk.co.davissoftware.magikareborn.client.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.text.ITextComponent;

public abstract class ContainerScreenBase<T extends Container> extends ContainerScreen {

    public ContainerScreenBase(Container screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    public int getWidth() {
        return field_230708_k_;
    }

    public int getHeight(){
        return field_230709_l_;
    }

    public Minecraft getMinecraft() {
        return field_230706_i_;
    }
}
