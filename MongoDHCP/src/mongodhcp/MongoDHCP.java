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
        Bson proyeccion = Document.parse("{_id:0, codigo:1, marca:1, sisope: 1, ramgb: 1, hdgb: 1, extras: 1}");
        Bson orden = Sorts.ascending("sisope");
        for (Document cursor : collection.find(filtro).projection(proyeccion).sort(orden)) {
            System.out.println("===============================================");
            System.out.printf("Código...........:",
                    cursor.get("codigo"));
            System.out.printf("\nMarca............:",
                    cursor.get("marca"));
            System.out.printf("\nSistema Operativo:",
                    cursor.get("sisope"));
            System.out.println("\n+----+-------+----+----+--------------------+");
            System.out.printf("| %-4s | %-7s | %-4s | %-4s | %-20s | \n", "RAM", "HD", "DVD", "TAR", "VIDEO");
            System.out.println("+----+-------+----+----+--------------------+");
            Document extras = (Document) cursor.get("extras");
            System.out.printf("\n| %-4s | %-7s | %-4s | %-4s | %-20s | \n",
                    cursor.get("ramgb"),
                    cursor.get("hdgb"),
                    extras.get("dvd"),
                    extras.get("lector_tarjetas"),
                    extras.get("video"));
            System.out.println("+----+-------+----+----+--------------------+");
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
