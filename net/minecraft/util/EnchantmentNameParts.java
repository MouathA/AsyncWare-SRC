package net.minecraft.util;

import java.util.*;

public class EnchantmentNameParts
{
    private Random rand;
    private String[] namePartsArray;
    private static final EnchantmentNameParts instance;
    
    public void reseedRandomGenerator(final long seed) {
        this.rand.setSeed(seed);
    }
    
    public static EnchantmentNameParts getInstance() {
        return EnchantmentNameParts.instance;
    }
    
    static {
        instance = new EnchantmentNameParts();
    }
    
    public String generateNewRandomName() {
        final int n = this.rand.nextInt(2) + 3;
        String string = "";
        while (0 < n) {
            string += this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
            int n2 = 0;
            ++n2;
        }
        return string;
    }
    
    public EnchantmentNameParts() {
        this.rand = new Random();
        this.namePartsArray = "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ".split(" ");
    }
}
