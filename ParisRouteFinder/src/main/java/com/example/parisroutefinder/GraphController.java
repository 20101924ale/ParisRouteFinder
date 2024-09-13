package com.example.parisroutefinder;


import java.util.*;

public class GraphController {

    // recursive dfs pathfinders
    public static <T> List<List<GraphNode<?>>> findAllPathsDepthFirst(GraphNode<?> from, List<GraphNode<?>> encountered, T lookingfor){
        List<List<GraphNode<?>>> result = null,temp2;
        if(from.data.equals(lookingfor)) {
            List<GraphNode<?>> temp=new ArrayList<>();
            temp.add(from);
            result=new ArrayList<>();
            result.add(temp);
            return result;
        }
        if(encountered == null) encountered = new ArrayList<>();
        encountered.add(from);
        for(GraphLink adjNode : from.adjList){
            if(!encountered.contains(adjNode.destNode)) {
                temp2=findAllPathsDepthFirst(adjNode.destNode,new ArrayList<>(encountered),lookingfor); //Use clone of encountered list

                if(temp2!=null) {
                    for(List<GraphNode<?>> x : temp2)
                        x.add(0,from);
                    if(result==null) result=temp2;
                    else result.addAll(temp2);
                }
            }
        }
        return result;
    }




    // GET COST/DISTANCE BETWEEN COORDINATES - for nodes
    public static int calculateCostOfEdge(GraphNode<LandmarkNode> nodeA, GraphNode<LandmarkNode> nodeB){
        // referenced from - https://javatutoring.com/distance-between-two-points-java-program
        int x1 = nodeA.data.getX();
        int y1 = nodeA.data.getY();
        int x2 = nodeB.data.getX();
        int y2 = nodeB.data.getY();
        return (int) Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }



    public static List<GraphNode<LandmarkNode>> findCheapestPathDijkstra(
            GraphNode<LandmarkNode> startNode, GraphNode<LandmarkNode> endNode,
            Collection<GraphNode<LandmarkNode>> allNodes) {
        final int INFINITY = Integer.MAX_VALUE; // infinity as max int value
        Map<GraphNode<LandmarkNode>, Integer> nodeValues = new HashMap<>(); // stores  current shortest distance from beginnign node to each node
        Map<GraphNode<LandmarkNode>, GraphNode<LandmarkNode>> previousNodes = new HashMap<>(); // trackign the previous node in the shortest path
        List<GraphNode<LandmarkNode>> encountered = new ArrayList<>(), unencountered = new ArrayList<>(); // list to track processed and unprocessed nodes

        // Initialize all nodes with infinity distance and null previous nodes
        for (GraphNode<LandmarkNode> node : allNodes) {
            nodeValues.put(node, INFINITY);
            previousNodes.put(node, null);
        }
        nodeValues.put(startNode, 0); // Start node distance to itself is 0
        unencountered.add(startNode); // Begin with the start node

        // Process nodes until there are no more to process
        while (!unencountered.isEmpty()) {
            unencountered.sort(Comparator.comparing(nodeValues::get)); // Sort unencountered nodes by distance (ascending)
            GraphNode<LandmarkNode> currentNode = unencountered.remove(0); // Remove and process the node with the shortest distance

            // If the current node is the target end node, reconstruct and return the path
            if (currentNode.equals(endNode)) {
                LinkedList<GraphNode<LandmarkNode>> path = new LinkedList<>(); // To store the path
                GraphNode<LandmarkNode> pathNode = endNode; // Start from the end node
                while (pathNode != null) { // Trace back from end node to start node
                    path.addFirst(pathNode); // Prepend node to path
                    pathNode = previousNodes.get(pathNode); // Move to the previous node in the path
                }
                return path; // Return the reconstructed path
            }

            // Update distances to neighbor nodes and their previous nodes
            for (GraphLink<LandmarkNode> edge : currentNode.adjList) {
                GraphNode<LandmarkNode> neighbor = edge.destNode; // The neighbor node at the end of the edge
                if (!encountered.contains(neighbor)) { // If neighbor hasn't been processed
                    int newCost = nodeValues.get(currentNode) + edge.cost; // Calculate new distance
                    if (newCost < nodeValues.get(neighbor)) { // If new distance is shorter
                        nodeValues.put(neighbor, newCost); // Update the distance
                        previousNodes.put(neighbor, currentNode); // Update the previous node
                        if (!unencountered.contains(neighbor)) unencountered.add(neighbor); // Add neighbor to be processed
                    }
                }
            }

            encountered.add(currentNode);
        }

        return null; // If the end node was never reached, return null (no path found)
    }




    public static List<GraphNode<LandmarkNode>> dijkstrasHistoricalPath(GraphNode<LandmarkNode> startNode, GraphNode<LandmarkNode> destNode, List<GraphNode<LandmarkNode>> landmarkNodes) {
        return landmarkNodes;
    }



    public static List<GraphNode<LandmarkNode>> findShortestPathBFS(GraphNode<LandmarkNode> startNode, GraphNode<LandmarkNode> endNode) {
        if (startNode == null || endNode == null) return new ArrayList<>(); // Handle null input

        Queue<GraphNode<LandmarkNode>> toExplore = new LinkedList<>();
        Map<GraphNode<LandmarkNode>, GraphNode<LandmarkNode>> cameFrom = new HashMap<>();
        Set<GraphNode<LandmarkNode>> visited = new HashSet<>();

        toExplore.add(startNode);
        visited.add(startNode);
        cameFrom.put(startNode, null); // Start node didn't come from any node

        while (!toExplore.isEmpty()) {
            GraphNode<LandmarkNode> current = toExplore.poll();

            // If we've reached the end node, backtrack to construct the path
            if (current.equals(endNode)) {
                return constructPath(cameFrom, endNode);
            }

            // Explore neighbors
            for (GraphLink edge : current.adjList) {
                GraphNode<LandmarkNode> neighbor = edge.destNode;
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                    toExplore.add(neighbor);
                }
            }
        }

        return new ArrayList<>(); // Return an empty list if no path found
    }

    //  method to backtrack and construct the path from endNode to startNode
    private static List<GraphNode<LandmarkNode>> constructPath(Map<GraphNode<LandmarkNode>, GraphNode<LandmarkNode>> cameFrom, GraphNode<LandmarkNode> endNode) {
        List<GraphNode<LandmarkNode>> path = new ArrayList<>();
        for (GraphNode<LandmarkNode> at = endNode; at != null; at = cameFrom.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }



    private static List<GraphNode<LandmarkNode>> reconstructPath(Map<GraphNode<LandmarkNode>, GraphNode<LandmarkNode>> cameFrom, GraphNode<LandmarkNode> endNode) {
        List<GraphNode<LandmarkNode>> path = new ArrayList<>();
        GraphNode<LandmarkNode> current = endNode;
        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }
        Collections.reverse(path);
        return path;
    }


}
