package com.example.parisroutefinder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import java.io.*;
import java.util.*;

import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

public class MainController {

    public ImageView imageView = new ImageView();
    public List<GraphNode<LandmarkNode>> landmarkNodes = new ArrayList<>();
    public ListView<List<GraphNode<?>>> listViewDepthFS = new ListView<>();
    public TextField maxNumDepthFSRoutes = new TextField();
    public ListView<GraphNode<?>> waypointsListView = new ListView<>();
    public ChoiceBox<String> avoidPointChoiceBox;
    @FXML
    private ChoiceBox<String> startChoiceBox;
    @FXML
    private ChoiceBox<String> endChoiceBox;

    private List<GraphNode<LandmarkNode>> startAndEndNodesBreadthFS = new ArrayList<>();

    private List<GraphNode<LandmarkNode>> pixelPoints = new ArrayList<>(); // List of road points (white) for BFS

    public void initialize() throws IOException {
        loadData();
        populateChoiceBoxes();
        plotWithLabels();
        addLandmarkLinks();
        processBitmap();

    }



    /**
     * this method establishes bidirectional (undirected) connections between predefined landmarks.
     * each landmark is represented as a node in the graph (GraphNode<LandmarkNode>), and edges are
     * created based on the calculated cost between each pair of connected nodes. The connections
     * define the links between landmarks such as the Eiffel Tower, Louvre, Notre-Dame, Arc de Triomphe,
     * and others, forming a network of connected landmarks throughout the map.
     *
     * the connections on the map are undirected, meaning movement between landmarks can occur in both directions.
     */
    public void addLandmarkLinks(){

        // Fetch each landmark node from the landmarkNodes list
        GraphNode<LandmarkNode> eiffelTower = landmarkNodes.get(0);
        GraphNode<LandmarkNode> louvre = landmarkNodes.get(1);
        GraphNode<LandmarkNode> notredame = landmarkNodes.get(2);
        GraphNode<LandmarkNode> arcdetriomphe = landmarkNodes.get(3);
        GraphNode<LandmarkNode> sacrecoeur = landmarkNodes.get(4);
        GraphNode<LandmarkNode> montmatre = landmarkNodes.get(5);
        GraphNode<LandmarkNode> musee = landmarkNodes.get(6);
        GraphNode<LandmarkNode> palais = landmarkNodes.get(7);
        GraphNode<LandmarkNode> champselysees = landmarkNodes.get(8);
        GraphNode<LandmarkNode> concorde = landmarkNodes.get(9);
        GraphNode<LandmarkNode> pantheon = landmarkNodes.get(10);
        GraphNode<LandmarkNode> lux = landmarkNodes.get(11);
        GraphNode<LandmarkNode> pompidou = landmarkNodes.get(12);
        GraphNode<LandmarkNode> saintechapelle = landmarkNodes.get(13);
        GraphNode<LandmarkNode> pontalexandre = landmarkNodes.get(14);
        GraphNode<LandmarkNode> vendome = landmarkNodes.get(15);
        GraphNode<LandmarkNode> a = landmarkNodes.get(16);
        GraphNode<LandmarkNode> b = landmarkNodes.get(17);
        GraphNode<LandmarkNode> c = landmarkNodes.get(18);
        GraphNode<LandmarkNode> d = landmarkNodes.get(19);

        // Create bidirectional connections between landmarks with calculated edge costs
        eiffelTower.connectToNodeUndirected(musee, GraphController.calculateCostOfEdge(musee, eiffelTower));
        concorde.connectToNodeUndirected(vendome, GraphController.calculateCostOfEdge(vendome, concorde));
        notredame.connectToNodeUndirected(pompidou, GraphController.calculateCostOfEdge(pompidou, notredame));
        a.connectToNodeUndirected(musee, GraphController.calculateCostOfEdge(a, musee));
        a.connectToNodeUndirected(louvre, GraphController.calculateCostOfEdge(a, louvre));
        pontalexandre.connectToNodeUndirected(eiffelTower, GraphController.calculateCostOfEdge(pontalexandre, eiffelTower));
        eiffelTower.connectToNodeUndirected(arcdetriomphe, GraphController.calculateCostOfEdge(eiffelTower, arcdetriomphe));
        eiffelTower.connectToNodeUndirected(a, GraphController.calculateCostOfEdge(eiffelTower, a));
        arcdetriomphe.connectToNodeUndirected(champselysees, GraphController.calculateCostOfEdge(arcdetriomphe, champselysees));
        champselysees.connectToNodeUndirected(pontalexandre, GraphController.calculateCostOfEdge(champselysees, pontalexandre));
        pontalexandre.connectToNodeUndirected(concorde, GraphController.calculateCostOfEdge(pontalexandre, concorde));
        concorde.connectToNodeUndirected(musee, GraphController.calculateCostOfEdge(concorde, musee));
        musee.connectToNodeUndirected(vendome, GraphController.calculateCostOfEdge(musee, vendome));
        vendome.connectToNodeUndirected(louvre, GraphController.calculateCostOfEdge(vendome, louvre));
        louvre.connectToNodeUndirected(pompidou, GraphController.calculateCostOfEdge(louvre, pompidou));
        louvre.connectToNodeUndirected(saintechapelle, GraphController.calculateCostOfEdge(louvre, saintechapelle));
        saintechapelle.connectToNodeUndirected(pompidou, GraphController.calculateCostOfEdge(saintechapelle, pompidou));
        saintechapelle.connectToNodeUndirected(notredame, GraphController.calculateCostOfEdge(saintechapelle, notredame));
        notredame.connectToNodeUndirected(lux, GraphController.calculateCostOfEdge(notredame, lux));
        lux.connectToNodeUndirected(pantheon, GraphController.calculateCostOfEdge(lux, pantheon));
        notredame.connectToNodeUndirected(pantheon, GraphController.calculateCostOfEdge(notredame, pantheon));
        lux.connectToNodeUndirected(a, GraphController.calculateCostOfEdge(lux, a));
        a.connectToNodeUndirected(c, GraphController.calculateCostOfEdge(a, c));
        pontalexandre.connectToNodeUndirected(musee, GraphController.calculateCostOfEdge(pontalexandre, musee));
        c.connectToNodeUndirected(d, GraphController.calculateCostOfEdge(c, d));
        d.connectToNodeUndirected(pantheon, GraphController.calculateCostOfEdge(d, pantheon));
        b.connectToNodeUndirected(d, GraphController.calculateCostOfEdge(b, d));
        b.connectToNodeUndirected(pantheon, GraphController.calculateCostOfEdge(b, pantheon));
        palais.connectToNodeUndirected(sacrecoeur, GraphController.calculateCostOfEdge(palais, sacrecoeur));
        sacrecoeur.connectToNodeUndirected(montmatre, GraphController.calculateCostOfEdge(sacrecoeur, montmatre));
        palais.connectToNodeUndirected(champselysees, GraphController.calculateCostOfEdge(palais, champselysees));
        palais.connectToNodeUndirected(concorde, GraphController.calculateCostOfEdge(palais, concorde));
        palais.connectToNodeUndirected(pompidou, GraphController.calculateCostOfEdge(palais, pompidou));
    }



