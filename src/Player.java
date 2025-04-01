import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    //private final Image grafik = new Image("file:rsc/manchen.png");
    private Image grafik = new Image("file:rsc/manchen2R.png");
    private String name;
    private String bildName;
    private ImageView playerView = new ImageView(); 
    private Collision collision;
    private String werkzeug;
    private Inventory inventory;
    private PlayerImage playerImage;

    public Player(String name) {
        this.name = name;
        this.werkzeug = "hand";
        this.inventory = new Inventory();
        this.playerImage = new PlayerImage(this);
        this.bildName = "manchen2R.png";
        this.playerView.setImage(grafik);
        this.playerView.setFitHeight(200);
        this.playerView.setY(200);
        this.playerView.setFitWidth(60);
        this.collision = new Collision(this);
    }

    public ImageView getImageView(){
        return playerView;
    }

    public void jumping(){
        collision.startJump();
    }
    
    public void stopJumping(){
        collision.stopJump();
    }

    public void aendereWidth(int px){
        playerView.setFitWidth(px);
    }


    public void startMovingRight() {
        playerImage.MoveRight();
        collision.startMovingRight();
    }

    public void startMovingLeft() {
        playerImage.MoveLeft();
        collision.startMovingLeft();
    }

    public void stopMovingRight() {
        playerImage.StandRight();
        collision.stopMovingRight();
    }

    public void stopMovingLeft() {
        playerImage.StandLeft();
        collision.stopMovingLeft();
        
    }

    public void setImage(String image){
        this.grafik = new Image("file:rsc/" + image);
        this.bildName = image;
        this.playerView.setImage(grafik);

    }

    public void setWerkzeug(String werkzeug){
        this.werkzeug = werkzeug;
    }

    public String getWerkzeug(){
        return werkzeug;
    }

    public void ImageAktualisieren(){
        if (bildName == "manchen2L.png" || bildName == "manchenMoveL.png" || bildName == "AxtLL.png" || bildName == "AxtLS.png") {
            playerImage.StandLeft();
        }
        else{
            playerImage.StandRight();
        }
    }

    public int etwasAbbauen(){
        if ("axt".equals(this.werkzeug)) {
            for (Tree tree : Tree.getTrees()){
                double min = tree.getX() + 50;
                double max = tree.getX() + 200;
                if (this.playerView.getX() + 60 >= min && this.playerView.getX() <= max) {
                    if (tree.getAbbauClicks() > 0) {
                        tree.setAbbauClicks(tree.getAbbauClicks() - 1);
                        if (tree.getAbbauClicks() == 0) {
                            GUIMain.treeFromPaneRemove(tree);
                            inventory.addWood(5);
                            return 0;
                        }
                        
                    }
                    /* 
                    else if (tree.getAbbauClicks() == 0) {
                        GUIMain.treeFromPaneRemove(tree);
                        inventory.addWood(5);
                        return 0;
                    }
                    */
                    
                }
            }
            
            
            return 0;
            
        }
        
        
        return 1;
    }

    public String getImage(){
        return bildName;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public Collision getCollision() {
        return collision;
    }

    

}
