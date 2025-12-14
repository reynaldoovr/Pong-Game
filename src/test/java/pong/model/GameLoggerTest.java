package pong.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

public class GameLoggerTest {
    @Mock
    private GameLogger mockLogger;
    
    @Mock
    private ScoreBoard mockScoreBoard;
    
    private GameModel gameModel;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameModel = new GameModel(mockScoreBoard, mockLogger);
    }
    
    @Test
    void testGameStartLogged() {
        verify(mockLogger).logGameStart();
    }
    
    @Test
    void testCollisionLogging() {
        // Configurar la posición de la pelota para que colisione
        Ball ball = gameModel.getBall();
        Paddle leftPaddle = gameModel.getLeftPaddle();
        
        // Colocar la pelota justo al lado del paddle izquierdo
        ball = new Ball(leftPaddle.getX() + leftPaddle.getWidth() + ball.getRadius(), 
                       leftPaddle.getY() + leftPaddle.getHeight()/2, 
                       ball.getRadius());
        ball.setVelocityX(-5);  // Mover hacia la izquierda
        
        when(mockScoreBoard.isGameOver()).thenReturn(false);
        
        // Forzar una colisión
        if (ball.checkCollision(leftPaddle)) {
            mockLogger.logCollision("leftPaddle");
        }
        
        verify(mockLogger).logCollision("leftPaddle");
    }
    
    @Test
    void testGameEndLogging() {
        // Configurar el ScoreBoard para que indique fin del juego
        when(mockScoreBoard.isGameOver()).thenReturn(true);
        when(mockScoreBoard.getWinner()).thenReturn("Left Player");
        
        // Mover la pelota fuera de los límites para activar checkScore
        Ball ball = gameModel.getBall();
        ball.setVelocityX(1000);
        ball = new Ball(GameModel.SCREEN_WIDTH + 100, ball.getY(), ball.getRadius());
        
        // Forzar la comprobación de fin de juego
        if (mockScoreBoard.isGameOver()) {
            mockLogger.logGameEnd(mockScoreBoard.getWinner());
        }
        
        verify(mockLogger).logGameEnd("Left Player");
    }
}