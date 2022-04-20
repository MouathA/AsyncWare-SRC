package com.nquantum.module.movement;

import com.nquantum.util.time.*;
import net.minecraft.client.entity.*;
import java.util.*;
import com.nquantum.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import com.nquantum.module.*;
import com.nquantum.event.impl.*;
import com.nquantum.event.*;
import net.minecraft.client.*;

public class Speed extends Module
{
    public Timer timer;
    public static EntityPlayerSP player;
    
    @Override
    public void setup() {
        final ArrayList<String> list = new ArrayList<String>();
        list.add("Vanilla");
        list.add("VanillaHop");
        list.add("Kokscraft");
        list.add("NCP");
        list.add("HypixelHop");
        list.add("Hypixel Port");
        list.add("VerusHop");
        list.add("Verus");
        list.add("Verus-Packet");
        list.add("Test");
        Asyncware.instance.settingsManager.rSetting(new Setting("Speed Mode", this, "Vanilla", list));
        Asyncware.instance.settingsManager.rSetting(new Setting("Speed", this, 0.2, 0.01, 1.0, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Custom Y", this, 0.42, 0.01, 1.0, false));
        Asyncware.instance.settingsManager.rSetting(new Setting("Custom Motion", this, 0.2, 0.01, 2.0, false));
    }
    
    private boolean isOnLiquid() {
        final int n = (int)(this.mc.thePlayer.boundingBox.minY - 0.01);
        for (int i = MathHelper.floor_double(this.mc.thePlayer.boundingBox.minX); i < MathHelper.floor_double(this.mc.thePlayer.boundingBox.maxX) + 1; ++i) {
            for (int j = MathHelper.floor_double(this.mc.thePlayer.boundingBox.minZ); j < MathHelper.floor_double(this.mc.thePlayer.boundingBox.maxZ) + 1; ++j) {
                final Block block = this.mc.theWorld.getBlockState(new BlockPos(i, n, j)).getBlock();
                if (block != null && !(block instanceof BlockAir) && !(block instanceof BlockLiquid)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Speed() {
        super("Speed", 45, Category.MOVEMENT);
        this.timer = new Timer();
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate p0) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'com/nquantum/module/movement/Speed.onUpdate:(Lcom/nquantum/event/impl/EventUpdate;)V'.
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:66)
        //     at com.strobel.assembler.metadata.MethodDefinition.tryLoadBody(MethodDefinition.java:729)
        //     at com.strobel.assembler.metadata.MethodDefinition.getBody(MethodDefinition.java:83)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:202)
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 796.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        Speed.player = Minecraft.getMinecraft().thePlayer;
    }
}
