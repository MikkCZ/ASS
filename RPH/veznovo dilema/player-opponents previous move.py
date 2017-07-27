class MyPlayer:
    '''Tento hrac vraci souperuv predchozi tah.'''
    
    def __init__(self,payoff_matrix,number_of_iterations=0):
        '''Constructor: Number of iterations doesn't have to be given. Also contains MyPlayers memory.'''
        self.payoff_matrix = payoff_matrix
        self.noi = number_of_iterations
        self.cooperate = False
        self.defect = True
        self.opponents_previous_move = self.defect   # defect as default if none is set
    
    def move(self):
        '''Returns opponents previous move.'''
        result_move = self.opponents_previous_move
        return result_move
    
    def record_opponents_move(self,opponent_move=True):
        '''Saves opponents move for the next game iteration (True as default if none is given).'''
        self.opponents_previous_move = opponent_move
    
