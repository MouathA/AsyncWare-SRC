package net.minecraft.tileentity;

import net.minecraft.entity.player.*;
import net.minecraft.command.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.event.*;
import net.minecraft.server.*;
import net.minecraft.network.*;
import com.nquantum.*;
import net.minecraft.network.play.server.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;

public class TileEntitySign extends TileEntity
{
    private EntityPlayer player;
    private final CommandResultStats stats;
    private boolean isEditable;
    public int lineBeingEdited;
    public final IChatComponent[] signText;
    
    @Override
    public boolean func_183000_F() {
        return true;
    }
    
    public boolean executeCommand(final EntityPlayer entityPlayer) {
        final ICommandSender commandSender = new ICommandSender(this, entityPlayer) {
            final TileEntitySign this$0;
            final EntityPlayer val$playerIn;
            
            @Override
            public String getName() {
                return this.val$playerIn.getName();
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.pos.getX() + 0.5, this.this$0.pos.getY() + 0.5, this.this$0.pos.getZ() + 0.5);
            }
            
            @Override
            public BlockPos getPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public World getEntityWorld() {
                return this.val$playerIn.getEntityWorld();
            }
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
            }
            
            @Override
            public void setCommandStat(final CommandResultStats.Type type, final int n) {
                TileEntitySign.access$000(this.this$0).func_179672_a(this, type, n);
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                return n <= 2;
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return this.val$playerIn;
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return this.val$playerIn.getDisplayName();
            }
            
            @Override
            public boolean sendCommandFeedback() {
                return false;
            }
        };
        while (0 < this.signText.length) {
            final ChatStyle chatStyle = (this.signText[0] == null) ? null : this.signText[0].getChatStyle();
            if (chatStyle != null && chatStyle.getChatClickEvent() != null) {
                final ClickEvent chatClickEvent = chatStyle.getChatClickEvent();
                if (chatClickEvent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    MinecraftServer.getServer().getCommandManager().executeCommand(commandSender, chatClickEvent.getValue());
                }
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        if (Asyncware.instance.moduleManager.getModuleByName("No Sign Render").isToggled()) {
            return null;
        }
        final IChatComponent[] array = new IChatComponent[4];
        System.arraycopy(this.signText, 0, array, 0, 4);
        return new S33PacketUpdateSign(this.worldObj, this.pos, array);
    }
    
    public boolean getIsEditable() {
        return this.isEditable;
    }
    
    public void setPlayer(final EntityPlayer player) {
        this.player = player;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        if (Asyncware.instance.moduleManager.getModuleByName("No Sign Render").isToggled()) {
            return;
        }
        super.writeToNBT(nbtTagCompound);
        while (true) {
            nbtTagCompound.setString("Text" + 1, IChatComponent.Serializer.componentToJson(this.signText[0]));
            int n = 0;
            ++n;
        }
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public void setEditable(final boolean isEditable) {
        if (!(this.isEditable = isEditable)) {
            this.player = null;
        }
    }
    
    public TileEntitySign() {
        this.signText = new IChatComponent[] { new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText(""), new ChatComponentText("") };
        this.lineBeingEdited = -1;
        this.isEditable = true;
        this.stats = new CommandResultStats();
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        if (Asyncware.instance.moduleManager.getModuleByName("No Sign Render").isToggled()) {
            return;
        }
        this.isEditable = false;
        super.readFromNBT(nbtTagCompound);
        final ICommandSender commandSender = new ICommandSender(this) {
            final TileEntitySign this$0;
            
            @Override
            public BlockPos getPosition() {
                return this.this$0.pos;
            }
            
            @Override
            public boolean canCommandSenderUseCommand(final int n, final String s) {
                return true;
            }
            
            @Override
            public Vec3 getPositionVector() {
                return new Vec3(this.this$0.pos.getX() + 0.5, this.this$0.pos.getY() + 0.5, this.this$0.pos.getZ() + 0.5);
            }
            
            @Override
            public World getEntityWorld() {
                return this.this$0.worldObj;
            }
            
            @Override
            public void setCommandStat(final CommandResultStats.Type type, final int n) {
            }
            
            @Override
            public Entity getCommandSenderEntity() {
                return null;
            }
            
            @Override
            public void addChatMessage(final IChatComponent chatComponent) {
            }
            
            @Override
            public IChatComponent getDisplayName() {
                return new ChatComponentText(this.getName());
            }
            
            @Override
            public String getName() {
                return "Sign";
            }
            
            @Override
            public boolean sendCommandFeedback() {
                return false;
            }
        };
        while (true) {
            this.signText[0] = ChatComponentProcessor.processComponent(commandSender, IChatComponent.Serializer.jsonToComponent(nbtTagCompound.getString("Text" + 1)), null);
            int n = 0;
            ++n;
        }
    }
    
    static CommandResultStats access$000(final TileEntitySign tileEntitySign) {
        return tileEntitySign.stats;
    }
    
    public CommandResultStats getStats() {
        return this.stats;
    }
}
