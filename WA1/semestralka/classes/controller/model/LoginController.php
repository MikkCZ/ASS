<?php

namespace controller\model;

/**
 * LoginController is used to handle the login and logout of users.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class LoginController {

    private static $instance = NULL;

    private function __construct() {
        
    }

    /**
     * Returns the LoginController singleton instace.
     * 
     * @return LoginController singleton instance
     */
    public static function getInstance() {
        if (self::$instance == NULL) {
            self::$instance = new LoginController();
        }
        return self::$instance;
    }

    /**
     * Check if the users is currently logged in (according to the session).
     * 
     * @return \Users logged if user (if logged in)
     */
    public function loggedIn() {
        @session_start();
        if (!isset($_SESSION['username']) || !\UsersTable::getInstance()->usernameExists($_SESSION['username'])) {
            $this->logout();
        } else
        if (!isset($_SESSION['lastAccess']) || $_SESSION['lastAccess'] < time() - \Config::getInstance()->getLoginExpire()) {
            $this->logout();
        } else {
            $_SESSION['lastAccess'] = time();
            return \UsersTable::getInstance()->getByUsername($_SESSION['username']);
        }
    }

    /**
     * Try to login user with given credentials.
     * 
     * @param string $username username
     * @param string $password password in plaintext
     * @return boolean if the login was succesfull
     */
    public function login($username, $password) {
        @session_start();
        if ($username != NULL && $password != NULL) {
            $login = (bool) \UsersTable::getInstance()->login($username, $password);
        } else {
            $login = (bool) $this->loggedIn();
        }
        if ($login) {
            $_SESSION['username'] = $username;
            $_SESSION['lastAccess'] = time();
        } else {
            $this->logout();
        }
        return $login;
    }

    /**
     * Logout the current user.
     */
    public function logout() {
        @session_start();
        session_destroy();
    }

}