    // Finds the three closest nodes to the given node (nodeA) based on the calculated distance (edge cost)
    public List<GraphNode<?>> findNearbyRoadPoints(GraphNode<?> nodeA) {
        // List to store the nearest nodes
        List<GraphNode<?>> nearbyNodes = new ArrayList<>();

        // List to store the distances between nodeA and every other node
        List<Integer> distances = new ArrayList<>();

        // Loop through all the nodes in pixelPoints
        for (GraphNode<?> nodeB : this.pixelPoints) {
            // Ensure we're not calculating the distance to the node itself
            if (nodeB != nodeA) {
                // Calculate and store the distance (cost of the edge) between nodeA and nodeB
                distances.add(GraphController.calculateCostOfEdge((GraphNode<LandmarkNode>) nodeB, (GraphNode<LandmarkNode>) nodeA));
            }
        }

        // Sort the distances to determine the closest ones
        Collections.sort(distances);

        // Variables to hold references to the three closest nodes
        GraphNode<?> adjNode1 = null;
        GraphNode<?> adjNode2 = null;
        GraphNode<?> adjNode3 = null;

        // Iterate through all the nodes again to find the ones corresponding to the shortest distances
        for (GraphNode<?> node : this.pixelPoints) {
            // Calculate the distance between the current node and nodeA
            int d = GraphController.calculateCostOfEdge((GraphNode<LandmarkNode>) node, (GraphNode<LandmarkNode>) nodeA);

            // Check if this node is the closest (1st closest) and has not yet been assigned
            if ((distances.get(0) == d) && adjNode1 == null && d != 0) {
                adjNode1 = node;
            }

            // Check if this node is the 2nd closest and has not yet been assigned
            if ((distances.get(1) == d) && adjNode2 == null && d != 0) {
                adjNode2 = node;
            }

            // Check if this node is the 3rd closest and has not yet been assigned
            if ((distances.get(2) == d) && adjNode3 == null && d != 0) {
                adjNode3 = node;
            }
        }

        // Add the three closest nodes to the nearbyNodes list (could be null if fewer than 3 nodes are found)
        nearbyNodes.add(adjNode1);
        nearbyNodes.add(adjNode2);
        nearbyNodes.add(adjNode3);

        // Return the list of nearby nodes
        return nearbyNodes;
    }


