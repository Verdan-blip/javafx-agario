package ru.kpfu.itis.bagaviev.agario.engine.geometrics;

public class CircleGeometrics {

    private static float sqr(float number) {
        return number * number;
    }

    public static boolean isInCircle(float pointX, float pointY, float centerX, float centerY, float radius) {
        return sqr(pointX - centerX) + sqr(pointY - centerY) <= sqr(radius);
    }

    public static boolean circlesPenetrate(
            float centerX1, float centerY1, float radius1,
            float centerX2, float centerY2, float radius2, float penetrationCoefficient
    ) {
        float distance = (float) Math.sqrt(sqr(centerX1 - centerX2) + sqr(centerY1 - centerY2));
        float penetration = radius1 + radius2 - distance;
        float minRadius = Math.min(radius1, radius2);
        return Double.compare(penetration, minRadius * penetrationCoefficient) > 0;
    }

    public static boolean circlesIntersect(
            float centerX1, float centerY1, float radius1,
            float centerX2, float centerY2, float radius2
    ) {
        float distance = (float) Math.sqrt(sqr(centerX1 - centerX2) + sqr(centerY1 - centerY2));
        return (radius1 + radius2) > distance;
    }

    public static float getCircleIntersectionProjectionX(float centerX1, float centerY1,
                                                         float centerX2, float centerY2, float radius2) {
        float distance = (float) Math.sqrt(sqr(centerX1 - centerX2) + sqr(centerY1 - centerY2));
        return (radius2 * (centerX2 - centerX1)) / distance;
    }

    public static float getCirclesIntersectionProjectionY(float centerX1, float centerY1,
                                                          float centerX2, float centerY2, float radius2) {
        float distance = (float) Math.sqrt(sqr(centerX1 - centerX2) + sqr(centerY1 - centerY2));
        return (radius2 * (centerY1 - centerY2)) / distance;
    }

}
