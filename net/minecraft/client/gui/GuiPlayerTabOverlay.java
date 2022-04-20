package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;

public class GuiPlayerTabOverlay extends Gui
{
    private long lastTimeOpened;
    private IChatComponent header;
    private IChatComponent footer;
    private final GuiIngame guiIngame;
    private boolean isBeingRendered;
    private static final Ordering field_175252_a;
    private final Minecraft mc;
    
    public void func_181030_a() {
        this.header = null;
        this.footer = null;
    }
    
    private void drawScoreboardValues(final ScoreObjective scoreObjective, final int n, final String s, final int n2, final int n3, final NetworkPlayerInfo networkPlayerInfo) {
        final int scorePoints = scoreObjective.getScoreboard().getValueFromObjective(s, scoreObjective).getScorePoints();
        if (scoreObjective.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
            this.mc.getTextureManager().bindTexture(GuiPlayerTabOverlay.icons);
            if (this.lastTimeOpened == networkPlayerInfo.func_178855_p()) {
                if (scorePoints < networkPlayerInfo.func_178835_l()) {
                    networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
                    networkPlayerInfo.func_178844_b(this.guiIngame.getUpdateCounter() + 20);
                }
                else if (scorePoints > networkPlayerInfo.func_178835_l()) {
                    networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
                    networkPlayerInfo.func_178844_b(this.guiIngame.getUpdateCounter() + 10);
                }
            }
            if (Minecraft.getSystemTime() - networkPlayerInfo.func_178847_n() > 1000L || this.lastTimeOpened != networkPlayerInfo.func_178855_p()) {
                networkPlayerInfo.func_178836_b(scorePoints);
                networkPlayerInfo.func_178857_c(scorePoints);
                networkPlayerInfo.func_178846_a(Minecraft.getSystemTime());
            }
            networkPlayerInfo.func_178843_c(this.lastTimeOpened);
            networkPlayerInfo.func_178836_b(scorePoints);
            final int ceiling_float_int = MathHelper.ceiling_float_int(Math.max(scorePoints, networkPlayerInfo.func_178860_m()) / 2.0f);
            final int max = Math.max(MathHelper.ceiling_float_int((float)(scorePoints / 2)), Math.max(MathHelper.ceiling_float_int((float)(networkPlayerInfo.func_178860_m() / 2)), 10));
            final boolean b = networkPlayerInfo.func_178858_o() > this.guiIngame.getUpdateCounter() && (networkPlayerInfo.func_178858_o() - this.guiIngame.getUpdateCounter()) / 3L % 2L == 1L;
            if (ceiling_float_int > 0) {
                final float min = Math.min((n3 - n2 - 4) / (float)max, 9.0f);
                if (min > 3.0f) {
                    int n4 = ceiling_float_int;
                    while (0 < max) {
                        this.drawTexturedModalRect(n2 + 0 * min, (float)n, b ? 25 : 16, 0, 9, 9);
                        ++n4;
                    }
                    while (0 < ceiling_float_int) {
                        this.drawTexturedModalRect(n2 + 0 * min, (float)n, b ? 25 : 16, 0, 9, 9);
                        if (b) {
                            if (1 < networkPlayerInfo.func_178860_m()) {
                                this.drawTexturedModalRect(n2 + 0 * min, (float)n, 70, 0, 9, 9);
                            }
                            if (1 == networkPlayerInfo.func_178860_m()) {
                                this.drawTexturedModalRect(n2 + 0 * min, (float)n, 79, 0, 9, 9);
                            }
                        }
                        if (1 < scorePoints) {
                            this.drawTexturedModalRect(n2 + 0 * min, (float)n, 52, 0, 9, 9);
                        }
                        if (scorePoints != 0) {
                            this.drawTexturedModalRect(n2 + 0 * min, (float)n, 61, 0, 9, 9);
                        }
                        ++n4;
                    }
                }
                else {
                    final float clamp_float = MathHelper.clamp_float(scorePoints / 20.0f, 0.0f, 1.0f);
                    final int n5 = (int)((1.0f - clamp_float) * 255.0f) << 16 | (int)(clamp_float * 255.0f) << 8;
                    String s2 = "" + scorePoints / 2.0f;
                    if (n3 - this.mc.fontRendererObj.getStringWidth(s2 + "hp") >= n2) {
                        s2 += "hp";
                    }
                    this.mc.fontRendererObj.drawStringWithShadow(s2, (float)((n3 + n2) / 2 - this.mc.fontRendererObj.getStringWidth(s2) / 2), (float)n, n5);
                }
            }
        }
        else {
            final String string = EnumChatFormatting.YELLOW + "" + scorePoints;
            this.mc.fontRendererObj.drawStringWithShadow(string, (float)(n3 - this.mc.fontRendererObj.getStringWidth(string)), (float)n, 16777215);
        }
    }
    
