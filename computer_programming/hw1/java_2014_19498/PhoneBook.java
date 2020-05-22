//2014-19498 Jung, Eunjoo
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Calendar;
import java.util.regex.*;

public class PhoneBook {
    final String REGEX_NUM = "^(010)-\\d{4}-\\d{4}$";
    final String REGEX_NUM2 = "^(02)-\\d{4}-\\d{4}$";
    final String REGEX_NAME = "^[a-zA-Z]+ [a-zA-Z]+$";
    final String REGEX_TEAM = "^[a-zA-z0-9]+$";
    final String REGEX_BIRTH = "^\\d{6}$";
    final String REGEX_AGE = "^\\d{1,3}$";

    final int NAME = 1;
    final int NUMBER = 2;
    final int TEAM = 3;
    final int BIRTHDAY = 4;
    final int AGE = 5;

    private BufferedReader inputScan = new BufferedReader(new InputStreamReader(System.in));
    private Scanner bufferScan;

    private Person PersonInfo;
    private List<Person> phoneBook = new ArrayList<>();
    private int index;

    public static void main(String[] args) throws IOException
    {
        PhoneBook Info = new PhoneBook();
        boolean keep = true;
        Info.index = -1;
        String input;
        Info.menuPrint();

        while(keep)
        {
            Info.promptLine();
            input = Info.inputScan.readLine();

            switch(input)
            {
                case("1"):
                    Info.addPerson();
                    break;

                case("2"):
                    Info.removePerson();
                    break;

                case("3"):
                    Info.printPhoneBook();
                    break;

                case("exit"):
                    keep = false;
                    return;

                case(""):
                    Info.menuPrint();
                    break;

                default:
                    break;
            }
        }
    }


    public void promptLine()
    {
        System.out.print("CP-2014-19498>");
    }


