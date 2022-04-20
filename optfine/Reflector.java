package optfine;

import java.lang.reflect.*;
import java.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;
import net.minecraft.potion.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.vertex.*;

public class Reflector
{
    public static ReflectorMethod ForgeHooksClient_setRenderPass;
    public static ReflectorMethod FMLCommonHandler_enhanceCrashReport;
    public static ReflectorClass MinecraftForgeClient;
    public static ReflectorClass MinecraftForge;
    public static ReflectorMethod ForgeWorld_countEntities;
    public static ReflectorMethod ForgeEntity_shouldRenderInPass;
    public static ReflectorClass EntityViewRenderEvent_RenderFogEvent;
    public static ReflectorMethod EventBus_post;
    public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight;
    public static ReflectorMethod ForgeHooksClient_orientBedCamera;
    public static ReflectorMethod LightCache_clear;
    public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget;
    public static ReflectorClass ModLoader;
    public static ReflectorMethod ForgeHooksClient_dispatchRenderLast;
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre;
    public static ReflectorMethod ForgeHooksClient_getOffsetFOV;
    public static ReflectorClass FMLCommonHandler;
    public static ReflectorClass ChunkWatchEvent_UnWatch;
    public static ReflectorMethod ForgeWorldProvider_getCloudRenderer;
    public static ReflectorClass BlockCoord;
    public static ReflectorMethod ForgeBlock_canCreatureSpawn;
    public static ReflectorMethod ForgeItemRecord_getRecordResource;
    public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor;
    public static ReflectorMethod ForgeHooks_onLivingDrops;
    public static ReflectorMethod ModLoader_getCustomAnimationLogic;
    public static ReflectorMethod ForgeWorldProvider_getSkyRenderer;
    public static ReflectorClass ForgeBlock;
    public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor;
    public static ReflectorMethod FMLRenderAccessLibrary_renderItemAsFull3DBlock;
    public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw;
    public static ReflectorClass DrawScreenEvent_Post;
    public static ReflectorMethod ForgeBlock_isBedFoot;
    public static ReflectorMethod IRenderHandler_render;
    public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox;
    public static ReflectorClass EventBus;
    public static ReflectorMethod FMLRenderAccessLibrary_renderInventoryBlock;
    public static ReflectorMethod ForgeEventFactory_canEntitySpawn;
    public static ReflectorField ForgeEntity_captureDrops;
    public static ReflectorField ItemRenderType_EQUIPPED;
    public static ReflectorClass EntityViewRenderEvent_FogDensity;
    public static ReflectorClass ForgeItem;
    public static ReflectorClass FMLClientHandler;
    public static ReflectorMethod ForgeTileEntity_shouldRenderInPass;
    public static ReflectorMethod FMLCommonHandler_handleServerStarting;
    public static ReflectorMethod ForgeHooksClient_drawScreen;
    public static ReflectorClass ForgeHooks;
    public static ReflectorClass ForgeItemRecord;
    public static ReflectorMethod ModLoader_renderBlockIsItemFull3D;
    public static ReflectorMethod ForgeBlock_hasTileEntity;
    public static ReflectorMethod FMLCommonHandler_getBrandings;
    public static ReflectorMethod ForgeHooks_onLivingHurt;
    public static ReflectorClass WorldEvent_Load;
    public static ReflectorField MinecraftForge_EVENT_BUS;
    public static ReflectorMethod ForgeBlock_isAir;
    public static ReflectorClass ForgeVertexFormatElementEnumUseage;
    public static ReflectorMethod ForgeItem_onEntitySwing;
    public static ReflectorClass DimensionManager;
    public static ReflectorConstructor WorldEvent_Load_Constructor;
    public static ReflectorField EntityViewRenderEvent_FogDensity_density;
    public static ReflectorClass ForgeWorldProvider;
    public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer;
    public static ReflectorMethod ForgeBlock_addDestroyEffects;
    public static ReflectorMethod ForgeBlock_canRenderInLayer;
    public static ReflectorMethod ModLoader_registerServer;
    public static ReflectorClass ForgeEntity;
    public static ReflectorField EntityViewRenderEvent_FogColors_red;
    public static ReflectorClass Event_Result;
    public static ReflectorField ForgeEntity_capturedDrops;
    public static ReflectorField Event_Result_DEFAULT;
    public static ReflectorField Event_Result_DENY;
    public static ReflectorMethod FMLClientHandler_isLoading;
    public static ReflectorClass DrawScreenEvent_Pre;
    public static ReflectorMethod DimensionManager_getStaticDimensionIDs;
    public static ReflectorMethod BlockCoord_resetPool;
    public static ReflectorClass LightCache;
    public static ReflectorClass ForgePotionEffect;
    public static ReflectorMethod ForgeTileEntity_canRenderBreaking;
    public static ReflectorMethod ForgeHooks_onLivingFall;
    public static ReflectorClass IRenderHandler;
    public static ReflectorMethod MinecraftForgeClient_getItemRenderer;
    public static ReflectorClass ForgeEventFactory;
    public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw;
    public static ReflectorMethod ForgeEventFactory_canEntityDespawn;
    public static ReflectorMethod FMLRenderAccessLibrary_renderWorldBlock;
    public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost;
    public static ReflectorMethod ForgePotionEffect_isCurativeItem;
    public static ReflectorMethod MinecraftForgeClient_getRenderPass;
    public static ReflectorMethod ForgeWorld_getPerWorldStorage;
    public static ReflectorConstructor DrawScreenEvent_Pre_Constructor;
    public static ReflectorClass ItemRenderType;
    public static ReflectorMethod ForgeHooks_onLivingDeath;
    public static ReflectorClass FMLRenderAccessLibrary;
    public static ReflectorClass ForgeWorld;
    public static ReflectorMethod ForgeBlock_addHitEffects;
    public static ReflectorMethod ForgeBlock_getBedDirection;
    public static ReflectorField EntityViewRenderEvent_FogColors_blue;
    public static ReflectorMethod ForgeEntity_canRiderInteract;
    public static ReflectorField Event_Result_ALLOW;
    public static ReflectorClass EntityViewRenderEvent_FogColors;
    public static ReflectorField EntityViewRenderEvent_FogColors_green;
    public static ReflectorField LightCache_cache;
    public static ReflectorMethod FMLClientHandler_instance;
    public static ReflectorConstructor DrawScreenEvent_Post_Constructor;
    public static ReflectorMethod ForgeHooksClient_setRenderLayer;
    public static ReflectorMethod ForgeHooks_onLivingJump;
    public static ReflectorConstructor EntityViewRenderEvent_RenderFogEvent_Constructor;
    public static ReflectorMethod ModLoader_renderInvBlock;
    public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand;
    public static ReflectorConstructor EntityViewRenderEvent_FogDensity_Constructor;
    public static ReflectorMethod FMLCommonHandler_instance;
    public static ReflectorMethod ModLoader_renderWorldBlock;
    public static ReflectorClass ForgeHooksClient;
    public static ReflectorClass ForgeTileEntity;
    public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart;
    public static ReflectorMethod ForgeHooksClient_onFogRender;
    public static ReflectorMethod ForgeHooks_onLivingUpdate;
    public static ReflectorMethod ForgeHooks_onLivingAttack;
    
