package net.minecraft.world;

public enum EnumDifficulty
{
    private static final EnumDifficulty[] difficultyEnums;
    
    EASY("EASY", 1, 1, "options.difficulty.easy");
    
    private static final EnumDifficulty[] $VALUES;
    
    PEACEFUL("PEACEFUL", 0, 0, "options.difficulty.peaceful");
    
    private final int difficultyId;
    private final String difficultyResourceKey;
    
    HARD("HARD", 3, 3, "options.difficulty.hard"), 
    NORMAL("NORMAL", 2, 2, "options.difficulty.normal");
    
    public static EnumDifficulty getDifficultyEnum(final int n) {
        return EnumDifficulty.difficultyEnums[n % EnumDifficulty.difficultyEnums.length];
    }
    
    private EnumDifficulty(final String s, final int n, final int difficultyId, final String difficultyResourceKey) {
        this.difficultyId = difficultyId;
        this.difficultyResourceKey = difficultyResourceKey;
    }
    
    public int getDifficultyId() {
        return this.difficultyId;
    }
    
    static {
        $VALUES = new EnumDifficulty[] { EnumDifficulty.PEACEFUL, EnumDifficulty.EASY, EnumDifficulty.NORMAL, EnumDifficulty.HARD };
        difficultyEnums = new EnumDifficulty[values().length];
        final EnumDifficulty[] values = values();
        while (0 < values.length) {
            final EnumDifficulty enumDifficulty = values[0];
            EnumDifficulty.difficultyEnums[enumDifficulty.difficultyId] = enumDifficulty;
            int n = 0;
            ++n;
        }
    }
    
    public String getDifficultyResourceKey() {
        return this.difficultyResourceKey;
    }
}
