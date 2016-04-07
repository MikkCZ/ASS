<?php

namespace admin\view;

/**
 * Description of Users
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Users extends ViewClass {

    private $users;
    private $usersHTML;

    protected function mainContent() {
        $this->usersHTML();
    }

    public function display() {
        $this->users = $this->model->getUsers();
        //$this->status = $this->model->getStatus();
        $this->prepareUsersHTML($this->users);
        $this->includeTemplate('users');
    }

    private function prepareUsersHTML(array $users) {
        $this->usersHTML = '<section id="users">
                    <h3>Seznam uživatelů</h3>
                    <ul>';
        foreach ($users as $user) {
            $id = $user['id'];
            $href = \Config::getInstance()->getRootURL() . '/user?id=' . $id;
            $this->usersHTML .= '<li>
                    <a href="' . $href . '">' . $user['username'] . '</a>
                    <a href="?deleteUser=' . $id . '" class="delete">X</a>
                    </li>';
        }
        $this->usersHTML .= '</ul></section>';
    }

    /**
     * Outputs users list HTML.
     */
    protected function usersHTML() {
        echo $this->usersHTML;
    }

}
