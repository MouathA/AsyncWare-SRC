package ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.sub;

import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.settings.*;
import ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.ok.clickgui.component.components.*;
import java.math.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class Slider extends Component
{
    private boolean hovered;
    private int x;
    private int y;
    private Setting set;
    private boolean dragging;
    private Button parent;
    private double renderWidth;
    private int offset;
    
    @Override
    public void updateComponent(final int p0, final int p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_0        
        //     2: iload_1        
        //     3: iload_2        
        //     4: if_icmple       13
        //     7: aload_0        
        //     8: iload_1        
        //     9: iload_2        
        //    10: if_icmple       17
        //    13: iconst_1       
        //    14: goto            18
        //    17: iconst_0       
        //    18: putfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.hovered:Z
        //    21: aload_0        
        //    22: aload_0        
        //    23: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.parent:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/Button;
        //    26: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/Button.parent:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/Frame;
        //    29: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/Frame.getY:()I
        //    32: aload_0        
        //    33: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.offset:I
        //    36: iadd           
        //    37: putfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.y:I
        //    40: aload_0        
        //    41: aload_0        
        //    42: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.parent:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/Button;
        //    45: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/Button.parent:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/Frame;
        //    48: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/Frame.getX:()I
        //    51: putfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.x:I
        //    54: bipush          88
        //    56: iconst_0       
        //    57: iload_1        
        //    58: aload_0        
        //    59: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.x:I
        //    62: isub           
        //    63: invokestatic    java/lang/Math.max:(II)I
        //    66: invokestatic    java/lang/Math.min:(II)I
        //    69: i2d            
        //    70: dstore_3       
        //    71: aload_0        
        //    72: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.set:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //    75: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getMin:()D
        //    78: dstore          5
        //    80: aload_0        
        //    81: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.set:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //    84: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getMax:()D
        //    87: dstore          7
        //    89: aload_0        
        //    90: ldc2_w          88.0
        //    93: aload_0        
        //    94: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.set:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //    97: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getValDouble:()D
        //   100: dload           5
        //   102: dsub           
        //   103: dmul           
        //   104: dload           7
        //   106: dload           5
        //   108: dsub           
        //   109: ddiv           
        //   110: putfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.renderWidth:D
        //   113: aload_0        
        //   114: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.dragging:Z
        //   117: ifeq            172
        //   120: dload_3        
        //   121: dconst_0       
        //   122: dcmpl          
        //   123: ifne            143
        //   126: aload_0        
        //   127: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.set:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   130: aload_0        
        //   131: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.set:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   134: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.getMin:()D
        //   137: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.setValDouble:(D)V
        //   140: goto            172
        //   143: dload_3        
        //   144: ldc2_w          88.0
        //   147: ddiv           
        //   148: dload           7
        //   150: dload           5
        //   152: dsub           
        //   153: dmul           
        //   154: dload           5
        //   156: dadd           
        //   157: iconst_2       
        //   158: invokestatic    ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.roundToPlace:(DI)D
        //   161: dstore          9
        //   163: aload_0        
        //   164: getfield        ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/clickgui/component/components/sub/Slider.set:Lok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting;
        //   167: dload           9
        //   169: invokevirtual   ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/ok/settings/Setting.setValDouble:(D)V
        //   172: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0013 (coming from #0010).
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
    
    public Slider(final Setting set, final Button parent, final int offset) {
        this.dragging = false;
        this.set = set;
        this.parent = parent;
        this.x = parent.parent.getX() + parent.parent.getWidth();
        this.y = parent.parent.getY() + parent.offset;
        this.offset = offset;
    }
    
    @Override
    public void mouseClicked(final int n, final int n2, final int n3) {
        if (n > n2 && n3 == 0 && this.parent.open) {
            this.dragging = true;
        }
        if (n > n2 && n3 == 0 && this.parent.open) {
            this.dragging = true;
        }
    }
    
    @Override
    public void setOff(final int offset) {
        this.offset = offset;
    }
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3) {
        this.dragging = false;
    }
    
    private static double roundToPlace(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
        final int n = (int)(this.set.getValDouble() / this.set.getMax() * this.parent.parent.getWidth());
        Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.renderWidth, this.parent.parent.getY() + this.offset + 12, this.hovered ? -11184811 : -12303292);
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.set.getName() + ": " + this.set.getValDouble(), (float)(this.parent.parent.getX() * 2 + 15), (float)((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
        GL11.glPopMatrix();
    }
}
