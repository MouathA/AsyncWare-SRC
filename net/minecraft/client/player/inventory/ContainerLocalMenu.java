package net.minecraft.client.player.inventory;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class ContainerLocalMenu extends InventoryBasic implements ILockableContainer
{
    private Map field_174895_b;
    private String guiID;
    
    @Override
    public LockCode getLockCode() {
        return LockCode.EMPTY_CODE;
    }
    
    @Override
    public void setLockCode(final LockCode lockCode) {
    }
    
    @Override
    public String getGuiID() {
        return this.guiID;
    }
    
    public ContainerLocalMenu(final String guiID, final IChatComponent chatComponent, final int n) {
        super(chatComponent, n);
        this.field_174895_b = Maps.newHashMap();
        this.guiID = guiID;
    }
    
    @Override
    public int getField(final int n) {
        return this.field_174895_b.containsKey(n) ? this.field_174895_b.get(n) : 0;
    }
    
    @Override
    public void setField(final int n, final int n2) {
        this.field_174895_b.put(n, n2);
    }
    
    @Override
    public boolean isLocked() {
        return false;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public int getFieldCount() {
        return this.field_174895_b.size();
    }
}
