import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
public class Quests {
  
    private Label textField;
    private QuestAnimations QA;

    public Quests(Player charakter){
      
        this.textField = new Label();
        this.QA = new QuestAnimations(charakter, this);
        this.textField.setPrefWidth(300);
        this.textField.setPrefHeight(120);
        this.textField.setLayoutX(900);
        this.textField.setLayoutY(0);
        //textField.setText("Gib etwas ein...\n\n Hallo");
        textField.setText("Sammle 50 Holz");
        
        // Styling über Inline-CSS
        textField.setStyle("-fx-background-radius: 30; " +
                            "-fx-background-color: rgba(6, 236, 102, 0.7);" + 
                            "-fx-alignment: center; " +
                            "-fx-font-family: impact; " + //schriftart ändern
                            "-fx-font-Size: 14;" + 
                           "-fx-border-radius: 30; " +
                           "-fx-border-color: rgba(6, 124, 35, 0.7); " +
                           "-fx-border-width: 4; " +
                           "-fx-padding: 5;");
        ersteQuests();
    }

    private void ersteQuests(){
        GUIMain.getPane().getChildren().add(textField);
    }

    public void setInhalt(String newInhalt){
        textField.setText(newInhalt);
    }

    
}
