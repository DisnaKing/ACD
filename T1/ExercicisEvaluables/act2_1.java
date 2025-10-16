import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class act2_1 {
    @SuppressWarnings("ObjectEqualsNull")
    public static void main(String[] args) throws IOException {
        if (args.length < 2){
            System.out.println("Argumentos necesarios 2, 1: fichero, 2: vocal");
        } else{
            System.out.println(args[1]);
            File f = new File(args[0]);
            char vocal = args[1].charAt(0);
            try (BufferedReader bf = new BufferedReader(new FileReader(f))) {
                int cantidad=0;
                String line;
                while ((line = bf.readLine()) != null) {
                    System.out.println(line);
                    for (int i=0;i<line.length();i++){
                        if (line.charAt(i) == vocal){
                            cantidad++;
                        }
                    }
                }   System.out.printf("Hay "+cantidad+" "+vocal);
                // close
            }
        }
    }
}

