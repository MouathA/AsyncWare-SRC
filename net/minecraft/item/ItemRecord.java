package net.minecraft.item;

import net.minecraft.creativetab.*;
import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.stats.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.util.*;

public class ItemRecord extends Item
{
    private static final Map RECORDS;
    public final String recordName;
    
    @Override
    public EnumRarity getRarity(final ItemStack itemStack) {
        return EnumRarity.RARE;
    }
    
    protected ItemRecord(final String recordName) {
        this.recordName = recordName;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        ItemRecord.RECORDS.put("records." + recordName, this);
    }
    
    public static ItemRecord getRecord(final String s) {
        return ItemRecord.RECORDS.get(s);
    }
    
    static {
        RECORDS = Maps.newHashMap();
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != Blocks.jukebox || (boolean)blockState.getValue(BlockJukebox.HAS_RECORD)) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        ((BlockJukebox)Blocks.jukebox).insertRecord(world, blockPos, blockState, itemStack);
        world.playAuxSFXAtEntity(null, 1005, blockPos, Item.getIdFromItem(this));
        --itemStack.stackSize;
        entityPlayer.triggerAchievement(StatList.field_181740_X);
        return true;
    }
    
    @Override
    public void addInformation(final ItemStack itemStack, final EntityPlayer entityPlayer, final List list, final boolean b) {
        list.add(this.getRecordNameLocal());
    }
    
    public String getRecordNameLocal() {
        return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
    }
}
