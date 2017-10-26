package hw4;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <b>RatPoly</b> represents an immutable single-variate polynomial expression.
 * RatPolys are sums of RatTerms with non-negative exponents.
 * <p>
 *
 * Examples of RatPolys include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
 */
// See RatNum's documentation for a definition of "immutable".
public final class RatPoly {
	/*
	public static void main(String[] args) {
		/*
		RatPoly test, test1, test2;
		RatTerm term1, term2;
		List<RatTerm> list, list1;
		
		// RatPoly: 0, degree 0 using second constructor
		test = new RatPoly(new RatTerm(new RatNum(0), 3));
		System.out.println(test.terms);
		System.out.println("Degree " + test.degree());
		
		// RatPoly: 2*x^3, degree 3 using second constructor 
		test = new RatPoly(new RatTerm(new RatNum(2), 3));
		System.out.println(test.terms);
		System.out.println("Degree " + test.degree());
		
		// RatPoly: 0, degree 0 using third constructor
		test = new RatPoly(0, 0);
		System.out.println(test.terms);
		System.out.println("Degree " + test.degree());
		
		// RatPoly: 2*x^3, degree 3 using third constructor
		test = new RatPoly(2, 3);
		System.out.println(test.terms);
		System.out.println("Degree " + test.degree());
		
		// Empty list
		list = new ArrayList<RatTerm>();
		list1 = new ArrayList<RatTerm>();
		System.out.println(list);
		
		// x^12: adding on empty list
		sortedInsert(list, new RatTerm(new RatNum(1), 12));
		System.out.println(list);
		
		// List of RatTerms from x^1 to x^11 excluding x^4
		for (int i = 11; i >= 1; i--) {
			if (i != 4) {
				list.add(new RatTerm(new RatNum(1), i));
				list1.add(new RatTerm(new RatNum(1), i));
			}
		}
		System.out.println(list);
		
		// x^13: expt bigger than anything in the list
		sortedInsert(list, new RatTerm(new RatNum(1), 13));
		System.out.println(list);
		
		// x^0; expt smaller than anything in the list
		sortedInsert(list, new RatTerm(new RatNum(1), 0));
		System.out.println(list);
		
		// x^4: expt smaller than something in the list
		sortedInsert(list, new RatTerm(new RatNum(1), 4));
		System.out.println(list);
		
		// x^4: expt same as something in the list
		sortedInsert(list, new RatTerm(new RatNum(1), 4));
		System.out.println(list);
		
		// negate previous RatPoly
		test1 = new RatPoly(list);
		test1 = test1.negate();
		System.out.println(test1.terms);
		
		// getTerm degree 14 (does not exist)
		test2 = new RatPoly(list1);
		System.out.println(list1);
		System.out.println(test2.getTerm(14));
		
		// getTerm degree 4 (does not exist)
		System.out.println(test2.getTerm(4));
		
		// getTerm degree 7 (exists)
		System.out.println(test2.getTerm(7));
		
		// Multiply list1 by 3
		scaleCoeff(list1, new RatNum(3));
		System.out.println(list1);
		
		// Increment expt by 3
		incremExpt(list1, 3);
		System.out.println(list1);
		
		// copy test2
		System.out.println(test2);
		System.out.println(test2.copy());
		
		// removing everything from list1
		System.out.println(list1);
		list1.removeAll(list1);
		System.out.println(list1);
		
		
		RatPoly u = new RatPoly(1, 3);
		u = u.add(new RatPoly(1, 1));
		u = u.sub(new RatPoly(1, 0));
		System.out.println("u = " + u);
		
		RatPoly v = new RatPoly(1, 1);
		v = v.add(new RatPoly(1, 0));
		System.out.println("v = " + v);
		
		RatPoly quotient = u.div(v);
		System.out.println("u/v = " + quotient);
		System.out.println();
		
		u = new RatPoly(1, 1);
		System.out.println("u = " + u);
		v = new RatPoly(1, 1);
		System.out.println("v = " + v);
		quotient = u.div(v);
		System.out.println("u/v = " + quotient);
		
		RatPoly test = new RatPoly(4, 2);
		test = test.add(new RatPoly(2, 1));
		System.out.println("dq/dx = " + test);
		test = test.antiDifferentiate(RatNum.ZERO);
		System.out.println("q = " + test);
		
	}
	
  /** Holds all the RatTerms in this RatPoly */
  private final List<RatTerm> terms;

