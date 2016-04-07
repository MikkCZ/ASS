<?php

namespace controller\db;

/*
 * Require the Doctrine library class and register its autoload functions.
 */
require_once \Config::getInstance()->getRootDir() . '/lib/doctrine/lib/Doctrine.php';
spl_autoload_register(array('Doctrine', 'autoload'));
spl_autoload_register(array('Doctrine_Core', 'modelsAutoload'));

/*
 * Register the autoload functions for the Doctrine library generated models.
 */
spl_autoload_register(function ($class_name) {
    $class_path = \Config::getInstance()->getClassesDir() . '/model/db/' . str_replace("\\", '/', $class_name) . '.php';
    if (file_exists($class_path)) {
        require $class_path;
        return true;
    }
    return false;
});
spl_autoload_register(function ($class_name) {
    $class_path = \Config::getInstance()->getClassesDir() . '/model/db/generated/' . str_replace("\\", '/', $class_name) . '.php';
    if (file_exists($class_path)) {
        require $class_path;
        return true;
    }
    return false;
});

/**
 * DBController is used to handle the DB connection. Should be used getting the PDO or Doctrine connection if ever needed.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class DBController {

    private static $instance = NULL;
    private $connection = NULL;

    private function __construct() {
        
    }

    /**
     * Returns the DBController singleton instace.
     * 
     * @return DBController singleton instance
     */
    public static function getInstance() {
        if (self::$instance == NULL) {
            self::$instance = new DBController();
        }
        return self::$instance;
    }

    /**
     * Checks the database connection is well configured and the server available for connection.
     * 
     * @return boolean true if the database connection can be established
     */
    public function checkDBConnection() {
        try {
            $this->getConnection();
            return true;
        } catch (\PDOException $e) {
            return false;
        }
    }

    private function getPDO() {
        return \Config::getInstance()->getPDO();
    }

    /**
     * Return the \Doctrine_Connection instance if ever needed.
     * 
     * @return \Doctrine_Connection Doctrine connection
     */
    public function getConnection() {
        if ($this->connection == NULL) {
            $this->connection = \Doctrine_Manager::connection($this->getPDO());
            $this->connection->setCharset('utf8');
        }
        return $this->connection;
    }

}
