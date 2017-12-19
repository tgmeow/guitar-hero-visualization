/**
 * Singleton pattern class to hash_map a key input to a callback function
 */


#ifndef KEYMAP_H
#define KEYMAP_H

#include <map>

class KeyMap {
public:
    //return the singleton instance
    static KeyMap *instance();

    //destructor
    ~KeyMap();

    void run(char c)

private:

    //hide the constructor
    KeyMap();

    //singleton instance
    static KeyMap *inst;

    //callback function ???
    typedef void (*myCB)();

    std::map<int, myCB> mKeyMap;

};

#endif //KEYMAP_H
