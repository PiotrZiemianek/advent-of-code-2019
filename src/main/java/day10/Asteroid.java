package day10;

public class Asteroid {
    private int x;
    private int y;

    public Asteroid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getAngle(Asteroid target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.y - y, target.x - x));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    @Override
    public String toString() {
        return "A{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Asteroid asteroid = (Asteroid) o;

        if (x != asteroid.x) return false;
        return y == asteroid.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
