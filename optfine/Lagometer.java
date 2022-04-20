package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.profiler.*;
import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class Lagometer
{
    private static long memDiff;
    private static long[] timesChunkUpdate;
    private static long memStart;
    public static boolean active;
    public static TimerNano timerVisibility;
    private static long[] timesTerrain;
    private static long[] timesScheduledExecutables;
    private static GameSettings gameSettings;
    private static long[] timesChunkUpload;
    private static long[] timesVisibility;
    private static long memTimeDiffMs;
    private static long[] timesFrame;
    public static TimerNano timerScheduledExecutables;
    public static TimerNano timerChunkUpload;
    private static long[] timesTick;
    private static int memMbSec;
    private static long memLast;
    public static TimerNano timerTerrain;
    private static Profiler profiler;
    public static TimerNano timerTick;
    private static Minecraft mc;
    private static long renderTimeNano;
    private static long memTimeStartMs;
    private static boolean[] gcs;
    private static int numRecordedFrameTimes;
    private static long prevFrameTimeNano;
    public static TimerNano timerServer;
    private static long memTimeLast;
    public static TimerNano timerChunkUpdate;
    private static long[] timesServer;
    
    public static boolean isActive() {
        return Lagometer.active;
    }
    
    private static long getMemoryUsed() {
        final Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    
    public static void updateLagometer() {
        if (Lagometer.mc == null) {
            Lagometer.mc = Minecraft.getMinecraft();
            Lagometer.gameSettings = Lagometer.mc.gameSettings;
            Lagometer.profiler = Lagometer.mc.mcProfiler;
        }
        if (Lagometer.gameSettings.showDebugInfo && Lagometer.gameSettings.ofLagometer) {
            Lagometer.active = ((((0x0 ^ 0x61CA81EB) << 3 | 0x6E5951F) >> 4 ^ 0xEF59F4) != 0x0);
            final long nanoTime = System.nanoTime();
            if (Lagometer.prevFrameTimeNano == ((4309328514418511112L >> 13 | 0x13544940984FCL) ^ 0x2C4F66847AB5L ^ 0xFFFE2CDE66E652B6L)) {
                Lagometer.prevFrameTimeNano = nanoTime;
            }
            else {
                final int n = Lagometer.numRecordedFrameTimes & Lagometer.timesFrame.length - ((0x0 | 0x790ACF95) >> 1 ^ 0x3C8567CB);
                Lagometer.numRecordedFrameTimes += ((0x0 ^ 0x2C16EC59) >>> 2 ^ 0xB05BB17);
                final boolean updateMemoryAllocation = updateMemoryAllocation();
                Lagometer.timesFrame[n] = nanoTime - Lagometer.prevFrameTimeNano - Lagometer.renderTimeNano;
                Lagometer.timesTick[n] = Lagometer.timerTick.timeNano;
                Lagometer.timesScheduledExecutables[n] = Lagometer.timerScheduledExecutables.timeNano;
                Lagometer.timesChunkUpload[n] = Lagometer.timerChunkUpload.timeNano;
                Lagometer.timesChunkUpdate[n] = Lagometer.timerChunkUpdate.timeNano;
                Lagometer.timesVisibility[n] = Lagometer.timerVisibility.timeNano;
                Lagometer.timesTerrain[n] = Lagometer.timerTerrain.timeNano;
                Lagometer.timesServer[n] = Lagometer.timerServer.timeNano;
                Lagometer.gcs[n] = updateMemoryAllocation;
                TimerNano.access$000(Lagometer.timerTick);
                TimerNano.access$000(Lagometer.timerScheduledExecutables);
                TimerNano.access$000(Lagometer.timerVisibility);
                TimerNano.access$000(Lagometer.timerChunkUpdate);
                TimerNano.access$000(Lagometer.timerChunkUpload);
                TimerNano.access$000(Lagometer.timerTerrain);
                TimerNano.access$000(Lagometer.timerServer);
                Lagometer.prevFrameTimeNano = System.nanoTime();
            }
        }
        else {
            Lagometer.active = ((((0x6463B6AD ^ 0x138B9DD5) & 0xFDBD106 & 0x7934219) ^ 0x7800000) != 0x0);
            Lagometer.prevFrameTimeNano = (((((0x16303895CF29D13FL & 0x94D6ABE39DA0B84L) ^ 0x10E844DB9511L) & 0x93B8B316ED3L) >> 16 & 0x3A49645L) ^ 0xFFFFFFFFFFDFFFFEL);
        }
    }
    
    public static void showLagometer(final ScaledResolution scaledResolution) {
        if (Lagometer.gameSettings != null && Lagometer.gameSettings.ofLagometer) {
            final long nanoTime = System.nanoTime();
            GlStateManager.clear(((0xC5 & 0x51) | 0x22) >>> 4 ^ 0x106);
            GlStateManager.matrixMode(((4691 >> 4 | 0x6A) & 0x148) ^ 0x49 ^ 0x1600);
            GlStateManager.pushMatrix();
            GlStateManager.enableColorMaterial();
            GlStateManager.loadIdentity();
            GlStateManager.ortho(0.0, Lagometer.mc.displayWidth, Lagometer.mc.displayHeight, 0.0, 1000.0, 3000.0);
            GlStateManager.matrixMode(((424 >> 1 | 0xC2) >>> 2 & 0x1) >>> 1 ^ 0x1700);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, 0.0f, -2000.0f);
            GL11.glLineWidth(1.0f);
            GlStateManager.disableTexture2D();
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(((0x0 | 0xC9CD8D8) << 1 >>> 3 | 0x97ABAB) >>> 1 ^ 0x1DBDFDE, DefaultVertexFormats.POSITION_COLOR);
            for (int i = (1875207366 << 3 | 0x171538FC) ^ 0x7F3F3EFC; i < Lagometer.timesFrame.length; ++i) {
                final int n = (i - Lagometer.numRecordedFrameTimes & Lagometer.timesFrame.length - ((0 >> 4 ^ 0x51098D2C) >> 4 ^ 0x3FCF049 ^ 0x6EC689A)) * ((77 << 1 << 3 << 4 ^ 0xECD) << 2 ^ 0x10F50) / Lagometer.timesFrame.length + ((((0x6E & 0x13) | 0x0) >>> 4 & 0x695F7638) ^ 0x9B);
                final float n2 = (float)Lagometer.mc.displayHeight;
                if (Lagometer.gcs[i]) {
                    renderTime(i, Lagometer.timesFrame[i], n, n / ((((true & false) ? 1 : 0) | 0x7072AC39) << 2 ^ 0x390A213A ^ 0xF8C091DC), (1348042770 >>> 2 ^ 0x133633B2) << 2 ^ 0x1C814ED8, n2, worldRenderer);
                }
                else {
                    renderTime(i, Lagometer.timesFrame[i], n, n, n, n2, worldRenderer);
                    final float n3 = n2 - renderTime(i, Lagometer.timesServer[i], n / ((0x0 | 0x68822FCB) << 3 >> 1 ^ 0x2208BF2E), n / (((0x0 | 0x15091BBF | 0xA959430) ^ 0x1B293253 ^ 0x41BEB37) << 2 ^ 0x2BD1B6E), n / ((1 << 1 & 0x1) ^ 0x2), n2, worldRenderer);
                    final float n4 = n3 - renderTime(i, Lagometer.timesTerrain[i], (665668670 << 2 & 0x432532E4) ^ 0x22530E0, n, (1794160929 >> 2 | 0x9E5B381) ^ 0xED3D020 ^ 0x152E6FE9, n3, worldRenderer);
                    final float n5 = n4 - renderTime(i, Lagometer.timesVisibility[i], n, n, ((0x3CC95C64 ^ 0x21C99DEC ^ 0x18F602E1) & 0x856ABA) ^ 0x3E2A62 ^ 0xBA684A, n4, worldRenderer);
                    final float n6 = n5 - renderTime(i, Lagometer.timesChunkUpdate[i], n, (((0x60DE7A25 | 0x57206967) << 2 & 0x6CEA4402) | 0x38B36C04) ^ 0x7CFB6C04, 2015440320 >>> 4 >> 1 ^ 0x3C1096E, n5, worldRenderer);
                    final float n7 = n6 - renderTime(i, Lagometer.timesChunkUpload[i], n, 486102252 >> 4 << 2 ^ 0x582CB1C ^ 0x2BC9E24, n, n6, worldRenderer);
                    final float n8 = n7 - renderTime(i, Lagometer.timesScheduledExecutables[i], 1793454256 >>> 3 >>> 3 >> 3 ^ 0x3572F8, (((0x415931B5 & 0x36BA37C2) | 0x60C05) & 0xFACF7) ^ 0xE2C85, n, n7, worldRenderer);
                    final float n9 = n8 - renderTime(i, Lagometer.timesTick[i], (585700922 >>> 3 << 1 | 0x8A8E915 | 0x528BC13) ^ 0xDBAFD9F, n, n, n8, worldRenderer);
                }
            }
            instance.draw();
            GlStateManager.matrixMode((0x795 & 0x2AF) >>> 1 ^ 0x1643);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode((((0x725 | 0x3E4) & 0x1A) ^ 0x32B468EE) >> 1 ^ 0x195A2377);
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            final float limit = Config.limit(1.0f - (float)((System.currentTimeMillis() - Lagometer.memTimeStartMs) / 1000.0), 0.0f, 1.0f);
            final int n10 = (int)(170.0f + limit * 85.0f) << ((0x0 | 0x133F4C65) >> 2 >> 4 >> 1 ^ 0x267E88) | (int)(100.0f + limit * 55.0f) << ((0x2 & 0x1 & 0x773563DE) ^ 0x8) | (int)(10.0f + limit * 10.0f);
            final int n11 = (((0x9 ^ 0x5) >>> 1 >> 3 & 0x6B60A116) >> 1 ^ 0x200) / scaledResolution.getScaleFactor() + ((0 << 1 ^ 0xFE52DA6) >>> 2 << 3 ^ 0x1FCA5B4A);
            final int n12 = Lagometer.mc.displayHeight / scaledResolution.getScaleFactor() - (7 >> 1 >> 2 >>> 3 ^ 0x8);
            final GuiIngame ingameGUI = Lagometer.mc.ingameGUI;
            Gui.drawRect(n11 - ((0x0 | 0x37C5000A) >>> 4 << 3 >>> 4 ^ 0x114086F ^ 0xAA206E), n12 - ((0x0 & 0x103745A5 & 0x52804794) >> 2 ^ 0x1), n11 + (46 >>> 4 << 1 << 2 ^ 0x22), n12 + (6 >>> 3 << 2 ^ 0xA), ((0x6CA04EC5 | 0x42B2B487) & 0x36D261F5 & 0x998E57E) ^ 0xA0C03014);
            Lagometer.mc.fontRendererObj.drawString(AntiTabioProtection_ILoveNekosAntiTabioProtection_ILoveNekosAntiTabioProtection_ILoveNekosAntiTabioProtection_ILoveNekos.\u267b\u28b9\u2898\u19ae\u1ba0\u242a\u2d52\u27d9\u160b\u1ca0\u2705\u2bbb\u2570\u2f2e("\ua1e5", "}\ua7f6\ua4a1\ua077\ua90c\u7d78\u8e4f\u2435\u8b1b\u3476u\ua7e9\ua4b8\ua074\ua911\u7d73\u8e58", (0x6BF0A655 ^ 0x5F79F316) << 3 ^ 0x21A09DD2, (111260659 << 3 >>> 3 | 0x2BFE64E) ^ 0x2B53F4F ^ 0x515D10F4, (0x9E99DA | 0x91C7E) >>> 3 >> 4 ^ 0x11DE1FDD, 467014 >> 1 >>> 2 >>> 1 << 3 ^ 0x389A4B) + Lagometer.memMbSec + AntiTabioProtection_ILoveNekosAntiTabioProtection_ILoveNekosAntiTabioProtection_ILoveNekosAntiTabioProtection_ILoveNekos.\u267b\u28b9\u2898\u19ae\u1ba0\u242a\u2d52\u27d9\u160b\u1ca0\u2705\u2bbb\u2570\u2f2e("\ua1e5\udf2b\ua187\udf49\ua1b6", "}\ua7f6\ua4a1\ua077\ua90c\u7d78\u8e4f\u2435\u8b1b\u3476u\ua7e9\ua4b8\ua074\ua911\u7d73\u8e58", (0x718A579C & 0x4A897B3C) << 1 << 2 ^ 0x81A8AF2A, (1405706453 << 2 >> 2 | 0xF3C5A6C) ^ 0x1C81079B ^ 0x562A6A24, ((0x4834223 ^ 0x2A11218) | 0x272FF8E) ^ 0x16C24E2E, ((((0x1D037 & 0xD009) ^ 0x83CF) | 0x39BA) & 0x58ED) ^ 0x391185), n11, n12, n10);
            Lagometer.renderTimeNano = System.nanoTime() - nanoTime;
        }
    }
    
    public static boolean updateMemoryAllocation() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long memoryUsed = getMemoryUsed();
        int n = ((0x54BA5AEF & 0x38328AF) >>> 2 & 0x69611) << 1 ^ 0x10402;
        if (memoryUsed < Lagometer.memLast) {
            final int memMbSec = (int)(Lagometer.memDiff / 1000000.0 / (Lagometer.memTimeDiffMs / 1000.0));
            if (memMbSec > 0) {
                Lagometer.memMbSec = memMbSec;
            }
            Lagometer.memTimeStartMs = currentTimeMillis;
            Lagometer.memStart = memoryUsed;
            Lagometer.memTimeDiffMs = ((3643457884063322417L << 24 ^ 0x5119A95CB4477454L) >>> 22 >>> 31 ^ 0x7AAL);
            Lagometer.memDiff = (((0x6A86692C83D92D65L ^ 0x37F37006E1B64F5EL) << 22 & 0x4801CD1E5B8F9838L) ^ 0x480089180A800000L);
            n = ((0 >> 4 | 0x7259503C) ^ 0x7259503D);
        }
        else {
            Lagometer.memTimeDiffMs = currentTimeMillis - Lagometer.memTimeStartMs;
            Lagometer.memDiff = memoryUsed - Lagometer.memStart;
        }
        Lagometer.memTimeLast = currentTimeMillis;
        Lagometer.memLast = memoryUsed;
        return n != 0;
    }
    
    static {
        Lagometer.active = ((1291743639 << 1 >> 2 ^ 0xE67F38CB) != 0x0);
        Lagometer.timerTick = new TimerNano();
        Lagometer.timerScheduledExecutables = new TimerNano();
        Lagometer.timerChunkUpload = new TimerNano();
        Lagometer.timerChunkUpdate = new TimerNano();
        Lagometer.timerVisibility = new TimerNano();
        Lagometer.timerTerrain = new TimerNano();
        Lagometer.timerServer = new TimerNano();
        Lagometer.timesFrame = new long[477 >>> 1 >>> 3 >> 4 ^ 0x201];
        Lagometer.timesTick = new long[(0 >>> 4 & 0x101A2212) ^ 0x6465F8E3 ^ 0x5819BA77 ^ 0x3C7C4094];
        Lagometer.timesScheduledExecutables = new long[(44 >>> 4 & 0x0) >>> 2 >> 1 ^ 0x200];
        Lagometer.timesChunkUpload = new long[((0x19B ^ 0x11C) | 0x4A) >>> 2 << 1 >> 2 ^ 0x219];
        Lagometer.timesChunkUpdate = new long[(0x17E | 0x56) << 1 >> 4 ^ 0x22F];
        Lagometer.timesVisibility = new long[(365 >> 3 << 2 >> 1 | 0x41) ^ 0x25B];
        Lagometer.timesTerrain = new long[(17 >> 2 ^ 0x0) >>> 2 ^ 0x201];
        Lagometer.timesServer = new long[252 >> 1 ^ 0x45 ^ 0xC ^ 0x237];
        Lagometer.gcs = new boolean[(132 >> 4 | 0x0 | 0x5) >> 3 ^ 0x201];
        Lagometer.numRecordedFrameTimes = ((((0x575E7F66 & 0x28EF205E) ^ 0x35E770) | 0x1F0650) ^ 0x7FC776);
        Lagometer.prevFrameTimeNano = ((((0x231A1F1446DD333CL & 0x79B914ED8736B6AL) ^ 0x7684A45A3792E1L) | 0x6145D304F4151EL) ^ 0xFC922A0CE1094A20L);
        Lagometer.renderTimeNano = ((0x90D6F1CF5AC8BABL ^ 0x86E2B3BAF065A9BL) >> 24 ^ 0x16344275AL);
        Lagometer.memTimeStartMs = System.currentTimeMillis();
        Lagometer.memStart = getMemoryUsed();
        Lagometer.memTimeLast = Lagometer.memTimeStartMs;
        Lagometer.memLast = Lagometer.memStart;
        Lagometer.memTimeDiffMs = (((0x0L & 0x7567D9602DD365B7L) >>> 24 & 0x1F5018FB46340D58L) ^ 0x1L);
        Lagometer.memDiff = (4068109843269828998L >> 24 << 29 << 22 << 14 ^ 0x0L);
        Lagometer.memMbSec = ((0x4BD05A2B ^ 0x4526E1DE ^ 0xBB1271D) << 2 ^ 0x151E73A0);
    }
    
    private static long renderTime(final int n, final long n2, final int n3, final int n4, final int n5, final float n6, final WorldRenderer worldRenderer) {
        final long n7 = n2 / ((0x180DDL & 0x41C8L) ^ 0xA7L ^ 0x30D2FL);
        if (n7 < (1L << 6 ^ 0x38L ^ 0x7BL)) {
            return (6249735666340812655L >> 3 >> 14 >> 7 & 0x59316F72DL) ^ 0x3781C3F6DL ^ 0x7EB184E61L;
        }
        worldRenderer.pos(n + 0.5f, n6 - n7 + 0.5f, 0.0).color(n3, n4, n5, (0xC8 & 0x16) >> 4 >> 2 >>> 1 >> 1 ^ 0xFF).endVertex();
        worldRenderer.pos(n + 0.5f, n6 + 0.5f, 0.0).color(n3, n4, n5, ((57 >>> 4 >> 1 | 0x0) & 0x0) ^ 0xFF).endVertex();
        return n7;
    }
    
    public static class TimerNano
    {
        public long timeNano;
        public long timeStartNano;
        
        private void reset() {
            this.timeNano = 0L;
            this.timeStartNano = 0L;
        }
        
        static void access$000(final TimerNano timerNano) {
            timerNano.reset();
        }
        
        public void start() {
            if (Lagometer.active && this.timeStartNano == 0L) {
                this.timeStartNano = System.nanoTime();
            }
        }
        
        public void end() {
            if (Lagometer.active && this.timeStartNano != 0L) {
                this.timeNano += System.nanoTime() - this.timeStartNano;
                this.timeStartNano = 0L;
            }
        }
        
        public TimerNano() {
            this.timeStartNano = 0L;
            this.timeNano = 0L;
        }
    }
}
