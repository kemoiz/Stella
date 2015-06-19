#version 100

precision highp float;


varying vec4 v_color;
varying vec2 v_texCoord0;


uniform float time;

uniform sampler2D u_sampler2D;
uniform vec4 u_neon;
float t, cx, cy;
vec4 color;

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main() {
   color = texture2D(u_sampler2D, v_texCoord0) * v_color;

  t = time * 0.001;


 //cx = (gl_FragCoord.x/ 10.0) + 0.5*sin(t/5.0);
 // cy = 100.0 - (gl_FragCoord.y/ 10.0) + 0.5*cos(t/3.0);
  

 // color.r= sin(0.24*((gl_FragCoord.x/100.0)*sin(t/3.0)+(gl_FragCoord.y/100.0)*cos(t/5.0))+t) - sin(sqrt(0.3*(cx*cx+t*cy)+1.0)+t);

// color.b =  color.r * 0.5 + sin(sqrt(0.3*(cx*cx+cy*cy)+1.0)+t);

// color.g = color.b - sin(sqrt(0.14*(cx*cx+cy*cy)+1.0)+t);
//	color.r = rand(vec2((t+gl_FragCoord.x/6.0) - (t+gl_FragCoord.x/6.0)/2.0 , (t+gl_FragCoord.x/6.0) - (t+gl_FragCoord.x/6.0)/2.0));
//	color.r = color.r * (gl_FragCoord.y / 1500.0);
	//color.g = color.b;
	// color.b = color.b * 1.1;
 
color.a = 2.5 - rand(vec2((t+gl_FragCoord.x/6.0) - (t+gl_FragCoord.x/6.0)/2.0 , (t+gl_FragCoord.x/6.0) - (t+gl_FragCoord.x/6.0)/2.0));;
 color.a = color.a * (gl_FragCoord.y / 2200.0);

  gl_FragColor = color;
}

