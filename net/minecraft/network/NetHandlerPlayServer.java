package net.minecraft.network;

import net.minecraft.network.play.*;
import net.minecraft.server.*;
import io.netty.util.concurrent.*;
import com.google.common.util.concurrent.*;
import net.minecraft.world.*;
import net.minecraft.entity.passive.*;
import net.minecraft.server.management.*;
import net.minecraft.stats.*;
import org.apache.logging.log4j.*;
import io.netty.buffer.*;
import java.io.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.command.server.*;
import net.minecraft.entity.*;
import com.google.common.collect.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.play.server.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;

public class NetHandlerPlayServer implements ITickable, INetHandlerPlayServer
{
    private int chatSpamThresholdCount;
    private int floatingTickCount;
    private boolean hasMoved;
    private double lastPosZ;
    private final MinecraftServer serverController;
    private int networkTickCount;
    private boolean field_147366_g;
    private int itemDropThreshold;
    private double lastPosY;
    private long lastPingTime;
    private long lastSentPingPacket;
    private int field_175090_f;
    private int field_147378_h;
    private IntHashMap field_147372_n;
    public final NetworkManager netManager;
    private static final Logger logger;
    public EntityPlayerMP playerEntity;
    private double lastPosX;
    
    public void kickPlayerFromServer(final String s) {
        final ChatComponentText chatComponentText = new ChatComponentText(s);
        this.netManager.sendPacket(new S40PacketDisconnect(chatComponentText), (GenericFutureListener)new GenericFutureListener(this, chatComponentText) {
            final ChatComponentText val$chatcomponenttext;
            final NetHandlerPlayServer this$0;
            
            public void operationComplete(final Future future) throws Exception {
                this.this$0.netManager.closeChannel(this.val$chatcomponenttext);
            }
        }, new GenericFutureListener[0]);
        this.netManager.disableAutoRead();
        Futures.getUnchecked((java.util.concurrent.Future)this.serverController.addScheduledTask(new Runnable(this) {
            final NetHandlerPlayServer this$0;
            
            @Override
            public void run() {
                this.this$0.netManager.checkDisconnected();
            }
        }));
    }
    
    @Override
    public void handleResourcePackStatus(final C19PacketResourcePackStatus c19PacketResourcePackStatus) {
    }
    
    public void sendPacket(final Packet packet) {
        if (packet instanceof S02PacketChat) {
            final S02PacketChat s02PacketChat = (S02PacketChat)packet;
            final EntityPlayer.EnumChatVisibility chatVisibility = this.playerEntity.getChatVisibility();
            if (chatVisibility == EntityPlayer.EnumChatVisibility.HIDDEN) {
                return;
            }
            if (chatVisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !s02PacketChat.isChat()) {
                return;
            }
        }
        this.netManager.sendPacket(packet);
    }
    
