package net.minecraft.world.gen.structure;

import net.minecraft.world.*;
import java.util.*;

public class StructureMineshaftStart extends StructureStart
{
    public StructureMineshaftStart() {
    }
    
    public StructureMineshaftStart(final World world, final Random random, final int n, final int n2) {
        super(n, n2);
        final StructureMineshaftPieces.Room room = new StructureMineshaftPieces.Room(0, random, (n << 4) + 2, (n2 << 4) + 2);
        this.components.add(room);
        room.buildComponent(room, this.components, random);
        this.updateBoundingBox();
        this.markAvailableHeight(world, random, 10);
    }
}