  // Definitions:
  // For a RatPoly p, let C(p,i) be "p.terms.get(i).getCoeff()" and
  // E(p,i) be "p.terms.get(i).getExpt()"
  // length(p) be "p.terms.size()"
  // (These are helper functions that will make it easier for us
  // to write the remainder of the specifications. They are not
  // executable code; they just represent complex expressions in a
  // concise manner, so that we can stress the important parts of
  // other expressions in the spec rather than get bogged down in
  // the details of how we extract the coefficient for the 2nd term
  // or the exponent for the 5th term. So when you see C(p,i),
  // think "coefficient for the ith term in p".)
  //
  // Abstraction Function:
  // RatPoly, p, represents the polynomial equal to the sum of the
  // RatTerms contained in 'terms':
  // sum (0 <= i < length(p)): p.terms.get(i)
  // If there are no terms, then the RatPoly represents the zero
  // polynomial.
  //
  // Representation Invariant for every RatPoly p:
  // terms != null &&
  // forall i such that (0 <= i < length(p)), C(p,i) != 0 &&
  // forall i such that (0 <= i < length(p)), E(p,i) >= 0 &&
  // forall i such that (0 <= i < length(p) - 1), E(p,i) > E(p, i+1)
  // In other words:
  // * The terms field always points to some usable object.
  // * No term in a RatPoly has a zero coefficient.
  // * No term in a RatPoly has a negative exponent.
  // * The terms in a RatPoly are sorted in descending exponent order.
  // (It is implied that 'terms' does not contain any null elements by the
  // above
  // invariant.)

  /** A constant holding a Not-a-Number (NaN) value of type RatPoly */
  public static final RatPoly NaN = new RatPoly(RatTerm.NaN);

  /** A constant holding a zero value of type RatPoly */
  public static final RatPoly ZERO = new RatPoly();

  /**
   * @effects Constructs a new Poly, "0".
   */
  public RatPoly() {
    terms = new ArrayList<RatTerm>();
    checkRep();
  }

  /**
   * @param rt The single term which the new RatPoly equals.
   * @requires rt.getExpt() >= 0
   * @effects Constructs a new Poly equal to "rt". If rt.getCoeff() is zero,
   *          constructs a "0" polynomial.
   */
  public RatPoly(RatTerm rt) {
    this();
    if (!rt.getCoeff().equals(RatNum.ZERO)) {
    	this.terms.add(rt);
    }
    checkRep();
  }

  /**
   * @param c The constant in the term which the new RatPoly equals.
   * @param e The exponent in the term which the new RatPoly equals.
   * @requires e >= 0
   * @effects Constructs a new Poly equal to "c*x^e". If c is zero, constructs
   *          a "0" polynomial.
   */
  public RatPoly(int c, int e) {
    this(new RatTerm(new RatNum(c), e));
  }

  /**
   * @param rt A list of terms to be contained in the new RatPoly.
   * @requires 'rt' satisfies clauses given in rep. invariant
   * @effects Constructs a new Poly using 'rt' as part of the representation.
   *          The method does not make a copy of 'rt'.
   */
  private RatPoly(List<RatTerm> rt) {
    terms = rt;
    // The spec tells us that we don't need to make a copy of 'rt'
    checkRep();
  }

  /**
   * Returns the degree of this RatPoly.
   *
   * @requires !this.isNaN()
   * @return the largest exponent with a non-zero coefficient, or 0 if this is
   *         "0".
   */
  public int degree() {
  	if (this.terms.isEmpty()) {
  		return 0;
  	}
    return this.terms.get(0).getExpt();
  }

  /**
   * Gets the RatTerm associated with degree 'deg'
   *
   * @param deg The degree for which to find the corresponding RatTerm.
   * @requires !this.isNaN()
   * @return the RatTerm of degree 'deg'. If there is no term of degree 'deg'
   *         in this poly, then returns the zero RatTerm.
   */
  public RatTerm getTerm(int deg) {
    if (deg > this.degree()) {
    	return RatTerm.ZERO;
    }
    for (RatTerm term : terms) {
    	if (term.getExpt() == deg) {
    		return new RatTerm(term.getCoeff(), term.getExpt());
    	}
    }
    return RatTerm.ZERO;
    //throw new RuntimeException("RatPoly->getTerm() is not yet implemented");
  }

