package com.coboltforge.dontmind.multivnc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

// every java programm is a class, every class should have a main-methode..
public class GL4VInterlacing {

    // var declared outside of method is a global var (defined with static)
    // public allows access from outside of class
    // final == const, prevents its contents from being modified
    public static final int QUADVIEW = 0;
    public static final int TWOD = 1;

    // setup fragment buffer?

    // draw the interlacing - fragment shader
    // select 1,2,3,4 of quadview

    // constructor, set up buffer - obtain frame from framebuffer and switch pixels in the fragment shader
    public GL4Interlacing() {

    }

}
