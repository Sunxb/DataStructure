package com.sunxb.哈希表;


import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap<K,V> implements Map<K,V> {
    private static final boolean RED = false;
    private static final boolean BLACK = false;
    private int size;  // 所有键值对的个数
    private Node<K,V>[] table; // 数组 , 每个数组中有一颗红黑树
    private static final int DEFALT_CAPACITY = 1 << 4; // 数值默认大小1<<4  2^4

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashMap() {
        table = new Node[DEFALT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        size = 0;
        // table数组清空
        for (int i = 0; i < table.length; i ++ ) {
            table[i] = null;
        }
    }

    /**
     * 存储k,v  , 首先要想计算出k对应的table中的下标, 如果此位置还没有树,那就创建一棵,如果存在了,就添加到树里面
     */
    @Override
    public V put(K key, V value) {
        // 扩容
        reSize();

        int index = index(key);
        Node<K,V> root = table[index];

        if (root == null) {
            // 此位置的第一个
            root = createNode(key,value,null);
            table[index] = root;
            size ++ ;
            fixAfterPut(root);
            return null;
        }

        // 添加新的结点到红黑树
        // 1. 找一个位置添加上
        Node<K,V> parent = root;
        Node<K,V> node = root;
        int cmp = 0;
        K k1 = key;
        int h1 = hash(k1);
        Node<K,V> result = null;
        boolean searched = false; //是否已经搜索过这个key

        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;

            if (h1 > h2) { // h1 与 h2 不相等  两个node的hash不一样 (hash不相等 但是 &(table.length-1)之后可能相等啊 )
                cmp = 1;
            }
            else if (h1 < h2) {
                cmp = -1;
            }

            // h1与h2相等 - 找别的计算方式

            // 看k1跟k2是不是equals  如果equal就代表我们定义k1和k2是相同的
            else if (Objects.equals(k1,k2)) {
                cmp = 0;
            }

            //
            else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
                // 这一堆的条件,前几个 就是代表k1和k2 是同类  并且能比较 (实现了Comparable) 并且都不为null
                // 如果满足了前几个条件, 那就比较k1 k2  , 如果比较出来的结果不是0, 就代表k1和k2 比较结束了  比出了大小
                // 如果比完了还是0  那么不满足这个else if的执行条件  就会继续往下走 继续比较
            }

            else if (searched) { // 为了优化
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            // 有的时候我们需要认为new出来的实例是相等的, 但是地址谁大谁小是不一定的,
            else {// 扫描
                if ((node.left != null && (result=node(node.left,k1)) != null)
                        || (node.right != null && (result=node(node.right,k1)) != null)) {

                    node = result;
                    cmp = 0;
                }

                else {
                    searched = true;
                    cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
                }
            }

            if (cmp > 0) {
                node = node.right;
            }
            else if (cmp < 0) {
                node = node.left;
            }
            else {
                V oldValue = node.value;
                node.key = key;
                node.value = value;
                node.hash = h1;
                return oldValue;
            }

        } while (node != null);

        // 插入
        Node<K,V> newNode = createNode(key,value,parent);
        if (cmp > 0) {
            parent.right = newNode;
        }
        else {
            parent.left = newNode;
        }
        size ++;

        // 2. 处理平衡问题
        fixAfterPut(newNode);

        return null;
    }

    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i ++) {
            if (table[i] == null) continue;

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K,V> node = queue.poll();

                if (Objects.equals(value, node.value)) return true;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i ++) {
            if (table[i] == null) continue;

            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K,V> node = queue.poll();

                if (visitor.visit(node.key,node.value)) return;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }

    }

    /**
     * 扩容
     */
    private void reSize() {
        if (size * 1.0 / table.length <= DEFAULT_LOAD_FACTOR) return;

        Node<K,V>[] oldTable = table;
        table = new Node[oldTable.length << 1];

        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i < oldTable.length; i ++) {
            if (oldTable[i] == null) continue;
            queue.offer(oldTable[i]);

            while (!queue.isEmpty()) {
                Node<K,V> node = queue.poll();

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);

                moveNode(node);
            }
        }

    }

    /**
     * 移动结点  扩容的时候重新移动结点的位置
     * @param newNode
     */
    private void moveNode(Node<K,V> newNode) {
        // 重置 (只需要重置下面这几个跟位置相关的属性
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        int index = index(newNode);
        Node<K,V> root = table[index];
        if (root == null) {// 当前位置第一个
            root = newNode;
            table[index] = root;
            fixAfterPut(root);
            return;
        }

        // 添加新的节点到红黑树上面
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hash;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hash;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        // 新添加节点之后的处理
        fixAfterPut(newNode);
    }


    /**
     * 移除结点
     * @param node
     * @return
     */
    protected V remove(Node<K, V> node) {
        if (node == null) return null;

        Node<K, V> willNode = node;

        size--;

        V oldValue = node.value;

        if (node.hasTwoChildren()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            node.hash = s.hash;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;
        int index = index(node);

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                table[index] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            fixAfterRemove(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            table[index] = null;
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }

            // 删除节点之后的处理
            fixAfterRemove(node);
        }

        // 交给子类去处理
        afterRemove(willNode, node);

        return oldValue;
    }

    /**
     * 后继节点
     * @param node
     * @return
     */
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * 找Key对应的node
     * @param key
     * @return
     */
    private Node<K,V> node(K key) {
        Node<K,V> n = table[index(key)];
        return n == null ? null : node(n, key);
    }

    /**
     * 找Key对应的node
     * @param root
     * @param key
     * @return
     */
    private Node<K,V> node(Node<K,V> root, K key) {
        K k1 = key;
        int h1 = hash(key);

        Node<K,V> node = root;
        int cmp = 0;
        Node<K,V> result = null;

        while (node != null) {
            K k2 = node.key;
            int h2 = node.hash;

            if (h1 > h2) {
                node = node.right;
            }
            else if (h1 < h2) {
                node = node.left;
            }
            else if (Objects.equals(k1,k2)) {
                return node;
            }
            else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp=((Comparable)k1).compareTo(k2)) != 0) {

                node = cmp > 0 ? node.right : node.left;
            }
            else if (node.right != null && (result = node(node.right, k1)) != null) { // 往右边遍历
                return result;
            }
            else {// 只能往左找
                node = node.left;
            }
        }

        return null;
    }


    /**
     * 方便子类如果要有自己的node类型, 灵活的替换整个结构中使用的node
     */
    protected Node<K,V> createNode(K key, V value, Node<K, V> parent) {
        return new Node<>(key, value, parent);
    }

    /**
     * 给子类重写
     * @param willNode
     * @param removedNode
     */
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) { }


    /**
     *  计算key对应的索引
     */
    private int index(K key) {
        return  hash(key) & (table.length-1); //为了在计算hash值的时候充分运算 我们优化一下hash
    }

    private int index(Node<K,V> node) {
        return  node.hash & (table.length-1); //为了在计算hash值的时候充分运算 我们优化一下hash
    }

    private int hash(K key) {
        // hashMap的key可能为null
        if (key == null) return 0;
        int hash = key.hashCode();
        return hash ^ (hash >>> 16);
    }


    /**
     * 红黑树删除结点之后 颜色和旋转处理
     * @param node
     */
    private void fixAfterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    fixAfterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }


    /**
     *  红黑树添加结点之后 颜色和旋转处理
     * @param node
     */
    private void fixAfterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;

        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }

        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            fixAfterPut(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
                black(parent);
            } else { // LR
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else { // R
            if (node.isLeftChild()) { // RL
                black(node);
                rotateRight(parent);
            } else { // RR
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            table[index(grand)] = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
    }


    // 红黑树颜色相关
    private Node<K,V> color(Node<K,V> node, boolean color) {
        if (node == null) return null;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }


    // node
    protected static class Node<K,V> {
        int hash; // 哈希值
        K key;
        V value;
        boolean color = RED;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            int hash = key == null ? 0 : key.hashCode();
            this.hash = hash ^ (hash >>> 16); // int 一共 4 字节 32 位   (充分运算)
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<K,V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }

            if (isRightChild()) {
                return parent.left;
            }

            return null;
        }

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }

    }
}
