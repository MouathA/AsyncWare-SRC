package net.minecraft.potion;

import net.minecraft.util.*;
import com.google.common.collect.*;
import java.util.*;

public class PotionHelper
{
    public static final String sugarEffect = "-0+1-2-3&4-4+13";
    public static final String field_77924_a;
    public static final String spiderEyeEffect = "-0-1+2-3&4-4+13";
    private static final Map DATAVALUE_COLORS;
    public static final String blazePowderEffect = "+0-1-2+3&4-4+13";
    private static final Map potionAmplifiers;
    private static final Map potionRequirements;
    public static final String pufferfishEffect = "+0-1+2+3+13&4-4";
    public static final String glowstoneEffect = "+5-6-7";
    public static final String speckledMelonEffect = "+0-1+2-3&4-4+13";
    public static final String ghastTearEffect = "+0-1-2-3&4-4+13";
    public static final String redstoneEffect = "-5+6-7";
    public static final String magmaCreamEffect = "+0+1-2-3&4-4+13";
    public static final String goldenCarrotEffect = "-0+1+2-3+13&4-4";
    public static final String gunpowderEffect = "+14&13-13";
    public static final String fermentedSpiderEyeEffect = "-0+3-4+13";
    public static final String rabbitFootEffect = "+0+1-2+3&4-4+13";
    
    public static String getPotionPrefix(final int n) {
        return PotionHelper.potionPrefixes[getPotionPrefixIndex(n)];
    }
    
    static {
        field_77924_a = null;
        potionRequirements = Maps.newHashMap();
        potionAmplifiers = Maps.newHashMap();
        DATAVALUE_COLORS = Maps.newHashMap();
        PotionHelper.potionPrefixes = new String[] { "potion.prefix.mundane", "potion.prefix.uninteresting", "potion.prefix.bland", "potion.prefix.clear", "potion.prefix.milky", "potion.prefix.diffuse", "potion.prefix.artless", "potion.prefix.thin", "potion.prefix.awkward", "potion.prefix.flat", "potion.prefix.bulky", "potion.prefix.bungling", "potion.prefix.buttered", "potion.prefix.smooth", "potion.prefix.suave", "potion.prefix.debonair", "potion.prefix.thick", "potion.prefix.elegant", "potion.prefix.fancy", "potion.prefix.charming", "potion.prefix.dashing", "potion.prefix.refined", "potion.prefix.cordial", "potion.prefix.sparkling", "potion.prefix.potent", "potion.prefix.foul", "potion.prefix.odorless", "potion.prefix.rank", "potion.prefix.harsh", "potion.prefix.acrid", "potion.prefix.gross", "potion.prefix.stinky" };
        PotionHelper.potionRequirements.put(Potion.regeneration.getId(), "0 & !1 & !2 & !3 & 0+6");
        PotionHelper.potionRequirements.put(Potion.moveSpeed.getId(), "!0 & 1 & !2 & !3 & 1+6");
        PotionHelper.potionRequirements.put(Potion.fireResistance.getId(), "0 & 1 & !2 & !3 & 0+6");
        PotionHelper.potionRequirements.put(Potion.heal.getId(), "0 & !1 & 2 & !3");
        PotionHelper.potionRequirements.put(Potion.poison.getId(), "!0 & !1 & 2 & !3 & 2+6");
        PotionHelper.potionRequirements.put(Potion.weakness.getId(), "!0 & !1 & !2 & 3 & 3+6");
        PotionHelper.potionRequirements.put(Potion.harm.getId(), "!0 & !1 & 2 & 3");
        PotionHelper.potionRequirements.put(Potion.moveSlowdown.getId(), "!0 & 1 & !2 & 3 & 3+6");
        PotionHelper.potionRequirements.put(Potion.damageBoost.getId(), "0 & !1 & !2 & 3 & 3+6");
        PotionHelper.potionRequirements.put(Potion.nightVision.getId(), "!0 & 1 & 2 & !3 & 2+6");
        PotionHelper.potionRequirements.put(Potion.invisibility.getId(), "!0 & 1 & 2 & 3 & 2+6");
        PotionHelper.potionRequirements.put(Potion.waterBreathing.getId(), "0 & !1 & 2 & 3 & 2+6");
        PotionHelper.potionRequirements.put(Potion.jump.getId(), "0 & 1 & !2 & 3 & 3+6");
        PotionHelper.potionAmplifiers.put(Potion.moveSpeed.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.digSpeed.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.damageBoost.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.regeneration.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.harm.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.heal.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.resistance.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.poison.getId(), "5");
        PotionHelper.potionAmplifiers.put(Potion.jump.getId(), "5");
    }
    
