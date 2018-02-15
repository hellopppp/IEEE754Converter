/*Assignment 2
Use a programming language of your choice (i.e java) to write an application to convert decimal number to IEEE-754 Single Precision Floating-Point Representation (32-bit) and IEEE-754 Double Precision Floating-Point Representation (64-bit) and convert back to decimal. Use GUI as user interface asking the user for decimal number and the program will output the result.  
Specification
•	The IEEE-754 single precision floating point standard uses an 8-bit exponent (with a bias of 127) and a 23-bit significand.
•	The IEEE-754 double precision standard uses an 11-bit exponent (with a bias of 1023) and a 52-bit significand.
Requirements
•	Make your application runnable.
•	Demonstrate your application.
•	Submit your source code on gitub.com and copy and paste the link under assignment2 dropbox.
Extra credit
•	Android app or ios app  20%
•	Web application 10%
•	Convert to Hexadecimal and Hexadecimal to Binary 5%
Grading Criteria
Convert decimal number to IEEE-754 Single Precision Floating-Point Representation	20
Convert decimal number to IEEE-754 Double Precision Floating-Point Representation	20
Convert IEEE-754 Single Precision Floating-Point Representation to decimal number	20
Convert IEEE-754 Double Precision Floating-Point Representation to decimal number	20
Develop IEEE-754 application	20
Correct Results check with http://www.binaryconvert.com/result_float.html	

Resources
http://www.binaryconvert.com/result_float.html
*/

import java.util.*;

public class Converter
{
	public static int power;
	
	public static int excess = power + 127;
	
	public double inputDouble;
	
	public static void main(String[]args)
	{
		Scanner input = new Scanner(System.in);//input
		
		//System.out.println(DectoIEEE_SP(555));
		//System.out.println(DectoIEEE_DP(0));
		//System.out.println(SPtoDecimalSignificance("00010101100000000000000"));
		//System.out.println(IEEESingletoDec("01000000010110011001100110011010"));
		//System.out.println(IEEEDoubletoDec("0100000000110111000000000000000000000000000000000000000000000000"));
		System.out.println("This is an IEEE754 converter, please select an option:");
		System.out.println("1. Decimal to Single Precision IEEE754");
		System.out.println("2. Decimal to Double Precision IEEE754");
		System.out.println("3. Single Precision IEEE754 to Decimal");
		System.out.println("4. Double Precision IEEE754 to Decimal");
		Scanner userInput = new Scanner(System.in);
		String userSelection = userInput.next();
		switch (userSelection) {
		case "1": //Decimal To SPIEEE
			System.out.println("Enter the Decimal number you wish to convert.");
			userSelection = userInput.next();
			if(CheckInput(userSelection, 1))
			{
				double converterInput = Double.parseDouble(userSelection);
				System.out.println("IEEE754:\n" + (DectoIEEE_SP(converterInput)));
				System.out.println("Enter any key to exit.");
				userSelection = userInput.next();
			}
			else
			{
				System.out.println("Invalid Input!");
			}
			break;
		case "2": //Decimal to DPIEEE
			System.out.println("Enter the Decimal number you wish to convert.");
			userSelection = userInput.next();
			if(CheckInput(userSelection, 2))
			{
				double converterInput = Double.parseDouble(userSelection);
				System.out.println("IEEE754:\n" + (DectoIEEE_DP(converterInput)));
				System.out.println("Enter any key to exit.");
				userSelection = userInput.next();
			}
			else
			{
				System.out.println("Invalid Input!");
			}
			break;
		case "3": //SPIEEE to Decimal
			System.out.println("Enter the IEEE representation you wish to convert.");
			userSelection = userInput.next();
			if(CheckInput(userSelection, 3))
			{
				System.out.println("Decimal:\n" + (IEEESingletoDec(userSelection)));
				System.out.println("Enter any key to exit.");
				userSelection = userInput.next();
			}
			else
			{
				System.out.println("Invalid Input!");
			}
			break;
		case "4": //DPIEEE to Decimal
			System.out.println("Enter the IEEE representation you wish to convert.");
			userSelection = userInput.next();
			if(CheckInput(userSelection, 4))
			{
				System.out.println("Decimal:\n" + (IEEEDoubletoDec(userSelection)));
				System.out.println("Enter any key to exit.");
				userSelection = userInput.next();
			}
			else
			{
				System.out.println("Invalid Input!");
			}
			break;
		default:
			break;
		}
		
	}
	
