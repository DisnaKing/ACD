/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.ieseljust.practicaunitat1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author jasb
 */
public class PracticaUnitat1 {

    String dest;

    private String importarCSV() throws IOException {

        // Ruta CSV
        System.out.println("Introudeix el nom complet de l'arxiu CSV a importar (p.e. dades.csv): ");
        String filename = Utilitats.leerTextoG("");

        // Convertir CSV a Objectes
        List<String>lines=Files.readAllLines(Paths.get(filename));
        List<pilot> pilots = new ArrayList<>();


        for (int i = 1; i < lines.size();i++) {
            String[] parameters = lines.get(i).split(",");
            pilots.add(new pilot(
                    parameters[0],
                    parameters[1],
                    Integer.parseInt(parameters[2]),
                    Integer.parseInt(parameters[3]),
                    Integer.parseInt(parameters[4]),
                    Integer.parseInt(parameters[5]),
                    Integer.parseInt(parameters[6]),
                    Integer.parseInt(parameters[7]),
                    Integer.parseInt(parameters[8]),
                    Integer.parseInt(parameters[9]),
                    Integer.parseInt(parameters[10]),
                    Integer.parseInt(parameters[11]),
                    Integer.parseInt(parameters[12]),
                    Integer.parseInt(parameters[13])));
        }

        // Escriure Objectes a arxiu.obj
        dest = "src/main/files/pilots.obj";
        File f = new File(dest);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))){
            for (pilot p : pilots){
                oos.writeObject(p);
            }
        }

        // Resultat
        System.out.println("\nLes dades han sigut importades correctament des de l'arxiu " + filename + " i emmagatzemades a l'arxiu " + dest + "\n");
        return dest;
    }

    private void exportar(String format) {


        switch (format) {
            case "XML":
                String filenameXML = Utilitats.leerTextoG("Digues el nom complet de l'arxiu a generar (p.e. dades.xml): ");
                 {
                    try {
                        writingXML(filenameXML);
                        System.out.println("\nLes dades dels jugadors han sigut exportades a l'arxiu " + filenameXML + "\n");

                    } catch (IOException ex) {
                        Logger.getLogger(PracticaUnitat1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(PracticaUnitat1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(PracticaUnitat1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(PracticaUnitat1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TransformerConfigurationException ex) {
                        Logger.getLogger(PracticaUnitat1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            //TO-DO
            case "JSON":
                String filenameJSON = Utilitats.leerTextoG("Digues el nom complet de l'arxiu a generar (p.e. dades.json): ");
                 {
                    try {
                        writingJSON(filenameJSON);
                        System.out.println("\nLes dades dels jugadors han sigut exportades a l'arxiu " + filenameJSON + "\n");
                    } catch (IOException ex) {
                        Logger.getLogger(PracticaUnitat1.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(PracticaUnitat1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            default:
                System.out.println("Format no existeix\n");
                break;

        }
    }

    public void writingXML(String filename) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException, TransformerConfigurationException {

        try {
            // Reading from file within instances of objects in bytes
            ObjectInputStream f;
            if (dest != null) {
                f = new ObjectInputStream(new FileInputStream(dest));
            } else {
                f = new ObjectInputStream(new FileInputStream("/src/main/files/pilots.obj"));
            }

            // Create an empty document
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            //Insert the root element: ""
            Element root = doc.createElement("pilots");

            doc.appendChild(root);

            // Bucle per a llegir els objectes de l'arxiu
            while (true) {
                try {
                    pilot p = (pilot) f.readObject();

                    // Crear element "pilot"
                    Element pilotElement = doc.createElement("pilot");
                    pilotElement.setAttribute("type", p.type);
                    pilotElement.setAttribute("name", p.name);
                    pilotElement.setAttribute("SpeedGround", Integer.toString(p.SpeedGround));
                    pilotElement.setAttribute("SpeedWater", Integer.toString(p.SpeedWater));
                    pilotElement.setAttribute("SpeedAir", Integer.toString(p.SpeedAir));
                    pilotElement.setAttribute("SpeedAntiGravity", Integer.toString(p.SpeedAntiGravity));
                    pilotElement.setAttribute("Acceleration", Integer.toString(p.Acceleration));
                    pilotElement.setAttribute("Weight", Integer.toString(p.Weight));
                    pilotElement.setAttribute("HandlingGround", Integer.toString(p.HandlingGround));
                    pilotElement.setAttribute("HandlingWater", Integer.toString(p.HandlingWater));
                    pilotElement.setAttribute("HandlingAir", Integer.toString(p.HandlingAir));
                    pilotElement.setAttribute("HandlingAntiGravity", Integer.toString(p.HandlingAntiGravity));
                    pilotElement.setAttribute("Traction", Integer.toString(p.Traction));
                    pilotElement.setAttribute("MiniTurbo", Integer.toString(p.MiniTurbo));

                    root.appendChild(pilotElement);

                } catch (EOFException eof) {
                    // Hem arribat al final del fitxer
                    break;
                }
            }



            /* Transformar el DOM (doc) a fitxer de text */
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(filename));

            trans.transform(source, result);

        } catch (TransformerException ex) {
            Logger.getLogger(PracticaUnitat1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void writingJSON(String filename) throws FileNotFoundException, IOException, ClassNotFoundException {

        // Reading from file within instances of objects in bytes
        ObjectInputStream f;
        if (dest != null) {
            f = new ObjectInputStream(new FileInputStream(dest));
        } else {
            f = new ObjectInputStream(new FileInputStream("/src/main/files/pilots.obj"));
        }

        //compondre l'estructura del JSON amb JSONObject, JSONArray, etc. amb els put(clau,valor) corresponents
        JSONObject myJson = new JSONObject();

        //cal escriure el JSONObject principal a un arxiu .json
        try {
            FileWriter file = new FileWriter(filename);
            file.write(myJson.toString(4)); // 4 són els espais d'indentació
            file.close();

        } catch (IOException e) {
            System.out.println("Error, no es pot crear el fitxer " + filename);
        }
    }

    private void mostrar(String format) throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream f;
        if (dest != null) {
            f = new ObjectInputStream(new FileInputStream(dest));
        } else {
            f = new ObjectInputStream(new FileInputStream("/src/main/files/pilots.obj"));
        }

        switch (format) {
            case "XML":
                //mostrar per eixida estandar el contingut XML final i, en el cas dels objectes llegits de l'arxiu, cridant al metode toXML de la classe que els emmagatzema
                mostrarXML();
                break;
            //TO-DO

            case "JSON":
                //mostrar per eixida estandar el contingut JSON final i, en el cas dels objectes llegits de l'arxiu, cridant al metode toXML de la classe que els emmagatzema
                break;
            default:
                System.out.println("Format no existeix\n");
                break;

        }
    }
    private void mostrarXML(){
        ObjectInputStream f = null;
        try{
            // Llegir de l'arxiu d'objectes
            if (dest!=null){
                f = new ObjectInputStream(new FileInputStream(dest));
            }else{
                f = new ObjectInputStream(new FileInputStream("/src/main/files/pilots.obj"));
            }

            // Crear un document buit
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            // Montar l'element arrel: "pilots"
            Element root = doc.createElement("pilots");

            // Insertar l'arrel al document
            doc.appendChild(root);

            // Bucle per a llegir els objectes de l'arxiu
            while (true) {
                try {
                    pilot p = (pilot) f.readObject();

                    // Crear element "pilot"
                    Element pilotElement = doc.createElement("pilot");
                    pilotElement.setAttribute("type", p.type);
                    pilotElement.setAttribute("name", p.name);
                    pilotElement.setAttribute("SpeedGround", Integer.toString(p.SpeedGround));
                    pilotElement.setAttribute("SpeedWater", Integer.toString(p.SpeedWater));
                    pilotElement.setAttribute("SpeedAir", Integer.toString(p.SpeedAir));
                    pilotElement.setAttribute("SpeedAntiGravity", Integer.toString(p.SpeedAntiGravity));
                    pilotElement.setAttribute("Acceleration", Integer.toString(p.Acceleration));
                    pilotElement.setAttribute("Weight", Integer.toString(p.Weight));
                    pilotElement.setAttribute("HandlingGround", Integer.toString(p.HandlingGround));
                    pilotElement.setAttribute("HandlingWater", Integer.toString(p.HandlingWater));
                    pilotElement.setAttribute("HandlingAir", Integer.toString(p.HandlingAir));
                    pilotElement.setAttribute("HandlingAntiGravity", Integer.toString(p.HandlingAntiGravity));
                    pilotElement.setAttribute("Traction", Integer.toString(p.Traction));
                    pilotElement.setAttribute("MiniTurbo", Integer.toString(p.MiniTurbo));

                    root.appendChild(pilotElement);

                } catch (EOFException eof) {
                    // Hem arribat al final del fitxer
                    break;
                }
            }

            // Escriure el document XML en un fitxer o consola
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(doc), new StreamResult(System.out));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al mostrar XML: " + e.getMessage());
        } finally {
            if (f != null) {
                try { f.close(); } catch (IOException ignore) {}
            }
        }
    }

    private int submenu(String prefix) {

        int select = -1;

        do {

            System.out.println("\n\t\t- - - -  - NOM PROJECTE - - - - -  \n");
            System.out.println("\t\t 1. " + prefix + " XML");
            System.out.println("\t\t 2. " + prefix + " JSON");
            System.out.println("\t\t 3. Tornar al menú principal");

            select = Utilitats.leerEnteroC("\n\tSelecciona una opció (1-3): ");

            switch (select) {
                case 1:
                    return select;
                case 2:
                    return select;
                case 3:
                    return select;
                default:
                    System.out.println("Opció seleccionada incorrecta.");
                    break;
            }

        } while (select != 3);

        return select;
    }

    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {

        PracticaUnitat1 app = new PracticaUnitat1();

        int opcio;
        int select;

        do {

            System.out.println("\n\t\t- - - - NOM PROJECTE - - - - \n");
            System.out.println("\t\t 1. Importar CSV");
            System.out.println("\t\t 2. Exportar a ...");
            System.out.println("\t\t 3. Mostrar dades ... ");
            System.out.println("\t\t 4. Eixir ");

            opcio = Utilitats.leerEnteroC("\n\tSelect an option (1-4): ");

            switch (opcio) {
                case 1:
                    app.dest = app.importarCSV();
                    break;
                case 2:
                    select = app.submenu("Exportar a");
                    switch (select) {
                        case 1:
                            app.exportar("XML");
                            break;
                        case 2:
                            app.exportar("JSON");
                            break;
                        default:
                            System.out.println("Opció seleccionada incorrecta.");
                            break;
                    }
                    break;
                case 3:
                    select = app.submenu("Mostrar dades en format");
                    switch (select) {
                        case 1:
                            app.mostrar("XML");
                            break;
                        case 2:
                            app.mostrar("JSON");
                            break;
                        default:
                            System.out.println("Opció seleccionada incorrecta.");
                            break;
                    }
                    break;

                case 4:
                    break;
                default:
                    System.out.println("Opció seleccionada incorrecta.");
                    break;
            }

        } while (opcio != 4);

    }
}
