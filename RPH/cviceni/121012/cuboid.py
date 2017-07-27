class Cuboid:
    def __init__ (self, a, b=None, c=None):
        self.a = a
        
        if b == None:
            self.b = a
        else:
            self.b = b
        
        if c == None:
            self.c = a
        else:
            self.c = c
        
    def compute_volume (self):
        """
        >>> c1 = Cuboid(1,2,3)
        >>> c1.compute_volume()
        6
        >>> c2 = Cuboid(2)
        >>> c2.compute_volume()
        8
        """
        volume = self.a * self.b * self.c
        return volume
    
    def make_scaled_copy (self, scale):
        """
        >>> c1 = Cuboid(1,2,3)
        >>> c1.compute_volume()
        6
        >>> c3 = c1.make_scaled_copy(2)
        >>> type(c3)
        <class '__main__.Cuboid'>
        >>> c3.compute_volume()
        48
        """
        a = self.a * scale
        b = self.b * scale
        c = self.c * scale
        result = Cuboid(a, b, c)
        return result
    
if __name__ == "__main__":
    import doctest
    doctest.testmod()