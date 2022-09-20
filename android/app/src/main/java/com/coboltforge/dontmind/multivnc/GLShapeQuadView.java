package com.coboltforge.dontmind.multivnc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import android.util.Log;
import android.os.SystemClock;

import com.leia.android.lights.LeiaDisplayManager;
import com.leia.android.lights.LeiaDisplayManager.BacklightMode;
import com.leia.android.lights.LeiaSDK;
import com.leia.android.lights.SimpleDisplayQuery;
import com.leia.android.lights.BacklightModeListener;

public class GLShapeQuadView {
    public int mProgram;
    private int fShaderId;

/*    private int circleSegments = 100;*/
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array

    // Constructor - Setup the vertex buffer
    public GLShapeQuadView() {

        float[] vertices = {  // Vertices for the square
                -1.0f, -1.0f,  0.0f,  // 0. left-bottom
                1.0f, -1.0f,  0.0f,  // 1. right-bottom
                -1.0f,  1.0f,  0.0f,  // 2. left-top
                1.0f,  1.0f,  0.0f   // 3. right-top
        };

        // Setup vertex array buffer. Vertices in float. A float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind

    }

    public static int loadShader(int type, String shaderCode){

        // shader type:
        // Specifies the type of shader to be created.
        // Must be one of GL_COMPUTE_SHADER, GL_VERTEX_SHADER, GL_TESS_CONTROL_SHADER,
        // GL_TESS_EVALUATION_SHADER, GL_GEOMETRY_SHADER, or GL_FRAGMENT_SHADER.

        // glCreateShader:
        // creates an empty shader object and returns a non-zero value by which it can be referenced.
        // A shader object is used to maintain the source code strings that define a shader.
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);

        if (compiled[0] == 0) {
            Log.d("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(shader));
            return 0;
        }

        return shader;
    }

    // Render the shape
    public void draw(GL10 gl,  String fragmentShaderCode) {

        // GL_BLEND:
        // layers are basically merged - current layer in the framebuffer is merged with the newly incoming layer
        // exact mode of composition is controlled through the blending function, set with glBlendFunc.
        // Each single primitive (triangle, line, point) drawn adds a new layer and immediately merges that with the bottom layer
        GLES20.glEnable(GLES20.GL_BLEND);

        fShaderId = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();             // create empty OpenGL Program

        GLES20.glAttachShader(mProgram, fShaderId); // add the fragment shader to program

        GLES20.glLinkProgram(mProgram);                  // create OpenGL program executables

        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(mProgram, GLES20.GL_LINK_STATUS, linkStatus, 0);

        /*if (linkStatus[0] != GLES20.GL_TRUE) {
            Log.e(TAG, "Could not link program: ");
            Log.e(TAG, GLES20.glGetProgramInfoLog(mProgram));
            GLES20.glDeleteProgram(mProgram);
        }*/

        // Disable 2d textures, otherwise shape won't show
        gl.glDisable(GL10.GL_TEXTURE_2D);
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertexBuffer.capacity() / 3);

/*
        if(kind == NONE) {
      */
/*      gl.glVertexPointer (2, GL10.GL_FLOAT , 0, vertexBuffer);
            gl.glDrawArrays (GL10.GL_TRIANGLE_FAN, 0, circleSegments);*//*

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertexBuffer.capacity() / 3);
        }
*/

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // re-able 2d textures
        gl.glEnable(GL10.GL_TEXTURE_2D);
    }
}



// ----------------------------- Reset -----------------------------
/*


package com.coboltforge.dontmind.multivnc;

        import java.nio.ByteBuffer;
        import java.nio.ByteOrder;
        import java.nio.FloatBuffer;
        import javax.microedition.khronos.opengles.GL10;

        import com.leia.android.lights.LeiaDisplayManager;
        import com.leia.android.lights.LeiaDisplayManager.BacklightMode;
        import com.leia.android.lights.LeiaSDK;
        import com.leia.android.lights.SimpleDisplayQuery;
        import com.leia.android.lights.BacklightModeListener;

public class GLShapeQuadView {

    public static final int QUAD=0;
    // public static final int NONE=1;

    private int kind = -1;

    */
/*    private int circleSegments = 100;*//*

    private FloatBuffer vertexBuffer;  // Buffer for vertex-array

    // Constructor - Setup the vertex buffer
    public GLShapeQuadView(int what) {

        kind = what;

        float[] vertices = null;

*/
/*        if(kind == QUAD) {
            float[] v = {  // Vertices for the square
                    -1.0f, -1.0f,  0.0f,  // 0. left-bottom
                    1.0f, -1.0f,  0.0f,  // 1. right-bottom
                    -1.0f,  1.0f,  0.0f,  // 2. left-top
                    1.0f,  1.0f,  0.0f   // 3. right-top
            };
            vertices = v;
        }*//*


        if(kind == QUAD) {
            */
/*vertices = new float[circleSegments*3];
            int count = 0;
            for (float i = 0; i < 360.0f; i+=(360.0f/circleSegments)) {
                vertices[count++] = (float) Math.cos(Math.toRadians(i));
                vertices[count++] = (float) Math.sin(Math.toRadians(i));
            }*//*

            float[] v = {  // Vertices for the square
                    -1.0f, -1.0f,  0.0f,  // 0. left-bottom
                    1.0f, -1.0f,  0.0f,  // 1. right-bottom
                    -1.0f,  1.0f,  0.0f,  // 2. left-top
                    1.0f,  1.0f,  0.0f   // 3. right-top
            };
            vertices = v;
        }

        if(vertices != null) {
            // Setup vertex array buffer. Vertices in float. A float has 4 bytes
            ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
            vbb.order(ByteOrder.nativeOrder()); // Use native byte order
            vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
            vertexBuffer.put(vertices);         // Copy data into buffer
            vertexBuffer.position(0);           // Rewind
        }
    }

    // Render the shape
    public void draw(GL10 gl) {

        // Disable 2d textures, otherwise shape won't show
        gl.glDisable(GL10.GL_TEXTURE_2D);
        // Enable vertex-array and define its buffer
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        if(kind == QUAD) {
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertexBuffer.capacity() / 3);

        }

*/
/*
        if(kind == NONE) {
      *//*

*/
/*      gl.glVertexPointer (2, GL10.GL_FLOAT , 0, vertexBuffer);
            gl.glDrawArrays (GL10.GL_TRIANGLE_FAN, 0, circleSegments);*//*
*/
/*

            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertexBuffer.capacity() / 3);
        }
*//*


        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // re-able 2d textures
        gl.glEnable(GL10.GL_TEXTURE_2D);
    }
}

*/
