package net.minecraft.client.renderer;

import optfine.*;
import net.minecraft.client.*;
import java.nio.*;
import net.minecraft.client.settings.*;
import oshi.*;
import oshi.hardware.*;
import org.lwjgl.opengl.*;

public class OpenGlHelper
{
    public static int GL_FRAMEBUFFER;
    public static boolean framebufferSupported;
    public static int GL_RENDERBUFFER;
    public static boolean field_181063_b;
    public static int GL_TEXTURE2;
    public static int GL_PRIMARY_COLOR;
    public static boolean vboSupported;
    public static int GL_DEPTH_ATTACHMENT;
    public static int GL_FB_INCOMPLETE_READ_BUFFER;
    private static String logText;
    private static final String __OBFID = "CL_00001179";
    public static boolean extBlendFuncSeparate;
    public static int lightmapTexUnit;
    private static boolean arbTextureEnvCombine;
    public static int GL_COLOR_ATTACHMENT0;
    public static int GL_CONSTANT;
    public static boolean field_181062_Q;
    private static boolean arbMultitexture;
    public static int GL_OPERAND0_RGB;
    public static int GL_OPERAND0_ALPHA;
    public static boolean openGL21;
    private static boolean shadersAvailable;
    public static int GL_COMBINE;
    private static boolean openGL14;
    private static String field_183030_aa;
    public static int GL_SOURCE0_RGB;
    public static int GL_FRAGMENT_SHADER;
    public static int GL_COMBINE_RGB;
    public static int GL_FB_INCOMPLETE_MISS_ATTACH;
    public static int GL_FB_INCOMPLETE_ATTACHMENT;
    private static int framebufferType;
    public static int GL_COMPILE_STATUS;
    public static int defaultTexUnit;
    public static boolean shadersSupported;
    public static int GL_SOURCE1_ALPHA;
    private static boolean arbShaders;
    public static int GL_ARRAY_BUFFER;
    public static int GL_COMBINE_ALPHA;
    public static int GL_OPERAND2_ALPHA;
    public static int GL_SOURCE1_RGB;
    public static int GL_SOURCE2_ALPHA;
    public static int GL_SOURCE0_ALPHA;
    public static int GL_VERTEX_SHADER;
    public static int GL_OPERAND2_RGB;
    private static boolean arbVbo;
    public static int GL_INTERPOLATE;
    public static int GL_PREVIOUS;
    public static int GL_SOURCE2_RGB;
    public static int GL_LINK_STATUS;
    public static int GL_OPERAND1_RGB;
    public static int GL_STATIC_DRAW;
    public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
    public static int GL_FRAMEBUFFER_COMPLETE;
    public static boolean nvidia;
    public static int GL_OPERAND1_ALPHA;
    
