package optfine;

import net.minecraft.client.model.*;

public class PlayerItemRenderer
{
    private float scaleFactor;
    private int attachTo;
    private ModelRenderer modelRenderer;
    
    public ModelRenderer getModelRenderer() {
        return this.modelRenderer;
    }
    
    public PlayerItemRenderer(final int attachTo, final float scaleFactor, final ModelRenderer modelRenderer) {
        this.attachTo = 0;
        this.scaleFactor = 0.0f;
        this.modelRenderer = null;
        this.attachTo = attachTo;
        this.scaleFactor = scaleFactor;
        this.modelRenderer = modelRenderer;
    }
    
    public void render(final ModelBiped modelBiped, final float n) {
        final ModelRenderer attachModel = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
        if (attachModel != null) {
            attachModel.postRender(n);
        }
        this.modelRenderer.render(n * this.scaleFactor);
    }
}
