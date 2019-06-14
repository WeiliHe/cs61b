package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.lab11.MyTrieSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
//    point is (lat, lon), pointToNode is a map, pointsKDTree is to get the closest, lwToUpp is the lower case to upper case;
    private List<Point> points = new ArrayList<>();
    private HashMap<Point, Node> pointToNode = new HashMap<>();
    private KDTree pointsKDTree;
    private HashMap<String, String> lwToUpp = new HashMap<>();
    private MyTrieSet trieSet = new MyTrieSet();
//    private WeirdPointSet pointsWeird;
//    this is a wired data structure, should be created in another to avoid some awkward calls
    private HashMap<String, List<Node>> nameToNode = new HashMap<>();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
//         You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        for (Node node: nodes) {
//           this is preparation for the prefix method
            cleanNames(node);
//            this is for getting the points for the nearest method, exclude the nodes without neighbours
            getPoints(node);
        }
//        pointsWeird = new WeirdPointSet(points);
        pointsKDTree = new KDTree(points);
    }

    /**
        for storing the cleaned names, later used for prefix autocomplete
     */
    private void cleanNames(Node node) {
        String originName = node.name();
        if (originName != null) {
            if (originName.equals("Montano Velo")) {
                System.out.println("Montano Velo is here");
            }
            String cleanName = cleanString(originName);
//                String cleanName = cleanString(originName);
            trieSet.add(cleanName);
            lwToUpp.put(cleanName, originName);
//            this is for mapping the nodes to string, for getLocations method
            List<Node> nodeList;
            if (!nameToNode.containsKey(cleanName)) {
                nodeList = new ArrayList<>();
            } else {
                nodeList = nameToNode.get(cleanName);
            }
            nodeList.add(node);
            nameToNode.put(cleanName, nodeList);
        }
    }

    /**
     * get the list of points
     */
    private void getPoints(Node node) {
        if (neighbors(node.id()).size() != 0) {
            Point point = new Point(node.lon(), node.lat());
            points.add(point);
            pointToNode.put(point, node);
        }
    }



    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
//        Point closest = pointsWeird.nearest(lon, lat);
        Point closest = pointsKDTree.nearest(lon, lat);
        if (closest != null) {
            return pointToNode.get(closest).id();
        } else {
            return 0;
        }
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        String cleanPrefix = cleanString(prefix);
        List<String> matchKeys = trieSet.keysWithPrefix(cleanPrefix);
        List<String> originKeys = new LinkedList<>();
        for (String key: matchKeys) {
            originKeys.add(lwToUpp.get(key));
        }
        return originKeys;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Node> nodesOfLocations = nameToNode.get(cleanString(locationName));
        List locationsList = new LinkedList<>();
        for (Node node: nodesOfLocations){
            Map<String, Object> map = new HashMap<>();
            map.put("lat", node.lat());
            map.put("lon", node.lon());
            map.put("name", node.name());
            map.put("id", node.id());
            locationsList.add(map);
        }
        return locationsList;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

}
