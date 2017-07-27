def count_rows_and_words(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        rows_count = 0
        words_count = 0
        for row in file:
            rows_count += 1
            words = string.split()
            words_count = words_count + string.count(words, row)
        
        return (rows_count, words_count)