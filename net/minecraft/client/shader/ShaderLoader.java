package net.minecraft.client.shader;

import net.minecraft.client.resources.*;
import net.minecraft.util.*;
import org.lwjgl.*;
import net.minecraft.client.renderer.*;
import org.apache.commons.lang3.*;
import net.minecraft.client.util.*;
import java.nio.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.*;
import com.google.common.collect.*;

public class ShaderLoader
{
    private int shader;
    private final ShaderType shaderType;
    private int shaderAttachCount;
    private final String shaderFilename;
    
    private ShaderLoader(final ShaderType shaderType, final int shader, final String shaderFilename) {
        this.shaderAttachCount = 0;
        this.shaderType = shaderType;
        this.shader = shader;
        this.shaderFilename = shaderFilename;
    }
    
    public static ShaderLoader loadShader(final IResourceManager resourceManager, final ShaderType shaderType, final String s) throws IOException {
        ShaderLoader shaderLoader = shaderType.getLoadedShaders().get(s);
        if (shaderLoader == null) {
            final ResourceLocation resourceLocation = new ResourceLocation("shaders/program/" + s + shaderType.getShaderExtension());
            final byte[] byteArray = toByteArray(new BufferedInputStream(resourceManager.getResource(resourceLocation).getInputStream()));
            final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(byteArray.length);
            byteBuffer.put(byteArray);
            byteBuffer.position(0);
            final int glCreateShader = OpenGlHelper.glCreateShader(shaderType.getShaderMode());
            OpenGlHelper.glShaderSource(glCreateShader, byteBuffer);
            OpenGlHelper.glCompileShader(glCreateShader);
            if (OpenGlHelper.glGetShaderi(glCreateShader, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
                final JsonException ex = new JsonException("Couldn't compile " + shaderType.getShaderName() + " program: " + StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(glCreateShader, 32768)));
                ex.func_151381_b(resourceLocation.getResourcePath());
                throw ex;
            }
            shaderLoader = new ShaderLoader(shaderType, glCreateShader, s);
            shaderType.getLoadedShaders().put(s, shaderLoader);
        }
        return shaderLoader;
    }
    
    public void deleteShader(final ShaderManager shaderManager) {
        --this.shaderAttachCount;
        if (this.shaderAttachCount <= 0) {
            OpenGlHelper.glDeleteShader(this.shader);
            this.shaderType.getLoadedShaders().remove(this.shaderFilename);
        }
    }
    
    public String getShaderFilename() {
        return this.shaderFilename;
    }
    
    public void attachShader(final ShaderManager shaderManager) {
        ++this.shaderAttachCount;
        OpenGlHelper.glAttachShader(shaderManager.getProgram(), this.shader);
    }
    
    protected static byte[] toByteArray(final BufferedInputStream bufferedInputStream) throws IOException {
        final byte[] byteArray = IOUtils.toByteArray((InputStream)bufferedInputStream);
        bufferedInputStream.close();
        return byteArray;
    }
    
    public enum ShaderType
    {
        FRAGMENT("FRAGMENT", 1, "fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);
        
        private final String shaderName;
        private static final ShaderType[] $VALUES;
        private final String shaderExtension;
        
        VERTEX("VERTEX", 0, "vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER);
        
        private final Map loadedShaders;
        private final int shaderMode;
        
        protected Map getLoadedShaders() {
            return this.loadedShaders;
        }
        
        static {
            $VALUES = new ShaderType[] { ShaderType.VERTEX, ShaderType.FRAGMENT };
        }
        
        protected int getShaderMode() {
            return this.shaderMode;
        }
        
        private ShaderType(final String s, final int n, final String shaderName, final String shaderExtension, final int shaderMode) {
            this.loadedShaders = Maps.newHashMap();
            this.shaderName = shaderName;
            this.shaderExtension = shaderExtension;
            this.shaderMode = shaderMode;
        }
        
        public String getShaderName() {
            return this.shaderName;
        }
        
        protected String getShaderExtension() {
            return this.shaderExtension;
        }
    }
}
