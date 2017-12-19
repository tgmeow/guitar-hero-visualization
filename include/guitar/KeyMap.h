/**
 * Singleton pattern class to hash_map a key input to a callback function
 */

#ifndef KEYMAP_H
#define KEYMAP_H

#include <guitar/GH_Runnable.h>
#include <map>

class KeyMap {
public:
    // return the singleton instance
    static KeyMap *instance();

    // destructor
    ~KeyMap();

    void run(int c);

    // Map<std::string, myCB> getRunnables(char key)
    void addRunnable(int c, GH_Runnable *runnable);

//    void removeKey(int key);

//    void removeRunnables(int c,  GH_Runnable* runnable);
//
//    void removeRunnables( GH_Runnable* runnable);

    int getNumRunnables() const;

    int getNumRunnables(int c) const;

private:
    // hide the constructor
    KeyMap();

    //void clearMap();

    // singleton instance
    static KeyMap *inst;

    std::map<int, std::map<std::string, GH_Runnable *>> mKeyMap;
};

#endif // KEYMAP_H
