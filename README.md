# guitar-hero-visualization
Visualizes the Guitar Hero Project from Vanderbilt's Data Structures class.
Although this project uses a similar object model, it is in a different language and uses different data structures for better flexibility.

###Summary
This application (currently) lets the user pluck strings, view the data in those strings live, and listen to the result of the plucks. The strings may be paused at any time to let the user see the state of the strings. The strings can also be ticed for one cycle to help understand the results of the Karplus-Strong algorithm on the ring buffer.

###Current Limitations
- The visualization gets laggy if there are too many strings (currently 13 for 1 whole octave)
- The sound output is frame rate dependent since the call to audio and tic is called each draw cycle

###Potential Optimizations
- Only draw the points of the string for X seconds/frames after they are plucked, otherwise just draw a horizontal line
- Use higher frequencies since high frequencies have shorter ring buffers
- Make audio output asynchronous and not frame dependent (??)

###Future Features
- Play music from text files
- Customize keybindings
- Customize number of strings, frequencies, spacing, which strings to visualize
- Toast text to help the user know what is going on
- Automated tutorial for people trying to understand how the Guitar Hero project works
