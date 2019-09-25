// Evaluator for Lego formulas
package lego;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import lego.parser.*;
// data structure to store values of free variables


class Env {
	    //hash map
		// storing in form of <var, value>
		Map<String,Integer> vars = new HashMap<String, Integer>();
		
	
}

public class Eval {
	public static int BinExp(Exp e) throws DivisionByZeroException, ModuloByZeroException{
		//solve mathematics return integer
		// e.g. (4+2)*3
		BinExp BinExp = (BinExp)e;
		String bin_op = BinExp.bin_op;
		Exp e1 = BinExp.e1;
		Exp e2 = BinExp.e2;
		int a,b;
		// check to make sure that a and b are integer
		if (e1 instanceof BinExp) {
			 a = BinExp(e1);
		}
		else {
			 a = Integer.parseInt(e1.toString());
		}
		if (e2 instanceof BinExp) {
			 b = BinExp(e2);
		}
		else {
			 b =  Integer.parseInt(e2.toString());
		}
		if (bin_op.equals("+")) {
            return a + b;
        } else if (bin_op.equals("-")) {
            return a - b;
        } else if (bin_op.equals("*")) {
            return a * b;
        } else if (bin_op.equals("/")) {
            if (b == 0) { //divided by zero
                System.err.println("Cannot divide " + e1 + " by " + e2);
                throw new DivisionByZeroException();
            }
            return a / b;
        } else if (bin_op.equalsIgnoreCase("mod")) {
            if (b == 0) { //divided by zero
                System.err.println("Cannot mod " + e1 + " by " + e2);
                throw new ModuloByZeroException();
            }
            return a % b;
        }
        
 
		return 0;		
	}
	
	
    public static boolean eval(Formula f) throws FreeVariableException, DivisionByZeroException, ModuloByZeroException {
    	return eval(f, new Env());
    }
    
    public static boolean eval(Formula f, Env e) throws FreeVariableException, DivisionByZeroException, ModuloByZeroException {
    	if(f instanceof Atomic) { 
    		// e.g. (4+3)>=y
			Atomic atomic= (Atomic)f;	
			String op = atomic.rel_op;
			Exp e1 = atomic.e1;
			Exp e2 = atomic.e2;
			int a,b;
			if (e1 instanceof BinExp) {
				 a = BinExp(e1);
			}
			else {
				 a = Integer.parseInt(e1.toString());
			}
			if (e2 instanceof BinExp) {
				 b = BinExp(e2);
			}
			else {
				 b =  Integer.parseInt(e2.toString());
			}
			switch (op) {
				case ">":
					return a > b;
				case ">=":
					return a >= b;
				case "=":
					return a == b;
			}
					
		}
				
		
		else if(f instanceof Unary) {
			// e.g. !(y>3)
			Unary unary = (Unary)f;
			String unary_conn = unary.unary_conn;
			Formula fm = unary.f;
			return !eval(fm,e);	
			
		}
		else if(f instanceof Binary) {
			//e.g.(x+3)>y || x>=5
			Binary binary= (Binary)f;
			String bin_conn = binary.bin_conn;
		    Formula f1 = binary.f1;
		    Formula f2 = binary.f2;
			switch (bin_conn) {
				case "&&":
					return eval(f1,e) && eval(f2,e);
				case "||":
					return eval(f1,e) || eval(f2,e);
				case "->":
					return !(eval(f1,e)) || eval(f2,e);
				case "<->":
					return (!(eval(f1,e)) || eval(f2,e)) && (eval(f1,e) || !(eval(f2,e)));
			}
		}
		else if(f instanceof Quantified) {
			//e.g. forall x :[1..10] x>=3+2
			Quantified quantified = (Quantified)f;
			String quant = quantified.quant;
			String var = quantified.var.toString();
			Domain dom = quantified.dom;
			Formula f1 = quantified.f;
			int from = Integer.parseInt(dom.from.toString());
			int to = Integer.parseInt(dom.to.toString());
			if (quant.equals("forall")) {
                // forall 
                for (; from <= to; from++) {
                    e.vars.put(var, from);

                    if (!eval(f1, e)) {
                        return false;
                    }
                }
            } else if (quant.equals("exists")) {
                // exists
                for (; from <= to; from++) {
                    e.vars.put(var, from);

                    if (eval(f1, e)) {
                        return true;
                    }
                }
                return false;
            }		
		}
					        
		
    	return true;
    }

}


