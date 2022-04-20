package optfine;

import net.minecraft.client.model.*;
import net.minecraft.client.entity.*;

public class PlayerConfiguration
{
    private PlayerItemModel[] playerItemModels;
    private boolean initialized;
    
    public PlayerItemModel[] getPlayerItemModels() {
        return this.playerItemModels;
    }
    
    public void renderPlayerItems(final ModelBiped modelBiped, final AbstractClientPlayer abstractClientPlayer, final float n, final float n2) {
        if (this.initialized) {
            while (0 < this.playerItemModels.length) {
                this.playerItemModels[0].render(modelBiped, abstractClientPlayer, n, n2);
                int n3 = 0;
                ++n3;
            }
        }
    }
    
    public PlayerConfiguration() {
        this.playerItemModels = new PlayerItemModel[0];
        this.initialized = false;
    }
    
    public void addPlayerItemModel(final PlayerItemModel playerItemModel) {
        this.playerItemModels = (PlayerItemModel[])Config.addObjectToArray(this.playerItemModels, playerItemModel);
    }
    
    public void setInitialized(final boolean initialized) {
        this.initialized = initialized;
    }
    
    public boolean isInitialized() {
        return this.initialized;
    }
}
