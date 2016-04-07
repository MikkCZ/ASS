<?php

namespace admin\view;

/**
 * Description of Index
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Index extends ViewClass {

    public function display() {
        $this->includeTemplate('index');
    }

    protected function mainContent() {
        $this->navHTML();
    }

}
