/**
 * Main file
 */

#include <cstdlib>
#include <iostream>

#include <glad/glad.h>
#include <GLFW/glfw3.h>
#include "linmath.h"

// Helper function declarations
/**
 * Main draw loop
 * @param window window to draw to
 * @param program ??
 * @param mvp_location ??
 */
void draw(GLFWwindow *window, GLuint &program, GLint &mvp_location);

//Declares an array of the three vertices and initializes it as an array with values and colors
static const struct {
  float x, y;
  float r, g, b;
} vertices[3] = {{-0.6f, -0.4f, 1.f, 0.f, 0.f},
                 {0.6f, -0.4f, 0.f, 1.f, 0.f},
                 {0.f, 0.6f, 0.f, 0.f, 1.f}};

//Shader something something
static const char *vertex_shader_text =
    "uniform mat4 MVP;\n"
    "attribute vec3 vCol;\n"
    "attribute vec2 vPos;\n"
    "varying vec3 color;\n"
    "void main()\n"
    "{\n"
    "    gl_Position = MVP * vec4(vPos, 0.0, 1.0);\n"
    "    color = vCol;\n"
    "}\n";

//Vector colors something something
static const char *fragment_shader_text =
    "varying vec3 color;\n"
    "void main()\n"
    "{\n"
    "    gl_FragColor = vec4(color, 1.0);\n"
    "}\n";

//Error
static void error_callback(int error, const char *description) {
  fprintf(stderr, "Error: %s\n", description);
}

//Key bindings function
static void key_callback(GLFWwindow *window, int key, int scancode, int action,
                         int mods) {
  if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS)
    glfwSetWindowShouldClose(window, GLFW_TRUE);
}

/**
 * Based on the example code given at http://www.glfw.org/docs/latest/quick.html
 * @return main exit code
 */
int main(void) {
  //*****setup the window, initialize window things and variables*****//
  GLFWwindow *window;
  GLuint vertex_buffer, vertex_shader, fragment_shader, program;
  GLint mvp_location, vpos_location, vcol_location;
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

  // bind keys
  glfwSetKeyCallback(window, key_callback);

  // make top
  glfwMakeContextCurrent(window);

  //*****set window variables, settings, create shaders, buffers*****//
  // ?????
  // ?????

  gladLoadGLLoader((GLADloadproc)glfwGetProcAddress);
  glfwSwapInterval(1);
  // NOTE: OpenGL error checks have been omitted for brevity
  glGenBuffers(1, &vertex_buffer);
  glBindBuffer(GL_ARRAY_BUFFER, vertex_buffer);
  glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);
  vertex_shader = glCreateShader(GL_VERTEX_SHADER);
  glShaderSource(vertex_shader, 1, &vertex_shader_text, NULL);
  glCompileShader(vertex_shader);
  fragment_shader = glCreateShader(GL_FRAGMENT_SHADER);
  glShaderSource(fragment_shader, 1, &fragment_shader_text, NULL);
  glCompileShader(fragment_shader);
  program = glCreateProgram();
  glAttachShader(program, vertex_shader);
  glAttachShader(program, fragment_shader);
  glLinkProgram(program);
  mvp_location = glGetUniformLocation(program, "MVP");
  vpos_location = glGetAttribLocation(program, "vPos");
  vcol_location = glGetAttribLocation(program, "vCol");
  glEnableVertexAttribArray(vpos_location);
  glVertexAttribPointer(vpos_location, 2, GL_FLOAT, GL_FALSE, sizeof(float) * 5,
                        (void *)0);
  glEnableVertexAttribArray(vcol_location);
  glVertexAttribPointer(vcol_location, 3, GL_FLOAT, GL_FALSE, sizeof(float) * 5,
                        (void *)(sizeof(float) * 2));

  //***** DRAW LOOP *****//
  while (!glfwWindowShouldClose(window)) {
    draw(window, program, mvp_location);
  }
  //***** EXIT *****//
  glfwDestroyWindow(window);
  glfwTerminate();
  exit(EXIT_SUCCESS);
}

void draw(GLFWwindow *window, GLuint &program, GLint &mvp_location) {
  float ratio;
  int width, height;
  mat4x4 m, p, mvp;
  glfwGetFramebufferSize(window, &width, &height);
  ratio = width / (float)height;

    //sets the viewport location, view size
  glViewport(0, 0, width, height);
  glClear(GL_COLOR_BUFFER_BIT);
  mat4x4_identity(m);
  mat4x4_rotate_Z(m, m, (float)glfwGetTime());
  mat4x4_ortho(p, -ratio, ratio, -1.f, 1.f, 1.f, -1.f);
  mat4x4_mul(mvp, p, m);
  glUseProgram(program);
  glUniformMatrix4fv(mvp_location, 1, GL_FALSE, (const GLfloat *)mvp);
  glDrawArrays(GL_TRIANGLES, 0, 3);
  glfwSwapBuffers(window);
  glfwPollEvents();
}