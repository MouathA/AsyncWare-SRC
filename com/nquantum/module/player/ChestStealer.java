package com.nquantum.module.player;

import com.nquantum.util.time.*;
import com.nquantum.module.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import com.nquantum.event.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import com.nquantum.event.impl.*;
import net.minecraft.tileentity.*;
import java.awt.*;
import com.nquantum.util.render.*;
import java.util.*;

public class ChestStealer extends Module
{
    private boolean fast;
    private int picked;
    private Stopwatch stopwatch;
    private Timer timer;
    
    public ChestStealer() {
        super("ChestStealer", 0, Category.PLAYER);
        this.stopwatch = new Stopwatch();
        this.timer = new Timer();
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Chest Stealer");
        if (this.mc.theWorld != null) {
            if (!(this.mc.currentScreen instanceof GuiChest)) {
                this.picked = 0;
                this.fast = true;
            }
            else {
                if (this.picked >= 10) {
                    this.fast = !this.fast;
                    this.picked = 0;
                }
                if (this.timer.hasTimeElapsed(this.fast ? 80 : 100, true) && !this.mc.inGameHasFocus && this.mc.currentScreen instanceof GuiChest) {
                    if (this == this.mc.thePlayer.openContainer) {
                        this.mc.playerController.windowClick(this.mc.thePlayer.openContainer.windowId, this.getNextSlot(this.mc.thePlayer.openContainer), 0, 1, this.mc.thePlayer);
                        ++this.picked;
                    }
                    else {
                        this.mc.thePlayer.closeScreen();
                        this.picked = 0;
                        this.fast = true;
                    }
                    this.timer.reset();
                }
            }
        }
    }
    
    private int getNextSlot(final Container container) {
        while (0 < ((container.inventorySlots.size() == 90) ? 54 : 27)) {
            if (container.getInventory().get(0) != null) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public static boolean isShit(final ItemStack itemStack) {
        return itemStack != null && (itemStack.getItem().getUnlocalizedName().contains("bow") || itemStack.getItem().getUnlocalizedName().contains("arrow") || itemStack.getItem().getUnlocalizedName().contains("stick") || itemStack.getItem().getUnlocalizedName().contains("egg") || itemStack.getItem().getUnlocalizedName().contains("stick") || itemStack.getItem().getUnlocalizedName().contains("string") || itemStack.getItem().getUnlocalizedName().contains("flint") || itemStack.getItem().getUnlocalizedName().contains("compass") || itemStack.getItem().getUnlocalizedName().contains("feather") || itemStack.getItem().getUnlocalizedName().contains("bucket") || itemStack.getItem().getUnlocalizedName().contains("snow") || itemStack.getItem().getUnlocalizedName().contains("fish") || itemStack.getItem().getUnlocalizedName().contains("enchant") || itemStack.getItem().getUnlocalizedName().contains("exp") || itemStack.getItem() instanceof ItemPickaxe || itemStack.getItem() instanceof ItemTool || itemStack.getItem().getUnlocalizedName().contains("potion"));
    }
    
    @Punjabi
    public void onRender3D(final Event3D event3D) {
        for (final TileEntityChest next : this.mc.theWorld.loadedTileEntityList) {
            if (next instanceof TileEntityChest) {
                RenderUtil.drawBlockBox(next.getPos(), new Color(109, 67, 226, 255), true);
            }
        }
    }
}
