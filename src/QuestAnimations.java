import javafx.animation.AnimationTimer;

public class QuestAnimations {
    private int questNummer;
    private Player player;
    private Quests quest;


    public QuestAnimations(Player player, Quests quest){
        this.questNummer = 1;
        this.player = player;
        this.quest = quest;
        ersteQuest();
    }
    
    private void ersteQuest(){
        AnimationTimer bewegungTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int wood = player.getInventory().getWood();
                quest.setInhalt("Sammle 50 Holz \n\n            " + wood + "/50");
                if (wood >= 50) {
                    zweiteQuest();
                    this.stop(); // Use this.stop() instead of bewegungTimer.stop() since we're inside the AnimationTimer
                }
            }
        };
        bewegungTimer.start();
    }

    private void zweiteQuest(){
        AnimationTimer bewegungTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int wood = 0;
                quest.setInhalt("Sammle 50 Stein \n\n            " + wood + "/50");
                if (wood >= 50) {
                    dritteQuest();
                    this.stop(); // Use this.stop() instead of bewegungTimer.stop() since we're inside the AnimationTimer
                }
            }
        };
        bewegungTimer.start();
    }

    public void dritteQuest(){
        
    }
}
