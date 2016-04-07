<?php

namespace controller\model;

/**
 * ModelController handles model side request service.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class ModelController {

    private static $instance = NULL;

    private function __construct() {
        
    }

    /**
     * Returns the ModelController singleton instace.
     * 
     * @return ModelController singleton instance
     */
    public static function getInstance() {
        if (self::$instance == NULL) {
            self::$instance = new ModelController();
        }
        return self::$instance;
    }

    /**
     * Parses the given URL, finds the appropriate model implementation and than forwards it to the view component.
     * 
     * @param string $relative_request_url request URL relative to the application root
     */
    public function loadAndDisplay($relative_request_url) {
        $request_array = explode('/', str_replace('?', '/', $relative_request_url));
        $requested = $request_array[1];
        $model = ModelFactory::getModel($requested);
        try {
            $model->setUrlAndLoad($relative_request_url);
        } catch (\model\NotFoundException $e) {
            $model = new \model\Error404;
            $model->setUrlAndLoad($relative_request_url);
        }
        \controller\view\ViewController::getInstance()->display($model);
    }

}
