package net.minecraft.client.shader;

import org.apache.logging.log4j.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.util.*;
import java.io.*;

public class ShaderLinkHelper
{
    private static ShaderLinkHelper staticShaderLinkHelper;
    private static final Logger logger;
    
    public static ShaderLinkHelper getStaticShaderLinkHelper() {
        return ShaderLinkHelper.staticShaderLinkHelper;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public static void setNewStaticShaderLinkHelper() {
        ShaderLinkHelper.staticShaderLinkHelper = new ShaderLinkHelper();
    }
    
    public int createProgram() throws JsonException {
        final int glCreateProgram = OpenGlHelper.glCreateProgram();
        if (glCreateProgram <= 0) {
            throw new JsonException("Could not create shader program (returned program ID " + glCreateProgram + ")");
        }
        return glCreateProgram;
    }
    
    public void deleteShader(final ShaderManager shaderManager) {
        shaderManager.getFragmentShaderLoader().deleteShader(shaderManager);
        shaderManager.getVertexShaderLoader().deleteShader(shaderManager);
        OpenGlHelper.glDeleteProgram(shaderManager.getProgram());
    }
    
    public void linkProgram(final ShaderManager shaderManager) throws IOException {
        shaderManager.getFragmentShaderLoader().attachShader(shaderManager);
        shaderManager.getVertexShaderLoader().attachShader(shaderManager);
        OpenGlHelper.glLinkProgram(shaderManager.getProgram());
        if (OpenGlHelper.glGetProgrami(shaderManager.getProgram(), OpenGlHelper.GL_LINK_STATUS) == 0) {
            ShaderLinkHelper.logger.warn("Error encountered when linking program containing VS " + shaderManager.getVertexShaderLoader().getShaderFilename() + " and FS " + shaderManager.getFragmentShaderLoader().getShaderFilename() + ". Log output:");
            ShaderLinkHelper.logger.warn(OpenGlHelper.glGetProgramInfoLog(shaderManager.getProgram(), 32768));
        }
    }
}
