package net.minecraft.client.shader;

import org.lwjgl.util.vector.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.renderer.texture.*;
import com.google.common.base.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.client.util.*;
import java.io.*;
import com.google.gson.*;
import com.google.common.collect.*;

public class ShaderGroup
{
    public Matrix4f projectionMatrix;
    public final Map mapFramebuffers;
    public float field_148037_k;
    public Framebuffer mainFramebuffer;
    public int mainFramebufferWidth;
    public int mainFramebufferHeight;
    public final List listShaders;
    public IResourceManager resourceManager;
    public float field_148036_j;
    public final List listFramebuffers;
    public String shaderGroupName;
    
    public void parseGroup(final TextureManager textureManager, final ResourceLocation resourceLocation) throws JsonException, IOException, JsonSyntaxException {
        final JsonParser jsonParser = new JsonParser();
        final InputStream inputStream = this.resourceManager.getResource(resourceLocation).getInputStream();
        final JsonObject asJsonObject = jsonParser.parse(IOUtils.toString(inputStream, Charsets.UTF_8)).getAsJsonObject();
        int n = 0;
        if (JsonUtils.isJsonArray(asJsonObject, "targets")) {
            final Iterator iterator = asJsonObject.getAsJsonArray("targets").iterator();
            while (iterator.hasNext()) {
                this.initTarget(iterator.next());
                ++n;
            }
        }
        if (JsonUtils.isJsonArray(asJsonObject, "passes")) {
            final Iterator iterator2 = asJsonObject.getAsJsonArray("passes").iterator();
            while (iterator2.hasNext()) {
                this.parsePass(textureManager, iterator2.next());
                ++n;
            }
        }
        IOUtils.closeQuietly(inputStream);
    }
    
