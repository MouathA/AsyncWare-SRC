package dupa;

public interface Easing
{
    public static final Easing SINE_OUT = Easing::lambda$static$14;
    public static final Easing SINE_IN_OUT = Easing::lambda$static$15;
    public static final Easing QUARTIC_OUT = Easing::lambda$static$8;
    public static final Easing CUBIC_IN_OUT = Easing::lambda$static$6;
    public static final Elastic ELASTIC_IN = new ElasticIn();
    public static final Easing LINEAR = Easing::lambda$static$0;
    public static final Easing CIRC_IN_OUT = Easing::lambda$static$21;
    public static final Easing EXPO_IN = Easing::lambda$static$16;
    public static final Easing CUBIC_IN = Easing::lambda$static$4;
    public static final Easing BOUNCE_OUT = Easing::lambda$static$22;
    public static final Easing SINE_IN = Easing::lambda$static$13;
    public static final Easing BOUNCE_IN_OUT = Easing::lambda$static$24;
    public static final Easing QUINTIC_IN_OUT = Easing::lambda$static$12;
    public static final Elastic ELASTIC_IN_OUT = new ElasticInOut();
    public static final Easing BOUNCE_IN = Easing::lambda$static$23;
    public static final Easing EXPO_IN_OUT = Easing::lambda$static$18;
    public static final Easing QUINTIC_OUT = Easing::lambda$static$11;
    public static final Easing CIRC_OUT = Easing::lambda$static$20;
    public static final Back BACK_IN = new BackIn();
    public static final Easing QUARTIC_IN = Easing::lambda$static$7;
    public static final Easing QUINTIC_IN = Easing::lambda$static$10;
    public static final Easing QUAD_IN = Easing::lambda$static$1;
    public static final Elastic ELASTIC_OUT = new ElasticOut();
    public static final Easing QUAD_IN_OUT = Easing::lambda$static$3;
    public static final Easing QUAD_OUT = Easing::lambda$static$2;
    public static final Easing QUARTIC_IN_OUT = Easing::lambda$static$9;
    public static final Easing CUBIC_OUT = Easing::lambda$static$5;
    public static final Easing EXPO_OUT = Easing::lambda$static$17;
    public static final Back BACK_IN_OUT = new BackInOut();
    public static final Easing CIRC_IN = Easing::lambda$static$19;
    public static final Back BACK_OUT = new BackOut();
    
    default float lambda$static$16(final float n, final float n2, final float n3, final float n4) {
        return (n == 0.0f) ? n2 : (n3 * (float)Math.pow(2.0, 10.0f * (n / n4 - 1.0f)) + n2);
    }
    
    default float lambda$static$7(float n, final float n2, final float n3, final float n4) {
        return n3 * (n /= n4) * n * n * n + n2;
    }
    
    default float lambda$static$18(float n, final float n2, final float n3, final float n4) {
        if (n == 0.0f) {
            return n2;
        }
        if (n == n4) {
            return n2 + n3;
        }
        if ((n /= n4 / 2.0f) < 1.0f) {
            return n3 / 2.0f * (float)Math.pow(2.0, 10.0f * (n - 1.0f)) + n2;
        }
        return n3 / 2.0f * (-(float)Math.pow(2.0, -10.0f * --n) + 2.0f) + n2;
    }
    
    default float lambda$static$17(final float n, final float n2, final float n3, final float n4) {
        return (n == n4) ? (n2 + n3) : (n3 * (-(float)Math.pow(2.0, -10.0f * n / n4) + 1.0f) + n2);
    }
    
    default float lambda$static$15(final float n, final float n2, final float n3, final float n4) {
        return -n3 / 2.0f * ((float)Math.cos(3.141592653589793 * n / n4) - 1.0f) + n2;
    }
    
    default float lambda$static$21(float n, final float n2, final float n3, final float n4) {
        if ((n /= n4 / 2.0f) < 1.0f) {
            return -n3 / 2.0f * ((float)Math.sqrt(1.0f - n * n) - 1.0f) + n2;
        }
        return n3 / 2.0f * ((float)Math.sqrt(1.0f - (n -= 2.0f) * n) + 1.0f) + n2;
    }
    
    default float lambda$static$19(float n, final float n2, final float n3, final float n4) {
        return -n3 * ((float)Math.sqrt(1.0f - (n /= n4) * n) - 1.0f) + n2;
    }
    
    default float lambda$static$24(final float n, final float n2, final float n3, final float n4) {
        if (n < n4 / 2.0f) {
            return Easing.BOUNCE_IN.ease(n * 2.0f, 0.0f, n3, n4) * 0.5f + n2;
        }
        return Easing.BOUNCE_OUT.ease(n * 2.0f - n4, 0.0f, n3, n4) * 0.5f + n3 * 0.5f + n2;
    }
    
