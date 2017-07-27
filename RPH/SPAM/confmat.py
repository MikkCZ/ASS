class BinaryConfusionMatrix:
    '''Recognizes filters correct/wrong preditictions'''
    def __init__(self, pos_tag, neg_tag):
        '''Initiates the counter and defines positive/negative tags.'''
        self.pos_tag = pos_tag
        self.neg_tag = neg_tag
        self.tp = 0
        self.tn = 0
        self.fp = 0
        self.fn = 0
        
    def compute_from_dicts(self, truth_dict, pred_dict):
        '''Call this method to start counting.'''
        for fname in pred_dict:
            self.update(truth_dict[fname], pred_dict[fname])
            
    def update(self, truth, prediction):
        '''Updates the counter according to given prediction velues.'''
        if prediction == truth:
            if prediction == self.pos_tag:
                self.tp += 1
            else:
                self.tn += 1
        else:
            if prediction == self.pos_tag:
                self.fp += 1
            else:
                self.fn += 1
                
    def as_dict(self):
        '''Returns the counter as a dictionary.'''
        dict = {}
        dict['tp'] = self.tp
        dict['tn'] = self.tn
        dict['fp'] = self.fp
        dict['fn'] = self.fn
        return dict
    