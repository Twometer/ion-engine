# ion-engine
LWJGL Boilerplate Engine

## Features
- Window management
- Resource management
- Abstraction layer over textures, shaders, VAOs and VBOs
- Wavefront OBJ parser
- Font renderer (Signed distance fields)

## Paths
All files are loaded from JAR resources. There is a folder for each category of fiiles:
- `fonts`: Contains .png and .fnt files for each font face
- `models`: Contains .obj and .mtl files for the wavefront loader
- `shaders`: Contains glsl files for the shaders
  - Fragment shader: `name.f.glsl`
  - Vertex shader: `name.v.glsl` 