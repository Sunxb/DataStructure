package com.sunxb.栈;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Stack;

public class _856_括号的分数 {
    public int scoreOfParentheses(String S) {
    	
    	Stack<Character> stack = new Stack<Character>();
    	
    	for (int i = 0; i <	S.length(); i++) {
			char c = S.charAt(i);
			
			if (c == '(') {
				stack.push(c);
			}
			else {
				
				int sum = 0;
				// 直到栈顶是（
				while (stack.peek() != '(') {
					char t = stack.pop();
		    		int n = t - '0';
					sum += n;
				}
				
				// 现在栈顶是（
				stack.pop();
				
				// sum==0 意味着中间没别的数 
				if (sum == 0) {
					stack.push('1');
				}
				else {
					stack.push((char)(2*sum+'0'));
				}
				
			}
		}
    	
    	int s = 0;
    	while (stack.size() != 0) {
    		char t = stack.pop();
    		int n = t - '0';
			s += n;
		}
    	
        return s;
    }
    
    // leetcode上面速度最快的
    public int scoreOfParentheses2(String S) {
    	// 双端队列
        Deque<Integer> stack = new ArrayDeque<>();
        int cur = 0;
        for (char c : S.toCharArray()) {
            if (c == '(') {
                stack.push(cur);
                cur = 0;
            } else {
                cur = stack.pop() + Math.max(cur * 2, 1);
            }
        }
        return cur;
    }
    
    
    
    public static void main(String[] args) {
    	_856_括号的分数 obj = new _856_括号的分数();
		System.out.println(obj.scoreOfParentheses2("(()(()))"));
	}
}
