package World;

import Model.Apple;
import Model.Bar;
import Model.Bomb;
import Model.Bonus;
import Model.Difficulty;
import Model.GameObjectFactory;
import Model.GameObjectIterator;
import Model.ImageObject;
import Model.Mango;
import Model.Plate;
import Model.Player;
import Model.Shape;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

public final class Game implements World {

    private static int MAX_TIME = 1 * 45 * 1000;
    private final int height;
    private final int width;
    private int score = 0;
    private int numOfMovingObjects = 10;
    private long passTimeStart = System.currentTimeMillis(), passTimeEnd, endTime, startTime = System.currentTimeMillis();

    ImageObject clown = Player.getInstance();
    Bar leftBar = new Bar(1200 * 5 / 10 - 30, 700 * 6 / 8 - 70, 60, true, Color.BLACK);
    Bar rightBar = new Bar(1200 * 5 / 10 + 70, 700 * 6 / 8 - 70, 60, true, Color.BLACK);

    private final ArrayList<GameObject> constant = new ArrayList<GameObject>();
    private final ArrayList<GameObject> moving = new ArrayList<GameObject>();
    private final ArrayList<GameObject> control = new ArrayList<GameObject>();
    ArrayList<Observer> observer = new ArrayList<Observer>();

    private Stack<Shape> wl = new Stack<Shape>();
    private Stack<Shape> wr = new Stack<Shape>();
    private int heightAboveLeftBar = 0;
    private int heightAboveRightBar = 0;
    private int center = 0;
    private boolean increase;
    GameObjectIterator iterator;
    GameObjectFactory factory = GameObjectFactory.getInstance();

    private Difficulty currentDifficultyMode;

    public Game(Difficulty difficultyMode, int frameHeight, int frameWidth) {
        currentDifficultyMode = difficultyMode;
        height = frameHeight;
        width = frameWidth;
        control.add(clown);
        control.add(new ImageObject(1200 * 5 / 10 - 60, 700 * 6 / 8 - 70, 125, true, "VerticalLeft.png"));
        control.add(leftBar);
        control.add(new ImageObject(1200 * 5 / 10 + 35, 700 * 6 / 8 - 70, 125, true, "VerticalRight.png"));
        control.add(rightBar);
        gameObjectCreator();
        addObserver(new ScoreObserver(this));
        clown.setX(1200 * 5 / 11);
    }

