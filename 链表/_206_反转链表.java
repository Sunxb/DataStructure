package com.sunxb.链表;

/*
 * https://leetcode-cn.com/problems/reverse-linked-list/
 * */
public class _206_反转链表 {

	static class ListNode {
		int val;
		ListNode next;
		
		ListNode(int x) { val = x; }
	}
	
	
	// 递归
	public ListNode reverseList(ListNode head) {
		
		if (head == null || head.next == null) {
			return head;
		}
		
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
		
		return newHead;
    }
	
	// 迭代
	public ListNode reverseList2(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		
		ListNode prev = head;
		ListNode cur = prev.next;
		ListNode tmp = cur.next;
		
		while (cur != null) {
			tmp = cur.next;
			cur.next = prev;
			
			prev = cur;
			cur = tmp;
		}
		
        head.next = null;
		
		return prev;
    }
	
	private void printListNodeValue(ListNode root) {
		ListNode node = root;
		while (node != null) {
			System.out.println(node.val);
			node = node.next;
		}
	}
	
	

//	public static void main(String[] args) {
//		
//		ListNode node0 =  new ListNode(8);
//		node0.next = null;
//		
//		ListNode node1 =  new ListNode(6);
//		node1.next = node0;
//		
//		ListNode node2 =  new ListNode(4);
//		node2.next = node1;
//		
//		ListNode node3 =  new ListNode(2);
//		node3.next = node2;
//		
//		ListNode node4 =  new ListNode(1);
//		node4.next = node3;
//		
//		
//		
//		_206_反转链表 link = new _206_反转链表();
//		
//		
////		ListNode newNode = link.reverseList(node4);
//		ListNode newNode1 = link.reverseList2(node4);
////		link.printListNodeValue(newNode);
//		System.out.println("\n\n");
//		link.printListNodeValue(newNode1);
//	}
	
}
