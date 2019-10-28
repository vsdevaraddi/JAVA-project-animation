import imt2018529.animation.Scene;
import imt2018529.animation.SceneObject;
import imt2018529.animation.View;
import imt2018529.SceneObject_d;
import imt2018529.Scene_d;


// Driver class to set up and exercise the animation
public class Demo {

	public static void main(String[] args) {
		Scene scene = new Scene_d(); // replace with your implementation

		// populate the scene with objects implemented by the team
		for (int i = 0; i < 6; i++) {
			SceneObject s = new SceneObject_d(); // replace with your implementation
			s.setPosition(i * 50, i * 50);
			scene.addObstacle(s); // using appropriate derived classes
		}

		for (int i = 0; i < 6; i++) {
			SceneObject s = new SceneObject_d(); // replace with your implementation
			s.setPosition(500 - i * 50, 300 + i * 50); // these will be changed for the demo
			s.setDestPosition(0, 0);
			scene.addActor(s); // using appropriate derived classes
		}

		 //View view = new DemoTextView();
		View view = new DemoSwingView();

		scene.setView(view);

		view.init();

	}

}
