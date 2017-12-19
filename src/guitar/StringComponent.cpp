/**
 * This class is wraps the GuitarString file to provide a drawable interface for
 * the string structure
 */

#include <glad/glad.h>
#include <guitar/StringComponent.h>
#include <iostream>
#include <vector>

StringComponent::StringComponent(double frequency, const std::string &label,
                                 float xStepDist, float yHeight)
        : mString(frequency), label(label), xStep(xStepDist), yHeight(yHeight) {}

// draw this string at 0,0 horizontally
void StringComponent::draw() const {

    // TODO REMOVE LOCAL VARIABLE(S)
    int LABEL_OFFSET = 80;

    //TODO DRAW TEXT

    // Construct a VBO object of the points

    // vertex struct in xy for easy VBO construction
    struct vertex {
        float x, y;
    };
    std::vector<vertex> vertices;

    auto itemPointer = mString.getHead();
    for (size_t i = 0; i < mString.size(); ++i) {
        vertices.push_back(
                {LABEL_OFFSET + i * xStep, (float) (*itemPointer) * yHeight});

        // iterate somehow
        ++itemPointer;
    }

    // generate VBO from array
    GLuint vboId;
    glGenBuffers(1, &vboId);
    glBindBuffer(GL_ARRAY_BUFFER, vboId);
    // is buffer, size of array, pointer, draw
    glBufferData(GL_ARRAY_BUFFER, sizeof(vertex) * vertices.size(), &vertices[0], GL_DYNAMIC_DRAW);

    // after passing to VBO can delete

    // draw VBO
    glBindBuffer(GL_ARRAY_BUFFER, vboId);
    glEnableClientState(GL_VERTEX_ARRAY);
    //num points? type, size of each entry
    glVertexPointer(2, GL_FLOAT, sizeof(vertex), nullptr);

    glDrawArrays(GL_LINE_STRIP, 0, (GLsizei) mString.size());

    glDisableClientState(GL_VERTEX_ARRAY);

    // umm delete buffer(s) from memory after done drawing???
    //TODO
    //glDeleteBuffers(sizeof(vertex) * vertices.size(), &vboId);
}

// pluck the string
void StringComponent::pluck() { mString.pluck(); }

// tic the string
void StringComponent::tic() { mString.tic(); }

// current sample
double StringComponent::sample() const { return mString.sample(); }

// number of time steps = number of calls to tic()
int StringComponent::getTime() const { return mString.getTime(); }

// return the frequency of the string
double StringComponent::getFrequency() const { return mString.getFrequency(); }

size_t StringComponent::size() const { return mString.size(); }