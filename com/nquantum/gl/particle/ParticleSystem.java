package com.nquantum.gl.particle;

import java.awt.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.*;
import com.nquantum.util.math.*;
import java.util.*;

public class ParticleSystem
{
    private int dist;
    private boolean rainbow;
    private boolean mouse;
    private List particleList;
    private static final float SPEED = 0.1f;
    
    private void drawLine(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        GL11.glColor4f(n5, n6, n7, n8);
        GL11.glLineWidth(0.5f);
        GL11.glBegin(1);
        GL11.glVertex2f(n, n2);
        GL11.glVertex2f(n3, n4);
        GL11.glEnd();
    }
    
    public ParticleSystem(final int n, final boolean mouse, final boolean rainbow, final int dist) {
        this.particleList = new ArrayList();
        this.addParticles(n);
        this.mouse = mouse;
        this.dist = dist;
        this.rainbow = rainbow;
    }
    
    public void addParticles(final int n) {
        while (0 < n) {
            this.particleList.add(Particle.generateParticle());
            int n2 = 0;
            ++n2;
        }
    }
    
    public void render() {
        for (final Particle particle : this.particleList) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, particle.getAlpha() / 255.0f);
            GL11.glPointSize(particle.getSize());
            GL11.glBegin(0);
            GL11.glVertex2f(particle.getX(), particle.getY());
            GL11.glEnd();
            if (this.mouse) {
                Color color = null;
                if (this.rainbow) {
                    color = new Color(255, 55, 55);
                }
                final float n = (float)MathUtil.distance(particle.getX(), particle.getY(), (float)Mouse.getX(), (float)(Display.getHeight() - Mouse.getY()));
                if (n >= this.dist) {
                    continue;
                }
                this.drawLine(particle.getX(), particle.getY(), (float)Mouse.getX(), (float)(Display.getHeight() - Mouse.getY()), this.rainbow ? (color.getRed() / 255.0f) : 1.0f, this.rainbow ? (color.getGreen() / 255.0f) : 1.0f, this.rainbow ? (color.getBlue() / 255.0f) : 1.0f, Math.min(1.0f, Math.min(1.0f, 1.0f - n / this.dist)));
            }
            else {
                float n2 = 0.0f;
                Particle particle2 = null;
                for (final Particle particle3 : this.particleList) {
                    final float distanceTo = particle.getDistanceTo(particle3);
                    if (distanceTo <= this.dist && (MathUtil.distance((float)Mouse.getX(), (float)(Display.getHeight() - Mouse.getY()), particle.getX(), particle.getY()) <= this.dist || MathUtil.distance((float)Mouse.getX(), (float)(Display.getHeight() - Mouse.getY()), particle3.getX(), particle3.getY()) <= this.dist) && (n2 <= 0.0f || distanceTo <= n2)) {
                        n2 = distanceTo;
                        particle2 = particle3;
                    }
                }
                if (particle2 == null) {
                    continue;
                }
                Color color2 = null;
                if (this.rainbow) {
                    color2 = new Color(22, 22, 22);
                }
                this.drawLine(particle.getX(), particle.getY(), particle2.getX(), particle2.getY(), this.rainbow ? (color2.getRed() / 255.0f) : 1.0f, this.rainbow ? (color2.getGreen() / 255.0f) : 1.0f, this.rainbow ? (color2.getBlue() / 255.0f) : 1.0f, Math.min(1.0f, Math.min(1.0f, 1.0f - n2 / this.dist)));
            }
        }
    }
    
    public void tick(final int n) {
        if (Mouse.isButtonDown(0)) {
            this.addParticles(1);
        }
        final Iterator<Particle> iterator = this.particleList.iterator();
        while (iterator.hasNext()) {
            iterator.next().tick(n, 0.1f);
        }
    }
}
