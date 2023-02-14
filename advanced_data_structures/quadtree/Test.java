import java.io.File;
import java.io.FileWriter;

public class Test {
    public static void main(String[] args){

        int N = 25;
        File myObj = new File("results.txt");

        for (int j=0;j<1000;j++){
            try{
                QuadTree qt = new QuadTree(N, N);
                QuadTree qt2 = new QuadTree(N,N);

                int[][] nodes = new int[N][3];

                for (int i = 0; i < N; i++) {
                    Integer x = (int)(Math.random() * 25 + 1);
                    Integer y = (int)(Math.random() * 25 + 1);
                    nodes[i] = new int[]{x, y, i};
                }

                for (int[] node: nodes){
                    qt.insert(node[0], node[1], node[2]);
                    qt2.insert(node[0], node[1], node[2]);
                }

                qt.dfs();

                // choose one node to delete
                int[] nodeToBeDeleted = nodes[(int) (100 * Math.random())];
                int x =  nodeToBeDeleted[0];
                int y = nodeToBeDeleted[1];
                int v = nodeToBeDeleted[2];
                System.out.println("x: " + x + " y: " + y + "value: " + v);

                qt.delete(qt.searchNode(x,y,v));

                QuadTree.Node node = qt2.searchNode(x,y,v);
                qt2.deleteChildFromParent(node, node.direction);
                qt2.reinsertQuadrant(qt2.root, node.SE);
                qt2.reinsertQuadrant(qt2.root, node.SW);
                qt2.reinsertQuadrant(qt2.root, node.NE);
                qt2.reinsertQuadrant(qt2.root, node.NW);

                System.out.println("Number of re-insertion in qt1: " + qt.n_re_insertion);
                System.out.println("Number of re-insertion in qt2: " + qt2.n_re_insertion);

                FileWriter myWriter = new FileWriter("results.txt", true);
                myWriter.write(qt.n_re_insertion + ";" + qt2.n_re_insertion + "\n");
                myWriter.close();
            } catch (Exception e){
                continue;
            }
        }
    }
}
