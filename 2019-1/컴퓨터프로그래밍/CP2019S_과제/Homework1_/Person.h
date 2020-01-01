//2014-19498 Jung Eunjoo
#ifndef __PERSON_H
#define __PERSON_H

#include <iostream>
#include <string>

using namespace std;
using std::string;

class Person {
public:
    Person(string &first, string &last, string &phone);
    void setFirstName(string &first);
    string getFirstName();
    void setLastName(string &last);
    string getLastName();
    void setPhoneNumber(string &phone);
    string getPhoneNumber();
    void virtual print();

private:
    string firstName;
    string lastName;
    string phoneNumber;
};

class Work : public Person {
public:
    Work(string &firstName, string &lastName, string &phoneNumber, string &teamName);
    void setTeam(string &team);
    string getTeam();
    void print();

private:
    string team;

};

class Family : public Person {
public:
    Family();
    Family(string &firstName, string &lastName, string &phoneNumber, string &day);
    void setBirthday(string &day);
    string getBirthday();
    int dDay();
    void virtual print();

private:
    string birthday;
    int d_day;
};

class Friend : public Person {
public:
    Friend(string &firstName, string &lastName, string &phoneNumber, int &num);
    void setAge(int &num);
    int getAge();
    void print();

private:
    int age;
};

#endif //__PERSON_H

