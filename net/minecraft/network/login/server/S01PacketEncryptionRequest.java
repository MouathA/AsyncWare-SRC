package net.minecraft.network.login.server;

import java.security.*;
import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;
import net.minecraft.util.*;

public class S01PacketEncryptionRequest implements Packet
{
    private String hashedServerId;
    private byte[] verifyToken;
    private PublicKey publicKey;
    
    public PublicKey getPublicKey() {
        return this.publicKey;
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.hashedServerId);
        packetBuffer.writeByteArray(this.publicKey.getEncoded());
        packetBuffer.writeByteArray(this.verifyToken);
    }
    
    public byte[] getVerifyToken() {
        return this.verifyToken;
    }
    
    public S01PacketEncryptionRequest() {
    }
    
    public S01PacketEncryptionRequest(final String hashedServerId, final PublicKey publicKey, final byte[] verifyToken) {
        this.hashedServerId = hashedServerId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }
    
    public void processPacket(final INetHandlerLoginClient netHandlerLoginClient) {
        netHandlerLoginClient.handleEncryptionRequest(this);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginClient)netHandler);
    }
    
    public String getServerId() {
        return this.hashedServerId;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.hashedServerId = packetBuffer.readStringFromBuffer(20);
        this.publicKey = CryptManager.decodePublicKey(packetBuffer.readByteArray());
        this.verifyToken = packetBuffer.readByteArray();
    }
}
