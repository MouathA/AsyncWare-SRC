package com.nquantum.util.player;

import net.minecraft.entity.*;
import java.util.*;

public class TargetUtil
{
    public static ArrayList blackList;
    public static ArrayList targets;
    
    public static boolean hasEntity(final Entity entity) {
        if (entity == null) {
            return false;
        }
        if (!TargetUtil.targets.isEmpty()) {
            for (final EntityLivingBase entityLivingBase : TargetUtil.targets) {
                if (entityLivingBase == null) {
                    continue;
                }
                if (entityLivingBase.isEntityEqual(entity)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean blackEntity(final Entity entity) {
        if (entity == null) {
            return false;
        }
        if (!TargetUtil.blackList.isEmpty()) {
            for (final EntityLivingBase entityLivingBase : TargetUtil.blackList) {
                if (entityLivingBase == null) {
                    continue;
                }
                if (entityLivingBase.isEntityEqual(entity)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    static {
        TargetUtil.targets = new ArrayList();
        TargetUtil.blackList = new ArrayList();
    }
}
