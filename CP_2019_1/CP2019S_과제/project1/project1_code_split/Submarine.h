#ifndef SUBMARINE
#define SUBMARINE
#include <iostream>
#include "Vehicle.h"
using namespace std;

class Submarine : public Vehicle {
    public:
        Submarine(int temperature, int depth, int waterflow);
        void set_depth(int depth);
        void set_waterflow(int waterflow);
        void light_on();
        int get_depth();
        int get_waterflow();
        void print(int, int, int);
    
    private:
        int depth;
        int waterflow;
};

#endif