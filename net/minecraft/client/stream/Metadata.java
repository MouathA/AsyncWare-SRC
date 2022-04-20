package net.minecraft.client.stream;

import java.util.*;
import com.google.gson.*;
import com.google.common.base.*;
import com.google.common.collect.*;

public class Metadata
{
    private String description;
    private final String name;
    private Map payload;
    private static final Gson field_152811_a;
    
    @Override
    public String toString() {
        return Objects.toStringHelper((Object)this).add("name", (Object)this.name).add("description", (Object)this.description).add("data", (Object)this.func_152806_b()).toString();
    }
    
    public String func_152806_b() {
        return (this.payload != null && !this.payload.isEmpty()) ? Metadata.field_152811_a.toJson((Object)this.payload) : null;
    }
    
    public void func_152807_a(final String description) {
        this.description = description;
    }
    
    public String func_152810_c() {
        return this.name;
    }
    
    public String func_152809_a() {
        return (this.description == null) ? this.name : this.description;
    }
    
    public Metadata(final String s) {
        this(s, null);
    }
    
    public Metadata(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
    
    static {
        field_152811_a = new Gson();
    }
    
    public void func_152808_a(final String s, final String s2) {
        if (this.payload == null) {
            this.payload = Maps.newHashMap();
        }
        if (this.payload.size() > 50) {
            throw new IllegalArgumentException("Metadata payload is full, cannot add more to it!");
        }
        if (s == null) {
            throw new IllegalArgumentException("Metadata payload key cannot be null!");
        }
        if (s.length() > 255) {
            throw new IllegalArgumentException("Metadata payload key is too long!");
        }
        if (s2 == null) {
            throw new IllegalArgumentException("Metadata payload value cannot be null!");
        }
        if (s2.length() > 255) {
            throw new IllegalArgumentException("Metadata payload value is too long!");
        }
        this.payload.put(s, s2);
    }
}
