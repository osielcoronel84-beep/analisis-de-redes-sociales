import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Fila> datos = new ArrayList<>();

        Map<String, Integer> mesesIndex = new HashMap<>();
        String[] meses = {"ENERO","FEBRERO","MARZO","ABRIL","MAYO","JUNIO","JULIO","AGOSTO","SEPTIEMBRE","OCTUBRE","NOVIEMBRE","DICIEMBRE"};
        for (int i = 0; i < meses.length; i++) {
            mesesIndex.put(meses[i], i);
        }

        try (BufferedReader br = new BufferedReader(new FileReader("datos_redes_sociales.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] valores = line.split(",");
                Fila f = new Fila();
                f.red = valores[0];
                f.concepto = valores[1];

                f.meses = new int[12];
                for (int i = 0; i < 12; i++) {
                    try {
                        f.meses[i] = Integer.parseInt(valores[i + 3].replaceAll("[^0-9]", ""));
                    } catch (Exception e) {
                        f.meses[i] = 0;
                    }
                }
                datos.add(f);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV");
            e.printStackTrace();
            return;
        }
        for (Fila f : datos) {
            if (f.red.equalsIgnoreCase("TWITTER") && f.concepto.startsWith("SEGUIDORES")) {
                int enero = f.meses[0];
                int junio = f.meses[5];
                int diferencia = junio - enero;
                System.out.println("Diferencia de seguidores en Twitter (Enero-Junio): " + diferencia);
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese mes inicial (ej: ENERO): ");
        String mesInicio = sc.nextLine().toUpperCase();
        System.out.print("Ingrese mes final (ej: JUNIO): ");
        String mesFin = sc.nextLine().toUpperCase();

        int idxInicio = mesesIndex.get(mesInicio);
        int idxFin = mesesIndex.get(mesFin);

        for (Fila f : datos) {
            if (f.red.equalsIgnoreCase("YOUTUBE") && f.concepto.equalsIgnoreCase("VISUALIZACIONES")) {
                int diff = f.meses[idxFin] - f.meses[idxInicio];
                System.out.println("Diferencia de visualizaciones YouTube (" + mesInicio + "-" + mesFin + "): " + diff);
            }
        }
        for (Fila f : datos) {
            if ((f.red.equalsIgnoreCase("TWITTER") && f.concepto.contains("CRECIMIENTO DE FOLLOWERS")) ||
                    (f.red.equalsIgnoreCase("FACEBOOK") && f.concepto.contains("CRECIMIENTO (seguidores)"))) {

                int suma = 0;
                for (int i = 0; i < 6; i++) {
                    suma += f.meses[i];
                }
                double promedio = suma / 6.0;
                System.out.println("Promedio de crecimiento (" + f.red + "): " + promedio);
            }
        }
        for (Fila f : datos) {
            if (f.concepto.toUpperCase().contains("ME GUSTA")) {
                int suma = 0;
                for (int i = 0; i < 6; i++) {
                    suma += f.meses[i];
                }
                double promedio = suma / 6.0;
                System.out.println("Promedio de 'Me gusta' (" + f.red + "): " + promedio);
            }
        }
    }
}

class Fila {
    String red;
    String concepto;
    int[] meses;
}