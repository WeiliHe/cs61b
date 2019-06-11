package bearmaps.proj2c.server.handler.impl;

import bearmaps.proj2c.AugmentedStreetMapGraph;
import bearmaps.proj2c.server.handler.APIRouteHandler;
import spark.Request;
import spark.Response;
import bearmaps.proj2c.utils.Constants;
import bearmaps.proj2c.utils.Tuple;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static bearmaps.proj2c.utils.Constants.SEMANTIC_STREET_GRAPH;
import static bearmaps.proj2c.utils.Constants.ROUTE_LIST;

/**
 * Handles requests from the web browser for map images. These images
 * will be rastered into one large image to be displayed to the user.
 * @author rahul, Josh Hug, _________
 */
public class RasterAPIHandler extends APIRouteHandler<Map<String, Double>, Map<String, Object>> {

    /**
     * Each raster request to the server will have the following parameters
     * as keys in the params map accessible by,
     * i.e., params.get("ullat") inside RasterAPIHandler.processRequest(). <br>
     * ullat : upper left corner latitude, <br> ullon : upper left corner longitude, <br>
     * lrlat : lower right corner latitude,<br> lrlon : lower right corner longitude <br>
     * w : user viewport window width in pixels,<br> h : user viewport height in pixels.
     **/
    private static final String[] REQUIRED_RASTER_REQUEST_PARAMS = {"ullat", "ullon", "lrlat",
            "lrlon", "w", "h"};

    /**
     * The result of rastering must be a map containing all of the
     * fields listed in the comments for RasterAPIHandler.processRequest.
     **/
    private static final String[] REQUIRED_RASTER_RESULT_PARAMS = {"render_grid", "raster_ul_lon",
            "raster_ul_lat", "raster_lr_lon", "raster_lr_lat", "depth", "query_success"};


//    this is actually hard to implement if the map is large, especially for the latitude, how do you know it's north or south
    private boolean coverOrnot(double ullon, double lrlon, double ullat, double lrlat) {
        if (Constants.ROOT_LRLON - ullon < 0 & Constants.ROOT_ULLON - ullon < 0) {
            return false;
        } else if (Constants.ROOT_LRLON - lrlon > 0 & Constants.ROOT_ULLON - lrlon > 0) {
            return false;
        } else if (lrlat > Constants.ROOT_ULLAT & ullat > Constants.ROOT_ULLAT) {
            return false;
        } else if (lrlat < Constants.ROOT_LRLAT & ullat < Constants.ROOT_LRLAT) {
            return false;
        } else {
            return true;
        }
    }

//    the start num of the images
    private Tuple<Integer, Integer> getHorizStartEnd(double ullon, double lrlon, int imgNum){
//        Longitudes per image
//        number of images in this depth
        double LonImg = (Constants.ROOT_LRLON - Constants.ROOT_ULLON) / imgNum;
        int start = 0;
        int end = 0;
        start = (int) Math.floor((ullon - Constants.ROOT_ULLON) / Math.abs(LonImg));
        end = (int) Math.floor(Math.abs(lrlon - Constants.ROOT_ULLON) / Math.abs(LonImg));
        start = Math.max(0, start);
        end = Math.min(imgNum - 1, end);
        Tuple horizStartEnd = new Tuple(start, end);
        return horizStartEnd;
    }

    private Tuple<Integer, Integer> getVerticalStartEnd(double ullat, double lrlat, int imgNum){
//        Longitudes per image
        double LatImg = (Constants.ROOT_ULLAT - Constants.ROOT_LRLAT) / imgNum;
        int start = 0;
        int end = 0;
        start  = (int) Math.floor(Math.abs(Constants.ROOT_ULLAT - ullat) / Math.abs(LatImg));
        end = (int) Math.floor(Math.abs(Constants.ROOT_ULLAT - lrlat) / Math.abs(LatImg));
        start = Math.max(0, start);
        end = Math.min(imgNum - 1, end);
        Tuple VerticalStartEnd = new Tuple(start, end);
        return VerticalStartEnd;
    }

