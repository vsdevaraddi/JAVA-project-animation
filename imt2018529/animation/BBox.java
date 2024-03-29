package imt2018529.animation;

public interface BBox {
	
	// return the bottom-left corner of the bounding box
	abstract public Point getMinPt();
	
	// return the top-left corner of the bounding box
	abstract public Point getMaxPt();
	
	// does this box intersect/overlap the input BBox
	public boolean intersects(BBox b);
	
}
