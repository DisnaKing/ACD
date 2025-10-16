/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.ieseljust.modulsxml;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jasb
 */
public class ModulsXML {

    /**
     * Write and Read modules to/from file
     */
    // Arrays with source data
    static String[] moduls={"Programació de serveis i processos", "Desenvolupament d'interfícies", "Programació Multimèdia i dispositiud mòbils", "Sistemes de Gestió Empresarial", "Itinerari per a l'Ocupabilitat II"};
    static int[]  hores={2, 5, 4, 4, 3};
    static double[] notes={9.0, 8.0, 7.34, 8.2, 7.4};

    public void readwrite(String obj,String xml) throws IOException, ClassNotFoundException, TransformerConfigurationException, ParserConfigurationException, SAXException {
        try {
            File file = new File(obj);
            if (!file.exists() || file.length() == 0) {
                System.out.println("El archivo no existe o está vacío.");
                return;
            }

            // Reading from file within instances of objects in bytes
            ObjectInputStream f = new ObjectInputStream(new FileInputStream(obj));

            // Create an empty document
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            //Insert the root element: "curso"
            Element root = doc.createElement("curs");
            doc.appendChild(root);

            //bucle
            for (int i = 0; i < moduls.length; i++) {
                Modul m = (Modul) f.readObject();
                Element modul = doc.createElement("modul");

                Element nom = doc.createElement("nom");
                nom.appendChild(doc.createTextNode(m.getModul()));
                modul.appendChild(nom);

                Element hours = doc.createElement("hores");
                hours.appendChild(doc.createTextNode(Integer.toString(m.getHores())));
                modul.appendChild(hours);

                Element nota = doc.createElement("qualificacio");
                nota.appendChild(doc.createTextNode(Double.toString(m.getNota())));
                modul.appendChild(nota);

                root.appendChild(modul);

            }//fi bucle

            f.close();

            /* transformar el DOM (doc) a fitxer de text */
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(xml.substring(0, xml.lastIndexOf(".")) + ".xml"));

            trans.transform(source, result);
            System.out.println("Escrit correctament");

        } catch (TransformerException ex) {
            Logger.getLogger(ModulsXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void EscriuObjecte(String nom) throws IOException {

        //destination file
        ObjectOutputStream f = new ObjectOutputStream(new FileOutputStream(nom));

        Modul m; // Single object

        // loop along the arrays
        for (int i = 0; i < moduls.length; i++) {
            m = new Modul(moduls[i], hores[i], notes[i]);
            f.writeObject(m);
        }
        // close the file
        f.close();
    }

    public void LligObjecte(String nom) throws IOException, ClassNotFoundException {

        // input file
        ObjectInputStream f = new ObjectInputStream(new FileInputStream(nom));

        Modul m;
        // we don't know how many objects exists in the file.
        try {
            while (true) { // forever

                m = (Modul) f.readObject();

                // show the module
                System.out.println("Modul: " + m.getModul());
                System.out.println("Hores: " + m.getHores());
                System.out.println("Nota: " + m.getNota());
                System.out.println();

            }
        } catch (EOFException ex) {
            f.close();
        }
    }

    public static void writingXML(String filename) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException, TransformerConfigurationException {

        try {
            // Reading from file within instances of objects in bytes
            ObjectInputStream f = new ObjectInputStream(new FileInputStream(filename));

            // Create an empty document
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

            //Insert the root element: "curso"
            Element root = doc.createElement("curs");
            doc.appendChild(root);

            //bucle
            for (int i = 0; i < moduls.length; i++) {
                Modul m = (Modul) f.readObject();
                Element modul = doc.createElement("modul");

                Element nom = doc.createElement("nom");
                nom.appendChild(doc.createTextNode(m.getModul()));
                modul.appendChild(nom);

                Element hours = doc.createElement("hores");
                hours.appendChild(doc.createTextNode(Integer.toString(m.getHores())));
                modul.appendChild(hours);

                Element nota = doc.createElement("qualificacio");
                nota.appendChild(doc.createTextNode(Double.toString(m.getNota())));
                modul.appendChild(nota);

                root.appendChild(modul);

            }//fi bucle

            f.close();

            /* transformar el DOM (doc) a fitxer de text */
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream(filename.substring(0, filename.lastIndexOf(".")) + ".xml"));

            trans.transform(source, result);

        } catch (TransformerException ex) {
            Logger.getLogger(ModulsXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Document OpenXML(String name) throws IOException, SAXException, ParserConfigurationException, FileNotFoundException {

        // Create an instance of DocumentBuilderFactory
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        // Using the DocumentBuilderFactory instance we create a DocumentBuilder
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        //And we use DocumentBuilder's "parse" method to get the document
        Document doc = (Document) dBuilder.parse(new File(name));

        return doc;
    }

    public static void readingXML(String name) throws IOException, SAXException, ParserConfigurationException {

        Document doc = OpenXML(name);

        Element root = doc.getDocumentElement();

        System.out.println("Llegint XML...");

        // We will get a list of nodes (Step 1)
        NodeList modules = root.getElementsByTagName("modul");

        //System.out.println(modules.getLength());
        // For each node (Step 2)
        for (int i = 0; i < modules.getLength(); i++) {
            Element el = (Element) modules.item(i);

            // Display the node name (Step 3)
            System.out.println(el.getNodeName().toUpperCase() + " " + (i + 1));

            // And we show the value of the different tags 
            System.out.println("Nom: " + el.getElementsByTagName("nom").item(0).getFirstChild().getNodeValue()); // sols hi haurà
            System.out.println("Hores: " + el.getElementsByTagName("hores").item(0).getFirstChild().getNodeValue());
            System.out.println("Qualificació: " + el.getElementsByTagName("qualificacio").item(0).getFirstChild().getNodeValue());
            System.out.println();
        }
    }

    public static void main(String[] args) {

        // test the args
        if (args.length != 2) {
            System.out.println("Nombre d'arguments incorrecte.\n\nSintaxi: \n\t java Moduls2 [ read | write | readxml | writexml] nom_del_fitxer");
            System.exit(0);
        }

        ModulsXML moduls = new ModulsXML();

        try {
            // depending the args
            if (args[0].equals("read")) {
                moduls.LligObjecte(args[1]);
            } else if (args[0].equals("write")) {
                moduls.EscriuObjecte(args[1]);
            } else if (args[0].equals("writexml")) {
                moduls.writingXML(args[1]);
            } else if (args[0].equals("readxml")) {
                moduls.readingXML(args[1]);
            } else if (args[0].equals("readwrite")){
                moduls.readwrite(args[1],"result.xml");
            } else {
                System.out.println("No entenc l'ordre " + args[0] + "\n");
            }
        } catch (IOException | ClassNotFoundException | SAXException | ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(ModulsXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
