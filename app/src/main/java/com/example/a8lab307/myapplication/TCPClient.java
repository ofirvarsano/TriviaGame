package com.example.a8lab307.myapplication;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.hit.communication.AuthRequest;
import com.hit.communication.AuthRespond;
import com.hit.communication.Question;
import com.hit.communication.QuestionsRequest;
import com.hit.communication.Result;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class TCPClient {
    private static Gson gson = new Gson();
    private static int PORT = 10000;

    public static AuthRespond sendAuthRequest(String serverAddress, String user, String password) throws Exception
    {
        Socket clientSocket = new Socket(serverAddress, PORT);

        ObjectOutputStream  outToServer =
                new ObjectOutputStream (clientSocket.getOutputStream() );
        BufferedReader inFromServer =
                new BufferedReader(new
                        InputStreamReader(clientSocket.getInputStream()));

        AuthRequest authRequest = new AuthRequest(user, password);

        String jsonToServer = gson.toJson(authRequest);
        outToServer.writeObject(jsonToServer);

        String jsonFromServer = inFromServer.readLine();
        AuthRespond authRespond = gson.fromJson(jsonFromServer, new TypeToken<AuthRespond>(){}.getType());

        outToServer.close();
        inFromServer.close();
        clientSocket.close();

        return authRespond;
    }

    public static ArrayList<Question> sendQuestionsRequest(String serverAddress, int difficulty, int questionsNumber) throws Exception
    {
        Socket clientSocket = new Socket(serverAddress, PORT);

        ObjectOutputStream  outToServer =
                new ObjectOutputStream (clientSocket.getOutputStream() );
        BufferedReader inFromServer =
                new BufferedReader(new
                        InputStreamReader(clientSocket.getInputStream()));

        QuestionsRequest authRequest = new QuestionsRequest(difficulty, questionsNumber);
        String jsonToServer = gson.toJson(authRequest);
        outToServer.writeObject(jsonToServer);

        String jsonFromServer = inFromServer.readLine();
        ArrayList<Question> questions = gson.fromJson(jsonFromServer, new TypeToken<ArrayList<Question>>(){}.getType());

        outToServer.close();
        inFromServer.close();
        clientSocket.close();

        return questions;
    }

    public static void sendResult(String serverAddress, String userName, int score, int difficulty) throws Exception
    {
        Socket clientSocket = new Socket(serverAddress, PORT);

        ObjectOutputStream  outToServer =
                new ObjectOutputStream (clientSocket.getOutputStream() );

        Result result = new Result(userName, score, difficulty, new Date());
        String jsonToServer = gson.toJson(result);
        outToServer.writeObject(jsonToServer);

        outToServer.close();
        clientSocket.close();
    }
}