    public void addPerson() throws IOException
    {
        String firstName;
        String lastName;
        String tempName;
        String phoneNumber;
        String team;
        String choice;
        String birthday;
        int age;

        System.out.println("Select Type");
        System.out.println("1.Person\n2.Work\n3.Family\n4.Friend");
        promptLine();

        choice = inputScan.readLine();

        while(true)
        {
            if(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4"))
                break;

            promptLine();
            choice = inputScan.readLine();
        }

        // name Input
        tempName = inputCheck(NAME);
        bufferScan = new Scanner(tempName);
        firstName = bufferScan.next();
        lastName = bufferScan.next();

        // phone Number
        phoneNumber = inputCheck(NUMBER);


        switch(choice)
        {
            case("1"):
                PersonInfo = new Person(firstName, lastName, phoneNumber);
                break;

            case("2"):
                team = inputCheck(TEAM);
                PersonInfo = new Work(firstName, lastName, phoneNumber, team);
                break;

            case("3"):
                birthday = inputCheck(BIRTHDAY);
                PersonInfo = new Family(firstName, lastName, phoneNumber, birthday);
                break;

            case("4"):
                age = Integer.parseInt(inputCheck(AGE));
                PersonInfo = new Friend(firstName, lastName, phoneNumber, age);
                break;

            default:
                break;
        }

        index++;
        phoneBook.add(index,PersonInfo);
        System.out.println("Successfully added new person.");
    }

    public String inputCheck(int n) throws IOException
    {
        String input = "";

        switch(n)
        {
            case(1):
                while(true)
                {
                    System.out.print("Name:");
                    input = inputScan.readLine();
                    if(input.matches(REGEX_NAME))
                        break;

                } break;

            case(2):
                while(true)
                {
                    System.out.print("Phone_number:");
                    input = inputScan.readLine();
                    if((input.matches(REGEX_NUM) || input.matches(REGEX_NUM2)))
                        break;

                } break;

            case(3):
                while(true)
                {
                    System.out.print("Team:");
                    input = inputScan.readLine();
                    if(input.matches(REGEX_TEAM))
                        break;

                } break;

            case(4):
                while(true)
                {
                    System.out.print("Birthday:");
                    input = inputScan.readLine();
                    if(input.matches(REGEX_BIRTH))
                        break;

                } break;

            case(5):
                while(true)
                {
                    System.out.print("Age:");
                    input = inputScan.readLine();
                    if (input.matches(REGEX_AGE))
                        break;

                }
                break;

            default:
                break;
        }
        return input;
    }

    public void removePerson() throws IOException
    {
        System.out.print("Enter index of person:");
        String temp = inputScan.readLine();
        int indexNum = Integer.parseInt(temp);

        if(indexNum > index + 1 || indexNum <= 0)
        {
            System.out.println("Person does not exist!");
        }
        else
        {
            phoneBook.remove(indexNum - 1);
            index--;
            System.out.println("A person is successfully deleted from the Phone Book!");
        }

    }

    public void printPhoneBook()
    {
        System.out.println("Phone Book Print");

        if(phoneBook.isEmpty())
            return;

        for(int i = 0; i < index + 1; i++)
        {
            System.out.print((i + 1) + ". ");
            (phoneBook.get(i)).print();
            System.out.println();
        }
    }

    public void menuPrint()
    {
        System.out.println("Phone Book");
        System.out.println("1. Add person");
        System.out.println("2. Remove person");
        System.out.println("3. Print phone book");
    }

    public class Person
    {
        private String firstName;
        private String lastName;
        private String phoneNumber;

        public Person(String firstName, String lastName, String phoneNumber)
        {
            setFirstName(firstName);
            setLastName(lastName);
            setPhoneNumber(phoneNumber);
        }

        public void setFirstName(String Name) {
            this.firstName = Name;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setLastName(String Name) {
            this.lastName = Name;
        }
        public String getLastName() {
            return lastName;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void print() {
            System.out.print(getFirstName() + " " + getLastName() + "_" + getPhoneNumber());
        }
    }

    class Family extends Person
    {
        private String birthday;
        private int dDayCount;


        public Family(String firstName, String lastName, String phoneNumber, String birthday)
        {
            super(firstName, lastName, phoneNumber);
            setBirthday(birthday);
            dDayCount = 0;
        }

        public void setBirthday(String birthDay)
        {
            this.birthday = birthDay;
        }

        public String getBirthday()
        {
            return birthday;
        }

        public int dDay()
        {
            int year = Integer.parseInt(birthday.substring(0,2));
            int month = Integer.parseInt(birthday.substring(2,4));
            int date = Integer.parseInt(birthday.substring(4,6));

            Calendar today = Calendar.getInstance();
            Calendar cal = Calendar.getInstance();
            cal.set(year, month-1, date);

            long data = cal.getTimeInMillis() - today.getTimeInMillis();
            cal.setTimeInMillis(data);

            dDayCount = cal.get(Calendar.DAY_OF_YEAR) -1;

            if(dDayCount < 0)
            {
                dDayCount *= (-1);
            }
            return dDayCount;
        }

        public void print()
        {
            super.print();
            System.out.print("_" + getBirthday() + "_" + this.dDay());
        }
    }

    class Work extends Person
    {
        private String team;

        public Work(String firstName, String lastName, String phoneNumber, String teamName)
        {
            super(firstName, lastName, phoneNumber);
            setTeam(teamName);
        }

        public void setTeam(String team)
        {
            this.team = team;
        }

        public String getTeam()
        {
            return team;
        }

        public void print()
        {
            super.print();
            System.out.print("_" + getTeam());
        }
    }

    class Friend extends Person
    {
        private int age;

        public Friend(String firstName, String lastName, String phoneNumber, int age)
        {
            super(firstName, lastName, phoneNumber);
            setAge(age);
        }

        public void setAge(int age)
        {
            this.age = age;
        }

        public int getAge()
        {
            return age;
        }

        public void print()
        {
            super.print();
            System.out.print("_" + getAge());
        }
    }
}


