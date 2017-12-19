/**
 * Abstract runnable type object
 */
//
// Created by tgmeow on 12/19/2017.
//

#ifndef GH_RUNNABLE_H
#define GH_RUNNABLE_H

#include <string>

class GH_Runnable {
public:
    virtual ~GH_Runnable() = default;

    virtual void run() = 0;

    virtual std::string getComparableID() const = 0;

    int compare(GH_Runnable *o) {
        return getComparableID().compare(o->getComparableID());
    }
};

#endif // GH_RUNNABLE_H
