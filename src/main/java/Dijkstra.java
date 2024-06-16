package adrien.sae_201;

import java.util.*;

public class Dijkstra {

    static class Node implements Comparable<Node> {
        int x, y;
        int cost;
        Node parent;

        Node(int x, int y, Node parent, int cost) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static List<int[]> findPath(String[][] grid, int startX, int startY, int endX, int endY) {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Map<String, Integer> distances = new HashMap<>();
        Map<String, Node> visitedNodes = new HashMap<>();

        String start = startX + "," + startY;
        distances.put(start, 0);
        queue.add(new Node(startX, startY, null, 0));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            String currentPos = currentNode.x + "," + currentNode.y;

            if (visitedNodes.containsKey(currentPos)) {
                continue;
            }
            visitedNodes.put(currentPos, currentNode);

            if (currentNode.x == endX && currentNode.y == endY) {
                List<int[]> path = new ArrayList<>();
                Node temp = currentNode;
                while (temp != null) {
                    path.add(0, new int[]{temp.x, temp.y});
                    temp = temp.parent;
                }
                return path;
            }

            for (int[] direction : new int[][]{{0, 2}, {0, -2}, {2, 0}, {-2, 0}}) {
                int newX = currentNode.x + direction[0];
                int newY = currentNode.y + direction[1];
                if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length && grid[newX][newY].equals(" ")) {
                    String newPos = newX + "," + newY;
                    int newCost = currentNode.cost + 1;
                    if (newCost < distances.getOrDefault(newPos, Integer.MAX_VALUE)) {
                        distances.put(newPos, newCost);
                        queue.add(new Node(newX, newY, currentNode, newCost));
                    }
                }
            }
        }

        return null; // No path found
    }
}
