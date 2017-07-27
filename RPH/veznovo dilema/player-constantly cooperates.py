class MyPlayer:
    '''Tento hrac vraci vzdy spolupracuje.'''
    
    def __init__(self,payoff_matrix,number_of_iterations=0):
        '''Constructor: Number of iterations doesn't have to be given.'''
        self.payoff_matrix = payoff_matrix
        self.noi = number_of_iterations
        self.cooperate = False
        self.defect = True
    
    def move(self):
        '''Returns cooperate value.'''
        result_move = self.cooperate
        return result_move
    
    def record_opponents_move(self,opponent_move=0):
        '''Just for the need of obligation + secured for the case if no value is given.'''
        pass
    
