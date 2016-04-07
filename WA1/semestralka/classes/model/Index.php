<?php

namespace model;

/**
 * Index handles getting information shown on the index page.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Index extends ModelClass {

    private $galleriesSamples;

    protected function load() {
        $this->galleriesSamples = \GalleriesTable::getInstance()->getLatestGalleries(5);
    }

    /**
     * Check the buttons navigation should be shown on the index page.
     * 
     * @return boolean show buttons navigation (signup / login)
     */
    public function buttonsNav() {
        return !$this->loggedIn();
    }

    /**
     * Returns latest galleries in an array, which should be shown on the index page.
     * 
     * @return array sample galleries
     */
    public function getGalleriesSamples() {
        return $this->galleriesSamples;
    }

}
