package optfine;

import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RandomMobsRule
{
    private RangeListInt heights;
    private int[] skins;
    private int[] weights;
    public int sumAllWeights;
    private BiomeGenBase[] biomes;
    private ResourceLocation baseResLoc;
    public int[] sumWeights;
    private ResourceLocation[] resourceLocations;
    
    public boolean matches(final EntityLiving p0) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'optfine/RandomMobsRule.matches:(Lnet/minecraft/entity/EntityLiving;)Z'.
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 3.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public RandomMobsRule(final ResourceLocation baseResLoc, final int[] skins, final int[] weights, final BiomeGenBase[] biomes, final RangeListInt heights) {
        this.baseResLoc = null;
        this.skins = null;
        this.resourceLocations = null;
        this.weights = null;
        this.biomes = null;
        this.heights = null;
        this.sumWeights = null;
        this.sumAllWeights = 1;
        this.baseResLoc = baseResLoc;
        this.skins = skins;
        this.weights = weights;
        this.biomes = biomes;
        this.heights = heights;
    }
    
    public boolean isValid(final String s) {
        this.resourceLocations = new ResourceLocation[this.skins.length];
        if (RandomMobs.getMcpatcherLocation(this.baseResLoc) == null) {
            Config.warn("Invalid path: " + this.baseResLoc.getResourcePath());
            return false;
        }
        int average = 0;
        while (0 < this.resourceLocations.length) {
            average = this.skins[0];
            this.resourceLocations[0] = this.baseResLoc;
            int n = 0;
            ++n;
        }
        if (this.weights != null) {
            if (this.weights.length > this.resourceLocations.length) {
                Config.warn("More weights defined than skins, trimming weights: " + s);
                final int[] weights = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, 0, weights, 0, weights.length);
                this.weights = weights;
            }
            if (this.weights.length < this.resourceLocations.length) {
                Config.warn("Less weights defined than skins, expanding weights: " + s);
                final int[] weights2 = new int[this.resourceLocations.length];
                System.arraycopy(this.weights, 0, weights2, 0, this.weights.length);
                average = ConnectedUtils.getAverage(this.weights);
                for (int i = this.weights.length; i < weights2.length; ++i) {
                    weights2[i] = 0;
                }
                this.weights = weights2;
            }
            this.sumWeights = new int[this.weights.length];
            while (0 < this.weights.length) {
                if (this.weights[0] < 0) {
                    Config.warn("Invalid weight: " + this.weights[0]);
                    return false;
                }
                final int n = 0 + this.weights[0];
                this.sumWeights[0] = 0;
                ++average;
            }
            this.sumAllWeights = 0;
            if (this.sumAllWeights <= 0) {
                Config.warn("Invalid sum of all weights: " + 0);
                this.sumAllWeights = 1;
            }
        }
        return true;
    }
    
    public ResourceLocation getTextureLocation(final ResourceLocation resourceLocation, final int n) {
        if (this.weights == null) {
            final int n2 = n % this.resourceLocations.length;
        }
        else {
            final int n3 = n % this.sumAllWeights;
            while (0 < this.sumWeights.length) {
                if (this.sumWeights[0] > n3) {
                    break;
                }
                int n4 = 0;
                ++n4;
            }
        }
        return this.resourceLocations[0];
    }
}
