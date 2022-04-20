package net.minecraft.block.state;

import net.minecraft.util.*;
import net.minecraft.world.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class BlockPistonStructureHelper
{
    private final BlockPos blockToMove;
    private final List toDestroy;
    private final BlockPos pistonPos;
    private final EnumFacing moveDirection;
    private final World world;
    private final List toMove;
    
    private void func_177255_a(final int n, final int n2) {
        final ArrayList arrayList = Lists.newArrayList();
        final ArrayList arrayList2 = Lists.newArrayList();
        final ArrayList arrayList3 = Lists.newArrayList();
        arrayList.addAll(this.toMove.subList(0, n2));
        arrayList2.addAll(this.toMove.subList(this.toMove.size() - n, this.toMove.size()));
        arrayList3.addAll(this.toMove.subList(n2, this.toMove.size() - n));
        this.toMove.clear();
        this.toMove.addAll(arrayList);
        this.toMove.addAll(arrayList2);
        this.toMove.addAll(arrayList3);
    }
    
    public BlockPistonStructureHelper(final World world, final BlockPos pistonPos, final EnumFacing moveDirection, final boolean b) {
        this.toMove = Lists.newArrayList();
        this.toDestroy = Lists.newArrayList();
        this.world = world;
        this.pistonPos = pistonPos;
        if (b) {
            this.moveDirection = moveDirection;
            this.blockToMove = pistonPos.offset(moveDirection);
        }
        else {
            this.moveDirection = moveDirection.getOpposite();
            this.blockToMove = pistonPos.offset(moveDirection, 2);
        }
    }
    
    public boolean canMove() {
        this.toMove.clear();
        this.toDestroy.clear();
        final Block block = this.world.getBlockState(this.blockToMove).getBlock();
        if (!BlockPistonBase.canPush(block, this.world, this.blockToMove, this.moveDirection, false)) {
            if (block.getMobilityFlag() != 1) {
                return false;
            }
            this.toDestroy.add(this.blockToMove);
            return true;
        }
        else {
            if (this == this.blockToMove) {
                return false;
            }
            while (0 < this.toMove.size()) {
                final BlockPos blockPos = this.toMove.get(0);
                if (this.world.getBlockState(blockPos).getBlock() == Blocks.slime_block && this < blockPos) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
    }
    
    public List getBlocksToDestroy() {
        return this.toDestroy;
    }
    
    public List getBlocksToMove() {
        return this.toMove;
    }
}
