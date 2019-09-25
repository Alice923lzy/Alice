# CISC-422-835
Your task is to implement the evaluation function for Lego formulas. To this end, complete the implementation of the method eval in file Eval.java in directory d/lego.
Evaluation of function and predicate symbols: You may assume that all function and predicate symbols have their standard meaning. For instance, the expressions 4+3, 7 mod 2, and 3 > 2 should evaluate to 7, 1, and true respectively. Moreover, the connectives !, &&, ||, ->, and <-> stand for negation, conjunction, disjunction, implication and equivalence respectively.
Evaluation of identifiers: The occurrence of an identifier x in some formula evaluates to a value in the domain of the most recent quantification of x that contains that occurrence of x in its scope. That quantification also determines whether x is universally or existentially quantified. For instance, identifier x at the underlined occurrence in the formula
forall x : [1..4] . exists y : [1..2]. exists x : [8..9] . x = y

is existentially quantified and may evaluate to 8 or 9, but never to a value between 1 and 4. This scoping rule has the following consequence:

If the scope of a quantification of x over a non-empty domain does not contain any free occurrences of x, that quantification is redundant. For instance , since the scope of the outermost quantification in the above formula does not contain a free occurrence of x, that quantification can be omitted, that is, the above formula is logically equivalent to
exists y : [1..2]. exists x : [8..9] . x = y

Thus, the formula

(forall x : [1..4] . exists y : [1..2]. exists x : [8..9] . x = y) <-> (exists y : [1..2]. exists x : [8..9] . x = y)

for instance, should evaluate to true.

Error conditions: Not every syntactically correct Lego formula can be evaluated. I.e., there are syntactically correct formulas that do not have a value. There are 3 cases in which this can occur:
Division by 0: As, for instance, in 1 = 3/(1-1).
Modulo by 0: As, for instance, in 2 mod 0 = 0.
Free variable: If the formula contains an occurrence of a variable x that is not bound by any quantification, that is, it is a free occurrence of x, then that occurrence of x cannot be evaluated. Consider, for instance,
10 = x
forall x : [0..9] . y + 11 > x
(exists x : [1..9] . 11 > x) && x > 0
The provided code defines three exceptions (DivisonByZeroException, ModuloByZeroException, and FreeVariableException) for each of these cases. When your implementation of eval detects that the input formula cannot be evaluated, it should raise the corresponding exception (which will then be caught in Main.java and cause an appropriate error message to be printed).
We suggest that you break down the implementation of the evaluation function into two stages:
Stage 1: First, assume that the formula to be evaluated does not contain any identifiers or quantifiers, as in, for instance, ((2*3)-4 > 10) || (2 = 1+1). In this case, your implementation of the evaluation function should be straight-forward and be based on a traversal of the AST returned by the parser.
Stage 2: Next, augment your implementation to account over variables and quantifications. You will need a so-called environment, that is, an additional data structure that stores the current values of the bound variables. Skeleton code for the environment is provided in class Env (inside of Eval.java). We suggest you use a stack to implement the environment. To evaluate the occurrence of an identifier x, the value of x is looked up in the environment. If the occurrence of x is free, then the attempt to look up the value of x in the environment should fail and prompt your program to output an appropriate error message instead of a truth value.
To execute and recompile the code:
from the command line: From directory d, invoke the formula parsing and (at the moment incomplete) evaluation with, for instance,
java lego.Main d/lego/formulas/f1.txt
Recompile the system with
javac lego/*.java lego/parser/*.java
from directory d. Note that on Windows, the forward slashes ('/') need to be replaced by backward slashes ('\').
using Eclipse: Look here for Eclipse instructions.
Hints:
Your implementation of the evaluation routine should be relatively short (the shortest implementation of the class Eval (including comments, but without class Env) that we've seen, fit onto less than 2 pages).
To determine whether a formula is an atomic, unary, binary, or quantified, you may find the instanceof operator useful.
The parser doesn't handle operator precedences right, that is, 3 + 4 * 6 gets parsed into (3 + 4) * 6, not 3 + (4 * 6). Always use parenthesis to enforce the intented order of evaluation.
The code for the lexical analysis was automatically generated using the JLex lexical analyzer generator on the definition file d/lego/parser/lego.lex. The code for the parser was automatically generated using the CUP parser generator on the definition file d/lego/parser/lego.cup. The use of lexer and parser generators is very common in industry. Generators for C under Unix include lex and yacc.
Important notes:
Only change the contents of Eval.java and leave all other files unchanged.
Also, Eval.java should not print anything to the console. Make sure you comment out any print statements prior to submission.
Document the code you write appropriately.
Test your implementation on the formulas f1.txt through f15.txt provided in the directory d/lego/formulas, but note that your code may be tested on other formulas as well.
Express the following statements in Lego and evaluate them using your implementation:
"For every number between 0 and 10, there exists a number between 0 and 20 such that they add up to 20."
"For all a between 0 and 50 and for all b between 1 and 50, a is equal to the sum of two numbers where the first is the result of multiplying b and the result of deviding a by b and the second the remainder of dividing a by b."
"There exist three numbers x, y, and z between 1 and 10 such that the sum of the square of x and the square of y is equal to the square of z."
"Every number between 3 and 10 is the sum of two prime numbers".
"For all x and y, if x evenly divides y, then x also evenly divides any multiple of y".
"For all p, if p is prime and p evenly divides the product of two numbers a and b, then p also evenly divides either a or b".
"There are perfect numbers between 1 and 10" (A perfect number is defined as a positive integer which is the sum of its proper positive divisors, that is, the sum of the positive divisors excluding the number itself.)
"All prime factors of 175 are greater than 4 and less than 11" where x is called a prime factor of y if x is prime and x evenly devides y.
Some of the statements do not specify the domain that some of the variables range over. Choose the domain [1..100] for these variables.
