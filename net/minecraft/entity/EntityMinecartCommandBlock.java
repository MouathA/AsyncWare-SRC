package net.minecraft.entity;

import net.minecraft.entity.item.*;
import net.minecraft.command.server.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import io.netty.buffer.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class EntityMinecartCommandBlock extends EntityMinecart
{
    private final CommandBlockLogic commandBlockLogic;
    private int activatorRailCooldown;
    
    @Override
    public IBlockState getDefaultDisplayTile() {
        return Blocks.command_block.getDefaultState();
    }
    
    @Override
    public EnumMinecartType getMinecartType() {
        return EnumMinecartType.COMMAND_BLOCK;
    }
    
    @Override
    public boolean interactFirst(final EntityPlayer entityPlayer) {
        this.commandBlockLogic.tryOpenEditCommandBlock(entityPlayer);
        return false;
    }
    
    public CommandBlockLogic getCommandBlockLogic() {
        return this.commandBlockLogic;
    }
    
    public EntityMinecartCommandBlock(final World world, final double n, final double n2, final double n3) {
        super(world, n, n2, n3);
        this.commandBlockLogic = new CommandBlockLogic() {
            final EntityMinecartCommandBlock this$0;
            
            @Override
            public void updateCommand() {
                this.this$0.getDataWatcher().updateObject(23, this.getCommand());
                this.this$0.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
            }
            
            @Override
            public int func_145751_f() {
                return 1;
            }
            
            @Override
            public BlockPos getPosition() {
                return new BlockPos(this.this$0.posX, this.this$0.posY + 0.5, this.this$0.posZ);
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.getEntityId());
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.posX, this.this$0.posY, this.this$0.posZ);
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.this$0;
            }
        };
        this.activatorRailCooldown = 0;
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        this.commandBlockLogic.writeDataToNBT(nbtTagCompound);
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.commandBlockLogic.readDataFromNBT(nbtTagCompound);
        this.getDataWatcher().updateObject(23, this.getCommandBlockLogic().getCommand());
        this.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getCommandBlockLogic().getLastOutput()));
    }
    
    @Override
    public void onDataWatcherUpdate(final int n) {
        super.onDataWatcherUpdate(n);
        if (n == 24) {
            this.commandBlockLogic.setLastOutput(IChatComponent.Serializer.jsonToComponent(this.getDataWatcher().getWatchableObjectString(24)));
        }
        else if (n == 23) {
            this.commandBlockLogic.setCommand(this.getDataWatcher().getWatchableObjectString(23));
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(23, "");
        this.getDataWatcher().addObject(24, "");
    }
    
    public EntityMinecartCommandBlock(final World world) {
        super(world);
        this.commandBlockLogic = new CommandBlockLogic() {
            final EntityMinecartCommandBlock this$0;
            
            @Override
            public void updateCommand() {
                this.this$0.getDataWatcher().updateObject(23, this.getCommand());
                this.this$0.getDataWatcher().updateObject(24, IChatComponent.Serializer.componentToJson(this.getLastOutput()));
            }
            
            @Override
            public int func_145751_f() {
                return 1;
            }
            
            @Override
            public BlockPos getPosition() {
                return new BlockPos(this.this$0.posX, this.this$0.posY + 0.5, this.this$0.posZ);
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.getEntityId());
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.posX, this.this$0.posY, this.this$0.posZ);
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.this$0;
            }
        };
        this.activatorRailCooldown = 0;
    }
    
    @Override
    public void onActivatorRailPass(final int n, final int n2, final int n3, final boolean b) {
        if (b && this.ticksExisted - this.activatorRailCooldown >= 4) {
            this.getCommandBlockLogic().trigger(this.worldObj);
            this.activatorRailCooldown = this.ticksExisted;
        }
    }
}
