// CISC/CMPE 422, CISC 835; Fall 2019; Assignment 1
// Top-level program for parsing and evaluation of Lego formulas.
// From command line, invoke with e.g., "java lego.Main lego/formulas/f1.txt"
// IMPORTANT: do not change this file! Marks may get deducted if you do!

package lego;



import lego.parser.*;

public class Main {

	public static void main(String argv[]) throws java.lang.Exception {
	
    	if (java.lang.reflect.Array.getLength(argv) == 0) {
    		System.out.println("Error: No file name provided. Terminating\n" );
    		System.exit(0);
    	}
    	else {
    		String fn = argv[0];
    		Formula f = null;
    		try {
    			// parse formula
    			f = Parse.parse(fn);
    		}
    		catch (java.lang.Exception e) {
				System.out.println("Error: Content of file "+fn+" syntactically incorrect. Terminating\n");
				System.exit(0);
    		}  		
			// print formula
			System.out.println("Here's the formula:");
			System.out.println(f.toString());
			try {  // evaluate formula
				System.out.println("Here's its value:");
				System.out.println(Eval.eval(f));
			}
			catch (FreeVariableException e) {
			    System.out.println("Error: Formula cannot be evaluated, because it contains free variable(s). Terminating\n");
				System.exit(0);
			}
			catch (DivisionByZeroException e) {
				System.out.println("Error: Formula cannot be evaluated, because it involves a division by 0. Terminating\n");
				System.exit(0);		    
			} 
			catch (ModuloByZeroException e) {
				System.out.println("Error: Formula cannot be evaluated, because it involves a modulo by 0. Terminating\n");
				System.exit(0);		    
			} 
    	}
    }
} // class Main

