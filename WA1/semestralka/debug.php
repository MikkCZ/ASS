<?php

/*
 * Define the process start time to be able to show the script duration at the end.
 */
define('START_TIME', microtime(true));

/**
 * Prints out the peak memory usage during the script processed.
 */
function peak_memory() {
    $memory = memory_get_peak_usage();
    echo '<pre>Peak Memory: ';
    if ($memory < 1024) {
        echo $memory . " B";
    } else if ($memory < 1048576) {
        echo round($memory / 1024, 2) . " KB";
    } else {
        echo round($memory / 1048576, 2) . " MB";
    }
    echo '</pre>';
}

/**
 * Prints out the script running time.
 */
function runtime() {
    echo '<pre>Running time: ';
    echo round(microtime(true) - START_TIME, 3) . ' sec';
    echo '</pre>';
}

/**
 * Prints out the POST array.
 */
function post() {
    array_dump('Post', $_POST);
}

/**
 * Prints out the SESSION array.
 */
function session() {
    array_dump('Session', $_SESSION);
}

/**
 * Basic function for dumping given the array and printing out its name.
 * 
 * @param string $name of the array you would like to print out above it
 * @param array $array to be printed
 */
function array_dump($name, $array) {
    echo '<pre>'.$name.': <br>';
    var_dump($array);
    echo '</pre>';
}

/**
 * Main function to print out all the information needed - should be called only if DEBUG is defined as true.
 */
function telemetry() {
    peak_memory();
    runtime();
    post();
    session();
}
