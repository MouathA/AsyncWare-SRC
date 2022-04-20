/*
 * Original shader from: https://www.shadertoy.com/view/flf3Rr
 */

#ifdef GL_ES
precision mediump float;
#endif

// glslsandbox uniforms
uniform float time;
uniform vec2 resolution;

// shadertoy emulation
#define iTime time
#define iResolution resolution

// --------[ Original ShaderToy begins here ]---------- //
const float BPM = 100.;
const float BPS = BPM/60.;
const float RBPS = 60./BPM;
#define PI 3.1415926


// from https://www.shadertoy.com/view/4ts3z2
vec2 add = vec2(1.0, 0.0);
#define MOD2 vec2(.16632,.17369)
#define MOD3 vec3(.16532,.17369,.15787)

float noise(float x) {
    vec2 p2 = fract(vec2(x) * MOD2);
    p2 += dot(p2.yx, p2.xy+19.19);
    return fract(p2.x * p2.y);
}
vec2 hash22(vec2 p)
{
    vec3 p3 = fract(vec3(p.xyx) * MOD3);
    p3 += dot(p3.zxy, p3.yxz+19.19);
    return fract(vec2(p3.x * p3.y, p3.z*p3.x));
}
vec2 Noise22(vec2 x)
{
    vec2 p = floor(x);
    vec2 f = fract(x);
    f = f*f*(3.0-2.0*f);
    vec2 res = mix(mix( hash22(p),          hash22(p + add.xy),f.x),
    mix( hash22(p + add.yx), hash22(p + add.xx),f.x),f.y);
    return res-.5;
}
vec2 FBM21(float v)
{
    vec2 r = vec2(0.0);
    vec2 x = vec2(v, v*1.3+23.333);
    float a = .6;
    for (int i = 0; i < 8; i++)
    {
        r += Noise22(x * a) / a;
        a += a;
    }
    return r;
}

float msign(in float x) { return (x<0.0)?-1.0:1.0; }
// sdEllipse by iq - https://www.shadertoy.com/view/4sS3zz
float sd_ellipse( vec2 p, in vec2 ab )
{
    //if( ab.x==ab.y ) return length(p)-ab.x;


    p = abs( p );
    if( p.x>p.y ){ p=p.yx; ab=ab.yx; }

    float l = ab.y*ab.y - ab.x*ab.x;

    float m = ab.x*p.x/l;
    float n = ab.y*p.y/l;
    float m2 = m*m;
    float n2 = n*n;

    float c = (m2+n2-1.0)/3.0;
    float c3 = c*c*c;

    float d = c3 + m2*n2;
    float q = d  + m2*n2;
    float g = m  + m *n2;

    float co;

    if( d<0.0 )
    {
        float h = acos(q/c3)/3.0;
        float s = cos(h) + 2.0;
        float t = sin(h) * sqrt(3.0);
        float rx = sqrt( m2-c*(s+t) );
        float ry = sqrt( m2-c*(s-t) );
        co = ry + sign(l)*rx + abs(g)/(rx*ry);
    }
    else
    {
        float h = 2.0*m*n*sqrt(d);
        float s = msign(q+h)*pow( abs(q+h), 1.0/3.0 );
        float t = msign(q-h)*pow( abs(q-h), 1.0/3.0 );
        float rx = -(s+t) - c*4.0 + 2.0*m2;
        float ry =  (s-t)*sqrt(3.0);
        float rm = sqrt( rx*rx + ry*ry );
        co = ry/sqrt(rm-rx) + 2.0*g/rm;
    }
    co = (co-m)/2.0;

    float si = sqrt( max(1.0-co*co,0.0) );

    vec2 r = ab * vec2(co,si);

    return length(r-p) * msign(p.y-r.y);
}


// opSmoothUnion and opSmoothSubtraction by iq - https://www.shadertoy.com/view/lt3BW2
float s_min( float d1, float d2, float k ) {
    float h = max(k-abs(d1-d2),0.0);
    return min(d1, d2) - h*h*0.25/k;
}

float s_sub( float d1, float d2, float k ) {
    float h = max(k-abs(-d1-d2),0.0);
    return max(-d1, d2) + h*h*0.25/k;
}

mat2 rot(float a) {
    float s = sin(a);
    float c = cos(a);
    return mat2(c, -s, s, c);
}

float edge(float a, vec2 p) {
    p *= rot(a);
    return p.y;
}

vec4 blend(vec4 a, vec4 b) {
    return vec4(a.w*a.xyz + (1.-a.w)*b.xyz, max(a.w, b.w));
}

