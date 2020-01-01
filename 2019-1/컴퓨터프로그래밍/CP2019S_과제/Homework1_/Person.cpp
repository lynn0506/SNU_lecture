//2014-19498 Jung Eunjoo
#include "Person.h"
#include <iostream>
#include <vector>
#include <regex>
#include <string>

using namespace std;
using std::string;
using std::getline;

#include <string>
#include <cstring>
#include <iostream>
#include <vector>
#include <regex>

enum
{
    PERSON = 1, WORK = 2, FAMILY = 3, FRIEND = 4,
};

regex REGEX_PHONE("^(010)-\\d{4}-\\d{4}$"); // only '010-XXXX-XXXX' form
regex REGEX_HOME("^(02)-\\d{4}-\\d{4}$"); // only '02-xxxx-xxxx' form
regex REGEX_NAME("^[a-zA-Z]+ [a-zA-Z]+$");
regex REGEX_TEAM("^[a-zA-z0-9]+$");// alphabets and numbers
regex REGEX_BIRTH("^\\d{6}$"); // 6 digit numbers
regex REGEX_AGE("^\\d{1,3}$");

void menu_print();
void prompt_line();
void get_name(string &fullName, string &firstName, string &lastName);
void get_phone(string &phoneNumber);
void get_team(string &team);
void get_birthday(string &birthday);
void get_age(string &age);
Person* add_person();
int remove_person(int phoneBookIndex);
void print_phoneBook(vector<Person*> phoneBook);



//PERSON CLASS
Person::Person(string &first, string &last, string &phone)
{
    this->firstName.append("");
    this->lastName.append("");
    this->phoneNumber.append("");
    setLastName(last);
    setFirstName(first);
    setPhoneNumber(phone);
}

void Person::setFirstName(string &first)
{
    this->firstName.clear();
    this->firstName.append(first);
}

string Person::getFirstName()
{
    return firstName;
}

void Person::setLastName(string &last)
{
    this->lastName.clear();
    this->lastName.append(last);
}

string Person::getLastName()
{
    return lastName;
}

void Person::setPhoneNumber(string &phone)
{
    this->phoneNumber.clear();
    this->phoneNumber.append(phone);
}

string Person::getPhoneNumber()
{
    return phoneNumber;
}

void Person::print()
{
    cout << getFirstName() << " " << getLastName() << "_" << getPhoneNumber();
}


// WORK CLASS
Work::Work(string &firstName, string &lastName, string &phoneNumber, string &teamName)
        : Person(firstName, lastName, phoneNumber)
{
    this->team.append("");
    setTeam(teamName);
}

void Work::setTeam(string &teamName)
{
    this->team.clear();
    this->team.append(teamName);
}

string Work::getTeam()
{
    return team;
}

void Work::print()
{
    Person::print();
    cout << "_" << getTeam();
}


//FAMILY
Family::Family(string &firstName, string &lastName, string &phoneNumber, string &day)
        : Person(firstName, lastName, phoneNumber)
{
    this->birthday.append("");
    setBirthday(day);
}

void Family::setBirthday(string &day)
{
    this -> birthday.clear();
    this -> birthday.append(day);
}

string Family::getBirthday()
{
    return birthday;
}

int Family::dDay()
{
    d_day = 0;
    int months[] = {31, 28, 31, 30, 31, 30, 31, 30, 31, 30, 31, 30};
    int total = 0;
    int total2 = 0;
    int difference = 0;

    time_t timer;
    struct tm *t;

    timer = time(NULL);
    t = localtime(&timer);

    int current_month = t->tm_mon + 1;
    int current_date = t->tm_mday;

    int month = stoi(getBirthday().substr(2, 2));
    int date = stoi(getBirthday().substr(4));

    for (int i = 0; i < month - 1; i++) {
        total += months[i];
    }
    total += date;

    for (int i = 0; i < current_month - 1; i++) {
        total2 += months[i];
    }
    total2 += current_date;

    difference = total - total2;
    d_day = (difference >= 0) ? difference : (365 + difference);

    return d_day;
}

void Family::print()
{
    Person::print();
    cout << "_" << getBirthday() << "_" << this->dDay();
}

//FRIEND
Friend::Friend(string &firstName, string &lastName, string &phoneNumber, int &num)
        : Person(firstName, lastName, phoneNumber)
{
    this -> age = 0;
    setAge(num);
}

void Friend::setAge(int &num)
{
    this -> age = num;
}

int Friend::getAge()
{
    return age;
}

void Friend::print()
{
    Person::print();
    cout << "_" << getAge();
}

