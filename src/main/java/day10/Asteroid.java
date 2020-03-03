package day10;

public class Asteroid {
    private int x;
    private int y;
    private double distanceToCenter;

    public double getDistanceToCenter() {
        return distanceToCenter;
    }

    public void setDistanceToCenter(double distanceToCenter) {
        this.distanceToCenter = distanceToCenter;
    }

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

    /**
     * Zero pointing up and the degrees rise clockwise.
     */
    public float getAngle(Asteroid target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.x - this.x, this.y - target.y));

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public double getDistance(Asteroid target) {

        return Math.sqrt((target.x - this.x) * (target.x - this.x) + (target.y - this.y) * (target.y - this.y));
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
