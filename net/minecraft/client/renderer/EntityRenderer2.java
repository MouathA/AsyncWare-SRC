package net.minecraft.client.renderer;

import java.util.concurrent.*;
import net.minecraft.client.*;

class EntityRenderer2 implements Callable
{
    private static final String __OBFID = "CL_00000948";
    final EntityRenderer field_90025_c;
    
    EntityRenderer2(final EntityRenderer field_90025_c) {
        this.field_90025_c = field_90025_c;
    }
    
    @Override
    public String call() throws Exception {
        return Minecraft.getMinecraft().currentScreen.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() throws Exception {
        return this.call();
    }
}
