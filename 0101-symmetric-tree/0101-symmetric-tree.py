class Solution:
    def isSymmetric(self, root):
        def mirror(a, b):
            if not a and not b:
                return True
            if not a or not b:
                return False
            return a.val == b.val and mirror(a.left, b.right) and mirror(a.right, b.left)

        return mirror(root.left, root.right)
        