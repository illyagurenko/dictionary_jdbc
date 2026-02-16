package org.dictionary;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    //адрес бд
    static final String URL_DB = "jdbc:postgresql://localhost:5432/learn_db";
    static final String USER_DB = "postgres";
    static final String PASSWORD_DB = System.getenv("PASSWORD");

    public static void main(String[] args) {
        //поиск URL и подключение к бд
        try(Connection connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB)){
            readData(connection);
            addData(connection);
            readData(connection);
        }
        catch(SQLException e){
            System.out.println("error connect");
            e.printStackTrace();
        }
    }

    private static void readData(Connection connection) throws SQLException{
        //объект для запросов
        try(java.sql.Statement statement = connection.createStatement()) {
            String sql = "select * from dictionary_en_ru";
            //отправка запроса и получение ответа
            try(java.sql.ResultSet resultSet = statement.executeQuery(sql)){
                //перебор таблицы
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String engWord = resultSet.getString("english_word");
                    String ruWord = resultSet.getString("russian_word");
                    System.out.println(id + ": " + engWord + " - " + ruWord);
                }
            }
        }
    }

    private static void addData(Connection connection) throws SQLException{
        Scanner scanner = new Scanner(System.in);

        System.out.println("input english word");
        String engWord = scanner.nextLine();

        System.out.println("input russian translate word");
        String ruWord = scanner.nextLine();

        String sql = "insert into dictionary_en_ru (english_word, russian_word) values (?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            //безопасное добавление, 1,2 - номер знака вопроса из values
            preparedStatement.setString(1, engWord);
            preparedStatement.setString(2, ruWord);
            //выполнение запроса
            int newRows = preparedStatement.executeUpdate();
            if(newRows > 0){
                System.out.println("success add words");
            }
        }
    }
}
