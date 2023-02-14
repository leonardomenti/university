import java.io.File;
import java.util.Random;
import java.io.FileWriter;

public class SplitExperiment {
    public static void main(String[] args) {

        System.out.println("\nNumber of splits");
        try {
            File myObj = new File("split_results.txt");
            //File myObj = new File("split_results2.txt");
            FileWriter myWriter = new FileWriter(myObj.getName());

            for(int i=2;i<101;i++){
                BTree btree = new BTree(i);
                for(int j=0;j<1000000;j++){
                    int x = new Random().nextInt(1000000);
                    btree.insert(x);
                }
                System.out.println("BTree of order " + i*2 + " did "+ btree.getN_split() +" splits");

                myWriter.write(i*2 + ":" + btree.getN_split() + "\n");
            }
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}