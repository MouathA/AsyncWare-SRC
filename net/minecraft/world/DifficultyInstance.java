package net.minecraft.world;

import net.minecraft.util.*;

public class DifficultyInstance
{
    private final float additionalDifficulty;
    private final EnumDifficulty worldDifficulty;
    
    public DifficultyInstance(final EnumDifficulty worldDifficulty, final long n, final long n2, final float n3) {
        this.worldDifficulty = worldDifficulty;
        this.additionalDifficulty = this.calculateAdditionalDifficulty(worldDifficulty, n, n2, n3);
    }
    
    public float getClampedAdditionalDifficulty() {
        return (this.additionalDifficulty < 2.0f) ? 0.0f : ((this.additionalDifficulty > 4.0f) ? 1.0f : ((this.additionalDifficulty - 2.0f) / 2.0f));
    }
    
    public float getAdditionalDifficulty() {
        return this.additionalDifficulty;
    }
    
    private float calculateAdditionalDifficulty(final EnumDifficulty enumDifficulty, final long n, final long n2, final float n3) {
        if (enumDifficulty == EnumDifficulty.PEACEFUL) {
            return 0.0f;
        }
        final boolean b = enumDifficulty == EnumDifficulty.HARD;
        final float n4 = 0.75f;
        final float n5 = MathHelper.clamp_float((n - 72000.0f) / 1440000.0f, 0.0f, 1.0f) * 0.25f;
        final float n6 = n4 + n5;
        float n7 = 0.0f + MathHelper.clamp_float(n2 / 3600000.0f, 0.0f, 1.0f) * (b ? 1.0f : 0.75f) + MathHelper.clamp_float(n3 * 0.25f, 0.0f, n5);
        if (enumDifficulty == EnumDifficulty.EASY) {
            n7 *= 0.5f;
        }
        return enumDifficulty.getDifficultyId() * (n6 + n7);
    }
}
