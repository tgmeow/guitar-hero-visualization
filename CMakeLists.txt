cmake_minimum_required(VERSION 3.2)

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 -Wall -Werror -Wextra -pedantic -pedantic-errors -g")

# Include project headers
include_directories(./include)
# Define the source files and dependencies for the executable
set(SOURCE_FILES
        ./src/GuitarHeroVisual.cpp
        )
# Make the project root directory the working directory when we run
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/bin)
add_executable(GuitarHeroVisual ${SOURCE_FILES})