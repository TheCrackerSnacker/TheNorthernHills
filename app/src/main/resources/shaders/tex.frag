#version 330 core
in vec3 vertColor;
in vec2 texCoord;

out vec4 FragColor;
uniform sampler2D ourTexture;

void main()
{
    FragColor = vec4(texture(ourTexture, texCoord).xyz * vertColor, 1.0);
}