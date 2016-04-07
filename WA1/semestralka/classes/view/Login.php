<?php

namespace view;

/**
 * Description of Login
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Login extends ViewClass {

    private $status;
    private $formHTML;

    public function display() {
        $this->status = $this->model->getStatus();
        $this->prepareNavHTML();
        $this->prepareFormHTML();
        $this->includeTemplate('form');
    }

    protected function mainContent() {
        $this->formHTML();
    }

    private function prepareFormHTML() {
        if ($this->status === NULL) {
            $this->simpleForm();
        } else if ($this->status) {
            $this->formOK();
        } else {
            $this->errorForm();
        }
    }

    private function errorForm() {
        $this->formHTML = '<div class="error">
                Přihlášení neproběhlo úspěšně. Zkontolujte prosím správnost zadávaných údajů.
            </div>';
        $this->formHTML .= '<form action="?login" method="post">';
        if (isset($this->status['username'])) {
            $this->formHTML .= '<label for="username">Uživatelské jméno:<span class="warning">' . $this->status['username'] . '</span></label>'
                    . '<input type="text" name="username" id="username" required value="' . filter_input(INPUT_POST, 'username') . '">';
        } else {
            $this->formHTML .= '<label for="username">Uživatelské jméno:</label>'
                    . '<input type="text" name="username" id="username" required>';
        }
        if (isset($this->status['password'])) {
            $this->formHTML .= '<label for = "password">Heslo:<span class="warning">' . $this->status['password'] . '</span></label>'
                    . '<input type = "password" name = "password" id = "password" required>';
        } else {
            $this->formHTML .= '<label for = "password">Heslo:</label>'
                    . '<input type = "password" name = "password" id = "password" required>';
        }
        $this->formHTML .= '<input type = "submit" name = "submit" value = "Přihlásit" id = "submit">
        </form>';
    }

    private function formOK() {
        $this->formHTML = '<div class = "ok">
        Přihlášení proběhlo úspěšně. Můžete přejít <a href="./user">na svou stránku</a>.
        </div>';
    }

    private function simpleForm() {
        $this->formHTML .= '<form action = "?login" method = "post">
        <label for = "username">Uživatelské jméno:</label>
        <input type = "text" name = "username" id = "username" required>
        <label for = "password">Heslo:</label>
        <input type = "password" name = "password" id = "password" required>
        <input type = "submit" name = "submit" value = "Přihlásit" id = "submit">
        </form>';
    }

    /**
     * Outputs the form HTML.
     */
    protected function formHTML() {
        echo $this->formHTML;
    }

}
