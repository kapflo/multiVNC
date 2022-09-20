/*
 * Native VNCCanvas drawing.
 * With help from https://www.learnopengles.com/calling-opengl-from-android-using-the-ndk/
 */

#include <jni.h>
#include <GLES/gl.h>
#include "rfb/rfbclient.h"

#include <GLES2/gl2.h>
#include <GLES2/gl2ext.h>

// https://arm-software.github.io/opengl-es-sdk-for-android/tutorials_2_simple_triangle_2jni_2_native_8cpp_source.html
// https://arm-software.github.io/opengl-es-sdk-for-android/introduction_to_shaders.html

// TODO:
// Implement fragment shader, load shaders and adapt client->framebuffer

static const char glFragmentShader[] =
        "uniform vec4 u_Colors[4];    \n"
        "uniform sampler2D u_Textures[4];   \n"
        "varying vec2 v_TexCoord;   \n"
        "   \n"
        "   \n"
        "void main()  \n"
        "{   \n"
        "float view_id = mod(floor(gl_FragCoord.x), 4.0);   \n"
        "if (view_id < 0.5) {gl_FragColor = texture2D(u_Textures[0], v_TexCoord);}   \n"
        "else if (view_id < 1.5) {gl_FragColor = texture2D(u_Textures[1], v_TexCoord);}   \n"
        "else if (view_id < 2.5) {gl_FragColor = texture2D(u_Textures[2], v_TexCoord);}   \n"
        "else {gl_FragColor = texture2D(u_Textures[3], v_TexCoord);}   \n"
        "gl_FragColor = mix(u_Colors[3], u_Colors[2], u_Colors[1], u_Colors[0], gl_FragColor.g);   \n"
        "   \n"
        "   \n"
        "}   ";
/*

//!HOOK OUTPUT
//!BIND HOOKED
//!DESC LitByLeia_2x2_4V

//attributes
vec4 myColor;
vec2 pos;

float halfX;
float halfY;
float viewID;

vec4 hook(){
    vec4 myColor = HOOKED_tex(0);
    vec2 pos = HOOKED_pos;
    float halfX = pos.x / 2.0;
    float halfY = pos.y / 2.0;

    //floor(x) = nearest integer that is equal or less to x
    //mod(x,y) = modulo
    //gl_FragCoord = contains the window-relative coordinates (x,y,z,1/w) of the current fragment
    float viewID = mod(floor(gl_FragCoord.x), 4.0);

    if (viewID < 0.5) {
        // fl (top-left)
        myColor = HOOKED_tex(vec2(halfX, halfY));
    } else if(viewID < 1.5) {
        // l (top-right)
        myColor = HOOKED_tex(vec2(0.5 + halfX, halfY));
    } else if(viewID < 2.5) {
        // r (bottom-left)
        myColor = HOOKED_tex(vec2(halfX, 0.5 + halfY));
    } else {
        // fr (bottom-right)
        myColor = HOOKED_tex(vec2(0.5 + halfX, 0.5 + halfY));
    }

    return myColor;
}
*/
GLuint loadShader(GLenum shaderType, const char* shaderSource)
{
    GLuint shader = glCreateShader(shaderType);
    if (shader)
    {
        glShaderSource(shader, 1, &shaderSource, NULL);
        glCompileShader(shader);

        GLint compiled = 0;
        glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled);
    }
}

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

    rfbClient *client = (rfbClient *) rfbClientPtr;
    GLuint fragmentShader = loadShader(GL_FRAGMENT_SHADER, glFragmentShader);

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
