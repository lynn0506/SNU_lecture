//2014-19498 Jung, Eunjoo
import java.util.Scanner;

public class myAssignment {
	final String OCCU_STUDENT = "Student";
	final String OCCU_PROFESSOR = "Professor";
	final String OCCU_TA = "TA";
	final String DISPLAY =
			 " A       add ContactEntry\n"
			+" L       add Last\n"
			+" E       Check if Empty\n"
			+" S       Search for Contact\n"
			+" P       Print Contacts\n"
			+" Z       Print Names\n"
			+" N       List Current Size\n"
			+" Q       Quit\n"
			+" R       Remove ContactEntry\n"
			+" T       Sort\n"
			+" U       Remove Duplicates\n"
			+" ?       Display Help\n";

	
	private Scanner inputScan;
	private String input;
	private int index;
	private myLinkedList<ContactEntry> personInfo = new myLinkedList<ContactEntry>();
	
	
	public static void main(String[] args) {
		myAssignment Control = new myAssignment();
		Control.index = -1;
		Control.inputScan = new Scanner(System.in);
		System.out.println(Control.DISPLAY);
		System.out.println("What action would you like to perform?");
		Control.input = Control.inputScan.next();
		
		while(Control.input != "Q") {
			switch(Control.input) {
			case("A"): case("a"):
				Control.ContactEntryInput();
				break;
			case("L"): case("l"):
				Control.index++;
				Control.addLast();
				break;
			case("E"): case("e"):
				Control.isEmpty();
				break;
			case("S"): case("s"):
				Control.searchContact();
				break;
			case("P"): case("p"):
				Control.printContact();
			    break;
			case("Z"): case("z"):
				Control.printName();
				break;
			case("N"): case("n"):
				Control.currentSize();
				break;
			case("Q"): case("q"):
				System.out.println("Program Finished");
				return;
			case("R"): case("r"):
				Control.removeContact();
				break;
			case("T"): case("t"):
				Control.sort();
				break;
			case("U"): case("u"):
				Control.removeDuplicate();
				break;
			case("?"):
				System.out.println(Control.DISPLAY);
				break;
			}
			System.out.println("What action would you like to perform?");
			Control.input = Control.inputScan.next();
		}	
		
		
	}
	
	public void sort() {
		personInfo.sort();
		System.out.println("list sorted");
	}
	    
	 public void removeDuplicate() {
	    boolean duplicate = personInfo.duplicate();
	    if(duplicate)
	    	System.out.println("Duplicate contact removed");
		else
	    	System.out.println("there is no duplicate");
	}

	private void removeContact() {
		System.out.println("Please enter the index of the list to remove: ");
			int inputIndex = inputScan.nextInt();
			boolean inputCheck = true;
  	
		if(personInfo.isEmpty()) {
			System.out.println("Empty list");
  		return;
		}
  	
	  	while(inputCheck) {
	  		if(inputIndex < 0 || inputIndex > index) {
	  			System.out.println("It does not exist.");
	  			System.out.println("Please enter the index of the list to remove");
	  			inputIndex = inputScan.nextInt();
	  		} else {
	  			System.out.println("'"+ ((ContactEntry) personInfo.remove(inputIndex)).getName() +"' was removed.");
	  			index--;
	  			return;
	  		}
  	}		
	}

	private void currentSize() {
		System.out.println("The current size is: " + personInfo.size());
	}

	private void printName() {
		System.out.print("{ ");
		if(!personInfo.isEmpty()) {
			personInfo.printName();
		} System.out.println("}");
	}

	private void printContact() {
		System.out.println("Student:");
	  	personInfo.printContact(OCCU_STUDENT);
	  	System.out.println();
	
	  	System.out.println("Professor:");
	  	personInfo.printContact(OCCU_PROFESSOR);
	  	System.out.println();
	  	
	  	System.out.println("TA:");
	  	personInfo.printContact(OCCU_TA);
	  	System.out.println();
	}

	private void searchContact() {
		System.out.println("Please enter an index to search: ");
		  	int searchIndex = inputScan.nextInt();
		  	boolean check = true;
  	
		if(personInfo.isEmpty()) {
			System.out.println("the list is empty");
			return;
		} 
  	
	  	while(check) {
	  		if(searchIndex < 0 || searchIndex > index) {
	  			System.out.println("index you want is not in the list");
	      		System.out.println("Please enter an index to search: ");
	      		searchIndex = inputScan.nextInt();
	      	} else {
	      		personInfo.searchContact(searchIndex);
	      		return;
	      	} 
	    }		 
	}

	public void addLast() {
		if(personInfo.isEmpty())
			personInfo.add(0, infoInput());
		else
			personInfo.lastAdd_(infoInput());
	}

	
	public void isEmpty() {
		if(personInfo.isEmpty())
			System.out.println("This list is Empty");
		else
			System.out.println("The list contains some element(s)");
	}
	

	public ContactEntry infoInput() {
		ContactEntry setInfo = new ContactEntry();
		System.out.println("Please enter a name:");
		String tempName = inputScan.next();
		setInfo.setName(tempName);
		
		System.out.println("Please enter a phone number:");
		String tempNumber = inputScan.next();
		setInfo.setNumber(tempNumber);
		
		System.out.println("Please enter an occupation:");
		String tempOccu = inputScan.next();
		setInfo.setOccu(tempOccu);
		return setInfo;
	}

	private void ContactEntryInput() {
		System.out.println("Please enter an index to add:");
		int inputNum = inputScan.nextInt();
		boolean inputCheck = true;
		
		while(inputCheck) {
			if((inputNum != 0 && personInfo.isEmpty()) || (inputNum != index+1 && !personInfo.isEmpty())) {
				System.out.println("The last index is " + index);
				System.out.println("Please enter an index to add:");
				inputNum = inputScan.nextInt();
			} else 
				inputCheck = false;
		}	
		index++;
		personInfo.add(index, infoInput());
		}
	}

