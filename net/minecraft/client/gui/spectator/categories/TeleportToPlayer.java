package net.minecraft.client.gui.spectator.categories;

import net.minecraft.client.network.*;
import net.minecraft.world.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.spectator.*;

public class TeleportToPlayer implements ISpectatorMenuView, ISpectatorMenuObject
{
    private static final Ordering field_178674_a;
    private final List field_178673_b;
    
    public TeleportToPlayer(final Collection collection) {
        this.field_178673_b = Lists.newArrayList();
        for (final NetworkPlayerInfo networkPlayerInfo : TeleportToPlayer.field_178674_a.sortedCopy((Iterable)collection)) {
            if (networkPlayerInfo.getGameType() != WorldSettings.GameType.SPECTATOR) {
                this.field_178673_b.add(new PlayerMenuObject(networkPlayerInfo.getGameProfile()));
            }
        }
    }
    
    static {
        field_178674_a = Ordering.from((Comparator)new Comparator() {
            public int compare(final NetworkPlayerInfo networkPlayerInfo, final NetworkPlayerInfo networkPlayerInfo2) {
                return ComparisonChain.start().compare((Comparable)networkPlayerInfo.getGameProfile().getId(), (Comparable)networkPlayerInfo2.getGameProfile().getId()).result();
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.compare((NetworkPlayerInfo)o, (NetworkPlayerInfo)o2);
            }
        });
    }
    
    @Override
    public void func_178663_a(final float n, final int n2) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 16, 16, 256.0f, 256.0f);
    }
    
    @Override
    public IChatComponent func_178670_b() {
        return new ChatComponentText("Select a player to teleport to");
    }
    
    @Override
    public List func_178669_a() {
        return this.field_178673_b;
    }
    
    public TeleportToPlayer() {
        this(TeleportToPlayer.field_178674_a.sortedCopy((Iterable)Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap()));
    }
    
    @Override
    public boolean func_178662_A_() {
        return !this.field_178673_b.isEmpty();
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu spectatorMenu) {
        spectatorMenu.func_178647_a(this);
    }
    
    @Override
    public IChatComponent getSpectatorName() {
        return new ChatComponentText("Teleport to player");
    }
}
