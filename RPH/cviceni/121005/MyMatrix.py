class MyMatrix:
    def __init__(self,row,col):
        poleTest = []
        for i in range(row):
            rowData = [[0]*col]
            poleTest = poleTest + rowData
        
        #poleTest = [[0 for i in range(col)] for j in range(row)]
        # poleTest = [[0]*col] * row   # problem s vkladanim/zmenou dat
        self.pole = poleTest
        
if __name__ == "__main__":
    mat = MyMatrix(3,4)
    mat.pole[0][0] = 1
    print (mat.pole)