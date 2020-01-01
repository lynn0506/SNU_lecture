#include "Airplane.h"
#include "Vehicle.h"
#include <iostream>

using namespace std;
const int SPEED = 900;
const int OXYGEN = 100;

Airplane::Airplane(int temperature, int humidity, int altitude, int airDensity) 
: Vehicle(SPEED, temperature, humidity) {
    set_altitude(altitude);
    set_airDensity(airDensity);
    Vehicle::set_status("Airplane");
}
void Airplane::set_altitude(int altitude) {
    this -> altitude = altitude;
    int n = altitude/1000;
    Vehicle::set_firstMode(false);
    Vehicle::set_oxygenLoss(n*10);
}

void Airplane::set_airDensity(int airDensity) {
    this -> airDensity = airDensity;

    if(airDensity >= 30 && airDensity < 50)
        Vehicle::set_speedLoss(200);
    else if(airDensity >=50 && airDensity < 70)
        Vehicle::set_speedLoss(300);
    else if(airDensity >= 70) 
        Vehicle::set_speedLoss(500);
}

int Airplane::get_altitude() {
    return altitude;
}
int Airplane::get_airDensity() {
    return airDensity;
}
void Airplane::print(int distance, int Energy, int oxygen) {
    Vehicle::print(distance, Energy, oxygen);
    cout << "Altitude: " << get_altitude() << " m" << endl; 
    cout << "Air Density: " << get_airDensity() << endl;
}
