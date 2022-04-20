package net.minecraft.tileentity;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class TileEntityBanner extends TileEntity
{
    private boolean field_175119_g;
    private List patternList;
    private NBTTagList patterns;
    private int baseColor;
    private String patternResourceLocation;
    private List colorList;
    
    public void setItemValues(final ItemStack itemStack) {
        this.patterns = null;
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("BlockEntityTag", 10)) {
            final NBTTagCompound compoundTag = itemStack.getTagCompound().getCompoundTag("BlockEntityTag");
            if (compoundTag.hasKey("Patterns")) {
                this.patterns = (NBTTagList)compoundTag.getTagList("Patterns", 10).copy();
            }
            if (compoundTag.hasKey("Base", 99)) {
                this.baseColor = compoundTag.getInteger("Base");
            }
            else {
                this.baseColor = (itemStack.getMetadata() & 0xF);
            }
        }
        else {
            this.baseColor = (itemStack.getMetadata() & 0xF);
        }
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = "";
        this.field_175119_g = true;
    }
    
    public String func_175116_e() {
        this.initializeBannerData();
        return this.patternResourceLocation;
    }
    
    private void initializeBannerData() {
        if (this.patternList == null || this.colorList == null || this.patternResourceLocation == null) {
            if (!this.field_175119_g) {
                this.patternResourceLocation = "";
            }
            else {
                this.patternList = Lists.newArrayList();
                this.colorList = Lists.newArrayList();
                this.patternList.add(EnumBannerPattern.BASE);
                this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
                this.patternResourceLocation = "b" + this.baseColor;
                if (this.patterns != null) {
                    while (0 < this.patterns.tagCount()) {
                        final NBTTagCompound compoundTag = this.patterns.getCompoundTagAt(0);
                        final EnumBannerPattern patternByID = EnumBannerPattern.getPatternByID(compoundTag.getString("Pattern"));
                        if (patternByID != null) {
                            this.patternList.add(patternByID);
                            final int integer = compoundTag.getInteger("Color");
                            this.colorList.add(EnumDyeColor.byDyeDamage(integer));
                            this.patternResourceLocation = this.patternResourceLocation + patternByID.getPatternID() + integer;
                        }
                        int n = 0;
                        ++n;
                    }
                }
            }
        }
    }
    
    public static int getPatterns(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound("BlockEntityTag", false);
        return (subCompound != null && subCompound.hasKey("Patterns")) ? subCompound.getTagList("Patterns", 10).tagCount() : 0;
    }
    
    public List getColorList() {
        this.initializeBannerData();
        return this.colorList;
    }
    
    public static void removeBannerData(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound("BlockEntityTag", false);
        if (subCompound != null && subCompound.hasKey("Patterns", 9)) {
            final NBTTagList tagList = subCompound.getTagList("Patterns", 10);
            if (tagList.tagCount() > 0) {
                tagList.removeTag(tagList.tagCount() - 1);
                if (tagList.hasNoTags()) {
                    itemStack.getTagCompound().removeTag("BlockEntityTag");
                    if (itemStack.getTagCompound().hasNoTags()) {
                        itemStack.setTagCompound(null);
                    }
                }
            }
        }
    }
    
    public List getPatternList() {
        this.initializeBannerData();
        return this.patternList;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        this.baseColor = nbtTagCompound.getInteger("Base");
        this.patterns = nbtTagCompound.getTagList("Patterns", 10);
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = null;
        this.field_175119_g = true;
    }
    
    public NBTTagList func_181021_d() {
        return this.patterns;
    }
    
    public static int getBaseColor(final ItemStack itemStack) {
        final NBTTagCompound subCompound = itemStack.getSubCompound("BlockEntityTag", false);
        return (subCompound != null && subCompound.hasKey("Base")) ? subCompound.getInteger("Base") : itemStack.getMetadata();
    }
    
    public static void func_181020_a(final NBTTagCompound nbtTagCompound, final int n, final NBTTagList list) {
        nbtTagCompound.setInteger("Base", n);
        if (list != null) {
            nbtTagCompound.setTag("Patterns", list);
        }
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.pos, 6, nbtTagCompound);
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        func_181020_a(nbtTagCompound, this.baseColor, this.patterns);
    }
    
    public int getBaseColor() {
        return this.baseColor;
    }
    
    public enum EnumBannerPattern
    {
        HALF_VERTICAL_MIRROR("HALF_VERTICAL_MIRROR", 28, "half_vertical_right", "vhr", " ##", " ##", " ##"), 
        STRIPE_SMALL("STRIPE_SMALL", 13, "small_stripes", "ss", "# #", "# #", "   "), 
        STRIPE_TOP("STRIPE_TOP", 6, "stripe_top", "ts", "###", "   ", "   ");
        
        private String[] craftingLayers;
        
        CREEPER("CREEPER", 32, "creeper", "cre", new ItemStack(Items.skull, 1, 4)), 
        RHOMBUS_MIDDLE("RHOMBUS_MIDDLE", 25, "rhombus", "mr", " # ", "# #", " # "), 
        HALF_HORIZONTAL("HALF_HORIZONTAL", 27, "half_horizontal", "hh", "###", "###", "   ");
        
        private String patternName;
        
        DIAGONAL_LEFT("DIAGONAL_LEFT", 20, "diagonal_left", "ld", "## ", "#  ", "   "), 
        SQUARE_TOP_RIGHT("SQUARE_TOP_RIGHT", 4, "square_top_right", "tr", "  #", "   ", "   "), 
        STRIPE_RIGHT("STRIPE_RIGHT", 8, "stripe_right", "rs", "  #", "  #", "  #"), 
        CROSS("CROSS", 14, "cross", "cr", "# #", " # ", "# #"), 
        BRICKS("BRICKS", 35, "bricks", "bri", new ItemStack(Blocks.brick_block)), 
        GRADIENT("GRADIENT", 33, "gradient", "gra", "# #", " # ", " # "), 
        DIAGONAL_LEFT_MIRROR("DIAGONAL_LEFT_MIRROR", 22, "diagonal_up_left", "lud", "   ", "#  ", "## "), 
        HALF_VERTICAL("HALF_VERTICAL", 26, "half_vertical", "vh", "## ", "## ", "## "), 
        STRIPE_BOTTOM("STRIPE_BOTTOM", 5, "stripe_bottom", "bs", "   ", "   ", "###"), 
        SQUARE_BOTTOM_LEFT("SQUARE_BOTTOM_LEFT", 1, "square_bottom_left", "bl", "   ", "   ", "#  "), 
        SKULL("SKULL", 36, "skull", "sku", new ItemStack(Items.skull, 1, 1)), 
        TRIANGLES_BOTTOM("TRIANGLES_BOTTOM", 18, "triangles_bottom", "bts", "   ", "# #", " # "), 
        TRIANGLE_BOTTOM("TRIANGLE_BOTTOM", 16, "triangle_bottom", "bt", "   ", " # ", "# #");
        
        private static final EnumBannerPattern[] $VALUES;
        private ItemStack patternCraftingStack;
        
        STRIPE_DOWNLEFT("STRIPE_DOWNLEFT", 12, "stripe_downleft", "dls", "  #", " # ", "#  "), 
        TRIANGLE_TOP("TRIANGLE_TOP", 17, "triangle_top", "tt", "# #", " # ", "   "), 
        CURLY_BORDER("CURLY_BORDER", 31, "curly_border", "cbo", new ItemStack(Blocks.vine)), 
        STRIPE_CENTER("STRIPE_CENTER", 9, "stripe_center", "cs", " # ", " # ", " # "), 
        STRAIGHT_CROSS("STRAIGHT_CROSS", 15, "straight_cross", "sc", " # ", "###", " # "), 
        FLOWER("FLOWER", 37, "flower", "flo", new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.getMeta())), 
        STRIPE_DOWNRIGHT("STRIPE_DOWNRIGHT", 11, "stripe_downright", "drs", "#  ", " # ", "  #"), 
        SQUARE_BOTTOM_RIGHT("SQUARE_BOTTOM_RIGHT", 2, "square_bottom_right", "br", "   ", "   ", "  #"), 
        HALF_HORIZONTAL_MIRROR("HALF_HORIZONTAL_MIRROR", 29, "half_horizontal_bottom", "hhb", "   ", "###", "###"), 
        DIAGONAL_RIGHT_MIRROR("DIAGONAL_RIGHT_MIRROR", 23, "diagonal_right", "rud", " ##", "  #", "   "), 
        SQUARE_TOP_LEFT("SQUARE_TOP_LEFT", 3, "square_top_left", "tl", "#  ", "   ", "   "), 
        STRIPE_LEFT("STRIPE_LEFT", 7, "stripe_left", "ls", "#  ", "#  ", "#  "), 
        DIAGONAL_RIGHT("DIAGONAL_RIGHT", 21, "diagonal_up_right", "rd", "   ", "  #", " ##"), 
        STRIPE_MIDDLE("STRIPE_MIDDLE", 10, "stripe_middle", "ms", "   ", "###", "   "), 
        CIRCLE_MIDDLE("CIRCLE_MIDDLE", 24, "circle", "mc", "   ", " # ", "   "), 
        BORDER("BORDER", 30, "border", "bo", "###", "# #", "###"), 
        TRIANGLES_TOP("TRIANGLES_TOP", 19, "triangles_top", "tts", " # ", "# #", "   "), 
        GRADIENT_UP("GRADIENT_UP", 34, "gradient_up", "gru", " # ", " # ", "# #"), 
        MOJANG("MOJANG", 38, "mojang", "moj", new ItemStack(Items.golden_apple, 1, 1));
        
        private String patternID;
        
        BASE("BASE", 0, "base", "b");
        
        public String[] getCraftingLayers() {
            return this.craftingLayers;
        }
        
        private EnumBannerPattern(final String s, final int n, final String patternName, final String patternID) {
            this.craftingLayers = new String[3];
            this.patternName = patternName;
            this.patternID = patternID;
        }
        
        public static EnumBannerPattern getPatternByID(final String s) {
            final EnumBannerPattern[] values = values();
            while (0 < values.length) {
                final EnumBannerPattern enumBannerPattern = values[0];
                if (enumBannerPattern.patternID.equals(s)) {
                    return enumBannerPattern;
                }
                int n = 0;
                ++n;
            }
            return null;
        }
        
        static {
            $VALUES = new EnumBannerPattern[] { EnumBannerPattern.BASE, EnumBannerPattern.SQUARE_BOTTOM_LEFT, EnumBannerPattern.SQUARE_BOTTOM_RIGHT, EnumBannerPattern.SQUARE_TOP_LEFT, EnumBannerPattern.SQUARE_TOP_RIGHT, EnumBannerPattern.STRIPE_BOTTOM, EnumBannerPattern.STRIPE_TOP, EnumBannerPattern.STRIPE_LEFT, EnumBannerPattern.STRIPE_RIGHT, EnumBannerPattern.STRIPE_CENTER, EnumBannerPattern.STRIPE_MIDDLE, EnumBannerPattern.STRIPE_DOWNRIGHT, EnumBannerPattern.STRIPE_DOWNLEFT, EnumBannerPattern.STRIPE_SMALL, EnumBannerPattern.CROSS, EnumBannerPattern.STRAIGHT_CROSS, EnumBannerPattern.TRIANGLE_BOTTOM, EnumBannerPattern.TRIANGLE_TOP, EnumBannerPattern.TRIANGLES_BOTTOM, EnumBannerPattern.TRIANGLES_TOP, EnumBannerPattern.DIAGONAL_LEFT, EnumBannerPattern.DIAGONAL_RIGHT, EnumBannerPattern.DIAGONAL_LEFT_MIRROR, EnumBannerPattern.DIAGONAL_RIGHT_MIRROR, EnumBannerPattern.CIRCLE_MIDDLE, EnumBannerPattern.RHOMBUS_MIDDLE, EnumBannerPattern.HALF_VERTICAL, EnumBannerPattern.HALF_HORIZONTAL, EnumBannerPattern.HALF_VERTICAL_MIRROR, EnumBannerPattern.HALF_HORIZONTAL_MIRROR, EnumBannerPattern.BORDER, EnumBannerPattern.CURLY_BORDER, EnumBannerPattern.CREEPER, EnumBannerPattern.GRADIENT, EnumBannerPattern.GRADIENT_UP, EnumBannerPattern.BRICKS, EnumBannerPattern.SKULL, EnumBannerPattern.FLOWER, EnumBannerPattern.MOJANG };
        }
        
        private EnumBannerPattern(final String s, final int n, final String s2, final String s3, final ItemStack patternCraftingStack) {
            this(s, n, s2, s3);
            this.patternCraftingStack = patternCraftingStack;
        }
        
        public ItemStack getCraftingStack() {
            return this.patternCraftingStack;
        }
        
        public String getPatternName() {
            return this.patternName;
        }
        
        private EnumBannerPattern(final String s, final int n, final String s2, final String s3, final String s4, final String s5, final String s6) {
            this(s, n, s2, s3);
            this.craftingLayers[0] = s4;
            this.craftingLayers[1] = s5;
            this.craftingLayers[2] = s6;
        }
        
        public boolean hasValidCrafting() {
            return this.patternCraftingStack != null || this.craftingLayers[0] != null;
        }
        
        public boolean hasCraftingStack() {
            return this.patternCraftingStack != null;
        }
        
        public String getPatternID() {
            return this.patternID;
        }
    }
}
