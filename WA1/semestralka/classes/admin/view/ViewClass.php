<?php

namespace admin\view;

/**
 * Description of ViewClass
 *
 * @property \model\ModelClass $model model class implementation according to the request
 * @property string $title
 * @property string $headline
 * @property string $navHTML
 * @property string $copyrightText
 * @property string $generalTemplate
 * 
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
abstract class ViewClass {

    protected $model;
    protected $title;
    protected $headline;
    protected $navHTML;
    protected $copyrightText;
    protected $generalTemplate;

    public function __construct(\admin\model\ModelClass $model) {
        $this->model = $model;
        $this->title = $model->getTitle();
        $this->headline = $model->getHeadline();
        $this->prepareNavHTML();
        $this->copyrightText = $model->getCopyright();
        $this->generalTemplate = \Config::getInstance()->getRootDir() . '/admin/theme/general.php';
    }

    /**
     * Call to display output the HTML.
     */
    public abstract function display();

    /**
     * Universal fallback function, which outputs the <main> tag content as designed by default.
     */
    protected abstract function mainContent();

    /**
     * Universal fallback function, which outputs the <head> tag content as designed by default.
     */
    protected function head() {
        echo '<meta charset = "utf-8">';
        echo '<title>';
        $this->title();
        echo '</title>';
        echo '<link rel = "stylesheet" type = "text/css" href = "';
        $this->themeURL();
        echo 'css/main.css" title = "Klasický">';
        $this->js();
    }

    /**
     * Intended to be overridden by the view implementation, which would like to have some javascript files in the <head>.
     */
    protected function js() {
        // nothing
    }

    /**
     * Intended to be overridden by the view implementation, which would like to have a breadcrumb navigation.
     */
    protected function breadcrumb() {
        // nothing
    }

    /**
     * Prepares main <nav> according the user is logged in or not.
     */
    protected function prepareNavHTML() {
        $loggedIn = $this->model->loggedIn();
        $rootURL = dirname(\Config::getInstance()->getRootURL());
        if ($rootURL == '/') {
            $rootURL = '';
        }
        if ($loggedIn == false) {
            $this->navHTML = '<nav>
        <a href="' . $rootURL . '/">Domů</a>
        <a href="' . $rootURL . '/signup">Registrace</a>
        <a href="' . $rootURL . '/admin/login">Přihlásit se</a>
    </nav>';
        } else {
            $this->navHTML = '<nav>
        <a href="' . $rootURL . '/admin/user">' . $loggedIn->getPublicName() . '</a>
        <a href="' . $rootURL . '/">Domů</a>';
            if ($loggedIn->isAdmin()) {
                $this->navHTML .= '<a href="' . $rootURL . '/admin/users">Uživatelé</a>';
                $this->navHTML .= '<a href="' . $rootURL . '/admin/settings">Nastavení</a>';
            }
            $this->navHTML .= '<a href="' . $rootURL . '/login?logout">Odhlásit se</a>
    </nav>';
        }
    }

    /**
     * Returns the application theme URL (which is currently used).
     * 
     * @return string application theme URL
     */
    protected function getThemeURL() {
        return \Config::getInstance()->getRootURL() . '/theme/';
    }

    protected function title() {
        echo $this->title;
    }

    protected function headline() {
        echo $this->headline;
    }

    protected function themeURL() {
        echo $this->getThemeURL();
    }

    protected function rootURL() {
        echo \Config::getInstance()->getRootURL();
    }

    protected function navHTML() {
        echo $this->navHTML;
    }

    protected function copyright() {
        echo $this->copyrightText;
    }

    /**
     * Function which includes the theme template file or a general one, if not found.
     * 
     * @param string $template
     */
    protected function includeTemplate($template) {
        $templateFile = \Config::getInstance()->getRootDir() . '/admin/theme/' . $template . '.php';
        if (file_exists($templateFile)) {
            include $templateFile;
        } else {
            include $this->generalTemplate;
        }
    }

}
