package com.srvr.serverForGame.controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.*;

public class GameWebSocketHandler extends TextWebSocketHandler {
    // Список всех активных подключений
    private static final List<WebSocketSession> sessions = new ArrayList<>();
    private static volatile boolean isGameStarted = false; // Флаг состояния игры
    private static volatile boolean isAllPLayesReady = false;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        if (isGameStarted) {
            try {
                session.sendMessage(new TextMessage("GAME_STARTED")); // Отправка новым клиентам
            } catch (Exception e) {
                sessions.remove(session);
            }
        } // Запоминаем новое подключение
    }

    // Метод, который шлёт всем "GAME_STARTED"
    public static void notifyAllPlayers() {
        isGameStarted = true; // Игра началась
        Iterator<WebSocketSession> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession session = iterator.next();
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage("GAME_STARTED"));
                } else {
                    iterator.remove(); // Удаляем закрытые сессии
                }
            } catch (Exception e) {
                iterator.remove();
                e.printStackTrace();
            }
        }
    }

    public static void notifyAllPlayersReady() {
        Iterator<WebSocketSession> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession session = iterator.next();
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage("ALL_PLAYERS_READY"));
                } else {
                    iterator.remove(); // Удаляем закрытые сессии
                }
            } catch (Exception e) {
                iterator.remove();
                e.printStackTrace();
            }
        }
    }

    public static void notifyAllPlayersEnd() {
        Iterator<WebSocketSession> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession session = iterator.next();
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage("GAME_END"));
                } else {
                    iterator.remove(); // Удаляем закрытые сессии
                }
            } catch (Exception e) {
                iterator.remove();
                e.printStackTrace();
            }
        }
    }
}
