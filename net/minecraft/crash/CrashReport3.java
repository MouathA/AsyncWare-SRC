package net.minecraft.crash;

import java.util.concurrent.*;
import net.minecraft.client.renderer.*;

class CrashReport3 implements Callable
{
    final CrashReport field_71490_a;
    
    @Override
    public Object call() throws Exception {
        return this.call();
    }
    
    CrashReport3(final CrashReport field_71490_a) {
        this.field_71490_a = field_71490_a;
    }
    
    @Override
    public String call() {
        return OpenGlHelper.func_183029_j();
    }
}
