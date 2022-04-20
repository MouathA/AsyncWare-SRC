package net.minecraft.client.player.inventory;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class LocalBlockIntercommunication implements IInteractionObject
{
    private String guiID;
    private IChatComponent displayName;
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean hasCustomName() {
        return true;
    }
    
    public LocalBlockIntercommunication(final String guiID, final IChatComponent displayName) {
        this.guiID = guiID;
        this.displayName = displayName;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        return this.displayName;
    }
    
    @Override
    public String getName() {
        return this.displayName.getUnformattedText();
    }
    
    @Override
    public String getGuiID() {
        return this.guiID;
    }
}
