package net.minecraft.realms;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import com.mojang.util.*;
import net.minecraft.client.resources.*;

public class RealmsScreen
{
    public static final int SKIN_TEX_WIDTH = 64;
    public static final int SKIN_HEAD_HEIGHT = 8;
    public static final int SKIN_HAT_V = 8;
    protected Minecraft minecraft;
    public static final int SKIN_TEX_HEIGHT = 64;
    public static final int SKIN_HAT_HEIGHT = 8;
    private GuiScreenRealmsProxy proxy;
    public int height;
    public static final int SKIN_HAT_WIDTH = 8;
    public int width;
    public static final int SKIN_HEAD_U = 8;
    public static final int SKIN_HEAD_V = 8;
    public static final int SKIN_HAT_U = 40;
    public static final int SKIN_HEAD_WIDTH = 8;
    
    public static String getLocalizedString(final String s, final Object... array) {
        return I18n.format(s, array);
    }
    
    public void keyboardEvent() {
    }
    
    public void renderBackground() {
        this.proxy.drawDefaultBackground();
    }
    
    public static void blit(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10) {
        Gui.drawScaledCustomSizeModalRect(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
    }
    
    public void renderTooltip(final String s, final int n, final int n2) {
        this.proxy.drawCreativeTabHoveringText(s, n, n2);
    }
    
    public List buttons() {
        return this.proxy.func_154320_j();
    }
    
    public void buttonClicked(final RealmsButton realmsButton) {
    }
    
    public void tick() {
    }
    
    public boolean isPauseScreen() {
        return this.proxy.doesGuiPauseGame();
    }
    
    public int height() {
        final GuiScreenRealmsProxy proxy = this.proxy;
        return GuiScreenRealmsProxy.height;
    }
    
    public void fillGradient(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.proxy.drawGradientRect(n, n2, n3, n4, n5, n6);
    }
    
    public int fontWidth(final String s) {
        return this.proxy.func_154326_c(s);
    }
    
    public void mouseClicked(final int n, final int n2, final int n3) {
    }
    
    public RealmsEditBox newEditBox(final int n, final int n2, final int n3, final int n4, final int n5) {
        return new RealmsEditBox(n, n2, n3, n4, n5);
    }
    
    public void init() {
    }
    
    public List fontSplit(final String s, final int n) {
        return this.proxy.func_154323_a(s, n);
    }
    
    public static void bind(final String s) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(s));
    }
    
    public void buttonsRemove(final RealmsButton realmsButton) {
        this.proxy.func_154328_b(realmsButton);
    }
    
    public void mouseEvent() {
    }
    
    public void renderBackground(final int n) {
        this.proxy.drawWorldBackground(n);
    }
    
    public static RealmsButton newButton(final int n, final int n2, final int n3, final String s) {
        return new RealmsButton(n, n2, n3, s);
    }
    
    public void keyPressed(final char c, final int n) {
    }
    
    public int fontLineHeight() {
        return this.proxy.func_154329_h();
    }
    
    public static RealmsButton newButton(final int n, final int n2, final int n3, final int n4, final int n5, final String s) {
        return new RealmsButton(n, n2, n3, n4, n5, s);
    }
    
    public void mouseReleased(final int n, final int n2, final int n3) {
    }
    
    public void drawCenteredString(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154325_a(s, n, n2, n3);
    }
    
    public GuiScreenRealmsProxy getProxy() {
        return this.proxy;
    }
    
    public static void blit(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final float n7, final float n8) {
        Gui.drawModalRectWithCustomSizedTexture(n, n2, n3, n4, n5, n6, n7, n8);
    }
    
    public void buttonsClear() {
        this.proxy.func_154324_i();
    }
    
    public void fontDrawShadow(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154319_c(s, n, n2, n3);
    }
    
    public RealmsScreen() {
        this.proxy = new GuiScreenRealmsProxy(this);
    }
    
    public void blit(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.proxy.drawTexturedModalRect(n, n2, n3, n4, n5, n6);
    }
    
    public void removed() {
    }
    
    public void buttonsAdd(final RealmsButton realmsButton) {
        this.proxy.func_154327_a(realmsButton);
    }
    
    public void init(final Minecraft minecraft, final int n, final int n2) {
    }
    
    public void renderTooltip(final ItemStack itemStack, final int n, final int n2) {
        this.proxy.renderToolTip(itemStack, n, n2);
    }
    
    public int width() {
        final GuiScreenRealmsProxy proxy = this.proxy;
        return GuiScreenRealmsProxy.width;
    }
    
    public void render(final int n, final int n2, final float n3) {
        while (0 < this.proxy.func_154320_j().size()) {
            this.proxy.func_154320_j().get(0).render(n, n2);
            int n4 = 0;
            ++n4;
        }
    }
    
    public static String getLocalizedString(final String s) {
        return I18n.format(s, new Object[0]);
    }
    
    public RealmsAnvilLevelStorageSource getLevelStorageSource() {
        return new RealmsAnvilLevelStorageSource(Minecraft.getMinecraft().getSaveLoader());
    }
    
    public void confirmResult(final boolean b, final int n) {
    }
    
    public void mouseDragged(final int n, final int n2, final int n3, final long n4) {
    }
    
    public void drawString(final String s, final int n, final int n2, final int n3) {
        this.proxy.func_154322_b(s, n, n2, n3);
    }
    
    public static void bindFace(final String s, final String s2) {
        ResourceLocation resourceLocation = AbstractClientPlayer.getLocationSkin(s2);
        if (resourceLocation == null) {
            resourceLocation = DefaultPlayerSkin.getDefaultSkin(UUIDTypeAdapter.fromString(s));
        }
        AbstractClientPlayer.getDownloadImageSkin(resourceLocation, s2);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
    }
    
    public void renderTooltip(final List list, final int n, final int n2) {
        this.proxy.drawHoveringText(list, n, n2);
    }
}
