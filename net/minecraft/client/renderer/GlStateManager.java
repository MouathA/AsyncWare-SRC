package net.minecraft.client.renderer;

import org.lwjgl.opengl.*;
import java.nio.*;

public class GlStateManager
{
    private static BooleanState rescaleNormalState;
    private static CullState cullState;
    private static StencilState stencilState;
    private static BooleanState normalizeState;
    private static ColorLogicState colorLogicState;
    private static AlphaState alphaState;
    private static TexGenState texGenState;
    public static boolean clearEnabled;
    private static BooleanState[] lightState;
    private static ColorMask colorMaskState;
    private static FogState fogState;
    private static BlendState blendState;
    private static TextureState[] textureState;
    private static BooleanState lightingState;
    private static PolygonOffsetState polygonOffsetState;
    private static Color colorState;
    private static ColorMaterialState colorMaterialState;
    private static final String __OBFID = "CL_00002558";
    private static ClearState clearState;
    private static DepthState depthState;
    
    public static void enableLighting() {
        GlStateManager.lightingState.setEnabled();
    }
    
    public static void texGen(final TexGen texGen, final int field_179066_c) {
        final TexGenCoord texGenCoord = texGenCoord(texGen);
        if (field_179066_c != texGenCoord.field_179066_c) {
            texGenCoord.field_179066_c = field_179066_c;
            GL11.glTexGeni(texGenCoord.field_179065_b, 9472, field_179066_c);
        }
    }
    
    public static void disableColorLogic() {
        GlStateManager.colorLogicState.field_179197_a.setDisabled();
    }
    
    public static void setFog(final int field_179047_b) {
        if (field_179047_b != GlStateManager.fogState.field_179047_b) {
            GL11.glFogi(2917, GlStateManager.fogState.field_179047_b = field_179047_b);
        }
    }
    
    public static void scale(final double n, final double n2, final double n3) {
        GL11.glScaled(n, n2, n3);
    }
    
    public static void disableColorMaterial() {
        GlStateManager.colorMaterialState.field_179191_a.setDisabled();
    }
    
    public static void disableBlend() {
        GlStateManager.blendState.field_179213_a.setDisabled();
    }
    
    public static void disableFog() {
        GlStateManager.fogState.field_179049_a.setDisabled();
    }
    
    public static void multMatrix(final FloatBuffer floatBuffer) {
        GL11.glMultMatrix(floatBuffer);
    }
    
