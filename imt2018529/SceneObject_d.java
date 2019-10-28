package imt2018529;

import imt2018529.animation.SceneObject;
import imt2018529.animation.Point;
import imt2018529.animation.BBox;
import imt2018529.animation.Scene;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
public class SceneObject_d extends SceneObject{
    private String objname = "a"; 
    private Point position;
    private Point destposition;
    private BBox bbox;
    private ArrayList outpoints = new ArrayList<Point>();
    private int flag=0;
    private static int dx=10;
    private static int dy=10;
    private static int outline_resolution = 3;
    private static int max_size=20;
    public SceneObject_d(){
        super();
        
    }
    // public SceneObject_d(String objname,java.awt.Point position){
    //     this.objname=objname;
    //     this.position.setPos(position.getX(),position.getY());
    //     this.setOutline();
    // }
    public String getObjName(){
        return this.objname;
    }
    public Point getPosition(){
        return this.position;
    }
    public void setPosition(int x,int y){
        this.position=new Point(x,y);
        this.setOutline();
    }
    public void setDestPosition(int x,int y){
        this.destposition=new Point(x,y);
    }
    public BBox getBBox(){
        return this.bbox;
    }
    protected ArrayList<Point> getOutline(){
        return this.outpoints;
    }
    public void flagit(){
        flag=1;
    }
    private void setOutline(){
        int i=0;
        int maxx=this.position.getX()+20;
        int maxy=this.position.getY()+20;
        int minx=this.position.getX()-20;
        int miny=this.position.getY()-20;
        Random rand = new Random();
        // for(i=0;i<SceneObject_d.outline_resolution;i++){
        //     int x=this.position.getX()+1+rand.nextInt(SceneObject_d.max_size)*(int)Math.pow(-1,i);
        //     int y=this.position.getY()+1+rand.nextInt(SceneObject_d.max_size)*(int)Math.pow(-1,i);
        //     this.outpoints.add(new Point(x,y));
        //     if(x<minx){
        //         minx=x;
        //     }
        //     if(x>maxx){
        //         maxx=x;
        //     }    
        //     if(y<miny){
        //         miny=y;
        //     }
        //     if(y>maxy){
        //         maxy=y;
        //     }
        // }
        // THIS NEED TO BE CHANGED
        this.bbox=new BBox_d(new Point(minx,miny),new Point(maxx,maxy));
    }
    private synchronized boolean checkanddo(int dx,int dy,int slope){
        Scene scene=Scene_d.getScene();
        ArrayList<SceneObject> actors=scene.getActors();
        ArrayList<SceneObject> obstacles = scene.getObstacles();
        int collision_flag=0;
        this.setPosition(this.getPosition().getX()+dx, this.getPosition().getY()+dy);
        for(SceneObject sceneobject2:actors){           
            if(sceneobject2!=this){
                    BBox bbox2 =sceneobject2.getBBox();   
                    if(bbox2.intersects(this.bbox)){
                        collision_flag=1;
                        break;
                    }
            }
        } 
        for(SceneObject sceneobject3:obstacles){
                if(sceneobject3!=this){
                    BBox bbox3 = sceneobject3.getBBox();   
                    if(bbox3.intersects(this.bbox)){
                        collision_flag=1;
                        break;
                    }
                }
        }
        //updating position
        if(collision_flag==1){
            this.position.setPos(this.position.getX()-dx,this.position.getY()-dy);
        }
        else{
            Point min=this.bbox.getMinPt();
            Point max=this.bbox.getMaxPt();
            min.setPos(min.getX()+dx,min.getY()+dy);
            max.setPos(max.getX()+dx,max.getY()+dy);
            BBox newbbox = new BBox_d(min,max);//Needs to be changed
            this.bbox=newbbox;
        }
        if(collision_flag==1){
            return true;
        }
        return false;
    }
    protected void updatePos(int deltaT){
        int slope=(this.position.getY()-this.destposition.getY())/(this.position.getX()-this.destposition.getX());
        int xslope=this.destposition.getX()-this.getPosition().getX();
        int yslope=this.destposition.getY()-this.getPosition().getY();
        int dx,dy;
        if(xslope<0){
            dx=-1*SceneObject_d.dx;
        }
        else{
            dx=SceneObject_d.dx;
        }
        if(yslope<0){
            dy=-1*SceneObject_d.dy;    
        }
        else{
            dy=SceneObject_d.dy;
        }
        if(slope<1){
            dy=slope*dx;
        }
        else{
            dx=dy/slope;
        }
        checkanddo(dx,dy,slope);
    }

}