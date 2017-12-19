/**
 * Manages all the guitar strings
 */

#ifndef STRINGMANAGER_H
#define STRINGMANAGER_H

#include <guitar/StringComponent.h>

#include <vector>

class StringManager {
public:
  // singleton access
  static StringManager *instance();

  // destructor since we have the singleton pointer
  ~StringManager();

  // draw the strings
  void draw();

  // add a string to the end of the set with the given frequency
  void addString(double frequency);

  // pluck a string by index
  void pluck(uint32_t index);

  // pluck all the strings
  void pluckAll();

  // tic a string by index
  void tic(uint32_t index);

  // tic all the strings
  void ticAll();

  // tic all the strings and send the audio to the C++ equivalent of
  // StdAudio.java  void ticPlayAll(); //TODO

  // Tic all the strings by their length
  void ticAllOneCycle();

  // Called once per draw cycle to play the strings "live" in "real time" and
  // handle events in
  // the local event queue if any (single threading)
  // void playStringsLive();

  /** Tic each string once and play the sound, increment the time and check
   * events */
  // void ticPlayEvents();
  /**
   * Check if the strings have been inactive for long enough to mark their
   * inactivity. Intended to be called periodically along side the tic call.
   */
  // boolean checkInactiveStrings();

  /** Reset the instance to the initial state. Releases the parent object. */
  void reset();

  // boolean getActivity();
  // uint32_t getActivityCount();
  // void useThreads(boolean val);

private:
  // Hide the constructor
  StringManager();

  // singleton instance pointer
  static StringManager *inst;

  float xStep;
  float yHeight;

  bool useThreads;
  bool hasActivity;
  uint32_t activityCount;
  std::vector<StringComponent> mStrings;

  const uint32_t MIN_FRAME_RATE = 15;
};

#endif // STRINGMANAGER_H
