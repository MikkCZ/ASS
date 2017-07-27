import os
from email.parser import Parser
class TestingCorpus:
    '''Class of methods for test handling.'''
    def __init__(self, corpus_dir):
        self.corpus_dir = corpus_dir
        self.file_names_list = self.list_file_names()
        self.pos_tag = 'SPAM'
        self.neg_tag = 'OK'
        
    def list_file_names(self):
        '''Returns a list of files in the directory (ignores special files beginning with "!").'''
        file_list = []
        for fname in os.listdir(self.corpus_dir):
            if not fname.startswith('!'):
                file_list.append(fname)
        return file_list
    
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
        
