class Solution {
    public List<TreeNode> generateTrees(int n) {
        return build(1, n);
    }

    private List<TreeNode> build(int left, int right) {
        List<TreeNode> ans = new ArrayList<>();

        if (left > right) {
            ans.add(null);
            return ans;
        }

        for (int root = left; root <= right; root++) {
            List<TreeNode> leftTrees = build(left, root - 1);
            List<TreeNode> rightTrees = build(root + 1, right);

            for (TreeNode l : leftTrees) {
                for (TreeNode r : rightTrees) {
                    TreeNode node = new TreeNode(root);
                    node.left = l;
                    node.right = r;
                    ans.add(node);
                }
            }
        }

        return ans;
    }
}