    public static Object getFieldValue(final Object o, final ReflectorField reflectorField) {
        final Field targetField = reflectorField.getTargetField();
        if (targetField == null) {
            return null;
        }
        return targetField.get(o);
    }
    
    public static String callString(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return null;
        }
        return (String)targetMethod.invoke(o, array);
    }
    
    private static void handleException(final Throwable t, final Object o, final ReflectorMethod reflectorMethod, final Object[] array) {
        if (t instanceof InvocationTargetException) {
            t.printStackTrace();
        }
        else {
            if (t instanceof IllegalArgumentException) {
                Config.warn("*** IllegalArgumentException ***");
                Config.warn("Method: " + reflectorMethod.getTargetMethod());
                Config.warn("Object: " + o);
                Config.warn("Parameter classes: " + Config.arrayToString(getClasses(array)));
                Config.warn("Parameters: " + Config.arrayToString(array));
            }
            Config.warn("*** Exception outside of method ***");
            Config.warn("Method deactivated: " + reflectorMethod.getTargetMethod());
            reflectorMethod.deactivate();
            t.printStackTrace();
        }
    }
    
    public static float callFloat(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return 0.0f;
        }
        return (float)targetMethod.invoke(o, array);
    }
    
    public static void callVoid(final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return;
        }
        targetMethod.invoke(null, array);
    }
    
    public static int callInt(final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return 0;
        }
        return (int)targetMethod.invoke(null, array);
    }
    
    public static boolean callBoolean(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        return targetMethod != null && (boolean)targetMethod.invoke(o, array);
    }
    
    private static void dbgCall(final boolean b, final String s, final ReflectorMethod reflectorMethod, final Object[] array, final Object o) {
        final String name = reflectorMethod.getTargetMethod().getDeclaringClass().getName();
        final String name2 = reflectorMethod.getTargetMethod().getName();
        String s2 = "";
        if (b) {
            s2 = " static";
        }
        Config.dbg(s + s2 + " " + name + "." + name2 + "(" + Config.arrayToString(array) + ") => " + o);
    }
    
    public static boolean callBoolean(final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        return targetMethod != null && (boolean)targetMethod.invoke(null, array);
    }
    
    public static float callFloat(final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return 0.0f;
        }
        return (float)targetMethod.invoke(null, array);
    }
    
    private static Object[] getClasses(final Object[] array) {
        if (array == null) {
            return new Class[0];
        }
        final Class[] array2 = new Class[array.length];
        while (0 < array2.length) {
            final Object o = array[0];
            if (o != null) {
                array2[0] = o.getClass();
            }
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    private static void handleException(final Throwable t, final ReflectorConstructor reflectorConstructor, final Object[] array) {
        if (t instanceof InvocationTargetException) {
            t.printStackTrace();
        }
        else {
            if (t instanceof IllegalArgumentException) {
                Config.warn("*** IllegalArgumentException ***");
                Config.warn("Constructor: " + reflectorConstructor.getTargetConstructor());
                Config.warn("Parameter classes: " + Config.arrayToString(getClasses(array)));
                Config.warn("Parameters: " + Config.arrayToString(array));
            }
            Config.warn("*** Exception outside of constructor ***");
            Config.warn("Constructor deactivated: " + reflectorConstructor.getTargetConstructor());
            reflectorConstructor.deactivate();
            t.printStackTrace();
        }
    }
    
    public static void callVoid(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        if (o == null) {
            return;
        }
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return;
        }
        targetMethod.invoke(o, array);
    }
    
    public static Object call(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return null;
        }
        return targetMethod.invoke(o, array);
    }
    
    public static void setFieldValue(final Object o, final ReflectorField reflectorField, final Object o2) {
        final Field targetField = reflectorField.getTargetField();
        if (targetField == null) {
            return;
        }
        targetField.set(o, o2);
    }
    
    public static Object getFieldValue(final ReflectorField reflectorField) {
        return getFieldValue(null, reflectorField);
    }
    
    public static String callString(final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return null;
        }
        return (String)targetMethod.invoke(null, array);
    }
    
    public static Object newInstance(final ReflectorConstructor reflectorConstructor, final Object... array) {
        final Constructor targetConstructor = reflectorConstructor.getTargetConstructor();
        if (targetConstructor == null) {
            return null;
        }
        return targetConstructor.newInstance(array);
    }
    
    private static void dbgCallVoid(final boolean b, final String s, final ReflectorMethod reflectorMethod, final Object[] array) {
        final String name = reflectorMethod.getTargetMethod().getDeclaringClass().getName();
        final String name2 = reflectorMethod.getTargetMethod().getName();
        String s2 = "";
        if (b) {
            s2 = " static";
        }
        Config.dbg(s + s2 + " " + name + "." + name2 + "(" + Config.arrayToString(array) + ")");
    }
    
    public static Object call(final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return null;
        }
        return targetMethod.invoke(null, array);
    }
    
    public static boolean postForgeBusEvent(final Object o) {
        if (o == null) {
            return false;
        }
        final Object fieldValue = getFieldValue(Reflector.MinecraftForge_EVENT_BUS);
        if (fieldValue == null) {
            return false;
        }
        final Object call = call(fieldValue, Reflector.EventBus_post, o);
        return call instanceof Boolean && (boolean)call;
    }
    
    public static float getFieldValueFloat(final Object o, final ReflectorField reflectorField, final float n) {
        final Object fieldValue = getFieldValue(o, reflectorField);
        if (!(fieldValue instanceof Float)) {
            return n;
        }
        return (float)fieldValue;
    }
    
    public static int callInt(final Object o, final ReflectorMethod reflectorMethod, final Object... array) {
        final Method targetMethod = reflectorMethod.getTargetMethod();
        if (targetMethod == null) {
            return 0;
        }
        return (int)targetMethod.invoke(o, array);
    }
    
    public static Field getField(final Class clazz, final Class clazz2) {
        final Field[] declaredFields = clazz.getDeclaredFields();
        while (0 < declaredFields.length) {
            final Field field = declaredFields[0];
            if (field.getType() == clazz2) {
                field.setAccessible(true);
                return field;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    public static boolean matchesTypes(final Class[] array, final Class[] array2) {
        if (array.length != array2.length) {
            return false;
        }
        while (0 < array2.length) {
            if (array[0] != array2[0]) {
                return false;
            }
            int n = 0;
            ++n;
        }
        return true;
    }
    
    public static Field[] getFields(final Class clazz, final Class clazz2) {
        final ArrayList<Field> list = new ArrayList<Field>();
        final Field[] declaredFields = clazz.getDeclaredFields();
        while (0 < declaredFields.length) {
            final Field field = declaredFields[0];
            if (field.getType() == clazz2) {
                field.setAccessible(true);
                list.add(field);
            }
            int n = 0;
            ++n;
        }
        return list.toArray(new Field[list.size()]);
    }
    
    private static void dbgFieldValue(final boolean b, final String s, final ReflectorField reflectorField, final Object o) {
        final String name = reflectorField.getTargetField().getDeclaringClass().getName();
        final String name2 = reflectorField.getTargetField().getName();
        String s2 = "";
        if (b) {
            s2 = " static";
        }
        Config.dbg(s + s2 + " " + name + "." + name2 + " => " + o);
    }
    
    static {
        Reflector.ModLoader = new ReflectorClass("ModLoader");
        Reflector.ModLoader_renderWorldBlock = new ReflectorMethod(Reflector.ModLoader, "renderWorldBlock");
        Reflector.ModLoader_renderInvBlock = new ReflectorMethod(Reflector.ModLoader, "renderInvBlock");
        Reflector.ModLoader_renderBlockIsItemFull3D = new ReflectorMethod(Reflector.ModLoader, "renderBlockIsItemFull3D");
        Reflector.ModLoader_registerServer = new ReflectorMethod(Reflector.ModLoader, "registerServer");
        Reflector.ModLoader_getCustomAnimationLogic = new ReflectorMethod(Reflector.ModLoader, "getCustomAnimationLogic");
        Reflector.FMLRenderAccessLibrary = new ReflectorClass("net.minecraft.src.FMLRenderAccessLibrary");
        Reflector.FMLRenderAccessLibrary_renderWorldBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, "renderWorldBlock");
        Reflector.FMLRenderAccessLibrary_renderInventoryBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, "renderInventoryBlock");
        Reflector.FMLRenderAccessLibrary_renderItemAsFull3DBlock = new ReflectorMethod(Reflector.FMLRenderAccessLibrary, "renderItemAsFull3DBlock");
        Reflector.LightCache = new ReflectorClass("LightCache");
        Reflector.LightCache_cache = new ReflectorField(Reflector.LightCache, "cache");
        Reflector.LightCache_clear = new ReflectorMethod(Reflector.LightCache, "clear");
        Reflector.BlockCoord = new ReflectorClass("BlockCoord");
        Reflector.BlockCoord_resetPool = new ReflectorMethod(Reflector.BlockCoord, "resetPool");
        Reflector.MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
        Reflector.MinecraftForge_EVENT_BUS = new ReflectorField(Reflector.MinecraftForge, "EVENT_BUS");
        Reflector.ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
        Reflector.ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(Reflector.ForgeHooks, "onLivingSetAttackTarget");
        Reflector.ForgeHooks_onLivingUpdate = new ReflectorMethod(Reflector.ForgeHooks, "onLivingUpdate");
        Reflector.ForgeHooks_onLivingAttack = new ReflectorMethod(Reflector.ForgeHooks, "onLivingAttack");
        Reflector.ForgeHooks_onLivingHurt = new ReflectorMethod(Reflector.ForgeHooks, "onLivingHurt");
        Reflector.ForgeHooks_onLivingDeath = new ReflectorMethod(Reflector.ForgeHooks, "onLivingDeath");
        Reflector.ForgeHooks_onLivingDrops = new ReflectorMethod(Reflector.ForgeHooks, "onLivingDrops");
        Reflector.ForgeHooks_onLivingFall = new ReflectorMethod(Reflector.ForgeHooks, "onLivingFall");
        Reflector.ForgeHooks_onLivingJump = new ReflectorMethod(Reflector.ForgeHooks, "onLivingJump");
        Reflector.MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
        Reflector.MinecraftForgeClient_getRenderPass = new ReflectorMethod(Reflector.MinecraftForgeClient, "getRenderPass");
        Reflector.MinecraftForgeClient_getItemRenderer = new ReflectorMethod(Reflector.MinecraftForgeClient, "getItemRenderer");
        Reflector.ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
        Reflector.ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(Reflector.ForgeHooksClient, "onDrawBlockHighlight");
        Reflector.ForgeHooksClient_orientBedCamera = new ReflectorMethod(Reflector.ForgeHooksClient, "orientBedCamera");
        Reflector.ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(Reflector.ForgeHooksClient, "dispatchRenderLast");
        Reflector.ForgeHooksClient_setRenderPass = new ReflectorMethod(Reflector.ForgeHooksClient, "setRenderPass");
        Reflector.ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(Reflector.ForgeHooksClient, "onTextureStitchedPre");
        Reflector.ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(Reflector.ForgeHooksClient, "onTextureStitchedPost");
        Reflector.ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(Reflector.ForgeHooksClient, "renderFirstPersonHand");
        Reflector.ForgeHooksClient_getOffsetFOV = new ReflectorMethod(Reflector.ForgeHooksClient, "getOffsetFOV");
        Reflector.ForgeHooksClient_drawScreen = new ReflectorMethod(Reflector.ForgeHooksClient, "drawScreen");
        Reflector.ForgeHooksClient_onFogRender = new ReflectorMethod(Reflector.ForgeHooksClient, "onFogRender");
        Reflector.ForgeHooksClient_setRenderLayer = new ReflectorMethod(Reflector.ForgeHooksClient, "setRenderLayer");
        Reflector.FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
        Reflector.FMLCommonHandler_instance = new ReflectorMethod(Reflector.FMLCommonHandler, "instance");
        Reflector.FMLCommonHandler_handleServerStarting = new ReflectorMethod(Reflector.FMLCommonHandler, "handleServerStarting");
        Reflector.FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(Reflector.FMLCommonHandler, "handleServerAboutToStart");
        Reflector.FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(Reflector.FMLCommonHandler, "enhanceCrashReport");
        Reflector.FMLCommonHandler_getBrandings = new ReflectorMethod(Reflector.FMLCommonHandler, "getBrandings");
        Reflector.FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
        Reflector.FMLClientHandler_instance = new ReflectorMethod(Reflector.FMLClientHandler, "instance");
        Reflector.FMLClientHandler_isLoading = new ReflectorMethod(Reflector.FMLClientHandler, "isLoading");
        Reflector.ItemRenderType = new ReflectorClass("net.minecraftforge.client.IItemRenderer$ItemRenderType");
        Reflector.ItemRenderType_EQUIPPED = new ReflectorField(Reflector.ItemRenderType, "EQUIPPED");
        Reflector.ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
        Reflector.ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, "getSkyRenderer");
        Reflector.ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, "getCloudRenderer");
        Reflector.ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(Reflector.ForgeWorldProvider, "getWeatherRenderer");
        Reflector.ForgeWorld = new ReflectorClass(World.class);
        Reflector.ForgeWorld_countEntities = new ReflectorMethod(Reflector.ForgeWorld, "countEntities", new Class[] { EnumCreatureType.class, Boolean.TYPE });
        Reflector.ForgeWorld_getPerWorldStorage = new ReflectorMethod(Reflector.ForgeWorld, "getPerWorldStorage");
        Reflector.IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
        Reflector.IRenderHandler_render = new ReflectorMethod(Reflector.IRenderHandler, "render");
        Reflector.DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
        Reflector.DimensionManager_getStaticDimensionIDs = new ReflectorMethod(Reflector.DimensionManager, "getStaticDimensionIDs");
        Reflector.WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
        Reflector.WorldEvent_Load_Constructor = new ReflectorConstructor(Reflector.WorldEvent_Load, new Class[] { World.class });
        Reflector.DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
        Reflector.DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(Reflector.DrawScreenEvent_Pre, new Class[] { GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
        Reflector.DrawScreenEvent_Post_Constructor = new ReflectorConstructor(Reflector.DrawScreenEvent_Post, new Class[] { GuiScreen.class, Integer.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
        Reflector.EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(Reflector.EntityViewRenderEvent_FogColors, new Class[] { EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Float.TYPE, Float.TYPE, Float.TYPE });
        Reflector.EntityViewRenderEvent_FogColors_red = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, "red");
        Reflector.EntityViewRenderEvent_FogColors_green = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, "green");
        Reflector.EntityViewRenderEvent_FogColors_blue = new ReflectorField(Reflector.EntityViewRenderEvent_FogColors, "blue");
        Reflector.EntityViewRenderEvent_FogDensity = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity");
        Reflector.EntityViewRenderEvent_FogDensity_Constructor = new ReflectorConstructor(Reflector.EntityViewRenderEvent_FogDensity, new Class[] { EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Float.TYPE });
        Reflector.EntityViewRenderEvent_FogDensity_density = new ReflectorField(Reflector.EntityViewRenderEvent_FogDensity, "density");
        Reflector.EntityViewRenderEvent_RenderFogEvent = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$RenderFogEvent");
        Reflector.EntityViewRenderEvent_RenderFogEvent_Constructor = new ReflectorConstructor(Reflector.EntityViewRenderEvent_RenderFogEvent, new Class[] { EntityRenderer.class, Entity.class, Block.class, Double.TYPE, Integer.TYPE, Float.TYPE });
        Reflector.EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
        Reflector.EventBus_post = new ReflectorMethod(Reflector.EventBus, "post");
        Reflector.Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
        Reflector.Event_Result_DENY = new ReflectorField(Reflector.Event_Result, "DENY");
        Reflector.Event_Result_ALLOW = new ReflectorField(Reflector.Event_Result, "ALLOW");
        Reflector.Event_Result_DEFAULT = new ReflectorField(Reflector.Event_Result, "DEFAULT");
        Reflector.ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
        Reflector.ForgeEventFactory_canEntitySpawn = new ReflectorMethod(Reflector.ForgeEventFactory, "canEntitySpawn");
        Reflector.ForgeEventFactory_canEntityDespawn = new ReflectorMethod(Reflector.ForgeEventFactory, "canEntityDespawn");
        Reflector.ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
        Reflector.ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(Reflector.ChunkWatchEvent_UnWatch, new Class[] { ChunkCoordIntPair.class, EntityPlayerMP.class });
        Reflector.ForgeBlock = new ReflectorClass(Block.class);
        Reflector.ForgeBlock_getBedDirection = new ReflectorMethod(Reflector.ForgeBlock, "getBedDirection");
        Reflector.ForgeBlock_isBedFoot = new ReflectorMethod(Reflector.ForgeBlock, "isBedFoot");
        Reflector.ForgeBlock_hasTileEntity = new ReflectorMethod(Reflector.ForgeBlock, "hasTileEntity", new Class[] { IBlockState.class });
        Reflector.ForgeBlock_canCreatureSpawn = new ReflectorMethod(Reflector.ForgeBlock, "canCreatureSpawn");
        Reflector.ForgeBlock_addHitEffects = new ReflectorMethod(Reflector.ForgeBlock, "addHitEffects");
        Reflector.ForgeBlock_addDestroyEffects = new ReflectorMethod(Reflector.ForgeBlock, "addDestroyEffects");
        Reflector.ForgeBlock_isAir = new ReflectorMethod(Reflector.ForgeBlock, "isAir");
        Reflector.ForgeBlock_canRenderInLayer = new ReflectorMethod(Reflector.ForgeBlock, "canRenderInLayer");
        Reflector.ForgeEntity = new ReflectorClass(Entity.class);
        Reflector.ForgeEntity_captureDrops = new ReflectorField(Reflector.ForgeEntity, "captureDrops");
        Reflector.ForgeEntity_capturedDrops = new ReflectorField(Reflector.ForgeEntity, "capturedDrops");
        Reflector.ForgeEntity_shouldRenderInPass = new ReflectorMethod(Reflector.ForgeEntity, "shouldRenderInPass");
        Reflector.ForgeEntity_canRiderInteract = new ReflectorMethod(Reflector.ForgeEntity, "canRiderInteract");
        Reflector.ForgeTileEntity = new ReflectorClass(TileEntity.class);
        Reflector.ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(Reflector.ForgeTileEntity, "shouldRenderInPass");
        Reflector.ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(Reflector.ForgeTileEntity, "getRenderBoundingBox");
        Reflector.ForgeTileEntity_canRenderBreaking = new ReflectorMethod(Reflector.ForgeTileEntity, "canRenderBreaking");
        Reflector.ForgeItem = new ReflectorClass(Item.class);
        Reflector.ForgeItem_onEntitySwing = new ReflectorMethod(Reflector.ForgeItem, "onEntitySwing");
        Reflector.ForgePotionEffect = new ReflectorClass(PotionEffect.class);
        Reflector.ForgePotionEffect_isCurativeItem = new ReflectorMethod(Reflector.ForgePotionEffect, "isCurativeItem");
        Reflector.ForgeItemRecord = new ReflectorClass(ItemRecord.class);
        Reflector.ForgeItemRecord_getRecordResource = new ReflectorMethod(Reflector.ForgeItemRecord, "getRecordResource", new Class[] { String.class });
        Reflector.ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement.EnumUsage.class);
        Reflector.ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(Reflector.ForgeVertexFormatElementEnumUseage, "preDraw");
        Reflector.ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(Reflector.ForgeVertexFormatElementEnumUseage, "postDraw");
    }
    
    public static void setFieldValue(final ReflectorField reflectorField, final Object o) {
        setFieldValue(null, reflectorField, o);
    }
    
    public static boolean postForgeBusEvent(final ReflectorConstructor reflectorConstructor, final Object... array) {
        final Object instance = newInstance(reflectorConstructor, array);
        return instance != null && postForgeBusEvent(instance);
    }
}
