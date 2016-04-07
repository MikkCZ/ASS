<?php

namespace admin\model;

/**
 * Index handles getting information shown on the index page.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Index extends ModelClass {

    protected function load() {
        if (\controller\model\LoginController::getInstance()->loggedIn() == false) {
            throw new LoginException();
        }
    }

}
