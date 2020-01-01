#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Vehicle {
    public:
        Vehicle(int speed, int temperature, int humidity)
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
        
        void set_speed(int speed) { this-> speed = speed; }
        void set_temperature(int temperature) 
        {
            this -> temperature = temperature;
            if(temperature < 40 && temperature > 0) 
                set_energyLoss(5);

            else if(temperature >= 40)
                set_energyLoss(10);

            else if(temperature == 0)
                set_energyLoss(8);
        }
        void set_humidity(int humidity) 
        {
            this -> humidity = humidity;
            if(humidity < 50) 
                set_energyLoss(5);
            else  
                set_energyLoss(8);
        }
        int get_speed() { return this -> speed - get_speedLoss(); }
        int get_temperature() { return temperature; }
        int get_humidity() { return humidity; }
        //** all the resources set and get **//

        void set_energyLoss(int n) { this -> energyLoss += n; }
        void set_oxygenLoss(int n) { this -> oxygenLoss += n; }
        void set_speedLoss(int n) { this -> speedLoss += n; }
        void set_plusEnergy(int n) { this -> plusEnergy += n; }
        int get_energyLoss() { return energyLoss; }
        int get_oxygenLoss() { return oxygenLoss; }
        int get_speedLoss() { return speedLoss; }
        int get_plusEnergy() { return this-> plusEnergy; }
        //** resources loss and plus **//

        void set_firstMode(bool n) { this -> firstMode = n; }
        void set_status(string n) { this -> status = n; }
        string current_status() { return status; }
        bool is_obstacleMode() { return obstacleMode; }
        bool is_firstMode() { return firstMode; }
        //** status check **//
        
        void print(int distance, int energy, int oxygen) {
            cout << "Current Status: " << current_status() << endl;
            cout << "Distance: " << distance << " km" << endl;
            cout << "Speed: " << get_speed() << " km/hr" << endl; 
            cout << "Energy: " << energy << endl;
            if(current_status() != "Car") cout << "Oxygen Level: " << oxygen << endl;
            cout << "Temperature: " << get_temperature()<< " C" << endl;
            if(current_status() != "Submarine") cout << "Humidity: " << get_humidity() << endl;       
         }

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
        //** status checking:

};

class Car : public Vehicle 
{
    public :
        Car(int temperature, int humidity, bool firstMode) 
        : Vehicle(80, temperature, humidity) 
        {   // car speed = 80
            this -> solarEnergy = 0;
            this -> firstMode = firstMode;
            Vehicle::set_firstMode(firstMode);
            Vehicle::set_status("Car");
            check_humidity(humidity);
        }
    
        void check_humidity(int humidity) 
        {
            if(humidity < 50 && firstMode == false)
                solar_recharge();       
        }
        void solar_recharge() 
        {
            this -> solarEnergy = 200;
            Vehicle::set_plusEnergy(solarEnergy);
        }

    private:
        int solarEnergy;
        bool firstMode;
};

class Airplane : public Vehicle 
{
    public: 
        Airplane(int temperature, int humidity, int altitude, int airDensity) 
        : Vehicle(900, temperature, humidity) 
        {   // Airplane speed = 900
            set_altitude(altitude);
            set_airDensity(airDensity);
            Vehicle::set_firstMode(false);
            Vehicle::set_status("Airplane");
        }
        void set_altitude(int altitude) 
        {
            this -> altitude = altitude;
            int n = altitude/1000;
            Vehicle::set_oxygenLoss(n*10);
        }
        void set_airDensity(int airDensity) 
        {
            this -> airDensity = airDensity;
            if(airDensity >= 30 && airDensity < 50)
                Vehicle::set_speedLoss(200);

            else if(airDensity >=50 && airDensity < 70)
                Vehicle::set_speedLoss(300);

            else if(airDensity >= 70) 
                Vehicle::set_speedLoss(500);
        }
        int get_altitude() { return altitude; }
        int get_airDensity() { return airDensity; }

        void print(int distance, int energy, int oxygen) 
        {
            Vehicle::print(distance, energy, oxygen);
            cout << "Altitude: " << get_altitude() << " m" << endl; 
            cout << "Air Density: " << get_airDensity() << endl;
        }

