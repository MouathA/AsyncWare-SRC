package net.minecraft.profiler;

import java.net.*;
import java.lang.management.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import java.util.*;

public class PlayerUsageSnooper
{
    private boolean isRunning;
    private final long minecraftStartTimeMilis;
    private int selfCounter;
    private final Map field_152773_a;
    private final Object syncLock;
    private final URL serverUrl;
    private final IPlayerUsage playerStatsCollector;
    private final String uniqueID;
    private final Timer threadTrigger;
    private final Map field_152774_b;
    
    private void addJvmArgsToSnooper() {
        for (final String s : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            if (s.startsWith("-X")) {
                final StringBuilder append = new StringBuilder().append("jvm_arg[");
                final int n = 0;
                int n2 = 0;
                ++n2;
                this.addClientStat(append.append(n).append("]").toString(), s);
            }
        }
        this.addClientStat("jvm_args", 0);
    }
    
    public boolean isSnooperRunning() {
        return this.isRunning;
    }
    
    static String access$500(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.uniqueID;
    }
    
    static URL access$600(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.serverUrl;
    }
    
    private void func_152766_h() {
        this.addJvmArgsToSnooper();
        this.addClientStat("snooper_token", this.uniqueID);
        this.addStatToSnooper("snooper_token", this.uniqueID);
        this.addStatToSnooper("os_name", "\ub077\ub06b\ub036\ub076\ub079\ub075\ub07d");
        this.addStatToSnooper("os_version", "\ub077\ub06b\ub036\ub06e\ub07d\ub06a\ub06b\ub071\ub077\ub076");
        this.addStatToSnooper("os_architecture", "\ub077\ub06b\ub036\ub079\ub06a\ub07b\ub070");
        this.addStatToSnooper("java_version", "\ub072\ub079\ub06e\ub079\ub036\ub06e\ub07d\ub06a\ub06b\ub071\ub077\ub076");
        this.addClientStat("version", "1.8.8");
        this.playerStatsCollector.addServerTypeToSnooper(this);
    }
    
    public String getUniqueID() {
        return this.uniqueID;
    }
    
    public void addClientStat(final String s, final Object o) {
        // monitorenter(syncLock = this.syncLock)
        this.field_152774_b.put(s, o);
    }
    // monitorexit(syncLock)
    
    static int access$300(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.selfCounter;
    }
    
    static Map access$200(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.field_152774_b;
    }
    
    static IPlayerUsage access$000(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.playerStatsCollector;
    }
    
    public void addStatToSnooper(final String s, final Object o) {
        // monitorenter(syncLock = this.syncLock)
        this.field_152773_a.put(s, o);
    }
    // monitorexit(syncLock)
    
    public long getMinecraftStartTimeMillis() {
        return this.minecraftStartTimeMilis;
    }
    
    public Map getCurrentStats() {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        // monitorenter(syncLock = this.syncLock)
        this.addMemoryStatsToSnooper();
        for (final Map.Entry<Object, V> entry : this.field_152773_a.entrySet()) {
            linkedHashMap.put(entry.getKey(), entry.getValue().toString());
        }
        for (final Map.Entry<Object, V> entry2 : this.field_152774_b.entrySet()) {
            linkedHashMap.put(entry2.getKey(), entry2.getValue().toString());
        }
        // monitorexit(syncLock)
        return (LinkedHashMap<Object, String>)linkedHashMap;
    }
    
    public void addMemoryStatsToSnooper() {
        this.addStatToSnooper("memory_total", Runtime.getRuntime().totalMemory());
        this.addStatToSnooper("memory_max", Runtime.getRuntime().maxMemory());
        this.addStatToSnooper("memory_free", Runtime.getRuntime().freeMemory());
        this.addStatToSnooper("cpu_cores", Runtime.getRuntime().availableProcessors());
        this.playerStatsCollector.addServerStatsToSnooper(this);
    }
    
    static int access$308(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.selfCounter++;
    }
    
    public void startSnooper() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.func_152766_h();
            this.threadTrigger.schedule(new TimerTask(this) {
                final PlayerUsageSnooper this$0;
                
                @Override
                public void run() {
                    if (PlayerUsageSnooper.access$000(this.this$0).isSnooperEnabled()) {
                        // monitorenter(access$100 = PlayerUsageSnooper.access$100(this.this$0))
                        final HashMap hashMap = Maps.newHashMap(PlayerUsageSnooper.access$200(this.this$0));
                        if (PlayerUsageSnooper.access$300(this.this$0) == 0) {
                            hashMap.putAll(PlayerUsageSnooper.access$400(this.this$0));
                        }
                        hashMap.put("snooper_count", PlayerUsageSnooper.access$308(this.this$0));
                        hashMap.put("snooper_token", PlayerUsageSnooper.access$500(this.this$0));
                        // monitorexit(access$100)
                        HttpUtil.postMap(PlayerUsageSnooper.access$600(this.this$0), hashMap, true);
                    }
                }
            }, 0L, 900000L);
        }
    }
    
    public void stopSnooper() {
        this.threadTrigger.cancel();
    }
    
    static Object access$100(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.syncLock;
    }
    
    public PlayerUsageSnooper(final String s, final IPlayerUsage playerStatsCollector, final long minecraftStartTimeMilis) {
        this.field_152773_a = Maps.newHashMap();
        this.field_152774_b = Maps.newHashMap();
        this.uniqueID = UUID.randomUUID().toString();
        this.threadTrigger = new Timer("Snooper Timer", true);
        this.syncLock = new Object();
        this.serverUrl = new URL("http://snoop.minecraft.net/" + s + "?version=" + 2);
        this.playerStatsCollector = playerStatsCollector;
        this.minecraftStartTimeMilis = minecraftStartTimeMilis;
    }
    
    static Map access$400(final PlayerUsageSnooper playerUsageSnooper) {
        return playerUsageSnooper.field_152773_a;
    }
}
