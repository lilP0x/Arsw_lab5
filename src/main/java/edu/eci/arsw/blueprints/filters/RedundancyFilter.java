package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RedundancyFilter implements BlueprintsFilter {

    @Override
    public void filter(Blueprint bp) {
        List<Point> uniquePoints = new ArrayList<>();

        for (Point i : bp.getPoints()) {
            boolean found = false;
            for (Point j : uniquePoints) {
                if (i.equals(j)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                uniquePoints.add(i);
            }
        }

        Point[] uniquePointsArray = uniquePoints.toArray(new Point[0]);

        new Blueprint(bp.getAuthor(), bp.getName(), uniquePointsArray);
    }
}