    default float lambda$static$6(float n, final float n2, final float n3, final float n4) {
        if ((n /= n4 / 2.0f) < 1.0f) {
            return n3 / 2.0f * n * n * n + n2;
        }
        return n3 / 2.0f * ((n -= 2.0f) * n * n + 2.0f) + n2;
    }
    
    default float lambda$static$9(float n, final float n2, final float n3, final float n4) {
        if ((n /= n4 / 2.0f) < 1.0f) {
            return n3 / 2.0f * n * n * n * n + n2;
        }
        return -n3 / 2.0f * ((n -= 2.0f) * n * n * n - 2.0f) + n2;
    }
    
    float ease(final float p0, final float p1, final float p2, final float p3);
    
    default float lambda$static$23(final float n, final float n2, final float n3, final float n4) {
        return n3 - Easing.BOUNCE_OUT.ease(n4 - n, 0.0f, n3, n4) + n2;
    }
    
    default float lambda$static$20(float n, final float n2, final float n3, final float n4) {
        return n3 * (float)Math.sqrt(1.0f - (n = n / n4 - 1.0f) * n) + n2;
    }
    
    default float lambda$static$5(float n, final float n2, final float n3, final float n4) {
        return n3 * ((n = n / n4 - 1.0f) * n * n + 1.0f) + n2;
    }
    
    default float lambda$static$3(float n, final float n2, final float n3, final float n4) {
        if ((n /= n4 / 2.0f) < 1.0f) {
            return n3 / 2.0f * n * n + n2;
        }
        return -n3 / 2.0f * (--n * (n - 2.0f) - 1.0f) + n2;
    }
    
    default float lambda$static$13(final float n, final float n2, final float n3, final float n4) {
        return -n3 * (float)Math.cos(n / n4 * 1.5707963267948966) + n3 + n2;
    }
    
    default float lambda$static$2(float n, final float n2, final float n3, final float n4) {
        return -n3 * (n /= n4) * (n - 2.0f) + n2;
    }
    
    default float lambda$static$8(float n, final float n2, final float n3, final float n4) {
        return -n3 * ((n = n / n4 - 1.0f) * n * n * n - 1.0f) + n2;
    }
    
    default float lambda$static$10(float n, final float n2, final float n3, final float n4) {
        return n3 * (n /= n4) * n * n * n * n + n2;
    }
    
    default float lambda$static$1(float n, final float n2, final float n3, final float n4) {
        return n3 * (n /= n4) * n + n2;
    }
    
    default float lambda$static$11(float n, final float n2, final float n3, final float n4) {
        return n3 * ((n = n / n4 - 1.0f) * n * n * n * n + 1.0f) + n2;
    }
    
    default float lambda$static$14(final float n, final float n2, final float n3, final float n4) {
        return n3 * (float)Math.sin(n / n4 * 1.5707963267948966) + n2;
    }
    
    default float lambda$static$22(float n, final float n2, final float n3, final float n4) {
        if ((n /= n4) < 0.36363637f) {
            return n3 * (7.5625f * n * n) + n2;
        }
        if (n < 0.72727275f) {
            return n3 * (7.5625f * (n -= 0.54545456f) * n + 0.75f) + n2;
        }
        if (n < 0.90909094f) {
            return n3 * (7.5625f * (n -= 0.8181818f) * n + 0.9375f) + n2;
        }
        return n3 * (7.5625f * (n -= 0.95454544f) * n + 0.984375f) + n2;
    }
    
    default float lambda$static$12(float n, final float n2, final float n3, final float n4) {
        if ((n /= n4 / 2.0f) < 1.0f) {
            return n3 / 2.0f * n * n * n * n * n + n2;
        }
        return n3 / 2.0f * ((n -= 2.0f) * n * n * n * n + 2.0f) + n2;
    }
    
    default float lambda$static$4(float n, final float n2, final float n3, final float n4) {
        return n3 * (n /= n4) * n * n + n2;
    }
    
    default float lambda$static$0(final float n, final float n2, final float n3, final float n4) {
        return n3 * n / n4 + n2;
    }
    
    public static class BackOut extends Back
    {
        public BackOut(final float n) {
            super(n);
        }
        
        public BackOut() {
        }
        
        @Override
        public float ease(float n, final float n2, final float n3, final float n4) {
            final float overshoot = this.getOvershoot();
            return n3 * ((n = n / n4 - 1.0f) * n * ((overshoot + 1.0f) * n + overshoot) + 1.0f) + n2;
        }
    }
    
    public abstract static class Back implements Easing
    {
        public static final float DEFAULT_OVERSHOOT = 1.70158f;
        private float overshoot;
        
        public Back() {
            this(1.70158f);
        }
        
        public void setOvershoot(final float overshoot) {
            this.overshoot = overshoot;
        }
        
        public float getOvershoot() {
            return this.overshoot;
        }
        
        public Back(final float overshoot) {
            this.overshoot = overshoot;
        }
    }
    
