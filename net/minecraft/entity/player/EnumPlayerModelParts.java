package net.minecraft.entity.player;

import net.minecraft.util.*;

public enum EnumPlayerModelParts
{
    CAPE("CAPE", 0, 0, "cape");
    
    private final IChatComponent field_179339_k;
    private final int partId;
    private final String partName;
    private static final EnumPlayerModelParts[] $VALUES;
    
    RIGHT_SLEEVE("RIGHT_SLEEVE", 3, 3, "right_sleeve");
    
    private final int partMask;
    
    RIGHT_PANTS_LEG("RIGHT_PANTS_LEG", 5, 5, "right_pants_leg"), 
    HAT("HAT", 6, 6, "hat"), 
    LEFT_SLEEVE("LEFT_SLEEVE", 2, 2, "left_sleeve"), 
    JACKET("JACKET", 1, 1, "jacket"), 
    LEFT_PANTS_LEG("LEFT_PANTS_LEG", 4, 4, "left_pants_leg");
    
    public int getPartId() {
        return this.partId;
    }
    
    static {
        $VALUES = new EnumPlayerModelParts[] { EnumPlayerModelParts.CAPE, EnumPlayerModelParts.JACKET, EnumPlayerModelParts.LEFT_SLEEVE, EnumPlayerModelParts.RIGHT_SLEEVE, EnumPlayerModelParts.LEFT_PANTS_LEG, EnumPlayerModelParts.RIGHT_PANTS_LEG, EnumPlayerModelParts.HAT };
    }
    
    private EnumPlayerModelParts(final String s, final int n, final int partId, final String partName) {
        this.partId = partId;
        this.partMask = 1 << partId;
        this.partName = partName;
        this.field_179339_k = new ChatComponentTranslation("options.modelPart." + partName, new Object[0]);
    }
    
    public int getPartMask() {
        return this.partMask;
    }
    
    public IChatComponent func_179326_d() {
        return this.field_179339_k;
    }
    
    public String getPartName() {
        return this.partName;
    }
}
