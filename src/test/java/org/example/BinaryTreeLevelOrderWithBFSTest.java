package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.junit.jupiter.api.Test;

/**
 * Problem: Binary Tree Level Order Traversal (plain BFS)
 *
 * <p>Given the root of a binary tree, return the values of its nodes in breadth-first order:
 * level by level, top to bottom, and left to right within each level.
 *
 * <p>Naive approach isn't really applicable here — the whole challenge is picking the right
 * traversal order in the first place. A depth-first walk would visit nodes in the wrong order
 * (it would dive down one branch before crossing to a sibling).
 *
 * <p>Solution below: a queue (FIFO). The root is enqueued first. Then, while the queue isn't
 * empty, the front node is dequeued and visited (its value recorded), and its non-null children
 * are enqueued, left before right. Because the queue preserves insertion order, nodes are always
 * visited in the order they were discovered — which naturally produces level-by-level,
 * left-to-right order.
 *
 * <p>Key reasoning: a stack (LIFO) would not work here. Pushing left then right and popping from
 * the top would visit right before left, reversing the expected order within a level.
 *
 * <p>Edge case: if {@code root} is null, there's no tree to walk, so the method returns an empty
 * list immediately without touching the queue.
 *
 * <p>This runs in O(n) time and O(w) space, where w is the widest level of the tree (the queue
 * holds at most one level's worth of nodes at any point, mixed with the next level as it starts
 * filling in).
 */
class BinaryTreeLevelOrderWithBFSTest {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List<Integer> bfs(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode currNode = queue.poll();
            result.add(currNode.val);

            if (currNode.left != null) {
                queue.offer(currNode.left);
            }
            if (currNode.right != null) {
                queue.offer(currNode.right);
            }
        }
        return result;
    }

    @Test
    void treeFromWalkthrough() {
        TreeNode one = new TreeNode(1);
        TreeNode four = new TreeNode(4);
        TreeNode nine = new TreeNode(9);
        TreeNode three = new TreeNode(3, one, four);
        TreeNode eight = new TreeNode(8, null, nine);
        TreeNode root = new TreeNode(5, three, eight);

        assertThat(bfs(root)).containsExactly(5, 3, 8, 1, 4, 9);
    }

    @Test
    void singleNodeTree() {
        TreeNode root = new TreeNode(1);
        assertThat(bfs(root)).containsExactly(1);
    }

    @Test
    void emptyTree() {
        assertThat(bfs(null)).isEmpty();
    }

    @Test
    void leftSkewedTree() {
        TreeNode level3 = new TreeNode(3);
        TreeNode level2 = new TreeNode(2, level3, null);
        TreeNode level1 = new TreeNode(1, level2, null);
        assertThat(bfs(level1)).containsExactly(1, 2, 3);
    }

    @Test
    void rightSkewedTree() {
        TreeNode level3 = new TreeNode(3);
        TreeNode level2 = new TreeNode(2, null, level3);
        TreeNode level1 = new TreeNode(1, null, level2);
        assertThat(bfs(level1)).containsExactly(1, 2, 3);
    }
}
