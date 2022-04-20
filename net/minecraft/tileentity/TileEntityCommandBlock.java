package net.minecraft.tileentity;

import net.minecraft.command.server.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import io.netty.buffer.*;
import net.minecraft.command.*;

public class TileEntityCommandBlock extends TileEntity
{
    private final CommandBlockLogic commandBlockLogic;
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        this.commandBlockLogic.writeDataToNBT(nbtTagCompound);
    }
    
    public CommandBlockLogic getCommandBlockLogic() {
        return this.commandBlockLogic;
    }
    
    @Override
    public boolean func_183000_F() {
        return true;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.commandBlockLogic.readDataFromNBT(nbtTagCompound);
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 2, nbtTagCompound);
    }
    
    public TileEntityCommandBlock() {
        this.commandBlockLogic = new CommandBlockLogic() {
            final TileEntityCommandBlock this$0;
            
            @Override
            public int func_145751_f() {
                return 0;
            }
            
            @Override
            public void updateCommand() {
                this.this$0.getWorld().markBlockForUpdate(this.this$0.pos);
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return null;
            }
            
            @Override
            public void setCommand(final String command) {
                super.setCommand(command);
                this.this$0.markDirty();
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.getWorld();
            }
            
            @Override
            public BlockPos getPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.pos.getX() + 0.5, this.this$0.pos.getY() + 0.5, this.this$0.pos.getZ() + 0.5);
            }
            
            @Override
            public void func_145757_a(final ByteBuf byteBuf) {
                byteBuf.writeInt(this.this$0.pos.getX());
                byteBuf.writeInt(this.this$0.pos.getY());
                byteBuf.writeInt(this.this$0.pos.getZ());
            }
        };
    }
    
    public CommandResultStats getCommandResultStats() {
        return this.commandBlockLogic.getCommandResultStats();
    }
}
