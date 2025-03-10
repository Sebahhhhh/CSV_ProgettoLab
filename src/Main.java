import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String fileCSV = "src/Rocchi_Biblioteche.csv";
        String riga;
        String separatoreCSV = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(fileCSV))) {
            while ((riga = br.readLine()) != null) {
                String[] biblioteca = riga.split(separatoreCSV);
                for (String campo : biblioteca) {
                    System.out.print(campo + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}