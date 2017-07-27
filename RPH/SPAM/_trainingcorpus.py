import os
from email.parser import Parser
class TrainingCorpus:
    '''Class of auxiliary methods for the learning filter.'''
    def __init__(self, corpus_dir):
        self.corpus_dir = corpus_dir
        self.file_names_list = self.list_file_names()
        self.truth_dict = self.truth_as_dict()
        self.pos_tag = 'SPAM'
        self.neg_tag = 'OK'
        
    def list_file_names(self):
        '''Returns a list of files in the directory (ignores special files beginning with "!").'''
        file_list = []
        for fname in os.listdir(self.corpus_dir):
            if not fname.startswith('!'):
                file_list.append(fname)
        return file_list
    
    def truth_as_dict(self):
        '''Returns the truth file as a dictionary.'''
        truth_file_name = '!truth.txt'
        truth_file_path = os.path.join(self.corpus_dir, truth_file_name)
        truth_dict = {}
        with open(truth_file_path, 'r', encoding='utf-8') as truth_file:
            for line in truth_file:
                (fname, classification) = line.split()
                truth_dict[fname] = classification
        return truth_dict

    def get_class(self, fname):
        '''Returns the classification of the required e-mail.'''
        classification = self.truth_dict[fname]
        return classification
    
    def is_ham(self, fname):
        '''Returns True/False if the e-mail is/isn't ham.'''
        if self.get_class(fname) == self.neg_tag:
            return True
        else:
            return False
        
    def is_spam(self,fname):
        '''Returns True/False if the e-mail is/isn't spam.'''
        if self.get_class(fname) == self.pos_tag:
            return True
        else:
            return False
        
    def hams_as_string(self):
        '''Returns tuples of ham file names and the own file as string.'''
        ham_list = []
        for fname in self.truth_dict:
            if self.truth_dict[fname] == self.neg_tag:
                ham_list.append(fname)
        for fname in ham_list:
            file_string = self.file_as_string(fname)
            yield (fname, file_string)

    def spams_as_string(self):
        '''Returns tuples of spam file names and the own file as string.'''
        spam_list = []
        for fname in self.truth_dict:
            if self.truth_dict[fname] == self.pos_tag:
                spam_list.append(fname)
        for fname in spam_list:
            file_string = self.file_as_string(fname)
            yield (fname, file_string)
            
    def file_as_lower_string(self,fname):
        '''Returns the required file as a string converted to lower chars.'''
        return self.file_as_string(fname).lower()    
        
    def file_as_string(self, fname):
        '''Returns the required file as a string. Necessary for file_as_lower_string().'''
        file_path = os.path.join(self.corpus_dir, fname)
        with open(file_path, 'r', encoding='utf-8') as file:
            file_string = file.read()
        return file_string
    
    def parse_email(self, fname):
        '''Used for get important parts of the mail.'''
        parser = Parser()
        body = self.file_as_lower_string(fname)
        email = parser.parsestr(body)
        sender = self.get_sender(email)
        subject = self.get_subject(email)
        return (sender, subject, body)
        
    def get_sender(self, email):
        '''Returns the sender's address. Necessary for parse_email().'''
        sender = email['from']
        if sender == None:
            return 'none'
        if "<" in sender:
            strip_chars = ''
            for char in sender:
                if char == '<':
                    break
                else:
                    strip_chars += char
            sender = sender.lstrip(strip_chars)
        return sender.strip().strip('<>')
    
    def get_subject(self, email):
        '''Returns the subject. Necessary for parse_email().'''
        subject = email['subject']
        if subject == None:
            return 'none'
        return subject
        