    public static class ElasticOut extends Elastic
    {
        @Override
        public float ease(float n, final float n2, final float n3, final float n4) {
            float amplitude = this.getAmplitude();
            float period = this.getPeriod();
            if (n == 0.0f) {
                return n2;
            }
            if ((n /= n4) == 1.0f) {
                return n2 + n3;
            }
            if (period == 0.0f) {
                period = n4 * 0.3f;
            }
            float n5;
            if (amplitude < Math.abs(n3)) {
                amplitude = n3;
                n5 = period / 4.0f;
            }
            else {
                n5 = period / 6.2831855f * (float)Math.asin(n3 / amplitude);
            }
            return amplitude * (float)Math.pow(2.0, -10.0f * n) * (float)Math.sin((n * n4 - n5) * 6.283185307179586 / period) + n3 + n2;
        }
        
        public ElasticOut() {
        }
        
        public ElasticOut(final float n, final float n2) {
            super(n, n2);
        }
    }
    
    public abstract static class Elastic implements Easing
    {
        private float amplitude;
        private float period;
        
        public Elastic(final float amplitude, final float period) {
            this.amplitude = amplitude;
            this.period = period;
        }
        
        public Elastic() {
            this(-1.0f, 0.0f);
        }
        
        public float getPeriod() {
            return this.period;
        }
        
        public void setPeriod(final float period) {
            this.period = period;
        }
        
        public float getAmplitude() {
            return this.amplitude;
        }
        
        public void setAmplitude(final float amplitude) {
            this.amplitude = amplitude;
        }
    }
    
    public static class BackInOut extends Back
    {
        public BackInOut(final float n) {
            super(n);
        }
        
        @Override
        public float ease(float n, final float n2, final float n3, final float n4) {
            final float overshoot = this.getOvershoot();
            if ((n /= n4 / 2.0f) < 1.0f) {
                final float n5 = n3 / 2.0f;
                final float n6 = n * n;
                final float n7 = (float)(overshoot * 1.525);
                return n5 * (n6 * ((n7 + 1.0f) * n - n7)) + n2;
            }
            final float n8 = n3 / 2.0f;
            final float n9 = (n -= 2.0f) * n;
            final float n10 = (float)(overshoot * 1.525);
            return n8 * (n9 * ((n10 + 1.0f) * n + n10) + 2.0f) + n2;
        }
        
        public BackInOut() {
        }
    }
    
    public static class BackIn extends Back
    {
        public BackIn() {
        }
        
        @Override
        public float ease(float n, final float n2, final float n3, final float n4) {
            final float overshoot = this.getOvershoot();
            return n3 * (n /= n4) * n * ((overshoot + 1.0f) * n - overshoot) + n2;
        }
        
        public BackIn(final float n) {
            super(n);
        }
    }
    
    public static class ElasticIn extends Elastic
    {
        public ElasticIn(final float n, final float n2) {
            super(n, n2);
        }
        
        @Override
        public float ease(float n, final float n2, final float n3, final float n4) {
            float amplitude = this.getAmplitude();
            float period = this.getPeriod();
            if (n == 0.0f) {
                return n2;
            }
            if ((n /= n4) == 1.0f) {
                return n2 + n3;
            }
            if (period == 0.0f) {
                period = n4 * 0.3f;
            }
            float n5;
            if (amplitude < Math.abs(n3)) {
                amplitude = n3;
                n5 = period / 4.0f;
            }
            else {
                n5 = period / 6.2831855f * (float)Math.asin(n3 / amplitude);
            }
            return -(amplitude * (float)Math.pow(2.0, 10.0f * --n) * (float)Math.sin((n * n4 - n5) * 6.283185307179586 / period)) + n2;
        }
        
        public ElasticIn() {
        }
    }
    
    public static class ElasticInOut extends Elastic
    {
        @Override
        public float ease(float n, final float n2, final float n3, final float n4) {
            float amplitude = this.getAmplitude();
            float period = this.getPeriod();
            if (n == 0.0f) {
                return n2;
            }
            if ((n /= n4 / 2.0f) == 2.0f) {
                return n2 + n3;
            }
            if (period == 0.0f) {
                period = n4 * 0.45000002f;
            }
            float n5;
            if (amplitude < Math.abs(n3)) {
                amplitude = n3;
                n5 = period / 4.0f;
            }
            else {
                n5 = period / 6.2831855f * (float)Math.asin(n3 / amplitude);
            }
            if (n < 1.0f) {
                return -0.5f * (amplitude * (float)Math.pow(2.0, 10.0f * --n) * (float)Math.sin((n * n4 - n5) * 6.283185307179586 / period)) + n2;
            }
            return amplitude * (float)Math.pow(2.0, -10.0f * --n) * (float)Math.sin((n * n4 - n5) * 6.283185307179586 / period) * 0.5f + n3 + n2;
        }
        
        public ElasticInOut(final float n, final float n2) {
            super(n, n2);
        }
        
        public ElasticInOut() {
        }
    }
}
