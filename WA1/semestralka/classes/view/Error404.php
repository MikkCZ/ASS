<?php

namespace view;

/**
 * Description of Error404
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Error404 extends ViewClass {

    public function display() {
        $this->includeTemplate('error');
    }

    protected function mainContent() {
        $this->message();
    }

    /**
     * Outputs the error message.
     */
    protected function message() {
        echo '<h2>404: Stránku se nepodařilo nalézt</h2>';
        if ($this->model->loggedIn() == false) {
            echo '<br>';
            echo 'Zkuste se <a href="./login">přihlásit</a>.';
        }
    }

}
