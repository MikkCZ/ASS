class MyVector:
    def __init__(self, coordinates):
        self.c = coordinates
    
    def __mul__(self, other):
        index = 0 #please do not change this; defines from which coordinate start to multiply (0 = the first one)
        result = 0 #please do not change this; defining the zero value
            
        while index < len(self.c): #multiply till you come to the last coordinate
            result = result + self.c[index]*other.c[index]
            index = index + 1
            
        return(result)
        
    def __add__(self,other):
        result = [0]
        for index in range(len(self.c)):
            result = result + [self.c[index]+other.c[index]]
        MyVector.get_vector(MyVector(result))
    
    def norm(self):
        result = 0
        for index in range(len(self.c)):
            result = result + self.c[index]*self.c[index]
        result = result**(1/2)
        return result
        
    def get_vector(self):
        return self.c
    