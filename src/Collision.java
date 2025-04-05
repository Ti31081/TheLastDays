import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class Collision {
    private Player player;
    private Stone stones;
    private AnimationTimer movementTimer;
    private final double GRAVITY = 0.5;
    private final double JUMP_FORCE = -10;
    private final double MOVEMENT_SPEED = 0.5;
    private final double MAX_SPEED = 8;
    private final double FRICTION = 0.8;
    private boolean canJump = false;
    private int jumpCounter = 0;
    private final int maxJumps = 1;

    
    private double velocityY = 0;
    private double velocityX = 0;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingUp = false;

    public Collision(Player player) {
        this.player = player;
        startMovementTimer();
    }

    private void startMovementTimer() {
        movementTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateMovement();
                if (!movingUp) {
                    applyGravity();    
                }
                
            }
        };
        movementTimer.start();
    }

    private void updateMovement() {
        // Beschleunigung basierend auf Eingabe
        if (movingRight) {
            
            velocityX += MOVEMENT_SPEED;
            GUIMain.grassPlazieren();
            
            
        }
        if (movingLeft) {
            velocityX -= MOVEMENT_SPEED;
        }

        if (movingUp && jumpCounter < maxJumps) {
            
            velocityY = JUMP_FORCE;
            double newY = player.getImageView().getY() + velocityY;
            player.getImageView().setY((int)newY);
            //System.out.println(velocityY);
            ++jumpCounter;
            System.out.println("jumpCounter: " + jumpCounter);
            
            
            
            
        }
        
        // Geschwindigkeit begrenzen
        velocityX = Math.min(Math.max(velocityX, -MAX_SPEED), MAX_SPEED);
        
        // Reibung anwenden, wenn keine Bewegung
        if (!movingLeft && !movingRight) {
            velocityX *= FRICTION;
        }
        
        // Sehr kleine Geschwindigkeiten auf 0 setzen
        if (Math.abs(velocityX) < 0.1) {
            velocityX = 0;
        }

        // Position aktualisieren
        double newX = player.getImageView().getX() + velocityX; // hallo

        boolean canMove = true;
        for (Grassblocks block : Grassblocks.getGrassblocks()) {
            if (wouldCollideHorizontally(newX, block)) {
                canMove = false;
                velocityX = 0;
                break;
            }
        }
        
        // Bildschirmgrenzen prüfen
        if (canMove) {
            if (newX + player.getImageView().getFitWidth() <= 890 && newX >= 0) {
                player.getImageView().setX(newX);
            } else if (newX + player.getImageView().getFitWidth() >= 890) {
                Grassblocks.verschiebeBlöcke();
                Tree.verschiebeTrees();
                Stone.verschiebeSteine();
                Eisen.verschiebeEisen();
                Schwarzpulver.verschiebeSchwarzpulver();
            }
        }
    }

    private boolean wouldCollideHorizontally(double newX, Grassblocks block) {
        double playerBottom = player.getImageView().getY() + player.getImageView().getFitHeight();
        double playerTop = player.getImageView().getY();
        double blockTop = block.getImageView().getY();
        double blockBottom = block.getImageView().getY() + block.getImageView().getFitHeight();
        double playerRight = newX + player.getImageView().getFitWidth();
        double playerLeft = newX;
        double blockRight = block.getImageView().getX() + block.getImageView().getFitWidth();
        double blockLeft = block.getImageView().getX();
        

        // Vertikale Überlappung
        boolean verticalOverlap = playerBottom > blockTop && playerTop < blockBottom;
        
        // Horizontale Kollision
        boolean horizontalCollision = playerRight > blockLeft && playerLeft < blockRight;

        return verticalOverlap && horizontalCollision;
    }

    // Methoden zum Starten und Stoppen der Bewegung
    public void startMovingRight() {
        movingRight = true;
        movingLeft = false;
    }

    public void startMovingLeft() {
        movingLeft = true;
        movingRight = false;
    }

    public void stopMovingRight() {
        movingRight = false;
    }

    public void stopMovingLeft() {
        movingLeft = false;
    }

    public void startJump(){
        if (jumpCounter < maxJumps) {
            movingUp = true;   
        }
        else{
            movingUp = false;
        }
    }
    public void stopJump(){
        movingUp = false;
    }

    // ... rest of the existing methods (applyGravity, checkCollision, etc.) ...



    private void applyGravity() {
        velocityY += GRAVITY;
        double newY = player.getImageView().getY() + velocityY;
        
        canJump = false;  // Reset jump flag
        
        for (Grassblocks block : Grassblocks.getGrassblocks()) {
            if (checkVerticalCollision(block)) {
                newY = block.getImageView().getY() - player.getImageView().getFitHeight();
                velocityY = 0;
                jumpCounter = 0;  // Spieler kann springen, wenn er auf einem Block steht
                break;
            }
        }
        for (Stone stones : Stone.getStones()) {
            if (checkVerticalCollision(stones)) {
                newY = stones.getImageView().getY() - player.getImageView().getFitHeight();
                velocityY = 0;
                jumpCounter = 0;  // Player can jump if standing on a stone
                break;
            }
        }
        for (Eisen eisen : Eisen.getEisen()) {
            if (checkVerticalCollision(eisen)) {
                newY = eisen.getImageView().getY() - player.getImageView().getFitHeight();
                velocityY = 0;
                jumpCounter = 0;  // Player can jump if standing on iron
                break;
            }
        }
        for (Schwarzpulver sp : Schwarzpulver.getSchwarzpulver()) {
            if (checkVerticalCollision(sp)) {
                newY = sp.getImageView().getY() - player.getImageView().getFitHeight();
                velocityY = 0;
                jumpCounter = 0;  // Player can jump if standing on gunpowder
                break;
            }
        }
        //System.out.println((int) newY);
        player.getImageView().setY((int) newY);
    }

    private boolean checkVerticalCollision(Grassblocks block) {
        double playerBottom = player.getImageView().getY() + player.getImageView().getFitHeight();
        double blockTop = block.getImageView().getY();
        double playerRight = player.getImageView().getX() + player.getImageView().getFitWidth();
        double playerLeft = player.getImageView().getX();
        double blockRight = block.getImageView().getX() + block.getImageView().getFitWidth();
        double blockLeft = block.getImageView().getX();
       
        // Überprüfe, ob der Spieler horizontal mit dem Block überlappt
        boolean horizontalOverlap = playerRight > blockLeft && playerLeft < blockRight;
        
        // Überprüfe, ob der Spieler vertikal mit dem Block kollidiert
        boolean verticalCollision = playerBottom >= blockTop && playerBottom <= blockTop + 20;
 
        return horizontalOverlap && verticalCollision;
    }
    private boolean checkVerticalCollision(Stone stone) {
        if (stone == null || stone.getImageView() == null || stone.getAbbauClicks() <= 0) return false; // bereits abgebaut
        if (stone.isRemoved()) return false;
    
        double playerBottom = player.getImageView().getY() + player.getImageView().getFitHeight();
        double playerTop = player.getImageView().getY();
        double stoneTop = stone.getImageView().getY();
        double playerRight = player.getImageView().getX() + player.getImageView().getFitWidth();
        double playerLeft = player.getImageView().getX();
        double stoneRight = stone.getImageView().getX() + stone.getImageView().getFitWidth() - 30;
        double stoneLeft = stone.getImageView().getX() + 32;
    
        boolean horizontalOverlap = playerRight > stoneLeft && playerLeft < stoneRight;
        boolean verticalCollision = playerBottom >= stoneTop && playerTop < stoneTop;
    
        return horizontalOverlap && verticalCollision;
    }

    private boolean checkVerticalCollision(Eisen eisen) {
        if (eisen == null || eisen.getImageView() == null || eisen.getAbbauClicks() <= 0) return false; // bereits abgebaut
        if (eisen.isRemoved()) return false;
    
        double playerBottom = player.getImageView().getY() + player.getImageView().getFitHeight();
        double playerTop = player.getImageView().getY();
        double eisenTop = eisen.getImageView().getY();
        double playerRight = player.getImageView().getX() + player.getImageView().getFitWidth();
        double playerLeft = player.getImageView().getX();
        double eisenRight = eisen.getImageView().getX() + eisen.getImageView().getFitWidth() - 30;
        double eisenLeft = eisen.getImageView().getX() + 32;
    
        boolean horizontalOverlap = playerRight > eisenLeft && playerLeft < eisenRight;
        boolean verticalCollision = playerBottom >= eisenTop && playerTop < eisenTop;
    
        return horizontalOverlap && verticalCollision;
    }
    
    private boolean checkVerticalCollision(Schwarzpulver schwarzpulver) {
        if (schwarzpulver == null || schwarzpulver.getImageView() == null || schwarzpulver.getAbbauClicks() <= 0) return false; // bereits abgebaut
        if (schwarzpulver.isRemoved()) return false;
    
        double playerBottom = player.getImageView().getY() + player.getImageView().getFitHeight();
        double playerTop = player.getImageView().getY();
        double schwarzpTop = schwarzpulver.getImageView().getY();
        double playerRight = player.getImageView().getX() + player.getImageView().getFitWidth();
        double playerLeft = player.getImageView().getX();
        double schwarzpRight = schwarzpulver.getImageView().getX() + schwarzpulver.getImageView().getFitWidth() - 30;
        double schwarzpLeft = schwarzpulver.getImageView().getX() + 32;
    
        boolean horizontalOverlap = playerRight > schwarzpLeft && playerLeft < schwarzpRight;
        boolean verticalCollision = playerBottom >= schwarzpTop && playerTop < schwarzpTop;
    
        return horizontalOverlap && verticalCollision;
    }

    private boolean checkHorizontalCollision(Object object) {
        double playerBottom = player.getImageView().getY() + player.getImageView().getFitHeight();
        double playerTop = player.getImageView().getY();
        double objectTop = 0;
        double objectBottom = 0;
        double objectRight = 0;
        double objectLeft = 0;

        if (object instanceof Eisen) {
            Eisen eisen = (Eisen) object;
            objectTop = eisen.getY();
            objectBottom = eisen.getY() + 50;
            objectRight = eisen.getX() + 50;
            objectLeft = eisen.getX();
        } else if (object instanceof Schwarzpulver) {
            Schwarzpulver sp = (Schwarzpulver) object;
            objectTop = sp.getY();
            objectBottom = sp.getY() + 50;
            objectRight = sp.getX() + 50;
            objectLeft = sp.getX();
        }

        double playerRight = player.getImageView().getX() + player.getImageView().getFitWidth();
        double playerLeft = player.getImageView().getX();

        // Überprüfe ob der Spieler vertikal mit dem Objekt überlappt
        boolean verticalOverlap = playerBottom > objectTop && playerTop < objectBottom;
        
        // Überprüfe ob der Spieler horizontal mit dem Objekt kollidiert
        boolean horizontalCollision = (playerRight >= objectLeft && playerRight <= objectLeft + 10) ||
                                    (playerLeft <= objectRight && playerLeft >= objectRight - 10);

        return verticalOverlap && horizontalCollision;
    }

    public void pauseMovementTimer() {
        if (movementTimer != null) {
            movementTimer.stop();
        }
    }

    public void resumeMovementTimer() {
        if (movementTimer != null) {
            movementTimer.start();
        }
    }
}