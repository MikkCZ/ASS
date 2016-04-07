<?php

namespace model;

/**
 * Gallery handles getting information about requested gallery page.
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Gallery extends ModelClass {

    private $gallery;
    private $user;
    private $fotos;
    private $totalFotos;
    private $page;
    private $limit = 20;

    protected function load() {
        if (isset($_GET['id'])) {
            $id = filter_input(INPUT_GET, 'id');
            $this->gallery = \GalleriesTable::getInstance()->getByID($id);
        }
        if ($this->gallery == NULL) {
            throw new NotFoundException();
        }
        if (isset($_GET['page'])) {
            $this->page = intval(filter_input(INPUT_GET, 'page'));
        } else {
            $this->page = 0;
        }
        $this->user = \UsersTable::getInstance()->getForGallery($this->gallery);
        $this->totalFotos = \FotosTable::getInstance()->getCountForGallery($this->gallery);
        $this->fotos = \FotosTable::getInstance()->getForGallery($this->gallery, $this->page, $this->limit);
    }

    /**
     * Returns Galleries class instance for the current gallery.
     * 
     * @return \Galleries this gallery instance
     */
    public function getGallery() {
        return $this->gallery;
    }

    /**
     * Returns Users class instance, which is owner of the current gallery.
     * 
     * @return \Users this gallery owner
     */
    public function getUser() {
        return $this->user;
    }

    /**
     * Returns fotos from the current gallery in an array.
     * 
     * @return array fotos of this gallery
     */
    public function getFotos() {
        return $this->fotos;
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
        return intval(ceil((double) $this->totalFotos / (double) $this->limit));
    }

}
