package net.minecraft.client.renderer.texture;

import java.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import com.google.common.collect.*;

public class Stitcher
{
    private final Set setStitchHolders;
    private final int mipmapLevelStitcher;
    private final int maxTileDimension;
    private int currentHeight;
    private final int maxWidth;
    private static final String __OBFID = "CL_00001054";
    private final boolean forcePowerOf2;
    private final List stitchSlots;
    private final int maxHeight;
    private int currentWidth;
    
    public List getStichSlots() {
        final ArrayList arrayList = Lists.newArrayList();
        final Iterator<Slot> iterator = this.stitchSlots.iterator();
        while (iterator.hasNext()) {
            iterator.next().getAllStitchSlots(arrayList);
        }
        final ArrayList arrayList2 = Lists.newArrayList();
        for (final Slot slot : arrayList) {
            final Holder stitchHolder = slot.getStitchHolder();
            final TextureAtlasSprite atlasSprite = stitchHolder.getAtlasSprite();
            atlasSprite.initSprite(this.currentWidth, this.currentHeight, slot.getOriginX(), slot.getOriginY(), stitchHolder.isRotated());
            arrayList2.add(atlasSprite);
        }
        return arrayList2;
    }
    
    private static int getMipmapDimension(final int n, final int n2) {
        return (n >> n2) + (((n & (1 << n2) - 1) != 0x0) ? 1 : 0) << n2;
    }
    
    public void doStitch() {
        final Holder[] array = this.setStitchHolders.toArray(new Holder[this.setStitchHolders.size()]);
        Arrays.sort(array);
        final Holder[] array2 = array;
        while (0 < array2.length) {
            final Holder holder = array2[0];
            if (this < holder) {
                throw new StitcherException(holder, String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", holder.getAtlasSprite().getIconName(), holder.getAtlasSprite().getIconWidth(), holder.getAtlasSprite().getIconHeight(), this.currentWidth, this.currentHeight, this.maxWidth, this.maxHeight));
            }
            int n = 0;
            ++n;
        }
        if (this.forcePowerOf2) {
            this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
            this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
        }
    }
    
    public int getCurrentHeight() {
        return this.currentHeight;
    }
    
    public int getCurrentWidth() {
        return this.currentWidth;
    }
    
    private boolean expandAndAllocateSlot(final Holder holder) {
        final int min = Math.min(holder.getWidth(), holder.getHeight());
        final boolean b = this.currentWidth == 0 && this.currentHeight == 0;
        boolean b5;
        if (this.forcePowerOf2) {
            final int roundUpToPowerOfTwo = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
            final int roundUpToPowerOfTwo2 = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
            final int roundUpToPowerOfTwo3 = MathHelper.roundUpToPowerOfTwo(this.currentWidth + min);
            final int roundUpToPowerOfTwo4 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + min);
            final boolean b2 = roundUpToPowerOfTwo3 <= this.maxWidth;
            final boolean b3 = roundUpToPowerOfTwo4 <= this.maxHeight;
            if (!b2 && !b3) {
                return false;
            }
            final boolean b4 = roundUpToPowerOfTwo != roundUpToPowerOfTwo3;
            if (b4 ^ roundUpToPowerOfTwo2 != roundUpToPowerOfTwo4) {
                b5 = !b4;
            }
            else {
                b5 = (b2 && roundUpToPowerOfTwo <= roundUpToPowerOfTwo2);
            }
        }
        else {
            final boolean b6 = this.currentWidth + min <= this.maxWidth;
            final boolean b7 = this.currentHeight + min <= this.maxHeight;
            if (!b6 && !b7) {
                return false;
            }
            b5 = (b6 && (b || this.currentWidth <= this.currentHeight));
        }
        if (MathHelper.roundUpToPowerOfTwo(((!b5) ? this.currentHeight : this.currentWidth) + Math.max(holder.getWidth(), holder.getHeight())) > ((!b5) ? this.maxHeight : this.maxWidth)) {
            return false;
        }
        Slot slot;
        if (b5) {
            if (holder.getWidth() > holder.getHeight()) {
                holder.rotate();
            }
            if (this.currentHeight == 0) {
                this.currentHeight = holder.getHeight();
            }
            slot = new Slot(this.currentWidth, 0, holder.getWidth(), this.currentHeight);
            this.currentWidth += holder.getWidth();
        }
        else {
            slot = new Slot(0, this.currentHeight, this.currentWidth, holder.getHeight());
            this.currentHeight += holder.getHeight();
        }
        slot.addSlot(holder);
        this.stitchSlots.add(slot);
        return true;
    }
    