	public static boolean CheckInput(String input, int mode)
	{
		if(mode == 1 || mode == 2) {
			if(input.matches("^[-+]?\\d+(\\.\\d+)?$"))
			{
				return true;
			}
			
		}
		else if(mode == 3)
		{
			if(input.length()!=32)
			{
				return false;
			}
			else if(input.matches("[01]+"))
			{
				return true;
			}
		}
		else if(mode == 4)
		{
			if(input.length()!=64)
			{
				return false;
			}
			else if(input.matches("[01]+"))
			{
				return true;
			}
		}
		else
		{
			return false;
		}
		return false;
	}
	
	public static String findBinaryAfterRadix(double input, int mantissaBits)
	{
		String inputString = Double.toString(input);
		String toConvert = "0." + inputString.substring(inputString.indexOf('.')+1, inputString.length());
		//System.out.println(toConvert);
		double doubleToConvert = Double.parseDouble(toConvert);
		String returnString = "";
		double workingDouble = doubleToConvert;
		for(int i = 0; i<mantissaBits; i++)
		{
			//System.out.println(workingDouble);
			if(workingDouble>1)
			{
				workingDouble = workingDouble-1;
			}
			workingDouble = workingDouble*2;
			if(workingDouble==1)
			{
				returnString = returnString + "1";
				i =99;
			}
			else if(workingDouble>1)
			{
				returnString = returnString + "1";
			}
			else
			{
				returnString = returnString + "0";
			}
		}
		//System.out.println(returnString);
		return returnString;
	}
	
