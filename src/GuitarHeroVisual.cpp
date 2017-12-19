/**
 * Main file
 */

#include <cstdlib>
#include <iostream>
#include <string>

#include <glad/glad.h>

#include "linmath.h"
#include <GLFW/glfw3.h>
#include <guitar/GH_Reactor.h>
#include <guitar/StringComponent.h>
#include <guitar/StringManager.h>

// Helper function declarations
/**
 * Main draw loop
 * @param window window to draw to
 * @param program ??
 * @param mvp_location ??
 */
void draw();

// Error
static void error_callback(int error, const char *description) {
    fprintf(stderr, "Error: %i: %s\n", error, description);
}

// Key bindings function
// static void key_callback(GLFWwindow *window, int key, int scancode, int
// action,
//                         int mods) {
//  std::cout << "Key: " << key << " " << scancode << " " << action << " " <<
//  mods
//            << std::endl;
//  if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
//    glfwSetWindowShouldClose(window, GLFW_TRUE);
//  }
//}

/**
 * Based on the example code given at http://www.glfw.org/docs/latest/quick.html
 * @return main exit code
 */
int main(void) {
    //*****setup the window, initialize window things and variables*****//
    GLFWwindow *window;
    GLuint vertex_buffer, program;
    // GLint mvp_location, vpos_location, vcol_location;
    glfwSetErrorCallback(error_callback);

    // Check initialization
    if (!glfwInit()) {
        exit(EXIT_FAILURE);
    }

    glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);

    //*****Create window*****//
    window = glfwCreateWindow(1300, 700, "Simple example", NULL, NULL);

    // Check window creation
    if (!window) {
        glfwTerminate();
        exit(EXIT_FAILURE);
    }

    // bind key callback
    glfwSetKeyCallback(window, GH_Reactor::instance()->run_key_event);

    // makes all gl operations go to the current window
    glfwMakeContextCurrent(window);

    //*****set window variables, settings, create shaders, buffers*****//
    // ?????
    // ?????

    gladLoadGLLoader((GLADloadproc) glfwGetProcAddress);
    glfwSwapInterval(1);
    // NOTE: OpenGL error checks have been omitted for brevity
    glGenBuffers(1, &vertex_buffer);
    glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer);

    program = glCreateProgram();

    glLinkProgram(program);

    // float ratio;
    int width, height;
    glfwGetFramebufferSize(window, &width, &height);
    // ratio = width / (float)height;

    // sets the viewport location, view size
    glViewport(0, 0, width, height);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    // glOrtho.xml for 2D canvas
    glOrtho(0.0, width, height, 0.0, 0.0, 1.0);

    //    glClear(GL_COLOR_BUFFER_BIT);

    // set up the program
    GH_Reactor::instance()->initialize_program();

    //***** DRAW LOOP *****//
    while (!glfwWindowShouldClose(window)) {
        draw();

        // update window
        glfwSwapBuffers(window);
        // Process events in queue and do input callbacks
        glfwPollEvents();
    }
    //***** EXIT *****//
    glfwDestroyWindow(window);
    glfwTerminate();
    exit(EXIT_SUCCESS);
}

void draw() {

    // glUseProgram(program);

    // background clear color
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    // Line attributes
    glPointSize(10);
    glLineWidth(1.0);

    // GL draw color
    glColor3f(0.0, 0.0, 0.0);
    StringManager::instance()->draw();
}