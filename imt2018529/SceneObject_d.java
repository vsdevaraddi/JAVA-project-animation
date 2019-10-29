package imt2018529;

import imt2018529.animation.SceneObject;
import imt2018529.animation.Point;
import imt2018529.animation.BBox;
import imt2018529.animation.Scene;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SceneObject_d extends SceneObject {
    private String objname = "default";
    private Point position;
    private Point destposition;
    private BBox bbox;
    private ArrayList<Point> outpoints = new ArrayList<Point>();
    private int flag = 0;
    private static int dx = 10;
    private static int dy = 10;
    private static int outline_resolution = 3;
    private static int max_size = 20;
    private static int id = -1;
    private int token = 0;//This helps in sort of making selction of path randomised IF THERE IS COLLISSION IN SHORTEST PATH

    public SceneObject_d() {
        super();
        this.objname = "Object " + SceneObject_d.id;
        SceneObject_d.id = SceneObject_d.id + 1;
    }

    public SceneObject_d(String objname, Point position) {
        this.objname = objname;
        this.position.setPos(position.getX(), position.getY());
        this.setOutline();
    }

    public String getObjName() {
        return this.objname;
    }

    public Point getPosition() {
        return this.position;
    }

    // this also defines the outline
    public void setPosition(int x, int y) {
        this.position = new Point(x, y);
        this.setOutline();
    }

    public void setDestPosition(int x, int y) {
        this.destposition = new Point(x, y);
    }

    public BBox getBBox() {
        return this.bbox;
    }

    protected ArrayList<Point> getOutline() {
        return this.outpoints;
    }

    public void flagit() {
        flag = 1;
    }
    //define the BBox and the outline can be randomised also
    private void setOutline() {
        int i = 0;
        int maxx = this.position.getX() + 10;
        int maxy = this.position.getY() + 10;
        int minx = this.position.getX() - 10;
        int miny = this.position.getY() - 10;
        this.bbox = new BBox_d(new Point(minx, miny), new Point(maxx, maxy));
        this.outpoints.add(new Point(minx,miny));
        this.outpoints.add(new Point(maxx,miny));
        this.outpoints.add(new Point(maxx,maxy));
        this.outpoints.add(new Point(minx,maxy));
    }

    private  boolean checkanddo(int dx, int dy) {
        Scene scene = Scene_d.getScene();
        ArrayList<SceneObject> actors = scene.getActors();
        ArrayList<SceneObject> obstacles = scene.getObstacles();
        int collision_flag = 0;
        // update to predicted position
        this.setPosition(this.getPosition().getX() + dx, this.getPosition().getY() + dy);
        Point min = this.bbox.getMinPt();
        Point max = this.bbox.getMaxPt();
        min.setPos(min.getX() + dx, min.getY() + dy);
        max.setPos(max.getX() + dx, max.getY() + dy);
        BBox newbbox = new BBox_d(min, max);// Needs to be changed
        this.bbox = newbbox;
        // Check is there any collision at updated position
        for (SceneObject sceneobject2 : actors) {
            if (sceneobject2 != this) {
                BBox bbox2 = sceneobject2.getBBox();
                if (bbox2.intersects(this.bbox)) {
                    collision_flag = 1;
                    break;
                }
            }
        }
        for (SceneObject sceneobject3 : obstacles) {
            if (sceneobject3 != this) {
                BBox bbox3 = sceneobject3.getBBox();
                if (bbox3.intersects(this.bbox)) {
                    collision_flag = 1;
                    break;
                }
            }
        }
        // if collision happened the return back to its original position
        if (collision_flag == 1) {
            this.position.setPos(this.position.getX() - dx, this.position.getY() - dy);
            Point min1 = this.bbox.getMinPt();
            Point max1 = this.bbox.getMaxPt();
            min1.setPos(min1.getX() - dx, min1.getY() - dy);
            max1.setPos(max1.getX() - dx, max1.getY() - dy);
            BBox newbbox1 = new BBox_d(min1, max1);// Needs to be changed
            this.bbox = newbbox1;
        }
        else{
            for(Point x:this.outpoints){
                Point newp=new Point(x.getX()+dx,x.getY()+dy);
                x=newp;
            }
        }
        if (collision_flag == 1) {
            return false;
        }
        return true;
    }

    private double distance(Point a, Point b) {
        int x = a.getX() - b.getX();
        int y = a.getY() - b.getY();
        return Math.sqrt(x * x + y * y);
    }

    protected void updatePos(int deltaT) {
        if (this.distance(this.position, this.destposition) < 5) {
            return;
        }
        // compute the slope of line joining current position and destination position
        int slope = (this.position.getY() - this.destposition.getY())
                / (this.position.getX() - this.destposition.getX());
        // compute the Vector's x component and y component
        int xslope = this.destposition.getX() - this.getPosition().getX();
        int yslope = this.destposition.getY() - this.getPosition().getY();
        int dx, dy;
        // Below if statements make sure that the desplacement is not more than
        // SceneObject_d.dx and ScenObject_d.dy
        if (xslope < 0) {
            dx = -1 * SceneObject_d.dx;
        } else {
            dx = SceneObject_d.dx;
        }
        if (yslope < 0) {
            dy = -1 * SceneObject_d.dy;
        } else {
            dy = SceneObject_d.dy;
        }
        if (slope < 1) {
            dy = slope * dx;
        } else {
            dx = dy / slope;
        }
        // try different different approaches along Vector
        // just below is shortest path
        if (checkanddo(dx, dy)) {
            return;
        }
        // the option other than shortest path is not fixed by using token
        this.token=this.token+1;
        if (checkanddo(dx, -dy) && this.token % 3 == 0) {

            return;
        }
        if (checkanddo(-dx, dy) && this.token % 3 == 1) {

            return;

        }
        if (checkanddo(-dx, -dy) && this.token % 3 == 2) {

            return;
        }

    }

}