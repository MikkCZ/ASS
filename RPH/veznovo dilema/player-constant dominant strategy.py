class MyPlayer:
    '''Tento hrac vraci hraje vzdy dominantni strategii. Pokud ji nenajde, spolupracuje.'''
    
    def __init__(self,payoff_matrix,number_of_iterations=0):
        '''Constructor: Number of iterations doesn't have to be given. Also contains the MyPlayer memory.'''
        self.payoff_matrix = payoff_matrix
        self.noi = number_of_iterations
        self.cooperate = False
        self.defect = True
        self.find_dominant_strategy()
        
    def find_dominant_strategy(self):
        '''Tries to find the dominant strategy. When no found, cooperation is chosen.'''
        pm = self.payoff_matrix
        cooperate = self.cooperate
        defect = self.defect
        
        benefit_of_cooperation = (pm[0][0][0] + pm[0][1][0]) - (pm[0][0][1] + pm[0][1][1])
        benefit_of_defect = (pm[1][0][0] + pm[1][1][0]) - (pm[1][0][1] + pm[1][1][1])
        benefit_of_cooperations_iter = pm[0][0][0]
        benefit_of_defects_iter = pm[1][1][1]
        
        # Change the dominant strategy to defect only if D-D iteration gives more points than C-C.
        # In the case when both are the same or C-C gives more points, cooperation is chosen rather.
        if benefit_of_defects_iter > benefit_of_cooperations_iter:
            dominant_strategy = defect
        else:
            dominant_strategy = cooperate
            
        # Change the dominant strategy according to decision giving more points in all combinations.
        # When both are the same, the previous decision stays unchanged.
        if benefit_of_cooperation > benefit_of_defect:
            dominant_strategy = cooperate
        else:
            if benefit_of_defect > benefit_of_cooperation:
                dominant_strategy = defect
        
        self.dominant_strategy = dominant_strategy
    
    def move(self):
        '''Returns dominant strategy value.'''
        result_move = self.dominant_strategy
        return result_move
    
    def record_opponents_move(self,opponent_move=0):
        '''Just for the need of obligation + secured for the case if no value is given.'''
        pass
    
if __name__ == "__main__":
    p = MyPlayer([ [(4,4),(4,4)], [(4,4),(4,4)] ])
    p.move()