    public static void disablePolygonOffset() {
        GlStateManager.polygonOffsetState.field_179044_a.setDisabled();
    }
    
    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }
    
    public static void color(final float n, final float n2, final float n3) {
        color(n, n2, n3, 1.0f);
    }
    
    public static void enableNormalize() {
        GlStateManager.normalizeState.setEnabled();
    }
    
    public static void bindCurrentTexture() {
        GL11.glBindTexture(3553, GlStateManager.textureState[0].textureName);
    }
    
    private static TexGenCoord texGenCoord(final TexGen texGen) {
        switch (GlStateManager$1.field_179175_a[texGen.ordinal()]) {
            case 1: {
                return GlStateManager.texGenState.field_179064_a;
            }
            case 2: {
                return GlStateManager.texGenState.field_179062_b;
            }
            case 3: {
                return GlStateManager.texGenState.field_179063_c;
            }
            case 4: {
                return GlStateManager.texGenState.field_179061_d;
            }
            default: {
                return GlStateManager.texGenState.field_179064_a;
            }
        }
    }
    
    public static void setActiveTexture(final int activeTexture) {
        if (0 != activeTexture - OpenGlHelper.defaultTexUnit) {
            GlStateManager.activeTextureUnit = activeTexture - OpenGlHelper.defaultTexUnit;
            OpenGlHelper.setActiveTexture(activeTexture);
        }
    }
    
    public static void pushMatrix() {
        GL11.glPushMatrix();
    }
    
    public static void setFogEnd(final float field_179046_e) {
        if (field_179046_e != GlStateManager.fogState.field_179046_e) {
            GL11.glFogf(2916, GlStateManager.fogState.field_179046_e = field_179046_e);
        }
    }
    
    public static void shadeModel(final int activeShadeModel) {
        if (activeShadeModel != 7425) {
            GL11.glShadeModel(GlStateManager.activeShadeModel = activeShadeModel);
        }
    }
    
    public static void func_179105_a(final TexGen texGen, final int n, final FloatBuffer floatBuffer) {
        GL11.glTexGen(texGenCoord(texGen).field_179065_b, n, floatBuffer);
    }
    
    public static void setFogStart(final float field_179045_d) {
        if (field_179045_d != GlStateManager.fogState.field_179045_d) {
            GL11.glFogf(2915, GlStateManager.fogState.field_179045_d = field_179045_d);
        }
    }
    
    public static void bindTexture(final int textureName) {
        if (textureName != GlStateManager.textureState[0].textureName) {
            GL11.glBindTexture(3553, GlStateManager.textureState[0].textureName = textureName);
        }
    }
    
    public static void popAttrib() {
        GL11.glPopAttrib();
    }
    
    public static void tryBlendFuncSeparate(final int srcFactor, final int dstFactor, final int srcFactorAlpha, final int dstFactorAlpha) {
        if (srcFactor != GlStateManager.blendState.srcFactor || dstFactor != GlStateManager.blendState.dstFactor || srcFactorAlpha != GlStateManager.blendState.srcFactorAlpha || dstFactorAlpha != GlStateManager.blendState.dstFactorAlpha) {
            OpenGlHelper.glBlendFunc(GlStateManager.blendState.srcFactor = srcFactor, GlStateManager.blendState.dstFactor = dstFactor, GlStateManager.blendState.srcFactorAlpha = srcFactorAlpha, GlStateManager.blendState.dstFactorAlpha = dstFactorAlpha);
        }
    }
    
    public static void colorMask(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        if (red != GlStateManager.colorMaskState.red || green != GlStateManager.colorMaskState.green || blue != GlStateManager.colorMaskState.blue || alpha != GlStateManager.colorMaskState.alpha) {
            GL11.glColorMask(GlStateManager.colorMaskState.red = red, GlStateManager.colorMaskState.green = green, GlStateManager.colorMaskState.blue = blue, GlStateManager.colorMaskState.alpha = alpha);
        }
    }
    
    public static void disableLighting() {
        GlStateManager.lightingState.setDisabled();
    }
    
    public static void disableLight(final int n) {
        GlStateManager.lightState[n].setDisabled();
    }
    
    public static void disableRescaleNormal() {
        GlStateManager.rescaleNormalState.setDisabled();
    }
    
    public static void alphaFunc(final int func, final float ref) {
        if (func != GlStateManager.alphaState.func || ref != GlStateManager.alphaState.ref) {
            GL11.glAlphaFunc(GlStateManager.alphaState.func = func, GlStateManager.alphaState.ref = ref);
        }
    }
    
    public static void depthFunc(final int depthFunc) {
        if (depthFunc != GlStateManager.depthState.depthFunc) {
            GL11.glDepthFunc(GlStateManager.depthState.depthFunc = depthFunc);
        }
    }
    
    public static void callList(final int n) {
        GL11.glCallList(n);
    }
    
    public static void enableTexGenCoord(final TexGen texGen) {
        texGenCoord(texGen).field_179067_a.setEnabled();
    }
    
    public static void enableTexture2D() {
        GlStateManager.textureState[0].texture2DState.setEnabled();
    }
    
    public static void disableAlpha() {
        GlStateManager.alphaState.field_179208_a.setDisabled();
    }
    
    public static void ortho(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        GL11.glOrtho(n, n2, n3, n4, n5, n6);
    }
    
    public static void translate(final double n, final double n2, final double n3) {
        GL11.glTranslated(n, n2, n3);
    }
    
    public static void clearColor(final float red, final float green, final float blue, final float alpha) {
        if (red != GlStateManager.clearState.field_179203_b.red || green != GlStateManager.clearState.field_179203_b.green || blue != GlStateManager.clearState.field_179203_b.blue || alpha != GlStateManager.clearState.field_179203_b.alpha) {
            GL11.glClearColor(GlStateManager.clearState.field_179203_b.red = red, GlStateManager.clearState.field_179203_b.green = green, GlStateManager.clearState.field_179203_b.blue = blue, GlStateManager.clearState.field_179203_b.alpha = alpha);
        }
    }
    
    public static void enableCull() {
        GlStateManager.cullState.field_179054_a.setEnabled();
    }
    
    public static void enableBlend() {
        GlStateManager.blendState.field_179213_a.setEnabled();
    }
    
    public static void matrixMode(final int n) {
        GL11.glMatrixMode(n);
    }
    
    public static void clearDepth(final double field_179205_a) {
        if (field_179205_a != GlStateManager.clearState.field_179205_a) {
            GL11.glClearDepth(GlStateManager.clearState.field_179205_a = field_179205_a);
        }
    }
    
    public static void disableTexGenCoord(final TexGen texGen) {
        texGenCoord(texGen).field_179067_a.setDisabled();
    }
    
    public static void colorLogicOp(final int field_179196_b) {
        if (field_179196_b != GlStateManager.colorLogicState.field_179196_b) {
            GL11.glLogicOp(GlStateManager.colorLogicState.field_179196_b = field_179196_b);
        }
    }
    
    public static void enableRescaleNormal() {
        GlStateManager.rescaleNormalState.setEnabled();
    }
    
    public static void enablePolygonOffset() {
        GlStateManager.polygonOffsetState.field_179044_a.setEnabled();
    }
    
    public static void enableLight(final int n) {
        GlStateManager.lightState[n].setEnabled();
    }
    
    public static void clear(final int n) {
        if (GlStateManager.clearEnabled) {
            GL11.glClear(n);
        }
    }
    
    public static void enableColorMaterial() {
        GlStateManager.colorMaterialState.field_179191_a.setEnabled();
    }
    
    public static void enableColorLogic() {
        GlStateManager.colorLogicState.field_179197_a.setEnabled();
    }
    
    public static void enableAlpha() {
        GlStateManager.alphaState.field_179208_a.setEnabled();
    }
    
    public static void disableDepth() {
        GlStateManager.depthState.depthTest.setDisabled();
    }
    
    public static void setFogDensity(final float field_179048_c) {
        if (field_179048_c != GlStateManager.fogState.field_179048_c) {
            GL11.glFogf(2914, GlStateManager.fogState.field_179048_c = field_179048_c);
        }
    }
    
    public static void cullFace(final int field_179053_b) {
        if (field_179053_b != GlStateManager.cullState.field_179053_b) {
            GL11.glCullFace(GlStateManager.cullState.field_179053_b = field_179053_b);
        }
    }
    
    public static void disableTexture2D() {
        GlStateManager.textureState[0].texture2DState.setDisabled();
    }
    
    public static void disableNormalize() {
        GlStateManager.normalizeState.setDisabled();
    }
    
    public static void colorMaterial(final int field_179189_b, final int field_179190_c) {
        if (field_179189_b != GlStateManager.colorMaterialState.field_179189_b || field_179190_c != GlStateManager.colorMaterialState.field_179190_c) {
            GL11.glColorMaterial(GlStateManager.colorMaterialState.field_179189_b = field_179189_b, GlStateManager.colorMaterialState.field_179190_c = field_179190_c);
        }
    }
    
    public static void disableCull() {
        GlStateManager.cullState.field_179054_a.setDisabled();
    }
    
    public static void resetColor() {
        final Color colorState = GlStateManager.colorState;
        final Color colorState2 = GlStateManager.colorState;
        final Color colorState3 = GlStateManager.colorState;
        final Color colorState4 = GlStateManager.colorState;
        final float n = -1.0f;
        colorState4.alpha = n;
        colorState3.blue = n;
        colorState2.green = n;
        colorState.red = n;
    }
    
    public static void scale(final float n, final float n2, final float n3) {
        GL11.glScalef(n, n2, n3);
    }
    
    public static int generateTexture() {
        return GL11.glGenTextures();
    }
    
    public static void blendFunc(final int srcFactor, final int dstFactor) {
        if (srcFactor != GlStateManager.blendState.srcFactor || dstFactor != GlStateManager.blendState.dstFactor) {
            GL11.glBlendFunc(GlStateManager.blendState.srcFactor = srcFactor, GlStateManager.blendState.dstFactor = dstFactor);
        }
    }
    
    public static void pushAttrib() {
        GL11.glPushAttrib(8256);
    }
    
    public static void popMatrix() {
        GL11.glPopMatrix();
    }
    
    public static void viewport(final int n, final int n2, final int n3, final int n4) {
        GL11.glViewport(n, n2, n3, n4);
    }
    
    public static void translate(final float n, final float n2, final float n3) {
        GL11.glTranslatef(n, n2, n3);
    }
    
    public static void rotate(final float n, final float n2, final float n3, final float n4) {
        GL11.glRotatef(n, n2, n3, n4);
    }
    
    public static void deleteTexture(final int n) {
        GL11.glDeleteTextures(n);
        final TextureState[] textureState = GlStateManager.textureState;
        while (0 < textureState.length) {
            final TextureState textureState2 = textureState[0];
            if (textureState2.textureName == n) {
                textureState2.textureName = -1;
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public static void enableDepth() {
        GlStateManager.depthState.depthTest.setEnabled();
    }
    
    public static void doPolygonOffset(final float field_179043_c, final float field_179041_d) {
        if (field_179043_c != GlStateManager.polygonOffsetState.field_179043_c || field_179041_d != GlStateManager.polygonOffsetState.field_179041_d) {
            GL11.glPolygonOffset(GlStateManager.polygonOffsetState.field_179043_c = field_179043_c, GlStateManager.polygonOffsetState.field_179041_d = field_179041_d);
        }
    }
    
    public static void enableFog() {
        GlStateManager.fogState.field_179049_a.setEnabled();
    }
    
    public static void getFloat(final int n, final FloatBuffer floatBuffer) {
        GL11.glGetFloat(n, floatBuffer);
    }
    
    public static void depthMask(final boolean maskEnabled) {
        if (maskEnabled != GlStateManager.depthState.maskEnabled) {
            GL11.glDepthMask(GlStateManager.depthState.maskEnabled = maskEnabled);
        }
    }
    
    static {
        GlStateManager.alphaState = new AlphaState(null);
        GlStateManager.lightingState = new BooleanState(2896);
        GlStateManager.lightState = new BooleanState[8];
        GlStateManager.colorMaterialState = new ColorMaterialState(null);
        GlStateManager.blendState = new BlendState(null);
        GlStateManager.depthState = new DepthState(null);
        GlStateManager.fogState = new FogState(null);
        GlStateManager.cullState = new CullState(null);
        GlStateManager.polygonOffsetState = new PolygonOffsetState(null);
        GlStateManager.colorLogicState = new ColorLogicState(null);
        GlStateManager.texGenState = new TexGenState(null);
        GlStateManager.clearState = new ClearState(null);
        GlStateManager.stencilState = new StencilState(null);
        GlStateManager.normalizeState = new BooleanState(2977);
        GlStateManager.textureState = new TextureState[8];
        GlStateManager.rescaleNormalState = new BooleanState(32826);
        GlStateManager.colorMaskState = new ColorMask(null);
        GlStateManager.colorState = new Color();
        GlStateManager.clearEnabled = true;
        while (true) {
            GlStateManager.lightState[0] = new BooleanState(16384);
            int n = 0;
            ++n;
        }
    }
    
    public static void color(final float red, final float green, final float blue, final float alpha) {
        if (red != GlStateManager.colorState.red || green != GlStateManager.colorState.green || blue != GlStateManager.colorState.blue || alpha != GlStateManager.colorState.alpha) {
            GL11.glColor4f(GlStateManager.colorState.red = red, GlStateManager.colorState.green = green, GlStateManager.colorState.blue = blue, GlStateManager.colorState.alpha = alpha);
        }
    }
    
    static class ColorMaterialState
    {
        public int field_179189_b;
        public int field_179190_c;
        private static final String __OBFID = "CL_00002549";
        public BooleanState field_179191_a;
        
        ColorMaterialState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private ColorMaterialState() {
            this.field_179191_a = new BooleanState(2903);
            this.field_179189_b = 1032;
            this.field_179190_c = 5634;
        }
    }
    
    static class BooleanState
    {
        private static final String __OBFID = "CL_00002554";
        private boolean currentState;
        private final int capability;
        
        public void setDisabled() {
            this.setState(false);
        }
        
        public BooleanState(final int capability) {
            this.currentState = false;
            this.capability = capability;
        }
        
        public void setEnabled() {
            this.setState(true);
        }
        
        public void setState(final boolean currentState) {
            if (currentState != this.currentState) {
                this.currentState = currentState;
                if (currentState) {
                    GL11.glEnable(this.capability);
                }
                else {
                    GL11.glDisable(this.capability);
                }
            }
        }
    }
    
    static final class GlStateManager$1
    {
        private static final String __OBFID = "CL_00002557";
        
        static {
            (GlStateManager$1.field_179175_a = new int[TexGen.values().length])[TexGen.S.ordinal()] = 1;
            GlStateManager$1.field_179175_a[TexGen.T.ordinal()] = 2;
            GlStateManager$1.field_179175_a[TexGen.R.ordinal()] = 3;
            GlStateManager$1.field_179175_a[TexGen.Q.ordinal()] = 4;
        }
    }
    
    public enum TexGen
    {
        T("T", 1, "T", 1), 
        R("R", 2, "R", 2);
        
        private static final TexGen[] $VALUES$;
        
        Q("Q", 3, "Q", 3);
        
        private static final TexGen[] $VALUES;
        
        S("S", 0, "S", 0);
        
        private static final String __OBFID = "CL_00002542";
        
        private TexGen(final String s, final int n, final String s2, final int n2) {
        }
        
        static {
            $VALUES$ = new TexGen[] { TexGen.S, TexGen.T, TexGen.R, TexGen.Q };
            $VALUES = new TexGen[] { TexGen.S, TexGen.T, TexGen.R, TexGen.Q };
        }
    }
    
    static class TextureState
    {
        public int textureName;
        private static final String __OBFID = "CL_00002539";
        public BooleanState texture2DState;
        
        private TextureState() {
            this.texture2DState = new BooleanState(3553);
            this.textureName = 0;
        }
        
        TextureState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class PolygonOffsetState
    {
        public BooleanState field_179044_a;
        public float field_179043_c;
        private static final String __OBFID = "CL_00002545";
        public float field_179041_d;
        public BooleanState field_179042_b;
        
        PolygonOffsetState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private PolygonOffsetState() {
            this.field_179044_a = new BooleanState(32823);
            this.field_179042_b = new BooleanState(10754);
            this.field_179043_c = 0.0f;
            this.field_179041_d = 0.0f;
        }
    }
    
    static class FogState
    {
        private static final String __OBFID = "CL_00002546";
        public float field_179045_d;
        public int field_179047_b;
        public float field_179048_c;
        public BooleanState field_179049_a;
        public float field_179046_e;
        
        private FogState() {
            this.field_179049_a = new BooleanState(2912);
            this.field_179047_b = 2048;
            this.field_179048_c = 1.0f;
            this.field_179045_d = 0.0f;
            this.field_179046_e = 1.0f;
        }
        
        FogState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class CullState
    {
        private static final String __OBFID = "CL_00002548";
        public int field_179053_b;
        public BooleanState field_179054_a;
        
        private CullState() {
            this.field_179054_a = new BooleanState(2884);
            this.field_179053_b = 1029;
        }
        
        CullState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class TexGenCoord
    {
        public BooleanState field_179067_a;
        public int field_179066_c;
        private static final String __OBFID = "CL_00002541";
        public int field_179065_b;
        
        public TexGenCoord(final int field_179065_b, final int n) {
            this.field_179066_c = -1;
            this.field_179065_b = field_179065_b;
            this.field_179067_a = new BooleanState(n);
        }
    }
    
    static class ClearState
    {
        public Color field_179203_b;
        public int field_179204_c;
        public double field_179205_a;
        private static final String __OBFID = "CL_00002553";
        
        private ClearState() {
            this.field_179205_a = 1.0;
            this.field_179203_b = new Color(0.0f, 0.0f, 0.0f, 0.0f);
            this.field_179204_c = 0;
        }
        
        ClearState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class Color
    {
        public float blue;
        public float green;
        public float alpha;
        private static final String __OBFID = "CL_00002552";
        public float red;
        
        public Color(final float red, final float green, final float blue, final float alpha) {
            this.red = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
            this.alpha = 1.0f;
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
        
        public Color() {
            this.red = 1.0f;
            this.green = 1.0f;
            this.blue = 1.0f;
            this.alpha = 1.0f;
        }
    }
    
    static class StencilFunc
    {
        public int field_179080_c;
        public int field_179079_b;
        private static final String __OBFID = "CL_00002544";
        public int field_179081_a;
        
        private StencilFunc() {
            this.field_179081_a = 519;
            this.field_179079_b = 0;
            this.field_179080_c = -1;
        }
        
        StencilFunc(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class StencilState
    {
        public int field_179076_b;
        public StencilFunc field_179078_a;
        public int field_179077_c;
        private static final String __OBFID = "CL_00002543";
        public int field_179075_e;
        public int field_179074_d;
        
        StencilState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private StencilState() {
            this.field_179078_a = new StencilFunc(null);
            this.field_179076_b = -1;
            this.field_179077_c = 7680;
            this.field_179074_d = 7680;
            this.field_179075_e = 7680;
        }
    }
    
    static class DepthState
    {
        private static final String __OBFID = "CL_00002547";
        public int depthFunc;
        public BooleanState depthTest;
        public boolean maskEnabled;
        
        private DepthState() {
            this.depthTest = new BooleanState(2929);
            this.maskEnabled = true;
            this.depthFunc = 513;
        }
        
        DepthState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class BlendState
    {
        public int srcFactor;
        public int dstFactor;
        public int dstFactorAlpha;
        public BooleanState field_179213_a;
        private static final String __OBFID = "CL_00002555";
        public int srcFactorAlpha;
        
        BlendState(final GlStateManager$1 glStateManager$1) {
            this();
        }
        
        private BlendState() {
            this.field_179213_a = new BooleanState(3042);
            this.srcFactor = 1;
            this.dstFactor = 0;
            this.srcFactorAlpha = 1;
            this.dstFactorAlpha = 0;
        }
    }
    
    static class TexGenState
    {
        public TexGenCoord field_179062_b;
        private static final String __OBFID = "CL_00002540";
        public TexGenCoord field_179064_a;
        public TexGenCoord field_179061_d;
        public TexGenCoord field_179063_c;
        
        private TexGenState() {
            this.field_179064_a = new TexGenCoord(8192, 3168);
            this.field_179062_b = new TexGenCoord(8193, 3169);
            this.field_179063_c = new TexGenCoord(8194, 3170);
            this.field_179061_d = new TexGenCoord(8195, 3171);
        }
        
        TexGenState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class ColorMask
    {
        public boolean green;
        private static final String __OBFID = "CL_00002550";
        public boolean red;
        public boolean blue;
        public boolean alpha;
        
        private ColorMask() {
            this.red = true;
            this.green = true;
            this.blue = true;
            this.alpha = true;
        }
        
        ColorMask(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class ColorLogicState
    {
        public BooleanState field_179197_a;
        public int field_179196_b;
        private static final String __OBFID = "CL_00002551";
        
        private ColorLogicState() {
            this.field_179197_a = new BooleanState(3058);
            this.field_179196_b = 5379;
        }
        
        ColorLogicState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
    
    static class AlphaState
    {
        public BooleanState field_179208_a;
        public int func;
        public float ref;
        private static final String __OBFID = "CL_00002556";
        
        private AlphaState() {
            this.field_179208_a = new BooleanState(3008);
            this.func = 519;
            this.ref = -1.0f;
        }
        
        AlphaState(final GlStateManager$1 glStateManager$1) {
            this();
        }
    }
}
