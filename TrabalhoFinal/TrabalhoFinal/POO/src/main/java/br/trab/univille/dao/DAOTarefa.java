package br.trab.univille.dao;

import br.trab.univille.factory.ConexaoSqlite;
import br.trab.univille.model.Tarefa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class DAOTarefa {
    private Connection connection;

    public DAOTarefa(){
        try{
            this.connection = ConexaoSqlite.getInstance().getConnection();
            this.createTableIfExists();
        } catch (SQLException e) {
            System.out.println("Falha ao conectar ao banco de dados: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void create(Tarefa tarefa) {
        if(!isValidoTituloTarefa(tarefa)){
            throw new RuntimeException("Título inválido");
        }
        String sql = "INSERT INTO tarefa(titulo, texto, dataCriacao, dataConclusao, concluida, idLista) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tarefa.getTitulo());
            stmt.setString(2, tarefa.getTexto());
            stmt.setString(3, tarefa.getDataCriacao().toString());
            stmt.setString(4, Objects.isNull(tarefa.getDataConclusao()) ? null : tarefa.getDataConclusao().toString());
            stmt.setInt(5, tarefa.isConcluida() ? 1 : 0);
            stmt.setInt(6, tarefa.getLista().getId());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Falha ao inserir tarefa: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Tarefa readOne(int idTarefa){
        String sql = "SELECT * FROM tarefa WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTarefa);
            ResultSet rs = stmt.executeQuery();

            Tarefa tarefa = new Tarefa();
            tarefa.setId(rs.getInt("id"));
            tarefa.setTitulo(rs.getString("titulo"));
            tarefa.setTexto(rs.getString("texto"));
            tarefa.setDataCriacao(LocalDate.parse(rs.getString("dataCriacao")));
            tarefa.setDataConclusao(Objects.isNull(rs.getString("dataConclusao")) ? null : LocalDate.parse(rs.getString("dataConclusao")));
            tarefa.setConcluida(rs.getInt("concluida") == 1);
            tarefa.setLista(new DAOLista().readOne(rs.getInt("idLista")));
            stmt.close();
            return tarefa;
        } catch (Exception e) {
            System.err.println("Falha ao buscar tarefa: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public ArrayList<Tarefa> readAllByIdLista(int idLista){
        String sql = "SELECT * FROM tarefa WHERE idLista = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idLista);
            ResultSet rs = stmt.executeQuery();

            ArrayList<Tarefa> tarefas = new ArrayList<>();
            while (rs.next()) {
                Tarefa tarefa = new Tarefa();
                tarefa.setId(rs.getInt("id"));
                tarefa.setTitulo(rs.getString("titulo"));
                tarefa.setTexto(rs.getString("texto"));
                tarefa.setDataCriacao(LocalDate.parse(rs.getString("dataCriacao")));
                tarefa.setDataConclusao(Objects.isNull(rs.getString("dataConclusao")) ? null : LocalDate.parse(rs.getString("dataConclusao")));
                tarefa.setConcluida(rs.getInt("concluida") == 1);
                tarefas.add(tarefa);
            }
            stmt.close();
            return tarefas;
        } catch (SQLException e) {
            System.err.println("Falha ao ler tarefas: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void delete(int idTarefa) {
        String sql = "DELETE FROM tarefa WHERE id = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTarefa);
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            System.err.println("Falha ao deletar tarefa: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getIdByTitulo(String tarefa) {
        String sql = "SELECT id FROM tarefa WHERE titulo = ?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tarefa);
            ResultSet rs = stmt.executeQuery();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("id");
            }
            stmt.close();
            return id;
        } catch (SQLException e) {
            System.err.println("Falha ao buscar id da tarefa: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public int getProximoId() {
        String sql = "SELECT MAX(id) FROM tarefa;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            int id = 0;
            if (rs.next()) {
                id = rs.getInt("MAX(id)");
            }
            stmt.close();
            return id + 1;
        } catch (SQLException e) {
            System.err.println("Falha ao buscar id da tarefa: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private void createTableIfExists(){
        String sql = "CREATE TABLE IF NOT EXISTS tarefa(     " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL,                 " +
                "texto TEXT,            " +
                "dataCriacao TEXT NOT NULL,                    " +
                "dataConclusao TEXT,                       " +
                "concluida INTEGER NOT NULL DEFAULT 0,                       " +
                "idLista INTEGER NOT NULL,                       " +
                "FOREIGN KEY (idLista) REFERENCES lista(id) " +
                ");                                    ";
        try {
            this.connection.createStatement().execute(sql);
        } catch (Exception e) {
            System.err.println("Falha ao criar tabela: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean isValidoTituloTarefa(Tarefa tarefa){
        for (Tarefa t : readAllByIdLista(tarefa.getLista().getId())){
            if(t.getTitulo().equals(tarefa.getTitulo()) && t.getId() != tarefa.getId()){
                return false;
            }
        }
        if (tarefa.getTitulo().isEmpty()){
            System.err.println("Título da tarefa não pode ser vazio");
            return false;
        }
        return true;
    }
}