    private:
        int altitude;
        int airDensity;
    };

class Submarine : public Vehicle 
{
    public :
       Submarine(int temperature, int depth, int waterflow) 
        : Vehicle(20, temperature, 100) 
        {   // Submarine speed = 20, humidity = 100;
            set_depth(depth);
            set_waterflow(waterflow);
            Vehicle::set_firstMode(false);
            Vehicle::set_status("Submarine");
            light_on();
        }
        void set_depth(int depth) 
        {
            this -> depth = depth;
            int n = depth/50;
            Vehicle::set_oxygenLoss(n*5);
        }
        void set_waterflow(int waterflow) 
        {
            this -> waterflow = waterflow;
            if(this -> waterflow >=30 && this -> waterflow < 50) 
                Vehicle::set_speedLoss(3);

            else if(this -> waterflow >= 50 && this -> waterflow < 70) 
                Vehicle::set_speedLoss(5);

            else if(this -> waterflow >=70) 
                Vehicle::set_speedLoss(10);
        }
        void light_on() { Vehicle::set_energyLoss(30);}
        int get_depth() { return depth;}
        int get_waterflow() { return waterflow;}

        void print(int distance, int energy, int oxygen) 
        {
            Vehicle::print(distance, energy, oxygen);
            cout << "Depth: " << get_depth() << " m" << endl;
            cout << "Water Flow: " << get_waterflow() << endl;
        }

    private:
        int depth;
        int waterflow;
};

void initializer(); // initialization for global variables
void test_case(); // test cases set.
int choose_testCase(); 
void tokenize(string);
int* unit_tokenize(int reasources[], char* mode, int index, int modeCount);

int next_move(); // choosing next mode 1 or 2
char* get_status(); // current mode 
bool percent(int); // for unexpected mode
int unit_set(char* mode); // unit(car, submarine, airplane)
string move_status(int move); // successfully moved.

bool mode_change(int energy, int oxygen, int distance);
void graphic_record(int lastPos, int currentPos);
string final_status(int energy, int oxygen, int distance);
string finish(int energy, int oxygen); // FINISHED! 
string blackbox(string mode, string energy, string oxygen, string speed);

const static int ROADUNIT = 50;
const static int OCEANUNIT = 10;
const static int SKYUNIT = 1000;

static bool extraMode = false;
static bool damaged = false;

static vector<Vehicle*> journey; // all the journeys
static string TC[10]; // test cases
static vector<int> distance_sum; // distance accumulation 
static string graphic = "|"; 

