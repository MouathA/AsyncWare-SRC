package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.gui.spectator.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.scoreboard.*;
import net.minecraft.util.*;
import net.minecraft.client.network.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import java.util.*;

public class TeleportToTeam implements ISpectatorMenuObject, ISpectatorMenuView
{
    private final List field_178672_a;
    
    @Override
    public List func_178669_a() {
        return this.field_178672_a;
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu spectatorMenu) {
        spectatorMenu.func_178647_a(this);
    }
    
    public TeleportToTeam() {
        this.field_178672_a = Lists.newArrayList();
        final Iterator<ScorePlayerTeam> iterator = Minecraft.getMinecraft().theWorld.getScoreboard().getTeams().iterator();
        while (iterator.hasNext()) {
            this.field_178672_a.add(new TeamSelectionObject(iterator.next()));
        }
    }
    
    @Override
    public IChatComponent func_178670_b() {
        return new ChatComponentText("Select a team to teleport to");
    }
    
    @Override
    public void func_178663_a(final float n, final int n2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 16.0f, 0.0f, 16, 16, 256.0f, 256.0f);
    }
    
    @Override
    public boolean func_178662_A_() {
        final Iterator<ISpectatorMenuObject> iterator = this.field_178672_a.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().func_178662_A_()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText("Teleport to team member");
    }
    
    class TeamSelectionObject implements ISpectatorMenuObject
    {
        private final ResourceLocation field_178677_c;
        private final List field_178675_d;
        private final ScorePlayerTeam field_178676_b;
        final TeleportToTeam this$0;
        
        public TeamSelectionObject(final TeleportToTeam this$0, final ScorePlayerTeam field_178676_b) {
            this.this$0 = this$0;
            this.field_178676_b = field_178676_b;
            this.field_178675_d = Lists.newArrayList();
            final Iterator<String> iterator = field_178676_b.getMembershipCollection().iterator();
            while (iterator.hasNext()) {
                final NetworkPlayerInfo playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(iterator.next());
                if (playerInfo != null) {
                    this.field_178675_d.add(playerInfo);
                }
            }
            if (!this.field_178675_d.isEmpty()) {
                final String name = this.field_178675_d.get(new Random().nextInt(this.field_178675_d.size())).getGameProfile().getName();
                AbstractClientPlayer.getDownloadImageSkin(this.field_178677_c = AbstractClientPlayer.getLocationSkin(name), name);
            }
            else {
                this.field_178677_c = DefaultPlayerSkin.getDefaultSkinLegacy();
            }
        }
        
        @Override
        public boolean func_178662_A_() {
            return !this.field_178675_d.isEmpty();
        }
        
        @Override
        public IChatComponent getSpectatorName() {
            return new ChatComponentText(this.field_178676_b.getTeamName());
        }
        
        @Override
        public void func_178663_a(final float n, final int n2) {
            final String formatFromString = FontRenderer.getFormatFromString(this.field_178676_b.getColorPrefix());
            if (formatFromString.length() >= 2) {
                Minecraft.getMinecraft().fontRendererObj.getColorCode(formatFromString.charAt(1));
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178677_c);
            GlStateManager.color(n, n, n, n2 / 255.0f);
            Gui.drawScaledCustomSizeModalRect(2, 2, 8.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
            Gui.drawScaledCustomSizeModalRect(2, 2, 40.0f, 8.0f, 8, 8, 12, 12, 64.0f, 64.0f);
        }
        
        @Override
        public void func_178661_a(final SpectatorMenu spectatorMenu) {
            spectatorMenu.func_178647_a(new TeleportToPlayer(this.field_178675_d));
        }
    }
}
