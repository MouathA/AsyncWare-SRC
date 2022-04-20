package net.minecraft.client.gui;

import net.minecraft.util.*;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate
{
    private String field_146589_f;
    private int progress;
    private boolean doneWorking;
    private String field_146591_a;
    
    @Override
    public void setDoneWorking() {
        this.doneWorking = true;
    }
    
    @Override
    public void drawScreen(final int n, final int n2, final float n3) {
        if (this.doneWorking) {
            if (!this.mc.func_181540_al()) {
                this.mc.displayGuiScreen(null);
            }
        }
        else {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, this.field_146591_a, GuiScreenWorking.width / 2, 70, 16777215);
            this.drawCenteredString(this.fontRendererObj, this.field_146589_f + " " + this.progress + "%", GuiScreenWorking.width / 2, 90, 16777215);
            super.drawScreen(n, n2, n3);
        }
    }
    
    @Override
    public void displayLoadingString(final String field_146589_f) {
        this.field_146589_f = field_146589_f;
        this.setLoadingProgress(0);
    }
    
    @Override
    public void displaySavingString(final String s) {
        this.resetProgressAndMessage(s);
    }
    
    public GuiScreenWorking() {
        this.field_146591_a = "";
        this.field_146589_f = "";
    }
    
    @Override
    public void resetProgressAndMessage(final String field_146591_a) {
        this.field_146591_a = field_146591_a;
        this.displayLoadingString("Working...");
    }
    
    @Override
    public void setLoadingProgress(final int progress) {
        this.progress = progress;
    }
}