  /**
   * Returns true if this RatPoly is not-a-number.
   *
   * @return true if and only if this has some coefficient = "NaN".
   */
  public boolean isNaN() {
    for (RatTerm term: terms) {
    	if (term.getCoeff().isNaN()) {
    		return true;
    	}
    }
    return false;
  }

  /**
   * Scales coefficients within 'lst' by 'scalar' (helper procedure).
   *
   * @param lst The RatTerms to be scaled.
   * @param scalar the value by which to scale coefficients in lst.
   * @requires lst, scalar != null
   * @modifies lst
   * @effects Forall i s.t. 0 <= i < lst.size(), if lst.get(i) = (C . E) then
   *          lst_post.get(i) = (C*scalar . E)
   * @see hw4.RatTerm regarding (C . E) notation
   */
  private static void scaleCoeff(List<RatTerm> lst, RatNum scalar) {
  	if (scalar.equals(RatNum.ZERO)) {
  		lst.removeAll(lst);
  	} else {
	    for (int i = 0; i < lst.size(); i++) {
	    	if (scalar.equals(RatNum.ZERO)) {
	    		
	    	}
	    	lst.add(i, lst.remove(i).mul(new RatTerm(scalar, 0)));
	    }
  	}
  }

  /**
   * Increments exponents within 'lst' by 'degree' (helper procedure).
   *
   * @param lst The RatTerms whose exponents are to be incremented.
   * @param degree the value by which to increment exponents in lst.
   * @requires lst != null
   * @modifies lst
   * @effects Forall i s.t. 0 <= i < lst.size(), if (C . E) = lst.get(i) then
   *          lst_post.get(i) = (C . E+degree)
   * @see hw4.RatTerm regarding (C . E) notation
   */
  private static void incremExpt(List<RatTerm> lst, int degree) {
  	for (int i = 0; i < lst.size(); i++) {
  		RatTerm curr = lst.remove(i);
  		RatNum coeff = curr.getCoeff();
  		int newExpt = curr.getExpt() + degree;
  		RatTerm update = new RatTerm(coeff, newExpt);
  		lst.add(i, update);
  	}
    // TODO: Fill in this method, then remove the RuntimeException
    //throw new RuntimeException("RatPoly->incremExpt() is not yet implemented");
  }

  /**
   * Helper procedure: Inserts a term into a sorted sequence of terms,
   * preserving the sorted nature of the sequence. If a term with the given
   * degree already exists, adds their coefficients.
   *
   * Definitions: Let a "Sorted List<RatTerm>" be a List<RatTerm> V such
   * that [1] V is sorted in descending exponent order && [2] there are no two
   * RatTerms with the same exponent in V && [3] there is no RatTerm in V with
   * a coefficient equal to zero
   *
   * For a Sorted List<RatTerm> V and integer e, let cofind(V, e) be either
   * the coefficient for a RatTerm rt in V whose exponent is e, or zero if
   * there does not exist any such RatTerm in V. (This is like the coeff
   * function of RatPoly.) We will write sorted(lst) to denote that lst is a
   * Sorted List<RatTerm>, as defined above.
   *
   * @param lst The list into which newTerm should be inserted.
   * @param newTerm The term to be inserted into the list.
   * @requires lst != null && sorted(lst)
   * @modifies lst
   * @effects sorted(lst_post) && (cofind(lst_post,newTerm.getExpt()) =
   *          cofind(lst,newTerm.getExpt()) + newTerm.getCoeff())
   */
  private static void sortedInsert(List<RatTerm> lst, RatTerm newTerm) {
  	if (!newTerm.isZero()) {
	    if (lst.isEmpty()) {
	    	lst.add(newTerm);
	    // !lst.isEmpty()
	    } else if (newTerm.getExpt() > lst.get(0).getExpt()) {
	    	lst.add(0, newTerm);
	    // !lst.isEmpty() && newTerm.getExpt() <= lst.get(0).getExpt()
	    } else {
	    	int i = 0;
	    	while (i < lst.size() && newTerm.getExpt() < lst.get(i).getExpt()) {
	    		i++;
	    	}
	    	// i >= lst.size() xor newTerm.getExpt() >= lst.get(i).getExpt()
	    	if (i == lst.size()) {
	    		lst.add(newTerm);
	    	// i < lst.size() && newTerm.getExpt() >= lst.get(i).getExpt()
	    	} else if (newTerm.getExpt() == lst.get(i).getExpt()) {
	    		// Bug: if the two add up to zero, you don't want to add it to the list
	    		RatTerm sum = lst.remove(i).add(newTerm);
	    		if (!sum.isZero()) {
	    			lst.add(i, sum);
	    		}
	    	// i < lst.size() && newTerm.getExpt() > lst.get(i).getExpt()
	    	} else {
	    		lst.add(i, newTerm);
	    	}
	    }
  	}
  }

