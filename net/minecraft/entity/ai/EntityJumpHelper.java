package net.minecraft.entity.ai;

import net.minecraft.entity.*;

public class EntityJumpHelper
{
    protected boolean isJumping;
    private EntityLiving entity;
    
    public EntityJumpHelper(final EntityLiving entity) {
        this.entity = entity;
    }
    
    public void setJumping() {
        this.isJumping = true;
    }
    
    public void doJump() {
        this.entity.setJumping(this.isJumping);
        this.isJumping = false;
    }
}
