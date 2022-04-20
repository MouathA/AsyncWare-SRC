package net.minecraft.client.shader;

import net.minecraft.client.resources.*;
import net.minecraft.client.util.*;
import java.io.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class ShaderManager
{
    private final int program;
    private final List shaderUniformLocations;
    private final ShaderLoader vertexShaderLoader;
    private final Map shaderSamplers;
    private final List attributes;
    private final JsonBlendingMode field_148016_p;
    private final Map mappedShaderUniforms;
    private static final Logger logger;
    private static final ShaderDefault defaultShaderUniform;
    private final List shaderSamplerLocations;
    private final ShaderLoader fragmentShaderLoader;
    private final List shaderUniforms;
    private final boolean useFaceCulling;
    private final String programFilename;
    private final List samplerNames;
    private boolean isDirty;
    private static ShaderManager staticShaderManager;
    private final List attribLocations;
    private static boolean field_148000_e;
    
    public ShaderManager(final IResourceManager p0, final String p1) throws JsonException, IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   java/lang/Object.<init>:()V
        //     4: aload_0        
        //     5: invokestatic    com/google/common/collect/Maps.newHashMap:()Ljava/util/HashMap;
        //     8: putfield        net/minecraft/client/shader/ShaderManager.shaderSamplers:Ljava/util/Map;
        //    11: aload_0        
        //    12: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    15: putfield        net/minecraft/client/shader/ShaderManager.samplerNames:Ljava/util/List;
        //    18: aload_0        
        //    19: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    22: putfield        net/minecraft/client/shader/ShaderManager.shaderSamplerLocations:Ljava/util/List;
        //    25: aload_0        
        //    26: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    29: putfield        net/minecraft/client/shader/ShaderManager.shaderUniforms:Ljava/util/List;
        //    32: aload_0        
        //    33: invokestatic    com/google/common/collect/Lists.newArrayList:()Ljava/util/ArrayList;
        //    36: putfield        net/minecraft/client/shader/ShaderManager.shaderUniformLocations:Ljava/util/List;
        //    39: aload_0        
        //    40: invokestatic    com/google/common/collect/Maps.newHashMap:()Ljava/util/HashMap;
        //    43: putfield        net/minecraft/client/shader/ShaderManager.mappedShaderUniforms:Ljava/util/Map;
        //    46: new             Lcom/google/gson/JsonParser;
        //    49: dup            
        //    50: invokespecial   com/google/gson/JsonParser.<init>:()V
        //    53: astore_3       
        //    54: new             Lnet/minecraft/util/ResourceLocation;
        //    57: dup            
        //    58: new             Ljava/lang/StringBuilder;
        //    61: dup            
        //    62: invokespecial   java/lang/StringBuilder.<init>:()V
        //    65: ldc             "shaders/program/"
        //    67: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    70: aload_2        
        //    71: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    74: ldc             ".json"
        //    76: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    79: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    82: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //    85: astore          4
        //    87: aload_0        
        //    88: aload_2        
        //    89: putfield        net/minecraft/client/shader/ShaderManager.programFilename:Ljava/lang/String;
        //    92: aconst_null    
        //    93: astore          5
        //    95: aload_1        
        //    96: aload           4
        //    98: invokeinterface net/minecraft/client/resources/IResourceManager.getResource:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/IResource;
        //   103: invokeinterface net/minecraft/client/resources/IResource.getInputStream:()Ljava/io/InputStream;
        //   108: astore          5
        //   110: aload_3        
        //   111: aload           5
        //   113: getstatic       com/google/common/base/Charsets.UTF_8:Ljava/nio/charset/Charset;
        //   116: invokestatic    org/apache/commons/io/IOUtils.toString:(Ljava/io/InputStream;Ljava/nio/charset/Charset;)Ljava/lang/String;
        //   119: invokevirtual   com/google/gson/JsonParser.parse:(Ljava/lang/String;)Lcom/google/gson/JsonElement;
        //   122: invokevirtual   com/google/gson/JsonElement.getAsJsonObject:()Lcom/google/gson/JsonObject;
        //   125: astore          6
        //   127: aload           6
        //   129: ldc             "vertex"
        //   131: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //   134: astore          7
        //   136: aload           6
        //   138: ldc             "fragment"
        //   140: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //   143: astore          8
        //   145: aload           6
        //   147: ldc             "samplers"
        //   149: aconst_null    
        //   150: checkcast       Lcom/google/gson/JsonArray;
        //   153: invokestatic    net/minecraft/util/JsonUtils.getJsonArray:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   156: astore          9
        //   158: aload           9
        //   160: ifnull          248
        //   163: aload           9
        //   165: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   168: astore          11
        //   170: aload           11
        //   172: invokeinterface java/util/Iterator.hasNext:()Z
        //   177: ifeq            248
        //   180: aload           11
        //   182: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   187: checkcast       Lcom/google/gson/JsonElement;
        //   190: astore          12
        //   192: aload_0        
        //   193: aload           12
        //   195: invokespecial   net/minecraft/client/shader/ShaderManager.parseSampler:(Lcom/google/gson/JsonElement;)V
        //   198: goto            242
        //   201: astore          13
        //   203: aload           13
        //   205: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   208: astore          14
        //   210: aload           14
        //   212: new             Ljava/lang/StringBuilder;
        //   215: dup            
        //   216: invokespecial   java/lang/StringBuilder.<init>:()V
        //   219: ldc             "samplers["
        //   221: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   224: iconst_0       
        //   225: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   228: ldc             "]"
        //   230: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   233: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   236: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   239: aload           14
        //   241: athrow         
        //   242: iinc            10, 1
        //   245: goto            170
        //   248: aload           6
        //   250: ldc             "attributes"
        //   252: aconst_null    
        //   253: checkcast       Lcom/google/gson/JsonArray;
        //   256: invokestatic    net/minecraft/util/JsonUtils.getJsonArray:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   259: astore          10
        //   261: aload           10
        //   263: ifnull          389
        //   266: aload_0        
        //   267: aload           10
        //   269: invokevirtual   com/google/gson/JsonArray.size:()I
        //   272: invokestatic    com/google/common/collect/Lists.newArrayListWithCapacity:(I)Ljava/util/ArrayList;
        //   275: putfield        net/minecraft/client/shader/ShaderManager.attribLocations:Ljava/util/List;
        //   278: aload_0        
        //   279: aload           10
        //   281: invokevirtual   com/google/gson/JsonArray.size:()I
        //   284: invokestatic    com/google/common/collect/Lists.newArrayListWithCapacity:(I)Ljava/util/ArrayList;
        //   287: putfield        net/minecraft/client/shader/ShaderManager.attributes:Ljava/util/List;
        //   290: aload           10
        //   292: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   295: astore          12
        //   297: aload           12
        //   299: invokeinterface java/util/Iterator.hasNext:()Z
        //   304: ifeq            386
        //   307: aload           12
        //   309: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   314: checkcast       Lcom/google/gson/JsonElement;
        //   317: astore          13
        //   319: aload_0        
        //   320: getfield        net/minecraft/client/shader/ShaderManager.attributes:Ljava/util/List;
        //   323: aload           13
        //   325: ldc             "attribute"
        //   327: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonElement;Ljava/lang/String;)Ljava/lang/String;
        //   330: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   335: pop            
        //   336: goto            380
        //   339: astore          14
        //   341: aload           14
        //   343: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   346: astore          15
        //   348: aload           15
        //   350: new             Ljava/lang/StringBuilder;
        //   353: dup            
        //   354: invokespecial   java/lang/StringBuilder.<init>:()V
        //   357: ldc             "attributes["
        //   359: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   362: iconst_0       
        //   363: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   366: ldc             "]"
        //   368: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   371: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   374: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   377: aload           15
        //   379: athrow         
        //   380: iinc            11, 1
        //   383: goto            297
        //   386: goto            399
        //   389: aload_0        
        //   390: aconst_null    
        //   391: putfield        net/minecraft/client/shader/ShaderManager.attribLocations:Ljava/util/List;
        //   394: aload_0        
        //   395: aconst_null    
        //   396: putfield        net/minecraft/client/shader/ShaderManager.attributes:Ljava/util/List;
        //   399: aload           6
        //   401: ldc             "uniforms"
        //   403: aconst_null    
        //   404: checkcast       Lcom/google/gson/JsonArray;
        //   407: invokestatic    net/minecraft/util/JsonUtils.getJsonArray:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   410: astore          11
        //   412: aload           11
        //   414: ifnull          502
        //   417: aload           11
        //   419: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   422: astore          13
        //   424: aload           13
        //   426: invokeinterface java/util/Iterator.hasNext:()Z
        //   431: ifeq            502
        //   434: aload           13
        //   436: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   441: checkcast       Lcom/google/gson/JsonElement;
        //   444: astore          14
        //   446: aload_0        
        //   447: aload           14
        //   449: invokespecial   net/minecraft/client/shader/ShaderManager.parseUniform:(Lcom/google/gson/JsonElement;)V
        //   452: goto            496
        //   455: astore          15
        //   457: aload           15
        //   459: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   462: astore          16
        //   464: aload           16
        //   466: new             Ljava/lang/StringBuilder;
        //   469: dup            
        //   470: invokespecial   java/lang/StringBuilder.<init>:()V
        //   473: ldc             "uniforms["
        //   475: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   478: iconst_0       
        //   479: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   482: ldc             "]"
        //   484: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   487: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   490: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   493: aload           16
        //   495: athrow         
        //   496: iinc            12, 1
        //   499: goto            424
        //   502: aload_0        
        //   503: aload           6
        //   505: ldc             "blend"
        //   507: aconst_null    
        //   508: checkcast       Lcom/google/gson/JsonObject;
        //   511: invokestatic    net/minecraft/util/JsonUtils.getJsonObject:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;
        //   514: invokestatic    net/minecraft/client/util/JsonBlendingMode.func_148110_a:(Lcom/google/gson/JsonObject;)Lnet/minecraft/client/util/JsonBlendingMode;
        //   517: putfield        net/minecraft/client/shader/ShaderManager.field_148016_p:Lnet/minecraft/client/util/JsonBlendingMode;
        //   520: aload_0        
        //   521: aload           6
        //   523: ldc             "cull"
        //   525: iconst_1       
        //   526: invokestatic    net/minecraft/util/JsonUtils.getBoolean:(Lcom/google/gson/JsonObject;Ljava/lang/String;Z)Z
        //   529: putfield        net/minecraft/client/shader/ShaderManager.useFaceCulling:Z
        //   532: aload_0        
        //   533: aload_1        
        //   534: getstatic       net/minecraft/client/shader/ShaderLoader$ShaderType.VERTEX:Lnet/minecraft/client/shader/ShaderLoader$ShaderType;
        //   537: aload           7
        //   539: invokestatic    net/minecraft/client/shader/ShaderLoader.loadShader:(Lnet/minecraft/client/resources/IResourceManager;Lnet/minecraft/client/shader/ShaderLoader$ShaderType;Ljava/lang/String;)Lnet/minecraft/client/shader/ShaderLoader;
        //   542: putfield        net/minecraft/client/shader/ShaderManager.vertexShaderLoader:Lnet/minecraft/client/shader/ShaderLoader;
        //   545: aload_0        
        //   546: aload_1        
        //   547: getstatic       net/minecraft/client/shader/ShaderLoader$ShaderType.FRAGMENT:Lnet/minecraft/client/shader/ShaderLoader$ShaderType;
        //   550: aload           8
        //   552: invokestatic    net/minecraft/client/shader/ShaderLoader.loadShader:(Lnet/minecraft/client/resources/IResourceManager;Lnet/minecraft/client/shader/ShaderLoader$ShaderType;Ljava/lang/String;)Lnet/minecraft/client/shader/ShaderLoader;
        //   555: putfield        net/minecraft/client/shader/ShaderManager.fragmentShaderLoader:Lnet/minecraft/client/shader/ShaderLoader;
        //   558: aload_0        
        //   559: invokestatic    net/minecraft/client/shader/ShaderLinkHelper.getStaticShaderLinkHelper:()Lnet/minecraft/client/shader/ShaderLinkHelper;
        //   562: invokevirtual   net/minecraft/client/shader/ShaderLinkHelper.createProgram:()I
        //   565: putfield        net/minecraft/client/shader/ShaderManager.program:I
        //   568: invokestatic    net/minecraft/client/shader/ShaderLinkHelper.getStaticShaderLinkHelper:()Lnet/minecraft/client/shader/ShaderLinkHelper;
        //   571: aload_0        
        //   572: invokevirtual   net/minecraft/client/shader/ShaderLinkHelper.linkProgram:(Lnet/minecraft/client/shader/ShaderManager;)V
        //   575: aload_0        
        //   576: invokespecial   net/minecraft/client/shader/ShaderManager.setupUniforms:()V
        //   579: aload_0        
        //   580: getfield        net/minecraft/client/shader/ShaderManager.attributes:Ljava/util/List;
        //   583: ifnull          648
        //   586: aload_0        
        //   587: getfield        net/minecraft/client/shader/ShaderManager.attributes:Ljava/util/List;
        //   590: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   595: astore          12
        //   597: aload           12
        //   599: invokeinterface java/util/Iterator.hasNext:()Z
        //   604: ifeq            648
        //   607: aload           12
        //   609: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   614: checkcast       Ljava/lang/String;
        //   617: astore          13
        //   619: aload_0        
        //   620: getfield        net/minecraft/client/shader/ShaderManager.program:I
        //   623: aload           13
        //   625: invokestatic    net/minecraft/client/renderer/OpenGlHelper.glGetAttribLocation:(ILjava/lang/CharSequence;)I
        //   628: istore          14
        //   630: aload_0        
        //   631: getfield        net/minecraft/client/shader/ShaderManager.attribLocations:Ljava/util/List;
        //   634: iload           14
        //   636: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   639: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   644: pop            
        //   645: goto            597
        //   648: aload           5
        //   650: invokestatic    org/apache/commons/io/IOUtils.closeQuietly:(Ljava/io/InputStream;)V
        //   653: goto            688
        //   656: astore          6
        //   658: aload           6
        //   660: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   663: astore          7
        //   665: aload           7
        //   667: aload           4
        //   669: invokevirtual   net/minecraft/util/ResourceLocation.getResourcePath:()Ljava/lang/String;
        //   672: invokevirtual   net/minecraft/client/util/JsonException.func_151381_b:(Ljava/lang/String;)V
        //   675: aload           7
        //   677: athrow         
        //   678: astore          17
        //   680: aload           5
        //   682: invokestatic    org/apache/commons/io/IOUtils.closeQuietly:(Ljava/io/InputStream;)V
        //   685: aload           17
        //   687: athrow         
        //   688: aload_0        
        //   689: invokevirtual   net/minecraft/client/shader/ShaderManager.markDirty:()V
        //   692: return         
        //    Exceptions:
        //  throws net.minecraft.client.util.JsonException
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:211)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
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
    
    public void endShader() {
        OpenGlHelper.glUseProgram(0);
        ShaderManager.currentProgram = -1;
        ShaderManager.staticShaderManager = null;
        ShaderManager.field_148000_e = true;
        while (0 < this.shaderSamplerLocations.size()) {
            if (this.shaderSamplers.get(this.samplerNames.get(0)) != null) {
                GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + 0);
                GlStateManager.bindTexture(0);
            }
            int n = 0;
            ++n;
        }
    }
    
    public void useShader() {
        this.isDirty = false;
        ShaderManager.staticShaderManager = this;
        this.field_148016_p.func_148109_a();
        if (this.program != -1) {
            OpenGlHelper.glUseProgram(this.program);
            ShaderManager.currentProgram = this.program;
        }
        if (this.useFaceCulling) {}
        while (0 < this.shaderSamplerLocations.size()) {
            if (this.shaderSamplers.get(this.samplerNames.get(0)) != null) {
                GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + 0);
                final Integer value = this.shaderSamplers.get(this.samplerNames.get(0));
                if (value instanceof Framebuffer) {
                    final int framebufferTexture = ((Framebuffer)value).framebufferTexture;
                }
                else if (value instanceof ITextureObject) {
                    ((ITextureObject)value).getGlTextureId();
                }
                else if (value instanceof Integer) {
                    value;
                }
            }
            int n = 0;
            ++n;
        }
        final Iterator<ShaderUniform> iterator = this.shaderUniforms.iterator();
        while (iterator.hasNext()) {
            iterator.next().upload();
        }
    }
    
    public ShaderUniform getShaderUniform(final String s) {
        return this.mappedShaderUniforms.containsKey(s) ? this.mappedShaderUniforms.get(s) : null;
    }
    
    static {
        logger = LogManager.getLogger();
        defaultShaderUniform = new ShaderDefault();
        ShaderManager.staticShaderManager = null;
        ShaderManager.field_148000_e = true;
    }
    
    public ShaderLoader getFragmentShaderLoader() {
        return this.fragmentShaderLoader;
    }
    
    public ShaderLoader getVertexShaderLoader() {
        return this.vertexShaderLoader;
    }
    
    public void deleteShader() {
        ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
    }
    
    public void markDirty() {
        this.isDirty = true;
    }
    
    public void addSamplerTexture(final String s, final Object o) {
        if (this.shaderSamplers.containsKey(s)) {
            this.shaderSamplers.remove(s);
        }
        this.shaderSamplers.put(s, o);
        this.markDirty();
    }
    
    public int getProgram() {
        return this.program;
    }
    
    private void parseUniform(final JsonElement jsonElement) throws JsonException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "uniform");
        final String string = JsonUtils.getString(jsonObject, "name");
        final int type = ShaderUniform.parseType(JsonUtils.getString(jsonObject, "type"));
        final int int1 = JsonUtils.getInt(jsonObject, "count");
        final float[] array = new float[Math.max(int1, 16)];
        final JsonArray jsonArray = JsonUtils.getJsonArray(jsonObject, "values");
        if (jsonArray.size() != int1 && jsonArray.size() > 1) {
            throw new JsonException("Invalid amount of values specified (expected " + int1 + ", found " + jsonArray.size() + ")");
        }
        final Iterator iterator = jsonArray.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            array[0] = JsonUtils.getFloat(iterator.next(), "value");
            ++n;
        }
        if (int1 > 1 && jsonArray.size() == 1) {
            while (0 < int1) {
                array[0] = array[0];
                ++n;
            }
        }
        final ShaderUniform shaderUniform = new ShaderUniform(string, type + ((int1 > 1 && int1 <= 4 && type < 8) ? (int1 - 1) : 0), int1, this);
        if (type <= 3) {
            shaderUniform.set((int)array[0], (int)array[1], (int)array[2], (int)array[3]);
        }
        else if (type <= 7) {
            shaderUniform.func_148092_b(array[0], array[1], array[2], array[3]);
        }
        else {
            shaderUniform.set(array);
        }
        this.shaderUniforms.add(shaderUniform);
    }
    
    public ShaderUniform getShaderUniformOrDefault(final String s) {
        return this.mappedShaderUniforms.containsKey(s) ? this.mappedShaderUniforms.get(s) : ShaderManager.defaultShaderUniform;
    }
    
    private void setupUniforms() {
        while (0 < this.samplerNames.size()) {
            final String s = this.samplerNames.get(0);
            final int glGetUniformLocation = OpenGlHelper.glGetUniformLocation(this.program, s);
            int n = 0;
            if (glGetUniformLocation == -1) {
                ShaderManager.logger.warn("Shader " + this.programFilename + "could not find sampler named " + s + " in the specified shader program.");
                this.shaderSamplers.remove(s);
                this.samplerNames.remove(0);
                --n;
            }
            else {
                this.shaderSamplerLocations.add(glGetUniformLocation);
            }
            int n2 = 0;
            ++n2;
            ++n;
        }
        for (final ShaderUniform shaderUniform : this.shaderUniforms) {
            final String shaderName = shaderUniform.getShaderName();
            final int glGetUniformLocation2 = OpenGlHelper.glGetUniformLocation(this.program, shaderName);
            if (glGetUniformLocation2 == -1) {
                ShaderManager.logger.warn("Could not find uniform named " + shaderName + " in the specified shader program.");
            }
            else {
                this.shaderUniformLocations.add(glGetUniformLocation2);
                shaderUniform.setUniformLocation(glGetUniformLocation2);
                this.mappedShaderUniforms.put(shaderName, shaderUniform);
            }
        }
    }
    
    private void parseSampler(final JsonElement jsonElement) throws JsonException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "sampler");
        final String string = JsonUtils.getString(jsonObject, "name");
        if (!JsonUtils.isString(jsonObject, "file")) {
            this.shaderSamplers.put(string, null);
            this.samplerNames.add(string);
        }
        else {
            this.samplerNames.add(string);
        }
    }
}
