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
            case "PickeH":
            player.setImage("HolzHackeRL.png");
            break;
            case "PickeS":
            player.setImage("SteinHackeRL.png");
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
            case "PickeH":
            player.setImage("HolzHackeLL.png");
            break;
            case "PickeS":
            player.setImage("SteinHackeLL.png");
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
            case "PickeH":
                player.setImage("HolzHackeRS.png");
            break;
            case "PickeS":
                player.setImage("SteinHackeRS.png");
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
            case "PickeH":
                player.setImage("HolzHackeLS.png");
            break;
            case "PickeS":
                player.setImage("SteinHackeLS.png");
            break;
        
        }
    }

}
