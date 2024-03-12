package World;

/**
 *
 * @author Admin
 */
public class ScoreObserver extends Observer{

    public ScoreObserver(Game game) 
    {
        super(game);
    }

    @Override
    public void update(boolean increase) 
    {
        int x = game.getScore();
        if(increase)
        {
            game.setScore(x+1);
        }
        else
        {
            game.setScore(x-1);
        }
    }
    
}
