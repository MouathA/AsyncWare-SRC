package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.io.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;

public class S38PacketPlayerListItem implements Packet
{
    private Action action;
    private final List players;
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handlePlayerListItem(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.action = (Action)packetBuffer.readEnumValue(Action.class);
        while (0 < packetBuffer.readVarIntFromBuffer()) {
            GameProfile gameProfile = null;
            WorldSettings.GameType gameType = null;
            IChatComponent chatComponent = null;
            switch (S38PacketPlayerListItem$1.$SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[this.action.ordinal()]) {
                case 1: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), packetBuffer.readStringFromBuffer(16));
                    while (0 < packetBuffer.readVarIntFromBuffer()) {
                        final String stringFromBuffer = packetBuffer.readStringFromBuffer(32767);
                        final String stringFromBuffer2 = packetBuffer.readStringFromBuffer(32767);
                        if (packetBuffer.readBoolean()) {
                            gameProfile.getProperties().put((Object)stringFromBuffer, (Object)new Property(stringFromBuffer, stringFromBuffer2, packetBuffer.readStringFromBuffer(32767)));
                        }
                        else {
                            gameProfile.getProperties().put((Object)stringFromBuffer, (Object)new Property(stringFromBuffer, stringFromBuffer2));
                        }
                        int n = 0;
                        ++n;
                    }
                    gameType = WorldSettings.GameType.getByID(packetBuffer.readVarIntFromBuffer());
                    packetBuffer.readVarIntFromBuffer();
                    if (packetBuffer.readBoolean()) {
                        chatComponent = packetBuffer.readChatComponent();
                        break;
                    }
                    break;
                }
                case 2: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), (String)null);
                    gameType = WorldSettings.GameType.getByID(packetBuffer.readVarIntFromBuffer());
                    break;
                }
                case 3: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), (String)null);
                    packetBuffer.readVarIntFromBuffer();
                    break;
                }
                case 4: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), (String)null);
                    if (packetBuffer.readBoolean()) {
                        chatComponent = packetBuffer.readChatComponent();
                        break;
                    }
                    break;
                }
                case 5: {
                    gameProfile = new GameProfile(packetBuffer.readUuid(), (String)null);
                    break;
                }
            }
            this.players.add(new AddPlayerData(gameProfile, 0, gameType, chatComponent));
            int n2 = 0;
            ++n2;
        }
    }
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("action", (Object)this.action).add("entries", (Object)this.players).toString();
    }
    
    public S38PacketPlayerListItem() {
        this.players = Lists.newArrayList();
    }
    
    public Action func_179768_b() {
        return this.action;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeVarIntToBuffer(this.players.size());
        for (final AddPlayerData addPlayerData : this.players) {
            switch (S38PacketPlayerListItem$1.$SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[this.action.ordinal()]) {
                case 1: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    packetBuffer.writeString(addPlayerData.getProfile().getName());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getProfile().getProperties().size());
                    for (final Property property : addPlayerData.getProfile().getProperties().values()) {
                        packetBuffer.writeString(property.getName());
                        packetBuffer.writeString(property.getValue());
                        if (property.hasSignature()) {
                            packetBuffer.writeBoolean(true);
                            packetBuffer.writeString(property.getSignature());
                        }
                        else {
                            packetBuffer.writeBoolean(false);
                        }
                    }
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getGameMode().getID());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getPing());
                    if (addPlayerData.getDisplayName() == null) {
                        packetBuffer.writeBoolean(false);
                        continue;
                    }
                    packetBuffer.writeBoolean(true);
                    packetBuffer.writeChatComponent(addPlayerData.getDisplayName());
                    continue;
                }
                case 2: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getGameMode().getID());
                    continue;
                }
                case 3: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    packetBuffer.writeVarIntToBuffer(addPlayerData.getPing());
                    continue;
                }
                case 4: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    if (addPlayerData.getDisplayName() == null) {
                        packetBuffer.writeBoolean(false);
                        continue;
                    }
                    packetBuffer.writeBoolean(true);
                    packetBuffer.writeChatComponent(addPlayerData.getDisplayName());
                    continue;
                }
                case 5: {
                    packetBuffer.writeUuid(addPlayerData.getProfile().getId());
                    continue;
                }
            }
        }
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public S38PacketPlayerListItem(final Action action, final Iterable iterable) {
        this.players = Lists.newArrayList();
        this.action = action;
        for (final EntityPlayerMP entityPlayerMP : iterable) {
            this.players.add(new AddPlayerData(entityPlayerMP.getGameProfile(), entityPlayerMP.ping, entityPlayerMP.theItemInWorldManager.getGameType(), entityPlayerMP.getTabListDisplayName()));
        }
    }
    
    public List func_179767_a() {
        return this.players;
    }
    
    public S38PacketPlayerListItem(final Action action, final EntityPlayerMP... array) {
        this.players = Lists.newArrayList();
        this.action = action;
        while (0 < array.length) {
            final EntityPlayerMP entityPlayerMP = array[0];
            this.players.add(new AddPlayerData(entityPlayerMP.getGameProfile(), entityPlayerMP.ping, entityPlayerMP.theItemInWorldManager.getGameType(), entityPlayerMP.getTabListDisplayName()));
            int n = 0;
            ++n;
        }
    }
    
    public class AddPlayerData
    {
        private final GameProfile profile;
        final S38PacketPlayerListItem this$0;
        private final IChatComponent displayName;
        private final int ping;
        private final WorldSettings.GameType gamemode;
        
        @Override
        public String toString() {
            return Objects.toStringHelper((Object)this).add("latency", this.ping).add("gameMode", (Object)this.gamemode).add("profile", (Object)this.profile).add("displayName", (Object)((this.displayName == null) ? null : IChatComponent.Serializer.componentToJson(this.displayName))).toString();
        }
        
        public GameProfile getProfile() {
            return this.profile;
        }
        
        public IChatComponent getDisplayName() {
            return this.displayName;
        }
        
        public int getPing() {
            return this.ping;
        }
        
        public AddPlayerData(final S38PacketPlayerListItem this$0, final GameProfile profile, final int ping, final WorldSettings.GameType gamemode, final IChatComponent displayName) {
            this.this$0 = this$0;
            this.profile = profile;
            this.ping = ping;
            this.gamemode = gamemode;
            this.displayName = displayName;
        }
        
        public WorldSettings.GameType getGameMode() {
            return this.gamemode;
        }
    }
    
    public enum Action
    {
        UPDATE_LATENCY("UPDATE_LATENCY", 2), 
        ADD_PLAYER("ADD_PLAYER", 0);
        
        private static final Action[] $VALUES;
        
        UPDATE_DISPLAY_NAME("UPDATE_DISPLAY_NAME", 3), 
        REMOVE_PLAYER("REMOVE_PLAYER", 4), 
        UPDATE_GAME_MODE("UPDATE_GAME_MODE", 1);
        
        static {
            $VALUES = new Action[] { Action.ADD_PLAYER, Action.UPDATE_GAME_MODE, Action.UPDATE_LATENCY, Action.UPDATE_DISPLAY_NAME, Action.REMOVE_PLAYER };
        }
        
        private Action(final String s, final int n) {
        }
    }
}
