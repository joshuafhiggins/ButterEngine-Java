package me.toast.engine.rendering;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.assimp.*;

import java.io.IOException;
import java.nio.ByteBuffer;

import static me.toast.engine.util.IOUtils.ioResourceToByteBuffer;
import static org.lwjgl.assimp.Assimp.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Model {

    static AIFileIO fileIo = AIFileIO.create()
            .OpenProc((pFileIO, fileName, openMode) -> {
                ByteBuffer data;
                String fileNameUtf8 = memUTF8(fileName);
                try {
                    data = ioResourceToByteBuffer(fileNameUtf8, 8192);
                } catch (IOException e) {
                    throw new RuntimeException("Could not open file: " + fileNameUtf8);
                }

                return AIFile.create()
                        .ReadProc((pFile, pBuffer, size, count) -> {
                            long max = Math.min(data.remaining(), size * count);
                            memCopy(memAddress(data) + data.position(), pBuffer, max);
                            return max;
                        })
                        .SeekProc((pFile, offset, origin) -> {
                            if (origin == Assimp.aiOrigin_CUR) {
                                data.position(data.position() + (int) offset);
                            } else if (origin == Assimp.aiOrigin_SET) {
                                data.position((int) offset);
                            } else if (origin == Assimp.aiOrigin_END) {
                                data.position(data.limit() + (int) offset);
                            }
                            return 0;
                        })
                        .FileSizeProc(pFile -> data.limit())
                        .address();
            })
            .CloseProc((pFileIO, pFile) -> {
                AIFile aiFile = AIFile.create(pFile);

                aiFile.ReadProc().free();
                aiFile.SeekProc().free();
                aiFile.FileSizeProc().free();
            });

    //TODO: Make a Models class for static referencing
    //NOTE: name needs to have the filetype as well
    public static Mesh[] LoadScene(String name, Shader shader, Material material) {
        AIScene scene = aiImportFileEx("assets/models/" + name,
                aiProcess_JoinIdenticalVertices | aiProcess_Triangulate | aiProcess_FixInfacingNormals, fileIo);

        if (scene == null) System.err.println("Couldn't load model at: " + "assets/models/" + name);
        assert scene != null;

        AIMesh[] meshes = new AIMesh[scene.mNumMeshes()];
        Mesh[] internal = new Mesh[scene.mNumMeshes()];

        for (int i = 0; i < scene.mNumMeshes(); i++) {
            meshes[i] = AIMesh.create(scene.mMeshes().get(i));
            int vertexCount = meshes[i].mNumVertices();

            AIVector3D.Buffer vertices = meshes[i].mVertices();
            //TODO: Add support for bones and animations
            AIVector3D.Buffer normals = meshes[i].mNormals();

            Vertex[] vertexList = new Vertex[vertexCount];
            for (int j = 0; j < vertexCount; j++) {
                AIVector3D vertex = vertices.get(j);
                Vector3f meshVertex = new Vector3f(vertex.x(), vertex.y(), vertex.z());

                //TODO: Add support for color, tangents, bi-tangents

                AIVector3D normal = normals.get(j);
                Vector3f meshNormal = new Vector3f(normal.x(), normal.y(), normal.z());

                //TODO: Support multiple textures here
                Vector2f meshTextureCoord = new Vector2f();
                if (meshes[i].mNumUVComponents().get(0) != 0) {
                    AIVector3D texture = meshes[i].mTextureCoords(0).get(j);
                    meshTextureCoord.x = texture.x();
                    meshTextureCoord.y = texture.y();
                }

                vertexList[j] = new Vertex(meshVertex, new Vector3f(1, 1, 1), meshTextureCoord, meshNormal);
            }

            int faceCount = meshes[i].mNumFaces();
            AIFace.Buffer indices = meshes[i].mFaces();
            int[] indicesList = new int[faceCount * 3];
            for (int k = 0; k < faceCount; k++) {
                AIFace face = indices.get(k);
                indicesList[k * 3] = face.mIndices().get(0);
                indicesList[k * 3 + 1] = face.mIndices().get(1);
                indicesList[k * 3 + 2] = face.mIndices().get(2);
            }

            internal[i] = new Mesh(vertexList, indicesList, shader, material);
        }

        return internal;
    }
}
