package pong.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import pong.model.Ball;
import pong.model.GameModel;
import pong.model.Paddle;
import pong.view.GameView;

public class GameControllerTest {
    
    @Mock
    private GameView mockView;
    
    @Mock
    private GameModel mockModel;
    
    @Mock
    private Paddle mockLeftPaddle;
    
    @Mock
    private Paddle mockRightPaddle;
    
    private GameController controller;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Configure mock returns
        when(mockModel.getLeftPaddle()).thenReturn(mockLeftPaddle);
        when(mockModel.getRightPaddle()).thenReturn(mockRightPaddle);
        
        controller = new GameController(mockModel, mockView);
    }
    
    @Test
    void testUpdateCallsModelAndView() {
        // Setup mock ball
        Ball mockBall = new Ball(100, 100, 10);
        when(mockModel.getBall()).thenReturn(mockBall);
        
        // Call controller update
        controller.update();
        
        // Verify all interactions
        verify(mockModel).update();
        verify(mockView).updateBallPosition(anyDouble(), anyDouble());
        verify(mockView).updatePaddlePositions(anyDouble(), anyDouble());
        verify(mockView).updateScore(anyInt(), anyInt());
        verify(mockView).render();
    }
    
    @Test
    void testPaddleMovement() {
        // Test left paddle movement
        controller.setLeftPaddleVelocity(5.0);
        verify(mockLeftPaddle).setVelocity(5.0);
        
        // Test right paddle movement
        controller.setRightPaddleVelocity(-5.0);
        verify(mockRightPaddle).setVelocity(-5.0);
    }
}