    // Returns 2-3 close/nearby nodes for the purpose of incorporating waypoint into LandmarkNodes list etc
    public List<GraphNode<?>> findNearbyLandmarkNodes(GraphNode<?> nodeA){
        List<GraphNode<?>> nearbyNodes = new ArrayList<>();
        List<Integer> distances = new ArrayList<>();

        for(GraphNode<?> nodeB : this.landmarkNodes){
            if (nodeB != nodeA) {
                distances.add(GraphController.calculateCostOfEdge((GraphNode<LandmarkNode>) nodeB, (GraphNode<LandmarkNode>) nodeA));
            }
        }
        Collections.sort(distances);

        GraphNode<?> adjacentNode1 = null;
        GraphNode<?> adjacentNode2 = null;
        GraphNode<?> adjacentNode3 = null;

        for(GraphNode<?> node : this.landmarkNodes){
            int d = GraphController.calculateCostOfEdge((GraphNode<LandmarkNode>) node, (GraphNode<LandmarkNode>) nodeA);
            if ((distances.get(0) == d) && adjacentNode1 == null && d != 0) {
                adjacentNode1 = node;
            }
            if ((distances.get(1) == d) && adjacentNode2 == null && d != 0) {
                adjacentNode2 = node;
            }
            if ((distances.get(2) == d) && adjacentNode3 == null && d != 0) {
                adjacentNode3 = node;
            }
        }



        nearbyNodes.add(adjacentNode1);
        nearbyNodes.add(adjacentNode2);
        nearbyNodes.add(adjacentNode3);

        return nearbyNodes;
    }


