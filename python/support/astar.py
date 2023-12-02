import heapq

def astar_variable_cost(grid, start, goal, cost_fn):
    rows, cols = len(grid), len(grid[0])
    directions = [(0, 1), (0, -1), (1, 0), (-1, 0)]

    def heuristic(node):
        return abs(node[0] - goal[0]) + abs(node[1] - goal[1])

    pq = [(0, start)]
    came_from = {start: None}
    cost_so_far = {start: 0}

    while pq:
        current_cost, current_node = heapq.heappop(pq)

        if current_node == goal:
            path = []
            while current_node is not None:
                path.insert(0, current_node)
                current_node = came_from[current_node]
            return path

        for direction in directions:
            neighbor = (current_node[0] + direction[0], current_node[1] + direction[1])

            if 0 <= neighbor[0] < rows and 0 <= neighbor[1] < cols and grid[neighbor[0]][neighbor[1]] != 1:
                new_cost = cost_so_far[current_node] + cost_fn(neighbor)
                if neighbor not in cost_so_far or new_cost < cost_so_far[neighbor]:
                    cost_so_far[neighbor] = new_cost
                    priority = new_cost + heuristic(neighbor)
                    heapq.heappush(pq, (priority, neighbor))
                    came_from[neighbor] = current_node

    return None  # No path found

def cost_fn(cell):
    move_costs = {
        (0, 0): 1,
        (0, 1): 2,
        (0, 2): 1,
        (0, 3): 3,
    }
    return 1

def main():
    # Example usage:
    grid = [
        [0, 0, 0, 0, 0],
        [0, 1, 1, 0, 0],
        [0, 0, 0, 1, 0],
        [0, 1, 0, 0, 1],
        [0, 0, 1, 0, 0]
    ]

    start = (0, 0)
    goal = (4, 4)

    # Define move costs for each cell

    path = astar_variable_cost(grid, start, goal, cost_fn)
    print(path)


if __name__=="__main__":
    main()
