package me.toast.engine.rendering;

import me.toast.engine.utils.FileUtils;

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

    //Bind the shader for the mesh to use
    public void Bind() {
        glUseProgram(ID);
    }

    //Unbind for good practice
    public void Unbind() {
        glUseProgram(0);
    }

    //Destroy the shader on Shutdown() //TODO: Check if destroy checks are required for stability or only good practice
    public void Destroy() {
        glDeleteProgram(ID);
    }
}
