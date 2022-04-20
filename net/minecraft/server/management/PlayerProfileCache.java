package net.minecraft.server.management;

import net.minecraft.server.*;
import java.text.*;
import java.lang.reflect.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import com.google.common.io.*;
import org.apache.commons.io.*;
import java.util.*;
import java.io.*;
import com.google.common.collect.*;
import com.google.gson.*;

public class PlayerProfileCache
{
    private final File usercacheFile;
    private final Map usernameToProfileEntryMap;
    private final LinkedList gameProfiles;
    private final MinecraftServer mcServer;
    private final Map uuidToProfileEntryMap;
    protected final Gson gson;
    private static final ParameterizedType TYPE;
    public static final SimpleDateFormat dateFormat;
    
    private ProfileEntry getByUUID(final UUID uuid) {
        final ProfileEntry profileEntry = this.uuidToProfileEntryMap.get(uuid);
        if (profileEntry != null) {
            final GameProfile gameProfile = profileEntry.getGameProfile();
            this.gameProfiles.remove(gameProfile);
            this.gameProfiles.addFirst(gameProfile);
        }
        return profileEntry;
    }
    
    private void addEntry(final GameProfile gameProfile, Date time) {
        final UUID id = gameProfile.getId();
        if (time == null) {
            final Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            instance.add(2, 1);
            time = instance.getTime();
        }
        gameProfile.getName().toLowerCase(Locale.ROOT);
        final ProfileEntry profileEntry = new ProfileEntry(gameProfile, time, null);
        if (this.uuidToProfileEntryMap.containsKey(id)) {
            this.usernameToProfileEntryMap.remove(((ProfileEntry)this.uuidToProfileEntryMap.get(id)).getGameProfile().getName().toLowerCase(Locale.ROOT));
            this.gameProfiles.remove(gameProfile);
        }
        this.usernameToProfileEntryMap.put(gameProfile.getName().toLowerCase(Locale.ROOT), profileEntry);
        this.uuidToProfileEntryMap.put(id, profileEntry);
        this.gameProfiles.addFirst(gameProfile);
        this.save();
    }
    
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        TYPE = new ParameterizedType() {
            @Override
            public Type getOwnerType() {
                return null;
            }
            
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { ProfileEntry.class };
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
        };
    }
    
    public String[] getUsernames() {
        final ArrayList arrayList = Lists.newArrayList((Iterable)this.usernameToProfileEntryMap.keySet());
        return (String[])arrayList.toArray(new String[arrayList.size()]);
    }
    
    public GameProfile getProfileByUUID(final UUID uuid) {
        final ProfileEntry profileEntry = this.uuidToProfileEntryMap.get(uuid);
        return (profileEntry == null) ? null : profileEntry.getGameProfile();
    }
    
    public void addEntry(final GameProfile gameProfile) {
        this.addEntry(gameProfile, null);
    }
    
    private static GameProfile getGameProfile(final MinecraftServer minecraftServer, final String s) {
        final GameProfile[] array = { null };
        final ProfileLookupCallback profileLookupCallback = (ProfileLookupCallback)new ProfileLookupCallback(array) {
            final GameProfile[] val$agameprofile;
            
            public void onProfileLookupFailed(final GameProfile gameProfile, final Exception ex) {
                this.val$agameprofile[0] = null;
            }
            
            public void onProfileLookupSucceeded(final GameProfile gameProfile) {
                this.val$agameprofile[0] = gameProfile;
            }
        };
        minecraftServer.getGameProfileRepository().findProfilesByNames(new String[] { s }, Agent.MINECRAFT, (ProfileLookupCallback)profileLookupCallback);
        if (!minecraftServer.isServerInOnlineMode() && array[0] == null) {
            ((ProfileLookupCallback)profileLookupCallback).onProfileLookupSucceeded(new GameProfile(EntityPlayer.getUUID(new GameProfile((UUID)null, s)), s));
        }
        return array[0];
    }
    
    public GameProfile getGameProfileForUsername(final String s) {
        final String lowerCase = s.toLowerCase(Locale.ROOT);
        ProfileEntry profileEntry = this.usernameToProfileEntryMap.get(lowerCase);
        if (profileEntry != null && new Date().getTime() >= ProfileEntry.access$200(profileEntry).getTime()) {
            this.uuidToProfileEntryMap.remove(profileEntry.getGameProfile().getId());
            this.usernameToProfileEntryMap.remove(profileEntry.getGameProfile().getName().toLowerCase(Locale.ROOT));
            this.gameProfiles.remove(profileEntry.getGameProfile());
            profileEntry = null;
        }
        if (profileEntry != null) {
            final GameProfile gameProfile = profileEntry.getGameProfile();
            this.gameProfiles.remove(gameProfile);
            this.gameProfiles.addFirst(gameProfile);
        }
        else {
            final GameProfile gameProfile2 = getGameProfile(this.mcServer, lowerCase);
            if (gameProfile2 != null) {
                this.addEntry(gameProfile2);
                profileEntry = this.usernameToProfileEntryMap.get(lowerCase);
            }
        }
        this.save();
        return (profileEntry == null) ? null : profileEntry.getGameProfile();
    }
    
    public void save() {
        final String json = this.gson.toJson((Object)this.getEntriesWithLimit(1000));
        final BufferedWriter writer = Files.newWriter(this.usercacheFile, Charsets.UTF_8);
        writer.write(json);
        IOUtils.closeQuietly((Writer)writer);
    }
    
    private List getEntriesWithLimit(final int n) {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<GameProfile> iterator = Lists.newArrayList(Iterators.limit((Iterator)this.gameProfiles.iterator(), n)).iterator();
        while (iterator.hasNext()) {
            final ProfileEntry byUUID = this.getByUUID(iterator.next().getId());
            if (byUUID != null) {
                arrayList.add(byUUID);
            }
        }
        return arrayList;
    }
    
    public void load() {
        final BufferedReader reader = Files.newReader(this.usercacheFile, Charsets.UTF_8);
        final List list = (List)this.gson.fromJson((Reader)reader, (Type)PlayerProfileCache.TYPE);
        this.usernameToProfileEntryMap.clear();
        this.uuidToProfileEntryMap.clear();
        this.gameProfiles.clear();
        for (final ProfileEntry profileEntry : Lists.reverse(list)) {
            if (profileEntry != null) {
                this.addEntry(profileEntry.getGameProfile(), profileEntry.getExpirationDate());
            }
        }
        IOUtils.closeQuietly((Reader)reader);
    }
    
    public PlayerProfileCache(final MinecraftServer mcServer, final File usercacheFile) {
        this.usernameToProfileEntryMap = Maps.newHashMap();
        this.uuidToProfileEntryMap = Maps.newHashMap();
        this.gameProfiles = Lists.newLinkedList();
        this.mcServer = mcServer;
        this.usercacheFile = usercacheFile;
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter((Class)ProfileEntry.class, (Object)new Serializer(null));
        this.gson = gsonBuilder.create();
        this.load();
    }
    
    class ProfileEntry
    {
        final PlayerProfileCache this$0;
        private final Date expirationDate;
        private final GameProfile gameProfile;
        
        private ProfileEntry(final PlayerProfileCache this$0, final GameProfile gameProfile, final Date expirationDate) {
            this.this$0 = this$0;
            this.gameProfile = gameProfile;
            this.expirationDate = expirationDate;
        }
        
        public Date getExpirationDate() {
            return this.expirationDate;
        }
        
        ProfileEntry(final PlayerProfileCache playerProfileCache, final GameProfile gameProfile, final Date date, final PlayerProfileCache$1 parameterizedType) {
            this(playerProfileCache, gameProfile, date);
        }
        
        public GameProfile getGameProfile() {
            return this.gameProfile;
        }
        
        static Date access$200(final ProfileEntry profileEntry) {
            return profileEntry.expirationDate;
        }
    }
    
    class Serializer implements JsonSerializer, JsonDeserializer
    {
        final PlayerProfileCache this$0;
        
        private Serializer(final PlayerProfileCache this$0) {
            this.this$0 = this$0;
        }
        
        Serializer(final PlayerProfileCache playerProfileCache, final PlayerProfileCache$1 parameterizedType) {
            this(playerProfileCache);
        }
        
        public ProfileEntry deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            final JsonElement value = asJsonObject.get("name");
            final JsonElement value2 = asJsonObject.get("uuid");
            final JsonElement value3 = asJsonObject.get("expiresOn");
            if (value == null || value2 == null) {
                return null;
            }
            final String asString = value2.getAsString();
            final String asString2 = value.getAsString();
            Date parse = null;
            if (value3 != null) {
                parse = PlayerProfileCache.dateFormat.parse(value3.getAsString());
            }
            if (asString2 != null && asString != null) {
                final UUID fromString = UUID.fromString(asString);
                final PlayerProfileCache this$0 = this.this$0;
                this$0.getClass();
                return this$0.new ProfileEntry(new GameProfile(fromString, asString2), parse, null);
            }
            return null;
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ProfileEntry)o, type, jsonSerializationContext);
        }
        
        public JsonElement serialize(final ProfileEntry profileEntry, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", profileEntry.getGameProfile().getName());
            final UUID id = profileEntry.getGameProfile().getId();
            jsonObject.addProperty("uuid", (id == null) ? "" : id.toString());
            jsonObject.addProperty("expiresOn", PlayerProfileCache.dateFormat.format(profileEntry.getExpirationDate()));
            return (JsonElement)jsonObject;
        }
    }
}
