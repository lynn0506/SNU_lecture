//2014-19498 Jung, Eunjoo

public class ContactEntry implements Comparable<ContactEntry> {

	final String OCCU_STUDENT = "Student";
	final String OCCU_PROF = "Professor";
	final String OCCU_TA = "TA";
	
  //class fields (leave them as private)
  private String name;
  private String phoneNumber;
  private String occupation; //Student, Professor, or TA

  public ContactEntry() {
  	name = null;
  	phoneNumber = null;
  	occupation = null;
  }
  
  public boolean isEmpty() {
  	if(name == null)
  		return true;
  	else
  		return false;
	}

  public void setName(String name) {
  	this.name = name;
  }
  
  public void setNumber(String phoneNumber) {
  	this.phoneNumber = phoneNumber;
  }
  
  public void setOccu(String occupation) {
  	this.occupation = occupation;
  }
  
  public String getName() {
  	return name;
  }
  
  public String getNumber() {
  	return phoneNumber;
  }
  
  public String getOccu() {
  	return occupation;
  }
  
  public void printName() {
  	System.out.print(name+ " ");
  }
  
  public void printInfo() {
	System.out.println(name+ " " + phoneNumber);
  }
  
  @Override
  public int compareTo(ContactEntry o) {
		return this.name.compareToIgnoreCase(o.name);
	}
  
  public boolean compareAll(ContactEntry o) {
  	if (this.getName().compareToIgnoreCase(o.getName()) != 0)
  		return false;
  	else if (this.getNumber().compareToIgnoreCase(o.getNumber()) != 0)
  		return false;
  	else if(this.getOccu().compareToIgnoreCase(o.getOccu()) != 0 )
  		return false;
  	else
  		return true;
  }
 
}
