package com.example.planets;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import java.io.IOException;
import javafx.geometry.Point2D;

public class HelloApplication extends Application {
    public Object[] names = new Object[4];
    public double[][] propdata = new double[4][2]; //mass, radius
    public double[][] gvalues = new double[4][4]; //gravity values
    public Point2D[][] motiondata = new Point2D[4][3]; //position, velocity, acceleration
    public Point2D[][] dirs = new Point2D[4][4]; //acceleration directions
    public double Gconst = 6.67 * Math.pow(10, -11);
    public void start(Stage stage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root, 1400, 1000, Color.BLACK);
        stage.setScene(scene);
        stage.show();


        Circle sun = new Circle();
        names[0] =  sun;
        Circle jupiter = new Circle();
        names[1] =  jupiter;
        Circle earth = new Circle();
        names[2] =  earth;
        Circle mars = new Circle();
        names[3] =  mars;


        propdata[0][0] = 2 * Math.pow(10, 30);
        propdata[1][0] = 1.9 * Math.pow(10, 27);
        propdata[2][0] = 3.7 * Math.pow(10, 24);
        propdata[3][0] = 6.4 * Math.pow(10, 23);


        propdata[0][1] = 20;
        propdata[1][1] = 10;
        propdata[2][1] = 5;
        propdata[3][1] = 4;


        motiondata[0][0] = new Point2D(700,500);
        motiondata[1][0] = new Point2D(400, 500);
        motiondata[2][0] = new Point2D(550, 500);
        motiondata[3][0] = new Point2D(500, 500);



        setCircle(sun, root, propdata[0][1], motiondata[0][0].getX(), motiondata[0][0].getY(), "RED");
        setCircle(jupiter, root, propdata[1][1], motiondata[1][0].getX(), motiondata[1][0].getY(), "YELLOW");
        setCircle(earth, root, propdata[2][1], motiondata[1][0].getX(), motiondata[1][0].getY(), "BLUE");
        setCircle(mars, root, propdata[3][1], motiondata[1][0].getX(), motiondata[1][0].getY(), "ORANGE");



        motiondata[0][1] = new Point2D(0, 0);
        motiondata[1][1] = new Point2D(0, 7);
        motiondata[2][1] = new Point2D(0, -10);
        motiondata[3][1] = new Point2D(0, 9);



        AnimationTimer timer = new AnimationTimer() {
            public void handle(long now) {
                updateg(.0001);
                update();


                updatepos(sun);
                updatepos(jupiter);
                updatepos(earth);
                updatepos(mars);
            }
        };
        timer.start();
    }


    public void setCircle(Circle name, Group root, double radius, double x, double y, String color) {
        name.setRadius(radius);
        name.setCenterX(x);
        name.setCenterY(y);
        name.setFill(Color.web(color));
        root.getChildren().add(name);
    }


    public void updatepos(Circle circle){
        circle.setCenterX(motiondata[fe(circle)][0].getX());
        circle.setCenterY(motiondata[fe(circle)][0].getY());
    }


    //find element in array
    public int fe(Object name){
        for (int i = 0; i < names.length; i++) {
            if (names[i] == name){
                return i;
            }
        }
        return -1;
    }


    public void update(){
        for (int i = 0; i < names.length; i++) {
            //acceleration
            accdir();
            motiondata[i][2] = new Point2D(0,0);
            for (int j = 0; j < names.length; j++) {
                motiondata[i][2] = new Point2D(motiondata[i][2].getX() + gvalues[i][j] * dirs[i][j].getX(), motiondata[i][2].getY() + gvalues[i][j] * dirs[i][j].getY());
            }
            //velocity
            motiondata[i][1] = motiondata[i][1].add(motiondata[i][2]);
            //position
            motiondata[i][0] = motiondata[i][0].add(motiondata[i][1]);
        }

    }


    public void accdir(){
        for (int i = 0; i < motiondata.length; i++) {
            for (int j = 0; j < motiondata.length; j++) {
                dirs[i][j] = (motiondata[j][0].subtract(motiondata[i][0])).normalize();
            }
        }
    }


    public double sq(double number){
        return number * number;
    }


    public void updateg(double mult) {
        for (int i =0; i < names.length; i++) {
            for (int j = 0; j < motiondata.length; j++) {
                if (motiondata[i][0] == motiondata[j][0]){
                    gvalues[i][j] = 0;
                } else {
                    gvalues[i][j] = mult * (Gconst * propdata[j][0]) / (((sq(motiondata[j][0].getX() - motiondata[i][0].getX())) + sq(motiondata[j][0].getY() - motiondata[i][0].getY())) * sq(Math.pow(10, 6)));
                }
            }
        }
    }


    public void pn(String string){
        System.out.println(string);
    }
}



