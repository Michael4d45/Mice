package graphics.renderEngine;

import graphics.entities.Entity;
import graphics.models.RawModel;
import graphics.models.TexturedModel;
import graphics.shaders.StaticShader;
import graphics.toolBox.Maths;
import org.lwjgl.util.vector.Matrix4f;

import java.util.Vector;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

/**
 * Handles the rendering of a model to the screen.
 *
 * @author Karl
 */
public class Renderer
{

    /**
     * This method must be called each frame, before any rendering is carried
     * out. It basically clears the screen of everything that was rendered last
     * frame (using the glClear() method). The glClearColor() method determines
     * the colour that it uses to clear the screen. In this example it makes the
     * entire screen red at the start of each frame.
     */
    public void prepare()
    {
        glClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    /**
     * Renders a model to the screen.
     * <p>
     * Before we can render a VAO it needs to be made active, and we can do this
     * by binding it. We also need to enable the relevant attributes of the VAO,
     * which in this case is just attribute 0 where we stored the position data.
     * <p>
     * The VAO can then be rendered to the screen using glDrawArrays(). We tell
     * it what type of objects.shapes to render and the number of vertices that it needs
     * to render.
     * <p>
     * After rendering we unbind the VAO and disable the attribute.
     *
     * @param entity - The textured entity to be rendered.
     * @param shader - The shader to render for the entity.
     */
    public void render(Entity entity, StaticShader shader)
    {
        if (entity == null)
            return;
        TexturedModel texturedModel = entity.getModel();
        RawModel model = texturedModel.getRawModel();
        glBindVertexArray(model.getVaoID());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
        glDrawElements(GL_TRIANGLES, model.getVertexCount(), GL_UNSIGNED_INT, 0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    public void render(Vector<Entity> entities, StaticShader shader)
    {
        for (Entity entity : entities)
            render(entity, shader);
    }

}