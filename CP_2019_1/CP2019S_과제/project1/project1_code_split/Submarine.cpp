#include "Vehicle.h"
#include "Submarine.h"
#include <iostream>

using namespace std;
const int SPEED = 20;

    Submarine::Submarine(int temperature, int depth,int waterflow) 
    : Vehicle(SPEED, temperature, 100) {
        Vehicle::set_firstMode(false);
        Vehicle::set_status("Submarine");
        set_depth(depth);
        set_waterflow(waterflow);
        light_on();
    }
    void Submarine::set_depth(int depth) {
        this -> depth = depth;
        int n = depth/50;
        Vehicle::set_oxygenLoss(n*5);
    }
    void Submarine::set_waterflow(int waterflow) {
        this -> waterflow = waterflow;

        if(this -> waterflow >=30 && this -> waterflow < 50) 
            Vehicle::set_speedLoss(3);
        else if(this -> waterflow >= 50 && this -> waterflow < 70) 
            Vehicle::set_speedLoss(5);
        else if(this -> waterflow >=70) 
            Vehicle::set_speedLoss(10);
    }

    void Submarine::light_on() {
        Vehicle::set_energyLoss(30);
    }

    int Submarine::get_depth() {
        return depth;
    }

    int Submarine::get_waterflow() {
        return waterflow;
    }
    void Submarine::print(int distance, int Energy, int oxygen) {
        Vehicle::print(distance,Energy, oxygen);
        cout << "Depth: " << get_depth() << " m" << endl;
        cout << "Water Flow: " << get_waterflow() << endl;
    }
