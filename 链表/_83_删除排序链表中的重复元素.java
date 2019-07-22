package com.sunxb.链表;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
 * @author sunxiaobin
 *
 */
public class _83_删除排序链表中的重复元素 {
	
	static class ListNode {
		int val;
		ListNode next;
		
		ListNode(int x) { val = x; }
	}
	
	public ListNode deleteDuplicates(ListNode head) {
		if (head == null || head.next == null) {
			return head;
		}
		
		ListNode root = head;
		ListNode oldStart = head.next;
		
		// 创建一条新的链
		ListNode node = new ListNode(root.val);
		
		// 标识尾部的node
		ListNode prev = node;
		
 
		while (oldStart != null) {			
			//循环新的链 从node.next开始
			ListNode startListNode = node;
			boolean hasSame = false;
			
			while (startListNode != null) {
				if (oldStart.val == startListNode.val) {
					hasSame = true;
					break;
				}
				
				startListNode = startListNode.next;
			}
			
			if (!hasSame) {
				prev.next = new ListNode(oldStart.val);
				prev = prev.next;
			}
			
			
			oldStart = oldStart.next;
		}
		
        return node;
    }
	
	
	public ListNode deleteDuplicates1(ListNode head) {
        return func1(head);
    }
    
    private ListNode func1(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode h = head;
        ListNode p1 = head;
        ListNode p2 = p1.next;
        p1.next = null;
        while (p2 != null) {
            while (p2 != null && p1.val == p2.val) {
                p2 = p2.next;
            }
            if (p2 != null) {
                // add p2;
                p1.next = p2;
                p2 = p2.next;
                p1 = p1.next;
                p1.next = null;
            }
        }
        return h;
    }
	
	
	
	
//	private void printListNodeValue(ListNode root) {
//		ListNode node = root;
//		while (node != null) {
//			System.out.println(node.val);
//			node = node.next;
//		}
//	}
	
//	public static void main(String[] args) {
//	
//		ListNode node0 =  new ListNode(2);
//		node0.next = null;
//		
//		ListNode node1 =  new ListNode(1);
//		node1.next = node0;
//		
//		ListNode node2 =  new ListNode(4);
//		node2.next = node1;
//		
//		ListNode node3 =  new ListNode(2);
//		node3.next = node2;
//		
//		ListNode node4 =  new ListNode(2);
//		node4.next = node3;
//		
//		
//		
//		_83_删除排序链表中的重复元素 link = new _83_删除排序链表中的重复元素();
//		
//		
//		ListNode newNode = link.deleteDuplicates1(node4);
////		ListNode newNode1 = link.reverseList2(node4);
//	//	link.printListNodeValue(newNode);
//		System.out.println("\n\n");
//		link.printListNodeValue(newNode);
//	}
	
}
