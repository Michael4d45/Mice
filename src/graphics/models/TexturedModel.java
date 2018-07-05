package graphics.models;

import graphics.renderEngine.Loader;
import graphics.textures.ModelTexture;

public class TexturedModel
{
    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture texture)
    {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public TexturedModel(String textureFile)
    {
        Loader loader = Loader.get();
        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
                0, 1, 3,//top left triangle (v0, v1, v3)
                3, 1, 2//bottom right triangle (v3, v1, v2)
        };

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        this.rawModel = loader.loadToVAO(vertices, textureCoords, indices);
        this.texture = new ModelTexture(loader.loadTexture(textureFile));
    }

    public RawModel getRawModel()
    {
        return rawModel;
    }

    public ModelTexture getTexture()
    {
        return texture;
    }
}
