package com.nquantum.module.combat;

import net.minecraft.entity.*;
import com.nquantum.util.*;
import com.nquantum.*;
import net.minecraft.client.renderer.*;
import com.nquantum.module.customize.*;
import com.nquantum.util.color.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import com.nquantum.event.*;
import com.nquantum.module.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import java.util.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import com.mojang.realmsclient.gui.*;
import com.nquantum.event.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class KillAura extends Module
{
    public Random random;
    public EntityLivingBase target;
    public long current;
    private int colorPrimary;
    public float yaw;
    public int directiond;
    public int delay;
    private double animHealth;
    public boolean others;
    private int colorSecondary;
    private double width;
    public float pitch;
    private int direction;
    public long last;
    
    @Punjabi
    public void onRenderUI(final Event2D event2D) {
        final ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        if (this.target != null) {
            GuiUtil.drawScaledString("Distance: " + this.mc.thePlayer.getDistanceToEntity(this.target), scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() / 2 - 120, true, 1.0f);
        }
        if (this.target != null && Asyncware.instance.moduleManager.getModuleByName("KillAura").isToggled()) {
            GlStateManager.translate(300.0f, 300.0f, 0.0f);
            this.colorPrimary = Colors.darker(HUD.hudColor, 0.5f);
            this.colorSecondary = HUD.hudColor;
            GL11.glPushMatrix();
            this.width = 107.5;
            Gui.drawRect(-22.5, 0.0, 124.5, 50.0, new Color(24, 24, 24, 121).getRGB());
            GL11.glTranslatef(-22.0f, -2.2f, 0.0f);
            this.mc.fontRendererObj.drawString(this.target.getName(), 30.0f, 8.0f, -1, true);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glTranslatef(-15.0f, -15.0f, 0.0f);
            this.mc.fontRendererObj.drawStringWithShadow(Math.round(this.target.getHealth()) + "", 30.0f, 25.0f, HUD.hudColor);
            this.mc.fontRendererObj.drawStringWithShadow("\u2764", (float)(this.mc.fontRendererObj.getStringWidth(Math.round(this.target.getHealth()) + "") + 32), 25.0f, HUD.hudColor);
            GL11.glTranslatef(15.0f, 15.0f, 0.0f);
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            this.mc.fontRendererObj.drawString("", 30.0f, 25.0f, -1, true);
            GuiInventory.drawEntityOnScreen(15, 47, 20, 2.0f, 2.0f, this.target);
            this.animHealth += (this.target.getHealth() - this.animHealth) / 32.0 * 0.7;
            if (this.animHealth < 0.0 || this.animHealth > this.target.getMaxHealth()) {
                this.animHealth = this.target.getHealth();
            }
            else {
                GL11.glTranslatef(30.0f, 0.0f, 0.0f);
                Gui.drawRect(0.0, 40.5, (int)this.width, 48.5, this.colorPrimary);
                Gui.drawRect(0.0, 40.5, (int)(this.animHealth / this.target.getMaxHealth() * this.width), 48.5, this.colorSecondary);
            }
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            GL11.glPopMatrix();
        }
    }
    
    public static double randomNumber(final double n, final double n2) {
        return Math.random() * (n - n2) + n2;
    }
    
    private void updateTime() {
        this.current = System.nanoTime() / 1000000L;
    }
    
    @Punjabi
    public void onRender3D(final Event3D event3D) {
        final double valDouble = Asyncware.instance.settingsManager.getSettingByName("Strafe Radius").getValDouble();
        if (Asyncware.instance.moduleManager.getModuleByName("TargetStrafe").isToggled() && Asyncware.instance.moduleManager.getModuleByName("KillAura").isToggled() && this.target != null) {
            new TargetStrafe().drawCircleAroundEntity(this.target, event3D.getPartialTicks(), valDouble);
        }
    }
    
    private void resetTime() {
        this.last = System.nanoTime() / 1000000L;
    }
    
    public KillAura() {
        super("KillAura", 19, Category.COMBAT);
        this.delay = 4;
        this.direction = -1;
        this.animHealth = 1.0;
        this.random = new Random();
    }
    
    public float[] getRotations(final double n, final double n2, final double n3) {
        final EntityPlayerSP thePlayer = this.mc.thePlayer;
        final double n4 = n - thePlayer.posX;
        final double n5 = n2 - (thePlayer.posY + thePlayer.getEyeHeight());
        final double n6 = n3 - thePlayer.posZ;
        return new float[] { (float)(Math.atan2(n6, n4) * 180.0 / 3.141592653589793) - 90.0f, (float)(-(Math.atan2(n5, MathHelper.sqrt_double(n4 * n4 + n6 * n6)) * 180.0 / 3.141592653589793)) };
    }
    
    @Override
    public void setup() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("AAC V5");
        list.add("Koks Craft");
        list.add("Watchdog");
        list.add("NCP");
        Asyncware.instance.settingsManager.rSetting(new Setting("Crack Size", this, 5.0, 0.0, 65.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Kill Aura Rotations", this, "AACV5", list));
        Asyncware.instance.settingsManager.rSetting(new Setting("KA Range", this, 3.0, 1.0, 6.0, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("ClicksPerSecond", this, 9.0, 1.0, 20.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Existed", this, 30.0, 0.0, 500.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("FOV", this, 360.0, 0.0, 360.0, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("TPAura", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("AutoBlock", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Invisibles", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Players", this, true));
        Asyncware.instance.settingsManager.rSetting(new Setting("Animals", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Monsters", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Villagers", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Teams", this, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("LockView", this, false));
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        Asyncware.instance.settingsManager.getSettingByName("Kill Aura Rotations").getValString();
        Asyncware.instance.settingsManager.getSettingByName("KA Range").getValDouble();
        this.setDisplayName("Kill Aura " + ChatFormatting.GRAY + "Switch");
    }
    
    public EntityLivingBase getClosest(final double p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: dstore_3       
        //     2: aconst_null    
        //     3: astore          5
        //     5: aload_0        
        //     6: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //     9: getfield        net/minecraft/client/Minecraft.theWorld:Lnet/minecraft/client/multiplayer/WorldClient;
        //    12: getfield        net/minecraft/client/multiplayer/WorldClient.loadedEntityList:Ljava/util/List;
        //    15: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    20: astore          6
        //    22: aload           6
        //    24: invokeinterface java/util/Iterator.hasNext:()Z
        //    29: ifeq            102
        //    32: aload           6
        //    34: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    39: astore          7
        //    41: aload           7
        //    43: checkcast       Lnet/minecraft/entity/Entity;
        //    46: astore          11
        //    48: aload           11
        //    50: instanceof      Lnet/minecraft/entity/EntityLivingBase;
        //    53: ifeq            22
        //    56: aload_0        
        //    57: aload           11
        //    59: checkcast       Lnet/minecraft/entity/EntityLivingBase;
        //    62: dup            
        //    63: astore          10
        //    65: ifne            22
        //    68: aload_0        
        //    69: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //    72: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    75: aload           10
        //    77: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getDistanceToEntity:(Lnet/minecraft/entity/Entity;)F
        //    80: f2d            
        //    81: dup2           
        //    82: dstore          8
        //    84: dload_3        
        //    85: dcmpg          
        //    86: ifle            92
        //    89: goto            22
        //    92: dload           8
        //    94: dstore_3       
        //    95: aload           10
        //    97: astore          5
        //    99: goto            22
        //   102: aload           5
        //   104: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0022 (coming from #0065).
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
    
    private void switchDirection() {
        this.direction = ((this.direction == 1) ? -1 : 1);
    }
    
    private float getAngleDifference(final float n, final float n2) {
        final float n3 = Math.abs(n2 - n) % 360.0f;
        return (n3 > 180.0f) ? (360.0f - n3) : n3;
    }
    
    @Punjabi
    @Override
    public void onPre(final EventPreMotionUpdate p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //     6: ldc_w           "KA Range"
        //     9: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //    12: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //    15: dstore_3       
        //    16: aload_0        
        //    17: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //    20: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    23: getfield        net/minecraft/client/settings/GameSettings.keyBindLeft:Lnet/minecraft/client/settings/KeyBinding;
        //    26: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //    29: ifeq            37
        //    32: aload_0        
        //    33: iconst_1       
        //    34: putfield        com/nquantum/module/combat/KillAura.directiond:I
        //    37: aload_0        
        //    38: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //    41: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    44: getfield        net/minecraft/client/settings/GameSettings.keyBindRight:Lnet/minecraft/client/settings/KeyBinding;
        //    47: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //    50: ifeq            58
        //    53: aload_0        
        //    54: iconst_m1      
        //    55: putfield        com/nquantum/module/combat/KillAura.directiond:I
        //    58: aload_0        
        //    59: aload_0        
        //    60: dload_3        
        //    61: invokevirtual   com/nquantum/module/combat/KillAura.getClosest:(D)Lnet/minecraft/entity/EntityLivingBase;
        //    64: putfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //    67: aload_0        
        //    68: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //    71: ifnonnull       75
        //    74: return         
        //    75: aload_0        
        //    76: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //    79: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    82: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //    85: aload_0        
        //    86: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //    89: getfield        net/minecraft/entity/EntityLivingBase.rotationYaw:F
        //    92: fsub           
        //    93: f2d            
        //    94: invokestatic    java/lang/Math.tan:(D)D
        //    97: dstore          5
        //    99: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //   102: dload           5
        //   104: invokevirtual   java/io/PrintStream.println:(D)V
        //   107: aload_0        
        //   108: invokespecial   com/nquantum/module/combat/KillAura.updateTime:()V
        //   111: aload_0        
        //   112: aload_0        
        //   113: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   116: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   119: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   122: putfield        com/nquantum/module/combat/KillAura.yaw:F
        //   125: aload_0        
        //   126: aload_0        
        //   127: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   130: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   133: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationPitch:F
        //   136: putfield        com/nquantum/module/combat/KillAura.pitch:F
        //   139: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   142: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   145: ldc_w           "Kill Aura Rotations"
        //   148: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   151: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValString:()Ljava/lang/String;
        //   154: astore          7
        //   156: aload           7
        //   158: ldc_w           "AAC V5"
        //   161: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   164: ifeq            310
        //   167: aload_1        
        //   168: aload_0        
        //   169: aload_0        
        //   170: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   173: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   176: iconst_0       
        //   177: faload         
        //   178: aload_0        
        //   179: getfield        com/nquantum/module/combat/KillAura.random:Ljava/util/Random;
        //   182: bipush          30
        //   184: invokevirtual   java/util/Random.nextInt:(I)I
        //   187: i2f            
        //   188: fadd           
        //   189: ldc_w           5.0
        //   192: fsub           
        //   193: f2d            
        //   194: aload_0        
        //   195: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   198: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   201: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   204: aload_0        
        //   205: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   208: getfield        net/minecraft/entity/EntityLivingBase.rotationYaw:F
        //   211: fsub           
        //   212: f2d            
        //   213: invokestatic    java/lang/Math.tan:(D)D
        //   216: dadd           
        //   217: d2f            
        //   218: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setYaw:(F)V
        //   221: aload_1        
        //   222: aload_0        
        //   223: aload_0        
        //   224: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   227: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   230: iconst_1       
        //   231: faload         
        //   232: aload_0        
        //   233: getfield        com/nquantum/module/combat/KillAura.random:Ljava/util/Random;
        //   236: bipush          30
        //   238: invokevirtual   java/util/Random.nextInt:(I)I
        //   241: i2f            
        //   242: fadd           
        //   243: ldc_w           5.0
        //   246: fsub           
        //   247: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setPitch:(F)V
        //   250: aload_0        
        //   251: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   254: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   257: aload_0        
        //   258: aload_0        
        //   259: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   262: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   265: iconst_0       
        //   266: faload         
        //   267: aload_0        
        //   268: getfield        com/nquantum/module/combat/KillAura.random:Ljava/util/Random;
        //   271: bipush          30
        //   273: invokevirtual   java/util/Random.nextInt:(I)I
        //   276: i2f            
        //   277: fadd           
        //   278: ldc_w           5.0
        //   281: fsub           
        //   282: f2d            
        //   283: aload_0        
        //   284: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   287: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   290: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   293: aload_0        
        //   294: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   297: getfield        net/minecraft/entity/EntityLivingBase.rotationYaw:F
        //   300: fsub           
        //   301: f2d            
        //   302: invokestatic    java/lang/Math.tan:(D)D
        //   305: dadd           
        //   306: d2f            
        //   307: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationYawHead:F
        //   310: aload           7
        //   312: ldc_w           "Koks Craft"
        //   315: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   318: ifeq            369
        //   321: aload_1        
        //   322: aload_0        
        //   323: aload_0        
        //   324: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   327: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   330: iconst_0       
        //   331: faload         
        //   332: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setYaw:(F)V
        //   335: aload_1        
        //   336: aload_0        
        //   337: aload_0        
        //   338: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   341: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   344: iconst_1       
        //   345: faload         
        //   346: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setPitch:(F)V
        //   349: aload_0        
        //   350: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   353: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   356: aload_0        
        //   357: aload_0        
        //   358: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   361: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   364: iconst_0       
        //   365: faload         
        //   366: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationYawHead:F
        //   369: aload           7
        //   371: ldc_w           "Watchdog"
        //   374: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   377: ifeq            486
        //   380: aload_1        
        //   381: aload_0        
        //   382: aload_0        
        //   383: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   386: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   389: iconst_0       
        //   390: faload         
        //   391: aload_0        
        //   392: getfield        com/nquantum/module/combat/KillAura.random:Ljava/util/Random;
        //   395: bipush          20
        //   397: invokevirtual   java/util/Random.nextInt:(I)I
        //   400: i2f            
        //   401: fadd           
        //   402: ldc_w           30.5
        //   405: fsub           
        //   406: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setYaw:(F)V
        //   409: aload_1        
        //   410: aload_0        
        //   411: aload_0        
        //   412: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   415: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   418: iconst_1       
        //   419: faload         
        //   420: ldc_w           5.0
        //   423: fadd           
        //   424: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setPitch:(F)V
        //   427: aload_0        
        //   428: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   431: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   434: aload_0        
        //   435: aload_0        
        //   436: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   439: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   442: iconst_0       
        //   443: faload         
        //   444: aload_0        
        //   445: getfield        com/nquantum/module/combat/KillAura.random:Ljava/util/Random;
        //   448: bipush          20
        //   450: invokevirtual   java/util/Random.nextInt:(I)I
        //   453: i2f            
        //   454: fadd           
        //   455: ldc             30.0
        //   457: fsub           
        //   458: f2d            
        //   459: aload_0        
        //   460: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   463: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   466: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   469: aload_0        
        //   470: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   473: getfield        net/minecraft/entity/EntityLivingBase.rotationYaw:F
        //   476: fsub           
        //   477: f2d            
        //   478: invokestatic    java/lang/Math.tanh:(D)D
        //   481: dadd           
        //   482: d2f            
        //   483: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationYawHead:F
        //   486: aload           7
        //   488: ldc_w           "NCP"
        //   491: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   494: ifeq            640
        //   497: aload_1        
        //   498: aload_0        
        //   499: aload_0        
        //   500: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   503: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   506: iconst_0       
        //   507: faload         
        //   508: aload_0        
        //   509: getfield        com/nquantum/module/combat/KillAura.random:Ljava/util/Random;
        //   512: bipush          30
        //   514: invokevirtual   java/util/Random.nextInt:(I)I
        //   517: i2f            
        //   518: fadd           
        //   519: ldc_w           5.0
        //   522: fsub           
        //   523: f2d            
        //   524: aload_0        
        //   525: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   528: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   531: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   534: aload_0        
        //   535: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   538: getfield        net/minecraft/entity/EntityLivingBase.rotationYaw:F
        //   541: fsub           
        //   542: f2d            
        //   543: invokestatic    java/lang/Math.tan:(D)D
        //   546: dadd           
        //   547: d2f            
        //   548: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setYaw:(F)V
        //   551: aload_1        
        //   552: aload_0        
        //   553: aload_0        
        //   554: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   557: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   560: iconst_1       
        //   561: faload         
        //   562: aload_0        
        //   563: getfield        com/nquantum/module/combat/KillAura.random:Ljava/util/Random;
        //   566: bipush          30
        //   568: invokevirtual   java/util/Random.nextInt:(I)I
        //   571: i2f            
        //   572: fadd           
        //   573: ldc_w           5.0
        //   576: fsub           
        //   577: invokevirtual   com/nquantum/event/impl/EventPreMotionUpdate.setPitch:(F)V
        //   580: aload_0        
        //   581: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   584: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   587: aload_0        
        //   588: aload_0        
        //   589: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   592: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   595: iconst_0       
        //   596: faload         
        //   597: aload_0        
        //   598: getfield        com/nquantum/module/combat/KillAura.random:Ljava/util/Random;
        //   601: bipush          30
        //   603: invokevirtual   java/util/Random.nextInt:(I)I
        //   606: i2f            
        //   607: fadd           
        //   608: ldc_w           5.0
        //   611: fsub           
        //   612: f2d            
        //   613: aload_0        
        //   614: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   617: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   620: getfield        net/minecraft/client/entity/EntityPlayerSP.rotationYaw:F
        //   623: aload_0        
        //   624: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   627: getfield        net/minecraft/entity/EntityLivingBase.rotationYaw:F
        //   630: fsub           
        //   631: f2d            
        //   632: invokestatic    java/lang/Math.tan:(D)D
        //   635: dadd           
        //   636: d2f            
        //   637: putfield        net/minecraft/client/entity/EntityPlayerSP.rotationYawHead:F
        //   640: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   643: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   646: ldc_w           "ClicksPerSecond"
        //   649: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   652: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   655: d2i            
        //   656: istore          8
        //   658: aload_0        
        //   659: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   662: ifnull          735
        //   665: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   668: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   671: ldc_w           "AutoBlock"
        //   674: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   677: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValBoolean:()Z
        //   680: ifeq            735
        //   683: aload_0        
        //   684: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   687: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   690: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //   693: ifnull          735
        //   696: aload_0        
        //   697: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   700: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   703: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //   706: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   709: ifnull          735
        //   712: aload_0        
        //   713: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   716: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   719: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getHeldItem:()Lnet/minecraft/item/ItemStack;
        //   722: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   725: instanceof      Lnet/minecraft/item/ItemSword;
        //   728: ifeq            735
        //   731: iconst_1       
        //   732: goto            736
        //   735: iconst_0       
        //   736: dup            
        //   737: istore_2       
        //   738: istore          9
        //   740: aload_0        
        //   741: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   744: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   747: getfield        net/minecraft/client/entity/EntityPlayerSP.isCollidedHorizontally:Z
        //   750: ifeq            757
        //   753: aload_0        
        //   754: invokespecial   com/nquantum/module/combat/KillAura.switchDirection:()V
        //   757: aload_0        
        //   758: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   761: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   764: getfield        net/minecraft/client/settings/GameSettings.keyBindLeft:Lnet/minecraft/client/settings/KeyBinding;
        //   767: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //   770: ifeq            778
        //   773: aload_0        
        //   774: iconst_1       
        //   775: putfield        com/nquantum/module/combat/KillAura.direction:I
        //   778: aload_0        
        //   779: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   782: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   785: getfield        net/minecraft/client/settings/GameSettings.keyBindRight:Lnet/minecraft/client/settings/KeyBinding;
        //   788: invokevirtual   net/minecraft/client/settings/KeyBinding.isPressed:()Z
        //   791: ifeq            799
        //   794: aload_0        
        //   795: iconst_m1      
        //   796: putfield        com/nquantum/module/combat/KillAura.direction:I
        //   799: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   802: getfield        com/nquantum/Asyncware.moduleManager:Lcom/nquantum/module/ModuleManager;
        //   805: ldc_w           "TargetStrafe"
        //   808: invokevirtual   com/nquantum/module/ModuleManager.getModuleByName:(Ljava/lang/String;)Lcom/nquantum/module/Module;
        //   811: invokevirtual   com/nquantum/module/Module.isToggled:()Z
        //   814: istore          10
        //   816: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   819: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   822: ldc_w           "Strafe Radius"
        //   825: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   828: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   831: dstore          11
        //   833: iload           10
        //   835: ifeq            860
        //   838: aload_0        
        //   839: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   842: aload_0        
        //   843: aload_0        
        //   844: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   847: invokevirtual   com/nquantum/module/combat/KillAura.getRotations:(Lnet/minecraft/entity/Entity;)[F
        //   850: aload_0        
        //   851: getfield        com/nquantum/module/combat/KillAura.directiond:I
        //   854: i2d            
        //   855: dload           11
        //   857: invokestatic    com/nquantum/module/combat/TargetStrafe.strafe:(Lnet/minecraft/entity/Entity;[FDD)V
        //   860: getstatic       com/nquantum/Asyncware.instance:Lcom/nquantum/Asyncware;
        //   863: getfield        com/nquantum/Asyncware.settingsManager:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager;
        //   866: ldc_w           "TPAura"
        //   869: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/SettingsManager.getSettingByName:(Ljava/lang/String;)Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   872: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValBoolean:()Z
        //   875: ifeq            916
        //   878: iload_2        
        //   879: ifeq            952
        //   882: aload_0        
        //   883: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   886: aload_0        
        //   887: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   890: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   893: invokevirtual   net/minecraft/entity/EntityLivingBase.getDistanceToEntity:(Lnet/minecraft/entity/Entity;)F
        //   896: dload_3        
        //   897: d2f            
        //   898: fcmpg          
        //   899: ifge            952
        //   902: aload_0        
        //   903: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   906: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   909: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isBlocking:()Z
        //   912: pop            
        //   913: goto            952
        //   916: iload_2        
        //   917: ifeq            952
        //   920: aload_0        
        //   921: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   924: aload_0        
        //   925: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   928: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   931: invokevirtual   net/minecraft/entity/EntityLivingBase.getDistanceToEntity:(Lnet/minecraft/entity/Entity;)F
        //   934: ldc_w           400.0
        //   937: fcmpg          
        //   938: ifge            952
        //   941: aload_0        
        //   942: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   945: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   948: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.isBlocking:()Z
        //   951: pop            
        //   952: aload_0        
        //   953: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   956: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //   959: getfield        net/minecraft/client/settings/GameSettings.keyBindJump:Lnet/minecraft/client/settings/KeyBinding;
        //   962: invokevirtual   net/minecraft/client/settings/KeyBinding.isKeyDown:()Z
        //   965: ifeq            986
        //   968: aload_0        
        //   969: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //   972: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   975: aload_0        
        //   976: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //   979: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getDistanceToEntity:(Lnet/minecraft/entity/Entity;)F
        //   982: ldc_w           5.0
        //   985: fcmpg          
        //   986: aload_0        
        //   987: getfield        com/nquantum/module/combat/KillAura.current:J
        //   990: aload_0        
        //   991: getfield        com/nquantum/module/combat/KillAura.last:J
        //   994: lsub           
        //   995: sipush          1000
        //   998: iload           8
        //  1000: idiv           
        //  1001: i2l            
        //  1002: lcmp           
        //  1003: ifle            1045
        //  1006: aload_0        
        //  1007: getfield        com/nquantum/module/combat/KillAura.mc:Lnet/minecraft/client/Minecraft;
        //  1010: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //  1013: aload_0        
        //  1014: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //  1017: invokevirtual   net/minecraft/client/entity/EntityPlayerSP.getDistanceToEntity:(Lnet/minecraft/entity/Entity;)F
        //  1020: f2d            
        //  1021: dload_3        
        //  1022: dcmpg          
        //  1023: ifge            1045
        //  1026: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //  1029: dload_3        
        //  1030: invokevirtual   java/io/PrintStream.println:(D)V
        //  1033: aload_0        
        //  1034: aload_0        
        //  1035: getfield        com/nquantum/module/combat/KillAura.target:Lnet/minecraft/entity/EntityLivingBase;
        //  1038: invokespecial   com/nquantum/module/combat/KillAura.attack:(Lnet/minecraft/entity/Entity;)V
        //  1041: aload_0        
        //  1042: invokespecial   com/nquantum/module/combat/KillAura.resetTime:()V
        //  1045: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0986 (coming from #0985).
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
    
    @Punjabi
    @Override
    public void onPost(final EventPostMotionUpdate eventPostMotionUpdate) {
        if (this.target == null) {
            return;
        }
        if (Asyncware.instance.settingsManager.getSettingByName("TPAura").getValBoolean()) {
            final InfAura infAura = new InfAura();
            infAura.teleport(this.target.getPosition().getX(), this.target.getPosition().getY(), this.target.getPosition().getZ());
            this.attack(this.target);
            infAura.teleport(this.mc.thePlayer.lastTickPosX, this.mc.thePlayer.lastTickPosY, this.mc.thePlayer.lastTickPosZ);
        }
        this.mc.playerController.sendFakeUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
        final double lastTickPosX = this.mc.thePlayer.lastTickPosX;
        final double lastTickPosY = this.mc.thePlayer.lastTickPosY;
        final double lastTickPosZ = this.mc.thePlayer.lastTickPosZ;
        this.mc.thePlayer.rotationYaw = this.yaw;
        this.mc.thePlayer.rotationPitch = this.pitch;
    }
    
    private void attack(final Entity entity) {
        while (0 < Asyncware.instance.settingsManager.getSettingByName("Crack Size").getValDouble()) {
            this.mc.thePlayer.onCriticalHit(entity);
            int n = 0;
            ++n;
        }
        final double lastTickPosX = this.mc.thePlayer.lastTickPosX;
        final double lastTickPosY = this.mc.thePlayer.lastTickPosY;
        final double lastTickPosZ = this.mc.thePlayer.lastTickPosZ;
        this.mc.thePlayer.swingItem();
        this.mc.playerController.attackEntity(this.mc.thePlayer, entity);
    }
    
    public float[] getRotations(final Entity entity) {
        final double n = entity.posX + (entity.posX - entity.lastTickPosX) - this.mc.thePlayer.posX;
        final double n2 = entity.posY - 3.5 + entity.getEyeHeight() - this.mc.thePlayer.posY + this.mc.thePlayer.getEyeHeight();
        final double n3 = entity.posZ + (entity.posZ - entity.lastTickPosZ) - this.mc.thePlayer.posZ;
        final double sqrt = Math.sqrt(Math.pow(n, 2.0) + Math.pow(n3, 2.0));
        float n4 = (float)Math.toDegrees(-Math.atan(n / n3));
        final float n5 = (float)(-Math.toDegrees(Math.atan(n2 / sqrt)));
        if (n < 0.0 && n3 < 0.0) {
            n4 = (float)(90.0 + Math.toDegrees(Math.atan(n3 / n)));
        }
        else if (n > 0.0 && n3 < 0.0) {
            n4 = (float)(-90.0 + Math.toDegrees(Math.atan(n3 / n)));
        }
        return new float[] { n4, n5 };
    }
}
