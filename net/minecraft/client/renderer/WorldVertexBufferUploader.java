package net.minecraft.client.renderer;

import optfine.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import java.nio.*;
import java.util.*;

public class WorldVertexBufferUploader
{
    private static final String __OBFID = "CL_00002567";
    
    public void func_181679_a(final WorldRenderer worldRenderer) {
        if (worldRenderer.getVertexCount() > 0) {
            final VertexFormat vertexFormat = worldRenderer.getVertexFormat();
            final int nextOffset = vertexFormat.getNextOffset();
            final ByteBuffer byteBuffer = worldRenderer.getByteBuffer();
            final List elements = vertexFormat.getElements();
            final boolean exists = Reflector.ForgeVertexFormatElementEnumUseage_preDraw.exists();
            final boolean exists2 = Reflector.ForgeVertexFormatElementEnumUseage_postDraw.exists();
            int n = 0;
            while (0 < elements.size()) {
                final VertexFormatElement vertexFormatElement = elements.get(0);
                final VertexFormatElement.EnumUsage usage = vertexFormatElement.getUsage();
                if (exists) {
                    Reflector.callVoid(usage, Reflector.ForgeVertexFormatElementEnumUseage_preDraw, vertexFormatElement, nextOffset, byteBuffer);
                }
                else {
                    final int glConstant = vertexFormatElement.getType().getGlConstant();
                    final int index = vertexFormatElement.getIndex();
                    byteBuffer.position(vertexFormat.func_181720_d(0));
                    switch (WorldVertexBufferUploader$1.field_178958_a[usage.ordinal()]) {
                        case 1: {
                            GL11.glVertexPointer(vertexFormatElement.getElementCount(), glConstant, nextOffset, byteBuffer);
                            GL11.glEnableClientState(32884);
                            break;
                        }
                        case 2: {
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + index);
                            GL11.glTexCoordPointer(vertexFormatElement.getElementCount(), glConstant, nextOffset, byteBuffer);
                            GL11.glEnableClientState(32888);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            break;
                        }
                        case 3: {
                            GL11.glColorPointer(vertexFormatElement.getElementCount(), glConstant, nextOffset, byteBuffer);
                            GL11.glEnableClientState(32886);
                            break;
                        }
                        case 4: {
                            GL11.glNormalPointer(glConstant, nextOffset, byteBuffer);
                            GL11.glEnableClientState(32885);
                            break;
                        }
                    }
                }
                ++n;
            }
            if (worldRenderer.isMultiTexture()) {
                worldRenderer.drawMultiTexture();
            }
            else {
                GL11.glDrawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.getVertexCount());
            }
            while (0 < elements.size()) {
                final VertexFormatElement vertexFormatElement2 = elements.get(0);
                final VertexFormatElement.EnumUsage usage2 = vertexFormatElement2.getUsage();
                if (exists2) {
                    Reflector.callVoid(usage2, Reflector.ForgeVertexFormatElementEnumUseage_postDraw, vertexFormatElement2, nextOffset, byteBuffer);
                }
                else {
                    final int index2 = vertexFormatElement2.getIndex();
                    switch (WorldVertexBufferUploader$1.field_178958_a[usage2.ordinal()]) {
                        case 1: {
                            GL11.glDisableClientState(32884);
                            break;
                        }
                        case 2: {
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + index2);
                            GL11.glDisableClientState(32888);
                            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                            break;
                        }
                        case 3: {
                            GL11.glDisableClientState(32886);
                            break;
                        }
                        case 4: {
                            GL11.glDisableClientState(32885);
                            break;
                        }
                    }
                }
                ++n;
            }
        }
        worldRenderer.reset();
    }
    
    static final class WorldVertexBufferUploader$1
    {
        private static final String __OBFID = "CL_00002566";
        
        static {
            (WorldVertexBufferUploader$1.field_178958_a = new int[VertexFormatElement.EnumUsage.values().length])[VertexFormatElement.EnumUsage.POSITION.ordinal()] = 1;
            WorldVertexBufferUploader$1.field_178958_a[VertexFormatElement.EnumUsage.UV.ordinal()] = 2;
            WorldVertexBufferUploader$1.field_178958_a[VertexFormatElement.EnumUsage.COLOR.ordinal()] = 3;
            WorldVertexBufferUploader$1.field_178958_a[VertexFormatElement.EnumUsage.NORMAL.ordinal()] = 4;
        }
    }
}