    private Map<String, Object> getReturn(Map<String, Double> requestParams) {
        double ullon = requestParams.get("ullon");
        double lrlon = requestParams.get("lrlon");
        double ullat = requestParams.get("ullat");
        double lrlat = requestParams.get("lrlat");
        double w = requestParams.get("w");
        double h = requestParams.get("h");
//        from left to right, from up to down
        double totalLon = lrlon - ullon;
        double totalLat = lrlat - ullat;
        Map<String, Object> returnParams = new HashMap<>();
        String[][] images = new String[0][0];
        if (!coverOrnot(ullon, lrlon, ullat, lrlat)) {
            returnParams = queryFail();
            return returnParams;
        } else {
            double RootLonDPP = (Constants.ROOT_LRLON - Constants.ROOT_ULLON) / Constants.TILE_SIZE;
            double boxLonDPP = (lrlon - ullon) / w;
            int Depth = (int) Math.ceil((Math.log(RootLonDPP / boxLonDPP) / Math.log(2)));
            Depth = Math.min(Depth, Constants.Depth);
            int imgNum = (int) Math.pow(2 / 1.0, Depth / 1.0);
//            need to decide the start index of the images (longitude to start with)
            Tuple<Integer, Integer> horizStartEnd = getHorizStartEnd(ullon, lrlon, imgNum);
            Tuple<Integer, Integer> verticalStartEnd = getVerticalStartEnd(ullat, lrlat, imgNum);

            int shape0 = horizStartEnd.getSecond() - horizStartEnd.getFirst() + 1;
            int shape1 = verticalStartEnd.getSecond() - verticalStartEnd.getFirst() + 1;
            images = new String[shape1][shape0];
            for (int i = 0; i < shape1; i++) {
                for (int j = 0; j < shape0; j++) {
                    images[i][j] = String.format("d%1$d_x%2$d_y%3$d.png", Depth, horizStartEnd.getFirst() + j, verticalStartEnd.getFirst()+ i);
                }
            }
            returnParams.put("render_grid", images);
            returnParams.put("raster_ul_lon", Constants.ROOT_ULLON + (Constants.ROOT_LRLON - Constants.ROOT_ULLON )* horizStartEnd.getFirst() / imgNum);
            returnParams.put("raster_ul_lat", Constants.ROOT_ULLAT - (Constants.ROOT_ULLAT - Constants.ROOT_LRLAT )* verticalStartEnd.getFirst() / imgNum);
            returnParams.put("raster_lr_lon", Constants.ROOT_ULLON + (Constants.ROOT_LRLON - Constants.ROOT_ULLON )* (horizStartEnd.getSecond() + 1)/ imgNum);
            returnParams.put("raster_lr_lat", Constants.ROOT_ULLAT - (Constants.ROOT_ULLAT - Constants.ROOT_LRLAT )* (verticalStartEnd.getSecond() + 1) / imgNum);
            returnParams.put("depth", Depth);
            returnParams.put("query_success", true);
        }
        return returnParams;
    }


    @Override
    protected Map<String, Double> parseRequestParams(Request request) {
        return getRequestParams(request, REQUIRED_RASTER_REQUEST_PARAMS);
    }
    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param requestParams Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @param response : Not used by this function. You may ignore.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image;
     *                    can also be interpreted as the length of the numbers in the image
     *                    string. <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    @Override
    public Map<String, Object> processRequest(Map<String, Double> requestParams, Response response) {
        System.out.println("yo, wanna know the parameters given by the web browser? They are:");
        System.out.println(requestParams);
        Map<String, Object> results = getReturn(requestParams);
//        System.out.println("Since you haven't implemented RasterAPIHandler.processRequest, nothing is displayed in "
//                + "your browser.");
        System.out.println("results"+ results);
        return results;
    }

