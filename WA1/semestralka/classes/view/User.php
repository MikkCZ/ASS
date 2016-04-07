<?php

namespace view;

/**
 * Description of User
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class User extends ViewClass {

    private $user;
    private $userInfoHTML;
    private $galleriesHTML;
    private $paginationHTML;

    protected function breadcrumb() {
        $rootURL = \Config::getInstance()->getRootURL();
        echo '<nav id="breadcrumb">
                        <a href="' . $rootURL . '">'.$this->model->getTitle().'</a> &gt; <a href="' . $rootURL . '/user?id=' . $this->user->getID() . '">' . $this->user->getPublicName() . '</a>
                  </nav>';
    }

    public function display() {
        $this->user = $this->model->getUser();
        $this->prepareUserInfoHTML($this->user);
        $galleries = $this->model->getGalleries();
        if ($galleries != NULL) {
            $this->prepareGalleriesHTML($galleries);
        }
        if ($galleries != NULL) {
            $this->preparePaginationHTML($this->model->getPage(), $this->model->getNumOfPages());
        }
        $this->includeTemplate('user');
    }

    protected function mainContent() {
        $this->breadcrumb();
        $this->userInfo();
        $this->galleriesHTML();
        $this->paginationHTML();
    }

    private function prepareUserInfoHTML(\Users $user) {
        $this->userInfoHTML .= '<section id="user-info">
                    <h2>' . $user->getPublicName() . '</h2>
                </section>';
    }

    private function prepareGalleriesHTML(array $galleries) {
        $this->galleriesHTML = '<section id="user-galleries">
                    <h3>Seznam galerií</h3>
                    <ul>';
        foreach ($galleries as $gallery) {
            $href = \Config::getInstance()->getRootURL() . '/gallery/?id=' . $gallery['id'];
            $alt = $gallery['name'];
            $src = \Config::getInstance()->getImageUploadURLForGallery($gallery['id']) . '/' . $gallery['Fotos']['0']['thumbname'];
            $this->galleriesHTML .= '<li>
                    <a href="' . $href . '"><img src="' . $src . '" alt="' . $alt . '"></a>
                    <aside>
                    <a href="' . $href . '"><h4>' . $gallery['name'] . ' <time>' . $gallery['uploaded'] . '</time></h4></a>
                    <div class="details">' . $gallery['description'] . '</div>
                    </aside>
                    </li>';
        }
        $this->galleriesHTML .= '</ul></section>';
    }

    private function preparePaginationHTML($page, $totalPages) {
        $userID = $this->user->getID();
        $this->paginationHTML = '<nav id="pagination">';
        for ($i = 0; $i < $totalPages; $i++) {
            if ($i != $page) {
                $this->paginationHTML .= '<a href="?id=' . $userID . '&page=' . $i . '">';
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
     * Outputs info about the user.
     */
    protected function userInfo() {
        echo $this->userInfoHTML;
    }

    /**
     * Outputs galleries of the user.
     */
    protected function galleriesHTML() {
        echo $this->galleriesHTML;
    }

    /**
     * Outputs pagination.
     */
    protected function paginationHTML() {
        echo $this->paginationHTML;
    }

}
