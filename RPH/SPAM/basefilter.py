import os
class BaseFilter:
    '''Parent for simple filters in simplefilters.py module'''
    def __init__(self):
        self.pos_tag = 'SPAM'
        self.neg_tag = 'OK'
    
    def train(self, corpus_dir):
        pass
    
    def test(self, corpus_dir):
        raise NotImplementedError
    
    def listdir_file_names(self, corpus_dir):
        '''Returns a list of files in the directory (ignores special files beginning with "!").'''
        file_list = []
        for fname in os.listdir(corpus_dir):
            if not fname.startswith('!'):
                file_list.append(fname)
        return file_list
    
    def write_prediction_to_file(self, corpus_dir, pred_dict):
        '''Writes the prediction (given as dictionary) into the prediction file.'''
        pred_file_name = '!prediction.txt'
        pred_file_path = os.path.join(corpus_dir, pred_file_name)
        with open(pred_file_path, 'w', encoding='utf-8') as pred_file:
            for fname in pred_dict:
                pred_file.write(fname + ' ' + pred_dict[fname] + '\n')
                
        