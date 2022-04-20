#ifdef GL_ES
precision mediump float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

vec2 random2( vec2 p ) { // имитация рандома с помощью фрактала от синуса помноженного на бальшое число
	return fract(sin(vec2(dot(p,vec2(127.1,311.7)),dot(p,vec2(269.5,183.3))))*43758.5453);
}
void main() {

	vec2 st = gl_FragCoord.xy/resolution.xy;
	st.x *= resolution.x/resolution.y;
	vec2 pz = gl_FragCoord.xy/resolution.xy;

	vec3 color = vec3(.0);
	st *= 5.; // размер сетки колличество точек (на сколько частей делится экранные координаты)
	vec2 i_st = floor(st);
	vec2 f_st = fract(st);
	float m_dist = 1.;
	vec2 m_point;

	for(int y=-1; y <= 1; y++){
		for(int x=-1; x <= 1; x++){
			vec2 neighbor = vec2(float(x), float(y));
			vec2 point = random2(i_st + neighbor);
			// point = vec2(.5,3.); // перекрыть фрактально синусовый шум константным значением

			point = 0.5 + 0.5*sin(time + 6.2831*point); // Анимация
			// point = 0.5 + 0.5*sin((time*.18+mouse.x*mouse.y*13.) + 3.2831*point); // отклик от мыши на шум
			vec2 diff = neighbor + point - f_st; // Вектор от отрисовываемого пиксела на ближайшую точку
			float dist = length(diff)*1.9; // Размер точки
			if(dist < m_dist){ 	      // поиск ближайшей точки в сетке
				m_dist = dist;
				m_point = point;
			}
		}
	}
	// color += vec3(pz.x-mouse.x,pz.y-mouse.y,color.z)*.5; // глобальный градиент от движения мышью
	// color += dot(m_point, vec2(.3,.6)); // диаграмма воронова цвет зависит от ближайшей точки

	// собственно отображение точки
	color += 1.-step(0.02, m_dist);
	// показать сетку
	// color.r += step(.99, f_st.x) + step(.99, f_st.y);
	gl_FragColor = vec4(color, 1.0);
}