<?php

namespace view;

/**
 * Description of Gallery
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Gallery extends ViewClass {

    private $gallery;
    private $user;
    private $galleryInfoHTML;
    private $fotosHTML;
    private $paginationHTML;

    protected function breadcrumb() {
        $rootURL = \Config::getInstance()->getRootURL();
        echo '<nav id="breadcrumb">
                        <a href="' . $rootURL . '">'.$this->model->getTitle().'</a> &gt; <a href="' . $rootURL . '/user?id=' . $this->user->getID() . '">' . $this->user->getPublicName() . '</a> &gt; <a href="' . $rootURL . '/gallery?id=' . $this->gallery->getID() . '">' . $this->gallery->getName() . '</a>
                  </nav>';
    }

    public function display() {
        $this->gallery = $this->model->getGallery();
        $this->user = $this->model->getUser();
        $this->prepareGalleryInfoHTML($this->gallery, $this->user);
        $fotos = $this->model->getFotos();
        if ($fotos != NULL) {
            $this->prepareFotosHTML($fotos);
        }
        if ($fotos != NULL) {
            $this->preparePaginationHTML($this->model->getPage(), $this->model->getNumOfPages());
        }
        $this->includeTemplate('gallery');
    }

    protected function mainContent() {
        $this->breadcrumb();
        $this->galleryInfo();
        $this->fotosHTML();
        $this->paginationHTML();
    }

    private function prepareGalleryInfoHTML(\Galleries $gallery, \Users $user) {
        $rootURL = \Config::getInstance()->getRootURL();
        $this->galleryInfoHTML .= '<section id="gallery-info">
                    <h2>' . $gallery->getName() . '</h2>
                    <small>nahráno <time>' . $gallery->getUploadedTime() . '</time> od <a href="' . $rootURL . '/user?id=' . $user->getID() . '">' . $user->getPublicName() . '</a></small>
                        <div class="details">' . $gallery->getDescription() . '</div>
                </section>';
    }

    private function prepareFotosHTML(array $fotos) {
        $galleryImgRoot = \Config::getInstance()->getImageUploadURLForGallery($this->gallery->getId());
        $this->fotosHTML = '<section id="photos">';
        foreach ($fotos as $foto) {
            $href = $galleryImgRoot . '/' . $foto['filename'];
            $alt = $foto['name'];
            $src = $galleryImgRoot . '/' . $foto['thumbname'];
            $this->fotosHTML .= '<div class="foto"><a href="' . $href . '"><img src="' . $src . '" alt="' . $alt . '"></a></div>';
        }
        $this->fotosHTML .= '</section>';
    }

    private function preparePaginationHTML($page, $totalPages) {
        $galleryID = $this->gallery->getID();
        $this->paginationHTML = '<nav id="pagination">';
        for ($i = 0; $i < $totalPages; $i++) {
            if ($i != $page) {
                $this->paginationHTML .= '<a href="?id=' . $galleryID . '&page=' . $i . '">';
            }
            $this->paginationHTML .= $i + 1;
            if ($i != $page) {
                $this->paginationHTML .= '</a>';
            }
            if ($i != $totalPages - 1) {
                $this->paginationHTML .= ' | ';
            }
        }
        $this->paginationHTML .= '</nav>';
    }

    /**
     * Outputs info about the gallery.
     */
    protected function galleryInfo() {
        echo $this->galleryInfoHTML;
    }

    /**
     * Outputs fotos of the gallery.
     */
    protected function fotosHTML() {
        echo $this->fotosHTML;
    }

    /**
     * Outputs pagination.
     */
    protected function paginationHTML() {
        echo $this->paginationHTML;
    }

}
