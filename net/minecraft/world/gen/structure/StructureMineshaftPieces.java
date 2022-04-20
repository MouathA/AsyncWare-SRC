package net.minecraft.world.gen.structure;

import net.minecraft.init.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import java.util.*;

public class StructureMineshaftPieces
{
    private static final List CHEST_CONTENT_WEIGHT_LIST;
    
    static {
        CHEST_CONTENT_WEIGHT_LIST = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5), new WeightedRandomChestContent(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1) });
    }
    
    static List access$100() {
        return StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST;
    }
    
    public static void registerStructurePieces() {
        MapGenStructureIO.registerStructureComponent(Corridor.class, "MSCorridor");
        MapGenStructureIO.registerStructureComponent(Cross.class, "MSCrossing");
        MapGenStructureIO.registerStructureComponent(Room.class, "MSRoom");
        MapGenStructureIO.registerStructureComponent(Stairs.class, "MSStairs");
    }
    
    private static StructureComponent func_175890_b(final StructureComponent structureComponent, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        if (n4 > 8) {
            return null;
        }
        if (Math.abs(n - structureComponent.getBoundingBox().minX) <= 80 && Math.abs(n3 - structureComponent.getBoundingBox().minZ) <= 80) {
            final StructureComponent func_175892_a = func_175892_a(list, random, n, n2, n3, enumFacing, n4 + 1);
            if (func_175892_a != null) {
                list.add(func_175892_a);
                func_175892_a.buildComponent(structureComponent, list, random);
            }
            return func_175892_a;
        }
        return null;
    }
    
    private static StructureComponent func_175892_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        final int nextInt = random.nextInt(100);
        if (nextInt >= 80) {
            final StructureBoundingBox func_175813_a = Cross.func_175813_a(list, random, n, n2, n3, enumFacing);
            if (func_175813_a != null) {
                return new Cross(n4, random, func_175813_a, enumFacing);
            }
        }
        else if (nextInt >= 70) {
            final StructureBoundingBox func_175812_a = Stairs.func_175812_a(list, random, n, n2, n3, enumFacing);
            if (func_175812_a != null) {
                return new Stairs(n4, random, func_175812_a, enumFacing);
            }
        }
        else {
            final StructureBoundingBox func_175814_a = Corridor.func_175814_a(list, random, n, n2, n3, enumFacing);
            if (func_175814_a != null) {
                return new Corridor(n4, random, func_175814_a, enumFacing);
            }
        }
        return null;
    }
    
    static StructureComponent access$000(final StructureComponent structureComponent, final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing, final int n4) {
        return func_175890_b(structureComponent, list, random, n, n2, n3, enumFacing, n4);
    }
    
    public static class Corridor extends StructureComponent
    {
        private int sectionCount;
        private boolean spawnerPlaced;
        private boolean hasRails;
        private boolean hasSpiders;
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            final int n = this.sectionCount * 5 - 1;
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 2, 1, n, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.func_175805_a(world, structureBoundingBox, random, 0.8f, 0, 2, 0, 2, 2, n, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            if (this.hasSpiders) {
                this.func_175805_a(world, structureBoundingBox, random, 0.6f, 0, 0, 0, 2, 1, n, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            int n3 = 0;
            while (0 < this.sectionCount) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 1, 0, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, 2, 0, 0, 2, 1, 0, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
                if (random.nextInt(4) == 0) {
                    this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 0, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
                    this.fillWithBlocks(world, structureBoundingBox, 2, 2, 0, 2, 2, 0, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
                }
                else {
                    this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 2, 2, 0, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
                }
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, 0, 2, -1, Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, 2, 2, -1, Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, 0, 2, 1, Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1f, 2, 2, 1, Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, 0, 2, -2, Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, 2, 2, -2, Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, 0, 2, 2, Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, 2, 2, 2, Blocks.web.getDefaultState());
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, 1, 2, -1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
                this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05f, 1, 2, 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
                if (random.nextInt(100) == 0) {
                    this.generateChestContents(world, structureBoundingBox, random, 2, 0, -1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.access$100(), Items.enchanted_book.getRandom(random)), 3 + random.nextInt(4));
                }
                if (random.nextInt(100) == 0) {
                    this.generateChestContents(world, structureBoundingBox, random, 0, 0, 1, WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.access$100(), Items.enchanted_book.getRandom(random)), 3 + random.nextInt(4));
                }
                if (this.hasSpiders && !this.spawnerPlaced) {
                    this.getYWithOffset(0);
                    final int n2 = -1 + random.nextInt(3);
                    this.getXWithOffset(1, n2);
                    final BlockPos blockPos = new BlockPos(-1, -1, this.getZWithOffset(1, n2));
                    if (structureBoundingBox.isVecInside(blockPos)) {
                        this.spawnerPlaced = true;
                        world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), 2);
                        final TileEntity tileEntity = world.getTileEntity(blockPos);
                        if (tileEntity instanceof TileEntityMobSpawner) {
                            ((TileEntityMobSpawner)tileEntity).getSpawnerBaseLogic().setEntityName("CaveSpider");
                        }
                    }
                }
                ++n3;
            }
            while (true) {
                if (0 <= n) {
                    if (this.getBlockStateFromPos(world, 0, -1, 0, structureBoundingBox).getBlock().getMaterial() == Material.air) {
                        this.setBlockState(world, Blocks.planks.getDefaultState(), 0, -1, 0, structureBoundingBox);
                    }
                    int n4 = 0;
                    ++n4;
                }
                else {
                    ++n3;
                }
            }
        }
        
        public static StructureBoundingBox func_175814_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2, n3, n, n2 + 2, n3);
            int i;
            for (i = random.nextInt(3) + 2; i > 0; --i) {
                final int n4 = i * 5;
                switch (StructureMineshaftPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                    case 1: {
                        structureBoundingBox.maxX = n + 2;
                        structureBoundingBox.minZ = n3 - (n4 - 1);
                        break;
                    }
                    case 2: {
                        structureBoundingBox.maxX = n + 2;
                        structureBoundingBox.maxZ = n3 + (n4 - 1);
                        break;
                    }
                    case 3: {
                        structureBoundingBox.minX = n - (n4 - 1);
                        structureBoundingBox.maxZ = n3 + 2;
                        break;
                    }
                    case 4: {
                        structureBoundingBox.maxX = n + (n4 - 1);
                        structureBoundingBox.maxZ = n3 + 2;
                        break;
                    }
                }
                if (StructureComponent.findIntersecting(list, structureBoundingBox) == null) {
                    break;
                }
            }
            return (i > 0) ? structureBoundingBox : null;
        }
        
        public Corridor() {
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            final int componentType = this.getComponentType();
            final int nextInt = random.nextInt(4);
            if (this.coordBaseMode != null) {
                switch (StructureMineshaftPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        if (nextInt <= 1) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ - 1, this.coordBaseMode, componentType);
                            break;
                        }
                        if (nextInt == 2) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, EnumFacing.WEST, componentType);
                            break;
                        }
                        StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, EnumFacing.EAST, componentType);
                        break;
                    }
                    case 2: {
                        if (nextInt <= 1) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ + 1, this.coordBaseMode, componentType);
                            break;
                        }
                        if (nextInt == 2) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.WEST, componentType);
                            break;
                        }
                        StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ - 3, EnumFacing.EAST, componentType);
                        break;
                    }
                    case 3: {
                        if (nextInt <= 1) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, componentType);
                            break;
                        }
                        if (nextInt == 2) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType);
                            break;
                        }
                        StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType);
                        break;
                    }
                    case 4: {
                        if (nextInt <= 1) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ, this.coordBaseMode, componentType);
                            break;
                        }
                        if (nextInt == 2) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType);
                            break;
                        }
                        StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX - 3, this.boundingBox.minY - 1 + random.nextInt(3), this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType);
                        break;
                    }
                }
            }
            if (componentType < 8) {
                if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
                    for (int n = this.boundingBox.minX + 3; n + 3 <= this.boundingBox.maxX; n += 5) {
                        final int nextInt2 = random.nextInt(5);
                        if (nextInt2 == 0) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, n, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType + 1);
                        }
                        else if (nextInt2 == 1) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, n, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType + 1);
                        }
                    }
                }
                else {
                    for (int n2 = this.boundingBox.minZ + 3; n2 + 3 <= this.boundingBox.maxZ; n2 += 5) {
                        final int nextInt3 = random.nextInt(5);
                        if (nextInt3 == 0) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, n2, EnumFacing.WEST, componentType + 1);
                        }
                        else if (nextInt3 == 1) {
                            StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, n2, EnumFacing.EAST, componentType + 1);
                        }
                    }
                }
            }
        }
        
        public Corridor(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
            this.hasRails = (random.nextInt(3) == 0);
            this.hasSpiders = (!this.hasRails && random.nextInt(23) == 0);
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
                this.sectionCount = boundingBox.getXSize() / 5;
            }
            else {
                this.sectionCount = boundingBox.getZSize() / 5;
            }
        }
        
        @Override
        protected boolean generateChestContents(final World world, final StructureBoundingBox structureBoundingBox, final Random random, final int n, final int n2, final int n3, final List list, final int n4) {
            final BlockPos blockPos = new BlockPos(this.getXWithOffset(n, n3), this.getYWithOffset(n2), this.getZWithOffset(n, n3));
            if (structureBoundingBox.isVecInside(blockPos) && world.getBlockState(blockPos).getBlock().getMaterial() == Material.air) {
                world.setBlockState(blockPos, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, (int)(random.nextBoolean() ? 1 : 0))), 2);
                final EntityMinecartChest entityMinecartChest = new EntityMinecartChest(world, blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f);
                WeightedRandomChestContent.generateChestContents(random, list, entityMinecartChest, n4);
                world.spawnEntityInWorld(entityMinecartChest);
                return true;
            }
            return false;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setBoolean("hr", this.hasRails);
            nbtTagCompound.setBoolean("sc", this.hasSpiders);
            nbtTagCompound.setBoolean("hps", this.spawnerPlaced);
            nbtTagCompound.setInteger("Num", this.sectionCount);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.hasRails = nbtTagCompound.getBoolean("hr");
            this.hasSpiders = nbtTagCompound.getBoolean("sc");
            this.spawnerPlaced = nbtTagCompound.getBoolean("hps");
            this.sectionCount = nbtTagCompound.getInteger("Num");
        }
    }
    
    public static class Room extends StructureComponent
    {
        private List roomsLinkedToTheRoom;
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            final NBTTagList tagList = nbtTagCompound.getTagList("Entrances", 11);
            while (0 < tagList.tagCount()) {
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(tagList.getIntArrayAt(0)));
                int n = 0;
                ++n;
            }
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            final NBTTagList list = new NBTTagList();
            final Iterator<StructureBoundingBox> iterator = this.roomsLinkedToTheRoom.iterator();
            while (iterator.hasNext()) {
                list.appendTag(iterator.next().toNBTTagIntArray());
            }
            nbtTagCompound.setTag("Entrances", list);
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            final int componentType = this.getComponentType();
            final int n = this.boundingBox.getYSize() - 3 - 1;
            while (0 < this.boundingBox.getXSize()) {
                final int n2 = 0 + random.nextInt(this.boundingBox.getXSize());
                if (3 > this.boundingBox.getXSize()) {
                    break;
                }
                final StructureComponent access$000 = StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 0, this.boundingBox.minY + random.nextInt(1) + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType);
                if (access$000 == null) {
                    continue;
                }
                final StructureBoundingBox boundingBox = access$000.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.minX, boundingBox.minY, this.boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, this.boundingBox.minZ + 1));
            }
            while (0 < this.boundingBox.getXSize()) {
                final int n3 = 0 + random.nextInt(this.boundingBox.getXSize());
                if (3 > this.boundingBox.getXSize()) {
                    break;
                }
                final StructureComponent access$2 = StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 0, this.boundingBox.minY + random.nextInt(1) + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType);
                if (access$2 == null) {
                    continue;
                }
                final StructureBoundingBox boundingBox2 = access$2.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox2.minX, boundingBox2.minY, this.boundingBox.maxZ - 1, boundingBox2.maxX, boundingBox2.maxY, this.boundingBox.maxZ));
            }
            while (0 < this.boundingBox.getZSize()) {
                final int n4 = 0 + random.nextInt(this.boundingBox.getZSize());
                if (3 > this.boundingBox.getZSize()) {
                    break;
                }
                final StructureComponent access$3 = StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + random.nextInt(1) + 1, this.boundingBox.minZ + 0, EnumFacing.WEST, componentType);
                if (access$3 == null) {
                    continue;
                }
                final StructureBoundingBox boundingBox3 = access$3.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.minX, boundingBox3.minY, boundingBox3.minZ, this.boundingBox.minX + 1, boundingBox3.maxY, boundingBox3.maxZ));
            }
            while (0 < this.boundingBox.getZSize()) {
                final int n5 = 0 + random.nextInt(this.boundingBox.getZSize());
                if (3 > this.boundingBox.getZSize()) {
                    break;
                }
                final StructureComponent access$4 = StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + random.nextInt(1) + 1, this.boundingBox.minZ + 0, EnumFacing.EAST, componentType);
                if (access$4 == null) {
                    continue;
                }
                final StructureBoundingBox boundingBox4 = access$4.getBoundingBox();
                this.roomsLinkedToTheRoom.add(new StructureBoundingBox(this.boundingBox.maxX - 1, boundingBox4.minY, boundingBox4.minZ, this.boundingBox.maxX, boundingBox4.maxY, boundingBox4.maxZ));
            }
        }
        
        public Room(final int n, final Random random, final int n2, final int n3) {
            super(n);
            this.roomsLinkedToTheRoom = Lists.newLinkedList();
            this.boundingBox = new StructureBoundingBox(n2, 50, n3, n2 + 7 + random.nextInt(6), 54 + random.nextInt(6), n3 + 7 + random.nextInt(6));
        }
        
        @Override
        public void func_181138_a(final int n, final int n2, final int n3) {
            super.func_181138_a(n, n2, n3);
            final Iterator<StructureBoundingBox> iterator = this.roomsLinkedToTheRoom.iterator();
            while (iterator.hasNext()) {
                iterator.next().offset(n, n2, n3);
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ, Blocks.dirt.getDefaultState(), Blocks.air.getDefaultState(), true);
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 1, this.boundingBox.minZ, this.boundingBox.maxX, Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            for (final StructureBoundingBox structureBoundingBox2 : this.roomsLinkedToTheRoom) {
                this.fillWithBlocks(world, structureBoundingBox, structureBoundingBox2.minX, structureBoundingBox2.maxY - 2, structureBoundingBox2.minZ, structureBoundingBox2.maxX, structureBoundingBox2.maxY, structureBoundingBox2.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            this.randomlyRareFillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), false);
            return true;
        }
        
        public Room() {
            this.roomsLinkedToTheRoom = Lists.newLinkedList();
        }
    }
    
    public static class Stairs extends StructureComponent
    {
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            final int componentType = this.getComponentType();
            if (this.coordBaseMode != null) {
                switch (StructureMineshaftPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.coordBaseMode.ordinal()]) {
                    case 1: {
                        StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType);
                        break;
                    }
                    case 2: {
                        StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType);
                        break;
                    }
                    case 3: {
                        StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, componentType);
                        break;
                    }
                    case 4: {
                        StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, componentType);
                        break;
                    }
                }
            }
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        public static StructureBoundingBox func_175812_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2 - 5, n3, n, n2 + 2, n3);
            switch (StructureMineshaftPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                case 1: {
                    structureBoundingBox.maxX = n + 2;
                    structureBoundingBox.minZ = n3 - 8;
                    break;
                }
                case 2: {
                    structureBoundingBox.maxX = n + 2;
                    structureBoundingBox.maxZ = n3 + 8;
                    break;
                }
                case 3: {
                    structureBoundingBox.minX = n - 8;
                    structureBoundingBox.maxZ = n3 + 2;
                    break;
                }
                case 4: {
                    structureBoundingBox.maxX = n + 8;
                    structureBoundingBox.maxZ = n3 + 2;
                    break;
                }
            }
            return (StructureComponent.findIntersecting(list, structureBoundingBox) != null) ? null : structureBoundingBox;
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            while (true) {
                this.fillWithBlocks(world, structureBoundingBox, 0, 5 - 1, 2, 2, 7, 2, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                int n = 0;
                ++n;
            }
        }
        
        public Stairs() {
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
        }
        
        public Stairs(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing coordBaseMode) {
            super(n);
            this.coordBaseMode = coordBaseMode;
            this.boundingBox = boundingBox;
        }
    }
    
    public static class Cross extends StructureComponent
    {
        private boolean isMultipleFloors;
        private EnumFacing corridorDirection;
        
        public Cross() {
        }
        
        public Cross(final int n, final Random random, final StructureBoundingBox boundingBox, final EnumFacing corridorDirection) {
            super(n);
            this.corridorDirection = corridorDirection;
            this.boundingBox = boundingBox;
            this.isMultipleFloors = (boundingBox.getYSize() > 3);
        }
        
        @Override
        protected void readStructureFromNBT(final NBTTagCompound nbtTagCompound) {
            this.isMultipleFloors = nbtTagCompound.getBoolean("tf");
            this.corridorDirection = EnumFacing.getHorizontal(nbtTagCompound.getInteger("D"));
        }
        
        public static StructureBoundingBox func_175813_a(final List list, final Random random, final int n, final int n2, final int n3, final EnumFacing enumFacing) {
            final StructureBoundingBox structureBoundingBox = new StructureBoundingBox(n, n2, n3, n, n2 + 2, n3);
            if (random.nextInt(4) == 0) {
                final StructureBoundingBox structureBoundingBox2 = structureBoundingBox;
                structureBoundingBox2.maxY += 4;
            }
            switch (StructureMineshaftPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[enumFacing.ordinal()]) {
                case 1: {
                    structureBoundingBox.minX = n - 1;
                    structureBoundingBox.maxX = n + 3;
                    structureBoundingBox.minZ = n3 - 4;
                    break;
                }
                case 2: {
                    structureBoundingBox.minX = n - 1;
                    structureBoundingBox.maxX = n + 3;
                    structureBoundingBox.maxZ = n3 + 4;
                    break;
                }
                case 3: {
                    structureBoundingBox.minX = n - 4;
                    structureBoundingBox.minZ = n3 - 1;
                    structureBoundingBox.maxZ = n3 + 3;
                    break;
                }
                case 4: {
                    structureBoundingBox.maxX = n + 4;
                    structureBoundingBox.minZ = n3 - 1;
                    structureBoundingBox.maxZ = n3 + 3;
                    break;
                }
            }
            return (StructureComponent.findIntersecting(list, structureBoundingBox) != null) ? null : structureBoundingBox;
        }
        
        @Override
        protected void writeStructureToNBT(final NBTTagCompound nbtTagCompound) {
            nbtTagCompound.setBoolean("tf", this.isMultipleFloors);
            nbtTagCompound.setInteger("D", this.corridorDirection.getHorizontalIndex());
        }
        
        @Override
        public void buildComponent(final StructureComponent structureComponent, final List list, final Random random) {
            final int componentType = this.getComponentType();
            switch (StructureMineshaftPieces$1.$SwitchMap$net$minecraft$util$EnumFacing[this.corridorDirection.ordinal()]) {
                case 1: {
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType);
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, componentType);
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, componentType);
                    break;
                }
                case 2: {
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType);
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, componentType);
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, componentType);
                    break;
                }
                case 3: {
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType);
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType);
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, componentType);
                    break;
                }
                case 4: {
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType);
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType);
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, componentType);
                    break;
                }
            }
            if (this.isMultipleFloors) {
                if (random.nextBoolean()) {
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, componentType);
                }
                if (random.nextBoolean()) {
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.WEST, componentType);
                }
                if (random.nextBoolean()) {
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.EAST, componentType);
                }
                if (random.nextBoolean()) {
                    StructureMineshaftPieces.access$000(structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, componentType);
                }
            }
        }
        
        @Override
        public boolean addComponentParts(final World world, final Random random, final StructureBoundingBox structureBoundingBox) {
            if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
                return false;
            }
            if (this.isMultipleFloors) {
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            else {
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
                this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
            }
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.minX + 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.minZ + 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, this.boundingBox.maxX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxX - 1, this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i) {
                for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j) {
                    if (this.getBlockStateFromPos(world, i, this.boundingBox.minY - 1, j, structureBoundingBox).getBlock().getMaterial() == Material.air) {
                        this.setBlockState(world, Blocks.planks.getDefaultState(), i, this.boundingBox.minY - 1, j, structureBoundingBox);
                    }
                }
            }
            return true;
        }
    }
}
