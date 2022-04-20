package net.minecraft.entity.projectile;

import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityFishHook extends Entity
{
    private int ticksInGround;
    private boolean inGround;
    private double fishY;
    private static final List TREASURE;
    private double fishX;
    private int yTile;
    private Block inTile;
    private double clientMotionZ;
    private int zTile;
    private float fishApproachAngle;
    private double fishYaw;
    private int ticksCatchableDelay;
    private double clientMotionX;
    private int ticksInAir;
    public int shake;
    private static final List FISH;
    private int fishPosRotationIncrements;
    public EntityPlayer angler;
    private static final List JUNK;
    private double fishZ;
    public Entity caughtEntity;
    private double fishPitch;
    private int xTile;
    private double clientMotionY;
    private int ticksCatchable;
    private int ticksCaughtDelay;
    
    @Override
    public void setPositionAndRotation2(final double fishX, final double fishY, final double fishZ, final float n, final float n2, final int fishPosRotationIncrements, final boolean b) {
        this.fishX = fishX;
        this.fishY = fishY;
        this.fishZ = fishZ;
        this.fishYaw = n;
        this.fishPitch = n2;
        this.fishPosRotationIncrements = fishPosRotationIncrements;
        this.motionX = this.clientMotionX;
        this.motionY = this.clientMotionY;
        this.motionZ = this.clientMotionZ;
    }
    
    static {
        JUNK = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10).setMaxDamagePercent(0.9f), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack(Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2).setMaxDamagePercent(0.9f), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, EnumDyeColor.BLACK.getDyeDamage()), 1), new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10));
        TREASURE = Arrays.asList(new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), new WeightedRandomFishable(new ItemStack(Items.bow), 1).setMaxDamagePercent(0.25f).setEnchantable(), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1).setMaxDamagePercent(0.25f).setEnchantable(), new WeightedRandomFishable(new ItemStack(Items.book), 1).setEnchantable());
        FISH = Arrays.asList(new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getMetadata()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.getMetadata()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.getMetadata()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()), 13));
    }
    
    public void handleHookCasting(double motionX, double motionY, double motionZ, final float n, final float n2) {
        final float sqrt_double = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        motionX /= sqrt_double;
        motionY /= sqrt_double;
        motionZ /= sqrt_double;
        motionX += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionY += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionZ += this.rand.nextGaussian() * 0.007499999832361937 * n2;
        motionX *= n;
        motionY *= n;
        motionZ *= n;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
        final float sqrt_double2 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        final float n3 = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0 / 3.141592653589793);
        this.rotationYaw = n3;
        this.prevRotationYaw = n3;
        final float n4 = (float)(MathHelper.func_181159_b(motionY, sqrt_double2) * 180.0 / 3.141592653589793);
        this.rotationPitch = n4;
        this.prevRotationPitch = n4;
        this.ticksInGround = 0;
    }
    
    public EntityFishHook(final World world, final double n, final double n2, final double n3, final EntityPlayer angler) {
        this(world);
        this.setPosition(n, n2, n3);
        this.ignoreFrustumCheck = true;
        this.angler = angler;
        angler.fishEntity = this;
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setShort("xTile", (short)this.xTile);
        nbtTagCompound.setShort("yTile", (short)this.yTile);
        nbtTagCompound.setShort("zTile", (short)this.zTile);
        final ResourceLocation resourceLocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
        nbtTagCompound.setString("inTile", (resourceLocation == null) ? "" : resourceLocation.toString());
        nbtTagCompound.setByte("shake", (byte)this.shake);
        nbtTagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    }
    
    public static List func_174855_j() {
        return EntityFishHook.FISH;
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        this.xTile = nbtTagCompound.getShort("xTile");
        this.yTile = nbtTagCompound.getShort("yTile");
        this.zTile = nbtTagCompound.getShort("zTile");
        if (nbtTagCompound.hasKey("inTile", 8)) {
            this.inTile = Block.getBlockFromName(nbtTagCompound.getString("inTile"));
        }
        else {
            this.inTile = Block.getBlockById(nbtTagCompound.getByte("inTile") & 0xFF);
        }
        this.shake = (nbtTagCompound.getByte("shake") & 0xFF);
        this.inGround = (nbtTagCompound.getByte("inGround") == 1);
    }
    
    @Override
    public void onUpdate() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/minecraft/entity/Entity.onUpdate:()V
        //     4: aload_0        
        //     5: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //     8: ifle            169
        //    11: aload_0        
        //    12: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //    15: aload_0        
        //    16: getfield        net/minecraft/entity/projectile/EntityFishHook.fishX:D
        //    19: aload_0        
        //    20: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //    23: dsub           
        //    24: aload_0        
        //    25: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //    28: i2d            
        //    29: ddiv           
        //    30: dadd           
        //    31: dstore_1       
        //    32: aload_0        
        //    33: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //    36: aload_0        
        //    37: getfield        net/minecraft/entity/projectile/EntityFishHook.fishY:D
        //    40: aload_0        
        //    41: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //    44: dsub           
        //    45: aload_0        
        //    46: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //    49: i2d            
        //    50: ddiv           
        //    51: dadd           
        //    52: dstore_3       
        //    53: aload_0        
        //    54: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //    57: aload_0        
        //    58: getfield        net/minecraft/entity/projectile/EntityFishHook.fishZ:D
        //    61: aload_0        
        //    62: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //    65: dsub           
        //    66: aload_0        
        //    67: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //    70: i2d            
        //    71: ddiv           
        //    72: dadd           
        //    73: dstore          5
        //    75: aload_0        
        //    76: getfield        net/minecraft/entity/projectile/EntityFishHook.fishYaw:D
        //    79: aload_0        
        //    80: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //    83: f2d            
        //    84: dsub           
        //    85: invokestatic    net/minecraft/util/MathHelper.wrapAngleTo180_double:(D)D
        //    88: dstore          7
        //    90: aload_0        
        //    91: aload_0        
        //    92: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //    95: f2d            
        //    96: dload           7
        //    98: aload_0        
        //    99: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //   102: i2d            
        //   103: ddiv           
        //   104: dadd           
        //   105: d2f            
        //   106: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //   109: aload_0        
        //   110: aload_0        
        //   111: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   114: f2d            
        //   115: aload_0        
        //   116: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPitch:D
        //   119: aload_0        
        //   120: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   123: f2d            
        //   124: dsub           
        //   125: aload_0        
        //   126: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //   129: i2d            
        //   130: ddiv           
        //   131: dadd           
        //   132: d2f            
        //   133: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   136: aload_0        
        //   137: dup            
        //   138: getfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //   141: iconst_1       
        //   142: isub           
        //   143: putfield        net/minecraft/entity/projectile/EntityFishHook.fishPosRotationIncrements:I
        //   146: aload_0        
        //   147: dload_1        
        //   148: dload_3        
        //   149: dload           5
        //   151: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setPosition:(DDD)V
        //   154: aload_0        
        //   155: aload_0        
        //   156: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //   159: aload_0        
        //   160: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   163: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setRotation:(FF)V
        //   166: goto            2403
        //   169: aload_0        
        //   170: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //   173: getfield        net/minecraft/world/World.isRemote:Z
        //   176: ifne            323
        //   179: aload_0        
        //   180: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   183: invokevirtual   net/minecraft/entity/player/EntityPlayer.getCurrentEquippedItem:()Lnet/minecraft/item/ItemStack;
        //   186: astore_1       
        //   187: aload_0        
        //   188: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   191: getfield        net/minecraft/entity/player/EntityPlayer.isDead:Z
        //   194: ifne            236
        //   197: aload_0        
        //   198: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   201: invokevirtual   net/minecraft/entity/player/EntityPlayer.isEntityAlive:()Z
        //   204: ifeq            236
        //   207: aload_1        
        //   208: ifnull          236
        //   211: aload_1        
        //   212: invokevirtual   net/minecraft/item/ItemStack.getItem:()Lnet/minecraft/item/Item;
        //   215: getstatic       net/minecraft/init/Items.fishing_rod:Lnet/minecraft/item/ItemFishingRod;
        //   218: if_acmpne       236
        //   221: aload_0        
        //   222: aload_0        
        //   223: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   226: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getDistanceSqToEntity:(Lnet/minecraft/entity/Entity;)D
        //   229: ldc2_w          1024.0
        //   232: dcmpl          
        //   233: ifle            249
        //   236: aload_0        
        //   237: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setDead:()V
        //   240: aload_0        
        //   241: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   244: aconst_null    
        //   245: putfield        net/minecraft/entity/player/EntityPlayer.fishEntity:Lnet/minecraft/entity/projectile/EntityFishHook;
        //   248: return         
        //   249: aload_0        
        //   250: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   253: ifnull          323
        //   256: aload_0        
        //   257: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   260: getfield        net/minecraft/entity/Entity.isDead:Z
        //   263: ifne            318
        //   266: aload_0        
        //   267: aload_0        
        //   268: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   271: getfield        net/minecraft/entity/Entity.posX:D
        //   274: putfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   277: aload_0        
        //   278: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   281: getfield        net/minecraft/entity/Entity.height:F
        //   284: f2d            
        //   285: dstore_2       
        //   286: aload_0        
        //   287: aload_0        
        //   288: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   291: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   294: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //   297: dload_2        
        //   298: ldc2_w          0.8
        //   301: dmul           
        //   302: dadd           
        //   303: putfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   306: aload_0        
        //   307: aload_0        
        //   308: getfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   311: getfield        net/minecraft/entity/Entity.posZ:D
        //   314: putfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   317: return         
        //   318: aload_0        
        //   319: aconst_null    
        //   320: putfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   323: aload_0        
        //   324: getfield        net/minecraft/entity/projectile/EntityFishHook.shake:I
        //   327: ifle            340
        //   330: aload_0        
        //   331: dup            
        //   332: getfield        net/minecraft/entity/projectile/EntityFishHook.shake:I
        //   335: iconst_1       
        //   336: isub           
        //   337: putfield        net/minecraft/entity/projectile/EntityFishHook.shake:I
        //   340: aload_0        
        //   341: getfield        net/minecraft/entity/projectile/EntityFishHook.inGround:Z
        //   344: ifeq            491
        //   347: aload_0        
        //   348: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //   351: new             Lnet/minecraft/util/BlockPos;
        //   354: dup            
        //   355: aload_0        
        //   356: getfield        net/minecraft/entity/projectile/EntityFishHook.xTile:I
        //   359: aload_0        
        //   360: getfield        net/minecraft/entity/projectile/EntityFishHook.yTile:I
        //   363: aload_0        
        //   364: getfield        net/minecraft/entity/projectile/EntityFishHook.zTile:I
        //   367: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //   370: invokevirtual   net/minecraft/world/World.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //   373: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //   378: aload_0        
        //   379: getfield        net/minecraft/entity/projectile/EntityFishHook.inTile:Lnet/minecraft/block/Block;
        //   382: if_acmpne       410
        //   385: aload_0        
        //   386: dup            
        //   387: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksInGround:I
        //   390: iconst_1       
        //   391: iadd           
        //   392: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksInGround:I
        //   395: aload_0        
        //   396: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksInGround:I
        //   399: sipush          1200
        //   402: if_icmpne       409
        //   405: aload_0        
        //   406: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setDead:()V
        //   409: return         
        //   410: aload_0        
        //   411: iconst_0       
        //   412: putfield        net/minecraft/entity/projectile/EntityFishHook.inGround:Z
        //   415: aload_0        
        //   416: dup            
        //   417: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   420: aload_0        
        //   421: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //   424: invokevirtual   java/util/Random.nextFloat:()F
        //   427: ldc_w           0.2
        //   430: fmul           
        //   431: f2d            
        //   432: dmul           
        //   433: putfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   436: aload_0        
        //   437: dup            
        //   438: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   441: aload_0        
        //   442: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //   445: invokevirtual   java/util/Random.nextFloat:()F
        //   448: ldc_w           0.2
        //   451: fmul           
        //   452: f2d            
        //   453: dmul           
        //   454: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   457: aload_0        
        //   458: dup            
        //   459: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   462: aload_0        
        //   463: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //   466: invokevirtual   java/util/Random.nextFloat:()F
        //   469: ldc_w           0.2
        //   472: fmul           
        //   473: f2d            
        //   474: dmul           
        //   475: putfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   478: aload_0        
        //   479: iconst_0       
        //   480: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksInGround:I
        //   483: aload_0        
        //   484: iconst_0       
        //   485: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksInAir:I
        //   488: goto            501
        //   491: aload_0        
        //   492: dup            
        //   493: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksInAir:I
        //   496: iconst_1       
        //   497: iadd           
        //   498: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksInAir:I
        //   501: new             Lnet/minecraft/util/Vec3;
        //   504: dup            
        //   505: aload_0        
        //   506: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   509: aload_0        
        //   510: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   513: aload_0        
        //   514: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   517: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   520: astore_1       
        //   521: new             Lnet/minecraft/util/Vec3;
        //   524: dup            
        //   525: aload_0        
        //   526: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   529: aload_0        
        //   530: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   533: dadd           
        //   534: aload_0        
        //   535: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   538: aload_0        
        //   539: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   542: dadd           
        //   543: aload_0        
        //   544: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   547: aload_0        
        //   548: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   551: dadd           
        //   552: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   555: astore_2       
        //   556: aload_0        
        //   557: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //   560: aload_1        
        //   561: aload_2        
        //   562: invokevirtual   net/minecraft/world/World.rayTraceBlocks:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   565: astore_3       
        //   566: new             Lnet/minecraft/util/Vec3;
        //   569: dup            
        //   570: aload_0        
        //   571: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   574: aload_0        
        //   575: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   578: aload_0        
        //   579: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   582: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   585: astore_1       
        //   586: new             Lnet/minecraft/util/Vec3;
        //   589: dup            
        //   590: aload_0        
        //   591: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //   594: aload_0        
        //   595: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   598: dadd           
        //   599: aload_0        
        //   600: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //   603: aload_0        
        //   604: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   607: dadd           
        //   608: aload_0        
        //   609: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //   612: aload_0        
        //   613: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   616: dadd           
        //   617: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   620: astore_2       
        //   621: aload_3        
        //   622: ifnull          654
        //   625: new             Lnet/minecraft/util/Vec3;
        //   628: dup            
        //   629: aload_3        
        //   630: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   633: getfield        net/minecraft/util/Vec3.xCoord:D
        //   636: aload_3        
        //   637: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   640: getfield        net/minecraft/util/Vec3.yCoord:D
        //   643: aload_3        
        //   644: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   647: getfield        net/minecraft/util/Vec3.zCoord:D
        //   650: invokespecial   net/minecraft/util/Vec3.<init>:(DDD)V
        //   653: astore_2       
        //   654: aconst_null    
        //   655: astore          4
        //   657: aload_0        
        //   658: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //   661: aload_0        
        //   662: aload_0        
        //   663: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   666: aload_0        
        //   667: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   670: aload_0        
        //   671: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   674: aload_0        
        //   675: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   678: invokevirtual   net/minecraft/util/AxisAlignedBB.addCoord:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   681: dconst_1       
        //   682: dconst_1       
        //   683: dconst_1       
        //   684: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   687: invokevirtual   net/minecraft/world/World.getEntitiesWithinAABBExcludingEntity:(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;
        //   690: astore          5
        //   692: dconst_0       
        //   693: dstore          6
        //   695: iconst_0       
        //   696: aload           5
        //   698: invokeinterface java/util/List.size:()I
        //   703: if_icmpge       822
        //   706: aload           5
        //   708: iconst_0       
        //   709: invokeinterface java/util/List.get:(I)Ljava/lang/Object;
        //   714: checkcast       Lnet/minecraft/entity/Entity;
        //   717: astore          9
        //   719: aload           9
        //   721: invokevirtual   net/minecraft/entity/Entity.canBeCollidedWith:()Z
        //   724: ifeq            816
        //   727: aload           9
        //   729: aload_0        
        //   730: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   733: if_acmpne       744
        //   736: aload_0        
        //   737: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksInAir:I
        //   740: iconst_5       
        //   741: if_icmplt       816
        //   744: ldc_w           0.3
        //   747: fstore          10
        //   749: aload           9
        //   751: invokevirtual   net/minecraft/entity/Entity.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //   754: fload           10
        //   756: f2d            
        //   757: fload           10
        //   759: f2d            
        //   760: fload           10
        //   762: f2d            
        //   763: invokevirtual   net/minecraft/util/AxisAlignedBB.expand:(DDD)Lnet/minecraft/util/AxisAlignedBB;
        //   766: astore          11
        //   768: aload           11
        //   770: aload_1        
        //   771: aload_2        
        //   772: invokevirtual   net/minecraft/util/AxisAlignedBB.calculateIntercept:(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;
        //   775: astore          12
        //   777: aload           12
        //   779: ifnull          816
        //   782: aload_1        
        //   783: aload           12
        //   785: getfield        net/minecraft/util/MovingObjectPosition.hitVec:Lnet/minecraft/util/Vec3;
        //   788: invokevirtual   net/minecraft/util/Vec3.squareDistanceTo:(Lnet/minecraft/util/Vec3;)D
        //   791: dstore          13
        //   793: dload           13
        //   795: dload           6
        //   797: dcmpg          
        //   798: iflt            808
        //   801: dload           6
        //   803: dconst_0       
        //   804: dcmpl          
        //   805: ifne            816
        //   808: aload           9
        //   810: astore          4
        //   812: dload           13
        //   814: dstore          6
        //   816: iinc            8, 1
        //   819: goto            695
        //   822: aload           4
        //   824: ifnull          837
        //   827: new             Lnet/minecraft/util/MovingObjectPosition;
        //   830: dup            
        //   831: aload           4
        //   833: invokespecial   net/minecraft/util/MovingObjectPosition.<init>:(Lnet/minecraft/entity/Entity;)V
        //   836: astore_3       
        //   837: aload_3        
        //   838: ifnull          883
        //   841: aload_3        
        //   842: getfield        net/minecraft/util/MovingObjectPosition.entityHit:Lnet/minecraft/entity/Entity;
        //   845: ifnull          878
        //   848: aload_3        
        //   849: getfield        net/minecraft/util/MovingObjectPosition.entityHit:Lnet/minecraft/entity/Entity;
        //   852: aload_0        
        //   853: aload_0        
        //   854: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //   857: invokestatic    net/minecraft/util/DamageSource.causeThrownDamage:(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/DamageSource;
        //   860: fconst_0       
        //   861: invokevirtual   net/minecraft/entity/Entity.attackEntityFrom:(Lnet/minecraft/util/DamageSource;F)Z
        //   864: ifeq            883
        //   867: aload_0        
        //   868: aload_3        
        //   869: getfield        net/minecraft/util/MovingObjectPosition.entityHit:Lnet/minecraft/entity/Entity;
        //   872: putfield        net/minecraft/entity/projectile/EntityFishHook.caughtEntity:Lnet/minecraft/entity/Entity;
        //   875: goto            883
        //   878: aload_0        
        //   879: iconst_1       
        //   880: putfield        net/minecraft/entity/projectile/EntityFishHook.inGround:Z
        //   883: aload_0        
        //   884: getfield        net/minecraft/entity/projectile/EntityFishHook.inGround:Z
        //   887: ifne            2403
        //   890: aload_0        
        //   891: aload_0        
        //   892: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   895: aload_0        
        //   896: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   899: aload_0        
        //   900: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   903: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.moveEntity:(DDD)V
        //   906: aload_0        
        //   907: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   910: aload_0        
        //   911: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   914: dmul           
        //   915: aload_0        
        //   916: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   919: aload_0        
        //   920: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   923: dmul           
        //   924: dadd           
        //   925: invokestatic    net/minecraft/util/MathHelper.sqrt_double:(D)F
        //   928: fstore          8
        //   930: aload_0        
        //   931: aload_0        
        //   932: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //   935: aload_0        
        //   936: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //   939: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   942: ldc2_w          180.0
        //   945: dmul           
        //   946: ldc2_w          3.141592653589793
        //   949: ddiv           
        //   950: d2f            
        //   951: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //   954: aload_0        
        //   955: aload_0        
        //   956: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //   959: fload           8
        //   961: f2d            
        //   962: invokestatic    net/minecraft/util/MathHelper.func_181159_b:(DD)D
        //   965: ldc2_w          180.0
        //   968: dmul           
        //   969: ldc2_w          3.141592653589793
        //   972: ddiv           
        //   973: d2f            
        //   974: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   977: aload_0        
        //   978: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //   981: aload_0        
        //   982: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //   985: fsub           
        //   986: ldc_w           -180.0
        //   989: fcmpg          
        //   990: ifge            1008
        //   993: aload_0        
        //   994: dup            
        //   995: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //   998: ldc_w           360.0
        //  1001: fsub           
        //  1002: putfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1005: goto            977
        //  1008: aload_0        
        //  1009: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //  1012: aload_0        
        //  1013: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1016: fsub           
        //  1017: ldc_w           180.0
        //  1020: fcmpl          
        //  1021: iflt            1039
        //  1024: aload_0        
        //  1025: dup            
        //  1026: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1029: ldc_w           360.0
        //  1032: fadd           
        //  1033: putfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1036: goto            1008
        //  1039: aload_0        
        //  1040: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //  1043: aload_0        
        //  1044: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1047: fsub           
        //  1048: ldc_w           -180.0
        //  1051: fcmpg          
        //  1052: ifge            1070
        //  1055: aload_0        
        //  1056: dup            
        //  1057: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1060: ldc_w           360.0
        //  1063: fsub           
        //  1064: putfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1067: goto            1039
        //  1070: aload_0        
        //  1071: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //  1074: aload_0        
        //  1075: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1078: fsub           
        //  1079: ldc_w           180.0
        //  1082: fcmpl          
        //  1083: iflt            1101
        //  1086: aload_0        
        //  1087: dup            
        //  1088: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1091: ldc_w           360.0
        //  1094: fadd           
        //  1095: putfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1098: goto            1070
        //  1101: aload_0        
        //  1102: aload_0        
        //  1103: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1106: aload_0        
        //  1107: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //  1110: aload_0        
        //  1111: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationPitch:F
        //  1114: fsub           
        //  1115: ldc_w           0.2
        //  1118: fmul           
        //  1119: fadd           
        //  1120: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationPitch:F
        //  1123: aload_0        
        //  1124: aload_0        
        //  1125: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1128: aload_0        
        //  1129: getfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //  1132: aload_0        
        //  1133: getfield        net/minecraft/entity/projectile/EntityFishHook.prevRotationYaw:F
        //  1136: fsub           
        //  1137: ldc_w           0.2
        //  1140: fmul           
        //  1141: fadd           
        //  1142: putfield        net/minecraft/entity/projectile/EntityFishHook.rotationYaw:F
        //  1145: ldc_w           0.92
        //  1148: fstore          9
        //  1150: aload_0        
        //  1151: getfield        net/minecraft/entity/projectile/EntityFishHook.onGround:Z
        //  1154: ifne            1164
        //  1157: aload_0        
        //  1158: getfield        net/minecraft/entity/projectile/EntityFishHook.isCollidedHorizontally:Z
        //  1161: ifeq            1169
        //  1164: ldc_w           0.5
        //  1167: fstore          9
        //  1169: dconst_0       
        //  1170: dstore          11
        //  1172: aload_0        
        //  1173: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1176: astore          14
        //  1178: aload           14
        //  1180: getfield        net/minecraft/util/AxisAlignedBB.maxY:D
        //  1183: aload           14
        //  1185: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1188: dsub           
        //  1189: dstore          15
        //  1191: aload           14
        //  1193: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1196: dload           15
        //  1198: iconst_0       
        //  1199: i2d            
        //  1200: dmul           
        //  1201: iconst_5       
        //  1202: i2d            
        //  1203: ddiv           
        //  1204: dadd           
        //  1205: dstore          17
        //  1207: aload           14
        //  1209: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1212: dload           15
        //  1214: iconst_1       
        //  1215: i2d            
        //  1216: dmul           
        //  1217: iconst_5       
        //  1218: i2d            
        //  1219: ddiv           
        //  1220: dadd           
        //  1221: dstore          19
        //  1223: new             Lnet/minecraft/util/AxisAlignedBB;
        //  1226: dup            
        //  1227: aload           14
        //  1229: getfield        net/minecraft/util/AxisAlignedBB.minX:D
        //  1232: dload           17
        //  1234: aload           14
        //  1236: getfield        net/minecraft/util/AxisAlignedBB.minZ:D
        //  1239: aload           14
        //  1241: getfield        net/minecraft/util/AxisAlignedBB.maxX:D
        //  1244: dload           19
        //  1246: aload           14
        //  1248: getfield        net/minecraft/util/AxisAlignedBB.maxZ:D
        //  1251: invokespecial   net/minecraft/util/AxisAlignedBB.<init>:(DDDDDD)V
        //  1254: astore          21
        //  1256: aload_0        
        //  1257: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1260: aload           21
        //  1262: getstatic       net/minecraft/block/material/Material.water:Lnet/minecraft/block/material/Material;
        //  1265: invokevirtual   net/minecraft/world/World.isAABBInMaterial:(Lnet/minecraft/util/AxisAlignedBB;Lnet/minecraft/block/material/Material;)Z
        //  1268: ifeq            1280
        //  1271: dload           11
        //  1273: dconst_1       
        //  1274: iconst_5       
        //  1275: i2d            
        //  1276: ddiv           
        //  1277: dadd           
        //  1278: dstore          11
        //  1280: iinc            13, 1
        //  1283: goto            1172
        //  1286: aload_0        
        //  1287: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1290: getfield        net/minecraft/world/World.isRemote:Z
        //  1293: ifne            2297
        //  1296: dload           11
        //  1298: dconst_0       
        //  1299: dcmpl          
        //  1300: ifle            2297
        //  1303: aload_0        
        //  1304: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1307: checkcast       Lnet/minecraft/world/WorldServer;
        //  1310: astore          13
        //  1312: new             Lnet/minecraft/util/BlockPos;
        //  1315: dup            
        //  1316: aload_0        
        //  1317: invokespecial   net/minecraft/util/BlockPos.<init>:(Lnet/minecraft/entity/Entity;)V
        //  1320: invokevirtual   net/minecraft/util/BlockPos.up:()Lnet/minecraft/util/BlockPos;
        //  1323: astore          15
        //  1325: aload_0        
        //  1326: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1329: invokevirtual   java/util/Random.nextFloat:()F
        //  1332: ldc             0.25
        //  1334: fcmpg          
        //  1335: ifge            1350
        //  1338: aload_0        
        //  1339: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1342: aload           15
        //  1344: invokevirtual   net/minecraft/world/World.canLightningStrike:(Lnet/minecraft/util/BlockPos;)Z
        //  1347: ifeq            1350
        //  1350: aload_0        
        //  1351: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1354: invokevirtual   java/util/Random.nextFloat:()F
        //  1357: ldc_w           0.5
        //  1360: fcmpg          
        //  1361: ifge            1379
        //  1364: aload_0        
        //  1365: getfield        net/minecraft/entity/projectile/EntityFishHook.worldObj:Lnet/minecraft/world/World;
        //  1368: aload           15
        //  1370: invokevirtual   net/minecraft/world/World.canSeeSky:(Lnet/minecraft/util/BlockPos;)Z
        //  1373: ifne            1379
        //  1376: iinc            14, -1
        //  1379: aload_0        
        //  1380: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1383: ifle            1416
        //  1386: aload_0        
        //  1387: dup            
        //  1388: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1391: iconst_1       
        //  1392: isub           
        //  1393: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1396: aload_0        
        //  1397: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1400: ifgt            2253
        //  1403: aload_0        
        //  1404: iconst_0       
        //  1405: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1408: aload_0        
        //  1409: iconst_0       
        //  1410: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1413: goto            2253
        //  1416: aload_0        
        //  1417: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1420: ifle            1882
        //  1423: aload_0        
        //  1424: dup            
        //  1425: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1428: iconst_2       
        //  1429: isub           
        //  1430: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1433: aload_0        
        //  1434: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1437: ifgt            1611
        //  1440: aload_0        
        //  1441: dup            
        //  1442: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  1445: ldc2_w          0.20000000298023224
        //  1448: dsub           
        //  1449: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  1452: aload_0        
        //  1453: ldc_w           "random.splash"
        //  1456: ldc             0.25
        //  1458: fconst_1       
        //  1459: aload_0        
        //  1460: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1463: invokevirtual   java/util/Random.nextFloat:()F
        //  1466: aload_0        
        //  1467: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1470: invokevirtual   java/util/Random.nextFloat:()F
        //  1473: fsub           
        //  1474: ldc_w           0.4
        //  1477: fmul           
        //  1478: fadd           
        //  1479: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.playSound:(Ljava/lang/String;FF)V
        //  1482: aload_0        
        //  1483: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1486: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1489: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  1492: i2f            
        //  1493: fstore          16
        //  1495: aload           13
        //  1497: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //  1500: aload_0        
        //  1501: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  1504: fload           16
        //  1506: fconst_1       
        //  1507: fadd           
        //  1508: f2d            
        //  1509: aload_0        
        //  1510: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  1513: fconst_1       
        //  1514: aload_0        
        //  1515: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1518: ldc_w           20.0
        //  1521: fmul           
        //  1522: fadd           
        //  1523: f2i            
        //  1524: aload_0        
        //  1525: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1528: f2d            
        //  1529: dconst_0       
        //  1530: aload_0        
        //  1531: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1534: f2d            
        //  1535: ldc2_w          0.20000000298023224
        //  1538: iconst_0       
        //  1539: newarray        I
        //  1541: invokevirtual   net/minecraft/world/WorldServer.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1544: aload           13
        //  1546: getstatic       net/minecraft/util/EnumParticleTypes.WATER_WAKE:Lnet/minecraft/util/EnumParticleTypes;
        //  1549: aload_0        
        //  1550: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  1553: fload           16
        //  1555: fconst_1       
        //  1556: fadd           
        //  1557: f2d            
        //  1558: aload_0        
        //  1559: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  1562: fconst_1       
        //  1563: aload_0        
        //  1564: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1567: ldc_w           20.0
        //  1570: fmul           
        //  1571: fadd           
        //  1572: f2i            
        //  1573: aload_0        
        //  1574: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1577: f2d            
        //  1578: dconst_0       
        //  1579: aload_0        
        //  1580: getfield        net/minecraft/entity/projectile/EntityFishHook.width:F
        //  1583: f2d            
        //  1584: ldc2_w          0.20000000298023224
        //  1587: iconst_0       
        //  1588: newarray        I
        //  1590: invokevirtual   net/minecraft/world/WorldServer.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1593: aload_0        
        //  1594: aload_0        
        //  1595: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1598: bipush          10
        //  1600: bipush          30
        //  1602: invokestatic    net/minecraft/util/MathHelper.getRandomIntegerInRange:(Ljava/util/Random;II)I
        //  1605: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  1608: goto            2253
        //  1611: aload_0        
        //  1612: aload_0        
        //  1613: getfield        net/minecraft/entity/projectile/EntityFishHook.fishApproachAngle:F
        //  1616: f2d            
        //  1617: aload_0        
        //  1618: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1621: invokevirtual   java/util/Random.nextGaussian:()D
        //  1624: ldc2_w          4.0
        //  1627: dmul           
        //  1628: dadd           
        //  1629: d2f            
        //  1630: putfield        net/minecraft/entity/projectile/EntityFishHook.fishApproachAngle:F
        //  1633: aload_0        
        //  1634: getfield        net/minecraft/entity/projectile/EntityFishHook.fishApproachAngle:F
        //  1637: ldc_w           0.017453292
        //  1640: fmul           
        //  1641: fstore          16
        //  1643: fload           16
        //  1645: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  1648: fstore          17
        //  1650: fload           16
        //  1652: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  1655: fstore          18
        //  1657: aload_0        
        //  1658: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  1661: fload           17
        //  1663: aload_0        
        //  1664: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1667: i2f            
        //  1668: fmul           
        //  1669: ldc_w           0.1
        //  1672: fmul           
        //  1673: f2d            
        //  1674: dadd           
        //  1675: dstore          19
        //  1677: aload_0        
        //  1678: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  1681: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  1684: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  1687: i2f            
        //  1688: fconst_1       
        //  1689: fadd           
        //  1690: f2d            
        //  1691: dstore          21
        //  1693: aload_0        
        //  1694: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  1697: fload           18
        //  1699: aload_0        
        //  1700: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  1703: i2f            
        //  1704: fmul           
        //  1705: ldc_w           0.1
        //  1708: fmul           
        //  1709: f2d            
        //  1710: dadd           
        //  1711: dstore          23
        //  1713: aload           13
        //  1715: new             Lnet/minecraft/util/BlockPos;
        //  1718: dup            
        //  1719: dload           19
        //  1721: d2i            
        //  1722: dload           21
        //  1724: d2i            
        //  1725: iconst_1       
        //  1726: isub           
        //  1727: dload           23
        //  1729: d2i            
        //  1730: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //  1733: invokevirtual   net/minecraft/world/WorldServer.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  1736: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  1741: astore          25
        //  1743: aload           25
        //  1745: getstatic       net/minecraft/init/Blocks.water:Lnet/minecraft/block/BlockStaticLiquid;
        //  1748: if_acmpeq       1759
        //  1751: aload           25
        //  1753: getstatic       net/minecraft/init/Blocks.flowing_water:Lnet/minecraft/block/BlockDynamicLiquid;
        //  1756: if_acmpne       1879
        //  1759: aload_0        
        //  1760: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1763: invokevirtual   java/util/Random.nextFloat:()F
        //  1766: ldc_w           0.15
        //  1769: fcmpg          
        //  1770: ifge            1805
        //  1773: aload           13
        //  1775: getstatic       net/minecraft/util/EnumParticleTypes.WATER_BUBBLE:Lnet/minecraft/util/EnumParticleTypes;
        //  1778: dload           19
        //  1780: dload           21
        //  1782: ldc2_w          0.10000000149011612
        //  1785: dsub           
        //  1786: dload           23
        //  1788: iconst_1       
        //  1789: fload           17
        //  1791: f2d            
        //  1792: ldc2_w          0.1
        //  1795: fload           18
        //  1797: f2d            
        //  1798: dconst_0       
        //  1799: iconst_0       
        //  1800: newarray        I
        //  1802: invokevirtual   net/minecraft/world/WorldServer.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1805: fload           17
        //  1807: ldc_w           0.04
        //  1810: fmul           
        //  1811: fstore          26
        //  1813: fload           18
        //  1815: ldc_w           0.04
        //  1818: fmul           
        //  1819: fstore          27
        //  1821: aload           13
        //  1823: getstatic       net/minecraft/util/EnumParticleTypes.WATER_WAKE:Lnet/minecraft/util/EnumParticleTypes;
        //  1826: dload           19
        //  1828: dload           21
        //  1830: dload           23
        //  1832: iconst_0       
        //  1833: fload           27
        //  1835: f2d            
        //  1836: ldc2_w          0.01
        //  1839: fload           26
        //  1841: fneg           
        //  1842: f2d            
        //  1843: dconst_1       
        //  1844: iconst_0       
        //  1845: newarray        I
        //  1847: invokevirtual   net/minecraft/world/WorldServer.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1850: aload           13
        //  1852: getstatic       net/minecraft/util/EnumParticleTypes.WATER_WAKE:Lnet/minecraft/util/EnumParticleTypes;
        //  1855: dload           19
        //  1857: dload           21
        //  1859: dload           23
        //  1861: iconst_0       
        //  1862: fload           27
        //  1864: fneg           
        //  1865: f2d            
        //  1866: ldc2_w          0.01
        //  1869: fload           26
        //  1871: f2d            
        //  1872: dconst_1       
        //  1873: iconst_0       
        //  1874: newarray        I
        //  1876: invokevirtual   net/minecraft/world/WorldServer.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  1879: goto            2253
        //  1882: aload_0        
        //  1883: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1886: ifle            2216
        //  1889: aload_0        
        //  1890: dup            
        //  1891: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1894: iconst_2       
        //  1895: isub           
        //  1896: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1899: ldc_w           0.15
        //  1902: fstore          16
        //  1904: aload_0        
        //  1905: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1908: bipush          20
        //  1910: if_icmpge       1935
        //  1913: fload           16
        //  1915: f2d            
        //  1916: bipush          20
        //  1918: aload_0        
        //  1919: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1922: isub           
        //  1923: i2d            
        //  1924: ldc2_w          0.05
        //  1927: dmul           
        //  1928: dadd           
        //  1929: d2f            
        //  1930: fstore          16
        //  1932: goto            1994
        //  1935: aload_0        
        //  1936: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1939: bipush          40
        //  1941: if_icmpge       1966
        //  1944: fload           16
        //  1946: f2d            
        //  1947: bipush          40
        //  1949: aload_0        
        //  1950: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1953: isub           
        //  1954: i2d            
        //  1955: ldc2_w          0.02
        //  1958: dmul           
        //  1959: dadd           
        //  1960: d2f            
        //  1961: fstore          16
        //  1963: goto            1994
        //  1966: aload_0        
        //  1967: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1970: bipush          60
        //  1972: if_icmpge       1994
        //  1975: fload           16
        //  1977: f2d            
        //  1978: bipush          60
        //  1980: aload_0        
        //  1981: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  1984: isub           
        //  1985: i2d            
        //  1986: ldc2_w          0.01
        //  1989: dmul           
        //  1990: dadd           
        //  1991: d2f            
        //  1992: fstore          16
        //  1994: aload_0        
        //  1995: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  1998: invokevirtual   java/util/Random.nextFloat:()F
        //  2001: fload           16
        //  2003: fcmpg          
        //  2004: ifge            2176
        //  2007: aload_0        
        //  2008: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2011: fconst_0       
        //  2012: ldc_w           360.0
        //  2015: invokestatic    net/minecraft/util/MathHelper.randomFloatClamp:(Ljava/util/Random;FF)F
        //  2018: ldc_w           0.017453292
        //  2021: fmul           
        //  2022: fstore          17
        //  2024: aload_0        
        //  2025: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2028: ldc_w           25.0
        //  2031: ldc_w           60.0
        //  2034: invokestatic    net/minecraft/util/MathHelper.randomFloatClamp:(Ljava/util/Random;FF)F
        //  2037: fstore          18
        //  2039: aload_0        
        //  2040: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  2043: fload           17
        //  2045: invokestatic    net/minecraft/util/MathHelper.sin:(F)F
        //  2048: fload           18
        //  2050: fmul           
        //  2051: ldc_w           0.1
        //  2054: fmul           
        //  2055: f2d            
        //  2056: dadd           
        //  2057: dstore          19
        //  2059: aload_0        
        //  2060: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.getEntityBoundingBox:()Lnet/minecraft/util/AxisAlignedBB;
        //  2063: getfield        net/minecraft/util/AxisAlignedBB.minY:D
        //  2066: invokestatic    net/minecraft/util/MathHelper.floor_double:(D)I
        //  2069: i2f            
        //  2070: fconst_1       
        //  2071: fadd           
        //  2072: f2d            
        //  2073: dstore          21
        //  2075: aload_0        
        //  2076: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  2079: fload           17
        //  2081: invokestatic    net/minecraft/util/MathHelper.cos:(F)F
        //  2084: fload           18
        //  2086: fmul           
        //  2087: ldc_w           0.1
        //  2090: fmul           
        //  2091: f2d            
        //  2092: dadd           
        //  2093: dstore          23
        //  2095: aload           13
        //  2097: new             Lnet/minecraft/util/BlockPos;
        //  2100: dup            
        //  2101: dload           19
        //  2103: d2i            
        //  2104: dload           21
        //  2106: d2i            
        //  2107: iconst_1       
        //  2108: isub           
        //  2109: dload           23
        //  2111: d2i            
        //  2112: invokespecial   net/minecraft/util/BlockPos.<init>:(III)V
        //  2115: invokevirtual   net/minecraft/world/WorldServer.getBlockState:(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/block/state/IBlockState;
        //  2118: invokeinterface net/minecraft/block/state/IBlockState.getBlock:()Lnet/minecraft/block/Block;
        //  2123: astore          25
        //  2125: aload           25
        //  2127: getstatic       net/minecraft/init/Blocks.water:Lnet/minecraft/block/BlockStaticLiquid;
        //  2130: if_acmpeq       2141
        //  2133: aload           25
        //  2135: getstatic       net/minecraft/init/Blocks.flowing_water:Lnet/minecraft/block/BlockDynamicLiquid;
        //  2138: if_acmpne       2176
        //  2141: aload           13
        //  2143: getstatic       net/minecraft/util/EnumParticleTypes.WATER_SPLASH:Lnet/minecraft/util/EnumParticleTypes;
        //  2146: dload           19
        //  2148: dload           21
        //  2150: dload           23
        //  2152: iconst_2       
        //  2153: aload_0        
        //  2154: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2157: iconst_2       
        //  2158: invokevirtual   java/util/Random.nextInt:(I)I
        //  2161: iadd           
        //  2162: ldc2_w          0.10000000149011612
        //  2165: dconst_0       
        //  2166: ldc2_w          0.10000000149011612
        //  2169: dconst_0       
        //  2170: iconst_0       
        //  2171: newarray        I
        //  2173: invokevirtual   net/minecraft/world/WorldServer.spawnParticle:(Lnet/minecraft/util/EnumParticleTypes;DDDIDDDD[I)V
        //  2176: aload_0        
        //  2177: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  2180: ifgt            2213
        //  2183: aload_0        
        //  2184: aload_0        
        //  2185: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2188: fconst_0       
        //  2189: ldc_w           360.0
        //  2192: invokestatic    net/minecraft/util/MathHelper.randomFloatClamp:(Ljava/util/Random;FF)F
        //  2195: putfield        net/minecraft/entity/projectile/EntityFishHook.fishApproachAngle:F
        //  2198: aload_0        
        //  2199: aload_0        
        //  2200: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2203: bipush          20
        //  2205: bipush          80
        //  2207: invokestatic    net/minecraft/util/MathHelper.getRandomIntegerInRange:(Ljava/util/Random;II)I
        //  2210: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchableDelay:I
        //  2213: goto            2253
        //  2216: aload_0        
        //  2217: aload_0        
        //  2218: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2221: bipush          100
        //  2223: sipush          900
        //  2226: invokestatic    net/minecraft/util/MathHelper.getRandomIntegerInRange:(Ljava/util/Random;II)I
        //  2229: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  2232: aload_0        
        //  2233: dup            
        //  2234: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  2237: aload_0        
        //  2238: getfield        net/minecraft/entity/projectile/EntityFishHook.angler:Lnet/minecraft/entity/player/EntityPlayer;
        //  2241: invokestatic    net/minecraft/enchantment/EnchantmentHelper.getLureModifier:(Lnet/minecraft/entity/EntityLivingBase;)I
        //  2244: bipush          20
        //  2246: imul           
        //  2247: iconst_5       
        //  2248: imul           
        //  2249: isub           
        //  2250: putfield        net/minecraft/entity/projectile/EntityFishHook.ticksCaughtDelay:I
        //  2253: aload_0        
        //  2254: getfield        net/minecraft/entity/projectile/EntityFishHook.ticksCatchable:I
        //  2257: ifle            2297
        //  2260: aload_0        
        //  2261: dup            
        //  2262: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2265: aload_0        
        //  2266: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2269: invokevirtual   java/util/Random.nextFloat:()F
        //  2272: aload_0        
        //  2273: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2276: invokevirtual   java/util/Random.nextFloat:()F
        //  2279: fmul           
        //  2280: aload_0        
        //  2281: getfield        net/minecraft/entity/projectile/EntityFishHook.rand:Ljava/util/Random;
        //  2284: invokevirtual   java/util/Random.nextFloat:()F
        //  2287: fmul           
        //  2288: f2d            
        //  2289: ldc2_w          0.2
        //  2292: dmul           
        //  2293: dsub           
        //  2294: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2297: dload           11
        //  2299: ldc2_w          2.0
        //  2302: dmul           
        //  2303: dconst_1       
        //  2304: dsub           
        //  2305: dstore          13
        //  2307: aload_0        
        //  2308: dup            
        //  2309: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2312: ldc2_w          0.03999999910593033
        //  2315: dload           13
        //  2317: dmul           
        //  2318: dadd           
        //  2319: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2322: dload           11
        //  2324: dconst_0       
        //  2325: dcmpl          
        //  2326: ifle            2351
        //  2329: fload           9
        //  2331: f2d            
        //  2332: ldc2_w          0.9
        //  2335: dmul           
        //  2336: d2f            
        //  2337: fstore          9
        //  2339: aload_0        
        //  2340: dup            
        //  2341: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2344: ldc2_w          0.8
        //  2347: dmul           
        //  2348: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2351: aload_0        
        //  2352: dup            
        //  2353: getfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //  2356: fload           9
        //  2358: f2d            
        //  2359: dmul           
        //  2360: putfield        net/minecraft/entity/projectile/EntityFishHook.motionX:D
        //  2363: aload_0        
        //  2364: dup            
        //  2365: getfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2368: fload           9
        //  2370: f2d            
        //  2371: dmul           
        //  2372: putfield        net/minecraft/entity/projectile/EntityFishHook.motionY:D
        //  2375: aload_0        
        //  2376: dup            
        //  2377: getfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //  2380: fload           9
        //  2382: f2d            
        //  2383: dmul           
        //  2384: putfield        net/minecraft/entity/projectile/EntityFishHook.motionZ:D
        //  2387: aload_0        
        //  2388: aload_0        
        //  2389: getfield        net/minecraft/entity/projectile/EntityFishHook.posX:D
        //  2392: aload_0        
        //  2393: getfield        net/minecraft/entity/projectile/EntityFishHook.posY:D
        //  2396: aload_0        
        //  2397: getfield        net/minecraft/entity/projectile/EntityFishHook.posZ:D
        //  2400: invokevirtual   net/minecraft/entity/projectile/EntityFishHook.setPosition:(DDD)V
        //  2403: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void setVelocity(final double n, final double n2, final double n3) {
        this.motionX = n;
        this.clientMotionX = n;
        this.motionY = n2;
        this.clientMotionY = n2;
        this.motionZ = n3;
        this.clientMotionZ = n3;
    }
    
    public int handleHookRetraction() {
        if (this.worldObj.isRemote) {
            return 0;
        }
        if (this.caughtEntity != null) {
            final double n = this.angler.posX - this.posX;
            final double n2 = this.angler.posY - this.posY;
            final double n3 = this.angler.posZ - this.posZ;
            final double n4 = MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
            final double n5 = 0.1;
            final Entity caughtEntity = this.caughtEntity;
            caughtEntity.motionX += n * n5;
            final Entity caughtEntity2 = this.caughtEntity;
            caughtEntity2.motionY += n2 * n5 + MathHelper.sqrt_double(n4) * 0.08;
            final Entity caughtEntity3 = this.caughtEntity;
            caughtEntity3.motionZ += n3 * n5;
        }
        else if (this.ticksCatchable > 0) {
            final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, this.getFishingResult());
            final double n6 = this.angler.posX - this.posX;
            final double n7 = this.angler.posY - this.posY;
            final double n8 = this.angler.posZ - this.posZ;
            final double n9 = MathHelper.sqrt_double(n6 * n6 + n7 * n7 + n8 * n8);
            final double n10 = 0.1;
            entityItem.motionX = n6 * n10;
            entityItem.motionY = n7 * n10 + MathHelper.sqrt_double(n9) * 0.08;
            entityItem.motionZ = n8 * n10;
            this.worldObj.spawnEntityInWorld(entityItem);
            this.angler.worldObj.spawnEntityInWorld(new EntityXPOrb(this.angler.worldObj, this.angler.posX, this.angler.posY + 0.5, this.angler.posZ + 0.5, this.rand.nextInt(6) + 1));
        }
        if (this.inGround) {}
        this.setDead();
        this.angler.fishEntity = null;
        return 2;
    }
    
    @Override
    public void setDead() {
        super.setDead();
        if (this.angler != null) {
            this.angler.fishEntity = null;
        }
    }
    
    private ItemStack getFishingResult() {
        final float nextFloat = this.worldObj.rand.nextFloat();
        final int luckOfSeaModifier = EnchantmentHelper.getLuckOfSeaModifier(this.angler);
        final int lureModifier = EnchantmentHelper.getLureModifier(this.angler);
        final float n = 0.1f - luckOfSeaModifier * 0.025f - lureModifier * 0.01f;
        final float n2 = 0.05f + luckOfSeaModifier * 0.01f - lureModifier * 0.01f;
        final float clamp_float = MathHelper.clamp_float(n, 0.0f, 1.0f);
        final float clamp_float2 = MathHelper.clamp_float(n2, 0.0f, 1.0f);
        if (nextFloat < clamp_float) {
            this.angler.triggerAchievement(StatList.junkFishedStat);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.JUNK)).getItemStack(this.rand);
        }
        if (nextFloat - clamp_float < clamp_float2) {
            this.angler.triggerAchievement(StatList.treasureFishedStat);
            return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.TREASURE)).getItemStack(this.rand);
        }
        this.angler.triggerAchievement(StatList.fishCaughtStat);
        return ((WeightedRandomFishable)WeightedRandom.getRandomItem(this.rand, EntityFishHook.FISH)).getItemStack(this.rand);
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    public boolean isInRangeToRenderDist(final double n) {
        double n2 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0;
        if (Double.isNaN(n2)) {
            n2 = 4.0;
        }
        final double n3 = n2 * 64.0;
        return n < n3 * n3;
    }
    
    public EntityFishHook(final World world) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.setSize(0.25f, 0.25f);
        this.ignoreFrustumCheck = true;
    }
    
    public EntityFishHook(final World world, final EntityPlayer angler) {
        super(world);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
        this.ignoreFrustumCheck = true;
        this.angler = angler;
        (this.angler.fishEntity = this).setSize(0.25f, 0.25f);
        this.setLocationAndAngles(angler.posX, angler.posY + angler.getEyeHeight(), angler.posZ, angler.rotationYaw, angler.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.posY -= 0.10000000149011612;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * 0.16f;
        this.setPosition(this.posX, this.posY, this.posZ);
        final float n = 0.4f;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * n;
        this.handleHookCasting(this.motionX, this.motionY, this.motionZ, 1.5f, 1.0f);
    }
}
