package hibernate.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Test entity - points on a grid
 * 
 * @author dkaiser
 */
@Entity
public class Point implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	private int x;
	private int y;

	public Point() { /* EMPTY, used from Hibernate */
	}

	private Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int i) {
		this(i, i);
	}

	@Override
	public String toString() {
		return String.format("%s: (%d, %d)", id, x, y);
	}
}