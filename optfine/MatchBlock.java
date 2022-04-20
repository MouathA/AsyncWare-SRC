package optfine;

import net.minecraft.block.state.*;

public class MatchBlock
{
    private int[] metadatas;
    private int blockId;
    
    public MatchBlock(final int blockId) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
    }
    
    public boolean matches(final BlockStateBase blockStateBase) {
        if (blockStateBase.getBlockId() != this.blockId) {
            return false;
        }
        if (this.metadatas != null) {
            final int metadata = blockStateBase.getMetadata();
            while (0 < this.metadatas.length) {
                if (this.metadatas[0] == metadata) {
                    break;
                }
                int n = 0;
                ++n;
            }
        }
        return true;
    }
    
    public MatchBlock(final int blockId, final int[] metadatas) {
        this.blockId = -1;
        this.metadatas = null;
        this.blockId = blockId;
        this.metadatas = metadatas;
    }
    
    public int getBlockId() {
        return this.blockId;
    }
    
    public int[] getMetadatas() {
        return this.metadatas;
    }
}
