package net.minecraft.client.network;

import com.mojang.authlib.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import com.google.common.base.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import com.mojang.authlib.minecraft.*;

public class NetworkPlayerInfo
{
    private final GameProfile gameProfile;
    private IChatComponent displayName;
    private ResourceLocation locationSkin;
    private ResourceLocation locationCape;
    private String skinType;
    private long field_178871_k;
    private WorldSettings.GameType gameType;
    private int field_178873_i;
    private int field_178870_j;
    private long field_178868_l;
    private int responseTime;
    private long field_178869_m;
    private boolean playerTexturesLoaded;
    
    public long func_178847_n() {
        return this.field_178871_k;
    }
    
    public String getSkinType() {
        return (this.skinType == null) ? DefaultPlayerSkin.getSkinType(this.gameProfile.getId()) : this.skinType;
    }
    
    static ResourceLocation access$002(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation locationSkin) {
        return networkPlayerInfo.locationSkin = locationSkin;
    }
    
    static String access$102(final NetworkPlayerInfo networkPlayerInfo, final String skinType) {
        return networkPlayerInfo.skinType = skinType;
    }
    
    public int func_178835_l() {
        return this.field_178873_i;
    }
    
    public WorldSettings.GameType getGameType() {
        return this.gameType;
    }
    
    public IChatComponent getDisplayName() {
        return this.displayName;
    }
    
    public int getResponseTime() {
        return this.responseTime;
    }
    
    protected void setGameType(final WorldSettings.GameType gameType) {
        this.gameType = gameType;
    }
    
    public long func_178858_o() {
        return this.field_178868_l;
    }
    
    public long func_178855_p() {
        return this.field_178869_m;
    }
    
    public NetworkPlayerInfo(final S38PacketPlayerListItem.AddPlayerData addPlayerData) {
        this.playerTexturesLoaded = false;
        this.field_178873_i = 0;
        this.field_178870_j = 0;
        this.field_178871_k = 0L;
        this.field_178868_l = 0L;
        this.field_178869_m = 0L;
        this.gameProfile = addPlayerData.getProfile();
        this.gameType = addPlayerData.getGameMode();
        this.responseTime = addPlayerData.getPing();
        this.displayName = addPlayerData.getDisplayName();
    }
    
    public void func_178843_c(final long field_178869_m) {
        this.field_178869_m = field_178869_m;
    }
    
    public ResourceLocation getLocationSkin() {
        if (this.locationSkin == null) {
            this.loadPlayerTextures();
        }
        return (ResourceLocation)Objects.firstNonNull((Object)this.locationSkin, (Object)DefaultPlayerSkin.getDefaultSkin(this.gameProfile.getId()));
    }
    
    public ResourceLocation getLocationCape() {
        if (this.locationCape == null) {
            this.loadPlayerTextures();
        }
        return this.locationCape;
    }
    
    protected void setResponseTime(final int responseTime) {
        this.responseTime = responseTime;
    }
    
    public void func_178857_c(final int field_178870_j) {
        this.field_178870_j = field_178870_j;
    }
    
    static ResourceLocation access$202(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation locationCape) {
        return networkPlayerInfo.locationCape = locationCape;
    }
    
    public NetworkPlayerInfo(final GameProfile gameProfile) {
        this.playerTexturesLoaded = false;
        this.field_178873_i = 0;
        this.field_178870_j = 0;
        this.field_178871_k = 0L;
        this.field_178868_l = 0L;
        this.field_178869_m = 0L;
        this.gameProfile = gameProfile;
    }
    
    public void func_178846_a(final long field_178871_k) {
        this.field_178871_k = field_178871_k;
    }
    
    public boolean hasLocationSkin() {
        return this.locationSkin != null;
    }
    
    public void func_178836_b(final int field_178873_i) {
        this.field_178873_i = field_178873_i;
    }
    
    static String access$100(final NetworkPlayerInfo networkPlayerInfo) {
        return networkPlayerInfo.skinType;
    }
    
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
    
    public ScorePlayerTeam getPlayerTeam() {
        return Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(this.getGameProfile().getName());
    }
    
    public int func_178860_m() {
        return this.field_178870_j;
    }
    
    public void func_178844_b(final long field_178868_l) {
        this.field_178868_l = field_178868_l;
    }
    
    protected void loadPlayerTextures() {
        // monitorenter(this)
        if (!this.playerTexturesLoaded) {
            this.playerTexturesLoaded = true;
            Minecraft.getMinecraft().getSkinManager().loadProfileTextures(this.gameProfile, new SkinManager.SkinAvailableCallback(this) {
                final NetworkPlayerInfo this$0;
                
                @Override
                public void skinAvailable(final MinecraftProfileTexture.Type type, final ResourceLocation resourceLocation, final MinecraftProfileTexture minecraftProfileTexture) {
                    switch (NetworkPlayerInfo$2.$SwitchMap$com$mojang$authlib$minecraft$MinecraftProfileTexture$Type[type.ordinal()]) {
                        case 1: {
                            NetworkPlayerInfo.access$002(this.this$0, resourceLocation);
                            NetworkPlayerInfo.access$102(this.this$0, minecraftProfileTexture.getMetadata("model"));
                            if (NetworkPlayerInfo.access$100(this.this$0) == null) {
                                NetworkPlayerInfo.access$102(this.this$0, "default");
                                break;
                            }
                            break;
                        }
                        case 2: {
                            NetworkPlayerInfo.access$202(this.this$0, resourceLocation);
                            break;
                        }
                    }
                }
            }, true);
        }
    }
    // monitorexit(this)
    
    public void setDisplayName(final IChatComponent displayName) {
        this.displayName = displayName;
    }
}
