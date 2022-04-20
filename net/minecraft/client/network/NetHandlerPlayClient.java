package net.minecraft.client.network;

import net.minecraft.network.play.*;
import com.mojang.authlib.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.*;
import net.minecraft.world.chunk.*;
import net.minecraft.potion.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.settings.*;
import net.minecraft.realms.*;
import com.nquantum.event.impl.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.item.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import net.minecraft.client.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.monster.*;
import net.minecraft.client.audio.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.stream.*;
import net.minecraft.stats.*;
import java.io.*;
import com.google.common.util.concurrent.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.inventory.*;
import net.minecraft.world.*;
import net.minecraft.client.player.inventory.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import net.minecraft.village.*;
import net.minecraft.init.*;
import net.minecraft.client.gui.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.effect.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;

public class NetHandlerPlayClient implements INetHandlerPlayClient
{
    private static final Logger logger;
    private WorldClient clientWorldController;
    public int currentServerMaxPlayers;
    private final Map playerInfoMap;
    private final Random avRandomizer;
    private final NetworkManager netManager;
    private boolean field_147308_k;
    private final GuiScreen guiScreenServer;
    private Minecraft gameController;
    private boolean doneLoadingTerrain;
    private final GameProfile profile;
    
    @Override
    public void handleSetSlot(final S2FPacketSetSlot s2FPacketSetSlot) {
        PacketThreadUtil.checkThreadAndEnqueue(s2FPacketSetSlot, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (s2FPacketSetSlot.func_149175_c() == -1) {
            thePlayer.inventory.setItemStack(s2FPacketSetSlot.func_149174_e());
        }
        else {
            if (this.gameController.currentScreen instanceof GuiContainerCreative) {
                final boolean b = ((GuiContainerCreative)this.gameController.currentScreen).getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex();
            }
            if (s2FPacketSetSlot.func_149175_c() == 0 && s2FPacketSetSlot.func_149173_d() >= 36 && s2FPacketSetSlot.func_149173_d() < 45) {
                final ItemStack stack = thePlayer.inventoryContainer.getSlot(s2FPacketSetSlot.func_149173_d()).getStack();
                if (s2FPacketSetSlot.func_149174_e() != null && (stack == null || stack.stackSize < s2FPacketSetSlot.func_149174_e().stackSize)) {
                    s2FPacketSetSlot.func_149174_e().animationsToGo = 5;
                }
                thePlayer.inventoryContainer.putStackInSlot(s2FPacketSetSlot.func_149173_d(), s2FPacketSetSlot.func_149174_e());
            }
            else if (s2FPacketSetSlot.func_149175_c() == thePlayer.openContainer.windowId) {
                if (s2FPacketSetSlot.func_149175_c() == 0) {}
                thePlayer.openContainer.putStackInSlot(s2FPacketSetSlot.func_149173_d(), s2FPacketSetSlot.func_149174_e());
            }
        }
    }
    
    @Override
    public void handleRemoveEntityEffect(final S1EPacketRemoveEntityEffect s1EPacketRemoveEntityEffect) {
        PacketThreadUtil.checkThreadAndEnqueue(s1EPacketRemoveEntityEffect, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1EPacketRemoveEntityEffect.getEntityId());
        if (entityByID instanceof EntityLivingBase) {
            ((EntityLivingBase)entityByID).removePotionEffectClient(s1EPacketRemoveEntityEffect.getEffectId());
        }
    }
    
    @Override
    public void handleSpawnMob(final S0FPacketSpawnMob s0FPacketSpawnMob) {
        PacketThreadUtil.checkThreadAndEnqueue(s0FPacketSpawnMob, this, this.gameController);
        final double n = s0FPacketSpawnMob.getX() / 32.0;
        final double n2 = s0FPacketSpawnMob.getY() / 32.0;
        final double n3 = s0FPacketSpawnMob.getZ() / 32.0;
        final float n4 = s0FPacketSpawnMob.getYaw() * 360 / 256.0f;
        final float n5 = s0FPacketSpawnMob.getPitch() * 360 / 256.0f;
        final EntityLivingBase entityLivingBase = (EntityLivingBase)EntityList.createEntityByID(s0FPacketSpawnMob.getEntityType(), this.gameController.theWorld);
        entityLivingBase.serverPosX = s0FPacketSpawnMob.getX();
        entityLivingBase.serverPosY = s0FPacketSpawnMob.getY();
        entityLivingBase.serverPosZ = s0FPacketSpawnMob.getZ();
        final EntityLivingBase entityLivingBase2 = entityLivingBase;
        final EntityLivingBase entityLivingBase3 = entityLivingBase;
        final float n6 = s0FPacketSpawnMob.getHeadPitch() * 360 / 256.0f;
        entityLivingBase3.rotationYawHead = n6;
        entityLivingBase2.renderYawOffset = n6;
        final Entity[] parts = entityLivingBase.getParts();
        if (parts != null) {
            final int n7 = s0FPacketSpawnMob.getEntityID() - entityLivingBase.getEntityId();
            while (0 < parts.length) {
                parts[0].setEntityId(parts[0].getEntityId() + n7);
                int n8 = 0;
                ++n8;
            }
        }
        entityLivingBase.setEntityId(s0FPacketSpawnMob.getEntityID());
        entityLivingBase.setPositionAndRotation(n, n2, n3, n4, n5);
        entityLivingBase.motionX = s0FPacketSpawnMob.getVelocityX() / 8000.0f;
        entityLivingBase.motionY = s0FPacketSpawnMob.getVelocityY() / 8000.0f;
        entityLivingBase.motionZ = s0FPacketSpawnMob.getVelocityZ() / 8000.0f;
        this.clientWorldController.addEntityToWorld(s0FPacketSpawnMob.getEntityID(), entityLivingBase);
        final List func_149027_c = s0FPacketSpawnMob.func_149027_c();
        if (func_149027_c != null) {
            entityLivingBase.getDataWatcher().updateWatchedObjectsFromList(func_149027_c);
        }
    }
    
    @Override
    public void handleEffect(final S28PacketEffect s28PacketEffect) {
        PacketThreadUtil.checkThreadAndEnqueue(s28PacketEffect, this, this.gameController);
        if (s28PacketEffect.isSoundServerwide()) {
            this.gameController.theWorld.playBroadcastSound(s28PacketEffect.getSoundType(), s28PacketEffect.getSoundPos(), s28PacketEffect.getSoundData());
        }
        else {
            this.gameController.theWorld.playAuxSFX(s28PacketEffect.getSoundType(), s28PacketEffect.getSoundPos(), s28PacketEffect.getSoundData());
        }
    }
    
    @Override
    public void handleExplosion(final S27PacketExplosion s27PacketExplosion) {
        PacketThreadUtil.checkThreadAndEnqueue(s27PacketExplosion, this, this.gameController);
        new Explosion(this.gameController.theWorld, null, s27PacketExplosion.getX(), s27PacketExplosion.getY(), s27PacketExplosion.getZ(), s27PacketExplosion.getStrength(), s27PacketExplosion.getAffectedBlockPositions()).doExplosionB(true);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        thePlayer.motionX += s27PacketExplosion.func_149149_c();
        final EntityPlayerSP thePlayer2 = this.gameController.thePlayer;
        thePlayer2.motionY += s27PacketExplosion.func_149144_d();
        final EntityPlayerSP thePlayer3 = this.gameController.thePlayer;
        thePlayer3.motionZ += s27PacketExplosion.func_149147_e();
    }
    
    public void cleanup() {
        this.clientWorldController = null;
    }
    
    @Override
    public void handleSpawnObject(final S0EPacketSpawnObject s0EPacketSpawnObject) {
        PacketThreadUtil.checkThreadAndEnqueue(s0EPacketSpawnObject, this, this.gameController);
        final double n = s0EPacketSpawnObject.getX() / 32.0;
        final double n2 = s0EPacketSpawnObject.getY() / 32.0;
        final double n3 = s0EPacketSpawnObject.getZ() / 32.0;
        Entity func_180458_a = null;
        if (s0EPacketSpawnObject.getType() == 10) {
            func_180458_a = EntityMinecart.func_180458_a(this.clientWorldController, n, n2, n3, EntityMinecart.EnumMinecartType.byNetworkID(s0EPacketSpawnObject.func_149009_m()));
        }
        else if (s0EPacketSpawnObject.getType() == 90) {
            final Entity entityByID = this.clientWorldController.getEntityByID(s0EPacketSpawnObject.func_149009_m());
            if (entityByID instanceof EntityPlayer) {
                func_180458_a = new EntityFishHook(this.clientWorldController, n, n2, n3, (EntityPlayer)entityByID);
            }
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.getType() == 60) {
            func_180458_a = new EntityArrow(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 61) {
            func_180458_a = new EntitySnowball(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 71) {
            func_180458_a = new EntityItemFrame(this.clientWorldController, new BlockPos(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3)), EnumFacing.getHorizontal(s0EPacketSpawnObject.func_149009_m()));
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.getType() == 77) {
            func_180458_a = new EntityLeashKnot(this.clientWorldController, new BlockPos(MathHelper.floor_double(n), MathHelper.floor_double(n2), MathHelper.floor_double(n3)));
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.getType() == 65) {
            func_180458_a = new EntityEnderPearl(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 72) {
            func_180458_a = new EntityEnderEye(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 76) {
            func_180458_a = new EntityFireworkRocket(this.clientWorldController, n, n2, n3, null);
        }
        else if (s0EPacketSpawnObject.getType() == 63) {
            func_180458_a = new EntityLargeFireball(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.getType() == 64) {
            func_180458_a = new EntitySmallFireball(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.getType() == 66) {
            func_180458_a = new EntityWitherSkull(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.getType() == 62) {
            func_180458_a = new EntityEgg(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 73) {
            func_180458_a = new EntityPotion(this.clientWorldController, n, n2, n3, s0EPacketSpawnObject.func_149009_m());
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.getType() == 75) {
            func_180458_a = new EntityExpBottle(this.clientWorldController, n, n2, n3);
            s0EPacketSpawnObject.func_149002_g(0);
        }
        else if (s0EPacketSpawnObject.getType() == 1) {
            func_180458_a = new EntityBoat(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 50) {
            func_180458_a = new EntityTNTPrimed(this.clientWorldController, n, n2, n3, null);
        }
        else if (s0EPacketSpawnObject.getType() == 78) {
            func_180458_a = new EntityArmorStand(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 51) {
            func_180458_a = new EntityEnderCrystal(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 2) {
            func_180458_a = new EntityItem(this.clientWorldController, n, n2, n3);
        }
        else if (s0EPacketSpawnObject.getType() == 70) {
            func_180458_a = new EntityFallingBlock(this.clientWorldController, n, n2, n3, Block.getStateById(s0EPacketSpawnObject.func_149009_m() & 0xFFFF));
            s0EPacketSpawnObject.func_149002_g(0);
        }
        if (func_180458_a != null) {
            func_180458_a.serverPosX = s0EPacketSpawnObject.getX();
            func_180458_a.serverPosY = s0EPacketSpawnObject.getY();
            func_180458_a.serverPosZ = s0EPacketSpawnObject.getZ();
            func_180458_a.rotationPitch = s0EPacketSpawnObject.getPitch() * 360 / 256.0f;
            func_180458_a.rotationYaw = s0EPacketSpawnObject.getYaw() * 360 / 256.0f;
            final Entity[] parts = func_180458_a.getParts();
            if (parts != null) {
                final int n4 = s0EPacketSpawnObject.getEntityID() - func_180458_a.getEntityId();
                while (0 < parts.length) {
                    parts[0].setEntityId(parts[0].getEntityId() + n4);
                    int n5 = 0;
                    ++n5;
                }
            }
            func_180458_a.setEntityId(s0EPacketSpawnObject.getEntityID());
            this.clientWorldController.addEntityToWorld(s0EPacketSpawnObject.getEntityID(), func_180458_a);
            if (s0EPacketSpawnObject.func_149009_m() > 0) {
                if (s0EPacketSpawnObject.getType() == 60) {
                    final Entity entityByID2 = this.clientWorldController.getEntityByID(s0EPacketSpawnObject.func_149009_m());
                    if (entityByID2 instanceof EntityLivingBase && func_180458_a instanceof EntityArrow) {
                        ((EntityArrow)func_180458_a).shootingEntity = entityByID2;
                    }
                }
                func_180458_a.setVelocity(s0EPacketSpawnObject.getSpeedX() / 8000.0, s0EPacketSpawnObject.getSpeedY() / 8000.0, s0EPacketSpawnObject.getSpeedZ() / 8000.0);
            }
        }
    }
    
    @Override
    public void handleCloseWindow(final S2EPacketCloseWindow s2EPacketCloseWindow) {
        PacketThreadUtil.checkThreadAndEnqueue(s2EPacketCloseWindow, this, this.gameController);
        this.gameController.thePlayer.closeScreenAndDropStack();
    }
    
    @Override
    public void handleAnimation(final S0BPacketAnimation s0BPacketAnimation) {
        PacketThreadUtil.checkThreadAndEnqueue(s0BPacketAnimation, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s0BPacketAnimation.getEntityID());
        if (entityByID != null) {
            if (s0BPacketAnimation.getAnimationType() == 0) {
                ((EntityPlayer)entityByID).swingItem();
            }
            else if (s0BPacketAnimation.getAnimationType() == 1) {
                entityByID.performHurtAnimation();
            }
            else if (s0BPacketAnimation.getAnimationType() == 2) {
                ((EntityPlayer)entityByID).wakeUpPlayer(false, false, false);
            }
            else if (s0BPacketAnimation.getAnimationType() == 4) {
                this.gameController.effectRenderer.emitParticleAtEntity(entityByID, EnumParticleTypes.CRIT);
            }
            else if (s0BPacketAnimation.getAnimationType() == 5) {
                this.gameController.effectRenderer.emitParticleAtEntity(entityByID, EnumParticleTypes.CRIT_MAGIC);
            }
        }
    }
    
    @Override
    public void handleMapChunkBulk(final S26PacketMapChunkBulk s26PacketMapChunkBulk) {
        PacketThreadUtil.checkThreadAndEnqueue(s26PacketMapChunkBulk, this, this.gameController);
        while (0 < s26PacketMapChunkBulk.getChunkCount()) {
            final int chunkX = s26PacketMapChunkBulk.getChunkX(0);
            final int chunkZ = s26PacketMapChunkBulk.getChunkZ(0);
            this.clientWorldController.doPreChunk(chunkX, chunkZ, true);
            this.clientWorldController.invalidateBlockReceiveRegion(chunkX << 4, 0, chunkZ << 4, (chunkX << 4) + 15, 256, (chunkZ << 4) + 15);
            final Chunk chunkFromChunkCoords = this.clientWorldController.getChunkFromChunkCoords(chunkX, chunkZ);
            chunkFromChunkCoords.fillChunk(s26PacketMapChunkBulk.getChunkBytes(0), s26PacketMapChunkBulk.getChunkSize(0), true);
            this.clientWorldController.markBlockRangeForRenderUpdate(chunkX << 4, 0, chunkZ << 4, (chunkX << 4) + 15, 256, (chunkZ << 4) + 15);
            if (!(this.clientWorldController.provider instanceof WorldProviderSurface)) {
                chunkFromChunkCoords.resetRelightChecks();
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void handleEntityEffect(final S1DPacketEntityEffect s1DPacketEntityEffect) {
        PacketThreadUtil.checkThreadAndEnqueue(s1DPacketEntityEffect, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1DPacketEntityEffect.getEntityId());
        if (entityByID instanceof EntityLivingBase) {
            final PotionEffect potionEffect = new PotionEffect(s1DPacketEntityEffect.getEffectId(), s1DPacketEntityEffect.getDuration(), s1DPacketEntityEffect.getAmplifier(), false, s1DPacketEntityEffect.func_179707_f());
            potionEffect.setPotionDurationMax(s1DPacketEntityEffect.func_149429_c());
            ((EntityLivingBase)entityByID).addPotionEffect(potionEffect);
        }
    }
    
    public NetworkPlayerInfo getPlayerInfo(final UUID uuid) {
        return this.playerInfoMap.get(uuid);
    }
    
    @Override
    public void handleCollectItem(final S0DPacketCollectItem s0DPacketCollectItem) {
        PacketThreadUtil.checkThreadAndEnqueue(s0DPacketCollectItem, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s0DPacketCollectItem.getCollectedItemEntityID());
        EntityLivingBase thePlayer = (EntityLivingBase)this.clientWorldController.getEntityByID(s0DPacketCollectItem.getEntityID());
        if (thePlayer == null) {
            thePlayer = this.gameController.thePlayer;
        }
        if (entityByID != null) {
            if (entityByID instanceof EntityXPOrb) {
                this.clientWorldController.playSoundAtEntity(entityByID, "random.orb", 0.2f, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            else {
                this.clientWorldController.playSoundAtEntity(entityByID, "random.pop", 0.2f, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7f + 1.0f) * 2.0f);
            }
            this.gameController.effectRenderer.addEffect(new EntityPickupFX(this.clientWorldController, entityByID, thePlayer, 0.5f));
            this.clientWorldController.removeEntityFromWorld(s0DPacketCollectItem.getCollectedItemEntityID());
        }
    }
    
    @Override
    public void handlePlayerListHeaderFooter(final S47PacketPlayerListHeaderFooter s47PacketPlayerListHeaderFooter) {
        this.gameController.ingameGUI.getTabList().setHeader((s47PacketPlayerListHeaderFooter.getHeader().getFormattedText().length() == 0) ? null : s47PacketPlayerListHeaderFooter.getHeader());
        this.gameController.ingameGUI.getTabList().setFooter((s47PacketPlayerListHeaderFooter.getFooter().getFormattedText().length() == 0) ? null : s47PacketPlayerListHeaderFooter.getFooter());
    }
    
    @Override
    public void handleDisplayScoreboard(final S3DPacketDisplayScoreboard s3DPacketDisplayScoreboard) {
        PacketThreadUtil.checkThreadAndEnqueue(s3DPacketDisplayScoreboard, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        if (s3DPacketDisplayScoreboard.func_149370_d().length() == 0) {
            scoreboard.setObjectiveInDisplaySlot(s3DPacketDisplayScoreboard.func_149371_c(), null);
        }
        else {
            scoreboard.setObjectiveInDisplaySlot(s3DPacketDisplayScoreboard.func_149371_c(), scoreboard.getObjective(s3DPacketDisplayScoreboard.func_149370_d()));
        }
    }
    
    @Override
    public void handleConfirmTransaction(final S32PacketConfirmTransaction s32PacketConfirmTransaction) {
        PacketThreadUtil.checkThreadAndEnqueue(s32PacketConfirmTransaction, this, this.gameController);
        Container container = null;
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (s32PacketConfirmTransaction.getWindowId() == 0) {
            container = thePlayer.inventoryContainer;
        }
        else if (s32PacketConfirmTransaction.getWindowId() == thePlayer.openContainer.windowId) {
            container = thePlayer.openContainer;
        }
        if (container != null && !s32PacketConfirmTransaction.func_148888_e()) {
            this.addToSendQueue(new C0FPacketConfirmTransaction(s32PacketConfirmTransaction.getWindowId(), s32PacketConfirmTransaction.getActionNumber(), true));
        }
    }
    
    @Override
    public void handleSpawnPlayer(final S0CPacketSpawnPlayer s0CPacketSpawnPlayer) {
        PacketThreadUtil.checkThreadAndEnqueue(s0CPacketSpawnPlayer, this, this.gameController);
        final double n = s0CPacketSpawnPlayer.getX() / 32.0;
        final double n2 = s0CPacketSpawnPlayer.getY() / 32.0;
        final double n3 = s0CPacketSpawnPlayer.getZ() / 32.0;
        final float n4 = s0CPacketSpawnPlayer.getYaw() * 360 / 256.0f;
        final float n5 = s0CPacketSpawnPlayer.getPitch() * 360 / 256.0f;
        final EntityOtherPlayerMP entityOtherPlayerMP4;
        final EntityOtherPlayerMP entityOtherPlayerMP3;
        final EntityOtherPlayerMP entityOtherPlayerMP2;
        final EntityOtherPlayerMP entityOtherPlayerMP = entityOtherPlayerMP2 = (entityOtherPlayerMP3 = (entityOtherPlayerMP4 = new EntityOtherPlayerMP(this.gameController.theWorld, this.getPlayerInfo(s0CPacketSpawnPlayer.getPlayer()).getGameProfile())));
        final int x = s0CPacketSpawnPlayer.getX();
        entityOtherPlayerMP2.serverPosX = x;
        final double n6 = x;
        entityOtherPlayerMP3.lastTickPosX = n6;
        entityOtherPlayerMP4.prevPosX = n6;
        final EntityOtherPlayerMP entityOtherPlayerMP5 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP6 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP7 = entityOtherPlayerMP;
        final int y = s0CPacketSpawnPlayer.getY();
        entityOtherPlayerMP7.serverPosY = y;
        final double n7 = y;
        entityOtherPlayerMP6.lastTickPosY = n7;
        entityOtherPlayerMP5.prevPosY = n7;
        final EntityOtherPlayerMP entityOtherPlayerMP8 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP9 = entityOtherPlayerMP;
        final EntityOtherPlayerMP entityOtherPlayerMP10 = entityOtherPlayerMP;
        final int z = s0CPacketSpawnPlayer.getZ();
        entityOtherPlayerMP10.serverPosZ = z;
        final double n8 = z;
        entityOtherPlayerMP9.lastTickPosZ = n8;
        entityOtherPlayerMP8.prevPosZ = n8;
        final int currentItemID = s0CPacketSpawnPlayer.getCurrentItemID();
        if (currentItemID == 0) {
            entityOtherPlayerMP.inventory.mainInventory[entityOtherPlayerMP.inventory.currentItem] = null;
        }
        else {
            entityOtherPlayerMP.inventory.mainInventory[entityOtherPlayerMP.inventory.currentItem] = new ItemStack(Item.getItemById(currentItemID), 1, 0);
        }
        entityOtherPlayerMP.setPositionAndRotation(n, n2, n3, n4, n5);
        this.clientWorldController.addEntityToWorld(s0CPacketSpawnPlayer.getEntityID(), entityOtherPlayerMP);
        final List func_148944_c = s0CPacketSpawnPlayer.func_148944_c();
        if (func_148944_c != null) {
            entityOtherPlayerMP.getDataWatcher().updateWatchedObjectsFromList(func_148944_c);
        }
    }
    
    static Minecraft access$100(final NetHandlerPlayClient netHandlerPlayClient) {
        return netHandlerPlayClient.gameController;
    }
    
    static NetworkManager access$000(final NetHandlerPlayClient netHandlerPlayClient) {
        return netHandlerPlayClient.netManager;
    }
    
    @Override
    public void handleEntityVelocity(final S12PacketEntityVelocity s12PacketEntityVelocity) {
        PacketThreadUtil.checkThreadAndEnqueue(s12PacketEntityVelocity, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s12PacketEntityVelocity.getEntityID());
        if (entityByID != null) {
            entityByID.setVelocity(s12PacketEntityVelocity.getMotionX() / 8000.0, s12PacketEntityVelocity.getMotionY() / 8000.0, s12PacketEntityVelocity.getMotionZ() / 8000.0);
        }
    }
    
    @Override
    public void handleChat(final S02PacketChat s02PacketChat) {
        PacketThreadUtil.checkThreadAndEnqueue(s02PacketChat, this, this.gameController);
        if (s02PacketChat.getType() == 2) {
            this.gameController.ingameGUI.setRecordPlaying(s02PacketChat.getChatComponent(), false);
        }
        else {
            this.gameController.ingameGUI.getChatGUI().printChatMessage(s02PacketChat.getChatComponent());
        }
    }
    
    @Override
    public void handleEntityEquipment(final S04PacketEntityEquipment s04PacketEntityEquipment) {
        PacketThreadUtil.checkThreadAndEnqueue(s04PacketEntityEquipment, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s04PacketEntityEquipment.getEntityID());
        if (entityByID != null) {
            entityByID.setCurrentItemOrArmor(s04PacketEntityEquipment.getEquipmentSlot(), s04PacketEntityEquipment.getItemStack());
        }
    }
    
    @Override
    public void handleBlockChange(final S23PacketBlockChange s23PacketBlockChange) {
        PacketThreadUtil.checkThreadAndEnqueue(s23PacketBlockChange, this, this.gameController);
        this.clientWorldController.invalidateRegionAndSetBlock(s23PacketBlockChange.getBlockPosition(), s23PacketBlockChange.getBlockState());
    }
    
    @Override
    public void handlePlayerAbilities(final S39PacketPlayerAbilities s39PacketPlayerAbilities) {
        PacketThreadUtil.checkThreadAndEnqueue(s39PacketPlayerAbilities, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        thePlayer.capabilities.isFlying = s39PacketPlayerAbilities.isFlying();
        thePlayer.capabilities.isCreativeMode = s39PacketPlayerAbilities.isCreativeMode();
        thePlayer.capabilities.disableDamage = s39PacketPlayerAbilities.isInvulnerable();
        thePlayer.capabilities.allowFlying = s39PacketPlayerAbilities.isAllowFlying();
        thePlayer.capabilities.setFlySpeed(s39PacketPlayerAbilities.getFlySpeed());
        thePlayer.capabilities.setPlayerWalkSpeed(s39PacketPlayerAbilities.getWalkSpeed());
    }
    
    public GameProfile getGameProfile() {
        return this.profile;
    }
    
    @Override
    public void handleEntityNBT(final S49PacketUpdateEntityNBT s49PacketUpdateEntityNBT) {
        PacketThreadUtil.checkThreadAndEnqueue(s49PacketUpdateEntityNBT, this, this.gameController);
        final Entity entity = s49PacketUpdateEntityNBT.getEntity(this.clientWorldController);
        if (entity != null) {
            entity.clientUpdateEntityNBT(s49PacketUpdateEntityNBT.getTagCompound());
        }
    }
    
    @Override
    public void handleUpdateScore(final S3CPacketUpdateScore s3CPacketUpdateScore) {
        PacketThreadUtil.checkThreadAndEnqueue(s3CPacketUpdateScore, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        final ScoreObjective objective = scoreboard.getObjective(s3CPacketUpdateScore.getObjectiveName());
        if (s3CPacketUpdateScore.getScoreAction() == S3CPacketUpdateScore.Action.CHANGE) {
            scoreboard.getValueFromObjective(s3CPacketUpdateScore.getPlayerName(), objective).setScorePoints(s3CPacketUpdateScore.getScoreValue());
        }
        else if (s3CPacketUpdateScore.getScoreAction() == S3CPacketUpdateScore.Action.REMOVE) {
            if (StringUtils.isNullOrEmpty(s3CPacketUpdateScore.getObjectiveName())) {
                scoreboard.removeObjectiveFromEntity(s3CPacketUpdateScore.getPlayerName(), null);
            }
            else if (objective != null) {
                scoreboard.removeObjectiveFromEntity(s3CPacketUpdateScore.getPlayerName(), objective);
            }
        }
    }
    
    @Override
    public void handleChangeGameState(final S2BPacketChangeGameState s2BPacketChangeGameState) {
        PacketThreadUtil.checkThreadAndEnqueue(s2BPacketChangeGameState, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        final int gameState = s2BPacketChangeGameState.getGameState();
        final float func_149137_d = s2BPacketChangeGameState.func_149137_d();
        final int floor_float = MathHelper.floor_float(func_149137_d + 0.5f);
        if (gameState >= 0 && gameState < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[gameState] != null) {
            thePlayer.addChatComponentMessage(new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[gameState], new Object[0]));
        }
        if (gameState == 1) {
            this.clientWorldController.getWorldInfo().setRaining(true);
            this.clientWorldController.setRainStrength(0.0f);
        }
        else if (gameState == 2) {
            this.clientWorldController.getWorldInfo().setRaining(false);
            this.clientWorldController.setRainStrength(1.0f);
        }
        else if (gameState == 3) {
            this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(floor_float));
        }
        else if (gameState == 4) {
            this.gameController.displayGuiScreen(new GuiWinGame());
        }
        else if (gameState == 5) {
            final GameSettings gameSettings = this.gameController.gameSettings;
            if (func_149137_d == 0.0f) {
                this.gameController.displayGuiScreen(new GuiScreenDemo());
            }
            else if (func_149137_d == 101.0f) {
                this.gameController.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.movement", new Object[] { GameSettings.getKeyDisplayString(gameSettings.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gameSettings.keyBindRight.getKeyCode()) }));
            }
            else if (func_149137_d == 102.0f) {
                this.gameController.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(gameSettings.keyBindJump.getKeyCode()) }));
            }
            else if (func_149137_d == 103.0f) {
                this.gameController.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(gameSettings.keyBindInventory.getKeyCode()) }));
            }
        }
        else if (gameState == 6) {
            this.clientWorldController.playSound(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ, "random.successful_hit", 0.18f, 0.45f, false);
        }
        else if (gameState == 7) {
            this.clientWorldController.setRainStrength(func_149137_d);
        }
        else if (gameState == 8) {
            this.clientWorldController.setThunderStrength(func_149137_d);
        }
        else if (gameState == 10) {
            this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, thePlayer.posX, thePlayer.posY, thePlayer.posZ, 0.0, 0.0, 0.0, new int[0]);
            this.clientWorldController.playSound(thePlayer.posX, thePlayer.posY, thePlayer.posZ, "mob.guardian.curse", 1.0f, 1.0f, false);
        }
    }
    
    @Override
    public void handleTitle(final S45PacketTitle s45PacketTitle) {
        PacketThreadUtil.checkThreadAndEnqueue(s45PacketTitle, this, this.gameController);
        final S45PacketTitle.Type type = s45PacketTitle.getType();
        String s = null;
        String s2 = null;
        final String s3 = (s45PacketTitle.getMessage() != null) ? s45PacketTitle.getMessage().getFormattedText() : "";
        switch (NetHandlerPlayClient$4.$SwitchMap$net$minecraft$network$play$server$S45PacketTitle$Type[type.ordinal()]) {
            case 1: {
                s = s3;
                break;
            }
            case 2: {
                s2 = s3;
                break;
            }
            case 3: {
                this.gameController.ingameGUI.displayTitle("", "", -1, -1, -1);
                this.gameController.ingameGUI.func_175177_a();
                return;
            }
        }
        this.gameController.ingameGUI.displayTitle(s, s2, s45PacketTitle.getFadeInTime(), s45PacketTitle.getDisplayTime(), s45PacketTitle.getFadeOutTime());
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        this.gameController.loadWorld(null);
        if (this.guiScreenServer != null) {
            if (this.guiScreenServer instanceof GuiScreenRealmsProxy) {
                this.gameController.displayGuiScreen(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.guiScreenServer).func_154321_a(), "disconnect.lost", chatComponent).getProxy());
            }
            else {
                this.gameController.displayGuiScreen(new GuiDisconnected(this.guiScreenServer, "disconnect.lost", chatComponent));
            }
        }
        else {
            this.gameController.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", chatComponent));
        }
    }
    
    static Minecraft access$102(final NetHandlerPlayClient netHandlerPlayClient, final Minecraft gameController) {
        return netHandlerPlayClient.gameController = gameController;
    }
    
    @Override
    public void handleRespawn(final S07PacketRespawn s07PacketRespawn) {
        PacketThreadUtil.checkThreadAndEnqueue(s07PacketRespawn, this, this.gameController);
        if (s07PacketRespawn.getDimensionID() != this.gameController.thePlayer.dimension) {
            this.doneLoadingTerrain = false;
            (this.clientWorldController = new WorldClient(this, new WorldSettings(0L, s07PacketRespawn.getGameType(), false, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), s07PacketRespawn.getWorldType()), s07PacketRespawn.getDimensionID(), s07PacketRespawn.getDifficulty(), this.gameController.mcProfiler)).setWorldScoreboard(this.clientWorldController.getScoreboard());
            this.gameController.loadWorld(this.clientWorldController);
            this.gameController.thePlayer.dimension = s07PacketRespawn.getDimensionID();
            this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        }
        this.gameController.setDimensionAndSpawnPlayer(s07PacketRespawn.getDimensionID());
        this.gameController.playerController.setGameType(s07PacketRespawn.getGameType());
    }
    
    public void addToSendQueue(final Packet packet) {
        new EventSendPacket(packet).call();
        this.netManager.sendPacket(packet);
    }
    
    @Override
    public void handleSignEditorOpen(final S36PacketSignEditorOpen s36PacketSignEditorOpen) {
        PacketThreadUtil.checkThreadAndEnqueue(s36PacketSignEditorOpen, this, this.gameController);
        TileEntity tileEntity = this.clientWorldController.getTileEntity(s36PacketSignEditorOpen.getSignPosition());
        if (!(tileEntity instanceof TileEntitySign)) {
            tileEntity = new TileEntitySign();
            tileEntity.setWorldObj(this.clientWorldController);
            tileEntity.setPos(s36PacketSignEditorOpen.getSignPosition());
        }
        this.gameController.thePlayer.openEditSign((TileEntitySign)tileEntity);
    }
    
    @Override
    public void handleMultiBlockChange(final S22PacketMultiBlockChange s22PacketMultiBlockChange) {
        PacketThreadUtil.checkThreadAndEnqueue(s22PacketMultiBlockChange, this, this.gameController);
        final S22PacketMultiBlockChange.BlockUpdateData[] changedBlocks = s22PacketMultiBlockChange.getChangedBlocks();
        while (0 < changedBlocks.length) {
            final S22PacketMultiBlockChange.BlockUpdateData blockUpdateData = changedBlocks[0];
            this.clientWorldController.invalidateRegionAndSetBlock(blockUpdateData.getPos(), blockUpdateData.getBlockState());
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void handleWindowProperty(final S31PacketWindowProperty s31PacketWindowProperty) {
        PacketThreadUtil.checkThreadAndEnqueue(s31PacketWindowProperty, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (thePlayer.openContainer != null && thePlayer.openContainer.windowId == s31PacketWindowProperty.getWindowId()) {
            thePlayer.openContainer.updateProgressBar(s31PacketWindowProperty.getVarIndex(), s31PacketWindowProperty.getVarValue());
        }
    }
    
    @Override
    public void handleUseBed(final S0APacketUseBed s0APacketUseBed) {
        PacketThreadUtil.checkThreadAndEnqueue(s0APacketUseBed, this, this.gameController);
        s0APacketUseBed.getPlayer(this.clientWorldController).trySleep(s0APacketUseBed.getBedPosition());
    }
    
    @Override
    public void handleSetCompressionLevel(final S46PacketSetCompressionLevel s46PacketSetCompressionLevel) {
        if (!this.netManager.isLocalChannel()) {
            this.netManager.setCompressionTreshold(s46PacketSetCompressionLevel.func_179760_a());
        }
    }
    
    public Collection getPlayerInfoMap() {
        return this.playerInfoMap.values();
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    @Override
    public void handleSpawnPainting(final S10PacketSpawnPainting s10PacketSpawnPainting) {
        PacketThreadUtil.checkThreadAndEnqueue(s10PacketSpawnPainting, this, this.gameController);
        this.clientWorldController.addEntityToWorld(s10PacketSpawnPainting.getEntityID(), new EntityPainting(this.clientWorldController, s10PacketSpawnPainting.getPosition(), s10PacketSpawnPainting.getFacing(), s10PacketSpawnPainting.getTitle()));
    }
    
    @Override
    public void handleEntityTeleport(final S18PacketEntityTeleport s18PacketEntityTeleport) {
        PacketThreadUtil.checkThreadAndEnqueue(s18PacketEntityTeleport, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s18PacketEntityTeleport.getEntityId());
        if (entityByID != null) {
            entityByID.serverPosX = s18PacketEntityTeleport.getX();
            entityByID.serverPosY = s18PacketEntityTeleport.getY();
            entityByID.serverPosZ = s18PacketEntityTeleport.getZ();
            final double n = entityByID.serverPosX / 32.0;
            final double n2 = entityByID.serverPosY / 32.0;
            final double n3 = entityByID.serverPosZ / 32.0;
            final float n4 = s18PacketEntityTeleport.getYaw() * 360 / 256.0f;
            final float n5 = s18PacketEntityTeleport.getPitch() * 360 / 256.0f;
            if (Math.abs(entityByID.posX - n) < 0.03125 && Math.abs(entityByID.posY - n2) < 0.015625 && Math.abs(entityByID.posZ - n3) < 0.03125) {
                entityByID.setPositionAndRotation2(entityByID.posX, entityByID.posY, entityByID.posZ, n4, n5, 3, true);
            }
            else {
                entityByID.setPositionAndRotation2(n, n2, n3, n4, n5, 3, true);
            }
            entityByID.onGround = s18PacketEntityTeleport.getOnGround();
        }
    }
    
    @Override
    public void handleEntityHeadLook(final S19PacketEntityHeadLook s19PacketEntityHeadLook) {
        PacketThreadUtil.checkThreadAndEnqueue(s19PacketEntityHeadLook, this, this.gameController);
        final Entity entity = s19PacketEntityHeadLook.getEntity(this.clientWorldController);
        if (entity != null) {
            entity.setRotationYawHead(s19PacketEntityHeadLook.getYaw() * 360 / 256.0f);
        }
    }
    
    @Override
    public void handleTimeUpdate(final S03PacketTimeUpdate s03PacketTimeUpdate) {
        PacketThreadUtil.checkThreadAndEnqueue(s03PacketTimeUpdate, this, this.gameController);
        this.gameController.theWorld.setTotalWorldTime(s03PacketTimeUpdate.getTotalWorldTime());
        this.gameController.theWorld.setWorldTime(s03PacketTimeUpdate.getWorldTime());
    }
    
    @Override
    public void handleJoinGame(final S01PacketJoinGame s01PacketJoinGame) {
        PacketThreadUtil.checkThreadAndEnqueue(s01PacketJoinGame, this, this.gameController);
        this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
        this.clientWorldController = new WorldClient(this, new WorldSettings(0L, s01PacketJoinGame.getGameType(), false, s01PacketJoinGame.isHardcoreMode(), s01PacketJoinGame.getWorldType()), s01PacketJoinGame.getDimension(), s01PacketJoinGame.getDifficulty(), this.gameController.mcProfiler);
        this.gameController.gameSettings.difficulty = s01PacketJoinGame.getDifficulty();
        this.gameController.loadWorld(this.clientWorldController);
        this.gameController.thePlayer.dimension = s01PacketJoinGame.getDimension();
        this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
        this.gameController.thePlayer.setEntityId(s01PacketJoinGame.getEntityId());
        this.currentServerMaxPlayers = s01PacketJoinGame.getMaxPlayers();
        this.gameController.thePlayer.setReducedDebug(s01PacketJoinGame.isReducedDebugInfo());
        this.gameController.playerController.setGameType(s01PacketJoinGame.getGameType());
        this.gameController.gameSettings.sendSettingsToServer();
        this.netManager.sendPacket(new C17PacketCustomPayload("MC|Brand", new PacketBuffer(Unpooled.buffer()).writeString(ClientBrandRetriever.getClientModName())));
    }
    
    @Override
    public void handlePlayerListItem(final S38PacketPlayerListItem s38PacketPlayerListItem) {
        PacketThreadUtil.checkThreadAndEnqueue(s38PacketPlayerListItem, this, this.gameController);
        for (final S38PacketPlayerListItem.AddPlayerData addPlayerData : s38PacketPlayerListItem.func_179767_a()) {
            if (s38PacketPlayerListItem.func_179768_b() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
                this.playerInfoMap.remove(addPlayerData.getProfile().getId());
            }
            else {
                NetworkPlayerInfo networkPlayerInfo = this.playerInfoMap.get(addPlayerData.getProfile().getId());
                if (s38PacketPlayerListItem.func_179768_b() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
                    networkPlayerInfo = new NetworkPlayerInfo(addPlayerData);
                    this.playerInfoMap.put(networkPlayerInfo.getGameProfile().getId(), networkPlayerInfo);
                }
                if (networkPlayerInfo == null) {
                    continue;
                }
                switch (NetHandlerPlayClient$4.$SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[s38PacketPlayerListItem.func_179768_b().ordinal()]) {
                    case 1: {
                        networkPlayerInfo.setGameType(addPlayerData.getGameMode());
                        networkPlayerInfo.setResponseTime(addPlayerData.getPing());
                        continue;
                    }
                    case 2: {
                        networkPlayerInfo.setGameType(addPlayerData.getGameMode());
                        continue;
                    }
                    case 3: {
                        networkPlayerInfo.setResponseTime(addPlayerData.getPing());
                        continue;
                    }
                    case 4: {
                        networkPlayerInfo.setDisplayName(addPlayerData.getDisplayName());
                        continue;
                    }
                }
            }
        }
    }
    
    @Override
    public void handlePlayerPosLook(final S08PacketPlayerPosLook s08PacketPlayerPosLook) {
        PacketThreadUtil.checkThreadAndEnqueue(s08PacketPlayerPosLook, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        double x = s08PacketPlayerPosLook.getX();
        double y = s08PacketPlayerPosLook.getY();
        double z = s08PacketPlayerPosLook.getZ();
        float yaw = s08PacketPlayerPosLook.getYaw();
        float pitch = s08PacketPlayerPosLook.getPitch();
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
            x += thePlayer.posX;
        }
        else {
            thePlayer.motionX = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
            y += thePlayer.posY;
        }
        else {
            thePlayer.motionY = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
            z += thePlayer.posZ;
        }
        else {
            thePlayer.motionZ = 0.0;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
            pitch += thePlayer.rotationPitch;
        }
        if (s08PacketPlayerPosLook.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
            yaw += thePlayer.rotationYaw;
        }
        thePlayer.setPositionAndRotation(x, y, z, yaw, pitch);
        this.netManager.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(thePlayer.posX, thePlayer.getEntityBoundingBox().minY, thePlayer.posZ, thePlayer.rotationYaw, thePlayer.rotationPitch, false));
        if (!this.doneLoadingTerrain) {
            this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
            this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
            this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
            this.doneLoadingTerrain = true;
            this.gameController.displayGuiScreen(null);
        }
    }
    
    @Override
    public void handleEntityProperties(final S20PacketEntityProperties s20PacketEntityProperties) {
        PacketThreadUtil.checkThreadAndEnqueue(s20PacketEntityProperties, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s20PacketEntityProperties.getEntityId());
        if (entityByID != null) {
            if (!(entityByID instanceof EntityLivingBase)) {
                throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entityByID + ")");
            }
            final BaseAttributeMap attributeMap = ((EntityLivingBase)entityByID).getAttributeMap();
            for (final S20PacketEntityProperties.Snapshot snapshot : s20PacketEntityProperties.func_149441_d()) {
                IAttributeInstance attributeInstance = attributeMap.getAttributeInstanceByName(snapshot.func_151409_a());
                if (attributeInstance == null) {
                    attributeInstance = attributeMap.registerAttribute(new RangedAttribute(null, snapshot.func_151409_a(), 0.0, Double.MIN_NORMAL, Double.MAX_VALUE));
                }
                attributeInstance.setBaseValue(snapshot.func_151410_b());
                attributeInstance.removeAllModifiers();
                final Iterator iterator2 = snapshot.func_151408_c().iterator();
                while (iterator2.hasNext()) {
                    attributeInstance.applyModifier(iterator2.next());
                }
            }
        }
    }
    
    @Override
    public void handleBlockBreakAnim(final S25PacketBlockBreakAnim s25PacketBlockBreakAnim) {
        PacketThreadUtil.checkThreadAndEnqueue(s25PacketBlockBreakAnim, this, this.gameController);
        this.gameController.theWorld.sendBlockBreakProgress(s25PacketBlockBreakAnim.getBreakerId(), s25PacketBlockBreakAnim.getPosition(), s25PacketBlockBreakAnim.getProgress());
    }
    
    @Override
    public void handleEntityStatus(final S19PacketEntityStatus s19PacketEntityStatus) {
        PacketThreadUtil.checkThreadAndEnqueue(s19PacketEntityStatus, this, this.gameController);
        final Entity entity = s19PacketEntityStatus.getEntity(this.clientWorldController);
        if (entity != null) {
            if (s19PacketEntityStatus.getOpCode() == 21) {
                this.gameController.getSoundHandler().playSound(new GuardianSound((EntityGuardian)entity));
            }
            else {
                entity.handleStatusUpdate(s19PacketEntityStatus.getOpCode());
            }
        }
    }
    
    @Override
    public void handleSpawnExperienceOrb(final S11PacketSpawnExperienceOrb s11PacketSpawnExperienceOrb) {
        PacketThreadUtil.checkThreadAndEnqueue(s11PacketSpawnExperienceOrb, this, this.gameController);
        final EntityXPOrb entityXPOrb = new EntityXPOrb(this.clientWorldController, s11PacketSpawnExperienceOrb.getX() / 32.0, s11PacketSpawnExperienceOrb.getY() / 32.0, s11PacketSpawnExperienceOrb.getZ() / 32.0, s11PacketSpawnExperienceOrb.getXPValue());
        entityXPOrb.serverPosX = s11PacketSpawnExperienceOrb.getX();
        entityXPOrb.serverPosY = s11PacketSpawnExperienceOrb.getY();
        entityXPOrb.serverPosZ = s11PacketSpawnExperienceOrb.getZ();
        entityXPOrb.rotationYaw = 0.0f;
        entityXPOrb.rotationPitch = 0.0f;
        entityXPOrb.setEntityId(s11PacketSpawnExperienceOrb.getEntityID());
        this.clientWorldController.addEntityToWorld(s11PacketSpawnExperienceOrb.getEntityID(), entityXPOrb);
    }
    
    @Override
    public void handleEntityAttach(final S1BPacketEntityAttach s1BPacketEntityAttach) {
        PacketThreadUtil.checkThreadAndEnqueue(s1BPacketEntityAttach, this, this.gameController);
        Entity entity = this.clientWorldController.getEntityByID(s1BPacketEntityAttach.getEntityId());
        final Entity entityByID = this.clientWorldController.getEntityByID(s1BPacketEntityAttach.getVehicleEntityId());
        if (s1BPacketEntityAttach.getLeash() == 0) {
            if (s1BPacketEntityAttach.getEntityId() == this.gameController.thePlayer.getEntityId()) {
                entity = this.gameController.thePlayer;
                if (entityByID instanceof EntityBoat) {
                    ((EntityBoat)entityByID).setIsBoatEmpty(false);
                }
                final boolean b = entity.ridingEntity == null && entityByID != null;
            }
            else if (entityByID instanceof EntityBoat) {
                ((EntityBoat)entityByID).setIsBoatEmpty(true);
            }
            if (entity == null) {
                return;
            }
            entity.mountEntity(entityByID);
        }
        else if (s1BPacketEntityAttach.getLeash() == 1 && entity instanceof EntityLiving) {
            if (entityByID != null) {
                ((EntityLiving)entity).setLeashedToEntity(entityByID, false);
            }
            else {
                ((EntityLiving)entity).clearLeashed(false, false);
            }
        }
    }
    
    @Override
    public void handleEntityMetadata(final S1CPacketEntityMetadata s1CPacketEntityMetadata) {
        PacketThreadUtil.checkThreadAndEnqueue(s1CPacketEntityMetadata, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s1CPacketEntityMetadata.getEntityId());
        if (entityByID != null && s1CPacketEntityMetadata.func_149376_c() != null) {
            entityByID.getDataWatcher().updateWatchedObjectsFromList(s1CPacketEntityMetadata.func_149376_c());
        }
    }
    
    @Override
    public void handleDestroyEntities(final S13PacketDestroyEntities s13PacketDestroyEntities) {
        PacketThreadUtil.checkThreadAndEnqueue(s13PacketDestroyEntities, this, this.gameController);
        while (0 < s13PacketDestroyEntities.getEntityIDs().length) {
            this.clientWorldController.removeEntityFromWorld(s13PacketDestroyEntities.getEntityIDs()[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public void handleDisconnect(final S40PacketDisconnect s40PacketDisconnect) {
        this.netManager.closeChannel(s40PacketDisconnect.getReason());
    }
    
    @Override
    public void handleServerDifficulty(final S41PacketServerDifficulty s41PacketServerDifficulty) {
        PacketThreadUtil.checkThreadAndEnqueue(s41PacketServerDifficulty, this, this.gameController);
        this.gameController.theWorld.getWorldInfo().setDifficulty(s41PacketServerDifficulty.getDifficulty());
        this.gameController.theWorld.getWorldInfo().setDifficultyLocked(s41PacketServerDifficulty.isDifficultyLocked());
    }
    
    @Override
    public void handleUpdateHealth(final S06PacketUpdateHealth s06PacketUpdateHealth) {
        PacketThreadUtil.checkThreadAndEnqueue(s06PacketUpdateHealth, this, this.gameController);
        this.gameController.thePlayer.setPlayerSPHealth(s06PacketUpdateHealth.getHealth());
        this.gameController.thePlayer.getFoodStats().setFoodLevel(s06PacketUpdateHealth.getFoodLevel());
        this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(s06PacketUpdateHealth.getSaturationLevel());
    }
    
    @Override
    public void handleSoundEffect(final S29PacketSoundEffect s29PacketSoundEffect) {
        PacketThreadUtil.checkThreadAndEnqueue(s29PacketSoundEffect, this, this.gameController);
        this.gameController.theWorld.playSound(s29PacketSoundEffect.getX(), s29PacketSoundEffect.getY(), s29PacketSoundEffect.getZ(), s29PacketSoundEffect.getSoundName(), s29PacketSoundEffect.getVolume(), s29PacketSoundEffect.getPitch(), false);
    }
    
    public NetworkPlayerInfo getPlayerInfo(final String s) {
        for (final NetworkPlayerInfo networkPlayerInfo : this.playerInfoMap.values()) {
            if (networkPlayerInfo.getGameProfile().getName().equals(s)) {
                return networkPlayerInfo;
            }
        }
        return null;
    }
    
    @Override
    public void handleUpdateTileEntity(final S35PacketUpdateTileEntity s35PacketUpdateTileEntity) {
        PacketThreadUtil.checkThreadAndEnqueue(s35PacketUpdateTileEntity, this, this.gameController);
        if (this.gameController.theWorld.isBlockLoaded(s35PacketUpdateTileEntity.getPos())) {
            final TileEntity tileEntity = this.gameController.theWorld.getTileEntity(s35PacketUpdateTileEntity.getPos());
            final int tileEntityType = s35PacketUpdateTileEntity.getTileEntityType();
            if ((tileEntityType == 1 && tileEntity instanceof TileEntityMobSpawner) || (tileEntityType == 2 && tileEntity instanceof TileEntityCommandBlock) || (tileEntityType == 3 && tileEntity instanceof TileEntityBeacon) || (tileEntityType == 4 && tileEntity instanceof TileEntitySkull) || (tileEntityType == 5 && tileEntity instanceof TileEntityFlowerPot) || (tileEntityType == 6 && tileEntity instanceof TileEntityBanner)) {
                tileEntity.readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
            }
        }
    }
    
    @Override
    public void handleBlockAction(final S24PacketBlockAction s24PacketBlockAction) {
        PacketThreadUtil.checkThreadAndEnqueue(s24PacketBlockAction, this, this.gameController);
        this.gameController.theWorld.addBlockEvent(s24PacketBlockAction.getBlockPosition(), s24PacketBlockAction.getBlockType(), s24PacketBlockAction.getData1(), s24PacketBlockAction.getData2());
    }
    
    @Override
    public void handleChunkData(final S21PacketChunkData s21PacketChunkData) {
        PacketThreadUtil.checkThreadAndEnqueue(s21PacketChunkData, this, this.gameController);
        if (s21PacketChunkData.func_149274_i()) {
            if (s21PacketChunkData.getExtractedSize() == 0) {
                this.clientWorldController.doPreChunk(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ(), false);
                return;
            }
            this.clientWorldController.doPreChunk(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ(), true);
        }
        this.clientWorldController.invalidateBlockReceiveRegion(s21PacketChunkData.getChunkX() << 4, 0, s21PacketChunkData.getChunkZ() << 4, (s21PacketChunkData.getChunkX() << 4) + 15, 256, (s21PacketChunkData.getChunkZ() << 4) + 15);
        final Chunk chunkFromChunkCoords = this.clientWorldController.getChunkFromChunkCoords(s21PacketChunkData.getChunkX(), s21PacketChunkData.getChunkZ());
        chunkFromChunkCoords.fillChunk(s21PacketChunkData.func_149272_d(), s21PacketChunkData.getExtractedSize(), s21PacketChunkData.func_149274_i());
        this.clientWorldController.markBlockRangeForRenderUpdate(s21PacketChunkData.getChunkX() << 4, 0, s21PacketChunkData.getChunkZ() << 4, (s21PacketChunkData.getChunkX() << 4) + 15, 256, (s21PacketChunkData.getChunkZ() << 4) + 15);
        if (!s21PacketChunkData.func_149274_i() || !(this.clientWorldController.provider instanceof WorldProviderSurface)) {
            chunkFromChunkCoords.resetRelightChecks();
        }
    }
    
    @Override
    public void handleCombatEvent(final S42PacketCombatEvent s42PacketCombatEvent) {
        PacketThreadUtil.checkThreadAndEnqueue(s42PacketCombatEvent, this, this.gameController);
        final Entity entityByID = this.clientWorldController.getEntityByID(s42PacketCombatEvent.field_179775_c);
        final EntityLivingBase entityLivingBase = (entityByID instanceof EntityLivingBase) ? ((EntityLivingBase)entityByID) : null;
        if (s42PacketCombatEvent.eventType == S42PacketCombatEvent.Event.END_COMBAT) {
            this.gameController.getTwitchStream().func_176026_a(new MetadataCombat(this.gameController.thePlayer, entityLivingBase), 0L - 1000 * s42PacketCombatEvent.field_179772_d / 20, 0L);
        }
        else if (s42PacketCombatEvent.eventType == S42PacketCombatEvent.Event.ENTITY_DIED) {
            final Entity entityByID2 = this.clientWorldController.getEntityByID(s42PacketCombatEvent.field_179774_b);
            if (entityByID2 instanceof EntityPlayer) {
                final MetadataPlayerDeath metadataPlayerDeath = new MetadataPlayerDeath((EntityLivingBase)entityByID2, entityLivingBase);
                metadataPlayerDeath.func_152807_a(s42PacketCombatEvent.deathMessage);
                this.gameController.getTwitchStream().func_152911_a(metadataPlayerDeath, 0L);
            }
        }
    }
    
    @Override
    public void handleScoreboardObjective(final S3BPacketScoreboardObjective s3BPacketScoreboardObjective) {
        PacketThreadUtil.checkThreadAndEnqueue(s3BPacketScoreboardObjective, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        if (s3BPacketScoreboardObjective.func_149338_e() == 0) {
            final ScoreObjective addScoreObjective = scoreboard.addScoreObjective(s3BPacketScoreboardObjective.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
            addScoreObjective.setDisplayName(s3BPacketScoreboardObjective.func_149337_d());
            addScoreObjective.setRenderType(s3BPacketScoreboardObjective.func_179817_d());
        }
        else {
            final ScoreObjective objective = scoreboard.getObjective(s3BPacketScoreboardObjective.func_149339_c());
            if (s3BPacketScoreboardObjective.func_149338_e() == 1) {
                scoreboard.removeObjective(objective);
            }
            else if (s3BPacketScoreboardObjective.func_149338_e() == 2) {
                objective.setDisplayName(s3BPacketScoreboardObjective.func_149337_d());
                objective.setRenderType(s3BPacketScoreboardObjective.func_179817_d());
            }
        }
    }
    
    @Override
    public void handleHeldItemChange(final S09PacketHeldItemChange s09PacketHeldItemChange) {
        PacketThreadUtil.checkThreadAndEnqueue(s09PacketHeldItemChange, this, this.gameController);
        if (s09PacketHeldItemChange.getHeldItemHotbarIndex() >= 0 && s09PacketHeldItemChange.getHeldItemHotbarIndex() < InventoryPlayer.getHotbarSize()) {
            this.gameController.thePlayer.inventory.currentItem = s09PacketHeldItemChange.getHeldItemHotbarIndex();
        }
    }
    
    @Override
    public void handleStatistics(final S37PacketStatistics s37PacketStatistics) {
        PacketThreadUtil.checkThreadAndEnqueue(s37PacketStatistics, this, this.gameController);
        for (final Map.Entry<StatBase, V> entry : s37PacketStatistics.func_148974_c().entrySet()) {
            final StatBase statBase = entry.getKey();
            final int intValue = (int)entry.getValue();
            if (statBase.isAchievement() && intValue > 0 && this.field_147308_k && this.gameController.thePlayer.getStatFileWriter().readStat(statBase) == 0) {
                final Achievement achievement = (Achievement)statBase;
                this.gameController.guiAchievement.displayAchievement(achievement);
                this.gameController.getTwitchStream().func_152911_a(new MetadataAchievement(achievement), 0L);
                if (statBase == AchievementList.openInventory) {
                    this.gameController.gameSettings.showInventoryAchievementHint = false;
                    this.gameController.gameSettings.saveOptions();
                }
            }
            this.gameController.thePlayer.getStatFileWriter().unlockAchievement(this.gameController.thePlayer, statBase, intValue);
        }
        if (!this.field_147308_k) {}
        this.field_147308_k = true;
        if (this.gameController.currentScreen instanceof IProgressMeter) {
            ((IProgressMeter)this.gameController.currentScreen).doneLoading();
        }
    }
    
    @Override
    public void handleSetExperience(final S1FPacketSetExperience s1FPacketSetExperience) {
        PacketThreadUtil.checkThreadAndEnqueue(s1FPacketSetExperience, this, this.gameController);
        this.gameController.thePlayer.setXPStats(s1FPacketSetExperience.func_149397_c(), s1FPacketSetExperience.getTotalExperience(), s1FPacketSetExperience.getLevel());
    }
    
    @Override
    public void handleSpawnPosition(final S05PacketSpawnPosition s05PacketSpawnPosition) {
        PacketThreadUtil.checkThreadAndEnqueue(s05PacketSpawnPosition, this, this.gameController);
        this.gameController.thePlayer.setSpawnPoint(s05PacketSpawnPosition.getSpawnPos(), true);
        this.gameController.theWorld.getWorldInfo().setSpawn(s05PacketSpawnPosition.getSpawnPos());
    }
    
    @Override
    public void handleResourcePack(final S48PacketResourcePackSend s48PacketResourcePackSend) {
        final String url = s48PacketResourcePackSend.getURL();
        final String hash = s48PacketResourcePackSend.getHash();
        if (url.startsWith("level://")) {
            final File resourcePackInstance = new File(new File(this.gameController.mcDataDir, "saves"), url.substring(8));
            if (resourcePackInstance.isFile()) {
                this.netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.ACCEPTED));
                Futures.addCallback(this.gameController.getResourcePackRepository().setResourcePackInstance(resourcePackInstance), (FutureCallback)new FutureCallback(this, hash) {
                    final String val$s1;
                    final NetHandlerPlayClient this$0;
                    
                    public void onFailure(final Throwable t) {
                        NetHandlerPlayClient.access$000(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                    }
                    
                    public void onSuccess(final Object o) {
                        NetHandlerPlayClient.access$000(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                    }
                });
            }
            else {
                this.netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            }
        }
        else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.ACCEPTED));
            Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(url, hash), (FutureCallback)new FutureCallback(this, hash) {
                final NetHandlerPlayClient this$0;
                final String val$s1;
                
                public void onFailure(final Throwable t) {
                    NetHandlerPlayClient.access$000(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                }
                
                public void onSuccess(final Object o) {
                    NetHandlerPlayClient.access$000(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                }
            });
        }
        else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(hash, C19PacketResourcePackStatus.Action.DECLINED));
        }
        else {
            this.gameController.addScheduledTask(new Runnable(this, hash, url) {
                final String val$s;
                final NetHandlerPlayClient this$0;
                final String val$s1;
                
                @Override
                public void run() {
                    NetHandlerPlayClient.access$100(this.this$0).displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this) {
                        final NetHandlerPlayClient$3 this$1;
                        
                        @Override
                        public void confirmClicked(final boolean b, final int n) {
                            NetHandlerPlayClient.access$102(this.this$1.this$0, Minecraft.getMinecraft());
                            if (b) {
                                if (NetHandlerPlayClient.access$100(this.this$1.this$0).getCurrentServerData() != null) {
                                    NetHandlerPlayClient.access$100(this.this$1.this$0).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
                                }
                                NetHandlerPlayClient.access$000(this.this$1.this$0).sendPacket(new C19PacketResourcePackStatus(this.this$1.val$s1, C19PacketResourcePackStatus.Action.ACCEPTED));
                                Futures.addCallback(NetHandlerPlayClient.access$100(this.this$1.this$0).getResourcePackRepository().downloadResourcePack(this.this$1.val$s, this.this$1.val$s1), (FutureCallback)new FutureCallback(this) {
                                    final NetHandlerPlayClient$3$1 this$2;
                                    
                                    public void onSuccess(final Object o) {
                                        NetHandlerPlayClient.access$000(this.this$2.this$1.this$0).sendPacket(new C19PacketResourcePackStatus(this.this$2.this$1.val$s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                                    }
                                    
                                    public void onFailure(final Throwable t) {
                                        NetHandlerPlayClient.access$000(this.this$2.this$1.this$0).sendPacket(new C19PacketResourcePackStatus(this.this$2.this$1.val$s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                                    }
                                });
                            }
                            else {
                                if (NetHandlerPlayClient.access$100(this.this$1.this$0).getCurrentServerData() != null) {
                                    NetHandlerPlayClient.access$100(this.this$1.this$0).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
                                }
                                NetHandlerPlayClient.access$000(this.this$1.this$0).sendPacket(new C19PacketResourcePackStatus(this.this$1.val$s1, C19PacketResourcePackStatus.Action.DECLINED));
                            }
                            ServerList.func_147414_b(NetHandlerPlayClient.access$100(this.this$1.this$0).getCurrentServerData());
                            NetHandlerPlayClient.access$100(this.this$1.this$0).displayGuiScreen(null);
                        }
                    }, I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
                }
            });
        }
    }
    
    @Override
    public void handleWorldBorder(final S44PacketWorldBorder s44PacketWorldBorder) {
        PacketThreadUtil.checkThreadAndEnqueue(s44PacketWorldBorder, this, this.gameController);
        s44PacketWorldBorder.func_179788_a(this.clientWorldController.getWorldBorder());
    }
    
    @Override
    public void handleUpdateSign(final S33PacketUpdateSign s33PacketUpdateSign) {
        PacketThreadUtil.checkThreadAndEnqueue(s33PacketUpdateSign, this, this.gameController);
        if (this.gameController.theWorld.isBlockLoaded(s33PacketUpdateSign.getPos())) {
            final TileEntity tileEntity = this.gameController.theWorld.getTileEntity(s33PacketUpdateSign.getPos());
            if (tileEntity instanceof TileEntitySign) {
                final TileEntitySign tileEntitySign = (TileEntitySign)tileEntity;
                if (tileEntitySign.getIsEditable()) {
                    System.arraycopy(s33PacketUpdateSign.getLines(), 0, tileEntitySign.signText, 0, 4);
                    tileEntitySign.markDirty();
                }
            }
        }
    }
    
    public NetHandlerPlayClient(final Minecraft gameController, final GuiScreen guiScreenServer, final NetworkManager netManager, final GameProfile profile) {
        this.playerInfoMap = Maps.newHashMap();
        this.currentServerMaxPlayers = 20;
        this.field_147308_k = false;
        this.avRandomizer = new Random();
        this.gameController = gameController;
        this.guiScreenServer = guiScreenServer;
        this.netManager = netManager;
        this.profile = profile;
    }
    
    @Override
    public void handleOpenWindow(final S2DPacketOpenWindow s2DPacketOpenWindow) {
        PacketThreadUtil.checkThreadAndEnqueue(s2DPacketOpenWindow, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if ("minecraft:container".equals(s2DPacketOpenWindow.getGuiId())) {
            thePlayer.displayGUIChest(new InventoryBasic(s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
        }
        else if ("minecraft:villager".equals(s2DPacketOpenWindow.getGuiId())) {
            thePlayer.displayVillagerTradeGui(new NpcMerchant(thePlayer, s2DPacketOpenWindow.getWindowTitle()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
        }
        else if ("EntityHorse".equals(s2DPacketOpenWindow.getGuiId())) {
            final Entity entityByID = this.clientWorldController.getEntityByID(s2DPacketOpenWindow.getEntityId());
            if (entityByID instanceof EntityHorse) {
                thePlayer.displayGUIHorse((EntityHorse)entityByID, new AnimalChest(s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()));
                thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
            }
        }
        else if (!s2DPacketOpenWindow.hasSlots()) {
            thePlayer.displayGui(new LocalBlockIntercommunication(s2DPacketOpenWindow.getGuiId(), s2DPacketOpenWindow.getWindowTitle()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
        }
        else {
            thePlayer.displayGUIChest(new ContainerLocalMenu(s2DPacketOpenWindow.getGuiId(), s2DPacketOpenWindow.getWindowTitle(), s2DPacketOpenWindow.getSlotCount()));
            thePlayer.openContainer.windowId = s2DPacketOpenWindow.getWindowId();
        }
    }
    
    @Override
    public void handleTeams(final S3EPacketTeams s3EPacketTeams) {
        PacketThreadUtil.checkThreadAndEnqueue(s3EPacketTeams, this, this.gameController);
        final Scoreboard scoreboard = this.clientWorldController.getScoreboard();
        ScorePlayerTeam scorePlayerTeam;
        if (s3EPacketTeams.func_149307_h() == 0) {
            scorePlayerTeam = scoreboard.createTeam(s3EPacketTeams.func_149312_c());
        }
        else {
            scorePlayerTeam = scoreboard.getTeam(s3EPacketTeams.func_149312_c());
        }
        if (s3EPacketTeams.func_149307_h() == 0 || s3EPacketTeams.func_149307_h() == 2) {
            scorePlayerTeam.setTeamName(s3EPacketTeams.func_149306_d());
            scorePlayerTeam.setNamePrefix(s3EPacketTeams.func_149311_e());
            scorePlayerTeam.setNameSuffix(s3EPacketTeams.func_149309_f());
            scorePlayerTeam.setChatFormat(EnumChatFormatting.func_175744_a(s3EPacketTeams.func_179813_h()));
            scorePlayerTeam.func_98298_a(s3EPacketTeams.func_149308_i());
            final Team.EnumVisible func_178824_a = Team.EnumVisible.func_178824_a(s3EPacketTeams.func_179814_i());
            if (func_178824_a != null) {
                scorePlayerTeam.setNameTagVisibility(func_178824_a);
            }
        }
        if (s3EPacketTeams.func_149307_h() == 0 || s3EPacketTeams.func_149307_h() == 3) {
            final Iterator<String> iterator = s3EPacketTeams.func_149310_g().iterator();
            while (iterator.hasNext()) {
                scoreboard.addPlayerToTeam(iterator.next(), s3EPacketTeams.func_149312_c());
            }
        }
        if (s3EPacketTeams.func_149307_h() == 4) {
            final Iterator<String> iterator2 = s3EPacketTeams.func_149310_g().iterator();
            while (iterator2.hasNext()) {
                scoreboard.removePlayerFromTeam(iterator2.next(), scorePlayerTeam);
            }
        }
        if (s3EPacketTeams.func_149307_h() == 1) {
            scoreboard.removeTeam(scorePlayerTeam);
        }
    }
    
    @Override
    public void handleCustomPayload(final S3FPacketCustomPayload s3FPacketCustomPayload) {
        PacketThreadUtil.checkThreadAndEnqueue(s3FPacketCustomPayload, this, this.gameController);
        if ("MC|TrList".equals(s3FPacketCustomPayload.getChannelName())) {
            final PacketBuffer bufferData = s3FPacketCustomPayload.getBufferData();
            final int int1 = bufferData.readInt();
            final GuiScreen currentScreen = this.gameController.currentScreen;
            if (currentScreen != null && currentScreen instanceof GuiMerchant && int1 == this.gameController.thePlayer.openContainer.windowId) {
                ((GuiMerchant)currentScreen).getMerchant().setRecipes(MerchantRecipeList.readFromBuf(bufferData));
            }
            bufferData.release();
        }
        else if ("MC|Brand".equals(s3FPacketCustomPayload.getChannelName())) {
            this.gameController.thePlayer.setClientBrand(s3FPacketCustomPayload.getBufferData().readStringFromBuffer(32767));
        }
        else if ("MC|BOpen".equals(s3FPacketCustomPayload.getChannelName())) {
            final ItemStack currentEquippedItem = this.gameController.thePlayer.getCurrentEquippedItem();
            if (currentEquippedItem != null && currentEquippedItem.getItem() == Items.written_book) {
                this.gameController.displayGuiScreen(new GuiScreenBook(this.gameController.thePlayer, currentEquippedItem, false));
            }
        }
    }
    
    @Override
    public void handleTabComplete(final S3APacketTabComplete s3APacketTabComplete) {
        PacketThreadUtil.checkThreadAndEnqueue(s3APacketTabComplete, this, this.gameController);
        final String[] func_149630_c = s3APacketTabComplete.func_149630_c();
        if (this.gameController.currentScreen instanceof GuiChat) {
            ((GuiChat)this.gameController.currentScreen).onAutocompleteResponse(func_149630_c);
        }
    }
    
    @Override
    public void handleMaps(final S34PacketMaps s34PacketMaps) {
        PacketThreadUtil.checkThreadAndEnqueue(s34PacketMaps, this, this.gameController);
        final MapData loadMapData = ItemMap.loadMapData(s34PacketMaps.getMapId(), this.gameController.theWorld);
        s34PacketMaps.setMapdataTo(loadMapData);
        this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(loadMapData);
    }
    
    @Override
    public void handleEntityMovement(final S14PacketEntity s14PacketEntity) {
        PacketThreadUtil.checkThreadAndEnqueue(s14PacketEntity, this, this.gameController);
        final Entity entity = s14PacketEntity.getEntity(this.clientWorldController);
        if (entity != null) {
            final Entity entity2 = entity;
            entity2.serverPosX += s14PacketEntity.func_149062_c();
            final Entity entity3 = entity;
            entity3.serverPosY += s14PacketEntity.func_149061_d();
            final Entity entity4 = entity;
            entity4.serverPosZ += s14PacketEntity.func_149064_e();
            entity.setPositionAndRotation2(entity.serverPosX / 32.0, entity.serverPosY / 32.0, entity.serverPosZ / 32.0, s14PacketEntity.func_149060_h() ? (s14PacketEntity.func_149066_f() * 360 / 256.0f) : entity.rotationYaw, s14PacketEntity.func_149060_h() ? (s14PacketEntity.func_149063_g() * 360 / 256.0f) : entity.rotationPitch, 3, false);
            entity.onGround = s14PacketEntity.getOnGround();
        }
    }
    
    @Override
    public void handleWindowItems(final S30PacketWindowItems s30PacketWindowItems) {
        PacketThreadUtil.checkThreadAndEnqueue(s30PacketWindowItems, this, this.gameController);
        final EntityPlayerSP thePlayer = this.gameController.thePlayer;
        if (s30PacketWindowItems.func_148911_c() == 0) {
            thePlayer.inventoryContainer.putStacksInSlots(s30PacketWindowItems.getItemStacks());
        }
        else if (s30PacketWindowItems.func_148911_c() == thePlayer.openContainer.windowId) {
            thePlayer.openContainer.putStacksInSlots(s30PacketWindowItems.getItemStacks());
        }
    }
    
    @Override
    public void handleParticles(final S2APacketParticles s2APacketParticles) {
        PacketThreadUtil.checkThreadAndEnqueue(s2APacketParticles, this, this.gameController);
        if (s2APacketParticles.getParticleCount() == 0) {
            this.clientWorldController.spawnParticle(s2APacketParticles.getParticleType(), s2APacketParticles.isLongDistance(), s2APacketParticles.getXCoordinate(), s2APacketParticles.getYCoordinate(), s2APacketParticles.getZCoordinate(), s2APacketParticles.getParticleSpeed() * s2APacketParticles.getXOffset(), s2APacketParticles.getParticleSpeed() * s2APacketParticles.getYOffset(), s2APacketParticles.getParticleSpeed() * s2APacketParticles.getZOffset(), s2APacketParticles.getParticleArgs());
        }
        else {
            while (0 < s2APacketParticles.getParticleCount()) {
                this.clientWorldController.spawnParticle(s2APacketParticles.getParticleType(), s2APacketParticles.isLongDistance(), s2APacketParticles.getXCoordinate() + this.avRandomizer.nextGaussian() * s2APacketParticles.getXOffset(), s2APacketParticles.getYCoordinate() + this.avRandomizer.nextGaussian() * s2APacketParticles.getYOffset(), s2APacketParticles.getZCoordinate() + this.avRandomizer.nextGaussian() * s2APacketParticles.getZOffset(), this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed(), this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed(), this.avRandomizer.nextGaussian() * s2APacketParticles.getParticleSpeed(), s2APacketParticles.getParticleArgs());
                int n = 0;
                ++n;
            }
        }
    }
    
    public NetworkManager getNetworkManager() {
        return this.netManager;
    }
    
    @Override
    public void handleCamera(final S43PacketCamera s43PacketCamera) {
        PacketThreadUtil.checkThreadAndEnqueue(s43PacketCamera, this, this.gameController);
        final Entity entity = s43PacketCamera.getEntity(this.clientWorldController);
        if (entity != null) {
            this.gameController.setRenderViewEntity(entity);
        }
    }
    
    @Override
    public void handleSpawnGlobalEntity(final S2CPacketSpawnGlobalEntity s2CPacketSpawnGlobalEntity) {
        PacketThreadUtil.checkThreadAndEnqueue(s2CPacketSpawnGlobalEntity, this, this.gameController);
        final double n = s2CPacketSpawnGlobalEntity.func_149051_d() / 32.0;
        final double n2 = s2CPacketSpawnGlobalEntity.func_149050_e() / 32.0;
        final double n3 = s2CPacketSpawnGlobalEntity.func_149049_f() / 32.0;
        Entity entity = null;
        if (s2CPacketSpawnGlobalEntity.func_149053_g() == 1) {
            entity = new EntityLightningBolt(this.clientWorldController, n, n2, n3);
        }
        if (entity != null) {
            entity.serverPosX = s2CPacketSpawnGlobalEntity.func_149051_d();
            entity.serverPosY = s2CPacketSpawnGlobalEntity.func_149050_e();
            entity.serverPosZ = s2CPacketSpawnGlobalEntity.func_149049_f();
            entity.rotationYaw = 0.0f;
            entity.rotationPitch = 0.0f;
            entity.setEntityId(s2CPacketSpawnGlobalEntity.func_149052_c());
            this.clientWorldController.addWeatherEffect(entity);
        }
    }
    
    @Override
    public void handleKeepAlive(final S00PacketKeepAlive s00PacketKeepAlive) {
        this.addToSendQueue(new C00PacketKeepAlive(s00PacketKeepAlive.func_149134_c()));
    }
}
