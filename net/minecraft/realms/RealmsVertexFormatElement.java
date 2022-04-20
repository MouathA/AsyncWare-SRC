package net.minecraft.realms;

import net.minecraft.client.renderer.vertex.*;

public class RealmsVertexFormatElement
{
    private VertexFormatElement v;
    
    public int getIndex() {
        return this.v.getIndex();
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.v.equals(o);
    }
    
    public int getCount() {
        return this.v.getElementCount();
    }
    
    public VertexFormatElement getVertexFormatElement() {
        return this.v;
    }
    
    public RealmsVertexFormatElement(final VertexFormatElement v) {
        this.v = v;
    }
    
    @Override
    public int hashCode() {
        return this.v.hashCode();
    }
    
    @Override
    public String toString() {
        return this.v.toString();
    }
    
    public int getByteSize() {
        return this.v.getSize();
    }
    
    public boolean isPosition() {
        return this.v.isPositionElement();
    }
}
