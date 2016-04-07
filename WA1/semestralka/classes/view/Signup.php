<?php

namespace view;

/**
 * Description of Signup
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Signup extends ViewClass {

    private $status;
    private $formHTML;

    public function display() {
        $this->status = $this->model->getStatus();
        $this->prepareFormHTML();
        $this->includeTemplate('form');
    }

    protected function js() {
        echo '<script type="text/javascript" src="' . \Config::getInstance()->getJsURL() . '/signup.js"></script>';
    }

    protected function mainContent() {
        $this->formHTML();
    }

    private function prepareFormHTML() {
        if ($this->status === 'loggedIn') {
            $this->formHTML = 'Již jste přihlášen';
        } else
        if ($this->status === NULL) {
            $this->simpleForm();
        } else if ($this->status === true) {
            $this->formOK();
        } else if ($this->status === false) {
            $this->errorForm('Registrace neproběhla úspěšně. Zkontrolujte prosím vyplnění formuláře níže. Pokud problém přetrvá, obraťte se prosím na správce.');
        } else {
            $this->errorForm('Registrace neproběhla úspěšně. Zkontrolujte prosím vyplnění formuláře níže.');
        }
    }

    private function errorForm($message) {
        $this->formHTML = '<div class="error">' . $message . '</div>';
        $this->formHTML .= '<form action="?signup" method="post">';
        if (isset($this->status['username'])) {
            $this->formHTML .= '<label for="username">Uživatelské jméno:<span class="warning">' . $this->status['username'] . '</span></label>'
                    . '<input type="text" name="username" id="username" required value="' . filter_input(INPUT_POST, 'username') . '">';
        } else {
            $this->formHTML .= '<label for="username">Uživatelské jméno:</label>'
                    . '<input type="text" name="username" id="username" required>';
        }
        if (isset($this->status['email'])) {
            $this->formHTML .= '<label for = "email">E-mailová adresa:<span class="warning">' . $this->status['email'] . '</span></label>'
                    . '<input type = "email" name = "email" id = "email" required value="' . filter_input(INPUT_POST, 'email') . '">';
        } else {
            $this->formHTML .= '<label for = "email">E-mailová adresa:</label>'
                    . '<input type = "email" name = "email" id = "email" required>';
        }
        if (isset($this->status['password'])) {
            $this->formHTML .= '<label for = "password">Heslo:<span class="warning">' . $this->status['password'] . '</span></label>'
                    . '<input type = "password" name = "password" id = "password" required pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">';
        } else {
            $this->formHTML .= '<label for = "password">Heslo:</label>'
                    . '<input type = "password" name = "password" id = "password" required pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">';
        }
        if (isset($this->status['password2'])) {
            $this->formHTML .= '<label for = "password2">Heslo podruhé:<span class="warning">' . $this->status['password2'] . '</span></label>'
                    . '<input type = "password" name = "password2" id = "password2" required pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">';
        } else {
            $this->formHTML .= '<label for = "password2">Heslo podruhé:</label>'
                    . '<input type = "password" name = "password2" id = "password2" required pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">';
        }
        $this->formHTML .= '<label for = "name">Vaše jméno (nepovinné):</label>
        <input type = "text" name = "name" id = "name">
        <input type = "submit" name = "submit" value = "Zaregistrovat" id = "submit">
        </form>';
    }

    private function formOK() {
        $this->formHTML = '<div class = "ok">
        Registrace proběhla úspěšně. Nyní se můžete <a href = "./login">přihlásit</a>.
        </div>';
        $this->simpleForm();
    }

    private function simpleForm() {
        $this->formHTML .= '<form action = "?signup" method = "post">
        <label for = "username">Uživatelské jméno:</label>
        <input type = "text" name = "username" id = "username" required>
        <label for = "email">E-mailová adresa:</label>
        <input type = "email" name = "email" id = "email" required>
        <label for = "password">Heslo:</label>
        <input type = "password" name = "password" id = "password" required pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">
        <label for = "password2">Heslo podruhé:</label>
        <input type = "password" name = "password2" id = "password2" required pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">
        <label for = "name">Vaše jméno (nepovinné):</label>
        <input type = "text" name = "name" id = "name">
        <input type = "submit" name = "submit" value = "Zaregistrovat" id = "submit">
        </form>
        ';
    }

    /**
     * Outputs the form HTML.
     */
    protected function formHTML() {
        echo $this->formHTML;
    }

}
