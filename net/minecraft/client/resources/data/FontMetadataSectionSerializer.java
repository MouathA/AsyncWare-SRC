package net.minecraft.client.resources.data;

import java.lang.reflect.*;
import com.google.gson.*;

public class FontMetadataSectionSerializer extends BaseMetadataSectionSerializer
{
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.deserialize(jsonElement, type, jsonDeserializationContext);
    }
    
    @Override
    public String getSectionName() {
        return "font";
    }
    
    public FontMetadataSection deserialize(final JsonElement p0, final Type p1, final JsonDeserializationContext p2) throws JsonParseException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //     4: astore          4
        //     6: sipush          256
        //     9: newarray        F
        //    11: astore          5
        //    13: sipush          256
        //    16: newarray        F
        //    18: astore          6
        //    20: sipush          256
        //    23: newarray        F
        //    25: astore          7
        //    27: fconst_1       
        //    28: fstore          8
        //    30: fconst_0       
        //    31: fstore          9
        //    33: fconst_0       
        //    34: fstore          10
        //    36: aload           4
        //    38: ldc             "characters"
        //    40: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
        //    43: ifeq            387
        //    46: aload           4
        //    48: ldc             "characters"
        //    50: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //    53: invokevirtual   com/google/gson/JsonElement.isJsonObject:()Z
        //    56: ifne            92
        //    59: new             Lcom/google/gson/JsonParseException;
        //    62: dup            
        //    63: new             Ljava/lang/StringBuilder;
        //    66: dup            
        //    67: invokespecial   java/lang/StringBuilder.<init>:()V
        //    70: ldc             "Invalid font->characters: expected object, was "
        //    72: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    75: aload           4
        //    77: ldc             "characters"
        //    79: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //    82: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    85: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    88: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //    91: athrow         
        //    92: aload           4
        //    94: ldc             "characters"
        //    96: invokevirtual   com/google/gson/JsonObject.getAsJsonObject:(Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //    99: astore          11
        //   101: aload           11
        //   103: ldc             "default"
        //   105: invokevirtual   com/google/gson/JsonObject.has:(Ljava/lang/String;)Z
        //   108: ifeq            235
        //   111: aload           11
        //   113: ldc             "default"
        //   115: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   118: invokevirtual   com/google/gson/JsonElement.isJsonObject:()Z
        //   121: ifne            157
        //   124: new             Lcom/google/gson/JsonParseException;
        //   127: dup            
        //   128: new             Ljava/lang/StringBuilder;
        //   131: dup            
        //   132: invokespecial   java/lang/StringBuilder.<init>:()V
        //   135: ldc             "Invalid font->characters->default: expected object, was "
        //   137: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   140: aload           11
        //   142: ldc             "default"
        //   144: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   147: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   150: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   153: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   156: athrow         
        //   157: aload           11
        //   159: ldc             "default"
        //   161: invokevirtual   com/google/gson/JsonObject.getAsJsonObject:(Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //   164: astore          12
        //   166: aload           12
        //   168: ldc             "width"
        //   170: fload           8
        //   172: invokestatic    net/minecraft/util/JsonUtils.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   175: fstore          8
        //   177: dconst_0       
        //   178: ldc2_w          3.4028234663852886E38
        //   181: fload           8
        //   183: f2d            
        //   184: ldc             "Invalid default width"
        //   186: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   189: aload           12
        //   191: ldc             "spacing"
        //   193: fload           9
        //   195: invokestatic    net/minecraft/util/JsonUtils.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   198: fstore          9
        //   200: dconst_0       
        //   201: ldc2_w          3.4028234663852886E38
        //   204: fload           9
        //   206: f2d            
        //   207: ldc             "Invalid default spacing"
        //   209: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   212: aload           12
        //   214: ldc             "left"
        //   216: fload           9
        //   218: invokestatic    net/minecraft/util/JsonUtils.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   221: fstore          10
        //   223: dconst_0       
        //   224: ldc2_w          3.4028234663852886E38
        //   227: fload           10
        //   229: f2d            
        //   230: ldc             "Invalid default left"
        //   232: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   235: aload           11
        //   237: iconst_0       
        //   238: invokestatic    java/lang/Integer.toString:(I)Ljava/lang/String;
        //   241: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   244: astore          13
        //   246: fload           8
        //   248: fstore          14
        //   250: fload           9
        //   252: fstore          15
        //   254: fload           10
        //   256: fstore          16
        //   258: aload           13
        //   260: ifnull          363
        //   263: aload           13
        //   265: new             Ljava/lang/StringBuilder;
        //   268: dup            
        //   269: invokespecial   java/lang/StringBuilder.<init>:()V
        //   272: ldc             "characters["
        //   274: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   277: iconst_0       
        //   278: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   281: ldc             "]"
        //   283: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   286: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   289: invokestatic    net/minecraft/util/JsonUtils.getJsonObject:(Lcom/google/gson/JsonElement;Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //   292: astore          17
        //   294: aload           17
        //   296: ldc             "width"
        //   298: fload           8
        //   300: invokestatic    net/minecraft/util/JsonUtils.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   303: fstore          14
        //   305: dconst_0       
        //   306: ldc2_w          3.4028234663852886E38
        //   309: fload           14
        //   311: f2d            
        //   312: ldc             "Invalid width"
        //   314: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   317: aload           17
        //   319: ldc             "spacing"
        //   321: fload           9
        //   323: invokestatic    net/minecraft/util/JsonUtils.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   326: fstore          15
        //   328: dconst_0       
        //   329: ldc2_w          3.4028234663852886E38
        //   332: fload           15
        //   334: f2d            
        //   335: ldc             "Invalid spacing"
        //   337: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   340: aload           17
        //   342: ldc             "left"
        //   344: fload           10
        //   346: invokestatic    net/minecraft/util/JsonUtils.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   349: fstore          16
        //   351: dconst_0       
        //   352: ldc2_w          3.4028234663852886E38
        //   355: fload           16
        //   357: f2d            
        //   358: ldc             "Invalid left"
        //   360: invokestatic    org/apache/commons/lang3/Validate.inclusiveBetween:(DDDLjava/lang/String;)V
        //   363: aload           5
        //   365: iconst_0       
        //   366: fload           14
        //   368: fastore        
        //   369: aload           6
        //   371: iconst_0       
        //   372: fload           15
        //   374: fastore        
        //   375: aload           7
        //   377: iconst_0       
        //   378: fload           16
        //   380: fastore        
        //   381: iinc            12, 1
        //   384: goto            235
        //   387: new             Lnet/minecraft/client/resources/data/FontMetadataSection;
        //   390: dup            
        //   391: aload           5
        //   393: aload           7
        //   395: aload           6
        //   397: invokespecial   net/minecraft/client/resources/data/FontMetadataSection.<init>:([F[F[F)V
        //   400: areturn        
        //    Exceptions:
        //  throws com.google.gson.JsonParseException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
