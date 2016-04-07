<?php

namespace admin\view;

/**
 * Description of Settings
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Settings extends ViewClass {

    private $titleFromDB;
    private $copyright;
    private $used_theme;
    private $themes;
    private $formHTML;
    private $status;

    protected function mainContent() {
        $this->formHTML();
    }

    public function display() {
        $this->titleFromDB = $this->model->getTitleFromDB();
        $this->status = $this->model->getStatus();
        $this->copyright = $this->copyrightText;
        $this->used_theme = $this->model->getUsedTheme();
        $this->themes = $this->model->getThemes();
        $this->prepareFormHTML();
        $this->includeTemplate('users');
    }

    private function prepareFormHTML() {
        if ($this->status === NULL) {
            $this->simpleForm();
        } else if ($this->status === true) {
            $this->formOK();
        }
    }

    private function formOK() {
        $this->formHTML = '<div class = "ok">
            Změna nastavení proběhla úspěšně.
        </div>';
        $this->formHTML .= $this->simpleForm();
    }

    private function simpleForm() {
        $this->formHTML .= '<form action = "?update" method = "post">
            <label for = "title">Název:</label>
            <input type = "text" name = "title" id = "title" value="' . $this->titleFromDB . '">
            <label for = "copyrightarea">Copyright:</label>
            <textarea name="copyright" id="copyrightarea">' . $this->copyright . '</textarea>';
        $this->formHTML .= '<label for = "theme">Vzhled:</label>
                <select name="theme" id="theme">';
        foreach ($this->themes as $theme) {
            if ($this->used_theme == $theme) {
                $this->formHTML .= '<option value="' . $theme . '" selected>' . $theme . '</option>';
            } else {
                $this->formHTML .= '<option value="' . $theme . '">' . $theme . '</option>';
            }
        }
        $this->formHTML .= '</select>';
        $this->formHTML .= '<input type = "submit" name = "submit" value = "Nastavit" id = "submit">
        </form>';
    }

    /**
     * Outputs form HTML.
     */
    protected function formHTML() {
        echo $this->formHTML;
    }

}
