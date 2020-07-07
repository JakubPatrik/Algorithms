import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FastCollinearPoints_BETA {
    
    private List <Map<Double,List<List<Point>>>> all = new ArrayList<>();
    private List <LineSegment> lines = new ArrayList<>();

    public FastCollinearPoints_BETA(Point[] ps) {
        if (ps == null || ps.length == 0) { 
            throw new java.lang.NullPointerException();
        }
	if(pointsHaveRepeated(ps)) { 
            throw new java.lang.IllegalArgumentException("point repeat.");
        }

        for (int i = 0; i < ps.length - 1; i++) {
            Map<Double,List<List<Point>>> d = new HashMap<Double, List<List<Point>>>();

            for (int j = i+1; j < ps.length; j++) {
                
                List<List<Point>> lPoints = new ArrayList<>();
                List<Point> pair = new ArrayList<>();

                double slope = ps[i].slopeTo(ps[j]);
                pair.add(ps[i]); pair.add(ps[j]);

                if (d.containsKey(slope)) {
                    List<List<Point>> newList = d.get(slope);
                    if ( newList.get(0).get(0).slopeTo(newList.get(0).get(1)) == slope) {
                        newList.add(pair);
                        d.put(slope, newList);
                    }
                }
                else {
                    lPoints.add(pair);
                    d.put(slope, lPoints);
                }               
            }
            all.add(d);
        }
        for (Map<Double,List<List<Point>>> d : all) {
            for ( Entry<Double, List<List<Point>>> mapElement : d.entrySet()) { 
                int count = mapElement.getValue().size();
    
                if (count >= 3) {
                    List<List<Point>> points = mapElement.getValue();
                    
                    Point min = points.get(0).get(0);
                    Point max = points.get(0).get(1);
                    
                    for (List<Point> pair : points) {
                        Point localMax, localMin;
                        if (pair.get(0).compareTo(pair.get(1)) == 1) {
                            localMax = pair.get(0);
                            localMin = pair.get(1);
                        }
                        else { 
                            localMin = pair.get(0);
                            localMax = pair.get(1);
                        }
    
    
                        if (localMax.compareTo(max) == 1) {
                            max = localMax;
                        }
                        else if (localMin.compareTo(min) == -1) {
                            min = localMin;
                        }
                    }
    
                    LineSegment line = new LineSegment(min, max);
                    lines.add(line);
                    line.draw();
                }
            }    
        }
       
    }  // finds all line segments containing 4 points
    
    public int numberOfSegments() {
        return lines.size();

    }        // the number of line segments
    
    public LineSegment[] segments() {
        LineSegment[] res = (LineSegment[]) lines.toArray();
        return res;
    }                // the line segments

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