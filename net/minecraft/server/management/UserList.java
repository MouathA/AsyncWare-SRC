package net.minecraft.server.management;

import org.apache.logging.log4j.*;
import java.lang.reflect.*;
import com.google.common.base.*;
import com.google.common.io.*;
import org.apache.commons.io.*;
import java.io.*;
import com.google.common.collect.*;
import java.util.*;
import com.google.gson.*;

public class UserList
{
    protected final Gson gson;
    private boolean lanServer;
    private final File saveFile;
    protected static final Logger logger;
    private static final ParameterizedType saveFileFormat;
    private final Map values;
    
    public void addEntry(final UserListEntry userListEntry) {
        this.values.put(this.getObjectKey(userListEntry.getValue()), userListEntry);
        this.writeChanges();
    }
    
    protected UserListEntry createEntry(final JsonObject jsonObject) {
        return new UserListEntry(null, jsonObject);
    }
    
    protected Map getValues() {
        return this.values;
    }
    
    static {
        logger = LogManager.getLogger();
        saveFileFormat = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[] { UserListEntry.class };
            }
            
            @Override
            public Type getRawType() {
                return List.class;
            }
            
            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
    
    public UserListEntry getEntry(final Object o) {
        this.removeExpired();
        return this.values.get(this.getObjectKey(o));
    }
    
    public UserList(final File saveFile) {
        this.values = Maps.newHashMap();
        this.lanServer = true;
        this.saveFile = saveFile;
        final GsonBuilder setPrettyPrinting = new GsonBuilder().setPrettyPrinting();
        setPrettyPrinting.registerTypeHierarchyAdapter((Class)UserListEntry.class, (Object)new Serializer(null));
        this.gson = setPrettyPrinting.create();
    }
    
    public void removeEntry(final Object o) {
        this.values.remove(this.getObjectKey(o));
        this.writeChanges();
    }
    
    public void writeChanges() throws IOException {
        final String json = this.gson.toJson((Object)this.values.values());
        final BufferedWriter writer = Files.newWriter(this.saveFile, Charsets.UTF_8);
        writer.write(json);
        IOUtils.closeQuietly((Writer)writer);
    }
    
    protected boolean hasEntry(final Object o) {
        return this.values.containsKey(this.getObjectKey(o));
    }
    
    public String[] getKeys() {
        return (String[])this.values.keySet().toArray(new String[this.values.size()]);
    }
    
    private void removeExpired() {
        final ArrayList arrayList = Lists.newArrayList();
        for (final UserListEntry userListEntry : this.values.values()) {
            if (userListEntry.hasBanExpired()) {
                arrayList.add(userListEntry.getValue());
            }
        }
        final Iterator<Object> iterator2 = arrayList.iterator();
        while (iterator2.hasNext()) {
            this.values.remove(iterator2.next());
        }
    }
    
    protected String getObjectKey(final Object o) {
        return o.toString();
    }
    
    public boolean isLanServer() {
        return this.lanServer;
    }
    
    public void setLanServer(final boolean lanServer) {
        this.lanServer = lanServer;
    }
    
    class Serializer implements JsonSerializer, JsonDeserializer
    {
        final UserList this$0;
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((UserListEntry)o, type, jsonSerializationContext);
        }
        
        public JsonElement serialize(final UserListEntry userListEntry, final Type type, final JsonSerializationContext jsonSerializationContext) {
            final JsonObject jsonObject = new JsonObject();
            userListEntry.onSerialization(jsonObject);
            return (JsonElement)jsonObject;
        }
        
        Serializer(final UserList list, final UserList$1 parameterizedType) {
            this(list);
        }
        
        public UserListEntry deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonObject()) {
                return this.this$0.createEntry(jsonElement.getAsJsonObject());
            }
            return null;
        }
        
        private Serializer(final UserList this$0) {
            this.this$0 = this$0;
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}
