package com.sunxb.链表;

public class _21_合并两个有序链表 {
	static class ListNode {
		int val;
		ListNode next;
		
		ListNode(int x) { val = x; }
	}
	
	// 以l1为基础， 遍历l2的结点，插入到l1中，
	// 每插入一个结点，下次插入下一个节点时，无序从l1头开始遍历插入，直接从上次插入的点继续遍历的就行。因为两个链都是有序的
	// 1,2,3  1,4,5
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    	// return 
    	ListNode headerListNode = l1;
    	
    	// 两个标识
    	ListNode startListNode = l1;
    	ListNode prevListNode = null;
   
    	
    	while (l2 != null) {
    		// 提前把next 记录下来
    		ListNode nextInsert = l2.next;
    		    		
			if (l2.val > startListNode.val) {
				// 插到后面 - 具体往后到什么位置？
				ListNode start = startListNode;
	     		while (start != null && start.val < l2.val) {
					prevListNode = start;
	     			start = start.next;
				}
	     		// 找到要插入的位置了，（start之前
	     		prevListNode.next = l2;
	     		l2.next = start;
	     		
	     		// 更新一下标识
	     		startListNode = l2;	     		
			}
			else {
				// 插到前面
				
				if (prevListNode == null) {
					// 第一次往前面插入
					l2.next = startListNode;	
					
					// return
					headerListNode = l2;
				}
				else {
					prevListNode.next = l2;
					l2.next = startListNode;
				}
				// 更新prev
				prevListNode = l2;
			}
			
			l2 = nextInsert;
		}
    	
        return headerListNode;
    }
	
	private void printListNodeValue(ListNode root) {
		ListNode node = root;
		while (node != null) {
			System.out.println(node.val);
			node = node.next;
		}
	}
	
	public static void main(String[] args) {
		// list 2
		ListNode node0 =  new ListNode(5);
		node0.next = null;
		
		ListNode node1 =  new ListNode(2);
		node1.next = node0;
		
		ListNode node2 =  new ListNode(1);
		node2.next = node1;
		
		// list 1
		ListNode node3 =  new ListNode(3);
		node3.next = null;
		
		ListNode node4 =  new ListNode(3);
		node4.next = node3;
		
		ListNode node5 =  new ListNode(1);
		node5.next = node4;
		
		
		_21_合并两个有序链表 link = new _21_合并两个有序链表();
		
		
		ListNode newNode = link.mergeTwoLists(null,node2);
//		ListNode newNode1 = link.reverseList2(node4);
//		link.printListNodeValue(newNode);
		System.out.println("11\n\n");
		link.printListNodeValue(newNode);
	}
}
