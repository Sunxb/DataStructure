package com.sunxb.栈;

import java.util.List;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/basic-calculator/
 * @author sunxiaobin
 * 本体其实就是中缀表达式-转换为前缀或者后缀表达式 然后用栈来辅助求解
 */
public class _224_基本计算器 {
	
	public int calculate(String s) {
		
		String preStr = midTransformToPre(s);
        return calculaPre(preStr);
    }
	
	private int calculaPre(String s) {
		
		Stack<String> stack = new Stack<String>();
		// 遍历前缀表达式  从后向前
		for (int i = s.length()-1; i >= 0; i--) {
			char c = s.charAt(i);
			if (isSymbol(c)) {
				// 如果是运算符
				String first = String.valueOf(stack.pop());
				String second = String.valueOf(stack.pop());
				String res = calculateOperation(first, second, String.valueOf(c));
				stack.push(res);
			}
			
			else {
				// 是数字  因为前缀表达式 没有括号
				stack.push(String.valueOf(c));
			}
		}
		
		return Integer.parseInt(stack.pop());
	}
	
	// 逆波兰表达式 其实就是后缀表达式  运算的时候是栈低的在前   前缀表达式相反 - 栈低的在后
	private String calculateOperation(String first, String second, String symbol) {
		switch (symbol) {
		case "+":
			return String.valueOf(Integer.parseInt(first) + Integer.parseInt(second));
		case "-":
			return String.valueOf(Integer.parseInt(first) - Integer.parseInt(second));
		case "*":
			return String.valueOf(Integer.parseInt(first) * Integer.parseInt(second));
		case "/":
			return String.valueOf(Integer.parseInt(first) / Integer.parseInt(second));
		default:
			return "0";
		}
	}
	
	// 中缀表达式 -> 前缀表达式
	private String midTransformToPre(String s) {
		Stack<String> symbolStack = new Stack<String>();//
		Stack<String> valueStack = new Stack<String>();// 
		
		
		// 从右向左遍历s
		for (int i = s.length()-1; i >= 0; i--) {
			char c = s.charAt(i);
			
			// 空格处理掉
			if (c == ' ') continue;
			
			// 如果是数字 直接放到valueStack
			if (Character.isDigit(c)) {
				// 只有一位的数字还是多位的数字
				int res = (int)c - (int)('0');
				while ((i-1) >= 0 && Character.isDigit(s.charAt(i-1))) {
					char next = s.charAt(i-1);
					int n = (int)next - (int)('0');
					res = n * 10 + res;
					i --;
				}
				
				valueStack.push(String.valueOf(res));
			}
			// 不是数字
			// 是运算符号+-*/ 或者（）
			else {
				// 1. 如果是右括号 直接进栈 symbolStack
				if (c == ')') {
					symbolStack.push(String.valueOf(c));
				}
				// 2 是运算符号 
				else if (isSymbol(c)) {
					
					// 1. symbolStack 空  直接进栈
					if (symbolStack.isEmpty()) {
						symbolStack.push(String.valueOf(c));
					}
					// 2. symbolStack 栈顶为 ） 直接进栈
					else if (symbolStack.peek() == ")") {
						symbolStack.push(String.valueOf(c));
					}
					// 3. 栈顶是别的运算符  比较优先级  如果优先级 高于 或者 等于 栈顶的运算符  直接进栈
					//    如果优先级低  symbolStack的栈顶出栈 扔到valueStack中 然后继续跟symbolStack的栈顶比
					else {
						
						// 比较优先级高低
						while (!inputIsHigherOrEqual(String.valueOf(c), symbolStack.peek())) {
							valueStack.push(symbolStack.pop());
						}
						symbolStack.push(String.valueOf(c));
					}
				}
				
				// 3 是左括号 (
				else {
					while (symbolStack.peek() != ")") {
						valueStack.push(symbolStack.pop());
					}
					// 到 右括号停止循环 但是得删掉右括号
					if (symbolStack.peek() == ")") {
						symbolStack.pop();
					}
				}
			}
		}
		
		// 循环结束后  把symbolStack里面的值依次出栈 入到valueStack
		while (!symbolStack.isEmpty()) {
			valueStack.push(symbolStack.pop());
		}
		
		StringBuffer str = new StringBuffer();
		while (!valueStack.isEmpty()) {
			str.append(valueStack.pop());
		}
		 
		return str.toString();
	}
	
	private boolean inputIsHigherOrEqual(String input, String top) {
		
		if (input == "*" || input == "/") {
			return true; // 高
		}
		
		else {
			if (top == "*" || top == "/") {
				return false; // 低
			}
			else {
				return true; // 相等
			}
		}
	}
	
	// 是否是运算符 +-*/
	private boolean isSymbol(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/';
	}
	
	
    public static void main(String[] args) {
    	_224_基本计算器 obj = new _224_基本计算器();
		System.out.println(obj.calculate("1223 + 1"));
	}
}
