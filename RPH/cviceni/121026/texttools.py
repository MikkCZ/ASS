def compute_word_frequencies(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        d = {}
        for row in file:
            words = row.split()
            for a in words:
                if a in d:
                    d[a] += 1
                else:
                    d[a] = 1
        
    return d