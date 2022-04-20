package net.minecraft.stats;

import net.minecraft.server.*;
import java.io.*;
import org.apache.commons.io.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import java.util.*;
import org.apache.logging.log4j.*;
import com.google.gson.*;
import net.minecraft.util.*;

public class StatisticsFile extends StatFileWriter
{
    private final MinecraftServer mcServer;
    private final Set field_150888_e;
    private boolean field_150886_g;
    private int field_150885_f;
    private static final Logger logger;
    private final File statsFile;
    
    public static String dumpJson(final Map map) {
        final JsonObject jsonObject = new JsonObject();
        for (final Map.Entry<K, TupleIntJsonSerializable> entry : map.entrySet()) {
            if (entry.getValue().getJsonSerializableValue() != null) {
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("value", (Number)entry.getValue().getIntegerValue());
                jsonObject2.add("progress", entry.getValue().getJsonSerializableValue().getSerializableElement());
                jsonObject.add(((StatBase)entry.getKey()).statId, (JsonElement)jsonObject2);
            }
            else {
                jsonObject.addProperty(((StatBase)entry.getKey()).statId, (Number)entry.getValue().getIntegerValue());
            }
        }
        return jsonObject.toString();
    }
    
    public StatisticsFile(final MinecraftServer mcServer, final File statsFile) {
        this.field_150888_e = Sets.newHashSet();
        this.field_150885_f = -300;
        this.field_150886_g = false;
        this.mcServer = mcServer;
        this.statsFile = statsFile;
    }
    
    public void func_150877_d() {
        final Iterator<StatBase> iterator = this.statsData.keySet().iterator();
        while (iterator.hasNext()) {
            this.field_150888_e.add(iterator.next());
        }
    }
    
    public Set func_150878_c() {
        final HashSet hashSet = Sets.newHashSet((Iterable)this.field_150888_e);
        this.field_150888_e.clear();
        this.field_150886_g = false;
        return hashSet;
    }
    
    public void saveStatFile() {
        FileUtils.writeStringToFile(this.statsFile, dumpJson(this.statsData));
    }
    
    @Override
    public void unlockAchievement(final EntityPlayer entityPlayer, final StatBase statBase, final int n) {
        final int n2 = statBase.isAchievement() ? this.readStat(statBase) : 0;
        super.unlockAchievement(entityPlayer, statBase, n);
        this.field_150888_e.add(statBase);
        if (statBase.isAchievement() && n2 == 0 && n > 0) {
            this.field_150886_g = true;
            if (this.mcServer.isAnnouncingPlayerAchievements()) {
                this.mcServer.getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.achievement", new Object[] { entityPlayer.getDisplayName(), statBase.func_150955_j() }));
            }
        }
        if (statBase.isAchievement() && n2 > 0 && n == 0) {
            this.field_150886_g = true;
            if (this.mcServer.isAnnouncingPlayerAchievements()) {
                this.mcServer.getConfigurationManager().sendChatMsg(new ChatComponentTranslation("chat.type.achievement.taken", new Object[] { entityPlayer.getDisplayName(), statBase.func_150955_j() }));
            }
        }
    }
    
    public void readStatFile() {
        if (this.statsFile.isFile()) {
            this.statsData.clear();
            this.statsData.putAll(this.parseJson(FileUtils.readFileToString(this.statsFile)));
        }
    }
    
    public void sendAchievements(final EntityPlayerMP entityPlayerMP) {
        final HashMap hashMap = Maps.newHashMap();
        for (final Achievement achievement : AchievementList.achievementList) {
            if (this.hasAchievementUnlocked(achievement)) {
                hashMap.put(achievement, this.readStat(achievement));
                this.field_150888_e.remove(achievement);
            }
        }
        entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashMap));
    }
    
    public boolean func_150879_e() {
        return this.field_150886_g;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    public void func_150876_a(final EntityPlayerMP entityPlayerMP) {
        final int tickCounter = this.mcServer.getTickCounter();
        final HashMap hashMap = Maps.newHashMap();
        if (this.field_150886_g || tickCounter - this.field_150885_f > 300) {
            this.field_150885_f = tickCounter;
            for (final StatBase statBase : this.func_150878_c()) {
                hashMap.put(statBase, this.readStat(statBase));
            }
        }
        entityPlayerMP.playerNetServerHandler.sendPacket(new S37PacketStatistics(hashMap));
    }
    
    public Map parseJson(final String s) {
        final JsonElement parse = new JsonParser().parse(s);
        if (!parse.isJsonObject()) {
            return Maps.newHashMap();
        }
        final JsonObject asJsonObject = parse.getAsJsonObject();
        final HashMap hashMap = Maps.newHashMap();
        for (final Map.Entry<String, V> entry : asJsonObject.entrySet()) {
            final StatBase oneShotStat = StatList.getOneShotStat(entry.getKey());
            if (oneShotStat != null) {
                final TupleIntJsonSerializable tupleIntJsonSerializable = new TupleIntJsonSerializable();
                if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
                    tupleIntJsonSerializable.setIntegerValue(((JsonElement)entry.getValue()).getAsInt());
                }
                else if (((JsonElement)entry.getValue()).isJsonObject()) {
                    final JsonObject asJsonObject2 = ((JsonElement)entry.getValue()).getAsJsonObject();
                    if (asJsonObject2.has("value") && asJsonObject2.get("value").isJsonPrimitive() && asJsonObject2.get("value").getAsJsonPrimitive().isNumber()) {
                        tupleIntJsonSerializable.setIntegerValue(asJsonObject2.getAsJsonPrimitive("value").getAsInt());
                    }
                    if (asJsonObject2.has("progress") && oneShotStat.func_150954_l() != null) {
                        final IJsonSerializable jsonSerializableValue = oneShotStat.func_150954_l().getConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
                        jsonSerializableValue.fromJson(asJsonObject2.get("progress"));
                        tupleIntJsonSerializable.setJsonSerializableValue(jsonSerializableValue);
                    }
                }
                hashMap.put(oneShotStat, tupleIntJsonSerializable);
            }
            else {
                StatisticsFile.logger.warn("Invalid statistic in " + this.statsFile + ": Don't know what " + entry.getKey() + " is");
            }
        }
        return hashMap;
    }
}
