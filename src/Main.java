import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet {
    final int SCREEN_WIDTH = 1920;
    final int SCREEN_HEIGHT = 1080;
    final float phi = ((float)Math.sqrt(5)+1)/2 - 1;

    final float sphereRadius = 30.0f;
    final float strokeWeight = 1.5f;
    final float rotateSpeed = 0.0005f;
    final int numberOfPoints = 800;

    PVector[] points;
    float min = Float.POSITIVE_INFINITY;
    float max = Float.NEGATIVE_INFINITY;

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }

    public void settings(){
        fullScreen(P3D);
        //size(SCREEN_WIDTH, SCREEN_HEIGHT, P3D);
    }

    public void setup() {
        generateFibSphere(numberOfPoints, sphereRadius);

        // find max and min position values for colors
        for (PVector p : points){
            if (p.x > max) {
                max = p.x;
            }
            if (p.x < min) {
                min = p.x;
            }
        }
    }

    public void draw() {
        background(25);
        drawFibSphere(strokeWeight, true, false);
    }

    void generateFibSphere(int n, float radius) {
        points = new PVector[n];

        for (int i = 0; i < points.length; i++) {
            float longitude = phi * TWO_PI * i;
            longitude /= (2 * PI);
            longitude -= floor(longitude);
            longitude *= TWO_PI;

            if (longitude > PI) {
                longitude -= (2 * PI);
            }
			
			final float latitude = (float)Math.asin(-1 + 2 * i/(float)n);
			final float cosOfLatitude = (float)Math.cos(latitude);
			points[i] = new PVector(
				radius * cosOfLatitude * (float)Math.cos(longitude),
				radius * cosOfLatitude * (float)Math.sin(longitude),
				radius * (float)Math.sin(latitude)
			);
        }
    }

    void drawFibSphere(float strokeWeight, boolean usingColor, boolean usingRGB) {
        translate((float)width/2, (float)height/2);
        scale(10);
        strokeWeight(strokeWeight);

        pushMatrix();
        rotateY(millis() * rotateSpeed);

        if (usingColor){
            if (usingRGB){
                colorMode(RGB);
                for (PVector p : points) {
                    final int r = (int)map(p.x, min, max, 0, 255);
                    final int g = (int)map(p.y, min, max, 0, 255);
                    final int b = (int)map(p.z, min, max, 0, 255);

                    stroke(r, g, b);
                    point(p.x, p.y, p.z);
                }
            }
            else {
                colorMode(HSB);
                for (PVector p : points) {
                    final int hue = (int)map(p.x, min, max, 0, 255);
                    stroke(hue, 200, 255);
                    point(p.x, p.y, p.z);
                }
            }
        }
        else {
            stroke(220);
            for (PVector p : points) {
                point(p.x, p.y, p.z);
            }
        }

        popMatrix();
    }

}
