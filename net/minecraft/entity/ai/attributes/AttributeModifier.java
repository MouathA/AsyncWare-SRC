package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.*;
import net.minecraft.util.*;
import java.util.*;
import org.apache.commons.lang3.*;

public class AttributeModifier
{
    private final String name;
    private final UUID id;
    private final double amount;
    private final int operation;
    private boolean isSaved;
    
    public UUID getID() {
        return this.id;
    }
    
    public boolean isSaved() {
        return this.isSaved;
    }
    
    public AttributeModifier(final String s, final double n, final int n2) {
        this(MathHelper.getRandomUuid((Random)ThreadLocalRandom.current()), s, n, n2);
    }
    
    public int getOperation() {
        return this.operation;
    }
    
    @Override
    public String toString() {
        return "AttributeModifier{amount=" + this.amount + ", operation=" + this.operation + ", name='" + this.name + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
    }
    
    public String getName() {
        return this.name;
    }
    
    public AttributeModifier(final UUID id, final String name, final double amount, final int operation) {
        this.isSaved = true;
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.operation = operation;
        Validate.notEmpty((CharSequence)name, "Modifier name cannot be empty", new Object[0]);
        Validate.inclusiveBetween(0L, 2L, (long)operation, "Invalid operation");
    }
    
    @Override
    public int hashCode() {
        return (this.id != null) ? this.id.hashCode() : 0;
    }
    
    public AttributeModifier setSaved(final boolean isSaved) {
        this.isSaved = isSaved;
        return this;
    }
    
    public double getAmount() {
        return this.amount;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o != null && this.getClass() == o.getClass()) {
            final AttributeModifier attributeModifier = (AttributeModifier)o;
            if (this.id != null) {
                if (!this.id.equals(attributeModifier.id)) {
                    return false;
                }
            }
            else if (attributeModifier.id != null) {
                return false;
            }
            return true;
        }
        return false;
    }
}
