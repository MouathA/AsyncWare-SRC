package net.minecraft.init;

import org.apache.logging.log4j.*;
import net.minecraft.world.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.item.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.io.*;

public class Bootstrap
{
    private static final Logger LOGGER;
    private static final PrintStream SYSOUT;
    private static boolean alreadyRegistered;
    
    public static void printToSYSOUT(final String s) {
        Bootstrap.SYSOUT.println(s);
    }
    
    public static boolean isRegistered() {
        return Bootstrap.alreadyRegistered;
    }
    
    public static void register() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: ifne            18
        //     6: iconst_1       
        //     7: putstatic       net/minecraft/init/Bootstrap.alreadyRegistered:Z
        //    10: getstatic       net/minecraft/init/Bootstrap.LOGGER:Lorg/apache/logging/log4j/Logger;
        //    13: invokeinterface org/apache/logging/log4j/Logger.isDebugEnabled:()Z
        //    18: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0018 (coming from #0013).
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
    
    static {
        SYSOUT = System.out;
        Bootstrap.alreadyRegistered = false;
        LOGGER = LogManager.getLogger();
    }
    
    static void registerDispenserBehaviors() {
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.arrow, new BehaviorProjectileDispense() {
            @Override
            protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                final EntityArrow entityArrow = new EntityArrow(world, position.getX(), position.getY(), position.getZ());
                entityArrow.canBePickedUp = 1;
                return entityArrow;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.egg, new BehaviorProjectileDispense() {
            @Override
            protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                return new EntityEgg(world, position.getX(), position.getY(), position.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.snowball, new BehaviorProjectileDispense() {
            @Override
            protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                return new EntitySnowball(world, position.getX(), position.getY(), position.getZ());
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.experience_bottle, new BehaviorProjectileDispense() {
            @Override
            protected float func_82500_b() {
                return super.func_82500_b() * 1.25f;
            }
            
            @Override
            protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                return new EntityExpBottle(world, position.getX(), position.getY(), position.getZ());
            }
            
            @Override
            protected float func_82498_a() {
                return super.func_82498_a() * 0.5f;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.potionitem, new IBehaviorDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150843_b = new BehaviorDefaultDispenseItem();
            
            @Override
            public ItemStack dispense(final IBlockSource blockSource, final ItemStack itemStack) {
                return ItemPotion.isSplash(itemStack.getMetadata()) ? new BehaviorProjectileDispense(this, itemStack) {
                    final Bootstrap$5 this$0;
                    final ItemStack val$stack;
                    
                    @Override
                    protected IProjectile getProjectileEntity(final World world, final IPosition position) {
                        return new EntityPotion(world, position.getX(), position.getY(), position.getZ(), this.val$stack.copy());
                    }
                    
                    @Override
                    protected float func_82500_b() {
                        return super.func_82500_b() * 1.25f;
                    }
                    
                    @Override
                    protected float func_82498_a() {
                        return super.func_82498_a() * 0.5f;
                    }
                }.dispense(blockSource, itemStack) : this.field_150843_b.dispense(blockSource, itemStack);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.spawn_egg, new BehaviorDefaultDispenseItem() {
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final Entity spawnCreature = ItemMonsterPlacer.spawnCreature(blockSource.getWorld(), itemStack.getMetadata(), blockSource.getX() + facing.getFrontOffsetX(), blockSource.getBlockPos().getY() + 0.2f, blockSource.getZ() + facing.getFrontOffsetZ());
                if (spawnCreature instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                    ((EntityLiving)spawnCreature).setCustomNameTag(itemStack.getDisplayName());
                }
                itemStack.splitStack(1);
                return itemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fireworks, new BehaviorDefaultDispenseItem() {
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(1002, blockSource.getBlockPos(), 0);
            }
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                blockSource.getWorld().spawnEntityInWorld(new EntityFireworkRocket(blockSource.getWorld(), blockSource.getX() + facing.getFrontOffsetX(), blockSource.getBlockPos().getY() + 0.2f, blockSource.getZ() + facing.getFrontOffsetZ(), itemStack));
                itemStack.splitStack(1);
                return itemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.fire_charge, new BehaviorDefaultDispenseItem() {
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(1009, blockSource.getBlockPos(), 0);
            }
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final IPosition dispensePosition = BlockDispenser.getDispensePosition(blockSource);
                final double n = dispensePosition.getX() + facing.getFrontOffsetX() * 0.3f;
                final double n2 = dispensePosition.getY() + facing.getFrontOffsetY() * 0.3f;
                final double n3 = dispensePosition.getZ() + facing.getFrontOffsetZ() * 0.3f;
                final World world = blockSource.getWorld();
                final Random rand = world.rand;
                world.spawnEntityInWorld(new EntitySmallFireball(world, n, n2, n3, rand.nextGaussian() * 0.05 + facing.getFrontOffsetX(), rand.nextGaussian() * 0.05 + facing.getFrontOffsetY(), rand.nextGaussian() * 0.05 + facing.getFrontOffsetZ()));
                itemStack.splitStack(1);
                return itemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.boat, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150842_b = new BehaviorDefaultDispenseItem();
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final World world = blockSource.getWorld();
                final double n = blockSource.getX() + facing.getFrontOffsetX() * 1.125f;
                final double n2 = blockSource.getY() + facing.getFrontOffsetY() * 1.125f;
                final double n3 = blockSource.getZ() + facing.getFrontOffsetZ() * 1.125f;
                final BlockPos offset = blockSource.getBlockPos().offset(facing);
                final Material material = world.getBlockState(offset).getBlock().getMaterial();
                double n4;
                if (Material.water.equals(material)) {
                    n4 = 1.0;
                }
                else {
                    if (!Material.air.equals(material) || !Material.water.equals(world.getBlockState(offset.down()).getBlock().getMaterial())) {
                        return this.field_150842_b.dispense(blockSource, itemStack);
                    }
                    n4 = 0.0;
                }
                world.spawnEntityInWorld(new EntityBoat(world, n, n2 + n4, n3));
                itemStack.splitStack(1);
                return itemStack;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(1000, blockSource.getBlockPos(), 0);
            }
        });
        final BehaviorDefaultDispenseItem behaviorDefaultDispenseItem = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                if (((ItemBucket)itemStack.getItem()).tryPlaceContainedLiquid(blockSource.getWorld(), blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata())))) {
                    itemStack.setItem(Items.bucket);
                    itemStack.stackSize = 1;
                    return itemStack;
                }
                return this.field_150841_b.dispense(blockSource, itemStack);
            }
        };
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.lava_bucket, behaviorDefaultDispenseItem);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.water_bucket, behaviorDefaultDispenseItem);
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final IBlockState blockState = world.getBlockState(offset);
                final Block block = blockState.getBlock();
                final Material material = block.getMaterial();
                Item item;
                if (Material.water.equals(material) && block instanceof BlockLiquid && (int)blockState.getValue(BlockLiquid.LEVEL) == 0) {
                    item = Items.water_bucket;
                }
                else {
                    if (!Material.lava.equals(material) || !(block instanceof BlockLiquid) || (int)blockState.getValue(BlockLiquid.LEVEL) != 0) {
                        return super.dispenseStack(blockSource, itemStack);
                    }
                    item = Items.lava_bucket;
                }
                world.setBlockToAir(offset);
                final int stackSize = itemStack.stackSize - 1;
                itemStack.stackSize = stackSize;
                if (stackSize == 0) {
                    itemStack.setItem(item);
                    itemStack.stackSize = 1;
                }
                else if (((TileEntityDispenser)blockSource.getBlockTileEntity()).addItemStack(new ItemStack(item)) < 0) {
                    this.field_150840_b.dispense(blockSource, new ItemStack(item));
                }
                return itemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.flint_and_steel, new BehaviorDefaultDispenseItem() {
            private boolean field_150839_b = true;
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                if (this.field_150839_b) {
                    blockSource.getWorld().playAuxSFX(1000, blockSource.getBlockPos(), 0);
                }
                else {
                    blockSource.getWorld().playAuxSFX(1001, blockSource.getBlockPos(), 0);
                }
            }
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                if (world.isAirBlock(offset)) {
                    world.setBlockState(offset, Blocks.fire.getDefaultState());
                    if (itemStack.attemptDamageItem(1, world.rand)) {
                        itemStack.stackSize = 0;
                    }
                }
                else if (world.getBlockState(offset).getBlock() == Blocks.tnt) {
                    Blocks.tnt.onBlockDestroyedByPlayer(world, offset, Blocks.tnt.getDefaultState().withProperty(BlockTNT.EXPLODE, true));
                    world.setBlockToAir(offset);
                }
                else {
                    this.field_150839_b = false;
                }
                return itemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.dye, new BehaviorDefaultDispenseItem() {
            private boolean field_150838_b = true;
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                if (this.field_150838_b) {
                    blockSource.getWorld().playAuxSFX(1000, blockSource.getBlockPos(), 0);
                }
                else {
                    blockSource.getWorld().playAuxSFX(1001, blockSource.getBlockPos(), 0);
                }
            }
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                if (EnumDyeColor.WHITE == EnumDyeColor.byDyeDamage(itemStack.getMetadata())) {
                    final World world = blockSource.getWorld();
                    final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                    if (ItemDye.applyBonemeal(itemStack, world, offset)) {
                        if (!world.isRemote) {
                            world.playAuxSFX(2005, offset, 0);
                        }
                    }
                    else {
                        this.field_150838_b = false;
                    }
                    return itemStack;
                }
                return super.dispenseStack(blockSource, itemStack);
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.tnt), new BehaviorDefaultDispenseItem() {
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, offset.getX() + 0.5, offset.getY(), offset.getZ() + 0.5, null);
                world.spawnEntityInWorld(entityTNTPrimed);
                world.playSoundAtEntity(entityTNTPrimed, "game.tnt.primed", 1.0f, 1.0f);
                --itemStack.stackSize;
                return itemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.skull, new BehaviorDefaultDispenseItem() {
            private boolean field_179240_b = true;
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                if (this.field_179240_b) {
                    blockSource.getWorld().playAuxSFX(1000, blockSource.getBlockPos(), 0);
                }
                else {
                    blockSource.getWorld().playAuxSFX(1001, blockSource.getBlockPos(), 0);
                }
            }
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final BlockPos offset = blockSource.getBlockPos().offset(facing);
                final BlockSkull skull = Blocks.skull;
                if (world.isAirBlock(offset) && skull.canDispenserPlace(world, offset, itemStack)) {
                    if (!world.isRemote) {
                        world.setBlockState(offset, skull.getDefaultState().withProperty(BlockSkull.FACING, EnumFacing.UP), 3);
                        final TileEntity tileEntity = world.getTileEntity(offset);
                        if (tileEntity instanceof TileEntitySkull) {
                            if (itemStack.getMetadata() == 3) {
                                GameProfile gameProfileFromNBT = null;
                                if (itemStack.hasTagCompound()) {
                                    final NBTTagCompound tagCompound = itemStack.getTagCompound();
                                    if (tagCompound.hasKey("SkullOwner", 10)) {
                                        gameProfileFromNBT = NBTUtil.readGameProfileFromNBT(tagCompound.getCompoundTag("SkullOwner"));
                                    }
                                    else if (tagCompound.hasKey("SkullOwner", 8)) {
                                        final String string = tagCompound.getString("SkullOwner");
                                        if (!StringUtils.isNullOrEmpty(string)) {
                                            gameProfileFromNBT = new GameProfile((UUID)null, string);
                                        }
                                    }
                                }
                                ((TileEntitySkull)tileEntity).setPlayerProfile(gameProfileFromNBT);
                            }
                            else {
                                ((TileEntitySkull)tileEntity).setType(itemStack.getMetadata());
                            }
                            ((TileEntitySkull)tileEntity).setSkullRotation(facing.getOpposite().getHorizontalIndex() * 4);
                            Blocks.skull.checkWitherSpawn(world, offset, (TileEntitySkull)tileEntity);
                        }
                        --itemStack.stackSize;
                    }
                }
                else {
                    this.field_179240_b = false;
                }
                return itemStack;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Item.getItemFromBlock(Blocks.pumpkin), new BehaviorDefaultDispenseItem() {
            private boolean field_179241_b = true;
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final World world = blockSource.getWorld();
                final BlockPos offset = blockSource.getBlockPos().offset(BlockDispenser.getFacing(blockSource.getBlockMetadata()));
                final BlockPumpkin blockPumpkin = (BlockPumpkin)Blocks.pumpkin;
                if (world.isAirBlock(offset) && blockPumpkin.canDispenserPlace(world, offset)) {
                    if (!world.isRemote) {
                        world.setBlockState(offset, blockPumpkin.getDefaultState(), 3);
                    }
                    --itemStack.stackSize;
                }
                else {
                    this.field_179241_b = false;
                }
                return itemStack;
            }
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                if (this.field_179241_b) {
                    blockSource.getWorld().playAuxSFX(1000, blockSource.getBlockPos(), 0);
                }
                else {
                    blockSource.getWorld().playAuxSFX(1001, blockSource.getBlockPos(), 0);
                }
            }
        });
    }
    
    private static void redirectOutputToLog() {
        System.setErr(new LoggingPrintStream("STDERR", System.err));
        System.setOut(new LoggingPrintStream("STDOUT", Bootstrap.SYSOUT));
    }
}
