<?php

namespace controller\view;

/**
 * ModelController handles view side request service.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class ViewController {

    private static $instance = NULL;

    private function __construct() {
        
    }

    /**
     * Returns the ViewController singleton instace.
     * 
     * @return ViewController singleton instance
     */
    public static function getInstance() {
        if (self::$instance == NULL) {
            self::$instance = new ViewController();
        }
        return self::$instance;
    }

    /**
     * Handles the view side of the request forwarding it to the right view implementation according to the given model.
     * 
     * @param \model\ModelClass $model model implementation for the request
     */
    public function display(\model\ModelClass $model) {
        $view = ViewFactory::getView($model);
        $view->display();
    }

}
