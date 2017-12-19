//
// Created by tgmeow on 12/19/2017.
//

#include "guitar/KeyMap.h"

KeyMap* KeyMap::inst = nullptr;

KeyMap* KeyMap::instance() {
    if(KeyMap::inst == nullptr){
        inst = new KeyMap();
    }
    return inst;
}

KeyMap::~KeyMap() {

}


//private
KeyMap::KeyMap(){

}