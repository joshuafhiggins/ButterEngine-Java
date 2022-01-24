package me.toast.engine.rendering;

import me.toast.engine.utils.FileUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL30.*;

public class Shader {

    public int ID;

    public Shader(String name) {
        String vertexFile = FileUtils.loadAsString("/assets/shaders/" + name + ".vert");
        String fragmentFile = FileUtils.loadAsString("/assets/shaders/" + name + ".frag");

        ID = glCreateProgram();

        //Create the vertex shader
        int vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertexFile);
        glCompileShader(vertexID);
        //Check for compile errors
        if (glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Vertex Shader: " + glGetShaderInfoLog(vertexID));
            return;
        }

        //Create the fragment shader
        int fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragmentFile);
        glCompileShader(fragmentID);
        //Check for compile errors
        if (glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Fragment Shader: " + glGetShaderInfoLog(fragmentID));
            return;
        }

        //Tell OpenGL the shader is in this program
        glAttachShader(ID, vertexID);
        glAttachShader(ID, fragmentID);

        //Link and check for errors
        glLinkProgram(ID);
        if (glGetProgrami(ID, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Program Linking: " + glGetProgramInfoLog(ID));
            return;
        }
        //Validate and check for errors
        glValidateProgram(ID);
        if (glGetProgrami(ID, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println("Program Validation: " + glGetProgramInfoLog(ID));
            return;
        }

        //Delete the vertex and fragment shader since they were already compiles into the program
        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);
    }

    public int getUniformLocation(String name) { return glGetUniformLocation(ID, name); }

    //TODO: Make all types of uniforms set-able
    public void setUniform(String name, float value) { glUniform1f(getUniformLocation(name), value); }
    public void setUniform(String name, int value) { glUniform1i(getUniformLocation(name), value); }
    public void setUniform(String name, boolean value) { glUniform1i(getUniformLocation(name), value ? 1 : 0); }
    public void setUniform(String name, Vector2f value) { glUniform2f(getUniformLocation(name), value.x, value.y); }
    public void setUniform(String name, Vector3f value) { glUniform3f(getUniformLocation(name), value.x, value.y, value.z); }
    public void setUniform(String name, Matrix4f value) {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(16);
        value.get(buffer);
        glUniformMatrix4fv(getUniformLocation(name), true, buffer);
    }
    
    //Bind the shader for the mesh to use
    public void Bind() {
        glUseProgram(ID);
    }

    //Unbind for good practice
    public void Unbind() {
        glUseProgram(0);
    }

    //Destroy the shader on Shutdown() //TODO: Check if the tutorial is right about destroying shaders
    public void Cleanup() {
        glDeleteProgram(ID);
    }
}
