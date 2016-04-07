package alg;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class IntBinTree {
    private int root;
    private IntBinTree leftSubTree = null;
    private IntBinTree rightSubTree = null;
    private int size;
    
    public IntBinTree(int[] array) {
        size = array.length;
        
        int rootIndex;
        if (size%2==0) {
            rootIndex = (size)/2;
        } else {
            rootIndex = (size-1)/2;
        }
        this.root = array[rootIndex];
        
        if(rootIndex > 0) {
            int[] leftArray = new int[rootIndex];
            System.arraycopy(array, 0, leftArray, 0, rootIndex);
            this.leftSubTree = new IntBinTree(leftArray);
        }
        
        if (size-1 > rootIndex) {
            int rightArrayLength = size - rootIndex-1;
            int[] rightArray = new int[rightArrayLength];
            for (int i = 0; i<rightArrayLength; i++) {
                rightArray[i] = array[rootIndex+1 + i];
            }
            this.rightSubTree = new IntBinTree(rightArray);
        }
    }
    
    public boolean add(int toAdd) {
        if(toAdd == this.root) {
            return false;
        }
        boolean added;
        if(toAdd < root) {
            if(leftSubTree!=null) {
                added = leftSubTree.add(toAdd);
            } else {
                int[] temp = {toAdd};
                leftSubTree = new IntBinTree(temp);
                added = true;
            }
        } else {
            if(rightSubTree!=null) {
                added = rightSubTree.add(toAdd);
            } else {
                int[] temp = {toAdd};
                rightSubTree = new IntBinTree(temp);
                added = true;
            }
        }
        if(added) {
            size++;
        }
        return added;
    }
    
    public void remove(int toRemove) {
        this.remove(toRemove, this);
    }
    
    private void remove(int toRemove, IntBinTree fromTree) {
        if(toRemove == this.root) {
            if(leftSubTree!=null && rightSubTree==null) {
                if(this==fromTree.leftSubTree) {
                    fromTree.leftSubTree = this.leftSubTree;
                } else {
                    fromTree.rightSubTree = this.leftSubTree;
                }
            }
            else if(leftSubTree==null && rightSubTree!=null) {
                if(this==fromTree.leftSubTree) {
                    fromTree.leftSubTree = this.rightSubTree;
                } else {
                    fromTree.rightSubTree = this.rightSubTree;
                }
            }
            else if(leftSubTree==null && rightSubTree==null) {
                this.removeThis(fromTree);
            }
            else {
                int maxValue = this.leftSubTree.getMaxValue();
                this.root = maxValue;
                this.leftSubTree.remove(maxValue, this);
            }
        }
        else if(toRemove < root) {
            leftSubTree.remove(toRemove, this);
        }
        else {
            rightSubTree.remove(toRemove, this);
        }
        size--;
    }
    
    private void removeThis(IntBinTree fromTree) {
        if(this==fromTree.leftSubTree) {
            fromTree.leftSubTree=null;
        }
        else if(this==fromTree.rightSubTree) {
            fromTree.rightSubTree=null;
        }
    }
    
    private int getMaxValue() {
        if(this.rightSubTree==null) {
            return root;
        }
        else {
            return rightSubTree.getMaxValue();
        }
    }
    
    public int maxDepth() {
        //int maxDepth = 0;
        if(leftSubTree!=null && rightSubTree==null) {
            return leftSubTree.maxDepth()+1;
        }
        else if(leftSubTree==null && rightSubTree!=null) {
            return rightSubTree.maxDepth()+1;
        }
        else if(leftSubTree==null && rightSubTree==null) {
            return 0;
        }
        else {
            int leftMaxDepth = leftSubTree.maxDepth();
            int rightMaxDepth = rightSubTree.maxDepth();
            if(leftMaxDepth < rightMaxDepth) {
                return rightMaxDepth+1;
            } else if (leftMaxDepth>=rightMaxDepth && leftMaxDepth!=0) {
                return leftMaxDepth+1;
            }
        }
        return 0;
    }
    
    public int valuesInMaxDepth() {
        return valuesInTargetDepth(0, this.maxDepth());
    }
    
    private int valuesInTargetDepth(int thisDepth, int targetDepth) {
        //int numOfValues;
        if(thisDepth == targetDepth) {
            return 1;
        }
        else if(leftSubTree!=null && rightSubTree==null) {
            return leftSubTree.valuesInTargetDepth(thisDepth+1, targetDepth);
        }
        else if(leftSubTree==null && rightSubTree!=null) {
            return rightSubTree.valuesInTargetDepth(thisDepth+1, targetDepth);
        }
        else if(leftSubTree==null && rightSubTree==null) {
            return 0;
        }
        else {
            int leftValues = leftSubTree.valuesInTargetDepth(thisDepth+1, targetDepth);
            int rightValues = rightSubTree.valuesInTargetDepth(thisDepth+1, targetDepth);
            return leftValues + rightValues;
        }
        //return numOfValues;
    }
    
    public int size() {
        return size;
    }
    
    public int getValueHigherThanXNumbers(int x) {
        //int resultValue;
        int rootLowerNumbers = 0;
        if(leftSubTree!=null) {
            rootLowerNumbers = leftSubTree.size();
        }
        if(x < rootLowerNumbers) {
            return leftSubTree.getValueHigherThanXNumbers(x);
        } else if(x == rootLowerNumbers) {
            return this.root;
        } else {
            return rightSubTree.getValueHigherThanXNumbers(x-(rootLowerNumbers+1));
        }
        //return resultValue;
    }

}
