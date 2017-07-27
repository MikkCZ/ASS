def a():
    import collections
    results = collections.Counter("Toto je retezec")
    print(results)

def b():
    input = "Toto je retezec"
    dictionary = {}
    for char in input:
        try:
            dictionary[char] += 1
        except:
            dictionary[char] = 1
    for char in dictionary.keys():
        print ("%s %d" % (char, dictionary[char]))

def c():
    string = "Toto je retezec"
    dict = {}
    
    for letter in string:
        if letter in dict.keys():
            dict[letter] += 1
        else:
            dict[letter] = 1
    
    print (dict)
    for letter in dict.keys():
        print (letter, dict[letter], "\n")


# http://docs.python.org/library/stdtypes.html#dict
# http://docs.python.org/py3k/library/stdtypes.html#typesmapping

if __name__ == "__main__":
    c()