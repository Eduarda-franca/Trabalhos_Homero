package repository;

import database.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import Model.Tarefa;

public class TarefaRepository {

    public TarefaRepository() {
    }

    public void salvar(Tarefa tarefa) throws SQLException {
        String sql = "INSERT INTO tarefa (titulo, descricao, concluido) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, tarefa.getTitulo());
            statement.setString(2, tarefa.getDescricao());
            statement.setInt(3, tarefa.isConcluida() ? 1 : 0);

            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    tarefa.setId(rs.getInt(1));
                }
            }
        }
    }

    public ArrayList<Tarefa> listar() throws SQLException {
        ArrayList<Tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefa";

        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet rs = comando.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descricao = rs.getString("descricao");
                boolean concluida = rs.getInt("concluido") == 1;

                Tarefa tarefa = new Tarefa(titulo, descricao);
                tarefa.setId(id);
                tarefa.setConcluida(concluida);

                lista.add(tarefa);
            }
        }
        return lista;
    }

    public void atualizarStatus(int id, boolean concluida) throws SQLException {
        String sql = "UPDATE tarefa SET concluido = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, concluida ? 1 : 0);
            statement.setInt(2, id);

            statement.executeUpdate();
        }
    }

    public void editarTarefa(int id, String novoTitulo, String novaDescricao, boolean concluida) throws SQLException {
        String sql = "UPDATE tarefa SET titulo = ?, descricao = ?, concluido = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setString(1, novoTitulo);
            statement.setString(2, novaDescricao);
            statement.setInt(3, concluida ? 1 : 0);
            statement.setInt(4, id);

            statement.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM tarefa WHERE id = ?";

        try (Connection conexao = Conexao.conectar(); 
             PreparedStatement statement = conexao.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}