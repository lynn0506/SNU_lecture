#ifndef CAR_H
#define CAR_H
#include "Vehicle.h"
#include <iostream>

using namespace std;

class Car : public Vehicle{
    public:
        Car(int, int, bool);
        void solar_recharge();
        void check_humidity(int humidity);
        void print(int, int, int);

    private:
        int solarEnergy;
        bool firstMode;

};

#endif