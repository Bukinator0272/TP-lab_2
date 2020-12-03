package server;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Expression;
import parser.Lexeme;
import parser.LexemeBuffer;
import parser.Parser;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler extends Thread {

    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public ClientHandler(Socket socket) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String request = bufferedReader.readLine();
                Expression expression = new ObjectMapper().readValue(request, Expression.class);
                System.out.println("Server received expression: " + expression.getExpression());

                Parser parser = new Parser(expression);
                List<Lexeme> lexemes = parser.lexAnalyze();
                LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
                expression.setAnswer(parser.solveExpression(lexemeBuffer));

                String json = new ObjectMapper().writeValueAsString(expression);
                bufferedWriter.write(json);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
