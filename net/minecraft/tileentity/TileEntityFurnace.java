package net.minecraft.tileentity;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.crafting.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;

public class TileEntityFurnace extends TileEntityLockable implements ISidedInventory, ITickable
{
    private String furnaceCustomName;
    private int totalCookTime;
    private ItemStack[] furnaceItemStacks;
    private int currentItemBurnTime;
    private static final int[] slotsBottom;
    private int cookTime;
    private static final int[] slotsSides;
    private int furnaceBurnTime;
    
    @Override
    public boolean canExtractItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        if (enumFacing == EnumFacing.DOWN && n == 1) {
            final Item item = itemStack.getItem();
            if (item != Items.water_bucket && item != Items.bucket) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public Container createContainer(final InventoryPlayer inventoryPlayer, final EntityPlayer entityPlayer) {
        return new ContainerFurnace(inventoryPlayer, this);
    }
    
    @Override
    public void update() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   net/minecraft/tileentity/TileEntityFurnace.isBurning:()Z
        //     4: istore_1       
        //     5: aload_0        
        //     6: ifle            19
        //     9: aload_0        
        //    10: dup            
        //    11: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceBurnTime:I
        //    14: iconst_1       
        //    15: isub           
        //    16: putfield        net/minecraft/tileentity/TileEntityFurnace.furnaceBurnTime:I
        //    19: aload_0        
        //    20: getfield        net/minecraft/tileentity/TileEntityFurnace.worldObj:Lnet/minecraft/world/World;
        //    23: getfield        net/minecraft/world/World.isRemote:Z
        //    26: ifne            264
        //    29: aload_0        
        //    30: ifle            51
        //    33: aload_0        
        //    34: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //    37: iconst_1       
        //    38: aaload         
        //    39: ifnull          215
        //    42: aload_0        
        //    43: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //    46: iconst_0       
        //    47: aaload         
        //    48: ifnull          215
        //    51: aload_0        
        //    52: ifle            152
        //    55: aload_0        
        //    56: ifnonnull       152
        //    59: aload_0        
        //    60: aload_0        
        //    61: aload_0        
        //    62: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //    65: iconst_1       
        //    66: aaload         
        //    67: invokestatic    net/minecraft/tileentity/TileEntityFurnace.getItemBurnTime:(Lnet/minecraft/item/ItemStack;)I
        //    70: dup_x1         
        //    71: putfield        net/minecraft/tileentity/TileEntityFurnace.furnaceBurnTime:I
        //    74: putfield        net/minecraft/tileentity/TileEntityFurnace.currentItemBurnTime:I
        //    77: aload_0        
        //    78: ifle            152
        //    81: aload_0        
        //    82: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //    85: iconst_1       
        //    86: aaload         
        //    87: ifnull          152
        //    90: aload_0        
        //    91: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //    94: iconst_1       
        //    95: aaload         
        //    96: dup            
        //    97: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   100: iconst_1       
        //   101: isub           
        //   102: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   105: aload_0        
        //   106: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //   109: iconst_1       
        //   110: aaload         
        //   111: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   114: ifne            152
        //   117: aload_0        
        //   118: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //   121: iconst_1       
        //   122: aaload         
        //   123: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   126: invokevirtual   net/minecraft/item/Item.getContainerItem:()Lnet/minecraft/item/Item;
        //   129: astore_3       
        //   130: aload_0        
        //   131: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //   134: iconst_1       
        //   135: aload_3        
        //   136: ifnull          150
        //   139: new             Lnet/minecraft/item/ItemStack;
        //   142: dup            
        //   143: aload_3        
        //   144: invokespecial   net/minecraft/item/ItemStack.<init>:(Lnet/minecraft/item/Item;)V
        //   147: goto            151
        //   150: aconst_null    
        //   151: aastore        
        //   152: aload_0        
        //   153: ifle            207
        //   156: aload_0        
        //   157: ifnonnull       207
        //   160: aload_0        
        //   161: dup            
        //   162: getfield        net/minecraft/tileentity/TileEntityFurnace.cookTime:I
        //   165: iconst_1       
        //   166: iadd           
        //   167: putfield        net/minecraft/tileentity/TileEntityFurnace.cookTime:I
        //   170: aload_0        
        //   171: getfield        net/minecraft/tileentity/TileEntityFurnace.cookTime:I
        //   174: aload_0        
        //   175: getfield        net/minecraft/tileentity/TileEntityFurnace.totalCookTime:I
        //   178: if_icmpne       244
        //   181: aload_0        
        //   182: iconst_0       
        //   183: putfield        net/minecraft/tileentity/TileEntityFurnace.cookTime:I
        //   186: aload_0        
        //   187: aload_0        
        //   188: aload_0        
        //   189: getfield        net/minecraft/tileentity/TileEntityFurnace.furnaceItemStacks:[Lnet/minecraft/item/ItemStack;
        //   192: iconst_0       
        //   193: aaload         
        //   194: invokevirtual   net/minecraft/tileentity/TileEntityFurnace.getCookTime:(Lnet/minecraft/item/ItemStack;)I
        //   197: putfield        net/minecraft/tileentity/TileEntityFurnace.totalCookTime:I
        //   200: aload_0        
        //   201: invokevirtual   net/minecraft/tileentity/TileEntityFurnace.smeltItem:()V
        //   204: goto            244
        //   207: aload_0        
        //   208: iconst_0       
        //   209: putfield        net/minecraft/tileentity/TileEntityFurnace.cookTime:I
        //   212: goto            244
        //   215: aload_0        
        //   216: ifle            244
        //   219: aload_0        
        //   220: getfield        net/minecraft/tileentity/TileEntityFurnace.cookTime:I
        //   223: ifle            244
        //   226: aload_0        
        //   227: aload_0        
        //   228: getfield        net/minecraft/tileentity/TileEntityFurnace.cookTime:I
        //   231: iconst_2       
        //   232: isub           
        //   233: iconst_0       
        //   234: aload_0        
        //   235: getfield        net/minecraft/tileentity/TileEntityFurnace.totalCookTime:I
        //   238: invokestatic    net/minecraft/util/MathHelper.clamp_int:(III)I
        //   241: putfield        net/minecraft/tileentity/TileEntityFurnace.cookTime:I
        //   244: iload_1        
        //   245: aload_0        
        //   246: ifle            264
        //   249: aload_0        
        //   250: invokevirtual   net/minecraft/tileentity/TileEntityFurnace.isBurning:()Z
        //   253: aload_0        
        //   254: getfield        net/minecraft/tileentity/TileEntityFurnace.worldObj:Lnet/minecraft/world/World;
        //   257: aload_0        
        //   258: getfield        net/minecraft/tileentity/TileEntityFurnace.pos:Lnet/minecraft/util/BlockPos;
        //   261: invokestatic    net/minecraft/block/BlockFurnace.setState:(ZLnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;)V
        //   264: aload_0        
        //   265: invokevirtual   net/minecraft/tileentity/TileEntityFurnace.markDirty:()V
        //   268: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0264 (coming from #0246).
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
    
    public void smeltItem() {
        if (this == null) {
            final ItemStack smeltingResult = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
            if (this.furnaceItemStacks[2] == null) {
                this.furnaceItemStacks[2] = smeltingResult.copy();
            }
            else if (this.furnaceItemStacks[2].getItem() == smeltingResult.getItem()) {
                final ItemStack itemStack = this.furnaceItemStacks[2];
                ++itemStack.stackSize;
            }
            if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge) && this.furnaceItemStacks[0].getMetadata() == 1 && this.furnaceItemStacks[1] != null && this.furnaceItemStacks[1].getItem() == Items.bucket) {
                this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
            }
            final ItemStack itemStack2 = this.furnaceItemStacks[0];
            --itemStack2.stackSize;
            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        }
    }
    
    public TileEntityFurnace() {
        this.furnaceItemStacks = new ItemStack[3];
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        nbtTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
        nbtTagCompound.setShort("CookTime", (short)this.cookTime);
        nbtTagCompound.setShort("CookTimeTotal", (short)this.totalCookTime);
        final NBTTagList list = new NBTTagList();
        while (0 < this.furnaceItemStacks.length) {
            if (this.furnaceItemStacks[0] != null) {
                final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
                nbtTagCompound2.setByte("Slot", (byte)0);
                this.furnaceItemStacks[0].writeToNBT(nbtTagCompound2);
                list.appendTag(nbtTagCompound2);
            }
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Items", list);
        if (this != null) {
            nbtTagCompound.setString("CustomName", this.furnaceCustomName);
        }
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public int[] getSlotsForFace(final EnumFacing enumFacing) {
        return (enumFacing == EnumFacing.DOWN) ? TileEntityFurnace.slotsBottom : ((enumFacing == EnumFacing.UP) ? TileEntityFurnace.slotsTop : TileEntityFurnace.slotsSides);
    }
    
    @Override
    public void setField(final int n, final int n2) {
        switch (n) {
            case 0: {
                this.furnaceBurnTime = n2;
                break;
            }
            case 1: {
                this.currentItemBurnTime = n2;
                break;
            }
            case 2: {
                this.cookTime = n2;
                break;
            }
            case 3: {
                this.totalCookTime = n2;
                break;
            }
        }
    }
    
    public static boolean isBurning(final IInventory inventory) {
        return inventory.getField(0) > 0;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        final NBTTagList tagList = nbtTagCompound.getTagList("Items", 10);
        this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];
        while (0 < tagList.tagCount()) {
            final NBTTagCompound compoundTag = tagList.getCompoundTagAt(0);
            final byte byte1 = compoundTag.getByte("Slot");
            if (byte1 >= 0 && byte1 < this.furnaceItemStacks.length) {
                this.furnaceItemStacks[byte1] = ItemStack.loadItemStackFromNBT(compoundTag);
            }
            int n = 0;
            ++n;
        }
        this.furnaceBurnTime = nbtTagCompound.getShort("BurnTime");
        this.cookTime = nbtTagCompound.getShort("CookTime");
        this.totalCookTime = nbtTagCompound.getShort("CookTimeTotal");
        this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
        if (nbtTagCompound.hasKey("CustomName", 8)) {
            this.furnaceCustomName = nbtTagCompound.getString("CustomName");
        }
    }
    
    public void setCustomInventoryName(final String furnaceCustomName) {
        this.furnaceCustomName = furnaceCustomName;
    }
    
    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }
    
    @Override
    public void clear() {
        while (0 < this.furnaceItemStacks.length) {
            this.furnaceItemStacks[0] = null;
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public ItemStack getStackInSlot(final int n) {
        return this.furnaceItemStacks[n];
    }
    
    static {
        TileEntityFurnace.slotsTop = new int[] { 0 };
        slotsBottom = new int[] { 2, 1 };
        slotsSides = new int[] { 1 };
    }
    
    @Override
    public ItemStack decrStackSize(final int n, final int n2) {
        if (this.furnaceItemStacks[n] == null) {
            return null;
        }
        if (this.furnaceItemStacks[n].stackSize <= n2) {
            final ItemStack itemStack = this.furnaceItemStacks[n];
            this.furnaceItemStacks[n] = null;
            return itemStack;
        }
        final ItemStack splitStack = this.furnaceItemStacks[n].splitStack(n2);
        if (this.furnaceItemStacks[n].stackSize == 0) {
            this.furnaceItemStacks[n] = null;
        }
        return splitStack;
    }
    
    @Override
    public int getField(final int n) {
        switch (n) {
            case 0: {
                return this.furnaceBurnTime;
            }
            case 1: {
                return this.currentItemBurnTime;
            }
            case 2: {
                return this.cookTime;
            }
            case 3: {
                return this.totalCookTime;
            }
            default: {
                return 0;
            }
        }
    }
    
    @Override
    public void setInventorySlotContents(final int n, final ItemStack itemStack) {
        final boolean b = itemStack != null && itemStack.isItemEqual(this.furnaceItemStacks[n]) && ItemStack.areItemStackTagsEqual(itemStack, this.furnaceItemStacks[n]);
        this.furnaceItemStacks[n] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        if (n == 0 && !b) {
            this.totalCookTime = this.getCookTime(itemStack);
            this.cookTime = 0;
            this.markDirty();
        }
    }
    
    @Override
    public int getFieldCount() {
        return 4;
    }
    
    @Override
    public String getGuiID() {
        return "minecraft:furnace";
    }
    
    @Override
    public void openInventory(final EntityPlayer entityPlayer) {
    }
    
    public static int getItemBurnTime(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final Item item = itemStack.getItem();
        if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
            final Block blockFromItem = Block.getBlockFromItem(item);
            if (blockFromItem == Blocks.wooden_slab) {
                return 150;
            }
            if (blockFromItem.getMaterial() == Material.wood) {
                return 300;
            }
            if (blockFromItem == Blocks.coal_block) {
                return 16000;
            }
        }
        return (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) ? 200 : ((item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) ? 200 : ((item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) ? 200 : ((item == Items.stick) ? 100 : ((item == Items.coal) ? 1600 : ((item == Items.lava_bucket) ? 20000 : ((item == Item.getItemFromBlock(Blocks.sapling)) ? 100 : ((item == Items.blaze_rod) ? 2400 : 0)))))));
    }
    
    public int getCookTime(final ItemStack itemStack) {
        return 200;
    }
    
    @Override
    public ItemStack removeStackFromSlot(final int n) {
        if (this.furnaceItemStacks[n] != null) {
            final ItemStack itemStack = this.furnaceItemStacks[n];
            this.furnaceItemStacks[n] = null;
            return itemStack;
        }
        return null;
    }
    
    @Override
    public void closeInventory(final EntityPlayer entityPlayer) {
    }
    
    @Override
    public boolean isUseableByPlayer(final EntityPlayer entityPlayer) {
        return this.worldObj.getTileEntity(this.pos) == this && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
    }
    
    @Override
    public String getName() {
        return (this != null) ? this.furnaceCustomName : "container.furnace";
    }
    
    @Override
    public boolean isItemValidForSlot(final int n, final ItemStack itemStack) {
        return n != 2 && (n != 1 || (itemStack <= 0 || SlotFurnaceFuel.isBucket(itemStack)));
    }
    
    @Override
    public boolean canInsertItem(final int n, final ItemStack itemStack, final EnumFacing enumFacing) {
        return this.isItemValidForSlot(n, itemStack);
    }
}