    @Override
    protected Object buildJsonResponse(Map<String, Object> result) {
        boolean rasterSuccess = validateRasteredImgParams(result);

        if (rasterSuccess) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            writeImagesToOutputStream(result, os);
            String encodedImage = Base64.getEncoder().encodeToString(os.toByteArray());
            result.put("b64_encoded_image_data", encodedImage);
        }
        return super.buildJsonResponse(result);
    }

    private Map<String, Object> queryFail() {
        Map<String, Object> results = new HashMap<>();
        results.put("render_grid", null);
        results.put("raster_ul_lon", 0);
        results.put("raster_ul_lat", 0);
        results.put("raster_lr_lon", 0);
        results.put("raster_lr_lat", 0);
        results.put("depth", 0);
        results.put("query_success", false);
        return results;
    }

    /**
     * Validates that Rasterer has returned a result that can be rendered.
     * @param rip : Parameters provided by the rasterer
     */
    private boolean validateRasteredImgParams(Map<String, Object> rip) {
        for (String p : REQUIRED_RASTER_RESULT_PARAMS) {
            if (!rip.containsKey(p)) {
                System.out.println("Your rastering result is missing the " + p + " field.");
                return false;
            }
        }
        if (rip.containsKey("query_success")) {
            boolean success = (boolean) rip.get("query_success");
            if (!success) {
                System.out.println("query_success was reported as a failure");
                return false;
            }
        }
        return true;
    }

    /**
     * Writes the images corresponding to rasteredImgParams to the output stream.
     * In Spring 2016, students had to do this on their own, but in 2017,
     * we made this into provided code since it was just a bit too low level.
     */
    private  void writeImagesToOutputStream(Map<String, Object> rasteredImageParams,
                                                  ByteArrayOutputStream os) {
        String[][] renderGrid = (String[][]) rasteredImageParams.get("render_grid");
        int numVertTiles = renderGrid.length;
        int numHorizTiles = renderGrid[0].length;

        BufferedImage img = new BufferedImage(numHorizTiles * Constants.TILE_SIZE,
                numVertTiles * Constants.TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics graphic = img.getGraphics();
        int x = 0, y = 0;

        for (int r = 0; r < numVertTiles; r += 1) {
            for (int c = 0; c < numHorizTiles; c += 1) {
                graphic.drawImage(getImage(Constants.IMG_ROOT + renderGrid[r][c]), x, y, null);
                x += Constants.TILE_SIZE;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += Constants.TILE_SIZE;
                }
            }
        }

        /* If there is a route, draw it. */
        double ullon = (double) rasteredImageParams.get("raster_ul_lon"); //tiles.get(0).ulp;
        double ullat = (double) rasteredImageParams.get("raster_ul_lat"); //tiles.get(0).ulp;
        double lrlon = (double) rasteredImageParams.get("raster_lr_lon"); //tiles.get(0).ulp;
        double lrlat = (double) rasteredImageParams.get("raster_lr_lat"); //tiles.get(0).ulp;

        final double wdpp = (lrlon - ullon) / img.getWidth();
        final double hdpp = (ullat - lrlat) / img.getHeight();
        AugmentedStreetMapGraph graph = SEMANTIC_STREET_GRAPH;
        List<Long> route = ROUTE_LIST;

        if (route != null && !route.isEmpty()) {
            Graphics2D g2d = (Graphics2D) graphic;
            g2d.setColor(Constants.ROUTE_STROKE_COLOR);
            g2d.setStroke(new BasicStroke(Constants.ROUTE_STROKE_WIDTH_PX,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            route.stream().reduce((v, w) -> {
                g2d.drawLine((int) ((graph.lon(v) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(v)) * (1 / hdpp)),
                        (int) ((graph.lon(w) - ullon) * (1 / wdpp)),
                        (int) ((ullat - graph.lat(w)) * (1 / hdpp)));
                return w;
            });
        }

        rasteredImageParams.put("raster_width", img.getWidth());
        rasteredImageParams.put("raster_height", img.getHeight());

        try {
            ImageIO.write(img, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private BufferedImage getImage(String imgPath) {
        BufferedImage tileImg = null;
        if (tileImg == null) {
            try {
                File in = new File(imgPath);
                tileImg = ImageIO.read(in);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return tileImg;
    }
}
