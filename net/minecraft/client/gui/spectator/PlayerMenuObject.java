package net.minecraft.client.gui.spectator;

import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class PlayerMenuObject implements ISpectatorMenuObject
{
    private final GameProfile profile;
    private final ResourceLocation resourceLocation;
    
    public PlayerMenuObject(final GameProfile profile) {
        this.profile = profile;
        AbstractClientPlayer.getDownloadImageSkin(this.resourceLocation = AbstractClientPlayer.getLocationSkin(profile.getName()), profile.getName());
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu spectatorMenu) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C18PacketSpectate(this.profile.getId()));
    }
    
    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText(this.profile.getName());
    }
    
    @Override
    public boolean func_178662_A_() {
        return true;
    }
    
    @Override
    public void func_178663_a(final float n, final int n2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        GlStateManager.color(1.0f, 1.0f, 1.0f, n2 / 255.0f);
        Gui.drawScaledCustomSizeModalRect(2, 2, 8.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect(2, 2, 40.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
    }
}
