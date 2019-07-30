package com.sunxb.栈;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/evaluate-reverse-polish-notation/
 * @author sunxiaobin
 *
 */

public class _150_逆波兰表达式求值 {
    public int evalRPN(String[] tokens) {
    	
    	Stack<String> stack = new Stack<String>();
    	for (String s : tokens) {
    		if (isSymbol(s)) {
				// 符号
    			String first = stack.pop();
    			String second = stack.pop();
    			String value = calculate(first, second, s);
    			stack.push(value);
			}
    		else {
    			// 数字
    			stack.push(s);
    		}
    	}
    	
        return Integer.parseInt(stack.pop());
    }
    
    private boolean isSymbol(String s) {
		return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
	}
    
    private String calculate(String first, String second, String symbol) {
		switch (symbol) {
		case "+":
			return String.valueOf(Integer.parseInt(second) + Integer.parseInt(first));
		case "-":
			return String.valueOf(Integer.parseInt(second) - Integer.parseInt(first));
		case "*":
			return String.valueOf(Integer.parseInt(second) * Integer.parseInt(first));
		case "/":
			return String.valueOf(Integer.parseInt(second) / Integer.parseInt(first));
		default:
			return "0";
		}
	}
    
    public static void main(String[] args) {
    	_150_逆波兰表达式求值 obj = new _150_逆波兰表达式求值();
    	String[] tokens = {"2","1","+","3","*"};
		System.out.println(obj.evalRPN(tokens));
	}
}
