package net.minecraft.client.renderer.texture;

import net.minecraft.client.*;
import net.minecraft.util.*;

public class TextureClock extends TextureAtlasSprite
{
    private double field_94239_h;
    private double field_94240_i;
    
    public TextureClock(final String s) {
        super(s);
    }
    
    @Override
    public void updateAnimation() {
        if (!this.framesTextureData.isEmpty()) {
            final Minecraft minecraft = Minecraft.getMinecraft();
            double random = 0.0;
            if (minecraft.theWorld != null && minecraft.thePlayer != null) {
                random = minecraft.theWorld.getCelestialAngle(1.0f);
                if (!minecraft.theWorld.provider.isSurfaceWorld()) {
                    random = Math.random();
                }
            }
            double n;
            for (n = random - this.field_94239_h; n < -0.5; ++n) {}
            while (n >= 0.5) {
                --n;
            }
            this.field_94240_i += MathHelper.clamp_double(n, -1.0, 1.0) * 0.1;
            this.field_94240_i *= 0.8;
            this.field_94239_h += this.field_94240_i;
            int i;
            for (i = (int)((this.field_94239_h + 1.0) * this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size()) {}
            if (i != this.frameCounter) {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap(this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }
}
