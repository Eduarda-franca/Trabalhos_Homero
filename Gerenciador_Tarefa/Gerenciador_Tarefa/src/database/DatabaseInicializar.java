package database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInicializar {

    public static void incializar() {

        String sql = """
             CREATE TABLE IF NOT EXISTS tarefa (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        titulo TEXT NOT NULL,
        descricao TEXT,
        concluido INTEGER NOT NULL
        )
        """;
        
    try (Connection conexao = Conexao.conectar() ; Statement statement = conexao.createStatement()) {

        statement.execute(sql);

        System.out.println("Banco de dados incializado!");

    }catch (SQLException erro){

        System.out.println("Erro ao incializar bando de dados!!");
        erro.printStackTrace();
    }   
    } 
}