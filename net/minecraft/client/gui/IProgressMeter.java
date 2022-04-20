package net.minecraft.client.gui;

public interface IProgressMeter
{
    void doneLoading();
    
    default static {
        IProgressMeter.lanSearchStates = new String[] { "oooooo", "Oooooo", "oOoooo", "ooOooo", "oooOoo", "ooooOo", "oooooO" };
    }
}
