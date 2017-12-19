/**
 * This class is wraps the GuitarString file to provide a drawable interface for
 * the string structure
 */
//
// Created by tgmeow on 12/17/2017.
//

#ifndef STRINGCOMPONENT_H
#define STRINGCOMPONENT_H

#include <string>
#include <student/GuitarString.h>

class StringComponent {
public:
  // Construct the drawable String
  StringComponent(double frequency, const std::string& label, float xStepDist,
                  float yHeight);

  // draw this string at 0,0 horizontally
  void draw() const;

  // pluck the string
  void pluck();

  // tic the string
  void tic();

  // current sample
  double sample() const;

  // number of time steps = number of calls to tic()
  int getTime() const;

  // return the frequency of the string
  double getFrequency() const;

  // length of the string
  size_t size() const;

private:
  GuitarString mString;
  std::string label;
  float xStep;
  float yHeight;
};

#endif // STRINGCOMPONENT_H
