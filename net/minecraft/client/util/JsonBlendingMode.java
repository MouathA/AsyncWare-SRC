package net.minecraft.client.util;

import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import com.google.gson.*;

public class JsonBlendingMode
{
    private final int field_148116_b;
    private static JsonBlendingMode field_148118_a;
    private final boolean field_148113_g;
    private final int field_148115_e;
    private final int field_148112_f;
    private final boolean field_148119_h;
    private final int field_148114_d;
    private final int field_148117_c;
    
    public JsonBlendingMode() {
        this(false, true, 1, 0, 1, 0, 32774);
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * this.field_148116_b + this.field_148117_c) + this.field_148114_d) + this.field_148115_e) + this.field_148112_f) + (this.field_148113_g ? 1 : 0)) + (this.field_148119_h ? 1 : 0);
    }
    
    static {
        JsonBlendingMode.field_148118_a = null;
    }
    
    public JsonBlendingMode(final int n, final int n2, final int n3) {
        this(false, false, n, n2, n, n2, n3);
    }
    
    private JsonBlendingMode(final boolean field_148113_g, final boolean field_148119_h, final int field_148116_b, final int field_148114_d, final int field_148117_c, final int field_148115_e, final int field_148112_f) {
        this.field_148113_g = field_148113_g;
        this.field_148116_b = field_148116_b;
        this.field_148114_d = field_148114_d;
        this.field_148117_c = field_148117_c;
        this.field_148115_e = field_148115_e;
        this.field_148119_h = field_148119_h;
        this.field_148112_f = field_148112_f;
    }
    
    public JsonBlendingMode(final int n, final int n2, final int n3, final int n4, final int n5) {
        this(true, false, n, n2, n3, n4, n5);
    }
    
    public boolean func_148111_b() {
        return this.field_148119_h;
    }
    
    private static int func_148108_a(final String s) {
        final String lowerCase = s.trim().toLowerCase();
        return lowerCase.equals("add") ? 32774 : (lowerCase.equals("subtract") ? 32778 : (lowerCase.equals("reversesubtract") ? 32779 : (lowerCase.equals("reverse_subtract") ? 32779 : (lowerCase.equals("min") ? 32775 : (lowerCase.equals("max") ? 32776 : 32774)))));
    }
    
    private static int func_148107_b(final String s) {
        final String replaceAll = s.trim().toLowerCase().replaceAll("_", "").replaceAll("one", "1").replaceAll("zero", "0").replaceAll("minus", "-");
        return replaceAll.equals("0") ? 0 : (replaceAll.equals("1") ? 1 : (replaceAll.equals("srccolor") ? 768 : (replaceAll.equals("1-srccolor") ? 769 : (replaceAll.equals("dstcolor") ? 774 : (replaceAll.equals("1-dstcolor") ? 775 : (replaceAll.equals("srcalpha") ? 770 : (replaceAll.equals("1-srcalpha") ? 771 : (replaceAll.equals("dstalpha") ? 772 : (replaceAll.equals("1-dstalpha") ? 773 : -1)))))))));
    }
    
    public void func_148109_a() {
        if (this == JsonBlendingMode.field_148118_a) {
            if (JsonBlendingMode.field_148118_a == null || this.field_148119_h != JsonBlendingMode.field_148118_a.func_148111_b()) {
                JsonBlendingMode.field_148118_a = this;
                if (this.field_148119_h) {
                    return;
                }
            }
            GL14.glBlendEquation(this.field_148112_f);
            if (this.field_148113_g) {
                GlStateManager.tryBlendFuncSeparate(this.field_148116_b, this.field_148114_d, this.field_148117_c, this.field_148115_e);
            }
            else {
                GlStateManager.blendFunc(this.field_148116_b, this.field_148114_d);
            }
        }
    }
    
    public static JsonBlendingMode func_148110_a(final JsonObject p0) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/minecraft/client/util/JsonBlendingMode.func_148110_a:(Lcom/google/gson/JsonObject;)Lnet/minecraft/client/util/JsonBlendingMode;'.
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 17.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
