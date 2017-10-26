package hw4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PolyTest {
	public static void main(String[] args) {
		RatPoly test1 = new RatPoly(1, 1);
		System.out.println(test1);
		RatPoly test2 = new RatPoly();
		for (int i = 0; i < 10; i++) {
			test2 = test2.add(new RatPoly(i, i));
		}
		System.out.println(test2);
		Random r = new Random();
		RatPoly test3 = new RatPoly();
		for (int i = 0; i < 10; i++) {
			test3 = test3.add(new RatPoly(r.nextInt(20), r.nextInt(20)));
		}
		System.out.println(test3);
		
		RatPoly test4 = new RatPoly();
		for (int i = 0; i < r.nextInt(20); i++) {
			test4 = test4.add(new RatPoly(r.nextInt(100), r.nextInt(100)));
		}
		System.out.println(test4);
		int expt = 87696968;
		System.out.println(expt);
	}
}
