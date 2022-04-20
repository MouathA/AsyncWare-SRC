package optfine;

import net.minecraft.util.*;
import java.awt.*;
import com.google.gson.*;
import java.util.*;
import net.minecraft.client.model.*;

public class PlayerItemParser
{
    public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
    public static final String ITEM_TYPE = "type";
    public static final String MODEL_SUBMODEL = "submodel";
    public static final String MODEL_ID = "id";
    public static final String MODEL_TRANSLATE = "translate";
    public static final String BOX_TEXTURE_OFFSET = "textureOffset";
    public static final String ITEM_TEXTURE_SIZE = "textureSize";
    public static final String MODEL_INVERT_AXIS = "invertAxis";
    public static final String MODEL_BASE_ID = "baseId";
    public static final String MODEL_SUBMODELS = "submodels";
    public static final String ITEM_TYPE_MODEL = "PlayerItem";
    public static final String MODEL_TYPE_BOX = "ModelBox";
    public static final String MODEL_SPRITES = "sprites";
    public static final String BOX_COORDINATES = "coordinates";
    public static final String MODEL_SCALE = "scale";
    public static final String MODEL_TYPE = "type";
    public static final String MODEL_BOXES = "boxes";
    public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
    private static JsonParser jsonParser;
    public static final String BOX_SIZE_ADD = "sizeAdd";
    public static final String MODEL_ATTACH_TO = "attachTo";
    public static final String MODEL_ROTATE = "rotate";
    public static final String ITEM_MODELS = "models";
    
    private static void checkNull(final Object o, final String s) {
        if (o == null) {
            throw new JsonParseException(s);
        }
    }
    
    private static ResourceLocation makeResourceLocation(final String s) {
        final int index = s.indexOf(58);
        if (index < 0) {
            return new ResourceLocation(s);
        }
        return new ResourceLocation(s.substring(0, index), s.substring(index + 1));
    }
    
    private static PlayerItemRenderer parseItemRenderer(final JsonObject jsonObject, final Dimension dimension) {
        final String string = Json.getString(jsonObject, "type");
        if (!Config.equals(string, "ModelBox")) {
            Config.warn("Unknown model type: " + string);
            return null;
        }
        final int attachModel = parseAttachModel(Json.getString(jsonObject, "attachTo"));
        final float float1 = Json.getFloat(jsonObject, "scale", 1.0f);
        final ModelPlayerItem modelPlayerItem = new ModelPlayerItem();
        modelPlayerItem.textureWidth = dimension.width;
        modelPlayerItem.textureHeight = dimension.height;
        return new PlayerItemRenderer(attachModel, float1, parseModelRenderer(jsonObject, modelPlayerItem));
    }
    
    public static PlayerItemModel parseItemModel(final JsonObject jsonObject) {
        final String string = Json.getString(jsonObject, "type");
        if (!Config.equals(string, "PlayerItem")) {
            throw new JsonParseException("Unknown model type: " + string);
        }
        final int[] intArray = Json.parseIntArray(jsonObject.get("textureSize"), 2);
        checkNull(intArray, "Missing texture size");
        final Dimension dimension = new Dimension(intArray[0], intArray[1]);
        final boolean boolean1 = Json.getBoolean(jsonObject, "usePlayerTexture", false);
        final JsonArray jsonArray = (JsonArray)jsonObject.get("models");
        checkNull(jsonArray, "Missing elements");
        final HashMap<String, JsonObject> hashMap = (HashMap<String, JsonObject>)new HashMap<Object, JsonObject>();
        final ArrayList<PlayerItemRenderer> list = new ArrayList<PlayerItemRenderer>();
        new ArrayList();
        while (0 < jsonArray.size()) {
            final JsonObject jsonObject2 = (JsonObject)jsonArray.get(0);
            final String string2 = Json.getString(jsonObject2, "baseId");
            Label_0374: {
                if (string2 != null) {
                    final JsonObject jsonObject3 = hashMap.get(string2);
                    if (jsonObject3 == null) {
                        Config.warn("BaseID not found: " + string2);
                        break Label_0374;
                    }
                    for (final Map.Entry<String, V> entry : jsonObject3.entrySet()) {
                        if (!jsonObject2.has((String)entry.getKey())) {
                            jsonObject2.add((String)entry.getKey(), (JsonElement)entry.getValue());
                        }
                    }
                }
                final String string3 = Json.getString(jsonObject2, "id");
                if (string3 != null) {
                    if (!hashMap.containsKey(string3)) {
                        hashMap.put(string3, jsonObject2);
                    }
                    else {
                        Config.warn("Duplicate model ID: " + string3);
                    }
                }
                final PlayerItemRenderer itemRenderer = parseItemRenderer(jsonObject2, dimension);
                if (itemRenderer != null) {
                    list.add(itemRenderer);
                }
            }
            int n = 0;
            ++n;
        }
        return new PlayerItemModel(dimension, boolean1, list.toArray(new PlayerItemRenderer[list.size()]));
    }
    
