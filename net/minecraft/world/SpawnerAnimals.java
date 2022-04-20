package net.minecraft.world;

import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;

public final class SpawnerAnimals
{
    private static final int MOB_COUNT_DIV;
    private final Set eligibleChunksForSpawning;
    
    public static void performWorldGenSpawning(final World world, final BiomeGenBase biomeGenBase, final int n, final int n2, final int n3, final int n4, final Random random) {
        final List spawnableList = biomeGenBase.getSpawnableList(EnumCreatureType.CREATURE);
        if (!spawnableList.isEmpty()) {
            while (random.nextFloat() < biomeGenBase.getSpawningChance()) {
                final BiomeGenBase.SpawnListEntry spawnListEntry = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(world.rand, spawnableList);
                final int n5 = spawnListEntry.minGroupCount + random.nextInt(1 + spawnListEntry.maxGroupCount - spawnListEntry.minGroupCount);
                final int n6 = n + random.nextInt(n3);
                final int n7 = n2 + random.nextInt(n4);
                while (0 < n5) {
                    int n8 = 0;
                    ++n8;
                }
            }
        }
    }
    
    public int findChunksForSpawning(final WorldServer p0, final boolean p1, final boolean p2, final boolean p3) {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        // The error that occurred was:
        // 
        // com.strobel.assembler.metadata.MethodBodyParseException: An error occurred while parsing the bytecode of method 'net/minecraft/world/SpawnerAnimals.findChunksForSpawning:(Lnet/minecraft/world/WorldServer;ZZZ)I'.
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
        // Caused by: java.lang.IndexOutOfBoundsException: No instruction found at offset 87.
        //     at com.strobel.assembler.ir.InstructionCollection.atOffset(InstructionCollection.java:38)
        //     at com.strobel.assembler.metadata.MethodReader.readBodyCore(MethodReader.java:235)
        //     at com.strobel.assembler.metadata.MethodReader.readBody(MethodReader.java:62)
        //     ... 17 more
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public SpawnerAnimals() {
        this.eligibleChunksForSpawning = Sets.newHashSet();
    }
    
    protected static BlockPos getRandomChunkPosition(final World world, final int n, final int n2) {
        final Chunk chunkFromChunkCoords = world.getChunkFromChunkCoords(n, n2);
        final int n3 = n * 16 + world.rand.nextInt(16);
        final int n4 = n2 * 16 + world.rand.nextInt(16);
        final int func_154354_b = MathHelper.func_154354_b(chunkFromChunkCoords.getHeight(new BlockPos(n3, 0, n4)) + 1, 16);
        return new BlockPos(n3, world.rand.nextInt((func_154354_b > 0) ? func_154354_b : (chunkFromChunkCoords.getTopFilledSegment() + 16 - 1)), n4);
    }
    
    static {
        MOB_COUNT_DIV = (int)Math.pow(17.0, 2.0);
    }
}
