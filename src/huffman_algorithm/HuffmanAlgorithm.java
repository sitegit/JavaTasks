package huffman_algorithm;

import java.util.*;

import static huffman_algorithm.TreeNode.generateCodesRecursive;

public class HuffmanAlgorithm {
    public static void main(String[] args) {
        String text = "Build interactive agents with Gemini, supercharge your Android game with powerful APIs, " +
                "and expand your PC gamer audience with Play's growth tools.";
        compress(text);
    }

    private static void compress(String text) {
        if (text.isEmpty()) return;

        Map<Character, Integer> frequencies = countFrequency(text);
        PriorityQueue<TreeNode> queueNodes = new PriorityQueue<>();

        addNodesInQueue(frequencies, queueNodes);

        TreeNode tree = createHuffmanTree(queueNodes);
        Map<Character, String> codes = generateCodes(tree);
        String encoded = encodeText(text, codes);

        System.out.println("Размер исходного текста в битах: " + text.getBytes().length * 8);
        System.out.println("Размер закодированного текста в битах: " + encoded.length());
        System.out.println("Закодированный текст: " + encoded);

        String decoded = decodeText(encoded, tree);
        System.out.println("Декодированный текст: " + decoded);
    }

    private static Map<Character, Integer> countFrequency(String text) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        return freqMap;
    }

    private static void addNodesInQueue(Map<Character, Integer> freqMap, PriorityQueue<TreeNode> queueNodes) {
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            queueNodes.add(new TreeNode(entry.getKey(), entry.getValue()));
        }
    }

    private static TreeNode createHuffmanTree(PriorityQueue<TreeNode> queueNodes) {
        if (queueNodes.isEmpty()) return null;

        if (queueNodes.size() == 1) {
            TreeNode singleNode = queueNodes.poll();
            return new TreeNode(singleNode.frequency, singleNode, null);
        }

        while (queueNodes.size() > 1) {
            TreeNode left = queueNodes.poll();
            TreeNode right = queueNodes.poll();
            TreeNode parent = new TreeNode(left.frequency + right.frequency, left, right);

            queueNodes.add(parent);
        }

        return queueNodes.poll();
    }

    private static Map<Character, String> generateCodes(TreeNode root) {
        Map<Character, String> codes = new HashMap<>();
        generateCodesRecursive(root, "", codes);
        return codes;
    }

    private static String encodeText(String text, Map<Character, String> codes) {
        StringBuilder encoded = new StringBuilder();
        for (char c : text.toCharArray()) {
            encoded.append(codes.get(c));
        }
        return encoded.toString();
    }

    private static String decodeText(String encoded, TreeNode tree) {
        if (encoded.isEmpty()) return "";

        StringBuilder decoded = new StringBuilder();

        TreeNode node = tree;
        for (int i = 0; i < encoded.length(); i++) {
            node = encoded.charAt(i) == '0' ? node.left : node.right;

            if (node.content != null) {
                decoded.append(node.content);
                node = tree;
            }
        }

        return decoded.toString();
    }
}
