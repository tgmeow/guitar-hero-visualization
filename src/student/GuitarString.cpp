//
// Created by tgmeow on 12/17/2017.
//

#include <algorithm>
#include <chrono>
#include <cmath>
#include <random>
#include <stdexcept>
#include <student/GuitarString.h>

GuitarString::GuitarString(double freq) : numTics(0), frequency(freq) {

  if (freq <= 0.0 || freq > SAMPLE_RATE / 2.0) {
    std::invalid_argument("Frequency must be positive");
  }

  // create a queue of size n
  int n = (int)round(SAMPLE_RATE / frequency);

  for (int j = 0; j < n; ++j) {
    mQueue.emplace_back(0.0);
  }
}

void GuitarString::pluck() {
  unsigned seed =
      (unsigned)std::chrono::system_clock::now().time_since_epoch().count();
  std::default_random_engine generator(seed);
  // std::default_random_engine generator(1);
  std::uniform_real_distribution<double> distribution(-0.5, 0.5);
  std::transform(mQueue.begin(), mQueue.end(), mQueue.begin(),
                 [&](double) { return distribution(generator); });
}

void GuitarString::tic() {
  ++numTics;
  double current = mQueue.front();
  mQueue.front();
  double next = mQueue.front();
  mQueue.emplace_back(DECAY_FACTOR * ((current + next) / 2.0));
}

double GuitarString::sample() const { return mQueue.front(); }

int GuitarString::getTime() const { return numTics; }

double GuitarString::getFrequency() const { return frequency; }

std::list<double>::const_iterator GuitarString::getHead() const {
  return mQueue.begin();
}

size_t GuitarString::size() const { return mQueue.size(); }