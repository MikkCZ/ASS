<?php

namespace model;

/**
 * User handles getting information about requested user page.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class User extends ModelClass {

    private $user;
    private $galleries;
    private $totalGalleries;
    private $page;
    private $limit = 10;

    protected function load() {
        if (isset($_GET['id'])) {
            $id = filter_input(INPUT_GET, 'id');
        } else if ($this->loggedIn()) {
            $id = $this->loggedIn()->getID();
        } else {
            throw new NotFoundException();
        }
        if (isset($_GET['page'])) {
            $this->page = intval(filter_input(INPUT_GET, 'page'));
        } else {
            $this->page = 0;
        }
        $this->user = \UsersTable::getInstance()->getByID($id);
        if ($this->user == NULL) {
            throw new NotFoundException();
        }
        $this->totalGalleries = \GalleriesTable::getInstance()->getCountForUser($this->user);
        $this->galleries = \GalleriesTable::getInstance()->getForUser($this->user, $this->page, $this->limit);
    }

    /**
     * Returns Users class instance for the current user.
     * 
     * @return \Users this user instance
     */
    public function getUser() {
        return $this->user;
    }

    /**
     * Returns galleries of the current user in an array.
     * 
     * @return array galleries of this user
     */
    public function getGalleries() {
        return $this->galleries;
    }
    
    /**
     * Returns current page number.
     * 
     * @return integer page
     */
    public function getPage() {
        return $this->page;
    }

    /**
     * Returns total number of pages.
     * 
     * @return integer pages
     */
    public function getNumOfPages() {
        return intval(ceil((double) $this->totalGalleries / (double) $this->limit));
    }

}
