<?php

namespace view;

/**
 * Description of Index
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Index extends ViewClass {

    private $buttonsNavHTML;
    private $galleriesSamplesHTML;

    public function display() {
        if ($this->model->buttonsNav()) {
            $this->prepareButtonsHTML();
        }
        $galleriesSamples = $this->model->getGalleriesSamples();
        if ($galleriesSamples != NULL) {
            $this->prepareGalleriesSamplesHTML($galleriesSamples);
        }
        $this->includeTemplate('index');
    }

    protected function mainContent() {
        $this->galleriesSamplesHTML();
        $this->buttonsNavHTML();
    }

    private function prepareButtonsHTML() {
        $this->buttonsNavHTML = '<nav class="buttons">
                    <a href="./signup" id="signup">Registrace</a>
                    <a href="./login" id="login">Přihlásit se</a>
                </nav>';
    }

    private function prepareGalleriesSamplesHTML(array $galleries) {
        $this->galleriesSamplesHTML .= '<section id="party">
                    <h2>Ukázky galerií</h2>';
        foreach ($galleries as $gallery) {
            $href = \Config::getInstance()->getRootURL() . '/gallery/?id=' . $gallery['id'];
            $alt = $gallery['name'];
            if (isset($gallery['Fotos']['0']['thumbname'])) {
                $src = \Config::getInstance()->getImageUploadURLForGallery($gallery['id']) . '/' . $gallery['Fotos']['0']['thumbname'];
                $this->galleriesSamplesHTML .= '<a href="' . $href . '"><img src="' . $src . '" alt="' . $alt . '"></a>';
            }
        }
        $this->galleriesSamplesHTML .= '</section>';
    }

    /**
     * Outputs the buttons (signup/login) HTML.
     */
    protected function buttonsNavHTML() {
        echo $this->buttonsNavHTML;
    }

    /**
     * Outputs the galleries samples.
     */
    protected function galleriesSamplesHTML() {
        echo $this->galleriesSamplesHTML;
    }

}
