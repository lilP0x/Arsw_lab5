package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * In-memory implementation of BlueprintsPersistence.
 * Stores blueprints in a HashMap.
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence {

    private final ConcurrentHashMap<Tuple<String, String>, Blueprint> blueprints = new ConcurrentHashMap<>();
    public InMemoryBlueprintPersistence() {
        Point[] pts1 = new Point[]{new Point(140, 140), new Point(115, 115), new Point(130, 130)};
        Point[] pts2 = new Point[]{new Point(200, 150), new Point(180, 170), new Point(190, 120)};
        Point[] pts3 = new Point[]{new Point(100, 100), new Point(120, 130), new Point(140, 160)};
        Point[] pts4 = new Point[]{new Point(50, 60), new Point(70, 80), new Point(90, 110)};
        Point[] pts5 = new Point[]{new Point(160, 170), new Point(180, 190), new Point(150, 160)};

        Blueprint bp = new Blueprint("_authorname_", "_bpname_", pts1);
        Blueprint bp1 = new Blueprint("Monica", "_bpname1_", pts2);
        Blueprint bp2 = new Blueprint("Valentina", "_bpname2_", pts3);
        Blueprint bp3 = new Blueprint("Laura", "_bpname3_", pts4);
        Blueprint bp4 = new Blueprint("Laura", "_bpname4_", pts5);


        blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        blueprints.put(new Tuple<>(bp1.getAuthor(), bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(), bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(), bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(), bp4.getName()), bp4);


    }

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(), bp.getName()))) {
            throw new BlueprintPersistenceException("The given blueprint already exists: " + bp);
        } else {
            blueprints.put(new Tuple<>(bp.getAuthor(), bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        Blueprint blueprint = blueprints.get(new Tuple<>(author, bprintname));
        if (blueprint == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + ", " + bprintname);
        }
        return blueprint;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        return Collections.unmodifiableSet(new HashSet<>(blueprints.values()));
    }

    @Override
    public List<Tuple> getAllKeys() {
        return new ArrayList<>(blueprints.keySet());
    }

    @Override
    public Blueprint getBlueprintByAuthor(String author) throws BlueprintNotFoundException {
        List<Blueprint> authorBlueprints = new ArrayList<>();

        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            if (entry.getKey().getElem1().equals(author)) {
                authorBlueprints.add(entry.getValue());
            }
        }

        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }

        return authorBlueprints.get(0);
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> authorBlueprints = new HashSet<>();

        for (Map.Entry<Tuple<String, String>, Blueprint> entry : blueprints.entrySet()) {
            if (entry.getKey().getElem1().equals(author)) {
                authorBlueprints.add(entry.getValue());
            }
        }

        if (authorBlueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for author: " + author);
        }

        return Collections.unmodifiableSet(authorBlueprints);
    }
}
