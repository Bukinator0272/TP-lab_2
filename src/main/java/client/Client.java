package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Statement;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 4444);
            while (true) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader inputReader = new BufferedReader(
                        new InputStreamReader(System.in));

                System.out.println("Enter expression: ");
                Statement statement = new Statement(inputReader.readLine());

                String json = new ObjectMapper().writeValueAsString(statement);
                bufferedWriter.write(json);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                String response = bufferedReader.readLine();
                statement = new ObjectMapper().readValue(response, Statement.class);
                System.out.println("Answer: " + statement.getAnswer());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
