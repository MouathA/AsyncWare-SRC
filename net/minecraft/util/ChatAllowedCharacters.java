package net.minecraft.util;

public class ChatAllowedCharacters
{
    public static final char[] allowedCharactersArray;
    
    public static String filterAllowedCharacters(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/lang/StringBuilder.<init>:()V
        //     7: astore_1       
        //     8: aload_0        
        //     9: invokevirtual   java/lang/String.toCharArray:()[C
        //    12: astore_2       
        //    13: aload_2        
        //    14: arraylength    
        //    15: istore_3       
        //    16: iconst_0       
        //    17: iload_3        
        //    18: if_icmpge       44
        //    21: aload_2        
        //    22: iconst_0       
        //    23: caload         
        //    24: istore          5
        //    26: iload           5
        //    28: if_icmpeq       38
        //    31: aload_1        
        //    32: iload           5
        //    34: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //    37: pop            
        //    38: iinc            4, 1
        //    41: goto            16
        //    44: aload_1        
        //    45: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    48: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.ArrayIndexOutOfBoundsException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        allowedCharactersArray = new char[] { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
    }
}