int main() 
{
    cout << "PJ1. 정은주 CP-2014-19498" << endl;
    test_case();
    cout << "Mode Select(1 for EXTRA, 2 for NORMAL) :";
    int c; 
    cin >> c;
    extraMode = (c == 1) ? true : false;
    while(true) 
    {
        if(choose_testCase() == 0) break;
        int distance = 0;
        int move = 0;
        int energy = 1000;
        int oxygen = 100;
        int speed = 80;
        int lastPos = 1;
        int currentPos = lastPos;
        bool solarChange = false;

        string modeRecord = "Mode: ";
        string energyRecord = "Energy Level: ";
        string oxygenRecord = "Oxygen Level: ";
        string speedRecord = "Speed: ";
        int index = 0;

        while(true) 
        {
            Vehicle* mode;
            cout << graphic << endl;

            if(extraMode == true && journey.at(0) -> is_obstacleMode() == true)
            {
                char* status = get_status();
                mode = journey.at(0);
                journey.erase(journey.begin()); 
                string newBlackBox = final_status(energy, oxygen, distance)
                                    + graphic + "\n\n!FINISHED: Vehicle stop\n" 
                                    + blackbox(modeRecord, energyRecord, oxygenRecord, speedRecord);

                if(strstr(status, "X") != NULL) 
                {   if(percent(20) == false) 
                    {   energy -= mode -> get_energyLoss();
                        energy = (energy < 0) ? 0 : energy;
                        if(energy == 0)
                        {
                            cout << final_status(energy, oxygen, distance);
                            cout << graphic << "\n" << finish(energy, oxygen);
                            cout << blackbox(modeRecord, energyRecord, oxygenRecord, speedRecord);
                            break;
                        }
                    }
                    else { cout << newBlackBox; break;}        
                }
                else if(strstr(status, "Y") != NULL)
                {   if(percent(35) == false)  
                    {   if(percent(50) == true) 
                        {   if(graphic.substr(lastPos-1, lastPos).compare("="))
                                damaged = true; // solar penal damaged until the end.
                            else 
                            {
                                oxygen -= mode -> get_oxygenLoss();
                                oxygen = (oxygen < 0) ? 0 : oxygen;
                                if(oxygen == 0) 
                                {
                                    cout << final_status(energy, oxygen, distance);
                                    cout << graphic << "\n" << finish(energy, oxygen);
                                    cout << blackbox(modeRecord, energyRecord, oxygenRecord, speedRecord);
                                    break;
                                }
                            }
                        }  
                    } else { cout << newBlackBox; break; }
                }
            }
        
            //** for the car mode solar energy recharge **// 
            char* status = get_status();
            if(strstr(status, "Car") != NULL)
            {
                if(solarChange == false && damaged == false 
                    && journey.at(0) -> is_firstMode() == false)
                {
                    energy += journey.at(0) -> get_plusEnergy();
                    energy = (energy > 1000) ? 1000 : energy;
                    solarChange = true; //until mode change
                } 
                oxygen = 100;
            }

            switch(next_move())
            {   
                case(1):
                {   //** first Move: unit movement **//
                    move = unit_set(status);
                    energy -= journey.at(0) -> get_energyLoss();
                    oxygen -= journey.at(0) -> get_oxygenLoss();
                    speed = journey.at(0) -> get_speed();
                    currentPos++;
                    break;
                }
                case(2):
                {   //** second Move: at once movement  **//
                    int unit = unit_set(status);
                    int totalCount = (distance_sum.at(0) - distance)/unit;
                    int realCount = 0;

                    //** the Vehicle stops when there is not enough energy 
                    //** or oxygen, counting the times how far it can move.
                    for(int i = 0; i < totalCount; i++)
                    {
                        energy -= journey.at(0) -> get_energyLoss();
                        oxygen -= journey.at(0) -> get_oxygenLoss();
                        realCount++;
                        if(energy <= 0 || oxygen <= 0) break;
                    }
                    speed = journey.at(0) -> get_speed();
                    move = unit * realCount;
                    currentPos += realCount;
                    break;
                }
            }
            //** invalid range for distance, energy, oxygen check **//
            distance += move; // total movement record
            Vehicle* lastMode = journey.at(0);
            string finish_reason;

            if(mode_change(energy, oxygen, distance) == true)
            {
                finish_reason = finish(energy, oxygen);
                solarChange = false; //initialization for next mode
                energy = (energy < 0) ? 0 : energy;
                oxygen = (oxygen < 0) ? 0 : oxygen;

                modeRecord += lastMode -> current_status() + " > ";
                energyRecord += to_string(energy) + " > ";
                oxygenRecord += to_string(oxygen) + " > ";
                speedRecord += to_string(speed) + " > ";
            }
            graphic_record(lastPos, currentPos);
            lastPos = currentPos; // for the graphic movement
            bool finished = (finish_reason.length() > 13) ? true : false;

            if(finished == false && journey.size() != 0) 
            {
                cout << move_status(move);
                lastMode -> print(distance, energy, oxygen);
            }

            if(finished == true) // print final statements
            {
                cout << move_status(move);
                cout << final_status(energy, oxygen, distance);
                cout << graphic << "\n\n" << finish_reason << endl;
                cout << blackbox(modeRecord, energyRecord,
                                oxygenRecord, speedRecord);
                delete lastMode;
                break;
            }
        }        
    }  return 0;
}

void initializer() 
{
    while(!journey.empty())
        journey.pop_back();
        
    while(!distance_sum.empty())
        distance_sum.pop_back();

    graphic = "|";
    damaged = false;
}

