package net.minecraft.client.gui;

import com.nquantum.gl.*;
import java.util.concurrent.atomic.*;
import net.minecraft.client.renderer.texture.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.vertex.*;
import java.awt.*;
import com.nquantum.gl.particle.*;
import net.minecraft.client.*;
import com.nquantum.*;
import com.nquantum.util.color.*;
import com.nquantum.util.*;
import com.mojang.realmsclient.gui.*;
import com.google.common.collect.*;
import org.apache.commons.io.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;
import net.minecraft.world.demo.*;
import com.nquantum.ui.login.*;
import java.io.*;
import net.minecraft.realms.*;
import com.nquantum.auth.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import java.net.*;
import net.minecraft.world.storage.*;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback
{
    private float updateCounter;
    private int field_92022_t;
    private long initTime;
    private int field_92020_v;
    private static final ResourceLocation minecraftTitleTextures;
    private boolean field_175375_v;
    private GLSLSandboxShader backgroundShader;
    private String openGLWarning2;
    private static final Random RANDOM;
    private final Object threadLock;
    private GuiButton buttonResetDemo;
    private int field_92021_u;
    public static final String field_96138_a;
    private ResourceLocation backgroundTexture;
    private static final ResourceLocation[] titlePanoramaPaths;
    private GuiButton realmsButton;
    private String openGLWarningLink;
    private int field_92019_w;
    private String splashText;
    private String openGLWarning1;
    private static final AtomicInteger field_175373_f;
    private static final Logger logger;
    private int field_92024_r;
    private int field_92023_s;
    private static final ResourceLocation splashTexts;
    private DynamicTexture viewportTexture;
    private int panoramaTimer;
    
    private void drawPanorama(final int n, final int n2, final float n3) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GlStateManager.matrixMode(5888);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.translate((0 / (float)8 - 0.5f) / 64.0f, (0 / (float)8 - 0.5f) / 64.0f, 0.0f);
        GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + n3) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-(this.panoramaTimer + n3) * 0.1f, 0.0f, 1.0f, 0.0f);
        while (true) {
            this.mc.getTextureManager().bindTexture(GuiMainMenu.titlePanoramaPaths[0]);
            worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            worldRenderer.pos(-1.0, -1.0, 1.0).tex(0.0, 0.0).color(255, 255, 255, 2).endVertex();
            worldRenderer.pos(1.0, -1.0, 1.0).tex(1.0, 0.0).color(255, 255, 255, 2).endVertex();
            worldRenderer.pos(1.0, 1.0, 1.0).tex(1.0, 1.0).color(255, 255, 255, 2).endVertex();
            worldRenderer.pos(-1.0, 1.0, 1.0).tex(0.0, 1.0).color(255, 255, 255, 2).endVertex();
            instance.draw();
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final int n4 = GuiMainMenu.width / 2 - 2;
        this.backgroundShader.useShader(GuiMainMenu.width, GuiMainMenu.height, (float)n, (float)n2, (System.currentTimeMillis() - this.initTime) / 2000.0f);
        this.backgroundShader.useShader(GuiMainMenu.width, GuiMainMenu.height, (float)n, (float)n2, (System.currentTimeMillis() - this.initTime) / 2000.0f);
        Gui.drawRect(scaledResolution.getScaledWidth(), scaledResolution.getScaledHeight(), 0.0, 0.0, new Color(0, 0, 0, 255).getRGB());
        new ParticleSystem(230, true, true, 15).render();
        GL11.glBegin(7);
        GL11.glVertex2f(-1.0f, -1.0f);
        GL11.glVertex2f(-1.0f, 1.0f);
        GL11.glVertex2f(1.0f, 1.0f);
        GL11.glVertex2f(1.0f, -1.0f);
        GL11.glEnd();
        GL20.glUseProgram(0);
        this.mc.getTextureManager().bindTexture(GuiMainMenu.minecraftTitleTextures);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        // dcmpg((double)this.updateCounter, 1.0E-4)
        GlStateManager.translate((float)(GuiMainMenu.width / 2 + 90), 70.0f, 0.0f);
        GlStateManager.rotate(-20.0f, 0.0f, 0.0f, 1.0f);
        final float n5 = (1.8f - MathHelper.abs(MathHelper.sin(Minecraft.getSystemTime() % 1000L / 1000.0f * 3.1415927f * 2.0f) * 0.1f)) * 100.0f / (this.fontRendererObj.getStringWidth(this.splashText) + 32);
        GlStateManager.scale(n5, n5, n5);
        final int hsBtoRGB = Color.HSBtoRGB(System.currentTimeMillis() % 12000L / 6000.0f, 0.8f, 1.0f);
        Asyncware.sfbold.drawCenteredString(Asyncware.instance.name, (float)(scaledResolution.getScaledWidth() / 2), 50.0f, Colors.fadeBetween(hsBtoRGB, Colors.darker(hsBtoRGB, 0.52f), (System.currentTimeMillis() + 10L) % 1000L / 500.0f));
        GuiUtil.drawScaledString("Version " + Asyncware.instance.version + " | Build: 000141", scaledResolution.getScaledWidth() / 2 - 30, 62, false, 0.5f);
        this.mc.gameSettings.limitFramerate = 1000;
        Asyncware.s.drawString("fps: " + this.mc.getDebugFps(), (float)(scaledResolution.getScaledWidth() - this.mc.fontRendererObj.getStringWidth("fps: " + this.mc.getDebugFps()) + 4), 1.0f, -1);
        String s = "Protocol > " + Minecraft.getMinecraft().getVersion();
        if (this.mc.isDemo()) {
            s += " Demo";
        }
        GuiUtil.drawScaledString("[ " + ChatFormatting.BLUE + "Changelog" + ChatFormatting.WHITE + " ]", 2, 2, true, 1.0f);
        final ArrayList<String> list = new ArrayList<String>();
        list.add("No Mouse Interact");
        list.add("Anti Desync");
        list.add("Chest Stealer");
        list.add("Trajectories");
        list.add("Ambience");
        list.add("Item Physics");
        list.add("Slime Jump");
        list.add("ESP 2D");
        list.add("Motion Graph");
        list.add("Anti Obsidian");
        list.add("Packet Fixer");
        list.add("No Sign Render");
        list.add("No Mouse Intersect");
        list.add("Multi Action");
        list.add("Boat Fly");
        list.add("Crafting Carry");
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            GuiUtil.drawScaledString(ChatFormatting.GREEN + "> Added " + iterator.next(), 2, 12, true, 0.5f);
            final int n6;
            n6 += 5;
        }
        this.drawString(this.fontRendererObj, s, 2, GuiMainMenu.height - 10, -1);
        final String s2 = "Made by Nquantum and dev team. Game belongs to Mojang AB!";
        GuiUtil.drawScaledString(s2, scaledResolution.getScaledWidth() - this.fontRendererObj.getStringWidth(s2) + 150, GuiMainMenu.height - 7, true, 0.5f);
        if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
            Gui.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
            this.drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
            this.drawString(this.fontRendererObj, this.openGLWarning2, (GuiMainMenu.width - this.field_92024_r) / 2, this.buttonList.get(0).yPosition - 12, -1);
        }
        super.drawScreen(n, n2, n3);
    }
    
    public GuiMainMenu() {
        this.initTime = System.currentTimeMillis();
        this.field_175375_v = true;
        this.threadLock = new Object();
        this.openGLWarning2 = GuiMainMenu.field_96138_a;
        this.splashText = "missingno";
        final ArrayList arrayList = Lists.newArrayList();
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(GuiMainMenu.splashTexts).getInputStream(), Charsets.UTF_8));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            final String trim = line.trim();
            if (!trim.isEmpty()) {
                arrayList.add(trim);
            }
        }
        if (!arrayList.isEmpty()) {
            do {
                this.splashText = (String)arrayList.get(GuiMainMenu.RANDOM.nextInt(arrayList.size()));
            } while (this.splashText.hashCode() == 125780783);
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        this.updateCounter = GuiMainMenu.RANDOM.nextFloat();
        this.openGLWarning1 = "";
        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
        this.backgroundShader = new GLSLSandboxShader("/noise.fsh");
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (guiButton.id == 5) {
            this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
        }
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (guiButton.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (guiButton.id == 14 && this.realmsButton.visible) {
            this.switchToRealms();
        }
        if (guiButton.id == 4) {
            this.mc.shutdown();
        }
        if (guiButton.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }
        if (guiButton.id == 12) {
            final WorldInfo worldInfo = this.mc.getSaveLoader().getWorldInfo("Demo_World");
            if (worldInfo != null) {
                this.mc.displayGuiScreen(GuiSelectWorld.func_152129_a(this, worldInfo.getWorldName(), 12));
            }
        }
        if (guiButton.id == 500) {
            this.mc.displayGuiScreen(new GuiAltLogin(this));
        }
        if (guiButton.id == 123) {
            this.backgroundShader = new GLSLSandboxShader("/amogus.fsh");
        }
    }
    
    private void switchToRealms() {
        new RealmsBridge().switchToRealms(this);
    }
    
    @Override
    public void initGui() {
        if (!GuiAuthenticate.authenticated) {
            this.mc.displayGuiScreen(new GuiAuthenticate(new GuiMainMenu()));
        }
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        final Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        if (instance.get(2) + 1 == 12 && instance.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        }
        else if (instance.get(2) + 1 == 1 && instance.get(5) == 1) {
            this.splashText = "Happy new year!";
        }
        else if (instance.get(2) + 1 == 10 && instance.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }
        final int n = GuiMainMenu.height / 4 + 48;
        if (this.mc.isDemo()) {
            this.addDemoButtons(n, 24);
        }
        else {
            this.addSingleplayerMultiplayerButtons(n, 24);
        }
        this.buttonList.add(new GuiButton(0, GuiMainMenu.width / 2 - 100, n + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, GuiMainMenu.width / 2 + 2, n + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButtonLanguage(5, GuiMainMenu.width / 2 - 124, n + 72 + 12));
        this.buttonList.add(new GuiButton(500, GuiMainMenu.width / 2 - 100, GuiMainMenu.height / 4 + 48 + 48, "Login"));
        this.buttonList.add(new GuiButton(123, GuiMainMenu.width / 2 - 100, GuiMainMenu.height - 30, "Switch Background"));
        // monitorenter(threadLock = this.threadLock)
        this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
        this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
        final int max = Math.max(this.field_92023_s, this.field_92024_r);
        this.field_92022_t = (GuiMainMenu.width - max) / 2;
        this.field_92021_u = this.buttonList.get(0).yPosition - 24;
        this.field_92020_v = this.field_92022_t + max;
        this.field_92019_w = this.field_92021_u + 24;
        // monitorexit(threadLock)
        this.mc.func_181537_a(false);
        this.initTime = System.currentTimeMillis();
    }
    
    static {
        field_175373_f = new AtomicInteger(0);
        logger = LogManager.getLogger();
        RANDOM = new Random();
        splashTexts = new ResourceLocation("texts/splashes.txt");
        minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
        titlePanoramaPaths = new ResourceLocation[] { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
        field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    }
    
    private void addDemoButtons(final int n, final int n2) {
        this.buttonList.add(new GuiButton(11, GuiMainMenu.width / 2 - 100, n, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(12, GuiMainMenu.width / 2 - 100, n + n2 * 1, I18n.format("menu.resetdemo", new Object[0])));
        if (this.mc.getSaveLoader().getWorldInfo("Demo_World") == null) {
            this.buttonResetDemo.enabled = false;
        }
    }
    
    @Override
    public void confirmClicked(final boolean b, final int n) {
        if (b && n == 12) {
            final ISaveFormat saveLoader = this.mc.getSaveLoader();
            saveLoader.flushCache();
            saveLoader.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (n == 13) {
            if (b) {
                final Class<?> forName = Class.forName("java.awt.Desktop");
                forName.getMethod("browse", URI.class).invoke(forName.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]), new URI(this.openGLWarningLink));
            }
            this.mc.displayGuiScreen(this);
        }
    }
    
    private void rotateAndBlurSkybox(final float n) {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        while (true) {
            final float n2 = 1.0f / 1;
            final int width = GuiMainMenu.width;
            final int height = GuiMainMenu.height;
            final float n3 = 0 / 256.0f;
            worldRenderer.pos(width, height, this.zLevel).tex(0.0f + n3, 1.0).color(1.0f, 1.0f, 1.0f, n2).endVertex();
            worldRenderer.pos(width, 0.0, this.zLevel).tex(1.0f + n3, 1.0).color(1.0f, 1.0f, 1.0f, n2).endVertex();
            worldRenderer.pos(0.0, 0.0, this.zLevel).tex(1.0f + n3, 0.0).color(1.0f, 1.0f, 1.0f, n2).endVertex();
            worldRenderer.pos(0.0, height, this.zLevel).tex(0.0f + n3, 0.0).color(1.0f, 1.0f, 1.0f, n2).endVertex();
            int n4 = 0;
            ++n4;
        }
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
    }
    
    private void renderSkybox(final int n, final int n2, final float n3) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(n, n2, n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.rotateAndBlurSkybox(n3);
        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        final float n4 = (GuiMainMenu.width > GuiMainMenu.height) ? (120.0f / GuiMainMenu.width) : (120.0f / GuiMainMenu.height);
        final float n5 = GuiMainMenu.height * n4 / 256.0f;
        final float n6 = GuiMainMenu.width * n4 / 256.0f;
        final int width = GuiMainMenu.width;
        final int height = GuiMainMenu.height;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldRenderer.pos(0.0, height, this.zLevel).tex(0.5f - n5, 0.5f + n6).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(width, height, this.zLevel).tex(0.5f - n5, 0.5f - n6).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(width, 0.0, this.zLevel).tex(0.5f + n5, 0.5f - n6).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        worldRenderer.pos(0.0, 0.0, this.zLevel).tex(0.5f + n5, 0.5f + n6).color(1.0f, 1.0f, 1.0f, 1.0f).endVertex();
        instance.draw();
    }
    
    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
    }
    
    private void addSingleplayerMultiplayerButtons(final int n, final int n2) {
        this.buttonList.add(new GuiButton(1, GuiMainMenu.width / 2 - 100, n, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, GuiMainMenu.width / 2 - 100, n + n2 * 1, I18n.format("menu.multiplayer", new Object[0])));
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        // monitorenter(threadLock = this.threadLock)
        if (this.openGLWarning1.length() > 0 && n >= this.field_92022_t && n <= this.field_92020_v && n2 >= this.field_92021_u && n2 <= this.field_92019_w) {
            final GuiConfirmOpenLink guiConfirmOpenLink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
            guiConfirmOpenLink.disableSecurityWarning();
            this.mc.displayGuiScreen(guiConfirmOpenLink);
        }
    }
    // monitorexit(threadLock)
}