    private void initUniform(final JsonElement jsonElement) throws JsonException {
        final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "uniform");
        final String string = JsonUtils.getString(jsonObject, "name");
        final ShaderUniform shaderUniform = this.listShaders.get(this.listShaders.size() - 1).getShaderManager().getShaderUniform(string);
        if (shaderUniform == null) {
            throw new JsonException("Uniform '" + string + "' does not exist");
        }
        final float[] array = new float[4];
        final Iterator iterator = JsonUtils.getJsonArray(jsonObject, "values").iterator();
        while (iterator.hasNext()) {
            array[0] = JsonUtils.getFloat(iterator.next(), "value");
            int n = 0;
            ++n;
        }
        switch (false) {
            case 1: {
                shaderUniform.set(array[0]);
                break;
            }
            case 2: {
                shaderUniform.set(array[0], array[1]);
                break;
            }
            case 3: {
                shaderUniform.set(array[0], array[1], array[2]);
                break;
            }
            case 4: {
                shaderUniform.set(array[0], array[1], array[2], array[3]);
                break;
            }
        }
    }
    
    private void initTarget(final JsonElement jsonElement) throws JsonException {
        if (JsonUtils.isString(jsonElement)) {
            this.addFramebuffer(jsonElement.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
        }
        else {
            final JsonObject jsonObject = JsonUtils.getJsonObject(jsonElement, "target");
            final String string = JsonUtils.getString(jsonObject, "name");
            final int int1 = JsonUtils.getInt(jsonObject, "width", this.mainFramebufferWidth);
            final int int2 = JsonUtils.getInt(jsonObject, "height", this.mainFramebufferHeight);
            if (this.mapFramebuffers.containsKey(string)) {
                throw new JsonException(string + " is already defined");
            }
            this.addFramebuffer(string, int1, int2);
        }
    }
    
    public Framebuffer getFramebufferRaw(final String s) {
        return this.mapFramebuffers.get(s);
    }
    
    public final String getShaderGroupName() {
        return this.shaderGroupName;
    }
    
    public Shader addShader(final String s, final Framebuffer framebuffer, final Framebuffer framebuffer2) throws IOException, JsonException {
        final Shader shader = new Shader(this.resourceManager, s, framebuffer, framebuffer2);
        this.listShaders.add(this.listShaders.size(), shader);
        return shader;
    }
    
    private Framebuffer getFramebuffer(final String s) {
        return (s == null) ? null : (s.equals("minecraft:main") ? this.mainFramebuffer : this.mapFramebuffers.get(s));
    }
    
    public List getShaders() {
        return this.listShaders;
    }
    
    public void createBindFramebuffers(final int n, final int n2) {
        this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
        this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
        this.resetProjectionMatrix();
        final Iterator<Shader> iterator = this.listShaders.iterator();
        while (iterator.hasNext()) {
            iterator.next().setProjectionMatrix(this.projectionMatrix);
        }
        final Iterator<Framebuffer> iterator2 = this.listFramebuffers.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().createBindFramebuffer(n, n2);
        }
    }
    
    private void parsePass(final TextureManager p0, final JsonElement p1) throws JsonException, IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc_w           "pass"
        //     4: invokestatic    net/minecraft/util/JsonUtils.getJsonObject:(Lcom/google/gson/JsonElement;Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //     7: astore_3       
        //     8: aload_3        
        //     9: ldc             "name"
        //    11: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //    14: astore          4
        //    16: aload_3        
        //    17: ldc_w           "intarget"
        //    20: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //    23: astore          5
        //    25: aload_3        
        //    26: ldc_w           "outtarget"
        //    29: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //    32: astore          6
        //    34: aload_0        
        //    35: aload           5
        //    37: invokespecial   net/minecraft/client/shader/ShaderGroup.getFramebuffer:(Ljava/lang/String;)Lnet/minecraft/client/shader/Framebuffer;
        //    40: astore          7
        //    42: aload_0        
        //    43: aload           6
        //    45: invokespecial   net/minecraft/client/shader/ShaderGroup.getFramebuffer:(Ljava/lang/String;)Lnet/minecraft/client/shader/Framebuffer;
        //    48: astore          8
        //    50: aload           7
        //    52: ifnonnull       89
        //    55: new             Lnet/minecraft/client/util/JsonException;
        //    58: dup            
        //    59: new             Ljava/lang/StringBuilder;
        //    62: dup            
        //    63: invokespecial   java/lang/StringBuilder.<init>:()V
        //    66: ldc_w           "Input target '"
        //    69: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    72: aload           5
        //    74: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    77: ldc             "' does not exist"
        //    79: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    82: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    85: invokespecial   net/minecraft/client/util/JsonException.<init>:(Ljava/lang/String;)V
        //    88: athrow         
        //    89: aload           8
        //    91: ifnonnull       128
        //    94: new             Lnet/minecraft/client/util/JsonException;
        //    97: dup            
        //    98: new             Ljava/lang/StringBuilder;
        //   101: dup            
        //   102: invokespecial   java/lang/StringBuilder.<init>:()V
        //   105: ldc_w           "Output target '"
        //   108: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   111: aload           6
        //   113: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   116: ldc             "' does not exist"
        //   118: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   121: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   124: invokespecial   net/minecraft/client/util/JsonException.<init>:(Ljava/lang/String;)V
        //   127: athrow         
        //   128: aload_0        
        //   129: aload           4
        //   131: aload           7
        //   133: aload           8
        //   135: invokevirtual   net/minecraft/client/shader/ShaderGroup.addShader:(Ljava/lang/String;Lnet/minecraft/client/shader/Framebuffer;Lnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/client/shader/Shader;
        //   138: astore          9
        //   140: aload_3        
        //   141: ldc_w           "auxtargets"
        //   144: aconst_null    
        //   145: checkcast       Lcom/google/gson/JsonArray;
        //   148: invokestatic    net/minecraft/util/JsonUtils.getJsonArray:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   151: astore          10
        //   153: aload           10
        //   155: ifnull          508
        //   158: aload           10
        //   160: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   163: astore          12
        //   165: aload           12
        //   167: invokeinterface java/util/Iterator.hasNext:()Z
        //   172: ifeq            508
        //   175: aload           12
        //   177: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   182: checkcast       Lcom/google/gson/JsonElement;
        //   185: astore          13
        //   187: aload           13
        //   189: ldc_w           "auxtarget"
        //   192: invokestatic    net/minecraft/util/JsonUtils.getJsonObject:(Lcom/google/gson/JsonElement;Ljava/lang/String;)Lcom/google/gson/JsonObject;
        //   195: astore          14
        //   197: aload           14
        //   199: ldc             "name"
        //   201: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //   204: astore          15
        //   206: aload           14
        //   208: ldc_w           "id"
        //   211: invokestatic    net/minecraft/util/JsonUtils.getString:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Ljava/lang/String;
        //   214: astore          16
        //   216: aload_0        
        //   217: aload           16
        //   219: invokespecial   net/minecraft/client/shader/ShaderGroup.getFramebuffer:(Ljava/lang/String;)Lnet/minecraft/client/shader/Framebuffer;
        //   222: astore          17
        //   224: aload           17
        //   226: ifnonnull       438
        //   229: new             Lnet/minecraft/util/ResourceLocation;
        //   232: dup            
        //   233: new             Ljava/lang/StringBuilder;
        //   236: dup            
        //   237: invokespecial   java/lang/StringBuilder.<init>:()V
        //   240: ldc_w           "textures/effect/"
        //   243: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   246: aload           16
        //   248: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   251: ldc_w           ".png"
        //   254: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   257: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   260: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   263: astore          18
        //   265: aload_0        
        //   266: getfield        net/minecraft/client/shader/ShaderGroup.resourceManager:Lnet/minecraft/client/resources/IResourceManager;
        //   269: aload           18
        //   271: invokeinterface net/minecraft/client/resources/IResourceManager.getResource:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/resources/IResource;
        //   276: pop            
        //   277: goto            316
        //   280: astore          19
        //   282: new             Lnet/minecraft/client/util/JsonException;
        //   285: dup            
        //   286: new             Ljava/lang/StringBuilder;
        //   289: dup            
        //   290: invokespecial   java/lang/StringBuilder.<init>:()V
        //   293: ldc_w           "Render target or texture '"
        //   296: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   299: aload           16
        //   301: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   304: ldc             "' does not exist"
        //   306: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   309: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   312: invokespecial   net/minecraft/client/util/JsonException.<init>:(Ljava/lang/String;)V
        //   315: athrow         
        //   316: aload_1        
        //   317: aload           18
        //   319: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   322: aload_1        
        //   323: aload           18
        //   325: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.getTexture:(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraft/client/renderer/texture/ITextureObject;
        //   328: astore          19
        //   330: aload           14
        //   332: ldc             "width"
        //   334: invokestatic    net/minecraft/util/JsonUtils.getInt:(Lcom/google/gson/JsonObject;Ljava/lang/String;)I
        //   337: istore          20
        //   339: aload           14
        //   341: ldc             "height"
        //   343: invokestatic    net/minecraft/util/JsonUtils.getInt:(Lcom/google/gson/JsonObject;Ljava/lang/String;)I
        //   346: istore          21
        //   348: aload           14
        //   350: ldc_w           "bilinear"
        //   353: invokestatic    net/minecraft/util/JsonUtils.getBoolean:(Lcom/google/gson/JsonObject;Ljava/lang/String;)Z
        //   356: istore          22
        //   358: iload           22
        //   360: ifeq            390
        //   363: sipush          3553
        //   366: sipush          10241
        //   369: sipush          9729
        //   372: invokestatic    org/lwjgl/opengl/GL11.glTexParameteri:(III)V
        //   375: sipush          3553
        //   378: sipush          10240
        //   381: sipush          9729
        //   384: invokestatic    org/lwjgl/opengl/GL11.glTexParameteri:(III)V
        //   387: goto            414
        //   390: sipush          3553
        //   393: sipush          10241
        //   396: sipush          9728
        //   399: invokestatic    org/lwjgl/opengl/GL11.glTexParameteri:(III)V
        //   402: sipush          3553
        //   405: sipush          10240
        //   408: sipush          9728
        //   411: invokestatic    org/lwjgl/opengl/GL11.glTexParameteri:(III)V
        //   414: aload           9
        //   416: aload           15
        //   418: aload           19
        //   420: invokeinterface net/minecraft/client/renderer/texture/ITextureObject.getGlTextureId:()I
        //   425: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   428: iload           20
        //   430: iload           21
        //   432: invokevirtual   net/minecraft/client/shader/Shader.addAuxFramebuffer:(Ljava/lang/String;Ljava/lang/Object;II)V
        //   435: goto            502
        //   438: aload           9
        //   440: aload           15
        //   442: aload           17
        //   444: aload           17
        //   446: getfield        net/minecraft/client/shader/Framebuffer.framebufferTextureWidth:I
        //   449: aload           17
        //   451: getfield        net/minecraft/client/shader/Framebuffer.framebufferTextureHeight:I
        //   454: invokevirtual   net/minecraft/client/shader/Shader.addAuxFramebuffer:(Ljava/lang/String;Ljava/lang/Object;II)V
        //   457: goto            502
        //   460: astore          14
        //   462: aload           14
        //   464: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   467: astore          15
        //   469: aload           15
        //   471: new             Ljava/lang/StringBuilder;
        //   474: dup            
        //   475: invokespecial   java/lang/StringBuilder.<init>:()V
        //   478: ldc_w           "auxtargets["
        //   481: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   484: iconst_0       
        //   485: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   488: ldc             "]"
        //   490: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   493: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   496: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   499: aload           15
        //   501: athrow         
        //   502: iinc            11, 1
        //   505: goto            165
        //   508: aload_3        
        //   509: ldc_w           "uniforms"
        //   512: aconst_null    
        //   513: checkcast       Lcom/google/gson/JsonArray;
        //   516: invokestatic    net/minecraft/util/JsonUtils.getJsonArray:(Lcom/google/gson/JsonObject;Ljava/lang/String;Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;
        //   519: astore          11
        //   521: aload           11
        //   523: ifnull          612
        //   526: aload           11
        //   528: invokevirtual   com/google/gson/JsonArray.iterator:()Ljava/util/Iterator;
        //   531: astore          13
        //   533: aload           13
        //   535: invokeinterface java/util/Iterator.hasNext:()Z
        //   540: ifeq            612
        //   543: aload           13
        //   545: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   550: checkcast       Lcom/google/gson/JsonElement;
        //   553: astore          14
        //   555: aload_0        
        //   556: aload           14
        //   558: invokespecial   net/minecraft/client/shader/ShaderGroup.initUniform:(Lcom/google/gson/JsonElement;)V
        //   561: goto            606
        //   564: astore          15
        //   566: aload           15
        //   568: invokestatic    net/minecraft/client/util/JsonException.func_151379_a:(Ljava/lang/Exception;)Lnet/minecraft/client/util/JsonException;
        //   571: astore          16
        //   573: aload           16
        //   575: new             Ljava/lang/StringBuilder;
        //   578: dup            
        //   579: invokespecial   java/lang/StringBuilder.<init>:()V
        //   582: ldc_w           "uniforms["
        //   585: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   588: iconst_0       
        //   589: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   592: ldc             "]"
        //   594: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   597: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   600: invokevirtual   net/minecraft/client/util/JsonException.func_151380_a:(Ljava/lang/String;)V
        //   603: aload           16
        //   605: athrow         
        //   606: iinc            12, 1
        //   609: goto            533
        //   612: return         
        //    Exceptions:
        //  throws net.minecraft.client.util.JsonException
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public ShaderGroup(final TextureManager textureManager, final IResourceManager resourceManager, final Framebuffer mainFramebuffer, final ResourceLocation resourceLocation) throws JsonException, IOException, JsonSyntaxException {
        this.listShaders = Lists.newArrayList();
        this.mapFramebuffers = Maps.newHashMap();
        this.listFramebuffers = Lists.newArrayList();
        this.resourceManager = resourceManager;
        this.mainFramebuffer = mainFramebuffer;
        this.field_148036_j = 0.0f;
        this.field_148037_k = 0.0f;
        this.mainFramebufferWidth = mainFramebuffer.framebufferWidth;
        this.mainFramebufferHeight = mainFramebuffer.framebufferHeight;
        this.shaderGroupName = resourceLocation.toString();
        this.resetProjectionMatrix();
        this.parseGroup(textureManager, resourceLocation);
    }
    
    public void deleteShaderGroup() {
        final Iterator<Framebuffer> iterator = this.mapFramebuffers.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().deleteFramebuffer();
        }
        final Iterator<Shader> iterator2 = this.listShaders.iterator();
        while (iterator2.hasNext()) {
            iterator2.next().deleteShader();
        }
        this.listShaders.clear();
    }
    
    public void addFramebuffer(final String s, final int n, final int n2) {
        final Framebuffer framebuffer = new Framebuffer(n, n2, true);
        framebuffer.setFramebufferColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mapFramebuffers.put(s, framebuffer);
        if (n == this.mainFramebufferWidth && n2 == this.mainFramebufferHeight) {
            this.listFramebuffers.add(framebuffer);
        }
    }
    
    private void resetProjectionMatrix() {
        (this.projectionMatrix = new Matrix4f()).setIdentity();
        this.projectionMatrix.m00 = 2.0f / this.mainFramebuffer.framebufferTextureWidth;
        this.projectionMatrix.m11 = 2.0f / -this.mainFramebuffer.framebufferTextureHeight;
        this.projectionMatrix.m22 = -0.0020001999f;
        this.projectionMatrix.m33 = 1.0f;
        this.projectionMatrix.m03 = -1.0f;
        this.projectionMatrix.m13 = 1.0f;
        this.projectionMatrix.m23 = -1.0001999f;
    }
    
    public void loadShaderGroup(final float field_148037_k) {
        if (field_148037_k < this.field_148037_k) {
            this.field_148036_j += 1.0f - this.field_148037_k;
            this.field_148036_j += field_148037_k;
        }
        else {
            this.field_148036_j += field_148037_k - this.field_148037_k;
        }
        this.field_148037_k = field_148037_k;
        while (this.field_148036_j > 20.0f) {
            this.field_148036_j -= 20.0f;
        }
        final Iterator<Shader> iterator = this.listShaders.iterator();
        while (iterator.hasNext()) {
            iterator.next().loadShader(this.field_148036_j / 20.0f);
        }
    }
}
