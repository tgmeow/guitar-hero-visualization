/**
 * GH_Reactor singleton to handle user events such as click, key, threading ?
 * etc
 */

#include <cmath>
#include <guitar/GH_Reactor.h>
#include <guitar/StringManager.h>
#include <iostream>
#include <stdexcept>

GH_Reactor *GH_Reactor::inst = nullptr;

GH_Reactor *GH_Reactor::instance() {
    if (GH_Reactor::inst == nullptr) {
        GH_Reactor::inst = new GH_Reactor();
    }
    return GH_Reactor::inst;
}

GH_Reactor::~GH_Reactor() { delete inst; }

void GH_Reactor::initialize_program() {
    // Ensure that this is only ever called once
    if (initialized) {
        throw std::runtime_error("Can't initialize the program more than once");
    }

    int num_strings = 37;
    double BASE_FREQ = 110.0;

    // Add items to the StringManger
    for (int i = 0; i < num_strings; i++) {
        double factor = pow(2, i / 12.0);
        StringManager::instance()->addString(BASE_FREQ * factor);
    }
    //
    //    KeyMap keyMap = KeyMap.getInstance();
    //    keyMap.addRunnable('a', new AllStringsRunner());
    //    //keyMap.addRunnable('q', new SingleStringRunner(0));
    //    char[] keys = new char[] {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o',
    //    'p', '[', ']', '\\'}; for (int i = 0; i < keys.length; ++i) {
    //        keyMap.addRunnable(keys[i], new StringRunner(i));
    //    }
    //
    //    keyMap.addRunnable('z', new StringRunner(0, 4, 7));
    //
    //    Menu.setInstance(this);
    //    Menu myMenu = Menu.getInstance();
    //    myMenu.addController(
    //            "PlayPauseEvents",
    //            new RunnableButtonController(this, "Play/Pause", new
    //            PlayPauseRunnable()));
    //    myMenu.addController(
    //            "LoadSong0", new RunnableButtonController(this, "Load Song", new
    //            LoadSongRunnable(this)));
    // myMenu.addController("Button0", new ButtonController(this, "Button0"));

    // EventQueue.getInstance().addEvent(2, new StringRunner(chordA));
    // EventQueue.getInstance().beginEvents();

    // set success
    initialized = true;
}

void GH_Reactor::run_key_event(GLFWwindow *window, int key, int scancode,
                               int action, int mods) {
    std::cout << "Key: " << key << " " << scancode << " " << action << " " << mods
              << std::endl;
    if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
        glfwSetWindowShouldClose(window, GLFW_TRUE);
    }
    if(key == GLFW_KEY_Q && action == GLFW_PRESS){
        StringManager::instance()->pluck(0);
    }
    if(key == GLFW_KEY_A && action == GLFW_PRESS){
        StringManager::instance()->pluckAll();
    }
    //todo keymap
}

void GH_Reactor::run_mouse_event() {} // TODO parameters

// private
GH_Reactor::GH_Reactor() : initialized(false) {}
