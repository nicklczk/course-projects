package hw3;


public class ComparatorTest {
	public static void main(String[] args) {
		Box box = new Box(100000);
		box.add(new Ball(9.0));
		box.add(new Ball(9.0));
		box.add(new Ball(10.0));
		box.add(new Ball(8.0));
		box.add(new Ball(8.0));
		for (Ball b : box) {
			System.out.println("" + b.getVolume());
		}
	}
}