    private static ModelRenderer parseModelRenderer(final JsonObject p0, final ModelBase p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: aload_1        
        //     5: invokespecial   net/minecraft/client/model/ModelRenderer.<init>:(Lnet/minecraft/client/model/ModelBase;)V
        //     8: astore_2       
        //     9: aload_0        
        //    10: ldc             "invertAxis"
        //    12: ldc_w           ""
        //    15: invokestatic    optfine/Json.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //    18: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //    21: astore_3       
        //    22: aload_3        
        //    23: ldc_w           "x"
        //    26: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    29: istore          4
        //    31: aload_3        
        //    32: ldc_w           "y"
        //    35: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    38: istore          5
        //    40: aload_3        
        //    41: ldc_w           "z"
        //    44: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //    47: istore          6
        //    49: aload_0        
        //    50: ldc             "translate"
        //    52: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //    55: iconst_3       
        //    56: iconst_3       
        //    57: newarray        F
        //    59: invokestatic    optfine/Json.parseFloatArray:(Lcom/google/gson/JsonElement;I[F)[F
        //    62: astore          7
        //    64: iload           4
        //    66: ifeq            78
        //    69: aload           7
        //    71: iconst_0       
        //    72: aload           7
        //    74: iconst_0       
        //    75: faload         
        //    76: fneg           
        //    77: fastore        
        //    78: iload           5
        //    80: ifeq            92
        //    83: aload           7
        //    85: iconst_1       
        //    86: aload           7
        //    88: iconst_1       
        //    89: faload         
        //    90: fneg           
        //    91: fastore        
        //    92: iload           6
        //    94: ifeq            106
        //    97: aload           7
        //    99: iconst_2       
        //   100: aload           7
        //   102: iconst_2       
        //   103: faload         
        //   104: fneg           
        //   105: fastore        
        //   106: aload_0        
        //   107: ldc             "rotate"
        //   109: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   112: iconst_3       
        //   113: iconst_3       
        //   114: newarray        F
        //   116: invokestatic    optfine/Json.parseFloatArray:(Lcom/google/gson/JsonElement;I[F)[F
        //   119: astore          8
        //   121: iconst_0       
        //   122: aload           8
        //   124: arraylength    
        //   125: if_icmpge       150
        //   128: aload           8
        //   130: iconst_0       
        //   131: aload           8
        //   133: iconst_0       
        //   134: faload         
        //   135: ldc_w           180.0
        //   138: fdiv           
        //   139: ldc_w           3.1415927
        //   142: fmul           
        //   143: fastore        
        //   144: iinc            9, 1
        //   147: goto            121
        //   150: iload           4
        //   152: ifeq            164
        //   155: aload           8
        //   157: iconst_0       
        //   158: aload           8
        //   160: iconst_0       
        //   161: faload         
        //   162: fneg           
        //   163: fastore        
        //   164: iload           5
        //   166: ifeq            178
        //   169: aload           8
        //   171: iconst_1       
        //   172: aload           8
        //   174: iconst_1       
        //   175: faload         
        //   176: fneg           
        //   177: fastore        
        //   178: iload           6
        //   180: ifeq            192
        //   183: aload           8
        //   185: iconst_2       
        //   186: aload           8
        //   188: iconst_2       
        //   189: faload         
        //   190: fneg           
        //   191: fastore        
        //   192: aload_2        
        //   193: aload           7
        //   195: iconst_0       
        //   196: faload         
        //   197: aload           7
        //   199: iconst_1       
        //   200: faload         
        //   201: aload           7
        //   203: iconst_2       
        //   204: faload         
        //   205: invokevirtual   net/minecraft/client/model/ModelRenderer.setRotationPoint:(FFF)V
        //   208: aload_2        
        //   209: aload           8
        //   211: iconst_0       
        //   212: faload         
        //   213: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleX:F
        //   216: aload_2        
        //   217: aload           8
        //   219: iconst_1       
        //   220: faload         
        //   221: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleY:F
        //   224: aload_2        
        //   225: aload           8
        //   227: iconst_2       
        //   228: faload         
        //   229: putfield        net/minecraft/client/model/ModelRenderer.rotateAngleZ:F
        //   232: aload_0        
        //   233: ldc             "mirrorTexture"
        //   235: ldc_w           ""
        //   238: invokestatic    optfine/Json.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
        //   241: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   244: astore          9
        //   246: aload           9
        //   248: ldc_w           "u"
        //   251: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   254: istore          10
        //   256: aload           9
        //   258: ldc_w           "v"
        //   261: invokevirtual   java/lang/String.contains:(Ljava/lang/CharSequence;)Z
        //   264: istore          11
        //   266: iload           10
        //   268: ifeq            276
        //   271: aload_2        
        //   272: iconst_1       
        //   273: putfield        net/minecraft/client/model/ModelRenderer.mirror:Z
        //   276: iload           11
        //   278: ifeq            286
        //   281: aload_2        
        //   282: iconst_1       
        //   283: putfield        net/minecraft/client/model/ModelRenderer.mirrorV:Z
        //   286: aload_0        
        //   287: ldc             "boxes"
        //   289: invokevirtual   com/google/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/google/gson/JsonArray;
        //   292: astore          12
        //   294: aload           12
        //   296: ifnull          497
        //   299: iconst_0       
        //   300: aload           12
        //   302: invokevirtual   com/google/gson/JsonArray.size:()I
        //   305: if_icmpge       497
        //   308: aload           12
        //   310: iconst_0       
        //   311: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
        //   314: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //   317: astore          14
        //   319: aload           14
        //   321: ldc             "textureOffset"
        //   323: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   326: iconst_2       
        //   327: invokestatic    optfine/Json.parseIntArray:(Lcom/google/gson/JsonElement;I)[I
        //   330: astore          15
        //   332: aload           15
        //   334: ifnonnull       348
        //   337: new             Lcom/google/gson/JsonParseException;
        //   340: dup            
        //   341: ldc_w           "Texture offset not specified"
        //   344: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   347: athrow         
        //   348: aload           14
        //   350: ldc             "coordinates"
        //   352: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   355: bipush          6
        //   357: invokestatic    optfine/Json.parseFloatArray:(Lcom/google/gson/JsonElement;I)[F
        //   360: astore          16
        //   362: aload           16
        //   364: ifnonnull       378
        //   367: new             Lcom/google/gson/JsonParseException;
        //   370: dup            
        //   371: ldc_w           "Coordinates not specified"
        //   374: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   377: athrow         
        //   378: iload           4
        //   380: ifeq            397
        //   383: aload           16
        //   385: iconst_0       
        //   386: aload           16
        //   388: iconst_0       
        //   389: faload         
        //   390: fneg           
        //   391: aload           16
        //   393: iconst_3       
        //   394: faload         
        //   395: fsub           
        //   396: fastore        
        //   397: iload           5
        //   399: ifeq            416
        //   402: aload           16
        //   404: iconst_1       
        //   405: aload           16
        //   407: iconst_1       
        //   408: faload         
        //   409: fneg           
        //   410: aload           16
        //   412: iconst_4       
        //   413: faload         
        //   414: fsub           
        //   415: fastore        
        //   416: iload           6
        //   418: ifeq            435
        //   421: aload           16
        //   423: iconst_2       
        //   424: aload           16
        //   426: iconst_2       
        //   427: faload         
        //   428: fneg           
        //   429: aload           16
        //   431: iconst_5       
        //   432: faload         
        //   433: fsub           
        //   434: fastore        
        //   435: aload           14
        //   437: ldc             "sizeAdd"
        //   439: fconst_0       
        //   440: invokestatic    optfine/Json.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   443: fstore          17
        //   445: aload_2        
        //   446: aload           15
        //   448: iconst_0       
        //   449: iaload         
        //   450: aload           15
        //   452: iconst_1       
        //   453: iaload         
        //   454: invokevirtual   net/minecraft/client/model/ModelRenderer.setTextureOffset:(II)Lnet/minecraft/client/model/ModelRenderer;
        //   457: pop            
        //   458: aload_2        
        //   459: aload           16
        //   461: iconst_0       
        //   462: faload         
        //   463: aload           16
        //   465: iconst_1       
        //   466: faload         
        //   467: aload           16
        //   469: iconst_2       
        //   470: faload         
        //   471: aload           16
        //   473: iconst_3       
        //   474: faload         
        //   475: f2i            
        //   476: aload           16
        //   478: iconst_4       
        //   479: faload         
        //   480: f2i            
        //   481: aload           16
        //   483: iconst_5       
        //   484: faload         
        //   485: f2i            
        //   486: fload           17
        //   488: invokevirtual   net/minecraft/client/model/ModelRenderer.addBox:(FFFIIIF)V
        //   491: iinc            13, 1
        //   494: goto            299
        //   497: aload_0        
        //   498: ldc             "sprites"
        //   500: invokevirtual   com/google/gson/JsonObject.getAsJsonArray:(Ljava/lang/String;)Lcom/google/gson/JsonArray;
        //   503: astore          13
        //   505: aload           13
        //   507: ifnull          708
        //   510: iconst_0       
        //   511: aload           13
        //   513: invokevirtual   com/google/gson/JsonArray.size:()I
        //   516: if_icmpge       708
        //   519: aload           13
        //   521: iconst_0       
        //   522: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
        //   525: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //   528: astore          15
        //   530: aload           15
        //   532: ldc             "textureOffset"
        //   534: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   537: iconst_2       
        //   538: invokestatic    optfine/Json.parseIntArray:(Lcom/google/gson/JsonElement;I)[I
        //   541: astore          16
        //   543: aload           16
        //   545: ifnonnull       559
        //   548: new             Lcom/google/gson/JsonParseException;
        //   551: dup            
        //   552: ldc_w           "Texture offset not specified"
        //   555: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   558: athrow         
        //   559: aload           15
        //   561: ldc             "coordinates"
        //   563: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   566: bipush          6
        //   568: invokestatic    optfine/Json.parseFloatArray:(Lcom/google/gson/JsonElement;I)[F
        //   571: astore          17
        //   573: aload           17
        //   575: ifnonnull       589
        //   578: new             Lcom/google/gson/JsonParseException;
        //   581: dup            
        //   582: ldc_w           "Coordinates not specified"
        //   585: invokespecial   com/google/gson/JsonParseException.<init>:(Ljava/lang/String;)V
        //   588: athrow         
        //   589: iload           4
        //   591: ifeq            608
        //   594: aload           17
        //   596: iconst_0       
        //   597: aload           17
        //   599: iconst_0       
        //   600: faload         
        //   601: fneg           
        //   602: aload           17
        //   604: iconst_3       
        //   605: faload         
        //   606: fsub           
        //   607: fastore        
        //   608: iload           5
        //   610: ifeq            627
        //   613: aload           17
        //   615: iconst_1       
        //   616: aload           17
        //   618: iconst_1       
        //   619: faload         
        //   620: fneg           
        //   621: aload           17
        //   623: iconst_4       
        //   624: faload         
        //   625: fsub           
        //   626: fastore        
        //   627: iload           6
        //   629: ifeq            646
        //   632: aload           17
        //   634: iconst_2       
        //   635: aload           17
        //   637: iconst_2       
        //   638: faload         
        //   639: fneg           
        //   640: aload           17
        //   642: iconst_5       
        //   643: faload         
        //   644: fsub           
        //   645: fastore        
        //   646: aload           15
        //   648: ldc             "sizeAdd"
        //   650: fconst_0       
        //   651: invokestatic    optfine/Json.getFloat:(Lcom/google/gson/JsonObject;Ljava/lang/String;F)F
        //   654: fstore          18
        //   656: aload_2        
        //   657: aload           16
        //   659: iconst_0       
        //   660: iaload         
        //   661: aload           16
        //   663: iconst_1       
        //   664: iaload         
        //   665: invokevirtual   net/minecraft/client/model/ModelRenderer.setTextureOffset:(II)Lnet/minecraft/client/model/ModelRenderer;
        //   668: pop            
        //   669: aload_2        
        //   670: aload           17
        //   672: iconst_0       
        //   673: faload         
        //   674: aload           17
        //   676: iconst_1       
        //   677: faload         
        //   678: aload           17
        //   680: iconst_2       
        //   681: faload         
        //   682: aload           17
        //   684: iconst_3       
        //   685: faload         
        //   686: f2i            
        //   687: aload           17
        //   689: iconst_4       
        //   690: faload         
        //   691: f2i            
        //   692: aload           17
        //   694: iconst_5       
        //   695: faload         
        //   696: f2i            
        //   697: fload           18
        //   699: invokevirtual   net/minecraft/client/model/ModelRenderer.addSprite:(FFFIIIF)V
        //   702: iinc            14, 1
        //   705: goto            510
        //   708: aload_0        
        //   709: ldc             "submodel"
        //   711: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   714: checkcast       Lcom/google/gson/JsonObject;
        //   717: astore          14
        //   719: aload           14
        //   721: ifnull          738
        //   724: aload           14
        //   726: aload_1        
        //   727: invokestatic    optfine/PlayerItemParser.parseModelRenderer:(Lcom/google/gson/JsonObject;Lnet/minecraft/client/model/ModelBase;)Lnet/minecraft/client/model/ModelRenderer;
        //   730: astore          15
        //   732: aload_2        
        //   733: aload           15
        //   735: invokevirtual   net/minecraft/client/model/ModelRenderer.addChild:(Lnet/minecraft/client/model/ModelRenderer;)V
        //   738: aload_0        
        //   739: ldc             "submodels"
        //   741: invokevirtual   com/google/gson/JsonObject.get:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   744: checkcast       Lcom/google/gson/JsonArray;
        //   747: astore          15
        //   749: aload           15
        //   751: ifnull          794
        //   754: iconst_0       
        //   755: aload           15
        //   757: invokevirtual   com/google/gson/JsonArray.size:()I
        //   760: if_icmpge       794
        //   763: aload           15
        //   765: iconst_0       
        //   766: invokevirtual   com/google/gson/JsonArray.get:(I)Lcom/google/gson/JsonElement;
        //   769: checkcast       Lcom/google/gson/JsonObject;
        //   772: astore          17
        //   774: aload           17
        //   776: aload_1        
        //   777: invokestatic    optfine/PlayerItemParser.parseModelRenderer:(Lcom/google/gson/JsonObject;Lnet/minecraft/client/model/ModelBase;)Lnet/minecraft/client/model/ModelRenderer;
        //   780: astore          18
        //   782: aload_2        
        //   783: aload           18
        //   785: invokevirtual   net/minecraft/client/model/ModelRenderer.addChild:(Lnet/minecraft/client/model/ModelRenderer;)V
        //   788: iinc            16, 1
        //   791: goto            754
        //   794: aload_2        
        //   795: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static int parseAttachModel(final String s) {
        if (s == null) {
            return 0;
        }
        if (s.equals("body")) {
            return 0;
        }
        if (s.equals("head")) {
            return 1;
        }
        if (s.equals("leftArm")) {
            return 2;
        }
        if (s.equals("rightArm")) {
            return 3;
        }
        if (s.equals("leftLeg")) {
            return 4;
        }
        if (s.equals("rightLeg")) {
            return 5;
        }
        if (s.equals("cape")) {
            return 6;
        }
        Config.warn("Unknown attachModel: " + s);
        return 0;
    }
    
    static {
        PlayerItemParser.jsonParser = new JsonParser();
    }
}
