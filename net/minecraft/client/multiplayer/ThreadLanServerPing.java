package net.minecraft.client.multiplayer;

import java.util.concurrent.atomic.*;
import java.net.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class ThreadLanServerPing extends Thread
{
    private boolean isStopping;
    private final String address;
    private final String motd;
    private static final AtomicInteger field_148658_a;
    private static final Logger logger;
    private final DatagramSocket socket;
    
    @Override
    public void run() {
        final byte[] bytes = getPingResponse(this.motd, this.address).getBytes();
        while (!this.isInterrupted() && this.isStopping) {
            this.socket.send(new DatagramPacket(bytes, bytes.length, InetAddress.getByName("224.0.2.60"), 4445));
            Thread.sleep(1500L);
        }
    }
    
    public static String getPingResponse(final String s, final String s2) {
        return "[MOTD]" + s + "[/MOTD][AD]" + s2 + "[/AD]";
    }
    
    public ThreadLanServerPing(final String motd, final String address) throws IOException {
        super("LanServerPinger #" + ThreadLanServerPing.field_148658_a.incrementAndGet());
        this.isStopping = true;
        this.motd = motd;
        this.address = address;
        this.setDaemon(true);
        this.socket = new DatagramSocket();
    }
    
    public static String getMotdFromPingResponse(final String s) {
        final int index = s.indexOf("[MOTD]");
        if (index < 0) {
            return "missing no";
        }
        final int index2 = s.indexOf("[/MOTD]", index + 6);
        return (index2 < index) ? "missing no" : s.substring(index + 6, index2);
    }
    
    @Override
    public void interrupt() {
        super.interrupt();
        this.isStopping = false;
    }
    
    public static String getAdFromPingResponse(final String s) {
        final int index = s.indexOf("[/MOTD]");
        if (index < 0) {
            return null;
        }
        if (s.indexOf("[/MOTD]", index + 7) >= 0) {
            return null;
        }
        final int index2 = s.indexOf("[AD]", index + 7);
        if (index2 < 0) {
            return null;
        }
        final int index3 = s.indexOf("[/AD]", index2 + 4);
        return (index3 < index2) ? null : s.substring(index2 + 4, index3);
    }
    
    static {
        field_148658_a = new AtomicInteger(0);
        logger = LogManager.getLogger();
    }
}