    public static void glUniform2(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform2ARB(n, floatBuffer);
        }
        else {
            GL20.glUniform2(n, floatBuffer);
        }
    }
    
    static {
        OpenGlHelper.logText = "";
    }
    
    public static int glCreateShader(final int n) {
        return OpenGlHelper.arbShaders ? ARBShaderObjects.glCreateShaderObjectARB(n) : GL20.glCreateShader(n);
    }
    
    public static void glBindBuffer(final int n, final int n2) {
        if (OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glBindBufferARB(n, n2);
        }
        else {
            GL15.glBindBuffer(n, n2);
        }
    }
    
    public static void glBindRenderbuffer(final int n, final int n2) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glBindRenderbuffer(n, n2);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindRenderbuffer(n, n2);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindRenderbufferEXT(n, n2);
                    break;
                }
            }
        }
    }
    
    public static void glUniform2(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform2ARB(n, intBuffer);
        }
        else {
            GL20.glUniform2(n, intBuffer);
        }
    }
    
    public static void glUniform3(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform3ARB(n, intBuffer);
        }
        else {
            GL20.glUniform3(n, intBuffer);
        }
    }
    
    public static void glUniform1i(final int n, final int n2) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1iARB(n, n2);
        }
        else {
            GL20.glUniform1i(n, n2);
        }
    }
    
    public static void glBlendFunc(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.openGL14) {
            if (OpenGlHelper.extBlendFuncSeparate) {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT(n, n2, n3, n4);
            }
            else {
                GL14.glBlendFuncSeparate(n, n2, n3, n4);
            }
        }
        else {
            GL11.glBlendFunc(n, n2);
        }
    }
    
    public static String getLogText() {
        return OpenGlHelper.logText;
    }
    
    public static void glUniform1(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1ARB(n, intBuffer);
        }
        else {
            GL20.glUniform1(n, intBuffer);
        }
    }
    
    public static boolean useVbo() {
        return !Config.isMultiTexture() && (OpenGlHelper.vboSupported && Minecraft.getMinecraft().gameSettings.useVbo);
    }
    
    public static void glCompileShader(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glCompileShaderARB(n);
        }
        else {
            GL20.glCompileShader(n);
        }
    }
    
    public static boolean isFramebufferEnabled() {
        return !Config.isFastRender() && Config.getAntialiasingLevel() <= 0 && (OpenGlHelper.framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable);
    }
    
    public static void glDeleteBuffers(final int n) {
        if (OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glDeleteBuffersARB(n);
        }
        else {
            GL15.glDeleteBuffers(n);
        }
    }
    
    public static void glFramebufferTexture2D(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glFramebufferTexture2D(n, n2, n3, n4, n5);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferTexture2D(n, n2, n3, n4, n5);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferTexture2DEXT(n, n2, n3, n4, n5);
                    break;
                }
            }
        }
    }
    
    public static void glUniformMatrix3(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix3ARB(n, b, floatBuffer);
        }
        else {
            GL20.glUniformMatrix3(n, b, floatBuffer);
        }
    }
    
    public static boolean areShadersSupported() {
        return OpenGlHelper.shadersSupported;
    }
    
    public static void glUniformMatrix4(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix4ARB(n, b, floatBuffer);
        }
        else {
            GL20.glUniformMatrix4(n, b, floatBuffer);
        }
    }
    
    public static int glGenFramebuffers() {
        if (!OpenGlHelper.framebufferSupported) {
            return -1;
        }
        switch (OpenGlHelper.framebufferType) {
            case 0: {
                return GL30.glGenFramebuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenFramebuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenFramebuffersEXT();
            }
            default: {
                return -1;
            }
        }
    }
    
    public static void glLinkProgram(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glLinkProgramARB(n);
        }
        else {
            GL20.glLinkProgram(n);
        }
    }
    
    public static void glShaderSource(final int n, final ByteBuffer byteBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glShaderSourceARB(n, byteBuffer);
        }
        else {
            GL20.glShaderSource(n, byteBuffer);
        }
    }
    
    public static int glGetUniformLocation(final int n, final CharSequence charSequence) {
        return OpenGlHelper.arbShaders ? ARBShaderObjects.glGetUniformLocationARB(n, charSequence) : GL20.glGetUniformLocation(n, charSequence);
    }
    
    public static String glGetShaderInfoLog(final int n, final int n2) {
        return OpenGlHelper.arbShaders ? ARBShaderObjects.glGetInfoLogARB(n, n2) : GL20.glGetShaderInfoLog(n, n2);
    }
    
    public static void initializeTextures() {
        final ContextCapabilities capabilities = GLContext.getCapabilities();
        OpenGlHelper.arbMultitexture = (capabilities.GL_ARB_multitexture && !capabilities.OpenGL13);
        OpenGlHelper.arbTextureEnvCombine = (capabilities.GL_ARB_texture_env_combine && !capabilities.OpenGL13);
        if (OpenGlHelper.arbMultitexture) {
            OpenGlHelper.logText += "Using ARB_multitexture.\n";
            OpenGlHelper.defaultTexUnit = 33984;
            OpenGlHelper.lightmapTexUnit = 33985;
            OpenGlHelper.GL_TEXTURE2 = 33986;
        }
        else {
            OpenGlHelper.logText += "Using GL 1.3 multitexturing.\n";
            OpenGlHelper.defaultTexUnit = 33984;
            OpenGlHelper.lightmapTexUnit = 33985;
            OpenGlHelper.GL_TEXTURE2 = 33986;
        }
        if (OpenGlHelper.arbTextureEnvCombine) {
            OpenGlHelper.logText += "Using ARB_texture_env_combine.\n";
            OpenGlHelper.GL_COMBINE = 34160;
            OpenGlHelper.GL_INTERPOLATE = 34165;
            OpenGlHelper.GL_PRIMARY_COLOR = 34167;
            OpenGlHelper.GL_CONSTANT = 34166;
            OpenGlHelper.GL_PREVIOUS = 34168;
            OpenGlHelper.GL_COMBINE_RGB = 34161;
            OpenGlHelper.GL_SOURCE0_RGB = 34176;
            OpenGlHelper.GL_SOURCE1_RGB = 34177;
            OpenGlHelper.GL_SOURCE2_RGB = 34178;
            OpenGlHelper.GL_OPERAND0_RGB = 34192;
            OpenGlHelper.GL_OPERAND1_RGB = 34193;
            OpenGlHelper.GL_OPERAND2_RGB = 34194;
            OpenGlHelper.GL_COMBINE_ALPHA = 34162;
            OpenGlHelper.GL_SOURCE0_ALPHA = 34184;
            OpenGlHelper.GL_SOURCE1_ALPHA = 34185;
            OpenGlHelper.GL_SOURCE2_ALPHA = 34186;
            OpenGlHelper.GL_OPERAND0_ALPHA = 34200;
            OpenGlHelper.GL_OPERAND1_ALPHA = 34201;
            OpenGlHelper.GL_OPERAND2_ALPHA = 34202;
        }
        else {
            OpenGlHelper.logText += "Using GL 1.3 texture combiners.\n";
            OpenGlHelper.GL_COMBINE = 34160;
            OpenGlHelper.GL_INTERPOLATE = 34165;
            OpenGlHelper.GL_PRIMARY_COLOR = 34167;
            OpenGlHelper.GL_CONSTANT = 34166;
            OpenGlHelper.GL_PREVIOUS = 34168;
            OpenGlHelper.GL_COMBINE_RGB = 34161;
            OpenGlHelper.GL_SOURCE0_RGB = 34176;
            OpenGlHelper.GL_SOURCE1_RGB = 34177;
            OpenGlHelper.GL_SOURCE2_RGB = 34178;
            OpenGlHelper.GL_OPERAND0_RGB = 34192;
            OpenGlHelper.GL_OPERAND1_RGB = 34193;
            OpenGlHelper.GL_OPERAND2_RGB = 34194;
            OpenGlHelper.GL_COMBINE_ALPHA = 34162;
            OpenGlHelper.GL_SOURCE0_ALPHA = 34184;
            OpenGlHelper.GL_SOURCE1_ALPHA = 34185;
            OpenGlHelper.GL_SOURCE2_ALPHA = 34186;
            OpenGlHelper.GL_OPERAND0_ALPHA = 34200;
            OpenGlHelper.GL_OPERAND1_ALPHA = 34201;
            OpenGlHelper.GL_OPERAND2_ALPHA = 34202;
        }
        OpenGlHelper.extBlendFuncSeparate = (capabilities.GL_EXT_blend_func_separate && !capabilities.OpenGL14);
        OpenGlHelper.openGL14 = (capabilities.OpenGL14 || capabilities.GL_EXT_blend_func_separate);
        OpenGlHelper.framebufferSupported = (OpenGlHelper.openGL14 && (capabilities.GL_ARB_framebuffer_object || capabilities.GL_EXT_framebuffer_object || capabilities.OpenGL30));
        if (OpenGlHelper.framebufferSupported) {
            OpenGlHelper.logText += "Using framebuffer objects because ";
            if (capabilities.OpenGL30) {
                OpenGlHelper.logText += "OpenGL 3.0 is supported and separate blending is supported.\n";
                OpenGlHelper.framebufferType = 0;
                OpenGlHelper.GL_FRAMEBUFFER = 36160;
                OpenGlHelper.GL_RENDERBUFFER = 36161;
                OpenGlHelper.GL_COLOR_ATTACHMENT0 = 36064;
                OpenGlHelper.GL_DEPTH_ATTACHMENT = 36096;
                OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 36053;
                OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            }
            else if (capabilities.GL_ARB_framebuffer_object) {
                OpenGlHelper.logText += "ARB_framebuffer_object is supported and separate blending is supported.\n";
                OpenGlHelper.framebufferType = 1;
                OpenGlHelper.GL_FRAMEBUFFER = 36160;
                OpenGlHelper.GL_RENDERBUFFER = 36161;
                OpenGlHelper.GL_COLOR_ATTACHMENT0 = 36064;
                OpenGlHelper.GL_DEPTH_ATTACHMENT = 36096;
                OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 36053;
                OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            }
            else if (capabilities.GL_EXT_framebuffer_object) {
                OpenGlHelper.logText += "EXT_framebuffer_object is supported.\n";
                OpenGlHelper.framebufferType = 2;
                OpenGlHelper.GL_FRAMEBUFFER = 36160;
                OpenGlHelper.GL_RENDERBUFFER = 36161;
                OpenGlHelper.GL_COLOR_ATTACHMENT0 = 36064;
                OpenGlHelper.GL_DEPTH_ATTACHMENT = 36096;
                OpenGlHelper.GL_FRAMEBUFFER_COMPLETE = 36053;
                OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
                OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT = 36054;
                OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
                OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER = 36060;
            }
        }
        else {
            OpenGlHelper.logText += "Not using framebuffer objects because ";
            OpenGlHelper.logText = OpenGlHelper.logText + "OpenGL 1.4 is " + (capabilities.OpenGL14 ? "" : "not ") + "supported, ";
            OpenGlHelper.logText = OpenGlHelper.logText + "EXT_blend_func_separate is " + (capabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
            OpenGlHelper.logText = OpenGlHelper.logText + "OpenGL 3.0 is " + (capabilities.OpenGL30 ? "" : "not ") + "supported, ";
            OpenGlHelper.logText = OpenGlHelper.logText + "ARB_framebuffer_object is " + (capabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
            OpenGlHelper.logText = OpenGlHelper.logText + "EXT_framebuffer_object is " + (capabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
        }
        OpenGlHelper.openGL21 = capabilities.OpenGL21;
        OpenGlHelper.shadersAvailable = (OpenGlHelper.openGL21 || (capabilities.GL_ARB_vertex_shader && capabilities.GL_ARB_fragment_shader && capabilities.GL_ARB_shader_objects));
        OpenGlHelper.logText = OpenGlHelper.logText + "Shaders are " + (OpenGlHelper.shadersAvailable ? "" : "not ") + "available because ";
        if (OpenGlHelper.shadersAvailable) {
            if (capabilities.OpenGL21) {
                OpenGlHelper.logText += "OpenGL 2.1 is supported.\n";
                OpenGlHelper.arbShaders = false;
                OpenGlHelper.GL_LINK_STATUS = 35714;
                OpenGlHelper.GL_COMPILE_STATUS = 35713;
                OpenGlHelper.GL_VERTEX_SHADER = 35633;
                OpenGlHelper.GL_FRAGMENT_SHADER = 35632;
            }
            else {
                OpenGlHelper.logText += "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
                OpenGlHelper.arbShaders = true;
                OpenGlHelper.GL_LINK_STATUS = 35714;
                OpenGlHelper.GL_COMPILE_STATUS = 35713;
                OpenGlHelper.GL_VERTEX_SHADER = 35633;
                OpenGlHelper.GL_FRAGMENT_SHADER = 35632;
            }
        }
        else {
            OpenGlHelper.logText = OpenGlHelper.logText + "OpenGL 2.1 is " + (capabilities.OpenGL21 ? "" : "not ") + "supported, ";
            OpenGlHelper.logText = OpenGlHelper.logText + "ARB_shader_objects is " + (capabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
            OpenGlHelper.logText = OpenGlHelper.logText + "ARB_vertex_shader is " + (capabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
            OpenGlHelper.logText = OpenGlHelper.logText + "ARB_fragment_shader is " + (capabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
        }
        OpenGlHelper.shadersSupported = (OpenGlHelper.framebufferSupported && OpenGlHelper.shadersAvailable);
        final String lowerCase = GL11.glGetString(7936).toLowerCase();
        OpenGlHelper.nvidia = lowerCase.contains("nvidia");
        OpenGlHelper.arbVbo = (!capabilities.OpenGL15 && capabilities.GL_ARB_vertex_buffer_object);
        OpenGlHelper.vboSupported = (capabilities.OpenGL15 || OpenGlHelper.arbVbo);
        OpenGlHelper.logText = OpenGlHelper.logText + "VBOs are " + (OpenGlHelper.vboSupported ? "" : "not ") + "available because ";
        if (OpenGlHelper.vboSupported) {
            if (OpenGlHelper.arbVbo) {
                OpenGlHelper.logText += "ARB_vertex_buffer_object is supported.\n";
                OpenGlHelper.GL_STATIC_DRAW = 35044;
                OpenGlHelper.GL_ARRAY_BUFFER = 34962;
            }
            else {
                OpenGlHelper.logText += "OpenGL 1.5 is supported.\n";
                OpenGlHelper.GL_STATIC_DRAW = 35044;
                OpenGlHelper.GL_ARRAY_BUFFER = 34962;
            }
        }
        OpenGlHelper.field_181063_b = lowerCase.contains("ati");
        if (OpenGlHelper.field_181063_b) {
            if (OpenGlHelper.vboSupported) {
                OpenGlHelper.field_181062_Q = true;
            }
            else {
                GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0f);
            }
        }
        final Processor[] processors = new SystemInfo().getHardware().getProcessors();
        OpenGlHelper.field_183030_aa = String.format("%dx %s", processors.length, processors[0]).replaceAll("\\s+", " ");
    }
    
    public static void glUniform4(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform4ARB(n, floatBuffer);
        }
        else {
            GL20.glUniform4(n, floatBuffer);
        }
    }
    
    public static void glBindFramebuffer(final int n, final int n2) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glBindFramebuffer(n, n2);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindFramebuffer(n, n2);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindFramebufferEXT(n, n2);
                    break;
                }
            }
        }
    }
    
    public static int glGenBuffers() {
        return OpenGlHelper.arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
    }
    
    public static void glDeleteRenderbuffers(final int n) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glDeleteRenderbuffers(n);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteRenderbuffers(n);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteRenderbuffersEXT(n);
                    break;
                }
            }
        }
    }
    
    public static int glGenRenderbuffers() {
        if (!OpenGlHelper.framebufferSupported) {
            return -1;
        }
        switch (OpenGlHelper.framebufferType) {
            case 0: {
                return GL30.glGenRenderbuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenRenderbuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenRenderbuffersEXT();
            }
            default: {
                return -1;
            }
        }
    }
    
    public static void glUniform1(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform1ARB(n, floatBuffer);
        }
        else {
            GL20.glUniform1(n, floatBuffer);
        }
    }
    
    public static void glUniform4(final int n, final IntBuffer intBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform4ARB(n, intBuffer);
        }
        else {
            GL20.glUniform4(n, intBuffer);
        }
    }
    
    public static void setLightmapTextureCoords(final int n, final float lastBrightnessX, final float lastBrightnessY) {
        if (OpenGlHelper.arbMultitexture) {
            ARBMultitexture.glMultiTexCoord2fARB(n, lastBrightnessX, lastBrightnessY);
        }
        else {
            GL13.glMultiTexCoord2f(n, lastBrightnessX, lastBrightnessY);
        }
        if (n == OpenGlHelper.lightmapTexUnit) {
            OpenGlHelper.lastBrightnessX = lastBrightnessX;
            OpenGlHelper.lastBrightnessY = lastBrightnessY;
        }
    }
    
    public static String glGetProgramInfoLog(final int n, final int n2) {
        return OpenGlHelper.arbShaders ? ARBShaderObjects.glGetInfoLogARB(n, n2) : GL20.glGetProgramInfoLog(n, n2);
    }
    
    public static String func_183029_j() {
        return (OpenGlHelper.field_183030_aa == null) ? "<unknown>" : OpenGlHelper.field_183030_aa;
    }
    
    public static int glGetShaderi(final int n, final int n2) {
        return OpenGlHelper.arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(n, n2) : GL20.glGetShaderi(n, n2);
    }
    
    public static int glGetProgrami(final int n, final int n2) {
        return OpenGlHelper.arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(n, n2) : GL20.glGetProgrami(n, n2);
    }
    
    public static int glCheckFramebufferStatus(final int n) {
        if (!OpenGlHelper.framebufferSupported) {
            return -1;
        }
        switch (OpenGlHelper.framebufferType) {
            case 0: {
                return GL30.glCheckFramebufferStatus(n);
            }
            case 1: {
                return ARBFramebufferObject.glCheckFramebufferStatus(n);
            }
            case 2: {
                return EXTFramebufferObject.glCheckFramebufferStatusEXT(n);
            }
            default: {
                return -1;
            }
        }
    }
    
    public static void setClientActiveTexture(final int n) {
        if (OpenGlHelper.arbMultitexture) {
            ARBMultitexture.glClientActiveTextureARB(n);
        }
        else {
            GL13.glClientActiveTexture(n);
        }
    }
    
    public static void glDeleteProgram(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glDeleteObjectARB(n);
        }
        else {
            GL20.glDeleteProgram(n);
        }
    }
    
    public static void glUniform3(final int n, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniform3ARB(n, floatBuffer);
        }
        else {
            GL20.glUniform3(n, floatBuffer);
        }
    }
    
    public static int glCreateProgram() {
        return OpenGlHelper.arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
    }
    
    public static int glGetAttribLocation(final int n, final CharSequence charSequence) {
        return OpenGlHelper.arbShaders ? ARBVertexShader.glGetAttribLocationARB(n, charSequence) : GL20.glGetAttribLocation(n, charSequence);
    }
    
    public static void glBufferData(final int n, final ByteBuffer byteBuffer, final int n2) {
        if (OpenGlHelper.arbVbo) {
            ARBVertexBufferObject.glBufferDataARB(n, byteBuffer, n2);
        }
        else {
            GL15.glBufferData(n, byteBuffer, n2);
        }
    }
    
    public static void glUseProgram(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUseProgramObjectARB(n);
        }
        else {
            GL20.glUseProgram(n);
        }
    }
    
    public static void glFramebufferRenderbuffer(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glFramebufferRenderbuffer(n, n2, n3, n4);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferRenderbuffer(n, n2, n3, n4);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferRenderbufferEXT(n, n2, n3, n4);
                    break;
                }
            }
        }
    }
    
    public static void glRenderbufferStorage(final int n, final int n2, final int n3, final int n4) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glRenderbufferStorage(n, n2, n3, n4);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glRenderbufferStorage(n, n2, n3, n4);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glRenderbufferStorageEXT(n, n2, n3, n4);
                    break;
                }
            }
        }
    }
    
    public static void glUniformMatrix2(final int n, final boolean b, final FloatBuffer floatBuffer) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glUniformMatrix2ARB(n, b, floatBuffer);
        }
        else {
            GL20.glUniformMatrix2(n, b, floatBuffer);
        }
    }
    
    public static void setActiveTexture(final int n) {
        if (OpenGlHelper.arbMultitexture) {
            ARBMultitexture.glActiveTextureARB(n);
        }
        else {
            GL13.glActiveTexture(n);
        }
    }
    
    public static void glDeleteShader(final int n) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glDeleteObjectARB(n);
        }
        else {
            GL20.glDeleteShader(n);
        }
    }
    
    public static void glAttachShader(final int n, final int n2) {
        if (OpenGlHelper.arbShaders) {
            ARBShaderObjects.glAttachObjectARB(n, n2);
        }
        else {
            GL20.glAttachShader(n, n2);
        }
    }
    
    public static void glDeleteFramebuffers(final int n) {
        if (OpenGlHelper.framebufferSupported) {
            switch (OpenGlHelper.framebufferType) {
                case 0: {
                    GL30.glDeleteFramebuffers(n);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteFramebuffers(n);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteFramebuffersEXT(n);
                    break;
                }
            }
        }
    }
}
