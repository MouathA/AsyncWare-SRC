package com.nquantum.util.render.comp;

import java.awt.*;
import java.util.*;
import net.minecraft.client.gui.*;

public class Rectangle extends GuiScreen
{
    protected int mouseStartY;
    protected double x;
    protected Color color;
    protected ArrayList children;
    protected boolean dragging;
    protected double width;
    protected boolean childrenHidden;
    protected double height;
    protected boolean hidden;
    protected boolean draggable;
    protected int mouseStartButton;
    protected double y;
    protected double[] dragOffset;
    protected int mouseStartX;
    protected boolean mouseDown;
    
    public Rectangle setParent(final Rectangle rectangle) {
        rectangle.addChild(this);
        return this;
    }
    
    public void onClick() {
    }
    
    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }
    
    public Rectangle setDraggable(final boolean draggable) {
        this.draggable = draggable;
        return this;
    }
    
    public Rectangle() {
        this.mouseStartButton = -1;
        this.children = new ArrayList();
        this.x = 0.0;
        this.y = 0.0;
        this.width = 0.0;
        this.height = 0.0;
        this.color = new Color(0, 0, 0, 0);
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public void mouseReleased(final int p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/nquantum/util/render/comp/Rectangle.children:Ljava/util/ArrayList;
        //     4: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //     7: astore          4
        //     9: aload           4
        //    11: invokeinterface java/util/Iterator.hasNext:()Z
        //    16: ifeq            42
        //    19: aload           4
        //    21: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    26: checkcast       Lcom/nquantum/util/render/comp/Rectangle;
        //    29: astore          5
        //    31: aload           5
        //    33: iload_1        
        //    34: iload_2        
        //    35: iload_3        
        //    36: invokevirtual   com/nquantum/util/render/comp/Rectangle.mouseReleased:(III)V
        //    39: goto            9
        //    42: aload_0        
        //    43: iconst_0       
        //    44: putfield        com/nquantum/util/render/comp/Rectangle.mouseDown:Z
        //    47: aload_0        
        //    48: getfield        com/nquantum/util/render/comp/Rectangle.dragging:Z
        //    51: ifne            64
        //    54: aload_0        
        //    55: iload_1        
        //    56: iload_2        
        //    57: ifle            64
        //    60: aload_0        
        //    61: invokevirtual   com/nquantum/util/render/comp/Rectangle.onClick:()V
        //    64: aload_0        
        //    65: iconst_0       
        //    66: putfield        com/nquantum/util/render/comp/Rectangle.dragging:Z
        //    69: aload_0        
        //    70: iconst_m1      
        //    71: putfield        com/nquantum/util/render/comp/Rectangle.mouseStartX:I
        //    74: aload_0        
        //    75: iconst_m1      
        //    76: putfield        com/nquantum/util/render/comp/Rectangle.mouseStartY:I
        //    79: aload_0        
        //    80: iconst_m1      
        //    81: putfield        com/nquantum/util/render/comp/Rectangle.mouseStartButton:I
        //    84: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0064 (coming from #0057).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public ArrayList getChildren() {
        return this.children;
    }
    
    public double[] getCenter() {
        return new double[] { this.x + this.width / 2.0, this.y + this.height / 2.0 };
    }
    
    public void setChildren(final ArrayList children) {
        this.children = children;
        double y = this.y + this.height;
        for (final Rectangle rectangle : children) {
            rectangle.setX(this.x);
            rectangle.setY(y);
            y += rectangle.getHeight();
            rectangle.setHidden(this.childrenHidden);
            rectangle.setDraggable(false);
        }
    }
    
    public Rectangle(final double x, final double y, final double width, final double height, final Color color) {
        this.mouseStartButton = -1;
        this.children = new ArrayList();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    public boolean isDraggable() {
        return this.draggable;
    }
    
    public void draw(final int n, final int n2) {
        if (!this.hidden) {
            final Iterator<Rectangle> iterator = this.children.iterator();
            while (iterator.hasNext()) {
                iterator.next().draw(n, n2);
            }
            if (this.dragging) {
                this.setX(n - this.mouseStartX);
                this.setY(n2 - this.mouseStartY);
            }
            Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.color.getRGB());
            final double[] relativeCoords = this.getRelativeCoords(n, n2);
            if (this.draggable && this.mouseStartButton == 0 && Math.pow(this.mouseStartX - relativeCoords[0], 2.0) + Math.pow(this.mouseStartY - relativeCoords[1], 2.0) > 2.0) {
                this.dragging = true;
            }
        }
    }
    
    public double getY() {
        return this.y;
    }
    
    public void mouseClicked(final int n, final int n2, final int mouseStartButton) {
        final Iterator<Rectangle> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            iterator.next().mouseClicked(n, n2, mouseStartButton);
        }
        this.mouseDown = true;
        if (n2 > 0) {
            final double[] relativeCoords = this.getRelativeCoords(n, n2);
            this.mouseStartX = (int)relativeCoords[0];
            this.mouseStartY = (int)relativeCoords[1];
            this.mouseStartButton = mouseStartButton;
        }
    }
    
    public void addChild(final Rectangle rectangle) {
        double y = this.y + this.height;
        final Iterator<Rectangle> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            y += iterator.next().getHeight();
        }
        this.children.add(rectangle);
        rectangle.setY(y);
        rectangle.setX(this.x);
        rectangle.setHidden(this.childrenHidden);
        rectangle.setDraggable(false);
    }
    
    public void setWidth(final double width) {
        this.width = width;
    }
    
    public void setColor(final Color color) {
        this.color = color;
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setY(final double y) {
        this.y = y;
        final Iterator<Rectangle> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            iterator.next().setY(y + this.height);
        }
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double n) {
        this.x = n;
        final Iterator<Rectangle> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            iterator.next().setX(n);
        }
    }
    
    public void setChildrenHidden(final boolean b) {
        this.childrenHidden = b;
        final Iterator<Rectangle> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            iterator.next().setHidden(b);
        }
    }
    
    public void setHeight(final double height) {
        this.height = height;
        double y = this.y + height;
        for (final Rectangle rectangle : this.children) {
            rectangle.setY(y);
            y += rectangle.getHeight();
        }
    }
    
    public double getWidth() {
        return this.width;
    }
    
    protected double[] getRelativeCoords(final int n, final int n2) {
        return new double[] { n - this.x, n2 - this.y };
    }
}
