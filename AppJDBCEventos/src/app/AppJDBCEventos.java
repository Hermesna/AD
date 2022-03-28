package app;

import clases.Agendas;
import clases.Eventos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AppJDBCEventos {

    private static Connection con = null;

    public static void conectarAMySQL(String bd, String usuario, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url;
            url = "jdbc:mysql://localhost:3306/" + bd;
            url += "?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
            url += "&serverTimezone=UTC";

            con = DriverManager.getConnection(url, usuario, password);

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL no accesible");
        }
    }

    public static void desconectarMySQL() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getMessage());
        }
    }

    public static ArrayList<Agendas> cargarAgendasMemoria() {
        String sentenciaSQL = "SELECT codigo, titular, color FROM agendas order by codigo";
        Agendas agenda;
        Eventos evento;
        ArrayList<Agendas> listaAgendas = new ArrayList<>();

        try ( Statement statement = con.createStatement();  ResultSet rs = statement.executeQuery(sentenciaSQL)) {
            while (rs.next()) {
                agenda = new Agendas(rs.getString("codigo"), rs.getString("titular"), rs.getString("color"));

                sentenciaSQL = String.format("SELECT * FROM eventos WHERE codagenda = '%s'", rs.getString("codigo"));

                ArrayList<Eventos> eventos = new ArrayList<Eventos>();
                try ( Statement statement2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);  ResultSet rsEventos = statement2.executeQuery(sentenciaSQL)) {
                    while (rsEventos.next()) {
                        Eventos e = new Eventos();
                        e.setFecha(rsEventos.getDate("fecha").toString());
                        e.setHora(rsEventos.getTime("hora").toString());
                        e.setTexto(rsEventos.getString("texto"));
                        e.setEstado(rsEventos.getString("estado"));
                        eventos.add(e);
                    }
                } catch (SQLException se) {
                    System.out.println(se.getErrorCode() + " " + se.getMessage());
                }

                agenda.setEventos(eventos);
                listaAgendas.add(agenda);
            }

        } catch (SQLException se) {
            System.out.println(se.getErrorCode() + " " + se.getMessage());
        }
        return listaAgendas;

    }

    public static void mostrarDatos(ArrayList<Agendas> agendas) {

        for (Agendas agenda : agendas) {
            System.out.println("====================================");
            System.out.println("Código.: " + agenda.getCodigo());
            System.out.println("Titular: " + agenda.getTitular());
            System.out.println("Color..: " + agenda.getColor());

            ArrayList<Eventos> eventos = agenda.getEventos();

            System.out.println("+---------------------+---------------------------+------------+");
            for (Eventos evento : eventos) {
                System.out.printf("| %10s %8s | %-25s | %-10s |\n",
                        evento.getFecha(), evento.getHora(), evento.getTexto(), evento.getEstado());
            }
            System.out.println("+---------------------+---------------------------+------------+");
        }
    }

    public static void main(String[] args) {
        String usuario = "root";
        String password = "1234";
        String bd = "bdeventos";

        conectarAMySQL(bd, usuario, password);
        ArrayList<Agendas> agendas = cargarAgendasMemoria();
        mostrarDatos(agendas);
        desconectarMySQL();

    }

}
