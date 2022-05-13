package mongodhcp;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDHCP {

    public static void main(String[] args) {
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        MongoClientURI uri = new MongoClientURI("mongodb://localhost:27017");
        try (MongoClient mongoClient = new MongoClient(uri)) {

            MongoDatabase database = mongoClient.getDatabase("serdhcp");
            MongoCollection<Document> dispositivos = database.getCollection("dispositivos");

            mostrarInformacionDispositivos(dispositivos);
            insertarNuevoDispositivo(dispositivos);
            mostrarInformacionDispositivos(dispositivos);
        } catch (MongoException ex) {
            System.out.println("Error de conexión a MongoBD: " + ex.getMessage());
        }
    }

    public static void mostrarInformacionDispositivos(MongoCollection<Document> collection) {

        Bson filtro = Filters.gte("hdgb", 1024);
        Bson proyeccion = Document.parse("{_id:0, codigo:1, marca:1, sisope:1, ramgb:1, hdgb:1, extras:1}");
        
        Bson orden = Sorts.ascending("sisope");
        for (Document cursor : collection.find(filtro).projection(proyeccion).sort(orden)) {
            
            
            // En la mayoría de los casos has hecho solo cursor.get() o extras.get() pero hay que 
            // especificar el tipo por ejemplo ramgb es cursor.getInteger("ramgb")
            
            // Aquí te ha faltado poner los %s y te recomiendo que pongas los \n al final.
            System.out.println("=======================================");
            System.out.printf("Código...........: %s\n", cursor.getString("codigo"));
            System.out.printf("Marca............: %s\n", cursor.getString("marca"));
            System.out.printf("Sistema Operativo: %s\n", cursor.getString("sisope"));
            
            // Los tamaños de columna, no eran los del enunciado.
            System.out.printf("+-----+------+-----+-----+-------------+\n");
            System.out.printf("| %-3s | %-4s | %-3s | %-3s | %-11s |\n", "RAM", "HD", "DVD", "TAR", "VIDEO");
            System.out.printf("+-----+------+-----+-----+-------------+\n");
            Document extras = (Document) cursor.get("extras");
            
            // ramgb y hdgb son %d y no %s 
            System.out.printf("| %-3d | %-4d | %-3s | %-3s | %-11s |\n",
                    cursor.getInteger("ramgb"),
                    cursor.getInteger("hdgb"),
                    
                    // Aquí te he añadido una ternaria para que en lugar de true o false
                    // muestre SI o NO como se indica en las especificaciones.
                    extras.getBoolean("dvd")?"SI":"NO",
                    extras.getBoolean("lector_tarjetas")?"SI":"NO",
                    extras.getString("video"));
            System.out.printf("+-----+------+-----+-----+-------------+\n");
        }
    }
    
    public static void insertarNuevoDispositivo(MongoCollection<Document> dispositivos) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nDATOS PARA INSERTAR UNA NUEVO DISPOSITIVO");
        System.out.println("=========================================");
        System.out.print("Código.................: ");
        String codigo = scanner.nextLine();
        System.out.print("Marca..................: ");
        String marca = scanner.nextLine();
        System.out.print("Sistema Operativo......: ");
        String sisope = scanner.nextLine();
        System.out.print("Usuario................: ");
        String usuario = scanner.nextLine();
        System.out.print("RAM (GB)...............: ");
        int ram = Integer.parseInt(scanner.nextLine());
        System.out.print("HD (GB)................: ");
        int hd = Integer.parseInt(scanner.nextLine());
        System.out.print("DVD (SI/NO)............: ");
        String dvd = scanner.nextLine();
        System.out.print("LECTOR TARJETAS (SI/NO): ");
        String lector = scanner.nextLine();
        System.out.print("Video..................: ");
        String video = scanner.nextLine();
        
        boolean dvdB;
        if(dvd.equals("SI")){
            dvdB = true;
        } else {
            dvdB = false;
        }
        
        boolean lectorB;
        if(lector.equals("SI")){
            lectorB = true;
        } else {
            lectorB = false;
        }

        Document doc = new Document();
        doc.append("codigo", codigo);
        doc.append("marca", marca);
        doc.append("sisope", sisope);
        ArrayList<String> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        doc.append("usuarios", usuarios);
        doc.append("ramgb", ram);
        doc.append("hdgb", hd);
        Document extras = new Document();
        extras.append("dvd", dvdB);
        extras.append("lector_tarjetas", lectorB);
        extras.append("video", video);
        doc.append("extras", extras);
        dispositivos.insertOne(doc);
    }

}
