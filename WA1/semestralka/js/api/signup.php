<?php

require_once '../../config.php';

/*
 * Basic autoload function for the classes folder and subflders for classes in appropriate namespaces..
 */
spl_autoload_register(function ($class_name) {
    $class_path = ROOT_DIR . '/' . CLASSES_FOLDER . '/' . str_replace("\\", '/', $class_name) . '.php';
    if (file_exists($class_path)) {
        require $class_path;
        return true;
    }
    return false;
});

/**
 * If the username nor email GET params are given, calling this script makes no sense.
 */
if (!isset($_GET['username']) && !isset($_GET['email'])) {
    http_response_code(403);
    die();
}

/*
 * Check if the DB connection is well configured or server available.
 */
if (!controller\db\DBController::getInstance()->checkDBConnection()) {
    die('Nelze se pripojit k databazi.');
}

/**
 * Check the given username is already used.
 * 
 * @param type $username to check
 * @return boolean
 */
function usernameExists($username) {
    return (bool) \UsersTable::getInstance()->usernameExists($username);
}

/**
 * Check the given email is already used.
 * 
 * @param type $email to check
 * @return boolean
 */
function emailExists($email) {
    return (bool) \UsersTable::getInstance()->emailExists($email);
}

/**
 * Echo 1 if the given username/email is already registered, else echo 0, if still free.
 */
if (isset($_GET['username']) && usernameExists(filter_input(INPUT_GET, 'username'))) {
    die('1');
}
if (isset($_GET['email']) && emailExists(filter_input(INPUT_GET, 'email'))) {
    die('1');
}
die('0');
