/**
 * Plucks the specified string on keypress
 */

#include <guitar/StringRunner.h>
#include <algorithm>

StringRunner::StringRunner(int i): indexes(){
    indexes.insert(i);
}

StringRunner::~StringRunner(){
    indexes.clear();
}


void StringRunner::run(){
    std::for_each(indexes.begin(), indexes.end(), [](int i){
        StringManager::instance()->pluck(i);
    } );
}

std::string StringRunner::getComparableID() const{

    std::string ID = "SR" + indexes.size();
    int sum = 0;
    for (int index : indexes) {
        ID += std::to_string(index);
        sum += index;
    }
    return ID + std::to_string(sum);
}