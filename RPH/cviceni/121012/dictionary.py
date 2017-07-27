def char_counts_in_string(string):
    """
    >>> char_counts_in_string("ahoj AHOJ")
    {'a': 1, 'A': 1, 'J': 1, ' ': 1, 'h': 1, 'j': 1, 'o': 1, 'O': 1, 'H': 1}
    """
    d = {}
    
    for c in string:
        d[c] = d.get('c',0) + 1
    
    return d

if __name__ == "__main__":
    import doctest
    doctest.testmod()