int main()
{
    string input;
    int phoneBook_index = -1;

    vector<Person*> phoneBook;

    menu_print();
    bool check = true;

    while(check)
    {
        prompt_line();
        getline(cin, input);

        if(input.length() == 0)
            menu_print();

        else if(input.compare("exit") == 0)
            check = false;

        else if(input.compare("1") == 0)
        {
            phoneBook.push_back(add_person());
            phoneBook_index++;
        }

        else if(input.compare("2") == 0)
        {
            int remove_num = remove_person(phoneBook_index);
            if(remove_num != -1)
            {
                phoneBook_index--;
                phoneBook.erase(phoneBook.begin() + remove_num - 1);
            }
        }

        else if(input.compare("3") == 0)
            print_phoneBook(phoneBook);

        else
            continue;
    }

    for(int i = 0; i < phoneBook.size(); i++)
        delete phoneBook[i];

    return 0;
}


void menu_print()
{
    cout << "Phone Book"<< endl;
    cout << "1->Add person" << endl;
    cout << "2->Remove person" << endl;
    cout << "3->Print phone book" << endl;
}


void prompt_line()
{
    cout << "CP-2014-19498>";
}

void get_name(string &firstName, string &lastName)
{
    string full_name;
    firstName.clear();
    lastName.clear();

    while(true)
    {
        cout << "name:";
        getline(cin, full_name);

        if(regex_match(full_name, REGEX_NAME))
        {
            break;
        }
    }
    int space_num = full_name.find(" ");
    char *str_buff = new char[1000];
    strcpy(str_buff, full_name.c_str());

    char *tok = strtok(str_buff, " ");
    firstName.append(string(tok));

    tok = strtok(NULL, " ");
    lastName.append(string(tok));
}

void get_phone(string &phoneNumber)
{
    while(true)
    {
        cout << "Phone number:";
        getline(cin, phoneNumber);

        if(regex_match(phoneNumber, REGEX_PHONE) || (regex_match(phoneNumber, REGEX_HOME)))
            break;
    }
}

void get_team(string &team)
{
    while(true)
    {
        cout << "Team:";
        getline(cin, team);

        if(regex_match(team, REGEX_TEAM))
            break;
    }
}

void get_birthday(string &birthday)
{
    while(true)
    {
        cout << "Birthday:";
        getline(cin, birthday);

        if(regex_match(birthday, REGEX_BIRTH))
            break;
    }
}

void get_age(string &age)
{
    while(true)
    {
        cout << "Age:";
        getline(cin, age);

        if(regex_match(age, REGEX_AGE))
            break;
    }
}

Person* add_person()
{
    Person* person;
    string first_name;
    first_name.append("");
    string last_name;
    last_name.append("");

    string phone_number;
    string team;
    string birthday;
    string temp_age;
    int age = 0;

    cout << "Select Type" << endl;
    cout << "1->Person\n2->Work\n3->Family\n4->Friend" << endl;
    prompt_line();

    string type_input;
    getline(cin, type_input);
    int choice = atoi(type_input.c_str());

    while((type_input.length() == 0) ||
          (type_input.compare("1") != 0 && type_input.compare("2") != 0
           && type_input.compare("3") != 0 && type_input.compare("4") != 0))
    {
        prompt_line();
        getline(cin, type_input);
    }

    get_name(first_name, last_name);
    get_phone(phone_number);

    switch(choice)
    {
        case(PERSON):
        {
            person = new Person(first_name, last_name, phone_number);
            break;
        }

        case(WORK):
        {
            get_team(team);
            person = new Work(first_name, last_name, phone_number, team);
            break;
        }

        case(FAMILY):
        {
            get_birthday(birthday);
            person = new Family(first_name, last_name, phone_number, birthday);
            break;
        }

        case(FRIEND):
        {
            get_age(temp_age);
            age = atoi(temp_age.c_str());
            person = new Friend(first_name, last_name, phone_number, age);
            break;
        }
    }

    cout << "successfully added new person." << endl;
    return person;
}

int remove_person(int phoneBookIndex)
{
    string empty_line;
    int index_num;

    cout << "Enter index of person:";
    cin >> index_num;
    getline(cin, empty_line);

    if(index_num > phoneBookIndex + 1 || index_num == 0)
    {
        cout<< "Person does not exist!" << endl;
        return -1;
    }

    else if(index_num < 0)
    {
        cout << "Invalid Input!" << endl;
        return -1;
    }

    else
    {
        cout << "A person is successfully deleted from the Phone Book!" << endl;
        return index_num;
    }
}


void print_phoneBook(vector<Person*> phoneBook)
{
    cout << "Phone Book Print" << endl;

    for(int i = 0; i < phoneBook.size(); i++)
    {
        cout<< i+1 << ". ";
        phoneBook.at(i) -> print();
        cout << endl;
    }
}


