package net.minecraft.server.management;

import com.google.gson.*;
import java.util.*;

public class IPBanEntry extends BanEntry
{
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        if (this.getValue() != null) {
            jsonObject.addProperty("ip", (String)this.getValue());
            super.onSerialization(jsonObject);
        }
    }
    
    public IPBanEntry(final String s) {
        this(s, null, null, null, null);
    }
    
    public IPBanEntry(final JsonObject jsonObject) {
        super(getIPFromJson(jsonObject), jsonObject);
    }
    
    private static String getIPFromJson(final JsonObject jsonObject) {
        return jsonObject.has("ip") ? jsonObject.get("ip").getAsString() : null;
    }
    
    public IPBanEntry(final String s, final Date date, final String s2, final Date date2, final String s3) {
        super(s, date, s2, date2, s3);
    }
}
