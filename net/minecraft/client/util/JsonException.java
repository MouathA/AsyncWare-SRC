package net.minecraft.client.util;

import java.util.*;
import com.google.common.collect.*;
import java.io.*;
import org.apache.commons.lang3.*;

public class JsonException extends IOException
{
    private final List field_151383_a;
    private final String field_151382_b;
    
    public JsonException(final String field_151382_b) {
        (this.field_151383_a = Lists.newArrayList()).add(new Entry(null));
        this.field_151382_b = field_151382_b;
    }
    
    public JsonException(final String field_151382_b, final Throwable t) {
        super(t);
        (this.field_151383_a = Lists.newArrayList()).add(new Entry(null));
        this.field_151382_b = field_151382_b;
    }
    
    public void func_151381_b(final String s) {
        Entry.access$202(this.field_151383_a.get(0), s);
        this.field_151383_a.add(0, new Entry(null));
    }
    
    public static JsonException func_151379_a(final Exception ex) {
        if (ex instanceof JsonException) {
            return (JsonException)ex;
        }
        String message = ex.getMessage();
        if (ex instanceof FileNotFoundException) {
            message = "File not found";
        }
        return new JsonException(message, ex);
    }
    
    @Override
    public String getMessage() {
        return "Invalid " + this.field_151383_a.get(this.field_151383_a.size() - 1).toString() + ": " + this.field_151382_b;
    }
    
    public void func_151380_a(final String s) {
        Entry.access$100(this.field_151383_a.get(0), s);
    }
    
    public static class Entry
    {
        private final List field_151375_b;
        private String field_151376_a;
        
        Entry(final JsonException$1 object) {
            this();
        }
        
        static String access$202(final Entry entry, final String field_151376_a) {
            return entry.field_151376_a = field_151376_a;
        }
        
        @Override
        public String toString() {
            return (this.field_151376_a != null) ? (this.field_151375_b.isEmpty() ? this.field_151376_a : (this.field_151376_a + " " + this.func_151372_b())) : (this.field_151375_b.isEmpty() ? "(Unknown file)" : ("(Unknown file) " + this.func_151372_b()));
        }
        
        static void access$100(final Entry entry, final String s) {
            entry.func_151373_a(s);
        }
        
        private void func_151373_a(final String s) {
            this.field_151375_b.add(0, s);
        }
        
        private Entry() {
            this.field_151376_a = null;
            this.field_151375_b = Lists.newArrayList();
        }
        
        public String func_151372_b() {
            return StringUtils.join((Iterable)this.field_151375_b, "->");
        }
    }
}
