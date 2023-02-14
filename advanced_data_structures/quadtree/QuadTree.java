import java.util.HashMap;

public class QuadTree{

    public Node root;
    private final int Lx;
    private final int Ly;
    public int n_re_insertion;

    private static final HashMap<String, Integer> quad2dir;
    static {
        quad2dir = new HashMap<>();
        quad2dir.put("NE", 1);
        quad2dir.put("NW", 2);
        quad2dir.put("SW", 3);
        quad2dir.put("SE", 4);
    }

    private static final HashMap<Integer, String> dir2quad;
    static {
        dir2quad = new HashMap<>();
        dir2quad.put(1, "NE");
        dir2quad.put(2, "NW");
        dir2quad.put(3, "SW");
        dir2quad.put(4, "SE");
    }

    public QuadTree(int x, int y) {
        root = null;
        Lx = x;
        Ly = y;
        n_re_insertion = 0;
    }

    public static class Node {
        int x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees
        int value; // associated data
        int direction; // direction of quadrant (1=NE, 2=NW, 3=SW, 4=SE)
        Node parent; // parent node

        Node(int x, int y, int value, int direction, Node parent) {
            this.x = x;
            this.y = y;
            this.value = value;
            this.direction = direction;
            this.parent = parent;
        }

        public Node getQuadFromDir(int i){
            if (i==1) return NE;
            else if (i==2) return NW;
            else if (i==3) return SW;
            else if (i==4) return SE;
            else return null;
        }

        @Override
        public String toString(){
            return "[ X=" + x + "  Y=" + y + "]  value=" + (char) (value+65) + "   direction=" + direction
                    + "   ( parent=" + parent + " )";
        }

        public boolean isOutsideOfCrossRegion(int x1, int y1, int x2, int y2) {
            int xMin = Math.min(x1, x2);
            int xMax = Math.max(x1, x2);
            int yMin = Math.min(y1, y2);
            int yMax = Math.max(y1, y2);
            return !((x>=xMin & x<=xMax) | (y>=yMin & y<=yMax));
        }
    }

    /***********************************************************************
     *  Insertion
     ***************************************************************************/

    public void insert(int x, int y, int value) {
        if (x <= Lx & y <= Ly)
            root = insert(root, x, y, value, 0, null);
    }

