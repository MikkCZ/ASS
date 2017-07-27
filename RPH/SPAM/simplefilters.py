import os
import random
from basefilter import BaseFilter

class NaiveFilter(BaseFilter):
    '''Classifies every e-mail as OK.'''
    def test (self, corpus_dir):
        email_list = self.listdir_file_names(corpus_dir)
        pred_dict = {}
        for fname in email_list:
            pred_dict[fname] = self.pos_tag
        self.write_prediction_to_file(corpus_dir, pred_dict)
        
class ParanoidFilter(BaseFilter):
    '''Classifies every e-mail as SPAM.'''
    def test (self, corpus_dir):
        email_list = self.listdir_file_names(corpus_dir)
        pred_dict = {}
        for fname in email_list:
            pred_dict[fname] = self.neg_tag
        self.write_prediction_to_file(corpus_dir, pred_dict)
        
class RandomFilter(BaseFilter):
    '''Classifies e-mails randomly.'''
    def test (self, corpus_dir):
        email_list = self.listdir_file_names(corpus_dir)
        pred_dict = {}
        for fname in email_list:
            pred_dict[fname] = self.random_tag()
        self.write_prediction_to_file(corpus_dir, pred_dict)
                
    def random_tag (self):
        return random.choice([self.pos_tag, self.neg_tag])
    
