public class PlayerImage {
    private Player player;
    
    public PlayerImage(Player player){
        this.player = player;
    }

    public void MoveRight(){
        String werkzeug = player.getWerkzeug();
        
        switch (werkzeug) {
            case "hand":
                player.setImage("manchenMoveR.png");
            break;
            case "axt":
                player.setImage("AxtRL.png");
            break;
            case "Picke":

            break;
        
        }
    }

    public void MoveLeft(){
        String werkzeug = player.getWerkzeug();
        
        switch (werkzeug) {
            case "hand":
                player.setImage("manchenMoveL.png");
            break;
            case "axt":
                player.setImage("AxtLL.png");
            break;
            case "Picke":

            break;
        
        }
    }

    public void StandRight(){
        String werkzeug = player.getWerkzeug();
        
        switch (werkzeug) {
            case "hand":
                player.setImage("manchen2R.png");
            break;
            case "axt":
                player.setImage("AxtRS.png");
            break;
            case "Picke":

            break;
        
        }
    }

    public void StandLeft(){
        String werkzeug = player.getWerkzeug();
        
        switch (werkzeug) {
            case "hand":
                player.setImage("manchen2L.png");
            break;
            case "axt":
                player.setImage("AxtLS.png");
            break;
            case "Picke":

            break;
        
        }
    }

}
