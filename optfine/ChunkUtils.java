package optfine;

import java.lang.reflect.*;
import net.minecraft.world.chunk.*;

public class ChunkUtils
{
    private static Field fieldHasEntities;
    private static boolean fieldHasEntitiesMissing;
    
    static {
        ChunkUtils.fieldHasEntities = null;
        ChunkUtils.fieldHasEntitiesMissing = false;
    }
    
    private static Field fildFieldHasEntities(final Chunk p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: invokespecial   java/util/ArrayList.<init>:()V
        //     7: astore_1       
        //     8: new             Ljava/util/ArrayList;
        //    11: dup            
        //    12: invokespecial   java/util/ArrayList.<init>:()V
        //    15: astore_2       
        //    16: ldc             Lnet/minecraft/world/chunk/Chunk;.class
        //    18: invokevirtual   java/lang/Class.getDeclaredFields:()[Ljava/lang/reflect/Field;
        //    21: astore_3       
        //    22: iconst_0       
        //    23: aload_3        
        //    24: arraylength    
        //    25: if_icmpge       78
        //    28: aload_3        
        //    29: iconst_0       
        //    30: aaload         
        //    31: astore          5
        //    33: aload           5
        //    35: invokevirtual   java/lang/reflect/Field.getType:()Ljava/lang/Class;
        //    38: getstatic       java/lang/Boolean.TYPE:Ljava/lang/Class;
        //    41: if_acmpne       72
        //    44: aload           5
        //    46: iconst_1       
        //    47: invokevirtual   java/lang/reflect/Field.setAccessible:(Z)V
        //    50: aload_1        
        //    51: aload           5
        //    53: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    58: pop            
        //    59: aload_2        
        //    60: aload           5
        //    62: aload_0        
        //    63: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //    66: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //    71: pop            
        //    72: iinc            4, 1
        //    75: goto            22
        //    78: aload_0        
        //    79: iconst_0       
        //    80: invokevirtual   net/minecraft/world/chunk/Chunk.setHasEntities:(Z)V
        //    83: new             Ljava/util/ArrayList;
        //    86: dup            
        //    87: invokespecial   java/util/ArrayList.<init>:()V
        //    90: astore          4
        //    92: aload_1        
        //    93: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    98: astore          5
        //   100: aload           5
        //   102: invokeinterface java/util/Iterator.hasNext:()Z
        //   107: ifeq            139
        //   110: aload           5
        //   112: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   117: astore          6
        //   119: aload           4
        //   121: aload           6
        //   123: checkcast       Ljava/lang/reflect/Field;
        //   126: aload_0        
        //   127: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   130: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   135: pop            
        //   136: goto            100
        //   139: aload_0        
        //   140: iconst_1       
        //   141: invokevirtual   net/minecraft/world/chunk/Chunk.setHasEntities:(Z)V
        //   144: new             Ljava/util/ArrayList;
        //   147: dup            
        //   148: invokespecial   java/util/ArrayList.<init>:()V
        //   151: astore          5
        //   153: aload_1        
        //   154: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   159: astore          6
        //   161: aload           6
        //   163: invokeinterface java/util/Iterator.hasNext:()Z
        //   168: ifeq            200
        //   171: aload           6
        //   173: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   178: astore          7
        //   180: aload           5
        //   182: aload           7
        //   184: checkcast       Ljava/lang/reflect/Field;
        //   187: aload_0        
        //   188: invokevirtual   java/lang/reflect/Field.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   191: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   196: pop            
        //   197: goto            161
        //   200: new             Ljava/util/ArrayList;
        //   203: dup            
        //   204: invokespecial   java/util/ArrayList.<init>:()V
        //   207: astore          6
        //   209: iconst_0       
        //   210: aload_1        
        //   211: invokeinterface java/util/List.size:()I
        //   216: if_icmpge       309
        //   219: aload_1        
        //   220: iconst_0       
        //   221: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   226: checkcast       Ljava/lang/reflect/Field;
        //   229: astore          8
        //   231: aload           4
        //   233: iconst_0       
        //   234: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   239: checkcast       Ljava/lang/Boolean;
        //   242: astore          9
        //   244: aload           5
        //   246: iconst_0       
        //   247: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   252: checkcast       Ljava/lang/Boolean;
        //   255: astore          10
        //   257: aload           9
        //   259: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   262: ifne            303
        //   265: aload           10
        //   267: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   270: ifeq            303
        //   273: aload           6
        //   275: aload           8
        //   277: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   282: pop            
        //   283: aload_2        
        //   284: iconst_0       
        //   285: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   290: checkcast       Ljava/lang/Boolean;
        //   293: astore          11
        //   295: aload           8
        //   297: aload_0        
        //   298: aload           11
        //   300: invokevirtual   java/lang/reflect/Field.set:(Ljava/lang/Object;Ljava/lang/Object;)V
        //   303: iinc            7, 1
        //   306: goto            209
        //   309: aload           6
        //   311: invokeinterface java/util/List.size:()I
        //   316: iconst_1       
        //   317: if_icmpne       336
        //   320: aload           6
        //   322: iconst_0       
        //   323: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   328: checkcast       Ljava/lang/reflect/Field;
        //   331: astore          7
        //   333: aload           7
        //   335: areturn        
        //   336: goto            375
        //   339: astore_1       
        //   340: new             Ljava/lang/StringBuilder;
        //   343: dup            
        //   344: invokespecial   java/lang/StringBuilder.<init>:()V
        //   347: aload_1        
        //   348: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //   351: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   354: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   357: ldc             " "
        //   359: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   362: aload_1        
        //   363: invokevirtual   java/lang/Exception.getMessage:()Ljava/lang/String;
        //   366: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   369: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   372: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //   375: ldc             "Error finding Chunk.hasEntities"
        //   377: invokestatic    optfine/Config.warn:(Ljava/lang/String;)V
        //   380: aconst_null    
        //   381: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static boolean hasEntities(final Chunk chunk) {
        if (ChunkUtils.fieldHasEntities == null) {
            if (ChunkUtils.fieldHasEntitiesMissing) {
                return true;
            }
            ChunkUtils.fieldHasEntities = fildFieldHasEntities(chunk);
            if (ChunkUtils.fieldHasEntities == null) {
                return ChunkUtils.fieldHasEntitiesMissing = true;
            }
        }
        return ChunkUtils.fieldHasEntities.getBoolean(chunk);
    }
}
