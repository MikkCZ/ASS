<?php

namespace admin\view;

/**
 * Description of User
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class User extends ViewClass {

    private $user;
    private $status;
    private $formHTML;
    private $createHTML;
    private $galleriesHTML;

    protected function breadcrumb() {
        $rootURL = \Config::getInstance()->getRootURL();
        echo '<nav id="breadcrumb">
                        <a href="' . $rootURL . '">'.$this->model->getTitle().'</a> &gt; <a href="' . $rootURL . '/user?id=' . $this->user->getID() . '">' . $this->user->getPublicName() . '</a>
                  </nav>';
    }

    public function display() {
        $this->user = $this->model->getUser();
        $this->status = $this->model->getStatus();
        $this->prepareFormHTML();
        $this->prepareCreateHTML();
        $galleries = $this->model->getGalleries();
        if ($galleries != NULL) {
            $this->prepareGalleriesHTML($galleries);
        }
        $this->includeTemplate('user');
    }

    protected function mainContent() {
        $this->breadcrumb();
        $this->formHTML();
        $this->createHTML();
        $this->galleriesHTML();
    }

    private function prepareCreateHTML() {
        $this->createHTML = '<form action = "?id=' . $this->user->getID() . '&amp;create" method="post">
                <label for = "name">Název galerie:</label>
                <input type = "text" name = "createname" id = "createname" required>
                <input type = "submit" name = "submit" value = "Vytvořit" id = "create">
            </form>';
    }

    private function prepareFormHTML() {
        if ($this->status === NULL) {
            $this->simpleForm();
        } else if ($this->status === true) {
            $this->formOK();
        } else {
            $this->errorForm();
        }
    }

    private function errorForm() {
        $this->formHTML = '<div class="error">
                Změna údajů neproběhla úspěšně. Zkontolujte prosím správnost zadávaných údajů.
            </div>';
        $this->formHTML .= '<form action = "?id=' . $this->user->getID() . '&amp;update" method = "post">';
        $this->formHTML .= '<label for = "name">Vaše jméno:</label>
            <input type = "text" name = "name" id = "name" value="' . filter_input(INPUT_POST, 'name') . '">';
        if (isset($this->status['email'])) {
            $this->formHTML .= '<label for = "email">E-mail:<span class="warning">' . $this->status['email'] . '</span></label>'
                    . '<input type="text" name="email" id="email" required value="' . filter_input(INPUT_POST, 'email') . '">';
        } else {
            $this->formHTML .= '<label for = "email">E-mail:</label>
            <input type = "text" name = "email" id = "email" required value="' . filter_input(INPUT_POST, 'email') . '">';
        }
        if (isset($this->status['new_password'])) {
            $this->formHTML .= '<label for = "new_password">Nové heslo:<span class="warning">' . $this->status['new_password'] . '</span></label>'
                    . '<input type = "password" name = "new_password" id = "new_password" pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">';
        } else {
            $this->formHTML .= '<label for = "new_password">Nové heslo:</label>
            <input type = "password" name = "new_password" id = "new_password" pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">';
        }
        if ($this->model->loggedIn()->isAdmin()) {
            $this->formHTML .= '<label for = "role">Role:</label>
                <select name="role" id="role">';
            $user_role_is = $this->user->getRoleID();
            foreach ($this->model->getRoles() as $role) {
                $id = $role['id'];
                if ($id == $user_role_is) {
                    $this->formHTML .= '<option value="' . $id . '" selected>' . $role['name'] . '</option>';
                } else {
                    $this->formHTML .= '<option value="' . $id . '">' . $role['name'] . '</option>';
                }
            }
            $this->formHTML .= '</select>';
        }
        $this->formHTML .= '<input type = "submit" name = "submit" value = "Nastavit" id = "submit">
        </form>';
    }

    private function formOK() {
        $this->formHTML = '<div class = "ok">
            Změna údajů proběhla úspěšně.
        </div>';
        $this->formHTML .= $this->simpleForm();
    }

    private function simpleForm() {
        $this->formHTML .= '<form action = "?id=' . $this->user->getID() . '&amp;update" method = "post">
            <label for = "name">Vaše jméno:</label>
            <input type = "text" name = "name" id = "name" value="' . $this->user->getName() . '">
            <label for = "email">E-mail:</label>
            <input type = "text" name = "email" id = "email" required value="' . $this->user->getEmail() . '">
            <label for = "new_password">Nové heslo:</label>
            <input type = "password" name = "new_password" id = "new_password" pattern=".{' . \Users::MIN_PASSWORD_LENGTH . ',}">';
        if ($this->model->loggedIn()->isAdmin()) {
            $this->formHTML .= '<label for = "role">Role:</label>
                <select name="role" id="role">';
            $user_role_is = $this->user->getRoleID();
            foreach ($this->model->getRoles() as $role) {
                $id = $role['id'];
                if ($id == $user_role_is) {
                    $this->formHTML .= '<option value="' . $id . '" selected>' . $role['name'] . '</option>';
                } else {
                    $this->formHTML .= '<option value="' . $id . '">' . $role['name'] . '</option>';
                }
            }
            $this->formHTML .= '</select>';
        }
        $this->formHTML .= '<input type = "submit" name = "submit" value = "Nastavit" id = "submit">
        </form>';
    }

    private function prepareGalleriesHTML(array $galleries) {
        $this->galleriesHTML = '<section id="user-galleries">
                    <h3>Seznam galerií</h3>
                    <ul>';
        foreach ($galleries as $gallery) {
            $id = $gallery['id'];
            $href = \Config::getInstance()->getRootURL() . '/gallery?id=' . $id;
            $this->galleriesHTML .= '<li>
                    <a href="' . $href . '">' . $gallery['name'] . ' <time>' . $gallery['uploaded'] . '</time></a>
                    <a href="?id=' . $this->user->getID() . '&amp;deleteGallery=' . $id . '" class="delete">X</a>
                    </li>';
        }
        $this->galleriesHTML .= '</ul></section>';
    }

    /**
     * Outputs the form HTML.
     */
    protected function formHTML() {
        echo $this->formHTML;
    }

    /**
     * Outputs galleries of the user.
     */
    protected function galleriesHTML() {
        echo $this->galleriesHTML;
    }

    /**
     * Outputs create form HTML.
     */
    protected function createHTML() {
        echo $this->createHTML;
    }

}
