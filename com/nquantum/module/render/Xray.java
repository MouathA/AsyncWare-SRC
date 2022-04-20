package com.nquantum.module.render;

import java.util.*;
import com.nquantum.event.impl.*;
import net.minecraft.util.*;
import com.nquantum.event.*;
import net.minecraft.block.*;
import com.nquantum.module.*;

public class Xray extends Module
{
    public ArrayList xrayBlocks;
    private int zPos;
    private int yPos;
    private int xPos;
    
    @Override
    public void setup() {
        super.setup();
    }
    
    @Punjabi
    @Override
    public void onUpdate(final EventUpdate eventUpdate) {
        for (int i = -20; i < 20; ++i) {
            for (int j = 20; j > -20; --j) {
                for (int k = -20; k < 20; ++k) {
                    this.xPos = (int)this.mc.thePlayer.posX + i;
                    this.yPos = (int)this.mc.thePlayer.posY + j;
                    this.zPos = (int)this.mc.thePlayer.posZ + k;
                    this.mc.theWorld.getBlockState(new BlockPos(this.xPos, this.yPos, this.zPos)).getBlock();
                }
            }
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.renderGlobal.loadRenderers();
    }
    
    public boolean isCapable(final Block block) {
        return this.xrayBlocks.contains(block);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.mc.renderGlobal.loadRenderers();
    }
    
    public Xray() {
        super("Xray", 49, Category.RENDER);
        (this.xrayBlocks = new ArrayList()).add(Block.getBlockFromName("coal_ore"));
        this.xrayBlocks.add(Block.getBlockFromName("iron_ore"));
        this.xrayBlocks.add(Block.getBlockFromName("gold_ore"));
        this.xrayBlocks.add(Block.getBlockFromName("redstone_ore"));
        this.xrayBlocks.add(Block.getBlockById(74));
        this.xrayBlocks.add(Block.getBlockFromName("lapis_ore"));
        this.xrayBlocks.add(Block.getBlockFromName("diamond_ore"));
        this.xrayBlocks.add(Block.getBlockFromName("emerald_ore"));
        this.xrayBlocks.add(Block.getBlockFromName("quartz_ore"));
        this.xrayBlocks.add(Block.getBlockFromName("clay"));
        this.xrayBlocks.add(Block.getBlockFromName("glowstone"));
        this.xrayBlocks.add(Block.getBlockById(8));
        this.xrayBlocks.add(Block.getBlockById(9));
        this.xrayBlocks.add(Block.getBlockById(10));
        this.xrayBlocks.add(Block.getBlockById(11));
        this.xrayBlocks.add(Block.getBlockFromName("crafting_table"));
        this.xrayBlocks.add(Block.getBlockById(61));
        this.xrayBlocks.add(Block.getBlockById(62));
        this.xrayBlocks.add(Block.getBlockFromName("torch"));
        this.xrayBlocks.add(Block.getBlockFromName("ladder"));
        this.xrayBlocks.add(Block.getBlockFromName("tnt"));
        this.xrayBlocks.add(Block.getBlockFromName("coal_block"));
        this.xrayBlocks.add(Block.getBlockFromName("iron_block"));
        this.xrayBlocks.add(Block.getBlockFromName("gold_block"));
        this.xrayBlocks.add(Block.getBlockFromName("diamond_block"));
        this.xrayBlocks.add(Block.getBlockFromName("emerald_block"));
        this.xrayBlocks.add(Block.getBlockFromName("redstone_block"));
        this.xrayBlocks.add(Block.getBlockFromName("lapis_block"));
        this.xrayBlocks.add(Block.getBlockFromName("fire"));
        this.xrayBlocks.add(Block.getBlockFromName("mossy_cobblestone"));
        this.xrayBlocks.add(Block.getBlockFromName("mob_spawner"));
        this.xrayBlocks.add(Block.getBlockFromName("end_portal_frame"));
        this.xrayBlocks.add(Block.getBlockFromName("enchanting_table"));
        this.xrayBlocks.add(Block.getBlockFromName("bookshelf"));
        this.xrayBlocks.add(Block.getBlockFromName("command_block"));
        this.xrayBlocks.add(Block.getBlockFromName("bone_block"));
    }
}