    private static int isFlagSet(final int n, final int n2) {
        return (n2 != 0) ? 1 : 0;
    }
    
    private static int func_77904_a(final boolean b, final boolean b2, final boolean b3, final int n, final int n2, final int n3, final int n4) {
        if (b) {
            isFlagUnset(n4, n2);
        }
        else if (n != -1) {
            if (n != 0 || countSetFlags(n4) != n2) {
                if (n != 1 || countSetFlags(n4) <= n2) {
                    if (n == 2 && countSetFlags(n4) < n2) {}
                }
            }
        }
        else {
            isFlagSet(n4, n2);
        }
        if (b2) {}
        if (b3) {}
        return 1;
    }
    
    public static int getLiquidColor(final int n, final boolean b) {
        final Integer func_181756_a = IntegerCache.func_181756_a(n);
        if (b) {
            return calcPotionLiquidColor(getPotionEffects(func_181756_a, true));
        }
        if (PotionHelper.DATAVALUE_COLORS.containsKey(func_181756_a)) {
            return (int)PotionHelper.DATAVALUE_COLORS.get(func_181756_a);
        }
        final int calcPotionLiquidColor = calcPotionLiquidColor(getPotionEffects(func_181756_a, false));
        PotionHelper.DATAVALUE_COLORS.put(func_181756_a, calcPotionLiquidColor);
        return calcPotionLiquidColor;
    }
    
    public static int getPotionPrefixIndex(final int n) {
        return func_77908_a(n, 5, 4, 3, 2, 1);
    }
    
