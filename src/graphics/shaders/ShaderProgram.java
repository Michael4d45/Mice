package graphics.shaders;

import graphics.util.FileUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram
{
    private static final String RES = "src/graphics/shaders/";
    private static final String TYPE = ".shader";

    private static int programID;
    private static int vertID;
    private static int fragID;

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);


    ShaderProgram(String vertPath, String fragPath)
    {
        programID = glCreateProgram();
        vertID = loadShader(vertPath, GL_VERTEX_SHADER);
        fragID = loadShader(fragPath, GL_FRAGMENT_SHADER);
        bindAttributes();
        glLinkProgram(programID);
        glValidateProgram(programID);
        getAllUniformLocations();
    }

    public static void loadAll()
    {

    }

    protected abstract void getAllUniformLocations();

    int getUniformLocation(String name)
    {
        return glGetUniformLocation(programID, name);
    }


    protected void loadFloat(int location, float value)
    {
        glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector)
    {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadBoolean(int location, boolean value)
    {
        float toLoad = 0;
        if (value)
            toLoad = 1;
        glUniform1f(location, toLoad);
    }

    void loadMatrix(int location, Matrix4f matrix)
    {
        matrix.store(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix4(location, false, matrixBuffer);
    }

    void bindAttribute(int attribute, String variableName)
    {
        glBindAttribLocation(programID, attribute, variableName);
    }

    protected abstract void bindAttributes();

    public void start()
    {
        glUseProgram(programID);
    }

    public void stop()
    {
        glUseProgram(0);
    }

    public void cleanUp()
    {
        stop();
        glDetachShader(programID, vertID);
        glDetachShader(programID, fragID);
        glDeleteShader(vertID);
        glDeleteShader(fragID);
        glDeleteProgram(programID);
    }

    private static int loadShader(String file, int type)
    {
        String shader = FileUtils.loadAsString(RES + file + TYPE);
        int ID = glCreateShader(type);

        glShaderSource(ID, shader);

        glCompileShader(ID);
        if (glGetShaderi(ID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("Failed to compile shader!");
            System.err.println(glGetShaderInfoLog(ID, 2048));
            System.exit(-1);
        }
        glAttachShader(programID, ID);

        return ID;
    }
}
