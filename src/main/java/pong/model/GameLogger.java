package pong.model;

public interface GameLogger {
    void logGameStart();
    void logScore(String player, int score);
    void logCollision(String objectType);
    void logGameEnd(String winner);
    String getLastLog();
    void clearLogs();
}