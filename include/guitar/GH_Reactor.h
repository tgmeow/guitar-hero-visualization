/**
 * Singleton Reactor Pattern class to handle user events such as keypress, mouse, thread triggering
 * (if multithreading is implemented/available)
 */

#ifndef GUITARHEROREACTOR_H
#define GUITARHEROREACTOR_H

class GH_Reactor{
public:
    //singleton access point
    static GH_Reactor* instance();

    //all key presses enter through here and sent to the KeyMap singleton
    void run_key_event(); //TODO parameters

    //trigger a mouse click/release event
    void run_mouse_event(); //TODO parameters


private:
    // Constructor is private to ensure use as a singleton.
    GH_Reactor();

    // Pointer to the singleton instance of the GH_Reactor.
    static GH_Reactor* inst;
};

#endif //GUITARHEROREACTOR_H
