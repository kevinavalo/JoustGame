//Kevin Avalo (kwa6nf)

import java.awt.Rectangle;

public class CollisionBox {

	private Rectangle rect = new Rectangle();

	public CollisionBox(Rectangle rect) {
		this.rect = rect;
	}

	public CollisionBox(int x, int y, int width, int height) {
		this.rect = new Rectangle(x, y, width, height);
	}

	public Rectangle getRectangle() {
		return this.rect;
	}

	public boolean collidesWith(CollisionBox other) {
		boolean collision = false;
		collision = this.rect.intersects(other.getRectangle());
		return collision;
	}

	public void moveTo(int x, int y) {
		this.rect.setLocation(x, y);
	}

	public void moveCenterTo(int x, int y) {
//		double newXLocal = 0;
//		double newYLocal = 0;
//		newXLocal = (rect.getCenterX()+x) - (rect.getWidth() / 2);
//		newYLocal = (rect.getCenterY()+y) + (rect.getHeight() / 2);
//		newXLocal = Math.round(newXLocal);
//		newYLocal = Math.round(newYLocal);
//		this.rect.setLocation((int) newXLocal, (int) newYLocal);
		this.rect.setLocation(x - (1/2) * this.rect.width, y - (1/2) * this.rect.height);
	}

	public boolean isHigherThan(CollisionBox other) {
		boolean higher = true;
		Rectangle otherBox = other.getRectangle();
		double rectYCenter = this.rect.getCenterY();
		double otherBoxCenter = otherBox.getCenterY();
		if (rectYCenter < otherBoxCenter) {
			higher = true;
		} else {
			higher = false;
		}
		return higher;
	}

	public boolean isLeftOf(CollisionBox other) {
		boolean leftOf = true;
		Rectangle otherBox = other.getRectangle();
		double rectXCenter = rect.getCenterX();
		double otherBoxXCenter = otherBox.getCenterX();
		if (rectXCenter < otherBoxXCenter) {
			leftOf = true;
		} else {
			leftOf = false;
		}
		return leftOf;
	}

	public int verticalDifference(CollisionBox other) {
		int minimalYDistance = 0;
		int minimalDistCompare = 0;
		Rectangle otherBox = other.getRectangle();
		double maxRectY = this.rect.getMaxY();
		double maxOtherBoxY = otherBox.getMaxY();
		double minRectY = this.rect.getMinY();
		double minOtherBoxY = otherBox.getMinY();
		if(rect.contains(otherBox)){
			minimalYDistance = (int) (maxRectY - minOtherBoxY);
			minimalDistCompare = (int) (maxOtherBoxY - minRectY);
			if(minimalYDistance > minimalDistCompare){
				minimalYDistance = minimalDistCompare;
			}
		} else if(otherBox.contains(rect)){
			minimalYDistance = (int) (maxOtherBoxY - minRectY);
			minimalDistCompare = (int) (maxRectY - minOtherBoxY);
			if(minimalYDistance > minimalDistCompare){
				minimalYDistance = minimalDistCompare;
			}
		} else if (maxRectY < maxOtherBoxY) {
			minimalYDistance = (int) (maxRectY - minOtherBoxY);
		} else {
			minimalYDistance = (int) (maxOtherBoxY - minRectY);
		}
		return minimalYDistance;
	}

	public int horizontalDifference(CollisionBox other) {
		int minimalXDistance = 0;
		int minimalDistCompare = 0;
		Rectangle otherBox = other.getRectangle();
		double maxRectX = this.rect.getMaxX();
		double maxOtherBoxX = otherBox.getMaxX();
		double minRectX = this.rect.getMinX();
		double minOtherBoxX = otherBox.getMinX();
		if(rect.contains(otherBox)){
			minimalXDistance = (int) (maxRectX - minOtherBoxX);
			minimalDistCompare = (int) (maxOtherBoxX - minRectX);
			if(minimalXDistance > minimalDistCompare){
				minimalXDistance = minimalDistCompare;
			}
		} else if(otherBox.contains(rect)){
			minimalXDistance = (int) (maxOtherBoxX - minRectX);
			minimalDistCompare = (int) (maxRectX - minOtherBoxX);
			if(minimalXDistance > minimalDistCompare){
				minimalXDistance = minimalDistCompare;
			}
		} else if (minRectX < minOtherBoxX) {
			minimalXDistance = (int) (maxRectX - minOtherBoxX);
		} else {
			minimalXDistance = (int) (maxOtherBoxX - minRectX);
		}
		return minimalXDistance;
	}

	public boolean isSmallerOverlapVertical(CollisionBox other) {
		boolean verticalOrHorizontal = true;
		CollisionBox newRect = new CollisionBox(this.getRectangle());
		int x = newRect.horizontalDifference(other);
		int y = newRect.verticalDifference(other);
		if (x > y) {
			verticalOrHorizontal = true;
		} else {
			verticalOrHorizontal = false;
		}
		return verticalOrHorizontal;
	}
}
