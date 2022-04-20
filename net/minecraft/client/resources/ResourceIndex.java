package net.minecraft.client.resources;

import com.google.common.collect.*;
import com.google.common.base.*;
import com.google.common.io.*;
import com.google.gson.*;
import net.minecraft.util.*;
import org.apache.commons.io.*;
import java.io.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class ResourceIndex
{
    private final Map resourceMap;
    private static final Logger logger;
    
    public ResourceIndex(final File file, final String s) {
        this.resourceMap = Maps.newHashMap();
        if (s != null) {
            final File file2 = new File(file, "objects");
            final BufferedReader reader = Files.newReader(new File(file, "indexes/" + s + ".json"), Charsets.UTF_8);
            final JsonObject jsonObject = JsonUtils.getJsonObject(new JsonParser().parse((Reader)reader).getAsJsonObject(), "objects", null);
            if (jsonObject != null) {
                for (final Map.Entry<K, JsonObject> entry : jsonObject.entrySet()) {
                    final JsonObject jsonObject2 = entry.getValue();
                    final String[] split = ((String)entry.getKey()).split("/", 2);
                    final String s2 = (split.length == 1) ? split[0] : (split[0] + ":" + split[1]);
                    final String string = JsonUtils.getString(jsonObject2, "hash");
                    this.resourceMap.put(s2, new File(file2, string.substring(0, 2) + "/" + string));
                }
            }
            IOUtils.closeQuietly((Reader)reader);
        }
    }
    
    public Map getResourceMap() {
        return this.resourceMap;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
