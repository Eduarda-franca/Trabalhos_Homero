package database;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {
        
        try (Connection conexao = Conexao.conectar()){

        if (conexao.isValid(2)) {
            System.out.println("Conexão realizada com sucesso e validada!");
        } else {
            System.out.println("A conexão foi aberta, mas não respondeu.");
        }

        }catch (SQLException erro){

            System.out.println("Erro ao conectar com o banco de dados !");
            erro.printStackTrace();
        }
    }
    
}