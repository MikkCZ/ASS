from doctest import testmod

def add_numbers(num1,num2):
    """
    >>> add_numbers(1,2)
    3
    >>> add_numbers(2,3)
    5
    """
    return num1+num2
    
if __name__ == "__main__":
    testmod()
    