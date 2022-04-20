package net.minecraft.network.login.client;

import javax.crypto.*;
import net.minecraft.util.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;
import java.io.*;
import java.security.*;

public class C01PacketEncryptionResponse implements Packet
{
    private byte[] secretKeyEncrypted;
    private byte[] verifyTokenEncrypted;
    
    public C01PacketEncryptionResponse() {
        this.secretKeyEncrypted = new byte[0];
        this.verifyTokenEncrypted = new byte[0];
    }
    
    public SecretKey getSecretKey(final PrivateKey privateKey) {
        return CryptManager.decryptSharedKey(privateKey, this.secretKeyEncrypted);
    }
    
    public byte[] getVerifyToken(final PrivateKey privateKey) {
        return (privateKey == null) ? this.verifyTokenEncrypted : CryptManager.decryptData(privateKey, this.verifyTokenEncrypted);
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerLoginServer)netHandler);
    }
    
    public void processPacket(final INetHandlerLoginServer netHandlerLoginServer) {
        netHandlerLoginServer.processEncryptionResponse(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByteArray(this.secretKeyEncrypted);
        packetBuffer.writeByteArray(this.verifyTokenEncrypted);
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.secretKeyEncrypted = packetBuffer.readByteArray();
        this.verifyTokenEncrypted = packetBuffer.readByteArray();
    }
    
    public C01PacketEncryptionResponse(final SecretKey secretKey, final PublicKey publicKey, final byte[] array) {
        this.secretKeyEncrypted = new byte[0];
        this.verifyTokenEncrypted = new byte[0];
        this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
        this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, array);
    }
}
