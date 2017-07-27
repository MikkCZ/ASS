import os

def quality_score(tp, tn, fp, fn):
    '''Counts the quality score from the correct/wrong counter.'''
    score = (tp + tn) / (tp + tn + 10*fp + fn)
    return score
    
def compute_quality_for_corpus(corpus_dir):
    '''Return the quality score for tested corpus (with truth and prediction files).'''
    from utils import read_classification_from_file as load_as_dict
    truth_file = '!truth.txt'
    pred_file = '!prediction.txt'
    truth_dict = load_as_dict(os.path.join(corpus_dir, truth_file))
    pred_dict = load_as_dict(os.path.join(corpus_dir, pred_file))
    
    from confmat import BinaryConfusionMatrix
    pos_tag = 'SPAM'
    neg_tag = 'OK'
    cm = BinaryConfusionMatrix(pos_tag, neg_tag)
    
    cm.compute_from_dicts(truth_dict, pred_dict)
    
    confusion_dict = cm.as_dict()
    tp = confusion_dict['tp']
    tn = confusion_dict['tn']
    fp = confusion_dict['fp']
    fn = confusion_dict['fn']
    
    return quality_score(tp, tn, fp, fn)
