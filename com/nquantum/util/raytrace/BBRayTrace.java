package com.nquantum.util.raytrace;

import net.minecraft.client.*;
import net.minecraft.util.*;
import java.util.*;

public class BBRayTrace
{
    private Minecraft mc;
    private boolean hitBlock;
    private ArrayList hitBlocks;
    private BlockPos highestBlock;
    private int highestHitBlockHeight;
    
    public int getHighestHitBlockHeight() {
        return this.highestHitBlockHeight;
    }
    
    public BBRayTrace(final Vec3 vec3, final Vec3 vec4, final int n, final double n2) {
        this.mc = Minecraft.getMinecraft();
        this.highestBlock = null;
        this.hitBlocks = new ArrayList();
        this.mc.thePlayer.boundingBox.expand(n2, 0.0, n2);
        final double n3 = vec4.xCoord - vec3.xCoord;
        final double n4 = vec4.yCoord - vec3.yCoord;
        final double n5 = vec4.zCoord - vec3.zCoord;
        if (this.hitBlocks.isEmpty()) {
            this.hitBlock = false;
            return;
        }
        this.hitBlock = true;
        for (final BlockPos highestBlock : this.hitBlocks) {
            if (highestBlock.getY() > -1000) {
                highestBlock.getY();
                this.highestBlock = highestBlock;
            }
        }
    }
    
    public boolean didHitBlock() {
        return this.hitBlock;
    }
    
    public ArrayList getHitBlocks() {
        return this.hitBlocks;
    }
    
    public BlockPos getHighestBlock() {
        return this.highestBlock;
    }
}
