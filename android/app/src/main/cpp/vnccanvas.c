/*
 * Native VNCCanvas drawing.
 * With help from https://www.learnopengles.com/calling-opengl-from-android-using-the-ndk/
 */

#include <jni.h>
#include <GLES/gl.h>
#include "rfb/rfbclient.h"
/*#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>*/

// https://arm-software.github.io/opengl-es-sdk-for-android/tutorials_2_simple_triangle_2jni_2_native_8cpp_source.html
// https://arm-software.github.io/opengl-es-sdk-for-android/introduction_to_shaders.html

// TODO:
// Implement fragment shader, load shaders and adapt client->framebuffer

/*static const char glFragmentShader[] =
 *      // incoming texture coordinate, between 0 and 1 for x and y
        "in vec2 ex_TexCoord;     \n"
        // outgoing fragment color (r,g,b,a)
        "out vec4 out_Color; \n"
        //

        "void main()  \n"
        "{   \n"

        // halfX, halfY ->

        // gl_FragCoord - contains the window-relative coordinates of the current fragment
        "float view_id = mod(floor(gl_FragCoord.x), 4.0);   \n"

        // texture2D -> (sampler2D sampler, vec2 coord)
        // sampler = sampler specifies the sampler to which the texture from which texel will be retrieved is bound
        // sampler types = uniforms or function parameters.

        "if (view_id < 0.5) {out_Color = texture2D(u_Textures[0], ex_TexCoord);}   \n"

        "else if (view_id < 1.5) {out_Color = texture2D(u_Textures[1], ex_TexCoord);}   \n"

        "else if (view_id < 2.5) {out_Color = texture2D(u_Textures[2], ex_TexCoord);}   \n"

        "else {out_Color = texture2D(u_Textures[3], ex_TexCoord);}   \n"

        "}   ";*/

JNIEXPORT void JNICALL Java_com_coboltforge_dontmind_multivnc_VncCanvas_on_1surface_1created(JNIEnv * env, jclass cls) {
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
}

JNIEXPORT void JNICALL Java_com_coboltforge_dontmind_multivnc_VncCanvas_on_1surface_1changed(JNIEnv * env, jclass cls, jint width, jint height) {
    // No-op
}

JNIEXPORT void JNICALL Java_com_coboltforge_dontmind_multivnc_VncCanvas_on_1draw_1frame(JNIEnv * env, jclass cls) {
    glClear(GL_COLOR_BUFFER_BIT);
}

JNIEXPORT void JNICALL Java_com_coboltforge_dontmind_multivnc_VncCanvas_prepareTexture(JNIEnv *env, jclass clazz, jlong rfbClientPtr) {

/*

    GLuint initShader = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(initShader, 1, ( const char *const *) glFragmentShader, NULL);
    glCompileShader(initShader);*/

    rfbClient *client = (rfbClient *) rfbClientPtr;

    if (client && client->frameBuffer) {

        glTexImage2D(GL_TEXTURE_2D,
                     0,
                     GL_RGBA,
                     client->width,
                     client->height,
                     0,
                     GL_RGBA,
                     client->format.depth == 15 ? GL_UNSIGNED_SHORT_5_5_5_1 : GL_UNSIGNED_BYTE,
                     client->frameBuffer);

    }

}
