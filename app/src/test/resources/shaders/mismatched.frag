#version 330 core
layout(location = 0) in vec4 color;

out vec4 fragColor;

void main() {
    fragColor = color;
}