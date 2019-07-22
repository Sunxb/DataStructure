package com.sunxb.链表;

/**
 * https://leetcode-cn.com/problems/middle-of-the-linked-list/
 * @author sunxiaobin
 *
 */
public class _876_链表的中间结点 {
	
	static class ListNode {
		int val;
		ListNode next;
		
		ListNode(int x) { val = x; }
	}
	
	// 1. 这是自己第一反应的思路，先求出链表总长度，然后求出中间位置 ， 有点愚蠢。
	public ListNode middleNode(ListNode head) {
		int count = 0;
		ListNode node = head;
		
		while (node != null) {
			count ++;
			node = node.next;
		}
		System.out.println(count);
		
		double mid = Math.floor(5 / 2.0);
		System.out.println(mid);
		
		double index = 0;
		ListNode node1 = head;
		while (node1 != null) {
			if (index == mid) {
				return node1;
			}
			
			index ++;
			node1 = node1.next;
		}
		
		
        return head;
    }
	
	// 这是优化后的思路。利用快慢指针，当快指针走完了，慢指针正好指向中间位置
	public ListNode middleNode1(ListNode head) {
		
		ListNode n1 = head;
		ListNode n2 = head;
		
		while (n2 != null && n2.next != null) {
			n1 = n1.next;
			n2 = n2.next.next;
		}
		
		return n1;
	}
	
	
	private void printListNodeValue(ListNode root) {
		ListNode node = root;
		while (node != null) {
			System.out.println(node.val);
			node = node.next;
		}
	}
	
	public static void main(String[] args) {
		
		ListNode node0 =  new ListNode(2);
		node0.next = null;
		
		ListNode node1 =  new ListNode(1);
		node1.next = node0;
		
		ListNode node2 =  new ListNode(4);
		node2.next = node1;
		
		ListNode node3 =  new ListNode(2);
		node3.next = node2;
		
		ListNode node4 =  new ListNode(2);
		node4.next = node3;
		
		ListNode node5 =  new ListNode(3);
		node5.next = node4;
		
		
		_876_链表的中间结点 link = new _876_链表的中间结点();
		
		
		ListNode newNode = link.middleNode1(node5);
//		ListNode newNode1 = link.reverseList2(node4);
		link.printListNodeValue(newNode);
		System.out.println("\n\n");
//		link.printListNodeValue(newNode);
	}
}