void test_case() 
{
    TC[0] = "[R500T20H20],[S3000T10H5A2000D30],[O80T0D100W100]";
    TC[1] = "[R100T10H10],[S3000T10H5A2000D30],[O80T0D100W100],[S3000T10H5A2000D30]";
    TC[2] = "[R1000T20H20],[S2000T10H5A2000D30],[R100T20H20],[X],[S3000T10H5A2000D30]";
    TC[3] = "[R50T20H30],[S3000T10H5A2000D60],[O80T0D100W100],[R150T30H50]";
    TC[4] = "[R500T20H20],[S1000T10H5A2000D30],[R200T20H20],[O90T0D200W200],[S3000T20H100A3000D30]";
    TC[5] = "[R500T20H20],[Y],[S3000T10H5A2000D30],[R500T20H20],[O80T0D100W100],[S3000T10H5A2000D30]";
    TC[6] = "[R500T20H20],[Y],[S3000T10H5A2000D30],[R500T20H20],[O80T0D100W100],[S3000T10H5A2000D30]";
    TC[7] = "[R500T20H20],[S3000T10H5A2000D30],[R500T20H20],[O80T0D100W100],";
    TC[8] = "[R500T20H20],[S3000T10H5A2000D30],[Y],[R500T20H20],[O80T0D100W100],[S3000T10H5A2000D30]";
    TC[9] = "[R500T20H20],[S3000T10H5A2000D30],[R500T20H20],[O80T0D100W100],[S3000T10H5A2000D30]";
}

int choose_testCase() 
{
    cout << "Choose the number of the test case (1~10) : ";
    int n;
    cin >> n;
    
    if(n >= 1 && n <= 10) 
    {
        cout << "Test case #" << n << ".\n" << endl;
        tokenize(TC[n-1]);
        return 1;
    } 
    else 
        return 0;
}

void tokenize(string cases) 
{
    initializer();
    char testCase[cases.length() + 1];
    strcpy(testCase, cases.c_str());
    
    const char* delimiter = ",";
    int obstacle = 0; 
    int modeCount = 0; 

    char* pch = strtok(testCase, delimiter);
    vector<char*> a;

    while(pch != NULL)
    {
        a.push_back(pch);
        pch = strtok(NULL, delimiter);
            if(pch != NULL) 
            {
                if(strstr(pch, "X") != NULL || strstr(pch, "Y") != NULL)
                    obstacle++;
            }
        modeCount++;
    }
        modeCount -= obstacle;
        distance_sum.assign(modeCount, 0); 
        int index = 0; 

        for(int i = 0; i < modeCount + obstacle; i++) 
        {
            char* nextMode = a.at(i);
            if(strstr(nextMode, "R") != NULL) 
            {
                int a[3]; //road, temperature, humidity
                int *r = unit_tokenize(a, nextMode, index++, modeCount);
                Vehicle* car = new Car(r[1], r[2], (i==0)? true : false); 
                journey.push_back(car);
            }
            else if(strstr(nextMode, "S") != NULL)
            {
                int a[5]; // sky, temperature, humidity, altitude, density 
                int *s = unit_tokenize(a, nextMode, index++, modeCount);
                Vehicle* airplane = new Airplane(s[1], s[2], s[3], s[4]);
                journey.push_back(airplane);
            }
            else if(strstr(nextMode, "O") != NULL)
            {
                int a[4]; // ocean, temperature, depth, waterflow
                int *o = unit_tokenize(a, nextMode, index++, modeCount);
                Vehicle* submarine = new Submarine(o[1], o[2], o[3]);
                journey.push_back(submarine);
            }
            else 
            {
                if(strstr(nextMode, "X") != NULL && extraMode == true)
                {
                    Vehicle* X = new Vehicle(0, -100, -100);
                    X -> set_energyLoss(100);
                    X -> set_status("X");
                    journey.push_back(X);
                }
                else if(strstr(nextMode, "Y") != NULL && extraMode == true) 
                {
                    Vehicle* Y = new Vehicle(0, -100, -1);
                    Y -> set_oxygenLoss(30);
                    Y -> set_status("Y");
                    journey.push_back(Y);
                }
            } 
        }

    graphic = graphic.substr(0, 1) + "@" + graphic.substr(1) + "|";
    journey.at(0) -> set_speed(0);
    journey.at(0) -> print(0, 1000, 100);
    journey.at(0) -> set_speed(80);
}

