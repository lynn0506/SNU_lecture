#ifndef AIRPLANE
#define AIRPLANE
#include <iostream>
#include "Vehicle.h"
using namespace std;

class Airplane : public Vehicle {
    public: 
        Airplane(int temperature, int humidity, int altitude, int airDensity);
        void set_altitude(int altitude);
        void set_airDensity(int airDensity);
        int get_altitude();
        int get_airDensity();
        void print(int, int,int);

    private:
        int altitude;
        int airDensity;
};

#endif