cmake_minimum_required(VERSION 3.2)

# Set up HunterGate
include("cmake/HunterGate.cmake")
HunterGate(
        URL "https://github.com/ruslo/hunter/archive/v0.19.180.tar.gz"
        SHA1 "15ffd5005e7c7985aa3585ce25a772f1dbd2eee0"
)

# Set up my project
set (PROJECT_NAME guitar-hero-visualization)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14 -Wall -Wextra -pedantic -pedantic-errors -g")

project(${PROJECT_NAME})
include(ExternalProject)

# use hunter to add dependencies
hunter_add_package(glfw)

# hunter find dependencies
find_package(glfw3 CONFIG REQUIRED)

#add glad, deps from glfw/deps
include_directories(./lib/glad/include ./lib//deps)

# Include project headers
include_directories(./include)

# Define the source files and dependencies for the executable
set(SOURCE_FILES
        ./lib/glad/src/glad.c   #glad source files
        ./src/GuitarHeroVisual.cpp
        )
# Make the project root directory the working directory when we run
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/bin)
add_executable(${PROJECT_NAME} ${SOURCE_FILES})

target_link_libraries(${PROJECT_NAME} glfw)