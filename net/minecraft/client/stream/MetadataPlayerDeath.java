package net.minecraft.client.stream;

import net.minecraft.entity.*;

public class MetadataPlayerDeath extends Metadata
{
    public MetadataPlayerDeath(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        super("player_death");
        if (entityLivingBase != null) {
            this.func_152808_a("player", entityLivingBase.getName());
        }
        if (entityLivingBase2 != null) {
            this.func_152808_a("killer", entityLivingBase2.getName());
        }
    }
}
