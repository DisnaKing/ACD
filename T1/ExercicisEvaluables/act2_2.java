
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class act2_2 {
    public static void main(String[] args) throws IOException {
        
        if (args.length < 2) {
            System.err.println("Uso: java HeadTail <head|tail> [-nX] <fichero>");
            System.exit(1);
        }


        String accion = args[0];
        int n = 10; // por defecto 10 líneas
        String archivo = null;

        if(args.length==2){
            archivo = args[1];
        } else if (args.length ==3 ){
            archivo = args[2];

            String c = String.valueOf(args[1].charAt(2));
            n=Integer.parseInt(c);
        }
        switch (accion) {
                case "tail" -> tail(archivo,n);
                case "head" -> head(archivo,n);
                default -> throw new AssertionError();
        }
    }
    public static void tail(String ruta, int n) throws FileNotFoundException, IOException{
        LinkedList<String> buffer = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                buffer.add(linea);
                if (buffer.size() > n) {
                    buffer.removeFirst();
                }
            }
        }
        for (String l : buffer) {
            System.out.println(l);
        }
    }
    public static void head(String ruta, int n) throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            int contador = 0;
            while ((linea = br.readLine()) != null && contador < n) {
                System.out.println(linea);
                contador++;
            }
        }
    }
}

