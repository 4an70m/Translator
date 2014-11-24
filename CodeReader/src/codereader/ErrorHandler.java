package codereader;

public class ErrorHandler {

	public static void error(int errorCode) {
		// Todo: implement error handler

		switch (errorCode) {
		case 1: {
			System.out.println("Error " + errorCode + ". Unknown symbol.");
			break;
		}
		case 2: {
			System.out.println("Error " + errorCode
					+ ". Not declared identificator.");
			break;
		}
		case 3: {
			System.out.println("Error " + errorCode + ". Not declared label.");
			break;
		}
		case 100: {
			System.out.println("Error " + errorCode + ". 'Program' not found");break;
		}
		case 101: {
			System.out.println("Error " + errorCode + ". Identifier list not found.");break;
		}
		case 102: {
			System.out.println("Error " + errorCode + ". Identifier not found.");break;
		}
		case 103: {
			System.out.println("Error " + errorCode + ". Type not found.");break;
		}
		case 104: {
			System.out.println("Error " + errorCode + ". Operation list not found.");break;
		}
		case 105: {
			System.out.println("Error " + errorCode + ". Operation not found.");break;
		}
		case 106: {
			System.out.println("Error " + errorCode + ". Logical expression not found.");break;
		}
		case 107: {
			System.out.println("Error " + errorCode + ". Logical terminal not found.");break;
		}
		case 108: {
			System.out.println("Error " + errorCode + ". Logical multiplier not found.");break;
		}
		case 109: {
			System.out.println("Error " + errorCode + ". Logiacl ratio not found.");break;
		}
		case 110: {
			System.out.println("Error " + errorCode + ". Math expression not found.");break;
		}
		case 111: {
			System.out.println("Error " + errorCode + ". Math terminal not found.");break;
		}
		case 112: {
			System.out.println("Error " + errorCode + ". Math multiplier not found.");break;
		}
		case 113: {
			System.out.println("Error " + errorCode + ". Program name not found.");break;
		}
		case 114: {
			System.out.println("Error " + errorCode + ". 'var' not found.");break;
		}
		case 115: {
			System.out.println("Error " + errorCode + ". 'begin' not found");break;
		}
		case 116: {
			System.out.println("Error " + errorCode + ". 'end.' not found.");break;
		}
		case 117: {
			System.out.println("Error " + errorCode + ". 'var.' not found.");break;
		}
		default: {
			System.out.println("Unknown condition");
		}
		}
	}

}
