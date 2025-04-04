import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        // Filess
        String fileCSV = "src/Rocchi_Biblioteche.csv";
        String fileCSVModificato = "src/Rocchi_Biblioteche_modified.csv";

        // Separatore che c'è nei file
        String separatoreCSV = ",";

        Random casuale = new Random();

        // 1  Aggiunta dei campi "miovalore" e "deleted"
        try (BufferedReader lettore = new BufferedReader(new FileReader(fileCSV));
             BufferedWriter scrittore = new BufferedWriter(new FileWriter(fileCSVModificato))) {

            String riga;
            boolean primaRiga = true;

            while ((riga = lettore.readLine()) != null) {
                if (primaRiga) {
                    scrittore.write(riga + separatoreCSV + "miovalore" + separatoreCSV + "deleted");
                    primaRiga = false;
                } else {
                    int miovalore = 10 + casuale.nextInt(11);
                    scrittore.write(riga + separatoreCSV + miovalore + separatoreCSV + "false");
                }
                scrittore.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2 Conteggio del numero di campi per ogni record
        try (BufferedReader lettore = new BufferedReader(new FileReader(fileCSVModificato))) {
            String riga;
            while ((riga = lettore.readLine()) != null) {
                String[] campi = riga.split(separatoreCSV);
                System.out.println("Numero di campi: " + campi.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3 Calcolo della lunghezza massima dei record e di ogni campo
        int lunghezzaMassimaRecord = 0;
        int[] massimeLunghezzeCampi = null;
        try (BufferedReader lettore = new BufferedReader(new FileReader(fileCSVModificato))) {
            String riga;
            while ((riga = lettore.readLine()) != null) {
                String[] campi = riga.split(separatoreCSV);

                if (massimeLunghezzeCampi == null) {
                    massimeLunghezzeCampi = new int[campi.length];
                }

                lunghezzaMassimaRecord = Math.max(lunghezzaMassimaRecord, riga.length());

                for (int i = 0; i < campi.length; i++) {
                    massimeLunghezzeCampi[i] = Math.max(massimeLunghezzeCampi[i], campi[i].length());
                }
            }
            System.out.println("Lunghezza massima dei record: " + lunghezzaMassimaRecord);
            System.out.println("Lunghezze massime dei campi: " + Arrays.toString(massimeLunghezzeCampi));
        } catch (IOException e) {
            e.printStackTrace();
        }

             // 4 Inserimento di spazi per rendere fissa la dimensione di tutti i record

            try (BufferedReader lettore = new BufferedReader(new FileReader(fileCSVModificato));
                 BufferedWriter scrittore = new BufferedWriter(new FileWriter("src/Rocchi_Biblioteche_fixed.csv"))) {
                String riga;
                while ((riga = lettore.readLine()) != null) {
                    int spaziDaAggiungere = lunghezzaMassimaRecord - riga.length();
                    StringBuilder rigaFissa = new StringBuilder(riga);
                    for (int i = 0; i < spaziDaAggiungere; i++) {
                        rigaFissa.append(" ");
                    }
                    scrittore.write(rigaFissa.toString());
                    scrittore.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 5 Aggiunta di un record in coda
            try (BufferedWriter scrittore = new BufferedWriter(new FileWriter("src/Rocchi_Biblioteche_fixed.csv", true))) {
                String nuovoRecord = "99999,99,99,IT-NEW001,Nuova Biblioteca,Specializzata,Nuova Fondazione,Nuova Categoria,20,false";
                scrittore.write(nuovoRecord);
                scrittore.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 6 Visualizzazione di tre campi significativi a scelta

            try (BufferedReader lettore = new BufferedReader(new FileReader(fileCSVModificato))) {
                String riga;
                while ((riga = lettore.readLine()) != null) {
                    String[] campi = riga.split(separatoreCSV);
                    // 3 campi a caso 1 4 e 5
                    System.out.println("Campo 1: " + campi[0] + ", Campo 2: " + campi[3] + ", Campo 4: " + campi[4]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(" ");
        // 7 Ricerca di un record per campo chiave univoco
        try (BufferedReader lettore = new BufferedReader(new FileReader(fileCSVModificato))) {
            String riga;
            String chiaveRicerca = "IT-AL0001"; // Sostituire con la chiave da ricercare
            boolean trovato = false;
            while ((riga = lettore.readLine()) != null) {
                String[] campi = riga.split(separatoreCSV);
                if (campi[3].equals(chiaveRicerca)) { // Supponendo che il campo chiave univoco sia il quarto campo (indice 3)
                    System.out.println("Record trovato: " + riga);
                    trovato = true;
                    break;
                }
            }
            if (!trovato) {
                System.out.println("Record non trovato per la chiave: " + chiaveRicerca);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
    }
}
