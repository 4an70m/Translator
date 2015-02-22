package analyzer.util.handler;

public class ErrorHandler {

	public static void error(int errorCode, int line) {
		// Todo: implement error handler

		switch (errorCode) {
		case 1: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Unknown symbol.");
			break;
		}
		case 2: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Not declared identificator.");
			break;
		}
		case 3: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Not declared label.");
			break;
		}
		case 99: {
			System.out.println("Code was not translated.");
			break;
		}
		case 100: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". 'Program' not found");
			break;
		}
		case 101: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Identifier list not found.");
			break;
		}
		case 102: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Identifier not found.");
			break;
		}
		case 103: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Type not found.");
			break;
		}
		case 104: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Operation list not found.");
			break;
		}
		case 105: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Operation not found.");
			break;
		}
		case 106: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Logical expression not found.");
			break;
		}
		case 107: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Logical terminal not found.");
			break;
		}
		case 108: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Logical multiplier not found.");
			break;
		}
		case 109: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Logical ratio not found.");
			break;
		}
		case 110: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Math expression not found.");
			break;
		}
		case 111: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Math terminal not found.");
			break;
		}
		case 112: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Math multiplier not found.");
			break;
		}
		case 113: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". Program name not found.");
			break;
		}
		case 114: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". 'var' not found.");
			break;
		}
		case 115: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". 'begin' not found");
			break;
		}
		case 116: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". 'end.' not found.");
			break;
		}
		
		case 118: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". 'do' not found.");
			break;
		}
		case 119: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". ':' not found.");
			break;
		}
		case 120: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". '(' not found.");
			break;
		}
		case 121: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". ')' not found.");
			break;
		}
		case 122: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". 'then' not found.");
			break;
		}
		case 123: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". 'else' not found.");
			break;
		}
		case 124: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". '=' not found.");
			break;
		}
		case 125: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". ']' not found.");
			break;
		}
		case 126: {
			System.out.println("Line: " + line + ". Error " + errorCode
					+ ". ',' not found.");
			break;
		}
		case -1: {
			System.out.println("Line: " + (line -1) + " has an error.");
			break;
		}
		default: {
			System.out.println("Unknown condition");
		}
		}
	}

}
