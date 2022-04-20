package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class BlockWorkbench extends Block
{
    protected BlockWorkbench() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public boolean onBlockActivated(final World world, final BlockPos blockPos, final IBlockState blockState, final EntityPlayer entityPlayer, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (world.isRemote) {
            return true;
        }
        entityPlayer.displayGui(new InterfaceCraftingTable(world, blockPos));
        entityPlayer.triggerAchievement(StatList.field_181742_Z);
        return true;
    }
    
    public static class InterfaceCraftingTable implements IInteractionObject
    {
        private final World world;
        private final BlockPos position;
        
        @Override
        public String getName() {
            return null;
        }
        
        @Override
        public boolean hasCustomName() {
            return false;
        }
        
        @Override
        public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
            return new ContainerWorkbench(inventoryPlayer, this.world, this.position);
        }
        
        public InterfaceCraftingTable(final World world, final BlockPos position) {
            this.world = world;
            this.position = position;
        }
        
        @Override
        public String getGuiID() {
            return "minecraft:crafting_table";
        }
        
        @Override
        public IChatComponent getDisplayName() {
            return new ChatComponentTranslation(Blocks.crafting_table.getUnlocalizedName() + ".name", new Object[0]);
        }
    }
}
