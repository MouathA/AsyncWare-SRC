package net.minecraft.tileentity;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import com.google.common.collect.*;
import net.minecraft.block.*;
import net.minecraft.crash.*;
import java.util.concurrent.*;
import net.minecraft.block.state.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.network.*;

public abstract class TileEntity
{
    private static Map classToNameMap;
    protected World worldObj;
    private int blockMetadata;
    private static final Logger logger;
    protected boolean tileEntityInvalid;
    protected BlockPos pos;
    private static Map nameToClassMap;
    protected Block blockType;
    
    public double getMaxRenderDistanceSquared() {
        return 4096.0;
    }
    
    static Map access$000() {
        return TileEntity.classToNameMap;
    }
    
    static {
        logger = LogManager.getLogger();
        TileEntity.nameToClassMap = Maps.newHashMap();
        TileEntity.classToNameMap = Maps.newHashMap();
        addMapping(TileEntityFurnace.class, "Furnace");
        addMapping(TileEntityChest.class, "Chest");
        addMapping(TileEntityEnderChest.class, "EnderChest");
        addMapping(BlockJukebox.TileEntityJukebox.class, "RecordPlayer");
        addMapping(TileEntityDispenser.class, "Trap");
        addMapping(TileEntityDropper.class, "Dropper");
        addMapping(TileEntitySign.class, "Sign");
        addMapping(TileEntityMobSpawner.class, "MobSpawner");
        addMapping(TileEntityNote.class, "Music");
        addMapping(TileEntityPiston.class, "Piston");
        addMapping(TileEntityBrewingStand.class, "Cauldron");
        addMapping(TileEntityEnchantmentTable.class, "EnchantTable");
        addMapping(TileEntityEndPortal.class, "Airportal");
        addMapping(TileEntityCommandBlock.class, "Control");
        addMapping(TileEntityBeacon.class, "Beacon");
        addMapping(TileEntitySkull.class, "Skull");
        addMapping(TileEntityDaylightDetector.class, "DLDetector");
        addMapping(TileEntityHopper.class, "Hopper");
        addMapping(TileEntityComparator.class, "Comparator");
        addMapping(TileEntityFlowerPot.class, "FlowerPot");
        addMapping(TileEntityBanner.class, "Banner");
    }
    
    public void addInfoToCrashReport(final CrashReportCategory crashReportCategory) {
        crashReportCategory.addCrashSectionCallable("Name", new Callable(this) {
            final TileEntity this$0;
            
            @Override
            public Object call() throws Exception {
                return this.call();
            }
            
            @Override
            public String call() throws Exception {
                return TileEntity.access$000().get(this.this$0.getClass()) + " // " + this.this$0.getClass().getCanonicalName();
            }
        });
        if (this.worldObj != null) {
            CrashReportCategory.addBlockInfo(crashReportCategory, this.pos, this.getBlockType(), this.getBlockMetadata());
            crashReportCategory.addCrashSectionCallable("Actual block type", new Callable(this) {
                final TileEntity this$0;
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
                
                @Override
                public String call() throws Exception {
                    final int idFromBlock = Block.getIdFromBlock(this.this$0.worldObj.getBlockState(this.this$0.pos).getBlock());
                    return String.format("ID #%d (%s // %s)", idFromBlock, Block.getBlockById(idFromBlock).getUnlocalizedName(), Block.getBlockById(idFromBlock).getClass().getCanonicalName());
                }
            });
            crashReportCategory.addCrashSectionCallable("Actual block data value", new Callable(this) {
                final TileEntity this$0;
                
                @Override
                public String call() throws Exception {
                    final IBlockState blockState = this.this$0.worldObj.getBlockState(this.this$0.pos);
                    final int metaFromState = blockState.getBlock().getMetaFromState(blockState);
                    if (metaFromState < 0) {
                        return "Unknown? (Got " + metaFromState + ")";
                    }
                    return String.format("%1$d / 0x%1$X / 0b%2$s", metaFromState, String.format("%4s", Integer.toBinaryString(metaFromState)).replace(" ", "0"));
                }
                
                @Override
                public Object call() throws Exception {
                    return this.call();
                }
            });
        }
    }
    
