package br.trab.univille.factory;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class ConexaoSqlite extends javax.swing.JFrame {
    private static ConexaoSqlite instance;
    private Connection connection;
    private ConexaoSqlite() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite::resource:db.sqlite");
        } catch (ClassNotFoundException | SQLException | HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Erro banco de dados: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static ConexaoSqlite getInstance() throws SQLException {
        if (Objects.isNull(instance) || instance.getConnection().isClosed()) {
            instance = new ConexaoSqlite();
        }
        return instance;
    }
}