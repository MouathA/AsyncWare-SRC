package net.minecraft.client.gui.stream;

import org.apache.logging.log4j.*;
import java.net.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import org.lwjgl.opengl.*;
import tv.twitch.*;
import net.minecraft.util.*;
import net.minecraft.client.stream.*;
import java.util.*;

public class GuiStreamUnavailable extends GuiScreen
{
    private static final Logger field_152322_a;
    private final GuiScreen parentScreen;
    private final List field_152327_i;
    private final List field_152323_r;
    private final Reason field_152326_h;
    private final IChatComponent field_152324_f;
    
    static {
        field_152322_a = LogManager.getLogger();
    }
    
    private void func_152320_a(final String s) {
        final Class<?> forName = Class.forName("java.awt.Desktop");
        forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), new URI(s));
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        this.drawDefaultBackground();
        int max = Math.max((int)(GuiStreamUnavailable.height * 0.85 / 2.0 - this.field_152323_r.size() * this.fontRendererObj.FONT_HEIGHT / 2.0f), 50);
        this.drawCenteredString(this.fontRendererObj, this.field_152324_f.getFormattedText(), GuiStreamUnavailable.width / 2, max - this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
        final Iterator<String> iterator = this.field_152323_r.iterator();
        while (iterator.hasNext()) {
            this.drawCenteredString(this.fontRendererObj, iterator.next(), GuiStreamUnavailable.width / 2, max, 10526880);
            max += this.fontRendererObj.FONT_HEIGHT;
        }
        super.drawScreen(n, n2, n3);
    }
    
    @Override
    public void initGui() {
        if (this.field_152323_r.isEmpty()) {
            this.field_152323_r.addAll(this.fontRendererObj.listFormattedStringToWidth(this.field_152326_h.func_152561_a().getFormattedText(), (int)(GuiStreamUnavailable.width * 0.75f)));
            if (this.field_152327_i != null) {
                this.field_152323_r.add("");
                final Iterator<ChatComponentTranslation> iterator = this.field_152327_i.iterator();
                while (iterator.hasNext()) {
                    this.field_152323_r.add(iterator.next().getUnformattedTextForChat());
                }
            }
        }
        if (this.field_152326_h.func_152559_b() != null) {
            this.buttonList.add(new GuiButton(0, GuiStreamUnavailable.width / 2 - 155, GuiStreamUnavailable.height - 50, 150, 20, I18n.format("gui.cancel", new Object[0])));
            this.buttonList.add(new GuiButton(1, GuiStreamUnavailable.width / 2 - 155 + 160, GuiStreamUnavailable.height - 50, 150, 20, I18n.format(this.field_152326_h.func_152559_b().getFormattedText(), new Object[0])));
        }
        else {
            this.buttonList.add(new GuiButton(0, GuiStreamUnavailable.width / 2 - 75, GuiStreamUnavailable.height - 50, 150, 20, I18n.format("gui.cancel", new Object[0])));
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.enabled) {
            if (guiButton.id == 1) {
                switch (GuiStreamUnavailable$1.$SwitchMap$net$minecraft$client$gui$stream$GuiStreamUnavailable$Reason[this.field_152326_h.ordinal()]) {
                    case 1:
                    case 2: {
                        this.func_152320_a("https://account.mojang.com/me/settings");
                        break;
                    }
                    case 3: {
                        this.func_152320_a("https://account.mojang.com/migrate");
                        break;
                    }
                    case 4: {
                        this.func_152320_a("http://www.apple.com/osx/");
                        break;
                    }
                    case 5:
                    case 6:
                    case 7: {
                        this.func_152320_a("http://bugs.mojang.com/browse/MC");
                        break;
                    }
                }
            }
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    @Override
    public void onGuiClosed() {
    }
    
    public static void func_152321_a(final GuiScreen guiScreen) {
        final Minecraft minecraft = Minecraft.getMinecraft();
        final IStream twitchStream = minecraft.getTwitchStream();
        if (!OpenGlHelper.framebufferSupported) {
            final ArrayList arrayList = Lists.newArrayList();
            arrayList.add(new ChatComponentTranslation("stream.unavailable.no_fbo.version", new Object[] { GL11.glGetString(7938) }));
            arrayList.add(new ChatComponentTranslation("stream.unavailable.no_fbo.blend", new Object[] { GLContext.getCapabilities().GL_EXT_blend_func_separate }));
            arrayList.add(new ChatComponentTranslation("stream.unavailable.no_fbo.arb", new Object[] { GLContext.getCapabilities().GL_ARB_framebuffer_object }));
            arrayList.add(new ChatComponentTranslation("stream.unavailable.no_fbo.ext", new Object[] { GLContext.getCapabilities().GL_EXT_framebuffer_object }));
            minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.NO_FBO, arrayList));
        }
        else if (twitchStream instanceof NullStream) {
            if (((NullStream)twitchStream).func_152937_a().getMessage().contains("Can't load AMD 64-bit .dll on a IA 32-bit platform")) {
                minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.LIBRARY_ARCH_MISMATCH));
            }
            else {
                minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.LIBRARY_FAILURE));
            }
        }
        else if (!twitchStream.func_152928_D() && twitchStream.func_152912_E() == ErrorCode.TTV_EC_OS_TOO_OLD) {
            switch (GuiStreamUnavailable$1.$SwitchMap$net$minecraft$util$Util$EnumOS[Util.getOSType().ordinal()]) {
                case 1: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.UNSUPPORTED_OS_WINDOWS));
                    break;
                }
                case 2: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.UNSUPPORTED_OS_MAC));
                    break;
                }
                default: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.UNSUPPORTED_OS_OTHER));
                    break;
                }
            }
        }
        else if (!minecraft.getTwitchDetails().containsKey((Object)"twitch_access_token")) {
            if (minecraft.getSession().getSessionType() == Session.Type.LEGACY) {
                minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.ACCOUNT_NOT_MIGRATED));
            }
            else {
                minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.ACCOUNT_NOT_BOUND));
            }
        }
        else if (!twitchStream.func_152913_F()) {
            switch (GuiStreamUnavailable$1.$SwitchMap$net$minecraft$client$stream$IStream$AuthFailureReason[twitchStream.func_152918_H().ordinal()]) {
                case 1: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.FAILED_TWITCH_AUTH));
                    break;
                }
                default: {
                    minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.FAILED_TWITCH_AUTH_ERROR));
                    break;
                }
            }
        }
        else if (twitchStream.func_152912_E() != null) {
            minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.INITIALIZATION_FAILURE, Arrays.asList(new ChatComponentTranslation("stream.unavailable.initialization_failure.extra", new Object[] { ErrorCode.getString(twitchStream.func_152912_E()) }))));
        }
        else {
            minecraft.displayGuiScreen(new GuiStreamUnavailable(guiScreen, Reason.UNKNOWN));
        }
    }
    
    public GuiStreamUnavailable(final GuiScreen guiScreen, final Reason reason) {
        this(guiScreen, reason, null);
    }
    
    public GuiStreamUnavailable(final GuiScreen parentScreen, final Reason field_152326_h, final List field_152327_i) {
        this.field_152324_f = new ChatComponentTranslation("stream.unavailable.title", new Object[0]);
        this.field_152323_r = Lists.newArrayList();
        this.parentScreen = parentScreen;
        this.field_152326_h = field_152326_h;
        this.field_152327_i = field_152327_i;
    }
    
    public enum Reason
    {
        ACCOUNT_NOT_MIGRATED("ACCOUNT_NOT_MIGRATED", 6, (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_migrated", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_migrated.okay", new Object[0])), 
        LIBRARY_ARCH_MISMATCH("LIBRARY_ARCH_MISMATCH", 1, (IChatComponent)new ChatComponentTranslation("stream.unavailable.library_arch_mismatch", new Object[0]));
        
        private final IChatComponent field_152575_n;
        
        LIBRARY_FAILURE("LIBRARY_FAILURE", 2, (IChatComponent)new ChatComponentTranslation("stream.unavailable.library_failure", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])), 
        UNSUPPORTED_OS_WINDOWS("UNSUPPORTED_OS_WINDOWS", 3, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.windows", new Object[0])), 
        NO_FBO("NO_FBO", 0, (IChatComponent)new ChatComponentTranslation("stream.unavailable.no_fbo", new Object[0])), 
        UNSUPPORTED_OS_MAC("UNSUPPORTED_OS_MAC", 4, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.mac", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.mac.okay", new Object[0])), 
        ACCOUNT_NOT_BOUND("ACCOUNT_NOT_BOUND", 7, (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_bound", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.account_not_bound.okay", new Object[0]));
        
        private final IChatComponent field_152574_m;
        
        UNSUPPORTED_OS_OTHER("UNSUPPORTED_OS_OTHER", 5, (IChatComponent)new ChatComponentTranslation("stream.unavailable.not_supported.other", new Object[0])), 
        FAILED_TWITCH_AUTH_ERROR("FAILED_TWITCH_AUTH_ERROR", 9, (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth_error", new Object[0])), 
        UNKNOWN("UNKNOWN", 11, (IChatComponent)new ChatComponentTranslation("stream.unavailable.unknown", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])), 
        FAILED_TWITCH_AUTH("FAILED_TWITCH_AUTH", 8, (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.failed_auth.okay", new Object[0]));
        
        private static final Reason[] $VALUES;
        
        INITIALIZATION_FAILURE("INITIALIZATION_FAILURE", 10, (IChatComponent)new ChatComponentTranslation("stream.unavailable.initialization_failure", new Object[0]), (IChatComponent)new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0]));
        
        public IChatComponent func_152559_b() {
            return this.field_152575_n;
        }
        
        private Reason(final String s, final int n, final IChatComponent field_152574_m, final IChatComponent field_152575_n) {
            this.field_152574_m = field_152574_m;
            this.field_152575_n = field_152575_n;
        }
        
        static {
            $VALUES = new Reason[] { Reason.NO_FBO, Reason.LIBRARY_ARCH_MISMATCH, Reason.LIBRARY_FAILURE, Reason.UNSUPPORTED_OS_WINDOWS, Reason.UNSUPPORTED_OS_MAC, Reason.UNSUPPORTED_OS_OTHER, Reason.ACCOUNT_NOT_MIGRATED, Reason.ACCOUNT_NOT_BOUND, Reason.FAILED_TWITCH_AUTH, Reason.FAILED_TWITCH_AUTH_ERROR, Reason.INITIALIZATION_FAILURE, Reason.UNKNOWN };
        }
        
        private Reason(final String s, final int n, final IChatComponent chatComponent) {
            this(s, n, chatComponent, null);
        }
        
        public IChatComponent func_152561_a() {
            return this.field_152574_m;
        }
    }
}
