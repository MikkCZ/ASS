package alg;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class IntegerBinaryTree<Integer> {
    private int root;
    private IntegerBinaryTree leftSubTree = null;
    private IntegerBinaryTree rightSubTree = null;
    private int size;
    
    public IntegerBinaryTree(int[] array) {
        int arrayLength = array.length;
        size = arrayLength;
        
        int rootIndex;
        if (arrayLength%2==0) {
            rootIndex = (arrayLength)/2;
        } else {
            rootIndex = (arrayLength-1)/2;
        }
        this.root = array[rootIndex];
        
        int[] leftArray = new int[rootIndex];
        for (int i = 0; i<rootIndex; i++) {
            leftArray[i] = array[i];
        }
        if(rootIndex > 0) {
            this.leftSubTree = new IntegerBinaryTree(leftArray);
        }
        
        int rightArrayLength = arrayLength - rootIndex-1;
        int[] rightArray = new int[rightArrayLength];
        for (int i = 0; i<rightArrayLength; i++) {
            rightArray[i] = array[rootIndex+1 + i];
        }
        if (arrayLength-1 > rootIndex) {
            this.rightSubTree = new IntegerBinaryTree(rightArray);
        }
    }
    
    public boolean add(int toAdd) {
        if(toAdd == root) {
            return false;
        }
        boolean added = true;
        if(toAdd > root && rightSubTree==null) {
            int[] temp = {toAdd};
            rightSubTree = new IntegerBinaryTree(temp);
            added = true;
        } else if(toAdd > root ) {
            added = rightSubTree.add(toAdd);
        } else if(toAdd < root && leftSubTree==null) {
            int[] temp = {toAdd};
            leftSubTree = new IntegerBinaryTree(temp);
            added = true;
        } else if(toAdd < root) {
            added = leftSubTree.add(toAdd);
        }
        if(added) {
            size++;
        }
        return added;
    }
    
    public void remove(int toRemove) {
        this.remove(toRemove, this);
    }
    
    private void remove(int toRemove, IntegerBinaryTree fromSuper) {
        if(toRemove == root) {
            if(leftSubTree==null && rightSubTree!=null) {
                if(fromSuper.rightSubTree == this) {
                    fromSuper.rightSubTree = rightSubTree;
                } else if(fromSuper.leftSubTree == this) {
                    fromSuper.leftSubTree = rightSubTree;
                }
            } else if (leftSubTree==null && rightSubTree==null) {
                    this.removeThis(fromSuper);
            } else if(rightSubTree==null && leftSubTree!=null) {
                if(fromSuper.rightSubTree == this) {
                    fromSuper.rightSubTree = leftSubTree;
                } else if(fromSuper.leftSubTree == this) {
                    fromSuper.leftSubTree = leftSubTree;
                }
            } else if (rightSubTree!=null && leftSubTree!=null) {
                this.root = this.leftSubTree.removeAndGetMaxValue(this);
            }
        } else if(toRemove > root) {
            rightSubTree.remove(toRemove, this);
            size--;
        } else if (toRemove < root) {
            leftSubTree.remove(toRemove, this);
            size--;
        }
    }
    
    private int removeAndGetMaxValue(IntegerBinaryTree fromSuper) {
        if(rightSubTree==null) {
            removeThis(fromSuper);
            return root;
        } else {
            size--;
            return rightSubTree.removeAndGetMaxValue(this);
        }
    }
    
    private void removeThis(IntegerBinaryTree fromSuper) {
        if(fromSuper.leftSubTree == this) {
            fromSuper.leftSubTree = null;
        } else if (fromSuper.rightSubTree == this) {
            fromSuper.rightSubTree = null;
        }
    }
    
    public int size() {
        return size;
    }
    
    public int getValueLargerThanXNumbers(int x) {
        if(leftSubTree!=null) {
            if(leftSubTree.size()>x) {
                return leftSubTree.getValueLargerThanXNumbers(x);
            }
            if(leftSubTree.size()<x && rightSubTree!=null) {
                return rightSubTree.getValueLargerThanXNumbers(x-leftSubTree.size()-1);
            }
        }
        return root;
    }
    
    public int maxDepth() {
        int leftSubTreeDepth = 0, rightSubTreeDepth = 0;
        if(leftSubTree != null) {
            leftSubTreeDepth = leftSubTree.maxDepth();
        }
        if(rightSubTree != null) {
            rightSubTreeDepth = rightSubTree.maxDepth();
        }
        if(leftSubTreeDepth > rightSubTreeDepth) {
            return leftSubTreeDepth+1;
        } else {
            return rightSubTreeDepth+1;
        }
    }
    
    public int keysInDepth(int depth) {
        if(depth==0) {
            return 1;
        }
        int result = 0;
        if(leftSubTree != null) {
            if(leftSubTree.maxDepth() >= (depth-1)) {
                result += leftSubTree.keysInDepth(depth-1);
            }
        }
        if(rightSubTree != null) {
            if(rightSubTree.maxDepth() >= (depth-1)) {
                result += rightSubTree.keysInDepth(depth-1);
            }
        }
        return result;
    }
}
