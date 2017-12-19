/**
 * Singleton Reactor Pattern class to handle user events such as keypress,
 * mouse, thread triggering (if multithreading is implemented/available)
 */

#ifndef GH_REACTOR_H
#define GH_REACTOR_H

#include <GLFW/glfw3.h>

class GH_Reactor {
public:
  // singleton access point
  static GH_Reactor *instance();

  // destruct the GH_Reactor pointer instance
  ~GH_Reactor();

  // Initialize the program variables: Strings, controllers, etc
  // This should be called once before any other operations are done in this
  // program
  void initialize_program();

  // all key presses enter through here and sent to the KeyMap singleton
  static void run_key_event(GLFWwindow *window, int key, int scancode,
                            int action,
                            int mods);

  // trigger a mouse click/release event
  void run_mouse_event(); // TODO parameters

private:
  // Constructor is private to ensure use as a singleton.
  GH_Reactor();

  // Pointer to the singleton instance of the GH_Reactor.
  static GH_Reactor *inst;

  // ensure that initialize_program is only ever called once.
  bool initialized;
};

#endif // GH_REACTOR_H
