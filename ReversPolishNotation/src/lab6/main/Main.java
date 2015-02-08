package lab6.main;

import java.io.IOException;

import codereader.CodeReader;
import lab6.rpn.AscendingReversPolishNotationBuilder;
import lab6.rpn.ReversPolishNotationBuilder;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReversPolishNotationBuilder rpnBuilder = new AscendingReversPolishNotationBuilder();
		rpnBuilder.setExpression("a * ( - b + c )");
		System.out.println(rpnBuilder.parceExpression());
	}

}