    public void processBitmap() throws FileNotFoundException {
        // Load the bitmap image from file and get the PixelReader and PixelWriter
        Image bitmap = new Image(new FileInputStream("src/main/resources/com/example/parisroutefinder/parisbitmp.bmp"));
        Image image = imageView.getImage();
        PixelReader pixelReader = bitmap.getPixelReader();
        WritableImage writableImage = new WritableImage((int) bitmap.getWidth(), (int) bitmap.getHeight());
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        // Loop through every pixel in the bitmap
        for (int ycoord = 0; ycoord < bitmap.getHeight(); ycoord++) {
            for (int xcoord = 0; xcoord < bitmap.getWidth(); xcoord++) {
                // Get the color of the current pixel
                Color colorOfPixel = pixelReader.getColor(xcoord, ycoord);

                // If pixel is not black, set it as a white road pixel and create a node
                if (!colorOfPixel.equals(Color.BLACK)) {
                    pixelWriter.setColor(xcoord, ycoord, Color.WHITE);
                    pixelPoints.add(new GraphNode<>(new LandmarkNode("RoadPixel", new MapPixel(xcoord, ycoord), 3)));
                }
                // If pixel is black, keep it black and add a node for black pixels
                else {
                    pixelWriter.setColor(xcoord, ycoord, Color.BLACK);
                    pixelPoints.add(new GraphNode<>(new LandmarkNode("B", new MapPixel(xcoord, ycoord), 3)));
                }
            }
        }

        // Link neighboring RoadPixel nodes to form a graph
        for (int y = 0; y < imageView.getImage().getHeight(); y++) {
            for (int x = 0; x < imageView.getImage().getWidth(); x++) {
                // Calculate the index of the current pixel in the pixelPoints list
                int indexOfPixel = y * (int) imageView.getImage().getWidth() + x;

                // Only process if the current pixel is a RoadPixel
                if (pixelPoints.get(indexOfPixel).data.getName().equals("RoadPixel")) {
                    int rightIndex = indexOfPixel + 1;
                    int belowIndex = indexOfPixel + (int) image.getWidth();

                    // Link the current pixel with the pixel to the right if it's also a RoadPixel
                    if (rightIndex < pixelPoints.size() && pixelPoints.get(rightIndex).data.getName().equals("RoadPixel")) {
                        pixelPoints.get(indexOfPixel).connectToNodeUndirected(pixelPoints.get(rightIndex),
                                GraphController.calculateCostOfEdge(pixelPoints.get(indexOfPixel), pixelPoints.get(rightIndex)));
                    }

                    // Link the current pixel with the pixel below if it's also a RoadPixel
                    if (belowIndex < pixelPoints.size() && pixelPoints.get(belowIndex).data.getName().equals("RoadPixel")) {
                        pixelPoints.get(indexOfPixel).connectToNodeUndirected(pixelPoints.get(belowIndex),
                                GraphController.calculateCostOfEdge(pixelPoints.get(indexOfPixel), pixelPoints.get(belowIndex)));
                    }
                }
            }
        }

        // prints the total number of pixel points processed to console
        System.out.println("Number of pixel points in image: " + (pixelPoints.size()));


    }


    public void resetMap() {
        AnchorPane anchorPane = (AnchorPane) imageView.getParent();
        anchorPane.getChildren().removeIf(component -> component instanceof Line);
        anchorPane.getChildren().removeIf(component -> component instanceof Circle);
        landmarkNodes.clear(); // Reset landmark node list and re-add them so that all temporary waypoints added are removed
        listViewDepthFS.getItems().clear();
        waypointsListView.getItems().clear();
        maxNumDepthFSRoutes.clear();
        startAndEndNodesBreadthFS.clear();

        try {
            loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Connect each node again as the list was reset
        addLandmarkLinks();

    }

    /* loading in data from csv */
    public void loadData() throws IOException {
        /* Source: https://www.baeldung.com/java-csv-file-array */
        String row = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/com/example/parisroutefinder/nodes.csv"));
            while ((row = reader.readLine()) != null) {
                String[] line = row.split(",");
                LandmarkNode lmn = new LandmarkNode(line[0],new MapPixel(Integer.parseInt(line[1]),Integer.parseInt(line[2])),Integer.parseInt(line[3]));
                GraphNode<LandmarkNode> gnode = new GraphNode<>(lmn);
                this.landmarkNodes.add(gnode);
                //System.out.println("P: " + lmn.getName() + ", X: "+lmn.getX() + ", Y: "+ lmn.getY());
            }
            System.out.println("Successfully loaded data in");
            System.out.println("Total number of nodes:" + landmarkNodes.size());
        }
        catch(IOException error){
            System.out.println("Error occured when loading data: "+error);
        }
    }

