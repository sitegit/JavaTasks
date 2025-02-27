package huffman_algorithm;

import java.util.Map;

public class TreeNode implements Comparable<TreeNode> {
    Character content;
    int frequency;
    TreeNode left;
    TreeNode right;

    TreeNode(char content, int frequency) {
        this.content = content;
        this.frequency = frequency;
    }

    TreeNode(int frequency, TreeNode left, TreeNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(TreeNode o) {
        return Integer.compare(this.frequency, o.frequency);
    }

    public static void generateCodesRecursive(TreeNode node, String code, Map<Character, String> codes) {
        if (node == null) return;

        if (node.content != null) {
            codes.put(node.content, code);
        }

        generateCodesRecursive(node.left, code + "0", codes);
        generateCodesRecursive(node.right, code + "1", codes);
    }
}