    public static TileEntity createAndLoadEntity(final NBTTagCompound nbtTagCompound) {
        TileEntity tileEntity = null;
        final Class<TileEntity> clazz = TileEntity.nameToClassMap.get(nbtTagCompound.getString("id"));
        if (clazz != null) {
            tileEntity = clazz.newInstance();
        }
        if (tileEntity != null) {
            tileEntity.readFromNBT(nbtTagCompound);
        }
        else {
            TileEntity.logger.warn("Skipping BlockEntity with id " + nbtTagCompound.getString("id"));
        }
        return tileEntity;
    }
    
    public void markDirty() {
        if (this.worldObj != null) {
            final IBlockState blockState = this.worldObj.getBlockState(this.pos);
            this.blockMetadata = blockState.getBlock().getMetaFromState(blockState);
            this.worldObj.markChunkDirty(this.pos, this);
            if (this.getBlockType() != Blocks.air) {
                this.worldObj.updateComparatorOutputLevel(this.pos, this.getBlockType());
            }
        }
    }
    
    public boolean receiveClientEvent(final int n, final int n2) {
        return false;
    }
    
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        final String s = TileEntity.classToNameMap.get(this.getClass());
        if (s == null) {
            throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
        }
        nbtTagCompound.setString("id", s);
        nbtTagCompound.setInteger("x", this.pos.getX());
        nbtTagCompound.setInteger("y", this.pos.getY());
        nbtTagCompound.setInteger("z", this.pos.getZ());
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public boolean isInvalid() {
        return this.tileEntityInvalid;
    }
    
    public TileEntity() {
        this.pos = BlockPos.ORIGIN;
        this.blockMetadata = -1;
    }
    
    public void setWorldObj(final World worldObj) {
        this.worldObj = worldObj;
    }
    
    public boolean func_183000_F() {
        return false;
    }
    
    public Packet getDescriptionPacket() {
        return null;
    }
    
    public Block getBlockType() {
        if (this.blockType == null) {
            this.blockType = this.worldObj.getBlockState(this.pos).getBlock();
        }
        return this.blockType;
    }
    
    public double getDistanceSq(final double n, final double n2, final double n3) {
        final double n4 = this.pos.getX() + 0.5 - n;
        final double n5 = this.pos.getY() + 0.5 - n2;
        final double n6 = this.pos.getZ() + 0.5 - n3;
        return n4 * n4 + n5 * n5 + n6 * n6;
    }
    
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        this.pos = new BlockPos(nbtTagCompound.getInteger("x"), nbtTagCompound.getInteger("y"), nbtTagCompound.getInteger("z"));
    }
    
    public int getBlockMetadata() {
        if (this.blockMetadata == -1) {
            final IBlockState blockState = this.worldObj.getBlockState(this.pos);
            this.blockMetadata = blockState.getBlock().getMetaFromState(blockState);
        }
        return this.blockMetadata;
    }
    
    public World getWorld() {
        return this.worldObj;
    }
    
    public void invalidate() {
        this.tileEntityInvalid = true;
    }
    
    private static void addMapping(final Class clazz, final String s) {
        if (TileEntity.nameToClassMap.containsKey(s)) {
            throw new IllegalArgumentException("Duplicate id: " + s);
        }
        TileEntity.nameToClassMap.put(s, clazz);
        TileEntity.classToNameMap.put(clazz, s);
    }
    
    public void setPos(final BlockPos pos) {
        this.pos = pos;
    }
    
    public boolean hasWorldObj() {
        return this.worldObj != null;
    }
    
    public void updateContainingBlockInfo() {
        this.blockType = null;
        this.blockMetadata = -1;
    }
    
    public void validate() {
        this.tileEntityInvalid = false;
    }
}