    public void gameObjectCreator() {

        for (int i = 0; i < numOfMovingObjects; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height / 32);
            int type = (int) (Math.random() * 5 + 1);
            int imageColor = (int) (Math.random() * 3 + 1);
            Shape shape = null;
            if (type == 1) {
                shape = (Apple) factory.getShape(x, y, imageColor, type);
            } else if (type == 2) {
                shape = (Plate) factory.getShape(x, y, imageColor, type);
            } else if (type == 3) {
                shape = (Mango) factory.getShape(x, y, imageColor, type);
            } else if (type == 4) {
                shape = (Bomb) factory.getShape(x, y, imageColor, type);
            } else if (type == 5) {
                shape = (Bonus) factory.getShape(x, y, imageColor, type);
            }
            moving.add(shape);
        }
        constant.add(new ImageObject(0, 0, true, "circuis.jpg"));
        passTimeStart = System.currentTimeMillis();

    }

    void addObserver(Observer o) {
        observer.add(o);
    }

    void notifyAllObservers(boolean increase) {
        for (Observer o : observer) {
            o.update(increase);
        }

    }

    private boolean intersect(GameObject o1, GameObject o2) {
        return (Math.abs((o1.getX() + o1.getWidth() / 2) - (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth()) && (Math.abs((o1.getY() + o1.getHeight() / 2) - (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight());

    }

    @Override
    public ArrayList<GameObject> getConstantObjects() {
        return constant;
    }

    @Override
    public ArrayList<GameObject> getMovableObjects() {
        return moving;
    }

    @Override
    public ArrayList<GameObject> getControlableObjects() {
        return control;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean refresh() {
        boolean timeout = leftBar.getY() + this.heightAboveLeftBar > 920 && rightBar.getY() + this.heightAboveRightBar > 920 || System.currentTimeMillis() - startTime > MAX_TIME;
        //System.out.println(rightBar.getY());
        //System.out.println((leftBar.getY()+this.heightAboveLeftBar) + " " + (rightBar.getY() + this.heightAboveRightBar));
        passTimeEnd = System.currentTimeMillis();
        if (passTimeEnd - passTimeStart >= 1 * this.currentDifficultyMode.getSpawnSpeed() * 1000) {
            gameObjectCreator();
        }
        GameObject leftBar = this.leftBar;
        GameObject rightBar = this.rightBar;
        GameObject leftPeek = null;
        GameObject rightPeek = null;
        if (!wl.isEmpty()) {
            leftPeek = (GameObject) wl.peek();
        } else {
            leftPeek = leftBar;
        }
        if (!wr.isEmpty()) {
            rightPeek = (GameObject) wr.peek();
        } else {
            rightPeek = rightBar;
        }
        if (!timeout) {
            endTime = System.currentTimeMillis();
            iterator = new GameObjectIterator(moving);
            while (iterator.hasNext()) {
                GameObject o = iterator.next();
                int type = ((Shape) o).getType();
                o.setY((o.getY() + this.currentDifficultyMode.getMovingObjectSpeed()));
                if (o.getY() == getHeight()) {
                    // reuse the alien in another position
                    //o.setY(-1 * (int) (Math.random() * getHeight()));
                    o.setX((int) (Math.random() * getWidth()));
                }

                if (intersect(o, leftPeek)) {
                    if (type == 4) {
                        increase = false;
                        notifyAllObservers(increase);
                        ((Shape) o).setVisible(false);
                        moving.remove(o);
                        break;
                    } else if (type == 5) {
                        increase = true;
                        notifyAllObservers(increase);
                        ((Shape) o).setVisible(false);
                        moving.remove(o);
                        break;
                    } else {
                        heightAboveLeftBar += o.getHeight();

                        if (type == 1) {
                            center = 0;
                        } else {
                            center = 10;
                        }
                        Shape shape = new Shape(leftBar.getX() - center, leftBar.getY() - heightAboveLeftBar, ((Shape) o).getColor(), ((Shape) o).getType()) {
                        };
                        shape.setHorizontalOnly(true);
                        control.add(shape);
                        wl.push(shape);

                        ((Shape) o).setVisible(false);
                        moving.remove(o);
                        if (wl.size() >= 3 && wl.get(wl.size() - 1).getColor() == wl.get(wl.size() - 2).getColor() && wl.get(wl.size() - 1).getColor() == wl.get(wl.size() - 3).getColor() && wl.get(wl.size() - 2).getColor() == wl.get(wl.size() - 3).getColor()) {
                            wl.peek().setVisible(false);
                            heightAboveLeftBar -= wl.peek().getHeight();
                            control.remove(wl.peek());
                            wl.pop();
                            wl.peek().setVisible(false);
                            heightAboveLeftBar -= wl.peek().getHeight();
                            control.remove(wl.peek());
                            wl.pop();
                            wl.peek().setVisible(false);
                            heightAboveLeftBar -= wl.peek().getHeight();
                            control.remove(wl.peek());
                            wl.pop();
                            increase = true;
                            notifyAllObservers(increase);
                        }
                        break;
                    }
                }
                if (intersect(o, rightPeek)) {
                    if (type == 4) {
                        increase = false;
                        notifyAllObservers(increase);
                        ((Shape) o).setVisible(false);
                        moving.remove(o);
                        break;

                    } else if (type == 5) {
                        increase = true;
                        notifyAllObservers(increase);
                        ((Shape) o).setVisible(false);
                        moving.remove(o);
                        break;
                    } else {
                        heightAboveRightBar += o.getHeight();

                        if (type == 1) {
                            center = 0;
                        } else {
                            center = 10;
                        }
                        Shape shape = new Shape(rightBar.getX() - center, rightBar.getY() - heightAboveRightBar, ((Shape) o).getColor(), ((Shape) o).getType()) {
                        };
                        shape.setHorizontalOnly(true);
                        control.add(shape);
                        wr.push(shape);
                        ((Shape) o).setVisible(false);
                        moving.remove(o);
                        if (wr.size() >= 3 && wr.get(wr.size() - 1).getColor() == wr.get(wr.size() - 2).getColor() && wr.get(wr.size() - 1).getColor() == wr.get(wr.size() - 3).getColor() && wr.get(wr.size() - 2).getColor() == wr.get(wr.size() - 3).getColor()) {
                            wr.peek().setVisible(false);
                            heightAboveRightBar -= wr.peek().getHeight();
                            control.remove(wr.peek());
                            wr.pop();
                            wr.peek().setVisible(false);
                            heightAboveRightBar -= wr.peek().getHeight();
                            control.remove(wr.peek());
                            wr.pop();
                            wr.peek().setVisible(false);
                            heightAboveRightBar -= wr.peek().getHeight();
                            control.remove(wr.peek());
                            wr.pop();
                            increase = true;
                            notifyAllObservers(increase);
                        }
                        break;
                    }
                }
            }
             for (GameObject o : control) {
            if (control.get(0).getX() >= 1000) {
                control.get(1).setX(1000);
                control.get(2).setX(1030);
                control.get(3).setX(1090);
                control.get(4).setX(1125);
                for (int i = 5; i < control.size(); i++) {
                    if (control.get(i).getX() > 1030 && control.get(i).getX() < 1050) {
                        control.get(i).setX(1025);
                    }

                    if (control.get(i).getX() > 1050) {
                        control.get(i).setX(1125);
                    }
                }
            }
            if (control.get(0).getX() <= 10) {
                control.get(1).setX(0);
                control.get(2).setX(30);
                control.get(3).setX(95);
                control.get(4).setX(130);

                for (int i = 5; i < control.size(); i++) {
                    //System.out.println(control.get(i).getX());
                    if (control.get(i).getX() > 0 && control.get(i).getX() < 30) {
                        control.get(i).setX(20);
                    }

                    if (control.get(i).getX() > 50 && control.get(i).getX() < 125) {
                        control.get(i).setX(120);
                    }
                }
            }
             }
        }
        return !timeout;
    }

    @Override
    public String getStatus() {
        return "Please use Arrows to Move | Score = " + this.getScore() + " | Try to catch 3 Consecutive Shapes of Same Color | Avoid Bombs | Time=" + Math.max(0, (MAX_TIME - (endTime - startTime)) / 1000);
    }

    @Override
    public int getSpeed() {
        return 10;
    }

    @Override
    public int getControlSpeed() {
        return 10;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

}
