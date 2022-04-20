package net.minecraft.realms;

import net.minecraft.client.renderer.*;

public class Tezzelator
{
    public static Tessellator t;
    public static final Tezzelator instance;
    
    public void endVertex() {
        Tezzelator.t.getWorldRenderer().endVertex();
    }
    
    public Tezzelator vertex(final double n, final double n2, final double n3) {
        Tezzelator.t.getWorldRenderer().pos(n, n2, n3);
        return this;
    }
    
    public void color(final float n, final float n2, final float n3, final float n4) {
        Tezzelator.t.getWorldRenderer().color(n, n2, n3, n4);
    }
    
    public void begin(final int n, final RealmsVertexFormat realmsVertexFormat) {
        Tezzelator.t.getWorldRenderer().begin(n, realmsVertexFormat.getVertexFormat());
    }
    
    public void tex2(final short n, final short n2) {
        Tezzelator.t.getWorldRenderer().lightmap(n, n2);
    }
    
    public RealmsBufferBuilder color(final int n, final int n2, final int n3, final int n4) {
        return new RealmsBufferBuilder(Tezzelator.t.getWorldRenderer().color(n, n2, n3, n4));
    }
    
    public void normal(final float n, final float n2, final float n3) {
        Tezzelator.t.getWorldRenderer().normal(n, n2, n3);
    }
    
    public void end() {
        Tezzelator.t.draw();
    }
    
    public void offset(final double n, final double n2, final double n3) {
        Tezzelator.t.getWorldRenderer().setTranslation(n, n2, n3);
    }
    
    static {
        Tezzelator.t = Tessellator.getInstance();
        instance = new Tezzelator();
    }
    
    public Tezzelator tex(final double n, final double n2) {
        Tezzelator.t.getWorldRenderer().tex(n, n2);
        return this;
    }
}
