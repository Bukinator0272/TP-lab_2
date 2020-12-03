package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Expression;

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
                Expression expression = new Expression(inputReader.readLine());

                String json = new ObjectMapper().writeValueAsString(expression);
                bufferedWriter.write(json);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                String response = bufferedReader.readLine();
                expression = new ObjectMapper().readValue(response, Expression.class);
                System.out.println("Answer: " + expression.getAnswer());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
