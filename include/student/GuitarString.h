/**
 * GuitarString.h is a class based on the Guitar String class from the
 * GuitarHero project. The distributable final version will use a LinkedList
 * with memory allocation/deletion only when necessary.
 *
 * For a student's implementation, they will have to use a LinkedList backed
 * Queue but provide an iterator (or at least a pointer) to the head so that the
 * draw can use the data, violating the Queue data encapsulation.
 */

#ifndef GUITARSTRING_H
#define GUITARSTRING_H

#include <cstddef>
#include <list>

const int SAMPLE_RATE = 44100;
const double DECAY_FACTOR = 0.996;

class GuitarString {
public:
  // create a guitar string representing the given frequency
  GuitarString(double frequency);

  // pluck the string - excite with white noise between -0.5 and 0.5
  void pluck();

  // advance the simulation one step
  void tic();

  // current sample
  double sample() const;

  // number of time steps = number of calls to tic()
  int getTime() const;

  // return the frequency of the string
  double getFrequency() const;

  // For the students implementation, given that they are using a basic
  // LinkedList
  // implementation of a Queue, the easiest way to create an iterator-like
  // interface might be to... return the head and a length or walk until
  // nullptr. Length is more reliable than nullptr since other LinkedLists may
  // have different implementations

  // return a pointer or iterator to the head of the queue. Constructor ensures there are
  // at least one element in the queue
  std::list<double>::const_iterator getHead() const;

  // return the length of the Queue
  size_t size() const;

private:
  // Data structure that holds the string data
  std::list<double> mQueue;

  // number of times this string has been ticced
  int numTics;

  // frequency of this string
  double frequency;
};

#endif // GUITARSTRING_H
