# Butter Engine

Butter Engine is a game engine made in LWJGL and uses its subset libraries for various tasks. What originally started out as a simple rendering library to make the use of LWJGL easier is now becoming a fully fledged engine with easier end user development.

## TODO for a first release:
  
### Rendering
   - [X] Implement OpenGL 3.3 
   - [X] Shader Support
   - [X] Mesh System
   - [ ] Separate Entity Rendering from World Rendering
   - [ ] Model/Animation Loading
   - [ ] Multi-Texture Support/Materials
   - [ ] Transparency
   - [ ] Phong or PBR Lighting Model
   - [ ] Dynamic Lights
   - [ ] Baked Lighting

### UI
   - [ ] Implement Nuklear
   - [ ] Support On-Screen UIs and Menus
   - [ ] Input Switching

### Physics
   - [ ] Implement PhysX-JNI
   - [ ] Math Compatibility
   - [ ] Physics Model Loading

### Worlds
   - [X] Implement Ashley
   - [X] Entity Creation
   - [X] Logic Creation
   - [ ] Import Hammer Maps
   - [ ] Support Hammer Entities
  
### Audio
   - [ ] Implement OpenAL
   - [ ] Spacial Audio
   - [ ] Any-Sound Playback
   - [ ] Volume Control
 
### Proper Modding Support
   - [ ] Event Systems
   - [ ] Documentation
   - [ ] Mod Loading/Unloading
   - [ ] Editor UI with ImGUI (Should be own section)

### Base Engine
  - [X] Free Camera
  - [ ] Source Engine Movement Controller

## Advanced Features (way after first stable release)

### Networking
  - [ ] Implement Netty
  - [ ] UDP & Reliable UDP
  - [ ] Remote Function Calling
  - [ ] Networked Source Engine Movement Controller

### Rendering
   - [ ] Deferred Rendering
   - [ ] Raytracing
   - [ ] OpenGL 4.4 Support
   - [ ] Vulkan Support

### Audio
   - [ ] Steam Audio

Butter Engine is under the MIT License, check the [license](https://github.com/higgy999/ButterEngine/blob/main/LICENSE.md) for more details.
The libraries Butter Engine uses fall under their own respective licenses.
