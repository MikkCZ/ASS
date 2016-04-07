<?php

namespace model;

/**
 * Signup handles getting information for the signup form.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Signup extends ModelClass {

    private $status;

    protected function load() {
        if ($this->loggedIn()) {
            $this->status = 'loggedIn';
        } else if (isset($_GET['signup'])) {
            try {
                $this->status = $this->signupNewUser();
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else {
            $this->status = NULL;
        }
    }

    /**
     * Tries to signup new user with the information filled in the signup form.
     * 
     * @return mixed true if successfull, else $status array given from the formValid() function
     */
    private function signupNewUser() {
        $status = $this->formValid();
        if (empty($status)) {
            $user = new \Users();
            $user->setUserName(trim(filter_input(INPUT_POST, 'username')));
            $user->setPassword(trim(filter_input(INPUT_POST, 'password')));
            $user->setEmail(trim(filter_input(INPUT_POST, 'email')));
            $user->setName(filter_input(INPUT_POST, 'name'));
            $user->save();
            return true;
        }
        return $status;
    }

    /**
     * Returns the status of the signup form.
     * 
     * @return array with information about the form validation
     */
    private function formValid() {
        $status = array(
                /* 'username' => NULL,
                  'password' => NULL,
                  'email' => NULL */
        );
        // username
        if (!isset($_POST['username'])) {
            $status['username'] = 'Povinné';
        } else if (strpos(trim(filter_input(INPUT_POST, 'username')), ' ') !== false) {
            $status['username'] = 'Mezera není povolena';
        } else if (\UsersTable::getInstance()->usernameExists(trim(filter_input(INPUT_POST, 'username')))) {
            $status['username'] = 'Uživatel již existuje';
        }
        // email
        if (!isset($_POST['email'])) {
            $status['email'] = 'Povinné';
        } else if (!filter_var(trim(filter_input(INPUT_POST, 'email')), FILTER_VALIDATE_EMAIL)) {
            $status['email'] = 'Neplatné';
        } else if (\UsersTable::getInstance()->emailExists(trim(filter_input(INPUT_POST, 'email')))) {
            $status['email'] = 'Email již existuje';
        }
        // password
        if (!isset($_POST['password'])) {
            $status['password'] = 'Povinné';
        } else if (strlen(utf8_decode(filter_input(INPUT_POST, 'password')))<\Users::MIN_PASSWORD_LENGTH) {
            $status['password'] = 'Heslo je kratší než 6 znaků';
        }else if (strpos(trim(filter_input(INPUT_POST, 'password')), ' ') !== false) {
            $status['password'] = 'Mezera není povolena';
        }
        if (isset($_POST['password']) && isset($_POST['password2']) && trim(filter_input(INPUT_POST, 'password')) != trim(filter_input(INPUT_POST, 'password2'))) {
            $status['password'] = 'Hesla nesouhlasí';
        }
        return $status;
    }

    /**
     * Returns the status of the signup form or request.
     * 
     * @return mixed array with information about the unsuccessful form validation (false, when unknown error occurs), true if sinup successful or NULL, if no login attempt has been sent
     */
    public function getStatus() {
        return $this->status;
    }

}
