package net.minecraft.client.shader;

import java.nio.*;
import net.minecraft.client.renderer.*;
import org.apache.logging.log4j.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.*;

public class ShaderUniform
{
    private final int uniformType;
    private final int uniformCount;
    private final FloatBuffer uniformFloatBuffer;
    private int uniformLocation;
    private final ShaderManager shaderManager;
    private boolean dirty;
    private final String shaderName;
    private final IntBuffer uniformIntBuffer;
    private static final Logger logger;
    
    public void set(final float[] array) {
        if (array.length < this.uniformCount) {
            ShaderUniform.logger.warn("Uniform.set called with a too-small value array (expected " + this.uniformCount + ", got " + array.length + "). Ignoring.");
        }
        else {
            this.uniformFloatBuffer.position(0);
            this.uniformFloatBuffer.put(array);
            this.uniformFloatBuffer.position(0);
            this.markDirty();
        }
    }
    
    public void set(final float n, final float n2, final float n3, final float n4) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(n);
        this.uniformFloatBuffer.put(n2);
        this.uniformFloatBuffer.put(n3);
        this.uniformFloatBuffer.put(n4);
        this.uniformFloatBuffer.flip();
        this.markDirty();
    }
    
    public void set(final float n) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(0, n);
        this.markDirty();
    }
    
    private void uploadFloat() {
        switch (this.uniformType) {
            case 4: {
                OpenGlHelper.glUniform1(this.uniformLocation, this.uniformFloatBuffer);
                break;
            }
            case 5: {
                OpenGlHelper.glUniform2(this.uniformLocation, this.uniformFloatBuffer);
                break;
            }
            case 6: {
                OpenGlHelper.glUniform3(this.uniformLocation, this.uniformFloatBuffer);
                break;
            }
            case 7: {
                OpenGlHelper.glUniform4(this.uniformLocation, this.uniformFloatBuffer);
                break;
            }
            default: {
                ShaderUniform.logger.warn("Uniform.upload called, but count value (" + this.uniformCount + ") is not in the range of 1 to 4. Ignoring.");
                break;
            }
        }
    }
    
    public void set(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10, final float n11, final float n12, final float n13, final float n14, final float n15, final float n16) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(0, n);
        this.uniformFloatBuffer.put(1, n2);
        this.uniformFloatBuffer.put(2, n3);
        this.uniformFloatBuffer.put(3, n4);
        this.uniformFloatBuffer.put(4, n5);
        this.uniformFloatBuffer.put(5, n6);
        this.uniformFloatBuffer.put(6, n7);
        this.uniformFloatBuffer.put(7, n8);
        this.uniformFloatBuffer.put(8, n9);
        this.uniformFloatBuffer.put(9, n10);
        this.uniformFloatBuffer.put(10, n11);
        this.uniformFloatBuffer.put(11, n12);
        this.uniformFloatBuffer.put(12, n13);
        this.uniformFloatBuffer.put(13, n14);
        this.uniformFloatBuffer.put(14, n15);
        this.uniformFloatBuffer.put(15, n16);
        this.markDirty();
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    private void uploadInt() {
        switch (this.uniformType) {
            case 0: {
                OpenGlHelper.glUniform1(this.uniformLocation, this.uniformIntBuffer);
                break;
            }
            case 1: {
                OpenGlHelper.glUniform2(this.uniformLocation, this.uniformIntBuffer);
                break;
            }
            case 2: {
                OpenGlHelper.glUniform3(this.uniformLocation, this.uniformIntBuffer);
                break;
            }
            case 3: {
                OpenGlHelper.glUniform4(this.uniformLocation, this.uniformIntBuffer);
                break;
            }
            default: {
                ShaderUniform.logger.warn("Uniform.upload called, but count value (" + this.uniformCount + ") is  not in the range of 1 to 4. Ignoring.");
                break;
            }
        }
    }
    
    private void markDirty() {
        this.dirty = true;
        if (this.shaderManager != null) {
            this.shaderManager.markDirty();
        }
    }
    
    private void uploadFloatMatrix() {
        switch (this.uniformType) {
            case 8: {
                OpenGlHelper.glUniformMatrix2(this.uniformLocation, true, this.uniformFloatBuffer);
                break;
            }
            case 9: {
                OpenGlHelper.glUniformMatrix3(this.uniformLocation, true, this.uniformFloatBuffer);
                break;
            }
            case 10: {
                OpenGlHelper.glUniformMatrix4(this.uniformLocation, true, this.uniformFloatBuffer);
                break;
            }
        }
    }
    
    public void func_148092_b(final float n, final float n2, final float n3, final float n4) {
        this.uniformFloatBuffer.position(0);
        if (this.uniformType >= 4) {
            this.uniformFloatBuffer.put(0, n);
        }
        if (this.uniformType >= 5) {
            this.uniformFloatBuffer.put(1, n2);
        }
        if (this.uniformType >= 6) {
            this.uniformFloatBuffer.put(2, n3);
        }
        if (this.uniformType >= 7) {
            this.uniformFloatBuffer.put(3, n4);
        }
        this.markDirty();
    }
    
    public String getShaderName() {
        return this.shaderName;
    }
    
    public void set(final float n, final float n2, final float n3) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(0, n);
        this.uniformFloatBuffer.put(1, n2);
        this.uniformFloatBuffer.put(2, n3);
        this.markDirty();
    }
    
    public void set(final int n, final int n2, final int n3, final int n4) {
        this.uniformIntBuffer.position(0);
        if (this.uniformType >= 0) {
            this.uniformIntBuffer.put(0, n);
        }
        if (this.uniformType >= 1) {
            this.uniformIntBuffer.put(1, n2);
        }
        if (this.uniformType >= 2) {
            this.uniformIntBuffer.put(2, n3);
        }
        if (this.uniformType >= 3) {
            this.uniformIntBuffer.put(3, n4);
        }
        this.markDirty();
    }
    
    public void setUniformLocation(final int uniformLocation) {
        this.uniformLocation = uniformLocation;
    }
    
    public static int parseType(final String s) {
        if (!s.equals("int")) {
            if (!s.equals("float")) {
                if (s.startsWith("matrix")) {
                    if (!s.endsWith("2x2")) {
                        if (!s.endsWith("3x3")) {
                            if (s.endsWith("4x4")) {}
                        }
                    }
                }
            }
        }
        return 10;
    }
    
    public void set(final Matrix4f matrix4f) {
        this.set(matrix4f.m00, matrix4f.m01, matrix4f.m02, matrix4f.m03, matrix4f.m10, matrix4f.m11, matrix4f.m12, matrix4f.m13, matrix4f.m20, matrix4f.m21, matrix4f.m22, matrix4f.m23, matrix4f.m30, matrix4f.m31, matrix4f.m32, matrix4f.m33);
    }
    
    public ShaderUniform(final String shaderName, final int uniformType, final int uniformCount, final ShaderManager shaderManager) {
        this.shaderName = shaderName;
        this.uniformCount = uniformCount;
        this.uniformType = uniformType;
        this.shaderManager = shaderManager;
        if (uniformType <= 3) {
            this.uniformIntBuffer = BufferUtils.createIntBuffer(uniformCount);
            this.uniformFloatBuffer = null;
        }
        else {
            this.uniformIntBuffer = null;
            this.uniformFloatBuffer = BufferUtils.createFloatBuffer(uniformCount);
        }
        this.uniformLocation = -1;
        this.markDirty();
    }
    
    public void upload() {
        this.dirty;
        this.dirty = false;
        if (this.uniformType <= 3) {
            this.uploadInt();
        }
        else if (this.uniformType <= 7) {
            this.uploadFloat();
        }
        else {
            if (this.uniformType > 10) {
                ShaderUniform.logger.warn("Uniform.upload called, but type value (" + this.uniformType + ") is not a valid type. Ignoring.");
                return;
            }
            this.uploadFloatMatrix();
        }
    }
    
    public void set(final float n, final float n2) {
        this.uniformFloatBuffer.position(0);
        this.uniformFloatBuffer.put(0, n);
        this.uniformFloatBuffer.put(1, n2);
        this.markDirty();
    }
}
