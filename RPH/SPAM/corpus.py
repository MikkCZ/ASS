import os
class Corpus:
    '''Helps to access emails in the corpus directory.'''
    def __init__(self, corpus_dir):
        self.corpus_dir = corpus_dir
        
    def emails_as_string(self):
        '''Returns tuples of e-mail file names and the own file as string.'''
        email_list = self.listdir_file_names(self.corpus_dir)
        for fname in email_list:
            email_path = os.path.join(self.corpus_dir, fname)
            file_string = self.file_as_string(email_path)
            yield (fname, file_string)
        
    def listdir_file_names(self, directory):
        '''Returns a list of files in the directory (ignores special files beginning with "!").'''
        file_list = []
        for fname in os.listdir(directory):
            if not fname.startswith('!'):
                file_list.append(fname)
        return file_list

    def file_as_string(self, file_path):
        '''Return the required file as a string.'''
        with open(file_path, 'r', encoding='utf-8') as file:
            file_string = file.read()
        return file_string
    