    public String getPlayerName(final NetworkPlayerInfo networkPlayerInfo) {
        return (networkPlayerInfo.getDisplayName() != null) ? networkPlayerInfo.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfo.getPlayerTeam(), networkPlayerInfo.getGameProfile().getName());
    }
    
    public GuiPlayerTabOverlay(final Minecraft mc, final GuiIngame guiIngame) {
        this.mc = mc;
        this.guiIngame = guiIngame;
    }
    
    public void setFooter(final IChatComponent footer) {
        this.footer = footer;
    }
    
    static {
        field_175252_a = Ordering.from((Comparator)new PlayerComparator(null));
    }
    
    public void renderPlayerlist(final int n, final Scoreboard scoreboard, final ScoreObjective scoreObjective) {
        final List sortedCopy = GuiPlayerTabOverlay.field_175252_a.sortedCopy((Iterable)this.mc.thePlayer.sendQueue.getPlayerInfoMap());
        int n2 = 0;
        for (final NetworkPlayerInfo networkPlayerInfo : sortedCopy) {
            n2 = this.mc.fontRendererObj.getStringWidth(this.getPlayerName(networkPlayerInfo));
            Math.max(0, 1);
            if (scoreObjective != null && scoreObjective.getRenderType() != IScoreObjectiveCriteria.EnumRenderType.HEARTS) {
                n2 = this.mc.fontRendererObj.getStringWidth(" " + scoreboard.getValueFromObjective(networkPlayerInfo.getGameProfile().getName(), scoreObjective).getScorePoints());
                Math.max(0, 1);
            }
        }
        final List<NetworkPlayerInfo> subList = sortedCopy.subList(0, Math.min(sortedCopy.size(), 80));
        int i;
        int n3;
        for (n3 = (i = subList.size()); i > 20; i = (n3 + 1 - 1) / 1) {
            ++n2;
        }
        final boolean b = this.mc.isIntegratedServerRunning() || this.mc.getNetHandler().getNetworkManager().getIsencrypted();
        if (scoreObjective != null) {
            if (scoreObjective.getRenderType() == IScoreObjectiveCriteria.EnumRenderType.HEARTS) {}
        }
        final int n4 = Math.min(1 * ((b ? 9 : 0) + 0 + 0 + 13), n - 50) / 1;
        final int n5 = n / 2 - (n4 * 1 + 0) / 2;
        int n6 = n4 * 1 + 0;
        List<String> listFormattedStringToWidth = null;
        List<String> listFormattedStringToWidth2 = null;
        if (this.header != null) {
            listFormattedStringToWidth = (List<String>)this.mc.fontRendererObj.listFormattedStringToWidth(this.header.getFormattedText(), n - 50);
            final Iterator<String> iterator2 = listFormattedStringToWidth.iterator();
            while (iterator2.hasNext()) {
                n6 = Math.max(n6, this.mc.fontRendererObj.getStringWidth(iterator2.next()));
            }
        }
        if (this.footer != null) {
            listFormattedStringToWidth2 = (List<String>)this.mc.fontRendererObj.listFormattedStringToWidth(this.footer.getFormattedText(), n - 50);
            final Iterator<String> iterator3 = listFormattedStringToWidth2.iterator();
            while (iterator3.hasNext()) {
                n6 = Math.max(n6, this.mc.fontRendererObj.getStringWidth(iterator3.next()));
            }
        }
        if (listFormattedStringToWidth != null) {
            Gui.drawRect(n / 2 - n6 / 2 - 1, 9, n / 2 + n6 / 2 + 1, 10 + listFormattedStringToWidth.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);
            int n7 = 0;
            for (final String s : listFormattedStringToWidth) {
                this.mc.fontRendererObj.drawStringWithShadow(s, (float)(n / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2), 10, -1);
                n7 = 10 + this.mc.fontRendererObj.FONT_HEIGHT;
            }
            ++n7;
        }
        Gui.drawRect(n / 2 - n6 / 2 - 1, 9, n / 2 + n6 / 2 + 1, 10 + i * 9, Integer.MIN_VALUE);
        while (0 < n3) {
            final int n8 = 0 / i;
            final int n9 = 0 % i;
            int n10 = n5 + n8 * n4 + n8 * 5;
            final int n11 = 10 + n9 * 9;
            Gui.drawRect(n10, n11, n10 + n4, n11 + 8, 553648127);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if (0 < subList.size()) {
                final NetworkPlayerInfo networkPlayerInfo2 = subList.get(0);
                final String playerName = this.getPlayerName(networkPlayerInfo2);
                final GameProfile gameProfile = networkPlayerInfo2.getGameProfile();
                if (b) {
                    final EntityPlayer playerEntityByUUID = this.mc.theWorld.getPlayerEntityByUUID(gameProfile.getId());
                    final boolean b2 = playerEntityByUUID != null && playerEntityByUUID.isWearing(EnumPlayerModelParts.CAPE) && (gameProfile.getName().equals("Dinnerbone") || gameProfile.getName().equals("Grumm"));
                    this.mc.getTextureManager().bindTexture(networkPlayerInfo2.getLocationSkin());
                    Gui.drawScaledCustomSizeModalRect(n10, n11, 8.0f, (float)(8 + (b2 ? 8 : 0)), 8, 8 * (b2 ? -1 : 1), 8, 8, 64.0f, 64.0f);
                    if (playerEntityByUUID != null && playerEntityByUUID.isWearing(EnumPlayerModelParts.HAT)) {
                        Gui.drawScaledCustomSizeModalRect(n10, n11, 40.0f, (float)(8 + (b2 ? 8 : 0)), 8, 8 * (b2 ? -1 : 1), 8, 8, 64.0f, 64.0f);
                    }
                    n10 += 9;
                }
                if (networkPlayerInfo2.getGameType() == WorldSettings.GameType.SPECTATOR) {
                    this.mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.ITALIC + playerName, (float)n10, (float)n11, -1862270977);
                }
                else {
                    this.mc.fontRendererObj.drawStringWithShadow(playerName, (float)n10, (float)n11, -1);
                }
                if (scoreObjective != null && networkPlayerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR) {
                    final int n12 = n10 + 0 + 1;
                    final int n13 = n12 + 0;
                    if (n13 - n12 > 5) {
                        this.drawScoreboardValues(scoreObjective, n11, gameProfile.getName(), n12, n13, networkPlayerInfo2);
                    }
                }
                this.drawPing(n4, n10 - (b ? 9 : 0), n11, networkPlayerInfo2);
            }
            int n14 = 0;
            ++n14;
        }
        if (listFormattedStringToWidth2 != null) {
            Gui.drawRect(n / 2 - n6 / 2 - 1, 9, n / 2 + n6 / 2 + 1, 10 + listFormattedStringToWidth2.size() * this.mc.fontRendererObj.FONT_HEIGHT, Integer.MIN_VALUE);
            for (final String s2 : listFormattedStringToWidth2) {
                this.mc.fontRendererObj.drawStringWithShadow(s2, (float)(n / 2 - this.mc.fontRendererObj.getStringWidth(s2) / 2), 10, -1);
                final int n15 = 10 + this.mc.fontRendererObj.FONT_HEIGHT;
            }
        }
    }
    
    public void setHeader(final IChatComponent header) {
        this.header = header;
    }
    
    public void updatePlayerList(final boolean isBeingRendered) {
        if (isBeingRendered && !this.isBeingRendered) {
            this.lastTimeOpened = Minecraft.getSystemTime();
        }
        this.isBeingRendered = isBeingRendered;
    }
    
    protected void drawPing(final int n, final int n2, final int n3, final NetworkPlayerInfo networkPlayerInfo) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiPlayerTabOverlay.icons);
        if (networkPlayerInfo.getResponseTime() >= 0) {
            if (networkPlayerInfo.getResponseTime() >= 150) {
                if (networkPlayerInfo.getResponseTime() >= 300) {
                    if (networkPlayerInfo.getResponseTime() >= 600) {
                        if (networkPlayerInfo.getResponseTime() < 1000) {}
                    }
                }
            }
        }
        this.zLevel += 100.0f;
        this.drawTexturedModalRect(n2 + n - 11, n3, 0, 208, 10, 8);
        this.zLevel -= 100.0f;
    }
    
    static class PlayerComparator implements Comparator
    {
        private PlayerComparator() {
        }
        
        PlayerComparator(final GuiPlayerTabOverlay$1 object) {
            this();
        }
        
        public int compare(final NetworkPlayerInfo networkPlayerInfo, final NetworkPlayerInfo networkPlayerInfo2) {
            final ScorePlayerTeam playerTeam = networkPlayerInfo.getPlayerTeam();
            final ScorePlayerTeam playerTeam2 = networkPlayerInfo2.getPlayerTeam();
            return ComparisonChain.start().compareTrueFirst(networkPlayerInfo.getGameType() != WorldSettings.GameType.SPECTATOR, networkPlayerInfo2.getGameType() != WorldSettings.GameType.SPECTATOR).compare((Comparable)((playerTeam != null) ? playerTeam.getRegisteredName() : ""), (Comparable)((playerTeam2 != null) ? playerTeam2.getRegisteredName() : "")).compare((Comparable)networkPlayerInfo.getGameProfile().getName(), (Comparable)networkPlayerInfo2.getGameProfile().getName()).result();
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((NetworkPlayerInfo)o, (NetworkPlayerInfo)o2);
        }
    }
}
