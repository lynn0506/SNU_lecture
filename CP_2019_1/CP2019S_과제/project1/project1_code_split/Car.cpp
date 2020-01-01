#include "Car.h"
#include "Vehicle.h"
#include <iostream>

using namespace std;
const int SPEED = 80;

    Car::Car(int temperature, int humidity, bool firstMode) 
    : Vehicle(SPEED, temperature, humidity) 
    {   
        this -> solarEnergy = 0;
        this -> firstMode = firstMode;
        Vehicle::set_firstMode(firstMode);
        Vehicle::set_status("Car");
        check_humidity(humidity);
        //** set if it's the first mode for solar charge
    }
    
    void Car::check_humidity(int humidity) {
        if(humidity < 50 && firstMode == false)
            solar_recharge();       
    }

    void Car::solar_recharge() {
        this -> solarEnergy = 200;
        Vehicle::set_plusEnergy(solarEnergy);
    }

    void Car::print(int distance, int Energy, int oxygen) {
        Vehicle::print(distance, Energy, oxygen);
    }

