package net.minecraft.entity.ai;

import net.minecraft.profiler.*;
import java.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;

public class EntityAITasks
{
    private int tickCount;
    private final Profiler theProfiler;
    private static final Logger logger;
    private List executingTaskEntries;
    private List taskEntries;
    private int tickRate;
    
    public void addTask(final int n, final EntityAIBase entityAIBase) {
        this.taskEntries.add(new EntityAITaskEntry(n, entityAIBase));
    }
    
    public void removeTask(final EntityAIBase entityAIBase) {
        final Iterator<EntityAITaskEntry> iterator = (Iterator<EntityAITaskEntry>)this.taskEntries.iterator();
        while (iterator.hasNext()) {
            final EntityAITaskEntry entityAITaskEntry = iterator.next();
            final EntityAIBase action = entityAITaskEntry.action;
            if (action == entityAIBase) {
                if (this.executingTaskEntries.contains(entityAITaskEntry)) {
                    action.resetTask();
                    this.executingTaskEntries.remove(entityAITaskEntry);
                }
                iterator.remove();
            }
        }
    }
    
    private boolean canContinue(final EntityAITaskEntry entityAITaskEntry) {
        return entityAITaskEntry.action.continueExecuting();
    }
    
    public void onUpdateTasks() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        net/minecraft/entity/ai/EntityAITasks.theProfiler:Lnet/minecraft/profiler/Profiler;
        //     4: ldc             "goalSetup"
        //     6: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //     9: aload_0        
        //    10: dup            
        //    11: getfield        net/minecraft/entity/ai/EntityAITasks.tickCount:I
        //    14: dup_x1         
        //    15: iconst_1       
        //    16: iadd           
        //    17: putfield        net/minecraft/entity/ai/EntityAITasks.tickCount:I
        //    20: aload_0        
        //    21: getfield        net/minecraft/entity/ai/EntityAITasks.tickRate:I
        //    24: irem           
        //    25: ifne            154
        //    28: aload_0        
        //    29: getfield        net/minecraft/entity/ai/EntityAITasks.taskEntries:Ljava/util/List;
        //    32: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    37: astore_1       
        //    38: aload_1        
        //    39: invokeinterface java/util/Iterator.hasNext:()Z
        //    44: ifne            50
        //    47: goto            207
        //    50: aload_1        
        //    51: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    56: checkcast       Lnet/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry;
        //    59: astore_2       
        //    60: aload_0        
        //    61: getfield        net/minecraft/entity/ai/EntityAITasks.executingTaskEntries:Ljava/util/List;
        //    64: aload_2        
        //    65: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //    70: istore_3       
        //    71: iload_3        
        //    72: ifne            78
        //    75: goto            115
        //    78: aload_0        
        //    79: aload_2        
        //    80: ifeq            91
        //    83: aload_0        
        //    84: aload_2        
        //    85: invokespecial   net/minecraft/entity/ai/EntityAITasks.canContinue:(Lnet/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry;)Z
        //    88: ifne            112
        //    91: aload_2        
        //    92: getfield        net/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry.action:Lnet/minecraft/entity/ai/EntityAIBase;
        //    95: invokevirtual   net/minecraft/entity/ai/EntityAIBase.resetTask:()V
        //    98: aload_0        
        //    99: getfield        net/minecraft/entity/ai/EntityAITasks.executingTaskEntries:Ljava/util/List;
        //   102: aload_2        
        //   103: invokeinterface java/util/List.remove:(Ljava/lang/Object;)Z
        //   108: pop            
        //   109: goto            115
        //   112: goto            38
        //   115: aload_0        
        //   116: aload_2        
        //   117: ifeq            148
        //   120: aload_2        
        //   121: getfield        net/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry.action:Lnet/minecraft/entity/ai/EntityAIBase;
        //   124: invokevirtual   net/minecraft/entity/ai/EntityAIBase.shouldExecute:()Z
        //   127: ifeq            148
        //   130: aload_2        
        //   131: getfield        net/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry.action:Lnet/minecraft/entity/ai/EntityAIBase;
        //   134: invokevirtual   net/minecraft/entity/ai/EntityAIBase.startExecuting:()V
        //   137: aload_0        
        //   138: getfield        net/minecraft/entity/ai/EntityAITasks.executingTaskEntries:Ljava/util/List;
        //   141: aload_2        
        //   142: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   147: pop            
        //   148: goto            38
        //   151: goto            207
        //   154: aload_0        
        //   155: getfield        net/minecraft/entity/ai/EntityAITasks.executingTaskEntries:Ljava/util/List;
        //   158: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   163: astore_1       
        //   164: aload_1        
        //   165: invokeinterface java/util/Iterator.hasNext:()Z
        //   170: ifeq            207
        //   173: aload_1        
        //   174: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   179: checkcast       Lnet/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry;
        //   182: astore_2       
        //   183: aload_0        
        //   184: aload_2        
        //   185: invokespecial   net/minecraft/entity/ai/EntityAITasks.canContinue:(Lnet/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry;)Z
        //   188: ifne            204
        //   191: aload_2        
        //   192: getfield        net/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry.action:Lnet/minecraft/entity/ai/EntityAIBase;
        //   195: invokevirtual   net/minecraft/entity/ai/EntityAIBase.resetTask:()V
        //   198: aload_1        
        //   199: invokeinterface java/util/Iterator.remove:()V
        //   204: goto            164
        //   207: aload_0        
        //   208: getfield        net/minecraft/entity/ai/EntityAITasks.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   211: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   214: aload_0        
        //   215: getfield        net/minecraft/entity/ai/EntityAITasks.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   218: ldc             "goalTick"
        //   220: invokevirtual   net/minecraft/profiler/Profiler.startSection:(Ljava/lang/String;)V
        //   223: aload_0        
        //   224: getfield        net/minecraft/entity/ai/EntityAITasks.executingTaskEntries:Ljava/util/List;
        //   227: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //   232: astore_1       
        //   233: aload_1        
        //   234: invokeinterface java/util/Iterator.hasNext:()Z
        //   239: ifeq            262
        //   242: aload_1        
        //   243: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   248: checkcast       Lnet/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry;
        //   251: astore_2       
        //   252: aload_2        
        //   253: getfield        net/minecraft/entity/ai/EntityAITasks$EntityAITaskEntry.action:Lnet/minecraft/entity/ai/EntityAIBase;
        //   256: invokevirtual   net/minecraft/entity/ai/EntityAIBase.updateTask:()V
        //   259: goto            233
        //   262: aload_0        
        //   263: getfield        net/minecraft/entity/ai/EntityAITasks.theProfiler:Lnet/minecraft/profiler/Profiler;
        //   266: invokevirtual   net/minecraft/profiler/Profiler.endSection:()V
        //   269: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0038 (coming from #0148).
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
    
    static {
        logger = LogManager.getLogger();
    }
    
    public EntityAITasks(final Profiler theProfiler) {
        this.taskEntries = Lists.newArrayList();
        this.executingTaskEntries = Lists.newArrayList();
        this.tickRate = 3;
        this.theProfiler = theProfiler;
    }
    
    class EntityAITaskEntry
    {
        public int priority;
        final EntityAITasks this$0;
        public EntityAIBase action;
        
        public EntityAITaskEntry(final EntityAITasks this$0, final int priority, final EntityAIBase action) {
            this.this$0 = this$0;
            this.priority = priority;
            this.action = action;
        }
    }
}
