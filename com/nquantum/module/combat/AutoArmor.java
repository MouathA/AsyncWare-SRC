package com.nquantum.module.combat;

import com.nquantum.util.player.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class AutoArmor extends Module
{
    private int[] leggings;
    private int[] boots;
    private int[] helmet;
    private int delay;
    private int[] chestplate;
    private boolean best;
    
    public void autoArmor() {
        if (this.best) {
            return;
        }
        ++this.delay;
        if (this.delay >= 10) {
            int n2 = 0;
            if (this.mc.thePlayer.inventory.armorInventory[0] == null) {
                int[] boots;
                while (0 < (boots = this.boots).length) {
                    final int n = boots[0];
                    if (ArmorUtils.getItem(n) != -1) {
                        ArmorUtils.getItem(n);
                        break;
                    }
                    ++n2;
                }
            }
            if (this.mc.thePlayer.inventory.armorInventory[1] == null) {
                int[] leggings;
                while (0 < (leggings = this.leggings).length) {
                    final int n3 = leggings[0];
                    if (ArmorUtils.getItem(n3) != -1) {
                        ArmorUtils.getItem(n3);
                        break;
                    }
                    ++n2;
                }
            }
            if (this.mc.thePlayer.inventory.armorInventory[2] == null) {
                int[] chestplate;
                while (0 < (chestplate = this.chestplate).length) {
                    final int n4 = chestplate[0];
                    if (ArmorUtils.getItem(n4) != -1) {
                        ArmorUtils.getItem(n4);
                        break;
                    }
                    ++n2;
                }
            }
            if (this.mc.thePlayer.inventory.armorInventory[3] == null) {
                int[] helmet;
                while (0 < (helmet = this.helmet).length) {
                    final int n5 = helmet[0];
                    if (ArmorUtils.getItem(n5) != -1) {
                        ArmorUtils.getItem(n5);
                        break;
                    }
                    ++n2;
                }
            }
        }
    }
    
    @Override
    public void setup() {
        this.chestplate = new int[] { 311, 307, 315, 303, 299 };
        this.leggings = new int[] { 312, 308, 316, 304, 300 };
        this.boots = new int[] { 313, 309, 317, 305, 301 };
        this.helmet = new int[] { 310, 306, 314, 302, 298 };
        this.delay = 0;
        this.best = true;
    }
    
    public AutoArmor() {
        super("AutoArmor", 0, Category.COMBAT);
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName("Auto Armor");
        this.autoArmor();
        this.betterArmor();
    }
    
    public void betterArmor() {
        if (!this.best) {
            return;
        }
        ++this.delay;
        if (this.delay >= 10 && (this.mc.thePlayer.openContainer == null || this.mc.thePlayer.openContainer.windowId == 0)) {
            int n2 = 0;
            if (this.mc.thePlayer.inventory.armorInventory[0] == null) {
                final int[] boots;
                final int length = (boots = this.boots).length;
                int n;
                while (true) {
                    n = boots[0];
                    if (ArmorUtils.getItem(n) != -1) {
                        break;
                    }
                    ++n2;
                }
                ArmorUtils.getItem(n);
            }
            if (ArmorUtils.isBetterArmor(0, this.boots)) {}
            if (this.mc.thePlayer.inventory.armorInventory[3] == null) {
                final int[] helmet;
                final int length2 = (helmet = this.helmet).length;
                int n3;
                while (true) {
                    n3 = helmet[0];
                    if (ArmorUtils.getItem(n3) != -1) {
                        break;
                    }
                    ++n2;
                }
                ArmorUtils.getItem(n3);
            }
            if (ArmorUtils.isBetterArmor(3, this.helmet)) {}
            if (this.mc.thePlayer.inventory.armorInventory[1] == null) {
                final int[] leggings;
                final int length3 = (leggings = this.leggings).length;
                int n4;
                while (true) {
                    n4 = leggings[0];
                    if (ArmorUtils.getItem(n4) != -1) {
                        break;
                    }
                    ++n2;
                }
                ArmorUtils.getItem(n4);
            }
            if (ArmorUtils.isBetterArmor(1, this.leggings)) {}
            if (this.mc.thePlayer.inventory.armorInventory[2] == null) {
                final int[] chestplate;
                final int length4 = (chestplate = this.chestplate).length;
                int n5;
                while (true) {
                    n5 = chestplate[0];
                    if (ArmorUtils.getItem(n5) != -1) {
                        break;
                    }
                    ++n2;
                }
                ArmorUtils.getItem(n5);
            }
            if (ArmorUtils.isBetterArmor(2, this.chestplate)) {}
            while (true) {
                ItemStack[] mainInventory;
                while (0 < (mainInventory = this.mc.thePlayer.inventory.mainInventory).length) {
                    if (mainInventory[0] == null) {
                        this.mc.playerController.windowClick(0, 6, 0, 4, this.mc.thePlayer);
                        this.delay = 0;
                        return;
                    }
                    int n6 = 0;
                    ++n6;
                }
                continue;
            }
        }
    }
}
