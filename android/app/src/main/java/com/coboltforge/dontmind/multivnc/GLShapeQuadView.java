package com.coboltforge.dontmind.multivnc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

// GL10: public interface GL10, implements GL
/*

 */

public class GLShapeQuadView {

    public static final int QUAD=0;
    // public static final int NONE=1;

    private int kind = -1;

    private int circleSegments = 100;
    private FloatBuffer vertexBuffer;  // Buffer for vertex-array

    // Constructor - Setup the vertex buffer
    public GLShapeQuadView(int what) {

        kind = what;

        float[] vertices = null;

/*        if(kind == QUAD) {
            float[] v = {  // Vertices for the square
                    -1.0f, -1.0f,  0.0f,  // 0. left-bottom
                    1.0f, -1.0f,  0.0f,  // 1. right-bottom
                    -1.0f,  1.0f,  0.0f,  // 2. left-top
                    1.0f,  1.0f,  0.0f   // 3. right-top
            };
            vertices = v;
        }*/

        if(kind == QUAD) {
            /*vertices = new float[circleSegments*3];
            int count = 0;
            for (float i = 0; i < 360.0f; i+=(360.0f/circleSegments)) {
                vertices[count++] = (float) Math.cos(Math.toRadians(i));
                vertices[count++] = (float) Math.sin(Math.toRadians(i));
            }*/
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
