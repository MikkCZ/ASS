<?php

require_once '../config.php';
if (DEBUG) {
    require_once '../debug.php';
}

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

/*
 * Try to server the request or return the 500 HTTP response.
 */
try {
    admin\controller\Init::getInstance()->init();
} catch (Exception $e) {
    http_response_code(500);
    if (DEBUG) {
        die($e);
    } else {
        die('500: Unknown error');
    }
}

/*
 * If debugging is enabled, call the telemetry() function, which is defined in the debug.php file.
 */
if (DEBUG) {
    telemetry();
}
