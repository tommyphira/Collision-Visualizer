public class CollisionLogger {

	private int screenWidth;
	private int screenHeight;
	private int bucketWidth;
	private int[][] buckets;

	public CollisionLogger(int screenWidth, int screenHeight, int bucketWidth) {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.bucketWidth = bucketWidth;
		buckets = new int[screenHeight / bucketWidth][screenWidth / bucketWidth];
		for (int i = 0; i < buckets.length; i++) {
			for (int j = 0; j < buckets[0].length; j++) {
				buckets[i][j] = 0;
			}
		}
	}

	public void collide(Ball one, Ball two) {
		// Get Average Y Position
		int avgYPos = ((int) one.getYPos() + (int) two.getYPos()) / 2;
		// Get Average x Position
		int avgXPos = ((int) one.getXPos() + (int) two.getXPos()) / 2;

		int yGrid = Math.abs(avgYPos / bucketWidth);
		int xGrid = Math.abs(avgXPos / bucketWidth);
		if (yGrid < (this.screenHeight / this.bucketWidth)) {
			if (xGrid < (this.screenWidth / this.bucketWidth)) {
				buckets[yGrid][xGrid] += 1;
			}
		}
	}

	public int[][] getNormalizedHeatMap() {
		int[][] normalizedHeatMap = new int[this.screenHeight / this.bucketWidth][this.screenWidth / this.bucketWidth];
		for (int i = 0; i < normalizedHeatMap.length; i++) {
			for (int j = 0; j < normalizedHeatMap[i].length; j++) {
				normalizedHeatMap[i][j] = 0;
			}
		}

		int maxValue = 0;
		for (int i = 0; i < buckets.length; i++) {
			for (int j = 0; j < buckets[i].length; j++) {
				if (maxValue < buckets[i][j])
					maxValue = buckets[i][j];
			}
		}
		for (int i = 0; i < buckets.length; i++) {
			for (int j = 0; j < buckets[i].length; j++) {
				if (maxValue != 0 && maxValue == buckets[i][j])
					normalizedHeatMap[i][j] = 255;
			}
		}

		return (normalizedHeatMap);
	}
}
