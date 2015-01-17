/*
 * The MIT License (MIT)
 *
 * Copyright © 2014, Heiko Brumme
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package silvertiger.tutorial.lwjgl.graphic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL20.*;

/**
 * This class represents a shader.
 *
 * @author Heiko Brumme
 */
public class Shader {

    /**
     * Stores the handle of the shader.
     */
    private final int id;

    /**
     * Creates a shader with specified type and source and compiles it. The type
     * in the tutorial should be either <code>GL_VERTEX_SHADER</code> or
     * <code>GL_FRAGMENT_SHADER</code>.
     *
     * @param type Type of the shader
     * @param source Source of the shader
     */
    public Shader(int type, CharSequence source) {
        id = glCreateShader(type);
        glShaderSource(id, source);
        glCompileShader(id);

        checkStatus();
    }

    /**
     * Checks if the shader was compiled successfully.
     */
    private void checkStatus() {
        int status = glGetShaderi(id, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            System.err.println(glGetShaderInfoLog(id));
            System.exit(-1);
        }
    }

    /**
     * Deletes the shader.
     */
    public void delete() {
        glDeleteShader(id);
    }

    /**
     * Getter for the shader ID.
     *
     * @return Handle of this shader
     */
    public int getID() {
        return id;
    }

    /**
     * Load shader from file.
     *
     * @param type Type of the shader
     * @param path File path of the shader
     * @return Shader from specified file
     */
    public static Shader loadShader(int type, String path) {
        InputStream in = Shader.class.getClassLoader().getResourceAsStream(path);
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = reader.readLine();
            while (line != null) {
                builder.append(line).append("\n");
                line = reader.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(Shader.class.getName()).log(Level.SEVERE, null, ex);
        }

        CharSequence source = builder.toString();
        return new Shader(type, source);
    }
}