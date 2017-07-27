import os
import basefilter
from collections import Counter
from trainingcorpus import TrainingCorpus
from testingcorpus import TestingCorpus
class MyFilter(basefilter.BaseFilter):
    def __init__(self):
        '''Classificates e-mails dependig of their's headers dominantly.'''
        self.pos_tag = 'SPAM'
        self.neg_tag = 'OK'
        self.spam_sender_counter = Counter()
        self.spam_subject_word_counter = Counter()
        self.spam_body_word_counter = Counter()
        
    def train(self, corpus_dir):
        '''Train method the filter uses to teach according to !truth.txt file.'''
        self.train_corpus_dir = corpus_dir
        # test if the !truth.txt exists, else exit (no error raised)
        truth_file = os.path.join(corpus_dir, '!truth.txt')
        if os.path.exists(truth_file)==False:
            self.trained = False
            return
        
        # create TrainingCorpus object for better training handling
        Corpus = TrainingCorpus(corpus_dir)

        # get spams/hams senders, return_paths, subjects and strings
        for fname in Corpus.truth_dict:
            (sender, subject, body) = Corpus.parse_email(fname)
            if Corpus.truth_dict[fname] == self.neg_tag:
                self.save_ham_header(sender, subject)
                self.save_ham_body(body)
            else:
                self.save_spam_header(sender, subject)
                self.save_spam_body(body)
    
    def save_ham_header(self, sender, subject):
        '''Decreases the spam counter for this header information.'''
        self.spam_sender_counter[sender] -= 1
        for word in subject.split():
            self.spam_subject_word_counter[word] -= 1
            
    def save_spam_header(self, sender, subject):
        '''Inreases the spam counter for this header information.'''
        self.spam_sender_counter[sender] += 1
        for word in subject.split():
            self.spam_subject_word_counter[word] += 1
        
    def save_ham_body(self, body):
        '''Decreases the spam counter for words in this body.'''
        body = body.replace('<', ' ').replace('>', ' ')
        body_words_list = body.split()
        for word in body_words_list:
            self.spam_body_word_counter[word] -= 1
        
    
    def save_spam_body(self, body):
        '''Increases the spam counter for words in this body.'''
        body = body.replace('<', ' ').replace('>', ' ')
        body_words_list = body.split()
        for word in body_words_list:
            self.spam_body_word_counter[word] += 1
        
    def test(self, corpus_dir):
        '''Main test method.'''
        self.test_corpus_dir = corpus_dir
        
        # create TestingCorpus object for better test handling
        Corpus = TestingCorpus(corpus_dir)
        file_list = Corpus.file_names_list
        
        pred_dict = {}
        # decision process here
        for fname in file_list:
            (sender, subject, body) = Corpus.parse_email(fname)
            email_class = self.classificate_email_header(sender, subject)
            # if not recognized as spam, run additional body test
            if email_class != self.pos_tag:
                email_class = self.classificate_email_string(body)
            pred_dict[fname] = email_class
        
        # write all results to the prediction file
        self.write_prediction_to_file(corpus_dir, pred_dict)
        
    def classificate_email_header(self, sender, subject):
        '''Classificates the e-mail based on its header.'''
        email_class = self.neg_tag
            
        if self.spam_sender_counter[sender] > 0:
            email_class = self.pos_tag
            
        if email_class != self.pos_tag:
            subject_words = subject.split()
            subject_word_number = len(subject_words)
            word_classification = 0
            for word in subject_words:
                if self.spam_subject_word_counter[word] > 0:
                    word_classification += self.spam_subject_word_counter[word]
            if word_classification > subject_word_number*subject_word_number:
                email_class = self.pos_tag
        
        return email_class
    
    def classificate_email_string(self, body):
        '''Classificates the e-mail based on its body.'''
        email_class = self.neg_tag
        
        body = body.replace('<', ' ').replace('>', ' ')
        body_words = body.split()
        body_word_number = len(body_words)
        word_classification = 0
        for word in body_words:
            if self.spam_body_word_counter[word] > 500:
                word_classification += self.spam_body_word_counter[word]
        if word_classification > body_word_number*body_word_number*body_word_number:
            email_class = self.pos_tag
        
        return email_class
    
