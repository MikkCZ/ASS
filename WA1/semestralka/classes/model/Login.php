<?php

namespace model;

/**
 * Login handles getting information for the login form.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Login extends ModelClass {

    private $status;

    protected function load() {
        if (isset($_GET['logout'])) {
            \controller\model\LoginController::getInstance()->logout();
        }
        $this->status = $this->login();
        $this->loggedIn = $this->loggedIn();
    }

    private function login() {
        $username = trim(filter_input(INPUT_POST, 'username'));
        $password = trim(filter_input(INPUT_POST, 'password'));
        if ($username == NULL && $password == NULL) {
            return NULL;
        }
        return \controller\model\LoginController::getInstance()->login($username, $password);
    }

    /**
     * Returns the status of the login form or request.
     * 
     * @return mixed boolean (if the login was successful of not) or NULL, if no login attempt has been sent
     */
    public function getStatus() {
        return $this->status;
    }

}
