package net.minecraft.client.gui.inventory;

import net.minecraft.client.renderer.*;
import java.io.*;
import net.minecraft.creativetab.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import org.lwjgl.input.*;
import net.minecraft.client.settings.*;
import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;

public class GuiContainerCreative extends InventoryEffectRenderer
{
    private static InventoryBasic field_147060_v;
    private GuiTextField searchField;
    private boolean wasClicking;
    private List field_147063_B;
    private boolean field_147057_D;
    private static int selectedTabIndex;
    private static final ResourceLocation creativeInventoryTabs;
    private boolean isScrolling;
    private float currentScroll;
    private CreativeCrafting field_147059_E;
    private Slot field_147064_C;
    
    @Override
    protected void mouseClicked(final int p0, final int p1, final int p2) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifne            59
        //     4: iload_1        
        //     5: aload_0        
        //     6: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiLeft:I
        //     9: isub           
        //    10: istore          4
        //    12: iload_2        
        //    13: aload_0        
        //    14: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiTop:I
        //    17: isub           
        //    18: istore          5
        //    20: getstatic       net/minecraft/creativetab/CreativeTabs.creativeTabArray:[Lnet/minecraft/creativetab/CreativeTabs;
        //    23: astore          6
        //    25: aload           6
        //    27: arraylength    
        //    28: istore          7
        //    30: iconst_0       
        //    31: iload           7
        //    33: if_icmpge       59
        //    36: aload           6
        //    38: iconst_0       
        //    39: aaload         
        //    40: astore          9
        //    42: aload_0        
        //    43: aload           9
        //    45: iload           4
        //    47: iload           5
        //    49: if_icmpne       53
        //    52: return         
        //    53: iinc            8, 1
        //    56: goto            30
        //    59: aload_0        
        //    60: iload_1        
        //    61: iload_2        
        //    62: iload_3        
        //    63: invokespecial   net/minecraft/client/renderer/InventoryEffectRenderer.mouseClicked:(III)V
        //    66: return         
        //    Exceptions:
        //  throws java.io.IOException
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0030 (coming from #0056).
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
    
    @Override
    protected void mouseReleased(final int p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifne            65
        //     4: iload_1        
        //     5: aload_0        
        //     6: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiLeft:I
        //     9: isub           
        //    10: istore          4
        //    12: iload_2        
        //    13: aload_0        
        //    14: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiTop:I
        //    17: isub           
        //    18: istore          5
        //    20: getstatic       net/minecraft/creativetab/CreativeTabs.creativeTabArray:[Lnet/minecraft/creativetab/CreativeTabs;
        //    23: astore          6
        //    25: aload           6
        //    27: arraylength    
        //    28: istore          7
        //    30: iconst_0       
        //    31: iload           7
        //    33: if_icmpge       65
        //    36: aload           6
        //    38: iconst_0       
        //    39: aaload         
        //    40: astore          9
        //    42: aload_0        
        //    43: aload           9
        //    45: iload           4
        //    47: iload           5
        //    49: if_icmpne       59
        //    52: aload_0        
        //    53: aload           9
        //    55: invokespecial   net/minecraft/client/gui/inventory/GuiContainerCreative.setCurrentCreativeTab:(Lnet/minecraft/creativetab/CreativeTabs;)V
        //    58: return         
        //    59: iinc            8, 1
        //    62: goto            30
        //    65: aload_0        
        //    66: iload_1        
        //    67: iload_2        
        //    68: iload_3        
        //    69: invokespecial   net/minecraft/client/renderer/InventoryEffectRenderer.mouseReleased:(III)V
        //    72: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0030 (coming from #0062).
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
    
    public int getSelectedTabIndex() {
        return GuiContainerCreative.selectedTabIndex;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(final float p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: fconst_1       
        //     2: fconst_1       
        //     3: fconst_1       
        //     4: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //     7: getstatic       net/minecraft/creativetab/CreativeTabs.creativeTabArray:[Lnet/minecraft/creativetab/CreativeTabs;
        //    10: getstatic       net/minecraft/client/gui/inventory/GuiContainerCreative.selectedTabIndex:I
        //    13: aaload         
        //    14: astore          4
        //    16: getstatic       net/minecraft/creativetab/CreativeTabs.creativeTabArray:[Lnet/minecraft/creativetab/CreativeTabs;
        //    19: astore          5
        //    21: aload           5
        //    23: arraylength    
        //    24: istore          6
        //    26: iconst_0       
        //    27: iload           6
        //    29: if_icmpge       74
        //    32: aload           5
        //    34: iconst_0       
        //    35: aaload         
        //    36: astore          8
        //    38: aload_0        
        //    39: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.mc:Lnet/minecraft/client/Minecraft;
        //    42: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //    45: getstatic       net/minecraft/client/gui/inventory/GuiContainerCreative.creativeInventoryTabs:Lnet/minecraft/util/ResourceLocation;
        //    48: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //    51: aload           8
        //    53: invokevirtual   net/minecraft/creativetab/CreativeTabs.getTabIndex:()I
        //    56: getstatic       net/minecraft/client/gui/inventory/GuiContainerCreative.selectedTabIndex:I
        //    59: if_icmpeq       68
        //    62: aload_0        
        //    63: aload           8
        //    65: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative.func_147051_a:(Lnet/minecraft/creativetab/CreativeTabs;)V
        //    68: iinc            7, 1
        //    71: goto            26
        //    74: aload_0        
        //    75: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.mc:Lnet/minecraft/client/Minecraft;
        //    78: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //    81: new             Lnet/minecraft/util/ResourceLocation;
        //    84: dup            
        //    85: new             Ljava/lang/StringBuilder;
        //    88: dup            
        //    89: invokespecial   java/lang/StringBuilder.<init>:()V
        //    92: ldc             "textures/gui/container/creative_inventory/tab_"
        //    94: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    97: aload           4
        //    99: invokevirtual   net/minecraft/creativetab/CreativeTabs.getBackgroundImageName:()Ljava/lang/String;
        //   102: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   105: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   108: invokespecial   net/minecraft/util/ResourceLocation.<init>:(Ljava/lang/String;)V
        //   111: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   114: aload_0        
        //   115: aload_0        
        //   116: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiLeft:I
        //   119: aload_0        
        //   120: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiTop:I
        //   123: iconst_0       
        //   124: iconst_0       
        //   125: aload_0        
        //   126: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.xSize:I
        //   129: aload_0        
        //   130: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.ySize:I
        //   133: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative.drawTexturedModalRect:(IIIIII)V
        //   136: aload_0        
        //   137: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.searchField:Lnet/minecraft/client/gui/GuiTextField;
        //   140: invokevirtual   net/minecraft/client/gui/GuiTextField.drawTextBox:()V
        //   143: fconst_1       
        //   144: fconst_1       
        //   145: fconst_1       
        //   146: fconst_1       
        //   147: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //   150: aload_0        
        //   151: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiLeft:I
        //   154: sipush          175
        //   157: iadd           
        //   158: istore          5
        //   160: aload_0        
        //   161: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiTop:I
        //   164: bipush          18
        //   166: iadd           
        //   167: istore          6
        //   169: iload           6
        //   171: bipush          112
        //   173: iadd           
        //   174: istore          7
        //   176: aload_0        
        //   177: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.mc:Lnet/minecraft/client/Minecraft;
        //   180: invokevirtual   net/minecraft/client/Minecraft.getTextureManager:()Lnet/minecraft/client/renderer/texture/TextureManager;
        //   183: getstatic       net/minecraft/client/gui/inventory/GuiContainerCreative.creativeInventoryTabs:Lnet/minecraft/util/ResourceLocation;
        //   186: invokevirtual   net/minecraft/client/renderer/texture/TextureManager.bindTexture:(Lnet/minecraft/util/ResourceLocation;)V
        //   189: aload           4
        //   191: invokevirtual   net/minecraft/creativetab/CreativeTabs.shouldHidePlayerInventory:()Z
        //   194: ifeq            239
        //   197: aload_0        
        //   198: iload           5
        //   200: iload           6
        //   202: iconst_0       
        //   203: iload           6
        //   205: isub           
        //   206: bipush          17
        //   208: isub           
        //   209: i2f            
        //   210: aload_0        
        //   211: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.currentScroll:F
        //   214: fmul           
        //   215: f2i            
        //   216: iadd           
        //   217: sipush          232
        //   220: aload_0        
        //   221: if_icmpeq       228
        //   224: iconst_0       
        //   225: goto            230
        //   228: bipush          12
        //   230: iadd           
        //   231: iconst_0       
        //   232: bipush          12
        //   234: bipush          15
        //   236: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative.drawTexturedModalRect:(IIIIII)V
        //   239: aload_0        
        //   240: aload           4
        //   242: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative.func_147051_a:(Lnet/minecraft/creativetab/CreativeTabs;)V
        //   245: aload           4
        //   247: getstatic       net/minecraft/creativetab/CreativeTabs.tabInventory:Lnet/minecraft/creativetab/CreativeTabs;
        //   250: if_acmpne       302
        //   253: aload_0        
        //   254: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiLeft:I
        //   257: bipush          43
        //   259: iadd           
        //   260: aload_0        
        //   261: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiTop:I
        //   264: bipush          45
        //   266: iadd           
        //   267: bipush          20
        //   269: aload_0        
        //   270: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiLeft:I
        //   273: bipush          43
        //   275: iadd           
        //   276: iload_2        
        //   277: isub           
        //   278: i2f            
        //   279: aload_0        
        //   280: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiTop:I
        //   283: bipush          45
        //   285: iadd           
        //   286: bipush          30
        //   288: isub           
        //   289: iload_3        
        //   290: isub           
        //   291: i2f            
        //   292: aload_0        
        //   293: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.mc:Lnet/minecraft/client/Minecraft;
        //   296: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //   299: invokestatic    net/minecraft/client/gui/inventory/GuiInventory.drawEntityOnScreen:(IIIFFLnet/minecraft/entity/EntityLivingBase;)V
        //   302: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void updateActivePotionEffects() {
        final int guiLeft = this.guiLeft;
        super.updateActivePotionEffects();
        if (this.searchField != null && this.guiLeft != guiLeft) {
            this.searchField.xPosition = this.guiLeft + 82;
        }
    }
    
    protected void func_147051_a(final CreativeTabs creativeTabs) {
        final boolean b = creativeTabs.getTabIndex() == GuiContainerCreative.selectedTabIndex;
        final boolean tabInFirstRow = creativeTabs.isTabInFirstRow();
        final int tabColumn = creativeTabs.getTabColumn();
        final int n = tabColumn * 28;
        int n2 = this.guiLeft + 28 * tabColumn;
        final int guiTop = this.guiTop;
        int n3 = 0;
        if (b) {
            n3 += 32;
        }
        if (tabColumn == 5) {
            n2 = this.guiLeft + this.xSize - 28;
        }
        else if (tabColumn > 0) {
            n2 += tabColumn;
        }
        int n4;
        if (tabInFirstRow) {
            n4 = guiTop - 28;
        }
        else {
            n3 += 64;
            n4 = guiTop + (this.ySize - 4);
        }
        this.drawTexturedModalRect(n2, n4, n, 0, 28, 32);
        this.zLevel = 100.0f;
        this.itemRender.zLevel = 100.0f;
        final int n5 = n2 + 6;
        final int n6 = n4 + 8 + (tabInFirstRow ? 1 : -1);
        final ItemStack iconItemStack = creativeTabs.getIconItemStack();
        this.itemRender.renderItemAndEffectIntoGUI(iconItemStack, n5, n6);
        this.itemRender.renderItemOverlays(this.fontRendererObj, iconItemStack, n5, n6);
        this.itemRender.zLevel = 0.0f;
        this.zLevel = 0.0f;
    }
    
    @Override
    public void drawScreen(final int p0, final int p1, final float p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //     4: istore          4
        //     6: aload_0        
        //     7: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiLeft:I
        //    10: istore          5
        //    12: aload_0        
        //    13: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.guiTop:I
        //    16: istore          6
        //    18: iload           5
        //    20: sipush          175
        //    23: iadd           
        //    24: istore          7
        //    26: iload           6
        //    28: bipush          18
        //    30: iadd           
        //    31: istore          8
        //    33: iload           7
        //    35: bipush          14
        //    37: iadd           
        //    38: istore          9
        //    40: iload           8
        //    42: bipush          112
        //    44: iadd           
        //    45: istore          10
        //    47: aload_0        
        //    48: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.wasClicking:Z
        //    51: ifne            91
        //    54: iload           4
        //    56: ifeq            91
        //    59: iload_1        
        //    60: iload           7
        //    62: if_icmplt       91
        //    65: iload_2        
        //    66: iload           8
        //    68: if_icmplt       91
        //    71: iload_1        
        //    72: iload           9
        //    74: if_icmpge       91
        //    77: iload_2        
        //    78: iload           10
        //    80: if_icmpge       91
        //    83: aload_0        
        //    84: aload_0        
        //    85: invokespecial   net/minecraft/client/gui/inventory/GuiContainerCreative.needsScrollBars:()Z
        //    88: putfield        net/minecraft/client/gui/inventory/GuiContainerCreative.isScrolling:Z
        //    91: iload           4
        //    93: ifne            101
        //    96: aload_0        
        //    97: iconst_0       
        //    98: putfield        net/minecraft/client/gui/inventory/GuiContainerCreative.isScrolling:Z
        //   101: aload_0        
        //   102: iload           4
        //   104: putfield        net/minecraft/client/gui/inventory/GuiContainerCreative.wasClicking:Z
        //   107: aload_0        
        //   108: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.isScrolling:Z
        //   111: ifeq            163
        //   114: aload_0        
        //   115: iload_2        
        //   116: iload           8
        //   118: isub           
        //   119: i2f            
        //   120: ldc             7.5
        //   122: fsub           
        //   123: iload           10
        //   125: iload           8
        //   127: isub           
        //   128: i2f            
        //   129: ldc             15.0
        //   131: fsub           
        //   132: fdiv           
        //   133: putfield        net/minecraft/client/gui/inventory/GuiContainerCreative.currentScroll:F
        //   136: aload_0        
        //   137: aload_0        
        //   138: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.currentScroll:F
        //   141: fconst_0       
        //   142: fconst_1       
        //   143: invokestatic    net/minecraft/util/MathHelper.clamp_float:(FFF)F
        //   146: putfield        net/minecraft/client/gui/inventory/GuiContainerCreative.currentScroll:F
        //   149: aload_0        
        //   150: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.inventorySlots:Lnet/minecraft/inventory/Container;
        //   153: checkcast       Lnet/minecraft/client/gui/inventory/GuiContainerCreative$ContainerCreative;
        //   156: aload_0        
        //   157: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.currentScroll:F
        //   160: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative$ContainerCreative.scrollTo:(F)V
        //   163: aload_0        
        //   164: iload_1        
        //   165: iload_2        
        //   166: fload_3        
        //   167: invokespecial   net/minecraft/client/renderer/InventoryEffectRenderer.drawScreen:(IIF)V
        //   170: getstatic       net/minecraft/creativetab/CreativeTabs.creativeTabArray:[Lnet/minecraft/creativetab/CreativeTabs;
        //   173: astore          11
        //   175: aload           11
        //   177: arraylength    
        //   178: istore          12
        //   180: iconst_0       
        //   181: iload           12
        //   183: if_icmpge       209
        //   186: aload           11
        //   188: iconst_0       
        //   189: aaload         
        //   190: astore          14
        //   192: aload_0        
        //   193: aload           14
        //   195: iload_1        
        //   196: iload_2        
        //   197: if_icmpne       203
        //   200: goto            209
        //   203: iinc            13, 1
        //   206: goto            180
        //   209: aload_0        
        //   210: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.field_147064_C:Lnet/minecraft/inventory/Slot;
        //   213: ifnull          270
        //   216: getstatic       net/minecraft/client/gui/inventory/GuiContainerCreative.selectedTabIndex:I
        //   219: getstatic       net/minecraft/creativetab/CreativeTabs.tabInventory:Lnet/minecraft/creativetab/CreativeTabs;
        //   222: invokevirtual   net/minecraft/creativetab/CreativeTabs.getTabIndex:()I
        //   225: if_icmpne       270
        //   228: aload_0        
        //   229: aload_0        
        //   230: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.field_147064_C:Lnet/minecraft/inventory/Slot;
        //   233: getfield        net/minecraft/inventory/Slot.xDisplayPosition:I
        //   236: aload_0        
        //   237: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.field_147064_C:Lnet/minecraft/inventory/Slot;
        //   240: getfield        net/minecraft/inventory/Slot.yDisplayPosition:I
        //   243: bipush          16
        //   245: bipush          16
        //   247: iload_1        
        //   248: iload_2        
        //   249: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative.isPointInRegion:(IIIIII)Z
        //   252: ifeq            270
        //   255: aload_0        
        //   256: ldc             "inventory.binSlot"
        //   258: iconst_0       
        //   259: anewarray       Ljava/lang/Object;
        //   262: invokestatic    net/minecraft/client/resources/I18n.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   265: iload_1        
        //   266: iload_2        
        //   267: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative.drawCreativeTabHoveringText:(Ljava/lang/String;II)V
        //   270: fconst_1       
        //   271: fconst_1       
        //   272: fconst_1       
        //   273: fconst_1       
        //   274: invokestatic    net/minecraft/client/renderer/GlStateManager.color:(FFFF)V
        //   277: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0180 (coming from #0206).
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
    
    @Override
    protected void renderToolTip(final ItemStack p0, final int p1, final int p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: getstatic       net/minecraft/creativetab/CreativeTabs.tabAllSearch:Lnet/minecraft/creativetab/CreativeTabs;
        //     6: invokevirtual   net/minecraft/creativetab/CreativeTabs.getTabIndex:()I
        //     9: if_icmpne       321
        //    12: aload_1        
        //    13: aload_0        
        //    14: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.mc:Lnet/minecraft/client/Minecraft;
        //    17: getfield        net/minecraft/client/Minecraft.thePlayer:Lnet/minecraft/client/entity/EntityPlayerSP;
        //    20: aload_0        
        //    21: getfield        net/minecraft/client/gui/inventory/GuiContainerCreative.mc:Lnet/minecraft/client/Minecraft;
        //    24: getfield        net/minecraft/client/Minecraft.gameSettings:Lnet/minecraft/client/settings/GameSettings;
        //    27: getfield        net/minecraft/client/settings/GameSettings.advancedItemTooltips:Z
        //    30: invokevirtual   net/minecraft/item/ItemStack.getTooltip:(Lnet/minecraft/entity/player/EntityPlayer;Z)Ljava/util/List;
        //    33: astore          4
        //    35: aload_1        
        //    36: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    39: invokevirtual   net/minecraft/item/Item.getCreativeTab:()Lnet/minecraft/creativetab/CreativeTabs;
        //    42: astore          5
        //    44: aload           5
        //    46: ifnonnull       152
        //    49: aload_1        
        //    50: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //    53: getstatic       net/minecraft/init/Items.enchanted_book:Lnet/minecraft/item/ItemEnchantedBook;
        //    56: if_acmpne       152
        //    59: aload_1        
        //    60: invokestatic    net/minecraft/enchantment/EnchantmentHelper.getEnchantments:(Lnet/minecraft/item/ItemStack;)Ljava/util/Map;
        //    63: astore          6
        //    65: aload           6
        //    67: invokeinterface java/util/Map.size:()I
        //    72: iconst_1       
        //    73: if_icmpne       152
        //    76: aload           6
        //    78: invokeinterface java/util/Map.keySet:()Ljava/util/Set;
        //    83: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //    88: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    93: checkcast       Ljava/lang/Integer;
        //    96: invokevirtual   java/lang/Integer.intValue:()I
        //    99: invokestatic    net/minecraft/enchantment/Enchantment.getEnchantmentById:(I)Lnet/minecraft/enchantment/Enchantment;
        //   102: astore          7
        //   104: getstatic       net/minecraft/creativetab/CreativeTabs.creativeTabArray:[Lnet/minecraft/creativetab/CreativeTabs;
        //   107: astore          8
        //   109: aload           8
        //   111: arraylength    
        //   112: istore          9
        //   114: iconst_0       
        //   115: iload           9
        //   117: if_icmpge       152
        //   120: aload           8
        //   122: iconst_0       
        //   123: aaload         
        //   124: astore          11
        //   126: aload           11
        //   128: aload           7
        //   130: getfield        net/minecraft/enchantment/Enchantment.type:Lnet/minecraft/enchantment/EnumEnchantmentType;
        //   133: invokevirtual   net/minecraft/creativetab/CreativeTabs.hasRelevantEnchantmentType:(Lnet/minecraft/enchantment/EnumEnchantmentType;)Z
        //   136: ifeq            146
        //   139: aload           11
        //   141: astore          5
        //   143: goto            152
        //   146: iinc            10, 1
        //   149: goto            114
        //   152: aload           5
        //   154: ifnull          208
        //   157: aload           4
        //   159: iconst_1       
        //   160: new             Ljava/lang/StringBuilder;
        //   163: dup            
        //   164: invokespecial   java/lang/StringBuilder.<init>:()V
        //   167: ldc_w           ""
        //   170: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   173: getstatic       net/minecraft/util/EnumChatFormatting.BOLD:Lnet/minecraft/util/EnumChatFormatting;
        //   176: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   179: getstatic       net/minecraft/util/EnumChatFormatting.BLUE:Lnet/minecraft/util/EnumChatFormatting;
        //   182: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   185: aload           5
        //   187: invokevirtual   net/minecraft/creativetab/CreativeTabs.getTranslatedTabLabel:()Ljava/lang/String;
        //   190: iconst_0       
        //   191: anewarray       Ljava/lang/Object;
        //   194: invokestatic    net/minecraft/client/resources/I18n.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   197: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   200: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   203: invokeinterface java/util/List.add:(ILjava/lang/Object;)V
        //   208: iconst_0       
        //   209: aload           4
        //   211: invokeinterface java/util/List.size:()I
        //   216: if_icmpge       310
        //   219: aload           4
        //   221: iconst_0       
        //   222: new             Ljava/lang/StringBuilder;
        //   225: dup            
        //   226: invokespecial   java/lang/StringBuilder.<init>:()V
        //   229: aload_1        
        //   230: invokevirtual   net/minecraft/item/ItemStack.getRarity:()Lnet/minecraft/item/EnumRarity;
        //   233: getfield        net/minecraft/item/EnumRarity.rarityColor:Lnet/minecraft/util/EnumChatFormatting;
        //   236: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   239: aload           4
        //   241: iconst_0       
        //   242: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   247: checkcast       Ljava/lang/String;
        //   250: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   253: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   256: invokeinterface java/util/List.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //   261: pop            
        //   262: goto            304
        //   265: aload           4
        //   267: iconst_0       
        //   268: new             Ljava/lang/StringBuilder;
        //   271: dup            
        //   272: invokespecial   java/lang/StringBuilder.<init>:()V
        //   275: getstatic       net/minecraft/util/EnumChatFormatting.GRAY:Lnet/minecraft/util/EnumChatFormatting;
        //   278: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   281: aload           4
        //   283: iconst_0       
        //   284: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   289: checkcast       Ljava/lang/String;
        //   292: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   295: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   298: invokeinterface java/util/List.set:(ILjava/lang/Object;)Ljava/lang/Object;
        //   303: pop            
        //   304: iinc            6, 1
        //   307: goto            208
        //   310: aload_0        
        //   311: aload           4
        //   313: iload_2        
        //   314: iload_3        
        //   315: invokevirtual   net/minecraft/client/gui/inventory/GuiContainerCreative.drawHoveringText:(Ljava/util/List;II)V
        //   318: goto            328
        //   321: aload_0        
        //   322: aload_1        
        //   323: iload_2        
        //   324: iload_3        
        //   325: invokespecial   net/minecraft/client/renderer/InventoryEffectRenderer.renderToolTip:(Lnet/minecraft/item/ItemStack;II)V
        //   328: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
        }
        if (guiButton.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
        }
    }
    
    static {
        creativeInventoryTabs = new ResourceLocation("textures/gui/container/creative_inventory/tabs.png");
        GuiContainerCreative.field_147060_v = new InventoryBasic("tmp", true, 45);
        GuiContainerCreative.selectedTabIndex = CreativeTabs.tabBlock.getTabIndex();
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(final int n, final int n2) {
        final CreativeTabs creativeTabs = CreativeTabs.creativeTabArray[GuiContainerCreative.selectedTabIndex];
        if (creativeTabs.drawInForegroundOfTab()) {
            this.fontRendererObj.drawString(I18n.format(creativeTabs.getTranslatedTabLabel(), new Object[0]), 8.0, 6.0, 4210752);
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        Mouse.getEventDWheel();
    }
    
    private void setCurrentCreativeTab(final CreativeTabs creativeTabs) {
        final int selectedTabIndex = GuiContainerCreative.selectedTabIndex;
        GuiContainerCreative.selectedTabIndex = creativeTabs.getTabIndex();
        final ContainerCreative containerCreative = (ContainerCreative)this.inventorySlots;
        this.dragSplittingSlots.clear();
        containerCreative.itemList.clear();
        creativeTabs.displayAllReleventItems(containerCreative.itemList);
        if (creativeTabs == CreativeTabs.tabInventory) {
            final Container inventoryContainer = this.mc.thePlayer.inventoryContainer;
            if (this.field_147063_B == null) {
                this.field_147063_B = containerCreative.inventorySlots;
            }
            containerCreative.inventorySlots = Lists.newArrayList();
            while (0 < inventoryContainer.inventorySlots.size()) {
                final CreativeSlot creativeSlot = new CreativeSlot(inventoryContainer.inventorySlots.get(0), 0);
                containerCreative.inventorySlots.add(creativeSlot);
                creativeSlot.yDisplayPosition = -2000;
                creativeSlot.xDisplayPosition = -2000;
                int n = 0;
                ++n;
            }
            this.field_147064_C = new Slot(GuiContainerCreative.field_147060_v, 0, 173, 112);
            containerCreative.inventorySlots.add(this.field_147064_C);
        }
        else if (selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
            containerCreative.inventorySlots = this.field_147063_B;
            this.field_147063_B = null;
        }
        if (this.searchField != null) {
            if (creativeTabs == CreativeTabs.tabAllSearch) {
                this.searchField.setVisible(true);
                this.searchField.setCanLoseFocus(false);
                this.searchField.setFocused(true);
                this.searchField.setText("");
                this.updateCreativeSearch();
            }
            else {
                this.searchField.setVisible(false);
                this.searchField.setCanLoseFocus(true);
                this.searchField.setFocused(false);
            }
        }
        containerCreative.scrollTo(this.currentScroll = 0.0f);
    }
    
    @Override
    protected void handleMouseClick(final Slot slot, final int n, final int n2, int n3) {
        this.field_147057_D = true;
        final boolean b = n3 == 1;
        n3 = ((n == -999 && n3 == 0) ? 4 : n3);
        if (slot == null && GuiContainerCreative.selectedTabIndex != CreativeTabs.tabInventory.getTabIndex() && n3 != 5) {
            final InventoryPlayer inventory = this.mc.thePlayer.inventory;
            if (inventory.getItemStack() != null) {
                if (n2 == 0) {
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack(), true);
                    this.mc.playerController.sendPacketDropItem(inventory.getItemStack());
                    inventory.setItemStack(null);
                }
                if (n2 == 1) {
                    final ItemStack splitStack = inventory.getItemStack().splitStack(1);
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(splitStack, true);
                    this.mc.playerController.sendPacketDropItem(splitStack);
                    if (inventory.getItemStack().stackSize == 0) {
                        inventory.setItemStack(null);
                    }
                }
            }
        }
        else if (slot == this.field_147064_C && b) {
            while (0 < this.mc.thePlayer.inventoryContainer.getInventory().size()) {
                this.mc.playerController.sendSlotPacket(null, 0);
                int n4 = 0;
                ++n4;
            }
        }
        else if (GuiContainerCreative.selectedTabIndex == CreativeTabs.tabInventory.getTabIndex()) {
            if (slot == this.field_147064_C) {
                this.mc.thePlayer.inventory.setItemStack(null);
            }
            else if (n3 == 4 && slot != null && slot.getHasStack()) {
                final ItemStack decrStackSize = slot.decrStackSize((n2 == 0) ? 1 : slot.getStack().getMaxStackSize());
                this.mc.thePlayer.dropPlayerItemWithRandomChoice(decrStackSize, true);
                this.mc.playerController.sendPacketDropItem(decrStackSize);
            }
            else if (n3 == 4 && this.mc.thePlayer.inventory.getItemStack() != null) {
                this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.getItemStack(), true);
                this.mc.playerController.sendPacketDropItem(this.mc.thePlayer.inventory.getItemStack());
                this.mc.thePlayer.inventory.setItemStack(null);
            }
            else {
                this.mc.thePlayer.inventoryContainer.slotClick((slot == null) ? n : CreativeSlot.access$000((CreativeSlot)slot).slotNumber, n2, n3, this.mc.thePlayer);
                this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
            }
        }
        else if (n3 != 5 && slot.inventory == GuiContainerCreative.field_147060_v) {
            final InventoryPlayer inventory2 = this.mc.thePlayer.inventory;
            final ItemStack itemStack = inventory2.getItemStack();
            final ItemStack stack = slot.getStack();
            if (n3 == 2) {
                if (stack != null && n2 >= 0 && n2 < 9) {
                    final ItemStack copy = stack.copy();
                    copy.stackSize = copy.getMaxStackSize();
                    this.mc.thePlayer.inventory.setInventorySlotContents(n2, copy);
                    this.mc.thePlayer.inventoryContainer.detectAndSendChanges();
                }
                return;
            }
            if (n3 == 3) {
                if (inventory2.getItemStack() == null && slot.getHasStack()) {
                    final ItemStack copy2 = slot.getStack().copy();
                    copy2.stackSize = copy2.getMaxStackSize();
                    inventory2.setItemStack(copy2);
                }
                return;
            }
            if (n3 == 4) {
                if (stack != null) {
                    final ItemStack copy3 = stack.copy();
                    copy3.stackSize = ((n2 == 0) ? 1 : copy3.getMaxStackSize());
                    this.mc.thePlayer.dropPlayerItemWithRandomChoice(copy3, true);
                    this.mc.playerController.sendPacketDropItem(copy3);
                }
                return;
            }
            if (itemStack != null && stack != null && itemStack.isItemEqual(stack)) {
                if (n2 == 0) {
                    if (b) {
                        itemStack.stackSize = itemStack.getMaxStackSize();
                    }
                    else if (itemStack.stackSize < itemStack.getMaxStackSize()) {
                        final ItemStack itemStack2 = itemStack;
                        ++itemStack2.stackSize;
                    }
                }
                else if (itemStack.stackSize <= 1) {
                    inventory2.setItemStack(null);
                }
                else {
                    final ItemStack itemStack3 = itemStack;
                    --itemStack3.stackSize;
                }
            }
            else if (stack != null && itemStack == null) {
                inventory2.setItemStack(ItemStack.copyItemStack(stack));
                final ItemStack itemStack4 = inventory2.getItemStack();
                if (b) {
                    itemStack4.stackSize = itemStack4.getMaxStackSize();
                }
            }
            else {
                inventory2.setItemStack(null);
            }
        }
        else {
            this.inventorySlots.slotClick((slot == null) ? n : slot.slotNumber, n2, n3, this.mc.thePlayer);
            if (Container.getDragEvent(n2) == 2) {
                while (true) {
                    this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(45).getStack(), 36);
                    int n4 = 0;
                    ++n4;
                }
            }
            else if (slot != null) {
                this.mc.playerController.sendSlotPacket(this.inventorySlots.getSlot(slot.slotNumber).getStack(), slot.slotNumber - this.inventorySlots.inventorySlots.size() + 9 + 36);
            }
        }
    }
    
    public GuiContainerCreative(final EntityPlayer entityPlayer) {
        super(new ContainerCreative(entityPlayer));
        entityPlayer.openContainer = this.inventorySlots;
        this.allowUserInput = true;
        this.ySize = 136;
        this.xSize = 195;
    }
    
    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.mc.thePlayer != null && this.mc.thePlayer.inventory != null) {
            this.mc.thePlayer.inventoryContainer.removeCraftingFromCrafters(this.field_147059_E);
        }
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void keyTyped(final char c, final int n) throws IOException {
        if (GuiContainerCreative.selectedTabIndex != CreativeTabs.tabAllSearch.getTabIndex()) {
            if (GameSettings.isKeyDown(this.mc.gameSettings.keyBindChat)) {
                this.setCurrentCreativeTab(CreativeTabs.tabAllSearch);
            }
            else {
                super.keyTyped(c, n);
            }
        }
        else {
            if (this.field_147057_D) {
                this.field_147057_D = false;
                this.searchField.setText("");
            }
            if (!this.checkHotbarKeys(n)) {
                if (this.searchField.textboxKeyTyped(c, n)) {
                    this.updateCreativeSearch();
                }
                else {
                    super.keyTyped(c, n);
                }
            }
        }
    }
    
    @Override
    public void initGui() {
        if (this.mc.playerController.isInCreativeMode()) {
            super.initGui();
            this.buttonList.clear();
            Keyboard.enableRepeatEvents(true);
            (this.searchField = new GuiTextField(0, this.fontRendererObj, this.guiLeft + 82, this.guiTop + 6, 89, this.fontRendererObj.FONT_HEIGHT)).setMaxStringLength(15);
            this.searchField.setEnableBackgroundDrawing(false);
            this.searchField.setVisible(false);
            this.searchField.setTextColor(16777215);
            final int selectedTabIndex = GuiContainerCreative.selectedTabIndex;
            GuiContainerCreative.selectedTabIndex = -1;
            this.setCurrentCreativeTab(CreativeTabs.creativeTabArray[selectedTabIndex]);
            this.field_147059_E = new CreativeCrafting(this.mc);
            this.mc.thePlayer.inventoryContainer.onCraftGuiOpened(this.field_147059_E);
        }
        else {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
    }
    
    static InventoryBasic access$100() {
        return GuiContainerCreative.field_147060_v;
    }
    
    @Override
    public void updateScreen() {
        if (!this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiInventory(this.mc.thePlayer));
        }
        this.updateActivePotionEffects();
    }
    
    private void updateCreativeSearch() {
        final ContainerCreative containerCreative = (ContainerCreative)this.inventorySlots;
        containerCreative.itemList.clear();
        for (final Item item : Item.itemRegistry) {
            if (item != null && item.getCreativeTab() != null) {
                item.getSubItems(item, null, containerCreative.itemList);
            }
        }
        final Enchantment[] enchantmentsBookList = Enchantment.enchantmentsBookList;
        while (0 < enchantmentsBookList.length) {
            final Enchantment enchantment = enchantmentsBookList[0];
            if (enchantment != null && enchantment.type != null) {
                Items.enchanted_book.getAll(enchantment, containerCreative.itemList);
            }
            int n = 0;
            ++n;
        }
        final Iterator iterator2 = containerCreative.itemList.iterator();
        final String lowerCase = this.searchField.getText().toLowerCase();
        while (iterator2.hasNext()) {
            final Iterator iterator3 = iterator2.next().getTooltip(this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips).iterator();
            while (iterator3.hasNext()) {
                if (EnumChatFormatting.getTextWithoutFormattingCodes(iterator3.next()).toLowerCase().contains(lowerCase)) {
                    break;
                }
            }
        }
        containerCreative.scrollTo(this.currentScroll = 0.0f);
    }
    
    static class ContainerCreative extends Container
    {
        public List itemList;
        
        public void scrollTo(final float n) {
            final int n2 = (int)(n * ((this.itemList.size() + 9 - 1) / 9 - 5) + 0.5);
            while (true) {
                if (0 < this.itemList.size()) {
                    GuiContainerCreative.access$100().setInventorySlotContents(0, this.itemList.get(0));
                }
                else {
                    GuiContainerCreative.access$100().setInventorySlotContents(0, null);
                }
                int n3 = 0;
                ++n3;
            }
        }
        
        @Override
        public boolean canDragIntoSlot(final Slot slot) {
            return slot.inventory instanceof InventoryPlayer || (slot.yDisplayPosition > 90 && slot.xDisplayPosition <= 162);
        }
        
        @Override
        public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
            if (n >= this.inventorySlots.size() - 9 && n < this.inventorySlots.size()) {
                final Slot slot = this.inventorySlots.get(n);
                if (slot != null && slot.getHasStack()) {
                    slot.putStack(null);
                }
            }
            return null;
        }
        
        public ContainerCreative(final EntityPlayer entityPlayer) {
            this.itemList = Lists.newArrayList();
            final InventoryPlayer inventory = entityPlayer.inventory;
            while (true) {
                this.addSlotToContainer(new Slot(GuiContainerCreative.access$100(), 0, 9, 18));
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
            return slot.yDisplayPosition > 90;
        }
        
        @Override
        public boolean canInteractWith(final EntityPlayer entityPlayer) {
            return true;
        }
        
        public boolean func_148328_e() {
            return this.itemList.size() > 45;
        }
        
        @Override
        protected void retrySlotClick(final int n, final int n2, final boolean b, final EntityPlayer entityPlayer) {
        }
    }
    
    class CreativeSlot extends Slot
    {
        final GuiContainerCreative this$0;
        private final Slot slot;
        
        @Override
        public int getItemStackLimit(final ItemStack itemStack) {
            return this.slot.getItemStackLimit(itemStack);
        }
        
        @Override
        public ItemStack decrStackSize(final int n) {
            return this.slot.decrStackSize(n);
        }
        
        @Override
        public void putStack(final ItemStack itemStack) {
            this.slot.putStack(itemStack);
        }
        
        @Override
        public String getSlotTexture() {
            return this.slot.getSlotTexture();
        }
        
        @Override
        public boolean getHasStack() {
            return this.slot.getHasStack();
        }
        
        @Override
        public ItemStack getStack() {
            return this.slot.getStack();
        }
        
        @Override
        public boolean isHere(final IInventory inventory, final int n) {
            return this.slot.isHere(inventory, n);
        }
        
        @Override
        public boolean isItemValid(final ItemStack itemStack) {
            return this.slot.isItemValid(itemStack);
        }
        
        static Slot access$000(final CreativeSlot creativeSlot) {
            return creativeSlot.slot;
        }
        
        @Override
        public int getSlotStackLimit() {
            return this.slot.getSlotStackLimit();
        }
        
        public CreativeSlot(final GuiContainerCreative this$0, final Slot slot, final int n) {
            this.this$0 = this$0;
            super(slot.inventory, n, 0, 0);
            this.slot = slot;
        }
        
        @Override
        public void onSlotChanged() {
            this.slot.onSlotChanged();
        }
        
        @Override
        public void onPickupFromSlot(final EntityPlayer entityPlayer, final ItemStack itemStack) {
            this.slot.onPickupFromSlot(entityPlayer, itemStack);
        }
    }
}
