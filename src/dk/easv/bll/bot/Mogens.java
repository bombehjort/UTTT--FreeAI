package dk.easv.bll.bot;


public class Mogens extends LocalPrioritisedListBot {
    private static final String BOTNAME="Mogens";
    
    
    public Mogens() {
        int[][] pref = {
            {1, 1}, {0, 2}, {0, 2}, {2, 0},  
            {1, 1}, {2, 1}, {1, 0}, {1, 2}, 
            {1, 1}}; //C
        super.preferredMoves = pref;
    }

    @Override
    public String getBotName() {
        return BOTNAME; //To change body of generated methods, choose Tools | Templates.
    }
}