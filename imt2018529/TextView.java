package imt2018529;
import imt2018529.animation.SceneObject;
import imt2018529.animation.View;

public class TextView extends View {

	@Override
	public void clear() {
		System.out.println(" Clearing View \n");
	}

	@Override
	public void render(SceneObject s) {
		System.out.println("Object " + s.getObjName() + " at " + s.getPosition().getX
		()+" "+s.getPosition().getY());
	}

}