    @Override
    public void processEnchantItem(final C11PacketEnchantItem c11PacketEnchantItem) {
        PacketThreadUtil.checkThreadAndEnqueue(c11PacketEnchantItem, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        if (this.playerEntity.openContainer.windowId == c11PacketEnchantItem.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.isSpectator()) {
            this.playerEntity.openContainer.enchantItem(this.playerEntity, c11PacketEnchantItem.getButton());
            this.playerEntity.openContainer.detectAndSendChanges();
        }
    }
    
    @Override
    public void processPlayerBlockPlacement(final C08PacketPlayerBlockPlacement c08PacketPlayerBlockPlacement) {
        PacketThreadUtil.checkThreadAndEnqueue(c08PacketPlayerBlockPlacement, this, this.playerEntity.getServerForPlayer());
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final ItemStack currentItem = this.playerEntity.inventory.getCurrentItem();
        final BlockPos position = c08PacketPlayerBlockPlacement.getPosition();
        final EnumFacing front = EnumFacing.getFront(c08PacketPlayerBlockPlacement.getPlacedBlockDirection());
        this.playerEntity.markPlayerActive();
        if (c08PacketPlayerBlockPlacement.getPlacedBlockDirection() == 255) {
            if (currentItem == null) {
                return;
            }
            this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, worldServerForDimension, currentItem);
        }
        else if (position.getY() < this.serverController.getBuildLimit() - 1 || (front != EnumFacing.UP && position.getY() < this.serverController.getBuildLimit())) {
            if (this.hasMoved && this.playerEntity.getDistanceSq(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5) < 64.0 && !this.serverController.isBlockProtected(worldServerForDimension, position, this.playerEntity) && worldServerForDimension.getWorldBorder().contains(position)) {
                this.playerEntity.theItemInWorldManager.activateBlockOrUseItem(this.playerEntity, worldServerForDimension, currentItem, position, front, c08PacketPlayerBlockPlacement.getPlacedBlockOffsetX(), c08PacketPlayerBlockPlacement.getPlacedBlockOffsetY(), c08PacketPlayerBlockPlacement.getPlacedBlockOffsetZ());
            }
        }
        else {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("build.tooHigh", new Object[] { this.serverController.getBuildLimit() });
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(chatComponentTranslation));
        }
        this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, position));
        this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, position.offset(front)));
        ItemStack currentItem2 = this.playerEntity.inventory.getCurrentItem();
        if (currentItem2 != null && currentItem2.stackSize == 0) {
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
            currentItem2 = null;
        }
        if (currentItem2 == null || currentItem2.getMaxItemUseDuration() == 0) {
            this.playerEntity.isChangingQuantityOnly = true;
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
            final Slot slotFromInventory = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
            this.playerEntity.openContainer.detectAndSendChanges();
            this.playerEntity.isChangingQuantityOnly = false;
            if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), c08PacketPlayerBlockPlacement.getStack())) {
                this.sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, slotFromInventory.slotNumber, this.playerEntity.inventory.getCurrentItem()));
            }
        }
    }
    
    @Override
    public void processPlayerAbilities(final C13PacketPlayerAbilities c13PacketPlayerAbilities) {
        PacketThreadUtil.checkThreadAndEnqueue(c13PacketPlayerAbilities, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.capabilities.isFlying = (c13PacketPlayerAbilities.isFlying() && this.playerEntity.capabilities.allowFlying);
    }
    
    private long currentTimeMillis() {
        return System.nanoTime() / 1000000L;
    }
    
    @Override
    public void processEntityAction(final C0BPacketEntityAction c0BPacketEntityAction) {
        PacketThreadUtil.checkThreadAndEnqueue(c0BPacketEntityAction, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        switch (NetHandlerPlayServer$4.$SwitchMap$net$minecraft$network$play$client$C0BPacketEntityAction$Action[c0BPacketEntityAction.getAction().ordinal()]) {
            case 1: {
                this.playerEntity.setSneaking(true);
                break;
            }
            case 2: {
                this.playerEntity.setSneaking(false);
                break;
            }
            case 3: {
                this.playerEntity.setSprinting(true);
                break;
            }
            case 4: {
                this.playerEntity.setSprinting(false);
                break;
            }
            case 5: {
                this.playerEntity.wakeUpPlayer(false, true, true);
                this.hasMoved = false;
                break;
            }
            case 6: {
                if (this.playerEntity.ridingEntity instanceof EntityHorse) {
                    ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(c0BPacketEntityAction.getAuxData());
                    break;
                }
                break;
            }
            case 7: {
                if (this.playerEntity.ridingEntity instanceof EntityHorse) {
                    ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
                    break;
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid client command!");
            }
        }
    }
    
    public NetHandlerPlayServer(final MinecraftServer serverController, final NetworkManager netManager, final EntityPlayerMP playerEntity) {
        this.field_147372_n = new IntHashMap();
        this.hasMoved = true;
        this.serverController = serverController;
        (this.netManager = netManager).setNetHandler(this);
        this.playerEntity = playerEntity;
        playerEntity.playerNetServerHandler = this;
    }
    
    @Override
    public void processClientStatus(final C16PacketClientStatus c16PacketClientStatus) {
        PacketThreadUtil.checkThreadAndEnqueue(c16PacketClientStatus, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        switch (NetHandlerPlayServer$4.$SwitchMap$net$minecraft$network$play$client$C16PacketClientStatus$EnumState[c16PacketClientStatus.getStatus().ordinal()]) {
            case 1: {
                if (this.playerEntity.playerConqueredTheEnd) {
                    this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true);
                    break;
                }
                if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
                    if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
                        this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                        this.serverController.deleteWorldAndStopServer();
                        break;
                    }
                    this.serverController.getConfigurationManager().getBannedPlayers().addEntry(new UserListBansEntry(this.playerEntity.getGameProfile(), null, "(You just lost the game)", null, "Death in Hardcore"));
                    this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
                    break;
                }
                else {
                    if (this.playerEntity.getHealth() > 0.0f) {
                        return;
                    }
                    this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
                    break;
                }
                break;
            }
            case 2: {
                this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
                break;
            }
            case 3: {
                this.playerEntity.triggerAchievement(AchievementList.openInventory);
                break;
            }
        }
    }
    
    @Override
    public void processUpdateSign(final C12PacketUpdateSign c12PacketUpdateSign) {
        PacketThreadUtil.checkThreadAndEnqueue(c12PacketUpdateSign, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final BlockPos position = c12PacketUpdateSign.getPosition();
        if (worldServerForDimension.isBlockLoaded(position)) {
            final TileEntity tileEntity = worldServerForDimension.getTileEntity(position);
            if (!(tileEntity instanceof TileEntitySign)) {
                return;
            }
            final TileEntitySign tileEntitySign = (TileEntitySign)tileEntity;
            if (!tileEntitySign.getIsEditable() || tileEntitySign.getPlayer() != this.playerEntity) {
                this.serverController.logWarning("Player " + this.playerEntity.getName() + " just tried to change non-editable sign");
                return;
            }
            final IChatComponent[] lines = c12PacketUpdateSign.getLines();
            while (0 < lines.length) {
                tileEntitySign.signText[0] = new ChatComponentText(EnumChatFormatting.getTextWithoutFormattingCodes(lines[0].getUnformattedText()));
                int n = 0;
                ++n;
            }
            tileEntitySign.markDirty();
            worldServerForDimension.markBlockForUpdate(position);
        }
    }
    
    public NetworkManager getNetworkManager() {
        return this.netManager;
    }
    
    static {
        logger = LogManager.getLogger();
    }
    
    @Override
    public void processVanilla250Packet(final C17PacketCustomPayload c17PacketCustomPayload) {
        PacketThreadUtil.checkThreadAndEnqueue(c17PacketCustomPayload, this, this.playerEntity.getServerForPlayer());
        if ("MC|BEdit".equals(c17PacketCustomPayload.getChannelName())) {
            final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer((ByteBuf)c17PacketCustomPayload.getBufferData()));
            final ItemStack itemStackFromBuffer = packetBuffer.readItemStackFromBuffer();
            if (itemStackFromBuffer == null) {
                packetBuffer.release();
                return;
            }
            if (!ItemWritableBook.isNBTValid(itemStackFromBuffer.getTagCompound())) {
                throw new IOException("Invalid book tag!");
            }
            final ItemStack currentItem = this.playerEntity.inventory.getCurrentItem();
            if (currentItem == null) {
                packetBuffer.release();
                return;
            }
            if (itemStackFromBuffer.getItem() == Items.writable_book && itemStackFromBuffer.getItem() == currentItem.getItem()) {
                currentItem.setTagInfo("pages", itemStackFromBuffer.getTagCompound().getTagList("pages", 8));
            }
            packetBuffer.release();
        }
        else {
            if (!"MC|BSign".equals(c17PacketCustomPayload.getChannelName())) {
                if ("MC|TrSel".equals(c17PacketCustomPayload.getChannelName())) {
                    final int int1 = c17PacketCustomPayload.getBufferData().readInt();
                    final Container openContainer = this.playerEntity.openContainer;
                    if (openContainer instanceof ContainerMerchant) {
                        ((ContainerMerchant)openContainer).setCurrentRecipeIndex(int1);
                    }
                }
                else if ("MC|AdvCdm".equals(c17PacketCustomPayload.getChannelName())) {
                    if (!this.serverController.isCommandBlockEnabled()) {
                        this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
                    }
                    else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
                        final PacketBuffer bufferData = c17PacketCustomPayload.getBufferData();
                        final byte byte1 = bufferData.readByte();
                        CommandBlockLogic commandBlockLogic = null;
                        if (byte1 == 0) {
                            final TileEntity tileEntity = this.playerEntity.worldObj.getTileEntity(new BlockPos(bufferData.readInt(), bufferData.readInt(), bufferData.readInt()));
                            if (tileEntity instanceof TileEntityCommandBlock) {
                                commandBlockLogic = ((TileEntityCommandBlock)tileEntity).getCommandBlockLogic();
                            }
                        }
                        else if (byte1 == 1) {
                            final Entity entityByID = this.playerEntity.worldObj.getEntityByID(bufferData.readInt());
                            if (entityByID instanceof EntityMinecartCommandBlock) {
                                commandBlockLogic = ((EntityMinecartCommandBlock)entityByID).getCommandBlockLogic();
                            }
                        }
                        final String stringFromBuffer = bufferData.readStringFromBuffer(bufferData.readableBytes());
                        final boolean boolean1 = bufferData.readBoolean();
                        if (commandBlockLogic != null) {
                            commandBlockLogic.setCommand(stringFromBuffer);
                            commandBlockLogic.setTrackOutput(boolean1);
                            if (!boolean1) {
                                commandBlockLogic.setLastOutput(null);
                            }
                            commandBlockLogic.updateCommand();
                            this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.setCommand.success", new Object[] { stringFromBuffer }));
                        }
                        bufferData.release();
                    }
                    else {
                        this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
                    }
                }
                else if ("MC|Beacon".equals(c17PacketCustomPayload.getChannelName())) {
                    if (this.playerEntity.openContainer instanceof ContainerBeacon) {
                        final PacketBuffer bufferData2 = c17PacketCustomPayload.getBufferData();
                        final int int2 = bufferData2.readInt();
                        final int int3 = bufferData2.readInt();
                        final ContainerBeacon containerBeacon = (ContainerBeacon)this.playerEntity.openContainer;
                        final Slot slot = containerBeacon.getSlot(0);
                        if (slot.getHasStack()) {
                            slot.decrStackSize(1);
                            final IInventory func_180611_e = containerBeacon.func_180611_e();
                            func_180611_e.setField(1, int2);
                            func_180611_e.setField(2, int3);
                            func_180611_e.markDirty();
                        }
                    }
                }
                else if ("MC|ItemName".equals(c17PacketCustomPayload.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair) {
                    final ContainerRepair containerRepair = (ContainerRepair)this.playerEntity.openContainer;
                    if (c17PacketCustomPayload.getBufferData() != null && c17PacketCustomPayload.getBufferData().readableBytes() >= 1) {
                        final String filterAllowedCharacters = ChatAllowedCharacters.filterAllowedCharacters(c17PacketCustomPayload.getBufferData().readStringFromBuffer(32767));
                        if (filterAllowedCharacters.length() <= 30) {
                            containerRepair.updateItemName(filterAllowedCharacters);
                        }
                    }
                    else {
                        containerRepair.updateItemName("");
                    }
                }
                return;
            }
            final PacketBuffer packetBuffer2 = new PacketBuffer(Unpooled.wrappedBuffer((ByteBuf)c17PacketCustomPayload.getBufferData()));
            final ItemStack itemStackFromBuffer2 = packetBuffer2.readItemStackFromBuffer();
            if (itemStackFromBuffer2 == null) {
                packetBuffer2.release();
                return;
            }
            if (!ItemEditableBook.validBookTagContents(itemStackFromBuffer2.getTagCompound())) {
                throw new IOException("Invalid book tag!");
            }
            final ItemStack currentItem2 = this.playerEntity.inventory.getCurrentItem();
            if (currentItem2 == null) {
                packetBuffer2.release();
                return;
            }
            if (itemStackFromBuffer2.getItem() == Items.written_book && currentItem2.getItem() == Items.writable_book) {
                currentItem2.setTagInfo("author", new NBTTagString(this.playerEntity.getName()));
                currentItem2.setTagInfo("title", new NBTTagString(itemStackFromBuffer2.getTagCompound().getString("title")));
                currentItem2.setTagInfo("pages", itemStackFromBuffer2.getTagCompound().getTagList("pages", 8));
                currentItem2.setItem(Items.written_book);
            }
            packetBuffer2.release();
        }
    }
    
    @Override
    public void processTabComplete(final C14PacketTabComplete c14PacketTabComplete) {
        PacketThreadUtil.checkThreadAndEnqueue(c14PacketTabComplete, this, this.playerEntity.getServerForPlayer());
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<String> iterator = this.serverController.getTabCompletions(this.playerEntity, c14PacketTabComplete.getMessage(), c14PacketTabComplete.getTargetBlock()).iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete(arrayList.toArray(new String[arrayList.size()])));
    }
    
    @Override
    public void handleSpectate(final C18PacketSpectate c18PacketSpectate) {
        PacketThreadUtil.checkThreadAndEnqueue(c18PacketSpectate, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.isSpectator()) {
            Entity entity = null;
            final WorldServer[] worldServers = this.serverController.worldServers;
            while (0 < worldServers.length) {
                final WorldServer worldServer = worldServers[0];
                if (worldServer != null) {
                    entity = c18PacketSpectate.getEntity(worldServer);
                    if (entity != null) {
                        break;
                    }
                }
                int n = 0;
                ++n;
            }
            if (entity != null) {
                this.playerEntity.setSpectatingEntity(this.playerEntity);
                this.playerEntity.mountEntity(null);
                if (entity.worldObj != this.playerEntity.worldObj) {
                    final WorldServer serverForPlayer = this.playerEntity.getServerForPlayer();
                    final WorldServer worldServer2 = (WorldServer)entity.worldObj;
                    this.playerEntity.dimension = entity.dimension;
                    this.sendPacket(new S07PacketRespawn(this.playerEntity.dimension, serverForPlayer.getDifficulty(), serverForPlayer.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
                    serverForPlayer.removePlayerEntityDangerously(this.playerEntity);
                    this.playerEntity.isDead = false;
                    this.playerEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                    if (this.playerEntity.isEntityAlive()) {
                        serverForPlayer.updateEntityWithOptionalForce(this.playerEntity, false);
                        worldServer2.spawnEntityInWorld(this.playerEntity);
                        worldServer2.updateEntityWithOptionalForce(this.playerEntity, false);
                    }
                    this.playerEntity.setWorld(worldServer2);
                    this.serverController.getConfigurationManager().preparePlayer(this.playerEntity, serverForPlayer);
                    this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                    this.playerEntity.theItemInWorldManager.setWorld(worldServer2);
                    this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, worldServer2);
                    this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
                }
                else {
                    this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
                }
            }
        }
    }
    
    @Override
    public void processPlayer(final C03PacketPlayer c03PacketPlayer) {
        PacketThreadUtil.checkThreadAndEnqueue(c03PacketPlayer, this, this.playerEntity.getServerForPlayer());
        if (c03PacketPlayer != 0) {
            this.kickPlayerFromServer("Invalid move packet received");
        }
        else {
            final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
            this.field_147366_g = true;
            if (!this.playerEntity.playerConqueredTheEnd) {
                final double posX = this.playerEntity.posX;
                final double posY = this.playerEntity.posY;
                final double posZ = this.playerEntity.posZ;
                double n = 0.0;
                final double n2 = c03PacketPlayer.getPositionX() - this.lastPosX;
                final double n3 = c03PacketPlayer.getPositionY() - this.lastPosY;
                final double n4 = c03PacketPlayer.getPositionZ() - this.lastPosZ;
                if (c03PacketPlayer.isMoving()) {
                    n = n2 * n2 + n3 * n3 + n4 * n4;
                    if (!this.hasMoved && n < 0.25) {
                        this.hasMoved = true;
                    }
                }
                if (this.hasMoved) {
                    this.field_175090_f = this.networkTickCount;
                    if (this.playerEntity.ridingEntity != null) {
                        float n5 = this.playerEntity.rotationYaw;
                        float n6 = this.playerEntity.rotationPitch;
                        this.playerEntity.ridingEntity.updateRiderPosition();
                        final double posX2 = this.playerEntity.posX;
                        final double posY2 = this.playerEntity.posY;
                        final double posZ2 = this.playerEntity.posZ;
                        if (c03PacketPlayer.getRotating()) {
                            n5 = c03PacketPlayer.getYaw();
                            n6 = c03PacketPlayer.getPitch();
                        }
                        this.playerEntity.onGround = c03PacketPlayer.isOnGround();
                        this.playerEntity.onUpdateEntity();
                        this.playerEntity.setPositionAndRotation(posX2, posY2, posZ2, n5, n6);
                        if (this.playerEntity.ridingEntity != null) {
                            this.playerEntity.ridingEntity.updateRiderPosition();
                        }
                        this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                        if (this.playerEntity.ridingEntity != null) {
                            if (n > 4.0) {
                                this.playerEntity.playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(this.playerEntity.ridingEntity));
                                this.setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                            }
                            this.playerEntity.ridingEntity.isAirBorne = true;
                        }
                        if (this.hasMoved) {
                            this.lastPosX = this.playerEntity.posX;
                            this.lastPosY = this.playerEntity.posY;
                            this.lastPosZ = this.playerEntity.posZ;
                        }
                        worldServerForDimension.updateEntity(this.playerEntity);
                        return;
                    }
                    if (this.playerEntity.isPlayerSleeping()) {
                        this.playerEntity.onUpdateEntity();
                        this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                        worldServerForDimension.updateEntity(this.playerEntity);
                        return;
                    }
                    final double posY3 = this.playerEntity.posY;
                    this.lastPosX = this.playerEntity.posX;
                    this.lastPosY = this.playerEntity.posY;
                    this.lastPosZ = this.playerEntity.posZ;
                    double n7 = this.playerEntity.posX;
                    double n8 = this.playerEntity.posY;
                    double n9 = this.playerEntity.posZ;
                    float n10 = this.playerEntity.rotationYaw;
                    float n11 = this.playerEntity.rotationPitch;
                    if (c03PacketPlayer.isMoving() && c03PacketPlayer.getPositionY() == -999.0) {
                        c03PacketPlayer.setMoving(false);
                    }
                    if (c03PacketPlayer.isMoving()) {
                        n7 = c03PacketPlayer.getPositionX();
                        n8 = c03PacketPlayer.getPositionY();
                        n9 = c03PacketPlayer.getPositionZ();
                        if (Math.abs(c03PacketPlayer.getPositionX()) > 3.0E7 || Math.abs(c03PacketPlayer.getPositionZ()) > 3.0E7) {
                            this.kickPlayerFromServer("Illegal position");
                            return;
                        }
                    }
                    if (c03PacketPlayer.getRotating()) {
                        n10 = c03PacketPlayer.getYaw();
                        n11 = c03PacketPlayer.getPitch();
                    }
                    this.playerEntity.onUpdateEntity();
                    this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, n10, n11);
                    if (!this.hasMoved) {
                        return;
                    }
                    final double n12 = n7 - this.playerEntity.posX;
                    final double n13 = n8 - this.playerEntity.posY;
                    final double n14 = n9 - this.playerEntity.posZ;
                    if (n12 * n12 + n13 * n13 + n14 * n14 - (this.playerEntity.motionX * this.playerEntity.motionX + this.playerEntity.motionY * this.playerEntity.motionY + this.playerEntity.motionZ * this.playerEntity.motionZ) > 100.0 && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
                        NetHandlerPlayServer.logger.warn(this.playerEntity.getName() + " moved too quickly! " + n12 + "," + n13 + "," + n14 + " (" + n12 + ", " + n13 + ", " + n14 + ")");
                        this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                        return;
                    }
                    final float n15 = 0.0625f;
                    final boolean empty = worldServerForDimension.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(n15, n15, n15)).isEmpty();
                    if (this.playerEntity.onGround && !c03PacketPlayer.isOnGround() && n13 > 0.0) {
                        this.playerEntity.jump();
                    }
                    this.playerEntity.moveEntity(n12, n13, n14);
                    this.playerEntity.onGround = c03PacketPlayer.isOnGround();
                    final double n16 = n7 - this.playerEntity.posX;
                    double n17 = n8 - this.playerEntity.posY;
                    if (n17 > -0.5 || n17 < 0.5) {
                        n17 = 0.0;
                    }
                    final double n18 = n9 - this.playerEntity.posZ;
                    if (n16 * n16 + n17 * n17 + n18 * n18 > 0.0625 && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
                        NetHandlerPlayServer.logger.warn(this.playerEntity.getName() + " moved wrongly!");
                    }
                    this.playerEntity.setPositionAndRotation(n7, n8, n9, n10, n11);
                    this.playerEntity.addMovementStat(this.playerEntity.posX - posX, this.playerEntity.posY - posY, this.playerEntity.posZ - posZ);
                    if (!this.playerEntity.noClip) {
                        worldServerForDimension.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(n15, n15, n15)).isEmpty();
                        if (empty && !this.playerEntity.isPlayerSleeping()) {
                            this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, n10, n11);
                            return;
                        }
                    }
                    final AxisAlignedBB addCoord = this.playerEntity.getEntityBoundingBox().expand(n15, n15, n15).addCoord(0.0, -0.55, 0.0);
                    if (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying && !worldServerForDimension.checkBlockCollision(addCoord)) {
                        if (n17 >= -0.03125) {
                            ++this.floatingTickCount;
                            if (this.floatingTickCount > 80) {
                                NetHandlerPlayServer.logger.warn(this.playerEntity.getName() + " was kicked for floating too long!");
                                this.kickPlayerFromServer("Flying is not enabled on this server");
                                return;
                            }
                        }
                    }
                    else {
                        this.floatingTickCount = 0;
                    }
                    this.playerEntity.onGround = c03PacketPlayer.isOnGround();
                    this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                    this.playerEntity.handleFalling(this.playerEntity.posY - posY3, c03PacketPlayer.isOnGround());
                }
                else if (this.networkTickCount - this.field_175090_f > 20) {
                    this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                }
            }
        }
    }
    
    @Override
    public void processKeepAlive(final C00PacketKeepAlive c00PacketKeepAlive) {
        if (c00PacketKeepAlive.getKey() == this.field_147378_h) {
            this.playerEntity.ping = (this.playerEntity.ping * 3 + (int)(this.currentTimeMillis() - this.lastPingTime)) / 4;
        }
    }
    
    @Override
    public void processInput(final C0CPacketInput c0CPacketInput) {
        PacketThreadUtil.checkThreadAndEnqueue(c0CPacketInput, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.setEntityActionState(c0CPacketInput.getStrafeSpeed(), c0CPacketInput.getForwardSpeed(), c0CPacketInput.isJumping(), c0CPacketInput.isSneaking());
    }
    
    @Override
    public void update() {
        this.field_147366_g = false;
        ++this.networkTickCount;
        this.serverController.theProfiler.startSection("keepAlive");
        if (this.networkTickCount - this.lastSentPingPacket > 40L) {
            this.lastSentPingPacket = this.networkTickCount;
            this.lastPingTime = this.currentTimeMillis();
            this.field_147378_h = (int)this.lastPingTime;
            this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
        }
        this.serverController.theProfiler.endSection();
        if (this.chatSpamThresholdCount > 0) {
            --this.chatSpamThresholdCount;
        }
        if (this.itemDropThreshold > 0) {
            --this.itemDropThreshold;
        }
        if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60) {
            this.kickPlayerFromServer("You have been idle for too long!");
        }
    }
    
    @Override
    public void processCloseWindow(final C0DPacketCloseWindow c0DPacketCloseWindow) {
        PacketThreadUtil.checkThreadAndEnqueue(c0DPacketCloseWindow, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.closeContainer();
    }
    
    @Override
    public void processChatMessage(final C01PacketChatMessage c01PacketChatMessage) {
        PacketThreadUtil.checkThreadAndEnqueue(c01PacketChatMessage, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
            final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            this.sendPacket(new S02PacketChat(chatComponentTranslation));
        }
        else {
            this.playerEntity.markPlayerActive();
            final String normalizeSpace = StringUtils.normalizeSpace(c01PacketChatMessage.getMessage());
            while (0 < normalizeSpace.length()) {
                if (!ChatAllowedCharacters.isAllowedCharacter(normalizeSpace.charAt(0))) {
                    this.kickPlayerFromServer("Illegal characters in chat");
                    return;
                }
                int n = 0;
                ++n;
            }
            if (normalizeSpace.startsWith("/")) {
                this.handleSlashCommand(normalizeSpace);
            }
            else {
                this.serverController.getConfigurationManager().sendChatMsgImpl(new ChatComponentTranslation("chat.type.text", new Object[] { this.playerEntity.getDisplayName(), normalizeSpace }), false);
            }
            this.chatSpamThresholdCount += 20;
            if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile())) {
                this.kickPlayerFromServer("disconnect.spam");
            }
        }
    }
    
    public void setPlayerLocation(final double lastPosX, final double lastPosY, final double lastPosZ, final float n, final float n2, final Set set) {
        this.hasMoved = false;
        this.lastPosX = lastPosX;
        this.lastPosY = lastPosY;
        this.lastPosZ = lastPosZ;
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.X)) {
            this.lastPosX += this.playerEntity.posX;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
            this.lastPosY += this.playerEntity.posY;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
            this.lastPosZ += this.playerEntity.posZ;
        }
        float n3 = n;
        float n4 = n2;
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
            n3 = n + this.playerEntity.rotationYaw;
        }
        if (set.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
            n4 = n2 + this.playerEntity.rotationPitch;
        }
        this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, n3, n4);
        this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(lastPosX, lastPosY, lastPosZ, n, n2, set));
    }
    
    @Override
    public void processClickWindow(final C0EPacketClickWindow c0EPacketClickWindow) {
        PacketThreadUtil.checkThreadAndEnqueue(c0EPacketClickWindow, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        if (this.playerEntity.openContainer.windowId == c0EPacketClickWindow.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity)) {
            if (this.playerEntity.isSpectator()) {
                final ArrayList arrayList = Lists.newArrayList();
                while (0 < this.playerEntity.openContainer.inventorySlots.size()) {
                    arrayList.add(this.playerEntity.openContainer.inventorySlots.get(0).getStack());
                    int n = 0;
                    ++n;
                }
                this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, arrayList);
            }
            else if (ItemStack.areItemStacksEqual(c0EPacketClickWindow.getClickedItem(), this.playerEntity.openContainer.slotClick(c0EPacketClickWindow.getSlotId(), c0EPacketClickWindow.getUsedButton(), c0EPacketClickWindow.getMode(), this.playerEntity))) {
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(c0EPacketClickWindow.getWindowId(), c0EPacketClickWindow.getActionNumber(), true));
                this.playerEntity.isChangingQuantityOnly = true;
                this.playerEntity.openContainer.detectAndSendChanges();
                this.playerEntity.updateHeldItem();
                this.playerEntity.isChangingQuantityOnly = false;
            }
            else {
                this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, c0EPacketClickWindow.getActionNumber());
                this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(c0EPacketClickWindow.getWindowId(), c0EPacketClickWindow.getActionNumber(), false));
                this.playerEntity.openContainer.setCanCraft(this.playerEntity, false);
                final ArrayList arrayList2 = Lists.newArrayList();
                while (0 < this.playerEntity.openContainer.inventorySlots.size()) {
                    arrayList2.add(this.playerEntity.openContainer.inventorySlots.get(0).getStack());
                    int n2 = 0;
                    ++n2;
                }
                this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, arrayList2);
            }
        }
    }
    
    @Override
    public void handleAnimation(final C0APacketAnimation c0APacketAnimation) {
        PacketThreadUtil.checkThreadAndEnqueue(c0APacketAnimation, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.markPlayerActive();
        this.playerEntity.swingItem();
    }
    
    private void handleSlashCommand(final String s) {
        this.serverController.getCommandManager().executeCommand(this.playerEntity, s);
    }
    
    @Override
    public void processClientSettings(final C15PacketClientSettings c15PacketClientSettings) {
        PacketThreadUtil.checkThreadAndEnqueue(c15PacketClientSettings, this, this.playerEntity.getServerForPlayer());
        this.playerEntity.handleClientSettings(c15PacketClientSettings);
    }
    
    @Override
    public void processCreativeInventoryAction(final C10PacketCreativeInventoryAction c10PacketCreativeInventoryAction) {
        PacketThreadUtil.checkThreadAndEnqueue(c10PacketCreativeInventoryAction, this, this.playerEntity.getServerForPlayer());
        if (this.playerEntity.theItemInWorldManager.isCreative()) {
            final boolean b = c10PacketCreativeInventoryAction.getSlotId() < 0;
            final ItemStack stack = c10PacketCreativeInventoryAction.getStack();
            if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag", 10)) {
                final NBTTagCompound compoundTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
                if (compoundTag.hasKey("x") && compoundTag.hasKey("y") && compoundTag.hasKey("z")) {
                    final TileEntity tileEntity = this.playerEntity.worldObj.getTileEntity(new BlockPos(compoundTag.getInteger("x"), compoundTag.getInteger("y"), compoundTag.getInteger("z")));
                    if (tileEntity != null) {
                        final NBTTagCompound nbtTagCompound = new NBTTagCompound();
                        tileEntity.writeToNBT(nbtTagCompound);
                        nbtTagCompound.removeTag("x");
                        nbtTagCompound.removeTag("y");
                        nbtTagCompound.removeTag("z");
                        stack.setTagInfo("BlockEntityTag", nbtTagCompound);
                    }
                }
            }
            final boolean b2 = c10PacketCreativeInventoryAction.getSlotId() >= 1 && c10PacketCreativeInventoryAction.getSlotId() < 36 + InventoryPlayer.getHotbarSize();
            final boolean b3 = stack == null || stack.getItem() != null;
            final boolean b4 = stack == null || (stack.getMetadata() >= 0 && stack.stackSize <= 64 && stack.stackSize > 0);
            if (b2 && b3 && b4) {
                if (stack == null) {
                    this.playerEntity.inventoryContainer.putStackInSlot(c10PacketCreativeInventoryAction.getSlotId(), null);
                }
                else {
                    this.playerEntity.inventoryContainer.putStackInSlot(c10PacketCreativeInventoryAction.getSlotId(), stack);
                }
                this.playerEntity.inventoryContainer.setCanCraft(this.playerEntity, true);
            }
            else if (b && b3 && b4 && this.itemDropThreshold < 200) {
                this.itemDropThreshold += 20;
                final EntityItem dropPlayerItemWithRandomChoice = this.playerEntity.dropPlayerItemWithRandomChoice(stack, true);
                if (dropPlayerItemWithRandomChoice != null) {
                    dropPlayerItemWithRandomChoice.setAgeToCreativeDespawnTime();
                }
            }
        }
    }
    
    public void setPlayerLocation(final double n, final double n2, final double n3, final float n4, final float n5) {
        this.setPlayerLocation(n, n2, n3, n4, n5, Collections.emptySet());
    }
    
    @Override
    public void onDisconnect(final IChatComponent chatComponent) {
        NetHandlerPlayServer.logger.info(this.playerEntity.getName() + " lost connection: " + chatComponent);
        this.serverController.refreshStatusNextTick();
        final ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.getDisplayName() });
        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
        this.serverController.getConfigurationManager().sendChatMsg(chatComponentTranslation);
        this.playerEntity.mountEntityAndWakeUp();
        this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
        if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
            NetHandlerPlayServer.logger.info("Stopping singleplayer server as player logged out");
            this.serverController.initiateShutdown();
        }
    }
    
    @Override
    public void processHeldItemChange(final C09PacketHeldItemChange c09PacketHeldItemChange) {
        PacketThreadUtil.checkThreadAndEnqueue(c09PacketHeldItemChange, this, this.playerEntity.getServerForPlayer());
        if (c09PacketHeldItemChange.getSlotId() >= 0 && c09PacketHeldItemChange.getSlotId() < InventoryPlayer.getHotbarSize()) {
            this.playerEntity.inventory.currentItem = c09PacketHeldItemChange.getSlotId();
            this.playerEntity.markPlayerActive();
        }
        else {
            NetHandlerPlayServer.logger.warn(this.playerEntity.getName() + " tried to set an invalid carried item");
        }
    }
    
    @Override
    public void processPlayerDigging(final C07PacketPlayerDigging c07PacketPlayerDigging) {
        PacketThreadUtil.checkThreadAndEnqueue(c07PacketPlayerDigging, this, this.playerEntity.getServerForPlayer());
        final WorldServer worldServerForDimension = this.serverController.worldServerForDimension(this.playerEntity.dimension);
        final BlockPos position = c07PacketPlayerDigging.getPosition();
        this.playerEntity.markPlayerActive();
        switch (NetHandlerPlayServer$4.$SwitchMap$net$minecraft$network$play$client$C07PacketPlayerDigging$Action[c07PacketPlayerDigging.getStatus().ordinal()]) {
            case 1: {
                if (!this.playerEntity.isSpectator()) {
                    this.playerEntity.dropOneItem(false);
                }
            }
            case 2: {
                if (!this.playerEntity.isSpectator()) {
                    this.playerEntity.dropOneItem(true);
                }
            }
            case 3: {
                this.playerEntity.stopUsingItem();
            }
            case 4:
            case 5:
            case 6: {
                final double n = this.playerEntity.posX - (position.getX() + 0.5);
                final double n2 = this.playerEntity.posY - (position.getY() + 0.5) + 1.5;
                final double n3 = this.playerEntity.posZ - (position.getZ() + 0.5);
                if (n * n + n2 * n2 + n3 * n3 > 36.0) {
                    return;
                }
                if (position.getY() >= this.serverController.getBuildLimit()) {
                    return;
                }
                if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
                    if (!this.serverController.isBlockProtected(worldServerForDimension, position, this.playerEntity) && worldServerForDimension.getWorldBorder().contains(position)) {
                        this.playerEntity.theItemInWorldManager.onBlockClicked(position, c07PacketPlayerDigging.getFacing());
                    }
                    else {
                        this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, position));
                    }
                }
                else {
                    if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                        this.playerEntity.theItemInWorldManager.blockRemoving(position);
                    }
                    else if (c07PacketPlayerDigging.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                        this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
                    }
                    if (worldServerForDimension.getBlockState(position).getBlock().getMaterial() != Material.air) {
                        this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(worldServerForDimension, position));
                    }
                }
            }
            default: {
                throw new IllegalArgumentException("Invalid player action");
            }
        }
    }
    
    @Override
    public void processConfirmTransaction(final C0FPacketConfirmTransaction c0FPacketConfirmTransaction) {
        PacketThreadUtil.checkThreadAndEnqueue(c0FPacketConfirmTransaction, this, this.playerEntity.getServerForPlayer());
        final Short n = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
        if (n != null && c0FPacketConfirmTransaction.getUid() == n && this.playerEntity.openContainer.windowId == c0FPacketConfirmTransaction.getWindowId() && !this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.isSpectator()) {
            this.playerEntity.openContainer.setCanCraft(this.playerEntity, true);
        }
    }
    
    @Override
    public void processUseEntity(final C02PacketUseEntity c02PacketUseEntity) {
        PacketThreadUtil.checkThreadAndEnqueue(c02PacketUseEntity, this, this.playerEntity.getServerForPlayer());
        final Entity entityFromWorld = c02PacketUseEntity.getEntityFromWorld(this.serverController.worldServerForDimension(this.playerEntity.dimension));
        this.playerEntity.markPlayerActive();
        if (entityFromWorld != null) {
            final boolean canEntityBeSeen = this.playerEntity.canEntityBeSeen(entityFromWorld);
            double n = 36.0;
            if (!canEntityBeSeen) {
                n = 9.0;
            }
            if (this.playerEntity.getDistanceSqToEntity(entityFromWorld) < n) {
                if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.INTERACT) {
                    this.playerEntity.interactWith(entityFromWorld);
                }
                else if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
                    entityFromWorld.interactAt(this.playerEntity, c02PacketUseEntity.getHitVec());
                }
                else if (c02PacketUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                    if (entityFromWorld instanceof EntityItem || entityFromWorld instanceof EntityXPOrb || entityFromWorld instanceof EntityArrow || entityFromWorld == this.playerEntity) {
                        this.kickPlayerFromServer("Attempting to attack an invalid entity");
                        this.serverController.logWarning("Player " + this.playerEntity.getName() + " tried to attack an invalid entity");
                        return;
                    }
                    this.playerEntity.attackTargetEntityWithCurrentItem(entityFromWorld);
                }
            }
        }
    }
}
