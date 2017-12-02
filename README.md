# guitar-hero-visualization
Visualizes the Guitar Hero Project from Vanderbilt's Data Structures class.
Although this project uses a similar object model, it is in a different language and uses different data structures for better flexibility.

![image](https://user-images.githubusercontent.com/16727609/33507521-c82c8092-d6ba-11e7-9e91-d4aa5bc072cf.png)

### Summary
This application (currently) lets the user pluck strings, view the data in those strings live, and listen to the result of the plucks. The strings may be paused at any time to let the user see the state of the strings. The strings can also be ticed for one cycle to help understand the results of the Karplus-Strong algorithm on the ring buffer.

### Current keyboard shortcuts
| Key            | Action                  |
| -------------- | ----------------------- |
| qwertyuiop[]\  | Pluck strings [0...12]  |
| a              | Pluck ALL strings       |
| z              | Play a simple chord     |
| SPACE          | Pause tics and events   |
| ESC            | Exit program            |

### Current Limitations
- ~~The visualization gets laggy if there are too many strings (currently 13 for 1 whole octave)~~  
     [Fixed by switching to P2D fac7d2e](https://github.com/tgmeow/guitar-hero-visualization/commit/fac7d2efa312cf269af7dd3dbed85e532573f870)  
- ~~The sound output is frame rate dependent since the call to audio and tic is called each draw cycle~~  
     [Fixed with threading fac7d2e](https://github.com/tgmeow/guitar-hero-visualization/commit/fac7d2efa312cf269af7dd3dbed85e532573f870)  

### Potential Optimizations
- ~~Only draw the points of the string for X seconds/frames after they are plucked, otherwise just draw a horizontal line~~ 
     [Implemented in v1.1.0 in 0b99e1f](https://github.com/tgmeow/guitar-hero-visualization/commit/0b99e1f9e4ad652920d75dcc9afe80afbd2bf880)  
- Use higher frequencies since high frequencies have shorter ring buffers [Optional now, since we have enough fps]
- ~~Make audio output asynchronous and not frame dependent (??)~~  
     [Implemented with threading fac7d2e](https://github.com/tgmeow/guitar-hero-visualization/commit/fac7d2efa312cf269af7dd3dbed85e532573f870)  

### Future Features
- ~~Play music from text files~~  
     [Implemented in v1.0.0 fdc6e96](https://github.com/tgmeow/guitar-hero-visualization/commit/fdc6e96f1be42187d767dc05ea8e5af877d0d749)  
- Customize keybindings
- Customize number of strings, frequencies, spacing, which strings to visualize
- Toast text to help the user know what is going on
- Automated tutorial for people trying to understand how the Guitar Hero project works

### Releases
- [v1.1.1](https://github.com/tgmeow/guitar-hero-visualization/releases/tag/v1.1.1) Reduced idle cpu usage by reducing frame rate
- [v1.1.0](https://github.com/tgmeow/guitar-hero-visualization/releases/tag/v1.1.0) Reduced idle string performance, improved threading
- [v1.0.0](https://github.com/tgmeow/guitar-hero-visualization/releases/tag/v1.0.0) Big fps and audio improvements. Added read song from file. Load song with the button in the menu.
- [v0.1.0](https://github.com/tgmeow/guitar-hero-visualization/releases/tag/v0.1.0) Core string pluck functionality via keyboard shortcuts
