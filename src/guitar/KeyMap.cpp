//
// Created by tgmeow on 12/19/2017.
//

#include "guitar/KeyMap.h"
#include <algorithm>

KeyMap *KeyMap::inst = nullptr;

KeyMap *KeyMap::instance() {
  if (KeyMap::inst == nullptr) {
    inst = new KeyMap();
  }
  return inst;
}

KeyMap::~KeyMap() {
    //TODO
  mKeyMap.clear();
  delete inst;
}

void KeyMap::run(int c) {
  auto it = mKeyMap.find(c);
  if (it != mKeyMap.end()) {
    std::for_each(it->second.begin(), it->second.end(),
                  [](auto i) { i.second->run(); });
  }
}

// Map<std::string, myCB> getRunnables(char key)
void KeyMap::addRunnable(int c, GH_Runnable *runnable) {
  auto it = mKeyMap.find(c);
  if (it != mKeyMap.end()) {
    it->second.insert(std::pair<std::string, GH_Runnable *>(
        runnable->getComparableID(), runnable));
  } else {
    mKeyMap.insert(std::pair<int, std::map<std::string, GH_Runnable *>>(
        c, std::map<std::string, GH_Runnable *>()));
    mKeyMap.find(c)->second.insert(std::pair<std::string, GH_Runnable *>(
        runnable->getComparableID(), runnable));
  }
}

//void KeyMap::removeKey(int key) {
//  auto it = mKeyMap.find(key);
//  if (it != mKeyMap.end()) {
//    // delete the pointers of the second key map
//      std::for_each(it->second.begin(), it->second.end(), [&](auto& strPair){
//          std::remove(it->second.begin(), it->second.end(), strPair.second);
//      });
//
//  }
//  //
//  mKeyMap.erase(key);
//}

//void KeyMap::removeRunnables(int c, GH_Runnable* runnable){
//    auto it = mKeyMap.find(c);
//    if(it != mKeyMap.end()){
//        std::remove(it->second.begin(), it->second.end(), runnable);
//    }
//
//    delete runnable;
//}
//
//void KeyMap::removeRunnables( GH_Runnable* runnable){
//    std::for_each(mKeyMap.begin(), mKeyMap.end(), [&](auto it){std::remove(it.second.begin(),
//                                                                          it.second.end(),
//                                                                          runnable);});
//
//    delete runnable;
//}

int KeyMap::getNumRunnables() const{
    int sum = 0;
    std::for_each(mKeyMap.begin(), mKeyMap.end(), [&](auto it){sum += it.second.size();});
    return sum;
}

int KeyMap::getNumRunnables(int c) const{
    auto it = mKeyMap.find(c);
    if(it != mKeyMap.end()){
        return (int) it->second.size();
    }
    return 0;
}

// private
KeyMap::KeyMap() : mKeyMap() {}


//void KeyMap::clearMap(){
//    std::for_each(mKeyMap.begin(), mKeyMap.end(), [](auto it){
//        std::for_each(it.second.begin(), it.second.end(), [&](GH_Runnable* runnable){
//            std::remove(it.second.begin(), it.second.end(), runnable);});
//        });
//    mKeyMap.clear();
//}