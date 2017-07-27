import random

class MyPlayer:
    '''Tento hrac generuje/losuje nahodne tahy.'''
    
    def __init__(self,payoff_matrix,number_of_iterations=0):
        '''Constructor: Number of iterations doesn't have to be given. Also contains MyPlayers memory.'''
        self.payoff_matrix = payoff_matrix
        self.noi = number_of_iterations
        self.cooperate = False
        self.defect = True
        self.opponents_moves_memory = []
    
    def move(self):
        '''Returns False/True decision of the player (random in this case).'''
        cooperate = self.cooperate
        defect = self.defect
        
        basket_for_drawing_moves = [cooperate, defect]
        result_move = random.choice(basket_for_drawing_moves)       
        
        
        return result_move
    
    def record_opponents_move(self,opponent_move):
        '''Saves opponents previous moves into MyPlayers memory.'''
        self.opponents_moves_memory.append(opponent_move)
        
if __name__ == "__main__":
    p = MyPlayer([[[0,0],[0,0]], [[0,0],[0,1]]])
    p.move()