    public void plotWithLabels(){

        /* - Loop through each landmark
           - Display landmark as circle point
         */
        for(GraphNode<LandmarkNode> node : this.landmarkNodes){
            Rectangle rect = new Rectangle(node.data.getX(),node.data.getY(),7,7);
            rect.setFill(Color.BLACK);
            rect.setLayoutY(imageView.getLayoutY());
            rect.setLayoutX(imageView.getLayoutX());
            Text label = new Text();
            label.setText(node.data.getName());
            label.setLayoutX(imageView.getLayoutX());
            label.setLayoutY(imageView.getLayoutY());
            label.setX(rect.getX()-5);
            label.setY(rect.getY()-2);
            AnchorPane ap = (AnchorPane) imageView.getParent();
            ap.getChildren().add(rect);
            ap.getChildren().add(label);
        }
    }

    private void populateChoiceBoxes() {
        ObservableList<String> landmarkNames = FXCollections.observableArrayList();
        for (GraphNode<LandmarkNode> node : landmarkNodes) {
            landmarkNames.add(node.data.getName());
        }

        startChoiceBox.setItems(landmarkNames);
        endChoiceBox.setItems(landmarkNames);
        avoidPointChoiceBox.setItems(landmarkNames);
    }

    public void drawLinesBetweenLandmarkNodes(MouseEvent event){
        AnchorPane anchorPane = (AnchorPane) imageView.getParent();
        anchorPane.getChildren().removeIf(component -> component instanceof Line);

        List<GraphNode<?>> pathList;
        if(event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            pathList = listViewDepthFS.getSelectionModel().getSelectedItem();
            //   System.out.println("Total distance/cost (pixel units): " + GraphAPI.calculateTotalDistanceOfPath(pathList));

            for (int i = 0; i < pathList.size()-1; i++) {
                GraphNode<LandmarkNode> nodeA = (GraphNode<LandmarkNode>) pathList.get(i);
                GraphNode<LandmarkNode> nodeB = (GraphNode<LandmarkNode>) pathList.get(i+1);
                Line line = new Line(nodeA.data.getX(),nodeA.data.getY(),nodeB.data.getX(),nodeB.data.getY());
                line.setStroke(Color.PURPLE);
                line.setStrokeWidth(4);
                line.setLayoutY(imageView.getLayoutY());
                line.setLayoutX(imageView.getLayoutX());
                AnchorPane ap = (AnchorPane) imageView.getParent();
                ap.getChildren().add(line);

            }
        }

    }

    public void generateDepthFSRoutes(ActionEvent actionEvent) {
        listViewDepthFS.getItems().clear();

        String startingNodeName = startChoiceBox.getValue();
        String destNodeName = endChoiceBox.getValue();
        GraphNode<LandmarkNode> startNode = null;
        GraphNode<LandmarkNode> destNode = null;

        for(GraphNode<LandmarkNode> node : landmarkNodes){
            if(node.data.getName().equals(startingNodeName)) {
                startNode = node;
            }
            if(node.data.getName().equals(destNodeName)) {
                destNode = node;
            }
        }

        List<List<GraphNode<?>>> listOfPaths = GraphController.findAllPathsDepthFirst(startNode,null,destNode.data);

        // Limiting paths generated by user-specified value
        int pathCount = 1;
        for(List<GraphNode<?>> path : listOfPaths){
            if(pathCount <= getMaxNumRoutesDepthFS()){
                listViewDepthFS.getItems().add(path); // puttinh each path on separate entry in ListView forall dfs routes generated
                pathCount++;
            }
        }

    }

    private int getMaxNumRoutesDepthFS() {
        if(maxNumDepthFSRoutes.getText().isEmpty()) return 1; else return Integer.parseInt(maxNumDepthFSRoutes.getText());
    }

