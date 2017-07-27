def read_classification_from_file(file_path):
    '''Makes dictionary from a classification file.'''
    classification_dictionary = {}
    
    with open(file_path, 'r', encoding='utf-8') as class_file:
        for line in class_file:
            (fname, classification) = line.split()
            classification_dictionary[fname] = classification
    
    return classification_dictionary
