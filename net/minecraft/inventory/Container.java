package net.minecraft.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public abstract class Container
{
    public List inventorySlots;
    private Set playerList;
    private int dragMode;
    private short transactionID;
    private int dragEvent;
    protected List crafters;
    public int windowId;
    private final Set dragSlots;
    public List inventoryItemStacks;
    
    public short getNextTransactionID(final InventoryPlayer inventoryPlayer) {
        return (short)(++this.transactionID);
    }
    
    public ItemStack transferStackInSlot(final EntityPlayer entityPlayer, final int n) {
        final Slot slot = this.inventorySlots.get(n);
        return (slot != null) ? slot.getStack() : null;
    }
    
    protected Slot addSlotToContainer(final Slot slot) {
        slot.slotNumber = this.inventorySlots.size();
        this.inventorySlots.add(slot);
        this.inventoryItemStacks.add(null);
        return slot;
    }
    
    public abstract boolean canInteractWith(final EntityPlayer p0);
    
    public static int calcRedstone(final TileEntity tileEntity) {
        return (tileEntity instanceof IInventory) ? calcRedstoneFromInventory((IInventory)tileEntity) : 0;
    }
    
    public boolean canDragIntoSlot(final Slot slot) {
        return true;
    }
    
    public List getInventory() {
        final ArrayList arrayList = Lists.newArrayList();
        while (0 < this.inventorySlots.size()) {
            arrayList.add(this.inventorySlots.get(0).getStack());
            int n = 0;
            ++n;
        }
        return arrayList;
    }
    
    public static int func_94534_d(final int n, final int n2) {
        return (n & 0x3) | (n2 & 0x3) << 2;
    }
    
    public static void computeStackSize(final Set set, final int n, final ItemStack itemStack, final int n2) {
        switch (n) {
            case 0: {
                itemStack.stackSize = MathHelper.floor_float(itemStack.stackSize / (float)set.size());
                break;
            }
            case 1: {
                itemStack.stackSize = 1;
                break;
            }
            case 2: {
                itemStack.stackSize = itemStack.getItem().getItemStackLimit();
                break;
            }
        }
        itemStack.stackSize += n2;
    }
    
    public void detectAndSendChanges() {
        while (0 < this.inventorySlots.size()) {
            final ItemStack stack = this.inventorySlots.get(0).getStack();
            if (!ItemStack.areItemStacksEqual(this.inventoryItemStacks.get(0), stack)) {
                final ItemStack itemStack = (stack == null) ? null : stack.copy();
                this.inventoryItemStacks.set(0, itemStack);
                while (0 < this.crafters.size()) {
                    this.crafters.get(0).sendSlotContents(this, 0, itemStack);
                    int n = 0;
                    ++n;
                }
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public void removeCraftingFromCrafters(final ICrafting crafting) {
        this.crafters.remove(crafting);
    }
    
    protected void retrySlotClick(final int n, final int n2, final boolean b, final EntityPlayer entityPlayer) {
        this.slotClick(n, n2, 1, entityPlayer);
    }
    
    public void setCanCraft(final EntityPlayer entityPlayer, final boolean b) {
        if (b) {
            this.playerList.remove(entityPlayer);
        }
        else {
            this.playerList.add(entityPlayer);
        }
    }
    
    public boolean canMergeSlot(final ItemStack itemStack, final Slot slot) {
        return true;
    }
    
    public void onCraftMatrixChanged(final IInventory inventory) {
        this.detectAndSendChanges();
    }
    
    public void onContainerClosed(final EntityPlayer entityPlayer) {
        final InventoryPlayer inventory = entityPlayer.inventory;
        if (inventory.getItemStack() != null) {
            entityPlayer.dropPlayerItemWithRandomChoice(inventory.getItemStack(), false);
            inventory.setItemStack(null);
        }
    }
    
    public static int extractDragMode(final int n) {
        return n >> 2 & 0x3;
    }
    
    public boolean enchantItem(final EntityPlayer entityPlayer, final int n) {
        return false;
    }
    
    public Container() {
        this.inventoryItemStacks = Lists.newArrayList();
        this.inventorySlots = Lists.newArrayList();
        this.dragMode = -1;
        this.dragSlots = Sets.newHashSet();
        this.crafters = Lists.newArrayList();
        this.playerList = Sets.newHashSet();
    }
    
    public Slot getSlot(final int n) {
        return this.inventorySlots.get(n);
    }
    
    public void updateProgressBar(final int n, final int n2) {
    }
    
    protected boolean mergeItemStack(final ItemStack itemStack, final int n, final int n2, final boolean b) {
        int n3 = n;
        if (b) {
            n3 = n2 - 1;
        }
        if (itemStack.isStackable()) {
            while (itemStack.stackSize > 0 && ((!b && n3 < n2) || (b && n3 >= n))) {
                final Slot slot = this.inventorySlots.get(n3);
                final ItemStack stack = slot.getStack();
                if (stack != null && stack.getItem() == itemStack.getItem() && (!itemStack.getHasSubtypes() || itemStack.getMetadata() == stack.getMetadata()) && ItemStack.areItemStackTagsEqual(itemStack, stack)) {
                    final int stackSize = stack.stackSize + itemStack.stackSize;
                    if (stackSize <= itemStack.getMaxStackSize()) {
                        itemStack.stackSize = 0;
                        stack.stackSize = stackSize;
                        slot.onSlotChanged();
                    }
                    else if (stack.stackSize < itemStack.getMaxStackSize()) {
                        itemStack.stackSize -= itemStack.getMaxStackSize() - stack.stackSize;
                        stack.stackSize = itemStack.getMaxStackSize();
                        slot.onSlotChanged();
                    }
                }
                if (b) {
                    --n3;
                }
                else {
                    ++n3;
                }
            }
        }
        if (itemStack.stackSize > 0) {
            int n4;
            if (b) {
                n4 = n2 - 1;
            }
            else {
                n4 = n;
            }
            while ((!b && n4 < n2) || (b && n4 >= n)) {
                final Slot slot2 = this.inventorySlots.get(n4);
                if (slot2.getStack() == null) {
                    slot2.putStack(itemStack.copy());
                    slot2.onSlotChanged();
                    itemStack.stackSize = 0;
                    break;
                }
                if (b) {
                    --n4;
                }
                else {
                    ++n4;
                }
            }
        }
        return true;
    }
    
    public static int calcRedstoneFromInventory(final IInventory inventory) {
        if (inventory == null) {
            return 0;
        }
        float n = 0.0f;
        while (0 < inventory.getSizeInventory()) {
            final ItemStack stackInSlot = inventory.getStackInSlot(0);
            if (stackInSlot != null) {
                n += stackInSlot.stackSize / (float)Math.min(inventory.getInventoryStackLimit(), stackInSlot.getMaxStackSize());
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
        return MathHelper.floor_float(n / inventory.getSizeInventory() * 14.0f) + 0;
    }
    
    public static int getDragEvent(final int n) {
        return n & 0x3;
    }
    
    public void putStacksInSlots(final ItemStack[] array) {
        while (0 < array.length) {
            this.getSlot(0).putStack(array[0]);
            int n = 0;
            ++n;
        }
    }
    
    public boolean getCanCraft(final EntityPlayer entityPlayer) {
        return !this.playerList.contains(entityPlayer);
    }
    
    protected void resetDrag() {
        this.dragEvent = 0;
        this.dragSlots.clear();
    }
    
    public void onCraftGuiOpened(final ICrafting crafting) {
        if (this.crafters.contains(crafting)) {
            throw new IllegalArgumentException("Listener already listening");
        }
        this.crafters.add(crafting);
        crafting.updateCraftingInventory(this, this.getInventory());
        this.detectAndSendChanges();
    }
    
    public void putStackInSlot(final int n, final ItemStack itemStack) {
        this.getSlot(n).putStack(itemStack);
    }
    
    public Slot getSlotFromInventory(final IInventory inventory, final int n) {
        while (0 < this.inventorySlots.size()) {
            final Slot slot = this.inventorySlots.get(0);
            if (slot.isHere(inventory, n)) {
                return slot;
            }
            int n2 = 0;
            ++n2;
        }
        return null;
    }
    
    public ItemStack slotClick(final int p0, final int p1, final int p2, final EntityPlayer p3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: astore          5
        //     3: aload           4
        //     5: getfield        net/minecraft/entity/player/EntityPlayer.inventory:Lnet/minecraft/entity/player/InventoryPlayer;
        //     8: astore          6
        //    10: iload_3        
        //    11: iconst_5       
        //    12: if_icmpne       505
        //    15: aload_0        
        //    16: getfield        net/minecraft/inventory/Container.dragEvent:I
        //    19: istore          7
        //    21: aload_0        
        //    22: iload_2        
        //    23: invokestatic    net/minecraft/inventory/Container.getDragEvent:(I)I
        //    26: putfield        net/minecraft/inventory/Container.dragEvent:I
        //    29: iload           7
        //    31: iconst_1       
        //    32: if_icmpne       43
        //    35: aload_0        
        //    36: getfield        net/minecraft/inventory/Container.dragEvent:I
        //    39: iconst_2       
        //    40: if_icmpeq       59
        //    43: iload           7
        //    45: aload_0        
        //    46: getfield        net/minecraft/inventory/Container.dragEvent:I
        //    49: if_icmpeq       59
        //    52: aload_0        
        //    53: invokevirtual   net/minecraft/inventory/Container.resetDrag:()V
        //    56: goto            2032
        //    59: aload           6
        //    61: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //    64: ifnonnull       74
        //    67: aload_0        
        //    68: invokevirtual   net/minecraft/inventory/Container.resetDrag:()V
        //    71: goto            2032
        //    74: aload_0        
        //    75: getfield        net/minecraft/inventory/Container.dragEvent:I
        //    78: ifne            122
        //    81: aload_0        
        //    82: iload_2        
        //    83: invokestatic    net/minecraft/inventory/Container.extractDragMode:(I)I
        //    86: putfield        net/minecraft/inventory/Container.dragMode:I
        //    89: aload_0        
        //    90: getfield        net/minecraft/inventory/Container.dragMode:I
        //    93: aload           4
        //    95: ifne            115
        //    98: aload_0        
        //    99: iconst_1       
        //   100: putfield        net/minecraft/inventory/Container.dragEvent:I
        //   103: aload_0        
        //   104: getfield        net/minecraft/inventory/Container.dragSlots:Ljava/util/Set;
        //   107: invokeinterface java/util/Set.clear:()V
        //   112: goto            2032
        //   115: aload_0        
        //   116: invokevirtual   net/minecraft/inventory/Container.resetDrag:()V
        //   119: goto            2032
        //   122: aload_0        
        //   123: getfield        net/minecraft/inventory/Container.dragEvent:I
        //   126: iconst_1       
        //   127: if_icmpne       218
        //   130: aload_0        
        //   131: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //   134: iload_1        
        //   135: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   140: checkcast       Lnet/minecraft/inventory/Slot;
        //   143: astore          8
        //   145: aload           8
        //   147: ifnull          215
        //   150: aload           8
        //   152: aload           6
        //   154: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   157: iconst_1       
        //   158: ifnull          215
        //   161: aload           8
        //   163: aload           6
        //   165: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   168: invokevirtual   net/minecraft/inventory/Slot.isItemValid:(Lnet/minecraft/item/ItemStack;)Z
        //   171: ifeq            215
        //   174: aload           6
        //   176: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   179: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   182: aload_0        
        //   183: getfield        net/minecraft/inventory/Container.dragSlots:Ljava/util/Set;
        //   186: invokeinterface java/util/Set.size:()I
        //   191: if_icmple       215
        //   194: aload_0        
        //   195: aload           8
        //   197: invokevirtual   net/minecraft/inventory/Container.canDragIntoSlot:(Lnet/minecraft/inventory/Slot;)Z
        //   200: ifeq            215
        //   203: aload_0        
        //   204: getfield        net/minecraft/inventory/Container.dragSlots:Ljava/util/Set;
        //   207: aload           8
        //   209: invokeinterface java/util/Set.add:(Ljava/lang/Object;)Z
        //   214: pop            
        //   215: goto            2032
        //   218: aload_0        
        //   219: getfield        net/minecraft/inventory/Container.dragEvent:I
        //   222: iconst_2       
        //   223: if_icmpne       498
        //   226: aload_0        
        //   227: getfield        net/minecraft/inventory/Container.dragSlots:Ljava/util/Set;
        //   230: invokeinterface java/util/Set.isEmpty:()Z
        //   235: ifne            491
        //   238: aload           6
        //   240: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   243: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //   246: astore          8
        //   248: aload           6
        //   250: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   253: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   256: istore          9
        //   258: aload_0        
        //   259: getfield        net/minecraft/inventory/Container.dragSlots:Ljava/util/Set;
        //   262: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   267: astore          10
        //   269: aload           10
        //   271: invokeinterface java/util/Iterator.hasNext:()Z
        //   276: ifeq            466
        //   279: aload           10
        //   281: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   286: checkcast       Lnet/minecraft/inventory/Slot;
        //   289: astore          11
        //   291: aload           11
        //   293: ifnull          463
        //   296: aload           11
        //   298: aload           6
        //   300: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   303: iconst_1       
        //   304: ifnull          463
        //   307: aload           11
        //   309: aload           6
        //   311: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   314: invokevirtual   net/minecraft/inventory/Slot.isItemValid:(Lnet/minecraft/item/ItemStack;)Z
        //   317: ifeq            463
        //   320: aload           6
        //   322: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   325: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   328: aload_0        
        //   329: getfield        net/minecraft/inventory/Container.dragSlots:Ljava/util/Set;
        //   332: invokeinterface java/util/Set.size:()I
        //   337: if_icmplt       463
        //   340: aload_0        
        //   341: aload           11
        //   343: invokevirtual   net/minecraft/inventory/Container.canDragIntoSlot:(Lnet/minecraft/inventory/Slot;)Z
        //   346: ifeq            463
        //   349: aload           8
        //   351: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //   354: astore          12
        //   356: aload           11
        //   358: invokevirtual   net/minecraft/inventory/Slot.getHasStack:()Z
        //   361: ifeq            375
        //   364: aload           11
        //   366: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //   369: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   372: goto            376
        //   375: iconst_0       
        //   376: istore          13
        //   378: aload_0        
        //   379: getfield        net/minecraft/inventory/Container.dragSlots:Ljava/util/Set;
        //   382: aload_0        
        //   383: getfield        net/minecraft/inventory/Container.dragMode:I
        //   386: aload           12
        //   388: iload           13
        //   390: invokestatic    net/minecraft/inventory/Container.computeStackSize:(Ljava/util/Set;ILnet/minecraft/item/ItemStack;I)V
        //   393: aload           12
        //   395: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   398: aload           12
        //   400: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //   403: if_icmple       416
        //   406: aload           12
        //   408: aload           12
        //   410: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //   413: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   416: aload           12
        //   418: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   421: aload           11
        //   423: aload           12
        //   425: invokevirtual   net/minecraft/inventory/Slot.getItemStackLimit:(Lnet/minecraft/item/ItemStack;)I
        //   428: if_icmple       443
        //   431: aload           12
        //   433: aload           11
        //   435: aload           12
        //   437: invokevirtual   net/minecraft/inventory/Slot.getItemStackLimit:(Lnet/minecraft/item/ItemStack;)I
        //   440: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   443: iload           9
        //   445: aload           12
        //   447: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   450: iload           13
        //   452: isub           
        //   453: isub           
        //   454: istore          9
        //   456: aload           11
        //   458: aload           12
        //   460: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //   463: goto            269
        //   466: aload           8
        //   468: iload           9
        //   470: putfield        net/minecraft/item/ItemStack.stackSize:I
        //   473: aload           8
        //   475: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   478: ifgt            484
        //   481: aconst_null    
        //   482: astore          8
        //   484: aload           6
        //   486: aload           8
        //   488: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setItemStack:(Lnet/minecraft/item/ItemStack;)V
        //   491: aload_0        
        //   492: invokevirtual   net/minecraft/inventory/Container.resetDrag:()V
        //   495: goto            2032
        //   498: aload_0        
        //   499: invokevirtual   net/minecraft/inventory/Container.resetDrag:()V
        //   502: goto            2032
        //   505: aload_0        
        //   506: getfield        net/minecraft/inventory/Container.dragEvent:I
        //   509: ifeq            519
        //   512: aload_0        
        //   513: invokevirtual   net/minecraft/inventory/Container.resetDrag:()V
        //   516: goto            2032
        //   519: iload_3        
        //   520: ifeq            528
        //   523: iload_3        
        //   524: iconst_1       
        //   525: if_icmpne       1280
        //   528: iload_2        
        //   529: ifeq            537
        //   532: iload_2        
        //   533: iconst_1       
        //   534: if_icmpne       1280
        //   537: iload_1        
        //   538: sipush          -999
        //   541: if_icmpne       621
        //   544: aload           6
        //   546: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   549: ifnull          2032
        //   552: iload_2        
        //   553: ifne            577
        //   556: aload           4
        //   558: aload           6
        //   560: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   563: iconst_1       
        //   564: invokevirtual   net/minecraft/entity/player/EntityPlayer.dropPlayerItemWithRandomChoice:(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;
        //   567: pop            
        //   568: aload           6
        //   570: aconst_null    
        //   571: checkcast       Lnet/minecraft/item/ItemStack;
        //   574: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setItemStack:(Lnet/minecraft/item/ItemStack;)V
        //   577: iload_2        
        //   578: iconst_1       
        //   579: if_icmpne       2032
        //   582: aload           4
        //   584: aload           6
        //   586: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   589: iconst_1       
        //   590: invokevirtual   net/minecraft/item/ItemStack.splitStack:(I)Lnet/minecraft/item/ItemStack;
        //   593: iconst_1       
        //   594: invokevirtual   net/minecraft/entity/player/EntityPlayer.dropPlayerItemWithRandomChoice:(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;
        //   597: pop            
        //   598: aload           6
        //   600: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   603: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   606: ifne            2032
        //   609: aload           6
        //   611: aconst_null    
        //   612: checkcast       Lnet/minecraft/item/ItemStack;
        //   615: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setItemStack:(Lnet/minecraft/item/ItemStack;)V
        //   618: goto            2032
        //   621: iload_3        
        //   622: iconst_1       
        //   623: if_icmpne       723
        //   626: iload_1        
        //   627: ifge            632
        //   630: aconst_null    
        //   631: areturn        
        //   632: aload_0        
        //   633: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //   636: iload_1        
        //   637: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   642: checkcast       Lnet/minecraft/inventory/Slot;
        //   645: astore          7
        //   647: aload           7
        //   649: ifnull          720
        //   652: aload           7
        //   654: aload           4
        //   656: invokevirtual   net/minecraft/inventory/Slot.canTakeStack:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //   659: ifeq            720
        //   662: aload_0        
        //   663: aload           4
        //   665: iload_1        
        //   666: invokevirtual   net/minecraft/inventory/Container.transferStackInSlot:(Lnet/minecraft/entity/player/EntityPlayer;I)Lnet/minecraft/item/ItemStack;
        //   669: astore          8
        //   671: aload           8
        //   673: ifnull          720
        //   676: aload           8
        //   678: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   681: astore          9
        //   683: aload           8
        //   685: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //   688: astore          5
        //   690: aload           7
        //   692: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //   695: ifnull          720
        //   698: aload           7
        //   700: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //   703: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   706: aload           9
        //   708: if_acmpne       720
        //   711: aload_0        
        //   712: iload_1        
        //   713: iload_2        
        //   714: iconst_1       
        //   715: aload           4
        //   717: invokevirtual   net/minecraft/inventory/Container.retrySlotClick:(IIZLnet/minecraft/entity/player/EntityPlayer;)V
        //   720: goto            2032
        //   723: iload_1        
        //   724: ifge            729
        //   727: aconst_null    
        //   728: areturn        
        //   729: aload_0        
        //   730: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //   733: iload_1        
        //   734: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   739: checkcast       Lnet/minecraft/inventory/Slot;
        //   742: astore          7
        //   744: aload           7
        //   746: ifnull          1277
        //   749: aload           7
        //   751: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //   754: astore          8
        //   756: aload           6
        //   758: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   761: astore          9
        //   763: aload           8
        //   765: ifnull          775
        //   768: aload           8
        //   770: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //   773: astore          5
        //   775: aload           8
        //   777: ifnonnull       870
        //   780: aload           9
        //   782: ifnull          1272
        //   785: aload           7
        //   787: aload           9
        //   789: invokevirtual   net/minecraft/inventory/Slot.isItemValid:(Lnet/minecraft/item/ItemStack;)Z
        //   792: ifeq            1272
        //   795: iload_2        
        //   796: ifne            807
        //   799: aload           9
        //   801: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   804: goto            808
        //   807: iconst_1       
        //   808: istore          10
        //   810: iconst_m1      
        //   811: aload           7
        //   813: aload           9
        //   815: invokevirtual   net/minecraft/inventory/Slot.getItemStackLimit:(Lnet/minecraft/item/ItemStack;)I
        //   818: if_icmple       830
        //   821: aload           7
        //   823: aload           9
        //   825: invokevirtual   net/minecraft/inventory/Slot.getItemStackLimit:(Lnet/minecraft/item/ItemStack;)I
        //   828: istore          10
        //   830: aload           9
        //   832: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   835: iconst_m1      
        //   836: if_icmplt       850
        //   839: aload           7
        //   841: aload           9
        //   843: iconst_m1      
        //   844: invokevirtual   net/minecraft/item/ItemStack.splitStack:(I)Lnet/minecraft/item/ItemStack;
        //   847: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //   850: aload           9
        //   852: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   855: ifne            867
        //   858: aload           6
        //   860: aconst_null    
        //   861: checkcast       Lnet/minecraft/item/ItemStack;
        //   864: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setItemStack:(Lnet/minecraft/item/ItemStack;)V
        //   867: goto            1272
        //   870: aload           7
        //   872: aload           4
        //   874: invokevirtual   net/minecraft/inventory/Slot.canTakeStack:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //   877: ifeq            1272
        //   880: aload           9
        //   882: ifnonnull       955
        //   885: iload_2        
        //   886: ifne            897
        //   889: aload           8
        //   891: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   894: goto            906
        //   897: aload           8
        //   899: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   902: iconst_1       
        //   903: iadd           
        //   904: iconst_2       
        //   905: idiv           
        //   906: istore          10
        //   908: aload           7
        //   910: iconst_m1      
        //   911: invokevirtual   net/minecraft/inventory/Slot.decrStackSize:(I)Lnet/minecraft/item/ItemStack;
        //   914: astore          11
        //   916: aload           6
        //   918: aload           11
        //   920: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setItemStack:(Lnet/minecraft/item/ItemStack;)V
        //   923: aload           8
        //   925: getfield        net/minecraft/item/ItemStack.stackSize:I
        //   928: ifne            940
        //   931: aload           7
        //   933: aconst_null    
        //   934: checkcast       Lnet/minecraft/item/ItemStack;
        //   937: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //   940: aload           7
        //   942: aload           4
        //   944: aload           6
        //   946: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //   949: invokevirtual   net/minecraft/inventory/Slot.onPickupFromSlot:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V
        //   952: goto            1272
        //   955: aload           7
        //   957: aload           9
        //   959: invokevirtual   net/minecraft/inventory/Slot.isItemValid:(Lnet/minecraft/item/ItemStack;)Z
        //   962: ifeq            1146
        //   965: aload           8
        //   967: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   970: aload           9
        //   972: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   975: if_acmpne       1114
        //   978: aload           8
        //   980: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //   983: aload           9
        //   985: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //   988: if_icmpne       1114
        //   991: aload           8
        //   993: aload           9
        //   995: invokestatic    net/minecraft/item/ItemStack.areItemStackTagsEqual:(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z
        //   998: ifeq            1114
        //  1001: iload_2        
        //  1002: ifne            1013
        //  1005: aload           9
        //  1007: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1010: goto            1014
        //  1013: iconst_1       
        //  1014: istore          10
        //  1016: iconst_m1      
        //  1017: aload           7
        //  1019: aload           9
        //  1021: invokevirtual   net/minecraft/inventory/Slot.getItemStackLimit:(Lnet/minecraft/item/ItemStack;)I
        //  1024: aload           8
        //  1026: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1029: isub           
        //  1030: if_icmple       1048
        //  1033: aload           7
        //  1035: aload           9
        //  1037: invokevirtual   net/minecraft/inventory/Slot.getItemStackLimit:(Lnet/minecraft/item/ItemStack;)I
        //  1040: aload           8
        //  1042: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1045: isub           
        //  1046: istore          10
        //  1048: iconst_m1      
        //  1049: aload           9
        //  1051: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //  1054: aload           8
        //  1056: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1059: isub           
        //  1060: if_icmple       1076
        //  1063: aload           9
        //  1065: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //  1068: aload           8
        //  1070: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1073: isub           
        //  1074: istore          10
        //  1076: aload           9
        //  1078: iconst_m1      
        //  1079: invokevirtual   net/minecraft/item/ItemStack.splitStack:(I)Lnet/minecraft/item/ItemStack;
        //  1082: pop            
        //  1083: aload           9
        //  1085: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1088: ifne            1100
        //  1091: aload           6
        //  1093: aconst_null    
        //  1094: checkcast       Lnet/minecraft/item/ItemStack;
        //  1097: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setItemStack:(Lnet/minecraft/item/ItemStack;)V
        //  1100: aload           8
        //  1102: dup            
        //  1103: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1106: iconst_m1      
        //  1107: iadd           
        //  1108: putfield        net/minecraft/item/ItemStack.stackSize:I
        //  1111: goto            1272
        //  1114: aload           9
        //  1116: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1119: aload           7
        //  1121: aload           9
        //  1123: invokevirtual   net/minecraft/inventory/Slot.getItemStackLimit:(Lnet/minecraft/item/ItemStack;)I
        //  1126: if_icmpgt       1272
        //  1129: aload           7
        //  1131: aload           9
        //  1133: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //  1136: aload           6
        //  1138: aload           8
        //  1140: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setItemStack:(Lnet/minecraft/item/ItemStack;)V
        //  1143: goto            1272
        //  1146: aload           8
        //  1148: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //  1151: aload           9
        //  1153: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //  1156: if_acmpne       1272
        //  1159: aload           9
        //  1161: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //  1164: iconst_1       
        //  1165: if_icmple       1272
        //  1168: aload           8
        //  1170: invokevirtual   net/minecraft/item/ItemStack.getHasSubtypes:()Z
        //  1173: ifeq            1189
        //  1176: aload           8
        //  1178: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //  1181: aload           9
        //  1183: invokevirtual   net/minecraft/item/ItemStack.getMetadata:()I
        //  1186: if_icmpne       1272
        //  1189: aload           8
        //  1191: aload           9
        //  1193: invokestatic    net/minecraft/item/ItemStack.areItemStackTagsEqual:(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Z
        //  1196: ifeq            1272
        //  1199: aload           8
        //  1201: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1204: istore          10
        //  1206: goto            1272
        //  1209: iconst_m1      
        //  1210: aload           9
        //  1212: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1215: iadd           
        //  1216: aload           9
        //  1218: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //  1221: if_icmpgt       1272
        //  1224: aload           9
        //  1226: dup            
        //  1227: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1230: iconst_m1      
        //  1231: iadd           
        //  1232: putfield        net/minecraft/item/ItemStack.stackSize:I
        //  1235: aload           7
        //  1237: iconst_m1      
        //  1238: invokevirtual   net/minecraft/inventory/Slot.decrStackSize:(I)Lnet/minecraft/item/ItemStack;
        //  1241: astore          8
        //  1243: aload           8
        //  1245: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1248: ifne            1260
        //  1251: aload           7
        //  1253: aconst_null    
        //  1254: checkcast       Lnet/minecraft/item/ItemStack;
        //  1257: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //  1260: aload           7
        //  1262: aload           4
        //  1264: aload           6
        //  1266: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //  1269: invokevirtual   net/minecraft/inventory/Slot.onPickupFromSlot:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V
        //  1272: aload           7
        //  1274: invokevirtual   net/minecraft/inventory/Slot.onSlotChanged:()V
        //  1277: goto            2032
        //  1280: iload_3        
        //  1281: iconst_2       
        //  1282: if_icmpne       1557
        //  1285: iload_2        
        //  1286: iflt            1557
        //  1289: iload_2        
        //  1290: bipush          9
        //  1292: if_icmpge       1557
        //  1295: aload_0        
        //  1296: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //  1299: iload_1        
        //  1300: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //  1305: checkcast       Lnet/minecraft/inventory/Slot;
        //  1308: astore          7
        //  1310: aload           7
        //  1312: aload           4
        //  1314: invokevirtual   net/minecraft/inventory/Slot.canTakeStack:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //  1317: ifeq            1554
        //  1320: aload           6
        //  1322: iload_2        
        //  1323: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getStackInSlot:(I)Lnet/minecraft/item/ItemStack;
        //  1326: astore          8
        //  1328: aload           8
        //  1330: ifnull          1353
        //  1333: aload           7
        //  1335: getfield        net/minecraft/inventory/Slot.inventory:Lnet/minecraft/inventory/IInventory;
        //  1338: aload           6
        //  1340: if_acmpne       1357
        //  1343: aload           7
        //  1345: aload           8
        //  1347: invokevirtual   net/minecraft/inventory/Slot.isItemValid:(Lnet/minecraft/item/ItemStack;)Z
        //  1350: ifeq            1357
        //  1353: iconst_1       
        //  1354: goto            1358
        //  1357: iconst_0       
        //  1358: istore          9
        //  1360: iload           9
        //  1362: ifne            1385
        //  1365: aload           6
        //  1367: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getFirstEmptyStack:()I
        //  1370: istore          10
        //  1372: iload           9
        //  1374: goto            1381
        //  1377: iconst_1       
        //  1378: goto            1382
        //  1381: iconst_0       
        //  1382: ior            
        //  1383: istore          9
        //  1385: aload           7
        //  1387: invokevirtual   net/minecraft/inventory/Slot.getHasStack:()Z
        //  1390: ifeq            1514
        //  1393: iload           9
        //  1395: ifeq            1514
        //  1398: aload           7
        //  1400: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //  1403: astore          11
        //  1405: aload           6
        //  1407: iload_2        
        //  1408: aload           11
        //  1410: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //  1413: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //  1416: aload           7
        //  1418: getfield        net/minecraft/inventory/Slot.inventory:Lnet/minecraft/inventory/IInventory;
        //  1421: aload           6
        //  1423: if_acmpne       1436
        //  1426: aload           7
        //  1428: aload           8
        //  1430: invokevirtual   net/minecraft/inventory/Slot.isItemValid:(Lnet/minecraft/item/ItemStack;)Z
        //  1433: ifne            1484
        //  1436: aload           8
        //  1438: ifnull          1484
        //  1441: goto            1554
        //  1444: aload           6
        //  1446: aload           8
        //  1448: invokevirtual   net/minecraft/entity/player/InventoryPlayer.addItemStackToInventory:(Lnet/minecraft/item/ItemStack;)Z
        //  1451: pop            
        //  1452: aload           7
        //  1454: aload           11
        //  1456: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1459: invokevirtual   net/minecraft/inventory/Slot.decrStackSize:(I)Lnet/minecraft/item/ItemStack;
        //  1462: pop            
        //  1463: aload           7
        //  1465: aconst_null    
        //  1466: checkcast       Lnet/minecraft/item/ItemStack;
        //  1469: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //  1472: aload           7
        //  1474: aload           4
        //  1476: aload           11
        //  1478: invokevirtual   net/minecraft/inventory/Slot.onPickupFromSlot:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V
        //  1481: goto            1554
        //  1484: aload           7
        //  1486: aload           11
        //  1488: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1491: invokevirtual   net/minecraft/inventory/Slot.decrStackSize:(I)Lnet/minecraft/item/ItemStack;
        //  1494: pop            
        //  1495: aload           7
        //  1497: aload           8
        //  1499: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //  1502: aload           7
        //  1504: aload           4
        //  1506: aload           11
        //  1508: invokevirtual   net/minecraft/inventory/Slot.onPickupFromSlot:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V
        //  1511: goto            2032
        //  1514: aload           7
        //  1516: invokevirtual   net/minecraft/inventory/Slot.getHasStack:()Z
        //  1519: ifne            1554
        //  1522: aload           8
        //  1524: ifnull          1554
        //  1527: aload           7
        //  1529: aload           8
        //  1531: invokevirtual   net/minecraft/inventory/Slot.isItemValid:(Lnet/minecraft/item/ItemStack;)Z
        //  1534: ifeq            1554
        //  1537: aload           6
        //  1539: iload_2        
        //  1540: aconst_null    
        //  1541: checkcast       Lnet/minecraft/item/ItemStack;
        //  1544: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setInventorySlotContents:(ILnet/minecraft/item/ItemStack;)V
        //  1547: aload           7
        //  1549: aload           8
        //  1551: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //  1554: goto            2032
        //  1557: iload_3        
        //  1558: iconst_3       
        //  1559: if_icmpne       1643
        //  1562: aload           4
        //  1564: getfield        net/minecraft/entity/player/EntityPlayer.capabilities:Lnet/minecraft/entity/player/PlayerCapabilities;
        //  1567: getfield        net/minecraft/entity/player/PlayerCapabilities.isCreativeMode:Z
        //  1570: ifeq            1643
        //  1573: aload           6
        //  1575: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //  1578: ifnonnull       1643
        //  1581: iload_1        
        //  1582: iflt            1643
        //  1585: aload_0        
        //  1586: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //  1589: iload_1        
        //  1590: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //  1595: checkcast       Lnet/minecraft/inventory/Slot;
        //  1598: astore          7
        //  1600: aload           7
        //  1602: ifnull          1640
        //  1605: aload           7
        //  1607: invokevirtual   net/minecraft/inventory/Slot.getHasStack:()Z
        //  1610: ifeq            1640
        //  1613: aload           7
        //  1615: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //  1618: invokevirtual   net/minecraft/item/ItemStack.copy:()Lnet/minecraft/item/ItemStack;
        //  1621: astore          8
        //  1623: aload           8
        //  1625: aload           8
        //  1627: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //  1630: putfield        net/minecraft/item/ItemStack.stackSize:I
        //  1633: aload           6
        //  1635: aload           8
        //  1637: invokevirtual   net/minecraft/entity/player/InventoryPlayer.setItemStack:(Lnet/minecraft/item/ItemStack;)V
        //  1640: goto            2032
        //  1643: iload_3        
        //  1644: iconst_4       
        //  1645: if_icmpne       1742
        //  1648: aload           6
        //  1650: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //  1653: ifnonnull       1742
        //  1656: iload_1        
        //  1657: iflt            1742
        //  1660: aload_0        
        //  1661: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //  1664: iload_1        
        //  1665: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //  1670: checkcast       Lnet/minecraft/inventory/Slot;
        //  1673: astore          7
        //  1675: aload           7
        //  1677: ifnull          1739
        //  1680: aload           7
        //  1682: invokevirtual   net/minecraft/inventory/Slot.getHasStack:()Z
        //  1685: ifeq            1739
        //  1688: aload           7
        //  1690: aload           4
        //  1692: invokevirtual   net/minecraft/inventory/Slot.canTakeStack:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //  1695: ifeq            1739
        //  1698: aload           7
        //  1700: iload_2        
        //  1701: ifne            1708
        //  1704: iconst_1       
        //  1705: goto            1716
        //  1708: aload           7
        //  1710: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //  1713: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1716: invokevirtual   net/minecraft/inventory/Slot.decrStackSize:(I)Lnet/minecraft/item/ItemStack;
        //  1719: astore          8
        //  1721: aload           7
        //  1723: aload           4
        //  1725: aload           8
        //  1727: invokevirtual   net/minecraft/inventory/Slot.onPickupFromSlot:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V
        //  1730: aload           4
        //  1732: aload           8
        //  1734: iconst_1       
        //  1735: invokevirtual   net/minecraft/entity/player/EntityPlayer.dropPlayerItemWithRandomChoice:(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/item/EntityItem;
        //  1738: pop            
        //  1739: goto            2032
        //  1742: iload_3        
        //  1743: bipush          6
        //  1745: if_icmpne       2032
        //  1748: iload_1        
        //  1749: iflt            2032
        //  1752: aload_0        
        //  1753: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //  1756: iload_1        
        //  1757: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //  1762: checkcast       Lnet/minecraft/inventory/Slot;
        //  1765: astore          7
        //  1767: aload           6
        //  1769: invokevirtual   net/minecraft/entity/player/InventoryPlayer.getItemStack:()Lnet/minecraft/item/ItemStack;
        //  1772: astore          8
        //  1774: aload           8
        //  1776: ifnull          2028
        //  1779: aload           7
        //  1781: ifnull          1802
        //  1784: aload           7
        //  1786: invokevirtual   net/minecraft/inventory/Slot.getHasStack:()Z
        //  1789: ifeq            1802
        //  1792: aload           7
        //  1794: aload           4
        //  1796: invokevirtual   net/minecraft/inventory/Slot.canTakeStack:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //  1799: ifne            2028
        //  1802: iload_2        
        //  1803: ifne            1810
        //  1806: iconst_0       
        //  1807: goto            1821
        //  1810: aload_0        
        //  1811: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //  1814: invokeinterface java/util/List.size:()I
        //  1819: iconst_1       
        //  1820: isub           
        //  1821: istore          9
        //  1823: iload_2        
        //  1824: ifne            1831
        //  1827: iconst_1       
        //  1828: goto            1832
        //  1831: iconst_m1      
        //  1832: istore          10
        //  1834: iload           9
        //  1836: istore          12
        //  1838: iload           12
        //  1840: iflt            2022
        //  1843: iload           12
        //  1845: aload_0        
        //  1846: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //  1849: invokeinterface java/util/List.size:()I
        //  1854: if_icmpge       2022
        //  1857: aload           8
        //  1859: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1862: aload           8
        //  1864: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //  1867: if_icmpge       2022
        //  1870: aload_0        
        //  1871: getfield        net/minecraft/inventory/Container.inventorySlots:Ljava/util/List;
        //  1874: iload           12
        //  1876: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //  1881: checkcast       Lnet/minecraft/inventory/Slot;
        //  1884: astore          13
        //  1886: aload           13
        //  1888: invokevirtual   net/minecraft/inventory/Slot.getHasStack:()Z
        //  1891: ifeq            2013
        //  1894: aload           13
        //  1896: aload           8
        //  1898: iconst_1       
        //  1899: ifnull          2013
        //  1902: aload           13
        //  1904: aload           4
        //  1906: invokevirtual   net/minecraft/inventory/Slot.canTakeStack:(Lnet/minecraft/entity/player/EntityPlayer;)Z
        //  1909: ifeq            2013
        //  1912: aload_0        
        //  1913: aload           8
        //  1915: aload           13
        //  1917: invokevirtual   net/minecraft/inventory/Container.canMergeSlot:(Lnet/minecraft/item/ItemStack;Lnet/minecraft/inventory/Slot;)Z
        //  1920: ifeq            2013
        //  1923: aload           13
        //  1925: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //  1928: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1931: aload           13
        //  1933: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //  1936: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //  1939: if_icmpeq       2013
        //  1942: aload           8
        //  1944: invokevirtual   net/minecraft/item/ItemStack.getMaxStackSize:()I
        //  1947: aload           8
        //  1949: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1952: isub           
        //  1953: aload           13
        //  1955: invokevirtual   net/minecraft/inventory/Slot.getStack:()Lnet/minecraft/item/ItemStack;
        //  1958: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1961: invokestatic    java/lang/Math.min:(II)I
        //  1964: istore          14
        //  1966: aload           13
        //  1968: iload           14
        //  1970: invokevirtual   net/minecraft/inventory/Slot.decrStackSize:(I)Lnet/minecraft/item/ItemStack;
        //  1973: astore          15
        //  1975: aload           8
        //  1977: dup            
        //  1978: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1981: iload           14
        //  1983: iadd           
        //  1984: putfield        net/minecraft/item/ItemStack.stackSize:I
        //  1987: aload           15
        //  1989: getfield        net/minecraft/item/ItemStack.stackSize:I
        //  1992: ifgt            2004
        //  1995: aload           13
        //  1997: aconst_null    
        //  1998: checkcast       Lnet/minecraft/item/ItemStack;
        //  2001: invokevirtual   net/minecraft/inventory/Slot.putStack:(Lnet/minecraft/item/ItemStack;)V
        //  2004: aload           13
        //  2006: aload           4
        //  2008: aload           15
        //  2010: invokevirtual   net/minecraft/inventory/Slot.onPickupFromSlot:(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V
        //  2013: iload           12
        //  2015: iconst_m1      
        //  2016: iadd           
        //  2017: istore          12
        //  2019: goto            1838
        //  2022: iinc            11, 1
        //  2025: goto            1834
        //  2028: aload_0        
        //  2029: invokevirtual   net/minecraft/inventory/Container.detectAndSendChanges:()V
        //  2032: aload           5
        //  2034: areturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #2013 (coming from #1899).
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
}
