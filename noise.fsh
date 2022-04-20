#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform vec2      resolution;
uniform float     time;


float rand(vec2 n) {
    return fract(cos(dot(n, vec2(15.9898, 10.1414))) * 93758.5453);
}

float noise(vec2 n) {
    const vec2 d = vec2(0.0, 1.0);
    vec2 b = floor(n), f = smoothstep(vec2(0.0), vec2(1.0), fract(n));
    return mix(mix(rand(b), rand(b + d.yx), f.x), mix(rand(b + d.xy), rand(b + d.yy), f.x), f.y);
}

float fbm(vec2 n) {
    float total = 0.0, amplitude = 1.0;
    for (int i = 0; i < 5; i++) {
        total += noise(n) * amplitude;
        n += n;
        amplitude *= 0.12;
    }
    return total;
}

void main() {
    const vec3 c1 = vec3(126.0/255.0, 0.0/255.0, 127.0/255.0);
    const vec3 c2 = vec3(173.0/255.0, 0.0/255.0, 161.4/255.0);
    const vec3 c3 = vec3(0.2, 0.0, 0.0);
    const vec3 c4 = vec3(164.0/255.0, 1.0/255.0, 154.4/255.0);
    const vec3 c5 = vec3(124.0/255.0,0/255.0, 154.4/255.0);
    const vec3 c6 = vec3(164.0/255.0, 0/255.0, 154.4/255.0);

    vec2 p = gl_FragCoord.xy * 5.0 / resolution.xx;
    float q = fbm(p - time * 0.1);
    vec2 r = vec2(fbm(p + q + time * 1.0 - p.x - p.y), fbm(p + q - time * 1.0));
    vec3 c = mix(c1, c2, fbm(p + r)) + mix(c3, c4, r.x) - mix(c5, c6, r.y);
    gl_FragColor = vec4(c * cos(1.0 * gl_FragCoord.y / resolution.y), 1.0);
    gl_FragColor.xyz *= 1.0 - gl_FragCoord.y / resolution.y;
    gl_FragColor.w = 0.75;
}


/* #ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;
uniform sampler2D backbuffer;

void main( void ) {
    vec2 pos = gl_FragCoord.xy / resolution;
    float amnt = 200.0;
    float nd = 0.0;
    vec4 cbuff = vec4(0.0);

    for(float i=0.0; i<4.0;i++){
        nd =sin(4.93*0.8*pos.x + (i*0.2+sin(+time)*.8) + time)*0.2+0.1 + pos.x;
        amnt = 1.0/abs(nd-pos.y)*0.01;

        cbuff += vec4(amnt, amnt*0.15 , amnt*pos.y, 081.0);
    }

    gl_FragColor = cbuff ;
}*/
/*#ifdef GL_ES
precision mediump float;
#endif

uniform float time;
uniform vec2 resolution;

void main() {
    vec2 uv = (gl_FragCoord.xy * 2.0 - resolution) / min(resolution.x, resolution.y);
    vec2 f = vec2(0.0);
    vec3 c = vec3(1.0);
    float light = 0.0;

    for (float x = 0.1; x < 6.0; x += 1.0)
    {
        f = vec2(sin(sin(time + uv.x * x) - uv.y * dot(vec2(x + uv.y), vec2(sin(x), cos(x)))));
        light += (0.04 / distance(uv, f)) - (0.01 * distance(vec2((sin(time + uv.y))), vec2(uv)));
        c.y = sin(x) * 0.7 + 0.2;
    }

    c *= light;

    gl_FragColor = vec4(c, 1.0);
}*/