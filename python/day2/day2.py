#!/usr/bin/env python

import os
import sys
sys.path.append(os.path.join(os.path.dirname(__file__), ".."))

from support.astar import astar

def is_possible(game, red, green, blue):
    cube_counts = {'red': 0, 'green': 0, 'blue': 0}

    for subset in game:
        for cube in subset.split(', '):
            count, color = cube.split()
            cube_counts[color] = max(cube_counts[color], int(count))

    # print("cc",cube_counts, red, green, blue)
    return cube_counts['red'] <= red and cube_counts['green'] <= green and cube_counts['blue'] <= blue


def game_power(game):
    cube_counts = {'red': 0, 'green': 0, 'blue': 0}

    for subset in game:
        for cube in subset.split(', '):
            count, color = cube.split()
            cube_counts[color] = max(cube_counts[color], int(count))

    return cube_counts['red'] * cube_counts['green'] * cube_counts['blue']


def power(records):
    game_powers = []
    for record in records:
        game_id, cubes_str = record.strip().split(': ')
        game = cubes_str.split('; ')

        game_powers.append(game_power(game))

    return game_powers


def possible_games(records, red, green, blue):
    possible_game_ids = []

    for record in records:
        game_id, cubes_str = record.strip().split(': ')
        game = cubes_str.split('; ')

        if is_possible(game, red, green, blue):
            possible_game_ids.append(int(game_id.split()[1]))

    return possible_game_ids


def solve_part1(records):
    return sum(possible_games(records, 12, 13, 14))


def solve_part2(records):
    return sum(power(records))


def read_input(path):
    path = os.path.join(os.path.dirname(__file__), path)
    return open(path).readlines()


def solve_part1_sample():
    print("Part 1 sample solution:", solve_part1(read_input('sample')))


def solve_part1_real():
    print("Part 1 real solution:", solve_part1(read_input('input')))


def solve_part2_sample():
    print("Part 2 sample solution:", solve_part2(read_input('sample')))


def solve_part2_real():
    print("Part 2 real solution:", solve_part2(read_input('input')))


if __name__ == "__main__":
    solve_part1_sample()
    solve_part1_real()
    solve_part2_sample()
    solve_part2_real()
