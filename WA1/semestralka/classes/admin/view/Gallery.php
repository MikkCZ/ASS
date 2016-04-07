<?php

namespace admin\view;

/**
 * Description of Gallery
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Gallery extends ViewClass {

    private $gallery;
    private $status;
    private $user;
    private $formHTML;
    private $fotosHTML;
    private $uploadHTML;

    protected function breadcrumb() {
        $rootURL = \Config::getInstance()->getRootURL();
        echo '<nav id="breadcrumb">
                        <a href="' . $rootURL . '">'.$this->model->getTitle().'</a> &gt; <a href="' . $rootURL . '/user?id=' . $this->user->getID() . '">' . $this->user->getPublicName() . '</a> &gt; <a href="' . $rootURL . '/gallery?id=' . $this->gallery->getID() . '">' . $this->gallery->getName() . '</a>
                  </nav>';
    }

    protected function mainContent() {
        $this->breadcrumb();
        $this->formHTML();
        $this->uploadHTML();
        $this->fotosHTML();
    }

    public function display() {
        $this->gallery = $this->model->getGallery();
        $this->status = $this->model->getStatus();
        $this->user = $this->model->getUser();
        $this->prepareFormHTML();
        $fotos = $this->model->getFotos();
        $this->prepareUploadHTML();
        if ($fotos != NULL) {
            $this->prepareFotosHTML($fotos);
        }
        $this->includeTemplate('gallery');
    }

    private function prepareFotosHTML(array $fotos) {
        $galleryImgRoot = \Config::getInstance()->getImageUploadURLForGallery($this->gallery->getId());
        $this->fotosHTML = '<section id="photos">';
        if ($this->status === true && isset($_GET['deleteFoto'])) {
            $this->fotosHTML .= '<div class = "ok">
                Fotografie byla smazána.
            </div>';
        }
        foreach ($fotos as $foto) {
            $href = $galleryImgRoot . '/' . $foto['filename'];
            $alt = $foto['name'];
            $src = $galleryImgRoot . '/' . $foto['thumbname'];
            $id = $foto['id'];
            $this->fotosHTML .= '<article>';
            $this->fotosHTML .= '<a href="' . $href . '"><img src="' . $src . '" alt="' . $alt . '"></a>';
            $this->fotosHTML .= '</article>';
            $this->fotosHTML .= '<a href="?id=' . $this->gallery->getID() . '&amp;deleteFoto=' . $id . '" class="delete">X</a>';
        }
        $this->fotosHTML .= '</section>';
    }

    private function prepareUploadHTML() {
        $this->uploadHTML = '<form action = "?id=' . $this->gallery->getID() . '&amp;upload" method="post" enctype="multipart/form-data">
                <label for = "fotos">Zvolte fotografie k nahrání:</label>
                <input type="file" name="fotos" id="fotos" multiple>
                <input type = "submit" name = "submit" value = "Nahrát" id = "upload">
            </form>';
    }

    private function prepareFormHTML() {
        if ($this->status === NULL) {
            $this->simpleForm();
        } else if ($this->status === true) {
            $this->formOK();
        } else {
            $this->errorForm();
        }
    }

    private function errorForm() {
        $this->formHTML = '<div class="error">
                Změna údajů neproběhla úspěšně. Zkontolujte prosím správnost zadávaných údajů.
            </div>';
        $this->formHTML .= '<form action = "?id=' . $this->gallery->getID() . '&amp;update" method = "post">';
        if (isset($this->status['name'])) {
            $this->formHTML .= '<label for = "name">Název galerie:<span class="warning">' . $this->status['name'] . '</span></label>'
                    . '<input type = "text" name = "name" id = "name" required value="' . filter_input(INPUT_POST, 'name') . '">';
        } else {
            $this->formHTML .= '<label for = "name">Název galerie:</label>
            <input type = "text" name = "name" id = "name" required value="' . filter_input(INPUT_POST, 'name') . '">';
        }
        $this->formHTML .= '<label for = "description">Popis galerie:</label>
            <textarea name="description" id="description">' . filter_input(INPUT_POST, 'description') . '</textarea>';
        $this->formHTML .= '<input type = "submit" name = "submit" value = "Nastavit" id = "submit">
        </form>';
    }

    private function formOK() {
        $this->formHTML = '<div class = "ok">
            Změna údajů proběhla úspěšně.
        </div>';
        $this->formHTML .= $this->simpleForm();
    }

    private function simpleForm() {
        $this->formHTML .= '<form action = "?id=' . $this->gallery->getID() . '&amp;update" method = "post">
            <label for = "name">Název galerie:</label>
            <input type = "text" name = "name" id = "name" required value="' . $this->gallery->getName() . '">
            <label for = "description">Popis galerie:</label>
            <textarea name="description" id="description">' . $this->gallery->getDescription() . '</textarea>
            <input type = "submit" name = "submit" value = "Nastavit" id = "submit">
        </form>';
    }

    /**
     * Outputs the form HTML.
     */
    protected function formHTML() {
        echo $this->formHTML;
    }

    /**
     * Outputs upload form HTML.
     */
    protected function uploadHTML() {
        echo $this->uploadHTML;
    }

    /**
     * Outputs fotos list HTML.
     */
    protected function fotosHTML() {
        echo $this->fotosHTML;
    }

}
