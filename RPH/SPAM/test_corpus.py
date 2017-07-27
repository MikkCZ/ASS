#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""Test the Corpus class."""

import os
import shutil
import unittest
from test_readClassificationFromFile import (
    random_filename, random_string,
    FNAMECHARS)

from corpus import Corpus

TRUTH_FILENAME = '!truth.txt'
CORPUSDIR = 'testing_corpus_delete_me'
FCONTENTSCHARS = FNAMECHARS + '\n '
NEMAILS = 20

class TestCorpus(unittest.TestCase):

    def setUp(self):
        """Prepare classification file for the test."""
        self.expected = create_corpus_dictionary(NEMAILS)
        create_corpus_dir_from_dictionary(self.expected)
    
    def tearDown(self):
        delete_corpus_directory()
        
    def test_corpusContainsOnlyEmails(self):
        """Test reading the corpus with email messages only."""
        corpus = Corpus(CORPUSDIR)
        # Exercise the SUT
        nitems = 0
        for fname, contents in corpus.emails_as_string():
            nitems += 1
            # Validate the results
            self.assertEqual(self.expected[fname], contents,
                             'The read file contents are not equal to the expected contents.')
        self.assertEqual(nitems, NEMAILS,
                         'The emails_as_string() method did not return the right number of files.')
        
    def test_corpusContainsAlsoSpecialFiles(self):
        """Test reading the corpus with special files."""
        # Add a special file into the corpus dir
        fname = TRUTH_FILENAME
        contents = 'fake'
        save_file_to_corpus_dir(fname, contents, CORPUSDIR)        
        # Exercise the SUT
        corpus = Corpus(CORPUSDIR)
        nitems = 0
        for fname, contents in corpus.emails_as_string():
            nitems += 1
            # Validate results
            self.assertEqual(self.expected[fname], contents,
                             'The read file contents are not equal to the expected contents.')
        self.assertEqual(nitems, NEMAILS,
                         'The emails_as_string() method did not return the right number of files.')

def create_corpus_dictionary(nitems=NEMAILS):
    """Create a random dictionary of email file names and their contents."""
    d = {}
    for i in range(nitems):
        filename = random_filename()
        contents = random_string(200, chars=FCONTENTSCHARS)
        d[filename] = contents
    return d

def create_corpus_dir_from_dictionary(d, dirname=CORPUSDIR):
    """Save the dictionary to a directory."""
    os.makedirs(dirname, exist_ok=True)
    for fname, contents in d.items():
        save_file_to_corpus_dir(fname, contents, dirname)
    
def save_file_to_corpus_dir(fname, contents, dirname=CORPUSDIR):
    """Save the contents to the file into the dirname directory."""
    fpath = os.path.join(dirname, fname)
    with open(fpath, 'wt', encoding='utf-8') as f:
        f.write(contents)
        
def delete_corpus_directory(dirname=CORPUSDIR):
    """Delete the directory with testing corpus."""
    shutil.rmtree(dirname, ignore_errors=True)


if __name__ == "__main__":
    unittest.main()
