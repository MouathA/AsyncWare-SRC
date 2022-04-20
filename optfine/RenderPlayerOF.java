package optfine;

import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import java.lang.reflect.*;

public class RenderPlayerOF extends RenderPlayer
{
    protected void renderLayers(final AbstractClientPlayer abstractClientPlayer, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        super.renderLayers(abstractClientPlayer, n, n2, n3, n4, n5, n6, n7);
        this.renderEquippedItems(abstractClientPlayer, n7, n3);
    }
    
    protected void renderEquippedItems(final EntityLivingBase entityLivingBase, final float n, final float n2) {
        if (entityLivingBase instanceof AbstractClientPlayer) {
            final AbstractClientPlayer abstractClientPlayer = (AbstractClientPlayer)entityLivingBase;
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            PlayerConfigurations.renderPlayerItems((ModelBiped)this.mainModel, abstractClientPlayer, n, n2);
        }
    }
    
    public static void register() {
        final RenderManager renderManager = Config.getMinecraft().getRenderManager();
        final Map mapRenderTypes = getMapRenderTypes(renderManager);
        if (mapRenderTypes == null) {
            Config.warn("RenderPlayerOF init() failed: RenderManager.MapRenderTypes not found");
        }
        else {
            mapRenderTypes.put("default", new RenderPlayerOF(renderManager, false));
            mapRenderTypes.put("slim", new RenderPlayerOF(renderManager, true));
        }
    }
    
    private static Map getMapRenderTypes(final RenderManager renderManager) {
        final Field[] fields = Reflector.getFields(RenderManager.class, Map.class);
        while (0 < fields.length) {
            final Map map = (Map)fields[0].get(renderManager);
            if (map != null && map.get("default") instanceof RenderPlayer) {
                return map;
            }
            int n = 0;
            ++n;
        }
        return null;
    }
    
    @Override
    protected void renderLayers(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.renderLayers((AbstractClientPlayer)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public RenderPlayerOF(final RenderManager renderManager, final boolean b) {
        super(renderManager, b);
    }
}
