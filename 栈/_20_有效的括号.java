package com.sunxb.栈;

import java.util.Iterator;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/valid-parentheses/
 * @author sunxiaobin
 *
 */
public class _20_有效的括号 {
	public boolean isValid(String s) {
		
		while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
			s = s.replace("()", "");
			s = s.replace("{}", "");
			s = s.replace("[]", "");
		}
		
        return s.isEmpty();
    }
	
	public boolean isValid2(String s) {
		int len = s.length();
		Stack<Character> stack = new Stack<Character>();
		
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			
			if (c == '(' || c == '{' || c == '[') {
				stack.push(c);
			}
			
			else {
				if (stack.isEmpty()) return false;
				if (c == ')' && stack.pop() != '(') return false;
				if (c == '}' && stack.pop() != '{') return false;
				if (c == ']' && stack.pop() != '[') return false;
				
			}
		}
		
        return stack.isEmpty();
    }
	
	public static void main(String[] args) {
		_20_有效的括号 obj = new _20_有效的括号();
		System.out.println(obj.isValid2("[()]}()"));
	}
}
