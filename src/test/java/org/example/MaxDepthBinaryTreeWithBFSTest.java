package org.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedList;
import java.util.Queue;
import org.junit.jupiter.api.Test;

/**
 * Problem: Maximum Depth of Binary Tree
 *
 * <p>Given the root of a binary tree, find its maximum depth — the number of nodes along the
 * longest path from the root down to the farthest leaf, counting the root itself as depth 1.
 *
 * <p>Reference: https://www.hellointerview.com/learn/code/breadth-first-search/introduction
 *
 * <p>Solution below: BFS (level-order traversal), reusing the same queue-based walk as {@link
 * BinaryTreeLevelOrderWithBFSTest}, with one addition — processing the tree one full level at a
 * time instead of one node at a time.
 *
 * <p>Key technique: before processing a level, take a "snapshot" of the queue's current size
 * ({@code queue.size()}). That snapshot is exactly the number of nodes in the current level,
 * because every node from the previous level's children was already enqueued and nothing from
 * the next level has been added yet. Running a bounded {@code for} loop for exactly that many
 * iterations processes the whole level — polling each node, enqueuing its children — without
 * mixing in nodes from the level after it. Each full pass through the outer {@code while}
 * corresponds to exactly one level of depth, so {@code depth} is incremented once per pass.
 *
 * <p>The snapshot must be taken inside the {@code while} loop, at the start of each iteration —
 * not once before the loop — since it needs to be recomputed for every level.
 *
 * <p>This runs in O(n) time (every node is enqueued and dequeued exactly once) and O(w) space,
 * where w is the widest level of the tree (the queue never holds more than one level at a time).
 */
class MaxDepthBinaryTreeWithBFSTest {

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

    public int bfs(TreeNode root) {
        int depth = 0;
        if (root == null) {
            return depth;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode currNode = queue.poll();

                if (currNode.left != null) {
                    queue.offer(currNode.left);
                }
                if (currNode.right != null) {
                    queue.offer(currNode.right);
                }
            }
            depth++;
        }
        return depth;
    }

    @Test
    void treeFromWalkthrough() {
        TreeNode one = new TreeNode(1);
        TreeNode four = new TreeNode(4);
        TreeNode nine = new TreeNode(9);
        TreeNode three = new TreeNode(3, one, four);
        TreeNode eight = new TreeNode(8, null, nine);
        TreeNode root = new TreeNode(5, three, eight);

        assertThat(bfs(root)).isEqualTo(3);
    }

    @Test
    void singleNodeTree() {
        TreeNode root = new TreeNode(1);
        assertThat(bfs(root)).isEqualTo(1);
    }

    @Test
    void emptyTree() {
        assertThat(bfs(null)).isEqualTo(0);
    }

    @Test
    void unbalancedLeftOnlyChain() {
        TreeNode level3 = new TreeNode(3);
        TreeNode level2 = new TreeNode(2, level3, null);
        TreeNode level1 = new TreeNode(1, level2, null);
        assertThat(bfs(level1)).isEqualTo(3);
    }

    @Test
    void perfectBinaryTreeOfDepthTwo() {
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        TreeNode root = new TreeNode(1, left, right);
        assertThat(bfs(root)).isEqualTo(2);
    }
}