int* unit_tokenize(int resources[], char* mode, int index, int modeCount)
{
    int k = 0; 
    int unit;
    string tok;
    string shape;
    //** setting for delimiter, unit distance, graphic string **//
    if(strstr(mode, "R") != NULL)
    {   // Road 
        tok = "[]RTH";
        unit = ROADUNIT;
        shape = "=";
    }
    else if(strstr(mode, "S") != NULL)
    {   // Sky
        tok = "[]STHAD";
        unit = SKYUNIT;
        shape = "^";
    }
    else 
    {   // Ocean
        tok = "[]OTDW";
        unit = OCEANUNIT;
        shape = "~";
    }
    //** resourses tokenize  **//
    char* delimiter = new char[tok.length() + 1];
    strcpy(delimiter, tok.c_str());
    char* pch = strtok(mode, delimiter);

    //** resourses saving **//
    while(pch != NULL)
    {  
        resources[k++] = atoi(pch);
        pch = strtok(NULL, delimiter);
    }
    //** distance accumulation **//
    for(int j = index; j < modeCount; j++)
        distance_sum[j] += resources[0];

    //** graphic string **//
    for(int j = 0; j < resources[0] / unit; j++)
        graphic += shape;

    return resources;
}

int next_move() 
{
    cout << "Next move? (1, 2)" << endl;
    cout << "CP-2014-19498>";
    int n;
    cin >> n;
    return n;
}

char* get_status() 
{
    string c = journey.at(0) -> current_status();
    char* status = new char[c.length() + 1];
    strcpy(status, c.c_str());
    return status;
}

bool percent(int n) 
{
    int random[100];
    srand((unsigned int)time(NULL));
    int i, j;

    //** 1~100 random number array without duplicate **//
    for(i = 0; i < 100 ; i++) {
        random[i] = rand() % 100+1;
        for(j = 0; j < i; j++) {
            if(random[i] == random[j]) {
                i--;    
                break;
            }
        }
    }
    srand((unsigned int)time(NULL));
    int m = rand() % 100;
    int percent = random[m];

    if(percent >=0 && percent <= n)
        return true;
    else 
        return false;
}

int unit_set(char* mode) 
{
    if(strstr(mode, "Car") != NULL)
        return ROADUNIT;
    else if(strstr(mode, "Airplane") != NULL)
        return SKYUNIT;
    else
        return OCEANUNIT;
}

string move_status(int move) 
{
    return "Successfully moved to next " + to_string(move) + " km\n";
}

bool mode_change(int energy, int oxygen, int distance) 
{
    if(energy <= 0 || oxygen <= 0 || distance == distance_sum.at(0))
    {
        if( distance == distance_sum.at(0))
        {   // if the mode ends, change the mode in vectors!
            distance_sum.erase(distance_sum.begin());
            journey.erase(journey.begin());
        }
        return true;
    }
    return false;
}

void graphic_record(int lastPos, int currentPos)
{
    graphic = graphic.substr(0, lastPos) + graphic.substr(lastPos + 1);
    graphic = graphic.substr(0, currentPos)+ "@" +graphic.substr(currentPos);
}

string final_status(int energy, int oxygen, int distance) 
{
    string finalStatus = "Final Status: \n";
    finalStatus += "Distance: " + to_string(distance) + " km" + "\n"
                + "Energy: " + to_string(energy) + "\n"
                + "Oxygen Level: " + to_string(oxygen) + "\n";
    return finalStatus;
}

string finish(int energy, int oxygen) 
{
    string line = "!FINISHED : ";
    if(energy >= 0 && oxygen >= 0 && journey.size() == 0) 
        line.append("Arrived");

    else if(energy <= 0) line += "Energy Failure";
    else if(oxygen <= 0) line += "Oxygen Failure";
    return line;
}

string blackbox(string mode, string energy, string oxygen, string speed)
{   
    string blackbox = "Blackbox:\n";
    blackbox += mode.substr(0, mode.length()-2) + "\n"
            +  energy.substr(0, energy.length()-2) + "\n"
            +  oxygen.substr(0, oxygen.length()-2) + "\n"
            +  speed.substr(0, speed.length()-2) + "\n"
            +  "------------------------------------------" + "\n";
    return blackbox;
}