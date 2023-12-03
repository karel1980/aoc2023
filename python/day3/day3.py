import os
import sys

sys.path.append(os.path.join(os.path.dirname(__file__), ".."))


def parse_file(path):
    path = os.path.join(os.path.dirname(__file__), path)
    return [parse_line(l.strip()) for l in open(path).readlines()]


def parse_line(line):
    return line


def solve_part1(data):
    # TODO
    return 42


def solve_part2(data):
    # TODO
    return 42


def solve_part1_sample():
    return solve_part1(parse_file('sample'))


def solve_part1_real():
    return solve_part1(parse_file('input'))


def solve_part2_sample():
    return solve_part2(parse_file('sample'))


def solve_part2_real():
    return solve_part2(parse_file('input'))


if __name__ == "__main__":
    print("Part 1 sample solution:", solve_part1_sample())
    # print("Part 1 real solution:", solve_part1_real())
    # print("Part 2 sample solution:", solve_part2_sample())
    # print("Part 2 real solution:", solve_part2_real())


def test_sample():
    assert solve_part1_sample() == 42
