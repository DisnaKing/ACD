import java.io.File;

public class act1 {
    public static void main(String[] args) {
        String op = args[0];
        String ruta = args[1];
        File f = new File(ruta);
        File[] arch = f.listFiles();
        if (f.exists()) {
            if (f.isDirectory()) {
                switch (op) {
                    case "list" -> list(f.list());
                    case "columns" -> listaColumnas(f.list());
                    case "table" -> table(arch);
                    default -> throw new AssertionError();
                }
            }

        } else {
            System.out.println("El fichero o ruta no existe");
        }
    }

    public static void list(String[]filenames){
        for (String archivo : filenames) {
            System.out.println("\t" + archivo);
        }
    }

    public static void listaColumnas(String[] filenames) {
        int MAX_FILES_BY_COLUMN = 4;
        int columnas = (filenames.length / MAX_FILES_BY_COLUMN) + 1;
        String[][] salida = new String[MAX_FILES_BY_COLUMN][columnas];
        // Llenar la matriz 'salida' con los nombres de archivo
        for (int i = 0; i < filenames.length; i++) {
            salida[i % MAX_FILES_BY_COLUMN][i / MAX_FILES_BY_COLUMN] = filenames[i];
        }
        // Encontrar el nombre de archivo más largo para establecer el ancho de columna
        int maxLength = 0;
        for (String filename : filenames) {
            if (filename.length() > maxLength) {
                maxLength = filename.length();
            }
        }
        // Ajustar el formato para que cada columna tenga el mismo ancho
        String format = "%-" + (maxLength + 2) + "s";
        // Bucle para mostrar salida con formato simétrico
        for (int i = 0; i < MAX_FILES_BY_COLUMN; i++) {
            for (int j = 0; j < columnas; j++) {
                if (salida[i][j] != null) {
                    System.out.printf(format, salida[i][j]); // printf para aplicar el formato
                }
            }
            System.out.println();
        }
    }

    public static void table(File[] files) {
        if (files == null) return;

        for (File file : files) {
            String sb;
            // D/F
            sb = (file.isDirectory() ? "D" : "F");

            // R
            sb += (file.canRead() ? "R" : "-");

            // W
            sb += (file.canWrite() ? "W" : "-");

            // H
            sb += (file.isHidden() ? "H" : "-");

            // Nom i tamany
            String name = file.getName();
            long size = file.length();

            System.out.printf("%-5s %-20s %10d bytes%n", sb, name, size);
        }
    }
}
