#version 330 core
#extension GL_ARB_separate_shader_objects : enable
layout(location = 0) out vec3 color;

void main() {
    color = vec3(1.0);
    gl_Position = vec4(0.0);
}