package com.nquantum.module.customize;

import com.nquantum.event.impl.*;
import com.nquantum.*;
import java.awt.*;
import com.nquantum.util.color.*;
import net.minecraft.client.gui.*;
import com.mojang.realmsclient.gui.*;
import org.lwjgl.opengl.*;
import com.nquantum.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import com.nquantum.event.*;
import java.util.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.nquantum.module.*;

public class HUD extends Module
{
    public static int hudColor;
    
    @Punjabi
    public void onEvent(final Event2D event2D) {
        final int n = (int)Asyncware.instance.settingsManager.getSettingByName("HUD R").getValDouble();
        final int n2 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD G").getValDouble();
        final int n3 = (int)Asyncware.instance.settingsManager.getSettingByName("HUD B").getValDouble();
        HUD.hudColor = 0;
        final String valString = Asyncware.instance.settingsManager.getSettingByName("Color Mode").getValString();
        if (valString.equalsIgnoreCase("Static")) {
            HUD.hudColor = new Color(n, n2, n3, 255).getRGB();
        }
        if (valString.equalsIgnoreCase("Fade")) {
            final int rgb = new Color(n, n2, n3, 255).getRGB();
            HUD.hudColor = Colors.fadeBetween(rgb, Colors.darker(rgb, 0.52f), (System.currentTimeMillis() + 0) % 1000L / 500.0f);
        }
        if (valString.equalsIgnoreCase("Astolfo")) {
            HUD.hudColor = Colors.Astolfo(80, 1.0f, 0.55f);
        }
        if (valString.equalsIgnoreCase("Rainbow")) {
            HUD.hudColor = Colors.RGBX(1.0f, 0.5f, 1.0f, 0);
        }
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        final String valString2 = Asyncware.instance.settingsManager.getSettingByName("Watermark Mode").getValString();
        new Color(93, 62, 255, 255).getRGB();
        Gui.drawRect(0.0, 0.0, 0.0, 0.0, 0);
        final String string = "Asyncware | " + this.mc.thePlayer.getName() + " | fps: " + this.mc.getDebugFps() + " | version: " + this.mc.getVersion();
        if (valString2.equalsIgnoreCase("Classic")) {
            Asyncware.roboto.drawStringWithShadow("Async" + ChatFormatting.BLUE + "ware", 1.0f, 1.0f, -1);
        }
        if (valString2.equalsIgnoreCase("Astolfo")) {
            GL11.glPushMatrix();
            GL11.glTranslatef(2.0f, 0.0f, 0.0f);
            Gui.drawRect(4 + this.mc.fontRendererObj.getStringWidth(string), 17.0, 0.0, 1.0, new Color(1, 1, 1, 80).getRGB());
            Gui.drawRect(4 + this.mc.fontRendererObj.getStringWidth(string), 2.5, 0.0, 1.0, HUD.hudColor);
            GuiUtil.drawScaledCs(string, 2.0f, 5.5f, false);
            GL11.glPopMatrix();
        }
        if (valString2.equalsIgnoreCase("Asyncware")) {
            System.out.println(Colors.hudColor);
            Asyncware.astofolo.drawStringWithShadow("A", 2.0f, 2.0f, HUD.hudColor);
            Asyncware.astofolo.drawStringWithShadow("syncware", 7.5f, 2.0f, -1);
        }
        int n4 = scaledResolution.getScaledWidth() / 2 + 9;
        while (true) {
            final ItemStack equipmentInSlot = this.mc.thePlayer.getEquipmentInSlot(0);
            if (equipmentInSlot != null) {
                final float zLevel = this.mc.getRenderItem().zLevel;
                GL11.glPushMatrix();
                GlStateManager.clear(256);
                this.mc.getRenderItem().zLevel = -100.0f;
                this.mc.getRenderItem().renderItemIntoGUI(equipmentInSlot, n4, scaledResolution.getScaledHeight() - 55);
                this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, equipmentInSlot, n4, scaledResolution.getScaledHeight() - 55);
                this.mc.getRenderItem().zLevel = zLevel;
                GL11.glPopMatrix();
                if (equipmentInSlot.getItem() instanceof ItemSword || equipmentInSlot.getItem() instanceof ItemTool || equipmentInSlot.getItem() instanceof ItemArmor || equipmentInSlot.getItem() instanceof ItemBow) {
                    final int n5 = equipmentInSlot.getMaxDamage() - equipmentInSlot.getItemDamage();
                    final int n6 = scaledResolution.getScaledHeight() - 60;
                    GlStateManager.scale(0.5, 0.5, 0.5);
                }
                n4 += 16;
            }
            int n7 = 0;
            ++n7;
        }
    }
    
    @Override
    public void setup() {
        super.setup();
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Classic");
        list.add("Astolfo");
        list.add("Asyncware");
        final ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Static");
        list2.add("Fade");
        list2.add("Astolfo");
        list2.add("Rainbow");
        final ArrayList<String> list3 = new ArrayList<String>();
        list3.add("Astolfo");
        list3.add("Moon");
        list3.add("Asyncware");
        list3.add("Remix");
        list3.add("Custom");
        Asyncware.instance.settingsManager.rSetting(new Setting("Watermark Mode", this, "Classic", list));
        Asyncware.instance.settingsManager.rSetting(new Setting("Color Mode", this, "Static", list2));
        Asyncware.instance.settingsManager.rSetting(new Setting("ModuleList Mode", this, "Astolfo", list3));
        Asyncware.instance.settingsManager.rSetting(new Setting("ModuleList Rect Opacity", this, 50.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("ModuleList Y Spacing", this, 8.0, 7.0, 10.5, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("HUD R", this, 255.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("HUD G", this, 255.0, 0.0, 255.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("HUD B", this, 255.0, 0.0, 255.0, true));
    }
    
    public HUD() {
        super("HUD", 0, Category.CUSTOMIZE);
    }
}
