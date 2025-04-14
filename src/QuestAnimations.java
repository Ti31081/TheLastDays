import javafx.animation.AnimationTimer;

public class QuestAnimations {
    private int questNummer;
    private Player player;
    private Quests quest;
    private TimerManager timemanager;


    public QuestAnimations(Player player, Quests quest, TimerManager timerManager){
        this.questNummer = 1;
        this.player = player;
        this.quest = quest;
        this.timemanager = timerManager;
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
                    this.stop(); 
                }
            }
        };
        bewegungTimer.start();
    }

    private void zweiteQuest(){
        AnimationTimer bewegungTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int wood = player.getInventory().getStein();
                quest.setInhalt("Sammle 40 Stein \n\n            " + wood + "/40");
                if (wood >= 40) {
                    dritteQuest();
                    this.stop(); 
                }
            }
        };
        bewegungTimer.start();
    }

    public void dritteQuest(){
        AnimationTimer bewegungTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                boolean fertig = player.getSteinPicke();
                quest.setInhalt("Crafte Dir aus 70 Holz und 48 Stein \n eine Stein Spitzhacke ");
                if (fertig){
                    vierteQuest();
                    this.stop(); 
                }
            }
        };
        bewegungTimer.start();
    }

    public void vierteQuest(){
        AnimationTimer bewegungTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                boolean fertig = player.getGun();
                quest.setInhalt("Sammele 35 Eisen und 28 Schwarzpulver um dir\n             eine Waffe und dessen Patronen zur\n                            verteidigung zu Craften");
                if (fertig){
                    fünfteQuest();
                    this.stop(); 
                }
            }
        };
        bewegungTimer.start();
    }

    public void fünfteQuest(){
        timemanager.showWinScreen();
    }
}
