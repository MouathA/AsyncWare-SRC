package net.minecraft.stats;

import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;

public class StatCrafting extends StatBase
{
    private final Item field_150960_a;
    
    public StatCrafting(final String s, final String s2, final IChatComponent chatComponent, final Item field_150960_a) {
        super(s + s2, chatComponent);
        this.field_150960_a = field_150960_a;
        final int idFromItem = Item.getIdFromItem(field_150960_a);
        if (idFromItem != 0) {
            IScoreObjectiveCriteria.INSTANCES.put(s + idFromItem, this.func_150952_k());
        }
    }
    
    public Item func_150959_a() {
        return this.field_150960_a;
    }
}
