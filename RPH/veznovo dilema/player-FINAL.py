import random

class MyPlayer:
    '''Hrac hraje podle tahu protihrace, matice hry a castecne nahodne.'''
    
    def __init__(self,payoff_matrix,number_of_iterations=0):
        '''Constructor: Number of iterations doesn't have to be given. Also contains MyPlayers memory.'''
        self.cooperate = False
        self.defect = True
        self.payoff_matrix = payoff_matrix
        self.find_dominant_strategy()    # result: self.dominant_strategy with the calculated move, self.adv_identic_iteration with the richest move for both players
        self.opponents_previous_move = self.dominant_strategy    # the same as dominant strategy if none set by record_opponents_move() - tipically during the first iteration
        self.iteration_counter = 0    # used to time MyPlayers strategy
        self.list_of_my_moves = []
        self.list_of_opponents_moves = []

    def find_dominant_strategy(self):
        '''Tries to find the dominant strategy for the payoff matrix. When no found, cooperation is chosen.'''
        pm = self.payoff_matrix
        cooperate = self.cooperate
        defect = self.defect
        
        benefit_of_cooperations_iter = pm[0][0][0]    # both players cooperate
        benefit_of_defects_iter = pm[1][1][1]    # both players defect
        benefit_of_cooperation = (pm[0][0][0] + pm[0][1][0]) - (pm[0][0][1] + pm[0][1][1])
        benefit_of_defect = (pm[1][0][0] + pm[1][1][0]) - (pm[1][0][1] + pm[1][1][1])
        
        # Finds advantageous identic iterations:
        # Changes the dominant strategy to defect only if D-D iteration gives more points than C-C.
        # In the case when both are the same or C-C gives more points, cooperation is chosen rather.
        # After this if-condition something has to be chosen and it's saved for other use in the move().
        if benefit_of_defects_iter > benefit_of_cooperations_iter:
            dominant_strategy = defect
            self.adv_identic_iteration = defect
        else:
            dominant_strategy = cooperate
            self.adv_identic_iteration = cooperate
            
        # Finds domimant strategy in the whole matrix:
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
        But if the opponents player seems to repeat MyPlayers moves, tries to reach as much points as possible together.
        '''
        cooperate = self.cooperate
        defect = self.defect
        dominant = self.dominant_strategy
        opponents = self.opponents_previous_move
        self.iteration_counter = self.iteration_counter + 1
        iteration = self.iteration_counter
        my_list = self.list_of_my_moves[-8:-1]
        opponents_list = self.list_of_opponents_moves[-8:]
        
        # Basic /random/ procedure.
        basket_for_drawing_moves = [dominant, dominant, opponents, opponents, opponents]
        result_move = random.choice(basket_for_drawing_moves)
        
        # After ten iterations checks whether the opponent repeats my moves. If so, try to to reach the highest score together.
        # To avoid a sabotage, the first players move shouldn't be dominant.
        if iteration > 10 and my_list == opponents_list:
            result_move = self.adv_identic_iteration
        
        self.list_of_my_moves.append(result_move)
        return result_move
    
    def record_opponents_move(self,opponent_move):
        '''Saves opponents previous move (this information can be used in the next iteration).'''        
        self.opponents_previous_move = opponent_move
        self.list_of_opponents_moves.append(opponent_move)
        