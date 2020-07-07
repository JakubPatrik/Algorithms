import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints_BETA {

    private List<LineSegment> lines = new ArrayList<>();

    public BruteCollinearPoints_BETA(Point[] points) {
        if (points == null || points.length == 0) {
            throw new java.lang.NullPointerException();
        }

		if(pointsHaveRepeated(points)) { 
            throw new java.lang.IllegalArgumentException("point repeat.");
        }
        
        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point p = points[i], q = points[j], r = points[k], s = points[l];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                            // StdOut.println(p + " -> " + q + " -> " + r + " -> " + s);
                            p.drawTo(s);
                            lines.add(new LineSegment(p, s));
                        }
                    }
                }
            }
        }
    }    
    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return lines.size();
    }     
    // the number of line segments
    
    public LineSegment[] segments() {
        return (LineSegment[]) lines.toArray();
    }
    // the line segments

    private static boolean pointsHaveRepeated(Point[] points){
		// n^2 for cycle check
		// NlgN if use sort() and check
		Arrays.sort(points);
		for(int i = 0; i < points.length - 1; i++){
			if (points[i].compareTo(points[i+1]) == 0)
				return true;
		}
		return false;
	}


}