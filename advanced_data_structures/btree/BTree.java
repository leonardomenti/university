public class BTree {

  private final int B;
  private Node root;
  private int height;
  private int n_split;

  public BTree(int b) {
    B = b;
    root = new Node(0, true);
    height = 1;
    n_split = 0;
  }

  public int getHeight() { return height; }
  public int getN_split() { return n_split; }

  // Splitting the node
  private void split(Node x, int pos, Node y) {
    n_split++;
    Node z = new Node(B-1, y.leaf);
    if (B - 1 >= 0) System.arraycopy(y.keys, 0 + B, z.keys, 0, B - 1);

    if (!y.leaf) {
      if (B >= 0) System.arraycopy(y.children, 0 + B, z.children, 0, B);

    }
    y.n = B - 1;
    if (x.n + 1 - (pos + 1) >= 0) System.arraycopy(x.children, pos + 1, x.children, pos + 1 + 1, x.n + 1 - (pos + 1));

    x.children[pos + 1] = z;

    if (x.n - pos >= 0) System.arraycopy(x.keys, pos, x.keys, pos + 1, x.n - pos);

    x.keys[pos] = y.keys[B - 1];
    x.n = x.n + 1;
  }

  // Inserting a value
  public void insert(int key) {
    Node curr = this.root;
    if (curr.n == 2 * B - 1) {
      Node s = new Node(0, false);
      root = s;
      this.height++;
      s.insertChild(0, curr);
      split(s, 0, curr);
      insertValue(s, key);
    } 
    else {
      insertValue(curr, key);
    }
  }

  // Insert a value inside the node
  private void insertValue(Node x, int k) {
    if (x.leaf) {
      int i = 0;
      for (i = x.n - 1; i >= 0 && k < x.keys[i]; i--)
        x.keys[i + 1] = x.keys[i];
      x.keys[i + 1] = k;
      x.n = x.n + 1;
    } 
    else {
      int i = 1;
      Node tmp = x.children[i];
      if (tmp.n == 2 * B - 1) {
        split(x, i, tmp);
        if (k > x.keys[i])
          i++;
      }
      insertValue(x.children[i], k);
    }
  }

  // Node
  public class Node {

    private int n;
    private final boolean leaf;
    private final int[] keys = new int[2 * B - 1];
    private final Node[] children = new Node[2 * B];

    public Node(int n, boolean leaf) {
      this.n = n;
      this.leaf = leaf;
    }

    public void insertChild(int i, Node newNode) {
      children[i] = newNode;
    }
  }
}
