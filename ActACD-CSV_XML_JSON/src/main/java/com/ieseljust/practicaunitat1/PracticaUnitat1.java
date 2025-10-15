/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.ieseljust.practicaunitat1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;
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

        //TO-DO
        //Demanar el nom/ruta de l'arxiu CSV i crear els objectes de la classe que emmagatzema els registres
        //Crear un arxiu orientat a bytes que emmagatzema els objectes creats en el pas anterior (arxiu.obj)
        //Es retornarà el nom/ruta de l'arxiu .obj
        return "";
    }

    private void exportar(String format) {

        //TO-DO
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
                f = new ObjectInputStream(new FileInputStream("arxiu.obj"));
            }

            // Create an empty document
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            //Insert the root element: ""
            

            //bucle per a recorrer cada objecte "player" de l'arxiu i montar els elements del DOM associats
            

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
            f = new ObjectInputStream(new FileInputStream("arxiu.obj"));
        }

        //compondre l'estructura del JSON amb JSONObject, JSONArray, etc amb els put(clau,valor) corresponents
        JSONObject myJson = new JSONObject();

        //escriure el JSONObject principal a un arxiu .json
        try {
            FileWriter file = new FileWriter(filename);
            file.write(myJson.toString(4)); // 4 són els espais d'indentació
            file.close();

        } catch (IOException e) {
            System.out.println("Error, no es pot crear el fitxer " + filename);
        }
    }

    private void mostrar(String format) throws FileNotFoundException, IOException, ClassNotFoundException {

        //TO-DO
        ObjectInputStream f;
        if (dest != null) {
            f = new ObjectInputStream(new FileInputStream(dest));
        } else {
            f = new ObjectInputStream(new FileInputStream("arxiu.obj"));
        }

        switch (format) {
            case "XML":
                //mostrar per eixida estandar el contingut XML final i, en el cas dels objectes llegits de l'arxiu, cridant al metode toXML de la classe que els emmagatzema
                break;
            case "JSON":
                //mostrar per eixida estandar el contingut JSON final i, en el cas dels objectes llegits de l'arxiu, cridant al metode toXML de la classe que els emmagatzema
                break;
            default:
                System.out.println("Format no existeix\n");
                break;

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
