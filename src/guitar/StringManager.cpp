/**
 * Manages all the strings
 */

#include <algorithm>
#include <cmath>
#include <glad/glad.h>
#include <guitar/StringManager.h>
#include <iostream>

StringManager *StringManager::inst = nullptr;

// singleton access
StringManager *StringManager::instance() {
  if (StringManager::inst == nullptr) {
    StringManager::inst = new StringManager();
  }
  return StringManager::inst;
}

StringManager::~StringManager() {
  reset();
  delete inst;
}

// draw the strings
void StringManager::draw() {
  int spacingY = 50;
  int spacingX = 100;

  glPushMatrix();
  glTranslatef(spacingX, spacingY, 0.0);
  std::for_each(mStrings.begin(), mStrings.end(), [&](StringComponent &str) {
    str.draw();
    glTranslatef(0.0, spacingY, 0.0);
  });
  glPopMatrix();
}

// add a string to the end of the set with the given frequency
void StringManager::addString(double frequency) {
  double roundedLabelFreq = round(frequency * 10) / 10.0F;
  std::string label = std::to_string(mStrings.size()) + " [" +
                      std::to_string(roundedLabelFreq) + "]";
  //    float xStep = 5;
  //    float yHeight = 40;
  mStrings.emplace_back(StringComponent(frequency, label, xStep, yHeight));
}

// pluck a string by index
void StringManager::pluck(uint32_t index) { mStrings[index].pluck(); }

// pluck all the strings
void StringManager::pluckAll() {
  std::for_each(mStrings.begin(), mStrings.end(),
                [](StringComponent &str) { str.pluck(); });
}

// tic a string by index
void StringManager::tic(uint32_t index) { mStrings[index].tic(); }

// tic all the strings
void StringManager::ticAll() {
  std::for_each(mStrings.begin(), mStrings.end(),
                [](StringComponent &str) { str.tic(); });
}

// tic all the strings and send the audio to the C++ equivalent of StdAudio.java
// void StringManager::ticPlayAll(); //TODO

// Tic all the strings by their length
void StringManager::ticAllOneCycle() {
  std::for_each(mStrings.begin(), mStrings.end(), [](StringComponent &str) {
    for (uint32_t i = 0; i < str.size(); ++i) {
      str.tic();
    }
  });
}

// Called once per draw cycle to play the strings "live" in "real time" and
// handle events in
// the local event queue if any (single threading)
// void StringManager::playStringsLive();

/** Tic each string once and play the sound, increment the time and check events
 */
// void StringManager::ticPlayEvents();
/**
 * Check if the strings have been inactive for long enough to mark their
 * inactivity. Intended to be called periodically along side the tic call.
 */
// boolean StringManager::checkInactiveStrings();

/** Reset the instance to the initial state after initialization*/
void StringManager::reset() { mStrings.clear(); }

// boolean StringManager::getActivity();
// uint32_t StringManager::getActivityCount();
// void StringManager::useThreads(boolean val);

// Private
StringManager::StringManager()
    : xStep(5), yHeight(40), useThreads(false), hasActivity(true),
      activityCount(0) {}
