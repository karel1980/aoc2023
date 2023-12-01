import day1

def test_evaluate():
    assert day1.evaluate('abc123def456ghi') == 16

def test_solve_a_sample():
    assert day1.solve_a('data/day1/sample') == 142

def test_solve_a():
    assert day1.solve_a('data/day1/input') is None

def test_solve_b_sample():
    assert day1.solve_b('data/day1/sample2') == 281

def test_solve_b():
    assert day1.solve_b('data/day1/input') is None

def test_evaluate_two1nine():
    assert day1.evaluate_b("two1nine") == 29

def test_evaluate_eightwothree():
    assert day1.evaluate_b("eightwothree") == 83

def test_evaluate_abcone2threexyz():
    assert day1.evaluate_b("abcone2threexyz") == 13

def test_evaluate_xtwone3four():
    assert day1.evaluate_b("twone3four") == 24

def test_evaluate_4nineeightseven2():
    assert day1.evaluate_b("4nineeightseven2") == 42

def test_evaluate_zoneight234():
    assert day1.evaluate_b("zoneight234") == 14

def test_evaluate_7pqrstsixteen():
    assert day1.evaluate_b("7pqrstsixteen") == 76
