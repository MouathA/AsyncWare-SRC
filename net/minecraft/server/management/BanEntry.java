package net.minecraft.server.management;

import java.util.*;
import java.text.*;
import com.google.gson.*;

public abstract class BanEntry extends UserListEntry
{
    protected final Date banStartDate;
    protected final String bannedBy;
    protected final String reason;
    public static final SimpleDateFormat dateFormat;
    protected final Date banEndDate;
    
    public BanEntry(final Object o, final Date date, final String s, final Date banEndDate, final String s2) {
        super(o);
        this.banStartDate = ((date == null) ? new Date() : date);
        this.bannedBy = ((s == null) ? "(Unknown)" : s);
        this.banEndDate = banEndDate;
        this.reason = ((s2 == null) ? "Banned by an operator." : s2);
    }
    
    @Override
    protected void onSerialization(final JsonObject jsonObject) {
        jsonObject.addProperty("created", BanEntry.dateFormat.format(this.banStartDate));
        jsonObject.addProperty("source", this.bannedBy);
        jsonObject.addProperty("expires", (this.banEndDate == null) ? "forever" : BanEntry.dateFormat.format(this.banEndDate));
        jsonObject.addProperty("reason", this.reason);
    }
    
    public String getBanReason() {
        return this.reason;
    }
    
    @Override
    boolean hasBanExpired() {
        return this.banEndDate != null && this.banEndDate.before(new Date());
    }
    
    protected BanEntry(final Object o, final JsonObject jsonObject) {
        super(o, jsonObject);
        this.banStartDate = (jsonObject.has("created") ? BanEntry.dateFormat.parse(jsonObject.get("created").getAsString()) : new Date());
        this.bannedBy = (jsonObject.has("source") ? jsonObject.get("source").getAsString() : "(Unknown)");
        this.banEndDate = (jsonObject.has("expires") ? BanEntry.dateFormat.parse(jsonObject.get("expires").getAsString()) : null);
        this.reason = (jsonObject.has("reason") ? jsonObject.get("reason").getAsString() : "Banned by an operator.");
    }
    
    public Date getBanEndDate() {
        return this.banEndDate;
    }
    
    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }
}
