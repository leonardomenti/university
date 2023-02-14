import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimeExperiment {
    public static void main(String[] args) {

        System.out.println("\nCost of long sequences of updates");
        try {
            File myObj = new File("time_results.txt");
            //File myObj = new File("time_results2.txt");
            FileWriter myWriter = new FileWriter(myObj.getName());

            int[] values = new int[1000000];
            int[] sequence = new int[1000000];

            for(int j=0;j<1000000;j++)
                values[j] = new Random().nextInt(500);

            for(int j=0;j<1000000;j++)
                sequence[j] = j;

            for(int i=2;i<101;i++){
                BTree btree = new BTree(i);
                Instant start = Instant.now();
                for(int j=0;j<1000000;j++)
                    btree.insert(values[j]);

                Instant end = Instant.now();
                Duration timeElapsed = Duration.between(start, end);
                System.out.println("BTree of order " + i*2 + " took: "+ timeElapsed.toMillis() +" milliseconds");

                myWriter.write(i*2 + ":" + timeElapsed.toMillis() + "\n");
            }
            myWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}