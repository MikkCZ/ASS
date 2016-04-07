<?php

namespace admin\model;

/**
 * Description of Gallery
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class Gallery extends ModelClass {

    private $gallery;
    private $user;
    private $fotos;
    private $status;

    protected function load() {
        if (isset($_GET['id'])) {
            $id = filter_input(INPUT_GET, 'id');
            $this->gallery = \GalleriesTable::getInstance()->getByID($id);
        }
        if ($this->gallery == NULL) {
            throw new \model\NotFoundException();
        }
        $this->user = \UsersTable::getInstance()->getForGallery($this->gallery);
        if ($this->user == NULL) {
            throw new \model\NotFoundException();
        }
        if ($this->user != $this->loggedIn() && !($this->loggedIn()->allGalleries()) && !($this->loggedIn()->isAdmin())) {
            throw new \model\NotFoundException();
        }
        \FotosTable::getInstance();
        if (isset($_GET['update'])) {
            try {
                $this->status = $this->updateGallery($this->gallery);
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else if (isset($_GET['upload'])) {
            try {
                $this->status = $this->uploadFotos($this->gallery);
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else if (isset($_GET['deleteFoto'])) {
            try {
                $this->status = $this->deleteFoto(intval(filter_input(INPUT_GET, 'deleteFoto')));
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else {
            $this->status = NULL;
        }
        $this->fotos = \FotosTable::getInstance()->getForGallery($this->gallery, 0, 0);
    }

    private function deleteFoto($id) {
        $foto = \FotosTable::getInstance()->getByID($id);
        if ($foto != NULL) {
            $uploadDir = \Config::getInstance()->getImageUploadDirForGallery($this->gallery->getID());
            $file = $uploadDir . '/' . $foto->getFileName();
            $foto->delete();
            unlink($file);
            return true;
        } else {
            return false;
        }
    }

    private function uploadFotos(\Galleries $gallery) {
        if (isset($_FILES['fotos'])) {
            $count = count($_FILES['fotos']['name']);
            $uploadDir = \Config::getInstance()->getImageUploadDirForGallery($gallery->getID());
            for ($i = 0; $i < $count; $i++) {
                $name = $_FILES['fotos']['name'][$i];
                $path = $uploadDir . '/' . $name;
                $tmp = $_FILES['fotos']['tmp_name'][$i];
                if (move_uploaded_file($tmp, $path)) {
                    $this->uploadFotoToDB($gallery, $name);
                }
            }
        }
    }

    private function uploadFotoToDB(\Galleries $gallery, $filename) {
        $foto = \FotosTable::getInstance()->existsForGallery($gallery, $filename);
        if ($foto == NULL) {
            $foto = new \Fotos();
            $foto->setGalleryID($gallery->getId());
            $foto->setName($filename);
            $foto->setFileName($filename);
            $foto->setThumbname($filename);
            $foto->save();
        }
    }

    private function updateGallery(\Galleries $gallery) {
        $status = $this->formValid();
        if (empty($status)) {
            $gallery->setName(filter_input(INPUT_POST, 'name'));
            $gallery->setDescription(filter_input(INPUT_POST, 'description'));
            $gallery->save();
            return true;
        }
        return $status;
    }

    private function formValid() {
        $status = array(
                /* 'name' => NULL */
        );
// name
        if (!isset($_POST['name'])) {
            $status['name'] = 'Povinné';
        }
        return $status;
    }

    /**
     * Returns the current gallery object.
     * 
     * @return \Galleries gallery
     */
    public function getGallery() {
        return $this->gallery;
    }

    /**
     * Returns the gallery owner.
     * 
     * @return \Users owner
     */
    public function getUser() {
        return $this->user;
    }

    /**
     * Returns all the gallery fotos in an array.
     * 
     * @return array fotos
     */
    public function getFotos() {
        return $this->fotos;
    }

    /**
     * Returns the status of the form or request.
     * 
     * @return mixed boolean (if successful of not) or NULL, if no login attempt has been sent
     */
    public function getStatus() {
        return $this->status;
    }

}
