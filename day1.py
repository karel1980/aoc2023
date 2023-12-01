
def evaluate(line):
    nums = []
    for i in range(len(line)):
        if line[i] in '0123456789':
            nums.append(int(line[i]))
    return nums[0]*10 + nums[-1]


recognized = ['1','2','3','4','5','6','7','8','9', 'one', 'two','three','four','five','six','seven','eight','nine']

lookup = dict()
lookup["one"] = 1
lookup["two"] = 2
lookup["three"] = 3
lookup["four"] = 4
lookup["five"] = 5
lookup["six"] = 6
lookup["seven"] = 7
lookup["eight"] = 8
lookup["nine"] = 9

def to_int(x):
    if x in lookup:
        return lookup[x]
    return int(x)

def evaluate_b(line):
    #print("INPUT:",line)
    found = []
    for i in range(len(line)):
        for r in recognized:
            if line[i:].startswith(r):
                found.append(line[i:i+len(r)])

    #print("XXX", found)
    return to_int(found[0])*10 + to_int(found[-1])

def solve_a(path):
    lines = [l.strip() for l in open(path).readlines()]

    total = 0
    for l in lines:
        total += evaluate(l)

    return total

def solve_b(path):
    lines = [l.strip() for l in open(path).readlines()]

    total = 0
    for l in lines:
        value = evaluate_b(l)
        total += value

    return total
