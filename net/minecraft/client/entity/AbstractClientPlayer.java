package net.minecraft.client.entity;

import net.minecraft.entity.player.*;
import net.minecraft.client.network.*;
import net.minecraft.client.*;
import java.io.*;
import net.minecraft.util.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import org.apache.commons.io.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import java.awt.image.*;
import java.awt.*;
import net.minecraft.world.*;
import com.mojang.authlib.*;
import optfine.*;

public abstract class AbstractClientPlayer extends EntityPlayer
{
    private ResourceLocation ofLocationCape;
    private static final String __OBFID = "CL_00000935";
    private NetworkPlayerInfo playerInfo;
    
    static BufferedImage access$000(final AbstractClientPlayer abstractClientPlayer, final BufferedImage bufferedImage) {
        return abstractClientPlayer.parseCape(bufferedImage);
    }
    
    public static ThreadDownloadImageData getDownloadImageSkin(final ResourceLocation resourceLocation, final String s) {
        final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        ITextureObject texture = textureManager.getTexture(resourceLocation);
        if (texture == null) {
            texture = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", StringUtils.stripControlCodes(s)), DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getOfflineUUID(s)), new ImageBufferDownload());
            textureManager.loadTexture(resourceLocation, texture);
        }
        return (ThreadDownloadImageData)texture;
    }
    
    private void downloadCape(String stripControlCodes) {
        if (stripControlCodes != null && !stripControlCodes.isEmpty()) {
            stripControlCodes = StringUtils.stripControlCodes(stripControlCodes);
            final String string = "http://s.optifine.net/capes/" + stripControlCodes + ".png";
            final ResourceLocation ofLocationCape = new ResourceLocation("capeof/" + FilenameUtils.getBaseName(string));
            final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
            final ITextureObject texture = textureManager.getTexture(ofLocationCape);
            if (texture != null && texture instanceof ThreadDownloadImageData) {
                final ThreadDownloadImageData threadDownloadImageData = (ThreadDownloadImageData)texture;
                if (threadDownloadImageData.imageFound != null) {
                    if (threadDownloadImageData.imageFound) {
                        this.ofLocationCape = ofLocationCape;
                    }
                    return;
                }
            }
            textureManager.loadTexture(ofLocationCape, new ThreadDownloadImageData(null, string, null, new IImageBuffer(this, ofLocationCape) {
                final AbstractClientPlayer this$0;
                ImageBufferDownload ibd = new ImageBufferDownload();
                final ResourceLocation val$resourcelocation;
                
                @Override
                public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
                    return AbstractClientPlayer.access$000(this.this$0, bufferedImage);
                }
                
                @Override
                public void skinAvailable() {
                    AbstractClientPlayer.access$102(this.this$0, this.val$resourcelocation);
                }
            }));
        }
    }
    
    @Override
    public boolean isSpectator() {
        final NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getGameProfile().getId());
        return playerInfo != null && playerInfo.getGameType() == WorldSettings.GameType.SPECTATOR;
    }
    
    public static ResourceLocation getLocationSkin(final String s) {
        return new ResourceLocation("skins/" + StringUtils.stripControlCodes(s));
    }
    
    public ResourceLocation getLocationSkin() {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        return (playerInfo == null) ? DefaultPlayerSkin.getDefaultSkin(this.getUniqueID()) : playerInfo.getLocationSkin();
    }
    
    public String getSkinType() {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        return (playerInfo == null) ? DefaultPlayerSkin.getSkinType(this.getUniqueID()) : playerInfo.getSkinType();
    }
    
    public boolean hasSkin() {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        return playerInfo != null && playerInfo.hasLocationSkin();
    }
    
    static ResourceLocation access$102(final AbstractClientPlayer abstractClientPlayer, final ResourceLocation ofLocationCape) {
        return abstractClientPlayer.ofLocationCape = ofLocationCape;
    }
    
    public ResourceLocation getLocationCape() {
        if (!Config.isShowCapes()) {
            return null;
        }
        if (this.ofLocationCape != null) {
            return this.ofLocationCape;
        }
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo();
        return (playerInfo == null) ? null : playerInfo.getLocationCape();
    }
    
    public float getFovModifier() {
        float n = 1.0f;
        if (this.capabilities.isFlying) {
            n *= 1.1f;
        }
        float n2 = (float)(n * ((this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0) / 2.0));
        if (this.capabilities.getWalkSpeed() == 0.0f || Float.isNaN(n2) || Float.isInfinite(n2)) {
            n2 = 1.0f;
        }
        if (this.isUsingItem() && this.getItemInUse().getItem() == Items.bow) {
            final float n3 = this.getItemInUseDuration() / 20.0f;
            float n4;
            if (n3 > 1.0f) {
                n4 = 1.0f;
            }
            else {
                n4 = n3 * n3;
            }
            n2 *= 1.0f - n4 * 0.15f;
        }
        return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, this, n2) : n2;
    }
    
    public boolean hasPlayerInfo() {
        return this.getPlayerInfo() != null;
    }
    
    private BufferedImage parseCape(final BufferedImage bufferedImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        while (64 < width || 32 < height) {}
        final BufferedImage bufferedImage2 = new BufferedImage(64, 32, 2);
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        return bufferedImage2;
    }
    
    protected NetworkPlayerInfo getPlayerInfo() {
        if (this.playerInfo == null) {
            this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(this.getUniqueID());
        }
        return this.playerInfo;
    }
    
    public AbstractClientPlayer(final World world, final GameProfile gameProfile) {
        super(world, gameProfile);
        this.ofLocationCape = null;
        this.downloadCape(gameProfile.getName());
        PlayerConfigurations.getPlayerConfiguration(this);
    }
}
