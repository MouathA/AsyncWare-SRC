package optfine;

import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class CustomSkyLayer
{
    public static final float[] DEFAULT_AXIS;
    private int startFadeIn;
    public String source;
    private int endFadeOut;
    private boolean rotate;
    private int startFadeOut;
    private float speed;
    private int blend;
    private int endFadeIn;
    public int textureId;
    private float[] axis;
    
    public boolean isActive(final int n) {
        return this.endFadeOut <= this.startFadeIn;
    }
    
    private float[] parseAxis(final String s, final float[] array) {
        if (s == null) {
            return array;
        }
        final String[] tokenize = Config.tokenize(s, " ");
        if (tokenize.length != 3) {
            Config.warn("Invalid axis: " + s);
            return array;
        }
        final float[] array2 = new float[3];
        while (0 < tokenize.length) {
            array2[0] = Config.parseFloat(tokenize[0], Float.MIN_VALUE);
            if (array2[0] == Float.MIN_VALUE) {
                Config.warn("Invalid axis: " + s);
                return array;
            }
            if (array2[0] < -1.0f || array2[0] > 1.0f) {
                Config.warn("Invalid axis values: " + s);
                return array;
            }
            int n = 0;
            ++n;
        }
        final float n2 = array2[0];
        final float n3 = array2[1];
        final float n4 = array2[2];
        if (n2 * n2 + n3 * n3 + n4 * n4 < 1.0E-5f) {
            Config.warn("Invalid axis values: " + s);
            return array;
        }
        return new float[] { n4, n3, -n2 };
    }
    
    private float getFadeBrightness(final int n) {
        if (this.startFadeIn <= this.endFadeIn) {
            return this.normalizeTime(n - this.startFadeIn) / (float)this.normalizeTime(this.endFadeIn - this.startFadeIn);
        }
        if (this.endFadeIn <= this.startFadeOut) {
            return 1.0f;
        }
        if (this.startFadeOut <= this.endFadeOut) {
            return 1.0f - this.normalizeTime(n - this.startFadeOut) / (float)this.normalizeTime(this.endFadeOut - this.startFadeOut);
        }
        return 0.0f;
    }
    
    private void renderSide(final Tessellator tessellator, final int n) {
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        final double n2 = n % 3 / 3.0;
        final double n3 = n / 3 / 2.0;
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(-100.0, -100.0, -100.0).tex(n2, n3).endVertex();
        worldRenderer.pos(-100.0, -100.0, 100.0).tex(n2, n3 + 0.5).endVertex();
        worldRenderer.pos(100.0, -100.0, 100.0).tex(n2 + 0.3333333333333333, n3 + 0.5).endVertex();
        worldRenderer.pos(100.0, -100.0, -100.0).tex(n2 + 0.3333333333333333, n3).endVertex();
        tessellator.draw();
    }
    
    public void render(final int n, final float n2, final float n3) {
        final float limit = Config.limit(n3 * this.getFadeBrightness(n), 0.0f, 1.0f);
        if (limit >= 1.0E-4f) {
            GlStateManager.bindTexture(this.textureId);
            Blender.setupBlend(this.blend, limit);
            if (this.rotate) {
                GlStateManager.rotate(n2 * 360.0f * this.speed, this.axis[0], this.axis[1], this.axis[2]);
            }
            final Tessellator instance = Tessellator.getInstance();
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(instance, 4);
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            this.renderSide(instance, 1);
            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            this.renderSide(instance, 0);
            GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(instance, 5);
            GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(instance, 2);
            GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            this.renderSide(instance, 3);
        }
    }
    
    public CustomSkyLayer(final Properties properties, final String s) {
        this.source = null;
        this.startFadeIn = -1;
        this.endFadeIn = -1;
        this.startFadeOut = -1;
        this.endFadeOut = -1;
        this.blend = 1;
        this.rotate = false;
        this.speed = 1.0f;
        this.axis = CustomSkyLayer.DEFAULT_AXIS;
        this.textureId = -1;
        this.source = properties.getProperty("source", s);
        this.startFadeIn = this.parseTime(properties.getProperty("startFadeIn"));
        this.endFadeIn = this.parseTime(properties.getProperty("endFadeIn"));
        this.startFadeOut = this.parseTime(properties.getProperty("startFadeOut"));
        this.endFadeOut = this.parseTime(properties.getProperty("endFadeOut"));
        this.blend = Blender.parseBlend(properties.getProperty("blend"));
        this.rotate = this.parseBoolean(properties.getProperty("rotate"), true);
        this.speed = this.parseFloat(properties.getProperty("speed"), 1.0f);
        this.axis = this.parseAxis(properties.getProperty("axis"), CustomSkyLayer.DEFAULT_AXIS);
    }
    
    static {
        DEFAULT_AXIS = new float[] { 1.0f, 0.0f, 0.0f };
    }
    
    public boolean isValid(final String s) {
        if (this.source == null) {
            Config.warn("No source texture: " + s);
            return false;
        }
        this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(s));
        if (this.startFadeIn < 0 || this.endFadeIn < 0 || this.endFadeOut < 0) {
            Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
            return false;
        }
        final int normalizeTime = this.normalizeTime(this.endFadeIn - this.startFadeIn);
        if (this.startFadeOut < 0) {
            this.startFadeOut = this.normalizeTime(this.endFadeOut - normalizeTime);
        }
        final int n = normalizeTime + this.normalizeTime(this.startFadeOut - this.endFadeIn) + this.normalizeTime(this.endFadeOut - this.startFadeOut) + this.normalizeTime(this.startFadeIn - this.endFadeOut);
        if (n != 24000) {
            Config.warn("Invalid fadeIn/fadeOut times, sum is more than 24h: " + n);
            return false;
        }
        if (this.speed < 0.0f) {
            Config.warn("Invalid speed: " + this.speed);
            return false;
        }
        return true;
    }
    
    private int parseTime(final String s) {
        if (s == null) {
            return -1;
        }
        final String[] tokenize = Config.tokenize(s, ":");
        if (tokenize.length != 2) {
            Config.warn("Invalid time: " + s);
            return -1;
        }
        final String s2 = tokenize[0];
        final String s3 = tokenize[1];
        final int int1 = Config.parseInt(s2, -1);
        final int int2 = Config.parseInt(s3, -1);
        if (int1 >= 0 && int1 <= 23 && int2 >= 0 && int2 <= 59) {
            int n = int1 - 6;
            if (n < 0) {
                n += 24;
            }
            return n * 1000 + (int)(int2 / 60.0 * 1000.0);
        }
        Config.warn("Invalid time: " + s);
        return -1;
    }
    
    private boolean parseBoolean(final String s, final boolean b) {
        if (s == null) {
            return b;
        }
        if (s.toLowerCase().equals("true")) {
            return true;
        }
        if (s.toLowerCase().equals("false")) {
            return false;
        }
        Config.warn("Unknown boolean: " + s);
        return b;
    }
    
    private int normalizeTime(int i) {
        while (i >= 24000) {
            i -= 24000;
        }
        while (i < 0) {
            i += 24000;
        }
        return i;
    }
    
    private float parseFloat(final String s, final float n) {
        if (s == null) {
            return n;
        }
        final float float1 = Config.parseFloat(s, Float.MIN_VALUE);
        if (float1 == Float.MIN_VALUE) {
            Config.warn("Invalid value: " + s);
            return n;
        }
        return float1;
    }
}