  /**
   * Return the additive inverse of this RatPoly.
   *
   * @return a RatPoly equal to "0 - this"; if this.isNaN(), returns some r
   *         such that r.isNaN()
   */
  public RatPoly negate() {
  	if (this.isNaN()) {
  		return NaN;
  	} else {
  		/*
  		RatPoly negated = new RatPoly();
  		for (RatTerm term : this.terms) {
  			negated.terms.add(term.negate());
  		}
  		negated.checkRep();
  		return negated;
  		*/
  		// Using sorted insert
  		RatPoly negated = new RatPoly();
  		for (int i = this.terms.size() - 1; i >= 0; i--) {
  			sortedInsert(negated.terms, this.terms.get(i).negate());
  		}
  		checkRep();
  		return negated;
    }
  }

  /**
   * Addition operation.
   *
   * @param p The other value to be added.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this + p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly add(RatPoly p) {
  	if (this.isNaN() || p.isNaN()) {
  		return NaN; 
  	} else {
  		/*
	  	RatPoly sum = new RatPoly();
	  	for (int i = Math.max(this.degree(), p.degree()); i >= 0; i--) {
	  		RatTerm curr = this.getTerm(i).add(p.getTerm(i));
	  		if (!curr.isZero()) {
	  			sum.terms.add(curr);
	  		}
	  	}
	  	return sum;
	  	*/
  		// using sorted insert
  		RatPoly sum = new RatPoly();
  		for (int i = 0; i <= Math.max(this.degree(), p.degree()); i++) {
  			RatTerm curr = this.getTerm(i).add(p.getTerm(i));
	  		if (!curr.isZero()) {
	  			sortedInsert(sum.terms, curr);
	  		}
  		}
  		checkRep();
  		return sum;
  		// using the example algo
  		/*
  		RatPoly sum = new RatPoly();
  		for (RatTerm thisTerm : this.terms) {
  			sum.terms.add(thisTerm);
  		}
  		for (RatTerm pTerm : p.terms) {
  		for (int i = 0; i < p.terms.size(); i++) {
  			if (!sum.getTerm(p.terms.get(i).getExpt()).equals(RatTerm.ZERO)) {
  				sortedInsert(sum.terms, sum.terms.remove(i).add(p.terms.get(i)))
  			}
  		}
  		*/
  	}
  }

  /**
   * Subtraction operation.
   *
   * @param p The value to be subtracted.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this - p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly sub(RatPoly p) {
    return this.add(p.negate());
  }

  /**
   * Multiplication operation.
   *
   * @param p The other value to be multiplied.
   * @requires p != null
   * @return a RatPoly, r, such that r = "this * p"; if this.isNaN() or
   *         p.isNaN(), returns some r such that r.isNaN()
   */
  public RatPoly mul(RatPoly p) {
    if (this.isNaN() || p.isNaN()) {
    	return NaN;
    } else {
    	/*
    	RatPoly product = new RatPoly();
    	for (RatTerm thisTerm : this.terms) {
    		RatPoly currProduct = new RatPoly();
    		for (RatTerm pTerm : p.terms) {
    			currProduct.terms.add(thisTerm.mul(pTerm));
    		}
    		product = product.add(currProduct);
    	}
    	product.checkRep();
    	return product;
    	*/
    	// using sorted insert
    	RatPoly product = new RatPoly();
    	for (int i = this.terms.size() - 1; i >= 0; i--) {
    		for (int j = p.terms.size() - 1; j >= 0; j--) {
    			sortedInsert(product.terms, this.terms.get(i).mul(p.terms.get(j)));
    		}
    	}
    	checkRep();
    	return product;
    }
  }

  /**
   * Division operation (truncating).
   *
   * @param p The divisor.
   * @requires p != null
   * @return a RatPoly, q, such that q = "this / p"; if p = 0 or this.isNaN()
   *         or p.isNaN(), returns some q such that q.isNaN()
   *         <p>
   *
   * Division of polynomials is defined as follows: Given two polynomials u
   * and v, with v != "0", we can divide u by v to obtain a quotient
   * polynomial q and a remainder polynomial r satisfying the condition u = "q *
   * v + r", where the degree of r is strictly less than the degree of v, the
   * degree of q is no greater than the degree of u, and r and q have no
   * negative exponents.
   * <p>
   *
   * For the purposes of this class, the operation "u / v" returns q as
   * defined above.
   * <p>
   *
   * The following are examples of div's behavior: "x^3-2*x+3" / "3*x^2" =
   * "1/3*x" (with r = "-2*x+3"). "x^2+2*x+15 / 2*x^3" = "0" (with r =
   * "x^2+2*x+15"). "x^3+x-1 / x+1 = x^2-x+2 (with r = "-3").
   * <p>
   *
   * Note that this truncating behavior is similar to the behavior of integer
   * division on computers.
   */
  public RatPoly div(RatPoly p) {
  	if (p.equals(ZERO) || this.isNaN() || p.isNaN()) {
  		return NaN;
  	} else {
  		// Bug: You'll want to work with the existing dividend since the values that don't get operated on
  		//			come down with it.
	  	RatPoly quotient = new RatPoly();
	  	RatPoly dividend = this.copy();
	  	int divisorDegree = p.degree();
	  	
	  	// Bug: when RatPoly is empty, the degree is 0. If divisor is degree 0, you'll get an extra iteration  
	  	while (!dividend.terms.isEmpty() && dividend.degree() >= divisorDegree) {
	  		int dividendDegree = dividend.degree();
	  		// Bug: xFactor should be the division of the coefficients, not the terms
	  		RatNum xFactor = dividend.getTerm(dividendDegree).getCoeff().div(p.getTerm(divisorDegree).getCoeff());
	  		int xFactorDegree = dividendDegree - divisorDegree;
	  		sortedInsert(quotient.terms, new RatTerm(xFactor, xFactorDegree));
	  		
	  		
	  		// Bug: Make sure to copy divisor since you'll be modifying it and don't want to be modifying the original
	  		RatPoly divisorFactor = p.copy();
	  		scaleCoeff(divisorFactor.terms, xFactor);
	  		
	  		for (int i = 0; i <= divisorDegree; i++) {
	  			int dividendTermExpt = dividendDegree - i;
	  			RatTerm dividendTerm = dividend.getTerm(dividendTermExpt);
	  			int index = dividend.terms.indexOf(dividendTerm);
	  	
	  			RatNum dividendCoeff = dividendTerm.getCoeff();
	  			RatNum divisorCoeff = divisorFactor.getTerm(divisorDegree - i).getCoeff();
	  			RatNum coeffDifference = dividendCoeff.sub(divisorCoeff);
	  			
	  			// Bug: Trying to assign the expt for difference using existing term may not work if exiting term didn't
	  			// 			exist in the first place. 
	  			RatTerm differenceTerm = new RatTerm(coeffDifference, dividendTermExpt);
	  			
	  			// Bug: Possible that dividendTerm was 0 (didn't exist). Make sure to check for that
	  			index = dividend.terms.indexOf(dividendTerm);
	  			// Bug: When dividendTerm does not exist, you want to go down the first branch. Anding the nested if
	  			//			is not hte same thing as having it nested.
	  			if (index == -1) {
	  				if (!differenceTerm.isZero()) {
	  					sortedInsert(dividend.terms, differenceTerm);	
	  				}
	  			} else {
	  				dividend.terms.remove(index);
	  				if (!differenceTerm.isZero()) {
	  					dividend.terms.add(index, differenceTerm);
	  				}
	  			}
	  		}
	  	}
	  	checkRep();
	  	return quotient;
  	}
  }
  
  /**
   * Returns copy of this RatPoly.
   *
   * @return a copy of this RatPoly
   *
   * <p>
   * An exact copy of this RatPoly;
   */
  private RatPoly copy() {
  	RatPoly thisCopy = new RatPoly();
  	for (RatTerm thisTerm : this.terms) {
  		sortedInsert(thisCopy.terms, thisTerm);
  	}
  	return thisCopy;
  }

  /**
   * Return the derivative of this RatPoly.
   *
   * @return a RatPoly, q, such that q = dy/dx, where this == y. In other
   *         words, q is the derivative of this. If this.isNaN(), then return
   *         some q such that q.isNaN()
   *
   * <p>
   * The derivative of a polynomial is the sum of the derivative of each term.
   */
  public RatPoly differentiate() {
    RatPoly y = copy();
    for (int i = y.terms.size() - 1; i >= 0; i--) {
    	RatTerm currY = y.terms.remove(i);
    	RatTerm currDyDx = currY.differentiate();
    	sortedInsert(y.terms, currDyDx);
    }
    checkRep();
    return y;
  }

  /**
   * Returns the antiderivative of this RatPoly.
   *
   * @param integrationConstant The constant of integration to use when
   *  computating the antiderivative.
   * @requires integrationConstant != null
   * @return a RatPoly, q, such that dq/dx = this and the constant of
   *         integration is "integrationConstant" In other words, q is the
   *         antiderivative of this. If this.isNaN() or
   *         integrationConstant.isNaN(), then return some q such that
   *         q.isNaN()
   *
   * <p>
   * The antiderivative of a polynomial is the sum of the antiderivative of
   * each term plus some constant.
   */
  public RatPoly antiDifferentiate(RatNum integrationConstant) {
    RatPoly dQdX = copy();
    // Bug: Order matters. If you start with lowest degree, if you integrate it and it has the same 
    //			degree as something that hasn't been integrated, they'll be added together.
    //			Start from the term of highest degree.
    for (int i = 0; i < dQdX.terms.size(); i++) {
    	RatTerm currdQdX = dQdX.terms.remove(i);
    	RatTerm currQ = currdQdX.antiDifferentiate();
    	sortedInsert(dQdX.terms, currQ);
    }
    sortedInsert(dQdX.terms, new RatTerm(integrationConstant, 0));
    checkRep();
    return dQdX;
  }

  /**
   * Returns the integral of this RatPoly, integrated from lowerBound to
   * upperBound.
   *
   * <p>
   * The Fundamental Theorem of Calculus states that the definite integral of
   * f(x) with bounds a to b is F(b) - F(a) where dF/dx = f(x) NOTE: Remember
   * that the lowerBound can be higher than the upperBound.
   *
   * @param lowerBound The lower bound of integration.
   * @param upperBound The upper bound of integration.
   * @return a double that is the definite integral of this with bounds of
   *         integration between lowerBound and upperBound. If this.isNaN(),
   *         or either lowerBound or upperBound is Double.NaN, return
   *         Double.NaN.
   */
  public double integrate(double lowerBound, double upperBound) {
  	if (this.isNaN() || lowerBound == Double.NaN || upperBound == Double.NaN) {
  		return Double.NaN;
  	} else {
  		RatPoly integral = this.antiDifferentiate(RatNum.ZERO);
  		double evalUpperBound = integral.eval(upperBound);
  		double evalLowerBound = integral.eval(lowerBound);
  		return evalUpperBound - evalLowerBound;
  	}
    
  }

  /**
   * Returns the value of this RatPoly, evaluated at d.
   *
   * @param d The value at which to evaluate this polynomial.
   * @return the value of this polynomial when evaluated at 'd'. For example,
   *         "x+2" evaluated at 3 is 5, and "x^2-x" evaluated at 3 is 6. if
   *         (this.isNaN() == true), return Double.NaN
   */
  public double eval(double d) {
  	if (this.isNaN()) {
  		return Double.NaN;
  	} else {
	    double value = 0;
	    for (RatTerm term : this.terms) {
	    	value = value + term.eval(d);
	    }
	    return value;
  	}
  }

  /**
   * Returns a string representation of this RatPoly.
   *
   * @return A String representation of the expression represented by this,
   *         with the terms sorted in order of degree from highest to lowest.
   *         <p>
   *         There is no whitespace in the returned string.
   *         <p>
   *         If the polynomial is itself zero, the returned string will just
   *         be "0".
   *         <p>
   *         If this.isNaN(), then the returned string will be just "NaN"
   *         <p>
   *         The string for a non-zero, non-NaN poly is in the form
   *         "(-)T(+|-)T(+|-)...", where "(-)" refers to a possible minus
   *         sign, if needed, and "(+|-)" refer to either a plus or minus
   *         sign, as needed. For each term, T takes the form "C*x^E" or "C*x"
   *         where C > 0, UNLESS: (1) the exponent E is zero, in which case T
   *         takes the form "C", or (2) the coefficient C is one, in which
   *         case T takes the form "x^E" or "x". In cases were both (1) and
   *         (2) apply, (1) is used.
   *         <p>
   *         Valid example outputs include "x^17-3/2*x^2+1", "-x+1", "-1/2",
   *         and "0".
   *         <p>
   */
  @Override
  public String toString() {
    if (terms.size() == 0) {
      return "0";
    }
    if (isNaN()) {
      return "NaN";
    }
    StringBuilder output = new StringBuilder();
    boolean isFirst = true;
    for (RatTerm rt : terms) {
      if (isFirst) {
        isFirst = false;
        output.append(rt.toString());
      } else {
        if (rt.getCoeff().isNegative()) {
          output.append(rt.toString());
        } else {
          output.append("+" + rt.toString());
        }
      }
    }
    return output.toString();
  }

  /**
   * Builds a new RatPoly, given a descriptive String.
   *
   * @param ratStr A string of the format described in the @requires clause.
   * @requires 'polyStr' is an instance of a string with no spaces that
   *           expresses a poly in the form defined in the toString() method, except that
   *           the ordering of the terms by the degrees is not necessary.
   *           <p>
   *
   * Valid inputs include "0", "x-10", and "x^3-2*x^2+5/3*x+3", and "NaN".
   *
   * @return a RatPoly p such that p.toString() = polyStr
   */
  public static RatPoly valueOf(String polyStr) {

    List<RatTerm> parsedTerms = new ArrayList<RatTerm>();

    // First we decompose the polyStr into its component terms;
    // third arg orders "+" and "-" to be returned as tokens.
    StringTokenizer termStrings = new StringTokenizer(polyStr, "+-", true);

    boolean nextTermIsNegative = false;
    while (termStrings.hasMoreTokens()) {
      String termToken = termStrings.nextToken();

      if (termToken.equals("-")) {
        nextTermIsNegative = true;
      } else if (termToken.equals("+")) {
        nextTermIsNegative = false;
      } else {
        // Not "+" or "-"; must be a term
        RatTerm term = RatTerm.valueOf(termToken);

        // at this point, coeff and expt are initialized.
        // Need to fix coeff if it was preceeded by a '-'
        if (nextTermIsNegative) {
          term = term.negate();
        }

        // accumulate terms of polynomial in 'parsedTerms'
        sortedInsert(parsedTerms, term);
      }
    }
    return new RatPoly(parsedTerms);
  }

  /**
   * Standard hashCode function.
   *
   * @return an int that all objects equal to this will also return.
   */
  @Override
  public int hashCode() {
    // all instances that are NaN must return the same hashcode;
    if (this.isNaN()) {
      return 0;
    }
    return terms.hashCode();
  }

  /**
   * Standard equality operation.
   *
   * @param obj The object to be compared for equality.
   * @return true if and only if 'obj' is an instance of a RatPoly and 'this'
   *         and 'obj' represent the same rational polynomial. Note that all
   *         NaN RatPolys are equal.
   */
  @Override
  public boolean equals(/*@Nullable*/ Object obj) {
    if (obj instanceof RatPoly) {
      RatPoly rp = (RatPoly) obj;

      // special case: check if both are NaN
      if (this.isNaN() && rp.isNaN()) {
        return true;
      } else {
        return terms.equals(rp.terms);
      }
    } else {
      return false;
    }
  }

  /**
   * Checks that the representation invariant holds (if any).
   */
  private void checkRep() {
    assert (terms != null);
    
    for (int i = 0; i < terms.size(); i++) {
        assert (!terms.get(i).getCoeff().equals(new RatNum(0))) : "zero coefficient";
        assert (terms.get(i).getExpt() >= 0) : "negative exponent";
        
        if (i < terms.size() - 1)
            assert (terms.get(i + 1).getExpt() < terms.get(i).getExpt()) : "terms out of order";
    }
  }
}
