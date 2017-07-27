import random

class MyPlayer:
    '''Hrac hraje podle predchoziho tahu protihrace, matice hry a castecne nahodne.'''
    
    def __init__(self,payoff_matrix,number_of_iterations=0):
        '''Constructor: Number of iterations doesn't have to be given. Also contains MyPlayers memory.'''
        self.pm = payoff_matrix
        self.noi = number_of_iterations
        self.cooperate = False
        self.defect = True
        
        self.find_dominant_strategy()    # result: self.dominant_strategy containing the calculated dominant move
        self.opponents_previous_move = self.dominant_strategy    # the same as dominant strategy if none set by record_opponents_move() - tipically during the first iteration

    def find_dominant_strategy(self):
        '''Tries to find the dominant strategy for the payoff matrix. When no found, cooperation is chosen.'''
        pm = self.pm
        cooperate = self.cooperate
        defect = self.defect
        
        benefit_of_cooperations_iter = pm[0][0][0]    # both players cooperate
        benefit_of_defects_iter = pm[1][1][1]    # both players defect
        benefit_of_cooperation = (pm[0][0][0] + pm[0][1][0]) - (pm[0][0][1] + pm[0][1][1])
        benefit_of_defect = (pm[1][0][0] + pm[1][1][0]) - (pm[1][0][1] + pm[1][1][1])
        
        # Changes the dominant strategy to defect only if D-D iteration gives more points than C-C.
        # In the case when both are the same or C-C gives more points, cooperation is chosen rather.
        # After this if-condition something has to be chosen.
        if benefit_of_defects_iter > benefit_of_cooperations_iter:
            dominant_strategy = defect
        else:
            dominant_strategy = cooperate
            
        # Changes the dominant strategy according to decision giving more points in all combinations.
        # When both are the same, the previous decision (based on C-C/D-D iterations) stays unchanged.
        if benefit_of_defect > benefit_of_cooperation:
            dominant_strategy = defect
        if benefit_of_cooperation > benefit_of_defect:
            dominant_strategy = cooperate
        
        self.dominant_strategy = dominant_strategy
    
    def move(self):
        '''
        This method decides about the final move /randomly/ according to this move configuration:
        - two times dominant strategy move,
        - three times opponents previous move.
        '''
        cooperate = self.cooperate
        defect = self.defect
        dominant = self.dominant_strategy
        opponents = self.opponents_previous_move
        
        basket_for_drawing_moves = [dominant, dominant, opponents, opponents, opponents]
        
        result_move = random.choice(basket_for_drawing_moves)
        return result_move
    
    def record_opponents_move(self,opponent_move):
        '''Saves opponents previous move (this information can be used in the next iteration).'''
        self.opponents_previous_move = opponent_move
        
