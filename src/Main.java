import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileCSV = "src/Rocchi_Biblioteche.csv";
        String fileCSVModificato = "src/Rocchi_Biblioteche_modified.csv";
        String separatoreCSV = ",";
        int lunghezzaMassimaRecord = 0;
        Random casuale = new Random();

        BufferedReader lettore = null;
        BufferedWriter scrittore = null;

        while (true) {
            System.out.println("Scegli un'operazione:");
            System.out.println("1. Aggiungere campi 'miovalore' e 'deleted'");
            System.out.println("2. Contare il numero di campi per record");
            System.out.println("3. Calcolare la lunghezza massima dei record e dei campi");
            System.out.println("4. Rendere fissa la dimensione dei record");
            System.out.println("5. Aggiungere un record in coda");
            System.out.println("6. Visualizzare tre campi significativi");
            System.out.println("7. Ricercare un record per campo chiave");
            System.out.println("8. Modificare un record");
            System.out.println("9. Cancellare logicamente un record");
            System.out.println("10. Creare un file HTML con i dati del CSV");
            System.out.println("0. Uscire");

            int scelta = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (scelta) {
                    case 1:
                        lettore = new BufferedReader(new FileReader(fileCSV));
                        scrittore = new BufferedWriter(new FileWriter(fileCSVModificato));
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
                        System.out.println("Campi aggiunti con successo.");
                        break;

                    case 2:
                        lettore = new BufferedReader(new FileReader(fileCSVModificato));
                        while ((riga = lettore.readLine()) != null) {
                            String[] campi = riga.split(separatoreCSV);
                            System.out.println("Numero di campi: " + campi.length);
                        }
                        break;

                    case 3:
                        int[] massimeLunghezzeCampi = null;
                        lettore = new BufferedReader(new FileReader(fileCSVModificato));
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
                        break;

                    case 4:
                        lettore = new BufferedReader(new FileReader(fileCSVModificato));
                        scrittore = new BufferedWriter(new FileWriter(fileCSVModificato));
                        while ((riga = lettore.readLine()) != null) {
                            int spaziDaAggiungere = lunghezzaMassimaRecord - riga.length();
                            StringBuilder rigaFissa = new StringBuilder(riga);
                            for (int i = 0; i < spaziDaAggiungere; i++) {
                                rigaFissa.append(" ");
                            }
                            scrittore.write(rigaFissa.toString());
                            scrittore.newLine();
                        }
                        System.out.println("Dimensione dei record resa fissa.");
                        break;

                    case 5:
                        scrittore = new BufferedWriter(new FileWriter(fileCSVModificato, true));
                        System.out.println("Inserisci il nuovo record (campi separati da '" + separatoreCSV + "'):");
                        String nuovoRecord = scanner.nextLine();
                        scrittore.write(nuovoRecord);
                        scrittore.newLine();
                        System.out.println("Record aggiunto con successo.");
                        break;

                    case 6:
                        lettore = new BufferedReader(new FileReader(fileCSVModificato));
                        while ((riga = lettore.readLine()) != null) {
                            String[] campi = riga.split(separatoreCSV);
                            System.out.println("Campo 1: " + campi[0] + ", Campo 2: " + campi[1] + ", Campo 3: " + campi[2]);
                        }
                        break;

                    case 7:
                        lettore = new BufferedReader(new FileReader(fileCSVModificato));
                        System.out.println("Inserisci il valore del campo chiave da cercare:");
                        String chiave = scanner.nextLine();
                        while ((riga = lettore.readLine()) != null) {
                            if (riga.contains(chiave)) {
                                System.out.println("Record trovato: " + riga);
                            }
                        }
                        break;

                    case 8:
                        lettore = new BufferedReader(new FileReader(fileCSVModificato));
                        scrittore = new BufferedWriter(new FileWriter(fileCSVModificato + ".tmp"));
                        System.out.println("Inserisci il valore del campo chiave del record da modificare:");
                        String chiaveModifica = scanner.nextLine();
                        System.out.println("Inserisci il nuovo record (campi separati da '" + separatoreCSV + "'):");
                        String nuovoValore = scanner.nextLine();
                        while ((riga = lettore.readLine()) != null) {
                            if (riga.contains(chiaveModifica)) {
                                scrittore.write(nuovoValore);
                            } else {
                                scrittore.write(riga);
                            }
                            scrittore.newLine();
                        }
                        new File(fileCSVModificato + ".tmp").renameTo(new File(fileCSVModificato));
                        System.out.println("Record modificato con successo.");
                        break;

                    case 9:
                        lettore = new BufferedReader(new FileReader(fileCSVModificato));
                        scrittore = new BufferedWriter(new FileWriter(fileCSVModificato + ".tmp"));
                        System.out.println("Inserisci il valore del campo chiave del record da cancellare:");
                        String chiaveCancellazione = scanner.nextLine();
                        while ((riga = lettore.readLine()) != null) {
                            if (riga.contains(chiaveCancellazione)) {
                                String[] campi = riga.split(separatoreCSV);
                                campi[campi.length - 1] = "true";
                                scrittore.write(String.join(separatoreCSV, campi));
                            } else {
                                scrittore.write(riga);
                            }
                            scrittore.newLine();
                        }
                        new File(fileCSVModificato + ".tmp").renameTo(new File(fileCSVModificato));
                        System.out.println("Record cancellato logicamente con successo.");
                        break;

                    case 10:
                        String fileHTML = "src/biblioteche.html";
                        try {
                            lettore = new BufferedReader(new FileReader(fileCSVModificato));
                            scrittore = new BufferedWriter(new FileWriter(fileHTML));

                            scrittore.write("<!DOCTYPE html>\n");
                            scrittore.write("<html>\n");
                            scrittore.write("<head>\n<title>Elenco Biblioteche</title>\n</head>\n");
                            scrittore.write("<body>\n");
                            scrittore.write("<h1>Elenco delle Biblioteche</h1>\n");
                            scrittore.write("<table border='1'>\n");

                            boolean primaRigaHTML = true;
                            while ((riga = lettore.readLine()) != null) {
                                String[] campi = riga.split(separatoreCSV);
                                scrittore.write("<tr>\n");
                                for (String campo : campi) {
                                    if (primaRigaHTML) {
                                        scrittore.write("<th>" + campo + "</th>\n");
                                    } else {
                                        scrittore.write("<td>" + campo + "</td>\n");
                                    }
                                }
                                scrittore.write("</tr>\n");
                                primaRigaHTML = false;
                            }

                            scrittore.write("</table>\n");
                            scrittore.write("</body>\n");
                            scrittore.write("</html>\n");
                            System.out.println("File HTML creato con successo: " + fileHTML);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 0:
                        System.out.println("Uscita dal programma.");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (lettore != null) lettore.close();
                    if (scrittore != null) scrittore.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}