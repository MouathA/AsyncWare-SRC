package net.minecraft.tileentity;

import com.mojang.authlib.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.server.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;
import net.minecraft.nbt.*;
import java.util.*;

public class TileEntitySkull extends TileEntity
{
    private int skullRotation;
    private int skullType;
    private GameProfile playerProfile;
    
    public void setType(final int skullType) {
        this.skullType = skullType;
        this.playerProfile = null;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 4, nbtTagCompound);
    }
    
    public static GameProfile updateGameprofile(final GameProfile gameProfile) {
        if (gameProfile == null || StringUtils.isNullOrEmpty(gameProfile.getName())) {
            return gameProfile;
        }
        if (gameProfile.isComplete() && gameProfile.getProperties().containsKey((Object)"textures")) {
            return gameProfile;
        }
        if (MinecraftServer.getServer() == null) {
            return gameProfile;
        }
        GameProfile gameProfile2 = MinecraftServer.getServer().getPlayerProfileCache().getGameProfileForUsername(gameProfile.getName());
        if (gameProfile2 == null) {
            return gameProfile;
        }
        if (Iterables.getFirst((Iterable)gameProfile2.getProperties().get((Object)"textures"), (Object)null) == null) {
            gameProfile2 = MinecraftServer.getServer().getMinecraftSessionService().fillProfileProperties(gameProfile2, true);
        }
        return gameProfile2;
    }
    
    public int getSkullRotation() {
        return this.skullRotation;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setByte("SkullType", (byte)(this.skullType & 0xFF));
        nbtTagCompound.setByte("Rot", (byte)(this.skullRotation & 0xFF));
        if (this.playerProfile != null) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            NBTUtil.writeGameProfile(nbtTagCompound2, this.playerProfile);
            nbtTagCompound.setTag("Owner", nbtTagCompound2);
        }
    }
    
    public void setPlayerProfile(final GameProfile playerProfile) {
        this.skullType = 3;
        this.playerProfile = playerProfile;
        this.updatePlayerProfile();
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.skullType = nbtTagCompound.getByte("SkullType");
        this.skullRotation = nbtTagCompound.getByte("Rot");
        if (this.skullType == 3) {
            if (nbtTagCompound.hasKey("Owner", 10)) {
                this.playerProfile = NBTUtil.readGameProfileFromNBT(nbtTagCompound.getCompoundTag("Owner"));
            }
            else if (nbtTagCompound.hasKey("ExtraType", 8)) {
                final String string = nbtTagCompound.getString("ExtraType");
                if (!StringUtils.isNullOrEmpty(string)) {
                    this.playerProfile = new GameProfile((UUID)null, string);
                    this.updatePlayerProfile();
                }
            }
        }
    }
    
    public GameProfile getPlayerProfile() {
        return this.playerProfile;
    }
    
    public void setSkullRotation(final int skullRotation) {
        this.skullRotation = skullRotation;
    }
    
    public int getSkullType() {
        return this.skullType;
    }
    
    private void updatePlayerProfile() {
        this.playerProfile = updateGameprofile(this.playerProfile);
        this.markDirty();
    }
    
    public TileEntitySkull() {
        this.playerProfile = null;
    }
}
