package net.minecraft.creativetab;

import net.minecraft.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;

public abstract class CreativeTabs
{
    public static final CreativeTabs tabTools;
    private final int tabIndex;
    public static final CreativeTabs tabBlock;
    private EnumEnchantmentType[] enchantmentTypes;
    public static final CreativeTabs[] creativeTabArray;
    private String theTexture;
    public static final CreativeTabs tabAllSearch;
    private final String tabLabel;
    public static final CreativeTabs tabMisc;
    public static final CreativeTabs tabInventory;
    private boolean hasScrollbar;
    public static final CreativeTabs tabDecorations;
    public static final CreativeTabs tabRedstone;
    private boolean drawTitle;
    public static final CreativeTabs tabTransport;
    public static final CreativeTabs tabCombat;
    public static final CreativeTabs tabFood;
    public static final CreativeTabs tabMaterials;
    public static final CreativeTabs tabBrewing;
    private ItemStack iconItemStack;
    
    public String getTabLabel() {
        return this.tabLabel;
    }
    
    public CreativeTabs setNoScrollbar() {
        this.hasScrollbar = false;
        return this;
    }
    
    public abstract Item getTabIconItem();
    
    public int getTabIndex() {
        return this.tabIndex;
    }
    
    public boolean shouldHidePlayerInventory() {
        return this.hasScrollbar;
    }
    
    public CreativeTabs(final int tabIndex, final String tabLabel) {
        this.theTexture = "items.png";
        this.hasScrollbar = true;
        this.drawTitle = true;
        this.tabIndex = tabIndex;
        this.tabLabel = tabLabel;
        CreativeTabs.creativeTabArray[tabIndex] = this;
    }
    
    public int getIconItemDamage() {
        return 0;
    }
    
    public CreativeTabs setBackgroundImageName(final String theTexture) {
        this.theTexture = theTexture;
        return this;
    }
    
    public boolean isTabInFirstRow() {
        return this.tabIndex < 6;
    }
    
    public String getBackgroundImageName() {
        return this.theTexture;
    }
    
    public void addEnchantmentBooksToList(final List list, final EnumEnchantmentType... array) {
        final Enchantment[] enchantmentsBookList = Enchantment.enchantmentsBookList;
        while (0 < enchantmentsBookList.length) {
            final Enchantment enchantment = enchantmentsBookList[0];
            if (enchantment != null && enchantment.type != null) {
                if (0 < array.length) {}
                list.add(Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, enchantment.getMaxLevel())));
            }
            int n = 0;
            ++n;
        }
    }
    
    public CreativeTabs setRelevantEnchantmentTypes(final EnumEnchantmentType... enchantmentTypes) {
        this.enchantmentTypes = enchantmentTypes;
        return this;
    }
    
    public CreativeTabs setNoTitle() {
        this.drawTitle = false;
        return this;
    }
    
    public boolean drawInForegroundOfTab() {
        return this.drawTitle;
    }
    
    static {
        creativeTabArray = new CreativeTabs[12];
        tabBlock = new CreativeTabs(0, "buildingBlocks") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.brick_block);
            }
        };
        tabDecorations = new CreativeTabs(1, "decorations") {
            @Override
            public int getIconItemDamage() {
                return BlockDoublePlant.EnumPlantType.PAEONIA.getMeta();
            }
            
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.double_plant);
            }
        };
        tabRedstone = new CreativeTabs(2, "redstone") {
            @Override
            public Item getTabIconItem() {
                return Items.redstone;
            }
        };
        tabTransport = new CreativeTabs(3, "transportation") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.golden_rail);
            }
        };
        tabMisc = new CreativeTabs(4, "misc") {
            @Override
            public Item getTabIconItem() {
                return Items.lava_bucket;
            }
        }.setRelevantEnchantmentTypes(EnumEnchantmentType.ALL);
        tabAllSearch = new CreativeTabs(5, "search") {
            @Override
            public Item getTabIconItem() {
                return Items.compass;
            }
        }.setBackgroundImageName("item_search.png");
        tabFood = new CreativeTabs(6, "food") {
            @Override
            public Item getTabIconItem() {
                return Items.apple;
            }
        };
        tabTools = new CreativeTabs(7, "tools") {
            @Override
            public Item getTabIconItem() {
                return Items.iron_axe;
            }
        }.setRelevantEnchantmentTypes(EnumEnchantmentType.DIGGER, EnumEnchantmentType.FISHING_ROD, EnumEnchantmentType.BREAKABLE);
        tabCombat = new CreativeTabs(8, "combat") {
            @Override
            public Item getTabIconItem() {
                return Items.golden_sword;
            }
        }.setRelevantEnchantmentTypes(EnumEnchantmentType.ARMOR, EnumEnchantmentType.ARMOR_FEET, EnumEnchantmentType.ARMOR_HEAD, EnumEnchantmentType.ARMOR_LEGS, EnumEnchantmentType.ARMOR_TORSO, EnumEnchantmentType.BOW, EnumEnchantmentType.WEAPON);
        tabBrewing = new CreativeTabs(9, "brewing") {
            @Override
            public Item getTabIconItem() {
                return Items.potionitem;
            }
        };
        tabMaterials = new CreativeTabs(10, "materials") {
            @Override
            public Item getTabIconItem() {
                return Items.stick;
            }
        };
        tabInventory = new CreativeTabs(11, "inventory") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(Blocks.chest);
            }
        }.setBackgroundImageName("inventory.png").setNoScrollbar().setNoTitle();
    }
    
    public void displayAllReleventItems(final List list) {
        for (final Item item : Item.itemRegistry) {
            if (item != null && item.getCreativeTab() == this) {
                item.getSubItems(item, this, list);
            }
        }
        if (this.getRelevantEnchantmentTypes() != null) {
            this.addEnchantmentBooksToList(list, this.getRelevantEnchantmentTypes());
        }
    }
    
    public ItemStack getIconItemStack() {
        if (this.iconItemStack == null) {
            this.iconItemStack = new ItemStack(this.getTabIconItem(), 1, this.getIconItemDamage());
        }
        return this.iconItemStack;
    }
    
    public int getTabColumn() {
        return this.tabIndex % 6;
    }
    
    public boolean hasRelevantEnchantmentType(final EnumEnchantmentType enumEnchantmentType) {
        if (this.enchantmentTypes == null) {
            return false;
        }
        final EnumEnchantmentType[] enchantmentTypes = this.enchantmentTypes;
        while (0 < enchantmentTypes.length) {
            if (enchantmentTypes[0] == enumEnchantmentType) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public String getTranslatedTabLabel() {
        return "itemGroup." + this.getTabLabel();
    }
    
    public EnumEnchantmentType[] getRelevantEnchantmentTypes() {
        return this.enchantmentTypes;
    }
}
