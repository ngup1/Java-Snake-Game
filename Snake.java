import java.util.Deque;
import java.util.LinkedList;

public class Snake {
    private final Deque<GamePoint> body = new LinkedList<>();

    public Snake(GamePoint startPosition) {
        body.add(startPosition);
    }

    public GamePoint getHead() {
        return body.getFirst();
    }

    public void grow(GamePoint newHead) {
        body.addFirst(newHead);
    }

    public void move(GamePoint newHead) {
        body.addFirst(newHead);
        body.removeLast();
    }

    public boolean contains(GamePoint point) {
        return body.contains(point);
    }

    public void reset(GamePoint start) {
        body.clear();
        body.add(start);
    }

    public int size() {
        return body.size();
    }

    public Iterable<GamePoint> getBody() {
        return body;
    }
}