    private Node insert(Node h, int x, int y, int value, int direction, Node parent) {
        if (h == null)
            return new Node(x, y, value, direction, parent);
        else if ( less(x, h.x) &&  less(y, h.y)) h.SW = insert(h.SW, x, y, value, 3, h); // SW
        else if ( less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y, value, 2, h); // NW
        else if (!less(x, h.x) &&  less(y, h.y)) h.SE = insert(h.SE, x, y, value, 4, h); // SE
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y, value, 1, h); // NE
        return h;
    }

    /***********************************************************************
     *  Deletion
     ***************************************************************************/

    public void delete(Node nodeToBeDeleted) {
        Node candidate = findCandidate(nodeToBeDeleted);
        int direction = getStartDirection(nodeToBeDeleted, candidate);
        Node newRoot = replaceNode(nodeToBeDeleted, candidate);

        if (nodeToBeDeleted.parent == null)
            // nodeToBeDeleted is root
            root = newRoot;
        else {
            // nodeToBeDeleted is not root, so it has a parent
            String quad = dir2quad.get(nodeToBeDeleted.direction);
            if (quad.equals("NE")) nodeToBeDeleted.parent.NE = newRoot;
            else if (quad.equals("NW")) nodeToBeDeleted.parent.NW = newRoot;
            else if (quad.equals("SW")) nodeToBeDeleted.parent.SW = newRoot;
            else if (quad.equals("SE")) nodeToBeDeleted.parent.SE = newRoot;
        }

        Node[] quadsForAdj = getClosedQuadrants(nodeToBeDeleted, direction);
        for (Node quad: quadsForAdj)
            adj(nodeToBeDeleted, candidate, newRoot, quad, quad.direction, direction);

        newRoot(nodeToBeDeleted, candidate, newRoot, nodeToBeDeleted.getQuadFromDir(direction), direction);
    }

    /***********************************************************************
     *  ADJ and NEWROOT for Deletion
     ***************************************************************************/

    private void adj(Node nodeToBeDeleted, Node candidate, Node newRoot, Node j, int quad_direction, int start_direction) {
        // nodeToBeDeleted: A
        // candidate: older B (the closest node to A), in quadrant i rooted at A
        // new root: B that replace A in the tree
        // J root of the quadrant close to i
        // quad_direction: i
        // start_direction: quadrant (rooted in A) of the winner candidate

        // if J in inside the Crosshatched Region do ADJ just on 2 sub-quadrants
        System.out.println("\n***ADJ*** " + j);
        if (j!=null){
            if (j.isOutsideOfCrossRegion(nodeToBeDeleted.x, nodeToBeDeleted.y, candidate.x, candidate.y)){
                System.out.println("outside");

                if (j.getQuadFromDir(conjugate(start_direction))!=null)
                    adj(nodeToBeDeleted, candidate, newRoot, j.getQuadFromDir(conjugate(start_direction)), quad_direction, start_direction);
                if (j.getQuadFromDir(conjugate(quad_direction))!=null)
                    adj(nodeToBeDeleted, candidate, newRoot, j.getQuadFromDir(conjugate(quad_direction)), quad_direction, start_direction);
            }
            else {
                System.out.println("inside");

                // delete quadrant i (subtree rooted at J) from the parent
                deleteChildFromParent(j, j.direction);

                // reinsert all quadrant i (subtree rooted at J) on tree that was rooted on A
                reinsertQuadrant(newRoot, j);
            }
        }
    }

    private void newRoot(Node nodeToBeDeleted, Node candidate, Node newRoot, Node rootOfQuadrant, int direction) {
        System.out.println("\n***NEW ROOT***\n");
        // process nodes in quadrant i
        // i = direction

        // apply ADJ to the sub-quadrants adjacent to sub-quadrant i
        System.out.println("Root quad: " + rootOfQuadrant);
        Node[] subquadrants = ((direction == 1) | (direction == 3)) ?
                new Node[]{rootOfQuadrant.NW, rootOfQuadrant.SE} : new Node[]{rootOfQuadrant.NE, rootOfQuadrant.SW};
        for (Node subq : subquadrants){
            System.out.println("Subq: " + subq);
            if (subq!=null)
                adj(nodeToBeDeleted, candidate, newRoot, subq, conjugate(subq.direction), direction);
        }

        Node[] subqsToBeReinserted;
        Node[] subqsWhereToInsert;

        // apply NEWROOT to sub-quadrant conjugate(i)
        if (rootOfQuadrant.getQuadFromDir(conjugate(direction)) != null)
            newRoot(nodeToBeDeleted, candidate, newRoot, rootOfQuadrant.getQuadFromDir(conjugate(direction)), direction);

        // This is done until an empty link in direction conjugate(i) is encountered (at this point we are at the candidate)
        // Now, insert the nodes in the sub-quadrants adjacent to sub-quadrant i of the tree rooted at the candidate
        // in the quadrants adjacent to quadrant i of the tree rooted at the node to be deleted.
        else{
            subqsToBeReinserted = getClosedQuadrants(candidate, direction);
            subqsWhereToInsert = getClosedQuadrants(nodeToBeDeleted, direction);

            for (int i=0;i<2;i++)
                reinsertQuadrant(subqsWhereToInsert[i], subqsToBeReinserted[i]);

            // delete B from its parent
            deleteChildFromParent(candidate, conjugate(direction));

            // By definition of "closest," sub-quadrant conjugate(i) of the tree rooted at the candidate is empty
            // Sub-quadrant i of the tree rooted at the candidate replaces sub-quadrant conjugate(i)
            // of the previous father node of the candidate.
            Node sub_quadrant_i = candidate.getQuadFromDir(direction);
            reinsertQuadrant(candidate.parent.getQuadFromDir(conjugate(direction)), sub_quadrant_i);
        }
    }

    public void reinsertQuadrant(Node root, Node j) {
        if (j!=null){
            n_re_insertion++;
            insert(j.x, j.y, j.value);
            reinsertQuadrant(root, j.NE);
            reinsertQuadrant(root, j.NW);
            reinsertQuadrant(root, j.SW);
            reinsertQuadrant(root, j.SE);
        }
    }

    public void deleteChildFromParent(Node child, int direction){
        String quad = dir2quad.get(direction);
        switch (quad) {
            case "NE":
                child.parent.NE = null; break;
            case "NW":
                child.parent.NW = null; break;
            case "SW":
                child.parent.SW = null; break;
            case "SE":
                child.parent.SE = null; break;
        }
    }

    private Node[] getClosedQuadrants(Node j, int direction) {
        // return sub-quadrants close to sub-quadrant direction, inside quadrant of J
        String quad = dir2quad.get(direction);
        if (quad.equals("NE") | quad.equals("SW"))
            return new Node[]{j.NW, j.SE};
        else
            return new Node[]{j.NE, j.SW};
    }

    /***********************************************************************
     *  Property 1 and 2 for Deletion
     ***************************************************************************/

    private int conjugate(int n) {
        return ((n+1)%4)+1;
    }

    private Node findCandidate(Node nodeToBeDeleted) {
        Node[] candidates = new Node[4];
        for (int i=0; i<4; i++){
            Node quad = nodeToBeDeleted.getQuadFromDir(i+1);
            candidates[i] = quad==null? null : findCandidate(quad, conjugate(i+1));
            System.out.println("Candidate from " + dir2quad.get(i+1) + ": " + candidates[i]);
        }
        return chooseCandidate(candidates, nodeToBeDeleted.x, nodeToBeDeleted.y);
    }

    private Node findCandidate(Node node, int direction){
        try {
            while(node.getQuadFromDir(direction) != null) {
                node = node.getQuadFromDir(direction);
            }
            return node;
        } catch (NullPointerException npe){
            return node;
        }
    }

    private Node chooseCandidate(Node[] candidates, int x, int y) {

        // Property 1
        boolean isNECloser = isCloserToAxis(x,y,
                candidates[quad2dir.get("NE")-1],
                candidates[quad2dir.get("SE")-1],
                candidates[quad2dir.get("NW")-1]);
        boolean isNWCloser = isCloserToAxis(x,y,
                candidates[quad2dir.get("NW")-1],
                candidates[quad2dir.get("SW")-1],
                candidates[quad2dir.get("NE")-1]);
        boolean isSWCloser = isCloserToAxis(x,y,
                candidates[quad2dir.get("SW")-1],
                candidates[quad2dir.get("NW")-1],
                candidates[quad2dir.get("SE")-1]);
        boolean isSECloser = isCloserToAxis(x,y,
                candidates[quad2dir.get("SE")-1],
                candidates[quad2dir.get("NE")-1],
                candidates[quad2dir.get("SW")-1]);

        int c=0;
        Node candidate = null;

        if (isNECloser){
            c=+1;
            candidate = candidates[quad2dir.get("NE")-1];
        }
        if (isNWCloser){
            c=+1;
            candidate = candidates[quad2dir.get("NW")-1];
        }
        if (isSWCloser){
            c=+1;
            candidate = candidates[quad2dir.get("SW")-1];
        }
        if (isSECloser){
            c=+1;
            candidate = candidates[quad2dir.get("SE")-1];
        }

        if (c==1)
            return candidate;
        else{
            // Property 2
            int minRegion = Integer.MAX_VALUE;
            int index = 0;
            for(int i=0; i<candidates.length;i++){
                int currRegion = calculateCrosshatchedRegion(x, y, candidates[i]);
                if (currRegion < minRegion){
                    minRegion = currRegion;
                    index = i;
                }
            }
            return candidates[index];
        }
    }

    private boolean isCloserToAxis(int x, int y, Node candidate, Node first, Node second){
        return (Math.abs(x-candidate.x) < Math.abs(x-first.x) & Math.abs(y-candidate.y) < Math.abs(y-second.y));
    }

    private int calculateCrosshatchedRegion(int x, int y, Node B){
        int dx = Math.abs(y-B.y);
        int dy = Math.abs(x-B.x);
        return Lx*dx + Ly*dy - 2*dx*dy;
    }

    private Node replaceNode(Node a, Node b){
        Node ret = new Node(b.x, b.y, b.value, a.direction, a.parent);
        // setting children
        ret.NE = a.NE;
        ret.NW = a.NW;
        ret.SW = a.SW;
        ret.SE = a.SE;
        // changing parent of children
        ret.NE.parent = ret;
        ret.NW.parent = ret;
        ret.SW.parent = ret;
        ret.SE.parent = ret;
        return ret;
    }

    /***********************************************************************
     *  DFS & Search
     ***************************************************************************/

    public void dfs(){
        dfs(root, "root", 0);
    }

    private static void dfs(Node node, String quad, int level){
        if (node!=null){
            System.out.printf("LVL %d  %s  [X=%d, Y=%d]  value=%d\n", level, quad, node.x, node.y, node.value);
            dfs(node.NE, "NE", level+1);
            dfs(node.NW, "NW", level+1);
            dfs(node.SW, "SW", level+1);
            dfs(node.SE, "SE", level+1);
        }
    }

    public Node searchNode(int x, int y, int value){
        return searchNode(root, x, y, value);
    }

    public Node searchNode(Node curr, int x, int y, int value){
        if (curr!=null)
            if (curr.x==x && curr.y==y && curr.value==value)
                return curr;
            else if ( less(x, curr.x) && less(y, curr.y)) return searchNode(curr.SW, x, y, value); // SW
            else if ( less(x, curr.x) && !less(y, curr.y)) return searchNode(curr.NW, x, y, value); // NW
            else if (!less(x, curr.x) && less(y, curr.y)) return searchNode(curr.SE, x, y, value); // SE
            else if (!less(x, curr.x) && !less(y, curr.y)) return searchNode(curr.NE, x, y, value); // NE
        return curr;
    }

    private int getStartDirection(Node root, Node toFind){
        if (searchNode(root.NE, toFind.x, toFind.y, toFind.value)!=null) return 1;
        if (searchNode(root.NW, toFind.x, toFind.y, toFind.value)!=null) return 2;
        if (searchNode(root.SW, toFind.x, toFind.y, toFind.value)!=null) return 3;
        if (searchNode(root.SE, toFind.x, toFind.y, toFind.value)!=null) return 4;
        return -1;
    }

    /***************************************************************************
     *  helper comparison functions
     ***************************************************************************/

    private boolean less(int k1, int k2) { return k1 < k2; }

    /***************************************************************************
     * Testing
     ***************************************************************************/

    public static void main(String[] args) {

        QuadTree st = new QuadTree(50,50);

        st.insert(25, 25, 0); // A

        st.insert(12, 12, 1); // B
        st.insert(37, 12, 2); // C
        st.insert(37, 37, 3); // D
        st.insert(12, 37, 4); // E

        st.insert(32, 15, 5); // F
        st.insert(27, 20, 6); // G
        st.insert(30, 13, 7); // H
        st.insert(35, 22, 8); // I

        st.insert(14, 14, 9); // J
        st.insert(13, 34, 10); // K
        st.insert(33, 35, 11); // L

        st.delete(st.searchNode(25,25,0));
    }
}
