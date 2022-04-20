package com.nquantum.gl;

import java.nio.charset.*;
import java.io.*;
import org.lwjgl.opengl.*;

public class GLSLSandboxShader
{
    private final int timeUniform;
    private final int mouseUniform;
    private final int programId;
    private final int resolutionUniform;
    
    private String readStreamToString(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final byte[] array = new byte[512];
        int read;
        while ((read = inputStream.read(array, 0, array.length)) != -1) {
            byteArrayOutputStream.write(array, 0, read);
        }
        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }
    
    private int createShader(final InputStream inputStream, final int n) throws IOException {
        final int glCreateShader = GL20.glCreateShader(n);
        GL20.glShaderSource(glCreateShader, (CharSequence)this.readStreamToString(inputStream));
        GL20.glCompileShader(glCreateShader);
        if (GL20.glGetShaderi(glCreateShader, 35713) == 0) {
            System.err.println(GL20.glGetShaderInfoLog(glCreateShader, GL20.glGetShaderi(glCreateShader, 35716)));
            throw new IllegalStateException("Failed to compile shader");
        }
        return glCreateShader;
    }
    
    public void useShader(final int n, final int n2, final float n3, final float n4, final float n5) {
        GL20.glUseProgram(this.programId);
        GL20.glUniform2f(this.resolutionUniform, (float)n, (float)n2);
        GL20.glUniform2f(this.mouseUniform, n3 / n, 1.0f - n4 / n2);
        GL20.glUniform1f(this.timeUniform, n5);
    }
    
    public GLSLSandboxShader(final String s) throws IOException {
        final int glCreateProgram = GL20.glCreateProgram();
        GL20.glAttachShader(glCreateProgram, this.createShader(GLSLSandboxShader.class.getResourceAsStream("/passthrough.fsh"), 35633));
        GL20.glAttachShader(glCreateProgram, this.createShader(GLSLSandboxShader.class.getResourceAsStream(s), 35632));
        GL20.glLinkProgram(glCreateProgram);
        if (GL20.glGetProgrami(glCreateProgram, 35714) == 0) {
            System.err.println(GL20.glGetProgramInfoLog(glCreateProgram, GL20.glGetProgrami(glCreateProgram, 35716)));
            throw new IllegalStateException("Shader failed to link");
        }
        GL20.glUseProgram(this.programId = glCreateProgram);
        this.timeUniform = GL20.glGetUniformLocation(glCreateProgram, (CharSequence)"time");
        this.mouseUniform = GL20.glGetUniformLocation(glCreateProgram, (CharSequence)"mouse");
        this.resolutionUniform = GL20.glGetUniformLocation(glCreateProgram, (CharSequence)"resolution");
        GL20.glUseProgram(0);
    }
}