    @FXML
    private void findShortestPathDijkstra(ActionEvent event) {
        AnchorPane anchorPane = (AnchorPane) imageView.getParent();
        anchorPane.getChildren().removeIf(component -> component instanceof Line);

        String startLandmarkName = startChoiceBox.getValue();
        String endLandmarkName = endChoiceBox.getValue();

        GraphNode<LandmarkNode> startNode = findNodeByName(startLandmarkName);
        GraphNode<LandmarkNode> endNode = findNodeByName(endLandmarkName);

        List<GraphNode<LandmarkNode>> shortestPath = GraphController.findCheapestPathDijkstra(startNode, endNode, landmarkNodes);

        List<GraphNode<LandmarkNode>> waypoints = new ArrayList<>();
        List<List<GraphNode<LandmarkNode>>> waypointPaths = new ArrayList<>();

        if (!waypointsListView.getItems().isEmpty()){
            for(GraphNode<LandmarkNode> node : landmarkNodes){
                if(node.data.getName().equals("WayPoint")){
                    waypoints.add(node);
                }
            }
            System.out.println("Waypoints added to their own list temporarily\n");
            System.out.println(waypoints.size());

            waypointPaths.add(GraphController.findCheapestPathDijkstra(startNode, waypoints.get(0),landmarkNodes));

            for(int j = 0; j < waypoints.size()-1; j++){
                waypointPaths.add(GraphController.findCheapestPathDijkstra(waypoints.get(j),waypoints.get(j+1),landmarkNodes));
            }
            waypointPaths.add(GraphController.findCheapestPathDijkstra(waypoints.get(waypoints.size()-1),endNode,landmarkNodes));

            for(List<GraphNode<LandmarkNode>> list : waypointPaths){
                visualizePathOnMap(list,Color.MEDIUMPURPLE);
            }

        }
        else{
            visualizePathOnMap(shortestPath, Color.RED);
        }

    }

    public void generateShortestPathBreadthFS(ActionEvent event){
        // Check if the list contains at least two nodes
        if (startAndEndNodesBreadthFS.size() >= 2) {
            // Find the shortest path using BFS
            List<GraphNode<LandmarkNode>> path = GraphController.findShortestPathBFS(
                    startAndEndNodesBreadthFS.get(0), startAndEndNodesBreadthFS.get(1));

            // Visualize the path on the map
            visualizePathOnMap(path, Color.MAGENTA);
        } else {
            // Handle the case where there are not enough nodes selected
            System.out.println("Error: You must select at least two nodes before finding the shortest path.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Insufficient Nodes");
            alert.setHeaderText(null);
            alert.setContentText("Please select at least two nodes to find the shortest path.");
            alert.showAndWait();
        }
    }

    @FXML
    public void findShortestPathBFS(int x, int y) {
        if (startAndEndNodesBreadthFS.size() < 3) {
            Circle marker = new Circle(x, y, 3);
            marker.setLayoutX(imageView.getLayoutX());
            marker.setLayoutY(imageView.getLayoutY());
            marker.setFill(Color.GREY);
            marker.setStrokeWidth(3.5);
            marker.setStroke(Color.DARKBLUE);
            marker.setCenterX(x);
            marker.setCenterY(y);
            AnchorPane drawingArea = (AnchorPane) imageView.getParent();
            drawingArea.getChildren().add(marker);

            MapPixel mapPixel = new MapPixel(x,y);
            GraphNode<LandmarkNode> newNode = new GraphNode<>(new LandmarkNode("RoadPixel", mapPixel, 3));

            pixelPoints.add(newNode);
            startAndEndNodesBreadthFS.add(newNode);

            List<GraphNode<?>> listOfNearbyRoadNodes = findNearbyRoadPoints(newNode);

            System.out.println("Number of nearby nodes: " + listOfNearbyRoadNodes.size() + "\n");

            System.out.print(listOfNearbyRoadNodes);

            for (GraphNode<?> nearbyNode : listOfNearbyRoadNodes) {
                if (nearbyNode != null) newNode.connectToNodeUndirected((GraphNode<LandmarkNode>) nearbyNode, GraphController.calculateCostOfEdge(newNode, (GraphNode<LandmarkNode>) nearbyNode));
            }
            boolean isRemoved = pixelPoints.remove(newNode);
            if(isRemoved) pixelPoints.add(newNode);

        }
    }



