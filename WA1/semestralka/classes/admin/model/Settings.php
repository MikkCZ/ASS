<?php

namespace admin\model;

/**
 * Description of Settings
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Settings extends ModelClass {

    private $titleFromDB;
    private $used_theme;
    private $themes;
    private $status;

    protected function load() {
        if (!$this->loggedIn()->isAdmin()) {
            throw new \model\NotFoundException();
        }
        if (isset($_GET['update']) && isset($_POST['submit'])) {
            try {
                $this->status = $this->updateSettings();
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else {
            $this->status = NULL;
        }
        $this->loadGeneric();
        $this->titleFromDB = \SettingsTable::getInstance()->getSettingValue('title');
        $this->used_theme = \SettingsTable::getInstance()->getSettingValue('used_theme');
        $this->themes = \Config::getInstance()->listAllThemes();
    }

    private function updateSettings() {
        $titleSetting = \SettingsTable::getInstance()->getSetting('title');
        if ($titleSetting == NULL) {
            $titleSetting = new \Settings();
            $titleSetting->setName('title');
        }
        $titleSetting->setValue(filter_input(INPUT_POST, 'title'));
        $titleSetting->save();

        $copyrightSetting = \SettingsTable::getInstance()->getSetting('copyright');
        if ($copyrightSetting == NULL) {
            $copyrightSetting = new \Settings();
            $copyrightSetting->setName('copyright');
        }
        $copyrightSetting->setValue(filter_input(INPUT_POST, 'copyright'));
        $copyrightSetting->save();

        if (in_array(filter_input(INPUT_POST, 'theme'), \Config::getInstance()->listAllThemes())) {
            $usedThemeSetting = \SettingsTable::getInstance()->getSetting('used_theme');
            if ($usedThemeSetting == NULL) {
                $usedThemeSetting = new \Settings();
                $usedThemeSetting->setName('used_theme');
            }
            $usedThemeSetting->setValue(filter_input(INPUT_POST, 'theme'));
            $usedThemeSetting->save();
        }
        return true;
    }

    /**
     * Returns currently used theme name.
     * 
     * @return string used theme name
     */
    public function getUsedTheme() {
        return $this->used_theme;
    }

    /**
     * Returns all available theme names in a string array.
     * 
     * @return array available themes
     */
    public function getThemes() {
        return $this->themes;
    }

    /**
     * Returns the status of the form or request.
     * 
     * @return mixed boolean (if successful of not) or NULL, if no login attempt has been sent
     */
    public function getStatus() {
        return $this->status;
    }

    /**
     * Returns application title set in the database.
     * 
     * @return string title
     */
    public function getTitleFromDB() {
        return $this->titleFromDB;
    }

}
