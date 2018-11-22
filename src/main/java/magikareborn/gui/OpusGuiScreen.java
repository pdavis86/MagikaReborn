package magikareborn.gui;

import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import magikareborn.helpers.ResourceHelper;
import magikareborn.opus.BaseEntry;
import magikareborn.opus.OpusBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;

public class OpusGuiScreen extends GuiScreen {

    private static final int _xSize = 400;
    private static final int _ySize = 200;
    private static final int _buttonSize = 22;
    private static final int _buttonPadding = 2;
    private static final int _entrySize = 24;

    private static final ResourceLocation _utilitySpellsTabResourceLocation = ResourceHelper.getOpusGuiResource("OpusTabUtility");
    private static final ResourceLocation _supportSpellsTabResourceLocation = ResourceHelper.getOpusGuiResource("OpusTabSupport");
    private static final ResourceLocation _offenseSpellsTabResourceLocation = ResourceHelper.getOpusGuiResource("OpusTabOffensive");
    private static final ResourceLocation _defensiveSpellsTabResourceLocation = ResourceHelper.getOpusGuiResource("OpusTabDefensive");
    private static final ResourceLocation _isLockedResourceLocation = ResourceHelper.getOpusGuiResource("OverlayIsLocked");
    private static final ResourceLocation _canBeUnlockedResourceLocation = ResourceHelper.getOpusGuiResource("OverlayCanBeUnlocked");

    private final ArrayList<BaseEntry> _opusEntries;

    private GuiButton _utilitySpellsButton;
    private GuiButton _supportSpellsButton;
    private GuiButton _offenseSpellsButton;
    private GuiButton _defensiveSpellsButton;
    private IOpusCapability _opusCapability;

    public OpusGuiScreen() {
        _opusEntries = OpusBuilder.getOpusEntries();
    }

    private int getButtonLeft(int index) {
        return (_buttonSize + _buttonPadding) * index;
    }

    private int getUiLeft() {
        return (width - _xSize) / 2;
    }

    //private int getUiRight() { return getUiLeft() + _xSize; }

    private int getUiTop() {
        return (height - _ySize - _buttonSize) / 2;
    }

    //private int getUiBottom() { return getUiTop() + _ySize; }

