package optfine;

import net.minecraft.block.state.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.block.model.*;
import java.util.*;

public class RenderEnv
{
    private IBlockState blockState;
    private BlockPosM colorizerBlockPos;
    private BitSet boundsFlags;
    private float[] quadBounds;
    private BlockPos blockPos;
    private GameSettings gameSettings;
    private BlockModelRenderer.AmbientOcclusionFace aoFace;
    private static ThreadLocal threadLocalInstance;
    private boolean[] borderFlags;
    private IBlockAccess blockAccess;
    private int metadata;
    private int blockId;
    private int breakingAnimation;
    
    public int getBlockId() {
        if (this.blockId < 0) {
            this.blockId = Block.getIdFromBlock(this.blockState.getBlock());
        }
        return this.blockId;
    }
    
    private RenderEnv(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        this.blockId = -1;
        this.metadata = -1;
        this.breakingAnimation = -1;
        this.quadBounds = new float[EnumFacing.VALUES.length * 2];
        this.boundsFlags = new BitSet(3);
        this.aoFace = new BlockModelRenderer.AmbientOcclusionFace();
        this.colorizerBlockPos = null;
        this.borderFlags = null;
        this.blockAccess = blockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.gameSettings = Config.getGameSettings();
    }
    
    public IBlockState getBlockState() {
        return this.blockState;
    }
    
    public int getMetadata() {
        if (this.metadata < 0) {
            this.metadata = this.blockState.getBlock().getMetaFromState(this.blockState);
        }
        return this.metadata;
    }
    
    static {
        RenderEnv.threadLocalInstance = new ThreadLocal();
    }
    
    public boolean isBreakingAnimation() {
        return this.breakingAnimation == 1;
    }
    
    public BlockModelRenderer.AmbientOcclusionFace getAoFace() {
        return this.aoFace;
    }
    
    public boolean isBreakingAnimation(final BakedQuad bakedQuad) {
        if (this.breakingAnimation < 0) {
            if (bakedQuad instanceof BreakingFour) {
                this.breakingAnimation = 1;
            }
            else {
                this.breakingAnimation = 0;
            }
        }
        return this.breakingAnimation == 1;
    }
    
    public BitSet getBoundsFlags() {
        return this.boundsFlags;
    }
    
    public boolean isBreakingAnimation(final List list) {
        if (this.breakingAnimation < 0 && list.size() > 0) {
            if (list.get(0) instanceof BreakingFour) {
                this.breakingAnimation = 1;
            }
            else {
                this.breakingAnimation = 0;
            }
        }
        return this.breakingAnimation == 1;
    }
    
    public BlockPosM getColorizerBlockPos() {
        if (this.colorizerBlockPos == null) {
            this.colorizerBlockPos = new BlockPosM(0, 0, 0);
        }
        return this.colorizerBlockPos;
    }
    
    public static RenderEnv getInstance(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        final RenderEnv renderEnv = RenderEnv.threadLocalInstance.get();
        if (renderEnv == null) {
            final RenderEnv renderEnv2 = new RenderEnv(blockAccess, blockState, blockPos);
            RenderEnv.threadLocalInstance.set(renderEnv2);
            return renderEnv2;
        }
        renderEnv.reset(blockAccess, blockState, blockPos);
        return renderEnv;
    }
    
    public float[] getQuadBounds() {
        return this.quadBounds;
    }
    
    public boolean[] getBorderFlags() {
        if (this.borderFlags == null) {
            this.borderFlags = new boolean[4];
        }
        return this.borderFlags;
    }
    
    private void reset(final IBlockAccess blockAccess, final IBlockState blockState, final BlockPos blockPos) {
        this.blockAccess = blockAccess;
        this.blockState = blockState;
        this.blockPos = blockPos;
        this.blockId = -1;
        this.metadata = -1;
        this.breakingAnimation = -1;
        this.boundsFlags.clear();
    }
}
