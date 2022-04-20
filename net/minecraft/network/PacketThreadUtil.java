package net.minecraft.network;

import net.minecraft.util.*;

public class PacketThreadUtil
{
    public static void checkThreadAndEnqueue(final Packet packet, final INetHandler netHandler, final IThreadListener threadListener) throws ThreadQuickExitException {
        if (!threadListener.isCallingFromMinecraftThread()) {
            threadListener.addScheduledTask(new Runnable(packet, netHandler) {
                final Packet val$p_180031_0_;
                final INetHandler val$p_180031_1_;
                
                @Override
                public void run() {
                    this.val$p_180031_0_.processPacket(this.val$p_180031_1_);
                }
            });
            throw ThreadQuickExitException.field_179886_a;
        }
    }
}
