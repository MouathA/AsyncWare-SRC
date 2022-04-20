package com.nquantum.util.raytrace;

import java.util.*;
import net.minecraft.util.*;

public class TeleportResult
{
    public ArrayList positions;
    public boolean foundPath;
    public Vec3 lastPos;
    public ArrayList path;
    public ArrayList positionsBack;
    public ArrayList triedPaths;
    
    public TeleportResult(final ArrayList positions, final ArrayList positionsBack, final ArrayList triedPaths, final ArrayList path, final Vec3 lastPos, final boolean foundPath) {
        this.positions = positions;
        this.positionsBack = positionsBack;
        this.triedPaths = triedPaths;
        this.path = path;
        this.foundPath = foundPath;
        this.lastPos = lastPos;
    }
}
