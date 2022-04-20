package net.minecraft.client.network;

import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.multiplayer.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.client.*;
import java.net.*;
import java.io.*;

public class LanServerDetector
{
    private static final Logger logger;
    private static final AtomicInteger field_148551_a;
    
    static AtomicInteger access$000() {
        return LanServerDetector.field_148551_a;
    }
    
    static {
        field_148551_a = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
    
    static Logger access$100() {
        return LanServerDetector.logger;
    }
    
    public static class LanServerList
    {
        boolean wasUpdated;
        private List listOfLanServers;
        
        public synchronized void func_77551_a(final String s, final InetAddress inetAddress) {
            ThreadLanServerPing.getMotdFromPingResponse(s);
            final String adFromPingResponse = ThreadLanServerPing.getAdFromPingResponse(s);
            if (adFromPingResponse != null) {
                final String string = inetAddress.getHostAddress() + ":" + adFromPingResponse;
                for (final LanServer lanServer : this.listOfLanServers) {
                    if (lanServer.getServerIpPort().equals(string)) {
                        lanServer.updateLastSeen();
                        break;
                    }
                }
            }
        }
        
        public synchronized boolean getWasUpdated() {
            return this.wasUpdated;
        }
        
        public synchronized void setWasNotUpdated() {
            this.wasUpdated = false;
        }
        
        public LanServerList() {
            this.listOfLanServers = Lists.newArrayList();
        }
        
        public synchronized List getLanServers() {
            return Collections.unmodifiableList((List<?>)this.listOfLanServers);
        }
    }
    
    public static class LanServer
    {
        private String lanServerMotd;
        private String lanServerIpPort;
        private long timeLastSeen;
        
        public void updateLastSeen() {
            this.timeLastSeen = Minecraft.getSystemTime();
        }
        
        public String getServerMotd() {
            return this.lanServerMotd;
        }
        
        public LanServer(final String lanServerMotd, final String lanServerIpPort) {
            this.lanServerMotd = lanServerMotd;
            this.lanServerIpPort = lanServerIpPort;
            this.timeLastSeen = Minecraft.getSystemTime();
        }
        
        public String getServerIpPort() {
            return this.lanServerIpPort;
        }
    }
    
    public static class ThreadLanServerFind extends Thread
    {
        private final InetAddress broadcastAddress;
        private final LanServerList localServerList;
        private final MulticastSocket socket;
        
        @Override
        public void run() {
            final byte[] array = new byte[1024];
            while (!this.isInterrupted()) {
                final DatagramPacket datagramPacket = new DatagramPacket(array, array.length);
                this.socket.receive(datagramPacket);
                final String s = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
                LanServerDetector.access$100().debug(datagramPacket.getAddress() + ": " + s);
                this.localServerList.func_77551_a(s, datagramPacket.getAddress());
            }
            this.socket.leaveGroup(this.broadcastAddress);
            this.socket.close();
        }
        
        public ThreadLanServerFind(final LanServerList localServerList) throws IOException {
            super("LanServerDetector #" + LanServerDetector.access$000().incrementAndGet());
            this.localServerList = localServerList;
            this.setDaemon(true);
            this.socket = new MulticastSocket(4445);
            this.broadcastAddress = InetAddress.getByName("224.0.2.60");
            this.socket.setSoTimeout(5000);
            this.socket.joinGroup(this.broadcastAddress);
        }
    }
}
