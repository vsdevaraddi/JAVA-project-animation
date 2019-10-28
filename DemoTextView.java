
import imt2018529.animation.Scene;
import imt2018529.animation.SceneObject;
import imt2018529.animation.View;

public class DemoTextView extends View {

	@Override
	public void clear() {
		System.out.println(" Clearing View \n");
	}

	@Override
	public void render(SceneObject s) {
		System.out.println("Object " + s.getObjName() + " at " + s.getPosition().getX()+" "+s.getPosition().getY());
	}

	@Override
	public void init() {
		Scene.getScene().animate();
	}

	@Override
	public void updateView() {
		
	}

}
