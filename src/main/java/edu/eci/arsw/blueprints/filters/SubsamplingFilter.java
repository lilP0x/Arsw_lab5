package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

import java.util.ArrayList;
import java.util.List;


public class SubsamplingFilter implements BlueprintsFilter {
    @Override
    public void filter(Blueprint bp) {
        List<Point> oldPoints = bp.getPoints();
        List<Point> sampledPoints = new ArrayList<>();

        for (int i = 0; i < oldPoints.size(); i++) {
            if (i % 2 == 0) {
                sampledPoints.add(oldPoints.get(i));
            }
        }

        // Convert ArrayList to Point array
        Point[] sampledPointsArray = sampledPoints.toArray(new Point[0]);

        new Blueprint(bp.getAuthor(), bp.getName(), sampledPointsArray);
    }
}
