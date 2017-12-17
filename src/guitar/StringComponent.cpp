/**
 * This class is wraps the GuitarString file to provide a drawable interface for
 * the string structure
 */

#include <guitar/StringComponent.h>
#include <glad/glad.h>
#include <iostream>
#include <vector>

StringComponent::StringComponent(double frequency, std::string label, float xStepDist, float
yHeight): mString(frequency), label(label), xStep(xStepDist), yHeight(yHeight){

    }

    //draw this string at 0,0 horizontally
    void StringComponent::draw(){

        //TODO REMOVE LOCAL VARIABLE(S)
        int LABEL_OFFSET = 80;

        //Construct a VBO object of the points, bind and draw with x,y so length is

        struct vertex{
            float x, y;
        };
        std::vector<vertex> vertices;

        // vertex in xyz. Size 2D canvas, z should be zero
        auto itemPointer = mString.getHead();
        for(size_t i = 0; i <mString.size(); ++i){
            vertices.push_back({LABEL_OFFSET + i*xStep, (float)(*itemPointer) * yHeight});
            //std::cout << vertices[2*i] << " " << (float)(*itemPointer) * yHeight << std::endl;

            //iterate :(
            ++itemPointer;
        }

        //generate VBO from array
        GLuint vboId;
        glGenBuffers(1, &vboId);
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, sizeof(vertex)*vertices.size(), &vertices[0],
                     GL_DYNAMIC_DRAW);

        //after passing to VBO can delete

        //draw VBO
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glEnableClientState(GL_VERTEX_ARRAY);
        glVertexPointer(2, GL_FLOAT, sizeof(vertex), nullptr);

        glDrawArrays(GL_LINE_STRIP, 0, (GLsizei)mString.size());

        glDisableClientState(GL_VERTEX_ARRAY);

        //glBindBuffer(GL_ARRAY_BUFFER, 0);

        //umm delete buffer(s) from memory after done drawing
        //glDeleteBuffers(1, &vboId);
    }

    //pluck the string
    void StringComponent::pluck(){
        mString.pluck();
    }

    //tic the string
    void StringComponent::tic(){
        mString.tic();
    }

    // current sample
    double StringComponent::sample() const{
        return mString.sample();
    }

    // number of time steps = number of calls to tic()
    int StringComponent::getTime() const{
        return mString.getTime();
    }

    // return the frequency of the string
    double StringComponent::getFrequency() const{
        return mString.getFrequency();
    }