    private GraphNode<LandmarkNode> findNodeByName(String name) {
        for (GraphNode<LandmarkNode> node : landmarkNodes) {
            if (node.data.getName().equalsIgnoreCase(name)) {
                return node;
            }
        }
        return null; // Node not found
    }

    private void visualizePathOnMap(List<GraphNode<LandmarkNode>> path, Color color) {
        if (path == null || path.isEmpty()) {
            System.out.println("Path is empty or null");
            return;
        }

        double imageViewX = imageView.getLayoutX();
        double imageViewY = imageView.getLayoutY();

        for (int i = 0; i < path.size() - 1; i++) {
            GraphNode<LandmarkNode> currentNode = path.get(i);
            GraphNode<LandmarkNode> nextNode = path.get(i + 1);

            // Adjust line start and end positions based on the imageView's layout properties
            Line line = new Line(
                    imageViewX + currentNode.data.getX(),
                    imageViewY + currentNode.data.getY(),
                    imageViewX + nextNode.data.getX(),
                    imageViewY + nextNode.data.getY()
            );
            line.setStroke(color); // Set line color
            line.setStrokeWidth(2); // Set line width

            AnchorPane drawingArea = (AnchorPane) imageView.getParent();
            drawingArea.getChildren().add(line); // Add the line to the AnchorPane
        }
    }


    public void avoidPointInPaths(ActionEvent actionEvent) {
        GraphNode<LandmarkNode> nodeToAvoid = findNodeByName(avoidPointChoiceBox.getValue());

        for (GraphNode<LandmarkNode> node : landmarkNodes)
        {
            Iterator<GraphLink<LandmarkNode>> iterator = node.adjList.iterator();
            while (iterator.hasNext())
            {
                GraphLink<LandmarkNode> adjacentNode = iterator.next();
                if (adjacentNode.destNode.data.getName() == (nodeToAvoid != null ? nodeToAvoid.data.getName() : null)) {
                    iterator.remove();
                    System.out.println("Removed " + adjacentNode.destNode.data.getName());
                }
            }
        }

        landmarkNodes.remove(nodeToAvoid);
    }

    public void generateMostHistoricalRoute(ActionEvent actionEvent) {
        String startNodeName = startChoiceBox.getValue();
        String endNodeName = endChoiceBox.getValue();

        GraphNode<LandmarkNode> startNode = findNodeByName(startNodeName);
        GraphNode<LandmarkNode> destNode = findNodeByName(endNodeName);

        List<GraphNode<LandmarkNode>> mostHistoricalPath = GraphController.dijkstrasHistoricalPath(startNode, destNode, landmarkNodes);

        // Create a new Alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Most Historical Path");
        alert.setHeaderText(null);

        // Create a TextArea to display the historical values
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        // Build the string to display
        StringBuilder sb = new StringBuilder();
        sb.append("----- Most Historical Path ---------\n");
        for (GraphNode<LandmarkNode> pathNode : mostHistoricalPath) {
            sb.append(pathNode.data.getName()).append(": ").append(pathNode.data.getCulturalValue()).append("\n");
        }
        sb.append("----- ----------------- ---------");

        // Set the text to the TextArea
        textArea.setText(sb.toString());

        // Add the TextArea to the alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setContent(textArea);

        // Show the alert and wait for the user to close it
        Optional<ButtonType> result = alert.showAndWait();
    }

    public void displayLinksWithLines(ActionEvent actionEvent) {
        for(GraphNode<LandmarkNode> node : landmarkNodes){
            if (!node.adjList.isEmpty()){
                for(GraphLink<LandmarkNode> graphNodeLink : node.adjList){
                    GraphNode<LandmarkNode> destNode = graphNodeLink.destNode;

                    List<GraphNode<LandmarkNode>> miniPath = new ArrayList<>();
                    miniPath.add(node);
                    miniPath.add(destNode);
                    visualizePathOnMap(miniPath,Color.DARKMAGENTA);
                }
            }
        }
    }

    public void addWayPoint(MouseEvent event) {
        return;
    }
}
