package net.minecraft.block;

import net.minecraft.block.properties.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.item.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider
{
    public static final PropertyBool POWERED;
    public static final PropertyEnum MODE;
    
    @Override
    public IBlockState getStateFromMeta(final int n) {
        return this.getDefaultState().withProperty(BlockRedstoneComparator.FACING, EnumFacing.getHorizontal(n)).withProperty(BlockRedstoneComparator.POWERED, (n & 0x8) > 0).withProperty(BlockRedstoneComparator.MODE, ((n & 0x4) > 0) ? Mode.SUBTRACT : Mode.COMPARE);
    }
    
    @Override
    public void breakBlock(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        world.removeTileEntity(blockPos);
        this.notifyNeighbors(world, blockPos, blockState);
    }
    
    @Override
    public Item getItem(final World world, final BlockPos blockPos) {
        return Items.comparator;
    }
    
    private int calculateOutput(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return (blockState.getValue(BlockRedstoneComparator.MODE) == Mode.SUBTRACT) ? Math.max(this.calculateInputStrength(world, blockPos, blockState) - this.getPowerOnSides(world, blockPos, blockState), 0) : this.calculateInputStrength(world, blockPos, blockState);
    }
    
    @Override
    protected void updateState(final World p0, final BlockPos p1, final IBlockState p2) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_2        
        //     2: aload_0        
        //     3: invokevirtual   net/minecraft/world/World.isBlockTickPending:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;)Z
        //     6: ifne            95
        //     9: aload_0        
        //    10: aload_1        
        //    11: aload_2        
        //    12: aload_3        
        //    13: invokespecial   net/minecraft/block/BlockRedstoneComparator.calculateOutput:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)I
        //    16: istore          4
        //    18: aload_1        
        //    19: aload_2        
        //    20: invokevirtual   net/minecraft/world/World.getTileEntity:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
        //    23: astore          5
        //    25: aload           5
        //    27: instanceof      Lnet/minecraft/tileentity/TileEntityComparator;
        //    30: ifeq            44
        //    33: aload           5
        //    35: checkcast       Lnet/minecraft/tileentity/TileEntityComparator;
        //    38: invokevirtual   net/minecraft/tileentity/TileEntityComparator.getOutputSignal:()I
        //    41: goto            45
        //    44: iconst_0       
        //    45: istore          6
        //    47: iload           4
        //    49: iload           6
        //    51: if_icmpne       66
        //    54: aload_0        
        //    55: aload_3        
        //    56: invokevirtual   net/minecraft/block/BlockRedstoneComparator.isPowered:(Lnet/minecraft/block/state/IBlockState;)Z
        //    59: aload_0        
        //    60: aload_1        
        //    61: aload_2        
        //    62: aload_3        
        //    63: if_icmplt       95
        //    66: aload_0        
        //    67: aload_1        
        //    68: aload_2        
        //    69: aload_3        
        //    70: invokevirtual   net/minecraft/block/BlockRedstoneComparator.isFacingTowardsRepeater:(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z
        //    73: ifeq            87
        //    76: aload_1        
        //    77: aload_2        
        //    78: aload_0        
        //    79: iconst_2       
        //    80: iconst_m1      
        //    81: invokevirtual   net/minecraft/world/World.updateBlockTick:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;II)V
        //    84: goto            95
        //    87: aload_1        
        //    88: aload_2        
        //    89: aload_0        
        //    90: iconst_2       
        //    91: iconst_0       
        //    92: invokevirtual   net/minecraft/world/World.updateBlockTick:(Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/Block;II)V
        //    95: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0066 (coming from #0063).
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
    
    @Override
    public void onBlockAdded(final World world, final BlockPos blockPos, final IBlockState blockState) {
        super.onBlockAdded(world, blockPos, blockState);
        world.setTileEntity(blockPos, this.createNewTileEntity(world, 0));
    }
    
    @Override
    public int getMetaFromState(final IBlockState blockState) {
        final int n = 0x0 | ((EnumFacing)blockState.getValue(BlockRedstoneComparator.FACING)).getHorizontalIndex();
        if (blockState.getValue(BlockRedstoneComparator.POWERED)) {}
        if (blockState.getValue(BlockRedstoneComparator.MODE) == Mode.SUBTRACT) {}
        return 0;
    }
    
    @Override
    public Item getItemDropped(final IBlockState blockState, final Random random, final int n) {
        return Items.comparator;
    }
    
    @Override
    public boolean onBlockEventReceived(final World world, final BlockPos blockPos, final IBlockState blockState, final int n, final int n2) {
        super.onBlockEventReceived(world, blockPos, blockState, n, n2);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity != null && tileEntity.receiveClientEvent(n, n2);
    }
    
    @Override
    protected int getActiveSignal(final IBlockAccess blockAccess, final BlockPos blockPos, final IBlockState blockState) {
        final TileEntity tileEntity = blockAccess.getTileEntity(blockPos);
        return (tileEntity instanceof TileEntityComparator) ? ((TileEntityComparator)tileEntity).getOutputSignal() : 0;
    }
    
    @Override
    protected int calculateInputStrength(final World world, final BlockPos blockPos, final IBlockState blockState) {
        int n = super.calculateInputStrength(world, blockPos, blockState);
        final EnumFacing enumFacing = (EnumFacing)blockState.getValue(BlockRedstoneComparator.FACING);
        final BlockPos offset = blockPos.offset(enumFacing);
        final Block block = world.getBlockState(offset).getBlock();
        if (block.hasComparatorInputOverride()) {
            n = block.getComparatorInputOverride(world, offset);
        }
        else if (n < 15 && block.isNormalCube()) {
            final BlockPos offset2 = offset.offset(enumFacing);
            final Block block2 = world.getBlockState(offset2).getBlock();
            if (block2.hasComparatorInputOverride()) {
                n = block2.getComparatorInputOverride(world, offset2);
            }
            else if (block2.getMaterial() == Material.air) {
                final EntityItemFrame itemFrame = this.findItemFrame(world, enumFacing, offset2);
                if (itemFrame != null) {
                    n = itemFrame.func_174866_q();
                }
            }
        }
        return n;
    }
    
    private void onStateChange(final World world, final BlockPos blockPos, final IBlockState blockState) {
        final int calculateOutput = this.calculateOutput(world, blockPos, blockState);
        final TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityComparator) {
            final TileEntityComparator tileEntityComparator = (TileEntityComparator)tileEntity;
            tileEntityComparator.getOutputSignal();
            tileEntityComparator.setOutputSignal(calculateOutput);
        }
        if (calculateOutput || blockState.getValue(BlockRedstoneComparator.MODE) == Mode.COMPARE) {
            final boolean shouldBePowered = this.shouldBePowered(world, blockPos, blockState);
            final boolean powered = this.isPowered(blockState);
            if (powered && !shouldBePowered) {
                world.setBlockState(blockPos, blockState.withProperty(BlockRedstoneComparator.POWERED, false), 2);
            }
            else if (!powered && shouldBePowered) {
                world.setBlockState(blockPos, blockState.withProperty(BlockRedstoneComparator.POWERED, true), 2);
            }
            this.notifyNeighbors(world, blockPos, blockState);
        }
    }
    
    @Override
    public void updateTick(final World world, final BlockPos blockPos, final IBlockState blockState, final Random random) {
        if (this.isRepeaterPowered) {
            world.setBlockState(blockPos, this.getUnpoweredState(blockState).withProperty(BlockRedstoneComparator.POWERED, true), 4);
        }
        this.onStateChange(world, blockPos, blockState);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World world, final int n) {
        return new TileEntityComparator();
    }
    
    private EntityItemFrame findItemFrame(final World world, final EnumFacing enumFacing, final BlockPos blockPos) {
        final List entitiesWithinAABB = world.getEntitiesWithinAABB(EntityItemFrame.class, new AxisAlignedBB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1), (Predicate)new Predicate(this, enumFacing) {
            final EnumFacing val$facing;
            final BlockRedstoneComparator this$0;
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                return entity != null && entity.getHorizontalFacing() == this.val$facing;
            }
        });
        return (entitiesWithinAABB.size() == 1) ? entitiesWithinAABB.get(0) : null;
    }
    
    public BlockRedstoneComparator(final boolean b) {
        super(b);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockRedstoneComparator.FACING, EnumFacing.NORTH).withProperty(BlockRedstoneComparator.POWERED, false).withProperty(BlockRedstoneComparator.MODE, Mode.COMPARE));
        this.isBlockContainer = true;
    }
    
    @Override
    protected int getDelay(final IBlockState blockState) {
        return 2;
    }
    
    @Override
    public IBlockState onBlockPlaced(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3, final int n4, final EntityLivingBase entityLivingBase) {
        return this.getDefaultState().withProperty(BlockRedstoneComparator.FACING, entityLivingBase.getHorizontalFacing().getOpposite()).withProperty(BlockRedstoneComparator.POWERED, false).withProperty(BlockRedstoneComparator.MODE, Mode.COMPARE);
    }
    
    @Override
    protected IBlockState getPoweredState(final IBlockState blockState) {
        return Blocks.powered_comparator.getDefaultState().withProperty(BlockRedstoneComparator.FACING, blockState.getValue(BlockRedstoneComparator.FACING)).withProperty(BlockRedstoneComparator.POWERED, blockState.getValue(BlockRedstoneComparator.POWERED)).withProperty(BlockRedstoneComparator.MODE, blockState.getValue(BlockRedstoneComparator.MODE));
    }
    
    @Override
    protected boolean isPowered(final IBlockState blockState) {
        return this.isRepeaterPowered || (boolean)blockState.getValue(BlockRedstoneComparator.POWERED);
    }
    
    static {
        POWERED = PropertyBool.create("powered");
        MODE = PropertyEnum.create("mode", Mode.class);
    }
    
    @Override
    public String getLocalizedName() {
        return "\u4221\u423c\u422d\u4225\u4266\u422b\u4227\u4225\u4238\u4229\u423a\u4229\u423c\u4227\u423a\u4266\u4226\u4229\u4225\u422d";
    }
    
    @Override
    protected IBlockState getUnpoweredState(final IBlockState blockState) {
        return Blocks.unpowered_comparator.getDefaultState().withProperty(BlockRedstoneComparator.FACING, blockState.getValue(BlockRedstoneComparator.FACING)).withProperty(BlockRedstoneComparator.POWERED, blockState.getValue(BlockRedstoneComparator.POWERED)).withProperty(BlockRedstoneComparator.MODE, blockState.getValue(BlockRedstoneComparator.MODE));
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, IBlockState cycleProperty, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (!entityPlayer.capabilities.allowEdit) {
            return false;
        }
        cycleProperty = cycleProperty.cycleProperty(BlockRedstoneComparator.MODE);
        world.playSoundEffect(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, "random.click", 0.3f, (cycleProperty.getValue(BlockRedstoneComparator.MODE) == Mode.SUBTRACT) ? 0.55f : 0.5f);
        world.setBlockState(blockPos, cycleProperty, 2);
        this.onStateChange(world, blockPos, cycleProperty);
        return true;
    }
    
    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] { BlockRedstoneComparator.FACING, BlockRedstoneComparator.MODE, BlockRedstoneComparator.POWERED });
    }
    
    public enum Mode implements IStringSerializable
    {
        private final String name;
        
        COMPARE("COMPARE", 0, "compare");
        
        private static final Mode[] $VALUES;
        
        SUBTRACT("SUBTRACT", 1, "subtract");
        
        static {
            $VALUES = new Mode[] { Mode.COMPARE, Mode.SUBTRACT };
        }
        
        private Mode(final String s, final int n, final String name) {
            this.name = name;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
    }
}
