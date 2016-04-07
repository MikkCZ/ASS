<?php

namespace admin\model;

/**
 * Description of User
 *
 * @author Michal Stanke <michal.stanke@mikk.cz>
 */
class User extends ModelClass {

    private $user;
    private $roles;
    private $galleries;
    private $status;

    protected function load() {
        if (isset($_GET['id'])) {
            $id = filter_input(INPUT_GET, 'id');
        } else if ($this->loggedIn()) {
            $id = $this->loggedIn()->getID();
        } else {
            throw new \model\NotFoundException();
        }
        $this->user = \UsersTable::getInstance()->getByID($id);
        if($this->loggedIn()->isAdmin()) {
            $this->roles = \RolesTable::getInstance()->getAll();
        }
        if ($this->user == NULL) {
            throw new \model\NotFoundException();
        }
        if ($this->user != $this->loggedIn() && !($this->loggedIn()->isAdmin())) {
            throw new \model\NotFoundException();
        }
        \GalleriesTable::getInstance();
        if (isset($_GET['update'])) {
            try {
                $this->status = $this->updateUser($this->user);
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else if (isset($_GET['create'])) {
            try {
                $this->status = $this->createGallery(filter_input(INPUT_POST, 'createname'));
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else if (isset($_GET['deleteGallery'])) {
            try {
                $this->status = $this->deleteGallery(intval(filter_input(INPUT_GET, 'deleteGallery')));
            } catch (\Exception $ex) {
                $this->status = false;
            }
        } else {
            $this->status = NULL;
        }
        $this->galleries = \GalleriesTable::getInstance()->getForUser($this->user, 0, 0);
    }

    private function deleteGallery($id) {
        $gallery = \GalleriesTable::getInstance()->getByID($id);
        if ($gallery != NULL) {
            $uploadDir = \Config::getInstance()->getImageUploadDirForGallery($gallery->getID());
            $gallery->delete();
            \Config::getInstance()->deleteDir($uploadDir);
            return true;
        } else {
            return false;
        }
    }

    private function createGallery($name) {
        if ($name == NULL) {
            return false;
        }
        $gallery = new \Galleries();
        $gallery->setName($name);
        $gallery->setOwner($this->user->getID());
        $gallery->save();
    }

    private function updateUser(\Users $user) {
        $status = $this->formValid();
        if (empty($status)) {
            $user->setName(filter_input(INPUT_POST, 'name'));
            $user->setEmail(trim(filter_input(INPUT_POST, 'email')));
            if (isset($_POST['new_password']) && filter_input(INPUT_POST, 'new_password') != '') {
                $user->setPassword(trim(filter_input(INPUT_POST, 'new_password')));
            }
            if ($this->loggedIn()->isAdmin() && isset($_POST['role']) && filter_input(INPUT_POST, 'role') != '') {
                $user->setRoleID(intval(filter_input(INPUT_POST, 'role')));
            }
            $user->save();
            return true;
        }
        return $status;
    }

    private function formValid() {
        $status = array(
                /* 'email' => NULL,
                  'new_password' => NULL */
        );
// email
        if (!isset($_POST['email'])) {
            $status['email'] = 'Povinné';
        } else if (!filter_var(trim(filter_input(INPUT_POST, 'email')), FILTER_VALIDATE_EMAIL)) {
            $status['email'] = 'Neplatné';
        } else if ($this->user->getEmail() !== trim(filter_input(INPUT_POST, 'email')) && \UsersTable::getInstance()->emailExists(trim(filter_input(INPUT_POST, 'email')))) {
            $status['email'] = 'Email již existuje';
        }
// new_password
        if (isset($_POST['new_password']) && filter_input(INPUT_POST, 'new_password') != '') {
            if (strlen(utf8_decode(filter_input(INPUT_POST, 'new_password'))) < \Users::MIN_PASSWORD_LENGTH) {
                $status['new_password'] = 'Heslo je kratší než 6 znaků';
            } else if (strpos(trim(filter_input(INPUT_POST, 'new_password')), ' ') !== false) {
                $status['new_password'] = 'Mezera není povolena';
            }
        }
        return $status;
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
     * Return all available roles for the user.
     * 
     * @return array roles
     */
    public function getRoles() {
        return $this->roles;
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
     * Returns the status of the form or request.
     * 
     * @return mixed boolean (if successful of not) or NULL, if no login attempt has been sent
     */
    public function getStatus() {
        return $this->status;
    }

}
