package World;

/**
 *
 * @author Admin
 */
public abstract class Observer {
    protected Game game;
    
    public Observer(Game game)
    {
        this.game = game;
    }
    
    public abstract void update(boolean increase);
    
}
