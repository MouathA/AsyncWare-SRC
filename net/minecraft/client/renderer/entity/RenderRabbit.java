package net.minecraft.client.renderer.entity;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.model.*;

public class RenderRabbit extends RenderLiving
{
    private static final ResourceLocation WHITE;
    private static final ResourceLocation BROWN;
    private static final ResourceLocation TOAST;
    private static final ResourceLocation WHITE_SPLOTCHED;
    private static final ResourceLocation GOLD;
    private static final ResourceLocation CAERBANNOG;
    private static final ResourceLocation BLACK;
    private static final ResourceLocation SALT;
    
    protected ResourceLocation getEntityTexture(final EntityRabbit entityRabbit) {
        final String textWithoutFormattingCodes = EnumChatFormatting.getTextWithoutFormattingCodes(entityRabbit.getName());
        if (textWithoutFormattingCodes != null && textWithoutFormattingCodes.equals("Toast")) {
            return RenderRabbit.TOAST;
        }
        switch (entityRabbit.getRabbitType()) {
            default: {
                return RenderRabbit.BROWN;
            }
            case 1: {
                return RenderRabbit.WHITE;
            }
            case 2: {
                return RenderRabbit.BLACK;
            }
            case 3: {
                return RenderRabbit.WHITE_SPLOTCHED;
            }
            case 4: {
                return RenderRabbit.GOLD;
            }
            case 5: {
                return RenderRabbit.SALT;
            }
            case 99: {
                return RenderRabbit.CAERBANNOG;
            }
        }
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityRabbit)entity);
    }
    
    public RenderRabbit(final RenderManager renderManager, final ModelBase modelBase, final float n) {
        super(renderManager, modelBase, n);
    }
    
    static {
        BROWN = new ResourceLocation("textures/entity/rabbit/brown.png");
        WHITE = new ResourceLocation("textures/entity/rabbit/white.png");
        BLACK = new ResourceLocation("textures/entity/rabbit/black.png");
        GOLD = new ResourceLocation("textures/entity/rabbit/gold.png");
        SALT = new ResourceLocation("textures/entity/rabbit/salt.png");
        WHITE_SPLOTCHED = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
        TOAST = new ResourceLocation("textures/entity/rabbit/toast.png");
        CAERBANNOG = new ResourceLocation("textures/entity/rabbit/caerbannog.png");
    }
}
