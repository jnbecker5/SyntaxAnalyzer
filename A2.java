/**
 * CSC 345-01 Assignment #2
 * 
 * On my honor, Josiah Nathaniel Becker, this assignment is my own work.  
 * I, Josiah Nathaniel Becker, will follow the instructor's rules and processes 
 * related to academic integrity as directed in the course syllabus.
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class A2 { 
	
	//constants declarations
	public static String[] lexemes = new String[100];
	public static String strClass;
	public static int nextToken;
	public static int index = 0;
	public static final int DONE = 99;
	public static final int FLOATDCL = 0;
	public static final int INTDCL = 1;
	public static final int PRINT = 2;
	public static final int ID = 3;
	public static final int ASSIGN = 4;
	public static final int PLUS = 5;
	public static final int MINUS = 6;
	public static final int INUM = 7;
	public static final int FNUM = 8;
	
	public static void main(String[] args) {
		try {
			// Do NOT make any changes to the following TWO lines
			File file = new File(args[0]);		
			Scanner sc = new Scanner(file);		//*** Do not make any other Scanners ***//

			String testString = sc.nextLine();
			while (sc.hasNextLine()) { 

				testString = testString + sc.nextLine();
			}
			String [] lexemes1 = testString.split(" ");
			for(int i = 0; i < lexemes1.length; i++){

				lexemes[i] = lexemes1[i];

			}
			try{

				getStr();
				lex();
				Prog();

			}catch(Exception e){

				System.out.println();

			}		
			sc.close();
		} catch (FileNotFoundException e) {

			System.out.println("ERROR - cannot open front.in \n");

		}
	}

	//Gets the next string in the input
	public static void getStr(){

		if(lexemes[index] != null){

			strClass = lexemes[index];
			index++;

		}
		else{
			nextToken = DONE;
		}

	}

	//Matches each lexeme with the correct token
	public static int lex(){	

		switch(strClass){
			case "f":
				nextToken = FLOATDCL;
				break;
			case "p":
				nextToken = PRINT;
				break;
			case "i": 
				nextToken = INTDCL;
				break;
			case "=":
				nextToken = ASSIGN;
				break;
			case "+":
				nextToken = PLUS;
				break;
			case "-":
				nextToken = MINUS;
				break;
		}

		if(strClass.matches("[a-zA-Z&&[^fpiFPI]]+")){
			nextToken = ID;
		}

		if(strClass.matches("[0-9]*\\.[0-9]+")){
			nextToken = FNUM;
		}

		else if(strClass.matches("[0-9]+")){
			nextToken = INUM;
		}
		
		System.out.println("Next token is: " + nextToken + ", Next lexeme is " + strClass);
		getStr();

		return nextToken;
	}

	//Prog method: <Prog> -> <Dcls><Stmts>
	public static void Prog(){

		System.out.println("Enter <Prog>");

		if(nextToken == FLOATDCL || nextToken == INTDCL){
			Dcls();
		}

		if(nextToken == ID || nextToken == PRINT){
			Stmts();
		}

		System.out.println("Exit <Prog>");
	}

	//Dcls method: <Dcls> -> <Dcl><Dcls> | lambda
	public static void Dcls(){

		System.out.println("Enter <Dcls>");
		Dcl();

		while(nextToken == FLOATDCL || nextToken == INTDCL){
			Dcls();
		}

		System.out.println("Exit <Dcls>");
	}

	//Dcl method: <Dcl> -> FLOATDCL ID | INTDCL ID
	public static void Dcl(){

		System.out.println("Enter <Dcl>");

		if(nextToken == FLOATDCL || nextToken == INTDCL){

			lex();
			if(nextToken == ID){

				lex();
			}
		}

		System.out.println("Exit <Dcl>");
	}

	//Stmts method: <Stmts> -> <Stmt><Stmts> | lambda
	public static void Stmts(){

		System.out.println("Enter <Stmts>");
		Stmt();

		while(nextToken == ID || nextToken == PRINT){
			Stmts();
		}

		System.out.println("Exit <Stmts>");
	}

	//Stmt method: <Stmt> -> ID ASSIGN <Val> <Expr> | PRINT ID
	public static void Stmt(){

		System.out.println("Enter <Stmt>");

		if(nextToken == PRINT){

			lex();
			if(nextToken == ID){

				lex();
			}
		}
		if(nextToken == ID){

			lex();
			if(nextToken == ASSIGN){

				lex();
				Val();
				if(nextToken == PLUS || nextToken == MINUS){

					Expr();
				}
			}	
		}

		System.out.println("Leave <Stmt>");
	}

	//Expr method: <Expr> -> PLUS<Val><Expr> | MINUS<Val><Expr> | lambda
	public static void Expr(){

		System.out.println("Enter <Expr>");

		while(nextToken == PLUS || nextToken == MINUS){
			lex();
			Val();
		}

		System.out.println("Leave <Expr>");
	}

	//Val method: <Val> -> ID | FNUM | INUM
	public static void Val(){

		System.out.println("Enter <Val>");

		if(nextToken == ID || nextToken == FNUM || nextToken == INUM){
			lex();
		} 

		System.out.println("Leave <Val>");
	}
}