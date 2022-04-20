package net.minecraft.entity.passive;

import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.item.crafting.*;

public class EntitySheep extends EntityAnimal
{
    private static final Map DYE_TO_RGB;
    private EntityAIEatGrass entityAIEatGrass;
    private final InventoryCrafting inventoryCrafting;
    private int sheepTimer;
    
    public void setSheared(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(16);
        if (b) {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte | 0x10));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(watchableObjectByte & 0xFFFFFFEF));
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.shears && this != 0 && !this.isChild()) {
            if (!this.worldObj.isRemote) {
                this.setSheared(true);
                while (0 < 1 + this.rand.nextInt(3)) {
                    final EntityItem entityDropItem;
                    final EntityItem entityItem = entityDropItem = this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor().getMetadata()), 1.0f);
                    entityDropItem.motionY += this.rand.nextFloat() * 0.05f;
                    final EntityItem entityItem2 = entityItem;
                    entityItem2.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    final EntityItem entityItem3 = entityItem;
                    entityItem3.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
                    int n = 0;
                    ++n;
                }
            }
            currentItem.damageItem(1, entityPlayer);
            this.playSound("mob.sheep.shear", 1.0f, 1.0f);
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.sheep.say";
    }
    
    public static float[] func_175513_a(final EnumDyeColor enumDyeColor) {
        return EntitySheep.DYE_TO_RGB.get(enumDyeColor);
    }
    
    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.wool);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setSheared(nbtTagCompound.getBoolean("Sheared"));
        this.setFleeceColor(EnumDyeColor.byMetadata(nbtTagCompound.getByte("Color")));
    }
    
    @Override
    protected void updateAITasks() {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    public float getHeadRotationAngleX(final float n) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            return 0.62831855f + 0.2199115f * MathHelper.sin((this.sheepTimer - 4 - n) / 32.0f * 28.7f);
        }
        return (this.sheepTimer > 0) ? 0.62831855f : (this.rotationPitch / 57.295776f);
    }
    
    public EntitySheep(final World world) {
        super(world);
        this.inventoryCrafting = new InventoryCrafting(new Container() {
            final EntitySheep this$0;
            
            @Override
            public boolean canInteractWith(final EntityPlayer entityPlayer) {
                return false;
            }
        }, 2, 1);
        this.entityAIEatGrass = new EntityAIEatGrass(this);
        this.setSize(0.9f, 1.3f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0));
        this.tasks.addTask(3, new EntityAITempt(this, 1.1, Items.wheat, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1));
        this.tasks.addTask(5, this.entityAIEatGrass);
        this.tasks.addTask(6, new EntityAIWander(this, 1.0));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.inventoryCrafting.setInventorySlotContents(0, new ItemStack(Items.dye, 1, 0));
        this.inventoryCrafting.setInventorySlotContents(1, new ItemStack(Items.dye, 1, 0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Sheared", this.getSheared());
        nbtTagCompound.setByte("Color", (byte)this.getFleeceColor().getMetadata());
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        if (this != 0) {
            this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, this.getFleeceColor().getMetadata()), 0.0f);
        }
        while (0 < this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + n)) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_mutton, 1);
            }
            else {
                this.dropItem(Items.mutton, 1);
            }
            int n2 = 0;
            ++n2;
        }
    }
    
    public float getHeadRotationPointY(final float n) {
        return (this.sheepTimer <= 0) ? 0.0f : ((this.sheepTimer >= 4 && this.sheepTimer <= 36) ? 1.0f : ((this.sheepTimer < 4) ? ((this.sheepTimer - n) / 4.0f) : (-(this.sheepTimer - 40 - n) / 4.0f)));
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        this.setFleeceColor(getRandomSheepColor(this.worldObj.rand));
        return onInitialSpawn;
    }
    
    public void setFleeceColor(final EnumDyeColor enumDyeColor) {
        this.dataWatcher.updateObject(16, (byte)((this.dataWatcher.getWatchableObjectByte(16) & 0xF0) | (enumDyeColor.getMetadata() & 0xF)));
    }
    
    @Override
    public float getEyeHeight() {
        return 0.95f * this.height;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 10) {
            this.sheepTimer = 40;
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.sheep.say";
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.onLivingUpdate();
    }
    
    public EnumDyeColor getFleeceColor() {
        return EnumDyeColor.byMetadata(this.dataWatcher.getWatchableObjectByte(16) & 0xF);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    public EntitySheep createChild(final EntityAgeable entityAgeable) {
        final EntitySheep entitySheep = (EntitySheep)entityAgeable;
        final EntitySheep entitySheep2 = new EntitySheep(this.worldObj);
        entitySheep2.setFleeceColor(this.getDyeColorMixFromParents(this, entitySheep));
        return entitySheep2;
    }
    
    static {
        (DYE_TO_RGB = Maps.newEnumMap((Class)EnumDyeColor.class)).put(EnumDyeColor.WHITE, new float[] { 1.0f, 1.0f, 1.0f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.ORANGE, new float[] { 0.85f, 0.5f, 0.2f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.MAGENTA, new float[] { 0.7f, 0.3f, 0.85f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.LIGHT_BLUE, new float[] { 0.4f, 0.6f, 0.85f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.YELLOW, new float[] { 0.9f, 0.9f, 0.2f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.LIME, new float[] { 0.5f, 0.8f, 0.1f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.PINK, new float[] { 0.95f, 0.5f, 0.65f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.GRAY, new float[] { 0.3f, 0.3f, 0.3f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.SILVER, new float[] { 0.6f, 0.6f, 0.6f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.CYAN, new float[] { 0.3f, 0.5f, 0.6f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.PURPLE, new float[] { 0.5f, 0.25f, 0.7f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.BLUE, new float[] { 0.2f, 0.3f, 0.7f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.BROWN, new float[] { 0.4f, 0.3f, 0.2f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.GREEN, new float[] { 0.4f, 0.5f, 0.2f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.RED, new float[] { 0.6f, 0.2f, 0.2f });
        EntitySheep.DYE_TO_RGB.put(EnumDyeColor.BLACK, new float[] { 0.1f, 0.1f, 0.1f });
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.sheep.say";
    }
    
    public static EnumDyeColor getRandomSheepColor(final Random random) {
        final int nextInt = random.nextInt(100);
        return (nextInt < 5) ? EnumDyeColor.BLACK : ((nextInt < 10) ? EnumDyeColor.GRAY : ((nextInt < 15) ? EnumDyeColor.SILVER : ((nextInt < 18) ? EnumDyeColor.BROWN : ((random.nextInt(500) == 0) ? EnumDyeColor.PINK : EnumDyeColor.WHITE))));
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound("mob.sheep.step", 0.15f, 1.0f);
    }
    
    private EnumDyeColor getDyeColorMixFromParents(final EntityAnimal entityAnimal, final EntityAnimal entityAnimal2) {
        final int dyeDamage = ((EntitySheep)entityAnimal).getFleeceColor().getDyeDamage();
        final int dyeDamage2 = ((EntitySheep)entityAnimal2).getFleeceColor().getDyeDamage();
        this.inventoryCrafting.getStackInSlot(0).setItemDamage(dyeDamage);
        this.inventoryCrafting.getStackInSlot(1).setItemDamage(dyeDamage2);
        final ItemStack matchingRecipe = CraftingManager.getInstance().findMatchingRecipe(this.inventoryCrafting, ((EntitySheep)entityAnimal).worldObj);
        int metadata;
        if (matchingRecipe != null && matchingRecipe.getItem() == Items.dye) {
            metadata = matchingRecipe.getMetadata();
        }
        else {
            metadata = (this.worldObj.rand.nextBoolean() ? dyeDamage : dyeDamage2);
        }
        return EnumDyeColor.byDyeDamage(metadata);
    }
    
    @Override
    public void eatGrassBonus() {
        this.setSheared(false);
        if (this.isChild()) {
            this.addGrowth(60);
        }
    }
}
