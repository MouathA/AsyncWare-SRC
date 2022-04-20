package net.minecraft.util;

import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;

public interface IChatComponent extends Iterable
{
    List getSiblings();
    
    IChatComponent appendText(final String p0);
    
    String getUnformattedText();
    
    String getFormattedText();
    
    ChatStyle getChatStyle();
    
    String getUnformattedTextForChat();
    
    IChatComponent setChatStyle(final ChatStyle p0);
    
    IChatComponent createCopy();
    
    IChatComponent appendSibling(final IChatComponent p0);
    
    public static class Serializer implements JsonDeserializer, JsonSerializer
    {
        private static final Gson GSON;
        
        public static IChatComponent jsonToComponent(final String s) {
            return (IChatComponent)Serializer.GSON.fromJson(s, (Class)IChatComponent.class);
        }
        
        public static String componentToJson(final IChatComponent chatComponent) {
            return Serializer.GSON.toJson((Object)chatComponent);
        }
        
        public IChatComponent deserialize(final JsonElement p0, final Type p1, final JsonDeserializationContext p2) throws JsonParseException {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: invokevirtual   com/google/gson/JsonElement.isJsonPrimitive:()Z
            //     4: ifeq            19
            //     7: new             Lnet/minecraft/util/ChatComponentText;
            //    10: dup            
            //    11: aload_1        
            //    12: invokevirtual   com/google/gson/JsonElement.getAsString:()Ljava/lang/String;
            //    15: invokespecial   net/minecraft/util/ChatComponentText.<init>:(Ljava/lang/String;)V
            //    18: areturn        
            //    19: aload_1        
            //    20: invokevirtual   com/google/gson/JsonElement.isJsonObject:()Z
            //    23: ifne            148
            //    26: aload_1        
            //    27: invokevirtual   com/google/gson/JsonElement.isJsonArray:()Z
            //    30: ifeq            113
            //    33: aload_1        
            //    34: invokevirtual   com/google/gson/JsonElement.getAsJsonArray:()Lcom/google/gson/JsonArray;
            //    37: astore          4
            //    39: aconst_null    
            //    40: astore          5
            //    42: aload           4
            //    44: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
            //    47: astore          6
            //    49: aload           6
            //    51: invokeinterface java/util/Iterator.hasNext:()Z
            //    56: ifeq            110
            //    59: aload           6
            //    61: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
            //    66: checkcast       Lcom/google/gson/JsonElement;
            //    69: astore          7
            //    71: aload_0        
            //    72: aload           7
            //    74: aload           7
            //    76: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
            //    79: aload_3        
            //    80: invokevirtual   net/minecraft/util/IChatComponent$Serializer.deserialize:(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/util/IChatComponent;
            //    83: astore          8
            //    85: aload           5
            //    87: ifnonnull       97
            //    90: aload           8
            //    92: astore          5
            //    94: goto            49
            //    97: aload           5
            //    99: aload           8
            //   101: invokeinterface net/minecraft/util/IChatComponent.appendSibling:(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;
            //   106: pop            
            //   107: goto            49
            //   110: aload           5
            //   112: areturn        
            //   113: new             Lcom/google/gson/JsonParseException;
            //   116: dup            
            //   117: new             Ljava/lang/StringBuilder;
            //   120: dup            
            //   121: invokespecial   java/lang/StringBuilder.<init>:()V
            //   124: ldc             "Don't know how to turn "
            //   126: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   129: aload_1        
            //   130: invokevirtual   com/google/gson/JsonElement.toString:()Ljava/lang/String;
            //   133: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   136: ldc             " into a Component"
            //   138: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   141: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   144: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
            //   147: athrow         
            //   148: aload_1        
            //   149: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
            //   152: astore          4
            //   154: aload           4
            //   156: ldc             "text"
            //   158: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   161: ifeq            186
            //   164: new             Lnet/minecraft/util/ChatComponentText;
            //   167: dup            
            //   168: aload           4
            //   170: ldc             "text"
            //   172: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
            //   175: invokevirtual   com/google/gson/JsonElement.getAsString:()Ljava/lang/String;
            //   178: invokespecial   net/minecraft/util/ChatComponentText.<init>:(Ljava/lang/String;)V
            //   181: astore          5
            //   183: goto            513
            //   186: aload           4
            //   188: ldc             "translate"
            //   190: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   193: ifeq            352
            //   196: aload           4
            //   198: ldc             "translate"
            //   200: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
            //   203: invokevirtual   com/google/gson/JsonElement.getAsString:()Ljava/lang/String;
            //   206: astore          6
            //   208: aload           4
            //   210: ldc             "with"
            //   212: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   215: ifeq            334
            //   218: aload           4
            //   220: ldc             "with"
            //   222: invokevirtual   com/google/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/google/gson/JsonArray;
            //   225: astore          7
            //   227: aload           7
            //   229: invokevirtual   com/google/gson/JsonArray.size:()I
            //   232: anewarray       Ljava/lang/Object;
            //   235: astore          8
            //   237: iconst_0       
            //   238: aload           8
            //   240: arraylength    
            //   241: if_icmpge       318
            //   244: aload           8
            //   246: iconst_0       
            //   247: aload_0        
            //   248: aload           7
            //   250: iconst_0       
            //   251: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
            //   254: aload_2        
            //   255: aload_3        
            //   256: invokevirtual   net/minecraft/util/IChatComponent$Serializer.deserialize:(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/util/IChatComponent;
            //   259: aastore        
            //   260: aload           8
            //   262: iconst_0       
            //   263: aaload         
            //   264: instanceof      Lnet/minecraft/util/ChatComponentText;
            //   267: ifeq            312
            //   270: aload           8
            //   272: iconst_0       
            //   273: aaload         
            //   274: checkcast       Lnet/minecraft/util/ChatComponentText;
            //   277: astore          10
            //   279: aload           10
            //   281: invokevirtual   net/minecraft/util/ChatComponentText.getChatStyle:()Lnet/minecraft/util/ChatStyle;
            //   284: invokevirtual   net/minecraft/util/ChatStyle.isEmpty:()Z
            //   287: ifeq            312
            //   290: aload           10
            //   292: invokevirtual   net/minecraft/util/ChatComponentText.getSiblings:()Ljava/util/List;
            //   295: invokeinterface java/util/List.isEmpty:()Z
            //   300: ifeq            312
            //   303: aload           8
            //   305: iconst_0       
            //   306: aload           10
            //   308: invokevirtual   net/minecraft/util/ChatComponentText.getChatComponentText_TextValue:()Ljava/lang/String;
            //   311: aastore        
            //   312: iinc            9, 1
            //   315: goto            237
            //   318: new             Lnet/minecraft/util/ChatComponentTranslation;
            //   321: dup            
            //   322: aload           6
            //   324: aload           8
            //   326: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
            //   329: astore          5
            //   331: goto            513
            //   334: new             Lnet/minecraft/util/ChatComponentTranslation;
            //   337: dup            
            //   338: aload           6
            //   340: iconst_0       
            //   341: anewarray       Ljava/lang/Object;
            //   344: invokespecial   net/minecraft/util/ChatComponentTranslation.<init>:(Ljava/lang/String;[Ljava/lang/Object;)V
            //   347: astore          5
            //   349: goto            513
            //   352: aload           4
            //   354: ldc             "score"
            //   356: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   359: ifeq            452
            //   362: aload           4
            //   364: ldc             "score"
            //   366: invokevirtual   com/google/gson/JsonObject.getAsJsonObject:(Ljava/lang/String;)Lcom/google/gson/JsonObject;
            //   369: astore          6
            //   371: aload           6
            //   373: ldc             "name"
            //   375: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   378: ifeq            391
            //   381: aload           6
            //   383: ldc             "objective"
            //   385: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   388: ifne            401
            //   391: new             Lcom/google/gson/JsonParseException;
            //   394: dup            
            //   395: ldc             "A score component needs a least a name and an objective"
            //   397: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
            //   400: athrow         
            //   401: new             Lnet/minecraft/util/ChatComponentScore;
            //   404: dup            
            //   405: aload           6
            //   407: ldc             "name"
            //   409: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
            //   412: aload           6
            //   414: ldc             "objective"
            //   416: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
            //   419: invokespecial   net/minecraft/util/ChatComponentScore.<init>:(Ljava/lang/String;Ljava/lang/String;)V
            //   422: astore          5
            //   424: aload           6
            //   426: ldc             "value"
            //   428: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   431: ifeq            449
            //   434: aload           5
            //   436: checkcast       Lnet/minecraft/util/ChatComponentScore;
            //   439: aload           6
            //   441: ldc             "value"
            //   443: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
            //   446: invokevirtual   net/minecraft/util/ChatComponentScore.setValue:(Ljava/lang/String;)V
            //   449: goto            513
            //   452: aload           4
            //   454: ldc             "selector"
            //   456: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   459: ifne            497
            //   462: new             Lcom/google/gson/JsonParseException;
            //   465: dup            
            //   466: new             Ljava/lang/StringBuilder;
            //   469: dup            
            //   470: invokespecial   java/lang/StringBuilder.<init>:()V
            //   473: ldc             "Don't know how to turn "
            //   475: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   478: aload_1        
            //   479: invokevirtual   com/google/gson/JsonElement.toString:()Ljava/lang/String;
            //   482: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   485: ldc             " into a Component"
            //   487: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   490: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
            //   493: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
            //   496: athrow         
            //   497: new             Lnet/minecraft/util/ChatComponentSelector;
            //   500: dup            
            //   501: aload           4
            //   503: ldc             "selector"
            //   505: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
            //   508: invokespecial   net/minecraft/util/ChatComponentSelector.<init>:(Ljava/lang/String;)V
            //   511: astore          5
            //   513: aload           4
            //   515: ldc             "extra"
            //   517: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
            //   520: ifeq            585
            //   523: aload           4
            //   525: ldc             "extra"
            //   527: invokevirtual   com/google/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/google/gson/JsonArray;
            //   530: astore          6
            //   532: aload           6
            //   534: invokevirtual   com/google/gson/JsonArray.size:()I
            //   537: ifgt            550
            //   540: new             Lcom/google/gson/JsonParseException;
            //   543: dup            
            //   544: ldc             "Unexpected empty array of components"
            //   546: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
            //   549: athrow         
            //   550: iconst_0       
            //   551: aload           6
            //   553: invokevirtual   com/google/gson/JsonArray.size:()I
            //   556: if_icmpge       585
            //   559: aload           5
            //   561: aload_0        
            //   562: aload           6
            //   564: iconst_0       
            //   565: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
            //   568: aload_2        
            //   569: aload_3        
            //   570: invokevirtual   net/minecraft/util/IChatComponent$Serializer.deserialize:(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/util/IChatComponent;
            //   573: invokeinterface net/minecraft/util/IChatComponent.appendSibling:(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;
            //   578: pop            
            //   579: iinc            7, 1
            //   582: goto            550
            //   585: aload           5
            //   587: aload_3        
            //   588: aload_1        
            //   589: ldc             Lnet/minecraft/util/ChatStyle;.class
            //   591: invokeinterface com/google/gson/JsonDeserializationContext.deserialize:(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;)Ljava/lang/Object;
            //   596: checkcast       Lnet/minecraft/util/ChatStyle;
            //   599: invokeinterface net/minecraft/util/IChatComponent.setChatStyle:(Lnet/minecraft/util/ChatStyle;)Lnet/minecraft/util/IChatComponent;
            //   604: pop            
            //   605: aload           5
            //   607: areturn        
            //    Exceptions:
            //  throws com.google.gson.JsonParseException
            // 
            // The error that occurred was:
            // 
            // java.lang.NullPointerException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public JsonElement serialize(final IChatComponent chatComponent, final Type type, final JsonSerializationContext jsonSerializationContext) {
            if (chatComponent instanceof ChatComponentText && chatComponent.getChatStyle().isEmpty() && chatComponent.getSiblings().isEmpty()) {
                return (JsonElement)new JsonPrimitive(((ChatComponentText)chatComponent).getChatComponentText_TextValue());
            }
            final JsonObject jsonObject = new JsonObject();
            if (!chatComponent.getChatStyle().isEmpty()) {
                this.serializeChatStyle(chatComponent.getChatStyle(), jsonObject, jsonSerializationContext);
            }
            if (!chatComponent.getSiblings().isEmpty()) {
                final JsonArray jsonArray = new JsonArray();
                for (final IChatComponent chatComponent2 : chatComponent.getSiblings()) {
                    jsonArray.add(this.serialize(chatComponent2, chatComponent2.getClass(), jsonSerializationContext));
                }
                jsonObject.add("extra", (JsonElement)jsonArray);
            }
            if (chatComponent instanceof ChatComponentText) {
                jsonObject.addProperty("text", ((ChatComponentText)chatComponent).getChatComponentText_TextValue());
            }
            else if (chatComponent instanceof ChatComponentTranslation) {
                final ChatComponentTranslation chatComponentTranslation = (ChatComponentTranslation)chatComponent;
                jsonObject.addProperty("translate", chatComponentTranslation.getKey());
                if (chatComponentTranslation.getFormatArgs() != null && chatComponentTranslation.getFormatArgs().length > 0) {
                    final JsonArray jsonArray2 = new JsonArray();
                    final Object[] formatArgs = chatComponentTranslation.getFormatArgs();
                    while (0 < formatArgs.length) {
                        final Object o = formatArgs[0];
                        if (o instanceof IChatComponent) {
                            jsonArray2.add(this.serialize((IChatComponent)o, ((IChatComponent)o).getClass(), jsonSerializationContext));
                        }
                        else {
                            jsonArray2.add((JsonElement)new JsonPrimitive(String.valueOf(o)));
                        }
                        int n = 0;
                        ++n;
                    }
                    jsonObject.add("with", (JsonElement)jsonArray2);
                }
            }
            else if (chatComponent instanceof ChatComponentScore) {
                final ChatComponentScore chatComponentScore = (ChatComponentScore)chatComponent;
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("name", chatComponentScore.getName());
                jsonObject2.addProperty("objective", chatComponentScore.getObjective());
                jsonObject2.addProperty("value", chatComponentScore.getUnformattedTextForChat());
                jsonObject.add("score", (JsonElement)jsonObject2);
            }
            else {
                if (!(chatComponent instanceof ChatComponentSelector)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + chatComponent + " as a Component");
                }
                jsonObject.addProperty("selector", ((ChatComponentSelector)chatComponent).getSelector());
            }
            return (JsonElement)jsonObject;
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((IChatComponent)o, type, jsonSerializationContext);
        }
        
        private void serializeChatStyle(final ChatStyle chatStyle, final JsonObject jsonObject, final JsonSerializationContext jsonSerializationContext) {
            final JsonElement serialize = jsonSerializationContext.serialize((Object)chatStyle);
            if (serialize.isJsonObject()) {
                for (final Map.Entry<String, V> entry : ((JsonObject)serialize).entrySet()) {
                    jsonObject.add((String)entry.getKey(), (JsonElement)entry.getValue());
                }
            }
        }
        
        static {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new Serializer());
            gsonBuilder.registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer());
            gsonBuilder.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
            GSON = gsonBuilder.create();
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}
