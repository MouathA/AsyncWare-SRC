package net.minecraft.realms;

import net.minecraft.client.renderer.vertex.*;
import java.util.*;

public class RealmsVertexFormat
{
    private VertexFormat v;
    
    public RealmsVertexFormat(final VertexFormat v) {
        this.v = v;
    }
    
    public int getVertexSize() {
        return this.v.getNextOffset();
    }
    
    public RealmsVertexFormat addElement(final RealmsVertexFormatElement realmsVertexFormatElement) {
        return this.from(this.v.func_181721_a(realmsVertexFormatElement.getVertexFormatElement()));
    }
    
    @Override
    public boolean equals(final Object o) {
        return this.v.equals(o);
    }
    
    public boolean hasColor() {
        return this.v.hasColor();
    }
    
    public boolean hasNormal() {
        return this.v.hasNormal();
    }
    
    @Override
    public String toString() {
        return this.v.toString();
    }
    
    public int getIntegerSize() {
        return this.v.func_181719_f();
    }
    
    public RealmsVertexFormat from(final VertexFormat v) {
        this.v = v;
        return this;
    }
    
    public RealmsVertexFormatElement getElement(final int n) {
        return new RealmsVertexFormatElement(this.v.getElement(n));
    }
    
    public int getNormalOffset() {
        return this.v.getNormalOffset();
    }
    
    public List getElements() {
        final ArrayList<RealmsVertexFormatElement> list = new ArrayList<RealmsVertexFormatElement>();
        final Iterator<VertexFormatElement> iterator = this.v.getElements().iterator();
        while (iterator.hasNext()) {
            list.add(new RealmsVertexFormatElement(iterator.next()));
        }
        return list;
    }
    
    public VertexFormat getVertexFormat() {
        return this.v;
    }
    
    public int getUvOffset(final int n) {
        return this.v.getUvOffsetById(n);
    }
    
    public int getOffset(final int n) {
        return this.v.func_181720_d(n);
    }
    
    public boolean hasUv(final int n) {
        return this.v.hasUvOffset(n);
    }
    
    public int getElementCount() {
        return this.v.getElementCount();
    }
    
    public void clear() {
        this.v.clear();
    }
    
    @Override
    public int hashCode() {
        return this.v.hashCode();
    }
    
    public int getColorOffset() {
        return this.v.getColorOffset();
    }
}
