package net.minecraft.util;

import net.minecraft.event.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class ChatStyle
{
    private HoverEvent chatHoverEvent;
    private Boolean strikethrough;
    private Boolean italic;
    private EnumChatFormatting color;
    private String insertion;
    private Boolean underlined;
    private static final ChatStyle rootStyle;
    private ClickEvent chatClickEvent;
    private ChatStyle parentStyle;
    private Boolean bold;
    private Boolean obfuscated;
    
    public HoverEvent getChatHoverEvent() {
        return (this.chatHoverEvent == null) ? this.getParent().getChatHoverEvent() : this.chatHoverEvent;
    }
    
    static ClickEvent access$700(final ChatStyle chatStyle) {
        return chatStyle.chatClickEvent;
    }
    
    public ChatStyle setChatHoverEvent(final HoverEvent chatHoverEvent) {
        this.chatHoverEvent = chatHoverEvent;
        return this;
    }
    
    @Override
    public boolean equals(final Object p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: if_acmpne       7
        //     5: iconst_1       
        //     6: ireturn        
        //     7: aload_1        
        //     8: instanceof      Lnet/minecraft/util/ChatStyle;
        //    11: ifne            16
        //    14: iconst_0       
        //    15: ireturn        
        //    16: aload_1        
        //    17: checkcast       Lnet/minecraft/util/ChatStyle;
        //    20: astore_3       
        //    21: aload_0        
        //    22: invokevirtual   net/minecraft/util/ChatStyle.getBold:()Z
        //    25: aload_3        
        //    26: ifnonnull       174
        //    29: aload_0        
        //    30: invokevirtual   net/minecraft/util/ChatStyle.getColor:()Lnet/minecraft/util/EnumChatFormatting;
        //    33: aload_3        
        //    34: invokevirtual   net/minecraft/util/ChatStyle.getColor:()Lnet/minecraft/util/EnumChatFormatting;
        //    37: if_acmpne       174
        //    40: aload_0        
        //    41: invokevirtual   net/minecraft/util/ChatStyle.getItalic:()Z
        //    44: aload_3        
        //    45: ifnonnull       174
        //    48: aload_0        
        //    49: invokevirtual   net/minecraft/util/ChatStyle.getObfuscated:()Z
        //    52: aload_3        
        //    53: ifnonnull       174
        //    56: aload_0        
        //    57: invokevirtual   net/minecraft/util/ChatStyle.getStrikethrough:()Z
        //    60: aload_3        
        //    61: ifnonnull       174
        //    64: aload_0        
        //    65: invokevirtual   net/minecraft/util/ChatStyle.getUnderlined:()Z
        //    68: aload_3        
        //    69: ifnonnull       174
        //    72: aload_0        
        //    73: invokevirtual   net/minecraft/util/ChatStyle.getChatClickEvent:()Lnet/minecraft/event/ClickEvent;
        //    76: ifnull          96
        //    79: aload_0        
        //    80: invokevirtual   net/minecraft/util/ChatStyle.getChatClickEvent:()Lnet/minecraft/event/ClickEvent;
        //    83: aload_3        
        //    84: invokevirtual   net/minecraft/util/ChatStyle.getChatClickEvent:()Lnet/minecraft/event/ClickEvent;
        //    87: invokevirtual   net/minecraft/event/ClickEvent.equals:(Ljava/lang/Object;)Z
        //    90: ifne            106
        //    93: goto            174
        //    96: aload_3        
        //    97: invokevirtual   net/minecraft/util/ChatStyle.getChatClickEvent:()Lnet/minecraft/event/ClickEvent;
        //   100: ifnull          106
        //   103: goto            174
        //   106: aload_0        
        //   107: invokevirtual   net/minecraft/util/ChatStyle.getChatHoverEvent:()Lnet/minecraft/event/HoverEvent;
        //   110: ifnull          130
        //   113: aload_0        
        //   114: invokevirtual   net/minecraft/util/ChatStyle.getChatHoverEvent:()Lnet/minecraft/event/HoverEvent;
        //   117: aload_3        
        //   118: invokevirtual   net/minecraft/util/ChatStyle.getChatHoverEvent:()Lnet/minecraft/event/HoverEvent;
        //   121: invokevirtual   net/minecraft/event/HoverEvent.equals:(Ljava/lang/Object;)Z
        //   124: ifne            140
        //   127: goto            174
        //   130: aload_3        
        //   131: invokevirtual   net/minecraft/util/ChatStyle.getChatHoverEvent:()Lnet/minecraft/event/HoverEvent;
        //   134: ifnull          140
        //   137: goto            174
        //   140: aload_0        
        //   141: invokevirtual   net/minecraft/util/ChatStyle.getInsertion:()Ljava/lang/String;
        //   144: ifnull          164
        //   147: aload_0        
        //   148: invokevirtual   net/minecraft/util/ChatStyle.getInsertion:()Ljava/lang/String;
        //   151: aload_3        
        //   152: invokevirtual   net/minecraft/util/ChatStyle.getInsertion:()Ljava/lang/String;
        //   155: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   158: ifeq            174
        //   161: goto            176
        //   164: aload_3        
        //   165: invokevirtual   net/minecraft/util/ChatStyle.getInsertion:()Ljava/lang/String;
        //   168: ifnonnull       174
        //   171: goto            176
        //   174: iconst_1       
        //   175: ireturn        
        //   176: iconst_1       
        //   177: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0174 (coming from #0045).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2183)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:192)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:45)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public ChatStyle setUnderlined(final Boolean underlined) {
        this.underlined = underlined;
        return this;
    }
    
    static Boolean access$300(final ChatStyle chatStyle) {
        return chatStyle.strikethrough;
    }
    
    public ChatStyle createShallowCopy() {
        final ChatStyle chatStyle = new ChatStyle();
        chatStyle.bold = this.bold;
        chatStyle.italic = this.italic;
        chatStyle.strikethrough = this.strikethrough;
        chatStyle.underlined = this.underlined;
        chatStyle.obfuscated = this.obfuscated;
        chatStyle.color = this.color;
        chatStyle.chatClickEvent = this.chatClickEvent;
        chatStyle.chatHoverEvent = this.chatHoverEvent;
        chatStyle.parentStyle = this.parentStyle;
        chatStyle.insertion = this.insertion;
        return chatStyle;
    }
    
    static HoverEvent access$802(final ChatStyle chatStyle, final HoverEvent chatHoverEvent) {
        return chatStyle.chatHoverEvent = chatHoverEvent;
    }
    
    public ChatStyle setInsertion(final String insertion) {
        this.insertion = insertion;
        return this;
    }
    
    static Boolean access$402(final ChatStyle chatStyle, final Boolean obfuscated) {
        return chatStyle.obfuscated = obfuscated;
    }
    
    public ChatStyle setBold(final Boolean bold) {
        this.bold = bold;
        return this;
    }
    
    @Override
    public String toString() {
        return "Style{hasParent=" + (this.parentStyle != null) + ", color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", obfuscated=" + this.obfuscated + ", clickEvent=" + this.getChatClickEvent() + ", hoverEvent=" + this.getChatHoverEvent() + ", insertion=" + this.getInsertion() + '}';
    }
    
    static ClickEvent access$702(final ChatStyle chatStyle, final ClickEvent chatClickEvent) {
        return chatStyle.chatClickEvent = chatClickEvent;
    }
    
    static Boolean access$102(final ChatStyle chatStyle, final Boolean italic) {
        return chatStyle.italic = italic;
    }
    
    static Boolean access$002(final ChatStyle chatStyle, final Boolean bold) {
        return chatStyle.bold = bold;
    }
    
    public EnumChatFormatting getColor() {
        return (this.color == null) ? this.getParent().getColor() : this.color;
    }
    
    public ChatStyle createDeepCopy() {
        final ChatStyle chatStyle = new ChatStyle();
        chatStyle.setBold(this.getBold());
        chatStyle.setItalic(this.getItalic());
        chatStyle.setStrikethrough(this.getStrikethrough());
        chatStyle.setUnderlined(this.getUnderlined());
        chatStyle.setObfuscated(this.getObfuscated());
        chatStyle.setColor(this.getColor());
        chatStyle.setChatClickEvent(this.getChatClickEvent());
        chatStyle.setChatHoverEvent(this.getChatHoverEvent());
        chatStyle.setInsertion(this.getInsertion());
        return chatStyle;
    }
    
    @Override
    public int hashCode() {
        return 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * this.color.hashCode() + this.bold.hashCode()) + this.italic.hashCode()) + this.underlined.hashCode()) + this.strikethrough.hashCode()) + this.obfuscated.hashCode()) + this.chatClickEvent.hashCode()) + this.chatHoverEvent.hashCode()) + this.insertion.hashCode();
    }
    
    static Boolean access$302(final ChatStyle chatStyle, final Boolean strikethrough) {
        return chatStyle.strikethrough = strikethrough;
    }
    
    static String access$602(final ChatStyle chatStyle, final String insertion) {
        return chatStyle.insertion = insertion;
    }
    
    static Boolean access$202(final ChatStyle chatStyle, final Boolean underlined) {
        return chatStyle.underlined = underlined;
    }
    
    public ChatStyle setParentStyle(final ChatStyle parentStyle) {
        this.parentStyle = parentStyle;
        return this;
    }
    
    static {
        rootStyle = new ChatStyle() {
            @Override
            public String getFormattingCode() {
                return "";
            }
            
            @Override
            public String toString() {
                return "Style.ROOT";
            }
            
            public boolean getBold() {
                return false;
            }
            
            @Override
            public ChatStyle createDeepCopy() {
                return this;
            }
            
            @Override
            public EnumChatFormatting getColor() {
                return null;
            }
            
            @Override
            public ChatStyle createShallowCopy() {
                return this;
            }
            
            @Override
            public ChatStyle setChatClickEvent(final ClickEvent clickEvent) {
                throw new UnsupportedOperationException();
            }
            
            public boolean getUnderlined() {
                return false;
            }
            
            @Override
            public ChatStyle setColor(final EnumChatFormatting enumChatFormatting) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setObfuscated(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public HoverEvent getChatHoverEvent() {
                return null;
            }
            
            @Override
            public ChatStyle setBold(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            public boolean getItalic() {
                return false;
            }
            
            @Override
            public String getInsertion() {
                return null;
            }
            
            @Override
            public ChatStyle setItalic(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            public boolean getStrikethrough() {
                return false;
            }
            
            @Override
            public ChatStyle setParentStyle(final ChatStyle chatStyle) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setChatHoverEvent(final HoverEvent hoverEvent) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ClickEvent getChatClickEvent() {
                return null;
            }
            
            @Override
            public ChatStyle setUnderlined(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setStrikethrough(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            public boolean getObfuscated() {
                return false;
            }
        };
    }
    
    private ChatStyle getParent() {
        return (this.parentStyle == null) ? ChatStyle.rootStyle : this.parentStyle;
    }
    
    static Boolean access$000(final ChatStyle chatStyle) {
        return chatStyle.bold;
    }
    
    static Boolean access$400(final ChatStyle chatStyle) {
        return chatStyle.obfuscated;
    }
    
    static HoverEvent access$800(final ChatStyle chatStyle) {
        return chatStyle.chatHoverEvent;
    }
    
    public ChatStyle setObfuscated(final Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }
    
    static EnumChatFormatting access$502(final ChatStyle chatStyle, final EnumChatFormatting color) {
        return chatStyle.color = color;
    }
    
    static Boolean access$200(final ChatStyle chatStyle) {
        return chatStyle.underlined;
    }
    
    static EnumChatFormatting access$500(final ChatStyle chatStyle) {
        return chatStyle.color;
    }
    
    public ChatStyle setStrikethrough(final Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }
    
    static Boolean access$100(final ChatStyle chatStyle) {
        return chatStyle.italic;
    }
    
    public ChatStyle setChatClickEvent(final ClickEvent chatClickEvent) {
        this.chatClickEvent = chatClickEvent;
        return this;
    }
    
    public ChatStyle setColor(final EnumChatFormatting color) {
        this.color = color;
        return this;
    }
    
    static String access$600(final ChatStyle chatStyle) {
        return chatStyle.insertion;
    }
    
    public ChatStyle setItalic(final Boolean italic) {
        this.italic = italic;
        return this;
    }
    
    public String getFormattingCode() {
        if (this == null) {
            return (this.parentStyle != null) ? this.parentStyle.getFormattingCode() : "";
        }
        final StringBuilder sb = new StringBuilder();
        if (this.getColor() != null) {
            sb.append(this.getColor());
        }
        if (this == null) {
            sb.append(EnumChatFormatting.BOLD);
        }
        if (this == null) {
            sb.append(EnumChatFormatting.ITALIC);
        }
        if (this == null) {
            sb.append(EnumChatFormatting.UNDERLINE);
        }
        if (this == null) {
            sb.append(EnumChatFormatting.OBFUSCATED);
        }
        if (this == null) {
            sb.append(EnumChatFormatting.STRIKETHROUGH);
        }
        return sb.toString();
    }
    
    public ClickEvent getChatClickEvent() {
        return (this.chatClickEvent == null) ? this.getParent().getChatClickEvent() : this.chatClickEvent;
    }
    
    public String getInsertion() {
        return (this.insertion == null) ? this.getParent().getInsertion() : this.insertion;
    }
    
    public static class Serializer implements JsonSerializer, JsonDeserializer
    {
        public JsonElement serialize(final ChatStyle chatStyle, final Type type, final JsonSerializationContext jsonSerializationContext) {
            if (chatStyle.isEmpty()) {
                return null;
            }
            final JsonObject jsonObject = new JsonObject();
            if (ChatStyle.access$000(chatStyle) != null) {
                jsonObject.addProperty("bold", ChatStyle.access$000(chatStyle));
            }
            if (ChatStyle.access$100(chatStyle) != null) {
                jsonObject.addProperty("italic", ChatStyle.access$100(chatStyle));
            }
            if (ChatStyle.access$200(chatStyle) != null) {
                jsonObject.addProperty("underlined", ChatStyle.access$200(chatStyle));
            }
            if (ChatStyle.access$300(chatStyle) != null) {
                jsonObject.addProperty("strikethrough", ChatStyle.access$300(chatStyle));
            }
            if (ChatStyle.access$400(chatStyle) != null) {
                jsonObject.addProperty("obfuscated", ChatStyle.access$400(chatStyle));
            }
            if (ChatStyle.access$500(chatStyle) != null) {
                jsonObject.add("color", jsonSerializationContext.serialize((Object)ChatStyle.access$500(chatStyle)));
            }
            if (ChatStyle.access$600(chatStyle) != null) {
                jsonObject.add("insertion", jsonSerializationContext.serialize((Object)ChatStyle.access$600(chatStyle)));
            }
            if (ChatStyle.access$700(chatStyle) != null) {
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("action", ChatStyle.access$700(chatStyle).getAction().getCanonicalName());
                jsonObject2.addProperty("value", ChatStyle.access$700(chatStyle).getValue());
                jsonObject.add("clickEvent", (JsonElement)jsonObject2);
            }
            if (ChatStyle.access$800(chatStyle) != null) {
                final JsonObject jsonObject3 = new JsonObject();
                jsonObject3.addProperty("action", ChatStyle.access$800(chatStyle).getAction().getCanonicalName());
                jsonObject3.add("value", jsonSerializationContext.serialize((Object)ChatStyle.access$800(chatStyle).getValue()));
                jsonObject.add("hoverEvent", (JsonElement)jsonObject3);
            }
            return (JsonElement)jsonObject;
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ChatStyle)o, type, jsonSerializationContext);
        }
        
        public ChatStyle deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            final ChatStyle chatStyle = new ChatStyle();
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            if (asJsonObject == null) {
                return null;
            }
            if (asJsonObject.has("bold")) {
                ChatStyle.access$002(chatStyle, asJsonObject.get("bold").getAsBoolean());
            }
            if (asJsonObject.has("italic")) {
                ChatStyle.access$102(chatStyle, asJsonObject.get("italic").getAsBoolean());
            }
            if (asJsonObject.has("underlined")) {
                ChatStyle.access$202(chatStyle, asJsonObject.get("underlined").getAsBoolean());
            }
            if (asJsonObject.has("strikethrough")) {
                ChatStyle.access$302(chatStyle, asJsonObject.get("strikethrough").getAsBoolean());
            }
            if (asJsonObject.has("obfuscated")) {
                ChatStyle.access$402(chatStyle, asJsonObject.get("obfuscated").getAsBoolean());
            }
            if (asJsonObject.has("color")) {
                ChatStyle.access$502(chatStyle, (EnumChatFormatting)jsonDeserializationContext.deserialize(asJsonObject.get("color"), (Type)EnumChatFormatting.class));
            }
            if (asJsonObject.has("insertion")) {
                ChatStyle.access$602(chatStyle, asJsonObject.get("insertion").getAsString());
            }
            if (asJsonObject.has("clickEvent")) {
                final JsonObject asJsonObject2 = asJsonObject.getAsJsonObject("clickEvent");
                if (asJsonObject2 != null) {
                    final JsonPrimitive asJsonPrimitive = asJsonObject2.getAsJsonPrimitive("action");
                    final ClickEvent.Action action = (asJsonPrimitive == null) ? null : ClickEvent.Action.getValueByCanonicalName(asJsonPrimitive.getAsString());
                    final JsonPrimitive asJsonPrimitive2 = asJsonObject2.getAsJsonPrimitive("value");
                    final String s = (asJsonPrimitive2 == null) ? null : asJsonPrimitive2.getAsString();
                    if (action != null && s != null && action.shouldAllowInChat()) {
                        ChatStyle.access$702(chatStyle, new ClickEvent(action, s));
                    }
                }
            }
            if (asJsonObject.has("hoverEvent")) {
                final JsonObject asJsonObject3 = asJsonObject.getAsJsonObject("hoverEvent");
                if (asJsonObject3 != null) {
                    final JsonPrimitive asJsonPrimitive3 = asJsonObject3.getAsJsonPrimitive("action");
                    final HoverEvent.Action action2 = (asJsonPrimitive3 == null) ? null : HoverEvent.Action.getValueByCanonicalName(asJsonPrimitive3.getAsString());
                    final IChatComponent chatComponent = (IChatComponent)jsonDeserializationContext.deserialize(asJsonObject3.get("value"), (Type)IChatComponent.class);
                    if (action2 != null && chatComponent != null && action2.shouldAllowInChat()) {
                        ChatStyle.access$802(chatStyle, new HoverEvent(action2, chatComponent));
                    }
                }
            }
            return chatStyle;
        }
    }
}
