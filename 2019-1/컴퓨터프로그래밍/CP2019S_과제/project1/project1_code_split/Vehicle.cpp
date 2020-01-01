#include <iostream>
#include "Vehicle.h"
#include <string>
using namespace std;

        Vehicle::Vehicle(int speed, int temperature, int humidity)
        {       //** initialization **//
                energyLoss = 0;
                speedLoss = 0;
                oxygenLoss = 0;
                plusEnergy = 0;
                firstMode = true;
                status = "";

            if(speed == 0) //** X & Y (speed 0) else (speed > 0)
                this -> obstacleMode = true;
            else {
                this -> obstacleMode = false;
                set_speed(speed);
                set_temperature(temperature);
                //** submarine(speed 20) has no effect of humidity **//
                if(speed != 20) 
                    set_humidity(humidity);
            }
        }
        
        void Vehicle::set_speed(int speed) { this-> speed = speed; }
        void Vehicle::set_temperature(int temperature) 
        {
            this -> temperature = temperature;
            if(temperature < 40 && temperature > 0) 
                set_energyLoss(5);

            else if(temperature >= 40)
                set_energyLoss(10);

            else if(temperature == 0)
                set_energyLoss(8);
        }
        void Vehicle::set_humidity(int humidity) 
        {
            this -> humidity = humidity;
            if(humidity < 50) 
                set_energyLoss(5);
            else  
                set_energyLoss(8);
        }
        int Vehicle::get_speed() { return this -> speed - get_speedLoss(); }
        int Vehicle::get_temperature() { return temperature; }
        int Vehicle::get_humidity() { return humidity; }
        //** all the resources set and get **//

        void Vehicle::set_energyLoss(int n) { this -> energyLoss += n; }
        void Vehicle::set_oxygenLoss(int n) { this -> oxygenLoss += n; }
        void Vehicle::set_speedLoss(int n) { this -> speedLoss += n; }
        void Vehicle::set_plusEnergy(int n) { this -> plusEnergy += n; }
        int Vehicle::get_energyLoss() { return energyLoss; }
        int Vehicle::get_oxygenLoss() { return oxygenLoss; }
        int Vehicle::get_speedLoss() { return speedLoss; }
        int Vehicle::get_plusEnergy() { return this-> plusEnergy; }
        //** resources loss and plus **//

        void Vehicle::set_firstMode(bool n) { this -> firstMode = n; }
        void Vehicle::set_status(string n) { this -> status = n; }
        string Vehicle::current_status() { return status; }
        bool Vehicle::is_obstacleMode() { return obstacleMode; }
        bool Vehicle::is_firstMode() { return firstMode; }
        //** status check **//
        
        void Vehicle::print(int distance, int energy, int oxygen) {
            cout << "Current Status: " << current_status() << endl;
            cout << "Distance: " << distance << " km" << endl;
            cout << "Speed: " << get_speed() << " km/hr" << endl; 
            cout << "Energy: " << energy << endl;
            if(current_status() != "Car") cout << "Oxygen Level: " << oxygen << endl;
            cout << "Temperature: " << get_temperature()<< " C" << endl;
            if(current_status() != "Submarine") cout << "Humidity: " << get_humidity() << endl;       
         }
       
       