float sd_amogus(vec2 p) {

    // who the heck would use bezier curves for that? not me.

    float b = sd_ellipse(p, vec2(.5,1.66));   // body
    b = s_sub(edge(PI*0.97, p)+0.75, b, 0.4); // carving away some edges
    b = s_sub(edge(PI*0.6, p)+0.42, b, 0.1);
    b = s_sub(edge(PI*0.54, p)+0.37, b, 0.1);
    b = s_sub(edge(PI*0.07, p)+0.55, b, 0.1);

    float r = b;

    float l = sd_ellipse(p-vec2(-0.02,-0.4),vec2(.21,0.2625)); // seperation between the legs, starting with an ellipse
    l = s_sub(edge(PI, p) - 0.2, l, 0.1);                      // again, carvin away some parts of the ellipse
    l = s_sub(edge(PI*0.4, p)+0.32, l, 0.1);

    r = s_sub(l, r, 0.1); // subtracting that modified ellipse from the body

    float f1 = sd_ellipse((p*rot(PI*0.1)-vec2(-0.3,-0.53)),vec2(1./0.6*0.14,1.0*0.14)); // left foot, again an ellipse
    f1 = s_sub(edge(PI*1.09, p)-0.45, f1, 0.1);                                         // make the footh a bit steeper in the front

    r = s_min(f1, r, 0.07);

    float f2 = sd_ellipse((p-vec2(0.22,-0.6)),vec2(1./0.52*0.14,1.0*0.14)); // right foot
    f2 = s_sub(edge(PI*1.1, p)-0.35, f2, 0.2);                    // flatten the foot in the front
    //f2 = s_sub(edge(PI*0.1, p)+0.75, f2, 0.2);

    r = s_min(f2, r, 0.05);
    r = s_sub(edge(PI*-0.05, p)+0.75, r, 0.2); // flatten the underside of the right foot

    // maybe i should have used bezier curves
    return r;
}

vec4 visor(vec2 p) {
    float l = sd_ellipse(p-vec2(0.0,0.45), vec2(0.4,0.1));
    l = s_sub(edge(PI*0.45, p)+0.1, l, 0.1);
    l = s_sub(edge(PI*1.48, p)+0.35, l, 0.1);

    vec4 e1 = vec4(vec3(0.467,0.549,0.733), smoothstep(0.0,0.025,sd_ellipse(p-vec2(0.1,0.47), vec2(0.2,0.05))));
    vec4 e2 = vec4(vec3(0.686,0.776,0.871), smoothstep(0.0,0.025,sd_ellipse(p-vec2(0.1,0.47), vec2(0.1,0.025))));
    vec4 e3 = vec4(vec3(0.910,0.929,0.969), smoothstep(0.0,0.025,sd_ellipse(p-vec2(0.1,0.47), vec2(0.05,0.0125))));
    vec4 c = blend(e1, e2);
    c = blend(c, e3);


    float area = smoothstep(0.03,0.0,l);
    float outline = smoothstep(0.01,0.033,abs(l));

    return vec4(vec3(outline)*c.xyz, area);
    //return c;
}


vec4 amogus(vec2 p) {
    vec2 e = vec2(0.01,0.0);
    vec2 d = vec2(sd_amogus(p+e.xy)-sd_amogus(p-e.xy), sd_amogus(p+e.yx)-sd_amogus(p-e.yx))/e.x*0.5;
    float l = sd_amogus(p);
    float nl = l/length(d);


    float area = smoothstep(0.03,0.0,nl);
    float outline = smoothstep(0.01,0.033,abs(nl));


    //vec3 col = d_to_color(l);
    vec4 col = vec4(outline*vec3(0.898,0.933,1.000), area);

    vec4 vis = visor(p);

    col = blend(vis, col);
    return col;

}

float sdChecker(in vec2 p) {
    p = fract(p*0.5-0.5)*2.-1.;
    p = abs(p);
    float adj = max(abs(p.x-1.0)-0.5,abs(p.y-1.0)-0.5);
    return min(max(p.x-0.5,p.y-0.5), adj);
}

vec3 d_to_color(float d) {
    vec3 col = vec3(1.0) - sign(d)*vec3(0.1,0.4,0.7);
    col *= 1.0 - exp(-2.0*abs(d));
    col *= 0.8 + 0.2*cos(120.0*d);
    return mix( col, vec3(1.0), 1.0-smoothstep(0.0,0.02,abs(d)) );
}

vec2 transform(vec2 p) {
    float t = (iTime-1.0) * BPS * 0.5; // enter bps here
    float d = smoothstep(6.0,7.0,t); // intensity of the distortion/movement

    vec2 offs = p+vec2(sin(t*PI*2.), cos(t*PI))*0.2*(0.5 + 0.5 * d);
    offs *= rot(sin(t*PI)*PI*0.1);
    p *= (1.-pow(abs(sin(t*PI*2.)), 10.))*(0.25 + 0.25*d)-pow(length(p), 1.)*(0.5+0.5*d)*0.25;
    p += offs;
    return p;
}

void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = (fragCoord*2.-iResolution.xy)/iResolution.y;
    uv = transform(uv);

    vec4 col = amogus(uv);
    vec4 bg = vec4(vec3(0.910,0.129,0.404),1.0);
    bg.rgb *= 0.75 + 0.25 * smoothstep(0.0,0.05,sdChecker(uv*2.));
    col = blend(col, bg);

    vec2 nuv = fragCoord/iResolution.xy*2.-1.;
    col *= 0.6+0.4*(1.-nuv.x*nuv.x)*(1.-nuv.y*nuv.y);

    // Output to screen
    fragColor = col;
}
// --------[ Original ShaderToy ends here ]---------- //

void main(void)
{
    mainImage(gl_FragColor, gl_FragCoord.xy);
}