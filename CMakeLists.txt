cmake_minimum_required(VERSION 3.2)
set (PROJECT_NAME guitar-hero-visualization)
project(${PROJECT_NAME})
include(ExternalProject)

# Set GLFW directory
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14 -Wall -Wextra -pedantic -pedantic-errors -g")

set(GLFW_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/lib/glfw)

set(GLFW_BUILD_EXAMPLES OFF CACHE INTERNAL "Build the GLFW example programs")
set(GLFW_BUILD_TESTS OFF CACHE INTERNAL "Build the GLFW test programs")
set(GLFW_BUILD_DOCS OFF CACHE INTERNAL "Build the GLFW documentation")
set(GLFW_INSTALL ON CACHE INTERNAL "Generate installation target")

set(GLFW_PREFIX glfw)
# Include GLFW into the build
find_package(Threads)
ExternalProject_Add(${GLFW_PREFIX}
        DOWNLOAD_NO_PROGRESS 1
        UPDATE_DISCONNECTED 1
        GIT_REPOSITORY https://github.com/glfw/glfw
        CMAKE_ARGS -DCMAKE_INSTALL_PREFIX=${GLFW_DIRECTORY}
        -DGLFW_BUILD_EXAMPLES=${GLFW_BUILD_EXAMPLES} -DGLFW_BUILD_TESTS=${GLFW_BUILD_TESTS}
        -DGLFW_BUILD_DOCS=${GLFW_BUILD_DOCS}
        -DGLFW_INSTALL=${GLFW_INSTALL}
        )
# Include the GLFW headers and libraries
include_directories(${GLFW_DIRECTORY}/include)
link_directories(${GLFW_DIRECTORY}/lib)


set(GLAD_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/lib/glad)

set(GLAD_INSTALL ON CACHE INTERNAL "Generate installation target")

# Include GLFW into the build
find_package(Threads)
ExternalProject_Add(glad
        DOWNLOAD_NO_PROGRESS 1
        UPDATE_DISCONNECTED 1
        GIT_REPOSITORY https://github.com/Dav1dde/glad
        CMAKE_ARGS -DCMAKE_INSTALL_PREFIX=${GLAD_DIRECTORY}
        -DGLAD_INSTALL=${GLAD_INSTALL}
        )
# Include the GLFW headers and libraries and dependencies
#include_directories(${GLFW_DIRECTORY}/deps)
include_directories(${GLAD_DIRECTORY}/include)
link_directories(${GLAD_DIRECTORY}/lib)

find_package(glfw3 REQUIRED)
find_package(OpenGL REQUIRED)
target_include_directories(${PROJECT_NAME} ${OPENGL_INCLUDE_DIR})
target_link_libraries(${PROJECT_NAME} ${OPENGL_gl_LIBRARY})
target_link_libraries(${PROJECT_NAME} ${OPENGL_glu_LIBRARY})



# Include project dependencies (files copied from GLFW)
include_directories(./deps)

# Include project headers
include_directories(./include)
# Define the source files and dependencies for the executable
set(SOURCE_FILES
        ./src/GuitarHeroVisual.cpp
        )
# Make the project root directory the working directory when we run
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/bin)
add_executable(${PROJECT_NAME} ${SOURCE_FILES})