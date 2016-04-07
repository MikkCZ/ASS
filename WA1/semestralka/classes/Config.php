<?php

/**
 * Config handles the configuration - should be used to get the webserver dependend information (such as
 * directories or URLs), or PDO for the database.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Config {

    private static $instance = NULL;
    private $pdo;

    private function __construct() {
        
    }

    /**
     * Returns the Config singleton instace.
     * 
     * @return Config singleton instance
     */
    public static function getInstance() {
        if (self::$instance == NULL) {
            self::$instance = new Config();
        }
        return self::$instance;
    }

    /**
     * Returns the PDO instance for the database specified in the config.php file.
     * 
     * @return PDO PDO instance for the database
     */
    public function getPDO() {
        if ($this->pdo == NULL) {
            $dsn = 'mysql:host=' . DB_SERVER . ';dbname=' . DB_NAME;
            $this->pdo = new PDO($dsn, DB_USER, DB_PASS);
        }
        return $this->pdo;
    }

    /**
     * Returns the application root directory.
     * 
     * @return string application root directory
     */
    public function getRootDir() {
        return ROOT_DIR;
    }

    /**
     * Returns the application root URL.
     * 
     * @return string application root URL
     */
    public function getRootURL() {
        if (dirname(filter_input(INPUT_SERVER, 'PHP_SELF')) == '/') {
            $root = '';
        } else {
            $root = dirname(filter_input(INPUT_SERVER, 'PHP_SELF'));
        }
        return $root;
    }

    /**
     * Returns the application classes directory.
     * 
     * @return string application classes directory
     */
    public function getClassesDir() {
        return $this->getRootDir() . '/' . CLASSES_FOLDER;
    }

    /**
     * Returns the application javascript URL.
     * 
     * @return string application javascript URL
     */
    public function getJsURL() {
        return $this->getRootURL() . '/' . JS_FOLDER;
    }

    /**
     * Check all themes available.
     * 
     * @return array theme names as a string array
     */
    public function listAllThemes() {
        $themes_folder = $this->getRootDir() . '/' . THEMES_FOLDER . '/';
        $dirs = glob($themes_folder . '*', GLOB_ONLYDIR);
        foreach ($dirs as $dir) {
            if (file_exists($dir . '/general.php')) {
                $themes[] = basename($dir);
            }
        }
        return $themes;
    }

    /**
     * Returns the application theme directory (which is currently used).
     * 
     * @return string application theme directory
     */
    public function getThemeDir() {
        $theme_used = \SettingsTable::getInstance()->getSettingValue('used_theme');
        return $this->getRootDir() . '/' . THEMES_FOLDER . '/' . $theme_used;
    }

    /**
     * Returns the application theme URL (which is currently used).
     * 
     * @return string application theme URL
     */
    public function getThemeURL() {
        $theme_used = \SettingsTable::getInstance()->getSettingValue('used_theme');
        return $this->getRootURL() . '/' . THEMES_FOLDER . '/' . $theme_used;
    }

    /**
     * Returns the directory where the images are uploaded.
     * 
     * @return string directory for uploding images
     */
    public function getImageUploadDir() {
        $dir = $this->getRootDir() . '/' . IMG_UPLOAD_FOLDER;
        if (!file_exists($dir)) {
            $oldumask = umask(0);
            echo mkdir($dir, 0777, true);
            umask($oldumask);
        }
        return $dir;
    }

    /**
     * Returns the directory where the given gallery images are uploaded.
     * 
     * @return string directory for uploding images
     */
    public function getImageUploadDirForGallery($id) {
        $dir = $this->getImageUploadDir() . '/' . $id;
        if (!file_exists($dir)) {
            $oldumask = umask(0);
            echo mkdir($dir, 0777, true);
            umask($oldumask);
        }
        return $dir;
    }

    /**
     * Returns the URL of the directory where the images are uploaded.
     * 
     * @return string URL of the directory for uploded images
     */
    public function getImageUploadURL() {
        $dir = $this->getImageUploadDir();
        return str_replace('/admin', '', $this->getRootURL() . '/' . IMG_UPLOAD_FOLDER);
    }

    /**
     * Returns the URL of the gallery directory where its images are uploaded.
     * 
     * @return string URL of the gallery directory for its uploded images
     */
    public function getImageUploadURLForGallery($id) {
        $dir = $this->getImageUploadDirForGallery($id);
        return $this->getImageUploadURL() . '/' . $id;
    }

    /**
     * Returns how after how long the login should expire without activity (in seconds).
     * 
     * @return integer login expiration time in seconds
     */
    public function getLoginExpire() {
        return LOGIN_EXPIRE * 60;
    }

    /**
     * Delete given directory recursively.
     * 
     * @param string $dir directory
     */
    public function deleteDir($dir) {
        $files = array_diff(scandir($dir), array('.', '..'));
        foreach ($files as $file) {
            $file = $dir . '/' . $file;
            if (is_dir($file)) {
                deleteDir($file);
            } else {
                unlink($file);
            }
        }
        rmdir($dir);
    }

}