    public static int func_77908_a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n2 != 0) {
            16;
        }
        else {
            false;
        }
        n | ((n3 != 0) ? 8 : 0);
        n | ((n4 != 0) ? 4 : 0);
        n | ((n5 != 0) ? 2 : 0);
        return n | ((n6 != 0) ? 1 : 0);
    }
    
    private static int isFlagUnset(final int n, final int n2) {
        return (n2 == 0) ? 1 : 0;
    }
    
    private static int parsePotionEffects(final String s, final int n, final int n2, final int n3) {
        if (n >= s.length() || n2 < 0 || n >= n2) {
            return 0;
        }
        final int index = s.indexOf(124, n);
        if (index >= 0 && index < n2) {
            final int potionEffects = parsePotionEffects(s, n, index - 1, n3);
            if (potionEffects > 0) {
                return potionEffects;
            }
            parsePotionEffects(s, index + 1, n2, n3);
            return 0;
        }
        else {
            final int index2 = s.indexOf(38, n);
            if (index2 >= 0 && index2 < n2) {
                parsePotionEffects(s, n, index2 - 1, n3);
                return 0;
            }
            for (int i = n; i < n2; ++i) {
                final char char1 = s.charAt(i);
                if (char1 < '0' || char1 > '9') {
                    if (char1 != '*') {
                        if (char1 != '!') {
                            if (char1 != '-') {
                                if (char1 != '=' && char1 != '<' && char1 != '>') {
                                    if (char1 == '+') {}
                                }
                                else if (char1 != '=') {
                                    if (char1 != '<') {
                                        if (char1 == '>') {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return 0;
        }
    }
    
    public static int applyIngredient(final int n, final String s) {
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (char1 < '0' || char1 > '9') {
                if (char1 != '!') {
                    if (char1 != '-') {
                        if (char1 != '+') {
                            if (char1 == '&') {}
                        }
                    }
                }
            }
            int n2 = 0;
            ++n2;
        }
        return n & 0x7FFF;
    }
    
    public static List getPotionEffects(final int n, final boolean b) {
        List<PotionEffect> arrayList = null;
        final Potion[] potionTypes = Potion.potionTypes;
        while (0 < potionTypes.length) {
            final Potion potion = potionTypes[0];
            if (potion != null && (!potion.isUsable() || b)) {
                final String s = PotionHelper.potionRequirements.get(potion.getId());
                if (s != null) {
                    parsePotionEffects(s, 0, s.length(), n);
                    final String s2 = PotionHelper.potionAmplifiers.get(potion.getId());
                    if (s2 != null) {
                        parsePotionEffects(s2, 0, s2.length(), n);
                    }
                    if (!potion.isInstant()) {
                        final int n2 = (int)Math.round(1 * potion.getEffectiveness());
                        if ((n & 0x4000) != 0x0) {
                            final int n3 = (int)Math.round(1 * 0.75 + 0.5);
                        }
                    }
                    if (arrayList == null) {
                        arrayList = (List<PotionEffect>)Lists.newArrayList();
                    }
                    final PotionEffect potionEffect = new PotionEffect(potion.getId(), 1, 0);
                    if ((n & 0x4000) != 0x0) {
                        potionEffect.setSplashPotion(true);
                    }
                    arrayList.add(potionEffect);
                }
            }
            int n4 = 0;
            ++n4;
        }
        return arrayList;
    }
    
    public static int calcPotionLiquidColor(final Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return 3694022;
        }
        float n = 0.0f;
        float n2 = 0.0f;
        float n3 = 0.0f;
        float n4 = 0.0f;
        for (final PotionEffect potionEffect : collection) {
            if (potionEffect.getIsShowParticles()) {
                final int liquidColor = Potion.potionTypes[potionEffect.getPotionID()].getLiquidColor();
                while (0 <= potionEffect.getAmplifier()) {
                    n += (liquidColor >> 16 & 0xFF) / 255.0f;
                    n2 += (liquidColor >> 8 & 0xFF) / 255.0f;
                    n3 += (liquidColor >> 0 & 0xFF) / 255.0f;
                    ++n4;
                    int n5 = 0;
                    ++n5;
                }
            }
        }
        if (n4 == 0.0f) {
            return 0;
        }
        return (int)(n / n4 * 255.0f) << 16 | (int)(n2 / n4 * 255.0f) << 8 | (int)(n3 / n4 * 255.0f);
    }
    
    private static int brewBitOperations(final int p0, final int p1, final boolean p2, final boolean p3, final boolean p4) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     2: ifeq            12
        //     5: iload_0        
        //     6: iload_1        
        //     7: ifeq            65
        //    10: iconst_0       
        //    11: ireturn        
        //    12: iload_2        
        //    13: ifeq            27
        //    16: iload_0        
        //    17: iconst_1       
        //    18: iload_1        
        //    19: ishl           
        //    20: iconst_m1      
        //    21: ixor           
        //    22: iand           
        //    23: istore_0       
        //    24: goto            65
        //    27: iload_3        
        //    28: ifeq            59
        //    31: iload_0        
        //    32: iconst_1       
        //    33: iload_1        
        //    34: ishl           
        //    35: iand           
        //    36: ifne            48
        //    39: iload_0        
        //    40: iconst_1       
        //    41: iload_1        
        //    42: ishl           
        //    43: ior            
        //    44: istore_0       
        //    45: goto            65
        //    48: iload_0        
        //    49: iconst_1       
        //    50: iload_1        
        //    51: ishl           
        //    52: iconst_m1      
        //    53: ixor           
        //    54: iand           
        //    55: istore_0       
        //    56: goto            65
        //    59: iload_0        
        //    60: iconst_1       
        //    61: iload_1        
        //    62: ishl           
        //    63: ior            
        //    64: istore_0       
        //    65: iload_0        
        //    66: ireturn        
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0065 (coming from #0007).
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
    
    public static boolean getAreAmbient(final Collection collection) {
        final Iterator<PotionEffect> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getIsAmbient()) {
                return false;
            }
        }
        return true;
    }
    
    private static int countSetFlags(int i) {
        while (i > 0) {
            i &= i - 1;
            int n = 0;
            ++n;
        }
        return 0;
    }
}
