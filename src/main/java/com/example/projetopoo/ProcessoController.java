package com.example.projetopoo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.*;
import java.sql.Date;

public class ProcessoController {
    @FXML
    private TextArea welcomeText;

    @FXML
    private DatePicker dataProtocoloPicker;
    @FXML
    private TextField idProcessoField;
    @FXML
    private TextField numeroField;
    @FXML
    private TextField tipoField;
    @FXML
    private TextField numeroTribunalField;
    @FXML
    private TextField tribunalField;
    @FXML
    private TextField instanciaField;

    @FXML
    private TextField searchIdField;

    @FXML
    protected void onHelloButtonClick() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/poo", "postgres", "30092003")) {
            int searchId = Integer.parseInt(searchIdField.getText());

            String sql = "SELECT * FROM processo WHERE id_processo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, searchId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    StringBuilder resultText = new StringBuilder();

                    while (resultSet.next()) {
                        int idProcesso = resultSet.getInt("id_processo");
                        String numero = resultSet.getString("numero");
                        String tipo = resultSet.getString("tipo");
                        Date data_protocolo = resultSet.getDate("data_protocolo");
                        String numero_tribunal = resultSet.getString("numero_tribunal");
                        String tribunal = resultSet.getString("tribunal");
                        int instancia = resultSet.getInt("instancia");

                        resultText.append("ID: ").append(idProcesso)
                                .append("\nNúmero: ").append(numero)
                                .append("\nTipo: ").append(tipo)
                                .append("\nData Protocolo: ").append(data_protocolo)
                                .append("\nNúmero Tribunal: ").append(numero_tribunal)
                                .append("\nTribunal: ").append(tribunal)
                                .append("\nInstância: ").append(instancia)
                                .append("\n\n");
                    }

                    welcomeText.setText(resultText.toString());
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            welcomeText.setText("Erro ao buscar no banco de dados.");
        }
    }

    @FXML
    protected void onInsertButtonClick() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/poo", "postgres", "30092003")) {
            String sql = "INSERT INTO processo (id_processo, numero, tipo, data_protocolo, numero_tribunal, tribunal, instancia) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, Integer.parseInt(idProcessoField.getText()));
                preparedStatement.setString(2, numeroField.getText());
                preparedStatement.setString(3, tipoField.getText());
                preparedStatement.setDate(4, Date.valueOf(dataProtocoloPicker.getValue()));
                preparedStatement.setString(5, numeroTribunalField.getText());
                preparedStatement.setString(6, tribunalField.getText());
                preparedStatement.setInt(7, Integer.parseInt(instanciaField.getText()));

                preparedStatement.executeUpdate();
                welcomeText.setText("Inserção realizada com sucesso!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            welcomeText.setText("Erro ao inserir no banco de dados.");
        }
    }



    @FXML
    protected void onSearchButtonClick() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/poo", "postgres", "30092003")) {
            int searchId = Integer.parseInt(searchIdField.getText());

            String sql = "SELECT * FROM processo WHERE id_processo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, searchId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    StringBuilder resultText = new StringBuilder();

                    while (resultSet.next()) {
                        int idProcesso = resultSet.getInt("id_processo");
                        String numero = resultSet.getString("numero");
                        String tipo = resultSet.getString("tipo");
                        Date data_protocolo = resultSet.getDate("data_protocolo");
                        String numero_tribunal = resultSet.getString("numero_tribunal");
                        String tribunal = resultSet.getString("tribunal");
                        int instancia = resultSet.getInt("instancia");

                        resultText.append("ID: ").append(idProcesso)
                                .append("\nNúmero: ").append(numero)
                                .append("\nTipo: ").append(tipo)
                                .append("\nData Protocolo: ").append(data_protocolo)
                                .append("\nNúmero Tribunal: ").append(numero_tribunal)
                                .append("\nTribunal: ").append(tribunal)
                                .append("\nInstância: ").append(instancia)
                                .append("\n\n");
                    }

                    welcomeText.setText(resultText.toString());
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            welcomeText.setText("Erro ao buscar no banco de dados.");
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/poo", "postgres", "30092003")) {
            int deleteId = Integer.parseInt(searchIdField.getText());

            String sql = "DELETE FROM processo WHERE id_processo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, deleteId);

                int rowsDeleted = preparedStatement.executeUpdate();

                if (rowsDeleted > 0) {
                    welcomeText.setText("Registro excluído com sucesso.");
                } else {
                    welcomeText.setText("Nenhum registro encontrado para excluir com o ID fornecido.");
                }
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            welcomeText.setText("Erro ao excluir no banco de dados.");
        }
    }

}
