package com.nquantum.gl.particle;

import org.lwjgl.util.vector.*;
import java.util.*;
import com.nquantum.util.math.*;
import org.lwjgl.opengl.*;

public class Particle
{
    private Vector2f pos;
    private float size;
    private static final Random random;
    private Vector2f velocity;
    private float alpha;
    
    public float getDistanceTo(final float n, final float n2) {
        return (float)MathUtil.distance(this.getX(), this.getY(), n, n2);
    }
    
    public float getY() {
        return this.pos.getY();
    }
    
    public float getX() {
        return this.pos.getX();
    }
    
    public static Particle generateParticle() {
        return new Particle(new Vector2f((float)(Math.random() * 2.0 - 1.0), (float)(Math.random() * 2.0 - 1.0)), (float)Particle.random.nextInt(Display.getWidth()), (float)Particle.random.nextInt(Display.getHeight()), (float)(Math.random() * 4.0) + 1.0f);
    }
    
    public void setVelocity(final Vector2f velocity) {
        this.velocity = velocity;
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    public void setY(final float y) {
        this.pos.setY(y);
    }
    
    public float getSize() {
        return this.size;
    }
    
    public void setX(final float x) {
        this.pos.setX(x);
    }
    
    public float getDistanceTo(final Particle particle) {
        return this.getDistanceTo(particle.getX(), particle.getY());
    }
    
    static {
        random = new Random();
    }
    
    public Vector2f getVelocity() {
        return this.velocity;
    }
    
    public void tick(final int n, final float n2) {
        final Vector2f pos = this.pos;
        pos.x += this.velocity.getX() * n * n2;
        final Vector2f pos2 = this.pos;
        pos2.y += this.velocity.getY() * n * n2;
        if (this.alpha < 255.0f) {
            this.alpha += 0.05f * n;
        }
        if (this.pos.getX() > Display.getWidth()) {
            this.pos.setX(0.0f);
        }
        if (this.pos.getX() < 0.0f) {
            this.pos.setX((float)Display.getWidth());
        }
        if (this.pos.getY() > Display.getHeight()) {
            this.pos.setY(0.0f);
        }
        if (this.pos.getY() < 0.0f) {
            this.pos.setY((float)Display.getHeight());
        }
    }
    
    public void setSize(final float size) {
        this.size = size;
    }
    
    public Particle(final Vector2f velocity, final float n, final float n2, final float size) {
        this.velocity = velocity;
        this.pos = new Vector2f(n, n2);
        this.size = size;
    }
}
