package net.minecraft.client.resources.data;

public class FontMetadataSection implements IMetadataSection
{
    private final float[] charWidths;
    private final float[] charSpacings;
    private final float[] charLefts;
    
    public FontMetadataSection(final float[] charWidths, final float[] charLefts, final float[] charSpacings) {
        this.charWidths = charWidths;
        this.charLefts = charLefts;
        this.charSpacings = charSpacings;
    }
}
