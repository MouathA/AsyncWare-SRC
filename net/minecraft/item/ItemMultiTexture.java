package net.minecraft.item;

import net.minecraft.block.*;
import com.google.common.base.*;

public class ItemMultiTexture extends ItemBlock
{
    protected final Block theBlock;
    protected final Function nameFunction;
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    public ItemMultiTexture(final Block block, final Block theBlock, final Function nameFunction) {
        super(block);
        this.theBlock = theBlock;
        this.nameFunction = nameFunction;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return super.getUnlocalizedName() + "." + (String)this.nameFunction.apply((Object)itemStack);
    }
    
    public ItemMultiTexture(final Block block, final Block block2, final String[] array) {
        this(block, block2, (Function)new Function(array) {
            final String[] val$namesByMeta;
            
            public String apply(final ItemStack itemStack) {
                itemStack.getMetadata();
                if (0 >= this.val$namesByMeta.length) {}
                return this.val$namesByMeta[0];
            }
            
            public Object apply(final Object o) {
                return this.apply((ItemStack)o);
            }
        });
    }
}
