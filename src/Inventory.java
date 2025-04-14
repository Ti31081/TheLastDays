import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Inventory {
    private int wood;
    private int stone;
    private int iron;
    private int gunpowder;
    private Label textFieldINV;
    

    public Inventory(){
        this.wood = 0;
        this.stone = 0;
        this.iron = 0;
        this.gunpowder = 0;


        this.textFieldINV = new Label();
        
        this.textFieldINV.setPrefWidth(150);
        this.textFieldINV.setPrefHeight(100);
        this.textFieldINV.setLayoutX(1050);
        this.textFieldINV.setLayoutY(120);
        //textFieldINV.setText("Gib etwas ein...\n\n Hallo");
        textFieldINV.setText("Holz: ");
        
        // Styling über Inline-CSS
        textFieldINV.setStyle("-fx-background-radius: 30; " +
                            "-fx-background-color: rgba(6, 236, 102, 0.7);" + 
                            "-fx-alignment: center; " +
                            "-fx-font-family: impact; " + //schriftart ändern
                            "-fx-font-Size: 10;" + 
                           "-fx-border-radius: 30; " +
                           "-fx-border-color: rgba(6, 124, 35, 0.7); " +
                           "-fx-border-width: 4; " +
                           "-fx-padding: 5;");
    }

    public Label gettextFieldINV(){
        animationStart();
        return textFieldINV;
    }

    private void animationStart(){
        AnimationTimer bewegungTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                textFieldINV.setText("        Holz: " + wood + "\n      Stein: "+ stone + "\n      Eisen: " + iron +"\n Schwarzpulver: "+ gunpowder +"\n\n Craften mit C");
            }
        };
        bewegungTimer.start();
    }

    public void addWood(int amount){
        this.wood += amount;
    }

    public void printWoodAmount() {
        System.out.println("Du hast " + this.wood + " Holz.");
    }

    public void setWood(int amount){
        this.wood = amount;
    }

    public void addStone(int amount){
        this.stone += amount;
    }

    public void printStoneAmount() {
        System.out.println("Du hast " + this.stone + " Stein.");
    }

    public int getStein(){
        return stone;
    }

    public void setStein(int stein){
        this.stone = stein;
    }

    public void addEisen(int amount){
        this.iron += amount;
    }

    public void printEisenAmount() {
        System.out.println("Du hast " + this.iron + " Eisen.");
    }

    public int getEisen(){
        return this.iron;
    }

    public void setEisen(int eisen){
        this.iron = eisen;
    }

    public void printSchwarzpulverAmount() {
        System.out.println("Du hast " + this.gunpowder + " Schwarzpulver.");
    }

    public void addSchwarupulver(int amount){
        this.gunpowder += amount;
    }

    public int getSchwarzpulver(){
        return this.gunpowder;
    }

    public int getWood() {
        return wood; // oder holz – je nachdem, wie dein Attribut heißt!
    }
    
}
