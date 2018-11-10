package magikareborn.containers;

import magikareborn.Capabilities.IOpusCapability;
import magikareborn.Capabilities.OpusCapabilityStorage;
import magikareborn.helpers.ResourceHelper;
import magikareborn.network.OpusRequestPacket;
import magikareborn.network.OpusUpdatePacket;
import magikareborn.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class SkillTreeGui extends GuiScreen {

    private static final int _xSize = 400;
    private static final int _ySize = 200;
    private static final int _buttonSize = 22;
    private static final int _buttonPadding = 2;

    private static final ResourceLocation _questTabResourceLocation = ResourceHelper.getGuiResourceLocation("OpusTabQuests");
    private static final ResourceLocation _utilitySpellsTabResourceLocation = ResourceHelper.getGuiResourceLocation("OpusTabUtility");
    private static final ResourceLocation _offenseSpellsTabResourceLocation = ResourceHelper.getGuiResourceLocation("OpusTabOffensive");
    private static final ResourceLocation _defensiveSpellsTabResourceLocation = ResourceHelper.getGuiResourceLocation("OpusTabDefensive");

    private GuiButton _questsButton;
    private GuiButton _utilitySpellsButton;
    private GuiButton _offenseSpellsButton;
    private GuiButton _defensiveSpellsButton;
    private IOpusCapability _opusCapability;

    private int getButtonLeft(int index) {
        return (_buttonSize + _buttonPadding) * index;
    }

    private int getUiLeft() {
        return (width - _xSize) / 2;
    }

    private int getUiTop() {
        return (height - _ySize - _buttonSize) / 2;
    }

    @Override
    public void initGui() {
        super.initGui();

        int left = getUiLeft();
        int top = getUiTop();

        //System.out.println("Screen size is: " + width + " x " + height);
        //System.out.println("UI size is: " + _xSize + " x " + _ySize);
        //System.out.println("Position is: " + left + " x " + top);

        _questsButton = new GuiButton(0, left, top, _buttonSize, _buttonSize, "Q");
        _utilitySpellsButton = new GuiButton(1, left + getButtonLeft(1), top, _buttonSize, _buttonSize, "U");
        _offenseSpellsButton = new GuiButton(2, left + getButtonLeft(2), top, _buttonSize, _buttonSize, "O");
        _defensiveSpellsButton = new GuiButton(3, left + getButtonLeft(3), top, _buttonSize, _buttonSize, "D");

        this.buttonList.add(_questsButton);
        this.buttonList.add(_utilitySpellsButton);
        this.buttonList.add(_offenseSpellsButton);
        this.buttonList.add(_defensiveSpellsButton);

        //System.out.println("Getting player Opus capability");
        _opusCapability = Minecraft.getMinecraft().player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
        //System.out.println("SkillTree: Opus capability getSelectedTab is: " + _opusCapability.getSelectedTab());

        if (_opusCapability.getSelectedTab() == -1) {
            //System.out.println("SkillTree: Requesting Opus data");
            PacketHandler.INSTANCE.sendToServer(new OpusRequestPacket());
        }

        //System.out.println("Getting player Mana capability");
        //IManaCapability manaCapability = Minecraft.getMinecraft().player.getCapability(ManaCapabilitySerializer.MANA_CAP, null);
        //System.out.println("Mana capability is null: " + (manaCapability == null));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //drawBackground(0x00FF00); //dirt texture
        drawDefaultBackground(); //semi transparent dark overlay

        int activeButtonId = _opusCapability.getSelectedTab();
        if (activeButtonId == _questsButton.id) {
            drawQuestScreen();
        } else if (activeButtonId == _utilitySpellsButton.id) {
            drawUtilityScreen();
        } else if (activeButtonId == _offenseSpellsButton.id) {
            drawOffensiveScreen();
        } else if (activeButtonId == _defensiveSpellsButton.id) {
            drawDefensiveScreen();
        } else if (activeButtonId == -1) {
            //System.out.println("_activeButtonId is still -1");
            _opusCapability = Minecraft.getMinecraft().player.getCapability(OpusCapabilityStorage.CAPABILITY, null);
            //todo: draw loading screen
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {

        _opusCapability.setSelectedTab(button.id);
        PacketHandler.INSTANCE.sendToServer(new OpusUpdatePacket(_opusCapability));

        super.actionPerformed(button);
    }

    private void drawQuestScreen() {
        int left = getUiLeft();
        int top = getUiTop() + _buttonSize + _buttonPadding;

        Minecraft.getMinecraft().renderEngine.bindTexture(_questTabResourceLocation);
        drawTexturedModalRect(left, top, 0, 0, _xSize, _ySize);
    }

    private void drawUtilityScreen() {
        int left = getUiLeft();
        int top = getUiTop() + _buttonSize + _buttonPadding;

        Minecraft.getMinecraft().renderEngine.bindTexture(_utilitySpellsTabResourceLocation);
        drawTexturedModalRect(left, top, 0, 0, _xSize, _ySize);

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

    private void drawOffensiveScreen() {
        int left = getUiLeft();
        int top = getUiTop() + _buttonSize + _buttonPadding;

        Minecraft.getMinecraft().renderEngine.bindTexture(_offenseSpellsTabResourceLocation);
        drawTexturedModalRect(left, top, 0, 0, _xSize, _ySize);
    }

    private void drawDefensiveScreen() {
        int left = getUiLeft();
        int top = getUiTop() + _buttonSize + _buttonPadding;

        Minecraft.getMinecraft().renderEngine.bindTexture(_defensiveSpellsTabResourceLocation);
        drawTexturedModalRect(left, top, 0, 0, _xSize, _ySize);
    }


}
