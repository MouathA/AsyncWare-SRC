package net.minecraft.village;

import java.util.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import java.io.*;

public class MerchantRecipeList extends ArrayList
{
    public MerchantRecipe canRecipeBeUsed(final ItemStack p0, final ItemStack p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifle            107
        //     4: iload_3        
        //     5: aload_0        
        //     6: invokevirtual   net/minecraft/village/MerchantRecipeList.size:()I
        //     9: if_icmpge       107
        //    12: aload_0        
        //    13: iload_3        
        //    14: invokevirtual   net/minecraft/village/MerchantRecipeList.get:(I)Ljava/lang/Object;
        //    17: checkcast       Lnet/minecraft/village/MerchantRecipe;
        //    20: astore          4
        //    22: aload_0        
        //    23: aload_1        
        //    24: aload           4
        //    26: invokevirtual   net/minecraft/village/MerchantRecipe.getItemToBuy:()Lnet/minecraft/item/ItemStack;
        //    29: ifeq            100
        //    32: aload_2        
        //    33: ifnonnull       44
        //    36: aload           4
        //    38: invokevirtual   net/minecraft/village/MerchantRecipe.hasSecondItemToBuy:()Z
        //    41: ifeq            62
        //    44: aload           4
        //    46: invokevirtual   net/minecraft/village/MerchantRecipe.hasSecondItemToBuy:()Z
        //    49: ifeq            100
        //    52: aload_0        
        //    53: aload_2        
        //    54: aload           4
        //    56: invokevirtual   net/minecraft/village/MerchantRecipe.getSecondItemToBuy:()Lnet/minecraft/item/ItemStack;
        //    59: ifeq            100
        //    62: aload_1        
        //    63: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    66: aload           4
        //    68: invokevirtual   net/minecraft/village/MerchantRecipe.getItemToBuy:()Lnet/minecraft/item/ItemStack;
        //    71: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    74: if_icmplt       100
        //    77: aload           4
        //    79: invokevirtual   net/minecraft/village/MerchantRecipe.hasSecondItemToBuy:()Z
        //    82: ifeq            104
        //    85: aload_2        
        //    86: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    89: aload           4
        //    91: invokevirtual   net/minecraft/village/MerchantRecipe.getSecondItemToBuy:()Lnet/minecraft/item/ItemStack;
        //    94: getfield        net/minecraft/item/ItemStack.stackSize:I
        //    97: if_icmpge       104
        //   100: aconst_null    
        //   101: goto            106
        //   104: aload           4
        //   106: areturn        
        //   107: iconst_0       
        //   108: aload_0        
        //   109: invokevirtual   net/minecraft/village/MerchantRecipeList.size:()I
        //   112: if_icmpge       204
        //   115: aload_0        
        //   116: iconst_0       
        //   117: invokevirtual   net/minecraft/village/MerchantRecipeList.get:(I)Ljava/lang/Object;
        //   120: checkcast       Lnet/minecraft/village/MerchantRecipe;
        //   123: astore          5
        //   125: aload_0        
        //   126: aload_1        
        //   127: aload           5
        //   129: invokevirtual   net/minecraft/village/MerchantRecipe.getItemToBuy:()Lnet/minecraft/item/ItemStack;
        //   132: ifeq            198
        //   135: aload_1        
        //   136: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   139: aload           5
        //   141: invokevirtual   net/minecraft/village/MerchantRecipe.getItemToBuy:()Lnet/minecraft/item/ItemStack;
        //   144: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   147: if_icmplt       198
        //   150: aload           5
        //   152: invokevirtual   net/minecraft/village/MerchantRecipe.hasSecondItemToBuy:()Z
        //   155: ifne            162
        //   158: aload_2        
        //   159: ifnull          195
        //   162: aload           5
        //   164: invokevirtual   net/minecraft/village/MerchantRecipe.hasSecondItemToBuy:()Z
        //   167: ifeq            198
        //   170: aload_0        
        //   171: aload_2        
        //   172: aload           5
        //   174: invokevirtual   net/minecraft/village/MerchantRecipe.getSecondItemToBuy:()Lnet/minecraft/item/ItemStack;
        //   177: ifeq            198
        //   180: aload_2        
        //   181: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   184: aload           5
        //   186: invokevirtual   net/minecraft/village/MerchantRecipe.getSecondItemToBuy:()Lnet/minecraft/item/ItemStack;
        //   189: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   192: if_icmplt       198
        //   195: aload           5
        //   197: areturn        
        //   198: iinc            4, 1
        //   201: goto            107
        //   204: aconst_null    
        //   205: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0107 (coming from #0201).
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
    
    public NBTTagCompound getRecipiesAsTags() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        final NBTTagList list = new NBTTagList();
        while (0 < this.size()) {
            list.appendTag(this.get(0).writeToTags());
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Recipes", list);
        return nbtTagCompound;
    }
    
    public void writeToBuf(final PacketBuffer packetBuffer) {
        packetBuffer.writeByte((byte)(this.size() & 0xFF));
        while (0 < this.size()) {
            final MerchantRecipe merchantRecipe = this.get(0);
            packetBuffer.writeItemStackToBuffer(merchantRecipe.getItemToBuy());
            packetBuffer.writeItemStackToBuffer(merchantRecipe.getItemToSell());
            final ItemStack secondItemToBuy = merchantRecipe.getSecondItemToBuy();
            packetBuffer.writeBoolean(secondItemToBuy != null);
            if (secondItemToBuy != null) {
                packetBuffer.writeItemStackToBuffer(secondItemToBuy);
            }
            packetBuffer.writeBoolean(merchantRecipe.isRecipeDisabled());
            packetBuffer.writeInt(merchantRecipe.getToolUses());
            packetBuffer.writeInt(merchantRecipe.getMaxTradeUses());
            int n = 0;
            ++n;
        }
    }
    
    public void readRecipiesFromTags(final NBTTagCompound nbtTagCompound) {
        final NBTTagList tagList = nbtTagCompound.getTagList("Recipes", 10);
        while (0 < tagList.tagCount()) {
            this.add(new MerchantRecipe(tagList.getCompoundTagAt(0)));
            int n = 0;
            ++n;
        }
    }
    
    public static MerchantRecipeList readFromBuf(final PacketBuffer packetBuffer) throws IOException {
        final MerchantRecipeList list = new MerchantRecipeList();
        while (0 < (packetBuffer.readByte() & 0xFF)) {
            final ItemStack itemStackFromBuffer = packetBuffer.readItemStackFromBuffer();
            final ItemStack itemStackFromBuffer2 = packetBuffer.readItemStackFromBuffer();
            ItemStack itemStackFromBuffer3 = null;
            if (packetBuffer.readBoolean()) {
                itemStackFromBuffer3 = packetBuffer.readItemStackFromBuffer();
            }
            final boolean boolean1 = packetBuffer.readBoolean();
            final MerchantRecipe merchantRecipe = new MerchantRecipe(itemStackFromBuffer, itemStackFromBuffer3, itemStackFromBuffer2, packetBuffer.readInt(), packetBuffer.readInt());
            if (boolean1) {
                merchantRecipe.compensateToolUses();
            }
            list.add(merchantRecipe);
            int n = 0;
            ++n;
        }
        return list;
    }
    
    public MerchantRecipeList(final NBTTagCompound nbtTagCompound) {
        this.readRecipiesFromTags(nbtTagCompound);
    }
    
    public MerchantRecipeList() {
    }
}