	public static String findBinaryBeforeRadix(double input)
	{
		int inputInt = Math.abs((int)input);
		//System.out.println(inputInt);
		String inputString = Double.toString(input);
		String binary = "";
		binary = Integer.toBinaryString(inputInt);
		//System.out.println(binary);
		return binary;
	}
	
	
	public static String DectoIEEE_SP(double input) //Single Precision
	{
		if(input == 0)
			return "00000000000000000000000000000000";
		int afterRadixLength = findBinaryBeforeRadix(input).length();
		String toNormalize = "" + findBinaryBeforeRadix(input) + "." + findBinaryAfterRadix(input,24-afterRadixLength);
		//System.out.println(toNormalize);
		
		//Normalize
		int radixIndex = toNormalize.indexOf('.');
		//System.out.println(radixIndex);
		power =  radixIndex -1;
		if (toNormalize.contains("0."))
		{
			//System.out.println("test");
			power = -(toNormalize.indexOf("1")-1);
		}
		//System.out.println(power);
		excess = power + 127;
		//System.out.println(excess);
		
		//System.out.println(toNormalize);
		
		toNormalize = toNormalize.replace(".", "");
		//String normalized = toNormalize.substring(0,1) + "." + toNormalize.substring(1,toNormalize.length());
		String normalized = "1" + "." + toNormalize.substring(toNormalize.indexOf("1")+1, toNormalize.length());
		if(normalized.contains("0."))
		{
			//System.out.println(normalized);
			normalized = normalized.substring(normalized.indexOf("1"),normalized.length());
		}
		//System.out.println(excess);
		
		//System.out.println(normalized);
		
		String returnString = "";
		
		if(input>0)								//Sign
		{
			returnString = returnString + "0";
		}
		else
		{
			returnString = returnString + "1";
		}
		
		String expString = Integer.toBinaryString(excess);

		
		if(expString.length()<8)
		{
			for(int i = expString.length(); i<8; i++)
			{
				expString = "0"+ expString;
			}
		}
		//System.out.println(expString);
		
		returnString = returnString + expString; //Add Exponent
		if(normalized.length()==1)
		{
			returnString = returnString + "1";
		}
		else
		{
			returnString = returnString + normalized.substring(2,normalized.length()); //Add Mantissa
		}		
		if(returnString.length()<32)
		{
			for(int i=returnString.length();i<=32;i++)
			{
				returnString = returnString + "0";
			}
		}
		if(returnString.length()>32)
		{
			returnString = returnString.substring(0,32);
		}
		return returnString;
		
	}
	public static String DectoIEEE_DP(double input) //Double
	{
		if(input ==0)
		{
			return "0000000000000000000000000000000000000000000000000000000000000000";
		}
		int afterRadixLength = findBinaryBeforeRadix(input).length();
		String toNormalize = "" + findBinaryBeforeRadix(input) + "." + findBinaryAfterRadix(input,53-afterRadixLength);
		//System.out.println(toNormalize);
		
		//Normalize
		int radixIndex = toNormalize.indexOf('.');
		//System.out.println(radixIndex);
		power =  radixIndex -1;
		if (toNormalize.contains("0."))
		{
			//System.out.println("test");
			power = -(toNormalize.indexOf("1")-1);
		}
		//System.out.println(power);
		excess = power + 1023;
		//System.out.println(excess);
		
		//System.out.println(toNormalize);
		
		toNormalize = toNormalize.replace(".", "");
		//String normalized = toNormalize.substring(0,1) + "." + toNormalize.substring(1,toNormalize.length());
		String normalized = "1" + "." + toNormalize.substring(toNormalize.indexOf("1")+1, toNormalize.length());
		if(normalized.contains("0."))
		{
			//System.out.println(normalized);
			normalized = normalized.substring(normalized.indexOf("1"),normalized.length());
		}
		//System.out.println(excess);
		
		//System.out.println(normalized);
		
		String returnString = "";
		
		if(input>0)								//Sign
		{
			returnString = returnString + "0";
		}
		else
		{
			returnString = returnString + "1";
		}
		
		String expString = Integer.toBinaryString(excess);

		
		if(expString.length()<11)
		{
			for(int i = expString.length(); i<11; i++)
			{
				expString = "0"+ expString;
			}
		}
		//System.out.println(expString);
		
		returnString = returnString + expString; //Add Exponent
		if(normalized.length()==1)
		{
			returnString = returnString + "1";
		}
		else
		{
			returnString = returnString + normalized.substring(2,normalized.length()); //Add Mantissa
		}		
		if(returnString.length()<64)
		{
			for(int i=returnString.length();i<=64;i++)
			{
				returnString = returnString + "0";
			}
		}
		if(returnString.length()>64)
		{
			returnString = returnString.substring(0,64);
		}
		return returnString;
	}
	public static double IEEESingletoDec(String IEEE) //Single
	{
		int negativeFactor = 1;
		if (IEEE.charAt(0)=='1')
		{
			negativeFactor = -1;
		}
		String binaryExponent = IEEE.substring(1,9);
		int decimalExponent = Integer.parseInt(binaryExponent, 2)-127;
		//System.out.println(decimalExponent);
		String binarySig = IEEE.substring(9,IEEE.length());
		double sig = SPtoDecimalSignificance(binarySig);
		double returnDouble = Math.pow(2, decimalExponent) * negativeFactor * sig;
		return returnDouble;
	}
	
	public static double SPtoDecimalSignificance(String IEEESig)
	{
		//System.out.println(IEEESig);
		double returnDouble=1;
		
		for (int i=0; i<23; i++)
		{
			if (IEEESig.charAt(i)=='1')
			{
				returnDouble = returnDouble + 1*Math.pow(2, -(i+1));
				//System.out.println(returnDouble);
			}

		}

		//System.out.println(returnDouble);
		return returnDouble;
	}
	
	public static double DPtoDecimalSignificance(String IEEESig)
	{
		//System.out.println(IEEESig);
		double returnDouble=1;
		
		for (int i=0; i<52; i++)
		{
			if (IEEESig.charAt(i)=='1')
			{
				returnDouble = returnDouble + 1*Math.pow(2, -(i+1));
				//System.out.println(returnDouble);
			}

		}

		//System.out.println(returnDouble);
		return returnDouble;
	}
	
	public static double IEEEDoubletoDec(String IEEE) //Double
	{
		int negativeFactor = 1;
		if (IEEE.charAt(0)=='1')
		{
			negativeFactor = -1;
		}
		String binaryExponent = IEEE.substring(1,12);
		int decimalExponent = Integer.parseInt(binaryExponent, 2)-1023;
		//System.out.println(decimalExponent);
		String binarySig = IEEE.substring(12,IEEE.length());
		double sig = SPtoDecimalSignificance(binarySig);
		double returnDouble = Math.pow(2, decimalExponent) * negativeFactor * sig;
		return returnDouble;
	}

	
	
	
	
}
