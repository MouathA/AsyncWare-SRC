package net.minecraft.client.gui;

import net.minecraft.client.resources.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;

public class GuiSlider extends GuiButton
{
    private final float max;
    private final float min;
    private float sliderPosition;
    private String name;
    private FormatHelper formatHelper;
    private final GuiPageButtonList.GuiResponder responder;
    public boolean isMouseDown;
    
    public GuiSlider(final GuiPageButtonList.GuiResponder responder, final int n, final int n2, final int n3, final String name, final float min, final float max, final float n4, final FormatHelper formatHelper) {
        super(n, n2, n3, 150, 20, "");
        this.sliderPosition = 1.0f;
        this.name = name;
        this.min = min;
        this.max = max;
        this.sliderPosition = (n4 - min) / (max - min);
        this.formatHelper = formatHelper;
        this.responder = responder;
        this.displayString = this.getDisplayString();
    }
    
    @Override
    public void mouseReleased(final int n, final int n2) {
        this.isMouseDown = false;
    }
    
    @Override
    protected int getHoverState(final boolean b) {
        return 0;
    }
    
    public float func_175217_d() {
        return this.sliderPosition;
    }
    
    public float func_175220_c() {
        return this.min + (this.max - this.min) * this.sliderPosition;
    }
    
    private String getDisplayString() {
        return (this.formatHelper == null) ? (I18n.format(this.name, new Object[0]) + ": " + this.func_175220_c()) : this.formatHelper.getText(this.id, I18n.format(this.name, new Object[0]), this.func_175220_c());
    }
    
    @Override
    public boolean mousePressed(final Minecraft minecraft, final int n, final int n2) {
        if (super.mousePressed(minecraft, n, n2)) {
            this.sliderPosition = (n - (this.xPosition + 4)) / (float)(this.width - 8);
            if (this.sliderPosition < 0.0f) {
                this.sliderPosition = 0.0f;
            }
            if (this.sliderPosition > 1.0f) {
                this.sliderPosition = 1.0f;
            }
            this.displayString = this.getDisplayString();
            this.responder.onTick(this.id, this.func_175220_c());
            return this.isMouseDown = true;
        }
        return false;
    }
    
    @Override
    protected void mouseDragged(final Minecraft minecraft, final int n, final int n2) {
        if (this.visible) {
            if (this.isMouseDown) {
                this.sliderPosition = (n - (this.xPosition + 4)) / (float)(this.width - 8);
                if (this.sliderPosition < 0.0f) {
                    this.sliderPosition = 0.0f;
                }
                if (this.sliderPosition > 1.0f) {
                    this.sliderPosition = 1.0f;
                }
                this.displayString = this.getDisplayString();
                this.responder.onTick(this.id, this.func_175220_c());
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderPosition * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderPosition * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    public void func_175219_a(final float sliderPosition) {
        this.sliderPosition = sliderPosition;
        this.displayString = this.getDisplayString();
        this.responder.onTick(this.id, this.func_175220_c());
    }
    
    public void func_175218_a(final float n, final boolean b) {
        this.sliderPosition = (n - this.min) / (this.max - this.min);
        this.displayString = this.getDisplayString();
        if (b) {
            this.responder.onTick(this.id, this.func_175220_c());
        }
    }
    
    public interface FormatHelper
    {
        String getText(final int p0, final String p1, final float p2);
    }
}
