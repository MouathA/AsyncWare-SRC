package net.minecraft.client.renderer.entity;

import net.minecraft.entity.ai.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;

public class RenderMinecartMobSpawner extends RenderMinecart
{
    public RenderMinecartMobSpawner(final RenderManager renderManager) {
        super(renderManager);
    }
    
    protected void func_180560_a(final EntityMinecartMobSpawner entityMinecartMobSpawner, final float n, final IBlockState blockState) {
        super.func_180560_a(entityMinecartMobSpawner, n, blockState);
        if (blockState.getBlock() == Blocks.mob_spawner) {
            TileEntityMobSpawnerRenderer.renderMob(entityMinecartMobSpawner.func_98039_d(), entityMinecartMobSpawner.posX, entityMinecartMobSpawner.posY, entityMinecartMobSpawner.posZ, n);
        }
    }
    
    @Override
    protected void func_180560_a(final EntityMinecart entityMinecart, final float n, final IBlockState blockState) {
        this.func_180560_a((EntityMinecartMobSpawner)entityMinecart, n, blockState);
    }
}
