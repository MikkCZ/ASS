<?php

namespace admin\controller;

/**
 * Init is used for the application admin initialization, resp. the serving the request.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Init {

    private static $instance = NULL;
    private $RELATIVE_REQUEST_URL;

    private function __construct() {
        $this->RELATIVE_REQUEST_URL = substr(filter_input(INPUT_SERVER, 'REQUEST_URI'), strlen(\Config::getInstance()->getRootURL()));
    }

    /**
     * Returns the Init singleton instace.
     * 
     * @return Init singleton instance
     */
    public static function getInstance() {
        if (self::$instance == NULL) {
            self::$instance = new Init();
        }
        return self::$instance;
    }

    /**
     * Chacks the database connection is well configured and forwards the request to the model component.
     */
    public function init() {
        if (!\controller\db\DBController::getInstance()->checkDBConnection()) {
            http_response_code(500);
            die('Nelze se pripojit k databazi.');
        }
        model\ModelController::getInstance()->loadAndDisplay($this->RELATIVE_REQUEST_URL);
    }

}
