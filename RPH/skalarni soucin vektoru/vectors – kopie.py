class MyVector:
    def __init__(self, coordinates):
        self.c = coordinates
    
    def __mul__(self, other):
        index = 0 #please do not change this; from which coordinate start to multiply (0 = the first one)
        result = 0 #please do not change this; defining the zero value
        
        while len(other.c) < len(self.c): #when the 1st vector is longer, complete the 2nd to the same length with zeros
            other.c[len(other.c):1] = [0]
            
        while index < len(self.c): #multiply till you came to the last coordinate of the 1st vector
            result = result + self.c[index]*other.c[index]
            index = index + 1
            
        return(result)
        
        
    def get_vector(self):
        return self.c

