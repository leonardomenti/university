import java.io.File;
import java.util.Random;
import java.io.FileWriter;

public class HeightExperiment {
    public static void main(String[] args) {

        System.out.println("Relation between B and te height of the tree");
        try {
            File myObj = new File("height_results.txt");
            //File myObj = new File("height_results2.txt");
            FileWriter myWriter = new FileWriter(myObj.getName());

            for(int i=2;i<101;i++){
                BTree btree = new BTree(i);
                for(int j=0;j<1000000;j++){
                    int x = new Random().nextInt(500);
                    btree.insert(x);
                }
                System.out.println("BTree of order " + i*2 + " has height of: " + btree.getHeight());

                myWriter.write(i*2 + ":" + btree.getHeight() + "\n");
            }
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}