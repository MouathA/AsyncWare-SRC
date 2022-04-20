package net.minecraft.network.play.server;

import java.util.*;
import java.io.*;
import com.google.common.collect.*;
import net.minecraft.scoreboard.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public class S3EPacketTeams implements Packet
{
    private String field_149318_b;
    private String field_149316_d;
    private int field_149314_f;
    private String field_149319_c;
    private int field_149315_g;
    private String field_149320_a;
    private int field_179815_f;
    private Collection field_149317_e;
    private String field_179816_e;
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.field_149320_a);
        packetBuffer.writeByte(this.field_149314_f);
        if (this.field_149314_f == 0 || this.field_149314_f == 2) {
            packetBuffer.writeString(this.field_149318_b);
            packetBuffer.writeString(this.field_149319_c);
            packetBuffer.writeString(this.field_149316_d);
            packetBuffer.writeByte(this.field_149315_g);
            packetBuffer.writeString(this.field_179816_e);
            packetBuffer.writeByte(this.field_179815_f);
        }
        if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
            packetBuffer.writeVarIntToBuffer(this.field_149317_e.size());
            final Iterator<String> iterator = this.field_149317_e.iterator();
            while (iterator.hasNext()) {
                packetBuffer.writeString(iterator.next());
            }
        }
    }
    
    public String func_149309_f() {
        return this.field_149316_d;
    }
    
    public String func_149306_d() {
        return this.field_149318_b;
    }
    
    public S3EPacketTeams() {
        this.field_149320_a = "";
        this.field_149318_b = "";
        this.field_149319_c = "";
        this.field_149316_d = "";
        this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
        this.field_179815_f = -1;
        this.field_149317_e = Lists.newArrayList();
    }
    
    public int func_179813_h() {
        return this.field_179815_f;
    }
    
    public int func_149308_i() {
        return this.field_149315_g;
    }
    
    public int func_149307_h() {
        return this.field_149314_f;
    }
    
    public String func_149312_c() {
        return this.field_149320_a;
    }
    
    public S3EPacketTeams(final ScorePlayerTeam scorePlayerTeam, final Collection collection, final int field_149314_f) {
        this.field_149320_a = "";
        this.field_149318_b = "";
        this.field_149319_c = "";
        this.field_149316_d = "";
        this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
        this.field_179815_f = -1;
        this.field_149317_e = Lists.newArrayList();
        if (field_149314_f != 3 && field_149314_f != 4) {
            throw new IllegalArgumentException("Method must be join or leave for player constructor");
        }
        if (collection != null && !collection.isEmpty()) {
            this.field_149314_f = field_149314_f;
            this.field_149320_a = scorePlayerTeam.getRegisteredName();
            this.field_149317_e.addAll(collection);
            return;
        }
        throw new IllegalArgumentException("Players cannot be null/empty");
    }
    
    public String func_179814_i() {
        return this.field_179816_e;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleTeams(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.field_149320_a = packetBuffer.readStringFromBuffer(16);
        this.field_149314_f = packetBuffer.readByte();
        if (this.field_149314_f == 0 || this.field_149314_f == 2) {
            this.field_149318_b = packetBuffer.readStringFromBuffer(32);
            this.field_149319_c = packetBuffer.readStringFromBuffer(16);
            this.field_149316_d = packetBuffer.readStringFromBuffer(16);
            this.field_149315_g = packetBuffer.readByte();
            this.field_179816_e = packetBuffer.readStringFromBuffer(32);
            this.field_179815_f = packetBuffer.readByte();
        }
        if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
            while (0 < packetBuffer.readVarIntFromBuffer()) {
                this.field_149317_e.add(packetBuffer.readStringFromBuffer(40));
                int n = 0;
                ++n;
            }
        }
    }
    
    public S3EPacketTeams(final ScorePlayerTeam scorePlayerTeam, final int field_149314_f) {
        this.field_149320_a = "";
        this.field_149318_b = "";
        this.field_149319_c = "";
        this.field_149316_d = "";
        this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
        this.field_179815_f = -1;
        this.field_149317_e = Lists.newArrayList();
        this.field_149320_a = scorePlayerTeam.getRegisteredName();
        this.field_149314_f = field_149314_f;
        if (field_149314_f == 0 || field_149314_f == 2) {
            this.field_149318_b = scorePlayerTeam.getTeamName();
            this.field_149319_c = scorePlayerTeam.getColorPrefix();
            this.field_149316_d = scorePlayerTeam.getColorSuffix();
            this.field_149315_g = scorePlayerTeam.func_98299_i();
            this.field_179816_e = scorePlayerTeam.getNameTagVisibility().field_178830_e;
            this.field_179815_f = scorePlayerTeam.getChatFormat().getColorIndex();
        }
        if (field_149314_f == 0) {
            this.field_149317_e.addAll(scorePlayerTeam.getMembershipCollection());
        }
    }
    
    public String func_149311_e() {
        return this.field_149319_c;
    }
    
    public Collection func_149310_g() {
        return this.field_149317_e;
    }
}
