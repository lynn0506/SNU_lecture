

public class hashTest {
	public static void main(String[] args) {
		
		MyHash myhash = new MyHash(17);
		
		myhash.add(34);
		myhash.add(0);
		myhash.add(33);
		myhash.add(6);
		myhash.add(7);
		myhash.add(23);
		myhash.add(28);
		myhash.add(12);
		myhash.add(29);
		myhash.add(11);
		myhash.add(30);
		myhash.add(45);
		
		System.out.println(myhash.toString());
		System.out.println(myhash.toString2());
		
		myhash.remove(0);
		System.out.println(myhash.toString());
		System.out.println(myhash.toString2());
		
		myhash.add(51);
		myhash.add(52);
		myhash.add(53);
		myhash.add(54);
		myhash.add(55);
		myhash.add(56);
		System.out.println(myhash.toString());
		System.out.println(myhash.toString2());
}

}
