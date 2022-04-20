package net.minecraft.world.border;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class WorldBorder
{
    private long startTime;
    private double centerX;
    private int warningDistance;
    private double damageAmount;
    private int worldSize;
    private int warningTime;
    private long endTime;
    private double endDiameter;
    private final List listeners;
    private double centerZ;
    private double damageBuffer;
    private double startDiameter;
    
    public double minX() {
        double n = this.getCenterX() - this.getDiameter() / 2.0;
        if (n < -this.worldSize) {
            n = -this.worldSize;
        }
        return n;
    }
    
    public void setTransition(final double startDiameter, final double endDiameter, final long n) {
        this.startDiameter = startDiameter;
        this.endDiameter = endDiameter;
        this.startTime = System.currentTimeMillis();
        this.endTime = this.startTime + n;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().onTransitionStarted(this, startDiameter, endDiameter, n);
        }
    }
    
    public double maxX() {
        double n = this.getCenterX() + this.getDiameter() / 2.0;
        if (n > this.worldSize) {
            n = this.worldSize;
        }
        return n;
    }
    
    public long getTimeUntilTarget() {
        return (this.getStatus() != EnumBorderStatus.STATIONARY) ? (this.endTime - System.currentTimeMillis()) : 0L;
    }
    
    public EnumBorderStatus getStatus() {
        return (this.endDiameter < this.startDiameter) ? EnumBorderStatus.SHRINKING : ((this.endDiameter > this.startDiameter) ? EnumBorderStatus.GROWING : EnumBorderStatus.STATIONARY);
    }
    
    public double getDamageBuffer() {
        return this.damageBuffer;
    }
    
    public void setTransition(final double n) {
        this.startDiameter = n;
        this.endDiameter = n;
        this.endTime = System.currentTimeMillis();
        this.startTime = this.endTime;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().onSizeChanged(this, n);
        }
    }
    
    public void setWarningDistance(final int warningDistance) {
        this.warningDistance = warningDistance;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().onWarningDistanceChanged(this, warningDistance);
        }
    }
    
    public WorldBorder() {
        this.listeners = Lists.newArrayList();
        this.centerX = 0.0;
        this.centerZ = 0.0;
        this.startDiameter = 6.0E7;
        this.endDiameter = this.startDiameter;
        this.worldSize = 29999984;
        this.damageAmount = 0.2;
        this.damageBuffer = 5.0;
        this.warningTime = 15;
        this.warningDistance = 5;
    }
    
    public void setSize(final int worldSize) {
        this.worldSize = worldSize;
    }
    
    protected List getListeners() {
        return Lists.newArrayList((Iterable)this.listeners);
    }
    
    public void addListener(final IBorderListener borderListener) {
        this.listeners.add(borderListener);
    }
    
    public double getClosestDistance(final Entity entity) {
        return this.getClosestDistance(entity.posX, entity.posZ);
    }
    
    public double getDiameter() {
        if (this.getStatus() != EnumBorderStatus.STATIONARY) {
            final double n = (System.currentTimeMillis() - this.startTime) / (float)(this.endTime - this.startTime);
            if (n < 1.0) {
                return this.startDiameter + (this.endDiameter - this.startDiameter) * n;
            }
            this.setTransition(this.endDiameter);
        }
        return this.startDiameter;
    }
    
    public boolean contains(final AxisAlignedBB axisAlignedBB) {
        return axisAlignedBB.maxX > this.minX() && axisAlignedBB.minX < this.maxX() && axisAlignedBB.maxZ > this.minZ() && axisAlignedBB.minZ < this.maxZ();
    }
    
    public int getWarningDistance() {
        return this.warningDistance;
    }
    
    public void setDamageBuffer(final double damageBuffer) {
        this.damageBuffer = damageBuffer;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().onDamageBufferChanged(this, damageBuffer);
        }
    }
    
    public double getClosestDistance(final double n, final double n2) {
        return Math.min(Math.min(Math.min(n - this.minX(), this.maxX() - n), n2 - this.minZ()), this.maxZ() - n2);
    }
    
    public double minZ() {
        double n = this.getCenterZ() - this.getDiameter() / 2.0;
        if (n < -this.worldSize) {
            n = -this.worldSize;
        }
        return n;
    }
    
    public double getTargetSize() {
        return this.endDiameter;
    }
    
    public double getCenterX() {
        return this.centerX;
    }
    
    public double getResizeSpeed() {
        return (this.endTime == this.startTime) ? 0.0 : (Math.abs(this.startDiameter - this.endDiameter) / (this.endTime - this.startTime));
    }
    
    public boolean contains(final ChunkCoordIntPair chunkCoordIntPair) {
        return chunkCoordIntPair.getXEnd() > this.minX() && chunkCoordIntPair.getXStart() < this.maxX() && chunkCoordIntPair.getZEnd() > this.minZ() && chunkCoordIntPair.getZStart() < this.maxZ();
    }
    
    public void setWarningTime(final int warningTime) {
        this.warningTime = warningTime;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().onWarningTimeChanged(this, warningTime);
        }
    }
    
    public int getSize() {
        return this.worldSize;
    }
    
    public int getWarningTime() {
        return this.warningTime;
    }
    
    public void setCenter(final double centerX, final double centerZ) {
        this.centerX = centerX;
        this.centerZ = centerZ;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().onCenterChanged(this, centerX, centerZ);
        }
    }
    
    public void setDamageAmount(final double damageAmount) {
        this.damageAmount = damageAmount;
        final Iterator<IBorderListener> iterator = this.getListeners().iterator();
        while (iterator.hasNext()) {
            iterator.next().onDamageAmountChanged(this, damageAmount);
        }
    }
    
    public double maxZ() {
        double n = this.getCenterZ() + this.getDiameter() / 2.0;
        if (n > this.worldSize) {
            n = this.worldSize;
        }
        return n;
    }
    
    public boolean contains(final BlockPos blockPos) {
        return blockPos.getX() + 1 > this.minX() && blockPos.getX() < this.maxX() && blockPos.getZ() + 1 > this.minZ() && blockPos.getZ() < this.maxZ();
    }
    
    public double getDamageAmount() {
        return this.damageAmount;
    }
    
    public double getCenterZ() {
        return this.centerZ;
    }
}
