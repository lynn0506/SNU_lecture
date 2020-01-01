#ifndef VEHICLE
#define VEHICLE
#include <iostream>
using namespace std;

class Vehicle {
    public:
        Vehicle(int speed, int temperatureint , int humidity);
        
        void set_speed(int speed);
        void set_temperature(int temperature);
        void set_humidity(int humidity);
        int get_speed();
        int get_temperature();
        int get_humidity(); 
        //** all the resources **//

        void set_energyLoss(int);
        void set_oxygenLoss(int);
        void set_speedLoss(int);
        void set_plusEnergy(int);
        int get_energyLoss();
        int get_oxygenLoss();
        int get_speedLoss();
        int get_plusEnergy();
        //** resources loss and plus **//

        void set_firstMode(bool n);
        void set_status(string);
        string current_status();
        bool is_obstacleMode();
        bool is_firstMode();
        //** status check **//
        virtual void print(int, int, int);

    private:
        int speed;
        int temperature;
        int humidity;
        //** all the resources
        int plusEnergy;
        int oxygenLoss;
        int speedLoss;
        int energyLoss;
        //** loss and plus
        string status;
        bool firstMode;
        bool obstacleMode;
        //** status checking
};

#endif