    @Override
    public void initGui() {
        super.initGui();

        int left = getUiLeft();
        int top = getUiTop();

        //System.out.println("Screen size is: " + width + " x " + height);
        //System.out.println("UI size is: " + _xSize + " x " + _ySize);
        //System.out.println("Position is: " + left + " x " + top);

        _utilitySpellsButton = new GuiButton(0, left, top, _buttonSize, _buttonSize, "U");
        _supportSpellsButton = new GuiButton(1, left + getButtonLeft(1), top, _buttonSize, _buttonSize, "S");
        _offenseSpellsButton = new GuiButton(2, left + getButtonLeft(2), top, _buttonSize, _buttonSize, "O");
        _defensiveSpellsButton = new GuiButton(3, left + getButtonLeft(3), top, _buttonSize, _buttonSize, "D");

        this.buttonList.add(_utilitySpellsButton);
        this.buttonList.add(_supportSpellsButton);
        this.buttonList.add(_offenseSpellsButton);
        this.buttonList.add(_defensiveSpellsButton);

        _opusCapability = Minecraft.getMinecraft().player.getCapability(OpusCapabilityStorage.CAPABILITY, null);

        for (BaseEntry entry : _opusEntries) {
            entry.loadFromCapability(_opusCapability);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //drawBackground(0x00FF00); //dirt texture
        drawDefaultBackground(); //semi transparent dark overlay

        if (!_opusCapability.isInitialised()) {
            //System.out.println("Still haven't received the OpusUpdatePacket from the server");
            _opusCapability = Minecraft.getMinecraft().player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
            //todo: draw loading screen
        } else {
            int activeButtonId = _opusCapability.getSelectedTab();
            if (activeButtonId == _utilitySpellsButton.id) {
                drawTabScreen(_utilitySpellsTabResourceLocation, BaseEntry.CATEGORY_UTILITY);
            } else if (activeButtonId == _supportSpellsButton.id) {
                drawTabScreen(_supportSpellsTabResourceLocation, BaseEntry.CATEGORY_SUPPORT);
            } else if (activeButtonId == _offenseSpellsButton.id) {
                drawTabScreen(_offenseSpellsTabResourceLocation, BaseEntry.CATEGORY_OFFENSIVE);
            } else if (activeButtonId == _defensiveSpellsButton.id) {
                drawTabScreen(_defensiveSpellsTabResourceLocation, BaseEntry.CATEGORY_DEFENSIVE);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        _opusCapability.setSelectedTab(button.id);
        _opusCapability.sendToServer();

        super.actionPerformed(button);
    }

    private void drawEntry(BaseEntry entry, int left, int top) {

        Minecraft.getMinecraft().renderEngine.bindTexture(entry.getResource());
        drawTexturedModalRect(left, top, 0, 0, _entrySize, _entrySize);

        //System.out.println("Entry " + entry.getName() + "... unlocked: " + entry.isUnlocked() + ", canbeunlocked: " + entry.canBeUnlocked());

        if (!entry.isUnlocked()) {
            drawRect(left, top, left+_entrySize, top+_entrySize, 0xAF000000);
            if (entry.canBeUnlocked()) {
                Minecraft.getMinecraft().renderEngine.bindTexture(_canBeUnlockedResourceLocation);
                drawTexturedModalRect(left, top, 0, 0, _entrySize, _entrySize);
            } else {
                Minecraft.getMinecraft().renderEngine.bindTexture(_isLockedResourceLocation);
                drawTexturedModalRect(left, top, 0, 0, _entrySize, _entrySize);
            }
        }
    }

    private void drawXpLine(int prevX, int prevY, int currX, int currY, float progressToNext) {
        int radius = 1;
        int yValue = prevY + (_entrySize / 2);

        //System.out.println("progressToNext: " + progressToNext);

        if (progressToNext < 1) {
            //System.out.println("Drawing line from " + (prevX + _entrySize) + " to " + currX);
            drawRect(prevX + _entrySize, yValue - radius, currX, yValue + radius, 0xFF000000);
        }

        if (progressToNext > 0) {
            int xpProgress = (int) ((currX - (prevX + _entrySize)) * progressToNext);
            //System.out.println("Drawing xpProgress of " + xpProgress + " from " + (prevX + _entrySize) + " to " + (prevX + _entrySize + xpProgress));
            drawRect(prevX + _entrySize, yValue - radius, prevX + _entrySize + xpProgress, yValue + radius, 0xFFFFFFFF);
        }

        //GlStateManager.resetColor();

        //Hack to reset colour
        //drawRect(0, 0, 1, 1, 0xFFFFFFFF);
    }

    private void drawTabScreen(ResourceLocation resource, String category) {

        int left = getUiLeft();
        int top = getUiTop() + _buttonSize + _buttonPadding;

        //System.out.println("Draw values, left: " + left + ", top: " + top);

        //Background
        Minecraft.getMinecraft().renderEngine.bindTexture(resource);
        //drawTexturedModalRect(left, top, 0, 0, _xSize, _ySize);
        drawModalRectWithCustomSizedTexture(left, top, 0,0,_xSize, _ySize, _xSize, _ySize);

        final int initialPadding = 16;
        final int paddingX = 16;
        final int paddingY = 8;
        int offsetX = initialPadding;
        int offsetY = initialPadding;
        int prevX, prevY, currX, currY;
        float progressToNext;
        BaseEntry currentChild;

        for (BaseEntry entry : _opusEntries) {
            if (entry.getCategory() == category) {

                currX = left + offsetX;
                currY = top + offsetY;
                drawEntry(entry, currX, currY);

                progressToNext = entry.getProgressToNext();
                currentChild = entry.getNext();
                while (currentChild != null) {
                    offsetX += _entrySize + paddingX;

                    prevX = currX;
                    prevY = currY;
                    currX = left + offsetX;
                    currY = top + offsetY;

                    //System.out.println("parent of currentChild " + currentChild.getName() + " reported progressToNext: " + progressToNext);

                    drawXpLine(prevX, prevY, currX, currY, progressToNext);
                    drawEntry(currentChild, currX, currY);

                    progressToNext = currentChild.getProgressToNext();
                    currentChild = currentChild.getNext();
                }

                offsetX = initialPadding;
                offsetY += _entrySize + paddingY;
            }
        }

        //int left = getUiLeft();
        //int top = getUiTop() + _buttonSize + _buttonPadding;

        //mc.fontRenderer

        //int stringWidth = mc.fontRenderer.getStringWidth(displayString);

        //Minecraft.getMinecraft().renderEngine.bindTexture(tab1ResourceLocation);
        //drawTexturedModalRect(left, top, 0, 0, _xSize, _ySize);

        //drawGradientRect(50, 50, 50, 50, 0xFF0000, 0x0000FF);

        //drawHorizontalLine (x-start, x-stop, y, color);
        //drawVerticalLine(x, y-start, y-stop, color);
        //drawRect(x-start, y-start, x-stop, y-stop, color);
        //drawGradientRect(x-start, y-start, x-stop, y-stop, color-top, color-bottom);
        //drawCenteredString(fontRenderer, text, x, y, color);
        //drawString(fontRenderer, text, x, y, color);
    }


}
