package element;

public class Pair {

	/** priority */
	public int p;

	/** index */
	public int i;

	public Pair(int priority, int index) {
		this.p = priority;
		this.i = index;
	}

	@Override
	public String toString() {
		return "[p=" + p + ",i=" + i + "]";
	}

}