package net.minecraft.entity.passive;

import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.effect.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.world.*;
import net.minecraft.village.*;
import net.minecraft.potion.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant
{
    private boolean isPlaying;
    private InventoryBasic villagerInventory;
    private boolean isWillingToMate;
    private int timeUntilReset;
    private static final ITradeList[][][][] DEFAULT_TRADE_LIST_MAP;
    private String lastBuyingPlayer;
    private int careerId;
    private boolean needsInitilization;
    Village villageObj;
    private EntityPlayer buyingPlayer;
    private MerchantRecipeList buyingList;
    private int careerLevel;
    private int wealth;
    private boolean isLookingForHome;
    private boolean areAdditionalTasksSet;
    private int randomTickDivider;
    private boolean isMating;
    
    public boolean func_175557_cr() {
        return this.getProfession() == 0 && false;
    }
    
    public void setMating(final boolean isMating) {
        this.isMating = isMating;
    }
    
    public void setPlaying(final boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
    public boolean func_175553_cp() {
        return this.hasEnoughItems(1);
    }
    
    public void setProfession(final int n) {
        this.dataWatcher.updateObject(16, n);
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList list) {
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == 12) {
            this.spawnParticles(EnumParticleTypes.HEART);
        }
        else if (b == 13) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
        }
        else if (b == 14) {
            this.spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer customer) {
        final ItemStack currentItem = customer.inventory.getCurrentItem();
        if ((currentItem == null || currentItem.getItem() != Items.spawn_egg) && this.isEntityAlive() && this != null && !this.isChild()) {
            if (!this.worldObj.isRemote && (this.buyingList == null || this.buyingList.size() > 0)) {
                this.setCustomer(customer);
                customer.displayVillagerTradeGui(this);
            }
            customer.triggerAchievement(StatList.timesTalkedToVillagerStat);
            return true;
        }
        return super.interact(customer);
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
    }
    
    private void setAdditionalAItasks() {
        if (!this.areAdditionalTasksSet) {
            this.areAdditionalTasksSet = true;
            if (this.isChild()) {
                this.tasks.addTask(8, new EntityAIPlay(this, 0.32));
            }
            else if (this.getProfession() == 0) {
                this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6));
            }
        }
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    public void setIsWillingToMate(final boolean isWillingToMate) {
        this.isWillingToMate = isWillingToMate;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("Profession", this.getProfession());
        nbtTagCompound.setInteger("Riches", this.wealth);
        nbtTagCompound.setInteger("Career", this.careerId);
        nbtTagCompound.setInteger("CareerLevel", this.careerLevel);
        nbtTagCompound.setBoolean("Willing", this.isWillingToMate);
        if (this.buyingList != null) {
            nbtTagCompound.setTag("Offers", this.buyingList.getRecipiesAsTags());
        }
        final NBTTagList list = new NBTTagList();
        while (0 < this.villagerInventory.getSizeInventory()) {
            final ItemStack stackInSlot = this.villagerInventory.getStackInSlot(0);
            if (stackInSlot != null) {
                list.appendTag(stackInSlot.writeToNBT(new NBTTagCompound()));
            }
            int n = 0;
            ++n;
        }
        nbtTagCompound.setTag("Inventory", list);
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.villager.death";
    }
    
    @Override
    public EntityVillager createChild(final EntityAgeable entityAgeable) {
        final EntityVillager entityVillager = new EntityVillager(this.worldObj);
        entityVillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityVillager)), null);
        return entityVillager;
    }
    
    public boolean isMating() {
        return this.isMating;
    }
    
    public boolean isPlaying() {
        return this.isPlaying;
    }
    
    @Override
    public void verifySellingItem(final ItemStack itemStack) {
        if (!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
            this.livingSoundTime = -this.getTalkInterval();
            if (itemStack != null) {
                this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
            }
            else {
                this.playSound("mob.villager.no", this.getSoundVolume(), this.getSoundPitch());
            }
        }
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        if (this.villageObj != null) {
            final Entity entity = damageSource.getEntity();
            if (entity != null) {
                if (entity instanceof EntityPlayer) {
                    this.villageObj.setReputationForPlayer(entity.getName(), -2);
                }
                else if (entity instanceof IMob) {
                    this.villageObj.endMatingSeason();
                }
            }
            else if (this.worldObj.getClosestPlayerToEntity(this, 16.0) != null) {
                this.villageObj.endMatingSeason();
            }
        }
        super.onDeath(damageSource);
    }
    
    public boolean allowLeashing() {
        return false;
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        if (super.replaceItemInInventory(n, itemStack)) {
            return true;
        }
        final int n2 = n - 300;
        if (n2 >= 0 && n2 < this.villagerInventory.getSizeInventory()) {
            this.villagerInventory.setInventorySlotContents(n2, itemStack);
            return true;
        }
        return false;
    }
    
    public boolean isFarmItemInInventory() {
        while (0 < this.villagerInventory.getSizeInventory()) {
            final ItemStack stackInSlot = this.villagerInventory.getStackInSlot(0);
            if (stackInSlot != null && (stackInSlot.getItem() == Items.wheat_seeds || stackInSlot.getItem() == Items.potato || stackInSlot.getItem() == Items.carrot)) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    @Override
    public void setRevengeTarget(final EntityLivingBase revengeTarget) {
        super.setRevengeTarget(revengeTarget);
        if (this.villageObj != null && revengeTarget != null) {
            this.villageObj.addOrRenewAgressor(revengeTarget);
            if (revengeTarget instanceof EntityPlayer) {
                if (this.isChild()) {}
                this.villageObj.setReputationForPlayer(revengeTarget.getName(), -3);
                if (this.isEntityAlive()) {
                    this.worldObj.setEntityState(this, (byte)13);
                }
            }
        }
    }
    
    static {
        DEFAULT_TRADE_LIST_MAP = new ITradeList[][][][] { { { { new EmeraldForItems(Items.wheat, new PriceInfo(18, 22)), new EmeraldForItems(Items.potato, new PriceInfo(15, 19)), new EmeraldForItems(Items.carrot, new PriceInfo(15, 19)), new ListItemForEmeralds(Items.bread, new PriceInfo(-4, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new PriceInfo(8, 13)), new ListItemForEmeralds(Items.pumpkin_pie, new PriceInfo(-3, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new PriceInfo(7, 12)), new ListItemForEmeralds(Items.apple, new PriceInfo(-5, -7)) }, { new ListItemForEmeralds(Items.cookie, new PriceInfo(-6, -10)), new ListItemForEmeralds(Items.cake, new PriceInfo(1, 1)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ItemAndEmeraldToItem(Items.fish, new PriceInfo(6, 6), Items.cooked_fish, new PriceInfo(6, 6)) }, { new ListEnchantedItemForEmeralds(Items.fishing_rod, new PriceInfo(7, 8)) } }, { { new EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new PriceInfo(16, 22)), new ListItemForEmeralds(Items.shears, new PriceInfo(3, 4)) }, { new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new PriceInfo(1, 2)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new ListItemForEmeralds(Items.arrow, new PriceInfo(-12, -8)) }, { new ListItemForEmeralds(Items.bow, new PriceInfo(2, 3)), new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new PriceInfo(10, 10), Items.flint, new PriceInfo(6, 10)) } } }, { { { new EmeraldForItems(Items.paper, new PriceInfo(24, 36)), new ListEnchantedBookForEmeralds() }, { new EmeraldForItems(Items.book, new PriceInfo(8, 10)), new ListItemForEmeralds(Items.compass, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new PriceInfo(3, 4)) }, { new EmeraldForItems(Items.written_book, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.clock, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new PriceInfo(-5, -3)) }, { new ListEnchantedBookForEmeralds() }, { new ListEnchantedBookForEmeralds() }, { new ListItemForEmeralds(Items.name_tag, new PriceInfo(20, 22)) } } }, { { { new EmeraldForItems(Items.rotten_flesh, new PriceInfo(36, 40)), new EmeraldForItems(Items.gold_ingot, new PriceInfo(8, 10)) }, { new ListItemForEmeralds(Items.redstone, new PriceInfo(-4, -1)), new ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new PriceInfo(-2, -1)) }, { new ListItemForEmeralds(Items.ender_eye, new PriceInfo(7, 11)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new PriceInfo(-3, -1)) }, { new ListItemForEmeralds(Items.experience_bottle, new PriceInfo(3, 11)) } } }, { { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_helmet, new PriceInfo(4, 6)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListItemForEmeralds(Items.iron_chestplate, new PriceInfo(10, 14)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_chestplate, new PriceInfo(16, 19)) }, { new ListItemForEmeralds(Items.chainmail_boots, new PriceInfo(5, 7)), new ListItemForEmeralds(Items.chainmail_leggings, new PriceInfo(9, 11)), new ListItemForEmeralds(Items.chainmail_helmet, new PriceInfo(5, 7)), new ListItemForEmeralds(Items.chainmail_chestplate, new PriceInfo(11, 15)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_axe, new PriceInfo(6, 8)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_sword, new PriceInfo(9, 10)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_sword, new PriceInfo(12, 15)), new ListEnchantedItemForEmeralds(Items.diamond_axe, new PriceInfo(9, 12)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListEnchantedItemForEmeralds(Items.iron_shovel, new PriceInfo(5, 7)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_pickaxe, new PriceInfo(9, 11)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new PriceInfo(12, 15)) } } }, { { { new EmeraldForItems(Items.porkchop, new PriceInfo(14, 18)), new EmeraldForItems(Items.chicken, new PriceInfo(14, 18)) }, { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.cooked_porkchop, new PriceInfo(-7, -5)), new ListItemForEmeralds(Items.cooked_chicken, new PriceInfo(-8, -6)) } }, { { new EmeraldForItems(Items.leather, new PriceInfo(9, 12)), new ListItemForEmeralds(Items.leather_leggings, new PriceInfo(2, 4)) }, { new ListEnchantedItemForEmeralds(Items.leather_chestplate, new PriceInfo(7, 12)) }, { new ListItemForEmeralds(Items.saddle, new PriceInfo(8, 10)) } } } };
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer entityPlayer) {
        if (this.buyingList == null) {
            this.populateBuyingList();
        }
        return this.buyingList;
    }
    
    @Override
    public float getEyeHeight() {
        float n = 1.62f;
        if (this.isChild()) {
            n -= (float)0.81;
        }
        return n;
    }
    
    public boolean canAbondonItems() {
        return this.hasEnoughItems(2);
    }
    
    private void spawnParticles(final EnumParticleTypes enumParticleTypes) {
        while (true) {
            this.worldObj.spawnParticle(enumParticleTypes, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 1.0 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, this.rand.nextGaussian() * 0.02, new int[0]);
            int n = 0;
            ++n;
        }
    }
    
    public void setLookingForHome() {
        this.isLookingForHome = true;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setProfession(nbtTagCompound.getInteger("Profession"));
        this.wealth = nbtTagCompound.getInteger("Riches");
        this.careerId = nbtTagCompound.getInteger("Career");
        this.careerLevel = nbtTagCompound.getInteger("CareerLevel");
        this.isWillingToMate = nbtTagCompound.getBoolean("Willing");
        if (nbtTagCompound.hasKey("Offers", 10)) {
            this.buyingList = new MerchantRecipeList(nbtTagCompound.getCompoundTag("Offers"));
        }
        final NBTTagList tagList = nbtTagCompound.getTagList("Inventory", 10);
        while (0 < tagList.tagCount()) {
            final ItemStack loadItemStackFromNBT = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0));
            if (loadItemStackFromNBT != null) {
                this.villagerInventory.func_174894_a(loadItemStackFromNBT);
            }
            int n = 0;
            ++n;
        }
        this.setCanPickUpLoot(true);
        this.setAdditionalAItasks();
    }
    
    @Override
    public void onStruckByLightning(final EntityLightningBolt entityLightningBolt) {
        if (!this.worldObj.isRemote && !this.isDead) {
            final EntityWitch entityWitch = new EntityWitch(this.worldObj);
            entityWitch.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            entityWitch.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityWitch)), null);
            entityWitch.setNoAI(this.isAIDisabled());
            if (this.hasCustomName()) {
                entityWitch.setCustomNameTag(this.getCustomNameTag());
                entityWitch.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
            }
            this.worldObj.spawnEntityInWorld(entityWitch);
            this.setDead();
        }
    }
    
    public EntityVillager(final World world, final int profession) {
        super(world);
        this.villagerInventory = new InventoryBasic("Items", false, 8);
        this.setProfession(profession);
        this.setSize(0.6f, 1.8f);
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0f, 0.6, 0.6));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0f, 1.0f));
        this.tasks.addTask(9, new EntityAIVillagerInteract(this));
        this.tasks.addTask(9, new EntityAIWander(this, 0.6));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f));
        this.setCanPickUpLoot(true);
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final String customNameTag = this.getCustomNameTag();
        if (customNameTag != null && customNameTag.length() > 0) {
            final ChatComponentText chatComponentText = new ChatComponentText(customNameTag);
            chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentText.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatComponentText;
        }
        if (this.buyingList == null) {
            this.populateBuyingList();
        }
        String s = null;
        switch (this.getProfession()) {
            case 0: {
                if (this.careerId == 1) {
                    s = "farmer";
                    break;
                }
                if (this.careerId == 2) {
                    s = "fisherman";
                    break;
                }
                if (this.careerId == 3) {
                    s = "shepherd";
                    break;
                }
                if (this.careerId == 4) {
                    s = "fletcher";
                    break;
                }
                break;
            }
            case 1: {
                s = "librarian";
                break;
            }
            case 2: {
                s = "cleric";
                break;
            }
            case 3: {
                if (this.careerId == 1) {
                    s = "armor";
                    break;
                }
                if (this.careerId == 2) {
                    s = "weapon";
                    break;
                }
                if (this.careerId == 3) {
                    s = "tool";
                    break;
                }
                break;
            }
            case 4: {
                if (this.careerId == 1) {
                    s = "butcher";
                    break;
                }
                if (this.careerId == 2) {
                    s = "leather";
                    break;
                }
                break;
            }
        }
        if (s != null) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("entity.Villager." + s, new Object[0]);
            chatComponentTranslation.getChatStyle().setChatHoverEvent(this.getHoverEvent());
            chatComponentTranslation.getChatStyle().setInsertion(this.getUniqueID().toString());
            return chatComponentTranslation;
        }
        return super.getDisplayName();
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        this.setProfession(this.worldObj.rand.nextInt(5));
        this.setAdditionalAItasks();
        return onInitialSpawn;
    }
    
    @Override
    public void useRecipe(final MerchantRecipe merchantRecipe) {
        merchantRecipe.incrementToolUses();
        this.livingSoundTime = -this.getTalkInterval();
        this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
        int n = 3 + this.rand.nextInt(4);
        if (merchantRecipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
            this.timeUntilReset = 40;
            this.needsInitilization = true;
            this.isWillingToMate = true;
            if (this.buyingPlayer != null) {
                this.lastBuyingPlayer = this.buyingPlayer.getName();
            }
            else {
                this.lastBuyingPlayer = null;
            }
            n += 5;
        }
        if (merchantRecipe.getItemToBuy().getItem() == Items.emerald) {
            this.wealth += merchantRecipe.getItemToBuy().stackSize;
        }
        if (merchantRecipe.getRewardsExp()) {
            this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5, this.posZ, n));
        }
    }
    
    private void populateBuyingList() {
        final ITradeList[][][] array = EntityVillager.DEFAULT_TRADE_LIST_MAP[this.getProfession()];
        if (this.careerId != 0 && this.careerLevel != 0) {
            ++this.careerLevel;
        }
        else {
            this.careerId = this.rand.nextInt(array.length) + 1;
            this.careerLevel = 1;
        }
        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }
        final int n = this.careerId - 1;
        final int n2 = this.careerLevel - 1;
        final ITradeList[][] array2 = array[n];
        if (n2 >= 0 && n2 < array2.length) {
            final ITradeList[] array3 = array2[n2];
            while (0 < array3.length) {
                array3[0].modifyMerchantRecipeList(this.buyingList, this.rand);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    public InventoryBasic getVillagerInventory() {
        return this.villagerInventory;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    protected void updateAITasks() {
        final int randomTickDivider = this.randomTickDivider - 1;
        this.randomTickDivider = randomTickDivider;
        if (randomTickDivider <= 0) {
            final BlockPos blockPos = new BlockPos(this);
            this.worldObj.getVillageCollection().addToVillagerPositionList(blockPos);
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(blockPos, 32);
            if (this.villageObj == null) {
                this.detachHome();
            }
            else {
                this.setHomePosAndDistance(this.villageObj.getCenter(), (int)(this.villageObj.getVillageRadius() * 1.0f));
                if (this.isLookingForHome) {
                    this.isLookingForHome = false;
                    this.villageObj.setDefaultPlayerReputation(5);
                }
            }
        }
        if (this != null && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.needsInitilization) {
                    for (final MerchantRecipe merchantRecipe : this.buyingList) {
                        if (merchantRecipe.isRecipeDisabled()) {
                            merchantRecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                        }
                    }
                    this.populateBuyingList();
                    this.needsInitilization = false;
                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.worldObj.setEntityState(this, (byte)14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }
                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }
        super.updateAITasks();
    }
    
    public int getProfession() {
        return Math.max(this.dataWatcher.getWatchableObjectInt(16) % 5, 0);
    }
    
    public EntityVillager(final World world) {
        this(world, 0);
    }
    
    @Override
    public void setCustomer(final EntityPlayer buyingPlayer) {
        this.buyingPlayer = buyingPlayer;
    }
    
    public boolean getIsWillingToMate(final boolean b) {
        if (!this.isWillingToMate && b && this.func_175553_cp() && 0 < this.villagerInventory.getSizeInventory()) {
            final ItemStack stackInSlot = this.villagerInventory.getStackInSlot(0);
            if (stackInSlot != null) {
                if (stackInSlot.getItem() == Items.bread && stackInSlot.stackSize >= 3) {
                    this.villagerInventory.decrStackSize(0, 3);
                }
                else if ((stackInSlot.getItem() == Items.potato || stackInSlot.getItem() == Items.carrot) && stackInSlot.stackSize >= 12) {
                    this.villagerInventory.decrStackSize(0, 12);
                }
            }
            this.worldObj.setEntityState(this, (byte)18);
            this.isWillingToMate = true;
        }
        return this.isWillingToMate;
    }
    
    @Override
    protected String getLivingSound() {
        return (this != null) ? "mob.villager.haggle" : "mob.villager.idle";
    }
    
    @Override
    protected void onGrowingAdult() {
        if (this.getProfession() == 0) {
            this.tasks.addTask(8, new EntityAIHarvestFarmland(this, 0.6));
        }
        super.onGrowingAdult();
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.villager.hit";
    }
    
    @Override
    protected void updateEquipmentIfNeeded(final EntityItem entityItem) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        if (this != entityItem2.getItem()) {
            final ItemStack func_174894_a = this.villagerInventory.func_174894_a(entityItem2);
            if (func_174894_a == null) {
                entityItem.setDead();
            }
            else {
                entityItem2.stackSize = func_174894_a.stackSize;
            }
        }
    }
    
    static class ItemAndEmeraldToItem implements ITradeList
    {
        public ItemStack field_179411_a;
        public ItemStack field_179410_c;
        public PriceInfo field_179408_d;
        public PriceInfo field_179409_b;
        
        public ItemAndEmeraldToItem(final Item item, final PriceInfo field_179409_b, final Item item2, final PriceInfo field_179408_d) {
            this.field_179411_a = new ItemStack(item);
            this.field_179409_b = field_179409_b;
            this.field_179410_c = new ItemStack(item2);
            this.field_179408_d = field_179408_d;
        }
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            if (this.field_179409_b != null) {
                this.field_179409_b.getPrice(random);
            }
            if (this.field_179408_d != null) {
                this.field_179408_d.getPrice(random);
            }
            list.add(new MerchantRecipe(new ItemStack(this.field_179411_a.getItem(), 1, this.field_179411_a.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.field_179410_c.getItem(), 1, this.field_179410_c.getMetadata())));
        }
    }
    
    static class PriceInfo extends Tuple
    {
        public PriceInfo(final int n, final int n2) {
            super(n, n2);
        }
        
        public int getPrice(final Random random) {
            return (int)(((int)this.getFirst() >= (int)this.getSecond()) ? this.getFirst() : ((int)this.getFirst() + random.nextInt((int)this.getSecond() - (int)this.getFirst() + 1)));
        }
    }
    
    interface ITradeList
    {
        void modifyMerchantRecipeList(final MerchantRecipeList p0, final Random p1);
    }
    
    static class ListEnchantedBookForEmeralds implements ITradeList
    {
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            final Enchantment enchantment = Enchantment.enchantmentsBookList[random.nextInt(Enchantment.enchantmentsBookList.length)];
            final int randomIntegerInRange = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
            final ItemStack enchantedItemStack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, randomIntegerInRange));
            final int n = 2 + random.nextInt(5 + randomIntegerInRange * 10) + 3 * randomIntegerInRange;
            list.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, 64), enchantedItemStack));
        }
    }
    
    static class ListEnchantedItemForEmeralds implements ITradeList
    {
        public PriceInfo field_179406_b;
        public ItemStack field_179407_a;
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            if (this.field_179406_b != null) {
                this.field_179406_b.getPrice(random);
            }
            list.add(new MerchantRecipe(new ItemStack(Items.emerald, 1, 0), EnchantmentHelper.addRandomEnchantment(random, new ItemStack(this.field_179407_a.getItem(), 1, this.field_179407_a.getMetadata()), 5 + random.nextInt(15))));
        }
        
        public ListEnchantedItemForEmeralds(final Item item, final PriceInfo field_179406_b) {
            this.field_179407_a = new ItemStack(item);
            this.field_179406_b = field_179406_b;
        }
    }
    
    static class EmeraldForItems implements ITradeList
    {
        public Item sellItem;
        public PriceInfo price;
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            if (this.price != null) {
                this.price.getPrice(random);
            }
            list.add(new MerchantRecipe(new ItemStack(this.sellItem, 1, 0), Items.emerald));
        }
        
        public EmeraldForItems(final Item sellItem, final PriceInfo price) {
            this.sellItem = sellItem;
            this.price = price;
        }
    }
    
    static class ListItemForEmeralds implements ITradeList
    {
        public ItemStack field_179403_a;
        public PriceInfo field_179402_b;
        
        @Override
        public void modifyMerchantRecipeList(final MerchantRecipeList list, final Random random) {
            if (this.field_179402_b != null) {
                this.field_179402_b.getPrice(random);
            }
            list.add(new MerchantRecipe(new ItemStack(Items.emerald, 1, 0), new ItemStack(this.field_179403_a.getItem(), 1, this.field_179403_a.getMetadata())));
        }
        
        public ListItemForEmeralds(final ItemStack field_179403_a, final PriceInfo field_179402_b) {
            this.field_179403_a = field_179403_a;
            this.field_179402_b = field_179402_b;
        }
        
        public ListItemForEmeralds(final Item item, final PriceInfo field_179402_b) {
            this.field_179403_a = new ItemStack(item);
            this.field_179402_b = field_179402_b;
        }
    }
}
