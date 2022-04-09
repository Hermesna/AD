<%-- 
    Document   : index
    Created on : 9 abr. 2022, 13:40:39
    Author     : Hermes
--%>
<%@page import="clases.Estadistica"%>
<%@page import="clases.Votos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Votos</title>
        <style>
            table {
                border:1px solid black;
                border-collapse:collapse;                
            }
            table td,
            table th {
                border:1px solid black;
                padding:5px 10px;
                text-align:left;
            }
            table th {
                background-color:orange;
            }
            .cabecera {
                font-weight: bold;
                background-color:#dddddd;
            }
            .rotulo {
                font-weight: bold;
                background-color:#f2deac;
            }
            hr {
                height:8px;
                border:none;
                color:blue;
                background-color:blue;
                float: left;
            }
        </style> 
    </head>
    <body>
        <h1>ELECCIÓN DE DELEGADOS</h1>
        <table>
            <tr>
                <th>DELEGADO</th>
                <th colspan="2">PORCENTAJE VOTOS</th>
            </tr>
            <tr>
                <td>Juan Pérez</td>
                <td style="text-align:right">22%</td>
                <td><hr style="width:220px;"></td>
            </tr>
            <tr>
                <td>Sergio González</td>
                <td style="text-align:right">35%</td>
                <td><hr style="width:350px;"></td>
            </tr>
            <tr>
                <td>Elena Sánchez</td>
                <td style="text-align:right">40%</td>
                <td><hr style="width:400px;"></td>
            </tr>
            <tr>
                <td>Ana Serrano</td>
                <td style="text-align:right">3%</td>
                <td><hr style="width:30px;"></td>
            </tr>            
        </table>
    </body>
</html>
