/**
 *
 */

#ifndef STRINGRUNNER_H
#define STRINGRUNNER_H

#include <guitar/StringManager.h>
#include <guitar/GH_Runnable.h>
#include <string>
#include <set>


class StringRunner : public GH_Runnable{
public:
    explicit StringRunner(int i);

    virtual ~StringRunner();

    virtual void run();

    virtual std::string getComparableID() const;

private:
  std::set<int> indexes;
};


#endif //STRINGRUNNER_H
