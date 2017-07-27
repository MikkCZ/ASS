import os
import filter
MyFilter = filter.MyFilter()
import quality
def remove_prediction(test_corpus_dir):
    '''Removes !prediction.txt file from the corpus directory.'''
    filepath = os.path.join(test_corpus_dir, '!prediction.txt')
    os.remove(filepath)
    
def myfilter_test(corpus_dir):
    '''Runs MyFilter test and than prints its prediction quality.'''
    MyFilter.train(corpus_dir)
    MyFilter.test(corpus_dir)
    print ("MyFilter:")
    print ( quality.compute_quality_for_corpus(corpus_dir) )
    remove_prediction()
    
def myfilter_test_train_test(train_corpus_dir, test_corpus_dir):
    '''Runs MyFilter test and than prints its prediction quality.'''
    MyFilter.train(train_corpus_dir)
    MyFilter.test(test_corpus_dir)
    print ("MyFilter:")
    print ( quality.compute_quality_for_corpus(test_corpus_dir) )
    remove_prediction(test_corpus_dir)

if __name__== '__main__':
    print("--- train 1, test 2 ---")
    myfilter_test_train_test('spam-data\\1', 'spam-data\\2')
    print("--- train 2, test 1 ---")
    myfilter_test_train_test('spam-data\\2', 'spam-data\\1')
    #print ("--- train 1, test 1a2 ---")
    #myfilter_test_train_test('spam-data\\1', 'spam-data\\1a2')
    #print ("--- train 2, test 1a2 ---")
    #myfilter_test_train_test('spam-data\\2', 'spam-data\\1a2')
    #print ("--- train 1a2, test 1 ---")
    #myfilter_test_train_test('spam-data\\1a2', 'spam-data\\1')
    #print ("--- train 1a2, test 2 ---")
    #myfilter_test_train_test('spam-data\\1a2', 'spam-data\\2')
    #print("--- train 1, test 1 ---")
    #myfilter_test_train_test('spam-data\\1', 'spam-data\\1')
    #print("--- train 2, test 2 ---")    
    #myfilter_test_train_test('spam-data\\2', 'spam-data\\2')
    