    static int access$000(final int n, final int n2) {
        return getMipmapDimension(n, n2);
    }
    
    public void addSprite(final TextureAtlasSprite textureAtlasSprite) {
        final Holder holder = new Holder(textureAtlasSprite, this.mipmapLevelStitcher);
        if (this.maxTileDimension > 0) {
            holder.setNewDimension(this.maxTileDimension);
        }
        this.setStitchHolders.add(holder);
    }
    
    public Stitcher(final int maxWidth, final int maxHeight, final boolean forcePowerOf2, final int maxTileDimension, final int mipmapLevelStitcher) {
        this.setStitchHolders = Sets.newHashSetWithExpectedSize(256);
        this.stitchSlots = Lists.newArrayListWithCapacity(256);
        this.mipmapLevelStitcher = mipmapLevelStitcher;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.forcePowerOf2 = forcePowerOf2;
        this.maxTileDimension = maxTileDimension;
    }
    
    public static class Slot
    {
        private static final String __OBFID = "CL_00001056";
        private Holder holder;
        private final int width;
        private final int height;
        private final int originX;
        private final int originY;
        private List subSlots;
        
        public int getOriginY() {
            return this.originY;
        }
        
        @Override
        public String toString() {
            return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
        }
        
        public void getAllStitchSlots(final List list) {
            if (this.holder != null) {
                list.add(this);
            }
            else if (this.subSlots != null) {
                final Iterator<Slot> iterator = (Iterator<Slot>)this.subSlots.iterator();
                while (iterator.hasNext()) {
                    iterator.next().getAllStitchSlots(list);
                }
            }
        }
        
        public Slot(final int originX, final int originY, final int width, final int height) {
            this.originX = originX;
            this.originY = originY;
            this.width = width;
            this.height = height;
        }
        
        public Holder getStitchHolder() {
            return this.holder;
        }
        
        public int getOriginX() {
            return this.originX;
        }
    }
    
    public static class Holder implements Comparable
    {
        private final int height;
        private static final String __OBFID = "CL_00001055";
        private boolean rotated;
        private final int mipmapLevelHolder;
        private final TextureAtlasSprite theTexture;
        private float scaleFactor;
        private final int width;
        
        public int getHeight() {
            return this.rotated ? Stitcher.access$000((int)(this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.access$000((int)(this.height * this.scaleFactor), this.mipmapLevelHolder);
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((Holder)o);
        }
        
        public Holder(final TextureAtlasSprite theTexture, final int mipmapLevelHolder) {
            this.scaleFactor = 1.0f;
            this.theTexture = theTexture;
            this.width = theTexture.getIconWidth();
            this.height = theTexture.getIconHeight();
            this.mipmapLevelHolder = mipmapLevelHolder;
            this.rotated = (Stitcher.access$000(this.height, mipmapLevelHolder) > Stitcher.access$000(this.width, mipmapLevelHolder));
        }
        
        public boolean isRotated() {
            return this.rotated;
        }
        
        public int compareTo(final Holder holder) {
            int n;
            if (this.getHeight() == holder.getHeight()) {
                if (this.getWidth() == holder.getWidth()) {
                    if (this.theTexture.getIconName() == null) {
                        return (holder.theTexture.getIconName() == null) ? 0 : -1;
                    }
                    return this.theTexture.getIconName().compareTo(holder.theTexture.getIconName());
                }
                else {
                    n = ((this.getWidth() < holder.getWidth()) ? 1 : -1);
                }
            }
            else {
                n = ((this.getHeight() < holder.getHeight()) ? 1 : -1);
            }
            return n;
        }
        
        public TextureAtlasSprite getAtlasSprite() {
            return this.theTexture;
        }
        
        public void rotate() {
            this.rotated = !this.rotated;
        }
        
        @Override
        public String toString() {
            return "Holder{width=" + this.width + ", height=" + this.height + '}';
        }
        
        public void setNewDimension(final int n) {
            if (this.width > n && this.height > n) {
                this.scaleFactor = n / (float)Math.min(this.width, this.height);
            }
        }
        
        public int getWidth() {
            return this.rotated ? Stitcher.access$000((int)(this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.access$000((int)(this.width * this.scaleFactor), this.mipmapLevelHolder);
        }
    }
}
