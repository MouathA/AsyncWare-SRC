package net.minecraft.client.gui;

import org.apache.logging.log4j.*;
import java.util.*;
import org.apache.commons.lang3.*;
import com.google.common.collect.*;
import java.io.*;
import org.lwjgl.input.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class GuiChat extends GuiScreen
{
    private String historyBuffer;
    private static final Logger logger;
    protected GuiTextField inputField;
    private List foundPlayerNames;
    private String defaultInputFieldText;
    private int sentHistoryCursor;
    private int autocompleteIndex;
    private boolean playerNamesFound;
    private boolean waitingOnAutocomplete;
    
    static {
        logger = LogManager.getLogger();
    }
    
    public void autocompletePlayerNames() {
        if (this.playerNamesFound) {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
            if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
                this.autocompleteIndex = 0;
            }
        }
        else {
            final int func_146197_a = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
            this.foundPlayerNames.clear();
            this.autocompleteIndex = 0;
            this.sendAutocompleteRequest(this.inputField.getText().substring(0, this.inputField.getCursorPosition()), this.inputField.getText().substring(func_146197_a).toLowerCase());
            if (this.foundPlayerNames.isEmpty()) {
                return;
            }
            this.playerNamesFound = true;
            this.inputField.deleteFromCursor(func_146197_a - this.inputField.getCursorPosition());
        }
        if (this.foundPlayerNames.size() > 1) {
            final StringBuilder sb = new StringBuilder();
            for (final String s : this.foundPlayerNames) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(s);
            }
            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(sb.toString()), 1);
        }
        this.inputField.writeText(this.foundPlayerNames.get(this.autocompleteIndex++));
    }
    
    @Override
    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }
    
    public void onAutocompleteResponse(final String[] array) {
        if (this.waitingOnAutocomplete) {
            this.playerNamesFound = false;
            this.foundPlayerNames.clear();
            while (0 < array.length) {
                final String s = array[0];
                if (s.length() > 0) {
                    this.foundPlayerNames.add(s);
                }
                int n = 0;
                ++n;
            }
            final String substring = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
            final String commonPrefix = StringUtils.getCommonPrefix(array);
            if (commonPrefix.length() > 0 && !substring.equalsIgnoreCase(commonPrefix)) {
                this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
                this.inputField.writeText(commonPrefix);
            }
            else if (this.foundPlayerNames.size() > 0) {
                this.playerNamesFound = true;
                this.autocompletePlayerNames();
            }
        }
    }
    
    public GuiChat() {
        this.historyBuffer = "";
        this.sentHistoryCursor = -1;
        this.foundPlayerNames = Lists.newArrayList();
        this.defaultInputFieldText = "";
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        Mouse.getEventDWheel();
    }
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        (this.inputField = new GuiTextField(0, this.fontRendererObj, 4, GuiChat.height - 12, GuiChat.width - 4, 12)).setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 == 0 && this.handleComponentClick(this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY()))) {
            return;
        }
        this.inputField.mouseClicked(n, n2, n3);
        super.mouseClicked(n, n2, n3);
    }
    
    @Override
    protected void setText(final String text, final boolean b) {
        if (b) {
            this.inputField.setText(text);
        }
        else {
            this.inputField.writeText(text);
        }
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
    }
    
    public GuiChat(final String defaultInputFieldText) {
        this.historyBuffer = "";
        this.sentHistoryCursor = -1;
        this.foundPlayerNames = Lists.newArrayList();
        this.defaultInputFieldText = "";
        this.defaultInputFieldText = defaultInputFieldText;
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        this.waitingOnAutocomplete = false;
        if (n == 15) {
            this.autocompletePlayerNames();
        }
        else {
            this.playerNamesFound = false;
        }
        if (n == 1) {
            this.mc.displayGuiScreen(null);
        }
        else if (n != 28 && n != 156) {
            if (n == 200) {
                this.getSentHistory(-1);
            }
            else if (n == 208) {
                this.getSentHistory(1);
            }
            else if (n == 201) {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
            }
            else if (n == 209) {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
            }
            else {
                this.inputField.textboxKeyTyped(c, n);
            }
        }
        else {
            final String trim = this.inputField.getText().trim();
            if (trim.length() > 0) {
                this.sendChatMessage(trim);
            }
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public void getSentHistory(final int n) {
        final int n2 = this.sentHistoryCursor + n;
        final int size = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        final int clamp_int = MathHelper.clamp_int(n2, 0, size);
        if (clamp_int != this.sentHistoryCursor) {
            if (clamp_int == size) {
                this.sentHistoryCursor = size;
                this.inputField.setText(this.historyBuffer);
            }
            else {
                if (this.sentHistoryCursor == size) {
                    this.historyBuffer = this.inputField.getText();
                }
                this.inputField.setText((String)this.mc.ingameGUI.getChatGUI().getSentMessages().get(clamp_int));
                this.sentHistoryCursor = clamp_int;
            }
        }
    }
    
    private void sendAutocompleteRequest(final String s, final String s2) {
        if (s.length() >= 1) {
            BlockPos blockPos = null;
            if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                blockPos = this.mc.objectMouseOver.getBlockPos();
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C14PacketTabComplete(s, blockPos));
            this.waitingOnAutocomplete = true;
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        Gui.drawRect(2.0, GuiChat.height - 14, GuiChat.width - 2, GuiChat.height - 2, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
        final IChatComponent chatComponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        if (chatComponent != null && chatComponent.getChatStyle().getChatHoverEvent() != null) {
            this.handleComponentHover(chatComponent, n, n2);
        }
        super.drawScreen(n, n2, n3);
    }
}
